package com.accenture.sdp.csm.model.jpa.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BlackWhiteListableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5358723296215851030L;

	@Column(name = "IS_BL")
	private Byte isBl;

	@Column(name = "IS_WL")
	private Byte isWl;

	@Column(name = "BL_REASON")
	private String blReason;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public Byte getIsBl() {
		return isBl;
	}

	public void setIsBl(Byte isBl) {
		this.isBl = isBl;
	}

	public Byte getIsWl() {
		return isWl;
	}

	public void setIsWl(Byte isWl) {
		this.isWl = isWl;
	}

	public String getBlReason() {
		return blReason;
	}

	public void setBlReason(String blReason) {
		this.blReason = blReason;
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

}
