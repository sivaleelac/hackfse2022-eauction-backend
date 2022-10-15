package com.cognizant.hackfse.seller.exception;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 6102844129812383080L;
	
	public BadRequestException(String message) {
		super(message);
	}
}
