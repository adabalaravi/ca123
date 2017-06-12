/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.DigitalGoodBean;
import com.accenture.sdp.csmcc.beans.OfferBean;
import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.beans.ServiceVariantBean;
import com.accenture.sdp.csmcc.common.beans.Param;
import com.accenture.sdp.csmcc.common.constants.AvsConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsDigitalGoodInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.OfferComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.OfferInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferResp;

public final class OfferConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	private OfferConverter() {

	}

	public static List<OfferBean> buildOfferTable(SearchOfferComplexRespPaginated response) {
		List<OfferComplexInfoResp> infos = response.getOffers().getOffer();
		List<OfferBean> beanList = new ArrayList<OfferBean>();
		for (OfferComplexInfoResp info : infos) {
			beanList.add(convertOfferInfoToBean(info));
		}

		return beanList;
	}

	public static OfferBean convertOfferInfoToBean(OfferComplexInfoResp info) {
		OfferBean bean = new OfferBean();
		bean.setOfferDesc(info.getOfferDescription());
		bean.setOfferExtId(info.getExternalId());
		bean.setOfferId(info.getOfferId());
		bean.setOfferName(info.getOfferName());
		bean.setOfferProfile(info.getOfferProfile());

		bean.setPlatformName(info.getPlatformName());

		if (info.getPlatformId() != null) {
			PlatformBean parentPlatformBean = new PlatformBean();
			parentPlatformBean.setPlatformId(info.getPlatformId());
			parentPlatformBean.setPlatformName(info.getPlatformName());
			bean.setParentPlatform(parentPlatformBean);
		}

		bean.setServiceTemplateName(info.getServiceTemplateName());
		if (info.getServiceTemplateId() != null) {
			ServiceTemplateBean parentServiceTemplate = new ServiceTemplateBean();
			parentServiceTemplate.setServiceTemplateId(info.getServiceTemplateId());
			parentServiceTemplate.setServiceTemplateName(info.getServiceTemplateName());
			bean.setParentServiceTemplate(parentServiceTemplate);
		}

		bean.setServiceVariantName(info.getServiceVariantName());
		if (info.getServiceVariantId() != null) {
			ServiceVariantBean parent = new ServiceVariantBean();
			parent.setServiceVariantId(info.getServiceVariantId());
			parent.setServiceVariantName(info.getServiceVariantName());
			bean.setParentServiceVariant(parent);
		}

		bean.setOfferStatus(info.getStatusName());
		if (info.getCreatedDate() != null) {
			bean.setOfferCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		}
		setProfileFields(bean);
		return bean;
	}

	public static List<OfferBean> buildOfferTable(SearchOfferResp response) {
		List<OfferInfoResp> infos = response.getOffers().getOffer();
		List<OfferBean> beanList = new ArrayList<OfferBean>();
		for (OfferInfoResp info : infos) {
			beanList.add(convertOfferInfoToBean(info));
		}

		return beanList;
	}

	public static OfferBean convertOfferInfoToBean(OfferInfoResp info) {
		OfferBean bean = new OfferBean();
		bean.setOfferDesc(info.getOfferDescription());
		bean.setOfferExtId(info.getExternalId());
		bean.setOfferId(info.getOfferId());
		bean.setOfferName(info.getOfferName());
		bean.setOfferProfile(info.getOfferProfile());

		if (info.getServiceVariantId() != null) {
			ServiceVariantBean parent = new ServiceVariantBean();
			parent.setServiceVariantId(info.getServiceVariantId());
			bean.setParentServiceVariant(parent);
		}

		if (info.getCreatedDate() != null) {
			bean.setOfferCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		}
		return bean;
	}

	public static void setProfileFields(OfferBean bean) {
		List<Param> params = Utilities.readProfile(bean.getOfferProfile());
		for (Param param : params) {
			if (AvsConstants.CATEGORY.equalsIgnoreCase(param.getName())) {
				bean.setCategoryAVS(param.getValue());
			} else if (AvsConstants.TYPE.equalsIgnoreCase(param.getName())) {
				bean.setTypeAVS(param.getValue());
			}

		}

	}

	public static String getProfileAsString(OfferBean bean) {
		String category = bean.getCategoryAVS();
		String type = bean.getTypeAVS();

		ArrayList<Param> params = new ArrayList<Param>();
		if (category != null) {
			params.add(new Param(AvsConstants.CATEGORY, category));
		}
		if (type != null) {
			params.add(new Param(AvsConstants.TYPE, type));
		}
		return Utilities.writeProfile(params);
	}

	public static List<DigitalGoodBean> convertDigitalGoods(List<AvsDigitalGoodInfoResp> infos) {
		if (infos == null) {
			return null;
		}
		List<DigitalGoodBean> beans = new ArrayList<DigitalGoodBean>();
		for (AvsDigitalGoodInfoResp info : infos) {
			DigitalGoodBean bean = new DigitalGoodBean();
			bean.setCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreationDate()));
			bean.setDescription(info.getDescription());
			bean.setExternalId(info.getExternalId());
			bean.setId(info.getId());
			bean.setName(info.getName());
			bean.setPrice(info.getPrice());
			bean.setType(info.getType());
			bean.setUpdateDate(Utilities.getDateFromGregorianCalendar(info.getUpdateDate()));
			beans.add(bean);
		}
		return beans;

	}

}
