package com.accenture.sdp.csmfe.webservices.response.offer;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4947819021855611728L;
	
	private String offerName;
	private Long offerId;
	private String offerDescription;
	private String offerProfile;
	private Long statusId;
	private Long serviceVariantId;
	private String externalId;
	
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	public String getOfferDescription() {
		return offerDescription;
	}
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
	public String getOfferProfile() {
		return offerProfile;
	}
	public void setOfferProfile(String offerProfile) {
		this.offerProfile = offerProfile;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public Long getServiceVariantId() {
		return serviceVariantId;
	}
	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	
}
