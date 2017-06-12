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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_PACKAGE_GROUP database table.
 * 
 */
@Entity
@Table(name = "SDP_PACKAGE_GROUP")
@NamedQueries({ @NamedQuery(name = "selectPackageGroup", query = "select o from SdpOfferGroup o where o.groupName = :groupName and o.sdpSolutionOffer.solutionOfferId =:solutionOfferId") })
public class SdpOfferGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GROUP_ID")
	private Long groupId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOLUTION_OFFER_ID")
	private SdpSolutionOffer sdpSolutionOffer;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpPackage
	@OneToMany(mappedBy = "sdpOfferGroup")
	private List<SdpPackage> sdpPackages;

	public SdpOfferGroup() {
		sdpPackages = new ArrayList<SdpPackage>();
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public SdpSolutionOffer getSdpSolutionOffer() {
		return sdpSolutionOffer;
	}

	public void setSdpSolutionOffer(SdpSolutionOffer sdpSolutionOffer) {
		this.sdpSolutionOffer = sdpSolutionOffer;
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

	/**
	 * @return the sdpPackages
	 */
	public List<SdpPackage> getSdpPackages() {
		return sdpPackages;
	}

	/**
	 * @param sdpPackages
	 *            the sdpPackages to set
	 */
	public void setSdpPackages(List<SdpPackage> sdpPackages) {
		this.sdpPackages = sdpPackages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		SdpOfferGroup other = (SdpOfferGroup) obj;
		if (getGroupId() == null) {
			if (other.getGroupId() != null) {
				return false;
			}
		} else if (!getGroupId().equals(other.getGroupId())) {
			return false;
		}
		return true;
	}

}