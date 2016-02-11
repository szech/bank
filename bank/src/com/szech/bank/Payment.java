package com.szech.bank;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Payment {

	private double amount;
	private int fromAccountID;
	private int toAccountID;
	private Date timeOfPayment;
	private int id;
	
	public Payment() {}
	
	public Payment(int id, double amount, int fromAccountID, int toAccountID, Account fromAccount,
			Account toAccount, Date timeOfPayment) {
		super();
		this.amount = amount;
		this.fromAccountID = fromAccountID;
		this.toAccountID = toAccountID;
		this.timeOfPayment = timeOfPayment;
		this.id = id;
	}

	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@XmlElement
	public int getFromAccountID() {
		return fromAccountID;
	}
	public void setFromAccountID(int fromAccountID) {
		this.fromAccountID = fromAccountID;
	}
	
	@XmlElement
	public int getToAccountID() {
		return toAccountID;
	}
	public void setToAccountID(int toAccountID) {
		this.toAccountID = toAccountID;
	}
	
	@XmlElement
	public String getTimeOfPayment() {
		return timeOfPayment.toString();
	}
	
	@XmlTransient
	public Date getTimeOfPaymentAsDate() {
		return timeOfPayment;
	}
	public void setTimeOfPayment(Date timeOfPayment) {
		this.timeOfPayment = timeOfPayment;
	}
	
}
