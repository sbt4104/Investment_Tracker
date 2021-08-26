package com.natwest.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.natwest.portfolio.model.PortFolio;

@Repository
public interface PortFolioRepo extends MongoRepository<PortFolio,String>{

}
