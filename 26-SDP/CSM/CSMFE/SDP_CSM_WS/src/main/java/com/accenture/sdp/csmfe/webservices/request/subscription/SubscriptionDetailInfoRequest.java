package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SubscriptionDetailInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5369170926557989013L;

	private Long packageId;
	private String subscriptionOfferProfile;
	private String externalId;
	
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
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
	
	
}
