package com.accenture.sdp.csm.metering;

import java.util.List;

import com.accenture.sdp.csm.dto.metering.EventType;
import com.accenture.sdp.csm.exceptions.InitException;

public interface MeteringClient {

	void sendTraceMessage(String tenant, List<EventType> eventList);

	void init() throws InitException;

}
