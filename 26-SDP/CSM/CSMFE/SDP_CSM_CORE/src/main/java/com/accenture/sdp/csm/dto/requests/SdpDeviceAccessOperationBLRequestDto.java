package com.accenture.sdp.csm.dto.requests;

public class SdpDeviceAccessOperationBLRequestDto extends SdpDeviceAccessOperationRequestDto {

	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return super.toString() + "reason = " + reason + " ";
	}
}
