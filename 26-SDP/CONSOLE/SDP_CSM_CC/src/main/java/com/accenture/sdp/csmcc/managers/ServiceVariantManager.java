package com.accenture.sdp.csmcc.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.beans.ServiceVariantBean;
import com.accenture.sdp.csmcc.business.ServiceTemplateBusiness;
import com.accenture.sdp.csmcc.business.ServiceVariantBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.ServiceVariantComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PlatformService;


@ManagedBean(name = ApplicationConstants.SERVICEVARIANT_TABLE_BEAN_NAME)
@SessionScoped
public class ServiceVariantManager extends BaseManager {

	private static final long serialVersionUID = 1L;
	private ServiceTemplateBean parentTemplate;
	private boolean details;
	private ServiceVariantBean selectedBean;
	private List<ServiceVariantBean> serviceVariants;
	private List<String> templateNameFilterList = new ArrayList<String>();
	private List<String> platformNameFilterList = new ArrayList<String>();
	private ServiceVariantBean previusSelectedBean;	
	private List<SelectItem> notFilteredTemplateNameList = new ArrayList<SelectItem>();
	private List<SelectItem> templateNameList = new ArrayList<SelectItem>();
	

	public ServiceVariantManager() {
		details=false;
	}


	public boolean isDetails() {
		return details;
	}


	public void setDetails(boolean details) {
		this.details = details;
	}


	public ServiceVariantBean getPreviusSelectedBean() {
		return previusSelectedBean;
	}


	public void setPreviusSelectedBean(ServiceVariantBean previusSelectedBean) {
		this.previusSelectedBean = previusSelectedBean;
	}



	public List<String> getPlatformNameFilterList() {
		return platformNameFilterList;
	}


	public void setPlatformNameFilterList(List<String> platformNameFilterList) {
		this.platformNameFilterList = platformNameFilterList;
	}





	public List<String> getTemplateNameFilterList() {
		return templateNameFilterList;
	}


	public void setTemplateNameFilterList(List<String> templateNameFilterList) {
		this.templateNameFilterList = templateNameFilterList;
	}



	public ServiceTemplateBean getParentTemplate() {
		return parentTemplate;
	}


	public void setParentTemplate(ServiceTemplateBean parentTemplate) {
		this.parentTemplate = parentTemplate;
		refreshTable();
	}

	public void clearParentTemplate() {
		this.parentTemplate = null;
	}

	public List<ServiceVariantBean> getServiceVariants() {
		return serviceVariants;
	}

	public void setServiceVariants(List<ServiceVariantBean> serviceVariants) {
		this.serviceVariants = serviceVariants;
	}

	public ServiceVariantBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(ServiceVariantBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public void addServiceVariant(ActionEvent event) {
		LoggerWrapper log = new LoggerWrapper(ServiceVariantManager.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		if (infoSessionBean.getServiceTemplate() != null) {
			ServiceVariantBean variant = new ServiceVariantBean();
			variant.setParentServiceTemplate(infoSessionBean.getServiceTemplate());
			variant.setTemplateName(infoSessionBean.getServiceTemplate().getServiceTemplateName());
			variant.setParentReadOnly(true);
			this.setSelectedBean(variant);
			MenuController.redirectbyParam(PathConstants.ADD_SERVICEVARIANT);
		} else {
			try {			
				ServiceVariantBean variant = new ServiceVariantBean();
				this.setSelectedBean(variant);
				List<ServiceTemplateBean> list= ServiceTemplateBusiness.getBufferedData(0L, 0L);
				List<SelectItem> sList=new ArrayList<SelectItem>();
				for(ServiceTemplateBean b:list){
					sList.add(new SelectItem(b, b.getServiceTemplateName()));
				}
				this.setTemplateNameList(sList);
				MenuController.redirectbyParam(PathConstants.ADD_SERVICEVARIANT);
			} catch (ServiceErrorException e) {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(e.getMessage());
				
				log.logEndFeature(e.getCode(),e.getMessage());
			}
		}
	}

	public void updateServiceVariant(ActionEvent event) {
		// logging
		selectedBean = ((ServiceVariantBean) event.getComponent().getAttributes().get("item"));
		selectedBean.storeValues();
		MenuController.redirectbyParam(PathConstants.UPDATE_SERVICEVARIANT);

	}

	public void relatedPlatform(ActionEvent event) {

		PlatformService platformService = Utilities.findBean(
				ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME,
				PlatformService.class);
		ServiceVariantBean variant=((ServiceVariantBean) event
				.getComponent().getAttributes().get("item"));

		try {
			// just to check
			platformService.searchPlatformByServiceVariant(variant.getServiceVariantName(), Utilities.getTenantName());
			details=true;
			selectedBean=variant;
			MenuController.redirectbyParam(PathConstants.DETAILS_SERVICEVARIANT);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
		}



	}


	public void sortTable(ActionEvent event) {
		ServiceVariantComparator comparator = new ServiceVariantComparator(getSortColumn(), isAscending());
		Collections.sort(serviceVariants, comparator);
	}

	public void refreshTable() {
		try {
			serviceVariants = ServiceVariantBusiness.getBufferedData(0, 0);
		}catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
		}

		setSortColumn(ServiceVariantBean.SERVICEVARIANT_CREATION_DATE_FIELD);
		setAscending(false);
		sortTable(null);
		
	}


	public void askChangeStatus(ActionEvent event) {
		selectedBean = (ServiceVariantBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SERVICE_VARIANT);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}


	public void changeStatusOfSelectedBean() {
		ServiceVariantBusiness.changeStatus(selectedBean, getChangeStatusValue());
	}
	
	public List<SelectItem> getTemplateNameList() {
		return templateNameList;
	}


	public void setTemplateNameList(List<SelectItem> templateNameList) {
		this.templateNameList = templateNameList;
		this.notFilteredTemplateNameList.clear();
		for(SelectItem item:(List<SelectItem>) templateNameList) {
			this.notFilteredTemplateNameList.add(item);
		}
	}


}
