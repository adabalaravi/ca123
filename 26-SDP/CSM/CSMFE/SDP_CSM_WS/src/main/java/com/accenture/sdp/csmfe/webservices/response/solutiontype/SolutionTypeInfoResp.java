package com.accenture.sdp.csmfe.webservices.response.solutiontype;

import java.io.Serializable;
import java.util.Date;

public class SolutionTypeInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531762278843150425L;
	
	private Long solutionTypeId;
	private String solutionTypeDescription;
	private String solutionTypeName;
	private String createdById;	
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	private String deletedById;
	private Date deletedDate;
	public Long getSolutionTypeId() {
		return solutionTypeId;
	}
	public void setSolutionTypeId(Long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}
	public String getSolutionTypeDescription() {
		return solutionTypeDescription;
	}
	public void setSolutionTypeDescription(String solutionTypeDescription) {
		this.solutionTypeDescription = solutionTypeDescription;
	}
	public String getSolutionTypeName() {
		return solutionTypeName;
	}
	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
	}
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedById() {
		return updatedById;
	}
	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getDeletedById() {
		return deletedById;
	}
	public void setDeletedById(String deletedById) {
		this.deletedById = deletedById;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	
}
