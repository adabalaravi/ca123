package com.accenture.sdp.csm.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDevicePolicyConfigRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartyDeviceExtRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpDeviceHelper;
import com.accenture.sdp.csm.helpers.SdpDevicePolicyHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicy;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpDevicePolicyManager extends SdpBaseManager {

	private SdpDevicePolicyManager() {
		super();
	}

	private static SdpDevicePolicyManager instance;

	public static SdpDevicePolicyManager getInstance() {
		if (instance == null) {
			instance = new SdpDevicePolicyManager();
		}
		return instance;
	}

	public CreateServiceResponse createDevicePolicy(String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration,
			List<SdpDevicePolicyConfigRequestDto> maximumAllowedDevices, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(POLICY_NAME, policyName);
		logMap.put("maxAssociationsNumber", maxAssociationsNumber);
		logMap.put("safetyPeriodDuration", safetyPeriodDuration);
		logMap.put("maximumAllowedDevice", maximumAllowedDevices);
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

			// creazione policy
			SdpDevicePolicy policy = createDevicePolicy(policyName, maxAssociationsNumber, safetyPeriodDuration, maximumAllowedDevices,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(policy.getPolicyId());
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

	SdpDevicePolicy createDevicePolicy(String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration,
			List<SdpDevicePolicyConfigRequestDto> maximumAllowedDevices, String createdBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDevicePolicyHelper helper = SdpDevicePolicyHelper.getInstance();
		SdpDeviceHelper deviceHelper = SdpDeviceHelper.getInstance();
		helper.validateDevicePolicy(policyName, maxAssociationsNumber, safetyPeriodDuration);
		helper.validateDevicePolicyConfigs(maximumAllowedDevices);

		// verifica duplicati
		if (helper.searchDevicePolicyByName(policyName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, POLICY_NAME, policyName);
		}

		// lista d'appoggio
		List<RefDeviceChannel> channels = SdpDeviceHelper.getInstance().searchAllDeviceChannels(em);

		SdpDevicePolicy policy = helper.createDevicePolicy(policyName, maxAssociationsNumber, safetyPeriodDuration, createdBy, em);
		for (SdpDevicePolicyConfigRequestDto dto : maximumAllowedDevices) {
			RefDeviceChannel channel = deviceHelper.searchDeviceChannelByName(dto.getDeviceChannel(), em);
			if (channel == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "deviceChannel", dto.getDeviceChannel());
			}
			helper.createDevicePolicyConfig(policy, channel, dto.getMaximumNumber(), em);
			channels.remove(channel);
		}

		// metto a zero tutto il resto
		for (RefDeviceChannel channel : channels) {
			helper.createDevicePolicyConfig(policy, channel, 0L, em);
		}
		return policy;
	}

	public DataServiceResponse updateDevicePolicy(Long policyId, String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration,
			List<SdpDevicePolicyConfigRequestDto> maximumAllowedDevices, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(POLICY_ID, policyId);
		logMap.put(POLICY_NAME, policyName);
		logMap.put("maxAssociationsNumber", maxAssociationsNumber);
		logMap.put("safetyPeriodDuration", safetyPeriodDuration);
		logMap.put("maximumAllowedDevice", maximumAllowedDevices);
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

			// update
			updateDevicePolicy(policyId, policyName, maxAssociationsNumber, safetyPeriodDuration, maximumAllowedDevices, Utilities.getCurrentClassAndMethod(),
					em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
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

	SdpDevicePolicy updateDevicePolicy(Long policyId, String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration,
			List<SdpDevicePolicyConfigRequestDto> maximumAllowedDevices, String doneBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDevicePolicyHelper helper = SdpDevicePolicyHelper.getInstance();
		SdpDeviceHelper deviceHelper = SdpDeviceHelper.getInstance();
		helper.validateSearchDevicePolicyById(policyId);
		helper.validateDevicePolicy(policyName, maxAssociationsNumber, safetyPeriodDuration);
		// validazione solo se presente - fa diff
		if (maximumAllowedDevices != null && !maximumAllowedDevices.isEmpty()) {
			helper.validateDevicePolicyConfigs(maximumAllowedDevices);
		}

		SdpDevicePolicy policy = helper.searchDevicePolicyById(policyId, em);
		if (policy == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, POLICY_ID, policyId);
		}

		// verifica duplicati
		if (!policy.getPolicyName().equals(policyName) && helper.searchDevicePolicyByName(policyName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, POLICY_NAME, policyName);
		}
		// update policy
		helper.modifyDevicePolicy(policy, policyName, maxAssociationsNumber, safetyPeriodDuration, doneBy, em);

		// update configs - solo quelle inviate
		if (maximumAllowedDevices != null) {
			for (SdpDevicePolicyConfigRequestDto dto : maximumAllowedDevices) {
				RefDeviceChannel channel = deviceHelper.searchDeviceChannelByName(dto.getDeviceChannel(), em);
				if (channel == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "deviceChannel", dto.getDeviceChannel());
				}
				// update massivo: li cerco dalla lista anziche' fare puntuale
				if (!helper.updateDevicePolicyConfig(policy, channel, dto.getMaximumNumber())) {
					helper.createDevicePolicyConfig(policy, channel, dto.getMaximumNumber(), em);
				}
			}
		}

		return policy;
	}

	public DataServiceResponse deleteDevicePolicy(Long policyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(POLICY_ID, policyId);
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

			deleteDevicePolicy(policyId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED);
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

	void deleteDevicePolicy(Long policyId, String deletedBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDevicePolicyHelper helper = SdpDevicePolicyHelper.getInstance();
		helper.validateSearchDevicePolicyById(policyId);

		SdpDevicePolicy policy = helper.searchDevicePolicyById(policyId, em);
		if (policy == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, POLICY_ID, policyId);
		}

		if (BooleanConverter.getBooleanValue(policy.getIsDefault())) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, POLICY_ID, policyId);
		}

		// look for default policy as replacement
		SdpDevicePolicy replacement = helper.searchDefaultPolicy(em);

		helper.deleteDevicePolicy(policy, replacement, em);
	}

	public DataServiceResponse setPartyDevicePolicy(List<SdpPartyDeviceExtRequestDto> policies, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("policy", policies);
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

			setPartyDevicePolicy(policies, Utilities.getCurrentClassAndMethod(), em);

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

	void setPartyDevicePolicy(List<SdpPartyDeviceExtRequestDto> policies, String updatedBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (policies == null || policies.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "policies");
		}
		for (SdpPartyDeviceExtRequestDto dto : policies) {
			setPartyDevicePolicy(dto.getPartyId(), dto.getPolicyId(), updatedBy, em);
		}
	}

	void setPartyDevicePolicy(Long partyId, Long policyId, String updatedBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpDevicePolicyHelper policyHelper = SdpDevicePolicyHelper.getInstance();
		partyHelper.validateSearchPartyById(partyId);
		policyHelper.validateSearchDevicePolicyById(policyId);

		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		// bonifica
		if (party.getSdpPartyDeviceExt() == null) {
			SdpPartyDeviceManager.getInstance().createPartyDeviceExt(partyId, updatedBy, em);
		}

		SdpDevicePolicy policy = policyHelper.searchDevicePolicyById(policyId, em);
		if (policy == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, POLICY_ID, policyId);
		}

		policyHelper.setPartyDevicePolicy(party, policy, updatedBy);
	}

	public ComplexServiceResponse searchDevicePolicyByPartyId(Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpPartyHelper helper = SdpPartyHelper.getInstance();
			helper.validateSearchPartyById(partyId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpParty party = helper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}

			SdpDevicePolicy policy = party.getSdpPartyDeviceExt() == null ? null : party.getSdpPartyDeviceExt().getSdpDevicePolicy();
			if (policy == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			
			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertDevicePolicy(policy));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildComplexResponse(e.getDescription(), e.getParameters());
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

	public SearchServiceResponse searchAllDevicePolicies(String tenantName) throws PropertyNotFoundException {
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

			List<SdpDevicePolicy> policies = SdpDevicePolicyHelper.getInstance().searchAllDevicePolicies(em);

			if (policies == null || policies.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertDevicePolicies(policies));
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

	public ComplexServiceResponse searchDevicePolicyById(Long policyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(POLICY_ID, policyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpDevicePolicyHelper helper = SdpDevicePolicyHelper.getInstance();
			helper.validateSearchDevicePolicyById(policyId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpDevicePolicy policy = helper.searchDevicePolicyById(policyId, em);
			if (policy == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, POLICY_ID, policyId);
			}

			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertDevicePolicy(policy));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildComplexResponse(e.getDescription(), e.getParameters());
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
}
