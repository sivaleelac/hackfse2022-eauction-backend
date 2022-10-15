package com.cognizant.hackfse.person.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.hackfse.person.entity.PersonEntity;

@Repository
public interface PersonRepository extends MongoRepository<PersonEntity, String> {
	Optional<PersonEntity> findByEmailAddress(String emailAddress);
}
