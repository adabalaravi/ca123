package com.accenture.avs.console.beans.operator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.PropertyManager;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.constants.MessageConstants;
import com.accenture.avs.console.common.constants.PathConstants;
import com.accenture.avs.console.common.exception.ServiceErrorException;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.controllers.LayoutController;
import com.accenture.avs.console.controllers.MenuController;
import com.accenture.avs.console.controllers.PopupController;
import com.accenture.avs.console.controllers.SessionController;
import com.accenture.avs.console.services.OperatorService;

@ManagedBean(name = ApplicationConstants.LOGIN_BEAN_NAME)
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950541069147575047L;

	private static transient LoggerWrapper log = new LoggerWrapper(LoginBean.class);

	private String username;
	private String password;
	private String selectedTenant;
	private List<String> availableTenants;
	private String errorMessage;

	public LoginBean() throws ServiceErrorException {
		// SET COOKIE by Cassandri
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		boolean enabled = Boolean.parseBoolean(propertyManager.getProperty(ApplicationConstants.COOKIE_ENABLED));
		if (enabled) {
			String cookieName = propertyManager.getProperty(ApplicationConstants.COOKIE_NAME);
			String cookieValue = propertyManager.getProperty(ApplicationConstants.COOKIE_VALUE);
			log.logDebug("Cookie enabled - %s : %s ", cookieName, cookieValue);
			FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookieName, cookieValue, null);
		}

		OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
		List<TenantInfo> tenants = service.searchAllTenants();
		availableTenants = new ArrayList<String>();
		for (TenantInfo tenantBean : tenants) {
			if (Utilities.isStatusActive(tenantBean.getStatusName())) {
				availableTenants.add(tenantBean.getTenantName().trim());
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void login() {
		errorMessage = "";
		try {
			OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
			service.loginOperator(username, password, selectedTenant);
			log.logDebug("Operator: %s login success", username);
			LayoutController pageBean = Utilities.findBean(ApplicationConstants.CONTROLLER_LAYOUT_NAME, LayoutController.class);
			// looking for operator
			OperatorInfo operator = service.searchOperatorByUsername(username);
			// retrieving rights
			OperatorRoleInfo role = service.searchOperatorRights(username);
			operator.setRole(role);

			if (role.isCatalogueRead()) {
				pageBean.setIframedContentInclude(PathConstants.URL_CONSOLE_CATALOGUE);
				pageBean.setTabIndex(0);
			} else if (role.isAssuranceRead()) {
				pageBean.setIframedContentInclude(PathConstants.URL_CONSOLE_ASSURANCE);
				pageBean.setTabIndex(1);
			} else if (role.isCatchupRead()) {
				pageBean.setIframedContentInclude(PathConstants.URL_CONSOLE_CATCHUP);
				pageBean.setTabIndex(2);
			} else if (role.isCatchupRead()) {
				pageBean.setIframedContentInclude(PathConstants.URL_CONSOLE_MULTICAMERA);
				pageBean.setTabIndex(3);
			} else if (role.isOperatorsRead()) {
				pageBean.setIframedContentInclude(PathConstants.URL_CONSOLE_OPERATORS);
				pageBean.setTabIndex(4);
			} else {
				log.logDebug("User: %s not enabled to access", username);
				errorMessage = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.LOGIN_ERROR);
				new MenuController().redirectbyParam(PathConstants.LOGIN);
				return;
			}
			SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
			infoSessionBean.setOperator(operator);
			infoSessionBean.setTenantName(selectedTenant);

			pageBean.setMenuVisible(true);
		} catch (Exception e) {
			log.logError(e);
			log.logDebug("User: %s login rejected", username);
			errorMessage = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.LOGIN_ERROR);
			new MenuController().redirectbyParam(PathConstants.LOGIN);
		}
	}

	public String getSelectedTenant() {
		return selectedTenant;
	}

	public void setSelectedTenant(String selectedTenant) {
		this.selectedTenant = selectedTenant;
	}

	public List<String> getAvailableTenants() {
		return availableTenants;
	}

	public void setAvailableTenants(List<String> availableTenants) {
		this.availableTenants = availableTenants;
	}

}
