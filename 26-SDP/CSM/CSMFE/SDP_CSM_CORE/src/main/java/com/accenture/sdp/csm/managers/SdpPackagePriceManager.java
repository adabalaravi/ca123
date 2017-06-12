package com.accenture.sdp.csm.managers;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.FlagValue.Flag;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpPackagePriceDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefCurrencyTypeHelper;
import com.accenture.sdp.csm.helpers.RefFrequencyTypeHelper;
import com.accenture.sdp.csm.helpers.SdpPackageHelper;
import com.accenture.sdp.csm.helpers.SdpPackagePriceHelper;
import com.accenture.sdp.csm.helpers.SdpPriceCatalogHelper;
import com.accenture.sdp.csm.model.jpa.RefCurrencyType;
import com.accenture.sdp.csm.model.jpa.RefFrequencyType;
import com.accenture.sdp.csm.model.jpa.SdpPackagePrice;
import com.accenture.sdp.csm.model.jpa.SdpPriceCatalog;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpPackagePriceManager extends SdpBaseManager {

	private SdpPackagePriceManager() {
		super();
	}

	private static SdpPackagePriceManager instance;

	public static SdpPackagePriceManager getInstance() {
		if (instance == null) {
			instance = new SdpPackagePriceManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database all informations concerning the price category that can be associated to an Offer. Before creating the
	 * PackagePrice will need to verify that the parameters, that are bound to RC_Price_Catalog_ID field, are populated with the correct values in relation to
	 * the value taken from the RC_Price_Catalog_ID field. For example, if the RC_Price_Catalog_ID field is populated with an id related to a nonzero price
	 * amount, the fields:
	 * 
	 * RC_Frequency_Type_ID RC_Flag_Prorate RC_In_Advance
	 * 
	 * could be populated with one of the allowed values. However, if the RC_Price_Catalog_ID field is populated with the id related to the price amount = 0
	 * (zero), these fields must be populated only in the following way:
	 * 
	 * RC_Frequency_Type_ID -> id related to the value 'No Frequency' RC_Flag_Prorate -> N RC_In_Advance -> N
	 * </p>
	 * 
	 * @param rcPriceCatalogId
	 *            Id of the RC Price
	 * @param nrcPriceCatalogId
	 *            Id of the NRC
	 * @param currencyTypeId
	 *            Id of the
	 * @param rcFrequencyTypeId
	 *            Id of the
	 * @param rcFlagProrate
	 *            Prorate flag
	 * @param rcInAdvance
	 *            Prepayment flag
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpPackagePrice and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createPackagePrice(Long rcPriceCatalogId, Long nrcPriceCatalogId, Long currencyTypeId, Long rcFrequencyTypeId,
			String rcFlagProrate, String rcInAdvance, String tenantName) throws PropertyNotFoundException {

		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("rcPriceCatalogId", rcPriceCatalogId);
		logMap.put("nrcPriceCatalogId", nrcPriceCatalogId);
		logMap.put("CurrencyTypeId", currencyTypeId);
		logMap.put(RC_FREQUENCY_TYPE_ID, rcFrequencyTypeId);
		logMap.put("rcFlagProrate", rcFlagProrate);
		logMap.put("rcInAdvance", rcInAdvance);
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

			SdpPackagePrice newPackagePrice = createPackagePrice(rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId, rcFlagProrate,
					rcInAdvance, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newPackagePrice.getPackagePriceId());
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

	SdpPackagePrice createPackagePrice(Long rcPriceCatalogId, Long nrcPriceCatalogId, Long currencyTypeId, Long rcFrequencyTypeId, String rcFlagProrate,
			String rcInAdvance, String cretedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackagePriceHelper helper = SdpPackagePriceHelper.getInstance();
		helper.validatePackagePrice(rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId, rcFlagProrate, rcInAdvance);
		SdpPackagePrice packagePricePivot = setupPackagePrice(rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId, rcFlagProrate,
				rcInAdvance, em);
		return helper.createPackagePrice(packagePricePivot.getRefPriceCatalogRC(), packagePricePivot.getRefPriceCatalogNRC(),
				packagePricePivot.getRefCurrencyType(), packagePricePivot.getRefFrequencyType(), packagePricePivot.getRcFlagProrate(),
				packagePricePivot.getRcInAdvance(), cretedBy, em);
	}

	/**
	 * <p>
	 * This method allows to phisically delete a price category.
	 * </p>
	 * 
	 * @param packagePriceId
	 *            Id of the package price to delete
	 * @return Returns a DataServiceResponse containing all the informations related to RefPartyGroup and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse deletePackagePrice(Long packagePriceId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_PRICE_ID, packagePriceId);
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

			deletePackagePrice(packagePriceId, em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(PACKAGE_PRICE_ID, packagePriceId));
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

	void deletePackagePrice(Long packagePriceId, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackagePriceHelper helper = SdpPackagePriceHelper.getInstance();
		helper.validateSearchPackagePriceById(packagePriceId);
		// verifica site sia cancellabile
		SdpPackagePrice packagePriceToDelete = helper.searchPackagePriceById(packagePriceId, em);
		if (packagePriceToDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_PRICE_ID, packagePriceId);
		}
		SdpPackageHelper packageHelp = SdpPackageHelper.getInstance();
		if (packageHelp.countPackageByPackagePriceId(packagePriceId, em) != 0L) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PACKAGE_PRICE_ID, packagePriceId);
		}
		// Cancellazione
		helper.deletePackagePrice(packagePriceToDelete, em);
	}

	public SearchServiceResponse searchPackagePrice(Long packagePriceId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpPackagePriceHelper packagePriceHelper = SdpPackagePriceHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_PRICE_ID, packagePriceId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			packagePriceHelper.validateSearchPackagePriceById(packagePriceId);
			em = PersistenceManager.getEntityManager(tenantName);
			// retrieve refPartyGroup
			SdpPackagePrice packagePrice = packagePriceHelper.searchPackagePriceById(packagePriceId, em);
			if (packagePrice == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_PRICE_ID, packagePriceId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpPackagePriceDto> searchResult = new ArrayList<SdpPackagePriceDto>();
			searchResult.add(BeanConverter.convertSdpPackagePriceResponseDto(packagePrice));
			resp.setSearchResult(searchResult);
			log.logDebug(Utilities.getCurrentClassAndMethod() + " result " + Constants.CODE_OK);
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
	 * This method allows to update the information associated to a Price already present into CSM database. The method updates only the field valorized in
	 * input. Before modifing the PackagePrice will need to verify that the parameters, that are bound to RC_Price_Catalog_ID field, are populated with the
	 * correct values in relation to the value taken from the RC_Price_Catalog_ID field. For example, if the RC_Price_Catalog_ID field is populated with an id
	 * related to a nonzero price amount, the fields:
	 * 
	 * RC_Frequency_Type_IDR RC_Flag_Prorate RC_In_Advance
	 * 
	 * could be populated with one of the allowed values. However, if the RC_Price_Catalog_ID field is populated with the id related to the price amount = 0
	 * (zero), these fields must be populated only in the following way:
	 * 
	 * RC_Frequency_Type_ID -> id related to the value 'No Frequency' RC_Flag_Prorate -> N RC_In_Advance -> N
	 * </p>
	 * 
	 * @param packagePriceId
	 * @param rcPriceCatalogId
	 * @param nrcPriceCatalogId
	 * @param currencyTypeId
	 * @param rcFrequencyTypeId
	 * @param rcFlagProrate
	 * @param rcInAdvance
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpPackagePrice and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse modifyPackagePrice(Long packagePriceId, Long rcPriceCatalogId, Long nrcPriceCatalogId, Long currencyTypeId,
			Long rcFrequencyTypeId, String rcFlagProrate, String rcInAdvance, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_PRICE_ID, packagePriceId);
		logMap.put("rcPriceCatalogId", rcPriceCatalogId);
		logMap.put("nrcPriceCatalogId", nrcPriceCatalogId);
		logMap.put("CurrencyTypeId", currencyTypeId);
		logMap.put(RC_FREQUENCY_TYPE_ID, rcFrequencyTypeId);
		logMap.put("rcFlagProrate", rcFlagProrate);
		logMap.put("rcInAdvance", rcInAdvance);
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

			modifyPackagePrice(packagePriceId, rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId, rcFlagProrate, rcInAdvance,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			log.logDebug(Utilities.getCurrentClassAndMethod() + " result " + Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
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

	SdpPackagePrice modifyPackagePrice(Long packagePriceId, Long rcPriceCatalogId, Long nrcPriceCatalogId, Long currencyTypeId, Long rcFrequencyTypeId,
			String rcFlagProrate, String rcInAdvance, String modifiedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackagePriceHelper helper = SdpPackagePriceHelper.getInstance();
		helper.validateSearchPackagePriceById(packagePriceId);
		helper.validatePackagePrice(rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId, rcFlagProrate, rcInAdvance);
		SdpPackagePrice packagePriceToUpdate = helper.searchPackagePriceById(packagePriceId, em);
		if (packagePriceToUpdate == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_PRICE_ID, packagePriceId);
		}
		SdpPackagePrice packagePricePivot = setupPackagePrice(rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId, rcFlagProrate,
				rcInAdvance, em);
		// creazione party
		helper.modifyPackagePrice(packagePriceToUpdate, packagePricePivot.getRefPriceCatalogRC(), packagePricePivot.getRefPriceCatalogNRC(),
				packagePricePivot.getRefCurrencyType(), packagePricePivot.getRefFrequencyType(), packagePricePivot.getRcFlagProrate(),
				packagePricePivot.getRcInAdvance(), modifiedBy, em);
		return packagePriceToUpdate;
	}

	private SdpPackagePrice setupPackagePrice(Long rcPriceCatalogId, Long nrcPriceCatalogId, Long currencyTypeId, Long rcFrequencyTypeId, String rcFlagProrate,
			String rcInAdvance, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackagePrice packagePrice = new SdpPackagePrice();
		SdpPriceCatalog refPriceCatalogRC = null;
		SdpPriceCatalog refPriceCatalogNRC = null;
		RefCurrencyType refCurrencyType = null;
		RefFrequencyType refFrequencyType = null;
		SdpPriceCatalogHelper priceCatalogHelper = SdpPriceCatalogHelper.getInstance();
		RefCurrencyTypeHelper currencyTypeHelper = RefCurrencyTypeHelper.getInstance();
		RefFrequencyTypeHelper frequencyCatalogHelper = RefFrequencyTypeHelper.getInstance();
		// Get Recurring Charge price
		refPriceCatalogRC = priceCatalogHelper.searchPriceCatalogById(rcPriceCatalogId, em);
		if (refPriceCatalogRC == null || refPriceCatalogRC.getPrice() == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "rcPriceCatalogId", String.valueOf(rcPriceCatalogId));
		}
		// Logic for RC Price Amount = 0
		if (refPriceCatalogRC.getPrice().compareTo(BigDecimal.ZERO) == 0) {
			if (!rcFrequencyTypeId.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.NULL_FREQUENCY))) {
				throw new ValidationException(Constants.CODE_PARAMETER_NOT_RESPECT_CONSTRAINT_WITH_ANOTHER_PARAMETER, RC_FREQUENCY_TYPE_ID,
						String.valueOf(rcFrequencyTypeId));
			}
			if (!rcFlagProrate.equals(Flag.NO.getValue())) {
				throw new ValidationException(Constants.CODE_PARAMETER_NOT_RESPECT_CONSTRAINT_WITH_ANOTHER_PARAMETER, "rcFlagProrate", rcFlagProrate);
			}
			if (!rcInAdvance.equals(Flag.NO.getValue())) {
				throw new ValidationException(Constants.CODE_PARAMETER_NOT_RESPECT_CONSTRAINT_WITH_ANOTHER_PARAMETER, "rcInAdvance", rcInAdvance);
			}
		} else {
			if (rcFrequencyTypeId.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.NULL_FREQUENCY))) {
				throw new ValidationException(Constants.CODE_PARAMETER_NOT_RESPECT_CONSTRAINT_WITH_ANOTHER_PARAMETER, RC_FREQUENCY_TYPE_ID,
						String.valueOf(rcFrequencyTypeId));
			}

		}
		// Get NOT Recurring Charge price
		refPriceCatalogNRC = priceCatalogHelper.searchPriceCatalogById(nrcPriceCatalogId, em);
		if (refPriceCatalogNRC == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "nrcPriceCatalogId", String.valueOf(nrcPriceCatalogId));
		}
		// Get Currency
		refCurrencyType = currencyTypeHelper.searchCurrencyById(currencyTypeId, em);
		if (refCurrencyType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "currencyTypeId", currencyTypeId);
		}
		// Get Frequency
		refFrequencyType = frequencyCatalogHelper.searchFrequencyTypeById(rcFrequencyTypeId, em);
		if (refFrequencyType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, RC_FREQUENCY_TYPE_ID, String.valueOf(rcFrequencyTypeId));
		}

		packagePrice.setRefPriceCatalog1(refPriceCatalogRC);
		packagePrice.setRefPriceCatalog2(refPriceCatalogNRC);
		packagePrice.setRefCurrencyType(refCurrencyType);
		packagePrice.setRefFrequencyType(refFrequencyType);
		packagePrice.setRcInAdvance(rcInAdvance);
		packagePrice.setRcFlagProrate(rcFlagProrate);
		return packagePrice;
	}

}
