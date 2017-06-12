package com.accenture.sdp.csm.dto.responses;

import java.util.Date;
import java.util.List;

public class SdpSubscriptionResponseDto extends SdpBaseResponseDto {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subscriptionId;
	private Long statusId;
	private String statusName;
	private Long partyId;
	private String partyName;
	private Long parentSubscriptionId;
	private String userName;
	private String roleName;
	private Long ownerId;
	private String ownerAccountName;
	private Long payeeId;
	private String payeeAccountName;
	private Long siteId;
	private String siteName;
	private String externalId;
	private Date startDate;
	private Date endDate;
	private Long solutionOfferId;
	private String solutionOfferName;
	private Long partyTypeId;
	private String partyTypeName;
	
	private List<SdpSubscriptionDetailResponseDto> offers;
	
	
	
	public Long getPartyTypeId() {
		return partyTypeId;
	}

	public void setPartyTypeId(Long partyTypeId) {
		this.partyTypeId = partyTypeId;
	}

	public String getPartyTypeName() {
		return partyTypeName;
	}

	public void setPartyTypeName(String partyTypeName) {
		this.partyTypeName = partyTypeName;
	}

	public SdpSubscriptionResponseDto() {
		super();
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getParentSubscriptionId() {
		return parentSubscriptionId;
	}

	public void setParentSubscriptionId(Long parentSubscriptionId) {
		this.parentSubscriptionId = parentSubscriptionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<SdpSubscriptionDetailResponseDto> getOffers() {
		return offers;
	}

	public void setOffers(List<SdpSubscriptionDetailResponseDto> offers) {
		this.offers = offers;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getOwnerAccountName() {
		return ownerAccountName;
	}

	public void setOwnerAccountName(String ownerAccountName) {
		this.ownerAccountName = ownerAccountName;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public String getSolutionOfferName() {
		return solutionOfferName;
	}

	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}
	
	
	
	
	
	

}
