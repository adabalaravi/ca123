package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpPlatformDto;
import com.accenture.sdp.csm.dto.responses.SdpServiceTemplateDto;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantDto;
import com.accenture.sdp.csmfe.webservices.response.platform.PlatformComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.platform.PlatformInfoResp;
import com.accenture.sdp.csmfe.webservices.response.platform.ServiceTemplateInfoPlatformResp;
import com.accenture.sdp.csmfe.webservices.response.platform.ServiceVariantInfoPlatformResp;

public class PlatformBeanConverter extends BaseBeanConverter {

	public static PlatformInfoResp convertPlatform(SdpPlatformDto p) {
		if (p == null) {
			return null;
		}
		PlatformInfoResp pinfo = new PlatformInfoResp();
		convertBaseInfo(p, pinfo);
		pinfo.setPlatformId(p.getPlatformId());
		pinfo.setPlatformName(p.getPlatformName());
		pinfo.setPlatformDescription(p.getPlatformDescription());
		pinfo.setStatusId(p.getPlatformStatusId());
		pinfo.setExternalId(p.getExternalId());
		pinfo.setPlatformProfile(p.getPlatformProfile());
		return pinfo;
	}

	public static List<PlatformInfoResp> convertPlatform(List<SdpPlatformDto> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<PlatformInfoResp> pinfos = new ArrayList<PlatformInfoResp>();
		for (SdpPlatformDto p : pdtos) {
			pinfos.add(convertPlatform(p));
		}
		return pinfos;
	}

	// questo metodo aggiunge lo statusName rispetto a quello sopra
	public static List<PlatformComplexInfoResp> convertComplexPlatform(List<SdpPlatformDto> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<PlatformComplexInfoResp> pinfos = new ArrayList<PlatformComplexInfoResp>();
		for (SdpPlatformDto p : pdtos) {
			PlatformComplexInfoResp pinfo = new PlatformComplexInfoResp();
			convertBaseInfo(p, pinfo);
			pinfo.setPlatformId(p.getPlatformId());
			pinfo.setPlatformName(p.getPlatformName());
			pinfo.setPlatformDescription(p.getPlatformDescription());
			pinfo.setStatusId(p.getPlatformStatusId());
			pinfo.setExternalId(p.getExternalId());
			pinfo.setPlatformProfile(p.getPlatformProfile());
			pinfo.setStatusName(p.getStatusName());
			pinfos.add(pinfo);
		}
		return pinfos;
	}

	public static ServiceVariantInfoPlatformResp convertServiceVariant(SdpServiceVariantDto p) {
		if (p == null) {
			return null;
		}
		ServiceVariantInfoPlatformResp pinfo = new ServiceVariantInfoPlatformResp();
		convertBaseInfo(p, pinfo);
		pinfo.setServiceVariantId(p.getServiceVariantId());
		pinfo.setServiceVariantName(p.getServiceVariantName());
		pinfo.setServiceVariantDescription(p.getServiceVariantDescription());
		pinfo.setServiceVariantProfile(p.getServiceVariantProfile());
		pinfo.setServiceTemplateId(p.getServiceTemplateId());
		pinfo.setStatusId(p.getStatusId());
		pinfo.setExternalId(p.getExternalId());
		return pinfo;
	}

	public static ServiceTemplateInfoPlatformResp convertServiceTemplate(SdpServiceTemplateDto p) {
		if (p == null) {
			return null;
		}
		ServiceTemplateInfoPlatformResp pinfo = new ServiceTemplateInfoPlatformResp();
		convertBaseInfo(p, pinfo);
		pinfo.setServiceTemplateName(p.getServiceTemplateName());
		pinfo.setServiceTemplateDescription(p.getServiceTemplateDescription());
		pinfo.setServiceTemplateId(p.getServiceTemplateId());
		pinfo.setServiceTemplateProfile(p.getServiceTemplateProfile());
		pinfo.setPlatformId(p.getPlatformId());
		pinfo.setStatusId(p.getStatusId());
		pinfo.setExternalId(p.getExternalId());
		pinfo.setServiceTypeId(p.getServiceTypeId());
		return pinfo;
	}
}
