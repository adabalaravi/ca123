package com.accenture.sdp.csmcc.services;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.beans.VoucherInfo;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.VoucherConverter;
import com.accenture.sdp.csmfe.webservices.clients.voucher.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.ModifyVoucher;
import com.accenture.sdp.csmfe.webservices.clients.voucher.ModifyVoucherCampaign;
import com.accenture.sdp.csmfe.webservices.clients.voucher.ModifyVoucherCampaignRequest;
import com.accenture.sdp.csmfe.webservices.clients.voucher.ModifyVoucherRequest;
import com.accenture.sdp.csmfe.webservices.clients.voucher.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SdpVoucherService;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SdpVoucherService_Service;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchAvailableVoucherTypes;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchAvailableVouchersBySolutionOfferId;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVoucherByVoucherCode;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVoucherResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVoucherTypeResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVouchersBySolutionOfferId;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVouchersBySolutionOfferIdRequest;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVouchersResp;

@ManagedBean(name = ApplicationConstants.VOUCHER_SERVICE_BEAN)
@ApplicationScoped
public class VoucherService {

	private LoggerWrapper log = new LoggerWrapper(VoucherService.class);

	private SdpVoucherService port;

	public VoucherService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.VOUCHER_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpVoucherService_Service service = new SdpVoucherService_Service(url);
			port = service.getSdpVoucherServicePort();
			log.logDebug("Voucher Service instantiated. Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e.getMessage());
			log.logException(e.getMessage(), e);
		} catch (Exception e) {
			log.logError(e.getMessage());
			log.logException(e.getMessage(), e);
		}
	}

	public VoucherInfo searchVoucherByCode(String voucherCode, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchVoucherByVoucherCode req = new SearchVoucherByVoucherCode();
		req.setVoucherCode(voucherCode);
		SearchVoucherResp resp = port.searchVoucherByVoucherCode(req, tenantName).getSearchVoucherByVoucherCodeResponse();
		parseResponse(resp);
		return VoucherConverter.convertVoucher(resp.getVoucher());
	}

	public void modifyVoucher(Long partyId, Long voucherId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ModifyVoucher wrapper = new ModifyVoucher();
		ModifyVoucherRequest req = new ModifyVoucherRequest();
		wrapper.setModifyVoucherRequest(req);
		req.setPartyId(partyId);
		req.setVoucherId(voucherId);
		BaseResp resp = port.modifyVoucher(wrapper, tenantName).getModifyVoucherResponse();
		parseResponse(resp);
	}

	public void createVoucherCampaign(Long solutionOfferId, Long validityPeriod, String voucherType, Date starDate, Date endDate, Holder<String> tenantName) throws ServiceErrorException, DatatypeConfigurationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ModifyVoucherCampaign wrapper = new ModifyVoucherCampaign();
		ModifyVoucherCampaignRequest req = new ModifyVoucherCampaignRequest();
		wrapper.setModifyVoucherCampaignRequest(req);
		req.setSolutionOfferId(solutionOfferId);
		req.setValidityPeriod(validityPeriod);
		req.setVoucherType(voucherType);
		req.setStartDate(Utilities.getXMLGregorianCalendar(starDate));
		req.setEndDate(Utilities.getXMLGregorianCalendar(endDate));
		BaseResp resp = port.modifyVoucherCampaign(wrapper, tenantName).getModifyVoucherCampaignResponse();
		parseResponse(resp);
	}

	public SearchVouchersResp searchAvailableVouchersBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecords, Holder<String> tenantName)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvailableVouchersBySolutionOfferId wrapper = new SearchAvailableVouchersBySolutionOfferId();
		SearchVouchersBySolutionOfferIdRequest req = new SearchVouchersBySolutionOfferIdRequest();
		wrapper.setSearchAvailableVouchersBySolutionOfferIdRequest(req);
		req.setSolutionOfferId(solutionOfferId);
		req.setStartPosition(startPosition);
		req.setMaxRecordsNumber(maxRecords);
		SearchVouchersResp resp = port.searchAvailableVouchersBySolutionOfferId(wrapper, tenantName).getSearchAvailableVouchersBySolutionOfferIdResponse();
		parseResponse(resp);
		return resp;
	}
	
	public SearchVouchersResp searchVouchersBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecords, Holder<String> tenantName)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchVouchersBySolutionOfferId wrapper = new SearchVouchersBySolutionOfferId();
		SearchVouchersBySolutionOfferIdRequest req = new SearchVouchersBySolutionOfferIdRequest();
		wrapper.setSearchVouchersBySolutionOfferIdRequest(req);
		req.setSolutionOfferId(solutionOfferId);
		req.setStartPosition(startPosition);
		req.setMaxRecordsNumber(maxRecords);
		SearchVouchersResp resp = port.searchVouchersBySolutionOfferId(wrapper, tenantName).getSearchVouchersBySolutionOfferIdResponse();
		parseResponse(resp);
		return resp;
	}


	public SearchVoucherTypeResp searchAvailableVoucherTypes(Holder<String> tenantName)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchVoucherTypeResp resp = port.searchAvailableVoucherTypes(new SearchAvailableVoucherTypes(), tenantName).getSearchAvailableVoucherTypesResponse();
		parseResponse(resp);
		return resp;
	}

	private void parseResponse(BaseResp resp) throws ServiceErrorException {
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())) {
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters() != null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()) {
					parameters.add(new String[] { param.getName(), param.getValue() });
				}
			}
			throw new ServiceErrorException(resp.getResultCode(), Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}
}
