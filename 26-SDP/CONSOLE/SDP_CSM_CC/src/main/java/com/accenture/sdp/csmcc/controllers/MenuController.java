package com.accenture.sdp.csmcc.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.beans.BreadCrumbItem;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.managers.BaseManager;
import com.accenture.sdp.csmcc.managers.FrequencyManager;
import com.accenture.sdp.csmcc.managers.PackageManager;
import com.accenture.sdp.csmcc.managers.PartyGroupManager;
import com.accenture.sdp.csmcc.managers.PriceManager;
import com.accenture.sdp.csmcc.managers.SolutionOfferDiscountedManager;
import com.accenture.sdp.csmcc.popups.PopupBean;

@ManagedBean(name = ApplicationConstants.MENU_BEAN_NAME)
@RequestScoped
public class MenuController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LoggerWrapper log = new LoggerWrapper(MenuController.class);
	private String orientation = "horizontal";
	
	public MenuController() {
		super();
	}

	public void listener(ActionEvent e) {
		SessionController sessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		String myParam = (String) params.get("menuParam");
		sessionBean.clearRelationData();
		sessionBean.getBreadCrumb().clear();
		redirectbyParam(myParam, false);
	}

	public void listenerHeader(ActionEvent e) {
		SessionController sessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		sessionBean.clearRelationData();
		redirectbyParam(PathConstants.OPERATOR_VIEW);
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String dispatchRequest() throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		String breadParam = (String) params.get("breadParam");

		SessionController sessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String name = "";
		while (!name.equals(breadParam) && !sessionBean.getBreadCrumb().isEmpty()) {
			BreadCrumbItem bean = sessionBean.getBreadCrumb().pop();
			name = bean.getName();
		}
		redirectbyParam(breadParam, false);
		return null;
	}

	public static void redirectbyParam(String param) {
		redirectbyParam(param, false);
	}

	public static void redirectbyParamAndPop(String param) {
		SessionController sessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		sessionBean.getBreadCrumb().pop();
		redirectbyParam(param, false);
	}

	public static void redirectbyParam(String param, boolean updates) {
		try {
			SessionController sessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
			String pageUrl = Utilities.getPageUrl(param);
			log.logDebug("Redirecting to %s", pageUrl);
			if (param != null && param.length() > 0) {

				BaseManager dataTable = null;
				LayoutController bean = Utilities.findBean(ApplicationConstants.LAYOUT_CONTROLLER_BEAN_NAME, LayoutController.class);
				Stack<BreadCrumbItem> breadCrumb = sessionBean.getBreadCrumb();
				

				if (param.equals(PathConstants.START_PAGE)) {
					breadCrumb.clear();

				} else if (param.equals(PathConstants.CLUSTER_VIEW)) {
					breadCrumb.clear();
				//	breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_OFFER_MANAGEMENT));
					breadCrumb.push(new BreadCrumbItem(param, param));
					dataTable = Utilities.findBean(ApplicationConstants.PARTY_GROUP_MANAGER, PartyGroupManager.class);
				} else if (param.equals(PathConstants.TECHNICAL_PACKAGE_VIEW)) {
					breadCrumb.clear();
				//	breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_OFFER_MANAGEMENT));
					breadCrumb.push(new BreadCrumbItem(param, param));
					dataTable = Utilities.findBean(ApplicationConstants.OFFER_MANAGER, BaseManager.class);
				} else if (param.equals(PathConstants.SOLUTION_OFFER_VIEW)) {
					breadCrumb.clear();
				//	breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_OFFER_MANAGEMENT));
					breadCrumb.push(new BreadCrumbItem(param, param));
					dataTable = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_MANAGER, BaseManager.class);
				} else if (param.equals(PathConstants.CLUSTER_VIEW)) {
					breadCrumb.clear();
					breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_ADMINISTRATION));
					breadCrumb.push(new BreadCrumbItem(param, param));
				} else if (param.equals(PathConstants.PRICE_VIEW)) {
					breadCrumb.clear();
					breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_ADMINISTRATION));
					breadCrumb.push(new BreadCrumbItem(param, param));
					dataTable = Utilities.findBean(ApplicationConstants.PRICE_MANAGER, PriceManager.class);
				} else if (param.equals(PathConstants.FREQUENCY_VIEW)) {
					breadCrumb.clear();
					breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_ADMINISTRATION));
					breadCrumb.push(new BreadCrumbItem(param, param));
					dataTable = Utilities.findBean(ApplicationConstants.FREQUENCY_MANAGER, FrequencyManager.class);
				} else if (param.equals(PathConstants.SOLUTION_OFFER_PACKAGE_VIEW)) {
					breadCrumb.clear();
				//	breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_OFFER_MANAGEMENT));
					breadCrumb.push(new BreadCrumbItem(PathConstants.SOLUTION_OFFER_VIEW, PathConstants.SOLUTION_OFFER_VIEW));
					breadCrumb.push(new BreadCrumbItem(param, param));
					dataTable = Utilities.findBean(ApplicationConstants.PACKAGE_MANAGER, PackageManager.class);
				} else if (param.equals(PathConstants.SOLUTION_OFFER_DISCOUNT_VIEW)) {
					breadCrumb.clear();
				//	breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_OFFER_MANAGEMENT));
					breadCrumb.push(new BreadCrumbItem(PathConstants.SOLUTION_OFFER_VIEW, PathConstants.SOLUTION_OFFER_VIEW));
					breadCrumb.push(new BreadCrumbItem(param, param));
					SolutionOfferDiscountedManager manager = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_DISCOUNTED_MANAGER,
							SolutionOfferDiscountedManager.class);
					manager.setPrincipalView(param);
					dataTable = manager;
				} else if (param.equals(PathConstants.SOLUTION_OFFER_DISCOUNT_MENU)) {
					breadCrumb.clear();
				//	breadCrumb.push(new BreadCrumbItem(PathConstants.MENU_OFFER_MANAGEMENT));
					breadCrumb.push(new BreadCrumbItem(param, param));
					SolutionOfferDiscountedManager manager = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_DISCOUNTED_MANAGER,
							SolutionOfferDiscountedManager.class);
					manager.setPrincipalView(param);
					manager.setParentSolutionOffer(null);
					dataTable = manager;
				}

				else {
					breadCrumb.push(new BreadCrumbItem(param, param));
				}
				bean.setContentInclude(pageUrl);
				bean.setContentId(param);
				// REFRESH DATA
				if (dataTable != null) {
					if (!updates) {
						dataTable.refreshTable();
					}
					if (dataTable instanceof PackageManager) {
						((PackageManager) dataTable).calculateTotalPrice();
					}
					sessionBean.setTableBean(dataTable);
				}
				// log page navigation
				log.logStartFeature(sessionBean.getBreadCrumbAsString(), null);
			}

		} catch (Exception e) {
			log.logException(e.getMessage(), e);
			e.printStackTrace();
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup("RESOURCE NOT FOUND FOR: " + param);
			

		}

	}

}
