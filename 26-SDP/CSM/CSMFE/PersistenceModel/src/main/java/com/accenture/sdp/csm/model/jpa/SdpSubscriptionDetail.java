package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_SUBSCRIPTION_DETAIL database table.
 * 
 */

@Entity
@Table(name = "SDP_SUBSCRIPTION_DETAIL_VIEW")
@NamedQueries({
		@NamedQuery(name = "selectSubscriptionDetailsBySubscriptionId", query = "select a from SdpSubscriptionDetail a where a.subscriptionId=:subscriptionId"),
		@NamedQuery(name = "selectSubscriptionDetailsById", query = "select a from SdpSubscriptionDetail a where a.subscriptionId=:subscriptionId and a.packageId=:packageId"),
		@NamedQuery(name = SdpSubscriptionDetail.QUERY_COUNT_BY_PACKAGEID_AND_NOT_STATUS, query = "select COUNT(p.subscriptionId) from SdpSubscriptionDetail p where p.packageId=:packageId and p.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionDetailByExternalId", query = "select a from SdpSubscriptionDetail a where a.externalId=:externalId") })
@IdClass(SdpSubscriptionDetailPK.class)
public class SdpSubscriptionDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_COUNT_BY_PACKAGEID_AND_NOT_STATUS = "countSubscriptionDetailsByPackageIdAndStatusIdNotEqual";

	public static final String PARAM_SUBSCRIPTION_ID = "subscriptionId";
	public static final String PARAM_PACKAGE_ID = "packageId";
	public static final String PARAM_STATUS_ID = "statusId";
	
	@Id
	@Column(name = "SUBSCRIPTION_ID")
	private Long subscriptionId;

	@Id
	@Column(name = "PACKAGE_ID")
	private Long packageId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "DELETED_BY_ID")
	private String deletedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_DATE")
	private Date deletedDate;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Lob()
	@Column(name = "SUBSCRIPTION_OFFER_PROFILE")
	private String subscriptionOfferProfile;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to RefStatusType

	@Column(name = "STATUS_ID")
	private Long statusId;

	// bi-directional many-to-one association to SdpPackage
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACKAGE_ID", insertable = false, updatable = false)
	private SdpPackage sdpPackage;

	// bi-directional many-to-one association to SdpSubscription
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBSCRIPTION_ID", insertable = false, updatable = false)
	private SdpSubscription sdpSubscription;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	public SdpSubscriptionDetail() {
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeletedById() {
		return deletedById;
	}

	public void setDeletedById(String deletedById) {
		this.deletedById = deletedById;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getSubscriptionOfferProfile() {
		return subscriptionOfferProfile;
	}

	public void setSubscriptionOfferProfile(String subscriptionOfferProfile) {
		this.subscriptionOfferProfile = subscriptionOfferProfile;
	}

	public String getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public SdpPackage getSdpPackage() {
		return sdpPackage;
	}

	public void setSdpPackage(SdpPackage sdpPackage) {
		this.sdpPackage = sdpPackage;
	}

	public SdpSubscription getSdpSubscription() {
		return sdpSubscription;
	}

	public void setSdpSubscription(SdpSubscription sdpSubscription) {
		this.sdpSubscription = sdpSubscription;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packageId == null) ? 0 : packageId.hashCode());
		result = prime * result + ((subscriptionId == null) ? 0 : subscriptionId.hashCode());
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
		SdpSubscriptionDetail other = (SdpSubscriptionDetail) obj;
		if (getPackageId() == null) {
			if (other.getPackageId() != null) {
				return false;
			}
		} else if (!getPackageId().equals(other.getPackageId())) {
			return false;
		}
		if (getSubscriptionId() == null) {
			if (other.getSubscriptionId() != null) {
				return false;
			}
		} else if (!getSubscriptionId().equals(other.getSubscriptionId())) {
			return false;
		}
		return true;
	}

}