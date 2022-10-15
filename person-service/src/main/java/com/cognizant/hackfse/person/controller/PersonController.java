package com.cognizant.hackfse.person.controller;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.hackfse.person.exception.CustomExceptionHandler;
import com.cognizant.hackfse.person.exception.NotFoundException;
import com.cognizant.hackfse.person.model.AuthenticateRequest;
import com.cognizant.hackfse.person.model.JwtAuthenticationResponse;
import com.cognizant.hackfse.person.model.Person;
import com.cognizant.hackfse.person.model.UserPrincipal;
import com.cognizant.hackfse.person.service.JWTTokenProvider;
import com.cognizant.hackfse.person.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("http://localhost:3000")
public class PersonController {
	private static final Logger log = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Operation(summary = "Create a person")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created a person", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)) }),
			@ApiResponse(responseCode = "400", description = "Person with same email address already exists", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }) })
	@PostMapping("/api/v1/person/register")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		log.info("Create person details........");
		Person createdPerson = personService.createPerson(person);
		log.info("Person details created successfully........");
		return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
	}

	@Operation(summary = "Get person details by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found a person", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Person not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }) })
	@GetMapping("/api/v1/person/{personId}")
	public ResponseEntity<Person> getPersonById(@PathVariable("personId") String personId) {

		Person person = personService.getPersonById(personId);

		if (person == null) {
			throw new NotFoundException("error.client.person.notFound");
		}

		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@Operation(summary = "Get person details by email address")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found a person", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Person not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }) })
	@GetMapping("/api/v1/person")
	public ResponseEntity<Person> getPersonByEmailAddress(@RequestParam(name = "emailAddress") String emailAddress) {
		log.info("--------------> getPersonByEmailAddress - {}", emailAddress);
		Person person = personService.getPersonByEmailAddress(emailAddress);

		if (person == null) {
			throw new NotFoundException("error.client.person.notFound");
		}

		return new ResponseEntity<>(person, HttpStatus.OK);
	}
	
	@Operation(summary = "Get person details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found a person", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Person not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionHandler.ErrorResponse.class)) }) })
	@GetMapping("/api/v1/person/get")
	public ResponseEntity<Person> getPersons(@RequestHeader(value = "Authorization") String authorization) {
		log.info("--------------> getPerson - {}", authorization);
		String emailAddress = getPersonEmailAddressFromToken(authorization);
		log.info("--------------> getPerson - emailAddress - {}", emailAddress);
		Person person = personService.getPersonByEmailAddress(emailAddress);

		if (person == null) {
			throw new NotFoundException("error.client.person.notFound");
		}

		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@PostMapping("/api/v1/person/authenticate")
	public ResponseEntity<JwtAuthenticationResponse> authenticate(
			@Valid @RequestBody AuthenticateRequest authenticateRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticateRequest.getUserName(), authenticateRequest.getPassword()));
		
		Person person = personService.getPersonByEmailAddress(authenticateRequest.getUserName());
		
		String token = jwtTokenProvider.generateToken((UserPrincipal) authentication.getPrincipal());
		log.info("Token Created {}", token);
		return ResponseEntity.ok(new JwtAuthenticationResponse(token, person.getEmailAddress(), person.getClassification()));
	}
	
	private String getPersonEmailAddressFromToken(String bearerToken) {
		String emailAddress = null;
		if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String token = bearerToken.substring(7, bearerToken.length());

			if (StringUtils.isNotBlank(token) && jwtTokenProvider.validateToken(token)) {
				log.info("Token is Valid ");
				emailAddress = jwtTokenProvider.getUserNameFromToken(token);
				log.info("emailAddress -> {}", emailAddress);
			}
		}

		if (StringUtils.isBlank(emailAddress)) {
			throw new RuntimeException("Access Denied");
		}
		return emailAddress;
	}

}
