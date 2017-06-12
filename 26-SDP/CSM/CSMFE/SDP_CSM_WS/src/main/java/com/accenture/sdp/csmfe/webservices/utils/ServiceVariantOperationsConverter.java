package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpServiceVariantOperationDto;
import com.accenture.sdp.csmfe.webservices.response.servicevariantoperations.SdpServiceVariantOperationInfoResp;

public class ServiceVariantOperationsConverter extends BaseBeanConverter {

	public static List<SdpServiceVariantOperationInfoResp> convertServiceVariantOperations(List<SdpServiceVariantOperationDto> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<SdpServiceVariantOperationInfoResp> pinfos = new ArrayList<SdpServiceVariantOperationInfoResp>();
		for (SdpServiceVariantOperationDto p : pdtos) {
			SdpServiceVariantOperationInfoResp pinfo = new SdpServiceVariantOperationInfoResp();
			convertBaseInfo(p, pinfo);
			pinfo.setServiceVariantId(p.getServiceVariantId());
			pinfo.setServiceVariantName(p.getServiceVariantName());
			pinfo.setMethodName(p.getMethodName());
			pinfo.setInputParameters(p.getInputParameters());
			pinfo.setInputXslt(p.getInputXslt());
			pinfo.setOutputXslt(p.getOutputXslt());
			pinfo.setUddiKey(p.getUddiKey());
			pinfo.setOperationType(p.getOperationType());
			pinfo.setServiceTemplateId(p.getServiceTemplateId());
			pinfo.setServiceTemplateName(p.getServiceTemplateName());
			pinfos.add(pinfo);
		}
		return pinfos;
	}

}
