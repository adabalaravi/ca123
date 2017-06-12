/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.avs.clients.packageingestor.FlagTypeYN;
import com.accenture.sdp.csmfe.webservices.avs.clients.packageingestor.PackageIngestorPort;
import com.accenture.sdp.csmfe.webservices.avs.clients.packageingestor.PackageIngestorRequest;
import com.accenture.sdp.csmfe.webservices.avs.clients.packageingestor.PackageIngestorResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.packageingestor.PackageIngestorService;

@ManagedBean(name = ApplicationConstants.PACKAGE_INGESTOR_SERVICE_BEAN_NAME)
@ApplicationScoped
public class PackageIngestorAVSService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 252244519771694763L;

	public static final String ACN_200 = "ACN_200"; 
	// OK
    public static final String ACN_300 = "ACN_300"; 
    // 300-GENERIC ERROR
    public static final String ACN_3061 = "ACN_3061"; 
    // DB Error
    public static final String ACN_3066 = "ACN_3066"; 
    // CRM Account doesn't exist
    public static final String ACN_3064 = "ACN_3064"; 
    // Request Not Allowed
    public static final String ACN_3065 = "ACN_3065"; 
    // ACN_3065

	private transient PackageIngestorPort port;
	
	
	public PackageIngestorResponse invokePackageIngestorService(Long externalOfferId,String packageName,String packageDescription, String packageType, String isEnabled, 
			Date validityStartDate, Date validityEndDate, Long validityPeriod, Long viewNumber,	Holder<String> tenantName) throws ServiceErrorException, DatatypeConfigurationException{
		
		LoggerWrapper log = new LoggerWrapper(PackageIngestorAVSService.class);
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put(MessageConstants.PACKAGE_INGESTOR_EXTERNAL_OFFER_ID, externalOfferId);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_PACKAGE_NAME, packageName);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_PACKAGE_DESCRIPTION, packageDescription);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_PACKAGE_TYPE, packageType);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_IS_ENABLE, isEnabled);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_START_DATE, validityStartDate);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_END_DATE, validityEndDate);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_PERIOD, validityPeriod);
		logMap.put(MessageConstants.PACKAGE_INGESTOR_VIEW_NUMBER, viewNumber);
		logMap.put(MessageConstants.TENANT_NAME, tenantName.value);
		log.logStartFeature(Utilities.getCurrentMethodName(), logMap);
		PackageIngestorRequest req = new PackageIngestorRequest();
		req.setExternalOfferId(externalOfferId);
		req.setPackageName(packageName);
		req.setPackageDescription(packageDescription);
		req.setPackageType(packageType);
		if (ApplicationConstants.YES_VALUE.equals(isEnabled)){
			req.setIsEnabled(FlagTypeYN.Y);		
		} else {
			req.setIsEnabled(FlagTypeYN.N);	
		}
		req.setValidityStartDate(Utilities.getXMLGregorianCalendar(validityStartDate));
		req.setValidityEndDate(Utilities.getXMLGregorianCalendar(validityEndDate));
		req.setValidityPeriod(validityPeriod);
		req.setViewNumber(viewNumber);
		req.setTenantName(tenantName.value);
		PackageIngestorResponse resp = port.packageIngestorService(req);
		parseResponse(resp);
		log.logEndFeature(Utilities.getCurrentMethodName(), resp.getResultCode(), resp.getResultDescription());
		return resp;
	}

	
	public PackageIngestorAVSService() {
		URL url=null;
		LoggerWrapper log = new LoggerWrapper(PackageIngestorAVSService.class);
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.AVS_ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.PACKAGE_INGESTOR_AVS_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		PackageIngestorService service=new PackageIngestorService(url);
		port = service.getPackageIngestorPortImplPort();
		Utilities.addSoapHandler((BindingProvider)port, urlString);
		
		
	}


	private void parseResponse(PackageIngestorResponse resp) throws ServiceErrorException{
		if (!ACN_200.equals(resp.getResultCode())){
			throw new ServiceErrorException(resp.getResultCode(), Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "code."+ resp.getResultCode() + ".description"));
		}
	}

}
