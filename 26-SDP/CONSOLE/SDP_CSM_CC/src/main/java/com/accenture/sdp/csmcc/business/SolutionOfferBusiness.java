package com.accenture.sdp.csmcc.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import org.primefaces.model.DualListModel;

import com.accenture.sdp.csmcc.beans.CurrencyBean;
import com.accenture.sdp.csmcc.beans.ExternalIdBean;
import com.accenture.sdp.csmcc.beans.FrequencyBean;
import com.accenture.sdp.csmcc.beans.OfferBean;
import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.beans.PriceBean;
import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.AvsConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.comparators.OfferComparator;
import com.accenture.sdp.csmcc.converters.OfferDetailConverter;
import com.accenture.sdp.csmcc.converters.SolutionOfferConverter;
import com.accenture.sdp.csmcc.managers.SolutionManager;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.AvsGeneralService;
import com.accenture.sdp.csmcc.services.SolutionOfferService;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchDiscountSolutionOfferRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.DiscountListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferResp;

public class SolutionOfferBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String solutionName;

	// Step 2
	private Map<String, PartyGroupBean> partyGroupMap;
	private List<String> partyGroupItemList;
	private List<String> partyGroupItemListSelected;
	private DualListModel<String> clusters;

	// Step 3
	private Map<String, OfferBean> offerMap;
	private List<String> offerList;
	private List<String> offerListSelected;
	private DualListModel<String> offers;

	// STEP4
	private List<SelectItem> priceList;
	private List<SelectItem> frequencyList;
	private List<SelectItem> currencyList;
	private Map<Long, FrequencyBean> frequencyMap;

	// cache of external platforms
	private static List<ExternalIdBean> externalPlatformNames;

	public SolutionOfferBusiness() {
		super();

		priceList = new ArrayList<SelectItem>();
		frequencyList = new ArrayList<SelectItem>();
		currencyList = new ArrayList<SelectItem>();
		frequencyMap = new HashMap<Long, FrequencyBean>();

		partyGroupItemList = new ArrayList<String>();
		partyGroupItemListSelected = new ArrayList<String>();
		partyGroupMap = new HashMap<String, PartyGroupBean>();

		offerList = new ArrayList<String>();
		offerListSelected = new ArrayList<String>();
		offerMap = new HashMap<String, OfferBean>();

		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		solutionName = propertyManager.getProperty(ApplicationConstants.SOLUTION_NAME_AVS);
	}

	public List<String> getPartyGroupItemList() {
		return partyGroupItemList;
	}

	public void setPartyGroupItemList(List<String> partyGroupItemList) {
		this.partyGroupItemList = partyGroupItemList;
	}

	public List<String> getPartyGroupItemListSelected() {
		return partyGroupItemListSelected;
	}

	public void setPartyGroupItemListSelected(List<String> partyGroupItemListSelected) {
		this.partyGroupItemListSelected = partyGroupItemListSelected;
	}

	public List<SelectItem> getFrequencyList() {
		return frequencyList;
	}

	public void setFrequencyList(List<SelectItem> frequencyList) {
		this.frequencyList = frequencyList;
	}

	public List<SelectItem> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<SelectItem> currencyList) {
		this.currencyList = currencyList;
	}

	// BUSINESS METHODS
	// -----------------------------------------------------------------------------------------------------------------

	public static List<SolutionOfferBean> searchAllSolutionOffer() throws ServiceErrorException {
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		SearchSolutionOfferComplexRespPaginated resp = service.searchAllSolutionOffers(0L, 0L, Utilities.getTenantName());
		List<SolutionOfferBean> result = SolutionOfferConverter.buildSolutionOfferTable(resp);
		for (SolutionOfferBean so : result) {
			normalizeExternalIds(so);
		}
		return result;
	}

	public static int countAllCommercialPackages() throws ServiceErrorException {
		return searchAllCommercialPackages().size();
	}

	
	
	public static List<String> searchAllSolutionOfferType() throws ServiceErrorException{
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		return SolutionOfferConverter.buildSolutionOfferTypes(service.searchSolutionOfferTypes(Utilities.getTenantName()));
	}
	
	public static List<SolutionOfferBean> searchAllDiscountSolutionOffers() throws ServiceErrorException {
		AvsGeneralService avsService = Utilities.findBean(ApplicationConstants.AVS_GENERAL_SERVICE_BEAN_NAME, AvsGeneralService.class);
		List<SolutionOfferBean> tableResult = new ArrayList<SolutionOfferBean>();
		SearchDiscountSolutionOfferRespPaginated resp = avsService.searchAllDiscountSolutionOffers(0L, 0L, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = SolutionOfferConverter.buildSolutionOfferTable(resp);
		}
		// recupera i parent e li setta prima di tornare
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		Map<Long, String> parents = new HashMap<Long, String>();
		for (SolutionOfferBean so : tableResult) {
			String parentName = parents.get(so.getParentSolutionOfferId());
			if (parentName == null) {
				SearchSolutionOfferResp parentResp = service.searchSolutionOffer(so.getParentSolutionOfferId(), Utilities.getTenantName());
				parentName = parentResp.getSolutionOffers().getSolutionOffer().get(0).getSolutionOfferName();
				parents.put(so.getParentSolutionOfferId(), parentName);
			}
			so.setParentSolutionOfferName(parentName);
		}
		return tableResult;
	}

	public static List<SolutionOfferBean> searchDiscountSolutionOffersByParentSolutionOffer(SolutionOfferBean parentSolutionOffer) throws ServiceErrorException {
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		List<SolutionOfferBean> tableResult = new ArrayList<SolutionOfferBean>();
		SearchSolutionOfferComplexRespPaginated resp = service.searchDiscountedSolutionOfferBySolutionOffer(parentSolutionOffer.getSolutionOfferName(), 0L, 0L,
				Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = SolutionOfferConverter.buildSolutionOfferTable(resp);
			for (SolutionOfferBean discount : tableResult) {
				discount.setParentSolutionOfferName(parentSolutionOffer.getSolutionOfferName());
			}
		}
		return tableResult;
	}

	public static List<SolutionOfferBean> searchAllCommercialPackages() throws ServiceErrorException {
		List<SolutionOfferBean> filterResult = new ArrayList<SolutionOfferBean>();
		for (SolutionOfferBean bean : searchAllSolutionOffer()) {
			if (AvsConstants.SUBSCRIPTION.equalsIgnoreCase(bean.getProductType())) {
				filterResult.add(bean);
			}
		}
		return filterResult;
	}

	public List<SolutionOfferBean> searchAllPPVBundle() throws ServiceErrorException {
		List<SolutionOfferBean> tableResult = searchAllSolutionOffer();
		List<SolutionOfferBean> filterResult = new ArrayList<SolutionOfferBean>();
		for (SolutionOfferBean bean : tableResult) {
			if (AvsConstants.BUNDLE.equalsIgnoreCase(bean.getType()) && AvsConstants.BUNDLE_PVV.equalsIgnoreCase(bean.getProductType())) {
				filterResult.add(bean);
			}
		}
		return filterResult;
	}

	public boolean validateStep1(SolutionOfferBean bean) {
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_NAME_FIELD, bean.getSolutionOfferName());
		return ValidationUtilities.validateMandatoryFields(validationMap);
	}

	public void loadPrices() {
		try {
			priceList.clear();
			List<PriceBean> priceBeanList = PriceBusiness.searchAllPrice();
			for (PriceBean price : priceBeanList) {
				priceList.add(new SelectItem(price.getPriceId(), String.valueOf(price.getPrice())));
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

		}
	}

	public void loadFrequencies() {
		try {
			frequencyList.clear();
			frequencyMap.clear();
			List<FrequencyBean> frequencyBeanList = FrequencyBusiness.searchAllFrequencies();
			for (FrequencyBean frequency : frequencyBeanList) {
				frequencyMap.put(frequency.getFrequencyId(), frequency);
				frequencyList.add(new SelectItem(frequency.getFrequencyId(), frequency.getFrequencyName()));
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

		}
	}

	public void loadCurrencies() {
		try {
			currencyList.clear();
			List<CurrencyBean> currencyBeanList = new CurrencyBusiness().getBufferedData(0, 0);
			for (CurrencyBean currency : currencyBeanList) {
				currencyList.add(new SelectItem(currency.getCurrencyTypeId(), currency.getCurrencyTypeName()));
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

		}
	}

	public void loadPartyGroups() {
		try {
			partyGroupItemList.clear();
			partyGroupItemListSelected.clear();
			partyGroupMap.clear();

			List<PartyGroupBean> partyGroupBeanList = PartyGroupBusiness.searchAllPartyGroups();
			for (PartyGroupBean partyGroup : partyGroupBeanList) {
				partyGroupItemList.add(partyGroup.getPartyGroupName());
				partyGroupMap.put(partyGroup.getPartyGroupName(), partyGroup);
			}
			setClusters(new DualListModel<String>(partyGroupItemList, partyGroupItemListSelected));

		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}
	}

	public void initPartyGroups(List<String> partyGroups) {
		try {
			partyGroupItemList.clear();
			partyGroupItemListSelected.clear();
			partyGroupMap.clear();

			List<PartyGroupBean> partyGroupBeanList = PartyGroupBusiness.searchAllPartyGroups();
			for (PartyGroupBean partyGroup : partyGroupBeanList) {
				if (partyGroups.contains(partyGroup.getPartyGroupName())) {
					partyGroupItemListSelected.add(partyGroup.getPartyGroupName());
				} else {
					partyGroupItemList.add(partyGroup.getPartyGroupName());
				}
				partyGroupMap.put(partyGroup.getPartyGroupName(), partyGroup);
			}
			setClusters(new DualListModel<String>(partyGroupItemList, partyGroupItemListSelected));

		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}
	}

	public void loadOffers() {
		offerList.clear();
		offerListSelected.clear();
		offerMap.clear();
		try {
			List<OfferBean> offerBeanList = OfferBusiness.searchAllOffer();
			OfferComparator comparator = new OfferComparator(OfferBean.OFFER_NAME_FIELD, true);
			Collections.sort(offerBeanList, comparator);
			for (OfferBean offer : offerBeanList) {
				offerList.add(offer.getOfferName());
				offerMap.put(offer.getOfferName(), offer);
			}
			setOffers(new DualListModel<String>(offerList, offerListSelected));
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(SolutionOfferBean.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}

	}

	public List<String> getSelectedPartyGroups() {
		return clusters.getTarget();
	}

	public List<PackageBean> getSelectedOfferDetails() {
		ArrayList<PackageBean> offerDetailList = null;
		if (offers.getTarget().size() > 0) {
			offerDetailList = new ArrayList<PackageBean>();
			for (String offerName : offers.getTarget()) {
				PackageBean bean = new PackageBean();
				OfferBean offer = offerMap.get(offerName);
				bean.setOfferName(offer.getOfferName());
				bean.setOfferId(offer.getOfferId());
				bean.setFrequencyMap(frequencyMap);
				offerDetailList.add(bean);
			}
		}
		return offerDetailList;
	}

	public List<SelectItem> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<SelectItem> priceList) {
		this.priceList = priceList;
	}

	public void createSolutionOffer(SolutionOfferBean bean, String nextParam) throws DatatypeConfigurationException {
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();

		String solutionOfferName = bean.getSolutionOfferName();
		String solutionOfferDesc = bean.getSolutionOfferDesc();
		Date solutionOfferStartDate = bean.getSolutionOfferStartDate();
		Date solutionOfferEndDate = bean.getSolutionOfferEndDate();
		

		String solutionOfferProfile = SolutionOfferConverter.getProfileAsString(bean);

		List<PackageBean> offerDetailList = bean.getOfferDetailList();

		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_NAME_FIELD, solutionOfferName);
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			// logging
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, solutionOfferName);
			logMap.put(MessageConstants.SOLUTION_OFFER_DESCRIPTION_LBL, solutionOfferDesc);
			logMap.put(MessageConstants.SOLUTION_OFFER_START_DATE_LBL, solutionOfferStartDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_END_DATE_LBL, solutionOfferEndDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_PROFILE_LBL, solutionOfferProfile);
			log.logStartFeature(logMap);
			logMap.clear();

			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String mex;
			String code;
			Holder<String> tenantName = Utilities.getTenantName();
			try {

				List<String> partyGroups = bean.getPartyGroupNames();

				// STEP 1 CREATE SOLUTION OFFER AND PACKAGE
				service.createSolutionOfferAndPackage(solutionName, solutionOfferName, solutionOfferDesc, solutionOfferStartDate, solutionOfferEndDate,
						solutionOfferProfile, offerDetailList, partyGroups, bean.getExternalId(),bean.getSolutionOfferDuration(),bean.getSolutionOfferType(), tenantName);
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_SOLUTION_OFFER_MESSAGE), solutionOfferName);
				msgBean.setNextParam(nextParam);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			// logging
			log.logEndFeature(code, mex);
		}
	}

	public void updatePartyGroups(SolutionOfferBean bean, String nextParam) throws DatatypeConfigurationException {
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, bean.getSolutionName());
		log.logStartFeature(logMap);

		ArrayList<PartyGroupBean> partyGroups = new ArrayList<PartyGroupBean>();
		for (String key : clusters.getTarget()) {
			PartyGroupBean partyGroup = partyGroupMap.get(key);
			if (!bean.getPartyGroupNames().contains(key)) {
				partyGroup.setOperation("New");
				partyGroups.add(partyGroup);
			}
		}

		for (String key : bean.getPartyGroupNames()) {
			PartyGroupBean partyGroup = partyGroupMap.get(key);
			if (!clusters.getTarget().contains(key)) {
				partyGroup.setOperation("Delete");
				partyGroups.add(partyGroup);

			}
		}

		Holder<String> tenantName = Utilities.getTenantName();
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		String mex;
		String code;
		try {
			service.updatePartyGroups(partyGroups, bean.getSolutionOfferId(), tenantName);
			code = ApplicationConstants.CODE_OK;
			mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_PARTY_GROUP_MESSAGE), bean.getSolutionOfferName());

			msgBean.setNextParam(nextParam);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();

		}
		msgBean.openPopup(mex);
		log.logEndFeature(code, mex);
	}

	public void updateSolutionOffer(SolutionOfferBean bean, String nextParam) throws DatatypeConfigurationException {
		LoggerWrapper log = new LoggerWrapper(SolutionOfferBean.class);
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);

		Long solutionOfferId = bean.getSolutionOfferId();
		Long solutionId = bean.getSolutionId();
		String solutionOfferName = bean.getSolutionOfferName();
		String solutionOfferDesc = bean.getSolutionOfferDesc();
		Date solutionOfferStartDate = bean.getSolutionOfferStartDate();
		Date solutionOfferEndDate = bean.getSolutionOfferEndDate();
		String solutionOfferExtId = bean.getExternalId().toString();
		String solutionOfferProfile = SolutionOfferConverter.getProfileAsString(bean);

		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_NAME_FIELD, solutionOfferName);

		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			// logging

			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, solutionOfferName);
			logMap.put(MessageConstants.SOLUTION_OFFER_DESCRIPTION_LBL, solutionOfferDesc);
			logMap.put(MessageConstants.SOLUTION_OFFER_START_DATE_LBL, solutionOfferStartDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_END_DATE_LBL, solutionOfferEndDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_EXTID_LBL, solutionOfferExtId);
			logMap.put(MessageConstants.SOLUTION_OFFER_PROFILE_LBL, solutionOfferProfile);
			log.logStartFeature(logMap);
			logMap.clear();

			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String mex;
			String code;
			try {
				service.modifySolutionOffer(solutionOfferId, solutionId, solutionOfferName, solutionOfferDesc, solutionOfferStartDate, solutionOfferEndDate,
						bean.getExternalId(), solutionOfferProfile,bean.getSolutionOfferDuration(),bean.getSolutionOfferType(), Utilities.getTenantName());
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_SOLUTION_OFFER_MESSAGE), solutionOfferName);

				msgBean.setUpdateFlag(true);
				msgBean.setNextParam(nextParam);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			// logging
			log.logEndFeature(code, mex);
		}
	}

	public void createCommercialPackageDiscounted(SolutionOfferBean solutionOffer, String nextParam) {
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		solutionOffer.setType(AvsConstants.SUBSCRIPTION);
		solutionOffer.setProductType(AvsConstants.SUBSCRIPTION_DISCOUNT);
		String name = solutionOffer.getSolutionOfferName();
		String description = solutionOffer.getSolutionOfferDesc();
		Long prentSolutionOfferId = solutionOffer.getParentSolutionOfferId();
		String externalId = solutionOffer.getExternalId().toString();
		String profile = SolutionOfferConverter.getProfileAsString(solutionOffer);
		Date startDate = solutionOffer.getSolutionOfferStartDate();
		Date endDate = solutionOffer.getSolutionOfferEndDate();
		List<String> partyGroups = solutionOffer.getPartyGroupNames();
		DiscountListRequest discounts = OfferDetailConverter.convertOfferDetailsToDiscounts(solutionOffer.getOfferDetailList());
		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_SOLUTION_NAME_FIELD, name);
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, name);
			logMap.put(MessageConstants.SOLUTION_OFFER_DESCRIPTION_LBL, description);
			logMap.put(MessageConstants.SOLUTION_OFFER_START_DATE_LBL, startDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_END_DATE_LBL, endDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_EXTID_LBL, externalId);
			logMap.put(MessageConstants.SOLUTION_OFFER_PROFILE_LBL, profile);
			log.logStartFeature(logMap);
			logMap.clear();

			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String mex = null;
			String code = null;
			try {
				service.createSolutionOfferAndDiscount(name, description, prentSolutionOfferId, solutionOffer.getExternalId(), profile, startDate, endDate,
						discounts, partyGroups, Utilities.getTenantName());
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_DISCOUNT_SOLUTION_OFFER), name);

				msgBean.setNextParam(nextParam);
			} catch (ServiceErrorException se) {
				code = se.getCode();
				mex = se.getMessage();
				log.logException(se.getMessage(), se);
			} catch (DatatypeConfigurationException dce) {
				mex = dce.getMessage();
				log.logException(dce.getMessage(), dce);
			} catch (Exception e) {
				log.logException(e.getMessage(), e);
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}
	}

	public void updateCommercialPackageDiscounted(SolutionOfferBean solutionOffer, String nextView) throws DatatypeConfigurationException {
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		Long solutionOfferId = solutionOffer.getSolutionOfferId();
		Long solutionId = null;
		String name = solutionOffer.getSolutionOfferName();
		String description = solutionOffer.getSolutionOfferDesc();
		String externalId = solutionOffer.getExternalId().toString();
		String profile = solutionOffer.getSolutionOfferProfile();
		Date startDate = solutionOffer.getSolutionOfferStartDate();
		Date endDate = solutionOffer.getSolutionOfferEndDate();

		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_SOLUTION_NAME_FIELD, name);
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, name);
			logMap.put(MessageConstants.SOLUTION_OFFER_DESCRIPTION_LBL, description);
			logMap.put(MessageConstants.SOLUTION_OFFER_START_DATE_LBL, startDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_END_DATE_LBL, endDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_EXTID_LBL, externalId);
			logMap.put(MessageConstants.SOLUTION_OFFER_PROFILE_LBL, profile);
			log.logStartFeature(logMap);
			logMap.clear();

			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String mex;
			String code;
			try {
				service.modifySolutionOffer(solutionOfferId, solutionId, name, description, startDate, endDate, solutionOffer.getExternalId(), profile,solutionOffer.getSolutionOfferDuration(),solutionOffer.getSolutionOfferType(),
						Utilities.getTenantName());
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_DISCOUNT_SOLUTION_OFFER), name);

				msgBean.setNextParam(nextView);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}
	}

	public void updateDiscountPackages(SolutionOfferBean solutionOffer, String nextView) throws DatatypeConfigurationException {
		LoggerWrapper log = new LoggerWrapper(SolutionOfferBean.class);
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();

		String name = solutionOffer.getSolutionOfferName();
		String description = solutionOffer.getSolutionOfferDesc();
		String externalId = solutionOffer.getExternalId().toString();
		String profile = solutionOffer.getSolutionOfferProfile();
		Date startDate = solutionOffer.getSolutionOfferStartDate();
		Date endDate = solutionOffer.getSolutionOfferEndDate();

		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_SOLUTION_NAME_FIELD, name);
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, name);
			logMap.put(MessageConstants.SOLUTION_OFFER_DESCRIPTION_LBL, description);
			logMap.put(MessageConstants.SOLUTION_OFFER_START_DATE_LBL, startDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_END_DATE_LBL, endDate);
			logMap.put(MessageConstants.SOLUTION_OFFER_EXTID_LBL, externalId);
			logMap.put(MessageConstants.SOLUTION_OFFER_PROFILE_LBL, profile);
			log.logStartFeature(logMap);
			logMap.clear();

			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String mex;
			String code;
			try {
				service.modifyDiscounts(solutionOffer.getOfferDetailList(), Utilities.getTenantName());
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_DISCOUNT_SOLUTION_OFFER), name);

				msgBean.setNextParam(nextView);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}

	}

	public DualListModel<String> getClusters() {
		return clusters;
	}

	public void setClusters(DualListModel<String> clusters) {
		this.clusters = clusters;
	}

	public DualListModel<String> getOffers() {
		return offers;
	}

	public void setOffers(DualListModel<String> offers) {
		this.offers = offers;
	}

	public static List<ExternalIdBean> searchExternalPlatforms() {
		if (externalPlatformNames == null) {
			externalPlatformNames = new ArrayList<ExternalIdBean>();
			PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
			for (int i = 1;; i++) {
				String platform = propertyManager.getProperty(ApplicationConstants.STAR_EXTERNAL_PLATFORM_PREFIX + i);
				if (platform == null) {
					break;
				}
				platform = platform.trim();
				int cut = platform.indexOf(ApplicationConstants.STAR_EXTERNAL_PLATFORMS_SEPARATOR);
				// non so dove mapparli, per cui li metto qui che lo contiene
				ExternalIdBean bean = new ExternalIdBean();
				bean.setExternalPlatform(platform.substring(0, cut));
				bean.setValidationPattern(platform.substring(cut + 1, platform.length()));
				externalPlatformNames.add(bean);
			}
		}
		return externalPlatformNames;
	}

	public static void normalizeExternalIds(SolutionOfferBean solutionOffer) {
		// prepare list if unavailable
		if (solutionOffer.getExternalId() == null) {
			solutionOffer.setExternalId(new ArrayList<ExternalIdBean>());
		}
		for (ExternalIdBean platform : searchExternalPlatforms()) {
			boolean found = false;
			for (ExternalIdBean ext : solutionOffer.getExternalId()) {
				if (ext.getExternalPlatform().equals(platform.getExternalPlatform())) {
					ext.setValidationPattern(platform.getValidationPattern());
					found = true;
					break;
				}
			}
			// if not found, prepare a new one
			if (!found) {
				ExternalIdBean bean = new ExternalIdBean();
				bean.setExternalPlatform(platform.getExternalPlatform());
				bean.setValidationPattern(platform.getValidationPattern());
				solutionOffer.getExternalId().add(bean);
			}
		}
		// ordinamento
		Collections.sort(solutionOffer.getExternalId());
	}

}
