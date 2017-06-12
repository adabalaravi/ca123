package com.accenture.sdp.csm.dto.responses;

import java.util.Date;

public class SdpDeviceResponseDto extends SdpBaseResponseDto {
	private static final long serialVersionUID = 1L;

	private String deviceUUID;
	private String deviceUUIDType;
	private String deviceChannel;
	private String deviceBrand;
	private String deviceModel;
	private String deviceAlias;
	private Long partyId;
	private String status;
	private boolean isPaired;
	private Date lastFruitionDate;
	private boolean isBlacklisted;
	private boolean isWhitelisted;
	private String blacklistReason;

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getDeviceUUIDType() {
		return deviceUUIDType;
	}

	public void setDeviceUUIDType(String deviceUUIDType) {
		this.deviceUUIDType = deviceUUIDType;
	}

	public String getDeviceChannel() {
		return deviceChannel;
	}

	public void setDeviceChannel(String deviceChannel) {
		this.deviceChannel = deviceChannel;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceAlias() {
		return deviceAlias;
	}

	public void setDeviceAlias(String deviceAlias) {
		this.deviceAlias = deviceAlias;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isPaired() {
		return isPaired;
	}

	public void setPaired(boolean isPaired) {
		this.isPaired = isPaired;
	}

	public Date getLastFruitionDate() {
		return lastFruitionDate;
	}

	public void setLastFruitionDate(Date lastFruitionDate) {
		this.lastFruitionDate = lastFruitionDate;
	}

	public boolean isBlacklisted() {
		return isBlacklisted;
	}

	public void setBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}

	public boolean isWhitelisted() {
		return isWhitelisted;
	}

	public void setWhitelisted(boolean isWhitelisted) {
		this.isWhitelisted = isWhitelisted;
	}

	public String getBlacklistReason() {
		return blacklistReason;
	}

	public void setBlacklistReason(String blacklistReason) {
		this.blacklistReason = blacklistReason;
	}

}