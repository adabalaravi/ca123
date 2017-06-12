package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AVS_RETAILER_DOMAIN database table.
 * 
 */
@Entity
@Table(name = "AVS_RETAILER_DOMAIN")
@NamedQueries({ @NamedQuery(name = AvsRetailerDomain.QUERY_RETRIEVE_ALL, query = "select o from AvsRetailerDomain o") })
public class AvsRetailerDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsRetailerDomains";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	@Id
	@Column(name = "RETAILER_ID")
	private String retailerId;

	@Column(name = "HOST_DOMAIN")
	private String hostDomain;

	public AvsRetailerDomain() {
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public String getHostDomain() {
		return hostDomain;
	}

	public void setHostDomain(String hostDomain) {
		this.hostDomain = hostDomain;
	}

}