package com.accenture.ams.accountmgmtservice.sdpAccountProfileDataService;

import java.io.Serializable;
import java.util.List;

import com.accenture.ams.db.bean.AccountDeviceAMS;

public class SdpAccountProfileDataBean implements Serializable{

	private static final long serialVersionUID = 1L;

	List<AccountDeviceAMS> listOfCrmAccountDevice=null;
	String userRememberPinFlag;
	String userPCLevel;
	String userPin;
	String parentalPin;
	String pcExtendedRating;
	String retailerDomain;
	
	public List<AccountDeviceAMS> getListOfCrmAccountDevice() {
		return listOfCrmAccountDevice;
	}
	public void setListOfCrmAccountDevice(
			List<AccountDeviceAMS> listOfCrmAccountDevice) {
		this.listOfCrmAccountDevice = listOfCrmAccountDevice;
	}
	public String getUserRememberPinFlag() {
		return userRememberPinFlag;
	}
	public void setUserRememberPinFlag(String userRememberPinFlag) {
		this.userRememberPinFlag = userRememberPinFlag;
	}
	public String getUserPCLevel() {
		return userPCLevel;
	}
	public void setUserPCLevel(String userPCLevel) {
		this.userPCLevel = userPCLevel;
	}
	public String getUserPin() {
		return userPin;
	}
	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}
	public String getParentalPin() {
		return parentalPin;
	}
	public void setParentalPin(String parentalPin) {
		this.parentalPin = parentalPin;
	}
	public String getPcExtendedRating() {
		return pcExtendedRating;
	}
	public void setPcExtendedRating(String pcExtendedRating) {
		this.pcExtendedRating = pcExtendedRating;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRetailerDomain() {
		return retailerDomain;
	}
	public void setRetailerDomain(String retailerDomain) {
		this.retailerDomain = retailerDomain;
	}
}
