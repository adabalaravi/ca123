package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import org.apache.cxf.annotations.SchemaValidation;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpSolutionDto;
import com.accenture.sdp.csm.managers.SdpSolutionManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.solution.CreateSolutionComplexRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.CreateSolutionRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.DeleteSolutionRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.ModifySolutionPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.ModifySolutionRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.SearchSolutionByPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.SearchSolutionComplexRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.SearchSolutionRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.SolutionChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.solution.CreateSolutionResp;
import com.accenture.sdp.csmfe.webservices.response.solution.SearchSolutionComplexResp;
import com.accenture.sdp.csmfe.webservices.response.solution.SearchSolutionComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.solution.SearchSolutionResp;
import com.accenture.sdp.csmfe.webservices.utils.PartyGroupBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.SolutionBeanConverter;

@WebService(serviceName = "SdpSolutionService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpSolutionService extends BaseWebService {

	@WebMethod(action="cCreateSolution")
	@WebResult(name = "CreateSolutionResponse")
	public CreateSolutionResp cCreateSolution(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "CreateSolutionRequest") CreateSolutionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		CreateSolutionResp wsResp = new CreateSolutionResp();

		try {
			CreateServiceResponse resp = SdpSolutionManager.getInstance().createSolution(
					trim(request.getSolutionName()),
					trim(request.getSolutionDescription()), 
					trim(request.getExternalId()), 
					request.getStartDate(),
					request.getEndDate(),
					request.getSolutionTypeId(),
					trim(request.getProfile()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setSolutionId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="createSolution")
	@WebResult(name = "CreateSolutionResponse")
	public CreateSolutionResp createSolution(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "CreateSolutionRequest") CreateSolutionComplexRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		CreateSolutionResp wsResp = new CreateSolutionResp();

		try {
			CreateServiceResponse resp = SdpSolutionManager.getInstance().createSolution(
					trim(request.getSolutionName()),
					trim(request.getSolutionDescription()), 
					trim(request.getExternalId()), 
					request.getStartDate(),
					request.getEndDate(),
					request.getSolutionTypeId(),
					trim(request.getProfile()),
					SolutionBeanConverter.convertPartyGroupNames(request.getPartyGroups().getPartyGroupList()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setSolutionId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	
	@WebMethod(action="cModifySolution")
	@WebResult(name="ModifySolutionResponse")
	public BaseResp cModifySolution( @WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "ModifySolutionRequest") ModifySolutionRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionManager.getInstance().modifySolution(
					request.getSolutionId(),
					trim(request.getSolutionName()),
					request.getSolutionTypeId(),
					trim(request.getSolutionDescription()), 
					trim(request.getExternalId()), 
					request.getStartDate(),
					request.getEndDate(),
					trim(request.getProfile()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="modifySolutionPartyGroup")
	@WebResult(name="ModifySolutionResponse")
	public BaseResp modifySolutionPartyGroup( @WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "ModifySolutionPartyGroupRequest") ModifySolutionPartyGroupRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionManager.getInstance().modifySolutionPartyGroups(
					request.getSolutionId(),
					PartyGroupBeanConverter.convertPartyGroupsOperations(request.getPartyGroups().getPartyGroupList()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="cDeleteSolution")
	@WebResult(name="DeleteSolutionResponse")
	public BaseResp cDeleteSolution(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "DeleteSolutionRequest") DeleteSolutionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionManager.getInstance().deleteSolution(
					request.getSolutionId(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="cSearchSolution")
	@WebResult(name="SearchSolutionResponse")
	public SearchSolutionResp cSearchSolution(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "SearchSolutionRequest") SearchSolutionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionResp wsResp = new SearchSolutionResp();
		try {
			SearchServiceResponse resp = SdpSolutionManager.getInstance().searchSolution(
					(request.getSolutionId()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getSolutions().setSolutionList(SolutionBeanConverter.convertSolution((List<SdpSolutionDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="searchSolution")
	@WebResult(name="SearchSolutionComplexResponse")
	public SearchSolutionComplexResp searchSolution(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "SearchSolutionComplexRequest") SearchSolutionComplexRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionComplexResp wsResp = new SearchSolutionComplexResp();
		try {
			SearchServiceResponse resp = SdpSolutionManager.getInstance().searchSolution(
					(trim(request.getSolutionName())),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getSolutions().setSolutionList(SolutionBeanConverter.convertSolutionComplex((List<SdpSolutionDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="searchAllSolutions")
	@WebResult(name="SearchAllSolutionsResponse")
	public SearchSolutionComplexRespPaginated searchAllSolutions(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "SearchAllSolutionRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionComplexRespPaginated wsResp = new SearchSolutionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionManager.getInstance().searchAllSolutions(
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getSolutions().setSolutionList(SolutionBeanConverter.convertSolutionComplex((List<SdpSolutionDto>)resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="searchSolutionByPartyGroup")
	@WebResult(name="SearchSolutionByPartyGroupResponse")
	public SearchSolutionComplexRespPaginated searchSolutionByPartyGroup (
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "SearchSolutionByPartyGroupRequest") SearchSolutionByPartyGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionComplexRespPaginated wsResp = new SearchSolutionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionManager.getInstance().searchSolutionsByPartyGroup(
					trim(request.getPartyGroupName()),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSolutions().setSolutionList(SolutionBeanConverter.convertSolutionComplex((List<SdpSolutionDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="solutionChangeStatus")
	@WebResult(name="SolutionChangeStatusResponse")
	public BaseResp solutionChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName, @WebParam(name = "SolutionChangeStatusRequest") SolutionChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse  resp = SdpSolutionManager.getInstance().solutionChangeStatus(
					request.getSolutionId(),
					trim(request.getStatus()),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	
}