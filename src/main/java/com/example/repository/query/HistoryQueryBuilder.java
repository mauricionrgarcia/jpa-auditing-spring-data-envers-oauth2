package com.example.repository.query;

import static org.hibernate.envers.internal.entities.mapper.relation.query.QueryConstants.REFERENCED_ENTITY_ALIAS;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.internal.tools.query.QueryBuilder;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;

public class HistoryQueryBuilder {

	QueryBuilder qb;
	List<AuditCriterion> criterionsAudit;

	private String entityName;
	private AuditReaderImplementor versionsReader = null;
	private EnversService enversService;

	public HistoryQueryBuilder(String entityName, EnversService enversService, AuditReaderImplementor auditReader) {
		criterionsAudit = new ArrayList<AuditCriterion>();
		this.versionsReader = auditReader;
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

	// Query properties

	private Integer maxResults;
	private Integer firstResult;
	private Boolean cacheable;
	private String cacheRegion;
	private String comment;
	private FlushMode flushMode;
	private CacheMode cacheMode;
	private Integer timeout;
	private LockOptions lockOptions = new LockOptions(LockMode.NONE);

	public HistoryQueryBuilder setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	public HistoryQueryBuilder setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public HistoryQueryBuilder setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
		return this;
	}

	public HistoryQueryBuilder setCacheRegion(String cacheRegion) {
		this.cacheRegion = cacheRegion;
		return this;
	}

	public HistoryQueryBuilder setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public HistoryQueryBuilder setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
		return this;
	}

	public HistoryQueryBuilder setCacheMode(CacheMode cacheMode) {
		this.cacheMode = cacheMode;
		return this;
	}

	public HistoryQueryBuilder setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public void applyFilter() {
		for (AuditCriterion criterion : criterionsAudit) {
			criterion.addToQuery(enversService, versionsReader, entityName, qb, qb.getRootParameters());
		}
	}

	protected Query buildQuery(Session session) {
		Query query = qb.toQuery(session);
		setQueryProperties(query);
		return query;
	}

	public List<?> buildAndExecuteQuery(Session session) {
		applyFilter();
		Query query = buildQuery(session);
		return query.list();
	}

	protected void setQueryProperties(Query query) {
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (cacheable != null) {
			query.setCacheable(cacheable);
		}
		if (cacheRegion != null) {
			query.setCacheRegion(cacheRegion);
		}
		if (comment != null) {
			query.setComment(comment);
		}
		if (flushMode != null) {
			query.setFlushMode(flushMode);
		}
		if (cacheMode != null) {
			query.setCacheMode(cacheMode);
		}
		if (timeout != null) {
			query.setTimeout(timeout);
		}
		if (lockOptions != null && lockOptions.getLockMode() != LockMode.NONE) {
			query.setLockMode(REFERENCED_ENTITY_ALIAS, lockOptions.getLockMode());
		}
	}
}
