package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_EXTERNAL_PLATFORM database table.
 * 
 */
@Entity
@Table(name = "REF_EXTERNAL_PLATFORM")
public class RefExternalPlatform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EXTERNAL_PLATFORM_NAME")
	private String externalPlatformName;

	@Column(name = "EXTERNAL_PLATFORM_DESCRIPTION")
	private String externalPlatformDescription;

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

	public String getExternalPlatformName() {
		return externalPlatformName;
	}

	public void setExternalPlatformName(String externalPlatformName) {
		this.externalPlatformName = externalPlatformName;
	}

	public String getExternalPlatformDescription() {
		return externalPlatformDescription;
	}

	public void setExternalPlatformDescription(String externalPlatformDescription) {
		this.externalPlatformDescription = externalPlatformDescription;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedById() {
		return this.updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((externalPlatformName == null) ? 0 : externalPlatformName.hashCode());
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
		RefExternalPlatform other = (RefExternalPlatform) obj;
		if (externalPlatformName == null) {
			if (other.externalPlatformName != null) {
				return false;
			}
		} else if (!externalPlatformName.equals(other.externalPlatformName)) {
			return false;
		}
		return true;
	}

}