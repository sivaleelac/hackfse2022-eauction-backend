package com.cognizant.hackfse.person.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "person")
@Data
public class PersonEntity {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String password;
	private String city;
	private String state;
	private String pincode;
	private String classification;
}
