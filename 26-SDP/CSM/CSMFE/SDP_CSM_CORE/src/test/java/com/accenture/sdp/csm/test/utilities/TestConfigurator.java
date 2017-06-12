package com.accenture.sdp.csm.test.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.accenture.sdp.csm.metering.MeteringClientFactory;
import com.accenture.sdp.csm.utilities.CodeManager;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public class TestConfigurator {

	private static TestConfigurator instance;

	private String tenantName;

	public static TestConfigurator getInstance() throws Exception {
		if (instance == null) {
			instance = new TestConfigurator();
		}
		return instance;
	}

	private TestConfigurator() throws Exception {
		System.out.println("STARTING UP");
		InputStream testPropFile = null;
		try {
			testPropFile = ClassLoader.getSystemResourceAsStream("CONF/test.properties");
			Properties testProperties = new Properties();
			testProperties.load(testPropFile);
			testPropFile.close();
			System.out.println(Constants.JB6_HOME_DIR + ":  " + testProperties.getProperty(TestConstants.JBOSS_SERVER_HOME));
			System.setProperty(Constants.JB6_HOME_DIR, testProperties.getProperty(TestConstants.JBOSS_SERVER_HOME));

			System.out.println("Tenant Name: " + testProperties.getProperty(TestConstants.TENANT_NAME));
			setTenantName(testProperties.getProperty(TestConstants.TENANT_NAME).toString());
		} finally {
			Utilities.closeStream(testPropFile);
		}
		// apply preconfiguration to log4j : there is no jboss configuration available
		preInitLog4j();
		System.out.println("Initializing LoggerWrapper...");
		LoggerWrapper.initialize();
		System.out.println("LoggerWrapper initialized");
		CodeManager.init();
		System.out.println("CodeManager initialized");
		MeteringClientFactory.getClient().init();
		System.out.println("MeteringClient initialized");
		System.out.println("START UP COMPLETED");
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	private void preInitLog4j() throws IOException {
		System.out.println("preconfiguring log4j for testing pourpose");
		InputStream testPropFile = null;
		try {
			testPropFile = ClassLoader.getSystemResourceAsStream("CONF/log4test.properties");
			Properties properties = new Properties();
			properties.load(testPropFile);
			PropertyConfigurator.configure(properties);
			System.out.println("log4j preconfigured");
		} finally {
			Utilities.closeStream(testPropFile);
		}
	}
}
