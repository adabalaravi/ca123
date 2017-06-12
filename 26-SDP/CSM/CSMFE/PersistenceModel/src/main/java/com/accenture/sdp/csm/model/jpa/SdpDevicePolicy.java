package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
 * The persistent class for the sdp_device_policy database table.
 * 
 */
@Entity
@Table(name = "sdp_device_policy")
@NamedQueries({ @NamedQuery(name = SdpDevicePolicy.QUERY_RETRIEVE_ALL, query = "select p from SdpDevicePolicy p"),
		@NamedQuery(name = SdpDevicePolicy.QUERY_RETRIEVE_BY_NAME, query = "select p from SdpDevicePolicy p where p.policyName=:policyName"),
		@NamedQuery(name = SdpDevicePolicy.QUERY_RETRIEVE_BY_ISDEFAULT, query = "select p from SdpDevicePolicy p where p.isDefault=:isDefault") })
public class SdpDevicePolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllDevicePolicy";
	public static final String QUERY_RETRIEVE_BY_NAME = "selectPolicyByName";
	public static final String QUERY_RETRIEVE_BY_ISDEFAULT = "selectPolicyByIsDefault";

	public static final String PARAM_POLICY_NAME = "policyName";
	public static final String PARAM_IS_DEFAULT = "isDefault";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "POLICY_ID")
	private Long policyId;

	@Column(name = "IS_DEFAULT")
	private Byte isDefault;

	@Column(name = "NUMBER_OF_ASSOCIATIONS")
	private Long numberOfAssociations;

	@Column(name = "POLICY_NAME")
	private String policyName;

	@Column(name = "SAFETY_PERIOD_DURATION")
	private Long safetyPeriodDuration;

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

	// bi-directional many-to-one association to SdpDevicePolicyConfig
	@OneToMany(mappedBy = "sdpDevicePolicy", cascade = CascadeType.ALL)
	private List<SdpDevicePolicyConfig> sdpDevicePolicyConfigs;

	// bi-directional many-to-one association to SdpPartyDeviceExt
	@OneToMany(mappedBy = "sdpDevicePolicy")
	private List<SdpPartyDeviceExt> sdpPartyDeviceExts;

	public SdpDevicePolicy() {
		sdpDevicePolicyConfigs = new ArrayList<SdpDevicePolicyConfig>();
		sdpPartyDeviceExts = new ArrayList<SdpPartyDeviceExt>();
	}

	public Long getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public Byte getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Byte isDefault) {
		this.isDefault = isDefault;
	}

	public Long getNumberOfAssociations() {
		return this.numberOfAssociations;
	}

	public void setNumberOfAssociations(Long numberOfAssociations) {
		this.numberOfAssociations = numberOfAssociations;
	}

	public String getPolicyName() {
		return this.policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Long getSafetyPeriodDuration() {
		return this.safetyPeriodDuration;
	}

	public void setSafetyPeriodDuration(Long safetyPeriodDuration) {
		this.safetyPeriodDuration = safetyPeriodDuration;
	}

	public List<SdpDevicePolicyConfig> getSdpDevicePolicyConfigs() {
		return this.sdpDevicePolicyConfigs;
	}

	public void setSdpDevicePolicyConfigs(List<SdpDevicePolicyConfig> sdpDevicePolicyConfigs) {
		this.sdpDevicePolicyConfigs = sdpDevicePolicyConfigs;
	}

	public List<SdpPartyDeviceExt> getSdpPartyDeviceExts() {
		return this.sdpPartyDeviceExts;
	}

	public void setSdpPartyDeviceExts(List<SdpPartyDeviceExt> sdpPartyDeviceExts) {
		this.sdpPartyDeviceExts = sdpPartyDeviceExts;
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

}