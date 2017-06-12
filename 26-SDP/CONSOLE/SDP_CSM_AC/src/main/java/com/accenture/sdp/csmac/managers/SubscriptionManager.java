package com.accenture.sdp.csmac.managers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.beans.VoucherBean;
import com.accenture.sdp.csmac.beans.party.AvsPartyBean;
import com.accenture.sdp.csmac.beans.party.PartyBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.business.AvsSubscriptionBusiness;
import com.accenture.sdp.csmac.business.SubscriptionBusiness;
import com.accenture.sdp.csmac.business.VoucherBusiness;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.controllers.MenuController;
import com.accenture.sdp.csmac.controllers.PopupController;
import com.accenture.sdp.csmac.converters.SubscriptionFilter;

@ManagedBean(name = ApplicationConstants.SUBSCRIPTION_MANAGER)
@SessionScoped
public class SubscriptionManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// uso lo stesso bean per entrambe le liste
	private SubscriptionBean selectedBean;

	private List<SubscriptionBean> subscriptionsCommOffer;
	private List<SubscriptionBean> subscriptionsVoucher;

	public List<SubscriptionBean> getSubscriptionsCommOffer() {
		return subscriptionsCommOffer;
	}

	public void setSubscriptionsCommOffer(List<SubscriptionBean> subscriptionsCommOffer) {
		this.subscriptionsCommOffer = subscriptionsCommOffer;
	}

	public List<SubscriptionBean> getSubscriptionsVoucher() {
		return subscriptionsVoucher;
	}

	public void setSubscriptionsVoucher(List<SubscriptionBean> subscriptionsVoucher) {
		this.subscriptionsVoucher = subscriptionsVoucher;
	}

	public SubscriptionBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(SubscriptionBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public void goToDetailPage(ActionEvent event) {
		selectedBean = getItemParameter(SubscriptionBean.class, event);
		if (selectedBean != null) {
			SubscriptionBusiness.loadPrices(selectedBean);
			new MenuController().redirectbyParam(PathConstants.PARTY_SUBSCRIPTIONS_DETAILS);
		}
	}

	public void subscribeCommOffer(ActionEvent event) {
		SolutionOfferBean item = getItemParameter(SolutionOfferBean.class, event);
		AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
		AvsPartyBean party = pm.getSelectedBean();
		try {
			AvsSubscriptionBusiness.createSubscription(party.getCrmAccountId(), item);
			new MenuController().redirectbyParam(PathConstants.PARTY_DETAILS_VIEW);
			// force the reload of subscriptions
			refreshTable();
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void subscribeVoucher(ActionEvent event) {
		VoucherBean voucher = getItemParameter(VoucherBean.class, event);
		AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
		AvsPartyBean party = pm.getSelectedBean();
		try {
			// controlla se voucher è valido
			VoucherBusiness.checkVoucher(voucher);
			// vai con la sottoscrizione su AVS
			AvsSubscriptionBusiness.createSubscription(party.getCrmAccountId(), voucher);
			// segna usato il voucher su SDP
			VoucherBusiness.modifyVoucher(voucher.getVoucherId(), party.getId());
			new MenuController().redirectbyParam(PathConstants.PARTY_DETAILS_VIEW);
			// force the reload of subscriptions
			refreshTable();
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void deleteSubscription(ActionEvent event) {
		SubscriptionBean item = getItemParameter(SubscriptionBean.class, event);
		AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
		AvsPartyBean party = pm.getSelectedBean();
		try {
			AvsSubscriptionBusiness.deleteSubscription(party.getCrmAccountId(), item);

			// force the reload of subscriptions
			refreshTable();
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	@Override
	public void refreshTable() {
		AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
		if (this.subscriptionsCommOffer != null) {
			this.subscriptionsCommOffer.clear();
		}
		if (this.subscriptionsVoucher != null) {
			this.subscriptionsVoucher.clear();
		}
		PartyBean party = pm.getSelectedBean();
		if (party != null) {
			try {
				List<SubscriptionBean> subscriptions = SubscriptionBusiness.searchSubscriptionByPartyId(party.getId());
				this.subscriptionsCommOffer = SubscriptionFilter.filterCommOffer(subscriptions);
				this.subscriptionsVoucher = SubscriptionFilter.filterVoucher(subscriptions);
			} catch (ServiceErrorException e) {
				// niente popup per 0 found
			}
		}

	}
}
