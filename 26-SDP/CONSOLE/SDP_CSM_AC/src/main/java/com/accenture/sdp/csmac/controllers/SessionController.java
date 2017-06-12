package com.accenture.sdp.csmac.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.accenture.sdp.csmac.beans.operator.OperatorBean;
import com.accenture.sdp.csmac.business.OperatorBusiness;
import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.services.OperatorService;
import com.accenture.sdp.csmac.services.PartyService;

@ManagedBean(name = ApplicationConstants.CONTROLLER_SESSION_NAME)
@SessionScoped
public class SessionController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static transient LoggerWrapper log = new LoggerWrapper(SessionController.class);

	private OperatorBean operator;
	private boolean logged;
	private String ipAddress;
	private String sessionId;
	private String tenantName;
	private Long customersCounter;
	private int tenantsCounter;
	private BreadCrumbController breadCrumbController;
	

	public SessionController() {
		super();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		ipAddress = request.getRemoteAddr();
		this.sessionId = request.getSession().getId();
		breadCrumbController = new BreadCrumbController();
	}

	public void loadOperator() {
		log.logDebug("*** ASSURANCE LOOKING FOR OPERATOR ***");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int atIndex = request.getRemoteUser().lastIndexOf("@");
		String operatorName = request.getRemoteUser().substring(0, atIndex);
		this.tenantName = request.getRemoteUser().substring(atIndex + 1);
		log.logDebug("found : " + operatorName);
		if (operatorName != null) {
			try {
				this.operator = OperatorBusiness.searchOperatorByUsername(operatorName);
				this.operator.setRole(OperatorBusiness.searchOperatorRights(operatorName));
				this.logged = true;
				log.logDebug("operator loaded");
			} catch (ServiceErrorException e) {
				// loggo su error: eccezione non dovrebbe accadere
				log.logError(e);
			}
		}
	}
	
	public void refreshCustomersCounter(){
		try{
			PartyService service = Utilities.findBean(ApplicationConstants.PARTY_SERVICE_BEAN, PartyService.class);
			customersCounter = service.countAllParties(Utilities.getTenantName());
		} catch (ServiceErrorException e) {
			LoggerWrapper log = new LoggerWrapper(SessionController.class);
			log.logException(e.getMessage(), e);
		}
		
		
	}
	
	public void refreshTenantsCounter(){
		try{
			OperatorService service = Utilities.findBean(ApplicationConstants.OPERATOR_SERVICE_BEAN, OperatorService.class);
			tenantsCounter = service.countAllTenants();
		} catch (ServiceErrorException e) {
			LoggerWrapper log = new LoggerWrapper(SessionController.class);
			log.logException(e.getMessage(), e);
		}
		
	}


	public OperatorBean getOperator() {
		return operator;
	}

	public void setOperator(OperatorBean operator) {
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

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	
	public int getTenantsCounter() {
		return tenantsCounter;
	}

	public void setTenantsCounter(int tenantsCounter) {
		this.tenantsCounter = tenantsCounter;
	}

	public Long getCustomersCounter() {
		return customersCounter;
	}

	public void setCustomersCounter(Long customersCounter) {
		this.customersCounter = customersCounter;
	}
}
