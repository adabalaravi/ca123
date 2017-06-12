package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_ATTRIBUTE database table.
 * 
 * @author BEA Workshop
 */

public class AccountAttributeProfile  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long userAttributeId;
	private String attributeValue;
	private AttributeDetailProfile attributeDetail;

    public AccountAttributeProfile() {
    }

	public Long getUserAttributeId() {
		return this.userAttributeId;
	}
	public void setUserAttributeId(Long userAttributeId) {
		this.userAttributeId = userAttributeId;
	}

	public String getAttributeValue() {
		return this.attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	

	

	//bi-directional many-to-one association to AttributeDetail
	public AttributeDetailProfile getAttributeDetail() {
		return this.attributeDetail;
	}
	public void setAttributeDetail(AttributeDetailProfile attributeDetail) {
		this.attributeDetail = attributeDetail;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountAttribute)) {
			return false;
		}
		AccountAttribute castOther = (AccountAttribute)other;
		return new EqualsBuilder()
			.append(this.getUserAttributeId(), castOther.getUserAttributeId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserAttributeId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("userAttributeId", getUserAttributeId())
			.toString();
	}
}