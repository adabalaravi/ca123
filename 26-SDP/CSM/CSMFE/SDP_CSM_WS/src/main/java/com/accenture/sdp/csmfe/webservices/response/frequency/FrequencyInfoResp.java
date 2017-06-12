package com.accenture.sdp.csmfe.webservices.response.frequency;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class FrequencyInfoResp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143418361601702375L;

	private Long frequencyTypeId;
	private String frequencyTypeName;
	private String frequencyTypeDescription;
	private String createdById;
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	private Long frequencyDays;
	public Long getFrequencyTypeId() {
		return frequencyTypeId;
	}
	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}
	public String getFrequencyTypeName() {
		return frequencyTypeName;
	}
	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}
	public String getFrequencyTypeDescription() {
		return frequencyTypeDescription;
	}
	public void setFrequencyTypeDescription(String frequencyTypeDescription) {
		this.frequencyTypeDescription = frequencyTypeDescription;
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
	public Long getFrequencyDays() {
		return frequencyDays;
	}
	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
	}
	
	
	
}
