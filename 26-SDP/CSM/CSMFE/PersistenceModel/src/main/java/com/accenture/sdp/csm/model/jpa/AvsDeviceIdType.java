package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AVS_Device_ID_Type database table.
 * 
 */
@Entity
@Table(name = "AVS_Device_ID_Type")
@NamedQueries({ @NamedQuery(name = AvsDeviceIdType.QUERY_RETRIEVE_ALL, query = "select o from AvsDeviceIdType o ORDER BY o.typeId") })
public class AvsDeviceIdType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsDeviceIdTypes";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "type_description")
	private String typeDescription;

	@Id
	@Column(name = "type_id")
	private int typeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	private String value;

	@ManyToMany(mappedBy = "deviceIdTypes", fetch = FetchType.LAZY)
	private List<AvsPlatform> platforms;

	public AvsDeviceIdType() {
		platforms = new ArrayList<AvsPlatform>();
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<AvsPlatform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<AvsPlatform> platforms) {
		this.platforms = platforms;
	}

}