package com.natwest.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.natwest.portfolio.model.Bank;

@Repository
public interface BankRepo extends MongoRepository<Bank,Long>{

}
