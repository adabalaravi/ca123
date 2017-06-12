package com.accenture.sdp.csm.dto.responses;

public class SdpOperatorRightResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long rightId;
	private String rightName;
	private String rightDescription;
	private Long operatorResourceId;
	private String operatorResourceName;
	private String operatorResourceDescription;

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public String getRightDescription() {
		return rightDescription;
	}

	public void setRightDescription(String rightDescription) {
		this.rightDescription = rightDescription;
	}

	public Long getOperatorResourceId() {
		return operatorResourceId;
	}

	public void setOperatorResourceId(Long operatorResourceId) {
		this.operatorResourceId = operatorResourceId;
	}

	public String getOperatorResourceName() {
		return operatorResourceName;
	}

	public void setOperatorResourceName(String operatorResourceName) {
		this.operatorResourceName = operatorResourceName;
	}

	public String getOperatorResourceDescription() {
		return operatorResourceDescription;
	}

	public void setOperatorResourceDescription(String operatorResourceDescription) {
		this.operatorResourceDescription = operatorResourceDescription;
	}

}