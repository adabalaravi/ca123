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
import com.accenture.sdp.csm.dto.responses.RefCurrencyTypeResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefCurrencyTypeHelper;
import com.accenture.sdp.csm.helpers.SdpPackagePriceHelper;
import com.accenture.sdp.csm.model.jpa.RefCurrencyType;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class RefCurrencyTypeManager extends SdpBaseManager {

	private RefCurrencyTypeManager() {
		super();
	}

	private static RefCurrencyTypeManager instance;

	public static RefCurrencyTypeManager getInstance() {
		if (instance == null) {
			instance = new RefCurrencyTypeManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to return the informations on all currencies in the catalog.
	 * </p>
	 * 
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to all prices and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllCurrencies(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
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
			RefCurrencyTypeHelper help = RefCurrencyTypeHelper.getInstance();
			help.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = help.countAllCurrencies(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<RefCurrencyType> currencies = help.searchAllCurrencies(startPosition, maxRecordsNumber, em);

			if (currencies == null || currencies.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertListOfRefCurrencyTypeResponseDto(currencies));
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

	/**
	 * <p>
	 * This method allows to insert into CSM database the prices into the Frequency catalog.
	 * </p>
	 * 
	 * @param currencyTypeName
	 *            Name of the new Currency to create
	 * @return Returns a CreateServiceResponse containing all the informations related to Currency and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createCurrency(String currencyTypeName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(CURRENCY_TYPE_NAME, currencyTypeName);
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
			RefCurrencyType newRefCurrencyType = createCurrency(currencyTypeName, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newRefCurrencyType.getCurrencyTypeId());
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

	RefCurrencyType createCurrency(String currencyTypeName, String createdById, EntityManager em) throws PropertyNotFoundException, ValidationException {
		RefCurrencyTypeHelper helper = RefCurrencyTypeHelper.getInstance();
		// Validazione Formale
		helper.validateCurrencyType(currencyTypeName);

		if (helper.searchCurrencyTypeByName(currencyTypeName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, CURRENCY_TYPE_NAME, currencyTypeName);
		}

		return helper.createCurrency(currencyTypeName, createdById, em);
	}

	/**
	 * <p>
	 * This method allows to phyisically delete a Frequency Type from the catalog.
	 * </p>
	 * 
	 * @param currencyTypeId
	 *            Id of the party group to delete
	 * @return Returns a DataServiceResponse containing all the informations related to RefPartyGroup and operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteCurrency(Long currencyTypeId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(CURRENCY_TYPE_ID, currencyTypeId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			deleteCurrency(currencyTypeId, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(CURRENCY_TYPE_ID, currencyTypeId));
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

	void deleteCurrency(Long currencyTypeId, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		RefCurrencyTypeHelper helper = RefCurrencyTypeHelper.getInstance();
		// Validazione Formale
		helper.validateSearchCurrencyTypeById(currencyTypeId);
		// verifica sia cancellabile
		RefCurrencyType currencyTypeToDelete = helper.searchCurrencyById(currencyTypeId, em);
		if (currencyTypeToDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, CURRENCY_TYPE_ID, currencyTypeId);
		}
		SdpPackagePriceHelper packagePriceHelper = SdpPackagePriceHelper.getInstance();
		Long countpackagePrice = packagePriceHelper.searchPackagePriceByCurrencyTypeIdCount(currencyTypeId, em);
		if (countpackagePrice > 0) {
			log.logDebug("Frequency has not-deleted packagePrice associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, CURRENCY_TYPE_ID, currencyTypeId);
		}
		helper.deleteCurrency(currencyTypeToDelete, em);
	}

	public SearchServiceResponse searchCurrency(Long currencyTypeId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		RefCurrencyTypeHelper currencyTypeHelper = RefCurrencyTypeHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(CURRENCY_TYPE_ID, currencyTypeId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			currencyTypeHelper.validateSearchCurrencyTypeById(currencyTypeId);
			em = PersistenceManager.getEntityManager(tenantName);

			RefCurrencyType currencyType = currencyTypeHelper.searchCurrencyById(currencyTypeId, em);
			if (currencyType == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, CURRENCY_TYPE_ID, currencyTypeId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<RefCurrencyTypeResponseDto> searchResult = new ArrayList<RefCurrencyTypeResponseDto>();
			searchResult.add(BeanConverter.convertRefCurrencyTypeResponseDto(currencyType));
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
