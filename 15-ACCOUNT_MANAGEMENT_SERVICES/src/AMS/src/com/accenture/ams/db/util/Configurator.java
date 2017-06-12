package com.accenture.ams.db.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.accenture.ams.db.bean.Platform;
import com.accenture.ams.db.bean.SysMessages;
import com.accenture.ams.db.bean.SysParameter;
import com.accenture.ams.db.bean.SysParameterGroup;
import com.accenture.ams.db.dao.SysParamDAO;
import com.accenture.ams.db.framework.SystemMessages;
import com.accenture.ams.db.framework.WSConfigurator;
import com.accenture.ams.configurator.ConfiguratorLastDateUpdate;
import com.accenture.ams.configurator.FileWatcher;
import com.accenture.ams.configurator.ConfigFileDescriptor;

public class Configurator {

	private ConfigFileDescriptor configFileDescriptorTenant = null;
	// private FileWatcher dataModel;
	// private String fcPath = null;
	public static String PATH_CLASS_SYSMESSAGES = "com.accenture.avs.be.db.framework.SystemMessages";
	public static String PATH_CLASS_WSC = "com.accenture.avs.be.db.util.WSConfigurator";
	public static String GROUP_WBS = "wbservices";
	public static Logger log = null;

	private static HashMap<String, SystemMessages> systemMessagesMap = new HashMap<String, SystemMessages>();
	private static HashMap<String, WSConfigurator> wsConfiguratorMap = new HashMap<String, WSConfigurator>();

	public void init(String tenantPath) throws Exception {
		Properties propsTenant = new Properties();
		FileInputStream fileInputStreamTenant = null;

		LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "TENANT PROPERTIES: " + tenantPath);
		log = Logger.getLogger(Configurator.class);
		try {			
			if ( new File(tenantPath).exists() ){
				fileInputStreamTenant = new FileInputStream(tenantPath);
				propsTenant.load(fileInputStreamTenant);
				configFileDescriptorTenant = new ConfigFileDescriptor();
				configFileDescriptorTenant.setName(this.getClass().getName());
				configFileDescriptorTenant.setFcPath(tenantPath);
				File f2 = new File(configFileDescriptorTenant.getFcPath());
				configFileDescriptorTenant.setLastDateModified(new Date(f2.lastModified()));
				configFileDescriptorTenant.setProperties(propsTenant);
			}
			else{
				LogUtil.writeCommonLog("WARNING", Configurator.class, "INIT", "********Tenant File properties not exist");
			}

		} catch (IOException e) {
			LogUtil.writeErrorLog(Configurator.class, " init: ", e);
		} 
		finally {
			if (fileInputStreamTenant != null)
				try {
					fileInputStreamTenant.close();
				} catch (IOException e) {
					LogUtil.writeErrorLog(Configurator.class, " init: ", e);
				}
		}
	}

	public ConfigFileDescriptor getConfigFileDescriptor() {
		return this.configFileDescriptorTenant;
	}

	public static void updateAll() throws Exception {

		Set list = HibernateUtil.getInstance().getConfiguredTenant();
		Iterator iter = list.iterator();

		while (iter.hasNext()) {
			String tenant = (String) iter.next();
			List<SysParameterGroup> paramUpdated = null;
			LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "3065-Update parameter from table");
			List<SysParameter> parametri = null;
			SysParameter paramForList = null;
			HashMap<String, SysParameter> hmUpWSC = new HashMap<String, SysParameter>();

			try {
				parametri = SysParamDAO.getGroupUpdatedParam(ConfiguratorLastDateUpdate.dateLastUpdateSysParameters, tenant);
			} catch (Exception e) {
				LogUtil.writeErrorLog(Configurator.class, " UpdateAll: ", e);
			}
			if (parametri != null && parametri.size() != 0) {
				ConfiguratorLastDateUpdate.dateLastUpdateSysParameters = getCurrentTime(tenant);

				Iterator<SysParameter> itrParam = parametri.iterator();
				LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "3066-N? of param to update: " + parametri.size());
				while (itrParam.hasNext()) {
					paramForList = (SysParameter) itrParam.next();
					// popolazione maphash
					if (paramForList.getParamGroup().equalsIgnoreCase("wbservices"))
						hmUpWSC.put(paramForList.getParamLabel(), paramForList);
				}
				if (hmUpWSC.size() != 0) {
					WSConfigurator aWS = (WSConfigurator) updateGroup(hmUpWSC, PATH_CLASS_WSC, GROUP_WBS);
					wsConfiguratorMap.put(tenant, aWS);
				}
			}
		}
	}


	public static SystemMessages getSystemMessage(String tenantName) {
		return systemMessagesMap.get(tenantName);
	}

	public static Object updateGroup(HashMap<String, SysParameter> hmUpDate, String NameClas, String Group) {
		int i = 0;
		Class c = null;
		Constructor costruttore = null;
		Object mapObj = null;		
		java.lang.reflect.Field[] f = null;
		try {
			Set<String> keySet = hmUpDate.keySet();
			c = Class.forName(NameClas);
			costruttore = c.getConstructor(new Class[] {});
			mapObj = costruttore.newInstance(new Object[] {});			
			f = c.getFields();
			int parameterUpdated = 0;
			for (i = 0; i < f.length; i++)
				for (String key : keySet) {
					if (f[i].getName().equalsIgnoreCase(hmUpDate.get(key).getParamLabel())) {
						if (hmUpDate.get(f[i].getName()).getParamType().equalsIgnoreCase("STRING")) {
							f[i].set(mapObj, hmUpDate.get(f[i].getName()).getParamValue());
							parameterUpdated++;
						}
						if (hmUpDate.get(f[i].getName()).getParamType().equalsIgnoreCase("INTEGER")) {
							f[i].setInt(mapObj, Integer.parseInt(hmUpDate.get(f[i].getName()).getParamValue()));
							parameterUpdated++;
						}
						if (hmUpDate.get(f[i].getName()).getParamType().equalsIgnoreCase("LONG")) {
							f[i].setLong(mapObj, Long.parseLong(hmUpDate.get(f[i].getName()).getParamValue()));
							parameterUpdated++;
						}
						break;
					}
				}

			if (hmUpDate.size() > 0 && parameterUpdated == 0) {

				for (String key : hmUpDate.keySet()) {
					LogUtil.writeCommonLog("INFO", Configurator.class, "INTERNAL", "LABEL parameter on DB: " + hmUpDate.get(key).getParamLabel() + " doesn't match with parameter CLASS: ");
				}

			}

			LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", NameClas + " N° parameters: " + parameterUpdated + ", " + "3067-Parameters successfully updated from DB ");
		} catch (Exception e) {
			LogUtil.writeErrorLog(Configurator.class, "3068-Error on class" + ": " + NameClas + " PARAM: " + f[i].getName() + " " + "3069-not found in DB", e);
		}
		return mapObj;
	}

	public static void initAll() throws ConfigurationControllerException, Exception {
		LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "3070-Init parameter from table");
		// long startTime = System.currentTimeMillis();
		// long startTimeMs=Long.valueOf(startTime);
		// DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		// ConfiguratorLastDateUpdate.dateLastUpdate=dateFormat.format(startTimeMs);
		Set list = HibernateUtil.getInstance().getConfiguredTenant();
		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			String tenantId = (String) iter.next();
			ConfiguratorLastDateUpdate.dateLastUpdateSysParameters = getCurrentTime(tenantId);
			List<SysParameter> parametri = null;
			SysParameter paramForList = null;
			HashMap<String, SysParameter> hmInitWSC = new HashMap<String, SysParameter>();

			try {
				parametri = SysParamDAO.getInitAllParameters(tenantId);
			} catch (Exception e) {
				LogUtil.writeErrorLog(Configurator.class, " InitAll: ", e);
			}
			if (parametri != null) {
				Iterator<SysParameter> itr = parametri.iterator();
				while (itr.hasNext()) {
					// popolazione maphash
					paramForList = (SysParameter) itr.next();

					if (paramForList.getParamGroup().equalsIgnoreCase("wbservices"))
						hmInitWSC.put(paramForList.getParamLabel(), paramForList);
				}
			}

			if (hmInitWSC.size() != 0) {
				WSConfigurator aWS = (WSConfigurator) initGroup(hmInitWSC, PATH_CLASS_WSC, WSConfigurator.class);
				wsConfiguratorMap.put(tenantId, aWS);
			}
		}
	}

	public static Object initGroup(HashMap<String, SysParameter> hmInitDate, String NameClas, Class objClass) throws ConfigurationControllerException {
		int i = 0;
		Class c = null;
		Constructor costruttore = null;
		Object mapObj = null;
		java.lang.reflect.Field[] f = null;
		try {
			c = Class.forName(NameClas);
			costruttore = c.getConstructor(new Class[] {});
			mapObj = costruttore.newInstance(new Object[] {});
			f = c.getFields();
			boolean find;
			Set<String> keySet = hmInitDate.keySet();
			if (hmInitDate.size() != f.length) {
				// System.out.println("there is a mismatch between DB parameters and Class "+NameClas);
				log.error("there is a mismatch between DB parameters and Class " + NameClas);
				log.error("parameters db " + hmInitDate.size());
				log.error("parameters Class " + f.length);

				for (int j = 0; j < f.length; j++) {
					find = false;
					for (String key : keySet) {
						if (f[j].getName().equalsIgnoreCase(hmInitDate.get(key).getParamLabel())) {
							find = true;
							break;
						}
					}
					if (!find) {
						log.error("missing parameter on DB= " + f[j].getName());
						throw new ConfigurationControllerException("missing parameter on DB= " + f[j].getName());
					}

				}

				for (String key : keySet) {
					find = false;
					for (int j = 0; j < f.length; j++) {
						if (f[j].getName().equalsIgnoreCase(hmInitDate.get(key).getParamLabel())) {
							find = true;
							break;
						}
					}
					if (!find) {
						log.error("missing parameter on Class= " + hmInitDate.get(key).getParamLabel());
						throw new ConfigurationControllerException("missing parameter on Class= " + hmInitDate.get(key).getParamLabel());
					}
				}

			} else {
				for (i = 0; i < f.length; i++) {

					if (hmInitDate.get(f[i].getName()).getParamType().equalsIgnoreCase("STRING"))
						f[i].set(mapObj, hmInitDate.get(f[i].getName()).getParamValue());
					if (hmInitDate.get(f[i].getName()).getParamType().equalsIgnoreCase("INTEGER"))
						f[i].setInt(mapObj, Integer.parseInt(hmInitDate.get(f[i].getName()).getParamValue()));
					if (hmInitDate.get(f[i].getName()).getParamType().equalsIgnoreCase("LONG"))
						f[i].setLong(mapObj, Long.parseLong(hmInitDate.get(f[i].getName()).getParamValue()));
				}
				log.info(NameClas + "Parameters successfully init from DB");
			}
		} catch (Exception e) {

			if (e instanceof ConfigurationControllerException)
				throw (ConfigurationControllerException) e;
			e.printStackTrace();
			LogUtil.writeErrorLog(Configurator.class, "3068-Error on class" + ": " + NameClas + " PARAM: " + f[i].getName() + " " + "3069-not found in DB", e);
			throw new ConfigurationControllerException("3068-Error on class" + ": " + NameClas + " PARAM: " + f[i].getName() + " " + "3069-not found in DB");
		}
		return mapObj;
	}

	public static void initMessages() throws ConfigurationControllerException, Exception {
		HashMap<String, SysMessages> hmInitMessages = new HashMap<String, SysMessages>();
		List<SysMessages> lstMessages = new ArrayList<SysMessages>();
		SysMessages paramMessage = null;

		Set list = HibernateUtil.getInstance().getConfiguredTenant();
		Iterator iter = list.iterator();

		while (iter.hasNext()) {

			String tenantId = (String) iter.next();
			ConfiguratorLastDateUpdate.dateLastUpdateSysMessages = getCurrentTime(tenantId);
			try {
				lstMessages = SysParamDAO.getMessagesFromLanguage("", null, tenantId);
			} catch (Exception e) {
				LogUtil.writeErrorLog(Configurator.class, "300-GENERIC ERROR", e);
			}
			if (lstMessages != null) {

				Iterator<SysMessages> itr = lstMessages.iterator();
				while (itr.hasNext()) {
					paramMessage = (SysMessages) itr.next();
					hmInitMessages.put(paramMessage.getCompId().getMessageKey(), paramMessage);
				}
			}
			if (hmInitMessages.size() != 0) {
				SystemMessages sysMess = (SystemMessages) initGroupMessages(hmInitMessages, PATH_CLASS_SYSMESSAGES, SystemMessages.class);
				systemMessagesMap.put(tenantId, sysMess);
			}
		}
	}



	public static Object initGroupMessages(HashMap<String, SysMessages> hmInitDate, String NameClas, Class className) throws ConfigurationControllerException {
		int i = 0;
		Class c = null;
		Constructor costruttore = null;
		Object mapObj = null;
		java.lang.reflect.Field[] field = null;
		try {
			c = Class.forName(NameClas);
			costruttore = c.getConstructor(new Class[] {});
			mapObj = costruttore.newInstance(new Object[] {});
			field = c.getFields();
			boolean find;
			Set<String> keySet = hmInitDate.keySet();
			if (hmInitDate.size() != field.length) {

				log.error("there is a mismatch between DB messages and Class " + NameClas);
				log.error("message db " + hmInitDate.size());
				log.error("message Class " + field.length);

				for (int j = 0; j < field.length; j++) {
					find = false;
					for (String key : keySet) {
						if (field[j].getName().equalsIgnoreCase(hmInitDate.get(key).getCompId().getMessageKey())) {
							find = true;
							break;
						}
					}
					if (!find) {
						log.error("missing message on DB= " + field[j].getName());
						throw new ConfigurationControllerException("missing message on DB= " + field[j].getName());
					}
				}

				for (String key : keySet) {
					find = false;
					for (int j = 0; j < field.length; j++) {
						if (field[j].getName().equalsIgnoreCase(hmInitDate.get(key).getCompId().getMessageKey())) {
							find = true;
							break;
						}
					}
					if (!find) {
						log.error("missing message on Class= " + hmInitDate.get(key).getCompId().getMessageKey());
						throw new ConfigurationControllerException("missing message on Class= " + hmInitDate.get(key).getCompId().getMessageKey());
					}
				}
			} else {
				for (i = 0; i < field.length; i++) {
					field[i].set(mapObj, hmInitDate.get(field[i].getName()).getMessageCode() + "#COD#" + hmInitDate.get(field[i].getName()).getMessageText());
				}
				LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", NameClas + " " + "3073-Messages successfully init from DB");
			}
		} catch (Exception e) {

			if (e instanceof ConfigurationControllerException)
				throw (ConfigurationControllerException) e;

			LogUtil.writeErrorLog(Configurator.class, "3068-Error on class" + ": " + NameClas + " MESSAGE: " + field[i].getName() + " " + "3069-not found in DB", e);
			throw new ConfigurationControllerException("3068-Error on class" + ": " + NameClas + " MESSAGE: " + field[i].getName() + " " + "3069-not found in DB");
		}
		return mapObj;
	}

	public static void updateMessages() throws Exception {

		LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "3072-Update messages from table ");
		List<SysMessages> parametri = null;
		SysMessages paramMessage = null;
		HashMap<String, SysMessages> hmUpMessages = new HashMap<String, SysMessages>();

		Set list = HibernateUtil.getInstance().getConfiguredTenant();
		Iterator iter = list.iterator();

		while (iter.hasNext()) {
			String tenantId = (String) iter.next();
			try {
				parametri = SysParamDAO.getMessagesFromLanguage("", ConfiguratorLastDateUpdate.dateLastUpdateSysMessages, tenantId);
			} catch (Exception e) {
				LogUtil.writeErrorLog(Configurator.class, "300-GENERIC ERROR", e);
			}

			if (parametri != null && parametri.size() != 0) {
				// long startTime = System.currentTimeMillis();
				// long startTimeMs=Long.valueOf(startTime);
				// DateFormat dateFormat = new
				// SimpleDateFormat("yyyyMMddHHmmss");
				// ConfiguratorLastDateUpdate.dateLastUpdate=dateFormat.format(startTimeMs);

				ConfiguratorLastDateUpdate.dateLastUpdateSysMessages = getCurrentTime(tenantId);

				Iterator<SysMessages> itrParam = parametri.iterator();
				LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "3066-N? of param to update: " + parametri.size());
				while (itrParam.hasNext()) {
					paramMessage = (SysMessages) itrParam.next();
					hmUpMessages.put(paramMessage.getCompId().getMessageKey(), paramMessage);
				}
				// if (hmUpMessages.size() != 0)
				// updateGroupMessages(hmUpMessages, PATH_CLASS_SYSMESSAGES);
				if (hmUpMessages.size() != 0) {
					SystemMessages sysMess = (SystemMessages) updateGroupMessages(hmUpMessages, PATH_CLASS_SYSMESSAGES);
					systemMessagesMap.put(tenantId, sysMess);
				}
			}
		}
	}

	public static Object updateGroupMessages(HashMap<String, SysMessages> hmUpDate, String NameClas) {
		int i = 0;
		Class c = null;
		Constructor costruttore = null;
		Object mapObj = null;
		java.lang.reflect.Field[] f = null;
		try {
			Set<String> keySet = hmUpDate.keySet();
			c = Class.forName(NameClas);
			costruttore = c.getConstructor(new Class[] {});
			mapObj = costruttore.newInstance(new Object[] {});
			f = c.getFields();
			for (i = 0; i < f.length; i++)
				for (String key : keySet) {
					if (f[i].getName().equalsIgnoreCase(hmUpDate.get(key).getCompId().getMessageKey())) {
						f[i].set(mapObj, hmUpDate.get(f[i].getName()).getMessageCode() + "#COD#" + hmUpDate.get(f[i].getName()).getMessageText());
						break;
					}
				}
			LogUtil.writeCommonLog("DEBUG", Configurator.class, "INTERNAL", "Messages successfully updated from DB");
		} catch (Exception e) {
			LogUtil.writeErrorLog(Configurator.class, "3068-Error on class" + ": " + NameClas + " MESSAGE: " + f[i].getName() + " " + "3069-not found in DB", e);
		}
		return mapObj;
	}

	private static String getCurrentTime(String tenantId) throws Exception {

		// Prendo data e ora attuali dal db in quanto quella di sistema potrebbe
		// essere diversa (macchina DB != macchina BE)
		String sysDate = SysParamDAO.getSysDate(tenantId);

		return sysDate;
	}

}
