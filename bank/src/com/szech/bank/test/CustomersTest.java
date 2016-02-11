package com.szech.bank.test;

import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CustomersTest{
	
	@Test 
	public void testCustomerFetchesSuccess() {
		expect().
			statusCode(200).
			body(
				"id", equalTo(1),
				"name", equalTo("Scrooge McDuck")).
			when().
			get("/bank/api/customers/1");
	}
 
	@Test
	public void testCustomerNotFound() {
		expect().statusCode(404).when().get("/bank/api/customers/99");
	}
	
	
	@Test
	public void testNonIntegerInput() {
		expect().statusCode(400).when().get("/bank/api/customers/9.9");
		expect().statusCode(400).when().get("/bank/api/customers/e");
	}
	
}
