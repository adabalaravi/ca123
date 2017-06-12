package com.accenture.ams.configurator;

import java.util.TimerTask;

import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;


public class ConfigTenantTask extends TimerTask {

	private String tenantPath;
	
	public ConfigTenantTask(String _tenantPath) {
		tenantPath = _tenantPath;
	}

	@Override
	public void run() {
		try {
			//HibernateUtil.setTenantPath(tenantPath);
			//HibernateUtil.reloadTenantSet();
			showMemoryUsage();
		} catch (Exception e) {
			LogUtil.writeErrorLog(ConfigTenantTask.class, " run: ", e);
		}
	}

	private static String className() {
		return null;
	}

	private void showMemoryUsage() throws Exception {
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long maxMemory = Runtime.getRuntime().maxMemory();

		LogUtil.writeCommonLog("INFO", ConfigTenantTask.class, "INTERNAL", "totalMemory: " + totalMemory + " | freeMemory: " + freeMemory + " | maxMemory: " + maxMemory);

	}

}
