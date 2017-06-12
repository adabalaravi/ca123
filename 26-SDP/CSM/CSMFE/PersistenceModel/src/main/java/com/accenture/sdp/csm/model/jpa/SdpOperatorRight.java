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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_ROLE database table.
 * 
 */
@Entity
@Table(name = "SDP_OPERATOR_RIGHT")
@NamedQueries({ @NamedQuery(name = "selectAllOperatorRight", query = "select r from SdpOperatorRight r") })
public class SdpOperatorRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OPERATOR_RIGHT_ID")
	private Long id;

	@Column(name = "OPERATOR_RIGHT_NAME")
	private String name;

	@Column(name = "OPERATOR_RIGHT_DESCRIPTION")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_RESOURCE_ID")
	private RefOperatorResource operatorResource;

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

	@ManyToMany(mappedBy = "operatorRights", fetch = FetchType.LAZY)
	private List<SdpOperator> operators;

	public SdpOperatorRight() {
		operators = new ArrayList<SdpOperator>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RefOperatorResource getOperatorResource() {
		return operatorResource;
	}

	public void setOperatorResource(RefOperatorResource operatorResource) {
		this.operatorResource = operatorResource;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SdpOperatorRight other = (SdpOperatorRight) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public List<SdpOperator> getOperators() {
		return operators;
	}

	public void setOperators(List<SdpOperator> operators) {
		this.operators = operators;
	}

}