package com.cognizant.hackfse.seller.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cognizant.hackfse.seller.model.Person;

@FeignClient(name = "person-service", fallbackFactory = PersonClientFallbackFactory.class)
public interface PersonClient {

	@GetMapping(value = "/api/v1/person")
	public Person getPersonByEmailAddress(@RequestParam(name = "emailAddress") String emailAddress);
}
