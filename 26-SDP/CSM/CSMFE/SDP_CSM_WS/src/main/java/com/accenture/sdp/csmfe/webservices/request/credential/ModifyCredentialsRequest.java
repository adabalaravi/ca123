package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyCredentialsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9001257639884931007L;

	private String username;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String externalId;

	private SecretQuestionListModifyRequest secretQuestions;

	public ModifyCredentialsRequest() {
		secretQuestions = new SecretQuestionListModifyRequest();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public SecretQuestionListModifyRequest getSecretQuestions() {
		return secretQuestions;
	}

	public void setSecretQuestions(SecretQuestionListModifyRequest secretQuestions) {
		this.secretQuestions = secretQuestions;
	}

}
