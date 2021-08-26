package com.natwest.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.natwest.portfolio.model.User;

@Repository
public interface UserRepo extends MongoRepository<User,String> {

}
