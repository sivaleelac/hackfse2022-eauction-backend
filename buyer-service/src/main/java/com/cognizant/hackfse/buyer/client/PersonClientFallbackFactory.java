package com.cognizant.hackfse.buyer.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.cognizant.hackfse.buyer.model.Person;

@Component
public class PersonClientFallbackFactory implements FallbackFactory<PersonClient> {

	@Override
	public PersonClient create(Throwable cause) {
		return new PersonClient() {
			@Override
			public Person getPersonByEmailAddress(String emailAddress) {
				throw new RuntimeException("Internal server error");
			}
		};
	}

}
