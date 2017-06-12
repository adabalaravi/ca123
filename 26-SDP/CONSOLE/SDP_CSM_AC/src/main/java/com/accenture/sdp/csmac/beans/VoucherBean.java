package com.accenture.sdp.csmac.beans;

import java.io.Serializable;
import java.util.Date;

import com.accenture.sdp.csmac.business.SolutionOfferBusiness;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.controllers.PopupController;

public class VoucherBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1078987123980808008L;

	private Long voucherId;
	private String voucherCode;
	private String voucherType;
	private Long validityPeriod;
	private Long solutionOfferId;
	private Long partyId;
	private Date startDate;
	private Date endDate;
	// related entities
	// FIXME lazy loaded
	private SolutionOfferBean solutionOffer;

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
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

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public SolutionOfferBean getSolutionOffer() {
		if (solutionOffer == null && solutionOfferId != null) {
			try {
				solutionOffer = SolutionOfferBusiness.searchSolutionOfferById(solutionOfferId);
				// calcolo massimo startDate
				startDate = Utilities.getBiggestDate(startDate, solutionOffer.getStartDate());
				endDate = Utilities.getSmallestDate(endDate, solutionOffer.getEndDate());
			} catch (ServiceErrorException e) {
				PopupController.handleServiceErrorException(e);
			}
		}
		return solutionOffer;
	}

	public void setSolutionOffer(SolutionOfferBean solutionOffer) {
		this.solutionOffer = solutionOffer;
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
