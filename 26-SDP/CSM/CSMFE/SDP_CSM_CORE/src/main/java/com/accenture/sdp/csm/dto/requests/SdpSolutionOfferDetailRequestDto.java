/**
 * 
 */
package com.accenture.sdp.csm.dto.requests;

/**
 * @author patrizio.pontecorvi
 *
 */
public class SdpSolutionOfferDetailRequestDto {
	
	private Long rcPriceCatalogId;
	private Long nrcPriceCatalogId;
	private Long currencyTypeID;
	private Long rcFrequencyTypeID;
	private String rcFlagProrate;
	private String rcInAdvance;
	private String packageExternalId;
	private Long offerId;
	private String isMandatory;
	private String packageProfile;
	private String groupName;
	private Long parentOfferId;
	/**
	 * @return the rcPriceCatalogId
	 */
	public Long getRcPriceCatalogId() {
		return rcPriceCatalogId;
	}
	/**
	 * @param rcPriceCatalogId the rcPriceCatalogId to set
	 */
	public void setRcPriceCatalogId(Long rcPriceCatalogId) {
		this.rcPriceCatalogId = rcPriceCatalogId;
	}
	/**
	 * @return the nrcPriceCatalogId
	 */
	public Long getNrcPriceCatalogId() {
		return nrcPriceCatalogId;
	}
	/**
	 * @param nrcPriceCatalogId the nrcPriceCatalogId to set
	 */
	public void setNrcPriceCatalogId(Long nrcPriceCatalogId) {
		this.nrcPriceCatalogId = nrcPriceCatalogId;
	}
	/**
	 * @return the currencyTypeID
	 */
	public Long getCurrencyTypeID() {
		return currencyTypeID;
	}
	/**
	 * @param currencyTypeID the currencyTypeID to set
	 */
	public void setCurrencyTypeID(Long currencyTypeID) {
		this.currencyTypeID = currencyTypeID;
	}
	/**
	 * @return the rcFrequencyTypeID
	 */
	public Long getRcFrequencyTypeID() {
		return rcFrequencyTypeID;
	}
	/**
	 * @param rcFrequencyTypeID the rcFrequencyTypeID to set
	 */
	public void setRcFrequencyTypeID(Long rcFrequencyTypeID) {
		this.rcFrequencyTypeID = rcFrequencyTypeID;
	}
	/**
	 * @return the rcFlagProrate
	 */
	public String getRcFlagProrate() {
		return rcFlagProrate;
	}
	/**
	 * @param rcFlagProrate the rcFlagProrate to set
	 */
	public void setRcFlagProrate(String rcFlagProrate) {
		this.rcFlagProrate = rcFlagProrate;
	}
	/**
	 * @return the rcInAdvance
	 */
	public String getRcInAdvance() {
		return rcInAdvance;
	}
	/**
	 * @param rcInAdvance the rcInAdvance to set
	 */
	public void setRcInAdvance(String rcInAdvance) {
		this.rcInAdvance = rcInAdvance;
	}
	/**
	 * @return the packageExternalId
	 */
	public String getPackageExternalId() {
		return packageExternalId;
	}
	/**
	 * @param packageExternalId the packageExternalId to set
	 */
	public void setPackageExternalId(String packageExternalId) {
		this.packageExternalId = packageExternalId;
	}
	/**
	 * @return the offerId
	 */
	public Long getOfferId() {
		return offerId;
	}
	/**
	 * @param offerId the offerId to set
	 */
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	/**
	 * @return the isMandatory
	 */
	public String isMandatory() {
		return isMandatory;
	}
	/**
	 * @param isMandatory the isMandatory to set
	 */
	public void setMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	/**
	 * @return the packageProfile
	 */
	public String getPackageProfile() {
		return packageProfile;
	}
	/**
	 * @param packageProfile the packageProfile to set
	 */
	public void setPackageProfile(String packageProfile) {
		this.packageProfile = packageProfile;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the parentofferId
	 */
	public Long getParentOfferId() {
		return parentOfferId;
	}
	/**
	 * @param parentOfferId the parentofferId to set
	 */
	public void setParentOfferId(Long parentOfferId) {
		this.parentOfferId = parentOfferId;
	}
	
	@Override
	public String toString(){
		return " rcPriceCatalogId = "+rcPriceCatalogId+
		" rcPriceCatalogId = "+nrcPriceCatalogId+
		" currencyTypeID = "+currencyTypeID+
		" rcFrequencyTypeID = "+rcFrequencyTypeID+
		" rcFlagProrate = "+rcFlagProrate+
		" rcInAdvance = "+rcInAdvance+
		" packageExternalId = "+packageExternalId+
		" offerId = "+offerId+
		" isMandatory = "+ isMandatory+
		" packageProfile = "+packageProfile+
		" groupName = "+ groupName+
		" parentofferId = "+parentOfferId+" ";
	}
	
}
