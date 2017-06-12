package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;

@ManagedBean(name = ApplicationConstants.DEVICE_BEAN_NAME)
@SessionScoped
public class DeviceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private boolean paired;
	private String type;
	private String brand;
	private String model;
	private String alias;
	private boolean whiteList;
	private boolean blackList;
	private String status;
	private Date recordingDate;
	private Date lastUseDate;
	private boolean associated;
	
	public boolean isAssociated() {
		return associated;
	}
	public void setAssociated(boolean associated) {
		this.associated = associated;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isPaired() {
		return paired;
	}
	public void setPaired(boolean paired) {
		this.paired = paired;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean isWhiteList() {
		return whiteList;
	}
	public void setWhiteList(boolean whiteList) {
		this.whiteList = whiteList;
	}
	public boolean isBlackList() {
		return blackList;
	}
	public void setBlackList(boolean blackList) {
		this.blackList = blackList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRecordingDate() {
		return recordingDate;
	}
	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}
	public Date getLastUseDate() {
		return lastUseDate;
	}
	public void setLastUseDate(Date lastUseDate) {
		this.lastUseDate = lastUseDate;
	}
	@Override
	public String toString() {
		return "Device [id=" + id + ", paired=" + paired + ", type=" + type
				+ ", brand=" + brand + ", model=" + model + ", alias=" + alias
				+ ", whiteList=" + whiteList + ", blackList=" + blackList
				+ ", status=" + status + ", recordingDate=" + recordingDate
				+ ", lastUseDate=" + lastUseDate + ", associated=" + associated
				+ "]";
	}
	
	
}
