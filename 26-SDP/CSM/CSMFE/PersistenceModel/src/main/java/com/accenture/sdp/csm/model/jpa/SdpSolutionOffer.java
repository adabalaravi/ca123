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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_SOLUTION_OFFER database table.
 * 
 */
@Entity
@Table(name = "SDP_SOLUTION_OFFER")
@NamedQueries({
		@NamedQuery(name = "selectSolutionOfferByName", query = "select s from SdpSolutionOffer s where s.solutionOfferName=:solutionOfferName"),
		@NamedQuery(name = "selectAllParentSolutionOffers", query = "select s from SdpSolutionOffer s where s.parentSolutionOffer is null"),
		@NamedQuery(name = "selectAllDiscountSolutionOffers", query = "select s from SdpSolutionOffer s where s.parentSolutionOffer is not null"),
		@NamedQuery(name = "countAllParentSolutionOffers", query = "select count(s.solutionOfferId) from SdpSolutionOffer s where s.parentSolutionOffer is null"),
		@NamedQuery(name = "countAllDiscountSolutionOffers", query = "select count(s.solutionOfferId) from SdpSolutionOffer s where s.parentSolutionOffer is not null"),
		@NamedQuery(name = "selectSolutionOffersBySolutionId", query = "select s from SdpSolutionOffer s  where s.sdpSolution.solutionId=:solutionId"),
		@NamedQuery(name = "countSolutionOffersBySolutionId", query = "select COUNT(s.solutionOfferId) from SdpSolutionOffer s  where s.sdpSolution.solutionId=:solutionId"),
		@NamedQuery(name = "selectSolutionOfferBySolutionIdAndStatusIdNotEqual", query = "select s from SdpSolutionOffer s where s.sdpSolution.solutionId =:solutionId and s.statusId<>:statusId"),
		@NamedQuery(name = "selectSolutionOfferByNameCount", query = "select count(s.solutionOfferId) from SdpSolutionOffer s where s.solutionOfferName=:solutionOfferName"),
		@NamedQuery(name = "countSolutionOffersByParent", query = "select count(s.solutionOfferId) from SdpSolutionOffer s where s.parentSolutionOffer.solutionOfferId=:parentSolutionOfferId"),
		@NamedQuery(name = "searchSolutionOffersByParent", query = "select s from SdpSolutionOffer s where s.parentSolutionOffer.solutionOfferId=:parentSolutionOfferId"),
		@NamedQuery(name = "countSolutionOffersByParentAndStatusIdNotEqual", query = "select count(s.solutionOfferId) from SdpSolutionOffer s where s.parentSolutionOffer.solutionOfferId=:parentSolutionOfferId and s.statusId<>:statusId") })
public class SdpSolutionOffer implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PARAM_PARENT_SOLUTION_OFFER_ID = "parentSolutionOfferId";
	public static final String PARAM_STATUS_ID = "statusId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SOLUTION_OFFER_ID")
	private Long solutionOfferId;

	// bi-directional many-to-one association to SdpSolution
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOLUTION_ID")
	private SdpSolution sdpSolution;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_SOLUTION_OFFER_ID")
	private SdpSolutionOffer parentSolutionOffer;

	@Column(name = "SOLUTION_OFFER_NAME")
	private String solutionOfferName;

	@Column(name = "SOLUTION_OFFER_DESCRIPTION")
	private String solutionOfferDescription;

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOLUTION_OFFER_TYPE")
	private RefSolutionOfferType solutionOfferType;

	@Column(name = "DURATION")
	private Long duration;

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
	@Column(name = "SOLUTION_OFFER_PROFILE")
	private String solutionOfferProfile;

	// bi-directional many-to-one association to SdpPackage
	@OneToMany(mappedBy = "sdpSolutionOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpPackage> sdpPackages;

	@OneToMany(mappedBy = "sdpSolutionOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpSubscription> sdpSubscriptions;

	@OneToMany(mappedBy = "sdpSolutionOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpOfferGroup> sdpOfferGroups;

	@OneToMany(mappedBy = "solutionOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpDiscount> discounts;

	@OneToMany(mappedBy = "solutionOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpSolutionOfferExternalId> externalIds;

	// bi-directional many-to-many association to sdpPartyGroup for AVS
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "LNK_SOLUTION_OFFER_PARTY_GROUP", joinColumns = @JoinColumn(name = "SOLUTION_OFFER_ID"), inverseJoinColumns = @JoinColumn(name = "PARTY_GROUP_ID"))
	private List<SdpPartyGroup> partyGroups;

	public SdpSolutionOffer() {
		sdpPackages = new ArrayList<SdpPackage>();
		sdpSubscriptions = new ArrayList<SdpSubscription>();
		sdpOfferGroups = new ArrayList<SdpOfferGroup>();
		discounts = new ArrayList<SdpDiscount>();
		partyGroups = new ArrayList<SdpPartyGroup>();
		externalIds = new ArrayList<SdpSolutionOfferExternalId>();
	}

	public Long getSolutionOfferId() {
		return this.solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
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

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSolutionOfferDescription() {
		return this.solutionOfferDescription;
	}

	public void setSolutionOfferDescription(String solutionOfferDescription) {
		this.solutionOfferDescription = solutionOfferDescription;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getSolutionOfferName() {
		return this.solutionOfferName;
	}

	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public String getSolutionOfferProfile() {
		return solutionOfferProfile;
	}

	public void setSolutionOfferProfile(String solutionOfferProfile) {
		this.solutionOfferProfile = solutionOfferProfile;
	}

	public List<SdpPackage> getSdpPackages() {
		return this.sdpPackages;
	}

	public void setSdpPackages(List<SdpPackage> sdpPackages) {
		this.sdpPackages = sdpPackages;
	}

	public SdpSolution getSdpSolution() {
		return this.sdpSolution;
	}

	public void setSdpSolution(SdpSolution sdpSolution) {
		this.sdpSolution = sdpSolution;
	}

	public List<SdpSubscription> getSdpSubscriptions() {
		return sdpSubscriptions;
	}

	public void setSdpSubscriptions(List<SdpSubscription> sdpSubscriptions) {
		this.sdpSubscriptions = sdpSubscriptions;
	}

	public List<SdpOfferGroup> getSdpOfferGroups() {
		return sdpOfferGroups;
	}

	public void setSdpOfferGroups(List<SdpOfferGroup> sdpOfferGroups) {
		this.sdpOfferGroups = sdpOfferGroups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((solutionOfferId == null) ? 0 : solutionOfferId.hashCode());
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
		SdpSolutionOffer other = (SdpSolutionOffer) obj;
		if (getSolutionOfferId() == null) {
			if (other.getSolutionOfferId() != null) {
				return false;
			}
		} else if (!getSolutionOfferId().equals(other.getSolutionOfferId())) {
			return false;
		}
		return true;
	}

	public SdpSolutionOffer getParentSolutionOffer() {
		return parentSolutionOffer;
	}

	public void setParentSolutionOffer(SdpSolutionOffer parentSolutionOffer) {
		this.parentSolutionOffer = parentSolutionOffer;
	}

	public List<SdpDiscount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<SdpDiscount> discounts) {
		this.discounts = discounts;
	}

	public List<SdpPartyGroup> getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(List<SdpPartyGroup> partyGroups) {
		this.partyGroups = partyGroups;
	}

	public List<SdpSolutionOfferExternalId> getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(List<SdpSolutionOfferExternalId> externalIds) {
		this.externalIds = externalIds;
	}

	public RefSolutionOfferType getSolutionOfferType() {
		return solutionOfferType;
	}

	public void setSolutionOfferType(RefSolutionOfferType solutionOfferType) {
		this.solutionOfferType = solutionOfferType;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

}