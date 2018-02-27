package com.example.repository.envers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.criteria.internal.IdentifierEqAuditExpression;
import org.hibernate.envers.query.internal.impl.AbstractAuditQuery;

public class EntitiesQuery extends AbstractAuditQuery {

	private EntityInstantiatorImpl customEntityInstantiator;
	List<AuditCriterion> criterionsAudit;

	public EntitiesQuery(EnversService enversService, AuditReaderImplementor versionsReader, Class<?> cls) {
		super(enversService, versionsReader, cls);
		criterionsAudit = new ArrayList<AuditCriterion>();
		customEntityInstantiator = new EntityInstantiatorImpl(enversService, versionsReader);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List list() {
		withProperties("userChange", 1L);
		for (AuditCriterion criterion : criterions) {
			criterion.addToQuery(enversService, versionsReader, entityName, qb, qb.getRootParameters());
		}
		for (AuditCriterion criterion : criterionsAudit) {
			criterion.addToQuery(enversService, versionsReader, entityName, qb, qb.getRootParameters());
		}

		Query query = buildQuery();
		List queryResult = query.list();
		List result = new ArrayList();

		customEntityInstantiator.addInstancesFromVersionsEntities(entityName, result, queryResult);
		return result;
	}

	void withEntityId(Long code) {
		criterionsAudit.add(new IdentifierEqAuditExpression(AuditEntity.id(), Boolean.TRUE));
	}

	void withProperties(String propertyName, Object value) {
		criterionsAudit.add(AuditEntity.property(propertyName).eq(value));
	}

}