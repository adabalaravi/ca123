package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.List;

public class SdpDevicePolicyResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -239897556896906111L;

	private Long policyId;
	private String policyName;
	private Long maxAssociationsNumber;
	private Long safetyPeriodDuration;
	private Boolean isDefaultPolicy;
	private List<SdpDevicePolicyConfigResponseDto> configs;

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Long getMaxAssociationsNumber() {
		return maxAssociationsNumber;
	}

	public void setMaxAssociationsNumber(Long maxAssociationsNumber) {
		this.maxAssociationsNumber = maxAssociationsNumber;
	}

	public Long getSafetyPeriodDuration() {
		return safetyPeriodDuration;
	}

	public void setSafetyPeriodDuration(Long safetyPeriodDuration) {
		this.safetyPeriodDuration = safetyPeriodDuration;
	}

	public Boolean getIsDefaultPolicy() {
		return isDefaultPolicy;
	}

	public void setIsDefaultPolicy(Boolean isDefaultPolicy) {
		this.isDefaultPolicy = isDefaultPolicy;
	}

	public List<SdpDevicePolicyConfigResponseDto> getConfigs() {
		return configs;
	}

	public void setConfigs(List<SdpDevicePolicyConfigResponseDto> configs) {
		this.configs = configs;
	}

}
