package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PRODUCT database table.
 * 
 * @author BEA Workshop
 */
public class Product  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long solutionOfferId;
	private String approved;
//	private java.sql.Timestamp creationDate;
	private java.sql.Timestamp endDate;
	private Long externalId;
	private String offerDescription;
	private String offerName;
	private java.sql.Timestamp startDate;
//	private java.sql.Timestamp updateDate;
//	private CommercialCategory commercialCategory;
	private StatusType statusType;
	private java.util.Set<PromoOffer> promoOffers;

    public Product() {
    }

	public Long getSolutionOfferId() {
		return this.solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public String getApproved() {
		return this.approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public java.sql.Timestamp getEndDate() {
		return this.endDate;
	}
	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}

	public Long getExternalId() {
		return this.externalId;
	}
	public void setExternalId(Long externalId) {
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

	public java.sql.Timestamp getStartDate() {
		return this.startDate;
	}
	public void setStartDate(java.sql.Timestamp startDate) {
		this.startDate = startDate;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

//	//bi-directional many-to-one association to CommercialCategory
//	public CommercialCategory getCommercialCategory() {
//		return this.commercialCategory;
//	}
//	public void setCommercialCategory(CommercialCategory commercialCategory) {
//		this.commercialCategory = commercialCategory;
//	}

	//bi-directional many-to-one association to StatusType
	public StatusType getStatusType() {
		return this.statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	//bi-directional many-to-one association to PromoOffer
	public java.util.Set<PromoOffer> getPromoOffers() {
		return this.promoOffers;
	}
	public void setPromoOffers(java.util.Set<PromoOffer> promoOffers) {
		this.promoOffers = promoOffers;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Product)) {
			return false;
		}
		Product castOther = (Product)other;
		return new EqualsBuilder()
			.append(this.getSolutionOfferId(), castOther.getSolutionOfferId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSolutionOfferId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("solutionOfferId", getSolutionOfferId())
			.toString();
	}
}