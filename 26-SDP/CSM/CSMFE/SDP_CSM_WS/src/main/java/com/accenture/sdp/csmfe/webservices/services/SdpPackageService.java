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

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpPackageDto;
import com.accenture.sdp.csm.managers.SdpPackageManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.packages.CreatePackageRequest;
import com.accenture.sdp.csmfe.webservices.request.packages.DeletePackageRequest;
import com.accenture.sdp.csmfe.webservices.request.packages.ModifyPackageComplexRequest;
import com.accenture.sdp.csmfe.webservices.request.packages.ModifyPackageRequest;
import com.accenture.sdp.csmfe.webservices.request.packages.PackageChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.packages.SearchPackageComplexRequest;
import com.accenture.sdp.csmfe.webservices.request.packages.SearchPackageRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.packages.CreatePackageResp;
import com.accenture.sdp.csmfe.webservices.response.packages.SearchPackageComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.packages.SearchPackageResp;
import com.accenture.sdp.csmfe.webservices.utils.PackageBeanConverter;


/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpPackageService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
//JWS annotation that specifies the mapping of the service onto the
//SOAP message protocol. In particular, it specifies that the SOAP messages
//are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpPackageService extends BaseWebService {

	@WebMethod(action = "cCreatePackage")
	@WebResult(name = "CreatePackageResponse")
	public CreatePackageResp cCreatePackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreatePackageRequest") CreatePackageRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreatePackageResp wsResp = new CreatePackageResp();
		try {
			CreateServiceResponse resp = SdpPackageManager.getInstance().createPackage(
					request.getSolutionOfferId(),
					request.getOfferId(),
					trim(request.getIsMandatory()),
					request.getPackagePriceId(),
					request.getBasePackageId(),
					request.getGroupId(),
					trim(request.getExternalId()),
					trim(request.getProfile()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPackageId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "cModifyPackage")
	@WebResult(name="ModifyPackageResponse")
	public BaseResp cModifyPackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyPackageRequest") ModifyPackageRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackageManager.getInstance().modifyPackage(
					request.getPackageId(),
					request.getSolutionOfferId(),
					request.getOfferId(),
					trim(request.getIsMandatory()),
					request.getPackagePriceId(),
					request.getBasePackageId(),
					request.getGroupId(),
					trim(request.getExternalId()),
					trim(request.getProfile()),
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

	@WebMethod(action = "modifyPackage")
	@WebResult(name="ModifyPackageResponse")
	public BaseResp modifyPackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyPackageRequest") ModifyPackageComplexRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackageManager.getInstance().modifyPackage(
					request.getSolutionOfferId(), 
					PackageBeanConverter.convertSolutionOfferDetailReq(request.getPackages().getPackageList()),
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


	@WebMethod(action = "cDeletePackage")
	@WebResult(name="DeletePackageResponse")
	public BaseResp cDeletePackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePackageRequest") DeletePackageRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackageManager.getInstance().deletePackage(
					request.getPackageId(),
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

	@WebMethod(action = "cSearchPackage")
	@WebResult(name="SearchPackageResponse")
	public SearchPackageResp cSearchPackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPackageRequest") SearchPackageRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPackageResp wsResp = new SearchPackageResp();
		try {
			SearchServiceResponse resp = SdpPackageManager.getInstance().searchPackage(
					request.getPackageId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getPackages().setPackageList(PackageBeanConverter.convertPackages((List<SdpPackageDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchPackage")
	@WebResult(name="SearchPackageResponse")
	public SearchPackageComplexRespPaginated searchPackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPackageRequest") SearchPackageComplexRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPackageComplexRespPaginated wsResp = new SearchPackageComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpPackageManager.getInstance().searchPackage(
					trim(request.getSolutionOfferName()),
					trim(request.getOfferName()),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getPackages().setPackageList(PackageBeanConverter.convertComplexPackages((List<SdpPackageDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "packageChangeStatus")
	@WebResult(name="PackageChangeStatusResponse")
	public BaseResp packageChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "PackageChangeStatusRequest") PackageChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackageManager.getInstance().packageChangeStatus(
					request.getPackageId(),
					trim(request.getStatus()),
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
	
	@WebMethod(action = "isPackageSubscribed")
	@WebResult(name = "IsPackageSubscribedResponse")
	public BaseResp isPackageSubscribed(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "IsPackageSubscribedRequest") SearchPackageRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPackageManager.getInstance().isPackageSubscribed(request.getPackageId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
