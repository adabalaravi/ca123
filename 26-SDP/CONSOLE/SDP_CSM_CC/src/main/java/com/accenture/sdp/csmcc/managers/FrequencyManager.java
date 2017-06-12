package com.accenture.sdp.csmcc.managers;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.beans.FrequencyBean;
import com.accenture.sdp.csmcc.business.FrequencyBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.FrequencyComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;

@ManagedBean(name = ApplicationConstants.FREQUENCY_MANAGER)
@SessionScoped
public class FrequencyManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrequencyBean selectedBean;
	private List<FrequencyBean> frequencies;
	private List<FrequencyBean> filteredList;

	public FrequencyManager() {
	}

	public List<FrequencyBean> getFrequencies() {
		return frequencies;
	}

	public void setFrequencies(List<FrequencyBean> frequencies) {
		this.frequencies = frequencies;
	}

	public FrequencyBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(FrequencyBean selectedBean) {
		this.selectedBean = selectedBean;
	}




	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}


	public String gotoAddFrequency() {
		selectedBean = new FrequencyBean();
		MenuController.redirectbyParam(PathConstants.FREQUENCY_ADD);
		return null;
	}
	
	public void addFrequency(ActionEvent event) {
		FrequencyBusiness.addFrequency(selectedBean);
	}
	
	

	public void sortTable(ActionEvent event) {
		FrequencyComparator comparator= new FrequencyComparator(getSortColumn(), isAscending());
		Collections.sort(frequencies, comparator);
	}

	public void refreshTable() {
		try {
			frequencies = FrequencyBusiness.searchAllFrequencies();
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(frequencies);
			}
			setSortColumn(FrequencyBean.FREQUENCY_CREATION_DATE_FIELD);
			setAscending(false);
			sortTable(null);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(FrequencyManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}

	}

	public void askChangeStatus(ActionEvent event) {
		selectedBean = (FrequencyBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.FREQUENCY);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}



	public void changeStatusOfSelectedBean() {
		FrequencyBusiness.deleteFrequency(selectedBean);
		refreshTable();
	}

	public List<FrequencyBean> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<FrequencyBean> filteredList) {
		this.filteredList = filteredList;
	}

}
