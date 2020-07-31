package com.alvicom.transactions.service.impl;

import com.alvicom.transactions.entities.BankAccount;
import com.alvicom.transactions.exceptions.NotEnoughMoneyOnAccountException;
import com.alvicom.transactions.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	@Override
	public void deposit(BankAccount account, Double amount, Integer exchangeRate) throws NotEnoughMoneyOnAccountException {
		if (exchangeRate == null) {
			if (account.getBalance() + amount >= 0) {
				account.changeBalance(amount);
				account.changeTransactionList(amount);
			} else {
				throw new NotEnoughMoneyOnAccountException("This account does not have enough money to commit this transaction!");
			}
		} else if (exchangeRate != null) {
			if (account.getBalance() + exchangeRate * amount >= 0) {
				account.changeBalance(exchangeRate * amount);
				account.changeTransactionList(exchangeRate * amount);
			} else {
				throw new NotEnoughMoneyOnAccountException("This account does not have enough money to commit this transaction!");
			}
		}
	}
	
}
