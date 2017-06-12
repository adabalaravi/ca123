package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.common.constants.AvsConstants;

public abstract class SubscriptionFilter {

	public static List<SubscriptionBean> filterCommOffer(Collection<SubscriptionBean> inputs) {
		List<SubscriptionBean> filterResult = new ArrayList<SubscriptionBean>();
		for (SubscriptionBean bean : inputs) {
			if (!isVoucher(bean)) {
				filterResult.add(bean);
			}
		}
		return filterResult;
	}

	public static List<SubscriptionBean> filterVoucher(Collection<SubscriptionBean> inputs) {
		List<SubscriptionBean> filterResult = new ArrayList<SubscriptionBean>();
		for (SubscriptionBean bean : inputs) {
			// voucherId salvato nell'externalId
			if (isVoucher(bean)) {
				// aggiorno pure il codice del voucher
				bean.setVoucherCode(bean.getExternalId().substring(AvsConstants.PROFILE_SUBSCRIPTION_VOUCHER_PREFIX.length()));
				filterResult.add(bean);
			}
		}
		return filterResult;
	}

	private static boolean isVoucher(SubscriptionBean bean) {
		return bean.getExternalId() != null && bean.getExternalId().startsWith(AvsConstants.PROFILE_SUBSCRIPTION_VOUCHER_PREFIX);
	}
}
