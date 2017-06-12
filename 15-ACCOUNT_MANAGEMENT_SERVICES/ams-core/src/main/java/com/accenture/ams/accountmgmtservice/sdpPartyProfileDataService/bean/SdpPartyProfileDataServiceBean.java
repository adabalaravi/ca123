package com.accenture.ams.accountmgmtservice.sdpPartyProfileDataService.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class SdpPartyProfileDataServiceBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String userType;
	private String userLanguage;
	private String qualitySetting;
	private Integer userPaymentMethod;
	private boolean crmAccountPurchaseBlockingFlag;
	private String crmAccountPurchaseBlockingThresholdCurrency;
	private BigDecimal crmAccountPurchaseBlockingThresholdValue;
	private String crmAccountPurchaseBlockingThresholdPeriod;
	private boolean crmAccountPurchaseBlockingAlertEnabledFlag;
	private String crmAccountPurchaseBlockingAlertChannel;
	private String crmAccountPurchaseBlockingAlertMode;
	private BigDecimal crmAccountPurchaseBlockingIntermediateThreshold;
	private boolean crmAccountConsumptionBlockingFlag;
	private Integer crmAccountConsumptionBlockingThresholdValue;
	private String crmAccountConsumptionBlockingThresholdPeriod;
	private boolean crmAccountConsumptionBlockingEnabledFlag;
	private String crmAccountConsumptionBlockingAlertChannel;
	private String crmAccountConsumptionBlockingAlertMode;
	private Integer crmAccountConsumptionBlockingIntermediateThreshold;
	private BigDecimal crmAccountPurchaseValue;
	private Integer crmAccountConsumption;
	
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserLanguage() {
		return userLanguage;
	}
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}
	public String getQualitySetting() {
		return qualitySetting;
	}
	public void setQualitySetting(String qualitySetting) {
		this.qualitySetting = qualitySetting;
	}
	public Integer getUserPaymentMethod() {
		return userPaymentMethod;
	}
	public void setUserPaymentMethod(Integer userPaymentMethod) {
		this.userPaymentMethod = userPaymentMethod;
	}
	public boolean isCrmAccountPurchaseBlockingFlag() {
		return crmAccountPurchaseBlockingFlag;
	}
	public void setCrmAccountPurchaseBlockingFlag(
			boolean crmAccountPurchaseBlockingFlag) {
		this.crmAccountPurchaseBlockingFlag = crmAccountPurchaseBlockingFlag;
	}
	public String getCrmAccountPurchaseBlockingThresholdCurrency() {
		return crmAccountPurchaseBlockingThresholdCurrency;
	}
	public void setCrmAccountPurchaseBlockingThresholdCurrency(
			String crmAccountPurchaseBlockingThresholdCurrency) {
		this.crmAccountPurchaseBlockingThresholdCurrency = crmAccountPurchaseBlockingThresholdCurrency;
	}
	public BigDecimal getCrmAccountPurchaseBlockingThresholdValue() {
		return crmAccountPurchaseBlockingThresholdValue;
	}
	public void setCrmAccountPurchaseBlockingThresholdValue(
			BigDecimal crmAccountPurchaseBlockingThresholdValue) {
		this.crmAccountPurchaseBlockingThresholdValue = crmAccountPurchaseBlockingThresholdValue;
	}
	public String getCrmAccountPurchaseBlockingThresholdPeriod() {
		return crmAccountPurchaseBlockingThresholdPeriod;
	}
	public void setCrmAccountPurchaseBlockingThresholdPeriod(
			String crmAccountPurchaseBlockingThresholdPeriod) {
		this.crmAccountPurchaseBlockingThresholdPeriod = crmAccountPurchaseBlockingThresholdPeriod;
	}
	public boolean isCrmAccountPurchaseBlockingAlertEnabledFlag() {
		return crmAccountPurchaseBlockingAlertEnabledFlag;
	}
	public void setCrmAccountPurchaseBlockingAlertEnabledFlag(
			boolean crmAccountPurchaseBlockingAlertEnabledFlag) {
		this.crmAccountPurchaseBlockingAlertEnabledFlag = crmAccountPurchaseBlockingAlertEnabledFlag;
	}
	public String getCrmAccountPurchaseBlockingAlertChannel() {
		return crmAccountPurchaseBlockingAlertChannel;
	}
	public void setCrmAccountPurchaseBlockingAlertChannel(
			String crmAccountPurchaseBlockingAlertChannel) {
		this.crmAccountPurchaseBlockingAlertChannel = crmAccountPurchaseBlockingAlertChannel;
	}
	public String getCrmAccountPurchaseBlockingAlertMode() {
		return crmAccountPurchaseBlockingAlertMode;
	}
	public void setCrmAccountPurchaseBlockingAlertMode(
			String crmAccountPurchaseBlockingAlertMode) {
		this.crmAccountPurchaseBlockingAlertMode = crmAccountPurchaseBlockingAlertMode;
	}
	public BigDecimal getCrmAccountPurchaseBlockingIntermediateThreshold() {
		return crmAccountPurchaseBlockingIntermediateThreshold;
	}
	public void setCrmAccountPurchaseBlockingIntermediateThreshold(
			BigDecimal crmAccountPurchaseBlockingIntermediateThreshold) {
		this.crmAccountPurchaseBlockingIntermediateThreshold = crmAccountPurchaseBlockingIntermediateThreshold;
	}
	public boolean isCrmAccountConsumptionBlockingFlag() {
		return crmAccountConsumptionBlockingFlag;
	}
	public void setCrmAccountConsumptionBlockingFlag(
			boolean crmAccountConsumptionBlockingFlag) {
		this.crmAccountConsumptionBlockingFlag = crmAccountConsumptionBlockingFlag;
	}
	public Integer getCrmAccountConsumptionBlockingThresholdValue() {
		return crmAccountConsumptionBlockingThresholdValue;
	}
	public void setCrmAccountConsumptionBlockingThresholdValue(
			Integer crmAccountConsumptionBlockingThresholdValue) {
		this.crmAccountConsumptionBlockingThresholdValue = crmAccountConsumptionBlockingThresholdValue;
	}
	public String getCrmAccountConsumptionBlockingThresholdPeriod() {
		return crmAccountConsumptionBlockingThresholdPeriod;
	}
	public void setCrmAccountConsumptionBlockingThresholdPeriod(
			String crmAccountConsumptionBlockingThresholdPeriod) {
		this.crmAccountConsumptionBlockingThresholdPeriod = crmAccountConsumptionBlockingThresholdPeriod;
	}
	public boolean isCrmAccountConsumptionBlockingEnabledFlag() {
		return crmAccountConsumptionBlockingEnabledFlag;
	}
	public void setCrmAccountConsumptionBlockingEnabledFlag(
			boolean crmAccountConsumptionBlockingEnabledFlag) {
		this.crmAccountConsumptionBlockingEnabledFlag = crmAccountConsumptionBlockingEnabledFlag;
	}
	public String getCrmAccountConsumptionBlockingAlertChannel() {
		return crmAccountConsumptionBlockingAlertChannel;
	}
	public void setCrmAccountConsumptionBlockingAlertChannel(
			String crmAccountConsumptionBlockingAlertChannel) {
		this.crmAccountConsumptionBlockingAlertChannel = crmAccountConsumptionBlockingAlertChannel;
	}
	public String getCrmAccountConsumptionBlockingAlertMode() {
		return crmAccountConsumptionBlockingAlertMode;
	}
	public void setCrmAccountConsumptionBlockingAlertMode(
			String crmAccountConsumptionBlockingAlertMode) {
		this.crmAccountConsumptionBlockingAlertMode = crmAccountConsumptionBlockingAlertMode;
	}
	public Integer getCrmAccountConsumptionBlockingIntermediateThreshold() {
		return crmAccountConsumptionBlockingIntermediateThreshold;
	}
	public void setCrmAccountConsumptionBlockingIntermediateThreshold(
			Integer crmAccountConsumptionBlockingIntermediateThreshold) {
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
	@Override
	public String toString() {
		return "SdpPartyProfileDataServiceBean [userType=" + userType
				+ ", userLanguage=" + userLanguage + ", qualitySetting="
				+ qualitySetting + ", userPaymentMethod=" + userPaymentMethod
				+ ", crmAccountPurchaseBlockingFlag="
				+ crmAccountPurchaseBlockingFlag
				+ ", crmAccountPurchaseBlockingThresholdCurrency="
				+ crmAccountPurchaseBlockingThresholdCurrency
				+ ", crmAccountPurchaseBlockingThresholdValue="
				+ crmAccountPurchaseBlockingThresholdValue
				+ ", crmAccountPurchaseBlockingThresholdPeriod="
				+ crmAccountPurchaseBlockingThresholdPeriod
				+ ", crmAccountPurchaseBlockingAlertEnabledFlag="
				+ crmAccountPurchaseBlockingAlertEnabledFlag
				+ ", crmAccountPurchaseBlockingAlertChannel="
				+ crmAccountPurchaseBlockingAlertChannel
				+ ", crmAccountPurchaseBlockingAlertMode="
				+ crmAccountPurchaseBlockingAlertMode
				+ ", crmAccountPurchaseBlockingIntermediateThreshold="
				+ crmAccountPurchaseBlockingIntermediateThreshold
				+ ", crmAccountConsumptionBlockingFlag="
				+ crmAccountConsumptionBlockingFlag
				+ ", crmAccountConsumptionBlockingThresholdValue="
				+ crmAccountConsumptionBlockingThresholdValue
				+ ", crmAccountConsumptionBlockingThresholdPeriod="
				+ crmAccountConsumptionBlockingThresholdPeriod
				+ ", crmAccountConsumptionBlockingEnabledFlag="
				+ crmAccountConsumptionBlockingEnabledFlag
				+ ", crmAccountConsumptionBlockingAlertChannel="
				+ crmAccountConsumptionBlockingAlertChannel
				+ ", crmAccountConsumptionBlockingAlertMode="
				+ crmAccountConsumptionBlockingAlertMode
				+ ", crmAccountConsumptionBlockingIntermediateThreshold="
				+ crmAccountConsumptionBlockingIntermediateThreshold
				+ ", crmAccountPurchaseValue=" + crmAccountPurchaseValue
				+ ", crmAccountConsumption=" + crmAccountConsumption + "]";
	}
	
	

}
