package com.accenture.sdp.csmac.beans.party;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.accenture.sdp.csmac.beans.avs.CrmAccountDeviceBean;
import com.accenture.sdp.csmac.beans.avs.dto.UpdateCommProfOpBean;

public class AvsPartyBean extends PartyBean implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2238508016195506946L;

	// private String crmAccountId=name
	private Integer userType;
	private Integer userStatus;
	private List<CrmAccountDeviceBean> crmAccountDeviceList;
	private String username;
	private String password;
	private Date birthDate;
	private String gender;
	private String crmAccountZipCode;
	private String email;
	private String crmAccountMobileNumber;
	private String userCountry;
	private String userLanguage;
	private Integer qualitySetting;
	private Integer userPaymentMethod;
	private String userRememberPinFlag;
	// FIXME vogliono di default il red
	private Integer userPcLevel = 3;
	private String userPinPurchase;
	private String userPinParentalControl;
	private List<String> userPcExtendedRatings;
	private String crmAccountPurchaseBlockingFlag;
	private String crmAccountPurchaseBlockingThresholdCurrency;
	private BigDecimal crmAccountPurchaseBlockingThresholdValue;
	private Integer crmAccountPurchaseBlockingThresholdPeriod;
	private String crmAccountPurchaseBlockingAlertEnabledFlag;
	private Integer crmAccountPurchaseBlockingAlertChannel;
	private Integer crmAccountPurchaseBlockingAlertMode;
	private BigDecimal crmAccountPurchaseBlockingIntermediateThreshold;
	private String crmAccountConsumptionBlockingFlag;
	private Integer crmAccountConsumptionBlockingThresholdValue;
	private Integer crmAccountConsumptionBlockingThresholdPeriod;
	private String crmAccountConsumptionBlockingEnabledFlag;
	private Integer crmAccountConsumptionBlockingAlertChannel;
	private Integer crmAccountConsumptionBlockingAlertMode;
	private Integer crmAccountConsumptionBlockingIntermediateThreshold;
	private BigDecimal crmAccountPurchaseValue;
	private Integer crmAccountConsumption;
	private String crmAccountRetailerDomain;
	private List<UpdateCommProfOpBean> updateCommProfOpBeans;
	private Boolean blackListed;
	private Boolean whiteListed;
	private String devicePolicy;

	public Boolean getBlackListed() {
		return blackListed;
	}

	public void setBlackListed(Boolean blackListed) {
		this.blackListed = blackListed;
	}

	public Boolean getWhiteListed() {
		return whiteListed;
	}

	public void setWhiteListed(Boolean whiteListed) {
		this.whiteListed = whiteListed;
	}

	public String getDevicePolicy() {
		return devicePolicy;
	}

	public void setDevicePolicy(String devicePolicy) {
		this.devicePolicy = devicePolicy;
	}

	private String statusName;

	public String getCrmAccountId() {
		return getName();
	}

	public void setCrmAccountId(String crmAccountId) {
		setName(crmAccountId);
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public List<CrmAccountDeviceBean> getCrmAccountDeviceList() {
		return crmAccountDeviceList;
	}

	public void setCrmAccountDeviceList(List<CrmAccountDeviceBean> crmAccountDeviceList) {
		this.crmAccountDeviceList = crmAccountDeviceList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCrmAccountZipCode() {
		return crmAccountZipCode;
	}

	public void setCrmAccountZipCode(String crmAccountZipCode) {
		this.crmAccountZipCode = crmAccountZipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCrmAccountMobileNumber() {
		return crmAccountMobileNumber;
	}

	public void setCrmAccountMobileNumber(String crmAccountMobileNumber) {
		this.crmAccountMobileNumber = crmAccountMobileNumber;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public Integer getQualitySetting() {
		return qualitySetting;
	}

	public void setQualitySetting(Integer qualitySetting) {
		this.qualitySetting = qualitySetting;
	}

	public Integer getUserPaymentMethod() {
		return userPaymentMethod;
	}

	public void setUserPaymentMethod(Integer userPaymentMethod) {
		this.userPaymentMethod = userPaymentMethod;
	}

	public String getUserRememberPinFlag() {
		return userRememberPinFlag;
	}

	public void setUserRememberPinFlag(String userRememberPinFlag) {
		this.userRememberPinFlag = userRememberPinFlag;
	}

	public Integer getUserPcLevel() {
		return userPcLevel;
	}

	public void setUserPcLevel(Integer userPcLevel) {
		this.userPcLevel = userPcLevel;
	}

	public String getUserPinPurchase() {
		return userPinPurchase;
	}

	public void setUserPinPurchase(String userPinPurchase) {
		this.userPinPurchase = userPinPurchase;
	}

	public String getUserPinParentalControl() {
		return userPinParentalControl;
	}

	public void setUserPinParentalControl(String userPinParentalControl) {
		this.userPinParentalControl = userPinParentalControl;
	}

	public List<String> getUserPcExtendedRatings() {
		return userPcExtendedRatings;
	}

	public void setUserPcExtendedRatings(List<String> userPcExtendedRatings) {
		this.userPcExtendedRatings = userPcExtendedRatings;
	}

	public String getCrmAccountPurchaseBlockingFlag() {
		return crmAccountPurchaseBlockingFlag;
	}

	public void setCrmAccountPurchaseBlockingFlag(String crmAccountPurchaseBlockingFlag) {
		this.crmAccountPurchaseBlockingFlag = crmAccountPurchaseBlockingFlag;
	}

	public String getCrmAccountPurchaseBlockingThresholdCurrency() {
		return crmAccountPurchaseBlockingThresholdCurrency;
	}

	public void setCrmAccountPurchaseBlockingThresholdCurrency(String crmAccountPurchaseBlockingThresholdCurrency) {
		this.crmAccountPurchaseBlockingThresholdCurrency = crmAccountPurchaseBlockingThresholdCurrency;
	}

	public BigDecimal getCrmAccountPurchaseBlockingThresholdValue() {
		return crmAccountPurchaseBlockingThresholdValue;
	}

	public void setCrmAccountPurchaseBlockingThresholdValue(BigDecimal crmAccountPurchaseBlockingThresholdValue) {
		this.crmAccountPurchaseBlockingThresholdValue = crmAccountPurchaseBlockingThresholdValue;
	}

	public Integer getCrmAccountPurchaseBlockingThresholdPeriod() {
		return crmAccountPurchaseBlockingThresholdPeriod;
	}

	public void setCrmAccountPurchaseBlockingThresholdPeriod(Integer crmAccountPurchaseBlockingThresholdPeriod) {
		this.crmAccountPurchaseBlockingThresholdPeriod = crmAccountPurchaseBlockingThresholdPeriod;
	}

	public String getCrmAccountPurchaseBlockingAlertEnabledFlag() {
		return crmAccountPurchaseBlockingAlertEnabledFlag;
	}

	public void setCrmAccountPurchaseBlockingAlertEnabledFlag(String crmAccountPurchaseBlockingAlertEnabledFlag) {
		this.crmAccountPurchaseBlockingAlertEnabledFlag = crmAccountPurchaseBlockingAlertEnabledFlag;
	}

	public Integer getCrmAccountPurchaseBlockingAlertChannel() {
		return crmAccountPurchaseBlockingAlertChannel;
	}

	public void setCrmAccountPurchaseBlockingAlertChannel(Integer crmAccountPurchaseBlockingAlertChannel) {
		this.crmAccountPurchaseBlockingAlertChannel = crmAccountPurchaseBlockingAlertChannel;
	}

	public Integer getCrmAccountPurchaseBlockingAlertMode() {
		return crmAccountPurchaseBlockingAlertMode;
	}

	public void setCrmAccountPurchaseBlockingAlertMode(Integer crmAccountPurchaseBlockingAlertMode) {
		this.crmAccountPurchaseBlockingAlertMode = crmAccountPurchaseBlockingAlertMode;
	}

	public BigDecimal getCrmAccountPurchaseBlockingIntermediateThreshold() {
		return crmAccountPurchaseBlockingIntermediateThreshold;
	}

	public void setCrmAccountPurchaseBlockingIntermediateThreshold(BigDecimal crmAccountPurchaseBlockingIntermediateThreshold) {
		this.crmAccountPurchaseBlockingIntermediateThreshold = crmAccountPurchaseBlockingIntermediateThreshold;
	}

	public String getCrmAccountConsumptionBlockingFlag() {
		return crmAccountConsumptionBlockingFlag;
	}

	public void setCrmAccountConsumptionBlockingFlag(String crmAccountConsumptionBlockingFlag) {
		this.crmAccountConsumptionBlockingFlag = crmAccountConsumptionBlockingFlag;
	}

	public Integer getCrmAccountConsumptionBlockingThresholdValue() {
		return crmAccountConsumptionBlockingThresholdValue;
	}

	public void setCrmAccountConsumptionBlockingThresholdValue(Integer crmAccountConsumptionBlockingThresholdValue) {
		this.crmAccountConsumptionBlockingThresholdValue = crmAccountConsumptionBlockingThresholdValue;
	}

	public Integer getCrmAccountConsumptionBlockingThresholdPeriod() {
		return crmAccountConsumptionBlockingThresholdPeriod;
	}

	public void setCrmAccountConsumptionBlockingThresholdPeriod(Integer crmAccountConsumptionBlockingThresholdPeriod) {
		this.crmAccountConsumptionBlockingThresholdPeriod = crmAccountConsumptionBlockingThresholdPeriod;
	}

	public String getCrmAccountConsumptionBlockingEnabledFlag() {
		return crmAccountConsumptionBlockingEnabledFlag;
	}

	public void setCrmAccountConsumptionBlockingEnabledFlag(String crmAccountConsumptionBlockingEnabledFlag) {
		this.crmAccountConsumptionBlockingEnabledFlag = crmAccountConsumptionBlockingEnabledFlag;
	}

	public Integer getCrmAccountConsumptionBlockingAlertChannel() {
		return crmAccountConsumptionBlockingAlertChannel;
	}

	public void setCrmAccountConsumptionBlockingAlertChannel(Integer crmAccountConsumptionBlockingAlertChannel) {
		this.crmAccountConsumptionBlockingAlertChannel = crmAccountConsumptionBlockingAlertChannel;
	}

	public Integer getCrmAccountConsumptionBlockingAlertMode() {
		return crmAccountConsumptionBlockingAlertMode;
	}

	public void setCrmAccountConsumptionBlockingAlertMode(Integer crmAccountConsumptionBlockingAlertMode) {
		this.crmAccountConsumptionBlockingAlertMode = crmAccountConsumptionBlockingAlertMode;
	}

	public Integer getCrmAccountConsumptionBlockingIntermediateThreshold() {
		return crmAccountConsumptionBlockingIntermediateThreshold;
	}

	public void setCrmAccountConsumptionBlockingIntermediateThreshold(Integer crmAccountConsumptionBlockingIntermediateThreshold) {
		this.crmAccountConsumptionBlockingIntermediateThreshold = crmAccountConsumptionBlockingIntermediateThreshold;
	}

	public BigDecimal getCrmAccountPurchaseValue() {
		return crmAccountPurchaseValue;
	}

	public void setCrmAccountPurchaseValue(BigDecimal crmAccountPurchaseValue) {
		this.crmAccountPurchaseValue = crmAccountPurchaseValue;
	}

	public Integer getCrmAccountConsumption() {
		return crmAccountConsumption;
	}

	public void setCrmAccountConsumption(Integer crmAccountConsumption) {
		this.crmAccountConsumption = crmAccountConsumption;
	}

	public String getCrmAccountRetailerDomain() {
		return crmAccountRetailerDomain;
	}

	public void setCrmAccountRetailerDomain(String crmAccountRetailerDomain) {
		this.crmAccountRetailerDomain = crmAccountRetailerDomain;
	}

	public List<UpdateCommProfOpBean> getUpdateCommProfOpBeans() {
		return updateCommProfOpBeans;
	}

	public void setUpdateCommProfOpBeans(List<UpdateCommProfOpBean> updateCommProfOpBeans) {
		this.updateCommProfOpBeans = updateCommProfOpBeans;
	}

	public AvsPartyBean() {
		super();
		this.userPcExtendedRatings = new ArrayList<String>();
		this.updateCommProfOpBeans = new ArrayList<UpdateCommProfOpBean>();
		this.crmAccountDeviceList = new ArrayList<CrmAccountDeviceBean>();
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Object clone() throws CloneNotSupportedException {
		AvsPartyBean clone = (AvsPartyBean) super.clone();
		if (userPcExtendedRatings != null) {
			clone.setUserPcExtendedRatings(new ArrayList<String>(userPcExtendedRatings));
		}
		if (updateCommProfOpBeans != null) {
			clone.setUpdateCommProfOpBeans(new ArrayList<UpdateCommProfOpBean>(updateCommProfOpBeans));
		}
		if (crmAccountDeviceList != null) {
			clone.setCrmAccountDeviceList(new ArrayList<CrmAccountDeviceBean>(crmAccountDeviceList));
		}
		return clone;
	}
}
