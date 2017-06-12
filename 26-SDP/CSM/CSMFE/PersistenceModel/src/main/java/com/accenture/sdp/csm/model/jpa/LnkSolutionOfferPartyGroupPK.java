package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the LNK_SOLUTION_OFFER_PARTY_GROUP database table.
 * 
 */
@Embeddable
public class LnkSolutionOfferPartyGroupPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SOLUTION_OFFER_ID")
	private long solutionOfferId;

	@Column(name = "PARTY_GROUP_ID")
	private long partyGroupId;

	public LnkSolutionOfferPartyGroupPK() {
	}

	public long getSolutionOfferId() {
		return this.solutionOfferId;
	}

	public void setSolutionOfferId(long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public long getPartyGroupId() {
		return this.partyGroupId;
	}

	public void setPartyGroupId(long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (partyGroupId ^ (partyGroupId >>> 32));
		result = prime * result + (int) (solutionOfferId ^ (solutionOfferId >>> 32));
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
		LnkSolutionOfferPartyGroupPK other = (LnkSolutionOfferPartyGroupPK) obj;
		if (getPartyGroupId() != other.getPartyGroupId()) {
			return false;
		}
		if (getSolutionOfferId() != other.getSolutionOfferId()) {
			return false;
		}
		return true;
	}
}