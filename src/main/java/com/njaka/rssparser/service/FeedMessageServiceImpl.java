package com.njaka.rssparser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.njaka.rssparser.dao.FeedMessageRepository;
import com.njaka.rssparser.entity.Feed;
import com.njaka.rssparser.entity.FeedMessage;

@Service
public class FeedMessageServiceImpl implements FeedMessageService {

	FeedMessageRepository feedMessageRepository;

	@Autowired
	public FeedMessageServiceImpl(FeedMessageRepository feedMessageRepository) {
		this.feedMessageRepository = feedMessageRepository;
	}

	public FeedMessage find(int id) {
		Optional<FeedMessage> result = feedMessageRepository.findById(id);

		FeedMessage feedMessage = null;

		if (result.isPresent()) {
			feedMessage = result.get();
		} else {
			throw new RuntimeException("Feed id not found: " + id);
		}
		return feedMessage;
	}

	public void save(FeedMessage feedMessage) {
		feedMessageRepository.save(feedMessage);

	}

	public int save(String channel) {
		String url = getUrl(channel);
		int i = 0;
		RSSFeedParser rssFeedParser = new RSSFeedParser(url);
		Feed feed = rssFeedParser.readFeed();
		List<FeedMessage> messages = feed.getMessages();
		for (FeedMessage message : messages) {

			if (feedMessageRepository.findByGuid(message.getGuid()) == null) {
				feedMessageRepository.save(message);
				i++;
			}
		}
		return i;
	}

	public void deleteById(int id) {
		feedMessageRepository.deleteById(id);

	}

	private String getUrl(String channel) {
		String url = "";
		switch (channel) {
		case "n1":
			url = "https://rs-8nqof7qzeod2et99kimwqegbnmsmjnby.n1info.com/feed/";
			break;
		case "blic":
			url = "https://www.blic.rs/rss/danasnje-vesti";
			break;
		default:
			break;
		}
		return url;
	}

	@Override
	public Page<FeedMessage> findAll(Pageable pageable) {
		return feedMessageRepository.findAll(pageable);
	}

	@Override
	public List<FeedMessage> findAll() {

		return feedMessageRepository.findAll();
	}

	@Override
	public FeedMessage findByGuid(String guid) {
		// TODO Auto-generated method stub
		return feedMessageRepository.findByGuid(guid);
	}

}
