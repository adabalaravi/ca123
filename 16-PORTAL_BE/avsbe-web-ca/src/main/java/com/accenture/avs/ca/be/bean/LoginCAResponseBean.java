package com.accenture.avs.ca.be.bean;

import java.io.Serializable;

public class LoginCAResponseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String crmAccountId;
	String sessionToken;
	String sessionSecret;
	
	
	
	public String getCrmAccountId() {
		return crmAccountId;
	}
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public String getSessionSecret() {
		return sessionSecret;
	}
	public void setSessionSecret(String sessionSecret) {
		this.sessionSecret = sessionSecret;
	}
	

}
