package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifySolutionOfferRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long solutionOfferId;
	private Long solutionId;
	private String solutionOfferName;
	private String solutionOfferDescription;
	private Date startDate;
	private Date endDate;
	private String profile;
	// MODIFICATO PER STAR
	private ExternalIdList externalIds;

	private String solutionOfferType;
	private Long duration;

	public ModifySolutionOfferRequest() {
		externalIds = new ExternalIdList();
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public String getSolutionOfferName() {
		return solutionOfferName;
	}

	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}

	public String getSolutionOfferDescription() {
		return solutionOfferDescription;
	}

	public void setSolutionOfferDescription(String solutionOfferDescription) {
		this.solutionOfferDescription = solutionOfferDescription;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public ExternalIdList getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(ExternalIdList externalIds) {
		this.externalIds = externalIds;
	}

	public String getSolutionOfferType() {
		return solutionOfferType;
	}

	public void setSolutionOfferType(String solutionOfferType) {
		this.solutionOfferType = solutionOfferType;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

}
