package com.accenture.sdp.csm.dto.responses;

import java.math.BigDecimal;

public class SdpPackageDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1169920402280599050L;

	private Long packageId;
	private Long basePackageId;
	private Long solutionOfferId;
	private Long offerId;
	private String isMandatory;
	private Long statusId;
	private String externalId;
	private Long offerGroupId;
	private String offerGroupName;
	private Long packagePriceId;
	private String profile;

	// Extended properties

	private String offerName;
	private String baseOfferName;
	private String statusName;
	private BigDecimal nrcPrice;
	private BigDecimal rcPrice;
	private String frequencyPriceName;
	private String currencyPriceName;
	private String prorate;
	private String inAdvance;
	
	// added
	private Long rcPriceId;
	private Long nrcPriceId;
	private Long currencyTypeId;
	private Long frequencyTypeId;
	private Long frequencyDays;
	
	// discounts
	private Long discountId;
	private BigDecimal discountAbsRc;
	private BigDecimal discountAbsNrc;
	private BigDecimal discountPercRc;
	private BigDecimal discountPercNrc;


	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Long getBasePackageId() {
		return basePackageId;
	}

	public void setBasePackageId(Long basePackageId) {
		this.basePackageId = basePackageId;
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
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

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Long getPackagePriceId() {
		return packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getNrcPrice() {
		return nrcPrice;
	}

	public void setNrcPrice(BigDecimal nrcPrice) {
		this.nrcPrice = nrcPrice;
	}

	public BigDecimal getRcPrice() {
		return rcPrice;
	}

	public void setRcPrice(BigDecimal rcPrice) {
		this.rcPrice = rcPrice;
	}

	public String getFrequencyPriceName() {
		return frequencyPriceName;
	}

	public void setFrequencyPriceName(String frequencyPriceName) {
		this.frequencyPriceName = frequencyPriceName;
	}

	public String getCurrencyPriceName() {
		return currencyPriceName;
	}

	public void setCurrencyPriceName(String currencyPriceName) {
		this.currencyPriceName = currencyPriceName;
	}

	public String getProrate() {
		return prorate;
	}

	public void setProrate(String prorate) {
		this.prorate = prorate;
	}

	public String getInAdvance() {
		return inAdvance;
	}

	public void setInAdvance(String inAdvance) {
		this.inAdvance = inAdvance;
	}

	public Long getOfferGroupId() {
		return offerGroupId;
	}

	public void setOfferGroupId(Long offerGroupId) {
		this.offerGroupId = offerGroupId;
	}

	public String getOfferGroupName() {
		return offerGroupName;
	}

	public void setOfferGroupName(String offerGroupName) {
		this.offerGroupName = offerGroupName;
	}

	@Override
	public String toString() {
		return "SdpPackageDto [packageId=" + packageId + ", basePackageId="
				+ basePackageId
				+ ", solutionOfferId=" + solutionOfferId + ", offerId="
				+ offerId + ", isMandatory=" + isMandatory + ", statusId="
				+ statusId + ", externalId=" + externalId + ", offerGroupId="
				+ offerGroupId + ", offerGroupName=" + offerGroupName
				+ ", packagePriceId=" + packagePriceId + ", offerName="
				+ offerName + ", statusName=" + statusName + ", nrcPrice="
				+ nrcPrice + ", rcPrice=" + rcPrice + ", frequencyPriceName="
				+ frequencyPriceName + ", currencyPriceName="
				+ currencyPriceName + ", prorate=" + prorate + ", InAdvance="
				+ inAdvance + "]";
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getBaseOfferName() {
		return baseOfferName;
	}

	public void setBaseOfferName(String baseOfferName) {
		this.baseOfferName = baseOfferName;
	}

	public Long getRcPriceId() {
		return rcPriceId;
	}

	public void setRcPriceId(Long rcPriceId) {
		this.rcPriceId = rcPriceId;
	}

	public Long getNrcPriceId() {
		return nrcPriceId;
	}

	public void setNrcPriceId(Long nrcPriceId) {
		this.nrcPriceId = nrcPriceId;
	}

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public Long getFrequencyTypeId() {
		return frequencyTypeId;
	}

	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}

	public Long getFrequencyDays() {
		return frequencyDays;
	}

	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public BigDecimal getDiscountAbsRc() {
		return discountAbsRc;
	}

	public void setDiscountAbsRc(BigDecimal discountAbsRc) {
		this.discountAbsRc = discountAbsRc;
	}

	public BigDecimal getDiscountAbsNrc() {
		return discountAbsNrc;
	}

	public void setDiscountAbsNrc(BigDecimal discountAbsNrc) {
		this.discountAbsNrc = discountAbsNrc;
	}

	public BigDecimal getDiscountPercRc() {
		return discountPercRc;
	}

	public void setDiscountPercRc(BigDecimal discountPercRc) {
		this.discountPercRc = discountPercRc;
	}

	public BigDecimal getDiscountPercNrc() {
		return discountPercNrc;
	}

	public void setDiscountPercNrc(BigDecimal discountPercNrc) {
		this.discountPercNrc = discountPercNrc;
	}
	
}
