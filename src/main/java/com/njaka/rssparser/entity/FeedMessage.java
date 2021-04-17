package com.njaka.rssparser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/*
 * Represents one RSS message
 */
@Entity
@Table(name = "feed_messages")
public class FeedMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "link")
	private String link;

	// @Column(name = "author")
	// private String author;

	@Column(name = "guid")
	private String guid;

	@Lob
	@Column(name = "img")
	private String img;

	@Column(name = "pub_date")
	private String pubDate;

	public FeedMessage() {

	}

	public FeedMessage(String title, String description, String link,
			String guid, String img, String pubDate) {
		super();
		this.title = title;
		this.description = description;
		this.link = link;
		// this.author = author;
		this.guid = guid;
		this.img = img;
		this.pubDate = pubDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	// public String getAuthor() {
	// return author;
	// }
	//
	// public void setAuthor(String author) {
	// this.author = author;
	// }

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "FeedMessage [title=" + title + ", description=" + description
				+ ", link=" + link + ", guid=" + guid + "]";
	}

}
