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
import com.accenture.sdp.csm.dto.responses.RefCurrencyTypeResponseDto;
import com.accenture.sdp.csm.managers.RefCurrencyTypeManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.currency.CreateCurrencyRequest;
import com.accenture.sdp.csmfe.webservices.request.currency.DeleteCurrencyRequest;
import com.accenture.sdp.csmfe.webservices.request.currency.SearchCurrencyRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.currency.CreateCurrencyResp;
import com.accenture.sdp.csmfe.webservices.response.currency.SearchAllCurrenciesResp;
import com.accenture.sdp.csmfe.webservices.response.currency.SearchCurrencyResp;
import com.accenture.sdp.csmfe.webservices.utils.CurrencyBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "RefCurrencyTypeService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class RefCurrencyTypeService extends BaseWebService {

	@WebMethod(action = "cCreateCurrency")
	@WebResult(name = "CreateCurrencyResponse")
	public CreateCurrencyResp cCreateCurrency(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateCurrencyRequest") CreateCurrencyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateCurrencyResp wsResp = new CreateCurrencyResp();
		try {
			CreateServiceResponse resp = RefCurrencyTypeManager.getInstance().createCurrency(
					trim(request.getCurrencyTypeName()),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setCurrencyTypeId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeleteCurrency")
	@WebResult(name = "DeleteCurrencyResponse")
	public BaseResp cDeleteCurrency(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteCurrencyRequest") DeleteCurrencyRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = RefCurrencyTypeManager.getInstance().deleteCurrency(
					request.getCurrencyTypeId(),
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

	@WebMethod(action = "searchAllCurrencies")
	@WebResult(name = "SearchAllCurrenciesResponse")
	public SearchAllCurrenciesResp searchAllCurrencies(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllCurrenciesRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllCurrenciesResp wsResp = new SearchAllCurrenciesResp();
		try {
			SearchServiceResponse resp = RefCurrencyTypeManager.getInstance().searchAllCurrencies(
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getCurrencyList().setCurrencyList(CurrencyBeanConverter
						.convertCurrencies((List<RefCurrencyTypeResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchCurrency")
	@WebResult(name = "SearchCurrencyResponse")
	public SearchCurrencyResp cSearchCurrency(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchCurrencyRequest") SearchCurrencyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchCurrencyResp wsResp = new SearchCurrencyResp();
		try {
			SearchServiceResponse resp = RefCurrencyTypeManager.getInstance().searchCurrency(
					request.getCurrencyTypeId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getCurrencyList().setCurrencyList(CurrencyBeanConverter
						.convertCurrencies((List<RefCurrencyTypeResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}
