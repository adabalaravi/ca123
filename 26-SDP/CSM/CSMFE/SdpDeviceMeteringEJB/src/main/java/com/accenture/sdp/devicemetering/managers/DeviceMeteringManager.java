package com.accenture.sdp.devicemetering.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.accenture.sdp.devicemetering.database.PersistenceManager;
import com.accenture.sdp.devicemetering.exceptions.PropertyNotFoundException;
import com.accenture.sdp.devicemetering.exceptions.ValidationException;
import com.accenture.sdp.devicemetering.model.EventType;
import com.accenture.sdp.devicemetering.model.RefEventType;
import com.accenture.sdp.devicemetering.utilities.logging.LoggerWrapper;

public class DeviceMeteringManager {

	private LoggerWrapper log;

	private DeviceMeteringManager() {
		super();
		log = new LoggerWrapper(DeviceMeteringManager.class);
	}

	private static DeviceMeteringManager instance;

	public static DeviceMeteringManager getInstance() {
		if (instance == null) {
			instance = new DeviceMeteringManager();
		}
		return instance;
	}

	public boolean insertDeviceEvents(String tenantName, List<EventType> eventList) {
		EntityManager em = null;
		EntityTransaction tx = null;
		try {

			if (eventList != null) {
				em = PersistenceManager.getMeteringEntityManager(tenantName);
				tx = em.getTransaction();
				tx.begin();
				for (EventType event : eventList) {
					validateEvent(event);
					if (em.find(RefEventType.class, event.getEventTypeId()) == null) {
						throw new ValidationException("EventTypeId not found in database");
					}
					em.persist(event);
				}
				tx.commit();
			}
		} catch (ValidationException e) {
			log.logError(e);
			return false;
		} catch (PropertyNotFoundException e) {
			log.logError(e);
			return false;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return true;
	}

	public void validateEvent(EventType event) throws ValidationException {
		if (event == null) {
			throw new ValidationException("Event is null");
		} else if (event.getPartyId() == null && event.getDeviceUUID() == null) {
			throw new ValidationException("PartyId and  deviceUUID are null");
		} else if (event.getResult() == null) {
			throw new ValidationException("result is null");
		} else if (event.getResultDescription() == null) {
			throw new ValidationException("resultDescription is null");
		} else if (event.getTimestamp() == null) {
			throw new ValidationException("timeStamp is null");
		}
	}

}
