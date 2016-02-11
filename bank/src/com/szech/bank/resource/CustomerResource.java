package com.szech.bank.resource;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.szech.bank.Customer;
import com.szech.bank.errorhandling.BadInputException;
import com.szech.bank.errorhandling.NotFoundException;
import com.szech.bank.storage.CustomerStore;


@Path("/customers")
public class CustomerResource {


	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String customer;
	
	
	public CustomerResource(UriInfo uriInfo, Request request, 
			String customer) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.customer = customer;
	}
	
	public CustomerResource(){}
	
	@POST
	@Path("/new")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newCustomer(
			@FormParam("id") String str_id,
			@FormParam("name") String name,
			@Context HttpServletResponse servletResponse
	) throws IOException, NumberFormatException {
		
		int id = new Integer(str_id);
		
		Customer c = new Customer(id, name);
		CustomerStore.getStore().put(id, c);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(str_id).build();
		Response.created(uri).build();
		
		servletResponse.sendRedirect("../pages/new_customer.html");
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Customer> getCustomers() {
	
		List<Customer> customers = new ArrayList<Customer>();
		customers.addAll( CustomerStore.getStore().values() );
		return customers;
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Customer getCustomer(@PathParam("id") String str_id){
		Integer id;
		try {
			id = new Integer(str_id);
		} catch (NumberFormatException e) {
			throw new BadInputException("Please supply an integer to retrieve a customer record.");
		}
		
		Customer cust = CustomerStore.getStore().get(new Integer(id));
		if(cust == null)
			throw new NotFoundException("No such Customer with id " + id);
		return cust;
	}
	
}
