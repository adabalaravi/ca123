package com.accenture.sdp.devicemetering.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.accenture.sdp.devicemetering.database.NamedQueryHelper;
import com.accenture.sdp.devicemetering.database.PersistenceManager;
import com.accenture.sdp.devicemetering.exceptions.PropertyNotFoundException;
import com.accenture.sdp.devicemetering.exceptions.ValidationException;
import com.accenture.sdp.devicemetering.model.RefCsmConstants;
import com.accenture.sdp.devicemetering.utilities.logging.LoggerWrapper;

/**
 * this class handles the constants of REF_CSM_CONSTANTS
 * 
 * @author elia.furiozzi
 * 
 */
public final class ConstantsHandler {

	private Map<String, String> constants;

	private static final Map<String, ConstantsHandler> INSTANCES = new HashMap<String, ConstantsHandler>();
	private static ConstantsHandler instance;
	private static LoggerWrapper log = new LoggerWrapper(ConstantsHandler.class);

	private ConstantsHandler(EntityManager em) {
		this.constants = new HashMap<String, String>();
		List<RefCsmConstants> cList = NamedQueryHelper.executeNamedQuery(RefCsmConstants.class, RefCsmConstants.CONSTANTS_RETRIEVE_ALL, null, em);
		if (cList != null) {
			for (RefCsmConstants c : cList) {
				this.constants.put(c.getConstantKey(), c.getConstantValue());
			}
		}
	}

	public String retrieveConstant(String constantName) throws PropertyNotFoundException {
		String result = constants.get(constantName);
		if (result == null) {
			throw new PropertyNotFoundException(constantName, Utilities.getCurrentClassAndMethod(), String.format("Constant %s non found", constantName));
		}
		return result;
	}

	public boolean retrieveBooleanConstant(String constantName) throws PropertyNotFoundException {
		return Boolean.parseBoolean(retrieveConstant(constantName));
	}

	public Long retrieveLongConstant(String constantName) throws PropertyNotFoundException {
		try {
			return Long.parseLong(retrieveConstant(constantName));
		} catch (NumberFormatException e) {
			throw new PropertyNotFoundException(constantName, Utilities.getCurrentClassAndMethod(),
					String.format("Constant %s type is not Long", constantName), e);
		}
	}

	private static void init() {
		log.logInfo("Loading ConstantsHandler");
		EntityManager em = null;
		try {
			em = PersistenceManager.getEntityManager();
			instance = new ConstantsHandler(em);
			log.logInfo("ConstantsHandler loaded");
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	private static ConstantsHandler init(String tenantName) throws ValidationException, PropertyNotFoundException {
		log.logInfo("Loading ConstantsHandler for " + tenantName);
		EntityManager em = null;
		try {
			em = PersistenceManager.getMeteringEntityManager(tenantName);
			ConstantsHandler inst = new ConstantsHandler(em);
			INSTANCES.put(tenantName, inst);
			log.logInfo("ConstantsHandler loaded  for " + tenantName);
			return inst;
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	public static ConstantsHandler getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public static ConstantsHandler getInstance(String tenantName) throws ValidationException, PropertyNotFoundException {
		ConstantsHandler inst = INSTANCES.get(tenantName);
		if (inst == null) {
			inst = init(tenantName);
		}
		return inst;
	}

	public static void refresh() {
		log.logInfo("Refreshing constants...");
		init();
		log.logInfo("Constants have been refreshed");
	}

	public static void refresh(String tenantName) throws ValidationException, PropertyNotFoundException {
		log.logInfo("Refreshing constants...");
		init(tenantName);
		log.logInfo("Constants have been refreshed");
	}
}
