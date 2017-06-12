package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.OfferDetailConverter;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PackageService;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRespPaginated;

public class PackageBusiness {
	
	
	

	public static List<PackageBean> searchPackagesBySolutionOffer() throws ServiceErrorException{
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		LoggerWrapper log = new LoggerWrapper(PackageBusiness.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		List<PackageBean> tableResult= new ArrayList<PackageBean>();
		SolutionOfferBean solutionOfferBean = infoSessionBean.getSolutionOfferBean();
		if (solutionOfferBean != null){
			SearchPackageComplexRespPaginated resp = service.searchPackageByOfferAndSolutionOffer(null, solutionOfferBean.getSolutionOfferName(), 
					0L, 0L, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = OfferDetailConverter.buildOfferDetailTable(resp);
			} else {
				log.logEndFeature(resp.getResultCode(), resp.getDescription());
			}
		}
		return tableResult;
	}
	
	public static List<PackageBean> searchPackagesBySolutionOffer(String solutionOfferName) throws ServiceErrorException{
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		LoggerWrapper log = new LoggerWrapper(PackageBusiness.class);
		List<PackageBean> tableResult= new ArrayList<PackageBean>();
		if (solutionOfferName != null){
			SearchPackageComplexRespPaginated resp = service.searchPackageByOfferAndSolutionOffer(null, solutionOfferName, 
					0L, 0L, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = OfferDetailConverter.buildOfferDetailTable(resp);
			} else {
				log.logEndFeature(resp.getResultCode(), resp.getDescription());
			}
		}
		return tableResult;
	}

	public static PackageBean searchPackageByOfferAndSolutionOffer(String offerName, String solutionOfferName) throws ServiceErrorException{
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		LoggerWrapper log = new LoggerWrapper(PackageBusiness.class);
		List<PackageBean> tableResult= new ArrayList<PackageBean>();
		SearchPackageComplexRespPaginated resp = service.searchPackageByOfferAndSolutionOffer(offerName, solutionOfferName, 0L, 0L, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = OfferDetailConverter.buildOfferDetailTable(resp);
		} else {
			log.logEndFeature(resp.getResultCode(), resp.getDescription());
		}
		return (tableResult.size() > 0? tableResult.get(0):null);
	}



	public static List<PackageBean> searchDiscountPackagesBySolutionOffer(String solutionOfferName) throws ServiceErrorException{
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		LoggerWrapper log = new LoggerWrapper(PackageBusiness.class);
		List<PackageBean> tableResult= new ArrayList<PackageBean>();
		if (solutionOfferName != null){
			SearchPackageComplexRespPaginated resp = service.searchPackageByOfferAndSolutionOffer(null, solutionOfferName, 0L, 0L, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = OfferDetailConverter.buildOfferDetailTable(resp);
			} else {
				log.logEndFeature(resp.getResultCode(), resp.getDescription());
			}
		}
		return tableResult;
	}
	
	public static void addPackage(PackageBean bean) {
		LoggerWrapper log = new LoggerWrapper(PackageBean.class);
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		Map<String,Object> logMap = new HashMap<String,Object>();
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_OFFER_NAME, bean.getOfferName());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_NRC, bean.getNrcPriceCatalog());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_RCPRICE, bean.getRcPriceCatalog());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_RCFREQUENCY, bean.getRcFrequencyTypeId());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_TYPE, bean.getType());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_PACKAGE_LINK, bean.getParentOfferName());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_GROUP_NAME, bean.getGroupName());
		log.logStartFeature(logMap);
		logMap.clear();
		Long solutionOfferId = infoSessionBean.getSolutionOfferBean().getSolutionOfferId();
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		String code = null;
		String mex;
		try {
			String rcFlagProrate;
			String rcInAdvance;
			if (bean.getRcPriceCatalogId().equals(1L)){
				rcFlagProrate = ApplicationConstants.NO_VALUE;
				rcInAdvance = ApplicationConstants.NO_VALUE;
			} else {
				rcFlagProrate = ApplicationConstants.YES_VALUE;
				rcInAdvance = ApplicationConstants.YES_VALUE;
			}

			service.createPackage(bean.getBasePackageId(), bean.getPackageExternalId(), bean.getGroupId(), bean.getMandatory(), bean.getOfferId(), bean.getPackageProfile(), solutionOfferId, 
					bean.getCurrencyTypeId(), bean.getNrcPriceCatalogId(), bean.getRcPriceCatalogId(), bean.getRcFrequencyTypeId(), rcFlagProrate, rcInAdvance, Utilities.getTenantName());
			mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE), 
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_OFFER_DETAIL_MESSAGE), bean.getOfferName());
			code = ApplicationConstants.CODE_OK;
			msgBean.setNextParam(PathConstants.SOLUTION_OFFER_PACKAGE_VIEW);
			
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

	public static void updatePackage(PackageBean bean) {
		LoggerWrapper log = new LoggerWrapper(PackageBean.class);
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		Map<String,Object> logMap = new HashMap<String,Object>();
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_OFFER_NAME, bean.getOfferName());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_NRC, bean.getNrcPriceCatalog());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_RCPRICE, bean.getRcPriceCatalog());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_RCFREQUENCY, bean.getRcFrequencyTypeId());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_TYPE, bean.getType());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_PACKAGE_LINK, bean.getParentOfferName());
		logMap.put(MessageConstants.ADD_OFFER_DETAIL_GROUP_NAME, bean.getGroupName());
		log.logStartFeature(logMap);
		logMap.clear();
		Long solutionOfferId = infoSessionBean.getSolutionOfferBean().getSolutionOfferId();
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		String code = null;
		String mex;
		try {
			String rcFlagProrate;
			String rcInAdvance;
			if (bean.getRcPriceCatalogId().equals(1L)){
				rcFlagProrate = ApplicationConstants.NO_VALUE;
				rcInAdvance = ApplicationConstants.NO_VALUE;
			} else {
				rcFlagProrate = ApplicationConstants.YES_VALUE;
				rcInAdvance = ApplicationConstants.YES_VALUE;
			}
			
			service.updatePackage(bean.getPackageId(), bean.getBasePackageId(), bean.getPackageExternalId(), bean.getGroupId(), bean.getMandatory(), bean.getOfferId(), bean.getPackageProfile(), solutionOfferId, 
					bean.getCurrencyTypeId(), bean.getNrcPriceCatalogId(), bean.getRcPriceCatalogId(), bean.getRcFrequencyTypeId(), rcFlagProrate, rcInAdvance, Utilities.getTenantName());
			
			mex = String.format(
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.UPDATE_OFFER_DETAIL_MESSAGE), bean.getOfferName());
			
			msgBean.setUpdateFlag(true);
			code = ApplicationConstants.CODE_OK;
			msgBean.setNextParam(PathConstants.SOLUTION_OFFER_PACKAGE_VIEW);
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
