package com.accenture.sdp.csmcc.managers;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.beans.PriceBean;
import com.accenture.sdp.csmcc.business.PriceBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.PriceComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;

@ManagedBean(name = ApplicationConstants.PRICE_MANAGER)
@SessionScoped
public class PriceManager  extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PriceBean selectedBean;
	private List<PriceBean> prices;
	private List<PriceBean> filteredList;
	
	public List<PriceBean> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceBean> prices) {
		this.prices = prices;
	}

	
	public PriceManager() {
		super();
	}
	
	public PriceBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(PriceBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	

	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}

	public String gotoAddPrice() {
		selectedBean = new PriceBean();
		MenuController.redirectbyParam(PathConstants.PRICE_ADD);
		return null;
	}
	
	public void addPrice(ActionEvent event) {
		PriceBusiness.addPrice(selectedBean);
	}
	
	public void sortTable() {
			PriceComparator comparator = new PriceComparator(getSortColumn(), isAscending());
			Collections.sort(prices, comparator);
	}
	
	public void refreshTable() {
		try {
			prices = PriceBusiness.searchAllPrice();
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(prices);
			}
			setSortColumn(PriceBean.PRICE_CREATION_DATE_FIELD);
			setAscending(false);
			sortTable();
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			LoggerWrapper log = new LoggerWrapper(PriceManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}	
		
	}

	
	public void askChangeStatus(ActionEvent event) {
		selectedBean = (PriceBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.PRICE);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}
	
	
	
	
	public void changeStatusOfSelectedBean() {
		PriceBusiness.deletePrice(selectedBean);
		refreshTable();
	}

	public List<PriceBean> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<PriceBean> filteredList) {
		this.filteredList = filteredList;
	}
}
