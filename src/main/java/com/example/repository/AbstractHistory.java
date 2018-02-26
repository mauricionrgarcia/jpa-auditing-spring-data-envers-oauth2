package com.example.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import com.example.repository.envers.CustomAuditReaderFactory;
import com.example.repository.envers.CustomAuditReaderImpl;

public abstract class AbstractHistory<T, ID extends Serializable> {

	private final EntityManager entityManager;

	private Class<T> persistentClass;

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
	public List<T> findByIdAndRevision(ID id, Number revision) {
		CustomAuditReaderImpl reader = getAuditReader();
		return reader.findEntity(persistentClass, id);
	}

	/**
	 * @return
	 */
	private CustomAuditReaderImpl getAuditReader() {
		CustomAuditReaderImpl reader = CustomAuditReaderFactory.get(entityManager);
		return reader;
	}

}
