package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.RefFrequencyTypeResponseDto;
import com.accenture.sdp.csm.managers.RefFrequencyTypeManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.frequency.CreateFrequencyRequest;
import com.accenture.sdp.csmfe.webservices.request.frequency.DeleteFrequencyRequest;
import com.accenture.sdp.csmfe.webservices.request.frequency.SearchFraquencyRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.frequency.CreateFrequencyResp;
import com.accenture.sdp.csmfe.webservices.response.frequency.SearchAllFrequenciesResp;
import com.accenture.sdp.csmfe.webservices.response.frequency.SearchFrequencyResp;
import com.accenture.sdp.csmfe.webservices.utils.FrequencyBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "RefFrequencyTypeService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
//JWS annotation that specifies the mapping of the service onto the
//SOAP message protocol. In particular, it specifies that the SOAP messages
//are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class RefFrequencyTypeService extends BaseWebService {

	@WebMethod(action="cCreateFrequency")
	@WebResult(name="CreateFrequencyResponse")
	public CreateFrequencyResp cCreateFrequency(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateFrequencyRequest") CreateFrequencyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateFrequencyResp wsResp = new CreateFrequencyResp();
		try {
			CreateServiceResponse resp = RefFrequencyTypeManager.getInstance().createFrequency(
					trim(request.getFrequencyTypeName()), 
					trim(request.getFrequencyTypeDescription()),
					request.getFrequencyDays(),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setFrequencyTypeId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;	
	}


	@WebMethod(action="cDeleteFrequency")
	@WebResult(name="DeleteFrequencyResponse")
	public BaseResp cDeleteFrequency(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePriceRequest") DeleteFrequencyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = RefFrequencyTypeManager.getInstance().deleteFrequency(
					request.getFrequencyTypeId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;	
	}



	@WebMethod(action="searchAllFrequencies")
	@WebResult(name="SearchAllFrequenciesResponse")
	public SearchAllFrequenciesResp searchAllFrequencies(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllFrequenciesRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllFrequenciesResp wsResp = new SearchAllFrequenciesResp();
		try {
			SearchServiceResponse resp = RefFrequencyTypeManager.getInstance().searchAllFrequencies(
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getFrequencyList().setFrequencyList(FrequencyBeanConverter
					.convertFrequencies((List<RefFrequencyTypeResponseDto>)resp.getSearchResult()));
			}

		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;	
	}
	
	@WebMethod(action = "cSearchFrequency")
	@WebResult(name = "SearchFrequencyResponse")
	public SearchFrequencyResp cSearchFrequency(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchFrequencyRequest") SearchFraquencyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchFrequencyResp wsResp = new SearchFrequencyResp();
		try {
			SearchServiceResponse resp = RefFrequencyTypeManager.getInstance().searchFrequency(
					request.getFrequencyTypeId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getFrequencyList().setFrequencyList(FrequencyBeanConverter
					.convertFrequencies((List<RefFrequencyTypeResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}
