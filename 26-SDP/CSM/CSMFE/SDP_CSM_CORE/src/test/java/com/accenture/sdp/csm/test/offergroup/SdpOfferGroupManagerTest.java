package com.accenture.sdp.csm.test.offergroup;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpOfferGroupManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.solutionoffer.SdpSolutionOfferManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpOfferGroupManagerTest extends BaseTestCase {

	SdpOfferGroupManager manager = SdpOfferGroupManager.getInstance();

	@Test
	public void testCreateOfferGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createOfferGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createOfferGroup() throws PropertyNotFoundException {

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		CreateServiceResponse createServiceResponse = solutionOfferManagerTest.createSolutionOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long solutionOfferId = createServiceResponse.getEntityId();

		String groupName = Utils.getRandomName(10);

		CreateServiceResponse response = manager.createOfferGroup(groupName, solutionOfferId, getTenantName());
		return response;
	}

	@Test
	public void testModifyOfferGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyOfferGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyOfferGroup() throws PropertyNotFoundException {

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		CreateServiceResponse createServiceResponse = solutionOfferManagerTest.createSolutionOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long solutionOfferId = createServiceResponse.getEntityId();

		String groupName = Utils.getRandomName(10);

		createServiceResponse = manager.createOfferGroup(groupName, solutionOfferId, getTenantName());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifyOfferGroup(createServiceResponse.getEntityId(), groupName, solutionOfferId, getTenantName());
		return response;
	}

	@Test
	public void testDeleteOfferGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteOfferGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteOfferGroup() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createOfferGroup();

		DataServiceResponse response = manager.deleteOfferGroup(createServiceResponse.getEntityId(), getTenantName());
		return response;
	}

	@Test
	public void testSearchOfferGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchOfferGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchOfferGroup() throws PropertyNotFoundException {
		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		CreateServiceResponse createServiceResponse = solutionOfferManagerTest.createSolutionOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long solutionOfferId = createServiceResponse.getEntityId();

		String groupName = Utils.getRandomName(10);

		createServiceResponse = manager.createOfferGroup(groupName, solutionOfferId, getTenantName());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		SearchServiceResponse response = manager.searchOfferGroup(groupName, solutionOfferId, getTenantName());
		return response;
	}

}
