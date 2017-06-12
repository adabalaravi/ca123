package com.accenture.sdp.csm.dto.responses;

import java.util.Date;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.ExternalIdDto;

public class SdpSolutionOfferDto extends SdpBaseResponseDto {
	private static final long serialVersionUID = 1L;

	private Long solutionId;
	private String solutionName;
	private Long solutionOfferId;
	private Long parentSolutionOfferId;
	private String solutionOfferName;
	private String solutionOfferDescription;
	private Long statusId;
	private String statusName;
	// private String externalId;
	private Date startDate;
	private Date endDate;
	private String profile;

	private String solutionOfferType;
	private Long duration;

	// AGGIUNTO X AVS
	private List<SdpPartyGroupResponseDto> partyGroups;

	// AGGIUNTO X STAR
	private List<ExternalIdDto> externalIdList;

	/**
	 * @return the solutionId
	 */
	public Long getSolutionId() {
		return solutionId;
	}

	/**
	 * @param solutionId
	 *            the solutionId to set
	 */
	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	/**
	 * @return the solutionOfferId
	 */
	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	/**
	 * @param solutionOfferId
	 *            the solutionOfferId to set
	 */
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	/**
	 * @return the solutionOfferName
	 */
	public String getSolutionOfferName() {
		return solutionOfferName;
	}

	/**
	 * @param solutionOfferName
	 *            the solutionOfferName to set
	 */
	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}

	/**
	 * @return the solutionOfferDescription
	 */
	public String getSolutionOfferDescription() {
		return solutionOfferDescription;
	}

	/**
	 * @param solutionOfferDescription
	 *            the solutionOfferDescription to set
	 */
	public void setSolutionOfferDescription(String solutionOfferDescription) {
		this.solutionOfferDescription = solutionOfferDescription;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the solutionName
	 */
	public String getSolutionName() {
		return solutionName;
	}

	/**
	 * @param solutionName
	 *            the solutionName to set
	 */
	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}

	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * @return the parentSolutionOfferId
	 */
	public Long getParentSolutionOfferId() {
		return parentSolutionOfferId;
	}

	/**
	 * @param parentSolutionOfferId
	 *            the parentSolutionOfferId to set
	 */
	public void setParentSolutionOfferId(Long parentSolutionOfferId) {
		this.parentSolutionOfferId = parentSolutionOfferId;
	}

	public List<SdpPartyGroupResponseDto> getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(List<SdpPartyGroupResponseDto> partyGroups) {
		this.partyGroups = partyGroups;
	}

	public List<ExternalIdDto> getExternalIdList() {
		return externalIdList;
	}

	public void setExternalIdList(List<ExternalIdDto> externalIdList) {
		this.externalIdList = externalIdList;
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