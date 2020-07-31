package com.alvicom.transactions.service;

import com.alvicom.transactions.entities.BankAccount;

public interface TransactionService {
	public void deposit(BankAccount account, Double amount, Integer exchangeRate);
}
