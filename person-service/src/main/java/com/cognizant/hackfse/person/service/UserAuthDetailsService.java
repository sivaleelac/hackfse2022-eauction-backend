package com.cognizant.hackfse.person.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cognizant.hackfse.person.entity.PersonEntity;
import com.cognizant.hackfse.person.model.UserPrincipal;
import com.cognizant.hackfse.person.repository.PersonRepository;

@Component
public class UserAuthDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserAuthDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserPrincipal loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonEntity personEntity = personRepository
                .findByEmailAddress(s)
                .orElseThrow(() -> new UsernameNotFoundException("User name " + s + "Not Found in DB"));
        return UserPrincipal.create(personEntity);

    }
}
