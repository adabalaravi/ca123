package com.accenture.avs.ca.be.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;

//Added as part of UserConcurrentStreaming API
public class UserConcurrentStreamingCA implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timestamp creation_date;
	private Timestamp update_date;
	private Long sno;
	
	private String crmAccountId;
	private String deviceType;
	private String sessionid;
	private String is_disabled;
	private String channel;
	
	public String getIs_disabled() {
		return is_disabled;
	}
	public void setIs_disabled(String is_disabled) {
		this.is_disabled = is_disabled;
	}
	
	
	public Timestamp getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}
	public Timestamp getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	
	public Long getSno() {
		return sno;
	}
	public void setSno(Long sno) {
		this.sno = sno;
	}
	public String getCrmAccountId() {
		return crmAccountId;
	}
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
			
}
