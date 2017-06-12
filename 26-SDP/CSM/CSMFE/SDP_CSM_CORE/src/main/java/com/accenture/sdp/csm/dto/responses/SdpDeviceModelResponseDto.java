package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpDeviceModelResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1702335680963381673L;
	
	private Long modelId;
	private String modelName;
	private Boolean isBlacklisted;
	private Boolean isWhitelisted;
	private String blacklistReason;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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

	public String getBlacklistReason() {
		return blacklistReason;
	}

	public void setBlacklistReason(String blacklistReason) {
		this.blacklistReason = blacklistReason;
	}

}
