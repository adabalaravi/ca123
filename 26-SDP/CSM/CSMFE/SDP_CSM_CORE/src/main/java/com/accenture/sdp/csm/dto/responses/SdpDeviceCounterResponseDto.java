package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpDeviceCounterResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long deviceChannelId;
	private String deviceChannelName;
	private Long registeredDevices;

	public Long getDeviceChannelId() {
		return deviceChannelId;
	}

	public void setDeviceChannelId(Long deviceChannelId) {
		this.deviceChannelId = deviceChannelId;
	}

	public String getDeviceChannelName() {
		return deviceChannelName;
	}

	public void setDeviceChannelName(String deviceChannelName) {
		this.deviceChannelName = deviceChannelName;
	}

	public Long getRegisteredDevices() {
		return registeredDevices;
	}

	public void setRegisteredDevices(Long registeredDevices) {
		this.registeredDevices = registeredDevices;
	}

}