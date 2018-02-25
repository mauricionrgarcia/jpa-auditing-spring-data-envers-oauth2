package com.example.service;

import java.util.List;

import org.springframework.data.history.Revisions;

import com.example.model.Address;

/**
 * Interface to address Service.
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 25/02/2018 19:15:26
 */
public interface AddressService {

	/**
	 * find a specific Address by:
	 * 
	 * @param code: code identifier
	 * @return Address
	 */
	Address find(Long code);

	/**
	 * save a new Address
	 * 
	 * @param Address Address
	 * @return {@link Address}
	 */
	Address saveAddress(Address Address);

	/**
	 * delete a Address
	 * 
	 * @param code identifier
	 */
	void deleteAddress(Long code);

	/**
	 * @param Address
	 * @return
	 */
	Address udpateAddress(Address Address);

	/**
	 * find the revisions changes from:
	 * 
	 * @param code identifier {@link Address}
	 * @return all {@link Revisions}
	 */
	List<Address> findAllRevision(Long code);

}
