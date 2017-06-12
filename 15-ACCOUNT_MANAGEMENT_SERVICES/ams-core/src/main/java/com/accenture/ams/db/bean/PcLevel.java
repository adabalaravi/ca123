package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PC_LEVEL database table.
 * 
 * @author BEA Workshop
 */
public class PcLevel  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String value;
//	private java.sql.Timestamp creationDate;
	private String description;
//	private java.sql.Timestamp updateDate; 

    public PcLevel() {
    }

	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PcLevel)) {
			return false;
		}
		PcLevel castOther = (PcLevel)other;
		return new EqualsBuilder()
			.append(this.getValue(), castOther.getValue())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getValue())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("value", getValue())
			.toString();
	}
}