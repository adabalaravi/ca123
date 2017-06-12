package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * The persistent class for the SDP_SERVICE_VARIANT database table.
 * 
 */
@Entity
@Table(name = "SDP_SERVICE_VARIANT")
@NamedQueries({
		@NamedQuery(name = "selectServiceVariantByName", query = "select sv from SdpServiceVariant sv where sv.serviceVariantName=:serviceVariantName"),
		@NamedQuery(name = SdpServiceVariant.QUERY_RETRIEVE_ALL, query = "select s from SdpServiceVariant s"),
		@NamedQuery(name = SdpServiceVariant.QUERY_COUNT_ALL, query = "select count(s.serviceVariantId) from SdpServiceVariant s"),
		@NamedQuery(name = "selectServiceVariantsByServiceTemplateId", query = "select s from SdpServiceVariant s where s.sdpServiceTemplate.serviceTemplateId=:serviceTemplateId"),
		@NamedQuery(name = "countServiceVariantsByServiceTemplateId", query = "select count(s.serviceVariantId) from SdpServiceVariant s where s.sdpServiceTemplate.serviceTemplateId=:serviceTemplateId"),
		@NamedQuery(name = "countServiceVariantsByServiceTemplateIdAndStatusIdNotEqual", query = "select count(s.serviceVariantId) from SdpServiceVariant s where s.sdpServiceTemplate.serviceTemplateId=:serviceTemplateId and s.statusId<>:statusId"),
		@NamedQuery(name = SdpServiceVariant.QUERY_RETRIEVE_BY_EXTERNALID, query = "select a from SdpServiceVariant a where a.externalId=:externalId") })
public class SdpServiceVariant implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllServiceVariants";
	public static final String QUERY_COUNT_ALL = "countAllServiceVariants";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectServiceVariantByExternalId";

	public static final String PARAM_SERVICE_TEMPLATE_ID = "serviceTemplateId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SERVICE_VARIANT_ID")
	private Long serviceVariantId;

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

	@Column(name = "SERVICE_VARIANT_DESCRIPTION")
	private String serviceVariantDescription;

	@Column(name = "SERVICE_VARIANT_NAME")
	private String serviceVariantName;

	@Lob()
	@Column(name = "SERVICE_VARIANT_PROFILE")
	private String serviceVariantProfile;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpOffer
	@OneToMany(mappedBy = "sdpServiceVariant")
	private List<SdpOffer> sdpOffers;

	// bi-directional many-to-one association to SdpOffer
	@OneToMany(mappedBy = "sdpServiceVariant")
	private List<SdpServiceVariantOperation> sdpServiceVariantOperations;

	// bi-directional many-to-one association to RefStatusType

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	// bi-directional many-to-one association to SdpServiceTemplate
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_TEMPLATE_ID")
	private SdpServiceTemplate sdpServiceTemplate;

	public SdpServiceVariant() {
		sdpOffers = new ArrayList<SdpOffer>();
	}

	public Long getServiceVariantId() {
		return this.serviceVariantId;
	}

	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
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

	public String getServiceVariantDescription() {
		return this.serviceVariantDescription;
	}

	public void setServiceVariantDescription(String serviceVariantDescription) {
		this.serviceVariantDescription = serviceVariantDescription;
	}

	public String getServiceVariantName() {
		return this.serviceVariantName;
	}

	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}

	public String getServiceVariantProfile() {
		return this.serviceVariantProfile;
	}

	public void setServiceVariantProfile(String serviceVariantProfile) {
		this.serviceVariantProfile = serviceVariantProfile;
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

	public List<SdpOffer> getSdpOffers() {
		return this.sdpOffers;
	}

	public void setSdpOffers(List<SdpOffer> sdpOffers) {
		this.sdpOffers = sdpOffers;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public SdpServiceTemplate getSdpServiceTemplate() {
		return this.sdpServiceTemplate;
	}

	public void setSdpServiceTemplate(SdpServiceTemplate sdpServiceTemplate) {
		this.sdpServiceTemplate = sdpServiceTemplate;
	}

	public List<SdpServiceVariantOperation> getSdpServiceVariantOperations() {
		return sdpServiceVariantOperations;
	}

	public void setSdpServiceVariantOperations(List<SdpServiceVariantOperation> sdpServiceVariantOperations) {
		this.sdpServiceVariantOperations = sdpServiceVariantOperations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serviceVariantId == null) ? 0 : serviceVariantId.hashCode());
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
		SdpServiceVariant other = (SdpServiceVariant) obj;
		if (getServiceVariantId() == null) {
			if (other.getServiceVariantId() != null) {
				return false;
			}
		} else if (!getServiceVariantId().equals(other.getServiceVariantId())) {
			return false;
		}
		return true;
	}

}