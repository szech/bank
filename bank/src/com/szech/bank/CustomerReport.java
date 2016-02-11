package com.szech.bank;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerReport {
	private int id;
	private String name;
	private ArrayList<Account> accounts;

	public CustomerReport(){
		this.setAccounts(new ArrayList<>());
	}

	@XmlElement
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	@XmlElement
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}


	
	
	
	
}