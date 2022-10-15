package com.cognizant.hackfse.person.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.hackfse.person.exception.NotFoundException;
import com.cognizant.hackfse.person.model.Person;
import com.cognizant.hackfse.person.service.PersonService;

@SpringBootTest
public class PersonControllerTest {

	@InjectMocks
	private PersonController controller;

	@Mock
	private PersonService personService;

	@Test
	public void testToCreateAPerson() {
		Person request = buildPerson(null);
		when(personService.createPerson(request)).thenReturn(buildPerson("abcd"));
		ResponseEntity<Person> response = controller.createPerson(request);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("abcd", response.getBody().getId());
	}
	
	@Test
	public void testToGetAPersonById() {
		when(personService.getPersonById(anyString())).thenReturn(buildPerson("abcd"));
		ResponseEntity<Person> response = controller.getPersonById("abcd");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Sivaleela", response.getBody().getFirstName());
	}

	@Test
	public void testToThrowNotFOundExceptionWhenPersonNotFound() {
		when(personService.getPersonById(anyString())).thenReturn(null);
		try {
			controller.getPersonById("abcd");
		} catch (NotFoundException exp) {
			assertEquals("error.client.person.notFound", exp.getMessage());
		}
	}

	private Person buildPerson(String id) {
		Person person = new Person();
		person.setId(id);
		person.setFirstName("Sivaleela");
		person.setLastName("Chamarthi");
		person.setEmailAddress("sivaleela@gmail.com");
		person.setPhoneNumber("01234567890");
		return person;
	}
}
