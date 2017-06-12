package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;
import java.util.List;

public class DevicePolicyBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long policyId;
	private String devicePolicyName;
	private List<MaxNumberAllowedDevicesBean> allowedDevices;
	private long maxNumberOfAssociations;
	private long safetyPeriodInDays;
	private long maxNumberAllowedDevices;
	
	public String getDevicePolicyName() {
		return devicePolicyName;
	}
	public void setDevicePolicyName(String devicePolicyName) {
		this.devicePolicyName = devicePolicyName;
	}
	public long getMaxNumberOfAssociations() {
		return maxNumberOfAssociations;
	}
	public void setMaxNumberOfAssociations(long maxNumberOfAssociations) {
		this.maxNumberOfAssociations = maxNumberOfAssociations;
	}
	public long getSafetyPeriodInDays() {
		return safetyPeriodInDays;
	}
	public void setSafetyPeriodInDays(long safetyPeriodInDays) {
		this.safetyPeriodInDays = safetyPeriodInDays;
	}
	public List<MaxNumberAllowedDevicesBean> getAllowedDevices() {
		return allowedDevices;
	}
	public void setAllowedDevices(List<MaxNumberAllowedDevicesBean> allowedDevices) {
		this.allowedDevices = allowedDevices;
	}
	public long getMaxNumberAllowedDevices() {
		return maxNumberAllowedDevices;
	}
	public void setMaxNumberAllowedDevices(long maxNumberAllowedDevices) {
		this.maxNumberAllowedDevices = maxNumberAllowedDevices;
	}
	@Override
	public String toString() {
		return "DevicePolicyBean [policyId=" + policyId + ", devicePolicyName="
				+ devicePolicyName + ", allowedDevices=" + allowedDevices
				+ ", maxNumberOfAssociations=" + maxNumberOfAssociations
				+ ", safetyPeriodInDays=" + safetyPeriodInDays
				+ ", maxNumberAllowedDevices=" + maxNumberAllowedDevices + "]";
	}
	public long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

}
