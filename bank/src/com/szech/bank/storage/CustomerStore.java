package com.szech.bank.storage;

import java.util.HashMap;
import java.util.Map;

import com.szech.bank.Customer;


public class CustomerStore {
	private static Map<Integer,Customer> store;
	private static CustomerStore instance = null;
	
	private CustomerStore() {
		store = new HashMap<Integer,Customer>();
		initCustomers();
	}
	
	public static Map<Integer,Customer> getStore() {
		if(instance==null) {
			instance = new CustomerStore();
		}
		return store;
	}
	
	private static void initCustomers() {
		Customer scrooge = new Customer(1, "Scrooge McDuck");
		Customer donald = new Customer(2, "Donald Duck");
		Customer greenback = new Customer(3, "Baron Greenback");
		
		store.put(scrooge.getId(), scrooge);
		store.put(donald.getId(), donald);
		store.put(greenback.getId(), greenback);
	}
}
