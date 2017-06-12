package com.accenture.sdp.csmfe.webservices.response.servicetype;

import java.io.Serializable;
import java.util.Date;

public class ServiceTypeInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531762278843150425L;
	
	private Long serviceTypeId;
	private String serviceTypeDescription;
	private String serviceTypeName;
	private String createdById;	
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	private String deletedById;
	private Date deletedDate;
	
	public Long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}
	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
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
