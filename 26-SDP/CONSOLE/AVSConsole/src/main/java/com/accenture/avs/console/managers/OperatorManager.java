package com.accenture.avs.console.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.accenture.avs.console.beans.operator.OperatorBean;
import com.accenture.avs.console.beans.operator.OperatorInfo;
import com.accenture.avs.console.beans.operator.TenantInfo;
import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.constants.MessageConstants;
import com.accenture.avs.console.common.constants.PathConstants;
import com.accenture.avs.console.common.exception.ServiceErrorException;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.controllers.MenuController;
import com.accenture.avs.console.controllers.PopupController;
import com.accenture.avs.console.controllers.SessionController;
import com.accenture.avs.console.services.OperatorService;

@ManagedBean(name = ApplicationConstants.MANAGER_OPERATOR)
@SessionScoped
public final class OperatorManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static transient LoggerWrapper log = new LoggerWrapper(OperatorBean.class);

	private String username;
	private String lastName;
	private String firstName;
	private String tenant;

	private Map<String, Long> rightsMap;

	private int searchBy = ApplicationConstants.ORGANIZZATION_NAME_SEARCH;

	private List<OperatorInfo> operators;

	private List<SelectItem> statusList;

	private List<SelectItem> tenantList;

	private OperatorBean selectedBean;
	private OperatorInfo selectedInfo;
	private OperatorBean changePwBean;

	public OperatorManager() {
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		operators = new ArrayList<OperatorInfo>();
		// initialize tenantList => filter options
		loadTenantList();
		// initialize statusList => update status
		loadStatusList();
		try {
			rightsMap = service.getRightsMap();
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void setSelectedBean(OperatorBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<OperatorInfo> getOperators() {
		return operators;
	}

	public OperatorBean getSelectedBean() {
		return selectedBean;
	}

	public int getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(int searchBy) {
		this.searchBy = searchBy;
	}

	public List<SelectItem> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<SelectItem> statusList) {
		this.statusList = statusList;
	}

	public List<SelectItem> getTenantList() {
		return tenantList;
	}

	public void setTenantList(List<SelectItem> tenantList) {
		this.tenantList = tenantList;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public OperatorInfo getSelectedInfo() {
		return selectedInfo;
	}

	public void setSelectedInfo(OperatorInfo selectedInfo) {
		this.selectedInfo = selectedInfo;
	}

	/********** END GETTER AND SETTER METHODS ***************************/

	public void gotoAddOperatorStep1(ActionEvent event) {
		this.selectedBean = new OperatorBean();
		new MenuController().redirectbyParam(PathConstants.ADD_OPERATOR_STEP1);
	}

	public void backAddOperatorStep1(ActionEvent event) {
		new MenuController().redirectbyParam(PathConstants.ADD_OPERATOR_STEP1);
	}

	public void gotoAddOperatorStep2(ActionEvent event) {
		selectedBean.initAdminFlag();
		new MenuController().redirectbyParam(PathConstants.ADD_OPERATOR_STEP2);
	}
	
	public void backAddOperatorStep2(ActionEvent event) {
		new MenuController().redirectbyParam(PathConstants.ADD_OPERATOR_STEP2);
	}
	
	public void gotoAddOperatorStep3(ActionEvent event) {
		selectedBean.prepareTenantLists();
		new MenuController().redirectbyParam(PathConstants.ADD_OPERATOR_STEP3);
	}

	public void addOperator(ActionEvent event) {
		selectedBean.addOperator(rightsMap);
	}

	public void goToUpdatePage(ActionEvent event) throws ServiceErrorException {
		OperatorInfo info = ((OperatorInfo) event.getComponent().getAttributes().get(ApplicationConstants.ATTRIBUTE_OBJECT_NAME));
		selectedBean = new OperatorBean(info.clone());
		selectedBean.storeValues();
		new MenuController().redirectbyParam(PathConstants.UPDATE_OPERATOR);
	}

	public void goToUpdateOperatorRights(ActionEvent event) throws ServiceErrorException {
		OperatorInfo info = ((OperatorInfo) event.getComponent().getAttributes().get(ApplicationConstants.ATTRIBUTE_OBJECT_NAME));
		selectedBean = new OperatorBean(info.clone());
		selectedBean.storeValues();
		selectedBean.initAdminFlag();
		new MenuController().redirectbyParam(PathConstants.UPDATE_OPERATOR_RIGHTS);
	}

	public void updateOperatorRights(ActionEvent event) throws ServiceErrorException {
		selectedBean.updateOperatorRights(rightsMap);
	}

	public void goToUpdateTenants(ActionEvent event) throws ServiceErrorException {
		SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		OperatorInfo info = ((OperatorInfo) event.getComponent().getAttributes().get(ApplicationConstants.ATTRIBUTE_OBJECT_NAME));
		selectedBean = new OperatorBean(info.clone());
		selectedBean.storeValues();

		selectedBean.prepareUpdateTenants(session.getOperator().getTenants());

		new MenuController().redirectbyParam(PathConstants.UPDATE_OPERATOR_TENANTS);
	}

	public void loadStatusList() {
		statusList = new ArrayList<SelectItem>();
		statusList.add(new SelectItem(ApplicationConstants.OPERATOR_STATUS_ACT, String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,
				ApplicationConstants.OPERATOR_STATUS_ACTIVE))));
		statusList.add(new SelectItem(ApplicationConstants.OPERATOR_STATUS_INACT, String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,
				ApplicationConstants.OPERATOR_STATUS_INACTIVE))));
	}
	
	public void loadTenantList() {
		try {
			this.tenantList = new ArrayList<SelectItem>();
			// default choice
			this.tenantList.add(new SelectItem("", ""));
			OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
			List<TenantInfo> tenants = service.searchAllTenants();
			for (TenantInfo t : tenants) {
				this.tenantList.add(new SelectItem(t.getTenantName(), t.getTenantName()));
			}
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void cancelFields(ActionEvent event) {
		clearFields();
	}

	public void clearFields() {
		username = null;
		lastName = null;
		firstName = null;

	}

	public void deleteOperator(ActionEvent event) {
		log.logStartFeature(null);
		// non posso leggere l'item perche' c'e' il confirmation di mezzo
		// ma lo ho selezionato precedentemente
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		String mex;
		try {
			service.deleteOperator(selectedInfo.getUsername());
			mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.DELETE_OPERATOR_MESSAGE), selectedBean.getInfo().getUsername());
			log.logEndFeature(ApplicationConstants.CODE_OK, mex);
			searchAll();
			msgBean.openPopup(mex);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(e);
		}
	}

	public void searchOperatorByUsername() {
		searchBy = ApplicationConstants.OPERATOR_SEARCH_BY_USERNAME;
		goToViewPage();
	}

	public void searchOperatorByFirstAndLastName() {
		searchBy = ApplicationConstants.OPERATOR_SEARCH_BY_FIRSTNAME_AND_LASTNAME;
		goToViewPage();
	}

	public void searchOperatorByTenant() {
		searchBy = ApplicationConstants.OPERATOR_SEARCH_BY_TENANT;
		goToViewPage();
	}

	public void searchAll() {
		searchBy = ApplicationConstants.OPERATOR_SEARCH_ALL;
		goToViewPage();
	}

	public void goToViewPage() {
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		try {
			operators = new ArrayList<OperatorInfo>();
			switch (searchBy) {
			case ApplicationConstants.OPERATOR_SEARCH_BY_USERNAME: {
				operators.add(service.searchOperatorByUsername(username));
				break;
			}
			case ApplicationConstants.OPERATOR_SEARCH_BY_FIRSTNAME_AND_LASTNAME: {
				operators = service.searchOperatorByFirstNameAndLastName(firstName, lastName);
				break;
			}
			case ApplicationConstants.OPERATOR_SEARCH_BY_TENANT: {
				operators = service.searchOperatorByTenant(tenant);
				break;
			}
			case ApplicationConstants.OPERATOR_SEARCH_ALL: {
				operators = service.searchAllOperators();
				break;
			}
			default:
				break;
			}
			new MenuController().redirectbyParam(PathConstants.OPERATOR_VIEW);
			log.logEndFeature(ApplicationConstants.CODE_OK, null);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(e);
		}
	}

	public void refreshTable() {
		operators.clear();
		goToViewPage();
	}

	public void selectOperator(ActionEvent event) throws ServiceErrorException {
		selectedInfo = (OperatorInfo) event.getComponent().getAttributes().get(ApplicationConstants.ATTRIBUTE_OBJECT_NAME);
		selectedBean = new OperatorBean(selectedInfo);
	}
	
	public void prepareChangePassword(ActionEvent event) {
		SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		changePwBean = new OperatorBean(session.getOperator());
	}

	public OperatorBean getChangePwBean() {
		return changePwBean;
	}

	public void setChangePwBean(OperatorBean changePwBean) {
		this.changePwBean = changePwBean;
	}

}
