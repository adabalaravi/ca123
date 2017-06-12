/*
 * To change this Variant, choose Tools | Variants
 * and open the Variant in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.beans.ServiceVariantBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.platform.PlatformInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformByServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.ServiceTemplateInfoPlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.ServiceVariantInfoPlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchAllServiceVariantsResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantsByServiceTemplateResp;



public final class ServiceVariantConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public static List<ServiceVariantBean> buildServiceVariantTable(SearchServiceVariantsByServiceTemplateResp response){
	
		List<SearchServiceVariantInfoResp> infos=response.getServiceVariantList().getServiceVariant();
		List<ServiceVariantBean> beanList=new ArrayList<ServiceVariantBean>();
		for(SearchServiceVariantInfoResp info:infos){
				beanList.add(convertServiceVariantInfoToBean(info));
		}
		
		return beanList;
	}
	public static List<ServiceVariantBean> buildServiceVariantTable(SearchAllServiceVariantsResp response){
		List<SearchServiceVariantInfoResp> infos=response.getServicevariantList().getServiceVariant();
		List<ServiceVariantBean> beanList=new ArrayList<ServiceVariantBean>();
		for(SearchServiceVariantInfoResp info:infos){
				beanList.add(convertServiceVariantInfoToBean(info));
		}
		return beanList;
	}
	
	public static List<ServiceVariantBean> buildServiceVariantTable(SearchPlatformByServiceVariantResp response){
		PlatformInfoResp platform=response.getPlatforms().getPlatform();
		ServiceTemplateInfoPlatformResp template=response.getPlatforms().getServiceTemplate();
		ServiceVariantInfoPlatformResp variant=response.getPlatforms().getServiceVariant();
		List<ServiceVariantBean> beanList=new ArrayList<ServiceVariantBean>();
		
		beanList.add(convertServiceVariantInfoToBean(platform,template,variant));
		
		return beanList;
	}
	
	public static ServiceVariantBean convertServiceVariantInfoToBean(SearchServiceVariantInfoResp info){
		ServiceVariantBean bean=new ServiceVariantBean();
		if (info.getCreatedDate()!=null) {
			bean.setServiceVariantCreationDate(Utilities.formatDate((((XMLGregorianCalendar)info.getCreatedDate()).toGregorianCalendar().getTime())));
		}
		bean.setServiceVariantDesc(info.getServiceVariantDescription());
		bean.setServiceVariantExtId(info.getExternalId());
		bean.setServiceVariantId(info.getServiceVariantId());
		bean.setServiceVariantName(info.getServiceVariantName());
		bean.setServiceVariantProfile(info.getServiceVariantProfile());
		bean.setServiceVariantStatus(info.getStatusName());
		if (info.getServiceTemplateId() != null) {
			ServiceTemplateBean parent = new ServiceTemplateBean();
			parent.setServiceTemplateId(info.getServiceTemplateId());
			bean.setParentServiceTemplate(parent);
		}
		bean.setTemplateName(info.getServiceTemplateName());
		return bean;
	}	
	
	public static ServiceVariantBean convertServiceVariantInfoToBean(PlatformInfoResp platform,ServiceTemplateInfoPlatformResp template,ServiceVariantInfoPlatformResp variant){
		ServiceVariantBean bean=new ServiceVariantBean();
		bean.setServiceVariantDesc(variant.getServiceVariantDescription());
		bean.setServiceVariantExtId(variant.getExternalId());
		bean.setServiceVariantId(variant.getServiceVariantId());
		bean.setServiceVariantName(variant.getServiceVariantName());
		bean.setServiceVariantProfile(variant.getServiceVariantProfile());
		if (variant.getServiceTemplateId() != null) {
			ServiceTemplateBean parent = new ServiceTemplateBean();
			parent.setServiceTemplateId(variant.getServiceTemplateId());
			bean.setParentServiceTemplate(parent);
		}
		bean.setTemplateName(template.getServiceTemplateName());
		bean.setPlatformName(platform.getPlatformName());
		return bean;
	}	

	private ServiceVariantConverter() {
		
	}
	
	

}
