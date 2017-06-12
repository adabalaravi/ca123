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
 * The persistent class for the SDP_SOLUTION database table.
 * 
 */
@Entity
@Table(name = "SDP_SOLUTION")
@NamedQueries({
		@NamedQuery(name = "selectSolutionByName", query = "select s from SdpSolution s where s.solutionName = :solutionName"),
		@NamedQuery(name = "selectAllSolutions", query = "select s from SdpSolution s"),
		@NamedQuery(name = "selectAllSolutionsCount", query = "select COUNT(s.solutionId) from SdpSolution s"),
		@NamedQuery(name = "selectSolutionsByPartyGroup", query = "select s from SdpSolution s, LnkSolutionPartyGroup lnk, SdpPartyGroup pgroup "
				+ "where pgroup.partyGroupName = :partyGroupName and s.solutionId=lnk.id.solutionId and lnk.id.partyGroupId=pgroup.partyGroupId "),
		@NamedQuery(name = "selectSolutionsByPartyGroupCount", query = "select count(s.solutionId) from SdpSolution s, LnkSolutionPartyGroup lnk,"
				+ "SdpPartyGroup pgroup where pgroup.partyGroupName = :partyGroupName and s.solutionId=lnk.id.solutionId and lnk.id.partyGroupId=pgroup.partyGroupId "),
		@NamedQuery(name = "selectSolutionByExternalId", query = "select a from SdpSolution a where a.externalId=:externalId")

})
public class SdpSolution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SOLUTION_ID")
	private Long solutionId;

	// bi-directional many-to-one association to RefServiceType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOLUTION_TYPE_ID")
	private RefSolutionType refSolutionType;

	@Column(name = "SOLUTION_NAME")
	private String solutionName;

	@Column(name = "SOLUTION_DESCRIPTION")
	private String solutionDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Column(name = "DELETED_BY_ID")
	private String deletedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_DATE")
	private Date deletedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;

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
	@Column(name = "SOLUTION_PROFILE")
	private String solutionProfile;

	// bi-directional many-to-many association to sdpPartyGroup
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "LNK_SOLUTION_PARTY_GROUP", joinColumns = @JoinColumn(name = "SOLUTION_ID"), inverseJoinColumns = @JoinColumn(name = "PARTY_GROUP_ID"))
	private List<SdpPartyGroup> sdpPartyGroups;

	// bi-directional many-to-one association to SdpSolutionOffer
	@OneToMany(mappedBy = "sdpSolution", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpSolutionOffer> sdpSolutionOffers;

	public SdpSolution() {
		sdpSolutionOffers = new ArrayList<SdpSolutionOffer>();
	}

	public Long getSolutionId() {
		return this.solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
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

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getSolutionDescription() {
		return this.solutionDescription;
	}

	public void setSolutionDescription(String solutionDescription) {
		this.solutionDescription = solutionDescription;
	}

	public String getSolutionName() {
		return this.solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
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

	public RefSolutionType getRefSolutionType() {
		return this.refSolutionType;
	}

	public void setRefSolutionType(RefSolutionType refSolutionType) {
		this.refSolutionType = refSolutionType;
	}

	public List<SdpSolutionOffer> getSdpSolutionOffers() {
		return this.sdpSolutionOffers;
	}

	public void setSdpSolutionOffers(List<SdpSolutionOffer> sdpSolutionOffers) {
		this.sdpSolutionOffers = sdpSolutionOffers;
	}

	public List<SdpPartyGroup> getSdpPartyGroups() {
		return sdpPartyGroups;
	}

	public String getSolutionProfile() {
		return solutionProfile;
	}

	public void setSolutionProfile(String solutionProfile) {
		this.solutionProfile = solutionProfile;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setSdpPartyGroups(List<SdpPartyGroup> sdpPartyGroups) {
		this.sdpPartyGroups = sdpPartyGroups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((solutionId == null) ? 0 : solutionId.hashCode());
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
		SdpSolution other = (SdpSolution) obj;
		if (getSolutionId() == null) {
			if (other.getSolutionId() != null) {
				return false;
			}
		} else if (!getSolutionId().equals(other.getSolutionId())) {
			return false;
		}
		return true;
	}

}