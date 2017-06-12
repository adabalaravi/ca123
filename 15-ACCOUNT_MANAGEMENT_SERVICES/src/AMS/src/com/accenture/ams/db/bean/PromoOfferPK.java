package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the PROMO_OFFER database table.
 * 
 * @author BEA Workshop
 */
public class PromoOfferPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer promoId;
	private Long solutionOfferId;

    public PromoOfferPK() {
    }

	public Integer getPromoId() {
		return this.promoId;
	}
	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

	public Long getSolutionOfferId() {
		return this.solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PromoOfferPK)) {
			return false;
		}
		PromoOfferPK castOther = (PromoOfferPK)other;
		return new EqualsBuilder()
			.append(this.getPromoId(), castOther.getPromoId())
			.append(this.getSolutionOfferId(), castOther.getSolutionOfferId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPromoId())
			.append(getSolutionOfferId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("promoId", getPromoId())
			.append("solutionOfferId", getSolutionOfferId())
			.toString();
	}
}