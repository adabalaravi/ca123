package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpTokenDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;
	
	private String platform;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
