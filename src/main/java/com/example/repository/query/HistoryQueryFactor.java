package com.example.repository.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.exception.AuditException;
import org.hibernate.service.ServiceRegistry;

import com.example.repository.envers.CustomAuditReaderImpl;

public class HistoryQueryFactor {

	private HistoryQueryBuilder queryBuilder;
	private Session session;
	private CustomAuditReaderImpl auditReader;

	private HistoryQueryFactor(String versionsEntityName, Session session, EnversService enversService,
			SessionImplementor sessionImpl) {
		super();
		this.session = session;
		this.auditReader = new CustomAuditReaderImpl(enversService, session, sessionImpl);
		this.queryBuilder = new HistoryQueryBuilder(versionsEntityName, enversService, auditReader);
	}

	public List<?> getResult(Object code) {
		queryBuilder.withEntityId(code);
		return queryBuilder.buildAndExecuteQuery(session);
	}

	/**
	 * @param id
	 * @param user
	 * @return
	 */
	public List<?> getResult(Object code, Long user) {
		queryBuilder.withEntityId(code);
		queryBuilder.withProperties("userChange", user);
		return queryBuilder.buildAndExecuteQuery(session);
	}

	/**
	 * Create an audit reader associated with an open entity manager.
	 *
	 * @param entityManager An open entity manager.
	 *
	 * @return An audit reader associated with the given entity manager. It
	 *         shouldn't be used after the entity manager is closed.
	 *
	 * @throws AuditException When the given entity manager is not based on
	 *             Hibernate, or if the required listeners aren't installed.
	 */
	public static HistoryQueryFactor get(String versionsEntityName, EntityManager entityManager) throws AuditException {
		if (entityManager.getDelegate() instanceof Session) {
			return get(versionsEntityName, (Session) entityManager.getDelegate());
		}

		if (entityManager.getDelegate() instanceof EntityManager) {
			return get(versionsEntityName, (EntityManager) entityManager.getDelegate());
		}

		throw new AuditException("Hibernate EntityManager not present!");
	}

	/**
	 * Create a custom audit reader associated with an open session.
	 *
	 * @param session An open session.
	 *
	 * @return An audit reader associated with the given sesison. It shouldn't be
	 *         used after the session is closed.
	 *
	 * @throws AuditException When the given required listeners aren't installed.
	 */
	public static HistoryQueryFactor get(String versionsEntityName, Session session) {
		SessionImplementor sessionImpl;
		if (!(session instanceof SessionImplementor)) {
			sessionImpl = (SessionImplementor) session.getSessionFactory().getCurrentSession();
		} else {
			sessionImpl = (SessionImplementor) session;
		}

		final ServiceRegistry serviceRegistry = sessionImpl.getFactory().getServiceRegistry();
		final EnversService enversService = serviceRegistry.getService(EnversService.class);

		return new HistoryQueryFactor(versionsEntityName, session, enversService, sessionImpl);
	}

}
