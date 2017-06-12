package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;

public class DeviceChannelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String blacklistReason;
	private long deviceChannelId;
	private String deviceChannelName;
	private boolean blacklisted;
	private boolean whitelisted;
	private boolean portable;
	
	private boolean blackListDisabled;
	private boolean whiteListDisabled;
	
	public String getBlacklistReason() {
		return blacklistReason;
	}
	public void setBlacklistReason(String blacklistReason) {
		this.blacklistReason = blacklistReason;
	}
	public long getDeviceChannelId() {
		return deviceChannelId;
	}
	public void setDeviceChannelId(long deviceChannelId) {
		this.deviceChannelId = deviceChannelId;
	}
	public String getDeviceChannelName() {
		return deviceChannelName;
	}
	public void setDeviceChannelName(String deviceChannelName) {
		this.deviceChannelName = deviceChannelName;
	}
	public boolean isBlacklisted() {
		return blacklisted;
	}
	public void setBlacklisted(boolean blacklisted) {
		this.blacklisted = blacklisted;
	}
	public boolean isWhitelisted() {
		return whitelisted;
	}
	public void setWhitelisted(boolean whitelisted) {
		this.whitelisted = whitelisted;
	}
	public boolean isPortable() {
		return portable;
	}
	public void setPortable(boolean portable) {
		this.portable = portable;
	}
	
	public boolean isBlackListDisabled() {
		return blackListDisabled;
	}
	public void setBlackListDisabled(boolean blackListDisabled) {
		this.blackListDisabled = blackListDisabled;
	}
	public boolean isWhiteListDisabled() {
		return whiteListDisabled;
	}
	public void setWhiteListDisabled(boolean whiteListDisabled) {
		this.whiteListDisabled = whiteListDisabled;
	}
	@Override
	public String toString() {
		return "DeviceChannelBean [blacklistReason=" + blacklistReason
				+ ", deviceChannelId=" + deviceChannelId
				+ ", deviceChannelName=" + deviceChannelName + ", blacklisted="
				+ blacklisted + ", whitelisted=" + whitelisted + ", portable="
				+ portable + ", blackListDisabled=" + blackListDisabled
				+ ", whiteListDisabled=" + whiteListDisabled + "]";
	}
	
}
