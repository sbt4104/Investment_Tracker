package com.natwest.portfolio.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PortFolio {
	@Id
	String id;

	String name;
	
	Date timestamp;
	Double totalPrice;
	String userid;
	List<Trade> trades;
	Map<String, Integer> stockMap;

	public PortFolio(){
		this.timestamp = new Date();
		this.totalPrice = 0.0;
		stockMap = new TreeMap<String, Integer>();
		trades = new ArrayList<Trade>();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Map<String, Integer> getStockMap() {
		return stockMap;
	}

	public void setStockMap(Map<String, Integer> stockMap) {
		this.stockMap = stockMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getUserId() {
		return this.userid;
	}
	
	public void setUserId(String userId) {
		this.userid = userId;
	}
	
	public List<Trade> getTrades() {
		return trades;
	}
	
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

}
