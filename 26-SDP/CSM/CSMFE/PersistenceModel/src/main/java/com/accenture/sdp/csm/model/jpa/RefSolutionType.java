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
@Table(name = "REF_SOLUTION_TYPE")
@NamedQueries({ @NamedQuery(name = RefSolutionType.SOLUTION_TYPE_RETRIEVE_ALL, query = "select st from RefSolutionType st ORDER BY st.solutionTypeId") })
public class RefSolutionType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String SOLUTION_TYPE_RETRIEVE_ALL = "searchAllSolutionTypes";

	@Id
	@Column(name = "SOLUTION_TYPE_ID")
	private long solutionTypeId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "SOLUTION_TYPE_DESCRIPTION")
	private String solutionTypeDescription;

	@Column(name = "SOLUTION_TYPE_NAME")
	private String solutionTypeName;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public RefSolutionType() {
	}

	public long getSolutionTypeId() {
		return this.solutionTypeId;
	}

	public void setSolutionTypeId(long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
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

	public String getSolutionTypeDescription() {
		return this.solutionTypeDescription;
	}

	public void setSolutionTypeDescription(String solutionTypeDescription) {
		this.solutionTypeDescription = solutionTypeDescription;
	}

	public String getSolutionTypeName() {
		return this.solutionTypeName;
	}

	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
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
		result = prime * result + (int) (solutionTypeId ^ (solutionTypeId >>> 32));
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
		RefSolutionType other = (RefSolutionType) obj;
		if (getSolutionTypeId() != other.getSolutionTypeId()) {
			return false;
		}
		return true;
	}

}