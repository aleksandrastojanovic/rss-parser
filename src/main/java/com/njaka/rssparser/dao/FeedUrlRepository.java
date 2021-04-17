package com.njaka.rssparser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.njaka.rssparser.entity.FeedUrl;

public interface FeedUrlRepository extends
		JpaRepository<FeedUrl, Integer> {

	public FeedUrl findByUrl(String url);

	public void deleteByUrl(String url);

}
