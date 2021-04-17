package com.njaka.rssparser.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.njaka.rssparser.entity.FeedUrl;

public interface FeedUrlService {

	public Page<FeedUrl> findAll(Pageable pageable);

	public List<FeedUrl> findAll();

	public FeedUrl findByUrl(String url);

	public void save(FeedUrl feedUrl);

	public void deleteByUrl(String url);

}
