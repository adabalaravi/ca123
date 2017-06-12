package com.accenture.sdp.csmac.business;

import java.util.List;

import com.accenture.sdp.csmac.beans.PackageBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionDetailBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.SubscriptionConverter;
import com.accenture.sdp.csmac.services.SubscriptionService;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionByPartyResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SubscriptionComplexInfoResp;

public final class SubscriptionBusiness {

	private SubscriptionBusiness() {
	}

	public static void loadPrices(SubscriptionBean bean) {
		// FIXME il metodo e' fatto sui package anziche' sui details
		// su avs infatti non c'e' differenza
		try {
			double nrcPrice = 0d;
			double rcPrice = 0d;
			double nrcDiscountedPrice = 0d;
			double rcDiscountedPrice = 0d;
			String frequency = null;
			boolean discounted = false;

			List<PackageBean> packs = PackageBusiness.searchPackagesBySolutionOfferName(bean.getSolutionOfferName());
			// avs compra tutti i package: li sommo tutti,
			// senza controllare i subscription details
			for (PackageBean p : packs) {
				if (p.getNrcPrice() != null) {
					nrcPrice += p.getNrcPrice().doubleValue();
				}
				if (p.getRcPrice() != null) {
					rcPrice += p.getRcPrice().doubleValue();
				}
				if (p.getFrequencyName() != null) {
					frequency = p.getFrequencyName();
				}
				// GESTIONE SCONTI
				if (p.getDiscountId() != null) {
					discounted = true;
					if (p.getDiscountAbsNrc() != null) {
						// lo sconto e' considerato prezzo netto
						nrcDiscountedPrice += p.getDiscountAbsNrc().doubleValue();
					} else if (p.getDiscountPercNrc() != null) {
						nrcDiscountedPrice += Utilities.calculateDiscountedPrice(p.getNrcPrice(), p.getDiscountPercNrc());
					} else {
						// prezzo pieno se nessuno sconto applicabile
						nrcDiscountedPrice += p.getNrcPrice().doubleValue();
					}
					if (p.getDiscountAbsRc() != null) {
						// lo sconto e' considerato prezzo netto
						rcDiscountedPrice += p.getDiscountAbsRc().doubleValue();
					} else if (p.getDiscountPercRc() != null) {
						rcDiscountedPrice += Utilities.calculateDiscountedPrice(p.getRcPrice(), p.getDiscountPercRc());
					} else {
						// prezzo pieno se nessuno sconto applicabile
						rcDiscountedPrice += p.getRcPrice().doubleValue();
					}
				}
			}
			bean.setNrcPrice(nrcPrice);
			bean.setRcPrice(rcPrice);
			bean.setNrcDiscountedPrice(nrcDiscountedPrice);
			bean.setRcDiscountedPrice(rcDiscountedPrice);
			bean.setFrequency(frequency);
			bean.setDiscounted(discounted);
		} catch (ServiceErrorException e) {
			// non ha trovato package, amen
		}
	}

	public static List<SubscriptionDetailBean> searchSubscriptionDetails(Long subscriptionId) throws ServiceErrorException {
		SubscriptionService service = Utilities.findBean(ApplicationConstants.SUBSCRIPTION_SERVICE_BEAN, SubscriptionService.class);
		SearchSubscriptionComplexResp resp = service.searchSubscription(subscriptionId, Utilities.getTenantName());
		// c'e' la solita lista inutile, prendo primo elemento
		SubscriptionComplexInfoResp sub = resp.getSubscriptions().getSubscription().get(0);
		if (sub.getOffers() == null) {
			return null;
		}
		return SubscriptionConverter.convertSubscriptionDetails(sub.getOffers().getOffer());
	}

	public static List<SubscriptionBean> searchSubscriptionByPartyId(Long partyId) throws ServiceErrorException {
		SubscriptionService service = Utilities.findBean(ApplicationConstants.SUBSCRIPTION_SERVICE_BEAN, SubscriptionService.class);
		SearchSubscriptionByPartyResp resp = service.searchSubscriptionsByParty(partyId, 0L, 0L, Utilities.getTenantName());
		List<SubscriptionBean> result = SubscriptionConverter.convertSubscriptionByPartyInfoToBeanList(resp.getSubscriptions().getSubscription());
		// FIXME potenzialmente micidiale, ma AVS lo vuole
		for (SubscriptionBean s : result) {
			loadPrices(s);
		}
		return result;
	}

}
