package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SolutionOfferDetailInfoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -467670326816417409L;

	private Long rcPriceCatalogId;
	private Long nrcPriceCatalogId;
	private Long currencyTypeId;
	private Long rcFrequencyTypeId;
	private String rcFlagProrate;
	private String rcInAdvance;
	private String packageExternalId;
	private Long offerId;
	private String isMandatory;
	private String packageProfile;
	private String groupName;
	private Long parentOfferId;
	
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
	public String getPackageExternalId() {
		return packageExternalId;
	}
	public void setPackageExternalId(String packageExternalId) {
		this.packageExternalId = packageExternalId;
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
	public String getPackageProfile() {
		return packageProfile;
	}
	public void setPackageProfile(String packageProfile) {
		this.packageProfile = packageProfile;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getParentOfferId() {
		return parentOfferId;
	}
	public void setParentOfferId(Long parentOfferId) {
		this.parentOfferId = parentOfferId;
	}
	
}
