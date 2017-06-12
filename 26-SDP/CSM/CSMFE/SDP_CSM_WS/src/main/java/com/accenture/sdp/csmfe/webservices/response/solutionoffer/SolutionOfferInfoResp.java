package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.solutionoffer.ExternalIdList;
import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;
import com.accenture.sdp.csmfe.webservices.response.partygroup.PartyGroupListResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionOfferInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1594180553070290647L;

	private Long solutionId;
	private Long parentSolutionOfferId;
	private String solutionName;
	private Long solutionOfferId;
	private String solutionOfferName;
	private String solutionOfferDescription;
	private Long statusId;
	// MODIFICATO PER STAR
	private ExternalIdList externalIds;
	// private String externalId;
	private Date startDate;
	private Date endDate;
	private String profile;

	private PartyGroupListResp partyGroups;

	private String solutionOfferType;
	private Long duration;

	public SolutionOfferInfoResp() {
		partyGroups = new PartyGroupListResp();
		externalIds = new ExternalIdList();
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public Long getParentSolutionOfferId() {
		return parentSolutionOfferId;
	}

	public void setParentSolutionOfferId(Long parentSolutionOfferId) {
		this.parentSolutionOfferId = parentSolutionOfferId;
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
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

	public String getSolutionOfferDescription() {
		return solutionOfferDescription;
	}

	public void setSolutionOfferDescription(String solutionOfferDescription) {
		this.solutionOfferDescription = solutionOfferDescription;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public ExternalIdList getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(ExternalIdList externalIds) {
		this.externalIds = externalIds;
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

	public PartyGroupListResp getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupListResp partyGroups) {
		this.partyGroups = partyGroups;
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
