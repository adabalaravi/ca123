package com.accenture.sdp.csm.test.metering;

import java.util.Date;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.accenture.sdp.csm.dto.metering.EventType;
import com.accenture.sdp.csm.dto.metering.SdpDeviceTraceMessage.EventList;

public class MeteringHelperTest extends TestCase {
	
	@Test
	public void testSendTraceMessage() throws Exception {

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
		event2.setPartyId(1L);
		event2.setResult("000");
		event2.setResultDescription("OK");
		event2.setTimestamp(new Date());

		EventList list = new EventList();
		list.getEvent().add(event);
		list.getEvent().add(event2);
		
//		MeteringHelper helper = MeteringHelper.getInstance();
//		helper.sendTraceMessage("tenant1", list.getEvent());
	}
	
//	@Test
//	public void testMoneta(){
//		Random r = new Random();
//		for (int i = 0; i<10; i++)
//		if (r.nextBoolean()){
//			System.out.println("Fabrizio");
//		} else {
//			System.out.println("Elia");
//		}
//	}

}
