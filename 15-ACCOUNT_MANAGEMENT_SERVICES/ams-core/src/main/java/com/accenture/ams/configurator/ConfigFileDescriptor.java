package com.accenture.ams.configurator;

import java.util.Date;

public class ConfigFileDescriptor {
	private java.util.Properties properties = null;
	private String fcPath=null;
	private Date lastDateModified=null;
	private String name="";
	public void setProperties(java.util.Properties properties) {
		this.properties = properties;
	}
	public java.util.Properties getProperties() {
		return properties;
	}
	public void setFcPath(String fcPath) {
		this.fcPath = fcPath;
	}
	public String getFcPath() {
		return fcPath;
	}
	public void setLastDateModified(Date lastDateModified) {
		this.lastDateModified = lastDateModified;
	}
	public Date getLastDateModified() {
		return lastDateModified;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
}
