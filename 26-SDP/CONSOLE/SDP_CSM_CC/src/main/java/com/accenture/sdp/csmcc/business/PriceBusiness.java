/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.PriceBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.comparators.PriceComparator;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.PriceConverter;
import com.accenture.sdp.csmcc.managers.PriceManager;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PriceService;
import com.accenture.sdp.csmfe.webservices.clients.price.SearchAllPricesResp;

public final class PriceBusiness  {
	
	private PriceBusiness() {
		
	}	
		
	public static  List<PriceBean> searchAllPrice() throws ServiceErrorException{
		PriceService service = Utilities.findBean(ApplicationConstants.PRICE_SERVICE_BEAN_NAME, PriceService.class);
		List<PriceBean> tableResult= new ArrayList<PriceBean>();
		SearchAllPricesResp resp= service.searchAllPrice(0L, 0L, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = PriceConverter.buildPriceTable(resp);
				sortTable(tableResult);
		}
		return tableResult;
	}
	
	
	public static void addPrice(PriceBean priceBean) {
		LoggerWrapper log = new LoggerWrapper(PriceBean.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.PRICE_VALIDATION_NAME_FIELD, priceBean.getPriceString());
		if (ValidationUtilities.validateMandatoryFields(validationMap) && ValidationUtilities.validatePriceFormat(priceBean.getPriceString())){
			PriceManager tableBean = Utilities.findBean(ApplicationConstants.PRICE_MANAGER, PriceManager.class);
			PriceService service = Utilities.findBean(ApplicationConstants.PRICE_SERVICE_BEAN_NAME, PriceService.class);
			String priceLbl = Utilities	.getDefaultMessage(ApplicationConstants.MESSAGE_BUNDLE,	MessageConstants.PRICE_NAME_LBL);
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(priceLbl, priceBean.getPrice());
			logMap.clear();

			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				BigDecimal bigDecimalPrice= new BigDecimal(priceBean.getPriceString());
				service.createPrice(bigDecimalPrice, Utilities.getTenantName());
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_PRICE_MESSAGE), priceBean.getPriceString());
				tableBean.refreshTable();
				
				
				msgBean.setNextParam(PathConstants.PRICE_VIEW);
			} catch (ServiceErrorException e) {
				log.logException(e.getMessage(), e);
				code = e.getCode();
				mex = e.getMessage();
				
			} catch (Exception e) {
				log.logException(e.getMessage(), e);
				code = ApplicationConstants.CODE_GENERIC_ERROR;
				mex = e.getMessage();
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code,mex);
		}
	}
	
	public static void deletePrice(PriceBean priceBean) {
		// logging
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		LoggerWrapper log = new LoggerWrapper(PriceManager.class);
		PriceService service = Utilities.findBean(ApplicationConstants.PRICE_SERVICE_BEAN_NAME, PriceService.class);
		
		String delete = Utilities.getDefaultMessage(
				ApplicationConstants.MESSAGE_BUNDLE,
				MessageConstants.DELETE_PRICE);
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString() + "->"
				+ delete, null);
		
		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			service.deletePrice(priceBean.getPriceId(), Utilities.getTenantName());
			mex = String.format(Utilities.getMessage(
					ApplicationConstants.MESSAGE_BUNDLE,
					MessageConstants.OK_MESSAGE), Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,
					MessageConstants.DELETE_PRICE_MESSAGE), priceBean.getPrice());
			code = ApplicationConstants.CODE_OK;
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();
			
		}
		msgBean.openPopup(mex);
		// logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString() + "->"+ delete, code, mex);
	}
	
	
	
	public static void sortTable(List<PriceBean> prices) {
		PriceComparator comparator = new PriceComparator(PriceBean.PRICE_FIELD, true);
		Collections.sort(prices, comparator);
}

}
