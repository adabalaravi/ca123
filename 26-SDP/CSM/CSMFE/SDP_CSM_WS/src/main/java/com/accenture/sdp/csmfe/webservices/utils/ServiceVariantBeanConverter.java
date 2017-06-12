package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpServiceVariantDto;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.SearchServiceVariantInfoResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.ServiceVariantInfoResp;

public class ServiceVariantBeanConverter extends BaseBeanConverter {

	public static List<ServiceVariantInfoResp> convertServiceVariant(List<SdpServiceVariantDto> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<ServiceVariantInfoResp> pinfos = new ArrayList<ServiceVariantInfoResp>();
		for (SdpServiceVariantDto p : pdtos) {
			ServiceVariantInfoResp pinfo = new ServiceVariantInfoResp();
			convertBaseInfo(p, pinfo);
			pinfo.setServiceVariantId(p.getServiceVariantId());
			pinfo.setServiceVariantName(p.getServiceVariantName());
			pinfo.setServiceVariantProfile(p.getServiceVariantProfile());
			pinfo.setServiceVariantDescription(p.getServiceVariantDescription());
			pinfo.setServiceTemplateId(p.getServiceTemplateId());
			pinfo.setStatusId(p.getStatusId());
			pinfo.setExternalId(p.getExternalId());
			pinfos.add(pinfo);
		}
		return pinfos;
	}

	public static List<SearchServiceVariantInfoResp> convertSearchServiceVariant(List<SdpServiceVariantDto> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<SearchServiceVariantInfoResp> pinfos = new ArrayList<SearchServiceVariantInfoResp>();
		for (SdpServiceVariantDto p : pdtos) {
			SearchServiceVariantInfoResp pinfo = new SearchServiceVariantInfoResp();
			convertBaseInfo(p, pinfo);
			pinfo.setServiceVariantId(p.getServiceVariantId());
			pinfo.setServiceVariantName(p.getServiceVariantName());
			pinfo.setServiceVariantDescription(p.getServiceVariantDescription());
			pinfo.setServiceVariantProfile(p.getServiceVariantProfile());
			pinfo.setServiceTemplateId(p.getServiceTemplateId());
			pinfo.setServiceTemplateName(p.getServiceTemplateName());
			pinfo.setStatusId(p.getStatusId());
			pinfo.setStatusName(p.getStatusName());
			pinfo.setExternalId(p.getExternalId());
			pinfos.add(pinfo);
		}
		return pinfos;
	}
}
