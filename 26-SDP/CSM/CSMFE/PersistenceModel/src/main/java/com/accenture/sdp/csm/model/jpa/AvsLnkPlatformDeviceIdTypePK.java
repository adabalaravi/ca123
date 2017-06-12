package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AVS_LNK_PLATFORM_DEVICE_ID_TYPE database table.
 * 
 */
@Embeddable
public class AvsLnkPlatformDeviceIdTypePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "platform_id")
	private int platformId;

	@Column(name = "device_type_id")
	private int typeId;

	public AvsLnkPlatformDeviceIdTypePK() {
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + platformId;
		result = prime * result + typeId;
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
		AvsLnkPlatformDeviceIdTypePK other = (AvsLnkPlatformDeviceIdTypePK) obj;
		if (platformId != other.platformId) {
			return false;
		}
		if (typeId != other.typeId) {
			return false;
		}
		return true;
	}

}