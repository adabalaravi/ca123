package com.accenture.sdp.csm.dto.responses;

public class SdpSubscriptionDetailResponseDto extends SdpBaseResponseDto {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subscriptionId;
	private Long packageId;
	private Long statusId;
	private String statusName;
	private String subscriptionOfferProfile;
	private String detailExternalId;
	private Long offerId;
	private String offerName;

	public SdpSubscriptionDetailResponseDto() {
		super();
	}


	public Long getSubscriptionId() {
		return subscriptionId;
	}


	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}


	public Long getPackageId() {
		return packageId;
	}


	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}


	public Long getStatusId() {
		return statusId;
	}


	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public String getSubscriptionOfferProfile() {
		return subscriptionOfferProfile;
	}


	public void setSubscriptionOfferProfile(String subscriptionOfferProfile) {
		this.subscriptionOfferProfile = subscriptionOfferProfile;
	}


	public String getDetailExternalId() {
		return detailExternalId;
	}

	public void setDetailExternalId(String detailExternalId) {
		this.detailExternalId = detailExternalId;
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


	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
