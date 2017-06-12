package com.accenture.ams.db.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_DEVICE database table.
 * 
 */
public class AccountDeviceAMS implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long userId; // PK
	private String deviceId; // PK
	private String platformId;
	private AccountUser user;
	private Long type;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public AccountUser getUser() {
		return user;
	}

	public void setUser(AccountUser user) {
		this.user = user;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public AccountDeviceAMS() {
	}

	// public boolean equals(Object other) {
	// if (this == other) {
	// return true;
	// }
	// if (!(other instanceof AccountDeviceAMS)) {
	// return false;
	// }
	// AccountDeviceAMS castOther = (AccountDeviceAMS)other;
	// return new EqualsBuilder()
	// .append(this.getDeviceId(), castOther.getDeviceId())
	// .isEquals();
	// }
	//
	// public int hashCode() {
	// return new HashCodeBuilder()
	// .append(getDeviceId())
	// .toHashCode();
	// }
	//
	// public String toString() {
	// return new ToStringBuilder(this)
	// .append("deviceId", getDeviceId())
	// .toString();
	// }
}