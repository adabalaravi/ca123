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

/**
 * The persistent class for the ref_device_uuid_type database table.
 * 
 */
@Entity
@Table(name = "ref_device_uuid_type")
@NamedQueries({ @NamedQuery(name = RefDeviceUuidType.QUERY_RETRIEVE_BY_NAME, query = "select t from RefDeviceUuidType t where t.deviceUuidTypeName=:deviceUuidTypeName") })
public class RefDeviceUuidType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_NAME = "selectDeviceUuidTypeByName";

	public static final String PARAM_NAME = "deviceUuidTypeName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_UUID_TYPE_ID")
	private Long deviceUuidTypeId;

	@Column(name = "DEVICE_UUID_TYPE_DESCRIPTION")
	private String deviceUuidTypeDescription;

	@Column(name = "DEVICE_UUID_TYPE_NAME")
	private String deviceUuidTypeName;

	@Column(name = "DEVICE_UUID_TYPE_PATTERN")
	private String deviceUuidTypePattern;

	// bi-directional many-to-one association to SdpDevice
	@OneToMany(mappedBy = "refDeviceUuidType")
	private List<SdpDevice> sdpDevices;

	public RefDeviceUuidType() {
		sdpDevices = new ArrayList<SdpDevice>();
	}

	public Long getDeviceUuidTypeId() {
		return this.deviceUuidTypeId;
	}

	public void setDeviceUuidTypeId(Long deviceUuidTypeId) {
		this.deviceUuidTypeId = deviceUuidTypeId;
	}

	public String getDeviceUuidTypeDescription() {
		return this.deviceUuidTypeDescription;
	}

	public void setDeviceUuidTypeDescription(String deviceUuidTypeDescription) {
		this.deviceUuidTypeDescription = deviceUuidTypeDescription;
	}

	public String getDeviceUuidTypeName() {
		return this.deviceUuidTypeName;
	}

	public void setDeviceUuidTypeName(String deviceUuidTypeName) {
		this.deviceUuidTypeName = deviceUuidTypeName;
	}

	public String getDeviceUuidTypePattern() {
		return deviceUuidTypePattern;
	}

	public void setDeviceUuidTypePattern(String deviceUuidTypePattern) {
		this.deviceUuidTypePattern = deviceUuidTypePattern;
	}

	public List<SdpDevice> getSdpDevices() {
		return this.sdpDevices;
	}

	public void setSdpDevices(List<SdpDevice> sdpDevices) {
		this.sdpDevices = sdpDevices;
	}

}