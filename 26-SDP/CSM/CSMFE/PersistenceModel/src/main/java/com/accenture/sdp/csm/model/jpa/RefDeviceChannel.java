package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the ref_device_channel database table.
 * 
 */
@Entity
@Table(name = "ref_device_channel")
@NamedQueries({ @NamedQuery(name = RefDeviceChannel.QUERY_RETRIEVE_ALL, query = "select p from RefDeviceChannel p"),
		@NamedQuery(name = RefDeviceChannel.QUERY_RETRIEVE_BY_NAME, query = "select p from RefDeviceChannel p where p.deviceChannelName=:deviceChannelName"),
		@NamedQuery(name = RefDeviceChannel.QUERY_RETRIEVE_BY_BLACKLIST, query = "select p from RefDeviceChannel p where p.isBl=:isBl"),
		@NamedQuery(name = RefDeviceChannel.QUERY_RETRIEVE_BY_WHITELIST, query = "select p from RefDeviceChannel p where p.isWl=:isWl") })
public class RefDeviceChannel extends BlackWhiteListableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllDeviceChannel";
	public static final String QUERY_RETRIEVE_BY_NAME = "selectDeviceChannelByName";
	public static final String QUERY_RETRIEVE_BY_BLACKLIST = "selectDeviceChannelByBlacklist";
	public static final String QUERY_RETRIEVE_BY_WHITELIST = "selectDeviceChannelByWhitelist";

	public static final String PARAM_NAME = "deviceChannelName";
	public static final String PARAM_IS_BL = "isBl";
	public static final String PARAM_IS_WL = "isWl";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_CHANNEL_ID")
	private Long deviceChannelId;

	@Column(name = "DEVICE_CHANNEL_NAME")
	private String deviceChannelName;

	@Column(name = "IS_PORTABLE")
	private Byte isPortable;

	// bi-directional many-to-one association to SdpDevice
	@OneToMany(mappedBy = "refDeviceChannel")
	private List<SdpDevice> sdpDevices;

	// bi-directional many-to-one association to SdpDeviceCountersConfig
	@OneToMany(mappedBy = "refDeviceChannel")
	private List<SdpDeviceCounterConfig> sdpDeviceCountersConfigs;

	// bi-directional many-to-one association to SdpDevicePolicyConfig
	@OneToMany(mappedBy = "refDeviceChannel")
	private List<SdpDevicePolicyConfig> sdpDevicePolicyConfigs;

	public RefDeviceChannel() {
		sdpDevices = new ArrayList<SdpDevice>();
		sdpDeviceCountersConfigs = new ArrayList<SdpDeviceCounterConfig>();
		sdpDevicePolicyConfigs = new ArrayList<SdpDevicePolicyConfig>();
	}

	public Long getDeviceChannelId() {
		return this.deviceChannelId;
	}

	public void setDeviceChannelId(Long deviceChannelId) {
		this.deviceChannelId = deviceChannelId;
	}

	public String getDeviceChannelName() {
		return this.deviceChannelName;
	}

	public void setDeviceChannelName(String deviceChannelName) {
		this.deviceChannelName = deviceChannelName;
	}

	public Byte getIsPortable() {
		return this.isPortable;
	}

	public void setIsPortable(Byte isPortable) {
		this.isPortable = isPortable;
	}

	public List<SdpDevice> getSdpDevices() {
		return this.sdpDevices;
	}

	public void setSdpDevices(List<SdpDevice> sdpDevices) {
		this.sdpDevices = sdpDevices;
	}

	public List<SdpDeviceCounterConfig> getSdpDeviceCountersConfigs() {
		return this.sdpDeviceCountersConfigs;
	}

	public void setSdpDeviceCountersConfigs(List<SdpDeviceCounterConfig> sdpDeviceCountersConfigs) {
		this.sdpDeviceCountersConfigs = sdpDeviceCountersConfigs;
	}

	public List<SdpDevicePolicyConfig> getSdpDevicePolicyConfigs() {
		return this.sdpDevicePolicyConfigs;
	}

	public void setSdpDevicePolicyConfigs(List<SdpDevicePolicyConfig> sdpDevicePolicyConfigs) {
		this.sdpDevicePolicyConfigs = sdpDevicePolicyConfigs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceChannelId == null) ? 0 : deviceChannelId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RefDeviceChannel other = (RefDeviceChannel) obj;
		if (deviceChannelId == null) {
			if (other.deviceChannelId != null) {
				return false;
			}
		} else if (!deviceChannelId.equals(other.deviceChannelId)) {
			return false;
		}
		return true;
	}

}