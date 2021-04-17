package com.njaka.rssparser.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.njaka.rssparser.entity.Feed;
import com.njaka.rssparser.entity.FeedMessage;

public class RSSFeedParser {
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	// static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";
	static final String ENCODED = "encoded";

	final URL url;

	public RSSFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public Feed readFeed() {
		Feed feed = null;
		try (InputStream in = read()) {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			// String author = "";
			String pubdate = "";
			String guid = "";
			String encoded = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName()
							.getLocalPart();
					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new Feed(title, link, description, language,
									copyright, pubdate);
						}
						event = eventReader.nextEvent();
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						break;
					// case AUTHOR:
					// author = getCharacterData(event, eventReader);
					// break;
					case PUB_DATE:
						pubdate = getCharacterData(event, eventReader);
						break;

					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						break;
					case ENCODED:
						encoded = getCharacterData(event, eventReader);
						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						FeedMessage message = new FeedMessage();
						// message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						message.setImg(getImgUrl(encoded));
						message.setPubDate(pubdate);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException | IOException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	// private InputStream read() {
	// try {
	// return url.openStream();
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }
	//
	private InputStream read() {
		try {
			HttpURLConnection httpcon = (HttpURLConnection) url
					.openConnection();
			// za sad zakucano, ako treba promenicu
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

			return httpcon.getInputStream();
		} catch (IOException e) {
			// String error = e.toString();
			throw new RuntimeException(e.toString());
		}
	}

	private String getImgUrl(String encoded) {
		String img = "";

		if (encoded.startsWith("<img>")) {
			int firstPosition = encoded.indexOf("<img>");
			String temp = encoded.substring(firstPosition);
			temp = temp.replace("<img>", "");
			int lastPosition = temp.indexOf("</img>");
			temp = temp.substring(0, lastPosition);
			temp = temp.replace("</img>", "");
			img += temp + "\n";
		} else if (encoded.startsWith("<img src=\"")) {
			int firstPosition = encoded.indexOf("<img src=\"");
			String temp = encoded.substring(firstPosition);
			temp = temp.replace("<img src=\"", "");
			int lastPosition = temp.indexOf("\"");
			temp = temp.substring(0, lastPosition);
			temp = temp.replace("\"", "");
			img += temp + "\n";
		}

		return img;
	}

	@Override
	public String toString() {
		return "RSSFeedParser [url=" + url + "]";
	}

}
