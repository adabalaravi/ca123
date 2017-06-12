package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_FREQUENCY_TYPE database table.
 * 
 */
@Entity
@Table(name = "REF_FREQUENCY_TYPE")
@NamedQueries({
		@NamedQuery(name = RefFrequencyType.FREQUENCY_RETRIEVE_ALL, query = "select f from RefFrequencyType f ORDER BY f.frequencyTypeId"),
		@NamedQuery(name = RefFrequencyType.FREQUENCY_COUNT_ALL, query = "select COUNT(f.frequencyTypeId) from RefFrequencyType f"),
		@NamedQuery(name = RefFrequencyType.FREQUENCY_RETRIEVE_BY_NAME, query = "select f from RefFrequencyType f WHERE f.frequencyTypeName=:frequencyTypeName"),
		@NamedQuery(name = RefFrequencyType.FREQUENCY_RETRIEVE_BY_DAYS, query = "select f from RefFrequencyType f WHERE f.frequencyDays=:frequencyDays") })
public class RefFrequencyType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FREQUENCY_RETRIEVE_ALL = "searchAllFrequencies";
	public static final String FREQUENCY_COUNT_ALL = "searchAllFrequenciesCount";
	public static final String FREQUENCY_RETRIEVE_BY_NAME = "searchFrequencyByName";
	public static final String FREQUENCY_RETRIEVE_BY_DAYS = "searchFrequencyByDays";

	public static final String PARAM_FREQUENCY_TYPE_NAME = "frequencyTypeName";
	public static final String PARAM_FREQUENCY_DAYS = "frequencyDays";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FREQUENCY_TYPE_ID")
	private Long frequencyTypeId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "FREQUENCY_TYPE_DESCRIPTION")
	private String frequencyTypeDescription;

	@Column(name = "FREQUENCY_TYPE_NAME")
	private String frequencyTypeName;

	@Column(name = "FREQUENCY_IN_DAYS")
	private Long frequencyDays;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpPackagePrice
	@OneToMany(mappedBy = "refFrequencyType")
	private List<SdpPackagePrice> sdpPackagePrices;

	public RefFrequencyType() {
	}

	public Long getFrequencyTypeId() {
		return this.frequencyTypeId;
	}

	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
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

	public String getFrequencyTypeDescription() {
		return this.frequencyTypeDescription;
	}

	public void setFrequencyTypeDescription(String frequencyTypeDescription) {
		this.frequencyTypeDescription = frequencyTypeDescription;
	}

	public String getFrequencyTypeName() {
		return this.frequencyTypeName;
	}

	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}

	public Long getFrequencyDays() {
		return frequencyDays;
	}

	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
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

	public List<SdpPackagePrice> getSdpPackagePrices() {
		return this.sdpPackagePrices;
	}

	public void setSdpPackagePrices(List<SdpPackagePrice> sdpPackagePrices) {
		this.sdpPackagePrices = sdpPackagePrices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frequencyTypeId == null) ? 0 : frequencyTypeId.hashCode());
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
		RefFrequencyType other = (RefFrequencyType) obj;
		if (getFrequencyTypeId() == null) {
			if (other.getFrequencyTypeId() != null) {
				return false;
			}
		} else if (!getFrequencyTypeId().equals(other.getFrequencyTypeId())) {
			return false;
		}
		return true;
	}

}