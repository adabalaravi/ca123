package com.accenture.sdp.csmac.business;

import java.util.List;
import java.util.Vector;

import com.accenture.sdp.csmac.beans.avs.AvsCountryLangCodeBean;
import com.accenture.sdp.csmac.beans.avs.AvsDeviceIdTypeBean;
import com.accenture.sdp.csmac.beans.avs.AvsPaymentTypeBean;
import com.accenture.sdp.csmac.beans.avs.AvsPcExtendedRatingBean;
import com.accenture.sdp.csmac.beans.avs.AvsPcLevelBean;
import com.accenture.sdp.csmac.beans.avs.AvsPlatformBean;
import com.accenture.sdp.csmac.beans.avs.AvsRetailerDomainBean;
import com.accenture.sdp.csmac.beans.device.DevicePolicyBean;
import com.accenture.sdp.csmac.beans.device.MaxNumberAllowedDevicesBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.AvsConverter;
import com.accenture.sdp.csmac.services.AvsService;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsCountryLangCodeResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsDeviceIdTypeResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPaymentTypeResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPcExtendedRatingResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPcLevelResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsPlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsRetailerDomainResp;

public final class AvsBusiness {

	private AvsBusiness() {
	}

	public static List<AvsCountryLangCodeBean> searchAllAvsCountryLangCodes() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsCountryLangCodeResp resp = s.searchAllAvsCountryLangCodes(Utilities.getTenantName());
		return AvsConverter.convertAvsCountryLangCodes(resp.getCountryLangCodes().getCountryLangCode());
	}

	public static List<AvsDeviceIdTypeBean> searchAllAvsDeviceIdTypes() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsDeviceIdTypeResp resp = s.searchAllAvsDeviceIdTypes(Utilities.getTenantName());
		return AvsConverter.convertAvsDeviceTypes(resp.getDeviceIdTypes().getDeviceIdType());
	}

	public static List<AvsPaymentTypeBean> searchAllAvsPaymentTypes() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsPaymentTypeResp resp = s.searchAllAvsPaymentTypes(Utilities.getTenantName());
		return AvsConverter.convertAvsPaymentTypes(resp.getPaymentTypes().getPaymentType());
	}

	public static List<AvsPcLevelBean> searchAllAvsPcLevels() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsPcLevelResp resp = s.searchAllAvsPcLevels(Utilities.getTenantName());
		return AvsConverter.convertAvsPcLevels(resp.getPcLevels().getPcLevel());
	}

	public static List<AvsPcExtendedRatingBean> searchAllAvsPcExtendedRatings() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsPcExtendedRatingResp resp = s.searchAllAvsPcExtendedRatings(Utilities.getTenantName());
		return AvsConverter.convertAvsPcExtendedRating(resp.getPcExtendedRatings().getPcExtendedRating());
	}

	public static List<AvsPlatformBean> searchAllAvsPlatforms() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsPlatformResp resp = s.searchAllAvsPlatforms(Utilities.getTenantName());
		return AvsConverter.convertAvsPlatforms(resp.getPlatforms().getPlatform());
	}

	public static List<AvsRetailerDomainBean> searchAllAvsRetailerDomains() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		SearchAvsRetailerDomainResp resp = s.searchAllAvsRetailerDomains(Utilities.getTenantName());
		return AvsConverter.convertAvsRetailerDomains(resp.getRetailerDomains().getRetailerDomain());
	}
	
	public static List<DevicePolicyBean> searchAllDevicePolicies() throws ServiceErrorException {
		AvsService s = Utilities.findBean(ApplicationConstants.AVS_SERVICE_BEAN_NAME, AvsService.class);
		List<DevicePolicyBean> list = new Vector<DevicePolicyBean>();
		for(int i = 0; i < 3 ; i++){
			DevicePolicyBean bean = new DevicePolicyBean();
			List<MaxNumberAllowedDevicesBean> listMaxNumber = new Vector<MaxNumberAllowedDevicesBean>();
			long maxNumberAllowedDevices = 0;
			MaxNumberAllowedDevicesBean beanMaxNumber = new MaxNumberAllowedDevicesBean();
			beanMaxNumber.setChannel("PCTV");
			beanMaxNumber.setNumber(4);
			maxNumberAllowedDevices += 4;
			MaxNumberAllowedDevicesBean beanMaxNumber1 = new MaxNumberAllowedDevicesBean();
			beanMaxNumber1.setChannel("iPad");
			beanMaxNumber1.setNumber(1);
			maxNumberAllowedDevices += 1;
			MaxNumberAllowedDevicesBean beanMaxNumber2 = new MaxNumberAllowedDevicesBean();
			beanMaxNumber2.setChannel("OTTSTB");
			beanMaxNumber2.setNumber(2);
			maxNumberAllowedDevices += 2;
			MaxNumberAllowedDevicesBean beanMaxNumber3 = new MaxNumberAllowedDevicesBean();
			beanMaxNumber3.setChannel("ANDROID");
			beanMaxNumber3.setNumber(5);
			maxNumberAllowedDevices += 5;
			MaxNumberAllowedDevicesBean beanMaxNumber4 = new MaxNumberAllowedDevicesBean();
			beanMaxNumber4.setChannel("CONNECTED");
			beanMaxNumber4.setNumber(10);
			maxNumberAllowedDevices += 10;
			MaxNumberAllowedDevicesBean beanMaxNumber5 = new MaxNumberAllowedDevicesBean();
			beanMaxNumber5.setChannel("XBOX");
			beanMaxNumber5.setNumber(2);
			maxNumberAllowedDevices += 2;
			listMaxNumber.add(beanMaxNumber);
			listMaxNumber.add(beanMaxNumber1);
			listMaxNumber.add(beanMaxNumber2);
			listMaxNumber.add(beanMaxNumber3);
			listMaxNumber.add(beanMaxNumber4);
			listMaxNumber.add(beanMaxNumber5);
			bean.setAllowedDevices(listMaxNumber);
			if(i==0)
				bean.setDevicePolicyName("Default");
			else if (i==1)
				bean.setDevicePolicyName("VIP");
			else if (i == 2)
				bean.setDevicePolicyName("Client");
			bean.setMaxNumberAllowedDevices(maxNumberAllowedDevices);
			bean.setMaxNumberOfAssociations(23);
			bean.setSafetyPeriodInDays(30);
			bean.setPolicyId(i);
			list.add(bean);
		}
		
		//SearchAvsRetailerDomainResp resp = s.searchAllAvsRetailerDomains(Utilities.getTenantName());
		return list;//AvsConverter.convertAvsRetailerDomains(resp.getRetailerDomains().getRetailerDomain());
	}
	
	

}
