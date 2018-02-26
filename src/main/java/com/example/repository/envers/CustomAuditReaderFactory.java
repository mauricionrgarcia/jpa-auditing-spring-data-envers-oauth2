/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.example.repository.envers;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.exception.AuditException;
import org.hibernate.service.ServiceRegistry;

/**
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 23:20:27
 */
public class CustomAuditReaderFactory {

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
	public static CustomAuditReaderImpl get(Session session) throws AuditException {
		SessionImplementor sessionImpl;
		if (!(session instanceof SessionImplementor)) {
			sessionImpl = (SessionImplementor) session.getSessionFactory().getCurrentSession();
		} else {
			sessionImpl = (SessionImplementor) session;
		}

		final ServiceRegistry serviceRegistry = sessionImpl.getFactory().getServiceRegistry();
		final EnversService enversService = serviceRegistry.getService(EnversService.class);

		return new CustomAuditReaderImpl(enversService, session, sessionImpl);
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
	public static CustomAuditReaderImpl get(EntityManager entityManager) throws AuditException {
		if (entityManager.getDelegate() instanceof Session) {
			return get((Session) entityManager.getDelegate());
		}

		if (entityManager.getDelegate() instanceof EntityManager) {
			return get((EntityManager) entityManager.getDelegate());
		}

		throw new AuditException("Hibernate EntityManager not present!");
	}
}
