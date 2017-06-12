package com.accenture.sdp.csmac.business;

import com.accenture.sdp.csmac.beans.OfferBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.OfferConverter;
import com.accenture.sdp.csmac.services.OfferService;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferResp;

public final class OfferBusiness {

	private OfferBusiness() {
	}

	public static OfferBean searchOffer(Long offerId) throws ServiceErrorException {
		OfferService s = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN, OfferService.class);
		SearchOfferResp resp = s.searchOffer(offerId, Utilities.getTenantName());
		return OfferConverter.convertOffer(resp.getOffers().getOffer().get(0));
	}

}
