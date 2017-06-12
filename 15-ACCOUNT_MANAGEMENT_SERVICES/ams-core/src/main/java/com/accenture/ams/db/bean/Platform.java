package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PLATFORM database table.
 * 
 * @author BEA Workshop
 */
public class Platform  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long platformId;
//	private java.sql.Timestamp creationDate;
	private String platformName;
	private java.sql.Timestamp updateDate;

    public Platform() {
    }

	public Long getPlatformId() {
		return this.platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getPlatformName() {
		return this.platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public java.sql.Timestamp getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Platform)) {
			return false;
		}
		Platform castOther = (Platform)other;
		return new EqualsBuilder()
			.append(this.getPlatformId(), castOther.getPlatformId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPlatformId())
			.toHashCode();
    }   
/*
	public String toString() {
		return new ToStringBuilder(this)
			.append("platformId", getPlatformId())
			.toString();
	}
	*/
}