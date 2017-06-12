package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.RefServiceTypeResponseDto;
import com.accenture.sdp.csm.managers.RefServiceTypeManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.response.servicetype.SearchServiceTypesResp;
import com.accenture.sdp.csmfe.webservices.utils.ServiceTypeBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpServiceTypeService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpServiceTypeService extends BaseWebService {

	@WebMethod(action = "searchAllServiceTypes")
	@WebResult(name = "SearchAllServiceTypesResponse")
	public SearchServiceTypesResp searchAllServiceTypes(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceTypesResp wsResp = new SearchServiceTypesResp();
		try {
			SearchServiceResponse resp = RefServiceTypeManager.getInstance().searchAllServiceTypes(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getServiceTypeList().setServiceTypeList(
						ServiceTypeBeanConverter.convertServiceType((List<RefServiceTypeResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
