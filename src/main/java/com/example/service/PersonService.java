package com.example.service;

import java.util.List;

import org.springframework.data.history.Revisions;

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

	/**
	 * @param person
	 * @return
	 */
	Person udpatePerson(Person person);

	/**
	 * find the revisions changes from:
	 * 
	 * @param code identifier {@link Person}
	 * @return all {@link Revisions}
	 */
	List<Person> findAllRevision(Long code);

}
