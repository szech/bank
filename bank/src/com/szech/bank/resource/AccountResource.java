package com.szech.bank.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.szech.bank.Account;
import com.szech.bank.errorhandling.BadInputException;
import com.szech.bank.errorhandling.NotFoundException;
import com.szech.bank.storage.AccountStore;

@Path("/accounts")
public class AccountResource {

//	AccountDAO dao = new AccountDAO();
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String account;
	
	
	public AccountResource(UriInfo uriInfo, Request request, 
			String account) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.account = account;
	}
	
	public AccountResource(){}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Account> getAccounts() {
		List<Account> customers = new ArrayList<Account>();
		customers.addAll( AccountStore.getStore().values() );
		return customers;
	}
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Account getAccount(@PathParam("id") String str_id){
		Integer id;
		try {
			id = new Integer(str_id);
		} catch (NumberFormatException e) {
			throw new BadInputException("Please supply an integer to retrieve an account.");

		}
		
		Account acc = AccountStore.getStore().get(new Integer(id));
		if(acc == null)
			throw new NotFoundException("No such Account with id " + id);
		return acc;
	}
	
	
	
}
