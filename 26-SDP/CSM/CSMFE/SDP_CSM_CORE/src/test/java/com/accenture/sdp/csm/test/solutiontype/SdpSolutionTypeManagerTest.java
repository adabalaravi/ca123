package com.accenture.sdp.csm.test.solutiontype;

import org.junit.Test;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.RefSolutionTypeManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpSolutionTypeManagerTest extends BaseTestCase {

	RefSolutionTypeManager manager = RefSolutionTypeManager.getInstance();

	@Test
	public void testSearchAllSolutionTypes() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllSolutionTypes();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAllSolutionTypes() throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchAllSolutionTypes(getTenantName());

		return resp;

	}

}
