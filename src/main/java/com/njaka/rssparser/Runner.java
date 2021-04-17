package com.njaka.rssparser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.njaka.rssparser.entity.Feed;
import com.njaka.rssparser.entity.FeedMessage;
import com.njaka.rssparser.entity.FeedUrl;
import com.njaka.rssparser.service.FeedMessageService;
import com.njaka.rssparser.service.FeedUrlService;
import com.njaka.rssparser.service.RSSFeedParser;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	private FeedMessageService feedMessageService;

	@Autowired
	private FeedUrlService feedUrlService;

	@Override
	public void run(String... args) throws Exception {

		RSSFeedParser rssFeedParser;
		List<FeedUrl> list = feedUrlService.findAll();
		
		for(FeedUrl url : list){
			rssFeedParser = new RSSFeedParser(url.getUrl());
			Feed feed = rssFeedParser.readFeed();
			List<FeedMessage> messages = feed.getMessages();
			for (FeedMessage message : messages) {
				if (feedMessageService.findByGuid(message.getGuid()) == null) {
					feedMessageService.save(message);
				}
			}
		}

	}

}
