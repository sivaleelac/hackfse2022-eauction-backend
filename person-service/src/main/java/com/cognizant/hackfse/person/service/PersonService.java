package com.cognizant.hackfse.person.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.hackfse.person.entity.PersonEntity;
import com.cognizant.hackfse.person.exception.BadRequestException;
import com.cognizant.hackfse.person.exception.InternalServerException;
import com.cognizant.hackfse.person.model.Person;
import com.cognizant.hackfse.person.repository.PersonRepository;

@Service
public class PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private PersonRepository personRepository;

	public Person createPerson(Person person) {
		validatePerson(person);
		try {
			ModelMapper mapper = new ModelMapper();
			person.setPassword(new BCryptPasswordEncoder().encode(person.getPassword()));
			PersonEntity personEntity = mapper.map(person, PersonEntity.class);
			PersonEntity createdPersonEntity = personRepository.save(personEntity);
			Person createdPerson = mapper.map(createdPersonEntity, Person.class);
			createdPerson.setPassword("******");
			return createdPerson;
		} catch (Exception exp) {
			log.error("Exception occurred while creating person details", exp);
			throw new InternalServerException("Unexpected error occurred, please try again");
		}
	}

	private void validatePerson(Person person) {
		Optional<PersonEntity> personEntity = personRepository.findByEmailAddress(person.getEmailAddress());
		if (personEntity.isPresent()) {
			throw new BadRequestException("error.client.person.already.exists");
		}
	}

	public Person getPersonById(String personId) {
		Person person = null;
		try {
			Optional<PersonEntity> personEntity = personRepository.findById(personId);
			if (personEntity.isPresent()) {
				ModelMapper mapper = new ModelMapper();
				person = mapper.map(personEntity.get(), Person.class);
			}
		} catch (Exception exp) {
			log.error("Exception occurred while getting person details", exp);
			throw new InternalServerException("Unexpected error occurred, please try again");
		}
		return person;
	}
	
	public Person getPersonByEmailAddress(String emailAddress) {
		Person person = null;
		try {
			Optional<PersonEntity> personEntity = personRepository.findByEmailAddress(emailAddress);
			if (personEntity.isPresent()) {
				ModelMapper mapper = new ModelMapper();
				person = mapper.map(personEntity.get(), Person.class);
				person.setPassword("******");
			}
		} catch (Exception exp) {
			log.error("Exception occurred while getting person details", exp);
			throw new InternalServerException("Unexpected error occurred, please try again");
		}
		return person;
	}
}
