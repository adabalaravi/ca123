package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpOfferGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpOfferGroupHelper extends SdpBaseHelper {

	private static SdpOfferGroupHelper instance;

	private SdpOfferGroupHelper() {
		super();
	}

	public static SdpOfferGroupHelper getInstance() {
		if (instance == null) {
			instance = new SdpOfferGroupHelper();
		}
		return instance;
	}

	public SdpOfferGroup createOfferGroup(String groupName, SdpSolutionOffer solutionOffer, String createdBy, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOfferGroup group = new SdpOfferGroup();
		group.setGroupName(groupName);
		group.setSdpSolutionOffer(solutionOffer);
		group.setCreatedById(createdBy);
		group.setCreatedDate(new Date());
		em.persist(group);
		return group;
	}

	public void modifyOfferGroup(SdpOfferGroup group, String groupName, SdpSolutionOffer solutionOffer, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		group.setGroupName(groupName);
		group.setSdpSolutionOffer(solutionOffer);
		group.setUpdatedById(updatedBy);
		group.setUpdatedDate(new Date());
	}

	public void deleteOfferGroup(SdpOfferGroup packageGroup, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(packageGroup);
	}

	public SdpOfferGroup searchOfferGroup(String groupName, Long solutionOfferId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(groupName) || solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("groupName", groupName);
		parametersMap.put("solutionOfferId", solutionOfferId);
		List<SdpOfferGroup> results = NamedQueryHelper.executeNamedQuery(SdpOfferGroup.class, "selectPackageGroup", parametersMap, em);
		if (results != null) {
			return results.get(0);

		}
		return null;
	}

	public void validateOfferGroup(String groupName, Long solutionOfferId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("solutionOfferId", solutionOfferId);
		validationMap.put("groupName", groupName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchOfferGroupById(Long groupId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("groupId", groupId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpOfferGroup searchOfferGroupById(Long groupId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (groupId == null) {
			return null;
		}
		return em.find(SdpOfferGroup.class, groupId);
	}

}
