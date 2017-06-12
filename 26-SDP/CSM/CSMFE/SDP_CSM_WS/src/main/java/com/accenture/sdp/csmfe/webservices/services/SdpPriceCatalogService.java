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
import com.accenture.sdp.csm.dto.responses.SdpPriceCatalogDto;
import com.accenture.sdp.csm.managers.SdpPriceCatalogManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.price.CreatePriceRequest;
import com.accenture.sdp.csmfe.webservices.request.price.DeletePriceRequest;
import com.accenture.sdp.csmfe.webservices.request.price.SearchPriceRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.price.CreatePriceResp;
import com.accenture.sdp.csmfe.webservices.response.price.SearchAllPricesResp;
import com.accenture.sdp.csmfe.webservices.response.price.SearchPriceResp;
import com.accenture.sdp.csmfe.webservices.utils.PriceCatalogBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpPriceCatalogService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpPriceCatalogService extends BaseWebService {

	@WebMethod(action = "cCreatePrice")
	@WebResult(name = "CreatePriceResponse")
	public CreatePriceResp cCreatePrice(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreatePriceRequest") CreatePriceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreatePriceResp wsResp = new CreatePriceResp();
		try {
			CreateServiceResponse resp = SdpPriceCatalogManager.getInstance().createPrice(request.getPrice(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPriceCatalogId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeletePrice")
	@WebResult(name = "DeletePriceResponse")
	public BaseResp cDeletePrice(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePriceRequest") DeletePriceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPriceCatalogManager.getInstance().deletePrice(request.getPriceCatalogId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllprices")
	@WebResult(name = "SearchAllPricesResponse")
	public SearchAllPricesResp searchAllprices(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllPricesRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllPricesResp wsResp = new SearchAllPricesResp();
		try {
			SearchServiceResponse resp = SdpPriceCatalogManager.getInstance().searchAllPrices(request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getPriceList().setPriceList(PriceCatalogBeanConverter.convertPriceCatalogList((List<SdpPriceCatalogDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchPrice")
	@WebResult(name = "SearchPriceResponse")
	public SearchPriceResp cSearchPrice(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPriceRequest") SearchPriceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPriceResp wsResp = new SearchPriceResp();
		try {
			SearchServiceResponse resp = SdpPriceCatalogManager.getInstance().searchPrice(request.getPackagePriceId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPriceList().setPriceList(PriceCatalogBeanConverter.convertPrices((List<SdpPriceCatalogDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}
