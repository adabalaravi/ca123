package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PROMO_OFFER database table.
 * 
 * @author BEA Workshop
 */
public class PromoOffer  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private PromoOfferPK compId;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private Product product;
	private Promo promo;

    public PromoOffer() {
    }

	public PromoOfferPK getCompId() {
		return this.compId;
	}
	public void setCompId(PromoOfferPK compId) {
		this.compId = compId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to Product
	public Product getProduct() {
		return this.product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	//bi-directional many-to-one association to Promo
	public Promo getPromo() {
		return this.promo;
	}
	public void setPromo(Promo promo) {
		this.promo = promo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PromoOffer)) {
			return false;
		}
		PromoOffer castOther = (PromoOffer)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}