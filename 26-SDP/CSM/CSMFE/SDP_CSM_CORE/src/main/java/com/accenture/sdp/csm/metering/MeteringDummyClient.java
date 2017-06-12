package com.accenture.sdp.csm.metering;

import java.util.List;

import com.accenture.sdp.csm.dto.metering.EventType;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public final class MeteringDummyClient implements MeteringClient {

	private static LoggerWrapper log = new LoggerWrapper(MeteringDummyClient.class);

	public void init() {
		log.logDebug("metering is disabled");
	}

	public void sendTraceMessage(String tenant, List<EventType> eventList) {
		log.logDebug("metering is disabled");
	}
}
