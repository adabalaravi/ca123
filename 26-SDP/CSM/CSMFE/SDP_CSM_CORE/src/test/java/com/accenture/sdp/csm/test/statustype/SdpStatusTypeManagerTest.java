package com.accenture.sdp.csm.test.statustype;

import org.junit.Test;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.RefStatusTypeManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpStatusTypeManagerTest extends BaseTestCase {

	RefStatusTypeManager manager = RefStatusTypeManager.getInstance();

	@Test
	public void testSearchAllStatus() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllStatus();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAllStatus() throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchAllStatus();

		return resp;

	}
}
