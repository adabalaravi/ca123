package com.accenture.sdp.csm.test.platform;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpPlatformManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpPlatformManagerTest extends BaseTestCase {

	SdpPlatformManager manager = SdpPlatformManager.getInstance();

	@Test
	public void testCreatePlatform() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createPlatform();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createPlatform() throws PropertyNotFoundException {

		String platformName = Utils.getRandomName(10);
		String platformDescription = Utils.getRandomName(10);
		String externalId = null;
		String platformProfile = null;

		CreateServiceResponse response = manager.createPlatform(platformName, platformDescription, externalId, platformProfile, getTenantName());
		return response;
	}

	@Test
	public void testModifyPlatform() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyPlatform();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyPlatform() throws PropertyNotFoundException {

		String platformName = Utils.getRandomName(10);
		String platformDescription = Utils.getRandomName(10);
		String externalId = null;
		String platformProfile = null;

		CreateServiceResponse createServiceResponse = manager.createPlatform(platformName, platformDescription, externalId, platformProfile, getTenantName());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifyPlatform(createServiceResponse.getEntityId(), platformName, platformDescription, externalId,
				platformProfile, getTenantName());

		return response;
	}

	@Test
	public void testDeletePlatform() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deletePlatform();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deletePlatform() throws PropertyNotFoundException {

		String platformName = Utils.getRandomName(10);
		String platformDescription = Utils.getRandomName(10);
		String externalId = null;
		String platformProfile = null;

		CreateServiceResponse createServiceResponse = manager.createPlatform(platformName, platformDescription, externalId, platformProfile, getTenantName());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long platformId = createServiceResponse.getEntityId();

		manager.platformChangeStatus(platformId, "Suspended", getTenantName());
		manager.platformChangeStatus(platformId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deletePlatform(platformId, getTenantName());

		return response2;
	}

	@Test
	public void testSearchPlatform() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPlatform();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPlatform() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createPlatform();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long platformId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchPlatform(platformId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchPlatform(Long platformId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchPlatform(platformId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchAllPlatforms() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllPlatforms();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllPlatforms() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchAllPlatforms(0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchPlatformByServiceTemplate() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPlatformByServiceTemplate();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPlatformByServiceTemplate() throws PropertyNotFoundException {

		String serviceTemplateName = "serviceTemaplate1";

		SearchServiceResponse searchServiceResponse = manager.searchPlatformByServiceTemplate(serviceTemplateName, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchPlatformByServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPlatformByServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPlatformByServiceVariant() throws PropertyNotFoundException {
		String serviceVariantName = "serviceVariant1";

		SearchServiceResponse searchServiceResponse = manager.searchPlatformByServiceVariant(serviceVariantName, getTenantName());

		return searchServiceResponse;
	}
}
