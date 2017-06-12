package com.accenture.avs.console.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.accenture.avs.console.beans.operator.OperatorRoleInfo;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.constants.PathConstants;
import com.accenture.avs.console.common.utils.Utilities;

@ManagedBean(name = ApplicationConstants.CONTROLLER_LAYOUT_NAME)
@SessionScoped
public final class LayoutController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// *** NAVIGATION *** //
	private boolean menuVisible;
	private int tabIndex;
	private String contentInclude;
	// soluzione temporanea per gestire l'iframe
	private boolean iframed;
	private String iframedContentInclude;
	// pagine incluse nei wrapper delle sezioni principali
	// per ora solo operators e' fuori dall'iframe
	private String operatorsContentInclude;
	// *** LOCALES *** //
	private LocaleController localeController;
	private String contentId;

	public LayoutController() {
		this.menuVisible = false;
		this.localeController = new LocaleController();
		SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		session.loadOperator();
		session.refreshOperatorsCounter();
		session.refreshTenantsCounter();
		if (session.getOperator() != null) {
			loadStartPage(session.getOperator().getRole());
		}
	}

	private void loadStartPage(OperatorRoleInfo role) {
		// andrebbe gestita meglio l'associazione indice-console
		if (role.isCatalogueRead()) {
			setIframedContentInclude(PathConstants.URL_CONSOLE_CATALOGUE);
			this.tabIndex = 0;
		} else if (role.isAssuranceRead()) {
			setIframedContentInclude(PathConstants.URL_CONSOLE_ASSURANCE);
			this.tabIndex = 1;
		} else if (role.isCatchupRead()) {
			setIframedContentInclude(PathConstants.URL_CONSOLE_CATCHUP);
			this.tabIndex = 2;
		} else if (role.isCatchupRead()) {
			setIframedContentInclude(PathConstants.URL_CONSOLE_MULTICAMERA);
			this.tabIndex = 3;
		} else if (role.isOperatorsRead()) {
			setContentInclude(PathConstants.URL_CONSOLE_OPERATORS);
			this.tabIndex = 4;
		}
	}

	public String getContentInclude() {
		return contentInclude;
	}

	public void setContentInclude(String contentInclude) {
		this.iframed = false;
		this.contentInclude = contentInclude;
	}

	public String getIframedContentInclude() {
		return iframedContentInclude;
	}

	public void setIframedContentInclude(String iframedContentInclude) {
		this.iframed = true;
		this.iframedContentInclude = iframedContentInclude;
	}

	public boolean isMenuVisible() {
		return menuVisible;
	}

	public void setMenuVisible(boolean menuVisible) {
		this.menuVisible = menuVisible;
	}

	public boolean isIframed() {
		return iframed;
	}

	public void setIframed(boolean iframed) {
		this.iframed = iframed;
	}

	public String getOperatorsContentInclude() {
		return operatorsContentInclude;
	}

	public void setOperatorsContentInclude(String operatorsContentInclude) {
		this.operatorsContentInclude = operatorsContentInclude;
	}

	public LocaleController getLocaleController() {
		return localeController;
	}

	public void setLocaleController(LocaleController localeController) {
		this.localeController = localeController;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
}
