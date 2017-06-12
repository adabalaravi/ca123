package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the VOUCHER_PROMO database table.
 * 
 * @author BEA Workshop
 */
public class VoucherPromo  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String voucherId;
//	private java.sql.Timestamp creationDate;
	private Integer statusId;
//	private java.sql.Timestamp updateDate;
	private Promo promo;

    public VoucherPromo() {
    }

	public String getVoucherId() {
		return this.voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public Integer getStatusId() {
		return this.statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

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
		if (!(other instanceof VoucherPromo)) {
			return false;
		}
		VoucherPromo castOther = (VoucherPromo)other;
		return new EqualsBuilder()
			.append(this.getVoucherId(), castOther.getVoucherId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getVoucherId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("voucherId", getVoucherId())
			.toString();
	}
}