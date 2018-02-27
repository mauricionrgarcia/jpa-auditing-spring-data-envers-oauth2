package com.example.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.model.Person;

@Repository
public class HistoryFinal extends AbstractHistory<Person, Long> {

	public HistoryFinal(EntityManager entityManager) {
		super(entityManager);
	}
}
