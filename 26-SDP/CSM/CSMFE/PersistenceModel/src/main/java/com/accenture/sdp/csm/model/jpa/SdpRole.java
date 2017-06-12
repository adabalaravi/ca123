package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the SDP_ROLE database table.
 * 
 */
@Entity
@Table(name = "SDP_ROLE")
@NamedQueries({ @NamedQuery(name = "selectAllRole", query = "select p from SdpRole p"),
		@NamedQuery(name = "selectAllRoleCount", query = "select count(p.roleName) from SdpRole p") })
public class SdpRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "ROLE_DESCRIPTION")
	private String roleDescription;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

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

	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
		SdpRole other = (SdpRole) obj;
		if (getRoleName() == null) {
			if (other.getRoleName() != null) {
				return false;
			}
		} else if (!getRoleName().equals(other.getRoleName())) {
			return false;
		}
		return true;
	}

}