package com.szech.bank.resource;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.szech.bank.storage.AccountStore;
import com.szech.bank.storage.CustomerStore;
import com.szech.bank.storage.PaymentStore;


@Path("/begin")
public class BankResource {

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;
	
	@javax.ws.rs.core.Context 
	ServletContext context;
	

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/initialise")
	public String hello() throws ServletException, IOException{  

		// Initialise all the example data
		CustomerStore.getStore();
		AccountStore.getStore();
		PaymentStore.getStore();
		
		RequestDispatcher dispatcher =  context.getRequestDispatcher("/pages/hub.html");
		dispatcher.forward(request, response);
		
		return "";
	}


}