package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;

/**
 * The persistent class for the sdp_party_device_ext database table.
 * 
 */
@Entity
@Table(name = "sdp_party_device_ext")
@NamedQueries({
		@NamedQuery(name = SdpPartyDeviceExt.QUERY_RETRIEVE_BY_BLACKLIST, query = "select p from SdpPartyDeviceExt p where p.isBl=:isBl"),
		@NamedQuery(name = SdpPartyDeviceExt.QUERY_RETRIEVE_BY_WHITELIST, query = "select p from SdpPartyDeviceExt p where p.isWl=:isWl"),
		@NamedQuery(name = SdpPartyDeviceExt.QUERY_UPDATE_POLICY, query = "UPDATE SdpPartyDeviceExt e SET e.sdpDevicePolicy.policyId=:newPolicyId WHERE e.sdpDevicePolicy.policyId=:policyId") })
public class SdpPartyDeviceExt extends BlackWhiteListableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_BLACKLIST = "selectPartyDeviceExtByBlacklist";
	public static final String QUERY_RETRIEVE_BY_WHITELIST = "selectPartyDeviceExtByWhitelist";
	public static final String QUERY_UPDATE_POLICY = "updatePartyPolicy";

	public static final String PARAM_IS_BL = "isBl";
	public static final String PARAM_IS_WL = "isWl";
	public static final String PARAM_POLICY_ID = "policyId";
	public static final String PARAM_POLICY_ID_UPDATE = "newPolicyId";

	@Id
	@Column(name = "PARTY_ID")
	private Long partyId;

	@Column(name = "REGISTRATIONS_DONE")
	private Long registrationsDone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SAFETY_PERIOD_EXPIRATION")
	private Date safetyPeriodExpiration;

	// bi-directional many-to-one association to SdpDeviceCounterConfig
	@OneToMany(mappedBy = "sdpPartyDeviceExt", cascade = CascadeType.ALL)
	private List<SdpDeviceCounterConfig> sdpDeviceCounterConfigs;

	// bi-directional many-to-one association to SdpDevicePolicy
	@ManyToOne
	@JoinColumn(name = "POLICY_ID")
	private SdpDevicePolicy sdpDevicePolicy;

	// bi-directional many-to-one association to SdpDeviceCounterConfig
	@OneToMany(mappedBy = "sdpPartyDeviceExt", cascade = CascadeType.ALL)
	private List<SdpDevice> sdpDevices;

	// //bi-directional one-to-one association to SdpParty
	@OneToOne()
	@PrimaryKeyJoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID")
	private SdpParty sdpParty;

	public SdpPartyDeviceExt() {
		sdpDeviceCounterConfigs = new ArrayList<SdpDeviceCounterConfig>();
	}

	public Long getPartyId() {
		return this.partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getRegistrationsDone() {
		return this.registrationsDone;
	}

	public void setRegistrationsDone(Long registrationsDone) {
		this.registrationsDone = registrationsDone;
	}

	public Date getSafetyPeriodExpiration() {
		return this.safetyPeriodExpiration;
	}

	public void setSafetyPeriodExpiration(Date safetyPeriodExpiration) {
		this.safetyPeriodExpiration = safetyPeriodExpiration;
	}

	public List<SdpDeviceCounterConfig> getSdpDeviceCounterConfigs() {
		return this.sdpDeviceCounterConfigs;
	}

	public void setSdpDeviceCounterConfigs(List<SdpDeviceCounterConfig> sdpDeviceCounterConfigs) {
		this.sdpDeviceCounterConfigs = sdpDeviceCounterConfigs;
	}

	public SdpDevicePolicy getSdpDevicePolicy() {
		return this.sdpDevicePolicy;
	}

	public void setSdpDevicePolicy(SdpDevicePolicy sdpDevicePolicy) {
		this.sdpDevicePolicy = sdpDevicePolicy;
	}

	public List<SdpDevice> getSdpDevices() {
		return sdpDevices;
	}

	public void setSdpDevices(List<SdpDevice> sdpDevices) {
		this.sdpDevices = sdpDevices;
	}

	public SdpParty getSdpParty() {
		return sdpParty;
	}

	public void setSdpParty(SdpParty sdpParty) {
		this.sdpParty = sdpParty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partyId == null) ? 0 : partyId.hashCode());
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
		SdpPartyDeviceExt other = (SdpPartyDeviceExt) obj;
		if (partyId == null) {
			if (other.partyId != null) {
				return false;
			}
		} else if (!partyId.equals(other.partyId)) {
			return false;
		}
		return true;
	}

}