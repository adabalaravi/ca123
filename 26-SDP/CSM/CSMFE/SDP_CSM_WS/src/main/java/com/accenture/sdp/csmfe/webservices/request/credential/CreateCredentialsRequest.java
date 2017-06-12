package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateCredentialsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1651085988450867371L;

	private String username;
	private String password;
	private Long partyId;
	private String externalId;
	private String roleName;

	private SecretQuestionListRequest secretQuestions;

	public CreateCredentialsRequest() {
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

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public SecretQuestionListRequest getSecretQuestions() {
		return secretQuestions;
	}

	public void setSecretQuestions(SecretQuestionListRequest secretQuestions) {
		this.secretQuestions = secretQuestions;
	}

}
