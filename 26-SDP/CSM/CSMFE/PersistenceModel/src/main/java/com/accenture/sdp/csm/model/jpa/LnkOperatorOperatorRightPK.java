package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the LNK_OPERATOR_OPERATOR_RIGHT database table.
 * 
 */
@Embeddable
public class LnkOperatorOperatorRightPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	@Column(name = "OPERATOR_RIGHT_ID")
	private long operatorRightId;

	public LnkOperatorOperatorRightPK() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getOperatorRightId() {
		return operatorRightId;
	}

	public void setOperatorRightId(long operatorRightId) {
		this.operatorRightId = operatorRightId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (operatorRightId ^ (operatorRightId >>> 32));
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;}
		if (obj == null){
			return false;}
		if (getClass() != obj.getClass()){
			return false;}
		LnkOperatorOperatorRightPK other = (LnkOperatorOperatorRightPK) obj;
		if (operatorRightId != other.operatorRightId){
			return false;}
		if (username == null) {
			if (other.username != null){
				return false;}
		} else if (!username.equals(other.username)){
			return false;}
		return true;
	}

}