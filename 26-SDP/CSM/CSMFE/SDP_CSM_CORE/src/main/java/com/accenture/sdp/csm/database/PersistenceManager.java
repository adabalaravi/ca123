package com.accenture.sdp.csm.database;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefTenant;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public final class PersistenceManager {

	private static LoggerWrapper log = new LoggerWrapper(PersistenceManager.class);

	private EntityManagerFactory baseEmf;
	private Map<String, EntityManagerFactory> emfs;

	private static PersistenceManager singleton;

	private PersistenceManager() {
	}

	public static PersistenceManager getInstance() {
		if (singleton == null) {
			singleton = new PersistenceManager();
		}
		return singleton;
	}

	private EntityManagerFactory getEntityManagerFactory() {
		if (baseEmf == null) {
			baseEmf = createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME);
		}
		return baseEmf;
	}

	private EntityManagerFactory getEntityManagerFactory(String tenant) throws ValidationException, PropertyNotFoundException {
		if (Utilities.isNull(tenant)) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, FieldConstants.TENANT_NAME);
		}
		if (emfs == null) {
			emfs = new HashMap<String, EntityManagerFactory>();
		}
		EntityManagerFactory emf = emfs.get(tenant);
		if (emf == null) {
			// retrieve tenant information on base EM
			EntityManager em = null;
			try {
				em = getEntityManager();
				RefTenant bean = em.find(RefTenant.class, tenant);
				if (bean == null) {
					log.logError("Persistence Unit for tenant " + tenant + " not configured");
					throw new ValidationException(Constants.CODE_TENANT_INVALID, FieldConstants.TENANT_NAME, tenant);
				}
				if (!bean.getStatusId().equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE))) {
					log.logError("Persistence Unit for tenant " + tenant + " is not active");
					throw new ValidationException(Constants.CODE_TENANT_INACTIVE, FieldConstants.TENANT_NAME, tenant);
				}
				emf = createEntityManagerFactory(bean.getPersistenceUnit());
				emfs.put(tenant, emf);
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
			}

		}
		return emf;
	}

	private EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) {
		log.logInfo("createEntityManagerFactory start");
		EntityManagerFactory emf = null;
		FileInputStream persistencePropFile = null;
		try {
			Properties persistenceProperties = new Properties();

			String persistenceCfgFile = Constants.CSM_CONFIGURATION_PATH + "persistence.properties";
			log.logInfo("PERSISTENCE PROPERTIES PATH %s", persistenceCfgFile);

			persistencePropFile = new FileInputStream(persistenceCfgFile);
			persistenceProperties.load(persistencePropFile);

			for (Entry<Object, Object> entry : persistenceProperties.entrySet()) {
				log.logInfo("Persistence properties: %s = %s", entry.getKey(), entry.getValue());
			}

			Locale.setDefault(Locale.US);

			log.logInfo("Creating EM Factory");
			emf = Persistence.createEntityManagerFactory(persistenceUnitName, persistenceProperties);
			log.logInfo("*** Persistence started at " + new java.util.Date());
		} catch (Exception ex) {
			log.logError(ex);
		} finally {
			Utilities.closeStream(persistencePropFile);
		}
		return emf;
	}

	public static EntityManager getEntityManager() {
		EntityManager em = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			log.logDebug("Entity Manager Factory retrieved");
			em = emf.createEntityManager();
			log.logDebug("Entity Manager created");
		} catch (Exception e) {
			log.logError("Error creating Entity Manager", e);
		}
		return em;
	}

	public static EntityManager getEntityManager(String tenant) throws ValidationException, PropertyNotFoundException {
		EntityManager em = null;
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory(tenant);
		log.logDebug("Entity Manager Factory retrieved");
		try {
			em = emf.createEntityManager();
			log.logDebug("Entity Manager created");
		} catch (Exception e) {
			log.logError("Error creating Entity Manager", e);
		}
		return em;
	}

	private void closeEmf(EntityManagerFactory emf, String persistenceName) {
		if (emf != null) {
			emf.close();
			log.logInfo(persistenceName + " persistence finished at " + new java.util.Date());
		}
	}

	private void closeEntityManagerFactories() {
		if (emfs != null) {
			for (Entry<String, EntityManagerFactory> emf : emfs.entrySet()) {
				closeEmf(emf.getValue(), emf.getKey());
			}
			emfs.clear();
			emfs = null;
		}
	}

	public void destroyEntityManager() {
		log.logDebug("Destroying Entity Manager...");
		closeEmf(baseEmf, "Base SDP");
		baseEmf = null;
		// close every emf
		closeEntityManagerFactories();
		log.logDebug("Entity Manager destroyed...");
	}

	public void destroyEntityManager(String tenant) {
		log.logDebug("Destroying Entity Manager for tenant " + tenant + "...");
		if (emfs != null) {
			closeEmf(emfs.get(tenant), tenant);
			emfs.remove(tenant);
		}
		log.logDebug("...done");
	}
}