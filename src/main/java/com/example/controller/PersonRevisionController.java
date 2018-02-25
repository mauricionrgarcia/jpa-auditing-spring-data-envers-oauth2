package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Address;
import com.example.model.Person;
import com.example.service.AddressService;
import com.example.service.PersonService;

/**
 * controller to find history {@link Person}
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 23/02/2018 00:11:56
 */
@RestController
@RequestMapping("/api/person/")
public class PersonRevisionController {

	/**
	 * Person Serivce
	 */
	@Autowired
	private transient PersonService personService;

	/**
	 * Person Serivce
	 */
	@Autowired
	private transient AddressService addresService;

	/**
	 * find all Reviison from specific Person by:
	 *
	 * @param code: code identifier
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@GetMapping("/{id}/historical")
	public ResponseEntity<List<Person>> find(@PathVariable("id") Long code) {
		List<Person> revisions = personService.findAllRevision(code);
		return ResponseEntity.ok(revisions);
	}

	/**
	 * find all Reviison from specific Person by:
	 *
	 * @param code: code identifier
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@GetMapping("/{id}/address/historical")
	public ResponseEntity<List<Address>> findAddress(@PathVariable("id") Long code) {
		List<Address> revisions = addresService.findAllRevision(code);
		return ResponseEntity.ok(revisions);
	}

}
