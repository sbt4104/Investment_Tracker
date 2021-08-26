package com.natwest.portfolio.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WatchList {
	@Id
	Integer id;
	
	User user;
	Double amount;
	List<String> stocks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public List<String> getStocks() {
		return stocks;
	}
	public void setStocks(List<String> stocks) {
		this.stocks = stocks;
	}
}
