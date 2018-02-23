package com.example.service;

import com.example.model.Person;

/**
 * Interface to person service.
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 18/02/2018 19:27:02
 */
public interface PersonService {

	/**
	 * find a specific Person by:
	 * 
	 * @param code: code identifier
	 * @return Person
	 */
	Person find(Long code);

	/**
	 * save a new Person
	 * 
	 * @param person person
	 * @return {@link Person}
	 */
	Person savePerson(Person person);

	/**
	 * delete a Person
	 * 
	 * @param code identifier
	 */
	void deletePerson(Long code);

	Person udpatePerson(Person person);

}
