package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.hist.PersonHistory;

/**
 * Repository {@link PersonHistory}
 * 
 * @author <a href="mailto:mauricionrgarcia@gmailcom">Mauricio Garcia</a>
 * @version
 * @sinse 25/02/2018 01:30:13
 */
@Repository
public interface PersonHistoryRepository extends JpaRepository<PersonHistory, Long> {
}
