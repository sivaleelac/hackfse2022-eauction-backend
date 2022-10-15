package com.cognizant.hackfse.person.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Person {
	private String id;

	@NotBlank(message = "error.client.firstName.blank")
	@Pattern(regexp = "^.{5,30}", message = "error.client.firstName.invalid")
	private String firstName;

	@NotBlank(message = "error.client.lastName.blank")
	@Pattern(regexp = "^.{3,25}", message = "error.client.lastName.invalid")
	private String lastName;

	@NotNull(message = "error.client.phoneNumber.blank")
	@Pattern(regexp = "^[0-9]{10}", message = "error.client.phoneNumber.invalid")
	private String phoneNumber;

	@NotBlank(message = "error.client.emailAddress.blank")
	@Email(message = "error.client.emailAddress.invalid")
	private String emailAddress;

	@NotBlank(message = "error.client.password.blank")
	private String password;

	private String city;
	private String state;
	private String pincode;

	private String classification;
}
