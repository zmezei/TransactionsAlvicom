package com.alvicom.transactions.entities;

public class BankAccount {
	private String accountNumber;
	private String currency;
	private Double balance;
	private StringBuilder allTransactions = new StringBuilder("");
	
	public BankAccount(String accountNumber, String currency, Double balance) {
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.balance = balance;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void changeBalance(Double changingAmount) {
		balance += changingAmount;
	}
	
	public StringBuilder getAllTransactions() {
		return allTransactions;
	}
	
	public void changeTransactionList(Double changingAmount) {
		allTransactions.append("\n" + changingAmount);
	}
	
	@Override
	public String toString () {
		return(accountNumber + ' ' + currency + ' ' + balance);
	}
}

