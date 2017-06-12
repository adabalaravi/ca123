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
import com.accenture.sdp.csmfe.webservices.clients.packageprice.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.CCreatePackagePrice;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.CCreatePackagePriceResponse;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.CDeletePackagePrice;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.CDeletePackagePriceResponse;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.CreatePackagePriceRequest;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.CreatePackagePriceResp;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.DeletePackagePriceRequest;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.SdpPackagePriceService;
import com.accenture.sdp.csmfe.webservices.clients.packageprice.SdpPackagePriceService_Service;



@ManagedBean(name = ApplicationConstants.PACKAGE_PRICE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class PackagePriceService implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private transient SdpPackagePriceService port;

	
	public void deletePackagePrice(Long packagePriceId, Holder<String> tenantName) throws ServiceErrorException{
		DeletePackagePriceRequest req = new DeletePackagePriceRequest();
		req.setPackagePriceId(packagePriceId);
		CDeletePackagePrice request = new CDeletePackagePrice();
		request.setDeletePackagePriceRequest(req);
		CDeletePackagePriceResponse result = port.cDeletePackagePrice(request, tenantName);
		BaseResp resp = result.getDeletePackagePriceResponse();
		parseResponse(resp);
	}
	
	public Long createPackagePrice(
			Long currencyTypeId,  
			Long nrcPriceCatalogId, 
			Long rcPriceCatalogId,
			Long rcFrequencyTypeId,
			String rcFlagProrate,
			String rcInAdvance, Holder<String> tenantName) throws ServiceErrorException{

		CreatePackagePriceRequest r=new CreatePackagePriceRequest();
		r.setCurrencyTypeId(currencyTypeId);
		r.setNrcPriceCatalogId(nrcPriceCatalogId);
		r.setRcPriceCatalogId(rcPriceCatalogId);
		r.setRcFrequencyTypeId(rcFrequencyTypeId);
		r.setRcFlagProrate(rcFlagProrate);
		r.setRcInAdvance(rcInAdvance);
		CCreatePackagePrice req = new CCreatePackagePrice();
		req.setCreatePackagePriceRequest(r);
		CCreatePackagePriceResponse result = port.cCreatePackagePrice(req, tenantName);
		CreatePackagePriceResp resp = result.getCreatePackagePriceResponse();
		parseResponse(resp);
		return resp.getPackagePriceId();
	}

	public PackagePriceService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.PACKAGE_PRICE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpPackagePriceService_Service service = new SdpPackagePriceService_Service(url);
		port = service.getSdpPackagePriceServicePort();

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
