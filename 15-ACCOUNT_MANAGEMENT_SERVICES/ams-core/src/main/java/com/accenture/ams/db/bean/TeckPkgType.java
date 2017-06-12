package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the TECK_PKG_TYPE database table.
 * 
 * @author BEA Workshop
 */
public class TeckPkgType  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String packageType;
//	private java.sql.Timestamp creationDate;
	private String description;
//	private java.sql.Timestamp updateDate;
	private java.util.Set<TechnicalPackage> technicalPackages;

    public TeckPkgType() {
    }

	public String getPackageType() {
		return this.packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
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

	//bi-directional many-to-one association to TechnicalPackage
	public java.util.Set<TechnicalPackage> getTechnicalPackages() {
		return this.technicalPackages;
	}
	public void setTechnicalPackages(java.util.Set<TechnicalPackage> technicalPackages) {
		this.technicalPackages = technicalPackages;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TeckPkgType)) {
			return false;
		}
		TeckPkgType castOther = (TeckPkgType)other;
		return new EqualsBuilder()
			.append(this.getPackageType(), castOther.getPackageType())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPackageType())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("packageType", getPackageType())
			.toString();
	}
}