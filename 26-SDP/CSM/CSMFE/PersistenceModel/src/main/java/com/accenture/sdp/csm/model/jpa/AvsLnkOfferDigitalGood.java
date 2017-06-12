package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the AVS_LNK_OFFER_DIGITAL_GOOD database table.
 * 
 */
@Entity
@Table(name = "AVS_LNK_OFFER_DIGITAL_GOOD")
@NamedQueries({ @NamedQuery(name = AvsLnkOfferDigitalGood.QUERY_LNK_AND_DIGITALGOODS_RETRIEVE_BY_OFFERID, query = "SELECT lnk,lnk.avsDigitalGood FROM AvsLnkOfferDigitalGood lnk WHERE lnk.sdpOffer.offerId=:offerId") })
public class AvsLnkOfferDigitalGood implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_LNK_AND_DIGITALGOODS_RETRIEVE_BY_OFFERID = "selectAvsLnkOfferDigitalGood&AvsDigitalGoodsByOfferId";

	public static final String PARAM_OFFER_ID = "offerId";

	@EmbeddedId
	private AvsLnkOfferDigitalGoodPK id;

	private BigDecimal price;

	// bi-directional many-to-one association to SdpOffer
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OFFER_ID")
	private SdpOffer sdpOffer;

	// bi-directional many-to-one association to SdpOffer
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DIGITAL_GOOD_ID")
	private AvsDigitalGood avsDigitalGood;

	public AvsLnkOfferDigitalGoodPK getId() {
		return id;
	}

	public void setId(AvsLnkOfferDigitalGoodPK id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AvsLnkOfferDigitalGood other = (AvsLnkOfferDigitalGood) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	public SdpOffer getSdpOffer() {
		return sdpOffer;
	}

	public void setSdpOffer(SdpOffer sdpOffer) {
		this.sdpOffer = sdpOffer;
	}

	public AvsDigitalGood getAvsDigitalGood() {
		return avsDigitalGood;
	}

	public void setAvsDigitalGood(AvsDigitalGood avsDigitalGood) {
		this.avsDigitalGood = avsDigitalGood;
	}

}