package com.accenture.sdp.csmfe.webservices.request.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateAccountRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accountName;
	private String accountDescription;
	private boolean isDefaultAccount;
	private Long partyId;
	private Long siteId;
	private String externalId;
	private String accountProfile;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	@XmlElement(defaultValue = "false")
	public boolean isDefaultAccount() {
		return isDefaultAccount;
	}

	public void setDefaultAccount(boolean isDefaultAccount) {
		this.isDefaultAccount = isDefaultAccount;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(String accountProfile) {
		this.accountProfile = accountProfile;
	}

}
