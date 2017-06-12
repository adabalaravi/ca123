package com.accenture.sdp.csmcc.listners;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.accenture.sdp.csmcc.common.LoggerWrapper;

public class CatalogueConsoleContextListener implements ServletContextListener, Serializable{

	private static final long serialVersionUID = 1L;
	private LoggerWrapper log;
	
    public void contextInitialized(ServletContextEvent event) {
    	try{
    		LoggerWrapper.initialize();
    		log = new LoggerWrapper(CatalogueConsoleContextListener.class);
    		log.logInfo("APPLICATION STARTUP");
    	} catch (Exception e) {
    		log.logException(e.getMessage(), e);
    	}
       
    }

    public void contextDestroyed(ServletContextEvent event) {
		log.logInfo("APPLICATION SHOTDOWN");
	       
    }

}
