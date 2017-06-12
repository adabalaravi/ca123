package com.accenture.sdp.csm.model.jpa;

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
 * The persistent class for the REF_SOLUTION_TYPE database table.
 * 
 */
@Entity
@Table(name = "REF_STATUS_TYPE")
@NamedQueries({ @NamedQuery(name = RefStatusType.STATUS_TYPE_RETRIEVE_BY_NAME, query = "select a from RefStatusType a where a.statusName=:statusName"),
		@NamedQuery(name = RefStatusType.STATUS_TYPE_RETRIEVE_ALL, query = "select c from RefStatusType c ORDER BY c.statusId") })
public class RefStatusType implements Serializable {
	private static final long serialVersionUID = 5919460026064624201L;

	public static final String STATUS_TYPE_RETRIEVE_BY_NAME = "selectStatusByName";
	public static final String STATUS_TYPE_RETRIEVE_ALL = "searchAllStatus";

	public static final String PARAM_STATUS_NAME = "statusName";

	@Id
	@Column(name = "STATUS_ID")
	private long statusId;

	@Column(name = "STATUS_NAME")
	private String statusName;

	@Column(name = "STATUS_DESCRIPTION")
	private String statusDescription;

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

	public RefStatusType() {
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (statusId ^ (statusId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RefStatusType other = (RefStatusType) obj;
		if (getStatusId() != other.getStatusId()) {
			return false;
		}
		return true;
	}
}
