package com.cognizant.hackfse.person.exception;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -2706816991569806145L;

	public BadRequestException(String message) {
		super(message);
	}
}
