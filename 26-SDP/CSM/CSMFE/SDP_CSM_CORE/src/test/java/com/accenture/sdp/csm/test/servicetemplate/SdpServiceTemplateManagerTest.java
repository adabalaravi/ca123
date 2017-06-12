package com.accenture.sdp.csm.test.servicetemplate;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpPlatformDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpServiceTemplateManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.platform.SdpPlatformManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpServiceTemplateManagerTest extends BaseTestCase {

	SdpServiceTemplateManager manager = SdpServiceTemplateManager.getInstance();

	@Test
	public void testCreateServiceTemplate() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createServiceTemplate();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createServiceTemplate() throws PropertyNotFoundException {

		SdpPlatformManagerTest platformManagerTest = new SdpPlatformManagerTest();
		CreateServiceResponse createServiceResponse = platformManagerTest.createPlatform();

		String serviceTemplateName = Utils.getRandomName(10);
		String serviceTemplateDescription = Utils.getRandomName(10);
		String externalId = null;
		String serviceTemplateProfile = null;

		CreateServiceResponse response = manager.createServiceTemplate(serviceTemplateName, serviceTemplateDescription, serviceTemplateProfile,
				createServiceResponse.getEntityId(), externalId, 1L, getTenantName());
		return response;
	}

	@Test
	public void testModifyServiceTemplate() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyServiceTemplate();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyServiceTemplate() throws PropertyNotFoundException {

		SdpPlatformManagerTest platformManagerTest = new SdpPlatformManagerTest();
		CreateServiceResponse createServiceResponse = platformManagerTest.createPlatform();

		Long platformId = createServiceResponse.getEntityId();

		String serviceTemplateName = Utils.getRandomName(10);
		String serviceTemplateDescription = Utils.getRandomName(10);
		String externalId = null;
		String serviceTemplateProfile = null;

		createServiceResponse = manager.createServiceTemplate(serviceTemplateName, serviceTemplateDescription, serviceTemplateProfile, platformId,
				externalId, 1L, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifyServiceTemplate(createServiceResponse.getEntityId(), serviceTemplateName, serviceTemplateDescription,
				externalId, serviceTemplateProfile, 1L, platformId, getTenantName());

		return response;
	}

	@Test
	public void testDeleteServiceTemplate() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteServiceTemplate();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteServiceTemplate() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createServiceTemplate();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long serviceTemplateId = createServiceResponse.getEntityId();

		manager.serviceTemplateChangeStatus(serviceTemplateId, "Suspended", getTenantName());
		manager.serviceTemplateChangeStatus(serviceTemplateId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deleteServiceTemplate(serviceTemplateId, getTenantName());

		return response2;
	}

	@Test
	public void testSearchServiceTemplate() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceTemplate();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceTemplate() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createServiceTemplate();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long ServiceTemplateId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchServiceTemplate(ServiceTemplateId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchServiceTemplate(Long ServiceTemplateId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchServiceTemplate(ServiceTemplateId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchAllServiceTemplates() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllServiceTemplates();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllServiceTemplates() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchAllServiceTemplates(0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchServiceTemplateByServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceTemplateByServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceTemplateByServiceVariant() throws PropertyNotFoundException {

		String serviceVariantName = "serviceVariant1";

		SearchServiceResponse searchServiceResponse = manager.searchServiceTemplateByServiceVariant(serviceVariantName, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchServiceTemplateByPlatform() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceTemplateByPlatform();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceTemplateByPlatform() throws PropertyNotFoundException {

		SdpPlatformManagerTest platformManagerTest = new SdpPlatformManagerTest();
		CreateServiceResponse createServiceResponse = platformManagerTest.createPlatform();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SearchServiceResponse searchServiceResponse = platformManagerTest.searchPlatform(createServiceResponse.getEntityId());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPlatformDto platformDto = (SdpPlatformDto) searchServiceResponse.getSearchResult().get(0);

		String platformName = platformDto.getPlatformName();

		String serviceTemplateName = Utils.getRandomName(10);
		String serviceTemplateDescription = Utils.getRandomName(10);
		String externalId = null;
		String serviceTemplateProfile = null;

		manager.createServiceTemplate(serviceTemplateName, serviceTemplateDescription, serviceTemplateProfile, createServiceResponse.getEntityId(),
				externalId, 1L, getTenantName());

		searchServiceResponse = manager.searchServiceTemplatesByPlatform(platformName, 0L, 0L, getTenantName());

		return searchServiceResponse;
	}

}
