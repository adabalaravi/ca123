package com.accenture.sdp.csmfe.webservices.response.solution;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422867890970268171L;

	private Long solutionId;
	private Long solutionTypeId;
	private String solutionName;
	private String solutionDescription;
	private Long statusId;
	private String externalId;
	private Date startDate;
	private Date endDate;
	private String profile;

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public Long getSolutionTypeId() {
		return solutionTypeId;
	}

	public void setSolutionTypeId(Long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}

	public String getSolutionDescription() {
		return solutionDescription;
	}

	public void setSolutionDescription(String solutionDescription) {
		this.solutionDescription = solutionDescription;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
