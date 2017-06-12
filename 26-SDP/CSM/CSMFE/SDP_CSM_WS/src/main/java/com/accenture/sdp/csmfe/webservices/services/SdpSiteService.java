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
import com.accenture.sdp.csm.dto.responses.SdpPartySiteDto;
import com.accenture.sdp.csm.managers.SdpSiteManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.site.CreateSiteRequest;
import com.accenture.sdp.csmfe.webservices.request.site.DeleteSiteRequest;
import com.accenture.sdp.csmfe.webservices.request.site.ModifySiteRequest;
import com.accenture.sdp.csmfe.webservices.request.site.SearchSiteBySubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.site.SearchSitesByAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.site.SearchSitesByPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.site.SearchSitesRequest;
import com.accenture.sdp.csmfe.webservices.request.site.SiteChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.site.CreateSiteResp;
import com.accenture.sdp.csmfe.webservices.response.site.SearchSitesResp;
import com.accenture.sdp.csmfe.webservices.response.site.SearchSitesRespPaginated;
import com.accenture.sdp.csmfe.webservices.utils.PartySiteBeanConverter;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpSiteService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpSiteService extends BaseWebService {

	@WebMethod(action = "cCreateSite")
	@WebResult(name = "CreateSiteResponse")
	public CreateSiteResp cCreateSite(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSiteRequest") CreateSiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateSiteResp wsResp = new CreateSiteResp();
		try {
			CreateServiceResponse resp = SdpSiteManager.getInstance()
					.createSite(trim(request.getSiteName()), trim(request.getSiteDescription()), request.getParentPartyId(), trim(request.getExternalId()),
							trim(request.getStreetAddress()), trim(request.getCity()), trim(request.getZipCode()), trim(request.getProvince()),
							trim(request.getCountry()), trim(request.getSiteProfile()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setSiteId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cModifySite")
	@WebResult(name = "ModifySiteResponse")
	public BaseResp cModifySite(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySiteRequest") ModifySiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSiteManager.getInstance().modifySite(request.getSiteId(), trim(request.getSiteName()),
					trim(request.getSiteDescription()), trim(request.getStreetAddress()), trim(request.getCity()), trim(request.getZipCode()),
					trim(request.getProvince()), trim(request.getCountry()), trim(request.getExternalId()), trim(request.getSiteProfile()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeleteSite")
	@WebResult(name = "DeleteSiteResponse")
	public BaseResp cDeleteSite(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteSiteRequest") DeleteSiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSiteManager.getInstance().deleteSite(request.getSiteId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSites")
	@WebResult(name = "SearchSitesResponse")
	public SearchSitesRespPaginated searchSites(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSitesRequest") SearchSitesRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSitesRespPaginated wsResp = new SearchSitesRespPaginated();
		try {
			SearchServiceResponse resp = SdpSiteManager.getInstance().searchSites(request.getSiteId(), trim(request.getSiteName()), request.getStartPosition(),
					request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getSites().setSiteList(PartySiteBeanConverter.convertSites((List<SdpPartySiteDto>) resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSiteBySubscription")
	@WebResult(name = "SearchSiteBySubscriptionResponse")
	public SearchSitesResp searchSiteBySubscription(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSiteBySubscriptionRequest") SearchSiteBySubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSitesResp wsResp = new SearchSitesResp();
		try {
			SearchServiceResponse resp = SdpSiteManager.getInstance().searchSiteBySubscription(request.getSubscriptionId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getSites().setSiteList(PartySiteBeanConverter.convertSites((List<SdpPartySiteDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSitesByParty")
	@WebResult(name = "SearchSitesByPartyResponse")
	public SearchSitesRespPaginated searchSitesByParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSitesByPartyRequest") SearchSitesByPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSitesRespPaginated wsResp = new SearchSitesRespPaginated();
		try {
			SearchServiceResponse resp = SdpSiteManager.getInstance().searchSitesByParty(request.getParentPartyId(), request.getStartPosition(),
					request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getSites().setSiteList(PartySiteBeanConverter.convertSites((List<SdpPartySiteDto>) resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSitesByAccount")
	@WebResult(name = "SearchSitesByAccountResponse")
	public SearchSitesResp searchSitesByAccount(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSitesByAccountRequest") SearchSitesByAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSitesResp wsResp = new SearchSitesResp();
		try {
			SearchServiceResponse resp = SdpSiteManager.getInstance().searchSiteByAccount(trim(request.getAccountName()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getSites().setSiteList(PartySiteBeanConverter.convertSites((List<SdpPartySiteDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "siteChangeStatus")
	@WebResult(name = "SiteChangeStatusResponse")
	public BaseResp siteChangeStatus(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SiteChangeStatusRequest") SiteChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSiteManager.getInstance().siteChangeStatus(request.getSiteId(), trim(request.getStatus()), trim(tenantName.value));

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
