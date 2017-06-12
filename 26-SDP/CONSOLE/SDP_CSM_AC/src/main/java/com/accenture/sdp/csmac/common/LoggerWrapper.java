package com.accenture.sdp.csmac.common;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.controllers.SessionController;

public class LoggerWrapper {

	private static Logger rootLogger;
	private Logger logger = null;

	private static final long ONE_SECOND = 1000;
	private static final long ONE_MINUTE = ONE_SECOND * 60;
	private static final String LOG_DELIMITER = "\t- ";

	@SuppressWarnings("rawtypes")
	public LoggerWrapper(Class inputClass) {
		this.logger = Logger.getLogger(inputClass);
	}

	public static void initialize() throws IOException {
		String cfgFile = ApplicationConstants.CONF_APP_DIR + File.separator + ApplicationConstants.LOG4J_FILE_NAME;
		if (ApplicationConstants.CONFIGURATION_PATH != null) {
			cfgFile = ApplicationConstants.CONFIGURATION_PATH + File.separator + cfgFile;
			PropertyConfigurator.configureAndWatch(cfgFile, ONE_MINUTE);
		} else {
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(cfgFile));
			PropertyConfigurator.configure(properties);
		}
		rootLogger = Logger.getRootLogger();
		rootLogger.debug("Success Log4jConfiguration");
	}

	/**
	 * This method will root Logger
	 * 
	 * @return rootLogger Logger
	 */
	public static Logger getRootLogger() {
		return rootLogger;
	}

	public void logInfo(String message, Object... args) {
		try {
			if (this.logger.isEnabledFor(Level.INFO)) {
				this.logger.log(Level.INFO, getStaticInfo() + LOG_DELIMITER + String.format(message, args));
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logDebug(String message, Object... args) {
		try {
			if (this.logger.isEnabledFor(Level.DEBUG)) {
				this.logger.log(Level.DEBUG, getStaticInfo() + LOG_DELIMITER + String.format(message, args));
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logError(String message, Object... args) {
		try {
			if (this.logger.isEnabledFor(Level.ERROR)) {
				this.logger.log(Level.ERROR, String.format(message, args));
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logError(Exception ex) {
		logException(ex.getMessage(), ex);
	}

	public void logException(String message, Exception ex) {
		try {
			if (this.logger.isEnabledFor(Level.ERROR)) {
				this.logger.log(Level.ERROR, message, ex);
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logStartFeature(String feature, Map<String, Object> valueMap) {
		try {
			if (this.logger.isEnabledFor(Level.INFO)) {
				if (valueMap != null && !valueMap.isEmpty()) {
					StringBuffer buffer = new StringBuffer();
					Iterator<Entry<String, Object>> i = valueMap.entrySet().iterator();
					while (i.hasNext()) {
						Entry<String, Object> entry = i.next();
						buffer.append(Utilities.getDefaultMessage(ApplicationConstants.MESSAGE_BUNDLE, entry.getKey()));
						buffer.append(" = ");
						buffer.append(entry.getValue());
						if (i.hasNext()) {
							buffer.append(", ");
						}
					}
					this.logger.log(Level.INFO, getStaticInfo() + LOG_DELIMITER + feature + LOG_DELIMITER + buffer.toString());
				} else {
					this.logger.log(Level.INFO, getStaticInfo() + LOG_DELIMITER + feature);
				}
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logStartFeature(Map<String, Object> valueMap) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		logStartFeature(infoSessionBean.getBreadCrumbController().toString(), valueMap);
	}

	public void logEndFeature(String feature, String resultCode, String resultMessage) {
		try {

			if (this.logger.isEnabledFor(Level.INFO)) {
				this.logger.log(Level.INFO, getStaticInfo() + LOG_DELIMITER + feature + LOG_DELIMITER + resultCode + ", " + resultMessage);
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logEndFeature(String resultCode, String resultMessage) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		logEndFeature(infoSessionBean.getBreadCrumbController().toString(), resultCode, resultMessage);
	}

	public void logEndFeature(ServiceErrorException e) {
		logEndFeature(e.getCode(), e.getMessage());
	}

	private String getStaticInfo() {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		String username = "";
		if (infoSessionBean.isLogged()) {
			username = infoSessionBean.getOperator().getUsername();
		}
		String ipAddress = infoSessionBean.getIpAddress();
		String sessionId = infoSessionBean.getSessionId();
		return "sessionId=" + sessionId + LOG_DELIMITER + ipAddress + LOG_DELIMITER + username;
	}

}
