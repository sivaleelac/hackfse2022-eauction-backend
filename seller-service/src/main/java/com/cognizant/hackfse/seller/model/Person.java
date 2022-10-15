package com.cognizant.hackfse.seller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String city;
	private String state;
	private String pincode;
	private String classification;
}
