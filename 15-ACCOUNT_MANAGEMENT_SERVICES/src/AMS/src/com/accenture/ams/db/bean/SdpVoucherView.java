package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class SdpVoucherView implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long voucherID;
	private String voucherCode;
	private String voucherType;
	private Long validityPeriod;
	private Long solutionOfferID;
	private Long partyID;
	private String createdByID;
	private Timestamp createdDate;
	private String updateByID;
	private Timestamp updateDate;

	public Long getVoucherID() {
		return voucherID;
	}

	public void setVoucherID(Long voucherID) {
		this.voucherID = voucherID;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

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

	public Long getSolutionOfferID() {
		return solutionOfferID;
	}

	public void setSolutionOfferID(Long solutionOfferID) {
		this.solutionOfferID = solutionOfferID;
	}

	public Long getPartyID() {
		return partyID;
	}

	public void setPartyID(Long partyID) {
		this.partyID = partyID;
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