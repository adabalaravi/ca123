package com.accenture.sdp.csmac.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;

public final class PropertyManager {

	private static Map<String, PropertyManager> map = new HashMap<String, PropertyManager>();
	private static LoggerWrapper log = new LoggerWrapper(PropertyManager.class);

	private Properties properties;

	private PropertyManager(String fileName) {
		InputStream inputStream = null;
		try {
			String path = ApplicationConstants.CONF_APP_DIR + File.separator + fileName;
			properties = new Properties();
			if (ApplicationConstants.CONFIGURATION_PATH != null) {
				// configurazione per jboss
				path = ApplicationConstants.CONFIGURATION_PATH + File.separator + path;
				log.logDebug("Loading properties from : " + path);
				inputStream = new FileInputStream(new File(path));
				properties.load(inputStream);
			} else {
				// configurazione per tomcat
				log.logDebug("Loading properties from : " + path);
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			}
		} catch (IOException e) {
			log.logError(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.logError(e);
				}
			}
		}
	}

	/**
	 * 
	 * @param property
	 *            name of the property to read
	 * @return the value of the property null otherwise
	 */
	public String getProperty(String property) {
		return properties.getProperty(property);
	}

	public static PropertyManager getManager(String fileName) {
		PropertyManager manager = map.get(fileName);
		if (manager == null) {
			manager = new PropertyManager(fileName);
			map.put(fileName, manager);
		}
		return manager;
	}

}
