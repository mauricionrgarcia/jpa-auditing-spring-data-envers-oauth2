package com.example.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import com.example.service.PersonService;

/**
 * implementation of inteface {@link PersonService}.
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 18/02/2018 19:30:11
 */
@Service
public class PersonServiceImpl implements PersonService {

	/**
	 * Person Repository
	 */
	@Autowired
	private transient PersonRepository personRepository;

	public Person find(Long code) {
		Optional<Person> optional = personRepository.findByCode(code);
		return optional.orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	@Override
	public Person savePerson(Person person) {
		Person personEntity = personRepository.save(person);
		return personEntity;
	}

	@Override
	public void deletePerson(Long code) {
		Person person = this.find(code);
		personRepository.delete(person);
	}

	@Override
	public Person udpatePerson(Person person) {
		Person personDefault = find(person.getCode());
		BeanUtils.copyProperties(person, personDefault, "code");
		return this.personRepository.save(personDefault);
	}
}
