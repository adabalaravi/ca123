package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.solution.PartyGroupNameListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateSolutionOfferAndDiscountRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7627304161775973179L;

	private Long parentSolutionOfferId;
	private String solutionOfferName;
	private String solutionOfferDescription;
	private Date startDate;
	private Date endDate;
	private String profile;
	private DiscountListRequest discounts;
	// MODIFICATO PER STAR
	private ExternalIdList externalIds;

	// AGGIUNTO PER AVS
	private PartyGroupNameListRequest partyGroups;

	private String solutionOfferType;
	private Long duration;

	public CreateSolutionOfferAndDiscountRequest() {
		partyGroups = new PartyGroupNameListRequest();
		discounts = new DiscountListRequest();
		externalIds = new ExternalIdList();
	}

	public Long getParentSolutionOfferId() {
		return parentSolutionOfferId;
	}

	public void setParentSolutionOfferId(Long parentSolutionOfferId) {
		this.parentSolutionOfferId = parentSolutionOfferId;
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

	public DiscountListRequest getDiscounts() {
		return discounts;
	}

	public void setDiscounts(DiscountListRequest discounts) {
		this.discounts = discounts;
	}

	public PartyGroupNameListRequest getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupNameListRequest partyGroups) {
		this.partyGroups = partyGroups;
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
