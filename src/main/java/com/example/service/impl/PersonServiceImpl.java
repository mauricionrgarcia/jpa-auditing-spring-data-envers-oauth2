package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.model.Person;
import com.example.model.hist.PersonHistory;
import com.example.repository.PersonHistoryRepository;
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

	@Autowired
	private transient PersonHistoryRepository personHistoryRepository;

	public Person find(Long code) {
		Optional<Person> optional = personRepository.findByCode(code);
		return optional.orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	@Override
	public List<Person> findAllRevision(Long code) {
		// performance problem identified in standard implementation

		// Revisions<Integer, Person> revisions =
		// personRevisionRepository.findRevisions(code);
		// List<Person> people = revisions.reverse().getContent().stream().map(e ->
		// e.getEntity()) .collect(Collectors.toList());

		Sort sort = new Sort(Direction.DESC, "dtLastChange");
		PersonHistory filter = new PersonHistory(code);
		Example<PersonHistory> example = Example.of(filter);

		List<PersonHistory> history = personHistoryRepository.findAll(example, sort);
		return history.stream().map(hist -> {
			Person person = new Person();
			BeanUtils.copyProperties(hist, person);
			return person;
		}).collect(Collectors.toList());
	}

	@Override
	public Person savePerson(Person person) {

		person.getAddress().forEach(address -> {
			address.setPerson(person);
		});
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
