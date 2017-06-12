package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.party.AccountListRequest;
import com.accenture.sdp.csmfe.webservices.request.party.CredentialInfoRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SiteListRequest;
import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupIdListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreatePartyAndSubscriptionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6738941259098558808L;

	private String partyName;
	private String partyDescription;
	private String externalId;
	private String partyProfile;
	private CredentialInfoRequest credential;
	private SiteListRequest sites;
	private AccountListRequest accounts;
	private PartyGroupIdListRequest partyGroups;
	
	
	private CreateSubscriptionTogetherPartyRequest subscription;


	public CreatePartyAndSubscriptionRequest() {
		super();
		subscription = new CreateSubscriptionTogetherPartyRequest();
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


	public PartyGroupIdListRequest getPartyGroups() {
		return partyGroups;
	}


	public void setPartyGroups(PartyGroupIdListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}


	public CreateSubscriptionTogetherPartyRequest getSubscription() {
		return subscription;
	}


	public void setSubscription(CreateSubscriptionTogetherPartyRequest subscription) {
		this.subscription = subscription;
	}
	
	
}
