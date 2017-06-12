package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the LNK_SOLUTION_PARTY_GROUP database table.
 * 
 */
@Embeddable
public class LnkPartyPartyGroupPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "PARTY_ID")
	private long partyId;

	@Column(name = "PARTY_GROUP_ID")
	private long partyGroupId;

	public LnkPartyPartyGroupPK() {
	}

	public long getPartyId() {
		return partyId;
	}

	public void setPartyId(long partyId) {
		this.partyId = partyId;
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
		result = prime * result + (int) (partyId ^ (partyId >>> 32));
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
		LnkPartyPartyGroupPK other = (LnkPartyPartyGroupPK) obj;
		if (getPartyGroupId() != other.getPartyGroupId()) {
			return false;
		}
		if (getPartyId() != other.getPartyId()) {
			return false;
		}
		return true;
	}
}