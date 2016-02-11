package com.szech.bank.errorhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

public class NotFoundException extends WebApplicationException{
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

	    public NotFoundException() {
	    }

	    public NotFoundException(int status) {
	        super(status);
	    }

	    public NotFoundException(Response response) {
	        super(response);
	    }

	    public NotFoundException(Status status) {
	        super(status);
	    }

	    public NotFoundException(String message, Response response) {
	        super(message, response);
	    }

	    public NotFoundException(int status, String message) {
	        super(message, Response.status(new MyStatus(status, message)). build());
	    }

	    public NotFoundException(Status status, String message) {
	        this(status.getStatusCode(), message);
	    }

	    public NotFoundException(String message) {
	        this(404, message);
	    }

}
