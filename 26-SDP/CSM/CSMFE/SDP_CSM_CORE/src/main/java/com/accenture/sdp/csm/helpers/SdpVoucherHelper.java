package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpVoucher;
import com.accenture.sdp.csm.model.jpa.SdpVoucherCampaign;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpVoucherHelper extends SdpBaseHelper {
	private static SdpVoucherHelper instance;

	private SdpVoucherHelper() {
		super();
	}

	public static SdpVoucherHelper getInstance() {
		if (instance == null) {
			instance = new SdpVoucherHelper();
		}
		return instance;
	}

	public List<SdpVoucherCampaign> searchAllAvailableVoucherTypes(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQuery(SdpVoucherCampaign.class, SdpVoucherCampaign.QUERY_VOUCHERTYPE_RETRIEVE_BY_AVAILABLE, parameHashMap, em);
	}

	public Long countVoucherBySolutionOfferId(Long solutionOfferId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		return NamedQueryHelper.executeNamedQueryCount(SdpVoucher.QUERY_COUNT_BY_SOLUTIONOFFERID, parameHashMap, em);
	}

	public Long countAvailableVouchersBySolutionOfferId(Long solutionOfferId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		return NamedQueryHelper.executeNamedQueryCount(SdpVoucher.QUERY_COUNT_BY_AVAILABLE_AND_SOLUTIONOFFERID, parameHashMap, em);
	}

	public List<SdpVoucher> searchVouchersBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpVoucher.class, SdpVoucher.QUERY_RETRIEVE_BY_SOLUTIONOFFERID, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public List<SdpVoucher> searchAvailableVouchersBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpVoucher.class, SdpVoucher.QUERY_RETRIEVE_BY_AVAILABLE_AND_SOLUTIONOFFERID, parameHashMap,
				startPosition, maxRecordsNumber, em);
	}

	public SdpVoucher searchVoucherByCode(String voucherCode, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(voucherCode)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_VOUCHER_CODE, voucherCode);
		List<SdpVoucher> results = NamedQueryHelper.executeNamedQuery(SdpVoucher.class, SdpVoucher.QUERY_RETRIEVE_BY_VOUCHERCODE, parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public Long countVouchersByCodeLike(String voucherCode, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(voucherCode)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_VOUCHER_CODE, voucherCode + "%");
		return NamedQueryHelper.executeNamedQueryCount(SdpVoucher.QUERY_COUNT_BY_VOUCHERCODE_LIKE, parameHashMap, em);
	}

	public List<SdpVoucher> searchVouchersByCodeLike(String voucherCode, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(voucherCode)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpVoucher.PARAM_VOUCHER_CODE, voucherCode + "%");
		return NamedQueryHelper.executePaginatedNamedQuery(SdpVoucher.class, SdpVoucher.QUERY_RETRIEVE_BY_VOUCHERCODE_LIKE, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public void modifyVoucher(SdpVoucher toModify, SdpParty sdpParty, String modifyBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.setSdpParty(sdpParty);
		toModify.setUpdatedById(modifyBy);
		toModify.setUpdatedDate(new Date());
	}
	
	public void modifyVoucherCampaign(SdpVoucherCampaign campaign, SdpSolutionOffer sdpSolutionOffer, Long validityPeriod, Date startDate, Date endDate, 
			String upedateBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		campaign.setUpdatedById(upedateBy);
		campaign.setUpdatedDate(new Date());
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		campaign.setValidityPeriod(validityPeriod);
		campaign.setSdpSolutionOffer(sdpSolutionOffer);
	}

	public SdpVoucherCampaign searchVoucherCampaignByType(String voucherType, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(voucherType)) {
			return null;
		}
		return em.find(SdpVoucherCampaign.class, voucherType);
	}



	public SdpVoucher searchVoucherById(Long voucherId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (voucherId == null) {
			return null;
		}
		return em.find(SdpVoucher.class, voucherId);
	}

	public void validateSearchVoucherByCode(String voucherCode) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("voucherCode", voucherCode);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateModifyVoucher(Long voucherId, Long partyId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(VOUCHER_ID, voucherId);
		validationMap.put(PARTY_ID, partyId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);

	}

	public void validateCreateCampaign(Long solutionOfferId, String voucherType, Long validityPeriod,Date startDate, Date endDate) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		validationMap.put("voucherType", voucherType);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}

		// necessario validityPeriod e/o startDate
		validationMap.clear();
		validationMap.put("validityPeriod", validityPeriod);	
		validationMap.put("startDate", startDate);
		res = ValidationUtils.validateSoftMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		if(validityPeriod!= null && validityPeriod.longValue() <= 0L) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "validityPeriod", String.valueOf(validityPeriod));
		}
		// necessario  startDate deve essere popolato
		ValidationUtils.validateDates(startDate, endDate);
		log.logDebug(VALIDATION_END);
	}
}
