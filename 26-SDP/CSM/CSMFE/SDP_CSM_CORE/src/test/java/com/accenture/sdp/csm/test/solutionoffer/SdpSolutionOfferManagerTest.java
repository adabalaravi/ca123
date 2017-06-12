package com.accenture.sdp.csm.test.solutionoffer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.ExternalIdDto;
import com.accenture.sdp.csm.dto.requests.SdpSolutionOfferDetailRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpSolutionOfferManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.solution.SdpSolutionManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpSolutionOfferManagerTest extends BaseTestCase {

	SdpSolutionOfferManager manager = SdpSolutionOfferManager.getInstance();

	@Test
	public void testCreateSolutionOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createSolutionOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createSolutionOffer() throws PropertyNotFoundException {
		SdpSolutionManagerTest solutionManagerTest = new SdpSolutionManagerTest();
		SearchServiceResponse searchServiceResponse = solutionManagerTest.searchAllSolutions();
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		SdpSolutionDto solutionDto = (SdpSolutionDto) searchServiceResponse.getSearchResult().get(0);

		Long solutionId = solutionDto.getSolutionId();

		String solutionOfferName = Utils.getRandomName(10);
		String solutionOfferDescription = Utils.getRandomName(10);
		List<ExternalIdDto> externalId = null;
		String solutionOfferProfile = null;
		List<String> partyGroups = null;
		Date startDate = new Date();
		Date endDate = new Date();
		String solutionOfferType = Utils.getRandomName(10);
		Long duration = Utils.getRandomLong(1000);

		CreateServiceResponse response = manager.createSolutionOffer(solutionId, solutionOfferName, solutionOfferDescription, startDate, endDate,
				externalId, solutionOfferProfile, partyGroups, solutionOfferType, duration, getTenantName());
		return response;
	}

	@Test
	public void testCreateSolutionOfferAndPackages() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			ComplexServiceResponse resp = createSolutionOfferAndPackages();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ComplexServiceResponse createSolutionOfferAndPackages() throws PropertyNotFoundException {

		SdpSolutionManagerTest solutionManagerTest = new SdpSolutionManagerTest();
		SearchServiceResponse searchServiceResponse = solutionManagerTest.searchAllSolutions();
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		SdpSolutionDto solutionDto = (SdpSolutionDto) searchServiceResponse.getSearchResult().get(0);

		String solutionName = solutionDto.getSolutionName();

		String solutionOfferName = Utils.getRandomName(10);
		String solutionOfferDescription = Utils.getRandomName(10);

		List<SdpSolutionOfferDetailRequestDto> solutionsDetails = new ArrayList<SdpSolutionOfferDetailRequestDto>();
		SdpSolutionOfferDetailRequestDto dto = new SdpSolutionOfferDetailRequestDto();
		dto.setCurrencyTypeID(1L);
		dto.setNrcPriceCatalogId(2L);
		dto.setRcPriceCatalogId(2L);
		dto.setRcFlagProrate("N");
		dto.setRcInAdvance("N");
		dto.setRcFrequencyTypeID(17L);
		dto.setOfferId(1L);
		dto.setMandatory("N");
		solutionsDetails.add(dto);
		
		List<ExternalIdDto> externalId = null;
		String solutionOfferProfile = null;
		List<String> partyGroups = null;
		Date startDate = new Date();
		Date endDate = new Date();
		String solutionOfferType = Utils.getRandomName(10);
		Long duration = Utils.getRandomLong(1000);

		ComplexServiceResponse response = manager.createSolutionOfferAndPackage(solutionName, solutionOfferName, solutionOfferDescription, startDate,
				endDate, externalId, solutionOfferProfile, solutionsDetails, partyGroups, solutionOfferType, duration, getTenantName());

		return response;

	}

	@Test
	public void testModifySolutionOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifySolutionOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifySolutionOffer() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSolutionOffer();

		SdpSolutionManagerTest solutionManagerTest = new SdpSolutionManagerTest();
		SearchServiceResponse searchServiceResponse = solutionManagerTest.searchAllSolutions();
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		SdpSolutionDto solutionDto = (SdpSolutionDto) searchServiceResponse.getSearchResult().get(0);

		Long solutionId = solutionDto.getSolutionId();

		Long solutionOfferId = createServiceResponse.getEntityId();

		String solutionOfferName = Utils.getRandomName(10);
		String solutionOfferDescription = Utils.getRandomName(10);
		List<ExternalIdDto> externalIds = new ArrayList<ExternalIdDto>();
		ExternalIdDto externalId = new ExternalIdDto();
		externalId.setExternalId(Utils.getRandomName(10));
		externalId.setExternalPlatformName("AppleStore");
		externalIds.add(externalId);

		String solutionOfferType = Utils.getRandomName(10);
		Long duration = Utils.getRandomLong(1000);

		DataServiceResponse response = manager.modifySolutionOffer(solutionOfferId, solutionOfferName, solutionOfferDescription, new Date(), new Date(),
				externalIds, solutionId, null, solutionOfferType, duration, getTenantName());

		return response;
	}

	@Test
	public void testDeleteSolutionOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteSolutionOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteSolutionOffer() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSolutionOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long solutionOfferId = createServiceResponse.getEntityId();

		manager.solutionOfferChangeStatus(solutionOfferId, "Suspended", getTenantName());
		manager.solutionOfferChangeStatus(solutionOfferId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deleteSolutionOffer(solutionOfferId, getTenantName());

		return response2;
	}

	@Test
	public void testSearchSolutionOffer() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSolutionOffer();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchSolutionOffer() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSolutionOffer();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long solutionOfferId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchSolutionOffer(solutionOfferId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchSolutionOfferBySolution() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSolutionOfferBySolution();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchSolutionOfferBySolution() throws PropertyNotFoundException {

		SdpSolutionManagerTest solutionManagerTest = new SdpSolutionManagerTest();
		SearchServiceResponse searchServiceResponse = solutionManagerTest.searchAllSolutions();
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));
		SdpSolutionDto solutionDto = (SdpSolutionDto) searchServiceResponse.getSearchResult().get(0);

		String solutionName = solutionDto.getSolutionName();

		searchServiceResponse = manager.searchSolutionOffersBySolution(solutionName, 0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchSolutionOffer(Long SolutionOfferId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchSolutionOffer(SolutionOfferId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchAllSolutionOffers() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllSolutionOffers();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllSolutionOffers() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchAllSolutionOffers(0L, 0L, getTenantName());

		return searchServiceResponse;
	}

}
