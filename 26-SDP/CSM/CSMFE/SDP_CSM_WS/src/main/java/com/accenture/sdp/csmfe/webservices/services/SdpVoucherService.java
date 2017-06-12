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

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpVoucherDto;
import com.accenture.sdp.csm.managers.SdpVoucherManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.voucher.ModifyVoucherCampaignRequest;
import com.accenture.sdp.csmfe.webservices.request.voucher.ModifyVoucherRequest;
import com.accenture.sdp.csmfe.webservices.request.voucher.SearchVouchersBySolutionOfferIdRequest;
import com.accenture.sdp.csmfe.webservices.request.voucher.SearchVouchersByVoucherCodeRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.voucher.SearchVoucherResp;
import com.accenture.sdp.csmfe.webservices.response.voucher.SearchVoucherTypeResp;
import com.accenture.sdp.csmfe.webservices.response.voucher.SearchVouchersResp;
import com.accenture.sdp.csmfe.webservices.response.voucher.VoucherCampaignListResp;
import com.accenture.sdp.csmfe.webservices.utils.VoucherConverter;

@WebService(serviceName = "SdpVoucherService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpVoucherService extends BaseWebService {

	@WebMethod(action = "searchAvailableVoucherTypes")
	@WebResult(name = "SearchAvailableVoucherTypesResponse")
	public SearchVoucherTypeResp searchAvailableVoucherTypes(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchVoucherTypeResp wsResp = new SearchVoucherTypeResp();
		try {
			SearchServiceResponse resp = SdpVoucherManager.getInstance().searchAvailableVoucherTypes(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getVoucherTypeList().setVoucherCampaignList(VoucherConverter.convertVoucherTypes((List<SdpVoucherDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp; 
	}

	@WebMethod(action = "searchVouchersBySolutionOfferId")
	@WebResult(name = "SearchVouchersBySolutionOfferIdResponse")
	public SearchVouchersResp searchVouchersBySolutionOfferId(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchVouchersBySolutionOfferIdRequest") SearchVouchersBySolutionOfferIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchVouchersResp wsResp = new SearchVouchersResp();
		try {
			SearchServiceResponse resp = SdpVoucherManager.getInstance().searchVouchersBySolutionOfferId(request.getSolutionOfferId(),
					request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getVouchers().setVoucherList(VoucherConverter.convertVouchers((List<SdpVoucherDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAvailableVouchersBySolutionOfferId")
	@WebResult(name = "SearchAvailableVouchersBySolutionOfferIdResponse")
	public SearchVouchersResp searchAvailableVouchersBySolutionOfferId(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAvailableVouchersBySolutionOfferIdRequest") SearchVouchersBySolutionOfferIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchVouchersResp wsResp = new SearchVouchersResp();
		try {
			SearchServiceResponse resp = SdpVoucherManager.getInstance().searchAvailableVouchersBySolutionOfferId(request.getSolutionOfferId(),
					request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getVouchers().setVoucherList(VoucherConverter.convertVouchers((List<SdpVoucherDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchVoucherByVoucherCode")
	@WebResult(name = "SearchVoucherByVoucherCodeResponse")
	public SearchVoucherResp searchVoucherByVoucherCode(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "voucherCode") String voucherCode) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchVoucherResp wsResp = new SearchVoucherResp();
		try {
			ComplexServiceResponse resp = SdpVoucherManager.getInstance().searchVoucherByCode(trim(voucherCode), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setVoucher(VoucherConverter.convertVoucher((SdpVoucherDto) resp.getComplexObject()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchVouchersByVoucherCode")
	@WebResult(name = "SearchVouchersByVoucherCodeResponse")
	public SearchVouchersResp searchVouchersByVoucherCode(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchVouchersByVoucherCodeRequest") SearchVouchersByVoucherCodeRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchVouchersResp wsResp = new SearchVouchersResp();
		try {
			SearchServiceResponse resp = SdpVoucherManager.getInstance().searchVouchersByCodeLike(trim(request.getVoucherCode()), request.getStartPosition(),
					request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getVouchers().setVoucherList(VoucherConverter.convertVouchers((List<SdpVoucherDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifyVoucher")
	@WebResult(name = "ModifyVoucherResponse")
	public BaseResp modifyVoucher(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyVoucherRequest") ModifyVoucherRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpVoucherManager.getInstance().modifyVoucher(request.getVoucherId(), request.getPartyId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifyVoucherCampaign")
	@WebResult(name = "ModifyVoucherCampaignResponse")
	public BaseResp modifyVoucherCampaign(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyVoucherCampaignRequest") ModifyVoucherCampaignRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpVoucherManager.getInstance().modifyVoucherCampaign(request.getSolutionOfferId(), request.getValidityPeriod(),
					trim(request.getVoucherType()),request.getStartDate(),request.getEndDate(), trim(tenantName.value));

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
