package com.accenture.sdp.csmfe.webservices.request.solution;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifySolutionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7050831866800011620L;

	private Long solutionId;
	private String solutionName;
	private String solutionDescription;
	private String externalId;
	private Date startDate;
	private Date endDate;
	private Long solutionTypeId;
	private String profile;
	
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
	public String getSolutionDescription() {
		return solutionDescription;
	}
	public void setSolutionDescription(String solutionDescription) {
		this.solutionDescription = solutionDescription;
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
	public Long getSolutionTypeId() {
		return solutionTypeId;
	}
	public void setSolutionTypeId(Long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	

}
