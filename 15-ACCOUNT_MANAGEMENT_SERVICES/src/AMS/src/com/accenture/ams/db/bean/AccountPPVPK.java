package com.accenture.ams.db.bean;

import java.io.Serializable;

public class AccountPPVPK implements Serializable{
	private String crmAccountId;
	private Long liveContentId;
	private Long packageId;
	
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public AccountPPVPK() {
		
	}
	
	public String getCrmAccountId() {
		return crmAccountId;
	}

	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	public Long getLiveContentId() {
		return liveContentId;
	}
	public void setLiveContentId(Long liveContentId) {
		this.liveContentId = liveContentId;
	}
	
	
	
}
