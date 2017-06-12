/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchAllServiceTemplatesResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchServiceTemplatesByPlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ServiceTemplateSearchAllInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ServiceTemplateSearchByPlatformInfoResp;

public final class ServiceTemplateConverter  {

	
	
	public static List<ServiceTemplateBean> buildServiceTemplateTable(SearchServiceTemplatesByPlatformResp response){
		List<ServiceTemplateSearchByPlatformInfoResp> infos=response.getServiceTemplates().getServiceTemplate();
		List<ServiceTemplateBean> beanList=new ArrayList<ServiceTemplateBean>();
		for(ServiceTemplateSearchByPlatformInfoResp info:infos){
				beanList.add(convertServiceTemplateInfoToBean(info));
		}
		
		return beanList;
	}
	public static List<ServiceTemplateBean> buildServiceTemplateTable(SearchAllServiceTemplatesResp response){
		List<ServiceTemplateSearchAllInfoResp> infos=response.getServiceTemplates().getServiceTemplate();
		List<ServiceTemplateBean> beanList=new ArrayList<ServiceTemplateBean>();
		for(ServiceTemplateSearchAllInfoResp info:infos){
				beanList.add(convertServiceTemplateInfoToBean(info));
		}
		return beanList;
	}
	
	public static ServiceTemplateBean convertServiceTemplateInfoToBean(ServiceTemplateSearchByPlatformInfoResp info){
		ServiceTemplateBean bean=new ServiceTemplateBean();
		if(info.getCreatedDate()!=null) {
			bean.setServiceTemplateCreationDate(Utilities.formatDate((((XMLGregorianCalendar)info.getCreatedDate()).toGregorianCalendar().getTime())));
		}
		bean.setServiceTemplateDesc(info.getServiceTemplateDescription());
		bean.setServiceTemplateExtId(info.getExternalId());
		bean.setServiceTemplateId(info.getServiceTemplateId());
		bean.setServiceTemplateName(info.getServiceTemplateName());
		bean.setServiceTemplateProfile(info.getServiceTemplateProfile());
		bean.setServiceTemplateStatus(info.getStatusName());
		bean.setPlatformName(info.getPlatformName());
		bean.setPlatformId(info.getPlatformId());
		bean.setServiceTemplateTypeId(info.getServiceTypeId());
		bean.setServiceTemplateTypeName(info.getServiceTypeName());
		return bean;
	}
	
	public static ServiceTemplateBean convertServiceTemplateInfoToBean(ServiceTemplateSearchAllInfoResp info){
		ServiceTemplateBean bean=new ServiceTemplateBean();
		bean.setServiceTemplateCreationDate(Utilities.formatDate((((XMLGregorianCalendar)info.getCreatedDate()).toGregorianCalendar().getTime())));
		bean.setServiceTemplateDesc(info.getServiceTemplateDescription());
		bean.setServiceTemplateExtId(info.getExternalId());
		bean.setServiceTemplateId(info.getServiceTemplateId());
		bean.setServiceTemplateName(info.getServiceTemplateName());
		bean.setServiceTemplateProfile(info.getServiceTemplateProfile());
		bean.setServiceTemplateStatus(info.getStatusName());
		bean.setPlatformName(info.getPlatformName());
		bean.setPlatformId(info.getPlatformId());
		bean.setServiceTemplateTypeId(info.getServiceTypeId());
		bean.setServiceTemplateTypeName(info.getServiceTypeName());
		return bean;
	}

	

	private ServiceTemplateConverter() {
		
	}
	
	

}
