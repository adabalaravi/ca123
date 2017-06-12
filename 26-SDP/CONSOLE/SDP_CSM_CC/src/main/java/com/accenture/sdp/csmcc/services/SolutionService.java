/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.solution.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solution.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.CDeleteSolution;
import com.accenture.sdp.csmfe.webservices.clients.solution.CDeleteSolutionResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.CModifySolution;
import com.accenture.sdp.csmfe.webservices.clients.solution.CModifySolutionResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.CSearchSolution;
import com.accenture.sdp.csmfe.webservices.clients.solution.CSearchSolutionResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.CreateSolution;
import com.accenture.sdp.csmfe.webservices.clients.solution.CreateSolutionComplexRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.CreateSolutionResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.CreateSolutionResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.DeleteSolutionRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.ModifySolutionPartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.solution.ModifySolutionPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.ModifySolutionPartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.ModifySolutionRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.PartyGroupNameInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.PartyGroupNameListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.PartyGroupOperationInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.PartyGroupOperationListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.SdpSolutionService;
import com.accenture.sdp.csmfe.webservices.clients.solution.SdpSolutionService_Service;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchAllSolutions;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchAllSolutionsResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolution;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionResponse;
import com.accenture.sdp.csmfe.webservices.clients.solution.SolutionChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.solution.SolutionChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.solution.SolutionChangeStatusResponse;

@ManagedBean(name = ApplicationConstants.SOLUTION_SERVICE_BEAN_NAME)
@ApplicationScoped
public class SolutionService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpSolutionService port;


	public Long createSolution(String solutionName,
			String solutionDescription, String externalId,
			String solutionProfile,Date solutionStartDate,Date solutionEndDate,
			Long solutionTypeId,List<String> partyGroups, Holder<String> tenantName) throws DatatypeConfigurationException, ServiceErrorException{
		

		CreateSolutionComplexRequest r=new CreateSolutionComplexRequest();
		r.setSolutionName(solutionName);
		r.setSolutionDescription(solutionDescription);
		r.setExternalId(externalId);
		r.setProfile(solutionProfile);
		if (solutionStartDate!=null) {
			r.setStartDate(Utilities.getXMLGregorianCalendar(solutionStartDate));
		}
		if (solutionEndDate!=null) {
			r.setEndDate(Utilities.getXMLGregorianCalendar(solutionEndDate));
		}
		r.setSolutionTypeId(solutionTypeId);
		PartyGroupNameListRequest groups = new PartyGroupNameListRequest();
		for (String groupName : partyGroups) {
			PartyGroupNameInfoRequest groupNameReq = new PartyGroupNameInfoRequest();
			groupNameReq.setPartyGroupName(groupName);
			groups.getPartyGroup().add(groupNameReq);
		}
		r.setPartyGroups(groups);
		CreateSolution req = new CreateSolution();
		req.setCreateSolutionRequest(r);
		CreateSolutionResponse result  = port.createSolution(req, tenantName);
		CreateSolutionResp resp = result.getCreateSolutionResponse();
		parseResponse(resp);
		return resp.getSolutionId();
	}

	public void modifySolution(String solutionName,
			String solutionDescription, String externalId,
			String solutionProfile,Date solutionStartDate,Date solutionEndDate,Long solutionTypeId,
			Long solutionId, Holder<String> tenantName) throws DatatypeConfigurationException, ServiceErrorException{
		
		//Request
		ModifySolutionRequest r=new ModifySolutionRequest();
		r.setSolutionName(solutionName);
		r.setSolutionDescription(solutionDescription);
		r.setExternalId(externalId);
		r.setProfile(solutionProfile);
		if (solutionStartDate!=null){
			r.setStartDate(Utilities.getXMLGregorianCalendar(solutionStartDate));
		}
		if (solutionEndDate!=null){
			r.setEndDate(Utilities.getXMLGregorianCalendar(solutionEndDate));
		}
		r.setSolutionId(solutionId);
		r.setSolutionTypeId(solutionTypeId);
		//webmethod
		CModifySolution req = new CModifySolution();
		req.setModifySolutionRequest(r);
		CModifySolutionResponse result = port.cModifySolution(req, tenantName);
		BaseResp resp = result.getModifySolutionResponse();
		parseResponse(resp);
	}
	
	public void updatePartyGroups(Collection<PartyGroupBean> partyGroupIds,
			Long solutionId, Holder<String> tenantName) throws DatatypeConfigurationException, ServiceErrorException{
		
		//Request
		ModifySolutionPartyGroupRequest r=new ModifySolutionPartyGroupRequest();
		PartyGroupOperationListRequest groupRequest=new PartyGroupOperationListRequest();
		for(PartyGroupBean item:partyGroupIds){
			PartyGroupOperationInfoRequest groupInfo=new PartyGroupOperationInfoRequest();
			groupInfo.setOperation(item.getOperation());
			groupInfo.setPartyGroupId(item.getPartyGroupId());
			groupRequest.getPartyGroup().add(groupInfo);
		}
//
		r.setSolutionId(solutionId);
		r.setPartyGroups(groupRequest);
		//webmethod
		ModifySolutionPartyGroup req = new ModifySolutionPartyGroup();
		req.setModifySolutionPartyGroupRequest(r);
		ModifySolutionPartyGroupResponse result = port.modifySolutionPartyGroup(req, tenantName);
		BaseResp resp = result.getModifySolutionResponse();
		parseResponse(resp);
	}

	public void deleteSolution(Long solutionId, Holder<String> tenantName) throws ServiceErrorException{
		
		DeleteSolutionRequest r=new DeleteSolutionRequest();
		r.setSolutionId(solutionId);
		CDeleteSolution req = new CDeleteSolution();
		req.setDeleteSolutionRequest(r);
		CDeleteSolutionResponse result = port.cDeleteSolution(req, tenantName);
		BaseResp resp = result.getDeleteSolutionResponse();
		parseResponse(resp);
	}

	public SearchSolutionComplexRespPaginated searchAllSolutions(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		//webmethod
		SearchAllSolutions req = new SearchAllSolutions();
		req.setSearchAllSolutionRequest(r);
		SearchAllSolutionsResponse result = port.searchAllSolutions(req, tenantName);
		SearchSolutionComplexRespPaginated resp = result.getSearchAllSolutionsResponse();
		parseResponse(resp);
		return resp;
	}
	
	
	public void changeStatus(long id, String status, Holder<String> tenantName) throws ServiceErrorException{
		SolutionChangeStatusRequest r = new SolutionChangeStatusRequest();
		r.setSolutionId(id);
		r.setStatus(status);
		SolutionChangeStatus req = new SolutionChangeStatus();
		req.setSolutionChangeStatusRequest(r);
		SolutionChangeStatusResponse result = port.solutionChangeStatus(req, tenantName);
		BaseResp resp = result.getSolutionChangeStatusResponse();
		parseResponse(resp);
	}
	
	public SolutionService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);		
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SOLUTION_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpSolutionService_Service service=new SdpSolutionService_Service(url);
		port=service.getSdpSolutionServicePort();
		


	}
	
	
	public SearchSolutionComplexResp searchSolutionByName(String name, Holder<String> tenantName) throws ServiceErrorException{
		SearchSolutionComplexRequest r = new SearchSolutionComplexRequest();
		r.setSolutionName(name);
		SearchSolution req = new SearchSolution();
		req.setSearchSolutionComplexRequest(r);
		SearchSolutionResponse result = port.searchSolution(req, tenantName);
		SearchSolutionComplexResp resp = result.getSearchSolutionComplexResponse();
		parseResponse(resp);
		return resp;
	}
	
	public SearchSolutionResp searchSolutionById(Long id, Holder<String> tenantName) throws ServiceErrorException{
		SearchSolutionRequest r = new SearchSolutionRequest();
		r.setSolutionId(id);
		CSearchSolution req = new CSearchSolution();
		req.setSearchSolutionRequest(r);
		CSearchSolutionResponse result = port.cSearchSolution(req, tenantName);
		SearchSolutionResp resp = result.getSearchSolutionResponse();
		parseResponse(resp);
		return resp;
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
