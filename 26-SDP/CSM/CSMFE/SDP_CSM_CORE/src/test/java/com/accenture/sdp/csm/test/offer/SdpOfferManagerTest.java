package com.accenture.sdp.csm.test.offer;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpOfferDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpOfferManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.servicevariant.SdpServiceVariantManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpOfferManagerTest extends BaseTestCase {

	SdpOfferManager manager = SdpOfferManager.getInstance();

	@Test
	public void testCreateOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createOffer() throws PropertyNotFoundException {

		SdpServiceVariantManagerTest sdpServiceVariantManagerTest = new SdpServiceVariantManagerTest();
		CreateServiceResponse createServiceResponse = sdpServiceVariantManagerTest.createServiceVariant();
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long serviceVariantId = createServiceResponse.getEntityId();

		String offerName = Utils.getRandomName(10);
		String offerDescription = Utils.getRandomName(10);
		String externalId = null;
		String offerProfile = null;

		CreateServiceResponse response = manager.createOffer(offerName, offerDescription, serviceVariantId, externalId, offerProfile, getTenantName());
		return response;

	}

	@Test
	public void testModifyOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyOffer() throws PropertyNotFoundException {

		SdpServiceVariantManagerTest sdpServiceVariantManagerTest = new SdpServiceVariantManagerTest();
		CreateServiceResponse createServiceResponse = sdpServiceVariantManagerTest.createServiceVariant();
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long serviceVariantId = createServiceResponse.getEntityId();

		String offerName = Utils.getRandomName(10);
		String offerDescription = Utils.getRandomName(10);
		String externalId = null;
		String offerProfile = null;

		createServiceResponse = manager.createOffer(offerName, offerDescription, serviceVariantId, externalId, offerProfile, getTenantName());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long offerId = createServiceResponse.getEntityId();

		DataServiceResponse response = manager.modifyOffer(offerId, offerName, offerDescription, serviceVariantId, externalId, offerProfile, getTenantName());

		return response;
	}

	@Test
	public void testDeleteOfferAndPackages() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteOfferAndPackages();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteOfferAndPackages() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long offerId = createServiceResponse.getEntityId();

		manager.offerChangeStatus(offerId, "Suspended", getTenantName());
		manager.offerChangeStatus(offerId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deleteOfferAndPackages(offerId, getTenantName());

		return response2;
	}

	@Test
	public void testOfferAndPackagesChangeStatus() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = offerAndPackagesChangeStatus();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse offerAndPackagesChangeStatus() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long offerId = createServiceResponse.getEntityId();

		DataServiceResponse response = manager.offerAndPackagesChangeStatus(offerId, "Suspended", getTenantName());

		return response;
	}

	@Test
	public void testSearchOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchOffer() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long offerId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchOffer(offerId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchOffer(Long offerId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchOffer(offerId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchAllOffers() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllOffers();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllOffers() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchAllOffers(0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchOfferByServiceVariant() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchOfferByServiceVariant();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchOfferByServiceVariant() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// Search offer

		SearchServiceResponse searchServiceResponse = searchOffer(createServiceResponse.getEntityId());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpOfferDto offerDto = (SdpOfferDto) searchServiceResponse.getSearchResult().get(0);

		searchServiceResponse = manager.searchOffersByServiceVariant(offerDto.getServiceVariantName(), 0L, 0L, getTenantName());

		return searchServiceResponse;
	}
}
