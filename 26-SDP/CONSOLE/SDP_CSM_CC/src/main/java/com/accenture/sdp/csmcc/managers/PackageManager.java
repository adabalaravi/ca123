package com.accenture.sdp.csmcc.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.business.PackageBusiness;
import com.accenture.sdp.csmcc.business.PackageFormUtil;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PackageService;
import com.accenture.sdp.csmfe.webservices.clients.packages.BaseResp;

@ManagedBean(name = ApplicationConstants.PACKAGE_MANAGER)
@SessionScoped
public class PackageManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PackageBean selectedBean;
	private List<PackageBean> offerDetails;
	private List<PackageBean> filteredList;
	private List<PackageBean> formOfferDetails;
	private PackageFormUtil formUtil;
	private BigDecimal rcPriceTotal;
	private BigDecimal nrcPriceTotal;
	private PackageBean[] selectedList;

	public PackageManager() {
		formUtil = new PackageFormUtil();
	}

	public List<PackageBean> getOfferDetails() {
		return offerDetails;
	}

	public void setOfferDetails(List<PackageBean> offerDetails) {
		this.offerDetails = offerDetails;
	}

	public List<PackageBean> getFormOfferDetails() {
		return formOfferDetails;
	}

	public void setFormOfferDetails(List<PackageBean> formOfferDetails) {
		this.formOfferDetails = formOfferDetails;
	}

	public PackageFormUtil getFormUtil() {
		return formUtil;
	}

	public void setFormUtil(PackageFormUtil formUtil) {
		this.formUtil = formUtil;
	}

	public PackageBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(PackageBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}

	public void addOfferDetail(ActionEvent event) {
		formOfferDetails = new ArrayList<PackageBean>();
		formOfferDetails.add(new PackageBean());
		ArrayList<String> offerNames = new ArrayList<String>();
		for (PackageBean bean : offerDetails) {
			offerNames.add(bean.getOfferName());
		}
		formUtil.initialize(formOfferDetails, offerNames, true);
		formOfferDetails.get(0).setRcFrequencyTypeId(offerDetails.get(0).getRcFrequencyTypeId());
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_PACKAGE_ADD);

	}

	public void cancelFields() {
		formUtil.resetOfferDetailList(formOfferDetails);
	}

	public void resetOfferDetail() {
		if (formOfferDetails != null) {
			for (PackageBean bean : formOfferDetails) {
				bean.resetOfferDetail();
			}
		}
	}

	public void storeValues() {
		if (formOfferDetails != null) {
			for (PackageBean bean : formOfferDetails) {
				bean.storeValues();
			}
		}
	}

	public void finishAddOfferDetail()  {
		for (PackageBean pack : formOfferDetails) {
			formUtil.setRelatedData(pack);
			PackageBusiness.addPackage(pack);
		}
	}

	public void updateOfferDetail(ActionEvent event) {
		selectedBean = (PackageBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		selectedBean.storeValues();
		selectedBean.setFrequencyMap(formUtil.getFrequencyMap());
		formOfferDetails = new ArrayList<PackageBean>();
		formOfferDetails.add(selectedBean);
		formUtil.initialize(formOfferDetails, null, false);
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_PACKAGE_UPDATE);
	}

	public void finishUpdateOfferDetail() {
		for (PackageBean pack : formOfferDetails) {
			PackageBusiness.updatePackage(pack);
		}
	}

	public void refreshTable() {
		offerDetails = new ArrayList<PackageBean>();
		try {
			offerDetails = PackageBusiness.searchPackagesBySolutionOffer();
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(offerDetails);
			}
			
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(PackageManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}
	}

	

	public BigDecimal getRcPriceTotal() {
		return rcPriceTotal;
	}

	public void setRcPriceTotal(BigDecimal rcPriceTotal) {
		this.rcPriceTotal = rcPriceTotal;
	}

	public BigDecimal getNrcPriceTotal() {
		return nrcPriceTotal;
	}

	public void setNrcPriceTotal(BigDecimal nrcPriceTotal) {
		this.nrcPriceTotal = nrcPriceTotal;
	}

	public void calculateTotalPrice() {
		rcPriceTotal = BigDecimal.ZERO;
		nrcPriceTotal = BigDecimal.ZERO;
		if (offerDetails != null) {
			for (PackageBean offerDetail : offerDetails) {
				rcPriceTotal = rcPriceTotal.add(offerDetail.getRcPriceCatalog());
				nrcPriceTotal = nrcPriceTotal.add(offerDetail.getNrcPriceCatalog());
			}
		}

	}

	public List<PackageBean> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<PackageBean> filteredList) {
		this.filteredList = filteredList;
	}

	public PackageBean[] getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(PackageBean[] selectedList) {
		this.selectedList = selectedList;
	}

	
	public void askChangeStatus(ActionEvent event) {
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);
		String message = null;
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OFFER_DETAIL);
		if (StatusConstants.INACTIVE.equals(status)){
			if (selectedList != null && selectedList.length > 0){
				PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
				for (PackageBean bean : selectedList) {
					BaseResp resp = service.isPackageSubscribed(bean.getPackageId(), Utilities.getTenantName());
					if (resp != null && resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
						PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
						message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_MESSAGE_TECHNICAL_PACKAGE_ASSIGNED), bean.getOfferName());
						msgBean.openPopup(message);
						return;
					}
				}
				if (selectedList.length == 1) {
					message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
				} else {
					message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_INACTIVATE_SOME_ITEM);
				}
			} else {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NO_ITEM_SELECTED));
			}
		} else {
			selectedBean = (PackageBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
			message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
		}
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		confirm.setTableBean(this);
		confirm.openPopup(message);

	}

	public void changeStatusOfSelectedBean() {
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OFFER_DETAIL);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + getChangeStatusValue());
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->" + operation + " " + entityName, null);

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			if (StatusConstants.DELETED.equals(getChangeStatusValue())){
				service.deleteOfferDetail(selectedBean.getPackageId(), Utilities.getTenantName());
				mex = String.format(message, operation + " " + entityName, selectedBean.getOfferName());
			} else if (StatusConstants.INACTIVE.equals(getChangeStatusValue())) {
				if (selectedList != null && selectedList.length > 0){
					for (PackageBean bean : selectedList) {
						service.packageChangeStatus(bean.getPackageId(), StatusConstants.INACTIVE, Utilities.getTenantName());
						bean.setStatusName(StatusConstants.INACTIVE);
					}
				}
				if (selectedList.length == 1) {
					mex = String.format(message, operation + " " + entityName, selectedList[0].getOfferName());			
				} else {
					mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.INACTIVATE_SOME_ITEM_OK);		
				}
				selectedList = null;
			} else {
				service.packageChangeStatus(selectedBean.getPackageId(), getChangeStatusValue(), Utilities.getTenantName());	
				selectedBean.setStatusName(getChangeStatusValue());
				mex = String.format(message, operation + " " + entityName, selectedBean.getOfferName());
			}
			code = ApplicationConstants.CODE_OK;
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();

		}
		msgBean.openPopup(mex);

		//logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString()+"->"+operation, code,mex);
	}
	
	


}
