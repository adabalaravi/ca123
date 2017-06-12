package com.accenture.sdp.devicemetering;

import java.util.Date;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.accenture.sdp.devicemetering.managers.DeviceMeteringManager;
import com.accenture.sdp.devicemetering.model.EventType;
import com.accenture.sdp.devicemetering.model.SdpDeviceTraceMessage.EventList;

public class SdpDeviceManagerTest extends TestCase {

	protected String tenantName = "tenant1";

	public SdpDeviceManagerTest() {
		super();
	}

	@Test
	public void testInsertDeviceEventsOK(){

		EventType event = new EventType();
		event.setEventTypeId(1L);
		event.setDeviceUUID(UUID.randomUUID().toString());
		event.setPartyId(1L);
		event.setResult("000");
		event.setResultDescription("OK");
		event.setTimestamp(new Date());
		
		EventType event2 = new EventType();
		event2.setEventTypeId(2L);
		event2.setDeviceUUID(UUID.randomUUID().toString());
		event2.setPartyId(null);
		event2.setResult("000");
		event2.setResultDescription("OK");
		event2.setTimestamp(new Date());
		
		EventType event3 = new EventType();
		event3.setEventTypeId(2L);
		event3.setDeviceUUID(null);
		event3.setPartyId(2L);
		event3.setResult("000");
		event3.setResultDescription("OK");
		event3.setTimestamp(new Date());
		
		EventList list = new EventList();
		list.getEvent().add(event);
		list.getEvent().add(event2);
		list.getEvent().add(event3);
		assertTrue(DeviceMeteringManager.getInstance().insertDeviceEvents(tenantName, list.getEvent()));
	}

	@Test
	public void testInsertDeviceEventsDeviceTypeIDKO(){

		EventType event = new EventType();
		event.setEventTypeId(100L);
		event.setDeviceUUID(UUID.randomUUID().toString());
		event.setPartyId(1L);
		event.setResult("000");
		event.setResultDescription("OK");
		event.setTimestamp(new Date());
		EventList list = new EventList();
		list.getEvent().add(event);
		assertFalse(DeviceMeteringManager.getInstance().insertDeviceEvents(tenantName, list.getEvent()));
	}
	
	@Test
	public void testInsertDeviceEventsPartyIdNullAndDeviceUUIDNullKO(){

		EventType event = new EventType();
		event.setEventTypeId(1L);
		event.setDeviceUUID(null);
		event.setPartyId(null);
		event.setResult("000");
		event.setResultDescription("OK");
		event.setTimestamp(new Date());
		EventList list = new EventList();
		list.getEvent().add(event);
		assertFalse(DeviceMeteringManager.getInstance().insertDeviceEvents(tenantName, list.getEvent()));
	}
	
	@Test
	public void testInsertDeviceEventsResultNullKO(){

		EventType event = new EventType();
		event.setEventTypeId(1L);
		event.setDeviceUUID(null);
		event.setPartyId(1L);
		event.setResult(null);
		event.setResultDescription("OK");
		event.setTimestamp(new Date());
		EventList list = new EventList();
		list.getEvent().add(event);
		assertFalse(DeviceMeteringManager.getInstance().insertDeviceEvents(tenantName, list.getEvent()));
	}

}
