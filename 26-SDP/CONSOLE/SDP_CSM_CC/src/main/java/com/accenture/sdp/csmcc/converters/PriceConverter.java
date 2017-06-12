/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.PriceBean;
import com.accenture.sdp.csmfe.webservices.clients.price.PriceCatalogResp;
import com.accenture.sdp.csmfe.webservices.clients.price.SearchAllPricesResp;


public final class PriceConverter {

	
	public static List<PriceBean> buildPriceTable(SearchAllPricesResp response){
		List<PriceCatalogResp> infos=response.getPriceList().getPrice();
		List<PriceBean> beanList=new ArrayList<PriceBean>();
		for(PriceCatalogResp info:infos){
				beanList.add(convertPriceInfoToBean(info));
		}
		return beanList;
	}
	
	public static PriceBean convertPriceInfoToBean(PriceCatalogResp info){
		PriceBean bean=new PriceBean();
		if (info.getCreatedDate() != null) {
			bean.setpriceCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		}
		bean.setPrice(info.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
		bean.setPriceString(info.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
		bean.setPriceId(info.getPriceCatalogId());	
		return bean;
	}
	
	private PriceConverter() {
	
	}
	
	

}
