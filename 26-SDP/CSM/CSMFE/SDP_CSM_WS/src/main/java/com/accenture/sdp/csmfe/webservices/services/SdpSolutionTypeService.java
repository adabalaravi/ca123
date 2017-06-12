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
import com.accenture.sdp.csm.dto.responses.RefSolutionTypeResponseDto;
import com.accenture.sdp.csm.managers.RefSolutionTypeManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.response.solutiontype.SearchSolutionTypesResp;
import com.accenture.sdp.csmfe.webservices.utils.SolutionTypeBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpSolutionTypeService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
//JWS annotation that specifies the mapping of the service onto the
//SOAP message protocol. In particular, it specifies that the SOAP messages
//are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpSolutionTypeService extends BaseWebService {

	@WebMethod(action="searchAllSolutionTypes")
	@WebResult(name="SearchAllSolutionTypesResponse")
	public SearchSolutionTypesResp searchAllSolutionTypes(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionTypesResp wsResp = new SearchSolutionTypesResp();
		try {
			SearchServiceResponse resp = RefSolutionTypeManager.getInstance().searchAllSolutionTypes(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getSolutionTypeList().setSolutionTypeList(SolutionTypeBeanConverter.
						convertSolutionType((List<RefSolutionTypeResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;	
	}
}
