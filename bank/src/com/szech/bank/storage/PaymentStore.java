package com.szech.bank.storage;

import java.util.HashMap;
import java.util.Map;

import com.szech.bank.Payment;


public class PaymentStore {
	private static Map<Integer,Payment> store;
	private static PaymentStore instance = null;
	
	private PaymentStore() {
		store = new HashMap<Integer,Payment>();
		initPayments();
	}
	
	public static Map<Integer,Payment> getStore() {
		if(instance==null) {
			instance = new PaymentStore();
		}
		return store;
	}
	
	private static void initPayments() {
	}
}
