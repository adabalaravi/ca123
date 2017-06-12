package com.accenture.sdp.csm.metering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.metering.EventType;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public class MeteringManager {

	private static LoggerWrapper log = new LoggerWrapper(MeteringManager.class);
	
	public enum EventTypeEnum {

		DEVICE_REGISTRATION(1), DEVICE_UNREGISTRATION(2), AUTHENTICATE_DEVICE(3), RESET_SAFETY_PERIOD(4), RESET_DEVICE_ASSOCIATIONS(5), ADD_TO_BL(6), DEL_FROM_BL(
				7), ADD_TO_WL(8), DEL_FROM_WL(9);

		private long eventTypeId;

		EventTypeEnum(long eventTypeId) {
			this.eventTypeId = eventTypeId;
		}

		public long getEventTypeId() {
			return eventTypeId;
		}
	}

	// NB: ordinati, altrimenti la search non funziona
	private static final String[] EXCLUDED_CODES = { "010", "020" };

	private EventType lastEvent;
	private List<EventType> events;
	private String tenantName;

	public MeteringManager(String tenantName) {
		this.tenantName = tenantName;
		this.events = new ArrayList<EventType>();
	}

	public EventType getLastEvent() {
		return lastEvent;
	}

	public void startEvent(EventTypeEnum type) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		lastEvent = new EventType();
		lastEvent.setEventTypeId(type.getEventTypeId());
		lastEvent.setTimestamp(new Date());
	}

	public void flush() {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// se l'evento non e' completo lo salta
		if (lastEvent != null && (lastEvent.getDeviceUUID() != null || lastEvent.getPartyId() != null)) {
			events.add(lastEvent);
		}
		lastEvent = null;
	}

	public void rollBack(DataServiceResponse resp) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		events.clear();
		if (resp.getResultCode() != null && Arrays.binarySearch(EXCLUDED_CODES, resp.getResultCode()) < 0) {
			flush();
			commit(resp);
		}
	}

	public void commit(DataServiceResponse resp) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (!events.isEmpty()) {
			for (EventType e : events) {
				e.setResult(resp.getResultCode());
				e.setResultDescription(resp.getDescription());
			}
			MeteringClientFactory.getClient().sendTraceMessage(tenantName, events);
		}
	}
}
