package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.beans.ExternalIdBean;
import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.OfferDetailConverter;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CDeleteSolutionOffer;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CDeleteSolutionOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CSearchSolutionOffer;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOffer;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndDiscount;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndDiscountRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndDiscountResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndDiscountResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndPackage;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndPackageRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndPackageResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferAndPackageResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CreateSolutionOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.DeleteSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.DiscountListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ExternalIdInfo;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ExternalIdList;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.IsSolutionOfferSubscribed;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.IsSolutionOfferSubscribedResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifyDiscountInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifyDiscounts;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifyDiscountsRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifyDiscountsResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifySolutionOffer;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifySolutionOfferPartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifySolutionOfferPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifySolutionOfferPartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifySolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ModifySolutionOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.PartyGroupNameInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.PartyGroupNameListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.PartyGroupOperationInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.PartyGroupOperationListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SdpSolutionOfferService;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SdpSolutionOfferService_Service;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOfferTypes;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOfferTypesResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOffers;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOffersResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchDiscountedSolutionOfferBySolutionOffer;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchDiscountedSolutionOfferBySolutionOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchDiscountedSolutionOffersBySolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferAndSolutionRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersBySolution;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersBySolutionRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersBySolutionResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferChangeStatusResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferDetailListRequest;

@ManagedBean(name = ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME)
@ApplicationScoped
public class SolutionOfferService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpSolutionOfferService port;

	private ExternalIdList prepareExternalIdList(List<ExternalIdBean> externalId) {
		ExternalIdList externalIdList = new ExternalIdList();
		if (externalId != null) {
			for (ExternalIdBean extBean : externalId) {
				if (!Utilities.isEmptyString(extBean.getExternalId())) {
					ExternalIdInfo info = new ExternalIdInfo();
					info.setExternalPlatformName(extBean.getExternalPlatform());
					info.setExternalId(extBean.getExternalId());

					externalIdList.getExternalId().add(info);
				}
			}
		}
		return externalIdList;
	}

	public Long createSolutionOffer(String solutionOfferName, String solutionOfferDescription, List<ExternalIdBean> externalId, String solutionOfferProfile,
			Long platformId, Holder<String> tenantName) throws ServiceErrorException {
		CreateSolutionOfferRequest r = new CreateSolutionOfferRequest();
		r.setSolutionOfferName(solutionOfferName);
		r.setSolutionOfferDescription(solutionOfferDescription);
		r.setExternalIds(prepareExternalIdList(externalId));
		CreateSolutionOffer req = new CreateSolutionOffer();
		req.setCreateSolutionOfferRequest(r);
		CreateSolutionOfferResponse result = port.createSolutionOffer(req, tenantName);
		CreateSolutionOfferResp resp = result.getCreateSolutionOfferResponse();
		parseResponse(resp);
		return resp.getSolutionOfferId();
	}

	public Long createSolutionOfferAndPackage(String solutionName, String solutionOfferName, String solutionOfferDesc, Date solutionOfferStartDate,
			Date solutionOfferEndDate, String solutionOfferProfile, List<PackageBean> offerDetails, List<String> partyGroups, List<ExternalIdBean> externalId,Long solutionOfferDuration,String solutionOfferType,
			Holder<String> tenantName) throws DatatypeConfigurationException, ServiceErrorException {

		CreateSolutionOfferAndPackageRequest req = new CreateSolutionOfferAndPackageRequest();
		req.setSolutionName(solutionName);
		req.setSolutionOfferName(solutionOfferName);
		req.setSolutionOfferDescription(solutionOfferDesc);
		req.setDuration(solutionOfferDuration);
		req.setSolutionOfferType(solutionOfferType);
		if (solutionOfferStartDate != null) {
			req.setSolutionOfferStartDate(Utilities.getXMLGregorianCalendar(solutionOfferStartDate));
		}
		if (solutionOfferEndDate != null) {
			XMLGregorianCalendar end = Utilities.getXMLGregorianCalendar(solutionOfferEndDate);
//			Utilities.convertToEndOfDate(end);
			req.setSolutionOfferEndDate(end);
		}
		req.setExternalIds(prepareExternalIdList(externalId));
		req.setSolutionOfferProfile(solutionOfferProfile);
		SolutionOfferDetailListRequest offerReques = new SolutionOfferDetailListRequest();
		offerReques.getSolutionOfferDetail().addAll(OfferDetailConverter.convertOfferDetailList(offerDetails));
		req.setSolutionOfferDetails(offerReques);
		PartyGroupNameListRequest groups = new PartyGroupNameListRequest();
		for (String groupName : partyGroups) {
			PartyGroupNameInfoRequest groupNameReq = new PartyGroupNameInfoRequest();
			groupNameReq.setPartyGroupName(groupName);
			groups.getPartyGroup().add(groupNameReq);
		}
		req.setPartyGroups(groups);

		CreateSolutionOfferAndPackage request = new CreateSolutionOfferAndPackage();
		request.setCreateSolutionOfferRequest(req);
		CreateSolutionOfferAndPackageResponse result = port.createSolutionOfferAndPackage(request, tenantName);
		CreateSolutionOfferAndPackageResp resp = result.getCreateSolutionOfferAndPackageResponse();
		parseResponse(resp);
		return resp.getSolutionOfferId();
	}

	public void modifySolutionOffer(Long solutionOfferId, Long solutionId, String solutionOfferName, String solutionOfferDesc, Date solutionOfferStartDate,
			Date solutionOfferEndDate, List<ExternalIdBean> externalId, String solutionOfferProfile,Long solutionOfferDuration,String solutionOfferType, Holder<String> tenantName)
			throws DatatypeConfigurationException, ServiceErrorException {

		ModifySolutionOfferRequest r = new ModifySolutionOfferRequest();
		r.setSolutionOfferId(solutionOfferId);
		r.setSolutionId(solutionId);
		r.setSolutionOfferName(solutionOfferName);
		r.setSolutionOfferDescription(solutionOfferDesc);
		r.setDuration(solutionOfferDuration);
		r.setSolutionOfferType(solutionOfferType);
		if (solutionOfferStartDate != null) {
			r.setStartDate(Utilities.getXMLGregorianCalendar(solutionOfferStartDate));
		}
		if (solutionOfferEndDate != null) {
			XMLGregorianCalendar end = Utilities.getXMLGregorianCalendar(solutionOfferEndDate);
//			Utilities.convertToEndOfDate(end);
			r.setEndDate(end);
		}
		r.setExternalIds(prepareExternalIdList(externalId));
		r.setProfile(solutionOfferProfile);
		ModifySolutionOffer req = new ModifySolutionOffer();
		req.setModifySolutionOfferRequest(r);
		ModifySolutionOfferResponse result = port.modifySolutionOffer(req, tenantName);
		BaseResp resp = result.getModifySolutionOfferResponse();
		parseResponse(resp);
	}

	public void deleteSolutionOffer(Long solutionOfferId, Holder<String> tenantName) throws ServiceErrorException {

		DeleteSolutionOfferRequest r = new DeleteSolutionOfferRequest();
		r.setSolutionOfferId(solutionOfferId);
		CDeleteSolutionOffer req = new CDeleteSolutionOffer();
		req.setDeleteSolutionOfferRequest(r);
		CDeleteSolutionOfferResponse result = port.cDeleteSolutionOffer(req, tenantName);
		BaseResp resp = result.getDeleteSolutionOfferResponse();
		parseResponse(resp);
	}

	public SearchSolutionOfferComplexRespPaginated searchAllSolutionOffers(Long startPosition, Long maxRecordsNumber, Holder<String> tenantName)
			throws ServiceErrorException {

		BaseRequestPaginated r = new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllSolutionOffers req = new SearchAllSolutionOffers();
		req.setSearchAllSolutionOffersRequest(r);
		SearchAllSolutionOffersResponse result = port.searchAllSolutionOffers(req, tenantName);
		SearchSolutionOfferComplexRespPaginated resp = result.getSearchAllSolutionOffersResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchSolutionOfferAndSolutionRespPaginated searchSolutionOffersBySolution(String solutionName, Long startPosition, Long maxRecordsNumber,
			Holder<String> tenantName) throws ServiceErrorException {

		SearchSolutionOffersBySolutionRequest r = new SearchSolutionOffersBySolutionRequest();
		r.setSolutionName(solutionName);
		r.setStartPosition(startPosition);
		r.setMaxRecordsNumber(maxRecordsNumber);
		SearchSolutionOffersBySolution req = new SearchSolutionOffersBySolution();
		req.setSearchSolutionOffersBySolutionRequest(r);
		SearchSolutionOffersBySolutionResponse result = port.searchSolutionOffersBySolution(req, tenantName);
		SearchSolutionOfferAndSolutionRespPaginated resp = result.getSearchSolutionOffersBySolutionResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchSolutionOfferComplexRespPaginated searchDiscountedSolutionOfferBySolutionOffer(String solutionOfferName, Long startPosition,
			Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException {

		SearchDiscountedSolutionOffersBySolutionOfferRequest r = new SearchDiscountedSolutionOffersBySolutionOfferRequest();
		r.setSolutionOfferName(solutionOfferName);
		r.setStartPosition(startPosition);
		r.setMaxRecordsNumber(maxRecordsNumber);
		SearchDiscountedSolutionOfferBySolutionOffer req = new SearchDiscountedSolutionOfferBySolutionOffer();
		req.setSearchDiscountedSolutionOfferBySolutionOfferRequest(r);
		SearchDiscountedSolutionOfferBySolutionOfferResponse result = port.searchDiscountedSolutionOfferBySolutionOffer(req, tenantName);
		SearchSolutionOfferComplexRespPaginated resp = result.getSearchDiscountedSolutionOfferBySolutionOfferResponse();
		parseResponse(resp);
		return resp;
	}

	public void changeStatus(long id, String status, Holder<String> tenantName) throws ServiceErrorException {
		SolutionOfferChangeStatusRequest r = new SolutionOfferChangeStatusRequest();
		r.setSolutionOfferId(id);
		r.setStatus(status);
		SolutionOfferChangeStatus req = new SolutionOfferChangeStatus();
		req.setSolutionOfferChangeStatusRequest(r);
		SolutionOfferChangeStatusResponse result = port.solutionOfferChangeStatus(req, tenantName);
		BaseResp resp = result.getSolutionOfferChangeStatusResponse();
		parseResponse(resp);
	}

	public Long createSolutionOfferAndDiscount(String name, String description, Long prentSolutionOfferId, List<ExternalIdBean> externalId, String profile,
			Date startDate, Date endDate, DiscountListRequest discounts, List<String> partyGroups, Holder<String> tenantName) throws ServiceErrorException,
			DatatypeConfigurationException {
		CreateSolutionOfferAndDiscountRequest r = new CreateSolutionOfferAndDiscountRequest();
		r.setSolutionOfferName(name);
		r.setSolutionOfferDescription(description);
		r.setParentSolutionOfferId(prentSolutionOfferId);
		r.setExternalIds(prepareExternalIdList(externalId));
		r.setProfile(profile);
		r.setStartDate(Utilities.getXMLGregorianCalendar(startDate));
		XMLGregorianCalendar end = Utilities.getXMLGregorianCalendar(endDate);
//		Utilities.convertToEndOfDate(end);
		r.setEndDate(end);
		PartyGroupNameListRequest groups = new PartyGroupNameListRequest();
		for (String groupName : partyGroups) {
			PartyGroupNameInfoRequest groupNameReq = new PartyGroupNameInfoRequest();
			groupNameReq.setPartyGroupName(groupName);
			groups.getPartyGroup().add(groupNameReq);
		}
		r.setPartyGroups(groups);
		r.setDiscounts(discounts);
		CreateSolutionOfferAndDiscount req = new CreateSolutionOfferAndDiscount();
		req.setCreateSolutionOfferRequest(r);
		CreateSolutionOfferAndDiscountResponse result = port.createSolutionOfferAndDiscount(req, tenantName);
		CreateSolutionOfferAndDiscountResp resp = result.getCreateSolutionOfferAndDiscountResponse();
		parseResponse(resp);
		return resp.getSolutionOfferId();

	}

	public void modifyDiscounts(List<PackageBean> packages, Holder<String> tenantName) throws ServiceErrorException, DatatypeConfigurationException {

		ModifyDiscountsRequest r = new ModifyDiscountsRequest();
		for (PackageBean bean : packages) {
			ModifyDiscountInfoRequest discount = new ModifyDiscountInfoRequest();
			discount.setDiscountId(bean.getDiscountId());
			if (!Utilities.isEmptyString(bean.getSetupFeeDiscount())) {
				discount.setDiscountAbsNrc(new BigDecimal(bean.getSetupFeeDiscount()));
			}
			if (!Utilities.isEmptyString(bean.getRecurringFeeDiscount())) {
				discount.setDiscountAbsRc(new BigDecimal(bean.getRecurringFeeDiscount()));
			}
			if (!Utilities.isEmptyString(bean.getSetupFeeDiscountPercentage())) {
				discount.setDiscountPercNrc(new BigDecimal(bean.getSetupFeeDiscountPercentage()));
			}
			if (!Utilities.isEmptyString(bean.getRecurringFeeDiscountPercentage())) {
				discount.setDiscountPercRc(new BigDecimal(bean.getRecurringFeeDiscountPercentage()));
			}
			r.getDiscount().add(discount);
		}
		ModifyDiscounts req = new ModifyDiscounts();
		req.setModifyDiscountRequest(r);
		ModifyDiscountsResponse result = port.modifyDiscounts(req, tenantName);
		BaseResp resp = result.getModifyDiscountsResponse();
		parseResponse(resp);
	}

	public void updatePartyGroups(Collection<PartyGroupBean> partyGroupIds, Long solutionOfferId, Holder<String> tenantName)
			throws DatatypeConfigurationException, ServiceErrorException {

		// Request
		ModifySolutionOfferPartyGroupRequest r = new ModifySolutionOfferPartyGroupRequest();
		PartyGroupOperationListRequest groupRequest = new PartyGroupOperationListRequest();
		for (PartyGroupBean item : partyGroupIds) {
			PartyGroupOperationInfoRequest groupInfo = new PartyGroupOperationInfoRequest();
			groupInfo.setOperation(item.getOperation());
			groupInfo.setPartyGroupId(item.getPartyGroupId());
			groupRequest.getPartyGroup().add(groupInfo);
		}
		r.setSolutionOfferId(solutionOfferId);
		r.setPartyGroups(groupRequest);
		// webmethod
		ModifySolutionOfferPartyGroup req = new ModifySolutionOfferPartyGroup();
		req.setModifySolutionOfferPartyGroupRequest(r);
		ModifySolutionOfferPartyGroupResponse result = port.modifySolutionOfferPartyGroup(req, tenantName);
		BaseResp resp = result.getModifySolutionOfferResponse();
		parseResponse(resp);
	}

	public BaseResp isSolutionOfferSubscribed(Long solutionOfferId, Holder<String> tenantName) {
		SearchSolutionOfferRequest r = new SearchSolutionOfferRequest();
		r.setSolutionOfferId(solutionOfferId);
		IsSolutionOfferSubscribed req = new IsSolutionOfferSubscribed();
		req.setIsSolutionOfferSubscribedRequest(r);
		IsSolutionOfferSubscribedResponse result = port.isSolutionOfferSubscribed(req, tenantName);
		return result.getIsSolutionOfferSubscribedResponse();
	}

	public SearchSolutionOfferResp searchSolutionOffer(Long solutionOfferId, Holder<String> tenantName) throws ServiceErrorException {
		SearchSolutionOfferRequest r = new SearchSolutionOfferRequest();
		r.setSolutionOfferId(solutionOfferId);
		CSearchSolutionOffer req = new CSearchSolutionOffer();
		req.setSearchCredentialsRequest(r);
		SearchSolutionOfferResp resp = port.cSearchSolutionOffer(req, tenantName).getSearchSolutionOfferResponse();
		parseResponse(resp);
		return resp;
	}
	
	
	public SearchAllSolutionOfferTypesResp searchSolutionOfferTypes(Holder<String> tenantName) throws ServiceErrorException {
		SearchAllSolutionOfferTypes r = new SearchAllSolutionOfferTypes();
		SearchAllSolutionOfferTypesResp resp = port.searchAllSolutionOfferTypes(r, tenantName).getSearchAllSolutionOfferTypesResponse();
		parseResponse(resp);
		return resp;
	}
	
	
	

	public SolutionOfferService() {
		URL url = null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.SOLUTION_OFFER_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpSolutionOfferService_Service service = new SdpSolutionOfferService_Service(url);
		port = service.getSdpSolutionOfferServicePort();
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
