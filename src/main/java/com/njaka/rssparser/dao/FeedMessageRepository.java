package com.njaka.rssparser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.njaka.rssparser.entity.FeedMessage;

public interface FeedMessageRepository extends
		JpaRepository<FeedMessage, Integer> {

	public FeedMessage findByGuid(String guid);

}
