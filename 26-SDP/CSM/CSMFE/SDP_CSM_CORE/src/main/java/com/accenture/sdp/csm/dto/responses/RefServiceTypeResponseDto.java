package com.accenture.sdp.csm.dto.responses;

public class RefServiceTypeResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7542538788140930389L;

	private Long serviceTypeId;
	private String serviceTypeDescription;
	private String serviceTypeName;
	
	public Long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}
	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
}
