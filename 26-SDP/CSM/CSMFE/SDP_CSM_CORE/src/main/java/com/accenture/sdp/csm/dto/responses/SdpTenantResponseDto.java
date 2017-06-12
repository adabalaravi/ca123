package com.accenture.sdp.csm.dto.responses;


public class SdpTenantResponseDto extends SdpBaseResponseDto {

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
