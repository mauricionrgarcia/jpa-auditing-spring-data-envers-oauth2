package com.example.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.model.Address;
import com.example.service.AddressService;

/**
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 25/02/2018 19:15:06
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

	/**
	 * Address Serivce
	 */
	@Autowired
	private transient AddressService addressService;

	/**
	 * find a specific Address by:
	 * 
	 * @param code: code identifier
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Address> find(@PathVariable("id") Long code) {
		Address Address = addressService.find(code);
		return ResponseEntity.ok(Address);
	}

	/**
	 * save a new {@link Address}
	 * 
	 * @param Address {@link Address}
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@PostMapping()
	public ResponseEntity<Address> save(@RequestBody() @Valid Address Address) {
		Address entity = addressService.saveAddress(Address);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{publication}")
				.buildAndExpand(entity.getCode()).toUri();
		return ResponseEntity.created(location).body(entity);
	}

	/**
	 * save a new {@link Address}
	 * 
	 * @param Address {@link Address}
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@PutMapping()
	public ResponseEntity<Address> update(@RequestBody() @Valid Address Address) {
		Address entity = addressService.udpateAddress(Address);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{publication}")
				.buildAndExpand(entity.getCode()).toUri();
		return ResponseEntity.created(location).body(entity);
	}

	/**
	 * delete a Address by:
	 * 
	 * @param code: code identifier
	 * @return {@link ResponseEntity}
	 * @throws BusinessException
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Address> delete(@PathVariable("id") Long code) {
		addressService.deleteAddress(code);
		return ResponseEntity.noContent().build();
	}
}
