package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupIdListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateParentPartyCompleteRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026766102341157577L;

	private String partyName;
	private String partyDescription;
	private String externalId;
	private String partyProfile;

	private PartyGroupIdListRequest partyGroups;
	private CredentialInfoRequest credential;
	private SiteListRequest sites;
	private AccountListRequest accounts;

	public CreateParentPartyCompleteRequest() {
		super();
		credential = new CredentialInfoRequest();
		sites = new SiteListRequest();
		accounts = new AccountListRequest();
		partyGroups = new PartyGroupIdListRequest();
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

	public PartyGroupIdListRequest getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupIdListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}

	public CredentialInfoRequest getCredential() {
		return credential;
	}

	public void setCredential(CredentialInfoRequest credential) {
		this.credential = credential;
	}

	public SiteListRequest getSites() {
		return sites;
	}

	public void setSites(SiteListRequest sites) {
		this.sites = sites;
	}

	public AccountListRequest getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountListRequest accounts) {
		this.accounts = accounts;
	}

}
