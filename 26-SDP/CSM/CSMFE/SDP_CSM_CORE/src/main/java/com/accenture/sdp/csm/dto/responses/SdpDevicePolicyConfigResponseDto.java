package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpDevicePolicyConfigResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4738689819703823198L;

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

}
