package com.accenture.sdp.csmcc.popups;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.managers.BaseManager;


@ManagedBean(name = ApplicationConstants.CONFIRM_POPUP_BEAN)
@SessionScoped
public class ConfirmPopupBean implements Serializable {


	private static final long serialVersionUID = 2424638077939870540L;


	private boolean visible = false;

	private BaseManager tableBean;
	private String message;

	public ConfirmPopupBean() {
		super();
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

	public void closePopup() {
		visible = false;
	}

	public void openPopup() {
		visible = true;
	}

	public void openPopup(String message) {
		this.message = message;
		visible = true;
		RequestContext context = RequestContext.getCurrentInstance();  
        context.addCallbackParam("openConfirmPopup", true);  
	}

	public BaseManager getTableBean() {
		return tableBean;
	}

	public void setTableBean(BaseManager tableBean) {
		this.tableBean = tableBean;
	}

	

	public String confirm() {
		visible = false;
		if (tableBean != null) {
			tableBean.changeStatusOfSelectedBean();
		}
		tableBean = null;
		return null;
	}

	public String cancel() {
		visible = false;
		return null;
	}
}
