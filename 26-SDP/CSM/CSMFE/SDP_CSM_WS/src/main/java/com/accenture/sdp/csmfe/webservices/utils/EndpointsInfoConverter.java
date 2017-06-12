package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpEndpointInfoDto;
import com.accenture.sdp.csmfe.webservices.response.asyncflows.EndpointFlowResp;

public class EndpointsInfoConverter extends BaseBeanConverter {

	public static List<EndpointFlowResp> convertEndpointsInfoList(List<SdpEndpointInfoDto> endpointInfoDtos) {
		if (endpointInfoDtos == null) {
			return null;
		}
		List<EndpointFlowResp> result = new ArrayList<EndpointFlowResp>();
		for (SdpEndpointInfoDto cp : endpointInfoDtos) {
			EndpointFlowResp cpinfo = new EndpointFlowResp();

			cpinfo.setPlatformName(cp.getPlatformName());
			cpinfo.setServiceName(cp.getServiceName());
			cpinfo.setOperationName(cp.getOperationName());
			result.add(cpinfo);
		}
		return result;
	}
}
