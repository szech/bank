package com.szech.bank.errorhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

public class BadInputException extends WebApplicationException{
	  private static final long serialVersionUID = -9079411854450419091L;

	    public static class MyStatus implements StatusType {
	        final int statusCode;
	        final String reasonPhrase;

	        public MyStatus(int statusCode, String reasonPhrase) {
	            this.statusCode = statusCode;
	            this.reasonPhrase = reasonPhrase;
	        }

	        @Override
	        public int getStatusCode() {
	            return statusCode;
	        }
	        @Override
	        public Family getFamily() {
	            return Family.familyOf(statusCode);
	        }
	        @Override
	        public String getReasonPhrase() {
	            return reasonPhrase;
	        }
	    }

	    public BadInputException() {
	    }

	    public BadInputException(int status) {
	        super(status);
	    }

	    public BadInputException(Response response) {
	        super(response);
	    }

	    public BadInputException(Status status) {
	        super(status);
	    }

	    public BadInputException(String message, Response response) {
	        super(message, response);
	    }

	    public BadInputException(int status, String message) {
	        super(message, Response.status(new MyStatus(status, message)). build());
	    }

	    public BadInputException(Status status, String message) {
	        this(status.getStatusCode(), message);
	    }

	    public BadInputException(String message) {
	        this(400, message);
	    }

}
