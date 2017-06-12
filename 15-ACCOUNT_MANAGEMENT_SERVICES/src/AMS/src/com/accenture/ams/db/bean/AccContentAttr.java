package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACC_CONTENT_ATTR database table.
 * 
 * @author BEA Workshop
 */
public class AccContentAttr  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long childServiceAttributeId;
	private String attributeValue;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private AccountContent accountContent;
	private AttributeDetail attributeDetail;

    public AccContentAttr() {
    }

	public Long getChildServiceAttributeId() {
		return this.childServiceAttributeId;
	}
	public void setChildServiceAttributeId(Long childServiceAttributeId) {
		this.childServiceAttributeId = childServiceAttributeId;
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

	//bi-directional many-to-one association to AccountContent
	public AccountContent getAccountContent() {
		return this.accountContent;
	}
	public void setAccountContent(AccountContent accountContent) {
		this.accountContent = accountContent;
	}

	//bi-directional many-to-one association to AttributeDetail
	public AttributeDetail getAttributeDetail() {
		return this.attributeDetail;
	}
	public void setAttributeDetail(AttributeDetail attributeDetail) {
		this.attributeDetail = attributeDetail;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccContentAttr)) {
			return false;
		}
		AccContentAttr castOther = (AccContentAttr)other;
		return new EqualsBuilder()
			.append(this.getChildServiceAttributeId(), castOther.getChildServiceAttributeId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getChildServiceAttributeId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("childServiceAttributeId", getChildServiceAttributeId())
			.toString();
	}
}
