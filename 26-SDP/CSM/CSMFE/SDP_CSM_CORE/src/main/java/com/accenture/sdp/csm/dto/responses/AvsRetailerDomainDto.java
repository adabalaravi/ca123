package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;

public class AvsRetailerDomainDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6714296080644436013L;

	private Date creationDate;
	private String description;
	private Date updateDate;
	private String retailerId;
	private String hostDomain;

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRetailerId() {
		return retailerId;
	}
	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}
	public String getHostDomain() {
		return hostDomain;
	}
	public void setHostDomain(String hostDomain) {
		this.hostDomain = hostDomain;
	}
}
