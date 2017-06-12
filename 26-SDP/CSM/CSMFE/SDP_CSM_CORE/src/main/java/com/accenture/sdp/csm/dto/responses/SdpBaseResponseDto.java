package com.accenture.sdp.csm.dto.responses;


import java.io.Serializable;
import java.util.Date;

public abstract class SdpBaseResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String createdById;	
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	private String deletedById;
	private Date deletedDate;
	private String changeStatusById;
	private Date changeStatusDate;
	
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

	public String getChangeStatusById() {
		return changeStatusById;
	}

	public void setChangeStatusById(String changeStatusById) {
		this.changeStatusById = changeStatusById;
	}

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date chgStatusDate) {
		this.changeStatusDate = chgStatusDate;
	}

	public SdpBaseResponseDto() {
    }	
}