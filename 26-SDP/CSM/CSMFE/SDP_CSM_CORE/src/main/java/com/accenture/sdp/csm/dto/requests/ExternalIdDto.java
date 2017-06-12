package com.accenture.sdp.csm.dto.requests;

public class ExternalIdDto {

	private String externalId;
	private String externalPlatformName;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalPlatformName() {
		return externalPlatformName;
	}

	public void setExternalPlatformName(String externalPlatformName) {
		this.externalPlatformName = externalPlatformName;
	}

	@Override
	public String toString() {
		return " externalId = " + externalId +
				" externalPlatformName = " + externalPlatformName + " ";
	}

}
