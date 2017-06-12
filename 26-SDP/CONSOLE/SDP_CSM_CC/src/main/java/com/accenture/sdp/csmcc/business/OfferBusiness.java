package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.DigitalGoodBean;
import com.accenture.sdp.csmcc.beans.OfferBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.OfferConverter;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.AvsGeneralService;
import com.accenture.sdp.csmcc.services.OfferService;
import com.accenture.sdp.csmcc.services.PackageIngestorAVSService;
import com.accenture.sdp.csmfe.webservices.avs.clients.packageingestor.PackageIngestorResponse;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsDigitalGoodResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferResp;

public class OfferBusiness {

	public static List<OfferBean> searchAllOffer() throws ServiceErrorException {
		OfferService service = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN_NAME, OfferService.class);
		List<OfferBean> tableResult = new ArrayList<OfferBean>();
		SearchOfferComplexRespPaginated resp = service.searchAllOfferPaginated(0L, 0L, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = OfferConverter.buildOfferTable(resp);
		}
		List<OfferBean> filterResult= new ArrayList<OfferBean>();
		for (OfferBean bean : tableResult){
			if (bean.getOfferExtId() != null){
				filterResult.add(bean);
			}
		}
		return filterResult;

	}
	
	public static int countAllOffer() throws ServiceErrorException {
		return searchAllOffer().size();
	}

	public static OfferBean searchOfferId(long offerId) throws ServiceErrorException {
		OfferService service = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN_NAME, OfferService.class);
		SearchOfferResp resp = service.searchOfferById(offerId, Utilities.getTenantName());
		List<OfferBean> tableResult = new ArrayList<OfferBean>();
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = OfferConverter.buildOfferTable(resp);
		}
		return (tableResult.size() > 0 ? tableResult.get(0) : null);
	}

	public static List<DigitalGoodBean> searchDigitalGoodsByOfferId(long offerId) throws ServiceErrorException {
		AvsGeneralService service = Utilities.findBean(ApplicationConstants.AVS_GENERAL_SERVICE_BEAN_NAME, AvsGeneralService.class);
		SearchAvsDigitalGoodResp resp = service.searchDigitalGoodsByOffer(offerId, Utilities.getTenantName());
		return OfferConverter.convertDigitalGoods(resp.getDigitalGoods().getDigitalGood());
	}



	public static void addOffer(OfferBean bean) {
		LoggerWrapper log = new LoggerWrapper(OfferBusiness.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ApplicationConstants.OFFER_VALIDATION_NAME_FIELD, bean.getOfferName());
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		Long defaultServiceVariantId = Long.parseLong(propertyManager.getProperty(ApplicationConstants.SERVICE_VARIANT_ID_AVS));
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			// logging
			String offerNameLbl = MessageConstants.OFFER_NAME;
			String offerDescLbl = MessageConstants.OFFER_DESC;
			String offerProfileLbl = MessageConstants.OFFER_PROFILE;
			String offerServiceVariantLbl = MessageConstants.OFFER_SERVICEVARIANT;
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(offerNameLbl, bean.getOfferName());
			logMap.put(offerDescLbl, bean.getOfferDesc());
			logMap.put(offerProfileLbl, bean.getOfferProfile());
			logMap.put(offerServiceVariantLbl, defaultServiceVariantId);
			log.logStartFeature(logMap);
			logMap.clear();
			OfferService service = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN_NAME, OfferService.class);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);

			String code = null;
			String mex;
			String offerProfile = OfferConverter.getProfileAsString(bean);
			Long newOfferId = null;
			try {
				newOfferId = service.createOffer(bean.getOfferName(), bean.getOfferDesc(), bean.getOfferExtId(), offerProfile, defaultServiceVariantId, Utilities.getTenantName());
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_OFFER_MESSAGE), bean.getOfferName());

				// INVOKE AVS PACKAGE IN
				PackageIngestorAVSService packageIngestor = Utilities.findBean(ApplicationConstants.PACKAGE_INGESTOR_SERVICE_BEAN_NAME, PackageIngestorAVSService.class);
				PackageIngestorResponse response = packageIngestor.invokePackageIngestorService(newOfferId, bean.getOfferName(), bean.getOfferDesc(), bean.getTypeAVS(), ApplicationConstants.YES_VALUE, null, 
						null, null, null, Utilities.getTenantName());
				// Set AVS_ID to ExternalId and UPDATE 
				String offerExtId = ApplicationConstants.TECHNICAL_PACKAGE_PREVIX + response.getPackageId();
				service.modifyOffer(bean.getOfferName(), bean.getOfferDesc(), offerExtId, offerProfile, defaultServiceVariantId, newOfferId, Utilities.getTenantName());
				// inactivate the solution offer because of new AVS requirement of the day
				msgBean.openPopup(mex);

				msgBean.setNextParam(PathConstants.TECHNICAL_PACKAGE_VIEW);
				code = ApplicationConstants.CODE_OK;
			} catch (Exception e) {
				if (e instanceof ServiceErrorException) {
					code = ((ServiceErrorException) e).getCode();	
				}
				mex = e.getMessage();
				log.logException(e.getMessage(), e);
				if (newOfferId != null) {
					AvsGeneralService avsService = Utilities.findBean(ApplicationConstants.AVS_GENERAL_SERVICE_BEAN_NAME, AvsGeneralService.class);
					try {
						avsService.deleteOffer(newOfferId, Utilities.getTenantName());
					} catch (ServiceErrorException see) {
						log.logException(see.getMessage(), see);
					}
				}
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);

		}
	}

	public static void updateOffer(OfferBean bean) {
		LoggerWrapper log = new LoggerWrapper(OfferBusiness.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ApplicationConstants.OFFER_VALIDATION_NAME_FIELD, bean.getOfferName());
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			// logging
			String offerNameLbl = MessageConstants.OFFER_NAME;
			String offerDescLbl = MessageConstants.OFFER_DESC;
			String offerExtIdLbl = MessageConstants.OFFER_EXTERNALID;
			String offerServiceVariantLbl = MessageConstants.OFFER_SERVICEVARIANT;
			String offerProfileLbl = MessageConstants.OFFER_PROFILE;

			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(offerNameLbl, bean.getOfferName());
			logMap.put(offerDescLbl, bean.getOfferDesc());
			logMap.put(offerExtIdLbl, bean.getOfferExtId());
			logMap.put(offerProfileLbl, bean.getOfferProfile());
			logMap.put(offerServiceVariantLbl, bean.getServiceVariantName());
			log.logStartFeature(logMap);
			logMap.clear();
			String offerProfile = OfferConverter.getProfileAsString(bean);
			OfferService service = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN_NAME, OfferService.class);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String code = null;
			String mex;
			try {
				// INVOKE AVS PACKAGE IN
				PackageIngestorAVSService packageIngestor = Utilities.findBean(ApplicationConstants.PACKAGE_INGESTOR_SERVICE_BEAN_NAME, PackageIngestorAVSService.class);
				PackageIngestorResponse resp = packageIngestor.invokePackageIngestorService(bean.getOfferId(), bean.getOfferName(), bean.getOfferDesc(), bean.getTypeAVS(), ApplicationConstants.YES_VALUE, null, 
						null, null, null, Utilities.getTenantName());
				String offerExtId = ApplicationConstants.TECHNICAL_PACKAGE_PREVIX + resp.getPackageId();
				service.modifyOffer(bean.getOfferName(), bean.getOfferDesc(), offerExtId, offerProfile, bean.getParentServiceVariant().getServiceVariantId(), 
						bean.getOfferId(), Utilities.getTenantName());

				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.MODIFY_OFFER_MESSAGE), bean.getOfferName());

				msgBean.setUpdateFlag(true);
				msgBean.setNextParam(PathConstants.TECHNICAL_PACKAGE_VIEW);
				code = ApplicationConstants.CODE_OK;
			} catch (Exception e) {
				if (e instanceof ServiceErrorException) {
					code = ((ServiceErrorException) e).getCode();	
				}
				mex = e.getMessage();
				log.logException(e.getMessage(), e);
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}
	}

	public static void loadDigitalGoods(OfferBean bean) throws ServiceErrorException {
		bean.setDigitalGoods(OfferBusiness.searchDigitalGoodsByOfferId(bean.getOfferId()));
	}

	public static void changeStatus(OfferBean selectedBean, OfferBean[] selectedList, String newStatus) {
		LoggerWrapper log = new LoggerWrapper(OfferBusiness.class);
		OfferService service = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN_NAME, OfferService.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OFFER);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + newStatus);
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString() + "->" + operation + " " + entityName, null);
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		String code = null;
		String mex = null;
		try {
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			if (StatusConstants.DELETED.equals(newStatus)) {
				service.deleteOfferAndPackages(selectedBean.getOfferId(), Utilities.getTenantName());
				mex = String.format(message, operation + " " + entityName, selectedBean.getOfferName());
			}  else if (StatusConstants.INACTIVE.equals(newStatus)) {
				if (selectedList != null && selectedList.length > 0){
					for (OfferBean bean : selectedList) {
						service.offerAndPackagesChangeStatus(bean.getOfferId(), StatusConstants.INACTIVE, Utilities.getTenantName());
						bean.setOfferStatus(StatusConstants.INACTIVE);
					}
				}
				if (selectedList.length == 1) {
					mex = String.format(message, operation + " " + entityName, selectedList[0].getOfferName());             
				} else {
					mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.INACTIVATE_SOME_ITEM_OK);           
				}
				
			} else {
				service.offerAndPackagesChangeStatus(selectedBean.getOfferId(), newStatus, Utilities.getTenantName());      
				selectedBean.setOfferStatus(newStatus);
				mex = String.format(message, operation + " " + entityName, selectedBean.getOfferName());
			}
			// INVOKE AVS PACKAGE IN
			PackageIngestorAVSService packageIngestor = Utilities.findBean(ApplicationConstants.PACKAGE_INGESTOR_SERVICE_BEAN_NAME,
					PackageIngestorAVSService.class);
			String isEnable = ApplicationConstants.NO_VALUE;
			if (StatusConstants.ACTIVE.equals(newStatus)) {
				isEnable = ApplicationConstants.YES_VALUE;
			}
			
			if (selectedList != null && selectedList.length > 0){
				for (OfferBean bean : selectedList) {
					packageIngestor.invokePackageIngestorService(bean.getOfferId(), bean.getOfferName(), bean.getOfferDesc(),
							bean.getTypeAVS(), isEnable, null, null, null, null, Utilities.getTenantName());
					
				}
				selectedList = null;
			}
//			packageIngestor.invokePackageIngestorService(selectedBean.getOfferId(), selectedBean.getOfferName(), selectedBean.getOfferDesc(),
//					selectedBean.getTypeAVS(), isEnable, null, null, null, null, Utilities.getTenantName());

			msgBean.setUpdateFlag(true);
			code = ApplicationConstants.CODE_OK;
		} catch (Exception e) {
			if (e instanceof ServiceErrorException) {
				code = ((ServiceErrorException) e).getCode();	
			}
			mex = e.getMessage();
			log.logException(e.getMessage(), e);
			e.printStackTrace();
		}
		msgBean.openPopup(mex);
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString() + "->" + operation, code, mex);
	}

}
