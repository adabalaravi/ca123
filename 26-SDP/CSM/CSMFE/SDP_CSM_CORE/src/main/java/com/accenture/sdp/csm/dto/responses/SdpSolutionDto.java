package com.accenture.sdp.csm.dto.responses;


import java.io.Serializable;
import java.util.Date;
import java.util.List;





public class SdpSolutionDto  extends SdpBaseResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long solutionId;

	
	private Long solutionTypeId;
	
	
	private String solutionName;

	
	private String solutionDescription;
	
	
	private Long statusId;
	

	private String externalId;
	
	
	private Date startDate;
	
	
	private Date endDate;
	
	private String profile;
	
	
	//campi non presenti nel model

	private String solutionTypeName;
	
	
	private String statusName;
	
	
	private List<SdpPartyGroupResponseDto> partyGroups; 
	

	public List<SdpPartyGroupResponseDto> getPartyGroups() {
		return partyGroups;
	}


	public void setPartyGroups(List<SdpPartyGroupResponseDto> partyGroups) {
		this.partyGroups = partyGroups;
	}


	public String getSolutionTypeName() {
		return solutionTypeName;
	}


	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
	}


	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public Long getSolutionId() {
		return solutionId;
	}


	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}


	public Long getSolutionTypeId() {
		return solutionTypeId;
	}


	public void setSolutionTypeId(Long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}


	public String getSolutionName() {
		return solutionName;
	}


	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}


	public String getSolutionDescription() {
		return solutionDescription;
	}


	public void setSolutionDescription(String solutionDescription) {
		this.solutionDescription = solutionDescription;
	}


	public Long getStatusId() {
		return statusId;
	}


	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
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


	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}


	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
}