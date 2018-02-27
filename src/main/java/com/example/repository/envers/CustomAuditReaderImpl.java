package com.example.repository.envers;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.reader.AuditReaderImpl;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;

/**
 * Custom Audit reader from {@link AuditReaderImpl} to recover only entities
 * from code
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 23:06:05
 */
public class CustomAuditReaderImpl extends AuditReaderImpl implements AuditReaderImplementor {

	public CustomAuditReaderImpl(EnversService enversService, Session session, SessionImplementor sessionImplementor) {
		super(enversService, session, sessionImplementor);
	}
}
