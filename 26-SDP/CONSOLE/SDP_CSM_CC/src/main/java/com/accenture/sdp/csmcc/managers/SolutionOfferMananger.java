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

import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.beans.VoucherCampaignBean;
import com.accenture.sdp.csmcc.beans.VoucherInfo;
import com.accenture.sdp.csmcc.business.SolutionOfferBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.AvsConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.SolutionOfferComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PackageService;
import com.accenture.sdp.csmcc.services.SolutionOfferService;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.BaseResp;

@ManagedBean(name = ApplicationConstants.SOLUTION_OFFER_MANAGER)
@SessionScoped
public class SolutionOfferMananger extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SolutionOfferBean selectedBean;
	private List<SolutionOfferBean> filteredList;
	private List<SolutionOfferBean> solutionOffers;
	private SolutionOfferBusiness business;
	private SolutionOfferBean[] selectedList;
	private List<SelectItem> solutionOfferTypes = new ArrayList<SelectItem>();
	
	
	public SolutionOfferMananger(){
		business = new SolutionOfferBusiness();
		loadSolutionOfferTypes();
	}

	public List<SolutionOfferBean> getSolutionOffers() {
		return solutionOffers;
	}

	public void setSolutionOffers(List<SolutionOfferBean> solutionOffers) {
		this.solutionOffers = solutionOffers;
	}

	public SolutionOfferBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(SolutionOfferBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}

	public void relatedPackages(ActionEvent event) {
		LoggerWrapper log = new LoggerWrapper(SolutionOfferMananger.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		SolutionOfferBean solutionOffer = ((SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));
		selectedBean = solutionOffer;
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN_NAME, PackageService.class);
		try {
			// search just to check
			service.searchPackageByOfferAndSolutionOffer(null, selectedBean.getSolutionOfferName(), 0L, 1L, Utilities.getTenantName());
			infoSessionBean.setSolutionOfferBean(selectedBean);
			PackageManager packageManager = Utilities.findBean(ApplicationConstants.PACKAGE_MANAGER, PackageManager.class);
			packageManager.refreshTable();
			MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_PACKAGE_VIEW);
		} catch (ServiceErrorException e) {
			log.logException(e.getMessage(), e);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			log.logEndFeature(e.getCode(), e.getMessage());
		}
	}

	public void viewRelatedDiscount(ActionEvent event) {
		SolutionOfferBean solutionOffer = ((SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));
		selectedBean = solutionOffer;
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		SolutionOfferDiscountedManager discountManager = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_DISCOUNTED_MANAGER,
				SolutionOfferDiscountedManager.class);
		try {
			// search just to check
			service.searchDiscountedSolutionOfferBySolutionOffer(solutionOffer.getSolutionOfferName(), 0L, 1L, Utilities.getTenantName());
			discountManager.setParentSolutionOffer(selectedBean);
			MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_VIEW);
		} catch (ServiceErrorException e) {
			if (e.getCode().equals(ApplicationConstants.CODE_ZERO_RECORD_FOUND)) {
				discountManager.setParentSolutionOffer(selectedBean);
				MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_VIEW);
			} else {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(e.getMessage());
				LoggerWrapper log = new LoggerWrapper(SolutionOfferMananger.class);
				log.logEndFeature(e.getCode(), e.getMessage());
			}
		}
	}

	public void sortTable(ActionEvent event) {
		SolutionOfferComparator comparator = new SolutionOfferComparator(getSortColumn(), isAscending());
		Collections.sort(solutionOffers, comparator);
	}

	public void refreshTable() {
		LoggerWrapper log = new LoggerWrapper(SolutionOfferMananger.class);
		solutionOffers = new ArrayList<SolutionOfferBean>();
		try {
			solutionOffers = SolutionOfferBusiness.searchAllCommercialPackages();
			if (filteredList != null) {
				filteredList.clear();
				filteredList.addAll(solutionOffers);
			}
			SessionController session = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
			session.setCommercialPackageCounter(solutionOffers.size());
			setSortColumn(SolutionOfferBean.SOLUTION_OFFER_CREATION_DATE_FIELD);
			setAscending(false);
			sortTable(null);
		} catch (ServiceErrorException e) {
			log.logException(e.getMessage(), e);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			log.logEndFeature(e.getCode(), e.getMessage());
		}

	}

	public SolutionOfferBusiness getBusiness() {
		return business;
	}

	public void setBusiness(SolutionOfferBusiness business) {
		this.business = business;
	}

	public String gotoAddSubscriptionStep1() {
		selectedBean = new SolutionOfferBean();
		SolutionOfferBusiness.normalizeExternalIds(selectedBean);
		business.loadPrices();
		business.loadFrequencies();
		business.loadCurrencies();
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_ADD_STEP1);
		return null;
	}

	public String gotoAddSubscriptionStep2() {
		if (business.validateStep1(selectedBean)) {
			business.loadPartyGroups();
			MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP2);
		}
		return null;
	}

	public String gotoAddSubscriptionStep3() {
		selectedBean.setPartyGroupNames(business.getSelectedPartyGroups());
		business.loadOffers();
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP3);
		return null;
	}

	public String gotoAddSubscriptionStep4() {
		if (business.getSelectedOfferDetails() != null && business.getSelectedOfferDetails().size() > 0) {
			selectedBean.setOfferDetailList(business.getSelectedOfferDetails());
			MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP4);
		} else {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION_OFFER_VALIDATION_OFFERS_SELECTED));
		}
		return null;
	}

	public String backtoAddSubscriptionStep1() {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP1);
		return null;
	}

	public String backtoAddSubscriptionStep2() {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP2);
		return null;
	}

	public String backtoAddSubscriptionStep3() {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP3);
		return null;
	}

	public String backtoAddSubscriptionStep4() {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_ADD_STEP4);
		return null;
	}

	public void addSubscription(ActionEvent event) throws DatatypeConfigurationException {
		selectedBean.setProductType(AvsConstants.SUBSCRIPTION);
		for (PackageBean detail : selectedBean.getOfferDetailList()) {
			detail.setRcFrequencyTypeId(selectedBean.getRcFrequencyTypeId());
		}

		business.createSolutionOffer(selectedBean, PathConstants.SOLUTION_OFFER_VIEW);

	}

	public void gotoUpdateSubscription(ActionEvent event) {
		selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_UPDATE);
	}

	public void updateSubscription(ActionEvent event) throws DatatypeConfigurationException {
		business.updateSolutionOffer(selectedBean, PathConstants.SOLUTION_OFFER_VIEW);
		refreshTable();
	}

	public void gotoUpdateSolutionOfferPartyGroups(ActionEvent event) {
		selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		business.initPartyGroups(selectedBean.getPartyGroupNames());
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_UPDATE_PARTY_GROUPS);
	}

	public void updateSolutionOfferPartyGroups(ActionEvent event) throws DatatypeConfigurationException {
		business.updatePartyGroups(selectedBean, PathConstants.SOLUTION_OFFER_VIEW);
	}

	public String gotoCreateVoucherCampaign(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		infoSessionBean.setSolutionOfferBean(selectedBean);
			VoucherManager vm = Utilities.findBean(ApplicationConstants.VOUCHER_MANAGER, VoucherManager.class);
			vm.setSelectedBean(new VoucherInfo());
			vm.setSolutionOffer(selectedBean);
			vm.loadAvailableVoucherTypes();
			MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_VOUCHER_CAMPAIGN_ADD);
		return null;
	}

	public String gotoVoucherList(ActionEvent event) {
		selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		VoucherManager vm = Utilities.findBean(ApplicationConstants.VOUCHER_MANAGER, VoucherManager.class);
		vm.setSolutionOffer(selectedBean);
		vm.refreshTable();
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_VOUCHER_VIEW);
		return null;
	}
	
	private void loadSolutionOfferTypes()  {
		this.solutionOfferTypes.clear();
		List<String> userTypeLabels;
		try {
			SolutionOfferBusiness.searchAllCommercialPackages();
			userTypeLabels = SolutionOfferBusiness.searchAllSolutionOfferType();
			for (int i = 0; i < userTypeLabels.size(); i++) {
				this.solutionOfferTypes.add(new SelectItem(userTypeLabels.get(i), userTypeLabels.get(i)));
			}
		} catch (ServiceErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<SolutionOfferBean> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<SolutionOfferBean> filteredList) {
		this.filteredList = filteredList;
	}

	public SolutionOfferBean[] getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(SolutionOfferBean[] selectedList) {
		this.selectedList = selectedList;
	}

	public void askChangeStatus(ActionEvent event) {
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);
		String message = null;
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION_OFFER);
		if (StatusConstants.INACTIVE.equals(status)) {
			if (selectedList != null && selectedList.length > 0) {
				SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
				for (SolutionOfferBean bean : selectedList) {
					BaseResp resp = service.isSolutionOfferSubscribed(bean.getSolutionOfferId(), Utilities.getTenantName());
					if (resp != null && resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
						PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
						message = String.format(
								Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_MESSAGE_COMMERCIAL_PACKAGE_ASSIGNED),
								bean.getSolutionOfferName());
						msgBean.openPopup(message);
						return;
					}
				}
				if (selectedList.length == 1) {
					message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status),
							entityName);
				} else {
					message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_INACTIVATE_SOME_ITEM);
				}
			} else {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NO_ITEM_SELECTED));
			}
		} else {
			selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
			message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
		}
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		confirm.setTableBean(this);
		confirm.openPopup(message);

	}

	public void changeStatusOfSelectedBean() {
		LoggerWrapper log = new LoggerWrapper(SolutionOfferMananger.class);
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION_OFFER);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + getChangeStatusValue());
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString() + "->" + operation + " " + entityName, null);

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			if (StatusConstants.DELETED.equals(getChangeStatusValue())) {
				service.deleteSolutionOffer(selectedBean.getSolutionOfferId(), Utilities.getTenantName());
				mex = String.format(message, operation + " " + entityName, selectedBean.getSolutionOfferName());
			} else if (StatusConstants.INACTIVE.equals(getChangeStatusValue())) {
				if (selectedList != null && selectedList.length > 0) {
					for (SolutionOfferBean bean : selectedList) {
						service.changeStatus(bean.getSolutionOfferId(), StatusConstants.INACTIVE, Utilities.getTenantName());
						bean.setSolutionOfferStatus(StatusConstants.INACTIVE);
					}
				}
				if (selectedList.length == 1) {
					mex = String.format(message, operation + " " + entityName, selectedList[0].getSolutionOfferName());
				} else {
					mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.INACTIVATE_SOME_ITEM_OK);
				}
				selectedList = null;
			} else {
				service.changeStatus(selectedBean.getSolutionOfferId(), getChangeStatusValue(), Utilities.getTenantName());
				selectedBean.setSolutionOfferStatus(getChangeStatusValue());
				mex = String.format(message, operation + " " + entityName, selectedBean.getSolutionOfferName());
			}
			code = ApplicationConstants.CODE_OK;
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();

		}
		msgBean.openPopup(mex);

		// logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString() + "->" + operation, code, mex);
	}

	public List<SelectItem> getSolutionOfferTypes() {
		return solutionOfferTypes;
	}

	public void setSolutionOfferTypes(List<SelectItem> solutionOfferTypes) {
		this.solutionOfferTypes = solutionOfferTypes;
	}



}
