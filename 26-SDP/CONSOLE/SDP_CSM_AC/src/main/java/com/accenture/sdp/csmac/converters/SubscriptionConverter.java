package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionDetailBean;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.subscription.OfferInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SubscriptionByPartyInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SubscriptionComplexInfoResp;

public abstract class SubscriptionConverter {

	public static List<SubscriptionBean> convertSubscriptionByPartyInfoToBeanList(List<SubscriptionByPartyInfoResp> infos) {
		List<SubscriptionBean> beanList = new ArrayList<SubscriptionBean>();
		for (SubscriptionByPartyInfoResp info : infos) {
			beanList.add(convertSubscriptionByPartyInfoToBean(info));
		}
		return beanList;
	}

	public static List<SubscriptionBean> convertSubscriptionsByParentInfoToBeanList(List<SubscriptionComplexInfoResp> infos) {
		List<SubscriptionBean> beanList = new ArrayList<SubscriptionBean>();
		for (SubscriptionComplexInfoResp info : infos) {
			beanList.add(convertSubscriptionByParentInfoToBean(info));
		}
		return beanList;
	}

	public static SubscriptionBean convertSubscriptionByPartyInfoToBean(SubscriptionByPartyInfoResp info) {
		SubscriptionBean bean = new SubscriptionBean();
		bean.setId(info.getSubscriptionId());

		bean.setPartyId(info.getPartyId());
		bean.setPartyName(info.getPartyName());
		bean.setSolutionOfferId(info.getSolutionOfferId());
		bean.setSolutionOfferName(info.getSolutionOfferName());
		bean.setStatusId(info.getStatusId());
		bean.setStatus(info.getStatusName());
		bean.setExternalId(info.getExternalId());

		bean.setOwnerAccountName(info.getOwnerAccountName());
		bean.setPayeeAccountName(info.getPayeeAccountName());
		bean.setUsername(info.getUserName());
		bean.setRoleName(info.getRoleName());
		bean.setSiteName(info.getSiteName());

		bean.setStartDate(Utilities.getDateFromGregorianCalendar(info.getStartDate()));
		bean.setEndDate(Utilities.getDateFromGregorianCalendar(info.getEndDate()));
		bean.setCreatedDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		return bean;
	}

	public static SubscriptionBean convertSubscriptionByParentInfoToBean(SubscriptionComplexInfoResp info) {
		SubscriptionBean bean = new SubscriptionBean();
		bean.setId(info.getSubscriptionId());

		bean.setPartyId(info.getPartyId());
		bean.setPartyName(info.getPartyName());
		bean.setSolutionOfferId(info.getSolutionOfferId());
		bean.setSolutionOfferName(info.getSolutionOfferName());
		bean.setStatusId(info.getStatusId());
		bean.setStatus(info.getStatusName());
		bean.setExternalId(info.getExternalId());

		bean.setOwnerAccountName(info.getOwnerAccountName());
		bean.setPayeeAccountName(info.getPayeeAccountName());
		bean.setUsername(info.getUserName());
		bean.setRoleName(info.getRoleName());
		bean.setSiteName(info.getSiteName());

		bean.setStartDate(Utilities.getDateFromGregorianCalendar(info.getStartDate()));
		bean.setEndDate(Utilities.getDateFromGregorianCalendar(info.getEndDate()));
		bean.setCreatedDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		return bean;
	}

	public static List<SubscriptionDetailBean> convertSubscriptionDetails(List<OfferInfoResp> infos) {
		if (infos == null) {
			return null;
		}
		List<SubscriptionDetailBean> beanList = new ArrayList<SubscriptionDetailBean>();
		for (OfferInfoResp info : infos) {
			beanList.add(convertSubscriptionDetail(info));
		}
		return beanList;
	}

	public static SubscriptionDetailBean convertSubscriptionDetail(OfferInfoResp info) {
		SubscriptionDetailBean bean = new SubscriptionDetailBean();
		bean.setExternalId(info.getExternalId());
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatusName());
		bean.setPackageId(info.getPackageId());
		bean.setProfile(info.getSubscriptionOfferProfile());
		bean.setOfferId(info.getOfferId());
		bean.setOfferName(info.getOfferName());
		return bean;
	}

}
