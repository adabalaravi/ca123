package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the REF_TECHNICAL_PACKAGE database table.
 * 
 * @author BEA Workshop
 */
public class RefTechnicalPackage  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long packageValueId;
//	private java.sql.Timestamp creationDate;
	private long packageNumVal;
	private String packageValue;
//	private java.sql.Timestamp updateDate;
	private java.util.Set<RelPackageCommCat> relPackageCommCats;

    public RefTechnicalPackage() {
    }

	public Long getPackageValueId() {
		return this.packageValueId;
	}
	public void setPackageValueId(Long packageValueId) {
		this.packageValueId = packageValueId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public long getPackageNumVal() {
		return this.packageNumVal;
	}
	public void setPackageNumVal(long packageNumVal) {
		this.packageNumVal = packageNumVal;
	}

	public String getPackageValue() {
		return this.packageValue;
	}
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to RelPackageCommCat
	public java.util.Set<RelPackageCommCat> getRelPackageCommCats() {
		return this.relPackageCommCats;
	}
	public void setRelPackageCommCats(java.util.Set<RelPackageCommCat> relPackageCommCats) {
		this.relPackageCommCats = relPackageCommCats;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RefTechnicalPackage)) {
			return false;
		}
		RefTechnicalPackage castOther = (RefTechnicalPackage)other;
		return new EqualsBuilder()
			.append(this.getPackageValueId(), castOther.getPackageValueId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPackageValueId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("packageValueId", getPackageValueId())
			.toString();
	}
}