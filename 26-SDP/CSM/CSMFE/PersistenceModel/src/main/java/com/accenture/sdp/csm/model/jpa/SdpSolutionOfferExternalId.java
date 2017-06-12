package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SDP_SOLUTION_OFFER_EXTERNAL_ID database table.
 * 
 */
@Entity
@Table(name = "SDP_SOLUTION_OFFER_EXTERNAL_ID")
@NamedQueries({
		@NamedQuery(name = "selectSolutionOfferByExternalId", query = "select a.solutionOffer from SdpSolutionOfferExternalId a where a.externalId=:externalId and a.id.externalPlatformName=:externalPlatformName"),
		@NamedQuery(name = "deleteSolutionOfferExternalIdBySolutionOfferId", query = "delete SdpSolutionOfferExternalId a where a.id.solutionOfferId=:solutionOfferId") })
public class SdpSolutionOfferExternalId implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SdpSolutionOfferExternalIdPK id;

	// bi-directional many-to-one association to RefExternalPlatform
	@ManyToOne
	@JoinColumn(name = "EXTERNAL_PLATFORM_NAME", insertable = false, updatable = false)
	private RefExternalPlatform externalPlatform;

	// bi-directional many-to-one association to SdpSolutionOffer
	@ManyToOne
	@JoinColumn(name = "SOLUTION_OFFER_ID", insertable = false, updatable = false)
	private SdpSolutionOffer solutionOffer;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	public SdpSolutionOfferExternalIdPK getId() {
		return id;
	}

	public void setId(SdpSolutionOfferExternalIdPK id) {
		this.id = id;
	}

	public RefExternalPlatform getExternalPlatform() {
		return externalPlatform;
	}

	public void setExternalPlatform(RefExternalPlatform externalPlatform) {
		this.externalPlatform = externalPlatform;
	}

	public SdpSolutionOffer getSolutionOffer() {
		return solutionOffer;
	}

	public void setSolutionOffer(SdpSolutionOffer solutionOffer) {
		this.solutionOffer = solutionOffer;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
		SdpSolutionOfferExternalId other = (SdpSolutionOfferExternalId) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}