package com.cognizant.hackfse.buyer.exception;

public class InternalServerException extends RuntimeException{

	private static final long serialVersionUID = -2706816991569806145L;

	public InternalServerException(String message) {
		super(message);
	}
}
