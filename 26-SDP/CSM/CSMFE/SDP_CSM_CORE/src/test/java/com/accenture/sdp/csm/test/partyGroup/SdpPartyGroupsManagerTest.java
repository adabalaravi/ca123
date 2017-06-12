package com.accenture.sdp.csm.test.partyGroup;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpPartyGroupManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpPartyGroupsManagerTest extends BaseTestCase {

	SdpPartyGroupManager manager = SdpPartyGroupManager.getInstance();

	@Test
	public void testCreatePartyGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createPartyGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createPartyGroup() throws PropertyNotFoundException {

		String partyGroupName = Utils.getRandomName(10);
		String partyGroupDescription = Utils.getRandomName(10);

		CreateServiceResponse response = manager.createPartyGroup(partyGroupName, partyGroupDescription, getTenantName());
		return response;
	}

	@Test
	public void testModifyPartyGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyPartyGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyPartyGroup() throws PropertyNotFoundException {
		CreateServiceResponse CreateServiceResponse = createPartyGroup();

		String partyGroupName = Utils.getRandomName(10);
		String partyGroupDescription = Utils.getRandomName(10);

		DataServiceResponse response = manager.modifyPartyGroup(CreateServiceResponse.getEntityId(), partyGroupName, partyGroupDescription, getTenantName());
		return response;
	}

	@Test
	public void testDeletePartyGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deletePartyGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deletePartyGroup() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createPartyGroup();

		DataServiceResponse response = manager.deletePartyGroup(createServiceResponse.getEntityId(), getTenantName());
		return response;
	}

	@Test
	public void testSearchPartyGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPartyGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPartyGroup() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createPartyGroup();

		SearchServiceResponse response = manager.searchPartyGroup(createServiceResponse.getEntityId(), getTenantName());
		return response;
	}

	public SearchServiceResponse searchPartyGroup(Long partyGroupId) throws PropertyNotFoundException {
		SearchServiceResponse response = manager.searchPartyGroup(partyGroupId, getTenantName());
		return response;
	}

	@Test
	public void testSearchAllPartyGroups() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllPartyGroups();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllPartyGroups() throws PropertyNotFoundException {

		SearchServiceResponse response = manager.searchAllPartyGroups(0L, 0L, getTenantName());
		return response;
	}
}
