package com.accenture.sdp.csmcc.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.xml.datatype.DatatypeConfigurationException;

import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.business.PackageBusiness;
import com.accenture.sdp.csmcc.business.SolutionOfferBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.comparators.SolutionOfferComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.SolutionOfferService;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.BaseResp;

@ManagedBean(name = ApplicationConstants.SOLUTION_OFFER_DISCOUNTED_MANAGER)
@SessionScoped
public class SolutionOfferDiscountedManager extends BaseManager {

	private static final long serialVersionUID = 1L;
	private SolutionOfferBusiness business;
	private SolutionOfferBean selectedBean;
	private List<SolutionOfferBean> solutionOffers;
	private List<SolutionOfferBean> filteredList;
	private SolutionOfferBean parentSolutionOffer;
	private SolutionOfferBean[] selectedList;

	private String principalView;

	private Map<String, SolutionOfferBean> parents;

	public List<SolutionOfferBean> getSolutionOffers() {
		return solutionOffers;
	}

	public void setSolutionOffers(List<SolutionOfferBean> solutionOffers) {
		this.solutionOffers = solutionOffers;
	}


	public SolutionOfferDiscountedManager() {
		super();
		business = new SolutionOfferBusiness();
	}

	public SolutionOfferBusiness getBusiness() {
		return business;
	}

	public void setBusiness(SolutionOfferBusiness business) {
		this.business = business;
	}

	public SolutionOfferBean getParentSolutionOffer() {
		return parentSolutionOffer;
	}

	public void setParentSolutionOffer(SolutionOfferBean parentSolutionOffer) {
		this.parentSolutionOffer = parentSolutionOffer;
	}


	public String goToAddCommercialPackageDiscountedStep1() {
		this.setSelectedBean(new SolutionOfferBean());
		if (parentSolutionOffer == null) {
			loadParents();
		} else {
			selectedBean.setParentSolutionOfferName(parentSolutionOffer.getSolutionOfferName());
			selectedBean.setParentSolutionOfferId(parentSolutionOffer.getSolutionOfferId());
			selectedBean.setSolutionOfferDesc(parentSolutionOffer.getSolutionOfferDesc());
			selectedBean.setSolutionOfferStartDate(parentSolutionOffer.getSolutionOfferStartDate());
			selectedBean.setSolutionOfferEndDate(parentSolutionOffer.getSolutionOfferEndDate());

		}
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_ADD_STEP1);
		return null;
	}

	public String goToAddCommercialPackageDiscountedStep2() {
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_NAME_FIELD, selectedBean.getSolutionOfferName());
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			if (parentSolutionOffer == null) {
				SolutionOfferBean parent = parents.get(selectedBean.getParentSolutionOfferName());
				selectedBean.setParentSolutionOfferId(parent.getSolutionOfferId());
				business.initPartyGroups(parent.getPartyGroupNames());
			} else {
				business.initPartyGroups(parentSolutionOffer.getPartyGroupNames());
			}
			MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_DISCOUNT_ADD_STEP2);
		}
		return null;
	}

	public String goToAddCommercialPackageDiscountedStep3() {
		try {
			selectedBean.setPartyGroupNames(business.getSelectedPartyGroups());
			selectedBean.setOfferDetailList(PackageBusiness.searchPackagesBySolutionOffer(selectedBean.getParentSolutionOfferName()));
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(SolutionOfferDiscountedManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_DISCOUNT_ADD_STEP3);
		return null;
	}

	public String backToAddCommercialPackageDiscountedStep1(ActionEvent event) {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_DISCOUNT_ADD_STEP1);
		return null;
	}

	public String backToAddCommercialPackageDiscountedStep2(ActionEvent event) {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_DISCOUNT_ADD_STEP2);
		return null;
	}

	public String backToAddCommercialPackageDiscountedStep3(ActionEvent event) {
		MenuController.redirectbyParamAndPop(PathConstants.SOLUTION_OFFER_DISCOUNT_ADD_STEP3);
		return null;
	}

	public void addCommercialPackageDiscounted(ActionEvent event) {
		business.createCommercialPackageDiscounted(selectedBean, principalView);
	}

	public void gotoUpdateCommercialPackageDiscounted(ActionEvent event) {
		selectedBean = ((SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_UPDATE);
	}

	public void updateCommercialPackageDiscounted(ActionEvent event) throws DatatypeConfigurationException {
		business.updateCommercialPackageDiscounted(selectedBean, principalView);
		refreshTable();
	}

	public void goToUpdateDiscountPackages(ActionEvent event) {
		SolutionOfferBean solutionOffer = ((SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));
		selectedBean = solutionOffer;
		try {
			List<PackageBean> details = PackageBusiness.searchDiscountPackagesBySolutionOffer(selectedBean.getSolutionOfferName());
			for (PackageBean d : details) {
				d.calcultateNewPrice();
			}
			selectedBean.setOfferDetailList(details);
			MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_PACKAGE_UPDATE);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			LoggerWrapper log = new LoggerWrapper(SolutionOfferDiscountedManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}

	}

	public void updateDiscountPackages(ActionEvent event) throws DatatypeConfigurationException {
		business.updateDiscountPackages(selectedBean, principalView);
	}

	public void relatedDiscountPackages(ActionEvent event) {
		SolutionOfferBean solutionOffer = ((SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));
		selectedBean = solutionOffer;
		try {
			// search just to check
			List<PackageBean> details = PackageBusiness.searchDiscountPackagesBySolutionOffer(selectedBean.getSolutionOfferName());
			for (PackageBean d : details) {
				d.calcultateNewPrice();
			}
			selectedBean.setOfferDetailList(details);
			MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_PACKAGE_VIEW);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

			LoggerWrapper log = new LoggerWrapper(SolutionOfferDiscountedManager.class);
			log.logEndFeature(e.getCode(), e.getMessage());
		}
	}

	public void sortTable(ActionEvent event) {
		SolutionOfferComparator comparator = new SolutionOfferComparator(getSortColumn(), isAscending());
		Collections.sort(solutionOffers, comparator);
	}

	@Override
	public void refreshTable() {
		solutionOffers = new ArrayList<SolutionOfferBean>();
		try {
			if (parentSolutionOffer != null) {
				solutionOffers = SolutionOfferBusiness.searchDiscountSolutionOffersByParentSolutionOffer(parentSolutionOffer);
			} else {
				solutionOffers = SolutionOfferBusiness.searchAllDiscountSolutionOffers();
			}
			setSortColumn(SolutionOfferBean.SOLUTION_OFFER_CREATION_DATE_FIELD);
			setAscending(false);
			sortTable(null);
			// ripristina la lista filtrata
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(solutionOffers);
			}
		} catch (ServiceErrorException e) {
			if (!e.getCode().equals(ApplicationConstants.CODE_ZERO_RECORD_FOUND)) {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(e.getMessage());
				LoggerWrapper log = new LoggerWrapper(this.getClass());
				log.logEndFeature(e.getCode(), e.getMessage());
			}
		}

	}

	public SolutionOfferBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(SolutionOfferBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public void askChangeStatus(ActionEvent event) {
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);
		String message = null;
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION_OFFER);
		if (StatusConstants.INACTIVE.equals(status)){
			if (selectedList != null && selectedList.length > 0){
				SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
				for (SolutionOfferBean bean : selectedList) {
					BaseResp resp = service.isSolutionOfferSubscribed(bean.getSolutionOfferId(), Utilities.getTenantName());
					if (resp != null && resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
						PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
						message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_MESSAGE_COMMERCIAL_PACKAGE_ASSIGNED), bean.getSolutionOfferName());
						msgBean.openPopup(message);
						return;
					}if (selectedList.length == 1) {
						message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
					} else {
						message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_INACTIVATE_SOME_ITEM);
					}
				}
			}else {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NO_ITEM_SELECTED));
			}
		} else {
			selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);      
			message = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status), entityName);
		}

		//		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}



	public void changeStatusOfSelectedBean() {
		LoggerWrapper log = new LoggerWrapper(SolutionOfferDiscountedManager.class);
		SolutionOfferService service = Utilities.findBean(ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME, SolutionOfferService.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION_OFFER);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + getChangeStatusValue());
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString() + "->" + operation + " " + entityName, null);
		String mex="";
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
		try {
			if (StatusConstants.DELETED.equals(getChangeStatusValue())) {
				service.deleteSolutionOffer(selectedBean.getSolutionOfferId(), Utilities.getTenantName());
			}else if (StatusConstants.INACTIVE.equals(getChangeStatusValue())) {
				if (selectedList != null && selectedList.length > 0){
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
//			mex = String.format(message, operation + " " + entityName, selectedBean.getSolutionOfferName());
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();

		}
		msgBean.openPopup(mex);

		// logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString() + "->" + operation, code, mex);
	}


	public void loadParents() {
		try {
			if (parents == null){
				parents = new HashMap<String, SolutionOfferBean>();
			} else {
				parents.clear();	
			}
			List<SolutionOfferBean> parentList = SolutionOfferBusiness.searchAllCommercialPackages();
			for (SolutionOfferBean solutionOffer : parentList) {
				parents.put(solutionOffer.getSolutionOfferName(), solutionOffer);
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());

		}
	}

	public void goToUpdateClusters(ActionEvent event) {
		selectedBean = (SolutionOfferBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		business.initPartyGroups(selectedBean.getPartyGroupNames());
		MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER_DISCOUNT_CLUSTER_UPDATE);
	}

	public void updateClusters(ActionEvent event) throws DatatypeConfigurationException {
		business.updatePartyGroups(selectedBean, principalView);
	}

	public List<String> parentNameFilter(String query) {
		List<String> results = new ArrayList<String>();
		for (String entry : parents.keySet()) {
			// ignore case
			if (entry.toUpperCase().startsWith(query.toUpperCase())) {
				results.add(entry);
			}
		}
		// order by name before returning
		Collections.sort(results);
		return results;
	}

	public List<SolutionOfferBean> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<SolutionOfferBean> filteredList) {
		this.filteredList = filteredList;
	}

	public String getPrincipalView() {
		return principalView;
	}

	public void setPrincipalView(String principalView) {
		this.principalView = principalView;
	}

	public void selectParentListner() {
		SolutionOfferBean parent = parents.get(selectedBean.getParentSolutionOfferName());
		if (parent != null) {
			selectedBean.setParentSolutionOfferName(parent.getSolutionOfferName());
			selectedBean.setParentSolutionOfferId(parent.getSolutionOfferId());
			selectedBean.setSolutionOfferDesc(parent.getSolutionOfferDesc());
			selectedBean.setSolutionOfferStartDate(parent.getSolutionOfferStartDate());
			selectedBean.setSolutionOfferEndDate(parent.getSolutionOfferEndDate());
		} else {
			selectedBean.setParentSolutionOfferName(null);
			selectedBean.setParentSolutionOfferId(null);
			selectedBean.setSolutionOfferDesc(null);
			selectedBean.setSolutionOfferStartDate(null);
			selectedBean.setSolutionOfferEndDate(null);

		}


	}

	public SolutionOfferBean[] getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(SolutionOfferBean[] selectedList) {
		this.selectedList = selectedList;
	}

}
