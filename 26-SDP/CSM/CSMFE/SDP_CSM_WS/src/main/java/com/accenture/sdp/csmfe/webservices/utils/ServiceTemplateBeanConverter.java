package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpServiceTemplateDto;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.ServiceTemplateInfoResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.ServiceTemplateSearchAllInfoResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.ServiceTemplateSearchByPlatformInfoResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.ServiceTemplateSearchByVariantInfoResp;

public class ServiceTemplateBeanConverter extends BaseBeanConverter {

	public static List<ServiceTemplateInfoResp> convertServiceTemplate(List<SdpServiceTemplateDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<ServiceTemplateInfoResp> ainfos = new ArrayList<ServiceTemplateInfoResp>();
		for (SdpServiceTemplateDto a : dtos) {
			ServiceTemplateInfoResp ainfo = new ServiceTemplateInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setExternalId(a.getExternalId());
			ainfo.setPlatformId(a.getPlatformId());
			ainfo.setServiceTemplateDescription(a.getServiceTemplateDescription());
			ainfo.setServiceTemplateId(a.getServiceTemplateId());
			ainfo.setServiceTemplateName(a.getServiceTemplateName());
			ainfo.setServiceTemplateProfile(a.getServiceTemplateProfile());
			ainfo.setServiceTypeId(a.getServiceTypeId());
			ainfo.setStatusId(a.getStatusId());
			ainfos.add(ainfo);

		}
		return ainfos;
	}

	public static List<ServiceTemplateSearchAllInfoResp> convertServiceTemplateAll(List<SdpServiceTemplateDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<ServiceTemplateSearchAllInfoResp> ainfos = new ArrayList<ServiceTemplateSearchAllInfoResp>();
		for (SdpServiceTemplateDto a : dtos) {
			ServiceTemplateSearchAllInfoResp ainfo = new ServiceTemplateSearchAllInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setExternalId(a.getExternalId());
			ainfo.setPlatformId(a.getPlatformId());
			ainfo.setServiceTemplateDescription(a.getServiceTemplateDescription());
			ainfo.setServiceTemplateId(a.getServiceTemplateId());
			ainfo.setServiceTemplateName(a.getServiceTemplateName());
			ainfo.setServiceTemplateProfile(a.getServiceTemplateProfile());
			ainfo.setServiceTypeId(a.getServiceTypeId());
			ainfo.setStatusId(a.getStatusId());
			ainfo.setPlatformName(a.getPlatformName());
			ainfo.setServiceTypeName(a.getServiceTypeName());
			ainfo.setStatusName(a.getStatusName());
			ainfos.add(ainfo);

		}
		return ainfos;
	}

	public static List<ServiceTemplateSearchByVariantInfoResp> convertServiceTemplateByVariant(List<SdpServiceTemplateDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<ServiceTemplateSearchByVariantInfoResp> ainfos = new ArrayList<ServiceTemplateSearchByVariantInfoResp>();
		for (SdpServiceTemplateDto a : dtos) {
			ServiceTemplateSearchByVariantInfoResp ainfo = new ServiceTemplateSearchByVariantInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setExternalId(a.getExternalId());
			ainfo.setPlatformId(a.getPlatformId());
			ainfo.setServiceTemplateDescription(a.getServiceTemplateDescription());
			ainfo.setServiceTemplateId(a.getServiceTemplateId());
			ainfo.setServiceTemplateName(a.getServiceTemplateName());
			ainfo.setServiceTemplateProfile(a.getServiceTemplateProfile());
			ainfo.setServiceTypeId(a.getServiceTypeId());
			ainfo.setStatusId(a.getStatusId());
			ainfo.setPlatformName(a.getPlatformName());
			ainfo.setServiceTypeName(a.getServiceTypeName());
			ainfo.setStatusName(a.getStatusName());
			ainfos.add(ainfo);

		}
		return ainfos;
	}

	public static List<ServiceTemplateSearchByPlatformInfoResp> convertServiceTemplateByPlatform(List<SdpServiceTemplateDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<ServiceTemplateSearchByPlatformInfoResp> ainfos = new ArrayList<ServiceTemplateSearchByPlatformInfoResp>();
		for (SdpServiceTemplateDto a : dtos) {
			ServiceTemplateSearchByPlatformInfoResp ainfo = new ServiceTemplateSearchByPlatformInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setExternalId(a.getExternalId());
			ainfo.setPlatformId(a.getPlatformId());
			ainfo.setServiceTemplateDescription(a.getServiceTemplateDescription());
			ainfo.setServiceTemplateId(a.getServiceTemplateId());
			ainfo.setServiceTemplateName(a.getServiceTemplateName());
			ainfo.setServiceTemplateProfile(a.getServiceTemplateProfile());
			ainfo.setServiceTypeId(a.getServiceTypeId());
			ainfo.setStatusId(a.getStatusId());
			ainfo.setPlatformName(a.getPlatformName());
			ainfo.setServiceTypeName(a.getServiceTypeName());
			ainfo.setStatusName(a.getStatusName());
			ainfos.add(ainfo);

		}
		return ainfos;
	}
}
