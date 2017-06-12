package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;

@ManagedBean(name = ApplicationConstants.DEVICE_RULES_BEAN_NAME)
@SessionScoped
public class DeviceRulesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int maxNumber;
	private String type;
	private int alreadyRegistered;
	public int getAlreadyRegistered() {
		return alreadyRegistered;
	}
	public void setAlreadyRegistered(int alreadyRegistered) {
		this.alreadyRegistered = alreadyRegistered;
	}
	public int getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
