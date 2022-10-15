package com.cognizant.hackfse.seller.exception;

public class InternalServerException extends RuntimeException{
	
	private static final long serialVersionUID = -6242621871786132949L;

	public InternalServerException(String message) {
		super(message);
	}
}
