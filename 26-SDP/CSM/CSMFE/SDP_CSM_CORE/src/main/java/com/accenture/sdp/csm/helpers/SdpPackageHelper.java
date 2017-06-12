package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.IsMandatory.Mandatory;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpOfferGroup;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpPackagePrice;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpPackageHelper extends SdpBaseHelper {

	private static SdpPackageHelper instance;

	private SdpPackageHelper() {
		super();
	}

	public static SdpPackageHelper getInstance() {
		if (instance == null) {
			instance = new SdpPackageHelper();
		}
		return instance;
	}

	public SdpPackage createPackage(SdpSolutionOffer solutionOffer, String externalId, SdpOffer offer, String isMandatory, SdpPackagePrice packagePrice,
			SdpPackage basePackage, SdpOfferGroup sdpOfferGroup, String profile, String createdBy, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackage newPackage = new SdpPackage();
		newPackage.setSdpSolutionOffer(solutionOffer);
		newPackage.setExternalId(externalId);
		newPackage.setSdpOffer(offer);
		newPackage.setIsMandatory(Mandatory.getMandatoryValue(isMandatory));
		newPackage.setSdpPackagePrice(packagePrice);
		if (basePackage != null) {
			newPackage.setBasePackage(basePackage);
		}
		if (sdpOfferGroup != null) {
			newPackage.setSdpOfferGroup(sdpOfferGroup);
		}
		newPackage.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newPackage.setPackageProfile(profile);
		newPackage.setCreatedById(createdBy);
		newPackage.setCreatedDate(new Date());
		em.persist(newPackage);
		return newPackage;
	}

	public void modifyPackage(SdpPackage sdpPackageToModify, SdpSolutionOffer solutionOffer, String externalId, SdpOffer offer, String isMandatory,
			SdpPackagePrice packagePrice, SdpPackage basePackage, SdpOfferGroup sdpOfferGroup, String profile, String modifiedBy)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpPackageToModify.setSdpSolutionOffer(solutionOffer);
		sdpPackageToModify.setExternalId(externalId);
		sdpPackageToModify.setSdpOffer(offer);
		sdpPackageToModify.setIsMandatory(Mandatory.getMandatoryValue(isMandatory));
		sdpPackageToModify.setSdpPackagePrice(packagePrice);
		sdpPackageToModify.setBasePackage(basePackage);
		sdpPackageToModify.setSdpOfferGroup(sdpOfferGroup);
		sdpPackageToModify.setPackageProfile(profile);
		sdpPackageToModify.setUpdatedById(modifiedBy);
		sdpPackageToModify.setUpdatedDate(new Date());
	}

	public void deletePackage(SdpPackage packageToDelete, String deletedBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		packageToDelete.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		packageToDelete.setDeletedById(deletedBy);
		packageToDelete.setDeletedDate(new Date());
	}

	public SdpPackage searchPackageById(Long packageId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (packageId == null) {
			return null;
		}
		return em.find(SdpPackage.class, packageId);
	}

	public SdpPackage searchPackageBySolutionOfferIdAndOfferId(Long solutionOfferId, Long offerId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null || offerId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpPackage.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		parametersMap.put(SdpPackage.PARAM_OFFER_ID, offerId);
		List<SdpPackage> results = NamedQueryHelper.executeNamedQuery(SdpPackage.class, SdpPackage.QUERY_RETRIEVE_BY_SOLUTIONOFFERID_AND_OFFERID,
				parametersMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public List<SdpPackage> searchPackageBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpPackage.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpPackage.class, SdpPackage.QUERY_RETRIEVE_BY_SOLUTIONOFFERID, parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countPackageBySolutionOfferId(Long solutionOfferId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpPackage.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		return NamedQueryHelper.executeNamedQueryCount(SdpPackage.QUERY_COUNT_BY_SOLUTIONOFFERID, parametersMap, em);
	}

	public void validatePackage(Long solutionOfferId, Long offerId, Long packagePriceId, String isMandatory) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		validationMap.put(OFFER_ID, offerId);
		validationMap.put(PACKAGE_PRICE_ID, packagePriceId);
		validationMap.put("isMandatory", isMandatory);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}

		log.logDebug("Validating isMandatory");
		res = ValidationUtils.validateIsMandatory(isMandatory);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchPackageById(Long packageId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("packageId", packageId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void changeStatus(SdpPackage packageToUpdate, Long nextstatus, String changedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		packageToUpdate.setStatusId(nextstatus);
		packageToUpdate.setChangeStatusById(changedBy);
		packageToUpdate.setChangeStatusDate(new Date());
	}

	public Long countPackageByPackagePriceId(Long packagePriceId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		paramHashMap.put(SdpPackage.PARAM_PACKAGE_PRICE_ID, packagePriceId);
		return NamedQueryHelper.executeNamedQueryCount(SdpPackage.QUERY_COUNT_BY_PACKAGEPRICEID, paramHashMap, em);
	}

	public SdpPackage searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpPackage.class, SdpPackage.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}

	public List<SdpPackage> searchPackageByOfferId(Long offerId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (offerId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpPackage.PARAM_OFFER_ID, offerId);
		return NamedQueryHelper.executeNamedQuery(SdpPackage.class, SdpPackage.QUERY_RETRIEVE_BY_OFFERID, parametersMap, em);
	}

	public Long countPackagesNotDeletedBySolutionOfferId(Long solutionOfferId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpPackage.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		parametersMap.put(SdpPackage.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpPackage.QUERY_COUNT_BY_SOLUTIONOFFERID_AND_NOT_STATUS, parametersMap, em);
	}

	public Long countPackagesNotDeletedByOfferId(Long offerId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (offerId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpPackage.PARAM_OFFER_ID, offerId);
		parametersMap.put(SdpPackage.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpPackage.QUERY_COUNT_BY_OFFERID_AND_NOT_STATUS, parametersMap, em);
	}
}
