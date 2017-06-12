package com.accenture.ams.configurator;

import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.accenture.ams.db.util.Configurator;
import com.accenture.ams.db.util.LogUtil;


public class ConfigControllerTaskMessages extends TimerTask {
	//ConfigDataParam dataObject = null;
	//Date dataLastMod;
	private static Logger log = null;

	public ConfigControllerTaskMessages() {

	}

	private synchronized void setConfiguration() throws Exception {
		log = Logger.getLogger(ConfigControllerTaskMessages.class.getName());
		Configurator.updateMessages();
	}

	@Override
	public void run() {
		try {
			setConfiguration();
		} catch (Exception e) {
			LogUtil.writeErrorLog(ConfigControllerTaskMessages.class, "ERRORE", e);
		}
	}

	private static String clasName() {
		return null;
	}


}
