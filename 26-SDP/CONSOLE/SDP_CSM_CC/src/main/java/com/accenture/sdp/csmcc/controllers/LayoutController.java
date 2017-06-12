/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.controllers;

import java.io.Serializable;
import java.util.Stack;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.accenture.sdp.csmcc.common.beans.BreadCrumbItem;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.managers.BaseManager;

@ManagedBean(name = ApplicationConstants.LAYOUT_CONTROLLER_BEAN_NAME)
@SessionScoped
public class LayoutController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String contentInclude;
	private boolean menuVisible = true;
	private Boolean isAdmin;
	private String contentId;

	public String getContentInclude() {
		return contentInclude;
	}

	public void setContentInclude(String contentInclude) {
		this.contentInclude = contentInclude;
	}

	public LayoutController() {	
		contentInclude = Utilities.getPageUrl(PathConstants.SOLUTION_OFFER_VIEW);
		contentId = PathConstants.SOLUTION_OFFER_VIEW;
		Stack<BreadCrumbItem> breadCrumb = new Stack<BreadCrumbItem>();
		breadCrumb.push(new BreadCrumbItem(PathConstants.SOLUTION_OFFER_VIEW, PathConstants.SOLUTION_OFFER_VIEW));
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		infoSessionBean.setBreadCrumb(breadCrumb);
		infoSessionBean.loadOperator();
		infoSessionBean.refreshCommercialPackageCounter();
		infoSessionBean.refreshOfferCounter();
		BaseManager tableBean = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_MANAGER, BaseManager.class);
		tableBean.refreshTable();
		infoSessionBean.setTableBean(tableBean);
	}

	public boolean isMenuVisible() {
		return menuVisible;
	}

	public void setMenuVisible(boolean menuVisible) {
		this.menuVisible = menuVisible;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	

}
