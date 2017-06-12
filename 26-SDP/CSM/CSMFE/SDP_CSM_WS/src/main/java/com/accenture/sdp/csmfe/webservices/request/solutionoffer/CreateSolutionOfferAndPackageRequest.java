package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.solution.PartyGroupNameListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateSolutionOfferAndPackageRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1509334643180494236L;

	private String solutionName;
	private String solutionOfferName;
	private String solutionOfferDescription;
	// modificati per anomalia SDP00000271
	private Date solutionOfferStartDate;
	private Date solutionOfferEndDate;
	private String solutionOfferProfile;
	// fine anomalia
	// MODIFICATO PER STAR
	private ExternalIdList externalIds;
	private SolutionOfferDetailListRequest solutionOfferDetails;

	// AGGIUNTO PER AVS
	private PartyGroupNameListRequest partyGroups;

	private String solutionOfferType;
	private Long duration;

	public CreateSolutionOfferAndPackageRequest() {
		partyGroups = new PartyGroupNameListRequest();
		solutionOfferDetails = new SolutionOfferDetailListRequest();
		externalIds = new ExternalIdList();
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
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

	public String getSolutionOfferProfile() {
		return solutionOfferProfile;
	}

	public void setSolutionOfferProfile(String solutionOfferProfile) {
		this.solutionOfferProfile = solutionOfferProfile;
	}

	public SolutionOfferDetailListRequest getSolutionOfferDetails() {
		return solutionOfferDetails;
	}

	public void setSolutionOfferDetails(SolutionOfferDetailListRequest solutionOfferDetails) {
		this.solutionOfferDetails = solutionOfferDetails;
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
