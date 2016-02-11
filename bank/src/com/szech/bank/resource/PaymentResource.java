package com.szech.bank.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.szech.bank.Account;
import com.szech.bank.Customer;
import com.szech.bank.Payment;
import com.szech.bank.errorhandling.BadInputException;
import com.szech.bank.errorhandling.NotFoundException;
import com.szech.bank.errorhandling.WebApplicationException;
import com.szech.bank.storage.AccountStore;
import com.szech.bank.storage.CustomerStore;
import com.szech.bank.storage.PaymentStore;

@Path("/payments")
public class PaymentResource {

	private final String msg = new String("Failed to create payment. ");
	private double amount;
	private int fromAccountID;
	private int toAccountID;

	/* Create a new payment from HTML form <make_payment.html>
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/new")
	public Response create(
			@FormParam("amount") String str_amount,
			@FormParam("from_acc") String str_fromAccountID,
			@FormParam("to_acc") String str_toAccountID) 
					throws BadInputException, NumberFormatException, WebApplicationException  {

		validateRawInput(str_amount, str_fromAccountID, str_toAccountID);

		Account fromAccount = AccountStore.getStore().get(fromAccountID);
		Account toAccount = AccountStore.getStore().get(toAccountID);

		validatePaymentInput(fromAccount, toAccount, fromAccountID, toAccountID, amount);

		// input ok. now create the payment in the database
		Payment p = new Payment();
		p.setAmount(amount);
		p.setFromAccountID(fromAccountID);
		p.setToAccountID(toAccountID);
		Integer id = PaymentStore.getStore().size() + 1; 
		p.setId(id); // increment id
		p.setTimeOfPayment(new Date(System.currentTimeMillis()));

		PaymentStore.getStore().put(id, p);

		// update the account balances
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance() + amount);

		AccountStore.getStore().put(fromAccountID, fromAccount);
		AccountStore.getStore().put(toAccountID, toAccount);

		return Response
				.status(Response.Status.CREATED)// 201
				.entity("Success! Payment #" + id + " created for " + amount + 
						" from account " + fromAccountID + 
						" to account " + toAccountID)
				.header("View Payment details",
						"http://localhost:8080/bank/api/payments/"
								+ String.valueOf(p.getId())).build();
	}

	/* make sure we got valid input **/
	private void validateRawInput(String str_amount, String str_fromAccountID, String str_toAccountID) throws BadInputException, NumberFormatException, WebApplicationException {

		try {
			if(str_amount.isEmpty()) throw new BadInputException(msg + "Amount to be transferred was not supplied.");
			if(str_fromAccountID.isEmpty()) throw new BadInputException(msg + "Source account was not supplied.");
			if(str_toAccountID.isEmpty()) throw new BadInputException(msg + "Destination account was not supplied.");

			this.amount = new Double(str_amount);
			this.fromAccountID = new Integer(str_fromAccountID);
			this.toAccountID = new Integer(str_toAccountID);

			if(amount <= 0) throw new BadInputException(msg + "Payment amount supplied must be greater than zero.");
			
			String[] splitter = str_amount.toString().split("\\.");
			if(splitter.length > 1 && splitter[1].length() >= 3) throw new BadInputException(msg + "Payment amount must not contain more than 2 decimal places.");

		} catch (NumberFormatException e) {
			throw new BadInputException(e.getMessage());
		}

	}

	/* Ensure we have sane account information before we create the payment
	 * 	@return on success 
	 * 	@throws exception on fail */
	private void validatePaymentInput(Account fromAccount, Account toAccount, int fromAccountID, int toAccountID, double amount) throws BadInputException, NumberFormatException, WebApplicationException  {


		// validate accounts
		if(fromAccount == null) throw new NotFoundException(msg.concat("No such source account: " + fromAccountID));
		if(toAccount == null) throw new NotFoundException(msg.concat("No such destination account: " + toAccountID));
		if(fromAccountID == toAccountID) throw new BadInputException(msg.concat("Payments must be betwen different accounts"));

		// validate amounts/balances
		if(fromAccount.getBalance() <=0 || fromAccount.getBalance() - amount < 0) throw new WebApplicationException(msg.concat("Account " + fromAccountID + " does not have sufficient funds to transfer"));

		return;
	}


	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Payment findById(@PathParam("id") String str_id) 
			throws BadInputException,  NotFoundException{

		Integer id;
		try {
			id = new Integer(str_id);
		} catch (NumberFormatException e) {
			throw new BadInputException("Please supply an integer to retrieve a payment.");
		}


		Payment p = PaymentStore.getStore().get(new Integer(id));
		if(p == null)
			throw new NotFoundException("No such Payment with id " + id);
		return p; 
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Payment> getPayments() {
	
		List<Payment> payments = new ArrayList<Payment>();
		payments.addAll( PaymentStore.getStore().values() );
		return payments;
	}


}
