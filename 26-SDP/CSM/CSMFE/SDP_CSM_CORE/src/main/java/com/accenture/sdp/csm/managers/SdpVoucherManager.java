package com.accenture.sdp.csm.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.helpers.SdpVoucherHelper;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpVoucher;
import com.accenture.sdp.csm.model.jpa.SdpVoucherCampaign;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpVoucherManager extends SdpBaseManager {

	private SdpVoucherManager() {
		super();
	}

	private static SdpVoucherManager instance;

	public static SdpVoucherManager getInstance() {
		if (instance == null) {
			instance = new SdpVoucherManager();
		}
		return instance;
	}

	public SearchServiceResponse searchAvailableVoucherTypes(String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			List<SdpVoucherCampaign> types = SdpVoucherHelper.getInstance().searchAllAvailableVoucherTypes(em);
			if (types == null || types.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertListOfVoucherCampaignToVoucherDto(types));

			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public SearchServiceResponse searchVouchersBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, maxRecordsNumber);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpVoucherHelper handler = SdpVoucherHelper.getInstance();
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			Long totalRes = handler.countVoucherBySolutionOfferId(solutionOfferId, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}

			List<SdpVoucher> vouchers = SdpVoucherHelper.getInstance().searchVouchersBySolutionOfferId(solutionOfferId, startPosition, maxRecordsNumber, em);

			if (vouchers == null || vouchers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertListOfVoucherToVoucherDto(vouchers));
			resp.setTotalResult(totalRes);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public SearchServiceResponse searchAvailableVouchersBySolutionOfferId(Long solutionOfferId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpVoucherHelper handler = SdpVoucherHelper.getInstance();
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			Long totalRes = handler.countAvailableVouchersBySolutionOfferId(solutionOfferId, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}

			List<SdpVoucher> vouchers = SdpVoucherHelper.getInstance().searchAvailableVouchersBySolutionOfferId(solutionOfferId, startPosition,
					maxRecordsNumber, em);

			if (vouchers == null || vouchers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertListOfVoucherToVoucherDto(vouchers));
			resp.setTotalResult(totalRes);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public ComplexServiceResponse searchVoucherByCode(String voucherCode, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(VOUCHER_CODE, voucherCode);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		ComplexServiceResponse resp;
		EntityManager em = null;
		try {
			SdpVoucherHelper handler = SdpVoucherHelper.getInstance();
			// formal validation
			handler.validateSearchVoucherByCode(voucherCode);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpVoucher voucher = handler.searchVoucherByCode(voucherCode, em);
			if (voucher == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, VOUCHER_CODE, voucherCode);
			}
			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertVoucherToVoucherDto(voucher));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildComplexResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildComplexResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public SearchServiceResponse searchVouchersByCodeLike(String voucherCode, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(VOUCHER_CODE, voucherCode);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpVoucherHelper handler = SdpVoucherHelper.getInstance();
			// formal validation
			handler.validateSearchVoucherByCode(voucherCode);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			Long totalRes = handler.countVouchersByCodeLike(voucherCode, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, VOUCHER_CODE, voucherCode);
			}

			List<SdpVoucher> vouchers = SdpVoucherHelper.getInstance().searchVouchersByCodeLike(voucherCode, startPosition, maxRecordsNumber, em);

			if (vouchers == null || vouchers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertListOfVoucherToVoucherDto(vouchers));
			resp.setTotalResult(totalRes);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public DataServiceResponse modifyVoucher(Long voucherId, Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(VOUCHER_ID, voucherId);
		logMap.put(PARTY_ID, partyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyVoucher(voucherId, partyId, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	SdpVoucher modifyVoucher(Long voucherId, Long partyId, String modifyBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpVoucherHelper handler = SdpVoucherHelper.getInstance();
		SdpPartyHelper partyHandler = SdpPartyHelper.getInstance();
		handler.validateModifyVoucher(voucherId, partyId);
		SdpVoucher voucher = handler.searchVoucherById(voucherId, em);
		if (voucher == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, VOUCHER_ID, String.valueOf(voucherId));
		}
		SdpParty party = partyHandler.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		Date now = new Date();
		SdpVoucherCampaign campaign = voucher.getSdpVoucherCampaign();
		if (campaign.getSdpSolutionOffer() == null
				|| (campaign.getSdpSolutionOffer().getEndDate() != null && campaign.getSdpSolutionOffer().getEndDate().before(now))
				|| (campaign.getEndDate() != null && campaign.getEndDate().before(now))) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, VOUCHER_ID, String.valueOf(voucherId));
		}
		// verifica usato
		if (voucher.getSdpParty() != null) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
		handler.modifyVoucher(voucher, party, modifyBy, em);
		return voucher;
	}

	public DataServiceResponse modifyVoucherCampaign(Long solutionOfferId, Long validityPeriod, String voucherType, Date startDate, Date endDate,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put("validityPeriod", validityPeriod);
		logMap.put("voucherType", voucherType);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			modifyVoucherCampaign(solutionOfferId, validityPeriod, voucherType, startDate, endDate, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;

	}

	void modifyVoucherCampaign(Long solutionOfferId, Long validityPeriod, String voucherType, Date startDate, Date endDate, String updateBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpVoucherHelper handler = SdpVoucherHelper.getInstance();
		handler.validateCreateCampaign(solutionOfferId, voucherType, validityPeriod, startDate, endDate);

		SdpVoucherCampaign campaign = handler.searchVoucherCampaignByType(voucherType, em);
		if (campaign == null || campaign.getSdpVouchers().isEmpty()) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "voucherType", voucherType);
		}
		if (campaign.getSdpSolutionOffer() != null) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, "voucherType", voucherType);
		}
		SdpSolutionOffer solutionOffer = SdpSolutionOfferHelper.getInstance().searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		handler.modifyVoucherCampaign(campaign, solutionOffer, validityPeriod, startDate, endDate, updateBy);
	}

}
