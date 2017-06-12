package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.cxf.annotations.SchemaValidation;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpEndpointInfoDto;
import com.accenture.sdp.csm.managers.SdpAsynchronousFlowsManager;
import com.accenture.sdp.csmfe.webservices.request.asyncflows.SearchEndpointsFlowRequest;
import com.accenture.sdp.csmfe.webservices.response.asyncflows.SearchEndpointsInfoResp;
import com.accenture.sdp.csmfe.webservices.utils.EndpointsInfoConverter;

@WebService(serviceName = "SdpAsynchronousFlowsService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpAsynchronousFlowsService extends BaseWebService {

	@WebMethod(action = "getEndpointsInfo")
	@WebResult(name = "SearchEndpointsInfoResponse")
	public SearchEndpointsInfoResp getEndpointsInfo(@WebParam(name = "SearchEndpointsFlowRequest") SearchEndpointsFlowRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchEndpointsInfoResp wsResp = new SearchEndpointsInfoResp();
		try {
			SearchServiceResponse resp = SdpAsynchronousFlowsManager.getInstance().getEndpointsInfo(request.getSolutionOfferId(),
					trim(request.getOperationType()));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<SdpEndpointInfoDto> dtolist = (List<SdpEndpointInfoDto>) resp.getSearchResult();
				wsResp.getEndPoints().setEndPointList(EndpointsInfoConverter.convertEndpointsInfoList(dtolist));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
