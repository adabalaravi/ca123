package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;


public class PackageBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String offerName;
	private BigDecimal nrcPriceCatalog;
	private BigDecimal rcPriceCatalog;
	private String currencyPriceName;
	private String type;
	private String parentOfferName;
	private boolean basicFlag;
	private Long frequencyDays;
	private String frequencyName;
	private Map<Long, PriceBean>  priceMap;
	private Map<Long, FrequencyBean> frequencyMap;

	private Long packageId;
	private Long offerId;
	private Long currencyTypeId;
	private String groupName;
	private Long groupId;
	private String mandatory;
	private Long nrcPriceCatalogId;
	private String packageExternalId;
	private String packageProfile;
	private Long parentOfferId;
	private Long basePackageId;
	private String rcFlagProrate;
	private Long rcFrequencyTypeId;
	private String rcInAdvance;
	private Long rcPriceCatalogId;
	private Long statusId;
	private String statusName;

	private Long nrcPriceCatalogIdStored;
	private Long rcPriceCatalogIdStored;
	private Long rcFrequencyTypeIdStored;
	private boolean basicFlagStored;
	private Long groupIdStored;
	private Long basePackageIdStored;
	private String mandatoryStored;

	// DISCOUNTS

	private Long discountId;
	private String newSetupFee;
	private String newRecurringFee;	
	private String setupFeeDiscount;
	private String recurringFeeDiscount;	
	private String setupFeeDiscountPercentage;
	private String recurringFeeDiscountPercentage;	





	public void resetOfferDetail(){
		this.nrcPriceCatalogId=this.nrcPriceCatalogIdStored;
		this.rcPriceCatalogId=this.rcPriceCatalogIdStored;
		this.rcFrequencyTypeId=this.rcFrequencyTypeIdStored;
		this.basicFlag=this.basicFlagStored;
		this.groupId=this.groupIdStored;
		this.basePackageId=this.basePackageIdStored;
		this.mandatory=this.mandatoryStored;
	}

	public void storeValues(){
		this.nrcPriceCatalogIdStored=this.nrcPriceCatalogId;
		this.rcPriceCatalogIdStored=this.rcPriceCatalogId;
		this.rcFrequencyTypeIdStored=this.rcFrequencyTypeId;
		this.groupIdStored=this.groupId;
		this.basePackageIdStored=this.basePackageId;
		this.mandatoryStored=this.mandatory;
		//update flag
		if (ApplicationConstants.YES_VALUE.equalsIgnoreCase(this.mandatory)){
			this.basicFlag = true;
		}
		this.basicFlagStored=this.basicFlag;
	}	

	
	public PackageBean() {
		super();
		mandatory = ApplicationConstants.YES_VALUE;
		nrcPriceCatalogId = 1L;
		rcPriceCatalogId = 1L;
		rcFrequencyTypeId = 1L;
		currencyTypeId = 1L;
		rcFlagProrate = ApplicationConstants.NO_VALUE;
		rcInAdvance = ApplicationConstants.NO_VALUE;
		frequencyDays = 0L;
	}


	public Long getPackageId() {
		return packageId;
	}


	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}


	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public Long getNrcPriceCatalogId() {
		return nrcPriceCatalogId;
	}

	public void setNrcPriceCatalogId(Long nrcPriceCatalogId) {
		this.nrcPriceCatalogId = nrcPriceCatalogId;
	}

	public String getPackageExternalId() {
		return packageExternalId;
	}

	public void setPackageExternalId(String packageExternalId) {
		this.packageExternalId = packageExternalId;
	}

	public String getPackageProfile() {
		return packageProfile;
	}

	public void setPackageProfile(String packageProfile) {
		this.packageProfile = packageProfile;
	}

	public Long getParentOfferId() {
		return parentOfferId;
	}

	public void setParentOfferId(Long parentOfferId) {
		this.parentOfferId = parentOfferId;
	}

	public String getRcFlagProrate() {
		return rcFlagProrate;
	}

	public void setRcFlagProrate(String rcFlagProrate) {
		this.rcFlagProrate = rcFlagProrate;
	}

	public Long getRcFrequencyTypeId() {
		return rcFrequencyTypeId;
	}

	public void setRcFrequencyTypeId(Long rcFrequencyTypeId) {
		this.rcFrequencyTypeId = rcFrequencyTypeId;
	}

	public String getRcInAdvance() {
		return rcInAdvance;
	}

	public void setRcInAdvance(String rcInAdvance) {
		this.rcInAdvance = rcInAdvance;
	}

	public Long getRcPriceCatalogId() {
		return rcPriceCatalogId;
	}

	public void setRcPriceCatalogId(Long rcPriceCatalogId) {
		this.rcPriceCatalogId = rcPriceCatalogId;
	}

	public Long getFrequencyDays() {
		return frequencyDays;
	}

	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
	}

	public BigDecimal getNrcPriceCatalog() {
		return nrcPriceCatalog;
	}

	public void setNrcPriceCatalog(BigDecimal nrcPriceCatalog) {
		this.nrcPriceCatalog = nrcPriceCatalog;
	}

	public BigDecimal getRcPriceCatalog() {
		return rcPriceCatalog;
	}

	public void setRcPriceCatalog(BigDecimal rcPriceCatalog) {
		this.rcPriceCatalog = rcPriceCatalog;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentOfferName() {
		return parentOfferName;
	}

	public void setParentOfferName(String parentOfferName) {
		this.parentOfferName = parentOfferName;
	}

	public boolean isBasicFlag() {
		return basicFlag;
	}

	public void setBasicFlag(boolean basicFlag) {
		this.basicFlag = basicFlag;
	}


	public Long getGroupId() {
		return groupId;
	}


	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}


	public Long getBasePackageId() {
		return basePackageId;
	}


	public void setBasePackageId(Long basePackageId) {
		this.basePackageId = basePackageId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Map<Long, FrequencyBean> getFrequencyMap() {
		return frequencyMap;
	}

	public void setFrequencyMap(Map<Long, FrequencyBean> frequencyMap) {
		this.frequencyMap = frequencyMap;
	}



	public void packageMandatoryChangeListener(ValueChangeEvent event) {
		Object value = ((HtmlSelectOneMenu)event.getComponent()).getValue();
		if (value instanceof String) {
			if (ApplicationConstants.YES_VALUE.equalsIgnoreCase((String) value)){
				basicFlag = true;
				groupId = 0L;
				basePackageId = 0L;
			} else {
				basicFlag = false;	
			}
		} 
	}

	public void parentOfferNameChangeListener(ValueChangeEvent event) {
		Object value = ((HtmlSelectOneMenu)event.getComponent()).getValue();

		if (value instanceof String) {
			this.parentOfferId = new Long(value.toString());
		} if (value instanceof Double) {
			this.parentOfferId =((Double) value).longValue();
		} else {
			this.parentOfferId = null;
		}	
	}


	

	public void selectNRCPriceChangeListener(ValueChangeEvent event) {
		HtmlSelectOneMenu selItem=((HtmlSelectOneMenu)event.getComponent());
		if (selItem != null){
			Long priceId = (Long) selItem.getValue();
			this.nrcPriceCatalog = priceMap.get(priceId).getPrice();
		}
		else{
			this.nrcPriceCatalog = BigDecimal.ZERO;
		}
	}
	public void selectRCPriceChangeListener(ValueChangeEvent event) {
		HtmlSelectOneMenu selItem=((HtmlSelectOneMenu)event.getComponent());
		if (selItem != null){
			Long priceId = (Long) selItem.getValue();
			this.rcPriceCatalog = priceMap.get(priceId).getPrice();
		}
		else{
			this.rcPriceCatalog = BigDecimal.ZERO;
		}
	}

	public void selectFrequencyChangeListener(ValueChangeEvent event) {
		HtmlSelectOneMenu selItem=((HtmlSelectOneMenu)event.getComponent());
		if (selItem != null){
			Long frequencyId = (Long) selItem.getValue();
			this.frequencyName = frequencyMap.get(frequencyId).getFrequencyName();
			this.frequencyDays = frequencyMap.get(frequencyId).getFrequencyDays();
		}
		else{
			this.frequencyDays = 0L;
		}
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public String getCurrencyPriceName() {
		return currencyPriceName;
	}

	public void setCurrencyPriceName(String currencyPriceName) {
		this.currencyPriceName = currencyPriceName;
	}

	public String getRecurringFeeDiscount() {
		return recurringFeeDiscount;
	}

	public void setRecurringFeeDiscount(String recurringFeeDiscount) {
		this.recurringFeeDiscount = recurringFeeDiscount;
	}

	public String getSetupFeeDiscount() {
		return setupFeeDiscount;
	}

	public void setSetupFeeDiscount(String setupFeeDiscount) {
		this.setupFeeDiscount = setupFeeDiscount;
	}

	public String getRecurringFeeDiscountPercentage() {
		return recurringFeeDiscountPercentage;
	}

	public void setRecurringFeeDiscountPercentage(String recurringFeeDiscountPercentage) {
		this.recurringFeeDiscountPercentage = recurringFeeDiscountPercentage;
	}

	public String getSetupFeeDiscountPercentage() {
		return setupFeeDiscountPercentage;
	}

	public void setSetupFeeDiscountPercentage(String setupFeeDiscountPercentage) {
		this.setupFeeDiscountPercentage = setupFeeDiscountPercentage;
	}

	public boolean isActive(){
		return StatusConstants.ACTIVE.equalsIgnoreCase(statusName);
	}

	public boolean isSuspended(){
		return StatusConstants.SUSPENDED.equalsIgnoreCase(statusName);
	}

	public boolean isInactive(){
		return StatusConstants.INACTIVE.equalsIgnoreCase(statusName);
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public Map<Long, PriceBean> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(Map<Long, PriceBean> priceMap) {
		this.priceMap = priceMap;
	}

	public String getNewRecurringFee() {
		return newRecurringFee;
	}

	public void setNewRecurringFee(String newRecurringFee) {
		this.newRecurringFee = newRecurringFee;
	}

	public String getNewSetupFee() {
		return newSetupFee;
	}

	public void setNewSetupFee(String newSetupFee) {
		this.newSetupFee = newSetupFee;
	}
	
	public void calcultateNewPrice(){
		final int cento = 100;
		if (!Utilities.isEmptyString(setupFeeDiscount)){
			newSetupFee = setupFeeDiscount;
		}
		else if (!Utilities.isEmptyString(setupFeeDiscountPercentage)){
			BigDecimal tosub = nrcPriceCatalog.divide(BigDecimal.valueOf(cento)).multiply(new BigDecimal(setupFeeDiscountPercentage));
			newSetupFee = Utilities.getString(nrcPriceCatalog.subtract(tosub));
		}else {
			newSetupFee = Utilities.getString(nrcPriceCatalog);
		}
		if (!Utilities.isEmptyString(recurringFeeDiscount)){
			newRecurringFee = recurringFeeDiscount;
		}
		else if (!Utilities.isEmptyString(recurringFeeDiscountPercentage)){
			BigDecimal tosub = rcPriceCatalog.divide(BigDecimal.valueOf(cento)).multiply(new BigDecimal(recurringFeeDiscountPercentage));
			newRecurringFee = Utilities.getString(rcPriceCatalog.subtract(tosub));
		} else {
			newRecurringFee = Utilities.getString(rcPriceCatalog);
		}

	}

	

}
