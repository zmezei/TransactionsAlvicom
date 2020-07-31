package com.alvicom.transactions.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.alvicom.transactions.entities.BankAccount;
import com.alvicom.transactions.exceptions.NotEnoughMoneyOnAccountException;
import com.alvicom.transactions.service.TransactionService;
import com.alvicom.transactions.service.impl.TransactionServiceImpl;

public class TransactionsAlvicom {
	public static void main(String[] args) {
		TransactionsAlvicom main = new TransactionsAlvicom();
		
		Integer numberOfTransactions = 0;
		Boolean wantToTransferMore = true;
		
		TransactionService transactionService = new TransactionServiceImpl();
		
		List<BankAccount> allAccounts = main.readAccountDataFromCsv("/exampleAccounts.csv");
		System.out.println("Available accounts:");
		allAccounts.stream().forEach(account -> {
			System.out.println("Account number: " + account.getAccountNumber() + "\tcurrency: " + account.getCurrency() + "\tBalance: " + account.getBalance());
		});
		System.out.println("");
		
		Scanner scanner = new Scanner(System.in);
		
		do {			
			System.out.println("Enter the account number:");
			String currentAccountNumber = scanner.next();
						
			Optional<BankAccount> foundAccount = allAccounts.stream().filter(account -> currentAccountNumber.equals(account.getAccountNumber())).findFirst();
			if (foundAccount.isPresent()) {
				System.out.println("Currency: " + foundAccount.get().getCurrency() + "\tBalance: " + foundAccount.get().getBalance() + "\nEnter the currency:");
				String currentCurrency = scanner.next();
				
				System.out.println("Enter the amount:");
				Double currentAmount;
				while ((currentAmount = scanner.nextDouble()) == 0) {
					System.out.println("Amount can not be zero, enter a non-zero value:");
				};
				
				Integer currentExchangeRate = null;
				if (!currentCurrency.equals(foundAccount.get().getCurrency())) {
					System.out.println("Enter the exchange rate:");
					while((currentExchangeRate = scanner.nextInt()) < 0) {
						System.out.println("Exchange rate can not be zero or negative, enter a positive value:");
					}
				}
				
				try {
					transactionService.deposit(foundAccount.get(), currentAmount, currentExchangeRate);
					System.out.println("New balance: " + foundAccount.get().getBalance());
					if (++numberOfTransactions % 10 == 0) {
						allAccounts.stream().forEach(account -> {
							System.out.println("\nAccount info:\nAccount number: " + account.getAccountNumber() + "\tcurrency: " + account.getCurrency() + "\tBalance: " + account.getBalance());
							System.out.println("All transactions in " + account.getCurrency() + ":" + account.getAllTransactions());
						});
					}
				} catch (NotEnoughMoneyOnAccountException e) {
					System.out.println("This account does not have enough money to commit this transaction!");
				}
			} else {
				System.out.println("This account number does not exist!");
			}			
			System.out.println("\nWould you like to start another transaction (y/n)?");
			if (!"y".equalsIgnoreCase(scanner.next())) {
				wantToTransferMore = false;
			}
			System.out.println("");
		} while (wantToTransferMore);
		
		scanner.close();
	}
	
	private List<BankAccount> readAccountDataFromCsv(String filePath) {
		List<BankAccount> accounts = new ArrayList<BankAccount>();
		
		String line = "";
		
		try {
			InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream(filePath));
			BufferedReader br = new BufferedReader(isr);
			br.readLine();
			
			while ((line = br.readLine()) != null) {
				accounts.add(createAccount(line.split(";")));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	private static BankAccount createAccount(String[] accountData) {
		Double currentAmount = null;
		try {
			currentAmount = Double.parseDouble(accountData[2]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return new BankAccount(accountData[0], accountData[1], currentAmount);
	}
}

