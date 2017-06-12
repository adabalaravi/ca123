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
import com.accenture.sdp.csm.dto.responses.SdpPackagePriceDto;
import com.accenture.sdp.csm.managers.SdpPackagePriceManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.packageprice.CreatePackagePriceRequest;
import com.accenture.sdp.csmfe.webservices.request.packageprice.DeletePackagePriceRequest;
import com.accenture.sdp.csmfe.webservices.request.packageprice.ModifyPackagePriceRequest;
import com.accenture.sdp.csmfe.webservices.request.packageprice.SearchPackagePriceRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.packageprice.CreatePackagePriceResp;
import com.accenture.sdp.csmfe.webservices.response.packageprice.SearchPackagePriceResp;
import com.accenture.sdp.csmfe.webservices.utils.PackagePriceBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpPackagePriceService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
//JWS annotation that specifies the mapping of the service onto the
//SOAP message protocol. In particular, it specifies that the SOAP messages
//are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpPackagePriceService extends BaseWebService {
	
	@WebMethod(action="cCreatePackagePrice")
	@WebResult(name="CreatePackagePriceResponse")
	public CreatePackagePriceResp cCreatePackagePrice(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreatePackagePriceRequest") CreatePackagePriceRequest request) {
		
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		
		CreatePackagePriceResp wsResp = new CreatePackagePriceResp();
		try {
			CreateServiceResponse resp = SdpPackagePriceManager.getInstance().createPackagePrice(
					request.getRcPriceCatalogId(),
					request.getNrcPriceCatalogId(), 
					request.getCurrencyTypeId(),
					request.getRcFrequencyTypeId(),
					trim(request.getRcFlagProrate()),
					trim(request.getRcInAdvance()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPackagePriceId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;	
	}
	
	
	@WebMethod(action="cModifyPackagePrice")
	@WebResult(name="ModifyPackagePriceResponse")
	public BaseResp cModifyPackagePrice(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyPackagePriceRequest") ModifyPackagePriceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackagePriceManager.getInstance().modifyPackagePrice(
					request.getPackagePriceId(),
					request.getRcPriceCatalogId(),
					request.getNrcPriceCatalogId(), 
					request.getCurrencyTypeId(),
					request.getRcFrequencyTypeId(),
					trim(request.getRcFlagProrate()),
					trim(request.getRcInAdvance()),
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
	
	@WebMethod(action="cDeletePackagePrice")
	@WebResult(name="DeletePackagePriceResponse")
	public BaseResp cDeletePackagePrice(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePackagePriceRequest") DeletePackagePriceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackagePriceManager.getInstance().deletePackagePrice(
					request.getPackagePriceId(),
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
	
	@WebMethod(action = "cSearchPackagePrice")
	@WebResult(name = "SearchCurrencyResponse")
	public SearchPackagePriceResp cSearchPackagePrice(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPackagePriceRequest") SearchPackagePriceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPackagePriceResp wsResp = new SearchPackagePriceResp();
		try {
			SearchServiceResponse resp = SdpPackagePriceManager.getInstance().searchPackagePrice(
					request.getPackagePriceId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);					
				wsResp.getPackagePriceList().setPackagePriceList(PackagePriceBeanConverter
					.convertPackagePrices((List<SdpPackagePriceDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


}
