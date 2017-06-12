package com.accenture.ams.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;


public class ReloadDS extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String PARAMETER_TENANT_NAME = "tenantName";
	private static final String PARAMETER_PROPERTIES_PATH = "AMS_PROPERTIES";
	
	private static final String TENANT_PROPERTIES_FILE = "/tenant.properties";
	
	private static final String MISSING_PARAMETER_EXCEPTION = "Missing required parameter ";
	
	private static final String RESPONSE_OK = "<?xml version=\"1.0\"?><result><code>200</code><message>DB CONFIGURATION RELOADING</message><desc>#DESC#</desc></result>";
	private static final String RESPONSE_MISS = "<?xml version=\"1.0\"?><result><code>201</code><message>MISSING PARAMETER...SEE LOG FILE FOR MORE DETAILS</message><desc>#DESC#</desc></result>";	
	private static final String RESPONSE_KO = "<?xml version=\"1.0\"?><result><code>202</code><message>ERROR WHILE RELOADING DB CONFIGURATION...SEE LOG FILE FOR MORE DETAILS</message><desc>#DESC#</desc></result>";
	private static final String RESPONSE_ERR = "<?xml version=\"1.0\"?><result><code>203</code><message>SERVLET ERROR...SEE LOG FILE FOR MORE DETAILS</message><desc>#DESC#</desc></result>";
	
	public void init() throws ServletException {
		LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | Startup...");
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
		LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | Perfoming Action...");
		String tenantName = request.getParameter(PARAMETER_TENANT_NAME);
		
		if (tenantName==null || tenantName.isEmpty() ){
			sendResponse(response, RESPONSE_ERR, "Missing TENANT_NAME");
			return;
		}
		
		LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | Tenant to Reload = "+tenantName);
		
		Map<String, String> menv = System.getenv();
		String realPath = menv.get(PARAMETER_PROPERTIES_PATH);
		
		if (realPath==null || realPath.isEmpty() ){
			sendResponse(response, RESPONSE_ERR, "Cannot read system properties "+PARAMETER_PROPERTIES_PATH);
			return;
		}
		
		LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | ConfigFile Path = "+realPath);
		
		Properties props = new Properties();
		FileInputStream fileInputStream = null;
		String tenantConfigFullPath = realPath+TENANT_PROPERTIES_FILE;
		String hibernateConfigForTenat = "";
		
		try {
			fileInputStream = new FileInputStream(tenantConfigFullPath);
			
			props.load(fileInputStream);
			hibernateConfigForTenat = props.getProperty(tenantName);
			LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | Tenant ConfigFile = "+hibernateConfigForTenat);
			
			if (hibernateConfigForTenat==null || hibernateConfigForTenat.isEmpty() ){
				sendResponse(response, RESPONSE_ERR, "Cannot load hibernate config for tenant : "+tenantName);
				return;
			}
			
			if ( HibernateUtil.getInstance().reloadDbForTenant(tenantName, hibernateConfigForTenat) )
				sendResponse(response, RESPONSE_OK, "Reload success");
			else
				sendResponse(response, RESPONSE_KO, "Generic error during reloading...See log File");
			
			LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | end");
		}
		catch(IOException e){
			LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | Exception : "+e.getMessage());
			sendResponse(response, RESPONSE_ERR, "Generic Excpetion during execution : "+e.getMessage());
		}
	}
	
	private void sendResponse(HttpServletResponse response, String textResponse, String desc){
		response.setContentType("text/xml; charset=utf-8");
		ServletOutputStream sout=null;
		try {
			sout = response.getOutputStream();
			textResponse = textResponse.replace("#DESC#", desc);
			sout.print(textResponse);
			sout.flush();
			sout.close();
		}
		catch (IOException e) {
			LogUtil.writeCommonLog("INFO", ReloadDS.class, "INTERNAL", "Service | Exception : "+e.getMessage());
			e.printStackTrace();
		}		
	}


}
