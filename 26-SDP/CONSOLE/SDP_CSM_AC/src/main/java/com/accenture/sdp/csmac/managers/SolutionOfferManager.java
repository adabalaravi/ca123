package com.accenture.sdp.csmac.managers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.beans.VoucherBean;
import com.accenture.sdp.csmac.beans.party.PartyGroupBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.business.PartyGroupBusiness;
import com.accenture.sdp.csmac.business.SolutionOfferBusiness;
import com.accenture.sdp.csmac.business.VoucherBusiness;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.controllers.PopupController;

@ManagedBean(name = ApplicationConstants.SOLUTION_OFFER_MANAGER)
@SessionScoped
public class SolutionOfferManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// solution offer
	private SolutionOfferBean selectedCommOffer;
	private List<SolutionOfferBean> commOffers;

	private String contentInclude = PathConstants.PARTY_DETAILS_URL;

	// serve per il party group ALL
	private PartyGroupBean defaultPartyGroup;

	// gestione voucher
	private String voucherCode;
	private VoucherBean voucher;
	private List<VoucherBean> vouchers;

	public SolutionOfferManager() throws ServiceErrorException {
		// FIXME pg "ALL" encodato
		List<PartyGroupBean> groups = PartyGroupBusiness.searchAllPartyGroups();
		for (PartyGroupBean pg : groups) {
			if (pg.getName().equals("ALL")) {
				defaultPartyGroup = pg;
			}
		}
	}

	public void resetSelected() {
		this.selectedCommOffer = null;
	}

	public void resetVoucher() {
		this.voucherCode = null;
		this.voucher = null;
		this.vouchers = null;
	}

	public void resetAll() {
		resetSelected();
		this.commOffers = null;
	}

	public List<SolutionOfferBean> getCommOffers() {
		return commOffers;
	}

	public void setCommOffers(List<SolutionOfferBean> commOffers) {
		this.commOffers = commOffers;
	}

	public String getContentInclude() {
		return contentInclude;
	}

	public void setContentInclude(String contentInclude) {
		this.contentInclude = contentInclude;
	}

	public SolutionOfferBean getSelectedCommOffer() {
		return selectedCommOffer;
	}

	public void setSelectedCommOffer(SolutionOfferBean selectedCommOffer) {
		this.selectedCommOffer = selectedCommOffer;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public VoucherBean getVoucher() {
		return this.voucher;
	}

	public void loadVoucher(ActionEvent event) {
		if (!Utilities.isEmptyString(this.voucherCode)) {
			try {
				// resetto risultati
				this.voucher = null;
				// compresa lista, per evitare rimanga in caso di fallimento
				this.vouchers = null;
				this.vouchers = VoucherBusiness.searchVouchersByCodeLike(this.voucherCode);
			} catch (ServiceErrorException e) {
				PopupController.handleServiceErrorException(e);
			}
		}
	}

	public void setVoucher(VoucherBean voucher) {
		this.voucher = voucher;
	}

	public List<VoucherBean> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<VoucherBean> vouchers) {
		this.vouchers = vouchers;
	}

	@Override
	public void refreshTable() {
		// recupero party Group dell'utente
		AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
		Set<PartyGroupBean> partyGroups = new HashSet<PartyGroupBean>(pm.getSelectedBean().getPartyGroups());
		// FIXME AGGIUNGO PARTY-GROUP 'ALL'
		partyGroups.add(defaultPartyGroup);

		// recupero le sottoscrizioni dell'utente
		SubscriptionManager sm = Utilities.findBean(ApplicationConstants.SUBSCRIPTION_MANAGER, SubscriptionManager.class);
		List<SubscriptionBean> subscriptions = new ArrayList<SubscriptionBean>();
		if (sm.getSubscriptionsCommOffer()!=null) {
			subscriptions.addAll(sm.getSubscriptionsCommOffer());
		}
		if (sm.getSubscriptionsVoucher()!=null) {
			subscriptions.addAll(sm.getSubscriptionsVoucher());
		}
		commOffers = SolutionOfferBusiness.loadSolutionOffers(partyGroups, subscriptions);
	}

}
