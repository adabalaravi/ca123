package com.accenture.sdp.csmac.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.PropertyManager;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsService_Service;
import com.accenture.sdp.csmfe.webservices.clients.avs.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsCountryLangCodes;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsDeviceIdTypes;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsPaymentTypes;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsPcExtendedRatings;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsPcLevels;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsPlatforms;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllAvsRetailerDomains;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsCountryLangCodeResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsDeviceIdTypeResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPaymentTypeResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPcExtendedRatingResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPcLevelResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsRetailerDomainResp;

@ManagedBean(name = ApplicationConstants.AVS_SERVICE_BEAN_NAME)
@ApplicationScoped
public class AvsService {

	private static LoggerWrapper log = new LoggerWrapper(AvsService.class);

	private com.accenture.sdp.csmfe.webservices.clients.avs.AvsService port;

	public AvsService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SDP_AVS_WSDL_URL);
		try {
			URL url = new URL(urlString);
			AvsService_Service service = new AvsService_Service(url);
			port = service.getAvsServicePort();
			log.logDebug("AVS Service instantiated.Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchAvsCountryLangCodeResp searchAllAvsCountryLangCodes(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsCountryLangCodeResp resp = port.searchAllAvsCountryLangCodes(new SearchAllAvsCountryLangCodes(), tenantName)
				.getSearchAllAvsCountryLangCodesResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchAvsDeviceIdTypeResp searchAllAvsDeviceIdTypes(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsDeviceIdTypeResp resp = port.searchAllAvsDeviceIdTypes(new SearchAllAvsDeviceIdTypes(), tenantName).getSearchAllAvsDeviceIdTypesResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchAvsPaymentTypeResp searchAllAvsPaymentTypes(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsPaymentTypeResp resp = port.searchAllAvsPaymentTypes(new SearchAllAvsPaymentTypes(), tenantName).getSearchAllAvsPaymentTypesResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchAvsPcLevelResp searchAllAvsPcLevels(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsPcLevelResp resp = port.searchAllAvsPcLevels(new SearchAllAvsPcLevels(), tenantName).getSearchAllAvsPcLevelsResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchAvsPcExtendedRatingResp searchAllAvsPcExtendedRatings(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsPcExtendedRatingResp resp = port.searchAllAvsPcExtendedRatings(new SearchAllAvsPcExtendedRatings(), tenantName)
				.getSearchAllAvsPcExtendedRatingsResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchAvsPlatformResp searchAllAvsPlatforms(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsPlatformResp resp = port.searchAllAvsPlatforms(new SearchAllAvsPlatforms(), tenantName).getSearchAllAvsPlatformsResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchAvsRetailerDomainResp searchAllAvsRetailerDomains(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchAvsRetailerDomainResp resp = port.searchAllAvsRetailerDomains(new SearchAllAvsRetailerDomains(), tenantName)
				.getSearchAllAvsRetailerDomainsResponse();
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
