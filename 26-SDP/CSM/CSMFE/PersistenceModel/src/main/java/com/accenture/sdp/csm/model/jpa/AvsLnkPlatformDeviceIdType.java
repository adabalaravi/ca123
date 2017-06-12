package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the AVS_LNK_PLATFORM_DEVICE_ID_TYPE database table.
 * 
 */
@Entity
@Table(name = "AVS_LNK_PLATFORM_DEVICE_ID_TYPE")
public class AvsLnkPlatformDeviceIdType implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AvsLnkPlatformDeviceIdTypePK id;

	public AvsLnkPlatformDeviceIdTypePK getId() {
		return id;
	}

	public void setId(AvsLnkPlatformDeviceIdTypePK id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AvsLnkPlatformDeviceIdType other = (AvsLnkPlatformDeviceIdType) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}