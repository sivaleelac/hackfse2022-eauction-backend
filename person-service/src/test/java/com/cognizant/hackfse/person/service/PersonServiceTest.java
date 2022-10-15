package com.cognizant.hackfse.person.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.hackfse.person.entity.PersonEntity;
import com.cognizant.hackfse.person.exception.BadRequestException;
import com.cognizant.hackfse.person.exception.InternalServerException;
import com.cognizant.hackfse.person.model.Person;
import com.cognizant.hackfse.person.repository.PersonRepository;

@SpringBootTest
public class PersonServiceTest {

	@InjectMocks
	private PersonService personService;

	@Mock
	private PersonRepository personRepository;

	/*@Test
	public void testToCreateAPersonSuccessfully() {
		when(personRepository.findByEmailAddress(anyString())).thenReturn(Optional.empty());
		when(personRepository.save(any(PersonEntity.class))).thenReturn(buildPersonEntity());
		Person personResponse = personService.createPerson(buildPerson());
		assertEquals("abc", personResponse.getId());
	}

	@Test
	public void testToThrowExceptionWhenEmailAddressAlreadyExistsWhileCreatingAPerson() {
		when(personRepository.findByEmailAddress(anyString())).thenReturn(Optional.of(buildPersonEntity()));
		try {
			personService.createPerson(buildPerson());
		} catch (BadRequestException exp) {
			assertEquals("error.client.person.already.exists", exp.getMessage());
		}
	}
	
	@Test
	public void testToThrowExceptionWhenSavingDetailsToDBWhileCreatingAPerson() {
		when(personRepository.findByEmailAddress(anyString())).thenReturn(Optional.empty());
		try {
			when(personRepository.save(any(PersonEntity.class))).thenThrow(new InternalServerException("error"));
			personService.createPerson(buildPerson());
		} catch (InternalServerException exp) {
			assertEquals("Unexpected error occurred, please try again", exp.getMessage());
		}
	}

	@Test
	public void testToGetPersonByIdSuccessfully() {
		when(personRepository.findById(anyString())).thenReturn(Optional.of(buildPersonEntity()));	
		Person person = personService.getPersonById("abc");	
		assertEquals("Sivaleela", person.getFirstName());
	}
	
	@Test
	public void testToGetPersonByIdWhenDataNotFoundInDB() {
		when(personRepository.findById(anyString())).thenReturn(Optional.empty());	
		Person person = personService.getPersonById("abc");	
		assertNull(person);
	}
	
	@Test
	public void testToThrowExceptionWhenGettingPersonDetails() {
		try {
			when(personRepository.findById(anyString())).thenThrow(new InternalServerException("error"));
			personService.getPersonById("abc");	
		} catch (InternalServerException exp) {
			assertEquals("Unexpected error occurred, please try again", exp.getMessage());
		}
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

	private Person buildPerson() {
		Person person = new Person();
		person.setFirstName("Sivaleela");
		person.setLastName("Chamarthi");
		person.setEmailAddress("sivaleela@gmail.com");
		person.setPhoneNumber("01234567890");
		return person;
	}*/

}
