package com.accenture.sdp.csm.managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpDevicePolicyHelper;
import com.accenture.sdp.csm.helpers.SdpPartyDeviceHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.metering.MeteringManager;
import com.accenture.sdp.csm.metering.MeteringManager.EventTypeEnum;
import com.accenture.sdp.csm.model.jpa.SdpDevice;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicy;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpPartyDeviceManager extends SdpBaseManager {

	private SdpPartyDeviceManager() {
		super();
	}

	private static SdpPartyDeviceManager instance;

	public static SdpPartyDeviceManager getInstance() {
		if (instance == null) {
			instance = new SdpPartyDeviceManager();
		}
		return instance;
	}

	SdpPartyDeviceExt createPartyDeviceExt(Long partyId, String createdById, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// validazione
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		partyHelper.validateSearchPartyById(partyId);

		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}

		// look for default policy
		SdpDevicePolicy policy = SdpDevicePolicyHelper.getInstance().searchDefaultPolicy(em);

		return SdpPartyDeviceHelper.getInstance().createPartyDeviceExt(party, policy, createdById, em);
	}

	public DataServiceResponse resetDeviceSafetyPeriod(Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		MeteringManager mm = new MeteringManager(tenantName);
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// let's do the magic
			resetDeviceSafetyPeriod(partyId, Utilities.getCurrentClassAndMethod(), em, mm);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			mm.commit(resp);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
			mm.rollBack(resp);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
			mm.rollBack(resp);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
			mm.rollBack(resp);
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

	void resetDeviceSafetyPeriod(Long partyId, String doneBy, EntityManager em, MeteringManager mm) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper helper = SdpPartyHelper.getInstance();
		// validazione
		helper.validateSearchPartyById(partyId);
		
		// START METERING EVENT dopo validazione, che e' esclusa
		mm.startEvent(EventTypeEnum.RESET_SAFETY_PERIOD);
		mm.getLastEvent().setPartyId(partyId);

		SdpParty party = helper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		if (party.getSdpPartyDeviceExt() == null) {
			// bonifica
			SdpPartyDeviceManager.getInstance().createPartyDeviceExt(partyId, doneBy, em);
		}

		SdpPartyDeviceHelper.getInstance().resetSafetyPeriod(party.getSdpPartyDeviceExt(), doneBy);
		mm.flush();
	}

	public DataServiceResponse resetDeviceAssociations(Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		MeteringManager mm = new MeteringManager(tenantName);
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// let's do the magic
			resetDeviceAssociations(partyId, Utilities.getCurrentClassAndMethod(), em, mm);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			mm.commit(resp);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
			// sovrascrive last event prima del rollback
			mm.startEvent(EventTypeEnum.RESET_DEVICE_ASSOCIATIONS);
			mm.getLastEvent().setPartyId(partyId);
			mm.rollBack(resp);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
			// sovrascrive last event prima del rollback
			mm.startEvent(EventTypeEnum.RESET_DEVICE_ASSOCIATIONS);
			mm.getLastEvent().setPartyId(partyId);
			mm.rollBack(resp);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
			// sovrascrive last event prima del rollback
			mm.startEvent(EventTypeEnum.RESET_DEVICE_ASSOCIATIONS);
			mm.getLastEvent().setPartyId(partyId);
			mm.rollBack(resp);
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

	void resetDeviceAssociations(Long partyId, String doneBy, EntityManager em, MeteringManager mm) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// chiamo subito il reset, che fa anche la validazione
		resetDeviceSafetyPeriod(partyId, doneBy, em, mm);

		SdpParty party = SdpPartyHelper.getInstance().searchPartyById(partyId, em);

		for (SdpDevice device : party.getSdpPartyDeviceExt().getSdpDevices()) {
			// rimuovo quelli attivi
			if (isStatusActive(device.getStatusId())) {
				SdpDeviceManager.getInstance().unregisterDevice(device.getDeviceUuid(), doneBy, em, mm);
			}
		}
		mm.startEvent(EventTypeEnum.RESET_DEVICE_ASSOCIATIONS);
		mm.getLastEvent().setPartyId(partyId);
		mm.flush();
	}

	public ComplexServiceResponse searchDeviceCountersByPartyId(Long partyId, String tenantName) throws PropertyNotFoundException {
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

			if (party.getSdpPartyDeviceExt() == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertPartyDeviceExt(party.getSdpPartyDeviceExt()));
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
