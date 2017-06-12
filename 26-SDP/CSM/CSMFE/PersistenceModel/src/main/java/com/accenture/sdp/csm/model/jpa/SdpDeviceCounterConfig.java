package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the sdp_device_counter_config database table.
 * 
 */
@Entity
@Table(name="sdp_device_counter_config")
public class SdpDeviceCounterConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SdpDeviceCounterConfigPK id;

	@Column(name="REGISTERED_DEVICES")
	private Long registeredDevices;

	//bi-directional many-to-one association to SdpPartyDeviceExt
    @ManyToOne
	@JoinColumn(name="PARTY_ID", insertable = false, updatable = false)
	private SdpPartyDeviceExt sdpPartyDeviceExt;

	//bi-directional many-to-one association to RefDeviceChannel
    @ManyToOne
	@JoinColumn(name="DEVICE_CHANNEL_ID", insertable = false, updatable = false)
	private RefDeviceChannel refDeviceChannel;

    public SdpDeviceCounterConfig() {
    }

	public SdpDeviceCounterConfigPK getId() {
		return this.id;
	}

	public void setId(SdpDeviceCounterConfigPK id) {
		this.id = id;
	}
	
	public Long getRegisteredDevices() {
		return this.registeredDevices;
	}

	public void setRegisteredDevices(Long registeredDevices) {
		this.registeredDevices = registeredDevices;
	}

	public SdpPartyDeviceExt getSdpPartyDeviceExt() {
		return this.sdpPartyDeviceExt;
	}

	public void setSdpPartyDeviceExt(SdpPartyDeviceExt sdpPartyDeviceExt) {
		this.sdpPartyDeviceExt = sdpPartyDeviceExt;
	}
	
	public RefDeviceChannel getRefDeviceChannel() {
		return this.refDeviceChannel;
	}

	public void setRefDeviceChannel(RefDeviceChannel refDeviceChannel) {
		this.refDeviceChannel = refDeviceChannel;
	}
	
}