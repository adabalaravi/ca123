package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * The primary key class for the SDP_SUBSCRIPTION_DETAIL database table.
 * 
 */

public class SdpSubscriptionDetailPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SUBSCRIPTION_ID")
	private Long subscriptionId;

	@Column(name = "PACKAGE_ID")
	private Long packageId;

	public SdpSubscriptionDetailPK() {
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Long getSubscriptionId() {
		return this.subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packageId == null) ? 0 : packageId.hashCode());
		result = prime * result + ((subscriptionId == null) ? 0 : subscriptionId.hashCode());
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
		SdpSubscriptionDetailPK other = (SdpSubscriptionDetailPK) obj;
		if (getPackageId() == null) {
			if (other.getPackageId() != null) {
				return false;
			}
		} else if (!getPackageId().equals(other.getPackageId())) {
			return false;
		}
		if (getSubscriptionId() == null) {
			if (other.getSubscriptionId() != null) {
				return false;
			}
		} else if (!getSubscriptionId().equals(other.getSubscriptionId())) {
			return false;
		}
		return true;
	}

}