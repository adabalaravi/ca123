package com.accenture.sdp.csm.dto.responses;

public class RefStatusTypeResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long statusId;
	private String statusName;
	private String statusDescription;
	

	
	public RefStatusTypeResponseDto() {
		super();
	}



	public long getStatusId() {
		return statusId;
	}



	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}



	public String getStatusName() {
		return statusName;
	}



	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}



	public String getStatusDescription() {
		return statusDescription;
	}



	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
	
	
	

}
