package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferInfoResp extends BaseInfoResp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8210487309469300452L;
	
	private Long packageId;
	private Long offerId;
	private String offerName;
	private String subscriptionOfferProfile;
	private String externalId;
	private Long statusId;
	private String statusName;
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
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getSubscriptionOfferProfile() {
		return subscriptionOfferProfile;
	}
	public void setSubscriptionOfferProfile(String subscriptionOfferProfile) {
		this.subscriptionOfferProfile = subscriptionOfferProfile;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}