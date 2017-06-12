package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the AVS_LNK_OFFER_DIGITAL_GOOD database table.
 * 
 */
@Embeddable
public class AvsLnkOfferDigitalGoodPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "offer_id")
	private Long offerId;

	@Column(name = "digital_good_id")
	private Long digitalGoodId;

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getDigitalGoodId() {
		return digitalGoodId;
	}

	public void setDigitalGoodId(Long digitalGoodId) {
		this.digitalGoodId = digitalGoodId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((digitalGoodId == null) ? 0 : digitalGoodId.hashCode());
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
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
		AvsLnkOfferDigitalGoodPK other = (AvsLnkOfferDigitalGoodPK) obj;
		if (digitalGoodId == null) {
			if (other.digitalGoodId != null) {
				return false;
			}
		} else if (!digitalGoodId.equals(other.digitalGoodId)) {
			return false;
		}
		if (offerId == null) {
			if (other.offerId != null) {
				return false;
			}
		} else if (!offerId.equals(other.offerId)) {
			return false;
		}
		return true;
	}

}