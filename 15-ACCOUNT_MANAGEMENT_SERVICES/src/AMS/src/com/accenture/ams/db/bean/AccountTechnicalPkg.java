package com.accenture.ams.db.bean;
import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_TECHNICAL_PKG database table.
 * 
 * @author BEA Workshop
 */
public class AccountTechnicalPkg  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private Long subscriptionOfferId;
	private Long userId;
	private Long techPackageId;
	private Long viewsNumber;
	private String techPackageValue;
	private Timestamp creationDate;
	private Timestamp validityPeriod;
	private String crmAccountId;
	private Timestamp updateDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTechPackageId() {
		return techPackageId;
	}

	public void setTechPackageId(Long techPackageId) {
		this.techPackageId = techPackageId;
	}

	public Long getViewsNumber() {
		return viewsNumber;
	}

	public void setViewsNumber(Long viewsNumber) {
		this.viewsNumber = viewsNumber;
	}

	public String getTechPackageValue() {
		return techPackageValue;
	}

	public void setTechPackageValue(String techPackageValue) {
		this.techPackageValue = techPackageValue;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Timestamp validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getCrmAccountId() {
		return crmAccountId;
	}

	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Long getSubscriptionOfferId() {
		return subscriptionOfferId;
	}

	public void setSubscriptionOfferId(Long subscriptionOfferId) {
		this.subscriptionOfferId = subscriptionOfferId;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountTechnicalPkg)) {
			return false;
		}
		AccountTechnicalPkg castOther = (AccountTechnicalPkg)other;
		return new EqualsBuilder()
			.append(this.getSubscriptionOfferId(), castOther.getSubscriptionOfferId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSubscriptionOfferId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("subscriptionOfferId", getSubscriptionOfferId())
			.toString();
	}
}
