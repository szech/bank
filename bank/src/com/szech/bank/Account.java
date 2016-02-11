package com.szech.bank;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Account {

	private int id;
	private int customerID;
	private double balance;
	
	
	public Account() {
	}
	
	public Account(int id, double balance, int customerID) {
		this.id = id;
		this.balance = balance;
		this.customerID = customerID;
	}
	
	@XmlElement
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@XmlElement
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	
	@XmlElement
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
