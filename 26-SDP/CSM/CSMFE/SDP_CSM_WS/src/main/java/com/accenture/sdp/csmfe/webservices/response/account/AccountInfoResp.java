package com.accenture.sdp.csmfe.webservices.response.account;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AccountInfoResp extends BaseInfoResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303970072759252974L;
	
	private Long accountId;
	private String accountName;
	private String accountDescription;
	private Long statusId;
	private String status;
	private boolean isDefaultAccount;
	private Long partyId;	
	private String partyName;	
	private Long parentPartyId;
	private String partyParentName;
	private Long siteId;
	private String siteName;
	private String externalId;
	private String accountProfile;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
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
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
