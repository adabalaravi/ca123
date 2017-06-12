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
public class AccountAttribute  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long userAttributeId;
	private String attributeValue;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private Long userId;
	private Long attributeDetailId;
//	private AccountUser accountUser;
//	private AttributeDetail attributeDetail;

    public AccountAttribute() {
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAttributeDetailId() {
		return attributeDetailId;
	}

	public void setAttributeDetailId(Long attributeDetailId) {
		this.attributeDetailId = attributeDetailId;
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

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}
/*
	//bi-directional many-to-one association to AccountUser
	public AccountUser getAccountUser() {
		return this.accountUser;
	}
	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}

	//bi-directional many-to-one association to AttributeDetail
	public AttributeDetail getAttributeDetail() {
		return this.attributeDetail;
	}
	public void setAttributeDetail(AttributeDetail attributeDetail) {
		this.attributeDetail = attributeDetail;
	}
*/
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
