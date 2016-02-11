package com.szech.bank.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

public class WebApplicationException extends javax.ws.rs.WebApplicationException{
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

	    public WebApplicationException() {
	    }

	    public WebApplicationException(int status) {
	        super(status);
	    }

	    public WebApplicationException(Response response) {
	        super(response);
	    }

	    public WebApplicationException(Status status) {
	        super(status);
	    }

	    public WebApplicationException(String message, Response response) {
	        super(message, response);
	    }

	    public WebApplicationException(int status, String message) {
	        super(message, Response.status(new MyStatus(status, message)). build());
	    }

	    public WebApplicationException(Status status, String message) {
	        this(status.getStatusCode(), message);
	    }

	    public WebApplicationException(String message) {
	        this(500, message);
	    }

}
