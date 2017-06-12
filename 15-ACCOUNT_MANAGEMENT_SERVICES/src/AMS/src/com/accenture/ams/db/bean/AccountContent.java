package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_CONTENT database table.
 * 
 * @author BEA Workshop
 */
public class AccountContent  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long subscriptionOfferId;
	private java.sql.Timestamp creationDate;
	private String crmaccountid;
	private java.sql.Timestamp endDate;
	private String externalId;
	private String offerDescription;
	private String offerName;
	private String smartcardNumber;
	private java.sql.Timestamp startDate;
	private String workOrderId;
	private java.util.Set<AccContentAttr> accContentAttrs;
	private String priceCategoryId="";
	private long contentId=0L;
	private StatusType statusType;

    public AccountContent() {
    }

	public String getPriceCategoryId() {
		return priceCategoryId;
	}

	public void setPriceCategoryId(String priceCategoryId) {
		this.priceCategoryId = priceCategoryId;
	}

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public Long getSubscriptionOfferId() {
		return this.subscriptionOfferId;
	}
	public void setSubscriptionOfferId(Long subscriptionOfferId) {
		this.subscriptionOfferId = subscriptionOfferId;
	}

	public java.sql.Timestamp getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(java.sql.Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getCrmaccountid() {
		return this.crmaccountid;
	}
	public void setCrmaccountid(String crmaccountid) {
		this.crmaccountid = crmaccountid;
	}

	public java.sql.Timestamp getEndDate() {
		return this.endDate;
	}
	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getExternalId() {
		return this.externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getOfferDescription() {
		return this.offerDescription;
	}
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public String getOfferName() {
		return this.offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getSmartcardNumber() {
		return this.smartcardNumber;
	}
	public void setSmartcardNumber(String smartcardNumber) {
		this.smartcardNumber = smartcardNumber;
	}

	public java.sql.Timestamp getStartDate() {
		return this.startDate;
	}
	public void setStartDate(java.sql.Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getWorkOrderId() {
		return this.workOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	//bi-directional many-to-one association to AccContentAttr
	public java.util.Set<AccContentAttr> getAccContentAttrs() {
		return this.accContentAttrs;
	}
	public void setAccContentAttrs(java.util.Set<AccContentAttr> accContentAttrs) {
		this.accContentAttrs = accContentAttrs;
	}

	//bi-directional many-to-one association to StatusType
	public StatusType getStatusType() {
		return this.statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountContent)) {
			return false;
		}
		AccountContent castOther = (AccountContent)other;
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