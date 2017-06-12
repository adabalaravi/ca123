package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AVS_Platform database table.
 * 
 */
@Entity
@Table(name = "AVS_Platform")
@NamedQueries({ @NamedQuery(name = AvsPlatform.QUERY_RETRIEVE_ALL, query = "select o from AvsPlatform o ORDER BY o.platformId") })
public class AvsPlatform implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsPlatforms";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Id
	@Column(name = "platform_id")
	private int platformId;

	@Column(name = "platform_name")
	private String platformName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "AVS_LNK_PLATFORM_DEVICE_ID_TYPE", joinColumns = @JoinColumn(name = "platform_id"), inverseJoinColumns = @JoinColumn(name = "device_type_id", referencedColumnName = "type_id"))
	private List<AvsDeviceIdType> deviceIdTypes;

	public AvsPlatform() {
		deviceIdTypes = new ArrayList<AvsDeviceIdType>();
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return this.platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<AvsDeviceIdType> getDeviceIdTypes() {
		return deviceIdTypes;
	}

	public void setDeviceIdTypes(List<AvsDeviceIdType> deviceIdTypes) {
		this.deviceIdTypes = deviceIdTypes;
	}

}