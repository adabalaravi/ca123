package com.accenture.ams.db.bean;

import java.io.Serializable;
/**
 * The persistent class for the ACCOUNT_TECHNICAL_PKG database table.
 * 
 */
public class AccountTechnicalPkgProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String techPackageId;
	private Long userId;
	private TechnicalPackageProfile technicalPackage;

	public String getTechPackageId() {
		return techPackageId;
	}

	public void setTechPackageId(String techPackageId) {
		this.techPackageId = techPackageId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public TechnicalPackageProfile getTechnicalPackage() {
		return technicalPackage;
	}

	public void setTechnicalPackage(TechnicalPackageProfile technicalPackage) {
		this.technicalPackage = technicalPackage;
	}

	
	
	
	
	

}
