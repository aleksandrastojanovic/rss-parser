package com.njaka.rssparser.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.njaka.rssparser.entity.FeedMessage;

public interface FeedMessageService {

	public Page<FeedMessage> findAll(Pageable pageable);

	public List<FeedMessage> findAll();

	public FeedMessage find(int id);

	public void save(FeedMessage feedMessage);

	public int save(String channel);

	public void deleteById(int id);

	public FeedMessage findByGuid(String guid);

}
