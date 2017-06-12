package com.accenture.sdp.csm.test.sdppackage;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpOfferDto;
import com.accenture.sdp.csm.dto.responses.SdpPackageDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpPackageManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.offer.SdpOfferManagerTest;
import com.accenture.sdp.csm.test.packageprice.SdpPackagePriceManagerTest;
import com.accenture.sdp.csm.test.solutionoffer.SdpSolutionOfferManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpPackageManagerTest extends BaseTestCase {

	SdpPackageManager manager = SdpPackageManager.getInstance();

	@Test
	public void testCreatePackage() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createPackage();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createPackage() throws PropertyNotFoundException {

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();
		SdpOfferManagerTest offerManagerTest = new SdpOfferManagerTest();
		SdpPackagePriceManagerTest packagePriceManagerTest = new SdpPackagePriceManagerTest();

		// SOLUTION OFFER
		CreateServiceResponse createServiceResponse = solutionOfferManagerTest.createSolutionOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long solutionOfferId = createServiceResponse.getEntityId();

		// OFFER
		SearchServiceResponse searchServiceResponse = offerManagerTest.searchOffer(1L);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpOfferDto offerDto = (SdpOfferDto) searchServiceResponse.getSearchResult().get(0);

		Long offerId = offerDto.getOfferId();

		// PACKAGE PRICE
		createServiceResponse = packagePriceManagerTest.createPackagePrice();
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long packagePriceId = createServiceResponse.getEntityId();

		String externalId = null;
		String profile = null;

		CreateServiceResponse response = manager.createPackage(solutionOfferId, offerId, "Y", packagePriceId, null, null, externalId, profile,
				getTenantName());

		return response;
	}

	@Test
	public void testModifyPackage() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyPackage();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyPackage() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = c_searchPackage(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		Long packageId = 1L;

		SdpPackageDto packageDto = (SdpPackageDto) searchServiceResponse.getSearchResult().get(0);

		DataServiceResponse response = manager.modifyPackage(packageId, packageDto.getSolutionOfferId(), packageDto.getOfferId(), "Y",
				packageDto.getPackagePriceId(), null, null, packageDto.getExternalId(), null, getTenantName());
		return response;
	}

	@Test
	public void testDeletePackage() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deletePackage();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deletePackage() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createPackage();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long packageId = createServiceResponse.getEntityId();

		manager.packageChangeStatus(packageId, "Suspended", getTenantName());
		manager.packageChangeStatus(packageId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deletePackage(packageId, getTenantName());

		return response2;
	}

	@Test
	public void testc_SearchPackage() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = c_searchPackage();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse c_searchPackage() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createPackage();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long packageId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchPackage(packageId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse c_searchPackage(Long packageId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchPackage(packageId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testcSearchPackage() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPackage();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPackage() throws PropertyNotFoundException {

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		Long packageId = 1L;
		SearchServiceResponse searchServiceResponse = c_searchPackage(packageId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPackageDto packageDto = (SdpPackageDto) searchServiceResponse.getSearchResult().get(0);

		String offerName = packageDto.getOfferName();

		Long solutionOfferId = packageDto.getSolutionOfferId();

		searchServiceResponse = solutionOfferManagerTest.searchSolutionOffer(solutionOfferId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		SdpSolutionOfferDto solutionOfferDto = (SdpSolutionOfferDto) searchServiceResponse.getSearchResult().get(0);
		String solutionOfferName = solutionOfferDto.getSolutionOfferName();

		searchServiceResponse = manager.searchPackage(solutionOfferName, offerName, 0L, 0L, getTenantName());

		return searchServiceResponse;
	}
}
