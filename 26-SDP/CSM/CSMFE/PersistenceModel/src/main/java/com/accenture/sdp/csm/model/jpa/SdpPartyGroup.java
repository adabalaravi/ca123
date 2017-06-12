package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_SOLUTION_TYPE database table.
 * 
 */
@Entity
@Table(name = "REF_PARTY_GROUP")
@NamedQueries({ @NamedQuery(name = SdpPartyGroup.QUERY_RETRIEVE_BY_NAME, query = "select p from SdpPartyGroup p where p.partyGroupName=:partyGroupName"),
		@NamedQuery(name = SdpPartyGroup.QUERY_RETRIEVE_ALL, query = "select p from SdpPartyGroup p"),
		@NamedQuery(name = SdpPartyGroup.QUERY_COUNT_ALL, query = "select COUNT(p.partyGroupId) from SdpPartyGroup p") })
public class SdpPartyGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_NAME = "selectPartyGroupByName";
	public static final String QUERY_RETRIEVE_ALL = "selectAllPartyGroup";
	public static final String QUERY_COUNT_ALL = "countAllPartyGroup";

	public static final String PARAM_PARTY_GROUP_NAME = "partyGroupName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARTY_GROUP_ID")
	private Long partyGroupId;

	@Column(name = "PARTY_GROUP_NAME")
	private String partyGroupName;

	@Column(name = "PARTY_GROUP_DESCRIPTION")
	private String partyGroupDescription;

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

	@ManyToMany(mappedBy = "partyGroups", fetch = FetchType.LAZY)
	private List<SdpParty> sdpParties;

	@ManyToMany(mappedBy = "sdpPartyGroups", fetch = FetchType.LAZY)
	private List<SdpSolution> sdpSolutions;

	@ManyToMany(mappedBy = "partyGroups", fetch = FetchType.LAZY)
	private List<SdpSolutionOffer> solutionOffers;

	public SdpPartyGroup() {
	}

	public Long getPartyGroupId() {
		return partyGroupId;
	}

	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}

	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}

	public String getPartyGroupDescription() {
		return partyGroupDescription;
	}

	public void setPartyGroupDescription(String partyGroupDescription) {
		this.partyGroupDescription = partyGroupDescription;
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

	public List<SdpSolution> getSdpSolutions() {
		return sdpSolutions;
	}

	public void setSdpSolutions(List<SdpSolution> sdpSolutions) {
		this.sdpSolutions = sdpSolutions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partyGroupId == null) ? 0 : partyGroupId.hashCode());
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
		SdpPartyGroup other = (SdpPartyGroup) obj;
		if (getPartyGroupId() == null) {
			if (other.getPartyGroupId() != null) {
				return false;
			}
		} else if (!getPartyGroupId().equals(other.getPartyGroupId())) {
			return false;
		}
		return true;
	}

	public List<SdpParty> getSdpParties() {
		return sdpParties;
	}

	public void setSdpParties(List<SdpParty> sdpParties) {
		this.sdpParties = sdpParties;
	}

	public List<SdpSolutionOffer> getSolutionOffers() {
		return solutionOffers;
	}

	public void setSolutionOffers(List<SdpSolutionOffer> solutionOffers) {
		this.solutionOffers = solutionOffers;
	}

}