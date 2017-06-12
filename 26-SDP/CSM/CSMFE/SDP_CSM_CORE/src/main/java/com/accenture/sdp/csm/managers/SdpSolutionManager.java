package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.LinkUpdateOperation;
import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefSolutionTypeHelper;
import com.accenture.sdp.csm.helpers.SdpPartyGroupHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.model.jpa.RefSolutionType;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolution;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

/**
 * @author alberto.marimpietri
 * 
 */
public final class SdpSolutionManager extends SdpBaseManager {

	private SdpSolutionManager() {
		super();
	}

	private static SdpSolutionManager instance;

	public static SdpSolutionManager getInstance() {
		if (instance == null) {
			instance = new SdpSolutionManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new solution. The new solution is created by default with ACTIVE Status
	 * </p>
	 * 
	 * @param solutionName
	 *            Name of the new solution to create
	 * @param solutionDescription
	 *            Description of the new solution to create
	 * @param externalId
	 *            External id of new solution to create
	 * @param startDate
	 *            starting date of the solution to create
	 * @param endDate
	 *            ending date of the solution to create
	 * @param solutionTypeId
	 *            type of the solution to create
	 * @param profile
	 *            profile of the solution to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpSolution and operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createSolution(String solutionName, String solutionDescription, String externalId, Date startDate, Date endDate,
			Long solutionTypeId, String profile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_NAME, solutionName);
		logMap.put("solutionDescription", solutionDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(SOLUTION_TYPE_ID, solutionTypeId);
		logMap.put("profile", profile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		CreateServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpSolution solution = createSolution(solutionName, solutionDescription, externalId, startDate, endDate, solutionTypeId, profile, null,
					Utilities.getCurrentClassAndMethod(), em);
			// commit
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(solution.getSolutionId());
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

	SdpSolution createSolution(String solutionName, String solutionDescription, String externalId, Date startDate, Date endDate, Long solutionTypeId,
			String profile, List<SdpPartyGroup> partyGroups, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
		RefSolutionTypeHelper solutionTypeHelper = RefSolutionTypeHelper.getInstance();
		// formal validation
		handler.validateSolution(solutionName, solutionTypeId, startDate, endDate);

		// solution type validation
		RefSolutionType solutionType = solutionTypeHelper.searchSolutionTypeById(solutionTypeId, em);
		if (solutionType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_TYPE_ID, String.valueOf(solutionTypeId));
		}
		// duplicates
		SdpSolution checkName = handler.searchSolutionByName(solutionName, em);
		if (checkName != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SOLUTION_NAME, solutionName);
		}
		if (externalId != null) {
			SdpSolution checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		return handler.createSolution(solutionName, solutionDescription, externalId, startDate, endDate, solutionType, profile, partyGroups, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new solution and the links to the related party groups. The new solution is created by default with
	 * ACTIVE Status
	 * </p>
	 * 
	 * @param solutionName
	 *            Name of the new solution to create
	 * @param solutionDescription
	 *            Description of the new solution to create
	 * @param externalId
	 *            External id of new solution to create
	 * @param startDate
	 *            starting date of the solution to create
	 * @param endDate
	 *            ending date of the solution to create
	 * @param solutionTypeId
	 *            type of the solution to create
	 * @param profile
	 *            profile of the solution to create
	 * @param partyGroups
	 *            party groups to relate to the solution to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpSolution and operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createSolution(String solutionName, String solutionDescription, String externalId, Date startDate, Date endDate,
			Long solutionTypeId, String profile, List<String> partyGroups, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_NAME, solutionName);
		logMap.put("solutionDescription", solutionDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, startDate);
		logMap.put(SOLUTION_TYPE_ID, solutionTypeId);
		logMap.put("profile", profile);
		logMap.put("partyGroups", partyGroups);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		CreateServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpPartyGroupHelper groupHelper = SdpPartyGroupHelper.getInstance();
			List<SdpPartyGroup> groups = new ArrayList<SdpPartyGroup>();

			if (partyGroups != null) {
				for (String groupName : partyGroups) {
					groupHelper.validatePartyGroup(groupName);
					SdpPartyGroup group = groupHelper.searchPartyGroupByName(groupName, em);
					if (group == null) {
						throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_NAME, groupName);
					}
					// verifica duplicati -> gestisco direttamente sulla lista in input
					if (groups.contains(group)) {
						throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY,
						// in realtà il vincolo sarebbe solutionId + partyGroupId, ma in input ci sono i name
								new ParameterDto(SOLUTION_NAME, solutionName), new ParameterDto(PARTY_GROUP_NAME, groupName));
					}
					groups.add(group);
				}
			}
			SdpSolution solution = createSolution(solutionName, solutionDescription, externalId, startDate, endDate, solutionTypeId, profile, groups,
					Utilities.getCurrentClassAndMethod(), em);
			// commit
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(solution.getSolutionId());
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

	/**
	 * <p>
	 * This method allows to update the information associated to a solution (but not the Status) already present into CSM database with the following
	 * constraints:
	 * 
	 * <li>The solution status is ACTIVE</li>
	 * </p>
	 * 
	 * @param solutionId
	 *            Id of the solution to modify
	 * @param solutionName
	 *            Name of the new solution to modify
	 * @param solutionDescription
	 *            Description of the new solution to modify
	 * @param externalId
	 *            External id of new solution to modify
	 * @param startDate
	 *            starting date of the solution to modify
	 * @param endDate
	 *            ending date of the solution to modify
	 * @param solutionTypeId
	 *            type of the solution to modify
	 * @param profile
	 *            profile of the solution to modify
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifySolution(Long solutionId, String solutionName, Long solutionTypeId, String solutionDescription, String externalId,
			Date startDate, Date endDate, String profile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_ID, solutionId);
		logMap.put(SOLUTION_NAME, solutionName);
		logMap.put("solutionDescription", solutionDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, startDate);
		logMap.put(SOLUTION_TYPE_ID, solutionTypeId);
		logMap.put("profile", profile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifySolution(solutionId, solutionName, solutionTypeId, solutionDescription, externalId, startDate, endDate, profile,
					Utilities.getCurrentClassAndMethod(), em);

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

	SdpSolution modifySolution(Long solutionId, String solutionName, Long solutionTypeId, String solutionDescription, String externalId, Date startDate,
			Date endDate, String profile, String modifieBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
		RefSolutionTypeHelper solutionTypeHelper = RefSolutionTypeHelper.getInstance();
		// formal validation
		handler.validateSolution(solutionName, solutionTypeId, startDate, endDate);
		handler.validateSearchSolutionById(solutionId);
		// solutionType validation
		RefSolutionType solutionType = solutionTypeHelper.searchSolutionTypeById(solutionTypeId, em);
		if (solutionType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_TYPE_ID, String.valueOf(solutionTypeId));
		}
		// workflow validation
		SdpSolution toModify = handler.searchSolutionById(solutionId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
		}
		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SOLUTION_ID, solutionId);
		}
		// duplicates
		SdpSolution checkName = handler.searchSolutionByName(solutionName, em);
		if (checkName != null && !checkName.getSolutionId().equals(solutionId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SOLUTION_NAME, solutionName);
		}
		if (externalId != null) {
			SdpSolution checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null && !checkExternalId.getSolutionId().equals(solutionId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		handler.modifySolution(toModify, solutionName, solutionDescription, externalId, startDate, endDate, solutionType, profile, modifieBy);
		return toModify;
	}

	/**
	 * <p>
	 * This method allows to update the links between a solution and the related party groups with the following constraints:
	 * 
	 * <li>The solution status is ACTIVE</li>
	 * </p>
	 * 
	 * @param solutionId
	 *            Id of the solution to modify
	 * @param partyGroups
	 *            party groups to relate to the solution to create
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifySolutionPartyGroups(Long solutionId, List<SdpPartyGroupRequestDto> partyGroups, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_ID, solutionId);
		logMap.put("partyGroup", partyGroups);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifySolutionPartyGroups(solutionId, partyGroups, Utilities.getCurrentClassAndMethod(), em);

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

	SdpSolution modifySolutionPartyGroups(Long solutionId, List<SdpPartyGroupRequestDto> partyGroups, String modifiedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
		SdpPartyGroupHelper groupHelper = SdpPartyGroupHelper.getInstance();
		// formal validation
		handler.validateSearchSolutionById(solutionId);
		groupHelper.validateLinkUpdateOperation(partyGroups);
		// workflow validation
		SdpSolution toModify = handler.searchSolutionById(solutionId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
		}
		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SOLUTION_ID, solutionId);
		}
		for (SdpPartyGroupRequestDto dto : partyGroups) {
			SdpPartyGroup group = groupHelper.searchPartyGroupById(dto.getPartyGroupId(), em);
			if (group == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, String.valueOf(dto.getPartyGroupId()));
			}
			if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.DELETE.getValue())) {
				groupHelper.removePartyGroupLink(toModify, group, modifiedBy);
			} else if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.NEW.getValue())) {
				// verifica duplicati -> gestisco direttamente sulla lista
				if (toModify.getSdpPartyGroups().contains(group)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_ID, solutionId), new ParameterDto(PARTY_GROUP_ID,
							String.valueOf(dto.getPartyGroupId())));
				}
				groupHelper.addPartyGroupLink(toModify, group, modifiedBy);
			}

		}

		return toModify;
	}

	/**
	 * <p>
	 * This method allows to delete a solution, only in logically mode, setting the status of the solution to DELETED.
	 * 
	 * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * <li>The solution to be deleted is in status Inactive</li>
	 * <li>There aren't solution offers associated to the solution with status different from Deleted</li>
	 * </p>
	 * 
	 * @param solutionId
	 *            Id of the solution to delete
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteSolution(Long solutionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_ID, solutionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			deleteSolution(solutionId, Utilities.getCurrentClassAndMethod(), em);
			// commit
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

	void deleteSolution(Long solutionId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
		SdpSolutionOfferHelper solOfferHelp = SdpSolutionOfferHelper.getInstance();
		// formal validation
		handler.validateSearchSolutionById(solutionId);
		// workflow validation
		SdpSolution toDelete = handler.searchSolutionById(solutionId, em);
		if (toDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
		}
		handler.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, toDelete.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		List<SdpSolutionOffer> offers = solOfferHelp.searchSolutionOffersNotDeletedBySolutionId(solutionId, em);
		if (offers != null && !offers.isEmpty()) {
			log.logDebug("Solution has not-deleted solution offers associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, SOLUTION_ID, solutionId);
		}

		handler.deleteSolution(toDelete, deletedBy);
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a solution. NOTE: The input parameter is mandatory
	 * 
	 * @param solutionId
	 *            Id of the solution to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSolution and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSolution(Long solutionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_ID, solutionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
			// formal validation
			handler.validateSearchSolutionById(solutionId);

			em = PersistenceManager.getEntityManager(tenantName);

			// helper
			SdpSolution solution = handler.searchSolutionById(solutionId, em);
			if (solution == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
			}
			List<SdpSolution> solutions = new ArrayList<SdpSolution>();
			solutions.add(solution);
			List<SdpSolutionDto> solutionResponse = BeanConverter.convertSolution(solutions);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(solutionResponse);
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
	 * This method allows to retrieve the information related to a solution and its related party groups. NOTE: The input parameter is mandatory
	 * 
	 * @param solutionName
	 *            Name of the solution to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSolution and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSolution(String solutionName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_NAME, solutionName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
			// formal validation
			handler.validateSearchSolutionByName(solutionName);

			em = PersistenceManager.getEntityManager(tenantName);

			// helper
			SdpSolution solution = handler.searchSolutionByName(solutionName, em);
			if (solution == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_NAME, solutionName);
			}
			List<SdpSolution> solutions = new ArrayList<SdpSolution>();
			solutions.add(solution);
			List<SdpSolutionDto> solutionResponse = BeanConverter.convertSolution(solutions);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(solutionResponse);
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
	 * This method allows to retrieve the information related to all the solutions present into CSM database.
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSolution and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllSolutions(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpSolutionHelper handler = SdpSolutionHelper.getInstance();
			handler.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = handler.searchAllSolutionsCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpSolution> solutions = handler.searchAllSolutions(startPosition, maxRecordsNumber, em);
			if (solutions == null || solutions.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSolution(solutions));
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
	 * This method allows to retrieve the information related to a solution given a party group name. NOTE: The input parameter is mandatory
	 * 
	 * @param partyGroupName
	 *            Name of the party group of the solution to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSolution and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSolutionsByPartyGroup(String partyGroupName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PARTY_GROUP_NAME, partyGroupName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpPartyGroupHelper handler = SdpPartyGroupHelper.getInstance();
			SdpSolutionHelper solutionHelper = SdpSolutionHelper.getInstance();
			// formal validation
			handler.validatePartyGroup(partyGroupName);
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpPartyGroup partyGroup = handler.searchPartyGroupByName(partyGroupName, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_GROUP_NAME, partyGroupName);
			}
			Long totalResult = solutionHelper.countSolutionsByPartyGroup(partyGroupName, em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARTY_GROUP_NAME, partyGroupName);
			}

			List<SdpSolution> solutions = solutionHelper.searchSolutionsByPartyGroup(partyGroupName, startPosition, maxRecordsNumber, em);

			if (solutions == null || solutions.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSolution(solutions));
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
	 * This method allows to change the status of a solution.
	 * 
	 * @param solutionId
	 *            Id of the the solution to update
	 * @param status
	 *            value of the status to set on the solution
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse solutionChangeStatus(Long solutionId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_ID, solutionId);
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

			solutionChangeStatus(solutionId, status, Utilities.getCurrentClassAndMethod(), em);

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

	SdpSolution solutionChangeStatus(Long solutionId, String nextStatus, String changedBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionHelper helper = SdpSolutionHelper.getInstance();
		// formal validation
		helper.validateSearchSolutionById(solutionId);
		helper.validateChangeStatus(nextStatus);
		SdpSolution solution = helper.searchSolutionById(solutionId, em);
		if (solution == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, solution.getStatusId(), StatusIdConverter.getStatusValue(nextStatus), em);

		helper.changeStatus(solution, StatusIdConverter.getStatusValue(nextStatus), changedBy);
		return solution;
	}

}
