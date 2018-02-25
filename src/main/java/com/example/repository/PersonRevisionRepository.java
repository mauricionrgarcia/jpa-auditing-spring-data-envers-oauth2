package com.example.repository;

import org.springframework.stereotype.Repository;

import com.example.model.Person;

/***
 * Repository{@link Person}<br>
 * 
 * No default impl to list and filter by date history and user change.<br>
 * Redefined the approach for conducting the consultation through the historical
 * entity
 *
 * @author <a href="mailto:mauricionrgarcia@gmailcom">Mauricio Garcia</a>
 * @version
 * @sinse 18/02/2018 19:25:47
 */
@Deprecated
@Repository
public interface PersonRevisionRepository {// extends RevisionRepository<Person, Long, Integer> {
}
