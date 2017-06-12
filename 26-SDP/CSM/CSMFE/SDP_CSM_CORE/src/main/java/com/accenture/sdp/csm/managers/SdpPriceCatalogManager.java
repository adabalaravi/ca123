package com.accenture.sdp.csm.managers;

import java.math.BigDecimal;
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
import com.accenture.sdp.csm.dto.responses.SdpPriceCatalogDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpPackagePriceHelper;
import com.accenture.sdp.csm.helpers.SdpPriceCatalogHelper;
import com.accenture.sdp.csm.model.jpa.SdpPriceCatalog;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpPriceCatalogManager extends SdpBaseManager {

	private SdpPriceCatalogManager() {
		super();
	}

	private static SdpPriceCatalogManager instance;

	public static SdpPriceCatalogManager getInstance() {
		if (instance == null) {
			instance = new SdpPriceCatalogManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database the prices into the price catalog.
	 * </p>
	 * 
	 * @param price
	 *            Name of the new party group to create
	 * @return Returns a CreateServiceResponse containing all the informations related to Price and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createPrice(BigDecimal price, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("price", price);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityTransaction tx = null;
		try {
			SdpPriceCatalogHelper helper = SdpPriceCatalogHelper.getInstance();
			helper.validatePriceCatalog(price);
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			if (helper.searchPriceCatalogByPrice(price, em) != null) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, "price", price.toPlainString());
			}
			if (price.compareTo(BigDecimal.ZERO) < 0) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "price", price.toPlainString());
			}
			// creazione price
			SdpPriceCatalog newSdpPriceCatalog = helper.createPriceCatalog(price, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newSdpPriceCatalog.getPriceCatalogId());
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

	/**
	 * <p>
	 * This method allows to phyisically delete a price from the catalog.
	 * </p>
	 * 
	 * @param priceCatalogId
	 *            Id of the Price to delete
	 * @return Returns a DataServiceResponse containing all the informations related to Price and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse deletePrice(Long priceCatalogId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(PRICE_CATALOGUE_ID, priceCatalogId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			// Validazione Formale
			SdpPriceCatalogHelper helper = SdpPriceCatalogHelper.getInstance();
			helper.validateSearchPriceCatalogById(priceCatalogId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// verifica site sia cancellabile
			SdpPriceCatalog priceCatalogToDelete = helper.searchPriceCatalogById(priceCatalogId, em);
			if (priceCatalogToDelete == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PRICE_CATALOGUE_ID, priceCatalogId);
			}

			Long countPackagePrice = SdpPackagePriceHelper.getInstance().searchPackagePriceByPriceCatalogIdCount(priceCatalogId, em);
			if (countPackagePrice > 0) {
				log.logDebug("PriceCatalog has not-deleted PackagePrice associated!");
				throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PRICE_CATALOGUE_ID, priceCatalogId);
			}

			helper.deletePriceCatalog(priceCatalogToDelete, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			resp = buildUpdateResponse(Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(PRICE_CATALOGUE_ID, priceCatalogId));
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

	/**
	 * <p>
	 * This method allows to return the informations on all prices in the catalog.
	 * </p>
	 * 
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to all prices and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllPrices(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpPriceCatalogHelper help = SdpPriceCatalogHelper.getInstance();
			help.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = help.searchAllPricesCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpPriceCatalog> searchResult = help.searchAllPrices(startPosition, maxRecordsNumber, em);

			if (searchResult == null || searchResult.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertListOfPriceCatalogDto(searchResult));
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

	public SearchServiceResponse searchPrice(Long priceCatalogId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("packagePriceId", priceCatalogId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpPriceCatalogHelper priceCatalogHelper = SdpPriceCatalogHelper.getInstance();
			priceCatalogHelper.validateSearchPriceCatalogById(priceCatalogId);

			em = PersistenceManager.getEntityManager(tenantName);
			// retrieve refPartyGroup
			SdpPriceCatalog priceCatalog = priceCatalogHelper.searchPriceCatalogById(priceCatalogId, em);
			if (priceCatalog == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PRICE_CATALOGUE_ID, priceCatalogId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpPriceCatalogDto> searchResult = new ArrayList<SdpPriceCatalogDto>();
			searchResult.add(BeanConverter.convertSdpPriceCatalogResponseDto(priceCatalog));
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
