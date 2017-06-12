package com.accenture.ams.db.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;






public class SessionUtil {
	private static String LOCAL_SERVER_ID = "-1";

	public void init() {

		Map<String, String> menv = System.getenv();
		String LOCAL_SERVER_ID_STRING = menv.get("LOCAL_SERVER_AVSBE_ID");
		if (LOCAL_SERVER_ID_STRING != null) {
			if (LOCAL_SERVER_ID_STRING.length() > 0) {
				LOCAL_SERVER_ID = LOCAL_SERVER_ID_STRING;
			}// Fine if..
		}// Fine if..
	}

	public static String getLocalServerID() {

		return LOCAL_SERVER_ID;
	}
/*
	public synchronized String getTransactionNumber(HttpSession sessionHttp) {
		String transactioStringa = "";
		String transaction_id = "-1";

		if (sessionHttp.getAttribute("transactionNumber") == null || sessionHttp.getAttribute("transactionNumber").equals("")) {

			AVSController.TRANSACTION_NUMBER = AVSController.TRANSACTION_NUMBER + System.currentTimeMillis() + 1;

			transaction_id = String.valueOf(AVSController.TRANSACTION_NUMBER);
			transactioStringa = LOCAL_SERVER_ID + transaction_id;
			sessionHttp.setAttribute("transactionNumber", String.valueOf(transactioStringa));
			return transactioStringa;
		} else
			return (String) sessionHttp.getAttribute("transactionNumber");
	}
*/
	public String getIpFromHeader(HttpServletRequest request, String tenantId) throws UnknownHostException {

		java.util.Enumeration names = request.getHeaderNames();
		String ip = "";
		String headerValue = "";
		boolean trovato = false;
		int i = 1;
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if (name.equalsIgnoreCase("ClientIP")) {
				headerValue = (String) request.getHeader(name);
				trovato = true;
				ip = request.getHeader(name);
				LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL", "ClientIp founded in header. ClientIP: " + ip);
				// LogUtil.writeCommonLog("DEBUG", SessionUtil.class,
				// "INTERNAL",
				// " | **************************** PARAM HEADER n° "+(i++)+"***************************************");
				// LogUtil.writeCommonLog("DEBUG", SessionUtil.class,
				// "INTERNAL", " | ipFromWebCache | Param from header :"+name
				// +" = "+(String)request.getHeader(name));
				// LogUtil.writeCommonLog("DEBUG", SessionUtil.class,
				// "INTERNAL",
				// " | ********************************************************************************");
			} else {
				headerValue = (String) request.getHeader(name);
				// trovato=true;
				// ip = request.getHeader(name);
				// log.info("ClientIp founded in header. ClientIP: "+ip);
				// LogUtil.writeCommonLog("DEBUG", SessionUtil.class,
				// "INTERNAL",
				// " | **************************** PARAM HEADER n° "+(i++)+"***************************************");
				// LogUtil.writeCommonLog("DEBUG", SessionUtil.class,
				// "INTERNAL", " | ipFromWebCache | Param from header :"+name
				// +" = "+(String)request.getHeader(name));
				// LogUtil.writeCommonLog("DEBUG", SessionUtil.class,
				// "INTERNAL",
				// " | ********************************************************************************");
			}

		}

		if (trovato == false) {
			//InetAddress addr = InetAddress.getLocalHost();
			//byte[] ipAddr = addr.getAddress();
			//String hostname = addr.getHostName();
			//String hostAddress = addr.getHostAddress();
			LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL", "ClientIp parameter not found in header");
			// LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL",
			// " | **************************** PARAM HEADER n° "+(i++)+"***************************************");
			// LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL",
			// " | ipFromWebCache | hostAddress :"+hostAddress);
			// LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL",
			// " | ipFromWebCache | hostname    :"+hostname);
			// LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL",
			// " | ipFromWebCache | ipAddr      :"+ipAddr);
			// LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL",
			// " | ********************************************************************************");

			ip = "";

		}

		return ip;
	}
	public String getAttributeFromHeader(HttpServletRequest request, String attributeName)throws UnknownHostException {

		String attributeValue = null; 
		
		java.util.Enumeration names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			if(name.equalsIgnoreCase(attributeName)){
				attributeValue =(String)request.getHeader(name);

				LogUtil.writeCommonLog("DEBUG", SessionUtil.class, "INTERNAL", " | attributeFromHeader | Attribute name : ["+name+"]; Attribute value: ["+attributeValue+"]");
			}
		}

		return attributeValue;
	}

}
