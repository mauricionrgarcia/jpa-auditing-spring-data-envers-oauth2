package com.example.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import com.example.repository.query.HistoryQueryFactor;

public abstract class AbstractHistory<T, ID extends Serializable> {

	private final EntityManager entityManager;

	private Class<T> persistentClass;

	private HistoryQueryFactor factor;

	/**
	 * @param em
	 */
	@SuppressWarnings("unchecked")
	public AbstractHistory(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * @param id
	 * @param revision
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findById(ID id) {
		factor = HistoryQueryFactor.get(persistentClass.getName(), entityManager);
		return (List<T>) factor.getResult(id);
	}

	/**
	 * @param id
	 * @param revision
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findById(ID id, Long user) {
		factor = HistoryQueryFactor.get(persistentClass.getName(), entityManager);
		return (List<T>) factor.getResult(id, user);
	}

}
