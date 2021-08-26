package com.natwest.portfolio.exceptions;

public class TradeOperationNotAllowedException extends Exception{
	public TradeOperationNotAllowedException(String msg) {
		super(msg);
	}
}
