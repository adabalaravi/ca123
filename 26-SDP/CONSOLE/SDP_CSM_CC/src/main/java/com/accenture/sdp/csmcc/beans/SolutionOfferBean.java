package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.accenture.sdp.csmcc.common.constants.StatusConstants;

public class SolutionOfferBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SOLUTION_OFFER_NAME_FIELD = "solutionOfferName";
	public static final String SOLUTION_OFFER_DESC_FIELD = "solutionOfferDesc";
	public static final String SOLUTION_NAME_FIELD = "solutionName";
	public static final String SOLUTION_OFFER_STATUS_FIELD = "solutionOfferStatus";
	public static final String SOLUTION_OFFER_START_DATE_FIELD = "solutionOfferStartDate";
	public static final String SOLUTION_OFFER_END_DATE_FIELD = "solutionOfferEndDate";
	public static final String SOLUTION_OFFER_CREATION_DATE_FIELD = "solutionOfferCreationDate";

	private Long solutionOfferId;
	private String solutionOfferName;
	private String solutionOfferDesc;
	private Long parentSolutionOfferId;
	private String parentSolutionOfferName;
	private Long solutionId;
	private String solutionName;
	private String solutionOfferStatus;
	private Date solutionOfferStartDate;
	private Date solutionOfferEndDate;
	private Date solutionOfferCreationDate;
	private String solutionOfferProfile;
	private Long solutionOfferDuration;
	private String solutionOfferType;
	private boolean parentReadOnly;
	private List<PackageBean> offerDetailList;

	private Long rcFrequencyTypeId;
	private List<ExternalIdBean> externalId;

	private String purchaseType;

	// AVS
	private String type;
	private String productType;

	private Long solutionOfferPriceId;
	private BigDecimal solutionOfferPrice;
	private Long fruitionMaxNumber;
	private Long bundleDuration;
	private List<String> partyGroupNames;

	public SolutionOfferBean() {
		super();
		offerDetailList = new ArrayList<PackageBean>();
		partyGroupNames = new ArrayList<String>();
		externalId = new ArrayList<ExternalIdBean>();
		rcFrequencyTypeId = 1L;
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

	public String getSolutionOfferDesc() {
		return solutionOfferDesc;
	}

	public void setSolutionOfferDesc(String solutionOfferDesc) {
		this.solutionOfferDesc = solutionOfferDesc;
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}

	public String getSolutionOfferStatus() {
		return solutionOfferStatus;
	}

	public void setSolutionOfferStatus(String solutionOfferStatus) {
		this.solutionOfferStatus = solutionOfferStatus;
	}

	public Date getSolutionOfferStartDate() {
		return solutionOfferStartDate;
	}

	public void setSolutionOfferStartDate(Date solutionOfferStartDate) {
		this.solutionOfferStartDate = solutionOfferStartDate;
	}

	public Date getSolutionOfferEndDate() {
		return solutionOfferEndDate;
	}

	public void setSolutionOfferEndDate(Date solutionOfferEndDate) {
		this.solutionOfferEndDate = solutionOfferEndDate;
	}

	public Date getSolutionOfferCreationDate() {
		return solutionOfferCreationDate;
	}

	public void setSolutionOfferCreationDate(Date solutionOfferCreationDate) {
		this.solutionOfferCreationDate = solutionOfferCreationDate;
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public String getSolutionOfferProfile() {
		return solutionOfferProfile;
	}

	public void setSolutionOfferProfile(String solutionOfferProfile) {
		this.solutionOfferProfile = solutionOfferProfile;
	}

	public List<PackageBean> getOfferDetailList() {
		return offerDetailList;
	}

	public void setOfferDetailList(List<PackageBean> offerDetailList) {
		this.offerDetailList = offerDetailList;
	}

	/* END GETTER AND SETTER METHODS */

	public boolean isParentReadOnly() {
		return parentReadOnly;
	}

	public void setParentReadOnly(boolean parentReadOnly) {
		this.parentReadOnly = parentReadOnly;
	}

	public BigDecimal getSolutionOfferPrice() {
		return solutionOfferPrice;
	}

	public void setSolutionOfferPrice(BigDecimal solutionOfferPrice) {
		this.solutionOfferPrice = solutionOfferPrice;
	}

	public Long getFruitionMaxNumber() {
		return fruitionMaxNumber;
	}

	public void setFruitionMaxNumber(Long fruitionMaxNumber) {
		this.fruitionMaxNumber = fruitionMaxNumber;
	}

	public Long getBundleDuration() {
		return bundleDuration;
	}

	public void setBundleDuration(Long bundleDuration) {
		this.bundleDuration = bundleDuration;
	}

	public boolean isActive() {
		return StatusConstants.ACTIVE.equalsIgnoreCase(solutionOfferStatus);
	}

	public boolean isSuspended() {
		return StatusConstants.SUSPENDED.equalsIgnoreCase(solutionOfferStatus);
	}

	public boolean isInactive() {
		return StatusConstants.INACTIVE.equalsIgnoreCase(solutionOfferStatus);
	}

	public Long getParentSolutionOfferId() {
		return parentSolutionOfferId;
	}

	public void setParentSolutionOfferId(Long parentSolutionOfferId) {
		this.parentSolutionOfferId = parentSolutionOfferId;
	}

	public String getParentSolutionOfferName() {
		return parentSolutionOfferName;
	}

	public void setParentSolutionOfferName(String parentSolutionOfferName) {
		this.parentSolutionOfferName = parentSolutionOfferName;
	}

	public Long getSolutionOfferPriceId() {
		return solutionOfferPriceId;
	}

	public void setSolutionOfferPriceId(Long solutionOfferPriceId) {
		this.solutionOfferPriceId = solutionOfferPriceId;
	}

	public List<String> getPartyGroupNames() {
		return partyGroupNames;
	}

	public void setPartyGroupNames(List<String> partyGroupNames) {
		this.partyGroupNames = partyGroupNames;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getRcFrequencyTypeId() {
		return rcFrequencyTypeId;
	}

	public void setRcFrequencyTypeId(Long rcFrequencyTypeId) {
		this.rcFrequencyTypeId = rcFrequencyTypeId;
	}

	public List<ExternalIdBean> getExternalId() {
		return externalId;
	}

	public void setExternalId(List<ExternalIdBean> externalId) {
		this.externalId = externalId;
	}

	public Long getSolutionOfferDuration() {
		return solutionOfferDuration;
	}

	public void setSolutionOfferDuration(Long solutionOfferDuration) {
		this.solutionOfferDuration = solutionOfferDuration;
	}

	public String getSolutionOfferType() {
		return solutionOfferType;
	}

	public void setSolutionOfferType(String solutionOfferType) {
		this.solutionOfferType = solutionOfferType;
	}
	
	
	@Override
	public String toString() {
		return "SolutionOfferBean [solutionOfferId=" + solutionOfferId + ", solutionOfferName=" + solutionOfferName + ", solutionOfferDesc="
				+ solutionOfferDesc + ", parentSolutionOfferId=" + parentSolutionOfferId + ", parentSolutionOfferName=" + parentSolutionOfferName
				+ ", solutionId=" + solutionId + ", solutionName=" + solutionName + ", solutionOfferStatus=" + solutionOfferStatus
				+ ", solutionOfferStartDate=" + solutionOfferStartDate + ", solutionOfferEndDate=" + solutionOfferEndDate + ", solutionOfferCreationDate="
				+ solutionOfferCreationDate + ", solutionOfferProfile=" + solutionOfferProfile + ", parentReadOnly=" + parentReadOnly + ", offerDetailList="
				+ offerDetailList + ", rcFrequencyTypeId=" + rcFrequencyTypeId + ", externalId=" + externalId + ", purchaseType=" + purchaseType + ", type="
				+ type + ", productType=" + productType + ", solutionOfferPriceId=" + solutionOfferPriceId + ", solutionOfferPrice=" + solutionOfferPrice
				+ ", fruitionMaxNumber=" + fruitionMaxNumber + ", bundleDuration=" + bundleDuration + ", partyGroupNames=" + partyGroupNames + "]";
	}
}
