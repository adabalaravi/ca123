package com.accenture.sdp.csmac.services;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;

@ManagedBean(name = ApplicationConstants.DEVICE_ACCESS_SERVICE_BEAN)
@ApplicationScoped
public class DeviceAccessService {

	
	public DeviceAccessService() {
	}
	
//	private static LoggerWrapper log = new LoggerWrapper(PartyGroupService.class);
//	private SdpDeviceAccessService port;
//	
//	public DeviceAccessService() {		
//		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
//		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
//				+ propertyManager.getProperty(ApplicationConstants.DEVICE_ACCES_WSDL_URL);
//		try {
//			URL url = new URL(urlString);
//			SdpDeviceAccessService_Service service = new SdpDeviceAccessService_Service(url);
//			port = service.getSdpDeviceAccessServicePort();
//			log.logDebug("SdpPartyGroupService instantiated. Endpoint Url: %s", url);
//		} catch (MalformedURLException e) {
//			log.logError(e);
//		}
//	}
//	
//	
//	
//	
//	
//	public SearchAllDeviceChannelsResp searchAllDevicesChannel() throws ServiceErrorException {
//		log.logDebug(Utilities.getCurrentClassAndMethod());
//		BaseRequestPaginated req = new BaseRequestPaginated();
//		SearchAllDeviceChannels wrapper = new SearchAllDeviceChannels();
//		wrapper.getSearchAllDeviceChannelsRequest();
////		SearchAllDeviceChannelsResp resp = port.
////				searchAllPartyGroup(wrapper, tenantName).getSearchAllPartyGroupsResponse();
//////		log.logDebug("searchAllPartyGroups " + resp.getResultCode() + ", " + resp.getDescription() + " - result:" + resp.getTotalResult());
//////		parseResponse(resp);
//		return null;
//	}
	
}
