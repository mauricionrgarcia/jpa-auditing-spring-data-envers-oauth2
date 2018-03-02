package com.example.repository.query;

import static org.hibernate.envers.internal.entities.mapper.relation.query.QueryConstants.REFERENCED_ENTITY_ALIAS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.entities.EntityInstantiator;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.internal.tools.query.QueryBuilder;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HistoryQueryBuilder {

	private QueryBuilder qb;
	private List<AuditCriterion> criterionsAudit;
	private EntityInstantiator initiator;

	private String entityName;
	private AuditReaderImplementor versionsReader = null;
	private EnversService enversService;

	public HistoryQueryBuilder(String entityName, EnversService enversService, AuditReaderImplementor auditReader) {
		this.criterionsAudit = new ArrayList<AuditCriterion>();
		this.versionsReader = auditReader;
		this.initiator = new EntityInstantiator(enversService, versionsReader);
		String versionsEntityName = enversService.getAuditEntitiesConfiguration().getAuditEntityName(entityName);
		this.qb = new QueryBuilder(versionsEntityName, REFERENCED_ENTITY_ALIAS);
		this.entityName = entityName;
		this.enversService = enversService;
	}

	public HistoryQueryBuilder withEntityId(Object code) {
		criterionsAudit.add(AuditEntity.id().eq(code));
		return this;
	}

	public HistoryQueryBuilder withProperties(String propertyName, Object value) {
		criterionsAudit.add(AuditEntity.property(propertyName).eq(value));
		return this;
	}

	public void applyFilter() {
		for (AuditCriterion criterion : criterionsAudit) {
			criterion.addToQuery(enversService, versionsReader, entityName, qb, qb.getRootParameters());
		}
	}

	protected Query buildQuery(Session session) {
		Query query = qb.toQuery(session);
		return query;
	}

	public List<?> buildAndExecuteQuery(Session session) {
		applyFilter();
		Query query = buildQuery(session);
		List result = query.list();
		return convertTOInstancesFromVersionsEntities(entityName, result);
	}

	private List convertTOInstancesFromVersionsEntities(String entityName, List<Map> versionsEntities) {
		List entities = new ArrayList<Object>();
		for (Map versionsEntity : versionsEntities) {
			final Map originalId = (Map) versionsEntity
					.get(enversService.getAuditEntitiesConfiguration().getOriginalIdPropName());
			Number rev = ((org.hibernate.envers.DefaultRevisionEntity) originalId.get("REV")).getId();
			entities.add(initiator.createInstanceFromVersionsEntity(entityName, versionsEntity, rev));
		}
		return entities;
	}

}
