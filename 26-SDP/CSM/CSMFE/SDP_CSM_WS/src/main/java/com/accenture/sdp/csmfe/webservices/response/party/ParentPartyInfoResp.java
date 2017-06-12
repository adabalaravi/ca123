package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ParentPartyInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9143418361601702375L;

	private Long partyId;
	private String partyName;
	private String partyDescription;
	private Long statusId;
	private String status;
	private String externalId;
	private String partyProfile;
	private boolean isBlacklisted;
	private boolean isWhitelisted;

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

	public String getPartyDescription() {
		return partyDescription;
	}

	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
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

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPartyProfile() {
		return partyProfile;
	}

	public void setPartyProfile(String partyProfile) {
		this.partyProfile = partyProfile;
	}

	public boolean isBlacklisted() {
		return isBlacklisted;
	}

	public void setBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}

	public boolean isWhitelisted() {
		return isWhitelisted;
	}

	public void setWhitelisted(boolean isWhitelisted) {
		this.isWhitelisted = isWhitelisted;
	}

}
