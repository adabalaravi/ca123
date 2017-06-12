package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the ACCOUNT_PROMO database table.
 * 
 * @author BEA Workshop
 */
public class AccountPromoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer promoId;
	private java.sql.Timestamp startDate;
	private Long username;

    public AccountPromoPK() {
    }

	public Integer getPromoId() {
		return this.promoId;
	}
	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

	public java.sql.Timestamp getStartDate() {
		return this.startDate;
	}
	public void setStartDate(java.sql.Timestamp startDate) {
		this.startDate = startDate;
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
		if (!(other instanceof AccountPromoPK)) {
			return false;
		}
		AccountPromoPK castOther = (AccountPromoPK)other;
		return new EqualsBuilder()
			.append(this.getPromoId(), castOther.getPromoId())
			.append(this.getStartDate(), castOther.getStartDate())
			.append(this.getUsername(), castOther.getUsername())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPromoId())
			.append(getStartDate())
			.append(getUsername())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("promoId", getPromoId())
			.append("startDate", getStartDate())
			.append("username", getUsername())
			.toString();
	}
}