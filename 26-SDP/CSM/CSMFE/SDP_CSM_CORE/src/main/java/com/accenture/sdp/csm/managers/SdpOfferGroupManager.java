package com.accenture.sdp.csm.managers;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpOfferGroupHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.model.jpa.SdpOfferGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpOfferGroupManager extends SdpBaseManager {

	private SdpOfferGroupManager() {
		super();
	}

	private static SdpOfferGroupManager instance;

	public static SdpOfferGroupManager getInstance() {
		if (instance == null) {
			instance = new SdpOfferGroupManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to create a new group of offer into a package
	 * </p>
	 * 
	 * @param groupName
	 *            Name of group of offers
	 * @param solutionOfferId
	 *            Identifier of the solution offer
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createOfferGroup(String groupName, Long solutionOfferId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(GROUP_NAME, groupName);
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
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
			SdpOfferGroup newPackageGroup = createOfferGroup(groupName, solutionOfferId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			log.logDebug("OfferGroup with id: %s inserted successfully", newPackageGroup.getGroupId());
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newPackageGroup.getGroupId());
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

	SdpOfferGroup createOfferGroup(String groupName, Long solutionOfferId, String createdBy, EntityManager em) throws ValidationException {
		SdpOfferGroupHelper offerGroupHelper = SdpOfferGroupHelper.getInstance();
		offerGroupHelper.validateOfferGroup(groupName, solutionOfferId);
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			log.logDebug("solutionOfferId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}

		// verifica duplicati
		if (offerGroupHelper.searchOfferGroup(groupName, solutionOfferId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_ID, solutionOfferId), new ParameterDto(GROUP_NAME,
					groupName));
		}

		SdpOfferGroup packageGroup = offerGroupHelper.createOfferGroup(groupName, solutionOffer, createdBy, em);

		return packageGroup;
	}

	public DataServiceResponse modifyOfferGroup(Long groupId, String groupName, Long solutionOfferId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(GROUP_ID, groupId);
		logMap.put(GROUP_NAME, groupName);
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
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
			modifyOfferGroup(groupId, groupName, solutionOfferId, Utilities.getCurrentClassAndMethod(), em);

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

	SdpOfferGroup modifyOfferGroup(Long groupId, String groupName, Long solutionOfferId, String updatedBy, EntityManager em) throws ValidationException {
		SdpOfferGroupHelper offerGroupHelper = SdpOfferGroupHelper.getInstance();
		offerGroupHelper.validateSearchOfferGroupById(groupId);
		offerGroupHelper.validateOfferGroup(groupName, solutionOfferId);

		SdpOfferGroup groupToModify = offerGroupHelper.searchOfferGroupById(groupId, em);
		if (groupToModify == null) {
			log.logDebug("groupId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, GROUP_ID, groupId);
		}

		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			log.logDebug("solutionOfferId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		// REQ: SDP_02_06
		if (!solutionOffer.equals(groupToModify.getSdpSolutionOffer())) {
			log.logDebug("unallowed change of solutionOffer");
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_ID, solutionOfferId);
		}

		// verifica duplicati
		SdpOfferGroup temp = offerGroupHelper.searchOfferGroup(groupName, solutionOfferId, em);
		if (temp != null && !temp.getGroupId().equals(groupId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_ID, solutionOfferId), new ParameterDto(GROUP_NAME,
					groupName));
		}

		offerGroupHelper.modifyOfferGroup(groupToModify, groupName, solutionOffer, updatedBy);

		return groupToModify;
	}

	/**
	 * <p>
	 * This method allows to delete an offer group
	 * </p>
	 * 
	 * @param groupId
	 *            Identifier of the offer group to delete
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteOfferGroup(Long groupId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(GROUP_ID, groupId);
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

			deleteOfferGroup(groupId, em);

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
			resp = buildCreateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(GROUP_ID, groupId));
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

	void deleteOfferGroup(Long groupId, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOfferGroupHelper offerGroupHelper = SdpOfferGroupHelper.getInstance();
		offerGroupHelper.validateSearchOfferGroupById(groupId);

		SdpOfferGroup packageGroupToDelete = offerGroupHelper.searchOfferGroupById(groupId, em);
		if (packageGroupToDelete == null) {
			log.logDebug("groupId not found");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, GROUP_ID, groupId);
		}

		// SDP_02_07
		if (packageGroupToDelete.getSdpPackages() != null && !packageGroupToDelete.getSdpPackages().isEmpty()) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, GROUP_ID, groupId);
		}

		// end validation
		offerGroupHelper.deleteOfferGroup(packageGroupToDelete, em);

	}

	/**
	 * <p>
	 * This method allows to return the informations about an offer group:
	 * </p>
	 * 
	 * @param groupName
	 *            Identifier of the offer group
	 * @param solutionOfferId
	 *            Identifier of the solution offer
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchOfferGroup(String groupName, Long solutionOfferId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(GROUP_NAME, groupName);
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOfferGroupHelper offerGroupHelper = SdpOfferGroupHelper.getInstance();
			offerGroupHelper.validateOfferGroup(groupName, solutionOfferId);
			em = PersistenceManager.getEntityManager(tenantName);

			SdpSolutionOfferHelper solOfferHelp = SdpSolutionOfferHelper.getInstance();
			SdpSolutionOffer solOffer = solOfferHelp.searchSolutionOfferById(solutionOfferId, em);
			if (solOffer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}

			SdpOfferGroup res = offerGroupHelper.searchOfferGroup(groupName, solutionOfferId, em);
			if (res == null) {
				log.logDebug("group not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(GROUP_NAME, groupName), new ParameterDto(SOLUTION_OFFER_ID,
						solutionOfferId));
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpOfferGroup> offerGroupList = new ArrayList<SdpOfferGroup>();
			offerGroupList.add(res);

			resp.setSearchResult(BeanConverter.convertListOfSdpOfferGroupResponseDto(offerGroupList));
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

}
