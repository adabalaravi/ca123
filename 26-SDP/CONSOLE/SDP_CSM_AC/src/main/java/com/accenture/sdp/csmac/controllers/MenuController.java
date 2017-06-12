package com.accenture.sdp.csmac.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmac.beans.party.AvsPartyBean;
import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.beans.BreadCrumbItemBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.MessageConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.managers.AvsPartyManager;
import com.accenture.sdp.csmac.managers.SolutionOfferManager;

@ManagedBean(name = ApplicationConstants.CONTROLLER_MENU_NAME)
@RequestScoped
public class MenuController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static transient LoggerWrapper log = new LoggerWrapper(MenuController.class);

	public void menuListener(ActionEvent e) {
		// chiamata che arriva dal menu: resetto la breadcrumb
		SessionController sessionbean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		sessionbean.getBreadCrumbController().resetBreadCrumb();

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String myParam = (String) params.get("menuParam");
		redirectbyParam(myParam);
	}

	public String dispatchRequest() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String breadParam = (String) params.get("breadParam");
		redirectbyParam(breadParam);
		return null;
	}

	public void logOut() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.invalidateSession();
		try {
			context.redirect("forwardAvs.jsp");
		} catch (IOException e) {
			log.logError(e);
		}
	}

	public void redirectbyParam(String param) {
		if (param != null && param.length() > 0) {
			LayoutController page = Utilities.findBean(ApplicationConstants.CONTROLLER_LAYOUT_NAME, LayoutController.class);
			SessionController sessionbean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
			// CHECK AUTHENTICATION
			if (sessionbean.getOperator() == null) {
				log.logDebug("User not authenticated");
				page.setMenuVisible(false);
				logOut();
				return;
			} else if (!sessionbean.getOperator().getRole().isAssuranceRead()) {
				log.logDebug("User not enabled for assurance");
				page.setMenuVisible(false);
				logOut();
				return;
			}
			// PAGE NAVIGATION
			else if (param.equals(PathConstants.PARTY_SEARCH)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(new BreadCrumbItemBean(MessageConstants.BREADCRUMB_SEARCH, PathConstants.PARTY_SEARCH));
				AvsPartyManager manager = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
				manager.clearFields();
				page.setContentInclude(PathConstants.PARTY_SEARCH_URL);
			} else if (param.equals(PathConstants.PARTY_VIEW)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(new BreadCrumbItemBean(MessageConstants.BREADCRUMB_RESULT_LIST, PathConstants.PARTY_VIEW));
				page.setContentInclude(PathConstants.PARTY_VIEW_URL);
			} else if (param.equals(PathConstants.PARTY_CREATE)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(
						new BreadCrumbItemBean(MessageConstants.BREADCRUMB_CREATE_USER, PathConstants.PARTY_CREATE));
				page.setContentInclude(PathConstants.PARTY_CREATE_URL);
				AvsPartyManager partyManager = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
				partyManager.setSelectedBean(new AvsPartyBean());
			} else if (param.equals(PathConstants.PARTY_CLUSTER_UPDATE)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(
						new BreadCrumbItemBean(MessageConstants.BREADCRUMB_CLUSTER_UPDATE_USER, PathConstants.PARTY_CLUSTER_UPDATE));
				page.setContentInclude(PathConstants.PARTY_CLUSTER_UPDATE_URL);
			} else if (param.equals(PathConstants.PARTY_DETAILS_VIEW)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(
						new BreadCrumbItemBean(MessageConstants.BREADCRUMB_DETAILS, PathConstants.PARTY_DETAILS_VIEW));
				page.setContentInclude(PathConstants.PARTY_DETAILS_VIEW_URL);
				AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
				pm.setTabInnerPage(null);
			} else if (param.equals(PathConstants.PARTY_SUBSCRIPTIONS_DETAILS)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(
						new BreadCrumbItemBean(MessageConstants.BREADCRUMB_SUBSCRIPTION_DETAILS, PathConstants.PARTY_SUBSCRIPTIONS_DETAILS));
				page.setContentInclude(PathConstants.SUBSCRIPTION_DETAILS_VIEW_URL);
			} else if (param.equals(PathConstants.SUBSCRIBE_START_COMM_OFFER)) {
				SolutionOfferManager om = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_MANAGER, SolutionOfferManager.class);
				om.refreshTable();
				AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
				// e' diventata pagina interna, adatto url
				pm.setTabInnerPage("../../" + PathConstants.VIEW_SOLUTION_OFFER_URL);
			} else if (param.equals(PathConstants.SUBSCRIBE_START_VOUCHER)) {
				SolutionOfferManager om = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_MANAGER, SolutionOfferManager.class);
				om.resetVoucher();
				AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
				// e' diventata pagina interna, adatto url
				pm.setTabInnerPage("../../" + PathConstants.ENTER_VOUCHER_URL);
			} else if (param.equals(PathConstants.PARTY_DEVICE_DETAILS_VIEW)) {
				sessionbean.getBreadCrumbController().goToBreadCrumb(new BreadCrumbItemBean(MessageConstants.BREADCRUMB_DEVICE_DETAIL, PathConstants.PARTY_DEVICE_DETAILS_VIEW));
				page.setContentInclude(PathConstants.PARTY_DEVICE_DETAILS_URL);
			} 
			page.setContentId(param);
			log.logStartFeature(sessionbean.getBreadCrumbController().toString(), null);

		}

	}
}
