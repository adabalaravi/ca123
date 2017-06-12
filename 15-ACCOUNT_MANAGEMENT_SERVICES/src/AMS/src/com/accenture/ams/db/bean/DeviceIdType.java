package com.accenture.ams.db.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the DEVICE_ID_TYPE database table.
 * 
 */
public class DeviceIdType implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long typeId;
	private String value;
	private String typeDescription;
	private java.util.Set<AccountDeviceAMS> device;

	public DeviceIdType() {
	}

	public java.util.Set<AccountDeviceAMS> getDevice() {
		return device;
	}

	public void setDevice(java.util.Set<AccountDeviceAMS> device) {
		this.device = device;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DeviceIdType)) {
			return false;
		}
		DeviceIdType castOther = (DeviceIdType) other;
		return new EqualsBuilder().append(this.getTypeId(),
				castOther.getTypeId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getTypeId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("typeId", getTypeId())
				.toString();
	}
}