package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;

/**
 * The persistent class for the ref_device_brand database table.
 * 
 */
@Entity
@Table(name = "ref_device_brand")
@NamedQueries({ @NamedQuery(name = RefDeviceBrand.QUERY_RETRIEVE_ALL, query = "select p from RefDeviceBrand p"),
		@NamedQuery(name = RefDeviceBrand.QUERY_RETRIEVE_BY_NAME, query = "select p from RefDeviceBrand p where p.deviceBrandName=:deviceBrandName"),
		@NamedQuery(name = RefDeviceBrand.QUERY_RETRIEVE_BY_BLACKLIST, query = "select p from RefDeviceBrand p where p.isBl=:isBl"),
		@NamedQuery(name = RefDeviceBrand.QUERY_RETRIEVE_BY_WHITELIST, query = "select p from RefDeviceBrand p where p.isWl=:isWl") })
public class RefDeviceBrand extends BlackWhiteListableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllDeviceBrand";
	public static final String QUERY_RETRIEVE_BY_NAME = "selectDeviceBrandByName";
	public static final String QUERY_RETRIEVE_BY_BLACKLIST = "selectDeviceBrandByBlacklist";
	public static final String QUERY_RETRIEVE_BY_WHITELIST = "selectDeviceBrandByWhitelist";

	public static final String PARAM_NAME = "deviceBrandName";
	public static final String PARAM_IS_BL = "isBl";
	public static final String PARAM_IS_WL = "isWl";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_BRAND_ID")
	private Long deviceBrandId;

	@Column(name = "DEVICE_BRAND_NAME")
	private String deviceBrandName;

	// bi-directional many-to-one association to RefDeviceModel
	@OneToMany(mappedBy = "refDeviceBrand")
	private List<RefDeviceModel> refDeviceModels;

	// bi-directional many-to-one association to SdpDevice
	@OneToMany(mappedBy = "refDeviceBrand")
	private List<SdpDevice> sdpDevices;

	public RefDeviceBrand() {
		refDeviceModels = new ArrayList<RefDeviceModel>();
		sdpDevices = new ArrayList<SdpDevice>();
	}

	public Long getDeviceBrandId() {
		return this.deviceBrandId;
	}

	public void setDeviceBrandId(Long deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}

	public String getDeviceBrandName() {
		return this.deviceBrandName;
	}

	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}

	public List<RefDeviceModel> getRefDeviceModels() {
		return this.refDeviceModels;
	}

	public void setRefDeviceModels(List<RefDeviceModel> refDeviceModels) {
		this.refDeviceModels = refDeviceModels;
	}

	public List<SdpDevice> getSdpDevices() {
		return this.sdpDevices;
	}

	public void setSdpDevices(List<SdpDevice> sdpDevices) {
		this.sdpDevices = sdpDevices;
	}

}