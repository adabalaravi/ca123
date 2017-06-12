package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolution;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpPartyGroupHelper extends SdpBaseHelper {

	private static SdpPartyGroupHelper instance;

	private SdpPartyGroupHelper() {
		super();
	}

	public static SdpPartyGroupHelper getInstance() {
		if (instance == null) {
			instance = new SdpPartyGroupHelper();
		}
		return instance;
	}

	public SdpPartyGroup createPartyGroup(String partyGroupName, String partyGroupDescription, String createdById, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyGroup newPartyGroup = new SdpPartyGroup();
		// Model valorization
		newPartyGroup.setPartyGroupName(partyGroupName);
		newPartyGroup.setPartyGroupDescription(partyGroupDescription);
		newPartyGroup.setCreatedById(createdById);
		newPartyGroup.setCreatedDate(new Date());
		em.persist(newPartyGroup);
		return newPartyGroup;
	}

	public void deletePartyGroup(SdpPartyGroup partyGroupToDelete, String deletedBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(partyGroupToDelete);
	}

	public void modifyPartyGroup(SdpPartyGroup partyGroupToUpdate, String partyGroupName, String partyGroupDescription, String modifiedBy, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		partyGroupToUpdate.setPartyGroupName(partyGroupName);
		partyGroupToUpdate.setPartyGroupDescription(partyGroupDescription);
		partyGroupToUpdate.setUpdatedById(modifiedBy);
		partyGroupToUpdate.setUpdatedDate(new Date());
	}

	public SdpPartyGroup searchPartyGroupById(Long partyGroupId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyGroupId == null) {
			return null;
		}
		return em.find(SdpPartyGroup.class, partyGroupId);
	}

	public List<SdpPartyGroup> searchAllPartyGroups(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpPartyGroup.class, SdpPartyGroup.QUERY_RETRIEVE_ALL, null, startPosition,
				maxRecordsNumber, em);
	}

	public Long searchAllPartyGroupsCount(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQueryCount(SdpPartyGroup.QUERY_COUNT_ALL, null, em);
	}

	public List<SdpPartyGroup> searchAllPartyGroups(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQuery(SdpPartyGroup.class, SdpPartyGroup.QUERY_RETRIEVE_ALL, paramHashMap, em);
	}

	public SdpPartyGroup searchPartyGroupByName(String partyGroupName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(partyGroupName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpPartyGroup.PARAM_PARTY_GROUP_NAME, partyGroupName);
		List<SdpPartyGroup> results = NamedQueryHelper.executeNamedQuery(SdpPartyGroup.class, SdpPartyGroup.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public void validateSearchPartyGroupById(Long partyGroupId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_GROUP_ID, partyGroupId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End parameters validation");
	}

	public void validatePartyGroup(String partyGroupName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_GROUP_NAME, partyGroupName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateLinkUpdateOperation(List<SdpPartyGroupRequestDto> partyGroups) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (partyGroups == null || partyGroups.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, new ParameterDto(PARTY_GROUP_ID, null), new ParameterDto("operation", null));
		}
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		for (SdpPartyGroupRequestDto dto : partyGroups) {
			validationMap.put(OPERATION, dto.getOperation());
			validationMap.put(PARTY_GROUP_ID, dto.getPartyGroupId());
			ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
			validationMap.clear();
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
			}
			log.logDebug("Validating Operation");
			res = ValidationUtils.validateLinkUpdateOperation(dto.getOperation());
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public void addPartyGroupLink(SdpSolution toModify, SdpPartyGroup partyGroup, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.getSdpPartyGroups().add(partyGroup);
		partyGroup.getSdpSolutions().add(toModify);
		toModify.setUpdatedById(updatedBy);
		toModify.setUpdatedDate(new Date());
	}

	public void removePartyGroupLink(SdpSolution toModify, SdpPartyGroup partyGroup, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.getSdpPartyGroups().remove(partyGroup);
		partyGroup.getSdpSolutions().remove(toModify);
		toModify.setUpdatedById(updatedBy);
		toModify.setUpdatedDate(new Date());
	}

	public void addPartyGroupLink(SdpParty toModify, SdpPartyGroup partyGroup, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.getPartyGroups().add(partyGroup);
		partyGroup.getSdpParties().add(toModify);
		// UpdatedById/UpdateDate non modificabili sulle viste di AVS
	}

	public void removePartyGroupLink(SdpParty toModify, SdpPartyGroup partyGroup, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.getPartyGroups().remove(partyGroup);
		partyGroup.getSdpParties().remove(toModify);
		// UpdatedById/UpdateDate non modificabili sulle viste di AVS
	}

	public void addPartyGroupLink(SdpSolutionOffer toModify, SdpPartyGroup partyGroup, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.getPartyGroups().add(partyGroup);
		partyGroup.getSolutionOffers().add(toModify);
		toModify.setUpdatedById(updatedBy);
		toModify.setUpdatedDate(new Date());
	}

	public void removePartyGroupLink(SdpSolutionOffer toModify, SdpPartyGroup partyGroup, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.getPartyGroups().remove(partyGroup);
		partyGroup.getSolutionOffers().remove(toModify);
		toModify.setUpdatedById(updatedBy);
		toModify.setUpdatedDate(new Date());
	}
}