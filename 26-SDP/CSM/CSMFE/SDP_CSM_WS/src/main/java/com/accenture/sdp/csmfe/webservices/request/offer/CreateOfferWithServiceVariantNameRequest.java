package com.accenture.sdp.csmfe.webservices.request.offer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateOfferWithServiceVariantNameRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2132306701493182265L;
	
	private String offerName;
	private String offerDescription;
	private String serviceVariantName;
	private String externalId;
	private String offerProfile;
	
	
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferDescription() {
		return offerDescription;
	}
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
	public String getServiceVariantName() {
		return serviceVariantName;
	}
	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getOfferProfile() {
		return offerProfile;
	}
	public void setOfferProfile(String offerProfile) {
		this.offerProfile = offerProfile;
	}
	
	
	
}
