package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpOfferDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpOfferHelper;
import com.accenture.sdp.csm.helpers.SdpPackageHelper;
import com.accenture.sdp.csm.helpers.SdpServiceVariantHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

/**
 * @author patrizio.pontecorvi
 * 
 */
public final class SdpOfferManager extends SdpBaseManager {

	private SdpOfferManager() {
		super();
	}

	private static SdpOfferManager instance;

	public static SdpOfferManager getInstance() {
		if (instance == null) {
			instance = new SdpOfferManager();
		}
		return instance;
	}

	public CreateServiceResponse createOffer(String offerName, String offerDescription, Long serviceVariantId, String externalId, String offerProfile,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(OFFER_NAME, offerName);
		logMap.put("offerDescription", offerDescription);
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("offerProfile", offerProfile);
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

			SdpOffer offer = createOffer(offerName, offerDescription, serviceVariantId, externalId, offerProfile, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(offer.getOfferId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
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

	SdpOffer createOffer(String offerName, String offerDescription, Long serviceVariantId, String externalId, String offerProfile, String cretedBy,
			EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		SdpServiceVariantHelper serviceVariantHelper = SdpServiceVariantHelper.getInstance();
		// Validazione Formale
		serviceVariantHelper.validateSearchServiceVariantById(serviceVariantId);
		offerHelper.validateOffer(offerName);
		SdpServiceVariant serviceVariant = serviceVariantHelper.searchServiceVariantById(serviceVariantId, em);
		if (serviceVariant == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}
		// verifica duplicati
		if (offerHelper.searchOfferByName(offerName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, OFFER_NAME, offerName);
		}
		if (offerHelper.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		return offerHelper.createOffer(offerName, offerDescription, serviceVariant, externalId, offerProfile, cretedBy, em);
	}

	public DataServiceResponse modifyOffer(Long offerId, String offerName, String offerDescription, Long serviceVariantId, String externalId,
			String offerProfile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(OFFER_ID, offerId);
		logMap.put(OFFER_NAME, offerName);
		logMap.put("offerDescription", offerDescription);
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("offerProfile", offerProfile);
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

			modifyOffer(offerId, offerName, offerDescription, serviceVariantId, externalId, offerProfile, Utilities.getCurrentClassAndMethod(), em);

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

	SdpOffer modifyOffer(Long offerId, String offerName, String offerDescription, Long serviceVariantId, String externalId, String offerProfile,
			String modifyBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		SdpServiceVariantHelper serviceVariantHelper = SdpServiceVariantHelper.getInstance();
		offerHelper.validateSearchOfferById(offerId);
		offerHelper.validateOffer(offerName);
		serviceVariantHelper.validateSearchServiceVariantById(serviceVariantId);
		SdpOffer offer = offerHelper.searchOfferById(offerId, em);
		if (offer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
		}
		SdpServiceVariant serviceVariant = serviceVariantHelper.searchServiceVariantById(serviceVariantId, em);
		if (serviceVariant == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}
		if (!isStatusActive(offer.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, OFFER_ID, offerId);
		}

		// verifica duplicati
		SdpOffer temp = offerHelper.searchOfferByName(offerName, em);
		if (temp != null && !temp.getOfferId().equals(offerId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, OFFER_NAME, offerName);
		}
		if (!Utilities.isNull(externalId)) {
			temp = offerHelper.searchByExternalId(externalId, em);
			if (temp != null && !temp.getOfferId().equals(offerId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		offerHelper.modifyOffer(offer, offerName, offerDescription, serviceVariant, externalId, offerProfile, modifyBy);
		return offer;
	}

	void deleteOffer(Long offerId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		// Validazione Formale
		offerHelper.validateSearchOfferById(offerId);
		SdpOffer offer = offerHelper.searchOfferById(offerId, em);
		if (offer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
		}

		offerHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, offer.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);
		offerHelper.deleteOffer(offer, deletedBy);
	}

	public DataServiceResponse deleteOfferAndPackages(Long offerId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(OFFER_ID, offerId);
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

			String classAndMethod = Utilities.getCurrentClassAndMethod();
			// at first delete the offer, to do also validations controls
			deleteOffer(offerId, classAndMethod, em);

			// then delete the packages
			SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
			List<SdpPackage> packages = packageHelper.searchPackageByOfferId(offerId, em);
			boolean warning = false;
			if (packages != null) {
				SdpPackageManager packManager = SdpPackageManager.getInstance();
				for (SdpPackage pack : packages) {
					warning |= packManager.deletePackage(pack.getPackageId(), classAndMethod, em);
				}
			}

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			if (warning) {
				resp = buildUpdateResponse(Constants.CODE_OK_RELATED_SUBSCRIPTIONS);
			} else {
				resp = buildUpdateResponse(Constants.CODE_OK);
			}
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

	public SearchServiceResponse searchOffer(Long offerId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(OFFER_ID, offerId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			offerHelper.validateSearchOfferById(offerId);
			em = PersistenceManager.getEntityManager(tenantName);
			// retrieve refPartyGroup
			SdpOffer offer = offerHelper.searchOfferById(offerId, em);
			if (offer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpOfferDto> searchResult = new ArrayList<SdpOfferDto>();
			searchResult.add(BeanConverter.converSdpOffer(offer));
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

	public SearchServiceResponse searchOffersByServiceVariant(String serviceVariantName, Long startPosition, Long maxRecordNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		SdpServiceVariantHelper serviceVariantHelper = SdpServiceVariantHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_NAME, serviceVariantName);
		logMap.put(START_POSITION, startPosition);
		logMap.put("maxRecordNumber", maxRecordNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			serviceVariantHelper.validateSearchServiceVariantByName(serviceVariantName);
			offerHelper.validateSearchAll(startPosition, maxRecordNumber);
			em = PersistenceManager.getEntityManager(tenantName);
			// retrieve serviceVariant
			SdpServiceVariant serviceVariant = serviceVariantHelper.searchServiceVariantByName(serviceVariantName, em);
			if (serviceVariant == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_NAME, serviceVariantName);
			}
			Long totalRes = offerHelper.countOfferByServiceVariantId(serviceVariant.getServiceVariantId(), em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SERVICE_VARIANT_NAME, serviceVariantName);
			}

			List<SdpOffer> offers = offerHelper.searchOfferByServiceVariantId(serviceVariant.getServiceVariantId(), startPosition, maxRecordNumber, em);

			if (offers == null || offers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.converSdpOffers(offers));
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

	public DataServiceResponse offerChangeStatus(Long offerId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(OFFER_ID, offerId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			offerChangeStatus(offerId, status, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
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

	SdpOffer offerChangeStatus(Long offerId, String status, String changedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		offerHelper.validateSearchOfferById(offerId);
		offerHelper.validateChangeStatus(status);
		SdpOffer offer = offerHelper.searchOfferById(offerId, em);
		if (offer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
		}
		offerHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, offer.getStatusId(), StatusIdConverter.getStatusValue(status), em);
		offerHelper.changeStatus(offer, StatusIdConverter.getStatusValue(status), changedBy);
		return offer;
	}

	public SearchServiceResponse searchAllOffers(Long startPosition, Long maxRecordNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put("maxRecordNumber", maxRecordNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			offerHelper.validateSearchAll(startPosition, maxRecordNumber);
			// Validazione Formale
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = offerHelper.searchAllOffersCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpOffer> offers = offerHelper.searchAllOffers(startPosition, maxRecordNumber, em);

			if (offers == null || offers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.converSdpOffersUpToPlatform(offers));
			resp.setTotalResult(totalRes);

			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildSearchResponse(e.getDescription(), e.getParameters());
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

	public DataServiceResponse offerAndPackagesChangeStatus(Long offerId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(OFFER_ID, offerId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			String classAndMethod = Utilities.getCurrentClassAndMethod();
			// at first change the offer, to do also validations controls
			offerChangeStatus(offerId, status, Utilities.getCurrentClassAndMethod(), em);

			// then change the packages
			SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
			List<SdpPackage> packages = packageHelper.searchPackageByOfferId(offerId, em);
			if (packages != null) {
				SdpPackageManager packManager = SdpPackageManager.getInstance();
				Long statusId = StatusIdConverter.getStatusValue(status);
				for (SdpPackage pack : packages) {
					if (!isStatusDeleted(pack.getStatusId()) && !pack.getStatusId().equals(statusId)) {
						packManager.packageChangeStatus(pack.getPackageId(), status, classAndMethod, em);
					}
				}
			}

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
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

	public DataServiceResponse isOfferSubscribed(Long offerId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(OFFER_ID, offerId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
			SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();

			offerHelper.validateSearchOfferById(offerId);
			SdpOffer offer = offerHelper.searchOfferById(offerId, em);
			if (offer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
			}

			boolean found = false;
			List<SdpPackage> pacakgeList = packageHelper.searchPackageByOfferId(offerId, em);
			if (pacakgeList != null) {
				for (SdpPackage pack : pacakgeList) {
					Long result = SdpSubscriptionHelper.getInstance().countSubscriptionDetailsNotDeletedByPackageId(pack.getPackageId(), em);
					if (result != null && result.longValue() > 0L) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, OFFER_ID, offerId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
