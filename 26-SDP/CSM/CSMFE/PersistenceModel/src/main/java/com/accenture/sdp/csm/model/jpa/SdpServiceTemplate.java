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
 * The persistent class for the SDP_SERVICE_TEMPLATE database table.
 * 
 */
@Entity
@Table(name = "SDP_SERVICE_TEMPLATE")
@NamedQueries({
		@NamedQuery(name = "selectServiceTemplateByName", query = "select s from SdpServiceTemplate s where s.serviceTemplateName =:serviceTemplateName"),
		@NamedQuery(name = SdpServiceTemplate.QUERY_RETRIEVE_ALL, query = "select s from SdpServiceTemplate s"),
		@NamedQuery(name = SdpServiceTemplate.QUERY_COUNT_ALL, query = "select count(s.serviceTemplateId) from SdpServiceTemplate s"),
		@NamedQuery(name = "selectServiceTemplatesByPlatformIdAndStatusIdNotEqualCount", query = "select count(s.serviceTemplateId) from SdpServiceTemplate s where s.sdpPlatform.platformId=:platformId and s.statusId<>:statusId"),
		@NamedQuery(name = "selectServiceTemplatesByPlatformId", query = "select s from SdpServiceTemplate s where s.sdpPlatform.platformId=:platformId"),
		@NamedQuery(name = "countServiceTemplatesByPlatformId", query = "select count(s.serviceTemplateId) from SdpServiceTemplate s where s.sdpPlatform.platformId=:platformId"),
		@NamedQuery(name = SdpServiceTemplate.QUERY_RETRIEVE_BY_EXTERNALID, query = "select a from SdpServiceTemplate a where a.externalId=:externalId") })
public class SdpServiceTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllServiceTemplates";
	public static final String QUERY_COUNT_ALL = "countAllServiceTemplates";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectServiceTemplateByExternalId";

	public static final String PARAM_PLATFORM_ID = "platformId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SERVICE_TEMPLATE_ID")
	private Long serviceTemplateId;

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

	@Column(name = "SERVICE_TEMPLATE_DESCRIPTION")
	private String serviceTemplateDescription;

	@Column(name = "SERVICE_TEMPLATE_NAME")
	private String serviceTemplateName;

	@Lob()
	@Column(name = "SERVICE_TEMPLATE_PROFILE")
	private String serviceTemplateProfile;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to RefServiceType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_TYPE_ID")
	private RefServiceType refServiceType;

	// bi-directional many-to-one association to RefStatusType

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	// bi-directional many-to-one association to SdpPlatform
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLATFORM_ID")
	private SdpPlatform sdpPlatform;

	// bi-directional many-to-one association to SdpServiceVariant
	@OneToMany(mappedBy = "sdpServiceTemplate")
	private List<SdpServiceVariant> sdpServiceVariants;

	public SdpServiceTemplate() {
		sdpServiceVariants = new ArrayList<SdpServiceVariant>();
	}

	public Long getServiceTemplateId() {
		return this.serviceTemplateId;
	}

	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
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

	public String getServiceTemplateDescription() {
		return this.serviceTemplateDescription;
	}

	public void setServiceTemplateDescription(String serviceTemplateDescription) {
		this.serviceTemplateDescription = serviceTemplateDescription;
	}

	public String getServiceTemplateName() {
		return this.serviceTemplateName;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}

	public String getServiceTemplateProfile() {
		return this.serviceTemplateProfile;
	}

	public void setServiceTemplateProfile(String serviceTemplateProfile) {
		this.serviceTemplateProfile = serviceTemplateProfile;
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

	public RefServiceType getRefServiceType() {
		return this.refServiceType;
	}

	public void setRefServiceType(RefServiceType refServiceType) {
		this.refServiceType = refServiceType;
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

	public SdpPlatform getSdpPlatform() {
		return this.sdpPlatform;
	}

	public void setSdpPlatform(SdpPlatform sdpPlatform) {
		this.sdpPlatform = sdpPlatform;
	}

	public List<SdpServiceVariant> getSdpServiceVariants() {
		return this.sdpServiceVariants;
	}

	public void setSdpServiceVariants(List<SdpServiceVariant> sdpServiceVariants) {
		this.sdpServiceVariants = sdpServiceVariants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serviceTemplateId == null) ? 0 : serviceTemplateId.hashCode());
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
		SdpServiceTemplate other = (SdpServiceTemplate) obj;
		if (getServiceTemplateId() == null) {
			if (other.getServiceTemplateId() != null) {
				return false;
			}
		} else if (!getServiceTemplateId().equals(other.getServiceTemplateId())) {
			return false;
		}
		return true;
	}

}