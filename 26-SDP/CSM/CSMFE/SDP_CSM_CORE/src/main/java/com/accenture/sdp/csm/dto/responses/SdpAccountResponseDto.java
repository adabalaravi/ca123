package com.accenture.sdp.csm.dto.responses;

public class SdpAccountResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1804641316475641471L;

	private Long accountId;
	private String accountName;
	
	private String accountDescription;

	private String status;
	
	private boolean isDefaulAccount;
	
	private Long partyId;
	
	private String partyName;
	
	private String parentPartyName;
	
	private Long siteId;

	private String siteName;
	
	private String externalId;
	
	private String accountProfile;
	
	// only returned by searchAccountsBySubscription
	private String accountType;

	public SdpAccountResponseDto() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isDefaulAccount() {
		return isDefaulAccount;
	}

	public void setDefaulAccount(boolean isDefaulAccount) {
		this.isDefaulAccount = isDefaulAccount;
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

	public String getParentPartyName() {
		return parentPartyName;
	}

	public void setParentPartyName(String parentPartyName) {
		this.parentPartyName = parentPartyName;
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
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
}