package com.accenture.sdp.csmac.beans.subscription;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SubscriptionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long solutionOfferId;
	private Long statusId;
	private String status;
	private Long partyId;
	private Long parentSubscriptionId;
	private String username;
	private String roleName;
	private Long ownerId;
	private Long payeeId;
	private Long siteId;
	private String externalId;
	private Date startDate;
	private Date endDate;

	private String partyName;
	private String ownerAccountName;
	private String payeeAccountName;
	private String siteName;
	private String solutionOfferName;

	private String createdById;
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	private String deletedById;
	private Date deletedDate;
	private String changeStatusById;
	private Date changeStatusDate;

	// dovrebbe darceli avs dal profile, ma non ce li da
	private Double totalPrice;
	private Double nrcPrice;
	private Double rcPrice;
	private Boolean discounted;
	private Double nrcDiscountedPrice;
	private Double rcDiscountedPrice;
	private String frequency;
	private String voucherCode;

	private List<SubscriptionDetailBean> details;

	public Long getId() {
		return id;
	}

	public void setId(Long subscriptionId) {
		this.id = subscriptionId;
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getSolutionOfferName() {
		return solutionOfferName;
	}

	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeletedById() {
		return deletedById;
	}

	public void setDeletedById(String deletedById) {
		this.deletedById = deletedById;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getChangeStatusById() {
		return changeStatusById;
	}

	public void setChangeStatusById(String changeStatusById) {
		this.changeStatusById = changeStatusById;
	}

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getNrcPrice() {
		return nrcPrice;
	}

	public void setNrcPrice(Double nrcPrice) {
		this.nrcPrice = nrcPrice;
	}

	public Double getRcPrice() {
		return rcPrice;
	}

	public void setRcPrice(Double rcPrice) {
		this.rcPrice = rcPrice;
	}

	public Boolean getDiscounted() {
		return discounted;
	}

	public void setDiscounted(Boolean discounted) {
		this.discounted = discounted;
	}

	public Double getNrcDiscountedPrice() {
		return nrcDiscountedPrice;
	}

	public void setNrcDiscountedPrice(Double nrcDiscountedPrice) {
		this.nrcDiscountedPrice = nrcDiscountedPrice;
	}

	public Double getRcDiscountedPrice() {
		return rcDiscountedPrice;
	}

	public void setRcDiscountedPrice(Double rcDiscountedPrice) {
		this.rcDiscountedPrice = rcDiscountedPrice;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public List<SubscriptionDetailBean> getDetails() {
		return details;
	}

	public void setDetails(List<SubscriptionDetailBean> details) {
		this.details = details;
	}

}
