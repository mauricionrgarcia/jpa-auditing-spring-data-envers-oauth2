package com.example;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.Address;
import com.example.model.GenreEnum;
import com.example.model.Person;
import com.example.repository.PersonRepository;

/**
 * Test to save person
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 13:55:16
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringSecurityTestConfig.class)
@RunWith(SpringRunner.class)
public class SavePersonTest {

	@Autowired
	private transient PersonRepository personRepository;

	/**
	 * {@link List} {@link Person}
	 */
	private List<Person> people;

	// @Before
	@Test
	@WithUserDetails(value = "admin", userDetailsServiceBeanName = "SpringSecurityTestConfig")
	public void createDataValues() {
		Person person1 = new Person();
		person1.setName("Bruna");
		person1.setGenre(GenreEnum.FEMALE);
		Address address1 = new Address();
		address1.setCity("Barueri");
		address1.setStreet("Alameda Rio Negro");
		person1.setAddress(Arrays.asList(address1));

		people = Arrays.asList(person1);
		personRepository.save(people);
	}

	@After
	public void cleanData() {
		personRepository.delete(people);
	}

	/**
	 * Testa consulta por atributo: age
	 */
	@Test
	public void findByFild() {

	}

}
