package com.accenture.avs.console.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.accenture.avs.console.beans.operator.OperatorInfo;
import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.exception.ServiceErrorException;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.services.OperatorService;

@ManagedBean(name = ApplicationConstants.CONTROLLER_SESSION_NAME)
@SessionScoped
public class SessionController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static transient LoggerWrapper log = new LoggerWrapper(SessionController.class);

	private OperatorInfo operator;
	private String ipAddress;
	private String sessionId;
	private String tenantName;
	private BreadCrumbController breadCrumbController;
	private Long operatorsCounter;
	private int tenantsCounter;
	

	public SessionController() {
		super();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		ipAddress = request.getRemoteAddr();
		this.sessionId = request.getSession().getId();
		breadCrumbController = new BreadCrumbController();
	}

	public void loadOperator() {
		log.logDebug("*** LOOKING FOR OPERATOR ***");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int atIndex = request.getRemoteUser().lastIndexOf("@");
		String operatorName = request.getRemoteUser().substring(0, atIndex);
		this.tenantName = request.getRemoteUser().substring(atIndex + 1);
		if (operatorName != null) {
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("login.username", operatorName);
			logMap.put("login.tenant", tenantName);
			try {
				OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
				this.operator = service.searchOperatorByUsername(operatorName);
				this.operator.setRole(service.searchOperatorRights(operatorName));
				log.logStartFeature("LOGIN", logMap);
			} catch (ServiceErrorException e) {
				PopupController.handleServiceErrorException(e);
			}
		}
	}
	
	public void refreshOperatorsCounter(){
		try{
			OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
			operatorsCounter = service.countAllOperators();
		} catch (ServiceErrorException e) {
			LoggerWrapper log = new LoggerWrapper(SessionController.class);
			log.logException(e.getMessage(), e);
		}
		
		
	}
	
	public void refreshTenantsCounter(){
		try{
			OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
			tenantsCounter = service.countAllTenants();
		} catch (ServiceErrorException e) {
			LoggerWrapper log = new LoggerWrapper(SessionController.class);
			log.logException(e.getMessage(), e);
		}
		
	}


	public OperatorInfo getOperator() {
		return operator;
	}

	public void setOperator(OperatorInfo operator) {
		this.operator = operator;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public BreadCrumbController getBreadCrumbController() {
		return breadCrumbController;
	}

	public void setBreadCrumbController(BreadCrumbController breadCrumbController) {
		this.breadCrumbController = breadCrumbController;
	}

	public Long getOperatorsCounter() {
		return operatorsCounter;
	}

	public void setOperatorsCounter(Long operatorsCounter) {
		this.operatorsCounter = operatorsCounter;
	}

	public int getTenantsCounter() {
		return tenantsCounter;
	}

	public void setTenantsCounter(int tenantsCounter) {
		this.tenantsCounter = tenantsCounter;
	}
}
