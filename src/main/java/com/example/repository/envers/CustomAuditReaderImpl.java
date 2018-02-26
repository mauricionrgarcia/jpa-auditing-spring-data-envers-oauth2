package com.example.repository.envers;

import static org.hibernate.envers.internal.tools.ArgumentsTools.checkNotNull;
import static org.hibernate.envers.internal.tools.EntityTools.getTargetClassIfProxied;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.exception.NotAuditedException;
import org.hibernate.envers.internal.reader.AuditReaderImpl;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.query.AuditEntity;

/**
 * Custom Audit reader from {@link AuditReaderImpl} to recover only entities
 * from code
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 23:06:05
 */
public class CustomAuditReaderImpl extends AuditReaderImpl implements AuditReaderImplementor {
	private final EnversService enversService;
	private final Session session;

	public CustomAuditReaderImpl(EnversService enversService, Session session, SessionImplementor sessionImplementor) {
		super(enversService, session, sessionImplementor);
		this.enversService = enversService;
		this.session = session;
	}

	private void checkSession() {
		if (!session.isOpen()) {
			throw new IllegalStateException("The associated entity manager is closed!");
		}
	}

	@SuppressWarnings({ "unchecked" })
	public <T> List<T> findEntity(Class<T> cls, Object primaryKey)
			throws IllegalArgumentException, NotAuditedException, IllegalStateException {
		checkNotNull(cls, "Entity class");
		checkNotNull(primaryKey, "Primary key");
		checkSession();

		cls = getTargetClassIfProxied(cls);
		String entityName = cls.getName();

		if (!enversService.getEntitiesConfigurations().isVersioned(entityName)) {
			throw new NotAuditedException(entityName, entityName + " is not versioned!");
		}

		Object result;
		try {
			result = new EntitiesQuery(enversService, this, cls).add(AuditEntity.id().eq(primaryKey)).getResultList();
		} catch (NoResultException e) {
			result = null;
		}

		return (List<T>) result;
	}
}
