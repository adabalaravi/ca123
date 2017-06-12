package com.accenture.sdp.csm.test.servicetype;

import org.junit.Test;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.RefServiceTypeManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpServiceTypeManagerTest extends BaseTestCase {

	RefServiceTypeManager manager = RefServiceTypeManager.getInstance();

	@Test
	public void testSearchAllServiceTypes() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllServiceTypes();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAllServiceTypes() throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchAllServiceTypes(getTenantName());

		return resp;

	}

}
