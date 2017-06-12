package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the sdp_device_policy_config database table.
 * 
 */
@Entity
@Table(name="sdp_device_policy_config")
public class SdpDevicePolicyConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SdpDevicePolicyConfigPK id;

	@Column(name="MAXIMUM_ALLOWED_DEVICES")
	private Long maximumAllowedDevices;

	//bi-directional many-to-one association to SdpDevicePolicy
    @ManyToOne
	@JoinColumn(name="POLICY_ID", insertable = false, updatable = false)
	private SdpDevicePolicy sdpDevicePolicy;

	//bi-directional many-to-one association to RefDeviceChannel
    @ManyToOne
	@JoinColumn(name="DEVICE_CHANNEL_ID", insertable = false, updatable = false)
	private RefDeviceChannel refDeviceChannel;

    public SdpDevicePolicyConfig() {
    }

	public SdpDevicePolicyConfigPK getId() {
		return this.id;
	}

	public void setId(SdpDevicePolicyConfigPK id) {
		this.id = id;
	}
	
	public Long getMaximumAllowedDevices() {
		return this.maximumAllowedDevices;
	}

	public void setMaximumAllowedDevices(Long maximumAllowedDevices) {
		this.maximumAllowedDevices = maximumAllowedDevices;
	}

	public SdpDevicePolicy getSdpDevicePolicy() {
		return this.sdpDevicePolicy;
	}

	public void setSdpDevicePolicy(SdpDevicePolicy sdpDevicePolicy) {
		this.sdpDevicePolicy = sdpDevicePolicy;
	}
	
	public RefDeviceChannel getRefDeviceChannel() {
		return this.refDeviceChannel;
	}

	public void setRefDeviceChannel(RefDeviceChannel refDeviceChannel) {
		this.refDeviceChannel = refDeviceChannel;
	}
	
}