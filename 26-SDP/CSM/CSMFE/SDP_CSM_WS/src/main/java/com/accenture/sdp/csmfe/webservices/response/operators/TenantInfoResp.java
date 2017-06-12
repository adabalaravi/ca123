package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class TenantInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tenantName;
	private String tenantDescription;
	private Long statusId;
	private String statusName;
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantDescription() {
		return tenantDescription;
	}
	public void setTenantDescription(String tenantDescription) {
		this.tenantDescription = tenantDescription;
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
	
	
}
