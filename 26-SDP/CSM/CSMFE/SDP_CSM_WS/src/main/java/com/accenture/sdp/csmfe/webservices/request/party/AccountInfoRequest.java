package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class AccountInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3766252734842506145L;

	private String accountName;
	private String accountDescription;
	private boolean isDefaultAccount;
	private String accountSiteName;
	private String accountExternalId;
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

	public String getAccountSiteName() {
		return accountSiteName;
	}

	public void setAccountSiteName(String accountSiteName) {
		this.accountSiteName = accountSiteName;
	}

	public String getAccountExternalId() {
		return accountExternalId;
	}

	public void setAccountExternalId(String accountExternalId) {
		this.accountExternalId = accountExternalId;
	}

	public String getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(String accountProfile) {
		this.accountProfile = accountProfile;
	}

}
