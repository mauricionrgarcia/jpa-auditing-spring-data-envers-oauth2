package com.example.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.model.Contact;

@Repository
public class ContactHistoryRepository extends AbstractHistory<Contact, Long> {

	public ContactHistoryRepository(EntityManager entityManager) {
		super(entityManager);
	}

}
