package com.accenture.sdp.csmcc.popups;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;

@ManagedBean(name = ApplicationConstants.ADD_OFFER_GROUP_POPUP_BEAN_NAME)
@RequestScoped
public class AddOfferGroupPopupBean implements Serializable {


	private static final long serialVersionUID = 2424638077939870540L;


	private boolean visible = false;

	private String groupName;
	private String errorMessage;

	public AddOfferGroupPopupBean() {
		super();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
		errorMessage = "";
		groupName = "";
	}

	public void openPopup(String message) {
		visible = true;
		errorMessage = "";
		groupName = "";
	}

}
