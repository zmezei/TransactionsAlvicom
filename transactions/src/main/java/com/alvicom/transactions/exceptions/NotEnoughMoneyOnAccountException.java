package com.alvicom.transactions.exceptions;

public class NotEnoughMoneyOnAccountException extends RuntimeException {
	public NotEnoughMoneyOnAccountException(String errorMessage) {
		super(errorMessage);
	}
}
