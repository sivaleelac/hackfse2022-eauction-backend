package com.cognizant.hackfse.person.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.hackfse.person.entity.PersonEntity;
import com.cognizant.hackfse.person.repository.PersonRepository;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonIT {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private PersonRepository repository;

	@InjectMocks
	private PersonController controller;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		repository.save(buildPersonEntity());
	}

	@Test
	public void test_getById_successfull() throws Exception {
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/person/abc")).andReturn().getResponse();
	}

	private PersonEntity buildPersonEntity() {
		PersonEntity personEntity = new PersonEntity();
		personEntity.setId("abc");
		personEntity.setFirstName("Sivaleela");
		personEntity.setLastName("Chamarthi");
		personEntity.setEmailAddress("sivaleela@gmail.com");
		personEntity.setPhoneNumber("01234567890");
		return personEntity;
	}

}
