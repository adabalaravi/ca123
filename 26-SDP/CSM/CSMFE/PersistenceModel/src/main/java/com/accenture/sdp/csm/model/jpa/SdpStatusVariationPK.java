package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The persistent class for the SDP_STATUS_VARIATION database table.
 * 
 */

@Embeddable
public class SdpStatusVariationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ENTITY_ID")
	private Long entityId;
	@Column(name = "PREVIOUS_STATUS_ID")
	private Long previousStatusId;
	@Column(name = "NEXT_STATUS_ID")
	private Long nextStatusId;

	public SdpStatusVariationPK() {
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getPreviousStatusId() {
		return previousStatusId;
	}

	public void setPreviousStatusId(Long previousStatusId) {
		this.previousStatusId = previousStatusId;
	}

	public Long getNextStatusId() {
		return nextStatusId;
	}

	public void setNextStatusId(Long nextStatusId) {
		this.nextStatusId = nextStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result + ((nextStatusId == null) ? 0 : nextStatusId.hashCode());
		result = prime * result + ((previousStatusId == null) ? 0 : previousStatusId.hashCode());
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
		SdpStatusVariationPK other = (SdpStatusVariationPK) obj;
		if (getEntityId() == null) {
			if (other.getEntityId() != null) {
				return false;
			}
		} else if (!getEntityId().equals(other.getEntityId())) {
			return false;
		}
		if (getNextStatusId() == null) {
			if (other.getNextStatusId() != null) {
				return false;
			}
		} else if (!getNextStatusId().equals(other.getNextStatusId())) {
			return false;
		}
		if (getPreviousStatusId() == null) {
			if (other.getPreviousStatusId() != null) {
				return false;
			}
		} else if (!getPreviousStatusId().equals(other.getPreviousStatusId())) {
			return false;
		}
		return true;
	}

}
