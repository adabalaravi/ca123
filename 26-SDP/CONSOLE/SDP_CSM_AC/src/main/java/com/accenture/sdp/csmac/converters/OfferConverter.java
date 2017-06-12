package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.OfferBean;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.offer.OfferInfoResp;

public abstract class OfferConverter {

	public static List<OfferBean> convertOffers(List<OfferInfoResp> infos) {
		List<OfferBean> resultList = new ArrayList<OfferBean>();
		if (infos != null) {
			for (OfferInfoResp info : infos) {
				resultList.add(convertOffer(info));
			}
		}
		return resultList;
	}

	public static OfferBean convertOffer(OfferInfoResp info) {
		OfferBean bean = new OfferBean();
		bean.setCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		bean.setDescription(info.getOfferDescription());
		bean.setExternalId(info.getExternalId());
		bean.setId(info.getOfferId());
		bean.setName(info.getOfferName());
		bean.setProfile(info.getOfferProfile());
		bean.setId(info.getStatusId());
		return bean;
	}

}
