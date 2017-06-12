package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpDeviceChannelResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1702335680963381673L;

	private Long deviceChannelId;
	private String deviceChannelName;
	private Boolean isBlacklisted;
	private Boolean isWhitelisted;
	private Boolean portable;
	private String blacklistReason;

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

	public Boolean getIsBlacklisted() {
		return isBlacklisted;
	}

	public void setIsBlacklisted(Boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}

	public Boolean getIsWhitelisted() {
		return isWhitelisted;
	}

	public void setIsWhitelisted(Boolean isWhitelisted) {
		this.isWhitelisted = isWhitelisted;
	}

	public Boolean getPortable() {
		return portable;
	}

	public void setPortable(Boolean portable) {
		this.portable = portable;
	}

	public String getBlacklistReason() {
		return blacklistReason;
	}

	public void setBlacklistReason(String blacklistReason) {
		this.blacklistReason = blacklistReason;
	}

}
