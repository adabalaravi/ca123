package com.accenture.sdp.csmac.listners;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.accenture.sdp.csmac.common.LoggerWrapper;

public class AssuranceConsoleContextListener implements ServletContextListener, Serializable {

	private static final long serialVersionUID = 1L;
	private transient LoggerWrapper log;

	public void contextInitialized(ServletContextEvent event) {
		try {
			LoggerWrapper.initialize();
			log = new LoggerWrapper(AssuranceConsoleContextListener.class);
			log.logInfo("AVS ASSURANCE CONSOLE STARTUP");
		} catch (Exception e) {
			e.printStackTrace();
			if (log != null) {
				log.logError(e);
			}
		}

	}

	public void contextDestroyed(ServletContextEvent event) {
		log.logInfo("AVS ASSURANCE CONSOLE SHOUTDOWN");
	}

}
