package com.accenture.sdp.csmac.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;

@ManagedBean(name = ApplicationConstants.CONTROLLER_POPUP_NAME)
@SessionScoped
public class PopupController implements Serializable {

	private static final long serialVersionUID = 2424638077939870540L;

	private boolean showOk = false;
	private boolean showClose = false;

	private String nextParam;
	private String message;

	public PopupController() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void closePopup() {
		nextParam = null;
		showOk = false;
		this.message = null;
	}

	public void openPopup() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("messagePopupForm");
		context.addCallbackParam("openPopup", true);
	}

	public void openPopup(String message) {
		openPopup();
		if (message == null || message.trim().length() == 0) {
			this.message = Utilities.parseErrorParameter(ApplicationConstants.CODE_GENERIC_ERROR, null);
		} else {
			this.message = message;
		}
		// almeno un pulsante lo devo abilitare di default
		this.showClose = true;
	}

	public boolean isShowOk() {
		return showOk;
	}

	public void setShowOk(boolean showOk) {
		this.showOk = showOk;
	}

	public boolean isShowClose() {
		return showClose;
	}

	public void setShowClose(boolean showClose) {
		this.showClose = showClose;
	}

	public String getNextParam() {
		return nextParam;
	}

	public void setNextParam(String nextParam) {
		this.nextParam = nextParam;
	}

	public void closeAndGoNext() {
		showOk = false;
		if (nextParam != null) {
			new MenuController().redirectbyParam(nextParam);
		}
		nextParam = null;
	}

	public static void handleServiceErrorException(ServiceErrorException e) {
		PopupController popup = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		popup.openPopup(e.getMessage());
		popup.setShowClose(true);
		popup.setShowOk(false);
	}
}
