package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SDP_SOLUTION_OFFER_EXTERNAL_ID database table.
 * 
 */
@Embeddable
public class SdpSolutionOfferExternalIdPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EXTERNAL_PLATFORM_NAME")
	private String externalPlatformName;

	@Column(name = "SOLUTION_OFFER_ID")
	private Long solutionOfferId;

	public SdpSolutionOfferExternalIdPK() {
	}

	public String getExternalPlatformName() {
		return externalPlatformName;
	}

	public void setExternalPlatformName(String externalPlatformName) {
		this.externalPlatformName = externalPlatformName;
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((externalPlatformName == null) ? 0 : externalPlatformName.hashCode());
		result = prime * result + ((solutionOfferId == null) ? 0 : solutionOfferId.hashCode());
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
		SdpSolutionOfferExternalIdPK other = (SdpSolutionOfferExternalIdPK) obj;
		if (externalPlatformName == null) {
			if (other.externalPlatformName != null) {
				return false;
			}
		} else if (!externalPlatformName.equals(other.externalPlatformName)) {
			return false;
		}
		if (solutionOfferId == null) {
			if (other.solutionOfferId != null) {
				return false;
			}
		} else if (!solutionOfferId.equals(other.solutionOfferId)) {
			return false;
		}
		return true;
	}

}