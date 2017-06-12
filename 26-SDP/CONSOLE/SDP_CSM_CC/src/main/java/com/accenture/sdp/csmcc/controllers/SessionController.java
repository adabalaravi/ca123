package com.accenture.sdp.csmcc.controllers;

import java.io.Serializable;
import java.util.Stack;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.beans.OperatorInfo;
import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.beans.ServiceVariantBean;
import com.accenture.sdp.csmcc.beans.SolutionBean;
import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.business.OfferBusiness;
import com.accenture.sdp.csmcc.business.SolutionOfferBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.beans.BreadCrumbItem;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.managers.BaseManager;
import com.accenture.sdp.csmcc.managers.ServiceTemplateManager;
import com.accenture.sdp.csmcc.managers.ServiceVariantManager;
import com.accenture.sdp.csmcc.services.OperatorService;

@ManagedBean(name = ApplicationConstants.INFO_SESSION_BEAN_NAME)
@SessionScoped
public class SessionController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ipAddress;
	private String sessionId;
	private Stack<BreadCrumbItem> breadCrumb;
	private OperatorInfo operator;
	private PlatformBean platformBean;
	private ServiceTemplateBean serviceTemplate;
	private ServiceVariantBean serviceVariant;
	private SolutionBean solutionBean;
	private SolutionOfferBean solutionOfferBean;
	private BaseManager tableBean;
	private transient Holder<String> tenant;
	private String tenantName;
	private int commercialPackageCounter;
	private int technicalPackageCounter;
	

	public SessionController() {
		super();
		LoggerWrapper log = new LoggerWrapper(SessionController.class);
		try{
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ip = request.getRemoteAddr();
			ipAddress = ip;
			this.sessionId = request.getSession().getId();
		} catch (Exception e) {
			log.logException(e.getMessage(), e);
		}

	}
	

	public Stack<BreadCrumbItem> getBreadCrumb() {
		return breadCrumb;
	}

	public String getBreadCrumbAsString() {
		StringBuffer buffer = new StringBuffer("");
		int i = 0;
		if (breadCrumb != null) {
			for (BreadCrumbItem item : breadCrumb) {
				buffer.append(item.getDefaultTitle());
				if (i < breadCrumb.size() - 1){
					buffer.append("->");
				}
				i++;
			}
		}
		return buffer.toString();
	}

	public void setBreadCrumb(Stack<BreadCrumbItem> breadCrumb) {
		this.breadCrumb = breadCrumb;
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


	public PlatformBean getPlatformBean() {
		return platformBean;
	}

	public void setPlatformBean(PlatformBean platformBean) {
		this.platformBean = platformBean;
	}

	public ServiceTemplateBean getServiceTemplate() {
		return serviceTemplate;
	}

	public void setServiceTemplate(ServiceTemplateBean serviceTemplate) {
		this.serviceTemplate = serviceTemplate;
	}

	public SolutionBean getSolutionBean() {
		return solutionBean;
	}

	public void setSolutionBean(SolutionBean solutionBean) {
		this.solutionBean = solutionBean;
	}

	public SolutionOfferBean getSolutionOfferBean() {
		return solutionOfferBean;
	}

	public void setSolutionOfferBean(SolutionOfferBean solutionOfferBean) {
		this.solutionOfferBean = solutionOfferBean;
	}

	public ServiceVariantBean getServiceVariant() {
		return serviceVariant;
	}

	public void setServiceVariant(ServiceVariantBean serviceVariant) {
		this.serviceVariant = serviceVariant;
	}

	public BaseManager getTableBean() {
		return tableBean;
	}

	public void setTableBean(BaseManager tableBean) {
		this.tableBean = tableBean;
	}

	public void clearRelationData() {
		Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_TABLE_BEAN_NAME, ServiceTemplateManager.class).clearParentPlatform();
		ServiceVariantManager variant = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_TABLE_BEAN_NAME, ServiceVariantManager.class);
		variant.clearParentTemplate();
		variant.setDetails(false);

		platformBean = null;
		serviceTemplate = null;
		solutionBean = null;
		solutionOfferBean = null;
		serviceVariant = null;

	}


	public OperatorInfo getOperator() {
		return operator;
	}

	public void setOperator(OperatorInfo operator) {
		this.operator = operator;
	}


	public void loadOperator() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int atIndex = request.getRemoteUser().lastIndexOf("@");
		String operatorName = request.getRemoteUser().substring(0, atIndex);
		this.tenantName = request.getRemoteUser().substring(atIndex + 1);
		if (operatorName != null) {
			try {
				OperatorService service = Utilities.findBean(ApplicationConstants.OPERATOR_SERVICE_BEAN, OperatorService.class);
				this.operator = service.searchOperatorByUsername(operatorName);
				this.operator.setRole(service.searchOperatorRights(operatorName));
			} catch (ServiceErrorException e) {
				LoggerWrapper log = new LoggerWrapper(SessionController.class);
				log.logException(e.getMessage(), e);
			}
		}
	}
	public void refreshCommercialPackageCounter(){
		try{
			commercialPackageCounter = SolutionOfferBusiness.countAllCommercialPackages();
		} catch (ServiceErrorException e) {
			LoggerWrapper log = new LoggerWrapper(SessionController.class);
			log.logException(e.getMessage(), e);
		}
		
		
	}
	
	public void refreshOfferCounter(){
		try{
			technicalPackageCounter = OfferBusiness.countAllOffer();
		} catch (ServiceErrorException e) {
			LoggerWrapper log = new LoggerWrapper(SessionController.class);
			log.logException(e.getMessage(), e);
		}
		
	}

	

	public Holder<String> getTenant() {
		if (tenant == null){
			tenant = new Holder<String>(tenantName);
		}
		return tenant;
	}


	public void setTenant(Holder<String> tenant) {
		this.tenant = tenant; 
	}
	
	public int getCommercialPackageCounter() {
		return commercialPackageCounter;
	}


	public void setCommercialPackageCounter(int commercialPackageCounter) {
		this.commercialPackageCounter = commercialPackageCounter;
	}


	public int getTechnicalPackageCounter() {
		return technicalPackageCounter;
	}


	public void setTechnicalPackageCounter(int technicalPackageCounter) {
		this.technicalPackageCounter = technicalPackageCounter;
	}



}
