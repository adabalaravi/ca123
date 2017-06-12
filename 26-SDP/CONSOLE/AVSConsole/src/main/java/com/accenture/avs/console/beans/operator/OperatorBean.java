package com.accenture.avs.console.beans.operator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.constants.MessageConstants;
import com.accenture.avs.console.common.exception.ServiceErrorException;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.controllers.PopupController;
import com.accenture.avs.console.controllers.SessionController;
import com.accenture.avs.console.converters.OperatorRightsConverter;
import com.accenture.avs.console.managers.OperatorManager;
import com.accenture.avs.console.services.OperatorService;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorRoleRightListRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ResetPasswordResp;

public class OperatorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static transient LoggerWrapper log = new LoggerWrapper(OperatorBean.class);

	private OperatorInfo info;
	private OperatorInfo storedInfo;

	private List<String> tenantListSelected;
	private List<String> tenantListSelectedAll;
	private List<String> tenantListUnselected;
	private List<String> tenantListUnselectedAll;

	private Boolean adminFlag;

	// gestione Change Password
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	public OperatorBean() {
		this.info = new OperatorInfo();
		this.info.setRole(new OperatorRoleInfo());
	}

	public OperatorBean(OperatorInfo info) {
		this.info = info;
	}

	public void addOperator(Map<String, Long> rightsMap) {
		log.logStartFeature(null);
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		try {
			service.createOperator(info.getUsername(), info.getFirstName(), info.getLastName(), info.getPassword(), info.getEmail(), tenantListSelectedAll);
			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_OPERATOR_MESSAGE), info.getUsername());

			ModifyOperatorRoleRightListRequest rightsRequest = OperatorRightsConverter
					.convertRightsToRequest(info.getRole(), new OperatorRoleInfo(), rightsMap);
			if (rightsRequest.getRight().size() > 0) {
				service.modifyOperatorRoleRights(info.getUsername(), rightsRequest);
			}
			log.logEndFeature(ApplicationConstants.CODE_OK, mex);
			OperatorManager tableBean = Utilities.findBean(ApplicationConstants.MANAGER_OPERATOR, OperatorManager.class);
			tableBean.searchAll();
			session.refreshOperatorsCounter();
			msgBean.openPopup(mex);
			
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(e);
		}
	}

	public void updateOperator(ActionEvent event) {
		log.logStartFeature(null);
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		try {
			service.modifyOperator(info.getUsername(), info.getFirstName(), info.getLastName(), info.getEmail(), info.getStatusName());

			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_OPERATOR_MESSAGE), info.getUsername());
			log.logEndFeature(ApplicationConstants.CODE_OK, mex);
			OperatorManager tableBean = Utilities.findBean(ApplicationConstants.MANAGER_OPERATOR, OperatorManager.class);
			tableBean.refreshTable();
			msgBean.openPopup(mex);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(e);
		}

	}

	public void updateOperatorRights(Map<String, Long> rightsMap) {
		log.logStartFeature(null);
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		try {
			ModifyOperatorRoleRightListRequest rightsRequest = OperatorRightsConverter.convertRightsToRequest(info.getRole(), storedInfo.getRole(), rightsMap);
			if (rightsRequest.getRight().size() > 0) {
				service.modifyOperatorRoleRights(info.getUsername(), rightsRequest);
			}
			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_OPERATOR_RIGHTS_MESSAGE), info.getUsername());
			log.logEndFeature(ApplicationConstants.CODE_OK, mex);
			OperatorManager tableBean = Utilities.findBean(ApplicationConstants.MANAGER_OPERATOR, OperatorManager.class);
			tableBean.refreshTable();
			msgBean.openPopup(mex);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(e);
		}

	}

	public void resetPassword() {
		log.logStartFeature(null);
		try {
			OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
			ResetPasswordResp resp = service.resetOperatorPassword(info.getUsername());
			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE_RESET_PWD), resp.getNewPassword());
			log.logEndFeature(ApplicationConstants.CODE_OK, mex);
			PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
			msgBean.openPopup(mex);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(e);
		}
	}

	public void changePassword() {
		log.logStartFeature(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.BREADCRUMB_CHANGE_PWD), null);
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);

		try {
			service.changeOperatorPassword(info.getUsername(), oldPassword, newPassword, confirmPassword);

			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.CHANGE_OPERATOR_PASSWORD_MESSAGE), info.getUsername());
			log.logEndFeature(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.BREADCRUMB_CHANGE_PWD), ApplicationConstants.CODE_OK,
					mex);

			msgBean.openPopup(mex);

			// fa chiudere il popup
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("pwChanged", true);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
			log.logEndFeature(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.BREADCRUMB_CHANGE_PWD), e.getCode(), e.getMessage());
		}
		// clean pw
		oldPassword = null;
		newPassword = null;
		confirmPassword = null;
	}

	public void prepareTenantLists() {
		if (tenantListSelected == null) {
			tenantListSelected = new ArrayList<String>();
		}
		if (tenantListSelectedAll == null) {
			tenantListSelectedAll = new ArrayList<String>();
		}
		if (tenantListUnselected == null) {
			tenantListUnselected = new ArrayList<String>();
		}
		if (tenantListUnselectedAll == null) {
			tenantListUnselectedAll = new ArrayList<String>();
			// only tenants managed by operator can be added
			SessionController sessionBean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
			if (sessionBean.getOperator().getTenants() != null) {
				for (TenantInfo tenant : sessionBean.getOperator().getTenants()) {
					tenantListUnselectedAll.add(tenant.getTenantName());
				}
			}
		}
	}

	public void prepareUpdateTenants(List<TenantInfo> availableTenants) {
		// only tenants of the logged operator can be modified on this operator
		tenantListSelected = new ArrayList<String>();
		tenantListUnselected = new ArrayList<String>();

		// have to match operator info with available tenants
		// at first initialize to empty list
		tenantListUnselectedAll = new ArrayList<String>();
		tenantListSelectedAll = new ArrayList<String>();
		if (this.info.getTenants() != null && availableTenants != null) {
			// then check every available tenants...
			for (TenantInfo tenant : availableTenants) {
				boolean found = false;
				// against the tenants of the operator
				for (TenantInfo tenant2 : this.info.getTenants()) {
					// if it's one of the operator add to selected
					if (tenant.getTenantName().equals(tenant2.getTenantName())) {
						tenantListSelectedAll.add(tenant.getTenantName());
						found = true;
						break;
					}
				}
				// otherwise put it to the unselected
				if (!found) {
					tenantListUnselectedAll.add(tenant.getTenantName());
				}
			}
		}
	}

	public void updateOperatorTenants(ActionEvent event) {
		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		try {
			List<String> removeTenants = new ArrayList<String>();
			List<String> addTenants = new ArrayList<String>();
			for (String tname : tenantListUnselectedAll) {
				// have to test if unselected were previously selected
				for (TenantInfo t : info.getTenants()) {
					if (t.getTenantName().equals(tname)) {
						removeTenants.add(tname);
						break;
					}
				}
			}
			for (String tname : tenantListSelectedAll) {
				// have to test if selected were previously unselected
				boolean found = false;
				for (TenantInfo t : info.getTenants()) {
					if (t.getTenantName().equals(tname)) {
						found = true;
						break;
					}
				}
				if (!found) {
					addTenants.add(tname);
				}
			}
			if (!removeTenants.isEmpty() || !addTenants.isEmpty()) {
				service.modifyOperatorTenants(info.getUsername(), removeTenants, addTenants);
			}

			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_OPERATOR_MESSAGE), info.getUsername());

			// have to refresh the operator list to see the updated tenants
			OperatorManager tableBean = Utilities.findBean(ApplicationConstants.MANAGER_OPERATOR, OperatorManager.class);
			tableBean.refreshTable();

			msgBean.openPopup(mex);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void storeValues() {
		if (this.info != null) {
			this.storedInfo = this.info.clone();
		}
	}

	public void resetOperator() {
		if (this.storedInfo != null) {
			this.info = this.storedInfo.clone();
		}
	}

	public void cleanOperator() {
		this.info = new OperatorInfo();
	}

	public OperatorInfo getInfo() {
		return info;
	}

	public void setInfo(OperatorInfo info) {
		this.info = info;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<String> getTenantListSelected() {
		return tenantListSelected;
	}

	public void setTenantListSelected(List<String> tenantListSelected) {
		this.tenantListSelected = tenantListSelected;
	}

	public List<String> getTenantListSelectedAll() {
		return tenantListSelectedAll;
	}

	public void setTenantListSelectedAll(List<String> tenantListSelectedAll) {
		this.tenantListSelectedAll = tenantListSelectedAll;
	}

	public List<String> getTenantListUnselected() {
		return tenantListUnselected;
	}

	public void setTenantListUnselected(List<String> tenantListUnselected) {
		this.tenantListUnselected = tenantListUnselected;
	}

	public List<String> getTenantListUnselectedAll() {
		return tenantListUnselectedAll;
	}

	public void setTenantListUnselectedAll(List<String> tenantListUnselectedAll) {
		this.tenantListUnselectedAll = tenantListUnselectedAll;
	}

	public void toRight() {
		tenantListSelectedAll.addAll(tenantListUnselected);
		tenantListUnselectedAll.removeAll(tenantListUnselected);
	}

	public void toLeft() {
		tenantListUnselectedAll.addAll(tenantListSelected);
		tenantListSelectedAll.removeAll(tenantListSelected);
	}

	public void toRightAll() {
		tenantListSelectedAll.addAll(tenantListUnselectedAll);
		tenantListUnselectedAll.clear();
	}

	public void toLeftAll() {
		tenantListUnselectedAll.addAll(tenantListSelectedAll);
		tenantListSelectedAll.clear();
	}

	public void checkAdministatorListener() {
		info.getRole().setOperatorsRead(adminFlag);
		info.getRole().setOperatorsWrite(adminFlag);
		info.getRole().setAssuranceRead(adminFlag);
		info.getRole().setAssuranceWrite(adminFlag);
		info.getRole().setCatalogueRead(adminFlag);
		info.getRole().setCatalogueWrite(adminFlag);
		info.getRole().setCatchupRead(adminFlag);
		info.getRole().setCatchupWrite(adminFlag);
		info.getRole().setMulticameraRead(adminFlag);
		info.getRole().setMulticameraWrite(adminFlag);
	}

	public void checkCatalogueRead() {
		if (!info.getRole().isCatalogueRead()) {
			info.getRole().setCatalogueWrite(false);
		}
	}

	public void checkAssuranceRead() {
		if (!info.getRole().isAssuranceRead()) {
			info.getRole().setAssuranceWrite(false);
		}
	}

	public void checkCatchupRead() {
		if (!info.getRole().isCatchupRead()) {
			info.getRole().setCatchupWrite(false);
		}
	}

	public void checkMulticameraRead() {
		if (!info.getRole().isMulticameraRead()) {
			info.getRole().setMulticameraWrite(false);
		}
	}

	public Boolean getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(Boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	public void initAdminFlag() {
		adminFlag = info.getRole().isOperatorsRead() && info.getRole().isOperatorsWrite();
		adminFlag &= info.getRole().isAssuranceRead() && info.getRole().isAssuranceWrite();
		adminFlag &= info.getRole().isCatalogueRead() && info.getRole().isCatalogueWrite();
		adminFlag &= info.getRole().isCatchupRead() && info.getRole().isCatchupWrite();
		adminFlag &= info.getRole().isMulticameraRead() && info.getRole().isMulticameraWrite();
	}
}
