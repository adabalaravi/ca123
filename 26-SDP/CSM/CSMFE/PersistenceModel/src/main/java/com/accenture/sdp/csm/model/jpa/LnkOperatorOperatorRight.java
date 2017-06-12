package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the LNK_OPERATOR_OPERATOR_RIGHT database table.
 * 
 */
@Entity
@Table(name = "LNK_OPERATOR_OPERATOR_RIGHT")
public class LnkOperatorOperatorRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LnkOperatorOperatorRightPK id;

	public LnkOperatorOperatorRight() {
	}

	public LnkOperatorOperatorRightPK getId() {
		return id;
	}

	public void setId(LnkOperatorOperatorRightPK id) {
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
		LnkOperatorOperatorRight other = (LnkOperatorOperatorRight) obj;
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