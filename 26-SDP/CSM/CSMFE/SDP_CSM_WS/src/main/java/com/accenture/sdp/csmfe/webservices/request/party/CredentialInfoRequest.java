package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.credential.SecretQuestionListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CredentialInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4975176994703078650L;

	private String username;
	private String password;
	private String usernameExternalId;
	private SecretQuestionListRequest secretQuestions;
	
	public CredentialInfoRequest() {
		secretQuestions = new SecretQuestionListRequest();
	}

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

	public String getUsernameExternalId() {
		return usernameExternalId;
	}

	public void setUsernameExternalId(String usernameExternalId) {
		this.usernameExternalId = usernameExternalId;
	}

	public SecretQuestionListRequest getSecretQuestions() {
		return secretQuestions;
	}

	public void setSecretQuestions(SecretQuestionListRequest secretQuestions) {
		this.secretQuestions = secretQuestions;
	}
	
	
}
