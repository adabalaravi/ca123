package com.accenture.sdp.csmac.beans;

import java.io.Serializable;
import java.util.Date;

public class SolutionOfferBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -425175651336645907L;

	private long id;
	private String name;
	private String description;
	private Long parentSolutionOfferId;
	private Long solutionId;
	private String solutionName;
	private Long statusId;
	private String statusName;
	private Date startDate;
	private Date endDate;
	private Date creationDate;
	private String profile;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Long getParentSolutionOfferId() {
		return parentSolutionOfferId;
	}

	public void setParentSolutionOfferId(Long parentSolutionOfferId) {
		this.parentSolutionOfferId = parentSolutionOfferId;
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		SolutionOfferBean other = (SolutionOfferBean) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
