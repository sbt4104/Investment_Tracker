package com.natwest.portfolio.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	@Id
	String userid;
	
	Integer password;
	String firstName;
	String lastName;
	String mobileNumber;
	
	List<PortFolio> portfolios;
	WatchList watchlist;
	Bank bank;
	
	User(){
		this.portfolios = new ArrayList<PortFolio>();
		this.watchlist = new WatchList();
		this.bank = new Bank();
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getPassword() {
		return password;
	}
	public void setPassword(Integer password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public List<PortFolio> getPortfolios() {
		return portfolios;
	}
	public void setPortfolios(List<PortFolio> portfolios) {
		this.portfolios = portfolios;
	}
	public WatchList getWatchlist() {
		return watchlist;
	}
	public void setWatchlist(WatchList watchlist) {
		this.watchlist = watchlist;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
}
