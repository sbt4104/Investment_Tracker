package com.natwest.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.natwest.portfolio.model.Trade;

@Repository
public interface TradeRepo extends MongoRepository<Trade,String>{

}
