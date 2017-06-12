package com.accenture.sdp.csm.test.role;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpRoleManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpRoleManagerTest extends BaseTestCase {

	SdpRoleManager manager = SdpRoleManager.getInstance();

	@Test
	public void testCreateRole() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createRole();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createRole() throws PropertyNotFoundException {

		String roleName = Utils.getRandomName(10);
		String roleDescription = Utils.getRandomName(10);

		CreateServiceResponse response = manager.createRole(roleName, roleDescription, getTenantName());
		return response;
	}

	@Test
	public void testModifyRole() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyRole();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyRole() throws PropertyNotFoundException {

		String roleName = Utils.getRandomName(10);
		String roleDescription = Utils.getRandomName(10);

		manager.createRole(roleName, roleDescription, getTenantName());

		DataServiceResponse response = manager.modifyRole(roleName, roleDescription, getTenantName());
		return response;
	}

	@Test
	public void testDeleteRole() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteRole();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteRole() throws PropertyNotFoundException {
		String roleName = Utils.getRandomName(10);
		String roleDescription = Utils.getRandomName(10);

		manager.createRole(roleName, roleDescription, getTenantName());
		DataServiceResponse response = manager.deleteRole(roleName, getTenantName());
		return response;
	}

	@Test
	public void testSearchRole() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchRole();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchRole() throws PropertyNotFoundException {
		String roleName = Utils.getRandomName(10);
		String roleDescription = Utils.getRandomName(10);

		manager.createRole(roleName, roleDescription, getTenantName());

		SearchServiceResponse response = manager.searchRole(roleName, getTenantName());
		return response;
	}

	@Test
	public void testSearchAllRoles() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllRoles();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllRoles() throws PropertyNotFoundException {

		SearchServiceResponse response = manager.searchAllRole(0L, 0L, getTenantName());
		return response;
	}
}
