package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpOfferHelper extends SdpBaseHelper {

	private static SdpOfferHelper instance;

	private SdpOfferHelper() {
		super();
	}

	public static SdpOfferHelper getInstance() {
		if (instance == null) {
			instance = new SdpOfferHelper();
		}
		return instance;
	}

	public SdpOffer createOffer(String offerName, String offerDescription, SdpServiceVariant serviceVariant, String externalId, String offerProfile,
			String createBy, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOffer offer = new SdpOffer();
		offer.setCreatedById(createBy);
		offer.setCreatedDate(new Date());
		offer.setExternalId(externalId);
		offer.setOfferDescription(offerDescription);
		offer.setOfferName(offerName);
		offer.setOfferProfile(offerProfile);
		offer.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		offer.setSdpServiceVariant(serviceVariant);
		em.persist(offer);
		return offer;
	}

	public void modifyOffer(SdpOffer offer, String offerName, String offerDescription, SdpServiceVariant serviceVariant, String externalId,
			String offerProfile, String modifyBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		offer.setUpdatedById(modifyBy);
		offer.setUpdatedDate(new Date());
		offer.setExternalId(externalId);
		offer.setOfferDescription(offerDescription);
		offer.setOfferName(offerName);
		offer.setOfferProfile(offerProfile);
		offer.setSdpServiceVariant(serviceVariant);
	}

	public void deleteOffer(SdpOffer offer, String deleteBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		offer.setDeletedDate(new Date());
		offer.setDeletedById(deleteBy);
		offer.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
	}

	public SdpOffer searchOfferByName(String offerName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(offerName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOffer.PARAM_OFFER_NAME, offerName);
		List<SdpOffer> result = NamedQueryHelper.executeNamedQuery(SdpOffer.class, SdpOffer.QUERY_RETRIVE_BY_NAME, parameHashMap, em);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	public void validateSearchOfferByName(String offerName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(OFFER_NAME, offerName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public Long countOffersNotDeletedBySolutionOfferId(Long solutionOfferId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOffer.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		parameHashMap.put(SdpOffer.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpOffer.QUERY_COUNT_BY_SOLUTIONOFFERID_AND_NOT_STATUS, parameHashMap, em);
	}

	public void validateOffer(String offerName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(OFFER_NAME, offerName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchOfferById(Long offerId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(OFFER_ID, offerId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);

	}

	public SdpOffer searchOfferById(Long offerId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (offerId == null) {
			return null;
		}
		return em.find(SdpOffer.class, offerId);
	}

	public List<SdpOffer> searchOfferByServiceVariantId(Long serviceVariantId, Long startPosition, Long maxRecordNumber, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOffer.PARAM_SERVICE_VARIANT_ID, serviceVariantId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpOffer.class, SdpOffer.QUERY_RETRIEVE_BY_SERVICEVARIANTID, parameHashMap, startPosition,
				maxRecordNumber, em);
	}

	public Long countOfferByServiceVariantId(Long serviceVariantId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOffer.PARAM_SERVICE_VARIANT_ID, serviceVariantId);
		return NamedQueryHelper.executeNamedQueryCount(SdpOffer.QUERY_COUNT_BY_SERVICEVARIANTID, parameHashMap, em);
	}

	public void changeStatus(SdpOffer offer, Long status, String madeBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		offer.setStatusId(status);
		offer.setChangeStatusById(madeBy);
		offer.setChangeStatusDate(new Date());
	}

	public List<SdpOffer> searchAllOffers(Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " paginated start : " + startPosition + " record :" + maxRecordsNumber);
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpOffer.class, SdpOffer.QUERY_RETRIEVE_ALL, parameHashMap, startPosition, maxRecordsNumber, em);
	}

	public Long searchAllOffersCount(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount(SdpOffer.QUERY_COUNT_ALL, parameHashMap, em);
	}

	public SdpOffer searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpOffer.class, SdpOffer.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}
}
