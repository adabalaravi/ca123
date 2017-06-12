package com.accenture.sdp.csmcc.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.business.PlatformBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.PlatformComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PlatformService;
import com.accenture.sdp.csmcc.services.ServiceTemplateService;


@ManagedBean(name = ApplicationConstants.PLATFORM_TABLE_BEAN_NAME)
@SessionScoped
public class PlatformManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlatformBean selectedBean;
	private List<PlatformBean> platforms;
	private List<PlatformBean> notFilteredPlatforms;
	private String nameFilter;
	private String extIdFilter;
	private PlatformBean previusSelectedBean;
	private LoggerWrapper log;


	public PlatformManager() {
		nameFilter = "";
		extIdFilter = "";
		setSortColumn("platformName");
		log = new LoggerWrapper(PlatformManager.class);
	}



	public PlatformBean getPreviusSelectedBean() {
		return previusSelectedBean;
	}



	public void setPreviusSelectedBean(PlatformBean previusSelectedBean) {
		this.previusSelectedBean = previusSelectedBean;
	}


	public List<PlatformBean> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<PlatformBean> platforms) {
		this.platforms = platforms;
	}


	public PlatformBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(PlatformBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public String getExtIdFilter() {
		return extIdFilter;
	}

	public void setExtIdFilter(String extIdFilter) {
		this.extIdFilter = extIdFilter;
	}


	public void deletePlatform(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String delete = MessageConstants.DELETE_PLATFORM;
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->"+delete, null);
		selectedBean=((PlatformBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			PlatformService service = Utilities.findBean(ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME, PlatformService.class);
			service.deletePlatform(((PlatformBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM)).getPlatformId(), Utilities.getTenantName());
			mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.DELETE_PLATFORM_MESSAGE),
					((PlatformBean) event.getComponent().getAttributes().get("item")).getPlatformName());
			code = ApplicationConstants.CODE_OK;
			refreshTable();
			
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();
			
		}
		msgBean.openPopup(mex);
		//logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString()+"->"+delete, code,mex);
	}


	public void addPlatform(ActionEvent event) {
		MenuController.redirectbyParam(PathConstants.ADD_PLATFORM);
		this.selectedBean = new PlatformBean();
		log.logStartFeature(null);
	}

	public void modifyPlatform(ActionEvent event) {
		selectedBean = ((PlatformBean) event.getComponent().getAttributes().get("item"));
		selectedBean.storeValues();
		MenuController.redirectbyParam(PathConstants.UPDATE_PLATFORM);
		log.logStartFeature(null);
	}


	public void relatedServiceTemplates(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		ServiceTemplateService relatedService = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME, ServiceTemplateService.class);
		ServiceTemplateManager serviceTemplateTableBean = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_TABLE_BEAN_NAME, ServiceTemplateManager.class);
		PlatformBean platform=((PlatformBean) event.getComponent().getAttributes().get("item"));

		try {
			// just to check
			relatedService.searchServiceTemplatesByPlatform(platform.getPlatformName(), 0L, 1L, Utilities.getTenantName());
			infoSessionBean.setPlatformBean(platform);
			serviceTemplateTableBean.setParentPlatform(platform);
			MenuController.redirectbyParam(PathConstants.SERVICETEMPLATE);
			log.logStartFeature(null);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		}
	}


	public void nameFilterListener(ValueChangeEvent event) {
		nameFilter = event.getNewValue().toString();
		filterResult();
	}

	public void extIdFilterListener(ValueChangeEvent event) {
		extIdFilter = event.getNewValue().toString();
		filterResult();
	}

	public void filterResult(){
		if (nameFilter.equals("") && extIdFilter.equals("")) {
			refreshTable();
		} else {
			// Valorized filters
			platforms = new ArrayList<PlatformBean>();
			for (PlatformBean p : notFilteredPlatforms) {
				boolean nameFilterFlag  = nameFilter.equals("") || p.getPlatformName().toUpperCase().indexOf(nameFilter.toUpperCase()) == 0 ;
				boolean extIdFilterFlag = extIdFilter.equals("") || p.getPlatformExtId() != null && p.getPlatformExtId().toUpperCase().indexOf(extIdFilter.toUpperCase()) == 0;
				if (nameFilterFlag && extIdFilterFlag) {
					platforms.add(p);
				}
			}
		}
	}

	public void sortTable(ActionEvent event) {
		PlatformComparator comparator = new PlatformComparator(getSortColumn(), isAscending());
		Collections.sort(platforms, comparator);
	}

	public void refreshTable() {
		platforms = new ArrayList<PlatformBean>();
		try {
			notFilteredPlatforms = PlatformBusiness.getBufferedData(0, 0);
			for (PlatformBean platform : notFilteredPlatforms) {
				platforms.add(platform);
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		}
		setSortColumn(PlatformBean.PLATFORM_CREATION_DATE_FIELD);
		setAscending(false);
		sortTable(null);
		nameFilter = "";
		extIdFilter = "";	
	}

	public void askChangeStatus(ActionEvent event) {
		selectedBean = (PlatformBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.PLATFORM);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}


	public void changeStatusOfSelectedBean() {
		PlatformBusiness.changeStatus(selectedBean,  getChangeStatusValue());
	}

}
