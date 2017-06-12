/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.ExternalIdBean;
import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.common.beans.Param;
import com.accenture.sdp.csmcc.common.constants.AvsConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchDiscountSolutionOfferRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ExternalIdInfo;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.PartyGroupInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOfferTypesResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferAndSolutionRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferComplexInfoResp;

public final class SolutionOfferConverter {

	private SolutionOfferConverter() {

	}

	public static List<SolutionOfferBean> buildSolutionOfferTable(SearchSolutionOfferAndSolutionRespPaginated response) {
		List<SolutionOfferComplexInfoResp> infos = response.getSolutionOffers().getSolutionOffer();
		List<SolutionOfferBean> beanList = new ArrayList<SolutionOfferBean>();
		for (SolutionOfferComplexInfoResp info : infos) {
			beanList.add(convertSolutionOfferInfoToBean(info));
		}

		return beanList;
	}

	public static List<SolutionOfferBean> buildSolutionOfferTable(SearchSolutionOfferComplexRespPaginated response) {
		List<SolutionOfferComplexInfoResp> infos = response.getSolutionOffers().getSolutionOffer();
		List<SolutionOfferBean> beanList = new ArrayList<SolutionOfferBean>();
		for (SolutionOfferComplexInfoResp info : infos) {
			beanList.add(convertSolutionOfferInfoToBean(info));
		}
		return beanList;
	}

	public static List<SolutionOfferBean> buildSolutionOfferTable(SearchDiscountSolutionOfferRespPaginated response) {
		List<com.accenture.sdp.csmfe.webservices.clients.avs.SolutionOfferComplexInfoResp> infos = response.getSolutionOffers().getSolutionOffer();
		List<SolutionOfferBean> beanList = new ArrayList<SolutionOfferBean>();
		for (com.accenture.sdp.csmfe.webservices.clients.avs.SolutionOfferComplexInfoResp info : infos) {
			beanList.add(convertSolutionOfferInfoToBean(info));
		}

		return beanList;
	}
	
	
	public static List<String> buildSolutionOfferTypes(SearchAllSolutionOfferTypesResp response) {
		List<String> infos = response.getSolutionOfferType();		
		return infos;
	}
	
	
	
	
	

	public static SolutionOfferBean convertSolutionOfferInfoToBean(SolutionOfferComplexInfoResp info) {
		SolutionOfferBean bean = new SolutionOfferBean();
		bean.setSolutionOfferId(info.getSolutionOfferId());
		bean.setSolutionOfferName(info.getSolutionOfferName());
		bean.setSolutionOfferDesc(info.getSolutionOfferDescription());
		bean.setParentSolutionOfferId(info.getParentSolutionOfferId());
		bean.setSolutionId(info.getSolutionId());
		bean.setSolutionName(info.getSolutionName());
		bean.setSolutionOfferDuration(info.getDuration());
		bean.setSolutionOfferType(info.getSolutionOfferType());

		bean.setSolutionOfferProfile(info.getProfile());
		bean.setSolutionOfferStatus(info.getStatusName());
		if (info.getStartDate() != null) {
			bean.setSolutionOfferStartDate(info.getStartDate().toGregorianCalendar().getTime());
		}
		if (info.getEndDate() != null) {
			bean.setSolutionOfferEndDate(info.getEndDate().toGregorianCalendar().getTime());
		}
		for (PartyGroupInfoResp groupInfoResp : info.getPartyGroups().getPartyGroup()) {
			bean.getPartyGroupNames().add(groupInfoResp.getPartyGroupName());
		}
		if (info.getCreatedDate() != null) {
			bean.setSolutionOfferCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		}

		List<ExternalIdBean> externalIds = new ArrayList<ExternalIdBean>();
		for (ExternalIdInfo idInfo : info.getExternalIds().getExternalId()) {
			ExternalIdBean extIdbean = new ExternalIdBean();
			extIdbean.setExternalId(idInfo.getExternalId());
			extIdbean.setExternalPlatform(idInfo.getExternalPlatformName());

			externalIds.add(extIdbean);
		}
		bean.setExternalId(externalIds);

		setProfileFields(bean);
		if (info.getExternalIds() != null) {
			bean.setExternalId(convertExternalIds(info.getExternalIds().getExternalId()));
		}
		return bean;
	}

	public static SolutionOfferBean convertSolutionOfferInfoToBean(com.accenture.sdp.csmfe.webservices.clients.avs.SolutionOfferComplexInfoResp info) {
		SolutionOfferBean bean = new SolutionOfferBean();
		bean.setSolutionOfferId(info.getSolutionOfferId());
		bean.setSolutionOfferName(info.getSolutionOfferName());
		bean.setSolutionOfferDesc(info.getSolutionOfferDescription());
		bean.setParentSolutionOfferId(info.getParentSolutionOfferId());
		bean.setSolutionId(info.getSolutionId());
		bean.setSolutionName(info.getSolutionName());
		bean.setSolutionOfferProfile(info.getProfile());
		bean.setSolutionOfferStatus(info.getStatusName());
		bean.setSolutionOfferDuration(info.getDuration());
		bean.setSolutionOfferType(info.getSolutionOfferType());
		if (info.getStartDate() != null) {
			bean.setSolutionOfferStartDate(info.getStartDate().toGregorianCalendar().getTime());
		}
		if (info.getEndDate() != null) {
			bean.setSolutionOfferEndDate(info.getEndDate().toGregorianCalendar().getTime());
		}
		for (com.accenture.sdp.csmfe.webservices.clients.avs.PartyGroupInfoResp groupInfoResp : info.getPartyGroups().getPartyGroup()) {
			bean.getPartyGroupNames().add(groupInfoResp.getPartyGroupName());
		}
		if (info.getCreatedDate() != null) {
			bean.setSolutionOfferCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		}

		List<ExternalIdBean> externalIds = new ArrayList<ExternalIdBean>();
		for (com.accenture.sdp.csmfe.webservices.clients.avs.ExternalIdInfo idInfo : info.getExternalIds().getExternalId()) {
			ExternalIdBean extIdbean = new ExternalIdBean();
			extIdbean.setExternalId(idInfo.getExternalId());
			extIdbean.setExternalPlatform(idInfo.getExternalPlatformName());

			externalIds.add(extIdbean);
		}
		bean.setExternalId(externalIds);

		setProfileFields(bean);
		return bean;
	}

	public static void setProfileFields(SolutionOfferBean bean) {
		List<Param> params = Utilities.readProfile(bean.getSolutionOfferProfile());
		for (Param param : params) {
			if (AvsConstants.TYPE.equalsIgnoreCase(param.getName())) {
				bean.setType(param.getValue());
			} else if (AvsConstants.PRODUCT_TYPE.equalsIgnoreCase(param.getName())) {
				bean.setProductType(param.getValue());
			}
		}
	}

	public static String getProfileAsString(SolutionOfferBean bean) {
		String type = bean.getType();
		String productType = bean.getProductType();
		ArrayList<Param> params = new ArrayList<Param>();
		if (type != null) {
			params.add(new Param(AvsConstants.TYPE, type));
		}
		if (productType != null) {
			params.add(new Param(AvsConstants.PRODUCT_TYPE, productType));
		}
		return Utilities.writeProfile(params);
	}

	private static List<ExternalIdBean> convertExternalIds(List<ExternalIdInfo> infos) {
		List<ExternalIdBean> result = new ArrayList<ExternalIdBean>();
		if (infos != null) {
			for (ExternalIdInfo ext : infos) {
				ExternalIdBean bean = new ExternalIdBean();
				bean.setExternalPlatform(ext.getExternalPlatformName());
				bean.setExternalId(ext.getExternalId());
				result.add(bean);
			}
		}
		return result;
	}

}
