package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.packages.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.packages.CCreatePackage;
import com.accenture.sdp.csmfe.webservices.clients.packages.CCreatePackageResponse;
import com.accenture.sdp.csmfe.webservices.clients.packages.CDeletePackage;
import com.accenture.sdp.csmfe.webservices.clients.packages.CDeletePackageResponse;
import com.accenture.sdp.csmfe.webservices.clients.packages.CModifyPackage;
import com.accenture.sdp.csmfe.webservices.clients.packages.CModifyPackageResponse;
import com.accenture.sdp.csmfe.webservices.clients.packages.CreatePackageRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.CreatePackageResp;
import com.accenture.sdp.csmfe.webservices.clients.packages.DeletePackageRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.IsPackageSubscribed;
import com.accenture.sdp.csmfe.webservices.clients.packages.IsPackageSubscribedResponse;
import com.accenture.sdp.csmfe.webservices.clients.packages.ModifyPackageRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.PackageChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.packages.PackageChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.PackageChangeStatusResponse;
import com.accenture.sdp.csmfe.webservices.clients.packages.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.packages.SdpPackageService;
import com.accenture.sdp.csmfe.webservices.clients.packages.SdpPackageService_Service;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackage;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageResponse;


@ManagedBean(name = ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class PackageService implements Serializable{

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private transient SdpPackageService port;


	public void deleteOfferDetail(Long packageId, Holder<String> tenantName) throws ServiceErrorException{
		DeletePackageRequest req = new DeletePackageRequest();
		req.setPackageId(packageId);
		CDeletePackage request = new CDeletePackage();
		request.setDeletePackageRequest(req);
		CDeletePackageResponse result = port.cDeletePackage(request, tenantName);
		BaseResp resp = result.getDeletePackageResponse();
		parseResponse(resp);
	}

	public Long createPackage(
			Long basePackageId, 
			String externalId,
			Long groupId,
			String mandatory,
			Long offerId,
			String profile,
			Long solutionOfferId,

			Long currencyTypeId,  
			Long nrcPriceCatalogId, 
			Long rcPriceCatalogId,
			Long rcFrequencyTypeId,
			String rcFlagProrate,
			String rcInAdvance,
			Holder<String> tenantName) throws ServiceErrorException{
		CreatePackageRequest r=new CreatePackageRequest();
		PackagePriceService packagePriceService = Utilities.findBean(ApplicationConstants.PACKAGE_PRICE_SERVICE_BEAN_NAME, PackagePriceService.class);

		// CREATE PACKAGE PRICE
		Long packagePriceId = packagePriceService.createPackagePrice(currencyTypeId, nrcPriceCatalogId, rcPriceCatalogId, rcFrequencyTypeId, rcFlagProrate, rcInAdvance, tenantName);
		r.setPackagePriceId(packagePriceId);	

		if (basePackageId != null && basePackageId.longValue() > 0){
			r.setBasePackageId(basePackageId);
		}
		r.setExternalId(externalId);
		if (groupId != null && groupId.longValue() > 0){
			r.setGroupId(groupId);
		}
		r.setIsMandatory(mandatory);
		r.setOfferId(offerId);
		r.setProfile(profile);
		r.setSolutionOfferId(solutionOfferId);
		CCreatePackage req = new CCreatePackage();
		req.setCreatePackageRequest(r);
		CCreatePackageResponse result = port.cCreatePackage(req, tenantName);
		CreatePackageResp resp = result.getCreatePackageResponse();
		parseResponse(resp);
		return resp.getPackageId();
	}


	public void updatePackage(
			Long packageId,
			Long basePackageId, 
			String externalId,
			Long groupId,
			String mandatory,
			Long offerId,
			String profile,
			Long solutionOfferId,
			Long currencyTypeId,  
			Long nrcPriceCatalogId, 
			Long rcPriceCatalogId,
			Long rcFrequencyTypeId,
			String rcFlagProrate,
			String rcInAdvance, 
			Holder<String> tenantName) throws ServiceErrorException{
		ModifyPackageRequest req = new ModifyPackageRequest();
		PackagePriceService packagePriceService = Utilities.findBean(ApplicationConstants.PACKAGE_PRICE_SERVICE_BEAN_NAME, PackagePriceService.class);

		Long packagePriceId = packagePriceService.createPackagePrice(currencyTypeId, nrcPriceCatalogId, rcPriceCatalogId, rcFrequencyTypeId, rcFlagProrate, rcInAdvance, tenantName);
		req.setPackagePriceId(packagePriceId);	

		req.setPackageId(packageId);

		if (basePackageId != null && basePackageId.longValue() > 0){
			req.setBasePackageId(basePackageId);
		}
		if (groupId != null && groupId.longValue() > 0){
			req.setGroupId(groupId);
		}
		req.setExternalId(externalId);
		req.setIsMandatory(mandatory);
		req.setOfferId(offerId);
		req.setProfile(profile);
		req.setSolutionOfferId(solutionOfferId);
		CModifyPackage request = new CModifyPackage();
		request.setModifyPackageRequest(req);
		CModifyPackageResponse result = port.cModifyPackage(request, tenantName);
		BaseResp resp = result.getModifyPackageResponse();
		parseResponse(resp);
	}


	public void packageChangeStatus(long packageId, String status, Holder<String> tenantName) throws ServiceErrorException{
		PackageChangeStatusRequest req = new PackageChangeStatusRequest();
		req.setPackageId(packageId);
		req.setStatus(status);
		PackageChangeStatus request = new PackageChangeStatus();
		request.setPackageChangeStatusRequest(req);
		PackageChangeStatusResponse result  = port.packageChangeStatus(request, tenantName);
		BaseResp resp = result.getPackageChangeStatusResponse();
		parseResponse(resp);
	}

	public SearchPackageComplexRespPaginated searchPackageByOfferAndSolutionOffer(String offerName, String solutionOfferName, Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		SearchPackageComplexRequest req = new SearchPackageComplexRequest();
		if (offerName != null) {
			req.setOfferName(offerName);
		}
		req.setSolutionOfferName(solutionOfferName);
		req.setMaxRecordsNumber(maxRecordsNumber);
		req.setStartPosition(startPosition);
		SearchPackage request = new SearchPackage();
		request.setSearchPackageRequest(req);
		SearchPackageResponse result  = port.searchPackage(request, tenantName);
		SearchPackageComplexRespPaginated resp = result.getSearchPackageResponse();
		parseResponse(resp);
		return resp;
	}

	public BaseResp isPackageSubscribed(Long packageId, Holder<String> tenantName) {
		SearchPackageRequest r = new SearchPackageRequest();
		r.setPackageId(packageId);
		IsPackageSubscribed req = new IsPackageSubscribed();
		req.setIsPackageSubscribedRequest(r);
		IsPackageSubscribedResponse result = port.isPackageSubscribed(req, tenantName);
		return result.getIsPackageSubscribedResponse();
	}

	public PackageService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(PackageService.class);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.PACKAGE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpPackageService_Service service = new SdpPackageService_Service(url);
		port = service.getSdpPackageServicePort();
	}


	private void parseResponse(BaseResp resp) throws ServiceErrorException{
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())){
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters()!=null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()){
					parameters.add(new String[]{param.getName(), param.getValue()});
				}
			}
			throw new ServiceErrorException(resp.getResultCode(),
					Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}
}
