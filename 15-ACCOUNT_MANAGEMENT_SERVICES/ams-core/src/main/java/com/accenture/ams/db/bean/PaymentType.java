package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PAYMENT_TYPE database table.
 * 
 * @author BEA Workshop
 */
public class PaymentType implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long paymentTypeId;
	// private java.sql.Timestamp creationDate;
	private String paymentMethod;

	// private java.sql.Timestamp updateDate;

	public PaymentType() {
	}

	public Long getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	// public java.sql.Timestamp getCreationDate() {
	// return this.creationDate;
	// }
	// public void setCreationDate(java.sql.Timestamp creationDate) {
	// this.creationDate = creationDate;
	// }

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	// public java.sql.Timestamp getUpdateDate() {
	// return this.updateDate;
	// }
	// public void setUpdateDate(java.sql.Timestamp updateDate) {
	// this.updateDate = updateDate;
	// }

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PaymentType)) {
			return false;
		}
		PaymentType castOther = (PaymentType) other;
		return new EqualsBuilder().append(this.getPaymentTypeId(),
				castOther.getPaymentTypeId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getPaymentTypeId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("paymentTypeId",
				getPaymentTypeId()).toString();
	}
}