package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ATTRIBUTE_DETAIL database table.
 * 
 * @author BEA Workshop
 */
public class AttributeDetail  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long attributeDetailId;
	private String attributeDetailDescription;
	private String attributeDetailName;
//	private java.sql.Timestamp creationDate;
	private String defaultAttributeValue;
	private java.sql.Timestamp updateDate;
	private java.util.Set<AccContentAttr> accContentAttrs;
	private java.util.Set<AccountAttribute> accountAttributes;

    public AttributeDetail() {
    }

	public Long getAttributeDetailId() {
		return this.attributeDetailId;
	}
	public void setAttributeDetailId(Long attributeDetailId) {
		this.attributeDetailId = attributeDetailId;
	}

	public String getAttributeDetailDescription() {
		return this.attributeDetailDescription;
	}
	public void setAttributeDetailDescription(String attributeDetailDescription) {
		this.attributeDetailDescription = attributeDetailDescription;
	}

	public String getAttributeDetailName() {
		return this.attributeDetailName;
	}
	public void setAttributeDetailName(String attributeDetailName) {
		this.attributeDetailName = attributeDetailName;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getDefaultAttributeValue() {
		return this.defaultAttributeValue;
	}
	public void setDefaultAttributeValue(String defaultAttributeValue) {
		this.defaultAttributeValue = defaultAttributeValue;
	}

	public java.sql.Timestamp getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	//bi-directional many-to-one association to AccContentAttr
	public java.util.Set<AccContentAttr> getAccContentAttrs() {
		return this.accContentAttrs;
	}
	public void setAccContentAttrs(java.util.Set<AccContentAttr> accContentAttrs) {
		this.accContentAttrs = accContentAttrs;
	}

	//bi-directional many-to-one association to AccountAttribute
	public java.util.Set<AccountAttribute> getAccountAttributes() {
		return this.accountAttributes;
	}
	public void setAccountAttributes(java.util.Set<AccountAttribute> accountAttributes) {
		this.accountAttributes = accountAttributes;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AttributeDetail)) {
			return false;
		}
		AttributeDetail castOther = (AttributeDetail)other;
		return new EqualsBuilder()
			.append(this.getAttributeDetailId(), castOther.getAttributeDetailId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getAttributeDetailId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("attributeDetailId", getAttributeDetailId())
			.toString();
	}
}
