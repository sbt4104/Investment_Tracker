package com.natwest.portfolio.exceptions;

public class NotEnoughStocksToSellException extends Exception{
	public NotEnoughStocksToSellException(String msg) {
		super(msg);
	}
}
