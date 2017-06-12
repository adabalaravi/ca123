package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.accenture.sdp.csmcc.business.OfferBusiness;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;


public class OfferBean implements Serializable {
	/**
	 * 
	 */
	public static final String OFFER_NAME_FIELD = "offerName";
	public static final String OFFER_DESC_FIELD = "offerDesc";
	public static final String OFFER_STATUS_FIELD = "offerStatus";
	public static final String OFFER_CREATION_DATE_FIELD = "offerCreationDate";
	public static final String OFFER_SERVICE_VARIANT_NAME_FIELD = "serviceVariantName";
	public static final String OFFER_EXT_ID_FIELD = "offerExtId";
	public static final String OFFER_RENT_PRICE = "rent";
	public static final String OFFER_PRICE = "price";


	private static final long serialVersionUID = 1L;
	private String offerName;
	private String offerDesc;
	private String offerStatus;
	private String offerExtId;
	private String offerProfile;
	private Date offerCreationDate;
	private Long offerId;

	private String platformName;
	private PlatformBean parentPlatform;
	private String serviceTemplateName;
	private ServiceTemplateBean parentServiceTemplate;
	private String serviceVariantName;
	private ServiceVariantBean parentServiceVariant;

	private List<SelectItem> notFilteredPlatoformtList = new ArrayList<SelectItem>();
	private List<SelectItem> platformList = new ArrayList<SelectItem>();

	private List<SelectItem> notFilteredServiceTemplateList = new ArrayList<SelectItem>();
	private List<SelectItem> serviceTemplateList = new ArrayList<SelectItem>();

	private List<SelectItem> notFilteredServiceVariantList = new ArrayList<SelectItem>();
	private List<SelectItem> serviceVariantList = new ArrayList<SelectItem>();

	private String channelAVS;
	private String categoryAVS;
	private String priceAVS;
	private String rentPriceAVS;
	private String broadcastDateAVS;
	private String typeAVS;
	private String typeLabel;

	private String offerNameStored;
	private String offerDescStored;
	private String offerExtIdStored;
	private String offerProfileStored;
	private String platformNameStored;
	private PlatformBean parentPlatformStored;
	private String serviceTemplateNameStored;
	private ServiceTemplateBean parentServiceTemplateStored;
	private String serviceVariantNameStored;
	private ServiceVariantBean parentServiceVariantStored;
	
	private List<DigitalGoodBean> digitalGoods;

	public void storeValues() {
		this.offerNameStored = this.offerName;
		this.offerDescStored = this.offerDesc;
		this.offerExtIdStored = this.offerExtId;
		this.offerProfileStored = this.offerProfile;
		platformNameStored = platformName;
		parentPlatformStored = parentPlatform;
		serviceTemplateNameStored = serviceTemplateName;
		parentServiceTemplateStored = parentServiceTemplate;
		serviceVariantNameStored = serviceVariantName;
		parentServiceVariantStored = parentServiceVariant;

	}

	public void resetOffer() {
		this.offerName = this.offerNameStored;
		this.offerDesc = this.offerDescStored;
		this.offerExtId = this.offerExtIdStored;
		this.offerProfile = this.offerProfileStored;
		this.serviceVariantName = this.serviceVariantNameStored;
		platformName = platformNameStored;
		parentPlatform = parentPlatformStored;
		serviceTemplateName = serviceTemplateNameStored;
		parentServiceTemplate = parentServiceTemplateStored;
		serviceVariantName = serviceVariantNameStored;
		parentServiceVariant = parentServiceVariantStored;

	}

	public String getServiceVariantName() {
		return serviceVariantName;
	}

	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}

	public Date getOfferCreationDate() {
		return offerCreationDate;
	}

	public void setOfferCreationDate(Date offerCreationDate) {
		this.offerCreationDate = offerCreationDate;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getOfferExtId() {
		return offerExtId;
	}

	public void setOfferExtId(String offerExtId) {
		this.offerExtId = offerExtId;
	}

	public String getOfferProfile() {
		return offerProfile;
	}

	public void setOfferProfile(String offerProfile) {
		this.offerProfile = offerProfile;
	}

	public OfferBean() {
		super();
	}


	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public PlatformBean getParentPlatform() {
		return parentPlatform;
	}

	public void setParentPlatform(PlatformBean parentPlatform) {
		this.parentPlatform = parentPlatform;
	}

	public String getServiceTemplateName() {
		return serviceTemplateName;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}

	public ServiceTemplateBean getParentServiceTemplate() {
		return parentServiceTemplate;
	}

	public void setParentServiceTemplate(ServiceTemplateBean parentServiceTemplate) {
		this.parentServiceTemplate = parentServiceTemplate;
	}

	public ServiceVariantBean getParentServiceVariant() {
		return parentServiceVariant;
	}

	public void setParentServiceVariant(ServiceVariantBean parentServiceVariant) {
		this.parentServiceVariant = parentServiceVariant;
	}

	public List<SelectItem> getServiceVariantList() {
		return serviceVariantList;
	}

	public void setServiceVariantList(List<SelectItem> serviceVariantList) {
		this.serviceVariantList = serviceVariantList;
		this.notFilteredServiceVariantList.clear();
		for (SelectItem item : (List<SelectItem>) serviceVariantList) {
			this.notFilteredServiceVariantList.add(item);
		}
	}

	public List<SelectItem> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<SelectItem> platformList) {
		this.platformList = platformList;
		this.notFilteredPlatoformtList.clear();
		for (SelectItem item : (List<SelectItem>) platformList) {
			this.notFilteredPlatoformtList.add(item);
		}
	}

	public List<SelectItem> getServiceTemplateList() {
		return serviceTemplateList;
	}

	public void setServiceTemplateList(List<SelectItem> serviceTemplateList) {
		this.serviceTemplateList = serviceTemplateList;
		this.notFilteredServiceTemplateList.clear();
		for (SelectItem item : (List<SelectItem>) serviceTemplateList) {
			this.notFilteredServiceTemplateList.add(item);
		}
	}


	

	public void cleanOffer() {
		this.offerName = "";
		this.offerDesc = "";
		this.offerExtId = "";
		this.serviceVariantName = "";
		this.offerProfile = "";
	}


	public boolean isActive() {
		return StatusConstants.ACTIVE.equalsIgnoreCase(offerStatus);
	}

	public boolean isSuspended() {
		return StatusConstants.SUSPENDED.equalsIgnoreCase(offerStatus);
	}

	public boolean isInactive() {
		return StatusConstants.INACTIVE.equalsIgnoreCase(offerStatus);
	}

	public String getChannelAVS() {
		return channelAVS;
	}

	public void setChannelAVS(String channelAVS) {
		this.channelAVS = channelAVS;
	}

	public String getCategoryAVS() {
		return categoryAVS;
	}

	public void setCategoryAVS(String categoryAVS) {
		this.categoryAVS = categoryAVS;
	}

	public String getPriceAVS() {
		return priceAVS;
	}

	public void setPriceAVS(String priceAVS) {
		this.priceAVS = priceAVS;
	}

	public String getBroadcastDateAVS() {
		return broadcastDateAVS;
	}

	public void setBroadcastDateAVS(String broadcastDateAVS) {
		this.broadcastDateAVS = broadcastDateAVS;
	}

	public String getRentPriceAVS() {
		return rentPriceAVS;
	}

	public void setRentPriceAVS(String rentPriceAVS) {
		this.rentPriceAVS = rentPriceAVS;
	}

	public String getTypeAVS() {
		return typeAVS;
	}

	public void setTypeAVS(String typeAVS) {
		this.typeAVS = typeAVS;
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	public List<DigitalGoodBean> getDigitalGoods() {
		return digitalGoods;
	}

	public void setDigitalGoods(List<DigitalGoodBean> digitalGoods) {
		this.digitalGoods = digitalGoods;
	}
	
	public void loadDigitalGoods() throws ServiceErrorException {
		this.digitalGoods = OfferBusiness.searchDigitalGoodsByOfferId(offerId);
	}

}
