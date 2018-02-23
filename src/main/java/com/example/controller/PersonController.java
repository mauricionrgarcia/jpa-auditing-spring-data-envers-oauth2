package com.example.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.model.Person;
import com.example.service.PersonService;

/**
 * implementation of inteface {@link PersonService}.
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 18/02/2018 19:30:11
 */
@RestController
@RequestMapping("/api/person")
public class PersonController {

	/**
	 * Person Serivce
	 */
	@Autowired
	private transient PersonService personService;

	/**
	 * find a specific Person by:
	 * 
	 * @param code: code identifier
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Person> find(@PathVariable("id") Long code) {
		Person person = personService.find(code);
		return ResponseEntity.ok(person);
	}

	/**
	 * save a new {@link Person}
	 * 
	 * @param person {@link Person}
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@PostMapping()
	public ResponseEntity<Person> save(@RequestBody() @Valid Person person) {
		Person entity = personService.savePerson(person);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{publication}")
				.buildAndExpand(entity.getCode()).toUri();
		return ResponseEntity.created(location).body(entity);
	}

	/**
	 * save a new {@link Person}
	 * 
	 * @param person {@link Person}
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@PutMapping()
	public ResponseEntity<Person> update(@RequestBody() @Valid Person person) {
		Person entity = personService.udpatePerson(person);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{publication}")
				.buildAndExpand(entity.getCode()).toUri();
		return ResponseEntity.created(location).body(entity);
	}
}
