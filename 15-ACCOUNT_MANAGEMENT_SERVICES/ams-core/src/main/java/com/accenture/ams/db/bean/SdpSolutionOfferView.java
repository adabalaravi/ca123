package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.util.Date;

public class SdpSolutionOfferView implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long solutionOfferID;
	private Long parentSolutionOfferID;
	private Long solutionID;
	private String solutionOfferName;
	private String solutionOfferDescription;
	private Long statusID;
	private String solutionOfferProfile;
	private String externalID;
	private Date startDate;
	private Date endDate;
	private String createdByID;
	private Date createdDate;
	private String updatedByID;
	private Date updatedDate;
	private String deletedByID;
	private Date deletedDate;
	private String chgStatusByID;
	private Date chgStatusDate;
	private String isBasicProfile;
	private Long duration;
	private String solutionOfferType;
	
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getSolutionOfferType() {
		return solutionOfferType;
	}
	public void setSolutionOfferType(String solutionOfferType) {
		this.solutionOfferType = solutionOfferType;
	}
	public String getIsBasicProfile() {
		return isBasicProfile;
	}
	public void setIsBasicProfile(String isBasicProfile) {
		this.isBasicProfile = isBasicProfile;
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
	public String getSolutionOfferProfile() {
		return solutionOfferProfile;
	}
	public void setSolutionOfferProfile(String solutionOfferProfile) {
		this.solutionOfferProfile = solutionOfferProfile;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	public Date getChgStatusDate() {
		return chgStatusDate;
	}
	public void setChgStatusDate(Date chgStatusDate) {
		this.chgStatusDate = chgStatusDate;
	}
	public String getCreatedByID() {
		return createdByID;
	}
	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}
	public String getUpdatedByID() {
		return updatedByID;
	}
	public void setUpdatedByID(String updatedByID) {
		this.updatedByID = updatedByID;
	}
	public String getDeletedByID() {
		return deletedByID;
	}
	public void setDeletedByID(String deletedByID) {
		this.deletedByID = deletedByID;
	}
	public String getChgStatusByID() {
		return chgStatusByID;
	}
	public void setChgStatusByID(String chgStatusByID) {
		this.chgStatusByID = chgStatusByID;
	}
	public Long getSolutionOfferID() {
		return solutionOfferID;
	}
	public void setSolutionOfferID(Long solutionOfferID) {
		this.solutionOfferID = solutionOfferID;
	}
	public Long getParentSolutionOfferID() {
		return parentSolutionOfferID;
	}
	public void setParentSolutionOfferID(Long parentSolutionOfferID) {
		this.parentSolutionOfferID = parentSolutionOfferID;
	}
	public Long getSolutionID() {
		return solutionID;
	}
	public void setSolutionID(Long solutionID) {
		this.solutionID = solutionID;
	}
	public Long getStatusID() {
		return statusID;
	}
	public void setStatusID(Long statusID) {
		this.statusID = statusID;
	}
	public String getExternalID() {
		return externalID;
	}
	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}
	
	
	
	
	
}
