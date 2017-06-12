package com.accenture.sdp.csm.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.model.jpa.RefPartyType;

public abstract class PartyTypeIdConverter {

	private static final Map<Long, String> CACHE_ID = new HashMap<Long, String>();
	private static final Map<String, Long> CACHE_NAME = new HashMap<String, Long>();

	public static String getPartyTypeDescription(Long partyTypeId) throws PropertyNotFoundException {
		String name = CACHE_ID.get(partyTypeId);
		if (name == null) {
			EntityManager em = PersistenceManager.getEntityManager();
			try {
				RefPartyType type = em.find(RefPartyType.class, partyTypeId);
				if (type == null) {
					throw new PropertyNotFoundException(null, null, "Unexpected Party Type ID = " + partyTypeId);
				}
				name = type.getPartyTypeName();
				CACHE_ID.put(partyTypeId, name);
				CACHE_NAME.put(name, partyTypeId);
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
			}
		}
		return name;
	}

	public static Long getPartyTypeValue(String partyTypeName) throws PropertyNotFoundException {
		Long id = CACHE_NAME.get(partyTypeName);
		if (id == null) {
			EntityManager em = PersistenceManager.getEntityManager();
			try {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put(RefPartyType.PARAM_PARTY_TYPE_NAME, partyTypeName);
				List<RefPartyType> type = NamedQueryHelper.executeNamedQuery(RefPartyType.class, RefPartyType.PARTY_TYPE_RETRIEVE_BY_NAME, params, em);
				if (type == null) {
					throw new PropertyNotFoundException(null, null, "Unexpected Party Type Name = " + partyTypeName);
				}
				id = type.get(0).getPartyTypeId();
				CACHE_ID.put(id, partyTypeName);
				CACHE_NAME.put(partyTypeName, id);
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
			}
		}
		return id;
	}
}
