/**
 * 
 */
package com.accenture.sdp.csm.dto.requests;

public class SdpAccountRequestDto {
	
	private String accountName;
	private String accountDescription;
	private boolean isDefaultPartyAccount;
	private String accountSiteName;
	private String externalID;
	private String accountProfile;
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the accountDescription
	 */
	public String getAccountDescription() {
		return accountDescription;
	}
	/**
	 * @param accountDescription the accountDescription to set
	 */
	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}
	/**
	 * @return the partySiteName
	 */
	public String getAccountSiteName() {
		return accountSiteName;
	}
	/**
	 * @param accountSiteName the accountSiteName to set
	 */
	public void setAccountSiteName(String accountSiteName) {
		this.accountSiteName = accountSiteName;
	}
	/**
	 * @return the externalID
	 */
	public String getExternalID() {
		return externalID;
	}
	/**
	 * @param externalID the externalID to set
	 */
	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}
	/**
	 * @return the accountProfile
	 */
	public String getAccountProfile() {
		return accountProfile;
	}
	/**
	 * @param accountProfile the accountProfile to set
	 */
	public void setAccountProfile(String accountProfile) {
		this.accountProfile = accountProfile;
	}
	/**
	 * @return the isDefaultPartyAccount
	 */
	public boolean isDefaultPartyAccount() {
		return isDefaultPartyAccount;
	}
	/**
	 * @param isDefaultPartyAccount the isDefaultPartyAccount to set
	 */
	public void setDefaultPartyAccount(boolean isDefaultPartyAccount) {
		this.isDefaultPartyAccount = isDefaultPartyAccount;
	}
	/**
	 * @param accountName
	 * @param accountDescription
	 * @param isDefaultPartyAccount
	 * @param accountSiteName
	 * @param externalID
	 * @param accountProfile
	 */
	public SdpAccountRequestDto(String accountName, String accountDescription,
			boolean isDefaultPartyAccount, String accountSiteName,
			String externalID, String accountProfile) {
		super();
		this.accountName = accountName;
		this.accountDescription = accountDescription;
		this.isDefaultPartyAccount = isDefaultPartyAccount;
		this.accountSiteName = accountSiteName;
		this.externalID = externalID;
		this.accountProfile = accountProfile;
	}
	/**
	 * 
	 */
	public SdpAccountRequestDto() {
		super();
	}
	
	@Override
	public String toString(){
		return  " accountName = "+accountName+
				" accountDescription = "+accountDescription+
				" isDefaultAccount = "+isDefaultPartyAccount+
				" accountSiteName = "+ accountSiteName+
				" accountExternalId = "+ externalID+
				" accountProfile = "+ accountProfile+" ";
	}

}
