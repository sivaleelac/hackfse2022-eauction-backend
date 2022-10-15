package com.cognizant.hackfse.seller.exception;

public class AccessDeniedException extends RuntimeException{
	
	private static final long serialVersionUID = 4236852961019024006L;

	public AccessDeniedException(String message) {
		super(message);
	}

}
