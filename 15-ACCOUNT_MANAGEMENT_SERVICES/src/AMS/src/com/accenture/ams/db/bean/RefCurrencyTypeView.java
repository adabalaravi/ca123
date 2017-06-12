package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.util.Date;

public class RefCurrencyTypeView implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long currencyTypeID;
	private String currencyTypeName;	
	private String createdByID;
	private Date createdDate;
	private String updatedByID;
	private Date updatedDate;
	private String deletedByID;
	private Date deletedDate;
	
	public Long getCurrencyTypeID() {
		return currencyTypeID;
	}
	public void setCurrencyTypeID(Long currencyTypeID) {
		this.currencyTypeID = currencyTypeID;
	}
	public String getCurrencyTypeName() {
		return currencyTypeName;
	}
	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
	}
	public String getCreatedByID() {
		return createdByID;
	}
	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedByID() {
		return updatedByID;
	}
	public void setUpdatedByID(String updatedByID) {
		this.updatedByID = updatedByID;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getDeletedByID() {
		return deletedByID;
	}
	public void setDeletedByID(String deletedByID) {
		this.deletedByID = deletedByID;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	
	
	
}
