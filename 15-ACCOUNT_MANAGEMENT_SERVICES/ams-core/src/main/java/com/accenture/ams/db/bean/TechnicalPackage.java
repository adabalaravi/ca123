package com.accenture.ams.db.bean;
import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the TECHNICAL_PACKAGE database table.
 * 
 * @author BEA Workshop
 */
public class TechnicalPackage  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long packageId;
	private String packageName;
	private String packageType;
	private String packageDescription;	
	private String isEnabled;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private String externalId;
//	private java.util.Set<ChannelTechnicalPkg> channelTechnicalPkgs;
//	private java.util.Set<RelContentTechnical> relContentTechnicals;
//	private java.util.Set<RelPackageCommCat> relPackageCommCats;
//	private java.util.Set<AccountTechnicalPkg> accountTechnicalPkgs;
//	private TeckPkgType teckPkgType;

    public TechnicalPackage() {
    }

    
	public String getPackageType() {
		return packageType;
	}


	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	public Long getPackageId() {
		return this.packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
/*
	public java.sql.Timestamp getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(java.sql.Timestamp creationDate) {
		this.creationDate = creationDate;
	}
*/
	public String getIsEnabled() {
		return this.isEnabled;
	}
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getPackageDescription() {
		return this.packageDescription;
	}
	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}

	public String getPackageName() {
		return this.packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
/*	
	public java.sql.Timestamp getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}
*/
//	//bi-directional many-to-one association to ChannelTechnicalPkg
//	public java.util.Set<ChannelTechnicalPkg> getChannelTechnicalPkgs() {
//		return this.channelTechnicalPkgs;
//	}
//	public void setChannelTechnicalPkgs(java.util.Set<ChannelTechnicalPkg> channelTechnicalPkgs) {
//		this.channelTechnicalPkgs = channelTechnicalPkgs;
//	}

//	//bi-directional many-to-one association to RelContentTechnical
//	public java.util.Set<RelContentTechnical> getRelContentTechnicals() {
//		return this.relContentTechnicals;
//	}
//	public void setRelContentTechnicals(java.util.Set<RelContentTechnical> relContentTechnicals) {
//		this.relContentTechnicals = relContentTechnicals;
//	}

//	//bi-directional many-to-one association to RelPackageCommCat
//	public java.util.Set<RelPackageCommCat> getRelPackageCommCats() {
//		return this.relPackageCommCats;
//	}
//	public void setRelPackageCommCats(java.util.Set<RelPackageCommCat> relPackageCommCats) {
//		this.relPackageCommCats = relPackageCommCats;
//	}

//	//bi-directional many-to-one association to TeckPkgType
//	public TeckPkgType getTeckPkgType() {
//		return this.teckPkgType;
//	}
//	public void setTeckPkgType(TeckPkgType teckPkgType) {
//		this.teckPkgType = teckPkgType;
//	}

//	public java.util.Set<AccountTechnicalPkg> getAccountTechnicalPkgs() {
//		return accountTechnicalPkgs;
//	}

//	public void setAccountTechnicalPkgs(
//			java.util.Set<AccountTechnicalPkg> accountTechnicalPkgs) {
//		this.accountTechnicalPkgs = accountTechnicalPkgs;
//	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TechnicalPackage)) {
			return false;
		}
		TechnicalPackage castOther = (TechnicalPackage)other;
		return new EqualsBuilder()
			.append(this.getPackageId(), castOther.getPackageId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPackageId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("packageId", getPackageId())
			.toString();
	}
}