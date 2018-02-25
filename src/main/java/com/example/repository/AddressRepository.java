package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Address;

/**
 * Repository {@link Address}
 * 
 * @author <a href="mailto:mauricionrgarcia@gmailcom">Mauricio Garcia</a>
 * @version
 * @sinse 25/02/2018 19:18:04
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	/**
	 * find a specific Address by:
	 * 
	 * @param code: code identifier
	 * @return {@link Optional} {@link Address}
	 */
	Optional<Address> findByCode(Long code);
}
