package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;

/**
 * The persistent class for the ref_device_model database table.
 * 
 */
@Entity
@Table(name = "ref_device_model")
@NamedQueries({
		@NamedQuery(name = RefDeviceModel.QUERY_RETRIEVE_BY_BRANDID, query = "select p from RefDeviceModel p where p.refDeviceBrand.deviceBrandId=:deviceBrandId"),
		@NamedQuery(name = RefDeviceModel.QUERY_RETRIEVE_BY_NAME, query = "select p from RefDeviceModel p where p.deviceModelName=:deviceModelName"),
		@NamedQuery(name = RefDeviceModel.QUERY_RETRIEVE_BY_BLACKLIST, query = "select p from RefDeviceModel p where p.isBl=:isBl"),
		@NamedQuery(name = RefDeviceModel.QUERY_RETRIEVE_BY_WHITELIST, query = "select p from RefDeviceModel p where p.isWl=:isWl") })
public class RefDeviceModel extends BlackWhiteListableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_NAME = "selectDeviceModelByName";
	public static final String QUERY_RETRIEVE_BY_BRANDID = "selectDeviceModelByBrandId";
	public static final String QUERY_RETRIEVE_BY_BLACKLIST = "selectDeviceModelByBlacklist";
	public static final String QUERY_RETRIEVE_BY_WHITELIST = "selectDeviceModelByWhitelist";

	public static final String PARAM_NAME = "deviceModelName";
	public static final String PARAM_BRAND_ID = "deviceBrandId";
	public static final String PARAM_IS_BL = "isBl";
	public static final String PARAM_IS_WL = "isWl";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_MODEL_ID")
	private Long deviceModelId;

	@Column(name = "DEVICE_MODEL_NAME")
	private String deviceModelName;

	// bi-directional many-to-one association to RefDeviceBrand
	@ManyToOne
	@JoinColumn(name = "DEVICE_BRAND_ID")
	private RefDeviceBrand refDeviceBrand;

	// bi-directional many-to-one association to SdpDevice
	@OneToMany(mappedBy = "refDeviceModel")
	private List<SdpDevice> sdpDevices;

	public RefDeviceModel() {
		sdpDevices = new ArrayList<SdpDevice>();
	}

	public Long getDeviceModelId() {
		return this.deviceModelId;
	}

	public void setDeviceModelId(Long deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public String getDeviceModelName() {
		return this.deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public RefDeviceBrand getRefDeviceBrand() {
		return this.refDeviceBrand;
	}

	public void setRefDeviceBrand(RefDeviceBrand refDeviceBrand) {
		this.refDeviceBrand = refDeviceBrand;
	}

	public List<SdpDevice> getSdpDevices() {
		return this.sdpDevices;
	}

	public void setSdpDevices(List<SdpDevice> sdpDevices) {
		this.sdpDevices = sdpDevices;
	}

}