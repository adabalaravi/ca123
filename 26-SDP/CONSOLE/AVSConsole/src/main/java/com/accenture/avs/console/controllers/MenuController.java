package com.accenture.avs.console.controllers;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.tabmenu.TabMenu;

import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.PropertyManager;
import com.accenture.avs.console.common.beans.BreadCrumbItemBean;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.constants.MessageConstants;
import com.accenture.avs.console.common.constants.PathConstants;
import com.accenture.avs.console.common.utils.MWalletSSO;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.managers.OperatorManager;

@ManagedBean(name = ApplicationConstants.CONTROLLER_MENU_NAME)
@RequestScoped
public class MenuController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static transient LoggerWrapper log = new LoggerWrapper(MenuController.class);

	public void dispatchRequest() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String action = (String) params.get("menuAction");
		redirectbyParam(action);
	}

	public void wrapperListener(ActionEvent e) {
		// chiamata dal wrapper: cambio il tasto selezionato
		TabMenu menu = (TabMenu) e.getComponent().getParent();
		LayoutController layout = Utilities.findBean(ApplicationConstants.CONTROLLER_LAYOUT_NAME, LayoutController.class);
		// recupera l'indice dalla posizione del tasto nel menu
		layout.setTabIndex(menu.getChildren().indexOf(e.getComponent()));
		dispatchRequest();
	}

	public void menuListener(ActionEvent e) {
		// chiamata che arriva dal menu: resetto la breadcrumb
		SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		session.getBreadCrumbController().resetBreadCrumb();
		dispatchRequest();
	}

	public void redirectbyParam(String action) {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " : " + action);
		if (action != null && action.length() > 0) {
			SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
			LayoutController layout = Utilities.findBean(ApplicationConstants.CONTROLLER_LAYOUT_NAME, LayoutController.class);
			if (session.getOperator() == null) {
				layout.setMenuVisible(false);
				layout.setIframedContentInclude(PathConstants.URL_LOGOUT);
			} else {
				layout.setMenuVisible(true);
				// MAIN MENU OPTIONS
				if (action.equals(PathConstants.ACTION_CONSOLE_ASSURANCE)) {
					layout.setIframedContentInclude(PathConstants.URL_CONSOLE_ASSURANCE);
					return;
				} else if (action.equals(PathConstants.ACTION_CONSOLE_CATALOGUE)) {
					layout.setIframedContentInclude(PathConstants.URL_CONSOLE_CATALOGUE);
					return;
				} else if (action.equals(PathConstants.ACTION_CONSOLE_CATCHUP)) {
					layout.setIframedContentInclude(PathConstants.URL_CONSOLE_CATCHUP);
					return;
				} else if (action.equals(PathConstants.ACTION_CONSOLE_MULTICAMERA)) {
					layout.setIframedContentInclude(PathConstants.URL_CONSOLE_MULTICAMERA);
					return;
				} else if (action.equals(PathConstants.ACTION_CONSOLE_OPERATORS)) {
					layout.setContentInclude(PathConstants.URL_CONSOLE_OPERATORS);
					// metto sulla pagina iniziale => searchAll
					OperatorManager om = Utilities.findBean(ApplicationConstants.MANAGER_OPERATOR, OperatorManager.class);
					om.searchAll();
					return;
				} else if (action.equals(PathConstants.ACTION_CONSOLE_MWALLET)) {
					PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
					String urlString = propertyManager.getProperty(PathConstants.URL_CONSOLE_MWALLET);
					layout.setIframedContentInclude(urlString + MWalletSSO.generateGetUrl(session.getOperator().getUsername()));
					return;
				}
				// OPERATORS PAGES
				else if (action.equals(PathConstants.OPERATOR_VIEW)) {
					session.getBreadCrumbController().goToBreadCrumb(new BreadCrumbItemBean(MessageConstants.BREADCRUMB_OPERATOR, PathConstants.OPERATOR_VIEW));
					layout.setOperatorsContentInclude(PathConstants.OPERATOR_VIEW_URL);
				} else if (action.equals(PathConstants.ADD_OPERATOR_STEP1)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_ADD_OPERATOR, PathConstants.ADD_OPERATOR_STEP1));
					layout.setOperatorsContentInclude(PathConstants.ADD_OPERATOR_STEP1_URL);
				} else if (action.equals(PathConstants.ADD_OPERATOR_STEP2)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_ADD_OPERATOR, PathConstants.ADD_OPERATOR_STEP1));
					layout.setOperatorsContentInclude(PathConstants.ADD_OPERATOR_STEP2_URL);
				} else if (action.equals(PathConstants.ADD_OPERATOR_STEP3)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_ADD_OPERATOR, PathConstants.ADD_OPERATOR_STEP1));
					layout.setOperatorsContentInclude(PathConstants.ADD_OPERATOR_STEP3_URL);
				} else if (action.equals(PathConstants.UPDATE_OPERATOR)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_UPDATE_OPERATOR, PathConstants.UPDATE_OPERATOR));
					layout.setOperatorsContentInclude(PathConstants.UPDATE_OPERATOR_URL);
				} else if (action.equals(PathConstants.UPDATE_OPERATOR_RIGHTS)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_UPDATE_OPERATOR_RIGHTS, PathConstants.UPDATE_OPERATOR_RIGHTS));
					layout.setOperatorsContentInclude(PathConstants.UPDATE_OPERATOR_RIGHTS_URL);
				} else if (action.equals(PathConstants.UPDATE_OPERATOR_TENANTS)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_UPDATE_OPERATOR_TENANT, PathConstants.UPDATE_OPERATOR_TENANTS));
					layout.setOperatorsContentInclude(PathConstants.UPDATE_OPERATOR_TENANT_URL);
				}
				// la pagina di ricerca in realta' e' disattivata : sempre searchAll
				else if (action.equals(PathConstants.SEARCH_OPERATOR)) {
					session.getBreadCrumbController().goToBreadCrumb(
							new BreadCrumbItemBean(MessageConstants.BREADCRUMB_SEARCH_OPERATOR, PathConstants.SEARCH_OPERATOR));
					layout.setOperatorsContentInclude(PathConstants.SEARCH_OPERATOR_URL);
				}
				layout.setContentId(action);
				log.logStartFeature(session.getBreadCrumbController().toString(), null);
			}
		}
	}

}
