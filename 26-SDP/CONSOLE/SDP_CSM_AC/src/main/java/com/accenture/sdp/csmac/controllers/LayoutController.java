package com.accenture.sdp.csmac.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.common.utils.Utilities;

@ManagedBean(name = ApplicationConstants.CONTROLLER_LAYOUT_NAME)
@SessionScoped
public class LayoutController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contentInclude;
	private boolean menuVisible;
	private LocaleController localeController;

	private String contentId;
	
	public LayoutController() {
		this.menuVisible = true;
		this.localeController = new LocaleController();
	}

	public String getContentInclude() {
		if (contentInclude == null) {
			// FIXME carica operatore e pagina default
			SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
			session.loadOperator();
			session.refreshCustomersCounter();
			session.refreshTenantsCounter();
			contentId = PathConstants.PARTY_SEARCH;
			new MenuController().redirectbyParam(PathConstants.PARTY_SEARCH);
		}
		return contentInclude;
	}

	public void setContentInclude(String contentInclude) {
		this.contentInclude = contentInclude;
	}

	public boolean isMenuVisible() {
		return menuVisible;
	}

	public void setMenuVisible(boolean menuVisible) {
		this.menuVisible = menuVisible;
	}

	public LocaleController getLocaleController() {
		return localeController;
	}

	public void setLocaleController(LocaleController localeController) {
		this.localeController = localeController;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

}
