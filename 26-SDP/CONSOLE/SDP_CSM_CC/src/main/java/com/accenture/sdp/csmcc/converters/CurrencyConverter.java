/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.CurrencyBean;
import com.accenture.sdp.csmfe.webservices.clients.currency.CurrencyInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.currency.SearchAllCurrenciesResp;


public final class CurrencyConverter {

	
	public static List<CurrencyBean> convertCurrencyInfoList(SearchAllCurrenciesResp response){
		List<CurrencyInfoResp> infos=response.getCurrencyList().getCurrency();
		List<CurrencyBean> beanList=new ArrayList<CurrencyBean>();
		for(CurrencyInfoResp info:infos){
				beanList.add(convertCurrencyInfoToBean(info));
		}
		return beanList;
	}
	
	public static CurrencyBean convertCurrencyInfoToBean(CurrencyInfoResp info){
		CurrencyBean bean=new CurrencyBean();
		bean.setCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		bean.setCurrencyTypeId(info.getCurrencyTypeId());
		bean.setCurrencyTypeName(info.getCurrencyTypeName());
		return bean;
	}
	

	private CurrencyConverter() {
		
	}

}
