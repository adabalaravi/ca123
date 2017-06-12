package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpOfferDto;
import com.accenture.sdp.csmfe.webservices.response.offer.OfferComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.offer.OfferComplexWithStatusNameInfoResp;
import com.accenture.sdp.csmfe.webservices.response.offer.OfferInfoResp;

public class OfferBeanConverter extends BaseBeanConverter {

	private static OfferInfoResp convertOffer(SdpOfferDto o) {
		if (o == null) {
			return null;
		}
		OfferInfoResp info = new OfferInfoResp();
		convertBaseInfo(o, info);
		info.setOfferName(o.getOfferName());
		info.setOfferId(o.getOfferId());
		info.setOfferDescription(o.getOfferDescription());
		info.setOfferProfile(o.getOfferProfile());
		info.setStatusId(o.getStatusId());
		info.setServiceVariantId(o.getServiceVariantId());
		info.setExternalId(o.getExternalId());
		return info;
	}

	public static List<OfferInfoResp> convertOffers(List<SdpOfferDto> odtos) {
		if (odtos == null) {
			return null;
		}
		List<OfferInfoResp> oinfos = new ArrayList<OfferInfoResp>();
		for (SdpOfferDto o : odtos) {
			oinfos.add(convertOffer(o));
		}
		return oinfos;
	}

	// questo metodo aggiunge lo Status Name rispetto a quello sopra...
	public static List<OfferComplexWithStatusNameInfoResp> convertOffersWithStatus(List<SdpOfferDto> odtos) {
		if (odtos == null) {
			return null;
		}
		List<OfferComplexWithStatusNameInfoResp> oinfos = new ArrayList<OfferComplexWithStatusNameInfoResp>();
		for (SdpOfferDto o : odtos) {
			OfferComplexWithStatusNameInfoResp info = new OfferComplexWithStatusNameInfoResp();
			convertBaseInfo(o, info);
			info.setOfferName(o.getOfferName());
			info.setOfferId(o.getOfferId());
			info.setOfferDescription(o.getOfferDescription());
			info.setOfferProfile(o.getOfferProfile());
			info.setStatusId(o.getStatusId());
			info.setServiceVariantId(o.getServiceVariantId());
			info.setExternalId(o.getExternalId());
			info.setStatusName(o.getStatusName());
			oinfos.add(info);
		}
		return oinfos;
	}

	public static List<OfferComplexInfoResp> convertComplexOffers(List<SdpOfferDto> odtos) {
		if (odtos == null) {
			return null;
		}
		List<OfferComplexInfoResp> oinfos = new ArrayList<OfferComplexInfoResp>();
		for (SdpOfferDto o : odtos) {
			OfferComplexInfoResp info = new OfferComplexInfoResp();
			convertBaseInfo(o, info);
			info.setOfferName(o.getOfferName());
			info.setOfferId(o.getOfferId());
			info.setOfferDescription(o.getOfferDescription());
			info.setOfferProfile(o.getOfferProfile());
			info.setStatusId(o.getStatusId());
			info.setServiceVariantId(o.getServiceVariantId());
			info.setExternalId(o.getExternalId());
			info.setStatusName(o.getStatusName());
			info.setServiceVariantName(o.getServiceVariantName());
			info.setPlatformName(o.getPlatformName());
			info.setPlatformId(o.getPlatformId());
			info.setServiceTemplateName(o.getServiceTemplateName());
			info.setServiceTemplateId(o.getServiceTemplateId());
			oinfos.add(info);
		}
		return oinfos;
	}
}
