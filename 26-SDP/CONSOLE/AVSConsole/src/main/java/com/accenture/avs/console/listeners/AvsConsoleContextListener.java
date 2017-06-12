package com.accenture.avs.console.listeners;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.accenture.avs.console.common.LoggerWrapper;

public class AvsConsoleContextListener implements ServletContextListener, Serializable {

	private static final long serialVersionUID = 1L;
	private transient LoggerWrapper log;

	public void contextInitialized(ServletContextEvent event) {
		try {
			System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
			LoggerWrapper.initialize();
			log = new LoggerWrapper(AvsConsoleContextListener.class);
			log.logInfo("AVS CONSOLE STARTUP");
		} catch (Exception e) {
			e.printStackTrace();
			if (log != null) {
				log.logError(e);
			}
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		log.logInfo("AVS CONSOLE SHOUTDOWN");
	}

}
