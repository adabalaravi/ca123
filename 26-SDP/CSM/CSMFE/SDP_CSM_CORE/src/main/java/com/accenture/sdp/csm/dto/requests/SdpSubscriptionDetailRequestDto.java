package com.accenture.sdp.csm.dto.requests;

import java.io.Serializable;

public class SdpSubscriptionDetailRequestDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long packageId;
	private String subscriptionOfferProfile;
	private String externalId;
	
	
	public SdpSubscriptionDetailRequestDto() {
		super();
	}
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
	@Override
	public String toString() {
		return " packageId = " + packageId
				+ " subscriptionOfferProfile = " + subscriptionOfferProfile
				+ " externalId = " + externalId+" ";
	}
}
