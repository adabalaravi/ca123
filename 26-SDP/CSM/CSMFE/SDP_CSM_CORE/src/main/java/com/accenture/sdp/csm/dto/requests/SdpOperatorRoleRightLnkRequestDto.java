package com.accenture.sdp.csm.dto.requests;

import java.io.Serializable;

public class SdpOperatorRoleRightLnkRequestDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875682623697645703L;
	
	private Long rightId;
	private String operation;
	
	public Long getRightId() {
		return rightId;
	}
	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		return " rightId = " + rightId
				+ " operation = " + operation;
	}
	
	
}
