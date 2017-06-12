package com.accenture.avs.ca.be.db.bean;

import java.io.Serializable;

public class FetchUserSessionsCA implements Serializable{
	private static final long serialVersionUID = 1L;
		
	private String creation_date;	
	private String deviceType;
	private String sessionid;
	
	
	public String getCreation_date() {
		return creation_date;
	}


	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}


	public String getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}


	public String getSessionid() {
		return sessionid;
	}


	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
}
