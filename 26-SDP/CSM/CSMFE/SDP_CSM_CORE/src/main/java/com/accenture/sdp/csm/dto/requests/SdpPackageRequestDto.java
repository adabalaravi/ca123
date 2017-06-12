package com.accenture.sdp.csm.dto.requests;

/**
 * <p>
 * DTO used containing all package details
 * </p>
 * @param packageId
 *            Id of the package
 * @param offerId
 *            offerId of the offer
 * @param isMandatory
 *            Name of the offer
 * @param packagePriceId
 *            Package Price associated to the couple (solution offer, offer)
 * @param externalId
 *            Identifier used by external system
 * @param basePackageId
 *            Id of the package of the parent base offer
 * @param groupId
 *            Id of the group of offer
 * @param profile
 *            profile
 * @param rcPriceCatalogId
 *            Recurring Charge Price
 * @param nrcPriceCatalogId
 *            Not Recurring Charge Price
 * @param currencyTypeId
 *            Currency for the price
 * @param rcFrequencyTypeId
 *            Indicates the type of payment frequency
 * @param rcFlagProrate
 * 
 * */
public class SdpPackageRequestDto {

	private Long packageId;
	private Long offerId;
	private String isMandatory;
	private Long packagePriceId;
	private String externalId;
	private Long basePackageId;
	private Long groupId;
	private String profile;
	private Long rcPriceCatalogId;
	private Long nrcPriceCatalogId;
	private Long currencyTypeId;
	private Long rcFrequencyTypeId;
	private String rcFlagProrate;
	private String rcInAdvance;

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Long getPackagePriceId() {
		return packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Long getBasePackageId() {
		return basePackageId;
	}

	public void setBasePackageId(Long basePackageId) {
		this.basePackageId = basePackageId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Long getRcPriceCatalogId() {
		return rcPriceCatalogId;
	}

	public void setRcPriceCatalogId(Long rcPriceCatalogId) {
		this.rcPriceCatalogId = rcPriceCatalogId;
	}

	public Long getNrcPriceCatalogId() {
		return nrcPriceCatalogId;
	}

	public void setNrcPriceCatalogId(Long nrcPriceCatalogId) {
		this.nrcPriceCatalogId = nrcPriceCatalogId;
	}

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public Long getRcFrequencyTypeId() {
		return rcFrequencyTypeId;
	}

	public void setRcFrequencyTypeId(Long rcFrequencyTypeId) {
		this.rcFrequencyTypeId = rcFrequencyTypeId;
	}

	public String getRcFlagProrate() {
		return rcFlagProrate;
	}

	public void setRcFlagProrate(String rcFlagProrate) {
		this.rcFlagProrate = rcFlagProrate;
	}

	public String getRcInAdvance() {
		return rcInAdvance;
	}

	public void setRcInAdvance(String rcInAdvance) {
		this.rcInAdvance = rcInAdvance;
	}

	@Override
	public String toString() {
		return " packageId = " + packageId + " offerId = "
				+ offerId + " isMandatory = " + isMandatory + " packagePriceId = "
				+ packagePriceId + " externalId = " + externalId
				+ " basePackageId = " + basePackageId + " groupId = " + groupId
				+ " profile = " + profile + " rcPriceCatalogId = "
				+ rcPriceCatalogId + " nrcPriceCatalogId = " + nrcPriceCatalogId
				+ " currencyTypeId = " + currencyTypeId + " rcFrequencyTypeId = "
				+ rcFrequencyTypeId + " rcFlagProrate = " + rcFlagProrate
				+ " rcInAdvance = " + rcInAdvance + " ";
	}

}
