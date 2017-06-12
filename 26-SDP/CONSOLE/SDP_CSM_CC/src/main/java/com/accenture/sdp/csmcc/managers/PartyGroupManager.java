package com.accenture.sdp.csmcc.managers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.business.PartyGroupBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.comparators.PartyGroupComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PartyGroupService;


@ManagedBean(name = ApplicationConstants.PARTY_GROUP_MANAGER)
@SessionScoped
public class PartyGroupManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PartyGroupBean selectedBean;
	private List<PartyGroupBean> filteredList;
	private List<PartyGroupBean> partyGroups;
	private PartyGroupBean previusSelectedBean;
	private LoggerWrapper log;


	public PartyGroupManager() {
		setSortColumn(PartyGroupBean.PARTY_GROUP_NAME);
		log = new LoggerWrapper(PartyGroupManager.class);
	}



	public PartyGroupBean getPreviusSelectedBean() {
		return previusSelectedBean;
	}



	public void setPreviusSelectedBean(PartyGroupBean previusSelectedBean) {
		this.previusSelectedBean = previusSelectedBean;
	}

	public PartyGroupBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(PartyGroupBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public void deletePartyGroup(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String delete = MessageConstants.DELETE_PLATFORM;
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->"+delete, null);
		selectedBean=((PartyGroupBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			
			PartyGroupService service = Utilities.findBean(ApplicationConstants.PARTYGROUP_SERVICE_BEAN_NAME, PartyGroupService.class);
			service.deletePartyGroup(((PartyGroupBean) event.getComponent().getAttributes().get("item")).getPartyGroupId(), Utilities.getTenantName());
			mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.DELETE_PARTY_GROUP_MESSAGE),
					((PartyGroupBean) event.getComponent().getAttributes().get("item")).getPartyGroupName());
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


	public void gotoAddPartyGroup(ActionEvent event) {
		MenuController.redirectbyParam(PathConstants.CLUSTER_ADD);
		this.selectedBean = new PartyGroupBean();
		log.logStartFeature(null);
	}
	
	public void addPartyGroup(ActionEvent event) {
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.PARTY_GROUP_VALIDATION_NAME_FIELD, selectedBean.getPartyGroupName());
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			
			String partyGroupName = selectedBean.getPartyGroupName();
			String partyGroupDescription = selectedBean.getPartyGroupDescription();
			
			String nameLbl = MessageConstants.PARTY_GROUP_NAME;
			String descLbl = MessageConstants.PARTY_GROUP_DESCRIPTION;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(nameLbl, partyGroupName);
			logMap.put(descLbl, partyGroupDescription);
			log.logStartFeature(logMap);
			logMap.clear();
			PartyGroupService service = Utilities.findBean(ApplicationConstants.PARTYGROUP_SERVICE_BEAN_NAME, PartyGroupService.class);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			
			String code;
			String mex;
			try{
				service.createPartyGroup(partyGroupName, partyGroupDescription, Utilities.getTenantName());
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE), 
	            		Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_PARTY_GROUP), partyGroupName);
				
				setUpdatesFlag(false);
				msgBean.setUpdateFlag(false);
				msgBean.openPopup(mex);
				
				msgBean.setNextParam(PathConstants.CLUSTER_VIEW);
				code = ApplicationConstants.CODE_OK;
			} catch (ServiceErrorException see) {
				code = see.getCode();
				mex = see.getMessage();
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}
	}

	public void gotoUpdatePartyGroup(ActionEvent event) {
		selectedBean = ((PartyGroupBean) event.getComponent().getAttributes().get("item"));
		selectedBean.storeValues();
		MenuController.redirectbyParam(PathConstants.CLUSTER_UPDATE);
		log.logStartFeature(null);
	}
	
	public void updatePartyGroup(ActionEvent event) {
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.PARTY_GROUP_VALIDATION_NAME_FIELD, selectedBean.getPartyGroupName());
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			Long partygroupId = selectedBean.getPartyGroupId();
			String partyGroupName = selectedBean.getPartyGroupName();
			String partyGroupDescription = selectedBean.getPartyGroupDescription();
			
			String nameLbl = MessageConstants.PARTY_GROUP_NAME;
			String descLbl = MessageConstants.PARTY_GROUP_DESCRIPTION;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(nameLbl, partyGroupName);
			logMap.put(descLbl, partyGroupDescription);
			log.logStartFeature(logMap);
			logMap.clear();
			PartyGroupService service = Utilities.findBean(ApplicationConstants.PARTYGROUP_SERVICE_BEAN_NAME, PartyGroupService.class);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			
			String code;
			String mex;
			try{
				service.modifyPartyGroup(partygroupId, partyGroupName, partyGroupDescription, Utilities.getTenantName());
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE), 
	            		Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.UPDATE_PARTY_GROUP), partyGroupName);
				
				setUpdatesFlag(true);
				
				msgBean.setUpdateFlag(true);
				msgBean.setNextParam(PathConstants.CLUSTER_VIEW);
				code = ApplicationConstants.CODE_OK;
			} catch (ServiceErrorException see) {
				
				code = see.getCode();
				mex = see.getMessage();
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}
	}
	
	

	public void sortTable(ActionEvent event) {
		PartyGroupComparator comparator = new PartyGroupComparator(getSortColumn(), isAscending());
		Collections.sort(partyGroups, comparator);
	}

	public void refreshTable() {
		try {
			partyGroups = PartyGroupBusiness.searchAllPartyGroups();
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(partyGroups);
			}
			setSortColumn(PartyGroupBean.CREATION_DATE_FIELD);
			setAscending(false);
			sortTable(null);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			log.logEndFeature(e.getCode(),e.getMessage());
		} 
		
	}

	public void askChangeStatus(ActionEvent event) {
		selectedBean = (PartyGroupBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.PARTY_GROUP);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}



	public List<PartyGroupBean> getPartyGroups() {
		return partyGroups;
	}



	public void setPartyGroups(List<PartyGroupBean> partyGroups) {
		this.partyGroups = partyGroups;
	}



	@Override
	public void changeStatusOfSelectedBean() {
		
		
	}



	public List<PartyGroupBean> getFilteredList() {
		return filteredList;
	}



	public void setFilteredList(List<PartyGroupBean> filteredList) {
		this.filteredList = filteredList;
	}

}
