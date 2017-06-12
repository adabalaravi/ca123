package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the REF_OPERATOR_RESOURCE database table.
 * 
 */
@Entity
@Table(name = "REF_OPERATOR_RESOURCE")
public class RefOperatorResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OPERATOR_RESOURCE_ID")
	private long id;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "OPERATOR_RESOURCE_DESCRIPTION")
	private String description;

	@Column(name = "OPERATOR_RESOURCE_NAME")
	private String name;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpOperator
	@OneToMany(mappedBy = "operatorResource", fetch = FetchType.LAZY)
	private List<SdpOperatorRight> operatorRights;

	public RefOperatorResource() {
		operatorRights = new ArrayList<SdpOperatorRight>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<SdpOperatorRight> getOperatorRights() {
		return operatorRights;
	}

	public void setOperatorRights(List<SdpOperatorRight> operatorRights) {
		this.operatorRights = operatorRights;
	}

}