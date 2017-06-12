package com.accenture.sdp.csm.utilities.logging;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Trace level
 * 
 * @param text
 *            , log message formatted using String.format
 * @param args
 *            , objects used as arguments of String.format
 */
public class LoggerWrapper {
	private static Logger rootLogger;
	private Logger logger = null;

	private static final String DATE_FORMAT = "HH:mm:ss";
	private static final String SEPARATOR = " - ";

	/**
	 * Initializes the log4j static attributes
	 * 
	 */
	public static void initialize() {
		rootLogger = Logger.getRootLogger();
		rootLogger.debug("Success Log4jConfiguration");
	}

	public LoggerWrapper(Class clazz) {
		this.logger = Logger.getLogger(clazz);
	}

	/**
	 * This method will root Logger
	 * 
	 * @return rootLogger Logger
	 */
	public static Logger getRootLogger() {
		return rootLogger;
	}

	public void logStartMethod(long startTime) {
		// Timestamp, Class, Method, Start Time
		Object args[] = new String[2];
		args[0] = getLoggingMethod();
		SimpleDateFormat timeFormatter = new SimpleDateFormat(DATE_FORMAT);
		args[1] = timeFormatter.format(new Date(startTime));
		this.logger.log(Level.INFO, String.format("%s start - startTime:%s", args));
	}

	public void logStartMethod(long startTime, Map<String, Object> parameterMap) {
		// Timestamp, Class, Method, Start Time, Parameters List + Value.
		String[] args = new String[3];
		args[0] = getLoggingMethod();
		SimpleDateFormat timeFormatter = new SimpleDateFormat(DATE_FORMAT);
		args[1] = timeFormatter.format(new Date(startTime));
		try {
			StringBuffer buffer = new StringBuffer();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					Object obj = entry.getValue();
					if (obj != null) {
						if (obj instanceof Collection) {
							Collection<Object> objList = (Collection<Object>) obj;
							int i = 1;
							for (Object aux : objList) {
								buffer.append(String.format(" %s" + (i++) + " = %s ", entry.getKey(), "" + aux.toString()));
							}
						} else {
							buffer.append(String.format(" %s = %s ", entry.getKey(), "" + obj));
						}
					}
				}
			}
			args[2] = buffer.toString();
			this.logger.log(Level.INFO, String.format("%s start - startTime:%s - %s", (Object[]) args));
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logEndMethod(long startTime, long endTime) {
		// Timestamp, Class, Method, Start Time, End Time, Processing Time,
		// Result.
		String args[] = new String[4];
		args[0] = getLoggingMethod();
		SimpleDateFormat timeFormatter = new SimpleDateFormat(DATE_FORMAT);
		args[1] = timeFormatter.format(new Date(startTime));
		args[2] = timeFormatter.format(new Date(endTime));
		args[3] = Long.toString(endTime - startTime);
		this.logger.log(Level.INFO, String.format("%s end - startTime:%s - endTime:%s - processingTime(msec):%s", (Object[]) args));
	}

	public void logEndMethod(long startTime, long endTime, Object result) {
		// Timestamp, Class, Method, Start Time, End Time, Processing Time,
		// Result.
		String args[] = new String[5];
		args[0] = getLoggingMethod();
		SimpleDateFormat timeFormatter = new SimpleDateFormat(DATE_FORMAT);
		args[1] = timeFormatter.format(new Date(startTime));
		args[2] = timeFormatter.format(new Date(endTime));
		args[3] = Long.toString(endTime - startTime);
		if (result != null) {
			args[4] = result.toString();
		} else {
			args[4] = "";
		}
		try {
			this.logger.log(Level.INFO, String.format("%s end - startTime:%s - endTime:%s - processingTime(msec):%s - %s", (Object[]) args));
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logInfo(String message, Object... args) {
		log(Level.INFO, getLoggingMethod(), message, args);
	}

	public void logDebug(String message, Object... args) {
		// Timestamp, Class, Method, Debug information.
		log(Level.DEBUG, getLoggingMethod(), message, args);
	}

	public void logError(String message, Object... args) {
		log(Level.ERROR, getLoggingMethod(), message, args);
	}

	public void logWarning(String message, Object... args) {
		log(Level.WARN, getLoggingMethod(), message, args);
	}

	public void logFatal(String message, Object... args) {
		log(Level.FATAL, getLoggingMethod(), message, args);
	}

	public void logAll(String message, Object... args) {
		log(Level.ALL, getLoggingMethod(), message, args);
	}

	private void log(Level level, String loggingMethod, String message, Object... args) {
		try {
			if (this.logger.isEnabledFor(level)) {
				this.logger.log(level, loggingMethod + SEPARATOR + String.format(message, args));
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public void logError(Exception ex) {
		log(Level.ERROR, getLoggingMethod(), ex.getMessage(), ex);
	}

	public void logError(String message, Exception ex) {
		log(Level.ERROR, getLoggingMethod(), message, ex);
	}

	private void log(Level level, String loggingMethod, String message, Exception ex) {
		try {
			if (this.logger.isEnabledFor(level)) {
				this.logger.log(level, loggingMethod + SEPARATOR + message, ex);
			}
		} catch (Exception e) {
			rootLogger.error(e);
		}
	}

	public static String getLoggingMethod() {
		StackTraceElement stackTraceElements[] = (new Exception()).getStackTrace();
		StackTraceElement caller = stackTraceElements[2];
		return caller.getMethodName();
	}

}
