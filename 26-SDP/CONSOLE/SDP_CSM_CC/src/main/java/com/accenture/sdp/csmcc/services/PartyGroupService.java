/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CCreatePartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CCreatePartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CDeletePartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CDeletePartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CModifyPartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CModifyPartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CreatePartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.CreatePartyGroupResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.DeletePartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.ModifyPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SdpPartyGroupService;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SdpPartyGroupService_Service;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchAllPartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchAllPartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchPartyGroupResp;



@ManagedBean(name = ApplicationConstants.PARTYGROUP_SERVICE_BEAN_NAME)
@ApplicationScoped
public class PartyGroupService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpPartyGroupService port;


	public SearchPartyGroupResp searchAllPartyGroups(Holder<String> tenantName) throws ServiceErrorException{

		//Request
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(0L);
		r.setStartPosition(0L);

		//webmethod
		SearchAllPartyGroup req = new SearchAllPartyGroup();
		req.setSearchAllPartyGroupsRequest(r);
		SearchAllPartyGroupResponse result = port.searchAllPartyGroup(req, tenantName);
		SearchPartyGroupResp resp= result.getSearchAllPartyGroupsResponse();
		parseResponse(resp);
		return resp;
	}
	
	


	public CreatePartyGroupResp createPartyGroup(String partyGroupName, String partyGroupDescription, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		CreatePartyGroupRequest r=new CreatePartyGroupRequest();
		r.setPartyGroupName(partyGroupName);
		r.setPartyGroupDescription(partyGroupDescription);
		//webmethod
		CCreatePartyGroup req = new CCreatePartyGroup();
		req.setCreatePartyGroupRequest(r);
		CCreatePartyGroupResponse result = port.cCreatePartyGroup(req, tenantName);
		CreatePartyGroupResp resp= result.getCreatePartyGroupResponse();
		parseResponse(resp);
		return resp;
	}
	
	public BaseResp modifyPartyGroup(long partyGroupId, String partyGroupName, String partyGroupDescription, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		ModifyPartyGroupRequest r=new ModifyPartyGroupRequest();
		r.setPartyGroupId(partyGroupId);
		r.setPartyGroupName(partyGroupName);
		r.setPartyGroupDescription(partyGroupDescription);
		//webmethod
		CModifyPartyGroup req = new CModifyPartyGroup();
		req.setModifyPartyGroupRequest(r);
		CModifyPartyGroupResponse result = port.cModifyPartyGroup(req, tenantName);
		BaseResp resp= result.getModifyPartyGroupResponse();
		parseResponse(resp);
		return resp;
	}
	
	public BaseResp deletePartyGroup(long partyGroupId, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		DeletePartyGroupRequest r=new DeletePartyGroupRequest();
		r.setPartyGroupId(partyGroupId);
		//webmethod
		CDeletePartyGroup req = new CDeletePartyGroup();
		req.setDeletePartyGroupRequest(r);
		CDeletePartyGroupResponse result = port.cDeletePartyGroup(req, tenantName);
		BaseResp resp= result.getDeletePartyGroupResponse();
		parseResponse(resp);
		return resp;
	}



	public PartyGroupService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.PARTYGROUP_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpPartyGroupService_Service service=new SdpPartyGroupService_Service(url);
		port=service.getSdpPartyGroupServicePort();


	}


	private void parseResponse(BaseResp resp) throws ServiceErrorException{
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())){
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters()!=null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()){
					parameters.add(new String[]{param.getName(), param.getValue()});
				}
			}
			throw new ServiceErrorException(resp.getResultCode(),
					Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}

}
