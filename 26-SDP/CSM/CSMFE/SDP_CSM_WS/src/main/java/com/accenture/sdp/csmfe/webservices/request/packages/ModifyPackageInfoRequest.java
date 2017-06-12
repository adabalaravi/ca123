package com.accenture.sdp.csmfe.webservices.request.packages;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyPackageInfoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6037587685460354716L;

	private Long packageId;
	private String externalId;
	private Long offerId;
	private String isMandatory;
	private Long packagePriceId;
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
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
	
	
}
