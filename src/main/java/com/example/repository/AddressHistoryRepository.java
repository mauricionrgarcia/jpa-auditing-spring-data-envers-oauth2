package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.hist.AddressHistory;

/**
 * Repository {@link AddressHistory}
 * 
 * @author <a href="mailto:mauricionrgarcia@gmailcom">Mauricio Garcia</a>
 * @version
 * @sinse 25/02/2018 19:17:12
 */
@Repository
public interface AddressHistoryRepository extends JpaRepository<AddressHistory, Long> {
}
