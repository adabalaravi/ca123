package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import org.apache.cxf.annotations.SchemaValidation;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpDeviceBrandResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceModelResponseDto;
import com.accenture.sdp.csm.managers.SdpDeviceManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SearchDeviceModelsByBrandRequest;
import com.accenture.sdp.csmfe.webservices.response.device.SearchAllDeviceBrandsResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchAllDeviceBrandsResp.Brands.Brand;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDeviceModelsByBrandResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDeviceModelsByBrandResp.Models.Model;
import com.accenture.sdp.csmfe.webservices.utils.DeviceBeanConverter;

@WebService(serviceName = "SdpDeviceBrandService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpDeviceBrandService extends BaseWebService {

	@WebMethod(action = "searchAllDeviceBrands")
	@WebResult(name = "SearchAllDeviceBrandsResponse")
	public SearchAllDeviceBrandsResp searchAllDeviceBrands(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllDeviceBrandsRequest") BaseRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllDeviceBrandsResp wsResp = new SearchAllDeviceBrandsResp();
		try {
			SearchServiceResponse resp = SdpDeviceManager.getInstance().searchAllDeviceBrands(trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<Brand> temp = DeviceBeanConverter.convertBrands((List<SdpDeviceBrandResponseDto>) resp.getSearchResult());
				wsResp.setBrands(new SearchAllDeviceBrandsResp.Brands());
				wsResp.getBrands().getBrand().addAll(temp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchDeviceModelsByBrand")
	@WebResult(name = "SearchDeviceModelsByBrandResponse")
	public SearchDeviceModelsByBrandResp searchDeviceModelsByBrand(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDeviceModelsByBrandRequest") SearchDeviceModelsByBrandRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDeviceModelsByBrandResp wsResp = new SearchDeviceModelsByBrandResp();
		try {
			SearchServiceResponse resp = SdpDeviceManager.getInstance().searchDeviceModelsByBrand(request.getBrandId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<Model> temp = DeviceBeanConverter.convertModels((List<SdpDeviceModelResponseDto>) resp.getSearchResult());
				wsResp.setModels(new SearchDeviceModelsByBrandResp.Models());
				wsResp.getModels().getModel().addAll(temp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}