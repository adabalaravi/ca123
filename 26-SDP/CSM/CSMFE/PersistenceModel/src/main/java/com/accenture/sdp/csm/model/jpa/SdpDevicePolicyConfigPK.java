package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sdp_device_policy_config database table.
 * 
 */
@Embeddable
public class SdpDevicePolicyConfigPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "POLICY_ID")
	private Long policyId;

	@Column(name = "DEVICE_CHANNEL_ID")
	private Long deviceChannelId;

	public SdpDevicePolicyConfigPK() {
	}

	public Long getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public Long getDeviceChannelId() {
		return this.deviceChannelId;
	}

	public void setDeviceChannelId(Long deviceChannelId) {
		this.deviceChannelId = deviceChannelId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SdpDevicePolicyConfigPK)) {
			return false;
		}
		SdpDevicePolicyConfigPK castOther = (SdpDevicePolicyConfigPK) other;
		return this.policyId.equals(castOther.policyId) && this.deviceChannelId.equals(castOther.deviceChannelId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.policyId.hashCode();
		hash = hash * prime + this.deviceChannelId.hashCode();

		return hash;
	}
}