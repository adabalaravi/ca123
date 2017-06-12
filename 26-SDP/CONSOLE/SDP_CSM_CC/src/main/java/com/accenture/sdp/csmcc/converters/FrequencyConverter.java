package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.FrequencyBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.frequency.FrequencyInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.frequency.SearchAllFrequenciesResp;


public final class FrequencyConverter {
	
	public static List<FrequencyBean> buildFrequencyTable(SearchAllFrequenciesResp response){
		List<FrequencyInfoResp> infos=response.getFrequencyList().getFrequency();
		List<FrequencyBean> beanList=new ArrayList<FrequencyBean>();
		for(FrequencyInfoResp info:infos){
				beanList.add(convertFrequencyInfoToBean(info));
		}
		return beanList;
	}
	
	public static FrequencyBean convertFrequencyInfoToBean(FrequencyInfoResp info){
		FrequencyBean bean=new FrequencyBean();
		bean.setFrequencyCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		bean.setFrequencyDesc(info.getFrequencyTypeDescription());
		bean.setFrequencyId(info.getFrequencyTypeId());
		bean.setFrequencyName(info.getFrequencyTypeName());
		bean.setFrequencyDays(info.getFrequencyDays());
		return bean;
	}
	

	private FrequencyConverter() {
		
	}

}
