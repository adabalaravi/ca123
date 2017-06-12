package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_USER database table.
 * 
 * @author BEA Workshop
 */
public class Profile  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String crmAccountId;
	private String smartcardNumber;
	private long statusId;
	private String securityPin;
	private Long crmAccountPurchaseBlockingThresholdValue;
	private java.util.Set<AccountAttributeProfile> accountAttributes;
	private java.util.Set<AccountTechnicalPkgProfile> accountTechnicalPkgs;
	private String streamingEnabled;
	private List<RetailerDomain> availableDomains;

    public List<RetailerDomain> getAvailableDomains() {
		return availableDomains;
	}

	public void setAvailableDomains(List<RetailerDomain> availableDomains) {
		this.availableDomains = availableDomains;
	}
	
    public Profile() {
    }
    
    public java.util.Set<AccountTechnicalPkgProfile> getAccountTechnicalPkgs() {
		return accountTechnicalPkgs;
	}



	public void setAccountTechnicalPkgs(
			java.util.Set<AccountTechnicalPkgProfile> accountTechnicalPkgs) {
		this.accountTechnicalPkgs = accountTechnicalPkgs;
	}

	public String getStreamingEnabled() {
		return streamingEnabled;
	}



	public void setStreamingEnabled(String streamingEnabled) {
		this.streamingEnabled = streamingEnabled;
	}



	public Long getUserId() {
		return this.userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	public String getCrmAccountId() {
		return this.crmAccountId;
	}
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	
	public String getSmartcardNumber() {
		return this.smartcardNumber;
	}
	public void setSmartcardNumber(String smartcardNumber) {
		this.smartcardNumber = smartcardNumber;
	}

	
	public long getStatusId() {
		return this.statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	
	public java.util.Set<AccountAttributeProfile> getAccountAttributes() {
		return accountAttributes;
	}

	public void setAccountAttributes(
			java.util.Set<AccountAttributeProfile> accountAttributes) {
		this.accountAttributes = accountAttributes;
	}



	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Profile)) {
			return false;
		}
		Profile castOther = (Profile)other;
		return new EqualsBuilder()
			.append(this.getUserId(), castOther.getUserId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("userId", getUserId())
			.toString();
	}
	
	public String getSecurityPin() {
		return securityPin;
	}

	public void setSecurityPin(String securityPin) {
		this.securityPin = securityPin;
	}

	public Long getCrmAccountPurchaseBlockingThresholdValue() {
		return crmAccountPurchaseBlockingThresholdValue;
	}

	public void setCrmAccountPurchaseBlockingThresholdValue(
			Long crmAccountPurchaseBlockingThresholdValue) {
		this.crmAccountPurchaseBlockingThresholdValue = crmAccountPurchaseBlockingThresholdValue;
	}
}