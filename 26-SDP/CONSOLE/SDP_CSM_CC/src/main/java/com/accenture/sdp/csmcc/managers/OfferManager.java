package com.accenture.sdp.csmcc.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.xml.datatype.DatatypeConfigurationException;

import com.accenture.sdp.csmcc.beans.OfferBean;
import com.accenture.sdp.csmcc.business.OfferBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.OfferComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.OfferService;
import com.accenture.sdp.csmfe.webservices.clients.offer.BaseResp;

@ManagedBean(name = ApplicationConstants.OFFER_MANAGER)
@SessionScoped
public class OfferManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OfferBean selectedBean;
	private List<OfferBean> filteredList;
	private List<OfferBean> offers;
	private List<SelectItem> categoryList;
	private Map<String, String> categoryMap;
	private OfferBean[] selectedList;

	public OfferManager() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String[] types = propertyManager.getProperty(ApplicationConstants.OFFER_TYPES_AVS).split(";");
		String[] labels = propertyManager.getProperty(ApplicationConstants.OFFER_TYPES_AVS_LABEL).split(";");
		categoryList = new ArrayList<SelectItem>();
		categoryMap = new HashMap<String, String>();
		int index = 0;
		for (String type : types) {
			categoryList.add(new SelectItem(type, labels[index]));
			categoryMap.put(type, labels[index]);
			index++;
		}
	}

	public OfferBean[] getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(OfferBean[] selectedList) {
		this.selectedList = selectedList;
	}

	public List<OfferBean> getoffers() {
		return offers;
	}

	public void setoffers(List<OfferBean> offers) {
		this.offers = offers;
	}

	public OfferBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(OfferBean selectedBean) {
		this.selectedBean = selectedBean;
	}


	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}

	public String gotoAddTechnicalPackage() {

		selectedBean = new OfferBean();
		MenuController.redirectbyParam(PathConstants.TECHNICAL_PACKAGE_ADD);
		return null;

	}

	public void addTechnicalPackage(ActionEvent event) throws DatatypeConfigurationException {
		OfferBusiness.addOffer(selectedBean);
	}

	public String gotoUpdateTechnicalPackage(ActionEvent event) {
		selectedBean = (OfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		selectedBean.storeValues();
		MenuController.redirectbyParam(PathConstants.TECHNICAL_PACKAGE_UPDATE);
		return null;
	}

	public String gotoViewDigitalGoods(ActionEvent event) {
		selectedBean = (OfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		try {
			OfferBusiness.loadDigitalGoods(selectedBean);
			MenuController.redirectbyParam(PathConstants.DIGITAL_GOODS_VIEW);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(OfferManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}
		return null;
	}

	public String updateTechnicalPackage() {
		OfferBusiness.updateOffer(selectedBean);
		selectedBean.setTypeLabel(categoryMap.get(selectedBean.getTypeAVS()));
		return null;
	}

	public void sortTable(ActionEvent event) {
		OfferComparator comparator = new OfferComparator(getSortColumn(), isAscending());
		Collections.sort(offers, comparator);
	}

	public int sortByName(Object o1, Object o2) {
		OfferBean c1 = (OfferBean) o1;
		OfferBean c2 = (OfferBean) o1;
		return c1.getOfferName().compareToIgnoreCase(c2.getOfferName());
	}

	public void refreshTable() {
		try {
			offers = OfferBusiness.searchAllOffer();
			for (OfferBean o : offers) {
				o.setTypeLabel(categoryMap.get(o.getTypeAVS()));
			}
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(offers);
			}
			setSortColumn(OfferBean.OFFER_CREATION_DATE_FIELD);
			setAscending(false);
			sortTable(null);
			Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class).setTechnicalPackageCounter(offers.size());
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(OfferManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}

	}

	public void askChangeStatus(ActionEvent event) {
		selectedBean = (OfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OFFER);
		String message = null;
		if (StatusConstants.INACTIVE.equals(status)) {
			if (selectedList != null && selectedList.length > 0){
				OfferService service = Utilities.findBean(ApplicationConstants.OFFER_SERVICE_BEAN_NAME, OfferService.class);
				for (OfferBean bean : selectedList) {
					BaseResp resp = service.isOfferSubscribed(bean.getOfferId(), Utilities.getTenantName());
					if (resp != null && resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
						PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
						message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_MESSAGE_COMMERCIAL_PACKAGE_ASSIGNED), bean.getOfferName());
						msgBean.openPopup(message);
						return;
					}
				}
				if (selectedList.length == 1) {
					message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
				} else {
					message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_INACTIVATE_SOME_ITEM);
				}
			}  else {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NO_ITEM_SELECTED));
			}
		} else {
			selectedBean = (OfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);     
			message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
		}
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}

	public void changeStatusOfSelectedBean() {
		OfferBusiness.changeStatus(selectedBean, selectedList, getChangeStatusValue());
		selectedList = null;
	}

	public List<SelectItem> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<SelectItem> categoryList) {
		this.categoryList = categoryList;
	}

	public List<OfferBean> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<OfferBean> filteredList) {
		this.filteredList = filteredList;
	}

}
