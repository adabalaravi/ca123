/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.CurrencyBean;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.CurrencyConverter;
import com.accenture.sdp.csmcc.services.CurrencyService;
import com.accenture.sdp.csmfe.webservices.clients.currency.SearchAllCurrenciesResp;

public class CurrencyBusiness implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
		
	public List<CurrencyBean> getBufferedData(long startRow, long numElementToFind) throws ServiceErrorException{
		CurrencyService service = Utilities.findBean(ApplicationConstants.CURRENCY_SERVICE_BEAN_NAME, CurrencyService.class);
		List<CurrencyBean> tableResult= new ArrayList<CurrencyBean>();
		SearchAllCurrenciesResp resp=service.searchAllCurrencies(startRow, numElementToFind, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = CurrencyConverter.convertCurrencyInfoList(resp);
			}
		return tableResult;
		
	}

}
