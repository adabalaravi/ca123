package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpPlatformDto extends SdpBaseResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long platformId;

	private String platformDescription;

	private String platformName;

	private String externalId;

	private String platformProfile;

	// campi non presenti nel model

	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPlatformProfile() {
		return platformProfile;
	}

	public void setPlatformProfile(String platformProfile) {
		this.platformProfile = platformProfile;
	}

	private Long platformStatusId;

	public SdpPlatformDto() {

	}

	public String getExternalId() {
		return this.externalId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Long getPlatformStatusId() {
		return platformStatusId;
	}

	public void setPlatformStatusId(Long platformStatusId) {
		this.platformStatusId = platformStatusId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPlatformDescription() {
		return platformDescription;
	}

	public void setPlatformDescription(String platformDescription) {
		this.platformDescription = platformDescription;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

}