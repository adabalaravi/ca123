package com.accenture.sdp.csmcc.popups;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.controllers.MenuController;


@ManagedBean(name = ApplicationConstants.POPUP_BEAN)
@SessionScoped
public class PopupBean implements Serializable {


	private static final long serialVersionUID = 2424638077939870540L;


	private boolean visible = false;
	private boolean updateFlag;

	private String nextParam;
	private String modalMessage;
	private String message;

	
	public PopupBean() {
		super();
	}

	public String getModalMessage() {
		return modalMessage;
	}

	public void setModalMessage(String modalMessage) {
		this.modalMessage = modalMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public void closePopup() {
		visible = false;
		updateFlag = false;
	}

	public void openPopup() {
		visible = true;
	}

	public void openPopup(String message) {
		this.modalMessage = message;
		visible = true;
		RequestContext context = RequestContext.getCurrentInstance();  
		context.update("popupBeanContent");
		context.update("confirmPopupBeanContent");
        context.addCallbackParam("openPopup", true);  
	}
	
	public String getNextParam() {
		return nextParam;
	}

	public void setNextParam(String nextParam) {
		this.nextParam = nextParam;
	}
	
	

	public void closeAndGoNext() {
		visible = false;
		if (nextParam != null) {
			MenuController.redirectbyParam(nextParam, updateFlag);
		}
		updateFlag = false;
		nextParam = null;
	}
}
