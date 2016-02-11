package com.szech.bank.test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.jayway.restassured.response.Response;

public class PaymentsTest{



	@Test
	public void makeSuccessfulNewPayment() {

		float amount = new Float(40.0);
		final int fromAcc = 1;
		final int toAcc = 2;
		
		
		float fromAccBalance = get("/bank/api/accounts/1").then().extract().path("balance");
		float toAccBalance = get("/bank/api/accounts/2").then().extract().path("balance");

		
		// do transfer
		Response r = given().
			formParam("amount", amount).
			formParam("from_acc", fromAcc).
			formParam("to_acc", toAcc).
		expect().
			statusCode(201).
		when().
			post("/bank/api/payments/new").
		then().extract().response();
		
		// check account balances were updated
		assertEquals(fromAccBalance - amount, get("/bank/api/accounts/1").then().extract().path("balance"), 0);
		assertEquals(toAccBalance + amount, get("/bank/api/accounts/2").then().extract().path("balance"), 0);
		
				
		// retrieve the payment with some regex on the response
		Pattern p = Pattern.compile("(?<=#)(\\d*)");
		Matcher m = p.matcher(r.getBody().asString());
 		m.find();
		
		expect().
		statusCode(200).
		body(
			"id", hasToString(m.group(1)),
			"amount", hasToString("40.0"),
			"fromAccountID", hasToString("1"),
			"toAccountID", hasToString("2")).
		when().
		get("/bank/api/payments/" + m.group(1));

	}

	@Test
	public void testAmountValidation(){
		// amount = 0
		given().formParam("amount", 0).formParam("from_acc", 1).formParam("to_acc", 2).
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
		// amount = -1
		given().formParam("amount", 0).formParam("from_acc", 1).formParam("to_acc", 2).
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
		// amount = 1.001
		given().formParam("amount", 1.001).formParam("from_acc", 1).formParam("to_acc", 2).
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
	}
	
	
	@Test
	public void testfromAccountValidation(){
		// not supplied
		given().formParam("amount", 10).formParam("from_acc", "").formParam("to_acc", 2).
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
		// invalid id
		given().formParam("amount", 10).formParam("from_acc", 99).formParam("to_acc", 2).
		expect().statusCode(404).
		when().post("/bank/api/payments/new");
		
		// not an integer
		given().formParam("amount", 10).formParam("from_acc", "X").formParam("to_acc", 2).
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
		
		// insufficient funds
		given().formParam("amount", 100000000).formParam("from_acc", 1).formParam("to_acc", 2).
		expect().statusCode(500).
		when().post("/bank/api/payments/new");
		
	}
		
	@Test
	public void testToAccountValidation(){
		// not supplied
		given().formParam("amount", 10).formParam("from_acc", 1).formParam("to_acc", "").
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
		// invalid id
		given().formParam("amount", 10).formParam("from_acc", 1).formParam("to_acc", 99).
		expect().statusCode(404).
		when().post("/bank/api/payments/new");
		
		// not an integer
		given().formParam("amount", 10).formParam("from_acc", 2).formParam("to_acc", "C").
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
		// identical destination account
		given().formParam("amount", 10).formParam("from_acc", 1).formParam("to_acc", 1).
		expect().statusCode(400).
		when().post("/bank/api/payments/new");
		
	}

}
