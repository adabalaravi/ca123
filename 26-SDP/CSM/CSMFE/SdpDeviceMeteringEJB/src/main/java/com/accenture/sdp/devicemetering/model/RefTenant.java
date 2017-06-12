package com.accenture.sdp.devicemetering.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the REF_TENANT database table.
 * 
 */
@Entity
@Table(name="REF_TENANT")
@NamedQueries({
	@NamedQuery(name = "selectAllTenants", query = "select t from RefTenant t")
})
public class RefTenant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TENANT_NAME")
	private String name;
	
	@Column(name="TENANT_DESCRIPTION")
	private String description;
	
	@Column(name="PERSISTENCE_UNIT")
	private String persistenceUnit;
	
	@Column(name="METERING_PERSISTENCE_UNIT")
	private String meteringPersistenceUnit;
	
	
	
	
	@Column(name="STATUS_ID")
	private Long statusId;

	@Column(name="CREATED_BY_ID")
	private String createdById;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPersistenceUnit() {
		return persistenceUnit;
	}

	public void setPersistenceUnit(String persistenceUnit) {
		this.persistenceUnit = persistenceUnit;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public String getChangeStatusById() {
		return changeStatusById;
	}

	public void setChangeStatusById(String changeStatusById) {
		this.changeStatusById = changeStatusById;
	}

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public String getMeteringPersistenceUnit() {
		return meteringPersistenceUnit;
	}

	public void setMeteringPersistenceUnit(String meteringPersistenceUnit) {
		this.meteringPersistenceUnit = meteringPersistenceUnit;
	}
	
	
}