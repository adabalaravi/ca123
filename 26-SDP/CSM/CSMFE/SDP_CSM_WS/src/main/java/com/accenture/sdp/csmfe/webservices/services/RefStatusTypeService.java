package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.RefStatusTypeResponseDto;
import com.accenture.sdp.csm.managers.RefStatusTypeManager;
import com.accenture.sdp.csmfe.webservices.request.BaseRequest;
import com.accenture.sdp.csmfe.webservices.response.status.SearchAllStatusResp;
import com.accenture.sdp.csmfe.webservices.response.status.StatusInfoListResp;
import com.accenture.sdp.csmfe.webservices.utils.StatusBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;


@WebService(serviceName = "RefStatusTypeService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class RefStatusTypeService extends BaseWebService {

	@WebMethod(action = "searchAllStatus")
	@WebResult(name = "searchAllStatusResponse")
	public SearchAllStatusResp searchAllStatus(
			@WebParam(name = "searchAllStatusRequest") BaseRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllStatusResp wsResp = new SearchAllStatusResp();
		try {
			SearchServiceResponse resp = RefStatusTypeManager.getInstance().searchAllStatus();

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				StatusInfoListResp tmp = new StatusInfoListResp();
				tmp.setStatusList(StatusBeanConverter.convertListOfStatus((List<RefStatusTypeResponseDto>) resp.getSearchResult()));
				wsResp.setStatusList(tmp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


}
