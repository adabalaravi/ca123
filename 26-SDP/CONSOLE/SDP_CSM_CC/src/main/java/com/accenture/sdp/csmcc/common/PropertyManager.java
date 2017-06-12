package com.accenture.sdp.csmcc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;

public final class PropertyManager implements ApplicationConstants, Serializable {

	/**
	 * 
	 */
	private static Map<String, PropertyManager> map = new HashMap<String, PropertyManager>();

	private static final long serialVersionUID = 1L;

	private Properties properties;

	private PropertyManager(String fileName) {
		InputStream inputStream = null;
		LoggerWrapper log = new LoggerWrapper(PropertyManager.class);
		try {
			String path = CONF_APP_DIR + System.getProperty(FILE_SEPARATOR_PROP) + fileName;
			properties = new Properties();
			if (CONFIGURATION_PATH != null) {
				path = CONFIGURATION_PATH + path;
				inputStream = new FileInputStream(new File(path));
				properties.load(inputStream);
			} else {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			}

		} catch (IOException e) {
			log.logException(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.logException(e.getMessage(), e);
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
