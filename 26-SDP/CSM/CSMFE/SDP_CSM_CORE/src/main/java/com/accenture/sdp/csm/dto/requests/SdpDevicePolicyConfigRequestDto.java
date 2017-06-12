package com.accenture.sdp.csm.dto.requests;

public class SdpDevicePolicyConfigRequestDto {

	private String deviceChannel;
	private Long maximumNumber;

	public String getDeviceChannel() {
		return deviceChannel;
	}

	public void setDeviceChannel(String deviceChannel) {
		this.deviceChannel = deviceChannel;
	}

	public Long getMaximumNumber() {
		return maximumNumber;
	}

	public void setMaximumNumber(Long maximumNumber) {
		this.maximumNumber = maximumNumber;
	}

	@Override
	public String toString() {
		return " deviceChannel = " + deviceChannel + " maximumNumber = " + maximumNumber + " ";
	}

}
