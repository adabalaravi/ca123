package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * The primary key class for the SDP_SERVICE_Variant_OPERATION database table.
 * 
 */

public class SdpServiceVariantOperationPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SERVICE_VARIANT_ID")
	private Long serviceVariantId;

	@Column(name = "METHOD_NAME")
	private String methodName;

	public SdpServiceVariantOperationPK() {
	}

	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}

	public Long getServiceVariantId() {
		return this.serviceVariantId;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + ((serviceVariantId == null) ? 0 : serviceVariantId.hashCode());
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
		SdpServiceVariantOperationPK other = (SdpServiceVariantOperationPK) obj;
		if (getMethodName() == null) {
			if (other.getMethodName() != null) {
				return false;
			}
		} else if (!getMethodName().equals(other.getMethodName())) {
			return false;
		}
		if (getServiceVariantId() == null) {
			if (other.getServiceVariantId() != null) {
				return false;
			}
		} else if (!getServiceVariantId().equals(other.getServiceVariantId())) {
			return false;
		}
		return true;
	}
}