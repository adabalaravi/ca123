/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.ServiceTypeBean;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SearchServiceTypesResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.ServiceTypeInfoResp;

public final class ServiceTypeConverter {

		
	
	public static List<ServiceTypeBean> buildServiceTypeTable(SearchServiceTypesResp response){
		List<ServiceTypeInfoResp> infos=response.getServiceTypeList().getServiceType();
		List<ServiceTypeBean> beanList=new ArrayList<ServiceTypeBean>();
		for(ServiceTypeInfoResp info:infos){
				beanList.add(convertServiceTypeInfoToBean(info));
		}
		return beanList;
	}
	
	
	
	public static ServiceTypeBean convertServiceTypeInfoToBean(ServiceTypeInfoResp info){
		ServiceTypeBean bean=new ServiceTypeBean();
		bean.setServiceTypeId(info.getServiceTypeId());
		bean.setServiceTypeName(info.getServiceTypeName());
		return bean;
	}

	

	private ServiceTypeConverter() {
		
		
		
	
	}
	
	

}
