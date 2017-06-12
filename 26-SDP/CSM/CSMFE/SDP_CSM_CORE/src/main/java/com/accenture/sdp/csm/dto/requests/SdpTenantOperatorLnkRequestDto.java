package com.accenture.sdp.csm.dto.requests;

import java.io.Serializable;

public class SdpTenantOperatorLnkRequestDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875682623697645703L;
	
	private String tenantName;
	private String operation;
	
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		return " tenantName = " + tenantName
				+ " operation = " + operation;
	}
	
	
}
