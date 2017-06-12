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
import com.accenture.sdp.csmfe.webservices.clients.packages.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.packages.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.packages.SdpPackageService;
import com.accenture.sdp.csmfe.webservices.clients.packages.SdpPackageService_Service;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackage;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRequest;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRespPaginated;

@ManagedBean(name = ApplicationConstants.PACKAGE_SERVICE_BEAN)
@ApplicationScoped
public class PackageService {

	private static LoggerWrapper log = new LoggerWrapper(PackageService.class);

	private SdpPackageService port;

	public PackageService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.PACKAGE_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpPackageService_Service service = new SdpPackageService_Service(url);
			port = service.getSdpPackageServicePort();
			log.logDebug("Package Service instantiated.Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchPackageComplexRespPaginated searchPackagesBySolutionOffer(String solutionOfferName, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchPackage wrapper = new SearchPackage();
		SearchPackageComplexRequest req = new SearchPackageComplexRequest();
		req.setSolutionOfferName(solutionOfferName);
		req.setMaxRecordsNumber(0L);
		req.setStartPosition(0L);
		wrapper.setSearchPackageRequest(req);
		SearchPackageComplexRespPaginated resp = port.searchPackage(wrapper, tenantName).getSearchPackageResponse();
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
