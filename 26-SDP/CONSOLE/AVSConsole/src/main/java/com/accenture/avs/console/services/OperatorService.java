package com.accenture.avs.console.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.accenture.avs.console.beans.operator.OperatorInfo;
import com.accenture.avs.console.beans.operator.OperatorRoleInfo;
import com.accenture.avs.console.beans.operator.TenantInfo;
import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.PropertyManager;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.exception.ServiceErrorException;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.converters.OperatorConverter;
import com.accenture.avs.console.converters.OperatorRightsConverter;
import com.accenture.sdp.csmfe.webservices.clients.operators.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.operators.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.ChangeOperatorPasswordRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.CreateOperatorRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.LoginOperatorRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.LoginOperatorResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorRightsRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorRoleRightListRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorTenantInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorTenantListRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorTenantRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorRightInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.ResetOperatorPasswordRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ResetPasswordResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SdpOperatorsService;
import com.accenture.sdp.csmfe.webservices.clients.operators.SdpOperatorsService_Service;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorByNameRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorByTenantRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorRightResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchTenantResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.TenantInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.TenantListRequest;

@ManagedBean(name = ApplicationConstants.SERVICE_OPERATOR_BEAN)
@ApplicationScoped
public class OperatorService {

	private static LoggerWrapper log = new LoggerWrapper(OperatorService.class);
	private SdpOperatorsService port;

	public OperatorService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.WSDL_OPERATOR_URL);
		try {
			URL url = new URL(urlString);
			SdpOperatorsService_Service service = new SdpOperatorsService_Service(url);
			port = service.getSdpOperatorsServicePort();
			log.logDebug("SdpOperatorsService instantiated");
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public LoginOperatorResp loginOperator(String username, String password, String tenantName) throws ServiceErrorException {
		LoginOperatorRequest loginOperatorReq = new LoginOperatorRequest();
		log.logDebug("Searching operator for username & password: %s %s", username, password);
		loginOperatorReq.setPassword(password);
		loginOperatorReq.setUsername(username);
		loginOperatorReq.setTenantName(tenantName);
		LoginOperatorResp resp = port.loginOperator(loginOperatorReq);
		parseResponse(resp);
		return resp;
	}

	public List<OperatorInfo> searchAllOperators() throws ServiceErrorException {
		BaseRequestPaginated req = new BaseRequestPaginated();
		req.setMaxRecordsNumber(0L);
		req.setStartPosition(0L);
		SearchOperatorComplexRespPaginated resp = port.searchAllOperators(req);
		parseResponse(resp);
		return OperatorConverter.convertOperatorInfoToBean(resp.getOperators().getOperator());
	}
	
	public Long countAllOperators() throws ServiceErrorException {
		BaseRequestPaginated req = new BaseRequestPaginated();
		req.setMaxRecordsNumber(1L);
		req.setStartPosition(0L);
		SearchOperatorComplexRespPaginated resp = port.searchAllOperators(req);
		parseResponse(resp);
		return resp.getTotalResult();
	}

	public List<OperatorInfo> searchOperatorByFirstNameAndLastName(String firstName, String lastName) throws ServiceErrorException {
		SearchOperatorByNameRequest request = new SearchOperatorByNameRequest();
		request.setStartPosition(0L);
		request.setMaxRecordsNumber(0L);
		request.setFirstName(firstName);
		request.setLastName(lastName);
		SearchOperatorComplexRespPaginated resp = port.searchOperatorByName(request);
		parseResponse(resp);
		return OperatorConverter.convertOperatorInfoToBean(resp.getOperators().getOperator());
	}

	public OperatorInfo searchOperatorByUsername(String username) throws ServiceErrorException {
		SearchOperatorRequest request = new SearchOperatorRequest();
		request.setUsername(username);
		SearchOperatorComplexResp resp = port.searchOperator(request);
		parseResponse(resp);
		return OperatorConverter.convertOperatorInfoToBean(resp.getOperators().getOperator().get(0));
	}

	public List<OperatorInfo> searchOperatorByTenant(String tenantName) throws ServiceErrorException {
		SearchOperatorByTenantRequest request = new SearchOperatorByTenantRequest();
		request.setStartPosition(0L);
		request.setMaxRecordsNumber(0L);
		request.setTenantName(tenantName);
		SearchOperatorComplexRespPaginated resp = port.searchOperatorByTenant(request);
		parseResponse(resp);
		return OperatorConverter.convertOperatorInfoToBean(resp.getOperators().getOperator());
	}

	public BaseResp createOperator(String username, String firstName, String lastName, String password, String email, List<String> tenantNames) throws ServiceErrorException {
		CreateOperatorRequest r = new CreateOperatorRequest();
		r.setUsername(username);
		r.setFirstName(firstName);
		r.setLastName(lastName);
		r.setPassword(password);
		r.setEmail(email);
		TenantListRequest tenants = new TenantListRequest();
		for (String tenantName : tenantNames) {
			TenantInfoRequest treq = new TenantInfoRequest();
			treq.setTenantName(tenantName);
			tenants.getTenant().add(treq);
		}
		r.setTenants(tenants);
		// webmethod
		BaseResp resp = port.createOperator(r);
		parseResponse(resp);
		return resp;
	}

	public BaseResp deleteOperator(String username) throws ServiceErrorException {
		SearchOperatorRequest r = new SearchOperatorRequest();
		r.setUsername(username);
		// webmethod
		BaseResp resp = port.deleteOperator(r);
		parseResponse(resp);
		return resp;
	}

	public BaseResp modifyOperator(String username, String firstName, String lastName, String email, String status) throws ServiceErrorException {
		ModifyOperatorRequest request = new ModifyOperatorRequest();
		request.setFirstName(firstName);
		request.setLastName(lastName);
		request.setEmail(email);
		request.setStatus(status);
		request.setUsername(username);
		BaseResp resp = port.modifyOperator(request);
		parseResponse(resp);
		return resp;
	}

	public BaseResp modifyOperatorRoleRights(String username, ModifyOperatorRoleRightListRequest rights) throws ServiceErrorException {
		ModifyOperatorRightsRequest request = new ModifyOperatorRightsRequest();
		request.setUsername(username);
		request.setRights(rights);
		BaseResp resp = port.modifyOperatorRoleRights(request);
		parseResponse(resp);
		return resp;
	}

	public ResetPasswordResp resetOperatorPassword(String username) throws ServiceErrorException {
		ResetOperatorPasswordRequest r = new ResetOperatorPasswordRequest();
		r.setUsername(username);
		ResetPasswordResp resp = port.resetOperatorPassword(r);
		parseResponse(resp);
		return resp;
	}

	public BaseResp changeOperatorPassword(String username, String oldPassword, String newPassword, String confirmNewPassword) throws ServiceErrorException {
		ChangeOperatorPasswordRequest r = new ChangeOperatorPasswordRequest();
		r.setUsername(username);
		r.setOldPassword(oldPassword);
		r.setNewPassword(newPassword);
		r.setConfirmNewPassword(confirmNewPassword);
		BaseResp resp = port.changeOperatorPassword(r);
		parseResponse(resp);
		return resp;
	}

	public List<TenantInfo> searchAllTenants() throws ServiceErrorException {
		SearchTenantResp resp = port.searchAllTenants();
		parseResponse(resp);
		return OperatorConverter.convertTenantInfoToBean(resp.getTenants().getTenant());
	}
	
	
	public int countAllTenants() throws ServiceErrorException {
		SearchTenantResp resp = port.searchAllTenants();
		parseResponse(resp);
		return resp.getTenants().getTenant().size();
	}

	public BaseResp modifyOperatorTenants(String username, List<String> removeTenants, List<String> addTenants) throws ServiceErrorException {
		ModifyOperatorTenantRequest request = new ModifyOperatorTenantRequest();
		ModifyOperatorTenantListRequest operations = new ModifyOperatorTenantListRequest();
		if (removeTenants != null) {
			for (String t : removeTenants) {
				ModifyOperatorTenantInfoRequest o = new ModifyOperatorTenantInfoRequest();
				o.setTenantName(t);
				o.setOperation("Delete");
				operations.getTenant().add(o);
			}
		}
		if (addTenants != null) {
			for (String t : addTenants) {
				ModifyOperatorTenantInfoRequest o = new ModifyOperatorTenantInfoRequest();
				o.setTenantName(t);
				o.setOperation("New");
				operations.getTenant().add(o);
			}
		}
		request.setTenants(operations);
		request.setUsername(username);
		BaseResp resp = port.modifyOperatorTenant(request);
		parseResponse(resp);
		return resp;
	}

	// ///////// RIGHTS HANDLING ////////////////
	public SearchOperatorRightResp searchAllRights() throws ServiceErrorException {
		SearchOperatorRightResp resp = port.searchAllOperatorRights();
		parseResponse(resp);
		return resp;
	}

	public Map<String, Long> getRightsMap() throws ServiceErrorException {
		SearchOperatorRightResp resp = port.searchAllOperatorRights();
		parseResponse(resp);
		Map<String, Long> map = new HashMap<String, Long>();
		if (resp != null && resp.getRights().getRight() != null) {
			for (OperatorRightInfoResp i : resp.getRights().getRight()) {
				map.put(i.getResourceName() + i.getRightName(), i.getRightId());
			}
		}
		return map;
	}

	public OperatorRoleInfo searchOperatorRights(String username) throws ServiceErrorException {
		SearchOperatorRequest request = new SearchOperatorRequest();
		request.setUsername(username);
		SearchOperatorRightResp resp = port.searchOperatorRights(request);
		parseResponse(resp);
		return OperatorRightsConverter.convertRights(resp.getRights().getRight());
	}

	private void parseResponse(BaseResp resp) throws ServiceErrorException {
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())) {
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters() != null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()) {
					parameters.add(new String[] { param.getName(), param.getValue() });
				}
			}
			throw new ServiceErrorException(resp.getResultCode(), Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}

}