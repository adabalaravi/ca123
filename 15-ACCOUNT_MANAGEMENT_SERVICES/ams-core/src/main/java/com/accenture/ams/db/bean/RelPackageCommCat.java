package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the REL_PACKAGE_COMM_CAT database table.
 * 
 * @author BEA Workshop
 */
public class RelPackageCommCat  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private RelPackageCommCatPK compId;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private CommercialCategory commercialCategory;
	private RefTechnicalPackage refTechnicalPackage;
	private TechnicalPackage technicalPackage;
	
    public RelPackageCommCat() {
    }

	public RelPackageCommCatPK getCompId() {
		return this.compId;
	}
	public void setCompId(RelPackageCommCatPK compId) {
		this.compId = compId;
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

	//bi-directional many-to-one association to CommercialCategory
	public CommercialCategory getCommercialCategory() {
		return this.commercialCategory;
	}
	public void setCommercialCategory(CommercialCategory commercialCategory) {
		this.commercialCategory = commercialCategory;
	}

	//bi-directional many-to-one association to RefTechnicalPackage
	public RefTechnicalPackage getRefTechnicalPackage() {
		return this.refTechnicalPackage;
	}
	public void setRefTechnicalPackage(RefTechnicalPackage refTechnicalPackage) {
		this.refTechnicalPackage = refTechnicalPackage;
	}

	//bi-directional many-to-one association to TechnicalPackage
	public TechnicalPackage getTechnicalPackage() {
		return this.technicalPackage;
	}
	public void setTechnicalPackage(TechnicalPackage technicalPackage) {
		this.technicalPackage = technicalPackage;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelPackageCommCat)) {
			return false;
		}
		RelPackageCommCat castOther = (RelPackageCommCat)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}