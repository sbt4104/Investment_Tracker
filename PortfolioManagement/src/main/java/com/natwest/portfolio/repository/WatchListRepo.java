package com.natwest.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.natwest.portfolio.model.WatchList;

@Repository
public interface WatchListRepo extends MongoRepository<WatchList,Integer>{

}
