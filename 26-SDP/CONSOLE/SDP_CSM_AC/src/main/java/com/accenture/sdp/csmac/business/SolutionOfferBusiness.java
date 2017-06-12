package com.accenture.sdp.csmac.business;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.beans.party.PartyGroupBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.SolutionOfferConverter;
import com.accenture.sdp.csmac.converters.SolutionOfferFilter;
import com.accenture.sdp.csmac.services.SolutionOfferService;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferResp;

public final class SolutionOfferBusiness {

	private SolutionOfferBusiness() {
	}

	public static List<SolutionOfferBean> searchSolutionOffersByPartyGroup(Long partyGroupId) throws ServiceErrorException {
		SolutionOfferService s = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN, SolutionOfferService.class);
		SearchSolutionOfferComplexRespPaginated resp = s.searchSolutionOffersByPartyGroup(partyGroupId, Utilities.getTenantName());
		return SolutionOfferConverter.convertSolutionOffers(resp.getSolutionOffers().getSolutionOffer());
	}

	public static SolutionOfferBean searchSolutionOfferById(Long solutionOfferId) throws ServiceErrorException {
		SolutionOfferService s = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN, SolutionOfferService.class);
		SearchSolutionOfferResp resp = s.searchSolutionOfferById(solutionOfferId, Utilities.getTenantName());
		return SolutionOfferConverter.convertSolutionOffer(resp.getSolutionOffers().getSolutionOffer().get(0));
	}

	public static List<SolutionOfferBean> loadSolutionOffers(Collection<PartyGroupBean> partyGroups, Collection<SubscriptionBean> subscriptions) {
		Set<SolutionOfferBean> solutionOffers = new HashSet<SolutionOfferBean>();
		for (PartyGroupBean pg : partyGroups) {
			try {
				solutionOffers.addAll(searchSolutionOffersByPartyGroup(pg.getId()));
			} catch (ServiceErrorException e) {
				// ignoro le eccezioni, probabili 0found
			}
		}
		// filtro solo quelle attive
		List<SolutionOfferBean> filtered = SolutionOfferFilter.filterActive(solutionOffers);
		// rimuovo le solutionOffer gia' sottoscritte
		filtered = SolutionOfferFilter.filterNotSubscribed(filtered, subscriptions);

		return filtered;
	}

}
