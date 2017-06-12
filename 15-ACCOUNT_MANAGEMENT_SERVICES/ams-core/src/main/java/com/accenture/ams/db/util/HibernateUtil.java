package com.accenture.ams.db.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility used to configure Hibernate's Session Factory and retrieve
 * it.
 */
public class HibernateUtil {

	private static HibernateUtil instance = null;
	/**
	 * DEFAULT TENANT NAME WHEN APPLICATION IS CONFIGURED FOR SINGLE TENANT
	 * ENVIROMENT
	 */
	public static String TENANT_DEFAULT = "tenantDefault";

	/**
	 * MAP OF HIBERNATE SESSION FACTORY ONE MAP FOR EACH TENANT
	 */
	private HashMap<String, Object> sessionFactoriesMap = null;
	private boolean initialized = false;

	private HibernateUtil() {
	}

	public static HibernateUtil getInstance() {
		if (instance == null)
			instance = new HibernateUtil();
		return instance;
	}

	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Retrieve session factory for specified tenant If tenant doesn't exist,
	 * retrieve DEFAULT TENANT Session Factory
	 * 
	 * @param : tenantName(String) : tenant's Session Factory to retrieve
	 * @return session factory
	 */
	public SessionFactory getSessionFactory(String tenantName) {
		if (sessionFactoriesMap == null
				|| sessionFactoriesMap.get(tenantName) == null)
			return (SessionFactory) sessionFactoriesMap.get(TENANT_DEFAULT);
		return (SessionFactory) sessionFactoriesMap.get(tenantName);
	}

	public Set<String> getConfiguredTenant() {
		Set<String> listKeys = sessionFactoriesMap.keySet();
		return listKeys;
	}

	/**
	 * Initialized SessionFactorie's map with tenant specified in 'tenantSet'
	 * parameter. If there aren't tenant, set configuration only for default
	 * tenant
	 * 
	 * @param tenantSet
	 * @return
	 */
	public boolean initSessionFactories(Properties tenantSet) {
		try {
			LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
					"initSessionFactories. Start session factories map configuration");
			//if (sessionFactoriesMap != null)
			//	throw new Exception(
			//			"SessionFactories map initialized! Use updateSessionFactory instead");

			sessionFactoriesMap = new HashMap<String, Object>();

			// SINGLE TENANT CASE
			if (tenantSet.isEmpty()) {
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"initSessionFactories. Single tenant configuration");
				SessionFactory sf = new Configuration().configure()
						.buildSessionFactory();
				sessionFactoriesMap.put(TENANT_DEFAULT, sf);
				return true;
			}
			LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
					"initSessionFactories. Multi tenant configuration");
			// MULTI TENANT CASE
			Enumeration<Object> tenantKeys = tenantSet.keys();

			while (tenantKeys.hasMoreElements()) {
				String tenantKey = (String) tenantKeys.nextElement();
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"initSessionFactories. Configuration for tenant : "
								+ tenantKey);
				String hbmConfigFilePath = (String) tenantSet.get(tenantKey);
				File hbmConfigFile = new File(hbmConfigFilePath);

				if (!hbmConfigFile.exists())
					throw new Exception(
							hbmConfigFilePath
									+ " does not exist!! Check config file and system configuration!!");

				SessionFactory sf = new Configuration()
						.configure(hbmConfigFile).buildSessionFactory();
				sessionFactoriesMap.put(tenantKey, sf);
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"initSessionFactories. tenant '" + tenantKey
								+ "' correctly configured!!");
			}
			LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
					"initSessionFactories. End session factories map configuration");
			initialized = true;
			return true;

		} catch (Exception e) {
			sessionFactoriesMap = null;
			initialized = false;
			LogUtil.writeErrorLog(
					HibernateUtil.class,
					"initSessionFactories : Generic Exception during tenant's session Factory configuration. Cause : ",
					e);
			return false;
		}
	}

	/**
	 * Update SessionFactorie's map with tenant specified in 'tenantSet'
	 * parameter. If there aren't tenant, set configuration only for default
	 * tenant
	 * 
	 * @param tenantSet
	 * @return
	 */
	public boolean updateSessionFactories(Properties tenantSet) {
		try {
			LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
					"updateSessionFactories. Update session factories map configuration");
			if (sessionFactoriesMap == null)
				throw new Exception(
						"SessionFactories map not initialized! Use initSessionFactory instead");

			// SINGLE TENANT CASE
			if (tenantSet.isEmpty()) {
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"updateSessionFactories. Single tenant configuration");
				sessionFactoriesMap.remove(TENANT_DEFAULT);
				SessionFactory sf = new Configuration().configure()
						.buildSessionFactory();
				sessionFactoriesMap.put(TENANT_DEFAULT, sf);
				return true;
			}
			LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
					"updateSessionFactories. Multi tenant configuration");
			// MULTI TENANT CASE
			Enumeration<Object> tenantKeys = tenantSet.keys();

			while (tenantKeys.hasMoreElements()) {
				String tenantKey = (String) tenantKeys.nextElement();
				sessionFactoriesMap.remove(tenantKey);
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"updateSessionFactories. update for tenant : "
								+ tenantKey);
				String hbmConfigFilePath = (String) tenantSet.get(tenantKey);
				File hbmConfigFile = new File(hbmConfigFilePath);

				if (!hbmConfigFile.exists())
					throw new Exception(
							hbmConfigFilePath
									+ " does not exist!! Check config file and system configuration!!");

				SessionFactory sf = new Configuration()
						.configure(hbmConfigFile).buildSessionFactory();
				sessionFactoriesMap.put(tenantKey, sf);
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"updateSessionFactories. tenant '" + tenantKey
								+ "' correctly configured!!");
			}
			LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
					"updateSessionFactories. End session factories map configuration");
			return true;

		} catch (Exception e) {
			sessionFactoriesMap = null;
			LogUtil.writeErrorLog(
					HibernateUtil.class,
					"updateSessionFactories : Generic Exception during tenant's session Factory configuration. Cause : ",
					e);
			return false;
		}
	}

	public boolean reloadDbForTenant(String tenantName, String hibernateCfgPath) {
		try {
			SessionFactory sf = (SessionFactory) sessionFactoriesMap
					.get(tenantName);
			if (sf != null)
				sf.close();
			sessionFactoriesMap.remove(tenantName);

			File f = new File(hibernateCfgPath);
			if (!f.exists()) {
				LogUtil.writeCommonLog("INFO", HibernateUtil.class, "INTERNAL",
						"reloadDbForTenant : " + f.getAbsolutePath()
								+ " does not exist");
			}
			sf = new Configuration().configure(f).buildSessionFactory();
			sessionFactoriesMap.put(tenantName, sf);
			return true;
		} catch (Exception e) {
			LogUtil.writeErrorLog(HibernateUtil.class, "ERROR", e);
			return false;
		}
	}

	public boolean tenantCheck(String tenantName) throws Exception {
		if (sessionFactoriesMap == null)
			throw new Exception("Tenants MAP is null");

		return (sessionFactoriesMap.get(tenantName) != null) ? true : false;
	}
}