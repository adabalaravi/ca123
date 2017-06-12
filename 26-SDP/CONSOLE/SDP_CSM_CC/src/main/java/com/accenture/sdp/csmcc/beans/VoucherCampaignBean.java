package com.accenture.sdp.csmcc.beans;


import java.io.Serializable;
import java.util.Date;

public class VoucherCampaignBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1078987123980808008L;


	private String voucherType;
	private Long validityPeriod;
	private Long solutionOfferId;
	private Date startDate;
	private Date endDate;

	


	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public Long getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(Long validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public Long getSolutionOfferId() {
		return solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
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
	
}
