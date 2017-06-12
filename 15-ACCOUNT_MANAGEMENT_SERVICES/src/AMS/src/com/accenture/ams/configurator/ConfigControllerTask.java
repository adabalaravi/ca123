package com.accenture.ams.configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;


import com.accenture.ams.configurator.SystemMessages;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.configurator.ConfigFileDescriptor;

public class ConfigControllerTask extends TimerTask {
	private ConfigFileDescriptor dataObject = null;

	public ConfigControllerTask(ConfigFileDescriptor dataObject) {

		this.dataObject = dataObject;
	}

	private synchronized void setConfiguration() throws IOException {

		if (dataObject.getFcPath() != null && !"".equals(dataObject.getFcPath())) {
			LogUtil.writeCommonLog("INFO", ConfigControllerTask.class, "INTERNAL", "dataObject.getFcPath()=" + dataObject.getFcPath());
			File f = new File(dataObject.getFcPath());
			if ((f != null) && (f.exists()) && (f.canRead())) {
				{
					Date dateModified = new Date(f.lastModified());
					if (dataObject.getLastDateModified() == null)
						dataObject.setLastDateModified(dateModified);
					if (!dateModified.equals(dataObject.getLastDateModified())) {
						dataObject.setLastDateModified(dateModified);
						LogUtil.writeCommonLog("INFO", ConfigControllerTask.class, "INTERNAL", "RELOAD CONFIGURATION from file at URL = " + dataObject.getFcPath());
						Properties properties = new Properties();
						FileInputStream inputStream = new FileInputStream(f);
						properties.load(inputStream);
						dataObject.setProperties(properties);
						com.accenture.ams.configurator.FileWatcher.getInstance().setMyData(dataObject);
						inputStream.close();
						f = null;
						inputStream = null;
					} else {
						// Non vogliono vederlo nei log...vogliono vedere solo
						// quando cambia...
						// log.info("File "+dataObject.getFcPath()+" NON CAMBIATO");
						// System.out.println("File "+dataObject.getFcPath()+" NON CAMBIATO");
					}

				}

			}
		}
	}

	private synchronized void setConfigurationTable() {

	}

	@Override
	public void run() {

		try {
			setConfiguration();
		} catch (IOException e) {
			LogUtil.writeErrorLog(ConfigControllerTask.class, " run: ", e);
		}
	}

	public void doSetConfiguration() throws Exception {
		setConfiguration();
	}



}
