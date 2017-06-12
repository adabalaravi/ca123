package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;

/**
 * The persistent class for the sdp_device database table.
 * 
 */
@Entity
@Table(name = "sdp_device")
@NamedQueries({ @NamedQuery(name = SdpDevice.QUERY_RETRIEVE_BY_BLACKLIST, query = "select p from SdpDevice p where p.isBl=:isBl"),
		@NamedQuery(name = SdpDevice.QUERY_RETRIEVE_BY_WHITELIST, query = "select p from SdpDevice p where p.isWl=:isWl") })
public class SdpDevice extends BlackWhiteListableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_BLACKLIST = "selectDeviceByBlacklist";
	public static final String QUERY_RETRIEVE_BY_WHITELIST = "selectDeviceByWhitelist";

	public static final String PARAM_IS_BL = "isBl";
	public static final String PARAM_IS_WL = "isWl";

	@Id
	@Column(name = "DEVICE_UUID")
	private String deviceUuid;

	private String alias;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	@Column(name = "DELETED_BY_ID")
	private String deletedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_DATE")
	private Date deletedDate;

	@Column(name = "IS_PAIRED")
	private Byte isPaired;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_FRUITION_DATE")
	private Date lastFruitionDate;

	// bi-directional many-to-one association to SdpPartyDeviceExt
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTY_ID")
	private SdpPartyDeviceExt sdpPartyDeviceExt;

	@Column(name = "STATUS_ID")
	private Long statusId;

	// bi-directional many-to-one association to RefDeviceUuidType
	@ManyToOne
	@JoinColumn(name = "DEVICE_UUID_TYPE_ID")
	private RefDeviceUuidType refDeviceUuidType;

	// bi-directional many-to-one association to RefDeviceChannel
	@ManyToOne
	@JoinColumn(name = "DEVICE_CHANNEL_ID")
	private RefDeviceChannel refDeviceChannel;

	// bi-directional many-to-one association to RefDeviceBrand
	@ManyToOne
	@JoinColumn(name = "DEVICE_BRAND_ID")
	private RefDeviceBrand refDeviceBrand;

	// bi-directional many-to-one association to RefDeviceModel
	@ManyToOne
	@JoinColumn(name = "DEVICE_MODEL_ID")
	private RefDeviceModel refDeviceModel;

	public SdpDevice() {
	}

	public String getDeviceUuid() {
		return this.deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public Byte getIsPaired() {
		return this.isPaired;
	}

	public void setIsPaired(Byte isPaired) {
		this.isPaired = isPaired;
	}

	public Date getLastFruitionDate() {
		return this.lastFruitionDate;
	}

	public void setLastFruitionDate(Date lastFruitionDate) {
		this.lastFruitionDate = lastFruitionDate;
	}

	public SdpPartyDeviceExt getSdpPartyDeviceExt() {
		return sdpPartyDeviceExt;
	}

	public void setSdpPartyDeviceExt(SdpPartyDeviceExt sdpPartyDeviceExt) {
		this.sdpPartyDeviceExt = sdpPartyDeviceExt;
	}

	public Long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public RefDeviceUuidType getRefDeviceUuidType() {
		return this.refDeviceUuidType;
	}

	public void setRefDeviceUuidType(RefDeviceUuidType refDeviceUuidType) {
		this.refDeviceUuidType = refDeviceUuidType;
	}

	public RefDeviceChannel getRefDeviceChannel() {
		return this.refDeviceChannel;
	}

	public void setRefDeviceChannel(RefDeviceChannel refDeviceChannel) {
		this.refDeviceChannel = refDeviceChannel;
	}

	public RefDeviceBrand getRefDeviceBrand() {
		return this.refDeviceBrand;
	}

	public void setRefDeviceBrand(RefDeviceBrand refDeviceBrand) {
		this.refDeviceBrand = refDeviceBrand;
	}

	public RefDeviceModel getRefDeviceModel() {
		return this.refDeviceModel;
	}

	public void setRefDeviceModel(RefDeviceModel refDeviceModel) {
		this.refDeviceModel = refDeviceModel;
	}

}