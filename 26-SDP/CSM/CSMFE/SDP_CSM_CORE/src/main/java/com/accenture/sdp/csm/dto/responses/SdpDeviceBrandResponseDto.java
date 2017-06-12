package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpDeviceBrandResponseDto implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 6379379520912903315L;

	private Long brandId;
	private String brandName;
	private Boolean isBlacklisted;
	private Boolean isWhitelisted;
	private String blacklistReason;

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
