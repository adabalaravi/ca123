package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sdp_device_counter_config database table.
 * 
 */
@Embeddable
public class SdpDeviceCounterConfigPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "PARTY_ID")
	private Long partyId;

	@Column(name = "DEVICE_CHANNEL_ID")
	private Long deviceChannelId;

	public SdpDeviceCounterConfigPK() {
	}

	public Long getPartyId() {
		return this.partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
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
		if (!(other instanceof SdpDeviceCounterConfigPK)) {
			return false;
		}
		SdpDeviceCounterConfigPK castOther = (SdpDeviceCounterConfigPK) other;
		return this.partyId.equals(castOther.partyId) && this.deviceChannelId.equals(castOther.deviceChannelId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.partyId.hashCode();
		hash = hash * prime + this.deviceChannelId.hashCode();

		return hash;
	}
}