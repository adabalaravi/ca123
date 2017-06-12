package com.accenture.sdp.csmcc.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.model.SelectItem;

import com.accenture.sdp.csmcc.beans.CurrencyBean;
import com.accenture.sdp.csmcc.beans.FrequencyBean;
import com.accenture.sdp.csmcc.beans.OfferBean;
import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.beans.PriceBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.OfferComparator;
import com.accenture.sdp.csmcc.popups.PopupBean;

public class PackageFormUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SelectItem> offerList;
	private List<SelectItem> priceList;
	private List<SelectItem> currencyList;
	private List<SelectItem> frequencyList;
	private List<SelectItem> typeList;
	private String basicType;
	private String optionalType;
	private List<SelectItem> packageLinkList;
	private List<SelectItem> offerGroupNameList;
	private Set<String> offerGroupNameSet;
	private Map<Long, String> offerNameMap;
	private Map<Long, FrequencyBean> frequencyMap;

	public PackageFormUtil(){
		super();
	}

	public void initialize(List<PackageBean> offerDetailBeanList, List<String> toExclude, boolean defaultValue){

		packageLinkList = new ArrayList<SelectItem>();
		offerGroupNameList = new ArrayList<SelectItem>();
		offerGroupNameSet = new TreeSet<String>();

		packageLinkList.add((new SelectItem(0, "")));
		offerGroupNameList.add((new SelectItem(0, "")));
		offerGroupNameSet.add("");

		try {
			if (offerDetailBeanList != null) {
				for (PackageBean offerDetail : offerDetailBeanList) {
					if (ApplicationConstants.YES_VALUE.equalsIgnoreCase(offerDetail.getMandatory())){
						packageLinkList.add(new SelectItem(offerDetail.getPackageId(), offerDetail.getOfferName()));
					}
					if (offerDetail.getGroupName() != null && !offerGroupNameSet.contains(offerDetail.getGroupName())){
						offerGroupNameSet.add(offerDetail.getGroupName());
						offerGroupNameList.add(new SelectItem(offerDetail.getGroupId(), offerDetail.getGroupName()));
					}
				}
			}

			// SETUP Offer Name List
			offerNameMap = new HashMap<Long, String>();
			offerList = new ArrayList<SelectItem>();
			offerList.add((new SelectItem(String.valueOf(0), "")));
			List<OfferBean> offerBeanList = OfferBusiness.searchAllOffer();
			OfferComparator comparator = new OfferComparator(OfferBean.OFFER_NAME_FIELD, true);
			Collections.sort(offerBeanList, comparator);
			for (OfferBean offer : offerBeanList) {
				if (toExclude == null || !toExclude.contains(offer.getOfferName())) {
					offerNameMap.put(offer.getOfferId(), offer.getOfferName());
					offerList.add(new SelectItem(offer.getOfferId(), offer.getOfferName()));
				}
			}
			// SET Price List
			Map<Long, PriceBean> priceMap = new HashMap<Long, PriceBean>();
			priceList = new ArrayList<SelectItem>();
			List<PriceBean> priceBeanList = PriceBusiness.searchAllPrice();
			for (PriceBean price : priceBeanList) {
				priceMap.put(price.getPriceId(), price);	
				priceList.add(new SelectItem(price.getPriceId(), price.getPrice().toPlainString()));
			}
			// SET Currency List
			currencyList = new ArrayList<SelectItem>();
			List<CurrencyBean> currencyBeanList = new CurrencyBusiness().getBufferedData(0, 0);
			for (CurrencyBean currency : currencyBeanList) {
				currencyList.add(new SelectItem(currency.getCurrencyTypeId(), currency.getCurrencyTypeName()));
			}

			frequencyMap = new HashMap<Long, FrequencyBean>();
			frequencyList = new ArrayList<SelectItem>();
			List<FrequencyBean> frequencyBeanList = FrequencyBusiness.searchAllFrequencies();
			for (FrequencyBean frequency : frequencyBeanList) {
				frequencyMap.put(frequency.getFrequencyId(), frequency);
				frequencyList.add(new SelectItem(frequency.getFrequencyId(), frequency.getFrequencyName()));
			}
			// SET Type List
			typeList = new ArrayList<SelectItem>();
			basicType = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_SOLUTION_OFFER_TYPE_BASIC);
			optionalType = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_SOLUTION_OFFER_TYPE_OPTIONAL);
			typeList.add(new SelectItem(ApplicationConstants.YES_VALUE, basicType));
			typeList.add(new SelectItem(ApplicationConstants.NO_VALUE, optionalType));


			if (offerDetailBeanList != null) {
				for (PackageBean offerDetail : offerDetailBeanList) {
					if (defaultValue){
						offerDetail.setOfferId(0L);
						offerDetail.setBasePackageId(0L);
						offerDetail.setGroupId(0L);
						offerDetail.setMandatory(ApplicationConstants.NO_VALUE);
						offerDetail.setType(optionalType);
					}
					offerDetail.setPriceMap(priceMap);
					offerDetail.setFrequencyMap(frequencyMap);
				}
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(PackageFormUtil.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}



	}

	public void resetOfferDetailList(List<PackageBean> offerDetailBeanList){
		if (offerDetailBeanList != null) {
			for (PackageBean offerDetail : offerDetailBeanList) {
				offerDetail.setOfferId(0L);
				offerDetail.setNrcPriceCatalogId(1L);
				offerDetail.setRcPriceCatalogId(1L);
				offerDetail.setRcFrequencyTypeId(1L);
				offerDetail.setBasePackageId(0L);
				offerDetail.setGroupId(0L);
				offerDetail.setMandatory(ApplicationConstants.NO_VALUE);
				offerDetail.setBasicFlag(false);
				offerDetail.setType(optionalType);
			}
		}
	}

	public void setRelatedData(PackageBean bean){
		bean.setOfferName(offerNameMap.get(bean.getOfferId()));
	}



	public List<SelectItem> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<SelectItem> offerList) {
		this.offerList = offerList;
	}


	public List<SelectItem> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<SelectItem> priceList) {
		this.priceList = priceList;
	}

	public List<SelectItem> getFrequencyList() {
		return frequencyList;
	}

	public void setFrequencyList(List<SelectItem> frequencyList) {
		this.frequencyList = frequencyList;
	}

	public List<SelectItem> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<SelectItem> typeList) {
		this.typeList = typeList;
	}

	public String getBasicType() {
		return basicType;
	}

	public void setBasicType(String basicType) {
		this.basicType = basicType;
	}

	public String getOptionalType() {
		return optionalType;
	}

	public void setOptionalType(String optionalType) {
		this.optionalType = optionalType;
	}

	public List<SelectItem> getPackageLinkList() {
		return packageLinkList;
	}

	public void setPackageLinkList(List<SelectItem> packageLinkList) {
		this.packageLinkList = packageLinkList;
	}

	public List<SelectItem> getOfferGroupNameList() {
		return offerGroupNameList;
	}

	public void setOfferGroupNameList(List<SelectItem> offerGroupNameList) {
		this.offerGroupNameList = offerGroupNameList;
	}

	public Set<String> getOfferGroupNameSet() {
		return offerGroupNameSet;
	}

	public void setOfferGroupNameSet(Set<String> offerGroupNameSet) {
		this.offerGroupNameSet = offerGroupNameSet;
	}

	public List<SelectItem> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<SelectItem> currencyList) {
		this.currencyList = currencyList;
	}

	public Map<Long, FrequencyBean> getFrequencyMap() {
		return frequencyMap;
	}

	public void setFrequencyMap(Map<Long, FrequencyBean> frequencyMap) {
		this.frequencyMap = frequencyMap;
	}



}
