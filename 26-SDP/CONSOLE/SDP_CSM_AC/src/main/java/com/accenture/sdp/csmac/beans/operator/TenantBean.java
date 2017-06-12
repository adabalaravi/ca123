package com.accenture.sdp.csmac.beans.operator;

import java.io.Serializable;

public class TenantBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2495363443959045855L;
	
	private Long statusId;
	private String statusName;
	private String tenantName;
	private String tenantDescription;

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

}
