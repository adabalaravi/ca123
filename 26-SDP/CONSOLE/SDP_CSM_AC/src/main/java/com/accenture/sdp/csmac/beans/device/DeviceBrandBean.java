package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;

public class DeviceBrandBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String blacklistReason;
	private long deviceBrandId;
	private String deviceBrandName;
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
	public long getDeviceBrandId() {
		return deviceBrandId;
	}
	public void setDeviceBrandId(long devicebrandId) {
		this.deviceBrandId = devicebrandId;
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
	public String getDeviceBrandName() {
		return deviceBrandName;
	}
	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}
	@Override
	public String toString() {
		return "DeviceBrandBean [blacklistReason=" + blacklistReason
				+ ", deviceBrandId=" + deviceBrandId + ", deviceBrandName="
				+ deviceBrandName + ", blacklisted=" + blacklisted
				+ ", whitelisted=" + whitelisted + ", blackListDisabled="
				+ blackListDisabled + ", whiteListDisabled="
				+ whiteListDisabled + "]";
	}
	
	
}
