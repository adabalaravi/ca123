package com.accenture.sdp.csmcc.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.beans.ServiceTypeBean;
import com.accenture.sdp.csmcc.business.PlatformBusiness;
import com.accenture.sdp.csmcc.business.ServiceTemplateBusiness;
import com.accenture.sdp.csmcc.business.ServiceTypeBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.ServiceTemplateComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.ServiceTemplateService;
import com.accenture.sdp.csmcc.services.ServiceVariantService;


@ManagedBean(name = ApplicationConstants.SERVICETEMPLATE_TABLE_BEAN_NAME)
@SessionScoped
public class ServiceTemplateManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlatformBean parentPlatform;
	private ServiceTemplateBean selectedBean;
	private List<ServiceTemplateBean> serviceTemplates;
	private List<String> platformNameFilterList = new ArrayList<String>();
	private static transient LoggerWrapper log = new LoggerWrapper(ServiceTemplateManager.class);
	private ServiceTemplateBean previusSelectedBean;
	
	private List<SelectItem> notFilteredplatformNameList = new ArrayList<SelectItem>();
	private List<SelectItem> platformNameList = new ArrayList<SelectItem>();
	private List<SelectItem> serviceTypeNameList = new ArrayList<SelectItem>();
	private List<SelectItem> notFilteredServiceTypeNameList = new ArrayList<SelectItem>();



	public ServiceTemplateManager() {
		super();
	}


	public ServiceTemplateBean getPreviusSelectedBean() {
		return previusSelectedBean;
	}


	public void setPreviusSelectedBean(ServiceTemplateBean previusSelectedBean) {
		this.previusSelectedBean = previusSelectedBean;
	}

	public List<String> getPlatformNameFilterList() {
		return platformNameFilterList;
	}


	public void setPlatformNameFilterList(List<String> platformNameFilterList) {
		this.platformNameFilterList = platformNameFilterList;
	}


	public PlatformBean getParentPlatform() {
		return parentPlatform;
	}


	public void setParentPlatform(PlatformBean parentPlatform) {
		this.parentPlatform = parentPlatform;
		refreshTable();
	}

	public void clearParentPlatform() {
		this.parentPlatform = null;
	}

	
	public List<ServiceTemplateBean> getServiceTemplates() {
		return serviceTemplates;
	}

	public void setServiceTemplates(List<ServiceTemplateBean> serviceTemplates) {
		this.serviceTemplates = serviceTemplates;
	}


	public ServiceTemplateBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(ServiceTemplateBean selectedBean) {
		this.selectedBean = selectedBean;
	}


	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}

	public void addServiceTemplate(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		try {
			if (infoSessionBean.getPlatformBean() != null) {
				ServiceTemplateBean templateBean = new ServiceTemplateBean();
				templateBean.setPlatformId(infoSessionBean.getPlatformBean().getPlatformId());
				templateBean.setPlatformName(infoSessionBean.getPlatformBean().getPlatformName());
				templateBean.setParentReadOnly(true);
				this.setSelectedBean(templateBean);
				List<SelectItem> selectList=new ArrayList<SelectItem>();
				List<ServiceTypeBean> typeList = ServiceTypeBusiness.searchAllServiceTypes();
				for(ServiceTypeBean b:typeList) {
					selectList.add(new SelectItem(String.valueOf(b.getServiceTypeId()),b.getServiceTypeName()));
				}
				this.setServiceTypeNameList(selectList);
				MenuController.redirectbyParam(PathConstants.ADD_SERVICETEMPLATE);
			} else {
				
				ServiceTemplateBean templateBean = new ServiceTemplateBean();
				this.setSelectedBean(templateBean);
				List<SelectItem> sList=new ArrayList<SelectItem>();
				List<PlatformBean> list= PlatformBusiness.getBufferedData(0L, 0L);
				for(PlatformBean b:list){
					sList.add(new SelectItem(String.valueOf(b.getPlatformId()),b.getPlatformName()));
				}
				this.setPlatformNameList(sList);
				
				List<SelectItem> selectList=new ArrayList<SelectItem>();
				List<ServiceTypeBean> typeList = ServiceTypeBusiness.searchAllServiceTypes();
				for(ServiceTypeBean b:typeList) {
					selectList.add(new SelectItem(String.valueOf(b.getServiceTypeId()),b.getServiceTypeName()));
				}
				this.setServiceTypeNameList(selectList);
				MenuController.redirectbyParam(PathConstants.ADD_SERVICETEMPLATE);
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		}

	}

	public void updateServiceTemplate(ActionEvent event) {
		// logging
		selectedBean = ((ServiceTemplateBean) event.getComponent().getAttributes().get("item"));
		selectedBean.storeValues();
		List<SelectItem> sList=new ArrayList<SelectItem>();
		try {
			List<ServiceTypeBean> typeList = ServiceTypeBusiness.searchAllServiceTypes();
			for(ServiceTypeBean b : typeList){
				sList.add(new SelectItem(String.valueOf(b.getServiceTypeId()),b.getServiceTypeName()));
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		}
		this.setServiceTypeNameList(sList);
		MenuController.redirectbyParam(PathConstants.UPDATE_SERVICETEMPLATE);

	}

	public void relatedServiceVariants(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		ServiceVariantManager serviceVariantTableBean = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_TABLE_BEAN_NAME,ServiceVariantManager.class);
		ServiceVariantService relatedService = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME, ServiceVariantService.class);
		ServiceTemplateBean template=((ServiceTemplateBean) event.getComponent().getAttributes().get("item"));

		try {
			// just to check
			relatedService.searchServiceVariantsByServiceTemplate(template.getServiceTemplateName(), 0L, 1L, Utilities.getTenantName());
			infoSessionBean.setServiceTemplate(template);
			serviceVariantTableBean.setParentTemplate(template);
			MenuController.redirectbyParam(PathConstants.SERVICEVARIANT);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		}
	}



	public void sortTable(ActionEvent event) {
		ServiceTemplateComparator comparator = new ServiceTemplateComparator(getSortColumn(), isAscending());
		Collections.sort(serviceTemplates, comparator);
	}

	public void refreshTable() {
		try {
			serviceTemplates = ServiceTemplateBusiness.getBufferedData(0, 0);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		}	
		setSortColumn(ServiceTemplateBean.SERVICETEMPLATE_CREATION_DATE_FIELD);
		setAscending(false);

		sortTable(null);
	}


	public void askChangeStatus(ActionEvent event) {
		selectedBean = (ServiceTemplateBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SERVICE_TEMPLATE);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}

	public void changeStatusOfSelectedBean() {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		ServiceTemplateService service = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME, ServiceTemplateService.class);

		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SERVICE_TEMPLATE);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + getChangeStatusValue());
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->" + operation + " " + entityName, null);

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			if (StatusConstants.DELETED.equals(getChangeStatusValue())){
				service.deleteServiceTemplate(selectedBean.getServiceTemplateId(), Utilities.getTenantName());
			} else {
				service.changeStatus(selectedBean.getServiceTemplateId(), getChangeStatusValue(), Utilities.getTenantName());	
			}
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			mex = String.format(message, operation + " " + entityName, selectedBean.getServiceTemplateName());
			code = ApplicationConstants.CODE_OK;
			selectedBean.setServiceTemplateStatus(getChangeStatusValue());
			
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();
			
		}
		msgBean.openPopup(mex);
		//logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString()+"->"+ operation + " " + entityName, code,mex);
	}

	
	public List<SelectItem> getPlatformNameList() {
		return platformNameList;
	}




	public void setPlatformNameList(List<SelectItem> platformNameList) {
		this.platformNameList = platformNameList;
		this.notFilteredplatformNameList.clear();
		for(SelectItem item:(List<SelectItem>)platformNameList) {
			this.notFilteredplatformNameList.add(item);
		}
	}




	public List<SelectItem> getServiceTypeNameList() {
		return serviceTypeNameList;
	}

	public void setServiceTypeNameList(List<SelectItem> serviceTypeNameList) {
		this.serviceTypeNameList = serviceTypeNameList;
		this.notFilteredServiceTypeNameList.clear();
		for(SelectItem item:(List<SelectItem>)serviceTypeNameList) {
			this.notFilteredServiceTypeNameList.add(item);
		}
	}




}
