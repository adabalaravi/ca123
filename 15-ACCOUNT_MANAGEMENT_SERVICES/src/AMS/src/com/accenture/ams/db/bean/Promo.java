package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PROMO database table.
 * 
 * @author BEA Workshop
 */
public class Promo  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer promoId;
//	private java.sql.Timestamp creationDate;
	private String promoName;
	private Integer statusId;
//	private java.sql.Timestamp updateDate;
	private Integer validity;
	private java.util.Set<AccountPromo> accountPromos;
	private java.util.Set<PromoOffer> promoOffers;
	private java.util.Set<VoucherPromo> voucherPromos;

    public Promo() {
    }

	public Integer getPromoId() {
		return this.promoId;
	}
	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getPromoName() {
		return this.promoName;
	}
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	public Integer getStatusId() {
		return this.statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	public Integer getValidity() {
		return this.validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	//bi-directional many-to-one association to AccountPromo
	public java.util.Set<AccountPromo> getAccountPromos() {
		return this.accountPromos;
	}
	public void setAccountPromos(java.util.Set<AccountPromo> accountPromos) {
		this.accountPromos = accountPromos;
	}

	//bi-directional many-to-one association to PromoOffer
	public java.util.Set<PromoOffer> getPromoOffers() {
		return this.promoOffers;
	}
	public void setPromoOffers(java.util.Set<PromoOffer> promoOffers) {
		this.promoOffers = promoOffers;
	}
	
	//bi-directional many-to-one association to VoucherPromo
	public java.util.Set<VoucherPromo> getVoucherPromos() {
		return this.voucherPromos;
	}
	public void setVoucherPromos(java.util.Set<VoucherPromo> voucherPromos) {
		this.voucherPromos = voucherPromos;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Promo)) {
			return false;
		}
		Promo castOther = (Promo)other;
		return new EqualsBuilder()
			.append(this.getPromoId(), castOther.getPromoId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPromoId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("promoId", getPromoId())
			.toString();
	}
}