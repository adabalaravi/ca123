package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_PLATFORM database table.
 * 
 */
@Entity
@Table(name = "SDP_PLATFORM")
@NamedQueries({ @NamedQuery(name = "selectPlatformByName", query = "select p from SdpPlatform p where p.platformName = :platformName"),
		@NamedQuery(name = "selectAllPlatforms", query = "select p from SdpPlatform p"),
		@NamedQuery(name = "selectAllPlatformsCount", query = "select count(p.platformId) from SdpPlatform p"),
		@NamedQuery(name = "selectPlatformByExternalId", query = "select a from SdpPlatform a where a.externalId=:externalId")

})
public class SdpPlatform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PLATFORM_ID")
	private Long platformId;

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

	@Column(name = "PLATFORM_DESCRIPTION")
	private String platformDescription;

	@Column(name = "PLATFORM_NAME")
	private String platformName;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to RefStatusType

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	@Lob()
	@Column(name = "PLATFORM_PROFILE")
	private String platformProfile;

	// bi-directional many-to-one association to SdpServiceTemplate
	@OneToMany(mappedBy = "sdpPlatform")
	private List<SdpServiceTemplate> sdpServiceTemplates;

	public SdpPlatform() {
	}

	public Long getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
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

	public String getPlatformDescription() {
		return this.platformDescription;
	}

	public void setPlatformDescription(String platformDescription) {
		this.platformDescription = platformDescription;
	}

	public String getPlatformName() {
		return this.platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public String getPlatformProfile() {
		return platformProfile;
	}

	public void setPlatformProfile(String platformProfile) {
		this.platformProfile = platformProfile;
	}

	public List<SdpServiceTemplate> getSdpServiceTemplates() {
		return this.sdpServiceTemplates;
	}

	public void setSdpServiceTemplates(List<SdpServiceTemplate> sdpServiceTemplates) {
		this.sdpServiceTemplates = sdpServiceTemplates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((platformId == null) ? 0 : platformId.hashCode());
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
		SdpPlatform other = (SdpPlatform) obj;
		if (getPlatformId() == null) {
			if (other.getPlatformId() != null) {
				return false;
			}
		} else if (!getPlatformId().equals(other.getPlatformId())) {
			return false;
		}
		return true;
	}

}