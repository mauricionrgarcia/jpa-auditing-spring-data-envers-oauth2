/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.example.repository.envers;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.exception.AuditException;
import org.hibernate.envers.internal.entities.EntityConfiguration;
import org.hibernate.envers.internal.entities.EntityInstantiator;
import org.hibernate.envers.internal.entities.mapper.id.IdMapper;
import org.hibernate.envers.internal.entities.mapper.relation.lazy.ToOneDelegateSessionImplementor;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.internal.tools.ReflectionTools;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 27/02/2018 01:02:25
 */
public class EntityInstantiatorImpl extends EntityInstantiator {
	private final EnversService enversService;
	private final AuditReaderImplementor versionsReader;

	public EntityInstantiatorImpl(EnversService enversService, AuditReaderImplementor versionsReader) {
		super(enversService, versionsReader);
		this.enversService = enversService;
		this.versionsReader = versionsReader;
	}

	/**
	 * Creates an entity instance based on an entry from the versions table.
	 *
	 * @param entityName Name of the entity, which instances should be read
	 * @param versionsEntity An entry in the versions table, from which data should
	 *            be mapped.
	 * @param revision Revision at which this entity was read.
	 *
	 * @return An entity instance, with versioned properties set as in the
	 *         versionsEntity map, and proxies created for collections.
	 */
	public Object createInstanceFromVersionsEntity(String entityName, Map versionsEntity) {
		if (versionsEntity == null) {
			return null;
		}

		// The $type$ property holds the name of the (versions) entity
		final String type = enversService.getEntitiesConfigurations()
				.getEntityNameForVersionsEntityName((String) versionsEntity.get("$type$"));

		if (type != null) {
			entityName = type;
		}

		// First mapping the primary key
		final IdMapper idMapper = enversService.getEntitiesConfigurations().get(entityName).getIdMapper();
		final Map originalId = (Map) versionsEntity
				.get(enversService.getAuditEntitiesConfiguration().getOriginalIdPropName());

		// Fixes HHH-4751 issue (@IdClass with @ManyToOne relation mapping inside)
		// Note that identifiers are always audited
		// Replace identifier proxies if do not point to audit tables
		replaceNonAuditIdProxies(versionsEntity);

		final Object primaryKey = idMapper.mapToIdFromMap(originalId);

		// If it is not in the cache, creating a new entity instance
		Object ret;
		try {
			EntityConfiguration entCfg = enversService.getEntitiesConfigurations().get(entityName);
			if (entCfg == null) {
				// a relation marked as RelationTargetAuditMode.NOT_AUDITED
				entCfg = enversService.getEntitiesConfigurations().getNotVersionEntityConfiguration(entityName);
			}

			final Class<?> cls = ReflectionTools.loadClass(entCfg.getEntityClassName(),
					enversService.getClassLoaderService());
			ret = ReflectHelper.getDefaultConstructor(cls).newInstance();
		} catch (Exception e) {
			throw new AuditException(e);
		}

		enversService.getEntitiesConfigurations().get(entityName).getPropertyMapper().mapToEntityFromMap(enversService,
				ret, versionsEntity, primaryKey, versionsReader, null);
		idMapper.mapToEntityFromMap(ret, originalId);

		return ret;
	}

	private void replaceNonAuditIdProxies(Map versionsEntity) {
		final Map originalId = (Map) versionsEntity
				.get(enversService.getAuditEntitiesConfiguration().getOriginalIdPropName());
		for (Object key : originalId.keySet()) {
			final Object value = originalId.get(key);
			if (value instanceof HibernateProxy) {
				final HibernateProxy hibernateProxy = (HibernateProxy) value;
				final LazyInitializer initializer = hibernateProxy.getHibernateLazyInitializer();
				final String entityName = initializer.getEntityName();
				final Serializable entityId = initializer.getIdentifier();
				if (enversService.getEntitiesConfigurations().isVersioned(entityName)) {
					final String entityClassName = enversService.getEntitiesConfigurations().get(entityName)
							.getEntityClassName();
					final Class entityClass = ReflectionTools.loadClass(entityClassName,
							enversService.getClassLoaderService());
					final ToOneDelegateSessionImplementor delegate = new ToOneDelegateSessionImplementor(versionsReader,
							entityClass, entityId, null,
							RevisionType.DEL.equals(versionsEntity
									.get(enversService.getAuditEntitiesConfiguration().getRevisionTypePropName())),
							enversService);
					originalId.put(key, versionsReader.getSessionImplementor().getFactory()
							.getEntityPersister(entityName).createProxy(entityId, delegate));
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void addInstancesFromVersionsEntities(String entityName, Collection addTo, List<Map> versionsEntities) {
		for (Map versionsEntity : versionsEntities) {
			addTo.add(createInstanceFromVersionsEntity(entityName, versionsEntity));
		}
	}

	public EnversService getEnversService() {
		return enversService;
	}

	public AuditReaderImplementor getAuditReaderImplementor() {
		return versionsReader;
	}
}
