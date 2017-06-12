package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;

public class ServiceTypeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceTypeName;
	private Long serviceTypeId;
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public Long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	
	
	

}
