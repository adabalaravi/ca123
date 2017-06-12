package com.accenture.ams.init;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;

import com.accenture.ams.configurator.ConfigControllerTask;
import com.accenture.ams.configurator.ConfigControllerTaskMessages;
import com.accenture.ams.configurator.ConfigFileDescriptor;
import com.accenture.ams.configurator.ConfigTenantTask;
import com.accenture.ams.db.util.Configurator;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.db.util.SessionUtil;

public class InitAmsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String ENV_BE_PROPERTIES_NAME = "BE_PROPERTIES";
	private static String DEFAULT_PROPERTIES_PATH = "/properties";

	private static String TENANT_PROPERTIES_FILE = "/tenant.properties";
	// TIME TO REFRESH
	private static long TR_MAPPING_PROPERTIES_FILE = 300000; // RELOAD THE
																// PARAMETERS
																// EVERY 5
																// MINUTES
	private static String MAPPING_LOG4J_XML_FILE = "/log4j.xml";
	// TIME TO REFRESH
	private static long TR_MAPPING_LOG4J_PROPERTIES_FILE = 300000;// RELOAD THE
																	// PARAMETERS
																	// EVERY 5
																	// MINUTES
	// private static long TR_MAPPING_PARAM_TABLE=300000;//RELOAD THE PARAMETERS
	// EVERY 5 MINUTES
	// private static long TR_MAPPING_PARAM_MESSAGES=300000;//RELOAD THE
	// PARAMETERS EVERY 5 MINUTES
	private ConfigTenantTask confTenant = null;
	private ConfigControllerTask configControllerC = null;
	private ConfigControllerTaskMessages configControllerMessages = null;

	private Timer timerTenant = null;
	private Timer timerConfig = null;
	private Timer timerConfigTable = null;
	private Timer timerConfigMessages = null;

	public void init() throws ServletException {

		try {
			LogUtil.writeCommonLog("INFO", InitAmsServlet.class, "INTERNAL", "Service | init");
			ENV_BE_PROPERTIES_NAME = this.getInitParameter("ENV_BE_PROPERTIES_NAME");
			Map<String, String> menv = System.getenv();
			String realPath = menv.get(ENV_BE_PROPERTIES_NAME);
			

			TR_MAPPING_LOG4J_PROPERTIES_FILE = Long.parseLong(this.getInitParameter("TR_MAPPING_LOG4J_PROPERTIES_FILE"));
			
			this.initLog4JfromXml(realPath + MAPPING_LOG4J_XML_FILE);

			TENANT_PROPERTIES_FILE = this.getInitParameter("TENANT_PROPERTIES_FILE");

			TR_MAPPING_PROPERTIES_FILE = Long.parseLong(this.getInitParameter("TR_MAPPING_PROPERTIES_FILE"));
			

			// //classe osservata cio� che implementa Observer

			Configurator configurator = new Configurator();
			//LogUtil.writeCommonLog("INFO", InitBackEndServlet.class, "", "realPahProperties="+realPath + MAPPING_PROPERTIES_FILE);
			//LogUtil.writeCommonLog("INFO", InitBackEndServlet.class, "", "tenantPahProperties="+realPath + TENANT_PROPERTIES_FILE);
			configurator.init(realPath + TENANT_PROPERTIES_FILE);
			//configurator.init(realPath + TENANT_PROPERTIES_FILE);
			
			//loading tenantSet
			//HibernateUtil.setTenantPath(realPath+TENANT_PROPERTIES_FILE);
			//if (!HibernateUtil.reloadTenantSet())
			//	throw new ServletException("Fatal Error reloading tenantSet");
			Configurator.initAll();
			Configurator.initMessages();

			SessionUtil sessionUtil = new SessionUtil();
			sessionUtil.init();

			// estende timertask
			configControllerC = new ConfigControllerTask(configurator.getConfigFileDescriptor());
			configControllerMessages = new ConfigControllerTaskMessages();

			timerTenant = new Timer();
			timerConfig = new Timer("Configurator");
			timerConfigTable = new Timer("ConfiguratorTable");//sysparameter
			timerConfigMessages = new Timer("ConfiguratorMessages");
			

			try {
				ConfigTenantTask confTenant = new ConfigTenantTask(realPath + TENANT_PROPERTIES_FILE);

				ConfigControllerTask configControllerC = new ConfigControllerTask( new ConfigFileDescriptor() );

				ConfigControllerTaskMessages configControllerMessages = new ConfigControllerTaskMessages();
				
				timerTenant.scheduleAtFixedRate(confTenant, 2000, TR_MAPPING_PROPERTIES_FILE);

				
				timerConfig.scheduleAtFixedRate(configControllerC, 2000, TR_MAPPING_PROPERTIES_FILE);

				timerConfigMessages.scheduleAtFixedRate(configControllerMessages, 2000, TR_MAPPING_PROPERTIES_FILE);
				
			} catch (Exception e) {
				LogUtil.writeErrorLog(InitAmsServlet.class, "InitBackEndServlet |", e);
			}
		} catch (com.accenture.ams.db.util.ConfigurationControllerException ce) {
			LogUtil.writeErrorLog(InitAmsServlet.class, "InitBackEndServlet | Failure during start-up, cause: ", ce);
			throw new ServletException(ce.getMessage());
		} catch (Exception e) {
			LogUtil.writeErrorLog(InitAmsServlet.class, "InitBackEndServlet |", e);
		}
	}

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		performAction(req, res);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		performAction(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		performAction(req, res);
	}

	private void performAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String xmlOut = "";
		try {
			LogUtil.writeCommonLog("INFO", InitAmsServlet.class, "INTERNAL", "Service | start");
			configControllerC.doSetConfiguration();

			response.setContentType("text/xml; charset=utf-8");
			xmlOut = "<?xml version=\"1.0\"?><result><response>OK</response><message>Done</message></result>";
		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			// PrintWriter pw = new PrintWriter(sw);
			// e.printStackTrace(pw);
			String errorMessage = sw.toString();
			xmlOut = "<?xml version=\"1.0\"?><result><response>KO</response><message>Done</message>" + errorMessage + "</result>";

			LogUtil.writeErrorLog(InitAmsServlet.class, " performAction: ", e);
		} finally {
			ServletOutputStream sout = response.getOutputStream();
			sout.print(xmlOut);
			sout.flush();
			sout.close();
			LogUtil.writeCommonLog("INFO", InitAmsServlet.class, "INTERNAL", "Service | end");
		}
	}

	private void intitLog4J(String log4jpropertyPath) {
		System.out.println("initLog4j-->" + log4jpropertyPath);
		org.apache.log4j.PropertyConfigurator.configureAndWatch(log4jpropertyPath, TR_MAPPING_LOG4J_PROPERTIES_FILE);
	}

	private void initLog4JfromXml(String log4jxmlPath) {
		LogUtil.writeCommonLog("INFO", InitAmsServlet.class, "Log4j Configuration path = " + log4jxmlPath, "Service | initLog4JfromXml");
		org.apache.log4j.xml.DOMConfigurator.configureAndWatch(log4jxmlPath, TR_MAPPING_LOG4J_PROPERTIES_FILE);
	}

	public void destroy() {
		if (configControllerC != null) {
			configControllerC.cancel();
			configControllerC = null;
		}
		if (timerConfig != null) {
			timerConfig.cancel();
			timerConfig.purge();
			timerConfig = null;
		}

		if (configControllerMessages != null) {
			configControllerMessages.cancel();
			configControllerMessages = null;
		}
		if (timerConfigMessages != null) {
			timerConfigMessages.cancel();
			timerConfigMessages.purge();
			timerConfigMessages = null;
		}
		if (timerConfigTable != null) {
			timerConfigTable.cancel();
			timerConfigTable.purge();
			timerConfigTable = null;
		}
		LogManager.shutdown();
	}
}
