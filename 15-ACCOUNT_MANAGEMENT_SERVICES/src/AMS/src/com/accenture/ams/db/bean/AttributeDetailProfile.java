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
public class AttributeDetailProfile  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long attributeDetailId;
	private String attributeDetailName;

    public AttributeDetailProfile() {
    }

	public Long getAttributeDetailId() {
		return this.attributeDetailId;
	}
	public void setAttributeDetailId(Long attributeDetailId) {
		this.attributeDetailId = attributeDetailId;
	}


	public String getAttributeDetailName() {
		return this.attributeDetailName;
	}
	public void setAttributeDetailName(String attributeDetailName) {
		this.attributeDetailName = attributeDetailName;
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