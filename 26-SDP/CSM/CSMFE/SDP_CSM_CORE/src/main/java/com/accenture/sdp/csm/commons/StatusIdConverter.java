package com.accenture.sdp.csm.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.model.jpa.RefStatusType;

public abstract class StatusIdConverter {

	private static final Map<Long, String> CACHE_ID = new HashMap<Long, String>();
	private static final Map<String, Long> CACHE_NAME = new HashMap<String, Long>();

	public static String getStatusDescription(long statusId) throws PropertyNotFoundException {
		String name = CACHE_ID.get(statusId);
		if (name == null) {
			EntityManager em = PersistenceManager.getEntityManager();
			try {
				RefStatusType status = em.find(RefStatusType.class, statusId);
				if (status == null) {
					throw new PropertyNotFoundException(null, null, "Unexpected Status ID = " + statusId);
				}
				name = status.getStatusName();
				CACHE_ID.put(statusId, name);
				CACHE_NAME.put(name, statusId);
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
			}
		}
		return name;
	}

	public static Long getStatusValue(String statusName) throws PropertyNotFoundException {
		Long id = CACHE_NAME.get(statusName);
		if (id == null) {
			EntityManager em = PersistenceManager.getEntityManager();
			try {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put(RefStatusType.PARAM_STATUS_NAME, statusName);
				List<RefStatusType> status = NamedQueryHelper.executeNamedQuery(RefStatusType.class, RefStatusType.STATUS_TYPE_RETRIEVE_BY_NAME, params, em);
				if (status == null) {
					throw new PropertyNotFoundException(null, null, "Unexpected Status ID Description = " + statusName);
				}
				id = status.get(0).getStatusId();
				String name = status.get(0).getStatusName();
				CACHE_ID.put(id, name);
				CACHE_NAME.put(name, id);
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
			}
		}
		return id;
	}

	public static List<RefStatusType> searchAllStatus(EntityManager em) {
		return NamedQueryHelper.executeNamedQuery(RefStatusType.class, RefStatusType.STATUS_TYPE_RETRIEVE_ALL, null, em);
	}

}
