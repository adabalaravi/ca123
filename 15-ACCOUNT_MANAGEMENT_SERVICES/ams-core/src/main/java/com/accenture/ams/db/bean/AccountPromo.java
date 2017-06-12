package com.accenture.ams.db.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_PROMO database table.
 * 
 * @author BEA Workshop
 */
public class AccountPromo  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private AccountPromoPK compId;
//	private java.sql.Timestamp creationDate;
	private java.sql.Timestamp endDate;
	private int statusId;
//	private java.sql.Timestamp updateDate;
	private String voucher;
	private AccountUser accountUser;
	private Promo promo;

    public AccountPromo() {
    }

	public AccountPromoPK getCompId() {
		return this.compId;
	}
	public void setCompId(AccountPromoPK compId) {
		this.compId = compId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public java.sql.Timestamp getEndDate() {
		return this.endDate;
	}
	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getStatusId() {
		return this.statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	public String getVoucher() {
		return this.voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	//bi-directional many-to-one association to AccountUser
	public AccountUser getAccountUser() {
		return this.accountUser;
	}
	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}

	//bi-directional many-to-one association to Promo
	public Promo getPromo() {
		return this.promo;
	}
	public void setPromo(Promo promo) {
		this.promo = promo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountPromo)) {
			return false;
		}
		AccountPromo castOther = (AccountPromo)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}
