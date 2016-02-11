package com.szech.bank.storage;

import java.util.HashMap;
import java.util.Map;

import com.szech.bank.Account;


public class AccountStore {
	private static Map<Integer,Account> store;
	private static AccountStore instance = null;
	
	private AccountStore() {
		store = new HashMap<Integer,Account>();
		initAccounts();
	}
	
	public static Map<Integer,Account> getStore() {
		if(instance==null) {
			instance = new AccountStore();
		}
		return store;
	}
	
	private static void initAccounts() {
		Account scrooge = new Account(1, 30000, 1);
		Account scrooge2 = new Account(2, 50000, 1);
		Account donald = new Account(3, 50, 2);
		Account greenback = new Account(4, 30000, 3);
		Account greenback2 = new Account(5, 250000, 3);
		
		store.put(scrooge.getId(), scrooge);
		store.put(scrooge2.getId(), scrooge2);
		store.put(donald.getId(), donald);
		store.put(greenback.getId(), greenback);
		store.put(greenback2.getId(), greenback2);
	}
}
