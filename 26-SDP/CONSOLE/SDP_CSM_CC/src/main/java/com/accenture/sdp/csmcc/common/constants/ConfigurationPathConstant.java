package com.accenture.sdp.csmcc.common.constants;

import java.io.File;

interface ConfigurationPathConstant {
	
	// JBOSS 6
	String CONFIGURATION_PATH_JB6 = System.getProperty("jboss.server.home.dir") + File.separator + "conf" + File.separator ;

	// JBOSS 7
	String CONFIGURATION_PATH_JB7 = System.getProperty("jboss.home.dir") + File.separator + "standalone" + File.separator + "conf" + File.separator;

	// tomcat
	String CONFIGURATION_PATH = null;
		
}
