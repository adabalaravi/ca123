package com.accenture.sdp.csm.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;

public abstract class CodeManager {

	private static Properties codesProperties;

	private static final String COMMON_CODE_PREFIX = "com.accenture.sdp.csm.common.code";
	private static final String KEY_SEPARATOR = ".";
	private static final String DESCRIPTION_SUFFIX = "description";

	public static void init() throws IOException {
		codesProperties = new Properties();
		InputStream innerPropFile = null;
		try {
			// at fist look in the war
			innerPropFile = CodeManager.class.getClassLoader().getResourceAsStream(Constants.EVENT_DESCRIPTION_FILENAME);
			codesProperties.load(innerPropFile);
		} catch (Exception e) {
			// if not found look in the cfg folder
			String cfgFile = Constants.CSM_CONFIGURATION_PATH + Constants.EVENT_DESCRIPTION_FILENAME;
			InputStream propFile = null;
			try {
				propFile = new FileInputStream(cfgFile);
				codesProperties.load(propFile);
				// this time error will not be caught
			} finally {
				Utilities.closeStream(propFile);
			}
		} finally {
			Utilities.closeStream(innerPropFile);
		}
	}

	public static String loadCode(String codeType) throws PropertyNotFoundException {
		// Load data from the properties file.
		String code = readCode(COMMON_CODE_PREFIX, codeType);
		if (code == null) {
			throw new PropertyNotFoundException(codeType, COMMON_CODE_PREFIX);
		}
		return code;
	}

	public static String loadCodeDescription(String codeType) throws PropertyNotFoundException {
		// Load data from the properties file.
		String description = readCodeDescription(COMMON_CODE_PREFIX, codeType);
		if (description == null) {
			throw new PropertyNotFoundException(codeType, COMMON_CODE_PREFIX);
		}
		return description;
	}

	public static String loadCode(String codeType, String customPrefix) throws PropertyNotFoundException {
		// Load data from the properties file.
		String code = readCode(COMMON_CODE_PREFIX, codeType);
		if (code == null) {
			code = readCode(customPrefix, codeType);
		}
		if (code == null) {
			throw new PropertyNotFoundException(codeType, customPrefix);
		}
		return code;
	}

	public static String loadCodeDescription(String codeType, String customPrefix) throws PropertyNotFoundException {
		// Load data from the properties file.
		String description = readCodeDescription(COMMON_CODE_PREFIX, codeType);
		if (description == null) {
			description = readCodeDescription(customPrefix, codeType);
		}
		if (description == null) {
			throw new PropertyNotFoundException(codeType, customPrefix);
		}
		return description;
	}

	private static String readCode(String codePrefix, String codeType) {
		// load specific code
		String key = codePrefix + KEY_SEPARATOR + codeType;
		key = key.toLowerCase();
		return codesProperties.getProperty(key);
	}

	private static String readCodeDescription(String codePrefix, String codeType) {
		// Load data from the properties file.
		String codeDescription = null;
		String key = codePrefix + KEY_SEPARATOR + codeType + KEY_SEPARATOR + DESCRIPTION_SUFFIX;
		key = key.toLowerCase();
		codeDescription = codesProperties.getProperty(key);
		if (codeDescription == null) {
			return null;
		}
		return codeDescription;
	}
}
