package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the ACCOUNT_PRODUCT database table.
 * 
 * @author BEA Workshop
 */
public class AccountProductPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long solutionOfferId;
	private Long username;

    public AccountProductPK() {
    }

	public Long getSolutionOfferId() {
		return this.solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public Long getUsername() {
		return this.username;
	}
	public void setUsername(Long username) {
		this.username = username;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountProductPK)) {
			return false;
		}
		AccountProductPK castOther = (AccountProductPK)other;
		return new EqualsBuilder()
			.append(this.getSolutionOfferId(), castOther.getSolutionOfferId())
			.append(this.getUsername(), castOther.getUsername())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSolutionOfferId())
			.append(getUsername())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("solutionOfferId", getSolutionOfferId())
			.append("username", getUsername())
			.toString();
	}
}