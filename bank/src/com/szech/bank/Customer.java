package com.szech.bank;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	private int id;
	private String name;


	public Customer(){
	}

	public Customer(int id, String name){
		this.id = id;
		this.name = name;
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

	// inner class to return all Customers
	@XmlRootElement(name = "Customers")
	public static class Customers {
		private List<Customer> customerList;
		
		public Customers(List<Customer> list) {
			this.customerList = list;
		}


		public List<Customer> getCustomerList() {
			return customerList;
		}

		public void setCustomerList(List<Customer> customerList) {
			this.customerList = customerList;
		}
	}
	
}