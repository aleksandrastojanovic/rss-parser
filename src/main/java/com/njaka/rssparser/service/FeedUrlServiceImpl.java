package com.njaka.rssparser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.njaka.rssparser.dao.FeedUrlRepository;

import com.njaka.rssparser.entity.FeedUrl;

@Service
public class FeedUrlServiceImpl implements FeedUrlService {

	FeedUrlRepository feedUrlRepository;

	@Autowired
	public FeedUrlServiceImpl(FeedUrlRepository feedUrlRepository) {
		this.feedUrlRepository = feedUrlRepository;
	}

	@Override
	public Page<FeedUrl> findAll(Pageable pageable) {
		return feedUrlRepository.findAll(pageable);
	}

	@Override
	public List<FeedUrl> findAll() {
		return feedUrlRepository.findAll();
	}

	@Override
	public FeedUrl findByUrl(String url) {
		return feedUrlRepository.findByUrl(url);
	}

	@Override
	public void save(FeedUrl feedUrl) {
		feedUrlRepository.save(feedUrl);
	}

	@Override
	public void deleteByUrl(String url) {
		feedUrlRepository.deleteByUrl(url);
	}

	// @Override
	// public Page<FeedMessage> findAll(Pageable pageable) {
	// return feedMessageRepository.findAll(pageable);
	// }

}
