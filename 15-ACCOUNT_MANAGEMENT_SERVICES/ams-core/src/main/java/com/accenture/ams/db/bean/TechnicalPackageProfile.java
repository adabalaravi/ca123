package com.accenture.ams.db.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * The persistent class for the TECHNICAL_PACKAGE database table.
 * 
 */
public class TechnicalPackageProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long packageId;
	private String packageName;
	private String packageType;
	
	
	public String getPackageId() {
		return packageId.toString();
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
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
