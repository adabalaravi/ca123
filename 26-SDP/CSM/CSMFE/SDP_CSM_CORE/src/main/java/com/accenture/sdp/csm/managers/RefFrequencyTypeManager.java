package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.RefFrequencyTypeResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefFrequencyTypeHelper;
import com.accenture.sdp.csm.helpers.SdpPackagePriceHelper;
import com.accenture.sdp.csm.model.jpa.RefFrequencyType;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class RefFrequencyTypeManager extends SdpBaseManager {

	private RefFrequencyTypeManager() {
		super();
	}

	private static RefFrequencyTypeManager instance;

	public static RefFrequencyTypeManager getInstance() {
		if (instance == null) {
			instance = new RefFrequencyTypeManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database the prices into the Frequency catalog.
	 * </p>
	 * 
	 * @param frequencyTypeName
	 *            Name of the new Frequency to create
	 * @param frequencyTypeDescription
	 *            Descripion of the new Frequency to create
	 * @return Returns a CreateServiceResponse containing all the informations related to Frequency and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createFrequency(String frequencyTypeName, String frequencyTypeDescription, Long frequencyDays, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(FREQUENCY_TYPE_NAME, frequencyTypeName);
		logMap.put("frequencyTypeDescription", frequencyTypeDescription);
		logMap.put("frequencyDays", frequencyDays);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		EntityManager em = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			RefFrequencyType newRefFrequencyType = createFrequency(frequencyTypeName, frequencyTypeDescription, frequencyDays,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newRefFrequencyType.getFrequencyTypeId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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

	RefFrequencyType createFrequency(String frequencyTypeName, String frequencyTypeDescription, Long frequencyDays, String createdById, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		RefFrequencyTypeHelper helper = RefFrequencyTypeHelper.getInstance();
		// Validazione Formale
		helper.validateFrequencyType(frequencyTypeName, frequencyDays);

		if (helper.searchFrequencyTypeByName(frequencyTypeName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, FREQUENCY_TYPE_NAME, frequencyTypeName);
		}
		if (helper.searchFrequencyTypeByDays(frequencyDays, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, "frequencyDays", String.valueOf(frequencyDays));
		}
		return helper.createFrequency(frequencyTypeName, frequencyTypeDescription, frequencyDays, createdById, em);
	}

	/**
	 * <p>
	 * This method allows to phyisically delete a Frequency Type from the catalog.
	 * </p>
	 * 
	 * @param frequencyTypeId
	 *            Id of the party group to delete
	 * @return Returns a DataServiceResponse containing all the informations related to RefPartyGroup and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse deleteFrequency(Long frequencyTypeId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(FREQUENCY_TYPE_ID, frequencyTypeId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			deleteFrequency(frequencyTypeId, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(FREQUENCY_TYPE_ID, frequencyTypeId));
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
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

	void deleteFrequency(Long frequencyTypeId, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		RefFrequencyTypeHelper helper = RefFrequencyTypeHelper.getInstance();
		// Validazione Formale
		helper.validateSearchFrequencyTypeById(frequencyTypeId);
		// verifica sia cancellabile
		RefFrequencyType frequencyTypeToDelete = helper.searchFrequencyTypeById(frequencyTypeId, em);
		if (frequencyTypeToDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, FREQUENCY_TYPE_ID, frequencyTypeId);
		}
		SdpPackagePriceHelper packagePriceHelper = SdpPackagePriceHelper.getInstance();
		Long countpackagePrice = packagePriceHelper.searchPackagePriceByFrequencyIdCount(frequencyTypeId, em);
		if (countpackagePrice > 0) {
			log.logDebug("Frequency has not-deleted packagePrice associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, FREQUENCY_TYPE_ID, frequencyTypeId);
		}
		helper.deleteFrequency(frequencyTypeToDelete, em);
	}

	/**
	 * <p>
	 * This method allows to return the informations on all Frequencies in the catalog.
	 * </p>
	 * 
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to all Frequencies and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllFrequencies(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		try {
			RefFrequencyTypeHelper help = RefFrequencyTypeHelper.getInstance();
			help.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = help.searchAllFrequenciesCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<RefFrequencyType> frequencies = help.searchAllFrequencies(startPosition, maxRecordsNumber, em);
			if (frequencies == null || frequencies.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertListOfRefFrequencyTypeResponseDto(frequencies));
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

	public SearchServiceResponse searchFrequency(Long frequencyTypeId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		RefFrequencyTypeHelper frequencyTypeHelper = RefFrequencyTypeHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(FREQUENCY_TYPE_ID, frequencyTypeId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			frequencyTypeHelper.validateSearchFrequencyTypeById(frequencyTypeId);
			em = PersistenceManager.getEntityManager(tenantName);

			RefFrequencyType frequencyType = frequencyTypeHelper.searchFrequencyTypeById(frequencyTypeId, em);
			if (frequencyType == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, FREQUENCY_TYPE_ID, frequencyTypeId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<RefFrequencyTypeResponseDto> searchResult = new ArrayList<RefFrequencyTypeResponseDto>();
			searchResult.add(BeanConverter.convertRefFrequencTypeResponseDto(frequencyType));
			resp.setSearchResult(searchResult);
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

}
