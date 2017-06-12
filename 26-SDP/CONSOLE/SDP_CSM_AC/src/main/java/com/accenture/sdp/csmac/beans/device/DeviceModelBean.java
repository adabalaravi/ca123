package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;

public class DeviceModelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String blacklistReason;
	private long deviceModelId;
	private String deviceModelName;
	private boolean blacklisted;
	private boolean whitelisted;	
	private boolean blackListDisabled;
	private boolean whiteListDisabled;
	
	public String getBlacklistReason() {
		return blacklistReason;
	}
	public void setBlacklistReason(String blacklistReason) {
		this.blacklistReason = blacklistReason;
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

	public long getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(long deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	@Override
	public String toString() {
		return "DeviceModelBean [blacklistReason=" + blacklistReason
				+ ", deviceModelId=" + deviceModelId + ", deviceModelName="
				+ deviceModelName + ", blacklisted=" + blacklisted
				+ ", whitelisted=" + whitelisted + ", blackListDisabled="
				+ blackListDisabled + ", whiteListDisabled="
				+ whiteListDisabled + "]";
	}
	
	
}
