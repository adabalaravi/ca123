package com.accenture.sdp.csmfe.webservices.response.credential;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ResetPasswordResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6993675269269673947L;

	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
