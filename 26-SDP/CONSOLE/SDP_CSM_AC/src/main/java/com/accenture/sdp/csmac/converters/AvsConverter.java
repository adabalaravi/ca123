package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.avs.AvsCountryLangCodeBean;
import com.accenture.sdp.csmac.beans.avs.AvsDeviceIdTypeBean;
import com.accenture.sdp.csmac.beans.avs.AvsPaymentTypeBean;
import com.accenture.sdp.csmac.beans.avs.AvsPcExtendedRatingBean;
import com.accenture.sdp.csmac.beans.avs.AvsPcLevelBean;
import com.accenture.sdp.csmac.beans.avs.AvsPlatformBean;
import com.accenture.sdp.csmac.beans.avs.AvsRetailerDomainBean;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsCountryLangCodeInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsDeviceIdTypeInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsPaymentTypeInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsPcExtendedRatingInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsPcLevelInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsPlatformInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsRetailerDomainInfoResp;

public abstract class AvsConverter {

	public static List<AvsCountryLangCodeBean> convertAvsCountryLangCodes(List<AvsCountryLangCodeInfoResp> inputs) {
		List<AvsCountryLangCodeBean> result = new ArrayList<AvsCountryLangCodeBean>();
		for (AvsCountryLangCodeInfoResp i : inputs) {
			AvsCountryLangCodeBean bean = new AvsCountryLangCodeBean();
			bean.setCountry(i.getCountry());
			bean.setCountryCode(i.getCountryCode());
			result.add(bean);
		}
		return result;
	}

	public static List<AvsDeviceIdTypeBean> convertAvsDeviceTypes(List<AvsDeviceIdTypeInfoResp> inputs) {
		List<AvsDeviceIdTypeBean> result = new ArrayList<AvsDeviceIdTypeBean>();
		for (AvsDeviceIdTypeInfoResp i : inputs) {
			AvsDeviceIdTypeBean bean = new AvsDeviceIdTypeBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(i.getCreationDate()));
			bean.setTypeDescription(i.getTypeDescription());
			bean.setTypeId(i.getTypeId());
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(i.getUpdateDate()));
			bean.setValue(i.getValue());
			result.add(bean);
		}
		return result;
	}

	public static List<AvsPaymentTypeBean> convertAvsPaymentTypes(List<AvsPaymentTypeInfoResp> inputs) {
		List<AvsPaymentTypeBean> result = new ArrayList<AvsPaymentTypeBean>();
		for (AvsPaymentTypeInfoResp i : inputs) {
			AvsPaymentTypeBean bean = new AvsPaymentTypeBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(i.getCreationDate()));
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(i.getUpdateDate()));
			bean.setPaymentMethod(i.getPaymentMethod());
			bean.setPaymentTypeId(i.getPaymentTypeId());
			result.add(bean);
		}
		return result;
	}

	public static List<AvsPcLevelBean> convertAvsPcLevels(List<AvsPcLevelInfoResp> inputs) {
		List<AvsPcLevelBean> result = new ArrayList<AvsPcLevelBean>();
		for (AvsPcLevelInfoResp i : inputs) {
			AvsPcLevelBean bean = new AvsPcLevelBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(i.getCreationDate()));
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(i.getUpdateDate()));
			bean.setDescription(i.getDescription());
			bean.setValue(i.getValue());
			result.add(bean);
		}
		return result;
	}

	public static List<AvsPcExtendedRatingBean> convertAvsPcExtendedRating(List<AvsPcExtendedRatingInfoResp> inputs) {
		List<AvsPcExtendedRatingBean> result = new ArrayList<AvsPcExtendedRatingBean>();
		for (AvsPcExtendedRatingInfoResp i : inputs) {
			AvsPcExtendedRatingBean bean = new AvsPcExtendedRatingBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(i.getCreationDate()));
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(i.getUpdateDate()));
			bean.setDescription(i.getPcDescription());
			bean.setValue(i.getPcValue());
			bean.setId(i.getPcId());
			result.add(bean);
		}
		return result;
	}

	public static List<AvsPlatformBean> convertAvsPlatforms(List<AvsPlatformInfoResp> inputs) {
		List<AvsPlatformBean> result = new ArrayList<AvsPlatformBean>();
		for (AvsPlatformInfoResp i : inputs) {
			AvsPlatformBean bean = new AvsPlatformBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(i.getCreationDate()));
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(i.getUpdateDate()));
			bean.setPlatformId(i.getPlatformId());
			bean.setPlatformName(i.getPlatformName());
			bean.setDeviceTypes(convertAvsDeviceTypes(i.getDeviceIdTypes().getDeviceIdType()));
			result.add(bean);
		}
		return result;
	}
	
	public static List<AvsRetailerDomainBean> convertAvsRetailerDomains(List<AvsRetailerDomainInfoResp> inputs) {
		List<AvsRetailerDomainBean> result = new ArrayList<AvsRetailerDomainBean>();
		for (AvsRetailerDomainInfoResp i : inputs) {
			AvsRetailerDomainBean bean = new AvsRetailerDomainBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(i.getCreationDate()));
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(i.getUpdateDate()));
			bean.setDescription(i.getDescription());
			bean.setHostDomain(i.getHostDomain());
			bean.setRetailerId(i.getRetailerId());
			result.add(bean);
		}
		return result;
	}

}
