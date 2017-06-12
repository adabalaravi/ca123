package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefSolutionType;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolution;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

/**
 * @author alberto.marimpietri
 * 
 */
public final class SdpSolutionHelper extends SdpBaseHelper {

	private static SdpSolutionHelper instance;

	private SdpSolutionHelper() {
		super();
	}

	public static SdpSolutionHelper getInstance() {
		if (instance == null) {
			instance = new SdpSolutionHelper();
		}
		return instance;
	}

	public SdpSolution createSolution(String solutionName, String solutionDescription, String externalId, Date startDate, Date endDate,
			RefSolutionType solutionType, String solutionProfile, List<SdpPartyGroup> partyGroups, String createdBy, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolution newSolution = new SdpSolution();
		newSolution.setSolutionName(solutionName);
		newSolution.setSolutionDescription(solutionDescription);
		newSolution.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newSolution.setExternalId(externalId);
		newSolution.setStartDate(startDate);
		newSolution.setEndDate(endDate);
		newSolution.setRefSolutionType(solutionType);
		newSolution.setSolutionProfile(solutionProfile);
		if (partyGroups != null) {
			newSolution.setSdpPartyGroups(partyGroups);
		}
		newSolution.setCreatedById(createdBy);
		newSolution.setCreatedDate(new Date());
		em.persist(newSolution);
		return newSolution;
	}

	public void modifySolution(SdpSolution toModify, String solutionName, String solutionDescription, String externalId, Date startDate, Date endDate,
			RefSolutionType solutionType, String solutionProfile, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.setSolutionName(solutionName);
		toModify.setSolutionDescription(solutionDescription);
		toModify.setExternalId(externalId);
		toModify.setStartDate(startDate);
		toModify.setEndDate(endDate);
		toModify.setRefSolutionType(solutionType);
		toModify.setSolutionProfile(solutionProfile);
		toModify.setUpdatedById(updatedBy);
		toModify.setUpdatedDate(new Date());
	}

	public SdpSolution deleteSolution(SdpSolution solution, String deletedBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		solution.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		solution.setDeletedById(deletedBy);
		solution.setDeletedDate(new Date());
		return solution;
	}

	public SdpSolution searchSolutionByName(String solutionName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		if (Utilities.isNull(solutionName)) {
			return null;
		}
		parameHashMap.put("solutionName", solutionName);
		List<SdpSolution> results = NamedQueryHelper.executeNamedQuery(SdpSolution.class, "selectSolutionByName", parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public List<SdpSolution> searchAllSolutions(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSolution.class, "selectAllSolutions", null, startPosition, maxRecordsNumber, em);
	}

	public Long searchAllSolutionsCount(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQueryCount("selectAllSolutionsCount", null, em);
	}

	public void validateSolution(String solutionName, Long solutionTypeId, Date startDate, Date endDate) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_NAME, solutionName);
		validationMap.put(SOLUTION_TYPE_ID, solutionTypeId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		validationMap.clear();
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		ValidationUtils.validateDates(startDate, endDate);
		log.logDebug(VALIDATION_END);
	}

	public SdpSolution changeStatus(SdpSolution solution, Long nextstatus, String madeBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		solution.setStatusId(nextstatus);
		solution.setChangeStatusById(madeBy);
		solution.setChangeStatusDate(new Date());
		return solution;
	}

	public SdpSolution searchSolutionById(Long solutionId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionId == null) {
			return null;
		}
		return em.find(SdpSolution.class, solutionId);
	}

	public void validateSearchSolutionById(Long solutionId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("solutionId", solutionId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchSolutionByName(String solutionName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_NAME, solutionName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpSolution> searchSolutionsByPartyGroup(String partyGroupName, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(partyGroupName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("partyGroupName", partyGroupName);
		return NamedQueryHelper
				.executePaginatedNamedQuery(SdpSolution.class, "selectSolutionsByPartyGroup", parameHashMap, startPosition, maxRecordsNumber, em);
	}

	public Long countSolutionsByPartyGroup(String partyGroupName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(partyGroupName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("partyGroupName", partyGroupName);
		return NamedQueryHelper.executeNamedQueryCount("selectSolutionsByPartyGroupCount", parameHashMap, em);
	}

	public SdpSolution searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpSolution.class, "selectSolutionByExternalId", externalId, em);
	}

}
