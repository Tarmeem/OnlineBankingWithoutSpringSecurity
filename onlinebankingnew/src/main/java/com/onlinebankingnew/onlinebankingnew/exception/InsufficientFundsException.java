package com.onlinebankingnew.onlinebankingnew.exception;

@SuppressWarnings("serial")
public class InsufficientFundsException extends RuntimeException {
	public InsufficientFundsException(String message) {
		super(message);
	}

}
