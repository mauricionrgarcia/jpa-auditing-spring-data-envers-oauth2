package com.example.repository.envers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.internal.impl.AbstractAuditQuery;

public class EntitiesQuery extends AbstractAuditQuery {

	public EntitiesQuery(EnversService enversService, AuditReaderImplementor versionsReader, Class<?> cls) {
		super(enversService, versionsReader, cls);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List list() {
		for (AuditCriterion criterion : criterions) {
			criterion.addToQuery(enversService, versionsReader, entityName, qb, qb.getRootParameters());
		}

		Query query = buildQuery();
		List queryResult = query.list();
		List result = new ArrayList();
		entityInstantiator.addInstancesFromVersionsEntities(entityName, result, queryResult, 0);
		return result;
	}
}