package com.szech.bank.test;

import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AccountsTest{
	
	@Test 
	public void testAccountFetchesSuccess() {
		expect().
			statusCode(200).
			body(
				"id", equalTo(1),
				"balance", hasToString("30000.0"),
				"customerID", equalTo(1)).
			when().
			get("/bank/api/accounts/1");
	}
 
	@Test
	public void testAccountNotFound() {
		expect().statusCode(404).when().get("/bank/api/accounts/99");
	}
	
	
	@Test
	public void testNonIntegerInput() {
		expect().statusCode(400).when().get("/bank/api/accounts/9.9");
		expect().statusCode(400).when().get("/bank/api/accounts/e");
	}
	
}
