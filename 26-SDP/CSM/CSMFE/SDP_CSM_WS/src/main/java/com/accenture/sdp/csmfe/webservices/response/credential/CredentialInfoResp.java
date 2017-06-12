package com.accenture.sdp.csmfe.webservices.response.credential;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CredentialInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7624900491176566420L;

	private String username;
	private Long partyId;
	private String partyName;
	private Long parentPartyId;
	private String partyParentName;
	private Long statusId;
	private String status;
	private String roleName;
	private String externalId;
	
	private SecretQuestionListResp secretQuestions;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Long getParentPartyId() {
		return parentPartyId;
	}

	public void setParentPartyId(Long parentPartyId) {
		this.parentPartyId = parentPartyId;
	}

	public String getPartyParentName() {
		return partyParentName;
	}

	public void setPartyParentName(String partyParentName) {
		this.partyParentName = partyParentName;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public SecretQuestionListResp getSecretQuestions() {
		return secretQuestions;
	}

	public void setSecretQuestions(SecretQuestionListResp secretQuestions) {
		this.secretQuestions = secretQuestions;
	}

	public CredentialInfoResp() {
		super();
		secretQuestions = new SecretQuestionListResp();
	}
}
