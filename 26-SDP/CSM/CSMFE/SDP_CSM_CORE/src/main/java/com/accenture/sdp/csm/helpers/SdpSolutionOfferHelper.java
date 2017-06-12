package com.accenture.sdp.csm.helpers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefSolutionOfferType;
import com.accenture.sdp.csm.model.jpa.SdpDiscount;
import com.accenture.sdp.csm.model.jpa.RefExternalPlatform;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolution;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOfferExternalId;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOfferExternalIdPK;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

/**
 * @author patrizio.pontecorvi
 * 
 */
public final class SdpSolutionOfferHelper extends SdpBaseHelper {

	private static SdpSolutionOfferHelper instance;

	private SdpSolutionOfferHelper() {
		super();
	}

	public static SdpSolutionOfferHelper getInstance() {
		if (instance == null) {
			instance = new SdpSolutionOfferHelper();
		}
		return instance;
	}

	public void validateSolutionOffer(String solutionOfferName, Date startDate, Date endDate, String solutionOfferType, Long duration)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		validationMap.put(SOLUTION_OFFER_TYPE, solutionOfferType);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		ValidationUtils.validateDates(startDate, endDate);
		HashMap<String, Long> validationNumberFields = new HashMap<String, Long>();
		validationNumberFields.put("duration", duration);
		ValidationUtils.validatePositiveLongFields(validationNumberFields);
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchSolutionOfferByName(String solutionOfferName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpSolutionOffer searchSolutionOfferByName(String solutionOfferName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(solutionOfferName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("solutionOfferName", solutionOfferName);
		List<SdpSolutionOffer> results = NamedQueryHelper.executeNamedQuery(SdpSolutionOffer.class, "selectSolutionOfferByName", parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public SdpSolutionOffer createSolutionOffer(SdpSolution solution, SdpSolutionOffer parentSolutionOffer, String solutionOfferName,
			String solutionOfferDescription, Date startDate, Date endDate, String profile, List<SdpPartyGroup> partyGroups, String createBy,
			RefSolutionOfferType solutionOfferType, Long duration, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionOffer solutionOffer = new SdpSolutionOffer();
		solutionOffer.setSdpSolution(solution);
		solutionOffer.setSolutionOfferName(solutionOfferName);
		solutionOffer.setParentSolutionOffer(parentSolutionOffer);
		solutionOffer.setSolutionOfferDescription(solutionOfferDescription);
		solutionOffer.setStartDate(startDate);
		solutionOffer.setEndDate(endDate);
		solutionOffer.setSolutionOfferProfile(profile);
		solutionOffer.setCreatedById(createBy);
		solutionOffer.setCreatedDate(new Date());
		// aggiunto x avs
		solutionOffer.setPartyGroups(partyGroups);
		solutionOffer.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		solutionOffer.setSolutionOfferType(solutionOfferType);
		solutionOffer.setDuration(duration);
		em.persist(solutionOffer);
		return solutionOffer;
	}

	public SdpSolutionOfferExternalId createSolutionOfferExternalId(SdpSolutionOffer solutionOffer, String externalId, RefExternalPlatform externalPlatform,
			EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionOfferExternalId external = new SdpSolutionOfferExternalId();
		SdpSolutionOfferExternalIdPK id = new SdpSolutionOfferExternalIdPK();
		id.setSolutionOfferId(solutionOffer.getSolutionOfferId());
		id.setExternalPlatformName(externalPlatform.getExternalPlatformName());
		external.setId(id);
		external.setExternalPlatform(externalPlatform);
		external.setSolutionOffer(solutionOffer);
		external.setExternalId(externalId);
		solutionOffer.getExternalIds().add(external);
		em.persist(external);
		return external;
	}

	public void deleteSolutionOfferExternalId(SdpSolutionOfferExternalId externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// cancellazione fisica
		em.remove(externalId);
		// modello
		externalId.getSolutionOffer().getExternalIds().remove(externalId);
	}

	public void deleteSolutionOfferExternalIds(SdpSolutionOffer solutionOffer, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// cancellazione fisica
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("solutionOfferId", solutionOffer.getSolutionOfferId());
		NamedQueryHelper.executeNamedQueryUpdate("deleteSolutionOfferExternalIdBySolutionOfferId", parametersMap, em);
		em.flush();
		// modello
		solutionOffer.getExternalIds().clear();
	}

	public void validateSearchSolutionOfferById(Long solutionOfferId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchSolutionOfferByParentSolutionOfferId(Long parentSolutionOfferId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("parentSolutionOfferId", parentSolutionOfferId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpSolutionOffer searchSolutionOfferById(Long solutionOfferId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		return em.find(SdpSolutionOffer.class, solutionOfferId);
	}

	public RefSolutionOfferType searchSolutionOfferTypeByType(String solutionOfferType, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(solutionOfferType)) {
			return null;
		}
		return em.find(RefSolutionOfferType.class, solutionOfferType);
	}

	public void modifySolutionOffer(SdpSolutionOffer solutionOffer, SdpSolution solution, String solutionOfferName, String solutionOfferDescription,
			Date startDate, Date endDate, String profile, String modifyBy, RefSolutionOfferType solutionOfferType, Long duration) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		solutionOffer.setSdpSolution(solution);
		solutionOffer.setSolutionOfferName(solutionOfferName);
		solutionOffer.setSolutionOfferDescription(solutionOfferDescription);
		solutionOffer.setStartDate(startDate);
		solutionOffer.setEndDate(endDate);
		solutionOffer.setSolutionOfferProfile(profile);
		solutionOffer.setUpdatedById(modifyBy);
		solutionOffer.setUpdatedDate(new Date());
		solutionOffer.setSolutionOfferType(solutionOfferType);
		solutionOffer.setDuration(duration);
	}

	public void deleteSolutionOffer(SdpSolutionOffer solutionOffer, String madeBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		solutionOffer.setDeletedDate(new Date());
		solutionOffer.setDeletedById(madeBy);
		solutionOffer.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
	}

	public void changeStatus(SdpSolutionOffer solutionOffer, Long statusId, String changedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		solutionOffer.setChangeStatusDate(new Date());
		solutionOffer.setChangeStatusById(changedBy);
		solutionOffer.setStatusId(statusId);
	}

	public List<SdpSolutionOffer> searchSolutionOffersBySolutionId(Long solutionId, Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod() + "paginated start : " + startPosition + " record :" + maxRecordsNumber);
		if (solutionId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("solutionId", solutionId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSolutionOffer.class, "selectSolutionOffersBySolutionId", parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countSolutionOffersBySolutionId(Long solutionId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("solutionId", solutionId);
		return NamedQueryHelper.executeNamedQueryCount("countSolutionOffersBySolutionId", parameHashMap, em);
	}

	public List<SdpSolutionOffer> searchAllSolutionOffers(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQuery(SdpSolutionOffer.class, "selectAllParentSolutionOffers", parameHashMap, em);
	}

	public List<SdpSolutionOffer> searchAllSolutionOffers(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " paginated start : " + startPosition + " record :" + maxRecordsNumber);
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSolutionOffer.class, "selectAllParentSolutionOffers", parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public List<SdpSolutionOffer> searchAllDiscountSolutionOffers(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " paginated start : " + startPosition + " record :" + maxRecordsNumber);
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSolutionOffer.class, "selectAllDiscountSolutionOffers", parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long searchAllSolutionOffersCount(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQueryCount("countAllParentSolutionOffers", null, em);
	}

	public Long searchAllDiscountSolutionOffersCount(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQueryCount("countAllDiscountSolutionOffers", null, em);
	}

	public Long countSolutionOffersByParent(Long parentSolutionOfferId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentSolutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSolutionOffer.PARAM_PARENT_SOLUTION_OFFER_ID, parentSolutionOfferId);
		return NamedQueryHelper.executeNamedQueryCount("countSolutionOffersByParent", parameHashMap, em);
	}

	public List<SdpSolutionOffer> searchSolutionOffersByParent(Long parentSolutionOfferId, Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentSolutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSolutionOffer.PARAM_PARENT_SOLUTION_OFFER_ID, parentSolutionOfferId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSolutionOffer.class, "searchSolutionOffersByParent", parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public List<SdpSolutionOffer> searchSolutionOffersNotDeletedBySolutionId(Long solutionId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("solutionId", solutionId);
		parameHashMap.put(SdpSolutionOffer.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpSolutionOffer.class, "selectSolutionOfferBySolutionIdAndStatusIdNotEqual", parameHashMap, em);
	}

	public void validateSearchByExternalId(String externalId, String externalPlatformName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(EXTERNAL_ID, externalId);
		validationMap.put("externalPlatformName", externalPlatformName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public RefExternalPlatform searchExternalPlatformByName(String externalPlatformName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(externalPlatformName)) {
			return null;
		}
		return em.find(RefExternalPlatform.class, externalPlatformName);
	}

	public SdpSolutionOffer searchByExternalId(String externalId, String externalPlatformName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(externalId) || Utilities.isNull(externalPlatformName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("externalId", externalId);
		parameHashMap.put("externalPlatformName", externalPlatformName);
		return NamedQueryHelper.executeNamedQuerySingle(SdpSolutionOffer.class, "selectSolutionOfferByExternalId", parameHashMap, em);
	}

	public SdpSolutionOfferExternalId searchExternalId(SdpSolutionOffer solutionOffer, RefExternalPlatform externalPlatform) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOffer != null && externalPlatform == null) {
			for (SdpSolutionOfferExternalId i : solutionOffer.getExternalIds()) {
				if (i.getExternalPlatform().equals(externalPlatform)) {
					return i;
				}
			}
		}
		return null;
	}

	public void validateSearchDiscountById(Long discountId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("discountId", discountId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateDiscount(BigDecimal discountAbsRc, BigDecimal discountAbsNrc, BigDecimal discountPercRc, BigDecimal discountPercNrc)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		// validate exclusive combinations
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("discountAbsRc", discountAbsRc);
		validationMap.put("discountPercRc", discountPercRc);
		ValidationResult res = ValidationUtils.validateExclusiveFields(validationMap);
		if (res.isValid()) {
			validationMap.clear();
			validationMap.put("discountAbsNrc", discountAbsNrc);
			validationMap.put("discountPercNrc", discountPercNrc);
			res = ValidationUtils.validateExclusiveFields(validationMap);
		}
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		// validate positive
		HashMap<String, BigDecimal> validationNumbers = new HashMap<String, BigDecimal>();
		validationNumbers.put("discountAbsRc", discountAbsRc);
		validationNumbers.put("discountAbsNrc", discountAbsNrc);
		validationNumbers.put("discountPercRc", discountPercRc);
		validationNumbers.put("discountPercNrc", discountPercNrc);
		res = ValidationUtils.validatePositiveDecimalFields(validationNumbers);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public boolean isDiscounted(SdpSolutionOffer solutionOffer) {
		return solutionOffer.getParentSolutionOffer() != null;
	}

	public SdpDiscount createDiscount(SdpPackage sdpPackage, SdpSolutionOffer solutionOffer, BigDecimal discountAbsRc, BigDecimal discountAbsNrc,
			BigDecimal discountPercRc, BigDecimal discountPercNrc, String createdBy, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDiscount discount = new SdpDiscount();
		discount.setSdpPackage(sdpPackage);
		discount.setSolutionOffer(solutionOffer);
		discount.setDiscountAbsNRC(discountAbsNrc);
		discount.setDiscountAbsRC(discountAbsRc);
		discount.setDiscountPercNRC(discountPercNrc);
		discount.setDiscountPercRC(discountPercRc);
		discount.setCreatedById(createdBy);
		discount.setCreatedDate(new Date());
		em.persist(discount);
		sdpPackage.getDiscounts().add(discount);
		solutionOffer.getDiscounts().add(discount);
		return discount;
	}

	public void modifyDiscount(SdpDiscount discount, BigDecimal discountAbsRc, BigDecimal discountAbsNrc, BigDecimal discountPercRc,
			BigDecimal discountPercNrc, String updatedBy, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		discount.setDiscountAbsNRC(discountAbsNrc);
		discount.setDiscountAbsRC(discountAbsRc);
		discount.setDiscountPercNRC(discountPercNrc);
		discount.setDiscountPercRC(discountPercRc);
		discount.setUpdatedById(updatedBy);
		discount.setUpdatedDate(new Date());
	}

	public SdpDiscount searchDiscountById(Long discountId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (discountId == null) {
			return null;
		}
		return em.find(SdpDiscount.class, discountId);
	}

	public Long countSolutionOffersNotDeletedByParent(Long parentSolutionOfferId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentSolutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSolutionOffer.PARAM_PARENT_SOLUTION_OFFER_ID, parentSolutionOfferId);
		parameHashMap.put(SdpSolutionOffer.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount("countSolutionOffersByParentAndStatusIdNotEqual", parameHashMap, em);
	}

	public List<RefSolutionOfferType> searchAllSolutionOfferTypes(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefSolutionOfferType.class, RefSolutionOfferType.SOLUTION_OFFER_TYPE_RETRIEVE_ALL, null, em);
	}
}
