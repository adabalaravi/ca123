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
import com.accenture.sdp.csm.dto.requests.SdpPackageRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpOfferGroupHelper;
import com.accenture.sdp.csm.helpers.SdpOfferHelper;
import com.accenture.sdp.csm.helpers.SdpPackageHelper;
import com.accenture.sdp.csm.helpers.SdpPackagePriceHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpOfferGroup;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpPackagePrice;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpPackageManager extends SdpBaseManager {

	private SdpPackageManager() {
		super();
	}

	private static SdpPackageManager instance;

	public static SdpPackageManager getInstance() {
		if (instance == null) {
			instance = new SdpPackageManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database all information about a package.
	 * </p>
	 * 
	 * @param solutionOfferId
	 *            solutionOfferId of the solution offer
	 * @param externalId
	 *            Identifier used by external systems
	 * @param offerId
	 *            offerId associated to the solutionOfferId
	 * @param isMandatory
	 *            Flag that indicates if the offer is mandatory in the solution offer.
	 * @param packagePriceId
	 *            package price associated to the list of offer id
	 * @param basePackageId
	 *            Id of the package of the parent base offer
	 * @param groupId
	 *            Id of the group of offer
	 * @param profile
	 *            package profile
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createPackage(Long solutionOfferId, Long offerId, String isMandatory, Long packagePriceId, Long basePackageId, Long groupId,
			String externalId, String profile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(OFFER_ID, offerId);
		logMap.put("isMandatory", isMandatory);
		logMap.put(PACKAGE_PRICE_ID, packagePriceId);
		logMap.put(BASE_PACKAGE_ID, basePackageId);
		logMap.put(GROUP_ID, groupId);
		logMap.put("profile", profile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		CreateServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpPackage newPackage = createPackage(solutionOfferId, offerId, isMandatory, packagePriceId, basePackageId, groupId, externalId, profile,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			log.logDebug("SdpPackage with id:%s inserted successfully", newPackage.getPackageId());
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newPackage.getPackageId());
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

	SdpPackage createPackage(Long solutionOfferId, Long offerId, String isMandatory, Long packagePriceId, Long basePackageId, Long groupId, String externalId,
			String profile, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		SdpPackagePriceHelper packagePriceHelper = SdpPackagePriceHelper.getInstance();
		SdpOfferGroupHelper packageGroupHelper = SdpOfferGroupHelper.getInstance();

		packageHelper.validatePackage(solutionOfferId, offerId, packagePriceId, isMandatory);
		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			log.logDebug("solutionOfferId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		SdpOffer offer = offerHelper.searchOfferById(offerId, em);
		if (offer == null) {
			log.logDebug("offerId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
		}
		SdpPackagePrice packagePrice = packagePriceHelper.searchPackagePriceById(packagePriceId, em);
		if (packagePrice == null) {
			log.logDebug("packagePriceId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_PRICE_ID, packagePriceId);
		}

		SdpOfferGroup packageGroup = null;
		if (groupId != null) {
			packageGroup = packageGroupHelper.searchOfferGroupById(groupId, em);
			if (packageGroup == null) {
				log.logDebug("groupId not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, GROUP_ID, groupId);
			}

		}

		SdpPackage basePackage = null;
		if (basePackageId != null) {
			basePackage = packageHelper.searchPackageById(basePackageId, em);
			if (basePackage == null) {
				log.logDebug("basePackageId not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, BASE_PACKAGE_ID, basePackageId);
			}
			if (!basePackage.getSdpSolutionOffer().getSolutionOfferId().equals(solutionOfferId)) {
				log.logDebug("basePackageId value not Allowed");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, BASE_PACKAGE_ID, basePackageId);
			}
		}

		// End validation

		// verifica duplicati
		if (packageHelper.searchPackageBySolutionOfferIdAndOfferId(solutionOfferId, offerId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_ID, solutionOfferId),
					new ParameterDto(OFFER_ID, offerId));
		}
		if (packageHelper.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}
		SdpPackage newPackage = packageHelper.createPackage(solutionOffer, externalId, offer, isMandatory, packagePrice, basePackage, packageGroup, profile,
				createdBy, em);

		return newPackage;
	}

	/**
	 * <p>
	 * This method allows to modify into CSM database all information about a package.
	 * </p>
	 * 
	 * @param packageId
	 *            Id of the package
	 * @param solutionOfferId
	 *            solutionOfferId of the solution offer
	 * @param offerId
	 *            offerId of the offer
	 * @param isMandatory
	 *            Name of the offer
	 * @param packagePriceId
	 *            Package Price associated to the couple (solution offer, offer)
	 * @param externalId
	 *            Identifier used by external system
	 * @param basePackageId
	 *            Id of the package of the parent base offer
	 * @param groupId
	 *            Id of the group of offer
	 * @param profile
	 *            package profile
	 * 
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyPackage(Long packageId, Long solutionOfferId, Long offerId, String isMandatory, Long packagePriceId, Long basePackageId,
			Long groupId, String externalId, String profile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_ID, packageId);
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(OFFER_ID, offerId);
		logMap.put("isMandatory", isMandatory);
		logMap.put(PACKAGE_PRICE_ID, packagePriceId);
		logMap.put(BASE_PACKAGE_ID, basePackageId);
		logMap.put(GROUP_ID, groupId);
		logMap.put("profile", profile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		CreateServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyPackage(packageId, solutionOfferId, offerId, isMandatory, packagePriceId, basePackageId, groupId, externalId, profile,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			log.logDebug("SdpPackage with id:%s updated successfully", packageId);
			resp = buildCreateResponse(Constants.CODE_OK);
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

	SdpPackage modifyPackage(Long packageId, Long solutionOfferId, Long offerId, String isMandatory, Long packagePriceId, Long basePackageId, Long groupId,
			String externalId, String profile, String updatedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		SdpPackagePriceHelper packagePriceHelper = SdpPackagePriceHelper.getInstance();
		SdpOfferGroupHelper packageGroupHelper = SdpOfferGroupHelper.getInstance();

		packageHelper.validateSearchPackageById(packageId);
		packageHelper.validatePackage(solutionOfferId, offerId, packagePriceId, isMandatory);

		SdpPackage packageToModify = packageHelper.searchPackageById(packageId, em);
		if (packageToModify == null) {
			log.logDebug("packageId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
		}
		if (!isStatusActive(packageToModify.getStatusId())) {
			log.logDebug("package not active");
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, PACKAGE_ID, packageId);
		}

		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			log.logDebug("solutionOfferId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		// REQ: SDP_02_06
		if (!solutionOfferId.equals(packageToModify.getSdpSolutionOffer().getSolutionOfferId())) {
			log.logDebug("unallowed change of solutionOffer");
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_ID, solutionOfferId);
		}

		SdpOffer offer = offerHelper.searchOfferById(offerId, em);
		if (offer == null) {
			log.logDebug("offerId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OFFER_ID, offerId);
		}
		SdpPackagePrice packagePrice = packagePriceHelper.searchPackagePriceById(packagePriceId, em);
		if (packagePrice == null) {
			log.logDebug("packagePriceId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_PRICE_ID, packagePriceId);
		}

		SdpPackage basePackage = null;
		if (basePackageId != null) {
			basePackage = packageHelper.searchPackageById(basePackageId, em);
			if (basePackage == null) {
				log.logDebug("basePackageId not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, BASE_PACKAGE_ID, basePackageId);
			}
			// REQ: SDP_02_30
			if (!basePackage.getSdpSolutionOffer().getSolutionOfferId().equals(solutionOfferId)) {
				log.logDebug("basePackageId value not Allowed");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, BASE_PACKAGE_ID, basePackageId);
			}
		}

		SdpOfferGroup packageGroup = null;
		if (groupId != null) {
			packageGroup = packageGroupHelper.searchOfferGroupById(groupId, em);
			if (packageGroup == null) {
				log.logDebug("groupId not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, GROUP_ID, groupId);
			}

		}

		// End validation

		// verifica duplicati
		SdpPackage temp = packageHelper.searchPackageBySolutionOfferIdAndOfferId(solutionOfferId, offerId, em);
		if (temp != null && !temp.getPackageId().equals(packageId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_ID, solutionOfferId),
					new ParameterDto(OFFER_ID, offerId));
		}

		if (!Utilities.isNull(externalId)) {
			temp = packageHelper.searchByExternalId(externalId, em);
			if (temp != null && !temp.getPackageId().equals(packageId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		packageHelper.modifyPackage(packageToModify, packageToModify.getSdpSolutionOffer(), externalId, offer, isMandatory, packagePrice, basePackage,
				packageGroup, profile, updatedBy);

		return packageToModify;

	}

	/**
	 * <p>
	 * This method allows to update into CSM database all information about all packages into a solution offer.
	 * </p>
	 * <p>
	 * This method accept in inout a list of SdpPackageRequestDto, each one containing the following parameter
	 * </p>
	 * 
	 * @param solutionOfferId
	 *            Id of solution offer
	 * @param SdpPackageRequestDtoList
	 *            List of SdpPackageRequestDto
	 * 
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyPackage(Long solutionOfferId, List<SdpPackageRequestDto> sdpPackageRequestDtoList, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put("package", sdpPackageRequestDtoList);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		CreateServiceResponse resp = null;
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpPackagePriceManager packagePriceManager = SdpPackagePriceManager.getInstance();
		SdpPackagePriceHelper packagePriceHelper = SdpPackagePriceHelper.getInstance();
		EntityTransaction tx = null;
		try {
			solutionOfferHelper.validateSearchSolutionOfferById(solutionOfferId);

			// VALIDATION
			for (SdpPackageRequestDto sdpPackageRequestDto : sdpPackageRequestDtoList) {
				log.logDebug("validating Package info for packageId: %s", sdpPackageRequestDto.getPackageId());
				packageHelper.validateSearchPackageById(sdpPackageRequestDto.getPackageId());
				packageHelper.validatePackage(solutionOfferId, sdpPackageRequestDto.getOfferId(), sdpPackageRequestDto.getPackagePriceId(),
						sdpPackageRequestDto.getIsMandatory());

				packagePriceHelper.validatePackagePrice(sdpPackageRequestDto.getRcPriceCatalogId(), sdpPackageRequestDto.getNrcPriceCatalogId(),
						sdpPackageRequestDto.getCurrencyTypeId(), sdpPackageRequestDto.getRcFrequencyTypeId(), sdpPackageRequestDto.getRcFlagProrate(),
						sdpPackageRequestDto.getRcInAdvance());
			}

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);

			if (solutionOffer == null) {
				log.logDebug("solutionOfferId not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}

			// cicle on every sdpPackageRequestDto
			String classAndMethod = Utilities.getCurrentClassAndMethod();
			for (SdpPackageRequestDto sdpPackageRequestDto : sdpPackageRequestDtoList) {
				log.logDebug("Invoking method _modifyPackage for packageId: %s", sdpPackageRequestDto.getPackageId());
				modifyPackage(sdpPackageRequestDto.getPackageId(), solutionOfferId, sdpPackageRequestDto.getOfferId(), sdpPackageRequestDto.getIsMandatory(),
						sdpPackageRequestDto.getPackagePriceId(), sdpPackageRequestDto.getBasePackageId(), sdpPackageRequestDto.getGroupId(),
						sdpPackageRequestDto.getExternalId(), sdpPackageRequestDto.getProfile(), classAndMethod, em);

				packagePriceManager.modifyPackagePrice(sdpPackageRequestDto.getPackagePriceId(), sdpPackageRequestDto.getRcPriceCatalogId(),
						sdpPackageRequestDto.getNrcPriceCatalogId(), sdpPackageRequestDto.getCurrencyTypeId(), sdpPackageRequestDto.getRcFrequencyTypeId(),
						sdpPackageRequestDto.getRcFlagProrate(), sdpPackageRequestDto.getRcInAdvance(), classAndMethod, em);
			}
			// end cicle

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildCreateResponse(Constants.CODE_OK);
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

	/**
	 * <p>
	 * This method allows to delete a package only in logically mode.
	 * </p>
	 * 
	 * @param packageId
	 *            identifier of the package
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deletePackage(Long packageId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_ID, packageId);
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

			boolean warning = deletePackage(packageId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			if (warning) {
				resp = buildUpdateResponse(Constants.CODE_OK_RELATED_SUBSCRIPTIONS);
				log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK_RELATED_SUBSCRIPTIONS);
			} else {
				resp = buildUpdateResponse(Constants.CODE_OK);
				log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			}
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

	boolean deletePackage(Long packageId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		packageHelper.validateSearchPackageById(packageId);

		SdpPackage packageToDelete = packageHelper.searchPackageById(packageId, em);
		if (packageToDelete == null) {
			log.logDebug("packageId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
		}

		packageHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, packageToDelete.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED),
				em);

		// end validation
		packageHelper.deletePackage(packageToDelete, deletedBy);

		// RESULT 002 => variante SDP_02_25
		SdpSubscriptionHelper subHelper = SdpSubscriptionHelper.getInstance();
		Long nSub = subHelper.countSubscriptionDetailsNotDeletedByPackageId(packageId, em);
		if (nSub != null && nSub > 0L) {
			log.logDebug(nSub + " subscriptions details not deleted found for package with id " + packageId);
			// RETURN WARNING
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a Package.
	 * </p>
	 * 
	 * @param packageId
	 *            identifier of the package
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchPackage(Long packageId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_ID, packageId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpPackageHelper helper = SdpPackageHelper.getInstance();
			helper.validateSearchPackageById(packageId);
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpPackage res = helper.searchPackageById(packageId, em);
			if (res == null) {
				log.logDebug("packageId not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpPackage> packagesList = new ArrayList<SdpPackage>();
			packagesList.add(res);
			resp.setSearchResult(BeanConverter.convertListOfSdpPackageResponseDto(packagesList));
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
	 * This method allows to retrieve the information related to a Package. Using this interface it's possible to retrieve all the content of a solution offer
	 * if in input is present only the solutionOfferName Otherwise, if the input is composed both of solutionOfferName and offerName it's retrieved only the
	 * association of solution offer - offer.
	 * 
	 * </p>
	 * 
	 * @param solutionOfferName
	 *            solutionOfferName of the solution
	 * @param offerName
	 *            offerName of the offer
	 * @param startPosition
	 *            Index of the first result
	 * @param maxRecordsNumber
	 *            Total number of rows to return
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchPackage(String solutionOfferName, String offerName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		logMap.put("offerName", offerName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpSolutionOfferHelper solOfferHelp = SdpSolutionOfferHelper.getInstance();
			solOfferHelp.validateSearchSolutionOfferByName(solutionOfferName);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			// ricerca solutionOffer
			SdpSolutionOffer inputSolutionOffer = solOfferHelp.searchSolutionOfferByName(solutionOfferName, em);
			if (inputSolutionOffer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_NAME, solutionOfferName);
			}
			// if solOffer is a discounted one, have to search packages from the parent solutionOffer
			SdpSolutionOffer keySolutionOffer = inputSolutionOffer;
			if (solOfferHelp.isDiscounted(inputSolutionOffer)) {
				keySolutionOffer = inputSolutionOffer.getParentSolutionOffer();
			}

			List<SdpPackage> results = new ArrayList<SdpPackage>();
			SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();

			Long totalResult;
			if (!Utilities.isNull(offerName)) {
				// ricerca offer
				SdpOffer offer = (SdpOfferHelper.getInstance()).searchOfferByName(offerName, em);
				if (offer == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "offerName", offerName);
				}

				log.logDebug("Searching packages for solutionOfferName: %s and offerName: %s", keySolutionOffer.getSolutionOfferName(), offerName);

				SdpPackage pack = packageHelper.searchPackageBySolutionOfferIdAndOfferId(keySolutionOffer.getSolutionOfferId(), offer.getOfferId(), em);
				if (pack == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SOLUTION_OFFER_NAME, solutionOfferName), new ParameterDto(
							"offerName", offerName));
				}
				totalResult = Long.valueOf(1L);
				results.add(pack);
				resp = buildSearchResponse(Constants.CODE_OK);
			} else {
				packageHelper.validateSearchAll(startPosition, maxRecordsNumber);
				log.logDebug("Searching packages for solutionOfferName: " + keySolutionOffer.getSolutionOfferName());
				totalResult = packageHelper.countPackageBySolutionOfferId(keySolutionOffer.getSolutionOfferId(), em);
				if (totalResult == null || totalResult == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SOLUTION_OFFER_NAME, solutionOfferName);
				}

				results = packageHelper.searchPackageBySolutionOfferId(keySolutionOffer.getSolutionOfferId(), startPosition, maxRecordsNumber, em);

				if (results == null || results.isEmpty()) {
					resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
				} else {
					resp = buildSearchResponse(Constants.CODE_OK);
				}
			}

			// if inputSolutionOffer is discounted, have to apply the discounts
			if (solOfferHelp.isDiscounted(inputSolutionOffer)) {
				resp.setSearchResult(BeanConverter.convertListOfDiscountedSdpPackageResponseDto(results, inputSolutionOffer.getDiscounts()));
			} else {
				resp.setSearchResult(BeanConverter.convertListOfSdpPackageResponseDto(results));
			}
			resp.setTotalResult(totalResult);
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
	 * This method allows to change the status of package.
	 * </p>
	 * 
	 * @param packageId
	 *            Id of package
	 * @param status
	 *            status of package
	 * 
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse packageChangeStatus(Long packageId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_ID, packageId);
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

			packageChangeStatus(packageId, status, Utilities.getCurrentClassAndMethod(), em);
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

	SdpPackage packageChangeStatus(Long packageId, String nextStatus, String changedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackageHelper helper = SdpPackageHelper.getInstance();
		helper.validateSearchPackageById(packageId);
		helper.validateChangeStatus(nextStatus);
		SdpPackage packageToUpdate = helper.searchPackageById(packageId, em);
		if (packageToUpdate == null) {
			log.logDebug("packageId not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, packageToUpdate.getStatusId(), StatusIdConverter.getStatusValue(nextStatus), em);
		helper.changeStatus(packageToUpdate, StatusIdConverter.getStatusValue(nextStatus), changedBy);

		return packageToUpdate;

	}

	public DataServiceResponse isPackageSubscribed(Long packageId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PACKAGE_ID, packageId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			SdpPackageHelper helper = SdpPackageHelper.getInstance();
			helper.validateSearchPackageById(packageId);
			SdpPackage packageBean = helper.searchPackageById(packageId, em);
			if (packageBean == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
			}
			Long result = SdpSubscriptionHelper.getInstance().countSubscriptionDetailsNotDeletedByPackageId(packageId, em);
			if (result != null && result.longValue() > 0L) {
				resp = buildSearchResponse(Constants.CODE_OK);
			} else {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PACKAGE_ID, packageId);
			}
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