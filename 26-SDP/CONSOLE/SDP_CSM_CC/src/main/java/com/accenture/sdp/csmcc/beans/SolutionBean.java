package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.common.constants.StatusConstants;

public class SolutionBean implements Serializable {

	public static final String SOLUTION_NAME_FIELD = "solutionName";
	public static final String SOLUTION_DESC_FIELD = "solutionDesc";
	public static final String SOLUTION_STATUS_FIELD = "solutionStatus";
	public static final String SOLUTION_EXTID_FIELD = "solutionExtId";
	public static final String SOLUTION_START_DATE_FIELD = "solutionStartDate";
	public static final String SOLUTION_END_DATE_FIELD = "solutionEndDate";
	public static final String SOLUTION_CREATION_DATE_FIELD = "solutionCreationDate";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String solutionName;
	private Long solutionId;
	private Long solutionTypeId;
	private String solutionDesc;
	private String solutionStatus;
	private String solutionExtId;
	private String solutionProfile;
	private Date solutionStartDate;
	private Date solutionEndDate;
	private Date solutionCreationDate;
	private String solutionTypeName;
	private String partyGroupName;
	private String partyGroupDescription;
	

	private String solutionNameStored;
	private String solutionDescStored;
	private String solutionExtIdStored;
	private Date solutionStartDateStored;
	private Date solutionEndDateStored;
	private String solutionTypeNameStored;
	private Long solutionTypeIdStored;
	private String solutionProfileStored;
	

	public void storeValues() {
		this.solutionNameStored = this.solutionName;
		this.solutionDescStored = this.solutionDesc;
		this.solutionExtIdStored = this.solutionExtId;
		this.solutionStartDateStored = this.solutionStartDate;
		this.solutionEndDateStored = this.solutionEndDate;
		this.solutionTypeNameStored = this.solutionTypeName;
		this.solutionTypeIdStored = this.solutionTypeId;
		this.solutionProfileStored = this.solutionProfile;
	}

	

	public void resetSolution() {
		this.solutionName = this.solutionNameStored;
		this.solutionDesc = this.solutionDescStored;
		this.solutionExtId = this.solutionExtIdStored;
		this.solutionStartDate = this.solutionStartDateStored;
		this.solutionEndDate = this.solutionEndDateStored;
		this.solutionTypeName = this.solutionTypeNameStored;
		this.solutionTypeId = this.solutionTypeIdStored;
		this.solutionProfile = this.solutionProfileStored;
	}

	public SolutionBean() {

	}

	public Long getSolutionTypeId() {
		return solutionTypeId;
	}

	public void setSolutionTypeId(Long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}

	
	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}

	public String getSolutionDesc() {
		return solutionDesc;
	}

	public void setSolutionDesc(String solutionDesc) {
		this.solutionDesc = solutionDesc;
	}

	public String getSolutionStatus() {
		return solutionStatus;
	}

	public void setSolutionStatus(String solutionStatus) {
		this.solutionStatus = solutionStatus;
	}

	public String getSolutionExtId() {
		return solutionExtId;
	}

	public void setSolutionExtId(String solutionExtId) {
		this.solutionExtId = solutionExtId;
	}

	public String getSolutionProfile() {
		return solutionProfile;
	}

	public void setSolutionProfile(String solutionProfile) {
		this.solutionProfile = solutionProfile;
	}

	public String getSolutionTypeName() {
		return solutionTypeName;
	}

	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
	}

	public Date getSolutionStartDate() {
		return solutionStartDate;
	}

	public void setSolutionStartDate(Date solutionStartDate) {
		this.solutionStartDate = solutionStartDate;
	}

	public Date getSolutionEndDate() {
		return solutionEndDate;
	}

	public void setSolutionEndDate(Date solutionEndDate) {
		this.solutionEndDate = solutionEndDate;
	}

	public Date getSolutionCreationDate() {
		return solutionCreationDate;
	}

	public void setSolutionCreationDate(Date solutionCreationDate) {
		this.solutionCreationDate = solutionCreationDate;
	}

	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}

	public String getPartyGroupDescription() {
		return partyGroupDescription;
	}

	public void setPartyGroupDescription(String partyGroupDescription) {
		this.partyGroupDescription = partyGroupDescription;
	}

	
	
	//
	//
	public void cancelFields(ActionEvent event) {
		cleanSolution();
	}

	public void cleanSolution() {
		this.solutionName = "";
		this.solutionDesc = "";
		this.solutionExtId = "";
		this.solutionProfile = "";
		this.solutionStartDate = null;
		this.solutionEndDate = null;
		this.solutionTypeId = null;
		this.solutionTypeName = "";
	
	}


	public boolean isActive() {
		return StatusConstants.ACTIVE.equalsIgnoreCase(solutionStatus);
	}

	public boolean isSuspended() {
		return StatusConstants.SUSPENDED.equalsIgnoreCase(solutionStatus);
	}

	public boolean isInactive() {
		return StatusConstants.INACTIVE.equalsIgnoreCase(solutionStatus);
	}

}
