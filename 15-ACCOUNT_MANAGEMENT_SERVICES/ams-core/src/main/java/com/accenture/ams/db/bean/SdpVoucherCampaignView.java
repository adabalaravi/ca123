package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class SdpVoucherCampaignView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String voucherType;
	private Long solutionOfferID;
	private Long validityPeriod;
	private Timestamp startDate;
	private Timestamp endDate;
	private String createdByID;
	private Timestamp createdDate;
	private String updateByID;
	private Timestamp updateDate;
	
	
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public Long getSolutionOfferID() {
		return solutionOfferID;
	}
	public void setSolutionOfferID(Long solutionOfferID) {
		this.solutionOfferID = solutionOfferID;
	}
	public Long getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(Long validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getCreatedByID() {
		return createdByID;
	}
	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdateByID() {
		return updateByID;
	}
	public void setUpdateByID(String updateByID) {
		this.updateByID = updateByID;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
	
	
}
