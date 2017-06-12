package com.accenture.sdp.csm.test.servicevariant;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpServiceTemplateDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpServiceVariantManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.servicetemplate.SdpServiceTemplateManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpServiceVariantManagerTest extends BaseTestCase {

	SdpServiceVariantManager manager = SdpServiceVariantManager.getInstance();

	@Test
	public void testCreateServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createServiceVariant() throws PropertyNotFoundException {

		SdpServiceTemplateManagerTest sdpServiceTemplateManagerTest = new SdpServiceTemplateManagerTest();
		CreateServiceResponse createServiceResponse = sdpServiceTemplateManagerTest.createServiceTemplate();
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		String serviceVariantName = Utils.getRandomName(10);
		String serviceVariantDescription = Utils.getRandomName(10);
		String externalId = null;
		String serviceVariantProfile = null;

		CreateServiceResponse response = manager.createServiceVariant(serviceVariantName, serviceVariantDescription, serviceVariantProfile,
				createServiceResponse.getEntityId(), externalId, getTenantName());
		return response;

	}

	@Test
	public void testModifyServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyServiceVariant() throws PropertyNotFoundException {

		SdpServiceTemplateManagerTest sdpServiceTemplateManagerTest = new SdpServiceTemplateManagerTest();
		CreateServiceResponse createServiceResponse = sdpServiceTemplateManagerTest.createServiceTemplate();

		Long serviceTemplateId = createServiceResponse.getEntityId();

		String serviceVariantName = Utils.getRandomName(10);
		String serviceVariantDescription = Utils.getRandomName(10);
		String externalId = null;
		String serviceVariantProfile = null;

		createServiceResponse = manager.createServiceVariant(serviceVariantName, serviceVariantDescription, serviceVariantProfile, serviceTemplateId,
				externalId, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifyServiceVariant(createServiceResponse.getEntityId(), serviceVariantName, serviceVariantDescription,
				externalId, serviceTemplateId, serviceVariantProfile, getTenantName());

		return response;
	}

	@Test
	public void testDeleteServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteServiceVariant() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createServiceVariant();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long serviceVariantId = createServiceResponse.getEntityId();

		manager.serviceVariantChangeStatus(serviceVariantId, "Suspended", getTenantName());
		manager.serviceVariantChangeStatus(serviceVariantId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deleteServiceVariant(serviceVariantId, getTenantName());

		return response2;
	}

	@Test
	public void testSearchServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceVariant() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createServiceVariant();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long serviceVariantId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchServiceVariant(serviceVariantId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchServiceVariant(Long serviceVariantId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchServiceVariant(serviceVariantId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchAllServiceVariants() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllServiceVariants();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllServiceVariants() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchAllServiceVariants(0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchServiceVariantByOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceVariantByOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceVariantByOffer() throws PropertyNotFoundException {

		String offerName = "offer1";

		SearchServiceResponse searchServiceResponse = manager.searchServiceVariantByOffer(offerName, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchServiceVariantByServiceTemplate() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceVariantByServiceTemplate();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceVariantByServiceTemplate() throws PropertyNotFoundException {

		SdpServiceTemplateManagerTest sdpServiceTemplateManagerTest = new SdpServiceTemplateManagerTest();
		CreateServiceResponse createServiceResponse = sdpServiceTemplateManagerTest.createServiceTemplate();

		Long serviceTemplateId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = sdpServiceTemplateManagerTest.searchServiceTemplate(serviceTemplateId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpServiceTemplateDto serviceTemplateDto = (SdpServiceTemplateDto) searchServiceResponse.getSearchResult().get(0);

		String serviceVariantName = Utils.getRandomName(10);
		String serviceVariantDescription = Utils.getRandomName(10);
		String externalId = null;
		String serviceVariantProfile = null;

		createServiceResponse = manager.createServiceVariant(serviceVariantName, serviceVariantDescription, serviceVariantProfile, serviceTemplateId,
				externalId, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		searchServiceResponse = manager.searchServiceVariantsByServiceTemplate(serviceTemplateDto.getServiceTemplateName(), 0L, 0L, getTenantName());

		return searchServiceResponse;
	}
}
