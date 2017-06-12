package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_SERVICE_TYPE database table.
 * 
 */
@Entity
@Table(name = "REF_SERVICE_TYPE")
@NamedQueries({ @NamedQuery(name = RefServiceType.SERVICE_TYPE_RETRIEVE_ALL, query = "select st from RefServiceType st ORDER BY st.serviceTypeId") })
public class RefServiceType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String SERVICE_TYPE_RETRIEVE_ALL = "searchAllServiceTypes";

	@Id
	@Column(name = "SERVICE_TYPE_ID")
	private Long serviceTypeId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "SERVICE_TYPE_DESCRIPTION")
	private String serviceTypeDescription;

	@Column(name = "SERVICE_TYPE_NAME")
	private String serviceTypeName;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpServiceTemplate
	@OneToMany(mappedBy = "refServiceType")
	private List<SdpServiceTemplate> sdpServiceTemplates;

	public RefServiceType() {
	}

	public Long getServiceTypeId() {
		return this.serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
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

	public String getServiceTypeDescription() {
		return this.serviceTypeDescription;
	}

	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}

	public String getServiceTypeName() {
		return this.serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
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
		result = prime * result + ((serviceTypeId == null) ? 0 : serviceTypeId.hashCode());
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
		RefServiceType other = (RefServiceType) obj;
		if (getServiceTypeId() == null) {
			if (other.getServiceTypeId() != null) {
				return false;
			}
		} else if (!getServiceTypeId().equals(other.getServiceTypeId())) {
			return false;
		}
		return true;
	}

}