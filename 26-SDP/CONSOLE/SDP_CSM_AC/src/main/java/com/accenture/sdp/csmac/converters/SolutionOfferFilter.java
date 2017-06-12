package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.utils.Utilities;

public abstract class SolutionOfferFilter {

	public static List<SolutionOfferBean> filterActive(Collection<SolutionOfferBean> inputs) {
		List<SolutionOfferBean> filterResult = new ArrayList<SolutionOfferBean>();
		for (SolutionOfferBean bean : inputs) {
			if (ApplicationConstants.STATUS_ACTIVE.equals(bean.getStatusName()) && Utilities.isValidNow(bean.getStartDate(), bean.getEndDate())) {
				filterResult.add(bean);
			}
		}
		return filterResult;
	}

	public static List<SolutionOfferBean> filterNotSubscribed(Collection<SolutionOfferBean> inputs, Collection<SubscriptionBean> subscriptions) {
		List<SolutionOfferBean> filterResult = new ArrayList<SolutionOfferBean>(inputs);
		// rimuovo le solutionOffer gia' sottoscritte
		for (SubscriptionBean i : subscriptions) {
			for (SolutionOfferBean j : inputs) {
				if (i.getSolutionOfferId().equals(j.getId()) && !i.getStatus().equals(ApplicationConstants.STATUS_DELETED)) {
					filterResult.remove(j);
					break;
				}
			}
		}
		return filterResult;
	}

}
