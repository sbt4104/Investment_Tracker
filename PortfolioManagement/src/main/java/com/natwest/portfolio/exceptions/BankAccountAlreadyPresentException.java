package com.natwest.portfolio.exceptions;

public class BankAccountAlreadyPresentException extends Exception{
	public BankAccountAlreadyPresentException(String msg) {
		super(msg);
	}
}
