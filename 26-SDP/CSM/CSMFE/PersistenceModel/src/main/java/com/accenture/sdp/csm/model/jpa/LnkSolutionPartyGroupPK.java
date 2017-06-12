package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the LNK_SOLUTION_PARTY_GROUP database table.
 * 
 */
@Embeddable
public class LnkSolutionPartyGroupPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SOLUTION_ID")
	private long solutionId;

	@Column(name = "PARTY_GROUP_ID")
	private long partyGroupId;

	public LnkSolutionPartyGroupPK() {
	}

	public long getSolutionId() {
		return this.solutionId;
	}

	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
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
		result = prime * result + (int) (solutionId ^ (solutionId >>> 32));
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
		LnkSolutionPartyGroupPK other = (LnkSolutionPartyGroupPK) obj;
		if (getPartyGroupId() != other.getPartyGroupId()) {
			return false;
		}
		if (getSolutionId() != other.getSolutionId()) {
			return false;
		}
		return true;
	}
}