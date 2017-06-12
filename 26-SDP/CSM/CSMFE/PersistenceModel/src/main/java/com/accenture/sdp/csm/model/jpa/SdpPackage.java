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
 * The persistent class for the SDP_PACKAGE database table.
 * 
 */
@Entity
@Table(name = "SDP_PACKAGE")
@NamedQueries({
		@NamedQuery(name = SdpPackage.QUERY_COUNT_BY_PACKAGEPRICEID, query = "select COUNT (p.packageId) from SdpPackage p where p.sdpPackagePrice.packagePriceId=:packagePriceId"),
		@NamedQuery(name = SdpPackage.QUERY_RETRIEVE_BY_SOLUTIONOFFERID, query = "select p from SdpPackage p where p.sdpSolutionOffer.solutionOfferId=:solutionOfferId"),
		@NamedQuery(name = SdpPackage.QUERY_COUNT_BY_SOLUTIONOFFERID, query = "select COUNT(p.packageId) from SdpPackage p where p.sdpSolutionOffer.solutionOfferId=:solutionOfferId"),
		@NamedQuery(name = SdpPackage.QUERY_RETRIEVE_BY_SOLUTIONOFFERID_AND_OFFERID, query = "select p from SdpPackage p where p.sdpSolutionOffer.solutionOfferId = :solutionOfferId and p.sdpOffer.offerId = :offerId"),
		@NamedQuery(name = SdpPackage.QUERY_RETRIEVE_BY_EXTERNALID, query = "select p from SdpPackage p where p.externalId=:externalId"),
		@NamedQuery(name = SdpPackage.QUERY_RETRIEVE_BY_OFFERID, query = "select p from SdpPackage p where p.sdpOffer.offerId = :offerId"),
		@NamedQuery(name = SdpPackage.QUERY_COUNT_BY_SOLUTIONOFFERID_AND_NOT_STATUS, query = "select COUNT(p.packageId) from SdpPackage p where p.sdpSolutionOffer.solutionOfferId=:solutionOfferId and p.statusId<>:statusId"),
		@NamedQuery(name = SdpPackage.QUERY_COUNT_BY_OFFERID_AND_NOT_STATUS, query = "select COUNT(p.packageId) from SdpPackage p where p.sdpOffer.offerId=:offerId and p.statusId<>:statusId") })
public class SdpPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_COUNT_BY_PACKAGEPRICEID = "countPackageByPackagePriceId";
	public static final String QUERY_RETRIEVE_BY_SOLUTIONOFFERID = "selectPackagesBySolutionOfferId";
	public static final String QUERY_COUNT_BY_SOLUTIONOFFERID = "countPackagesBySolutionOfferId";
	public static final String QUERY_RETRIEVE_BY_SOLUTIONOFFERID_AND_OFFERID = "selectPackageBySolutionOfferIdAndOfferId";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectPackageByExternalId";
	public static final String QUERY_RETRIEVE_BY_OFFERID = "selectPackageByOfferId";
	public static final String QUERY_COUNT_BY_SOLUTIONOFFERID_AND_NOT_STATUS = "countPackagesBySolutionOfferIdAndStatusNotEqual";
	public static final String QUERY_COUNT_BY_OFFERID_AND_NOT_STATUS = "countPackagesByOfferIdAndStatusNotEqual";

	public static final String PARAM_PACKAGE_PRICE_ID = "packagePriceId";
	public static final String PARAM_OFFER_ID = "offerId";
	public static final String PARAM_SOLUTION_OFFER_ID = "solutionOfferId";
	public static final String PARAM_STATUS_ID = "statusId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PACKAGE_ID")
	private Long packageId;

	// bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BASE_PACKAGE_ID")
	private SdpPackage basePackage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	private SdpOfferGroup sdpOfferGroup;

	// bi-directional many-to-one association to SdpPackagePrice
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACKAGE_PRICE_ID")
	private SdpPackagePrice sdpPackagePrice;

	@Column(name = "IS_MANDATORY")
	private String isMandatory;

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

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

	@Lob()
	@Column(name = "PACKAGE_PROFILE")
	private String packageProfile;

	// bi-directional many-to-one association to SdpOffer
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OFFER_ID")
	private SdpOffer sdpOffer;

	// bi-directional many-to-one association to SdpSolutionOffer
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SOLUTION_OFFER_ID")
	private SdpSolutionOffer sdpSolutionOffer;

	// bi-directional many-to-one association to SdpSubscriptionDetail
	@OneToMany(mappedBy = "sdpPackage", fetch = FetchType.LAZY)
	private List<SdpSubscriptionDetail> sdpSubscriptionDetails;

	@OneToMany(mappedBy = "sdpPackage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpDiscount> discounts;

	public SdpPackage() {
		sdpSubscriptionDetails = new ArrayList<SdpSubscriptionDetail>();
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public SdpPackage getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(SdpPackage basePackage) {
		this.basePackage = basePackage;
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

	public String getChangeStatusById() {
		return changeStatusById;
	}

	public void setChangeStatusById(String changeStatusById) {
		this.changeStatusById = changeStatusById;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getIsMandatory() {
		return this.isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public String getPackageProfile() {
		return packageProfile;
	}

	public void setPackageProfile(String packageProfile) {
		this.packageProfile = packageProfile;
	}

	public SdpPackagePrice getSdpPackagePrice() {
		return this.sdpPackagePrice;
	}

	public void setSdpPackagePrice(SdpPackagePrice sdpPackagePrice) {
		this.sdpPackagePrice = sdpPackagePrice;
	}

	public SdpOffer getSdpOffer() {
		return sdpOffer;
	}

	public void setSdpOffer(SdpOffer sdpOffer) {
		this.sdpOffer = sdpOffer;
	}

	public SdpSolutionOffer getSdpSolutionOffer() {
		return sdpSolutionOffer;
	}

	public void setSdpSolutionOffer(SdpSolutionOffer sdpSolutionOffer) {
		this.sdpSolutionOffer = sdpSolutionOffer;
	}

	public List<SdpSubscriptionDetail> getSdpSubscriptionDetails() {
		return sdpSubscriptionDetails;
	}

	public void setSdpSubscriptionDetails(List<SdpSubscriptionDetail> sdpSubscriptionDetails) {
		this.sdpSubscriptionDetails = sdpSubscriptionDetails;
	}

	public SdpOfferGroup getSdpOfferGroup() {
		return sdpOfferGroup;
	}

	public void setSdpOfferGroup(SdpOfferGroup sdpOfferGroup) {
		this.sdpOfferGroup = sdpOfferGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packageId == null) ? 0 : packageId.hashCode());
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
		SdpPackage other = (SdpPackage) obj;
		if (getPackageId() == null) {
			if (other.getPackageId() != null) {
				return false;
			}
		} else if (!getPackageId().equals(other.getPackageId())) {
			return false;
		}
		return true;
	}

	public List<SdpDiscount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<SdpDiscount> discounts) {
		this.discounts = discounts;
	}

}