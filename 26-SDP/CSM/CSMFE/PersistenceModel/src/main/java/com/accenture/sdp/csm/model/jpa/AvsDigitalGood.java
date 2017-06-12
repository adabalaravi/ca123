package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AVS_Digital_Good database table.
 * 
 */
@Entity
@Table(name = "AVS_Digital_Good")
@NamedQueries({ @NamedQuery(name = AvsDigitalGood.QUERY_RETRIEVE_ALL, query = "select dg from AvsDigitalGood dg ORDER BY dg.id") })
public class AvsDigitalGood implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsDigitalGoods";

	@Id
	@Column(name = "digital_good_id")
	private Double id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	private String type;

	@Column(name = "external_id")
	private String externalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	// bi-directional many-to-one association to AvsLinkOfferDigitalGood
	@OneToMany(mappedBy = "avsDigitalGood", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AvsLnkOfferDigitalGood> offers;

	public AvsDigitalGood() {
		offers = new ArrayList<AvsLnkOfferDigitalGood>();
	}

	public List<AvsLnkOfferDigitalGood> getOffers() {
		return offers;
	}

	public void setOffers(List<AvsLnkOfferDigitalGood> offers) {
		this.offers = offers;
	}

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
		AvsDigitalGood other = (AvsDigitalGood) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}