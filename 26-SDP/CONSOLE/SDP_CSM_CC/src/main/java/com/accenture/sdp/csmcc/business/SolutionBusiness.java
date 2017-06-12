package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;

import com.accenture.sdp.csmcc.beans.SolutionBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.converters.SolutionConverter;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.SolutionService;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexRespPaginated;



public final class SolutionBusiness  {

	private SolutionBusiness(){

	}


	public static List<SolutionBean> getBufferedData(long startRow, long numElementToFind) throws ServiceErrorException{

		SolutionService service = Utilities.findBean(ApplicationConstants.SOLUTION_SERVICE_BEAN_NAME, SolutionService.class);
		List<SolutionBean> tableResult= new ArrayList<SolutionBean>();


		SearchSolutionComplexRespPaginated resp=service.searchAllSolutions(startRow, numElementToFind, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = SolutionConverter.buildSolutionTable(resp);
		}
		return tableResult;

	}

	public static SolutionBean searchSolutionByName(String solutionName) throws ServiceErrorException{
		SolutionBean result = null;
		SolutionService solutionService = Utilities.findBean(ApplicationConstants.SOLUTION_SERVICE_BEAN_NAME, SolutionService.class);
		SearchSolutionComplexResp resp = solutionService.searchSolutionByName(solutionName, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			result = SolutionConverter.buildSolutionTable(resp).get(0);
		}
		return result;


	}

	public void addSolution(SolutionBean solutionBean) throws DatatypeConfigurationException {

		String solutionName = solutionBean.getSolutionName();
		Long solutionTypeId = solutionBean.getSolutionTypeId();
		String solutionDesc = solutionBean.getSolutionDesc();
		String solutionExtId = solutionBean.getSolutionExtId();
		String solutionProfile = solutionBean.getSolutionProfile();
		Date solutionStartDate = solutionBean.getSolutionStartDate();
		Date solutionEndDate = solutionBean.getSolutionEndDate();
		List<String> partyGroupItemListSelectedAll = null;

		LoggerWrapper log = new LoggerWrapper(SolutionBean.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			// logging
			String solutionNameLbl = MessageConstants.SOLUTION_NAME_LBL;
			String solutionDescLbl = MessageConstants.SOLUTION_DESC_LBL;
			String solutionStartDateLbl = MessageConstants.SOLUTION_STARTDATE_LBL;
			String solutionEndDateLbl = MessageConstants.SOLUTION_ENDDATE_LBL;
			String solutionExtIdLbl = MessageConstants.SOLUTION_EXTID_LBL;
			String solutionProfileLbl = MessageConstants.SOLUTION_PROFILE_LBL;
			String solutionPartyGroupsLbl = MessageConstants.SOLUTION_PARTYGROUPS_LBL;
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(solutionNameLbl, solutionName);
			logMap.put(solutionDescLbl, solutionDesc);
			logMap.put(solutionStartDateLbl, solutionStartDate);
			logMap.put(solutionEndDateLbl, solutionEndDate);
			logMap.put(solutionExtIdLbl, solutionExtId);
			logMap.put(solutionProfileLbl, solutionProfile);
			logMap.put(solutionPartyGroupsLbl, partyGroupItemListSelectedAll);
			log.logStartFeature(logMap);
			logMap.clear();

			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				SolutionService service = Utilities.findBean(ApplicationConstants.SOLUTION_SERVICE_BEAN_NAME, SolutionService.class);
				service.createSolution(solutionName, solutionDesc, solutionExtId, solutionProfile, solutionStartDate, solutionEndDate, solutionTypeId,
						partyGroupItemListSelectedAll, Utilities.getTenantName());
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_SOLUTION_MESSAGE), solutionName);
				code = ApplicationConstants.CODE_OK;


				msgBean.setNextParam(PathConstants.SOLUTION);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			// logging
			log.logEndFeature(code, mex);
		}
	}

	public void updateSolution(SolutionBean solutionBean) throws DatatypeConfigurationException {

		String solutionName = solutionBean.getSolutionName();
		Long solutionId = solutionBean.getSolutionId();
		Long solutionTypeId = solutionBean.getSolutionTypeId();
		String solutionDesc = solutionBean.getSolutionDesc();
		String solutionExtId = solutionBean.getSolutionExtId();
		String solutionProfile = solutionBean.getSolutionProfile();
		Date solutionStartDate = solutionBean.getSolutionStartDate();
		Date solutionEndDate = solutionBean.getSolutionEndDate();
		
		LoggerWrapper log = new LoggerWrapper(SolutionBean.class);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ApplicationConstants.SOLUTION_VALIDATION_NAME_FIELD, solutionName);
		validationMap.put(ApplicationConstants.SOLUTION_VALIDATION_TYPE_FIELD, solutionTypeId);
		if (ValidationUtilities.validateMandatoryFields(validationMap)) {
			// logging
			String solutionNameLbl = MessageConstants.SOLUTION_NAME_LBL;
			String solutionDescLbl = MessageConstants.SOLUTION_DESC_LBL;
			String solutionStartDateLbl = MessageConstants.SOLUTION_STARTDATE_LBL;
			String solutionEndDateLbl = MessageConstants.SOLUTION_ENDDATE_LBL;
			String solutionExtIdLbl = MessageConstants.SOLUTION_EXTID_LBL;
			String solutionProfileLbl = MessageConstants.SOLUTION_PROFILE_LBL;
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(solutionNameLbl, solutionName);
			logMap.put(solutionDescLbl, solutionDesc);
			logMap.put(solutionStartDateLbl, solutionStartDate);
			logMap.put(solutionEndDateLbl, solutionEndDate);
			logMap.put(solutionExtIdLbl, solutionExtId);
			logMap.put(solutionProfileLbl, solutionProfile);
			log.logStartFeature(logMap);
			logMap.clear();

			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				SolutionService service = Utilities.findBean(ApplicationConstants.SOLUTION_SERVICE_BEAN_NAME, SolutionService.class);
				service.modifySolution(solutionName, solutionDesc, solutionExtId, solutionProfile, solutionStartDate, solutionEndDate, solutionTypeId,
						solutionId, Utilities.getTenantName());
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_SOLUTION_MESSAGE), solutionName);
				code = ApplicationConstants.CODE_OK;

				msgBean.setUpdateFlag(true);
				msgBean.setNextParam(PathConstants.SOLUTION);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			// logging
			log.logEndFeature(code, mex);

		}
	}

}
