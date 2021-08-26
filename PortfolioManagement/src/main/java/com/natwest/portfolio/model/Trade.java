package com.natwest.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Trade {

	@Id
	String id;
	
	String stock; // stock name is used while fetching data from json server 
	String portfolioId;
	
	// total quantity of a stock
	Integer quantity;
	
	// price of single stock at the time of operation
	Double price;

	// type of operation
	String buySell;
	
	PortFolio portfolio;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(String transId) {
		this.portfolioId = transId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getBuySell() {
		return buySell;
	}
	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}
	public PortFolio getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(PortFolio portfolio) {
		this.portfolio = portfolio;
	}
}
