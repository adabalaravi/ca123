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
import com.accenture.sdp.csm.dto.responses.SdpPartyGroupResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpPartyGroupHelper;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpPartyGroupManager extends SdpBaseManager {

	private SdpPartyGroupManager() {
		super();
	}

	private static SdpPartyGroupManager instance;

	public static SdpPartyGroupManager getInstance() {
		if (instance == null) {
			instance = new SdpPartyGroupManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database all information about a party group.
	 * </p>
	 * 
	 * @param partyGroupName
	 *            Name of the new party group to create
	 * @param partyGroupDescription
	 *            Descripion of the new party group to create
	 * @return Returns a CreateServiceResponse containing all the informations related to RefPartyGroup and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createPartyGroup(String partyGroupName, String partyGroupDescription, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_GROUP_NAME, partyGroupName);
		logMap.put("partyGroupDescription", partyGroupDescription);
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

			// creazione party
			SdpPartyGroup newPartyGroup = createPartyGroup(partyGroupName, partyGroupDescription, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newPartyGroup.getPartyGroupId());
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

	SdpPartyGroup createPartyGroup(String partyGroupName, String partyGroupDescription, String createdBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyGroupHelper helper = SdpPartyGroupHelper.getInstance();
		helper.validatePartyGroup(partyGroupName);

		// verifica duplicati
		if (helper.searchPartyGroupByName(partyGroupName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, PARTY_GROUP_NAME, partyGroupName);
		}

		return helper.createPartyGroup(partyGroupName, partyGroupDescription, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a partyGroup (but not the Status) already present into CSM database
	 * </p>
	 * 
	 * @param partyGroupId
	 *            Id of the partyGroup
	 * @param partyGroupName
	 *            Name of the partyGroup
	 * @param partyGroupDescription
	 *            Description of the partyGroup
	 * @return Returns a DataServiceResponse containing all the informations related to the partyGroup and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse modifyPartyGroup(Long partyGroupId, String partyGroupName, String partyGroupDescription, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(PARTY_GROUP_NAME, partyGroupName);
		logMap.put("partyGroupDescription", partyGroupDescription);
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
			// creazione party
			modifyPartyGroup(partyGroupId, partyGroupName, partyGroupDescription, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
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

	SdpPartyGroup modifyPartyGroup(Long partyGroupId, String partyGroupName, String partyGroupDescription, String modifiedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyGroupHelper helper = SdpPartyGroupHelper.getInstance();
		helper.validateSearchPartyGroupById(partyGroupId);
		helper.validatePartyGroup(partyGroupName);
		SdpPartyGroup partyGroupToUpdate = helper.searchPartyGroupById(partyGroupId, em);
		if (partyGroupToUpdate == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_GROUP_ID, partyGroupId);
		}
		// verifica duplicati
		if (!partyGroupToUpdate.getPartyGroupName().equals(partyGroupName)) {
			SdpPartyGroup temp = helper.searchPartyGroupByName(partyGroupName, em);
			if (temp != null && !temp.getPartyGroupId().equals(partyGroupId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, PARTY_GROUP_NAME, partyGroupName);
			}
		}

		helper.modifyPartyGroup(partyGroupToUpdate, partyGroupName, partyGroupDescription, modifiedBy, em);
		return partyGroupToUpdate;
	}

	/**
	 * <p>
	 * This method allows to delete a party group only in logically mode.
	 * </p>
	 * 
	 * @param partyGroupId
	 *            Id of the party group to delete
	 * @return Returns a DataServiceResponse operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deletePartyGroup(Long partyGroupId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_GROUP_ID, partyGroupId);
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

			deletePartyGroup(partyGroupId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(PARTY_GROUP_ID, partyGroupId));
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

	void deletePartyGroup(Long partyGroupId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpPartyGroupHelper helper = SdpPartyGroupHelper.getInstance();
		helper.validateSearchPartyGroupById(partyGroupId);

		SdpPartyGroup partyGroupToDelete = helper.searchPartyGroupById(partyGroupId, em);
		if (partyGroupToDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_GROUP_ID, partyGroupId);
		}
		// verify no solution is linked to partyGroup
		if (partyGroupToDelete.getSdpSolutions() != null && !partyGroupToDelete.getSdpSolutions().isEmpty()) {
			log.logDebug("partyGroup has related solutions");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_GROUP_ID, partyGroupId);
		}
		// AGGIUNTO X AVS
		// verify no solution offer is linked to partyGroup
		if (partyGroupToDelete.getSolutionOffers() != null && !partyGroupToDelete.getSolutionOffers().isEmpty()) {
			log.logDebug("partyGroup has related solutionOffers");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_GROUP_ID, partyGroupId);
		}
		// verify no party is linked to partyGroup
		if (partyGroupToDelete.getSdpParties() != null && !partyGroupToDelete.getSdpParties().isEmpty()) {
			log.logDebug("partyGroup has related parties");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_GROUP_ID, partyGroupId);
		}
		helper.deletePartyGroup(partyGroupToDelete, deletedBy, em);
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a party group.
	 * 
	 * @param partyGroupId
	 *            partyGroup Id related to the partyGroup to search
	 * @return Returns a SearchServiceResponse containing all the informations related to partyGroup and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchPartyGroup(Long partyGroupId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		List<SdpPartyGroupResponseDto> searchResult = null;
		try {
			// Validazione Formale
			SdpPartyGroupHelper subHelp = SdpPartyGroupHelper.getInstance();
			subHelp.validateSearchPartyGroupById(partyGroupId);

			em = PersistenceManager.getEntityManager(tenantName);
			SdpPartyGroup partyGroup = subHelp.searchPartyGroupById(partyGroupId, em);

			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_GROUP_ID, partyGroupId);
			} else {
				SdpPartyGroupResponseDto dto = BeanConverter.convertPartyGroupResponseDto(partyGroup);
				searchResult = new ArrayList<SdpPartyGroupResponseDto>();
				searchResult.add(dto);
			}
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(searchResult);
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
	 * This method allows to return the informations about all party groups into commercial catalogue.
	 * </p>
	 * 
	 * @param startPosition
	 *            index of the first result to return
	 * @param maxRecordsNumber
	 *            number of results to return
	 * @return Returns a SearchServiceResponse containing all the informations related to all partyGroups and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllPartyGroups(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpPartyGroupHelper help = SdpPartyGroupHelper.getInstance();
			help.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = help.searchAllPartyGroupsCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpPartyGroup> partyGroups = help.searchAllPartyGroups(startPosition, maxRecordsNumber, em);

			if (partyGroups == null || partyGroups.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertListOfPartyGroupResponseDto(partyGroups));
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

}
