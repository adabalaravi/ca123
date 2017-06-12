package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_OFFER database table.
 * 
 */
@Entity
@Table(name = "SDP_OFFER")
@NamedQueries({
		@NamedQuery(name = SdpOffer.QUERY_RETRIEVE_BY_SERVICEVARIANTID, query = "select o from SdpOffer o where o.sdpServiceVariant.serviceVariantId=:serviceVariantId ORDER BY o.offerId"),
		@NamedQuery(name = SdpOffer.QUERY_COUNT_BY_SERVICEVARIANTID, query = "select count(o.offerId) from SdpOffer o where o.sdpServiceVariant.serviceVariantId=:serviceVariantId"),
		@NamedQuery(name = SdpOffer.QUERY_COUNT_BY_SOLUTIONOFFERID_AND_NOT_STATUS, query = "select count(o.offerId) from SdpOffer o , SdpSolutionOffer s , SdpPackage p where p.sdpOffer.offerId = o.offerId and p.sdpSolutionOffer.solutionOfferId = s.solutionOfferId and s.solutionOfferId= :solutionOfferId and o.statusId <> :statusId"),
		@NamedQuery(name = SdpOffer.QUERY_RETRIVE_BY_NAME, query = "select o from SdpOffer o where o.offerName = :offerName"),
		@NamedQuery(name = SdpOffer.QUERY_RETRIEVE_ALL, query = "select o from SdpOffer o ORDER BY o.offerId"),
		@NamedQuery(name = SdpOffer.QUERY_COUNT_ALL, query = "select count (o.offerId) from SdpOffer o"),
		@NamedQuery(name = SdpOffer.QUERY_RETRIEVE_BY_EXTERNALID, query = "select o from SdpOffer o where o.externalId=:externalId") })
public class SdpOffer implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_SERVICEVARIANTID = "selectOffersByServiceVariantId";
	public static final String QUERY_COUNT_BY_SERVICEVARIANTID = "countOffersByServiceVariantId";
	public static final String QUERY_COUNT_BY_SOLUTIONOFFERID_AND_NOT_STATUS = "countOffersBySolutionOfferIdAndStatusIdNotEqual";
	public static final String QUERY_RETRIVE_BY_NAME = "selectOfferByName";
	public static final String QUERY_RETRIEVE_ALL = "selectAllOffers";
	public static final String QUERY_COUNT_ALL = "countAllOffers";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectOfferByExternalId";

	public static final String PARAM_OFFER_NAME = "offerName";
	public static final String PARAM_SERVICE_VARIANT_ID = "serviceVariantId";
	public static final String PARAM_SOLUTION_OFFER_ID = "solutionOfferId";
	public static final String PARAM_STATUS_ID = "statusId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OFFER_ID")
	private Long offerId;

	@Column(name = "OFFER_NAME")
	private String offerName;

	@Column(name = "OFFER_DESCRIPTION")
	private String offerDescription;

	@Column(name = "STATUS_ID")
	private Long statusId;

	// bi-directional many-to-one association to SdpServiceVariant
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_VARIANT_ID")
	private SdpServiceVariant sdpServiceVariant;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Lob()
	@Column(name = "OFFER_PROFILE")
	private String offerProfile;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "DELETED_BY_ID")
	private String deletedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_DATE")
	private Date deletedDate;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	// bi-directional many-to-one association to SdpPackage
	@OneToMany(mappedBy = "sdpOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpPackage> sdpPackages;
	
	// bi-directional many-to-one association to AvsLinkOfferDigitalGood
	@OneToMany(mappedBy = "sdpOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AvsLnkOfferDigitalGood> digitalGoods;

	public SdpOffer() {
		sdpPackages = new ArrayList<SdpPackage>();
		digitalGoods = new ArrayList<AvsLnkOfferDigitalGood>();
	}

	public Long getOfferId() {
		return this.offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeletedById() {
		return this.deletedById;
	}

	public void setDeletedById(String deletedById) {
		this.deletedById = deletedById;
	}

	public Date getDeletedDate() {
		return this.deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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

	public String getOfferProfile() {
		return this.offerProfile;
	}

	public void setOfferProfile(String offerProfile) {
		this.offerProfile = offerProfile;
	}

	public String getUpdatedById() {
		return this.updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getChangeStatusById() {
		return changeStatusById;
	}

	public void setChangeStatusById(String changeStatusById) {
		this.changeStatusById = changeStatusById;
	}

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public SdpServiceVariant getSdpServiceVariant() {
		return this.sdpServiceVariant;
	}

	public void setSdpServiceVariant(SdpServiceVariant sdpServiceVariant) {
		this.sdpServiceVariant = sdpServiceVariant;
	}

	public List<SdpPackage> getSdpPackages() {
		return this.sdpPackages;
	}

	public void setSdpPackages(List<SdpPackage> sdpPackages) {
		this.sdpPackages = sdpPackages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
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
		SdpOffer other = (SdpOffer) obj;
		if (getOfferId() == null) {
			if (other.getOfferId() != null) {
				return false;
			}
		} else if (!getOfferId().equals(other.getOfferId())) {
			return false;
		}
		return true;
	}

	public List<AvsLnkOfferDigitalGood> getDigitalGoods() {
		return digitalGoods;
	}

	public void setDigitalGoods(List<AvsLnkOfferDigitalGood> digitalGoods) {
		this.digitalGoods = digitalGoods;
	}

}