package com.accenture.sdp.csm.test.subscription;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpSubscriptionDetailRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpCredentialDto;
import com.accenture.sdp.csm.dto.responses.SdpPartySiteDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDetailDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferPackageDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpAccountManager;
import com.accenture.sdp.csm.managers.SdpCredentialManager;
import com.accenture.sdp.csm.managers.SdpSiteManager;
import com.accenture.sdp.csm.managers.SdpSubscriptionManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.party.SdpPartyManagerTest;
import com.accenture.sdp.csm.test.solutionoffer.SdpSolutionOfferManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpSubscriptionManagerTest extends BaseTestCase {

	SdpSubscriptionManager manager = SdpSubscriptionManager.getInstance();

	@Test
	public void testc_CreateSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = c_createSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse c_createSubscription() throws PropertyNotFoundException {

		Long solutionOfferId = 1L;

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();

		CreateServiceResponse createServiceResponse = partyManagerTest.createParentPartyComplete();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long partyId = createServiceResponse.getEntityId();

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		// SEARCH PARTY
		SearchServiceResponse searchServiceResponse = partyManagerTest.searchParty(partyId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH ACCOUNT
		SdpAccountManager accountManager = SdpAccountManager.getInstance();

		searchServiceResponse = accountManager.searchAccountsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		// SEARCH SOLUTION OFFER ID
		searchServiceResponse = solutionOfferManagerTest.searchSolutionOffer(solutionOfferId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH CREDENTIAL

		SdpCredentialManager credentialManager = SdpCredentialManager.getInstance();

		searchServiceResponse = credentialManager.searchCredentialsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpCredentialDto credentialDto = (SdpCredentialDto) searchServiceResponse.getSearchResult().get(0);

		String username = credentialDto.getUsername();

		// SEARCH SITE

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();
		searchServiceResponse = sdpSiteManager.searchSitesByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartySiteDto partySiteDto = (SdpPartySiteDto) searchServiceResponse.getSearchResult().get(0);

		CreateServiceResponse resp = manager.createSubscription(partyId, solutionOfferId, null, username, "Administrator", accountResponseDto.getAccountId(),
				accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, new Date(), new Date(), getTenantName());

		System.out.println("SubscritpionId: " + resp.getEntityId());
		return resp;

	}

	@Test
	public void testc_createSubscriptionDetail() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = c_createSubscriptionDetail();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse c_createSubscriptionDetail() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = c_createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		Long packageId = 1L;

		CreateServiceResponse resp = manager.createSubscriptionDetail(subscriptionId, packageId, null, null, getTenantName());

		System.out.println("SubscritpionId: " + resp.getEntityId());
		return resp;

	}

	@Test
	public void testCreateSubscription() {
		try {

			for (int i = 0; i < 2; i++) {
				System.out.println(Utilities.getCurrentClassAndMethod());
				CreateServiceResponse resp = createSubscription();
				printResponse(resp, Utilities.getCurrentClassAndMethod());
				assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createSubscription() throws PropertyNotFoundException {

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();
		ComplexServiceResponse complexServiceResponse = solutionOfferManagerTest.createSolutionOfferAndPackages();

		SdpSolutionOfferPackageDto sdpSolutionOfferPackageDto = (SdpSolutionOfferPackageDto) complexServiceResponse.getComplexObject();

		Long solutionOfferId = sdpSolutionOfferPackageDto.getSolutionOfferDto().getSolutionOfferId();
		System.out.println("solutionOfferId: " + solutionOfferId);

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();

		CreateServiceResponse createServiceResponse = partyManagerTest.createParentPartyComplete();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long partyId = createServiceResponse.getEntityId();

		// SEARCH PARTY
		SearchServiceResponse searchServiceResponse = partyManagerTest.searchParty(partyId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH ACCOUNT
		SdpAccountManager accountManager = SdpAccountManager.getInstance();

		searchServiceResponse = accountManager.searchAccountsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		// SEARCH CREDENTIAL

		SdpCredentialManager credentialManager = SdpCredentialManager.getInstance();

		searchServiceResponse = credentialManager.searchCredentialsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpCredentialDto credentialDto = (SdpCredentialDto) searchServiceResponse.getSearchResult().get(0);

		String username = credentialDto.getUsername();

		// SEARCH SITE

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();
		searchServiceResponse = sdpSiteManager.searchSitesByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartySiteDto partySiteDto = (SdpPartySiteDto) searchServiceResponse.getSearchResult().get(0);

		// Build SUBSCRIPTION DETAILS

		List<SdpSubscriptionDetailRequestDto> sdpSubscriptionDetailRequestDtos = new ArrayList<SdpSubscriptionDetailRequestDto>();

		List<SdpSolutionOfferDetailDto> solutionOfferDtos = sdpSolutionOfferPackageDto.getSolutionOfferDetailDto();

		for (SdpSolutionOfferDetailDto sdpSolutionOfferDetailDto : solutionOfferDtos) {
			SdpSubscriptionDetailRequestDto subscriptionDetailRequestDto = new SdpSubscriptionDetailRequestDto();
			subscriptionDetailRequestDto.setPackageId(sdpSolutionOfferDetailDto.getPackageId());
			sdpSubscriptionDetailRequestDtos.add(subscriptionDetailRequestDto);

		}

		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.roll(GregorianCalendar.MONTH, -1);
		System.out.println(calendar);
		CreateServiceResponse resp = manager.createSubscription(partyId, solutionOfferId, null, username, "Administrator", accountResponseDto.getAccountId(),
				accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, calendar.getTime(), null, sdpSubscriptionDetailRequestDtos, getTenantName());

		System.out.println("SubscritpionId: " + resp.getEntityId());

		manager.subscriptionChangeStatus(resp.getEntityId(), "Active", getTenantName());
		return resp;

	}

	@Test
	public void testCreateChildSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createChildSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createChildSubscription() throws PropertyNotFoundException {

		Long solutionOfferId = 1L;

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();

		CreateServiceResponse createServiceResponse = partyManagerTest.createParentPartyComplete();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long partyId = createServiceResponse.getEntityId();

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		// SEARCH PARTY
		SearchServiceResponse searchServiceResponse = partyManagerTest.searchParty(partyId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH ACCOUNT
		SdpAccountManager accountManager = SdpAccountManager.getInstance();

		searchServiceResponse = accountManager.searchAccountsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		// SEARCH SOLUTION OFFER ID
		searchServiceResponse = solutionOfferManagerTest.searchSolutionOffer(solutionOfferId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH CREDENTIAL

		SdpCredentialManager credentialManager = SdpCredentialManager.getInstance();

		searchServiceResponse = credentialManager.searchCredentialsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpCredentialDto credentialDto = (SdpCredentialDto) searchServiceResponse.getSearchResult().get(0);

		String username = credentialDto.getUsername();

		// SEARCH SITE

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();
		searchServiceResponse = sdpSiteManager.searchSitesByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartySiteDto partySiteDto = (SdpPartySiteDto) searchServiceResponse.getSearchResult().get(0);

		List<SdpSubscriptionDetailRequestDto> sdpSubscriptionDetailRequestDtos = new ArrayList<SdpSubscriptionDetailRequestDto>();

		SdpSubscriptionDetailRequestDto subscriptionDetailRequestDto = new SdpSubscriptionDetailRequestDto();
		subscriptionDetailRequestDto.setPackageId(1L);
		sdpSubscriptionDetailRequestDtos.add(subscriptionDetailRequestDto);

		CreateServiceResponse resp = manager.createSubscription(partyId, solutionOfferId, null, username, "Administrator", accountResponseDto.getAccountId(),
				accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, new Date(), new Date(), sdpSubscriptionDetailRequestDtos, getTenantName());

		CreateServiceResponse resp2 = manager.createSubscription(partyId, solutionOfferId, resp.getEntityId(), username, "User",
				accountResponseDto.getAccountId(), accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, new Date(), new Date(),
				sdpSubscriptionDetailRequestDtos, getTenantName());

		System.out.println("Parent SubscritpionId: " + resp.getEntityId());
		System.out.println("Parent SubscritpionId: " + resp2.getEntityId());
		return resp2;

	}

	@Test
	public void testc_modifySubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = c_modifySubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse c_modifySubscription() throws PropertyNotFoundException {

		Long solutionOfferId = 1L;

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();

		CreateServiceResponse createServiceResponse = partyManagerTest.createParentPartyComplete();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long partyId = createServiceResponse.getEntityId();

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		// SEARCH PARTY
		SearchServiceResponse searchServiceResponse = partyManagerTest.searchParty(partyId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH ACCOUNT
		SdpAccountManager accountManager = SdpAccountManager.getInstance();

		searchServiceResponse = accountManager.searchAccountsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		// SEARCH SOLUTION OFFER ID
		searchServiceResponse = solutionOfferManagerTest.searchSolutionOffer(solutionOfferId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH CREDENTIAL

		SdpCredentialManager credentialManager = SdpCredentialManager.getInstance();

		searchServiceResponse = credentialManager.searchCredentialsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpCredentialDto credentialDto = (SdpCredentialDto) searchServiceResponse.getSearchResult().get(0);

		String username = credentialDto.getUsername();

		// SEARCH SITE

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();
		searchServiceResponse = sdpSiteManager.searchSitesByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartySiteDto partySiteDto = (SdpPartySiteDto) searchServiceResponse.getSearchResult().get(0);

		CreateServiceResponse resp = manager.createSubscription(partyId, solutionOfferId, null, username, "Administrator", accountResponseDto.getAccountId(),
				accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, new Date(), new Date(), getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		manager.modifySubscription(resp.getEntityId(), null, username, "Administrator", accountResponseDto.getAccountId(), accountResponseDto.getAccountId(),
				partySiteDto.getSiteId(), null, new Date(), new Date(), getTenantName());

		System.out.println("SubscritpionId: " + resp.getEntityId());
		return resp;

	}

	@Test
	public void testc_modifySubscriptionDetail() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = c_modifySubscriptionDetail();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse c_modifySubscriptionDetail() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = c_createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		Long packageId = 1L;

		CreateServiceResponse resp = manager.createSubscriptionDetail(subscriptionId, packageId, null, null, getTenantName());

		DataServiceResponse dataServiceResponse = manager.modifySubscriptionDetail(subscriptionId, packageId, Utils.getRandomName(10), null, getTenantName());

		System.out.println("SubscritpionId: " + resp.getEntityId());
		return dataServiceResponse;

	}

	@Test
	public void testModifySubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifySubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse modifySubscription() throws PropertyNotFoundException {

		Long solutionOfferId = 1L;

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();

		CreateServiceResponse createServiceResponse = partyManagerTest.createParentPartyComplete();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long partyId = createServiceResponse.getEntityId();

		SdpSolutionOfferManagerTest solutionOfferManagerTest = new SdpSolutionOfferManagerTest();

		// SEARCH PARTY
		SearchServiceResponse searchServiceResponse = partyManagerTest.searchParty(partyId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH ACCOUNT
		SdpAccountManager accountManager = SdpAccountManager.getInstance();

		searchServiceResponse = accountManager.searchAccountsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		// SEARCH SOLUTION OFFER ID
		searchServiceResponse = solutionOfferManagerTest.searchSolutionOffer(solutionOfferId);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		// SEARCH CREDENTIAL

		SdpCredentialManager credentialManager = SdpCredentialManager.getInstance();

		searchServiceResponse = credentialManager.searchCredentialsByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpCredentialDto credentialDto = (SdpCredentialDto) searchServiceResponse.getSearchResult().get(0);

		String username = credentialDto.getUsername();

		// SEARCH SITE

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();
		searchServiceResponse = sdpSiteManager.searchSitesByParty(partyId, 0L, 0L, getTenantName());

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartySiteDto partySiteDto = (SdpPartySiteDto) searchServiceResponse.getSearchResult().get(0);

		List<SdpSubscriptionDetailRequestDto> sdpSubscriptionDetailRequestDtos = new ArrayList<SdpSubscriptionDetailRequestDto>();

		SdpSubscriptionDetailRequestDto subscriptionDetailRequestDto = new SdpSubscriptionDetailRequestDto();
		subscriptionDetailRequestDto.setPackageId(1L);
		sdpSubscriptionDetailRequestDtos.add(subscriptionDetailRequestDto);

		CreateServiceResponse resp = manager.createSubscription(partyId, solutionOfferId, null, username, "Administrator", accountResponseDto.getAccountId(),
				accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, new Date(), new Date(), sdpSubscriptionDetailRequestDtos, getTenantName());

		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifySubscription(resp.getEntityId(), null, username, "Administrator", accountResponseDto.getAccountId(),
				accountResponseDto.getAccountId(), partySiteDto.getSiteId(), null, new Date(), new Date(), sdpSubscriptionDetailRequestDtos, getTenantName());

		System.out.println("SubscritpionId: " + resp.getEntityId());
		return response;

	}

	@Test
	public void testDeleteSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse deleteSubscription() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = c_createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		DataServiceResponse resp = manager.subscriptionChangeStatus(subscriptionId, "Active", getTenantName());

		return resp;

	}

	@Test
	public void testDeleteOfferInSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteOfferInSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse deleteOfferInSubscription() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		manager.subscriptionChangeStatus(subscriptionId, "Active", getTenantName());
		manager.subscriptionChangeStatus(subscriptionId, "Suspending", getTenantName());
		manager.subscriptionChangeStatus(subscriptionId, "Suspended", getTenantName());
		manager.subscriptionChangeStatus(subscriptionId, "Inactivating", getTenantName());
		manager.subscriptionChangeStatus(subscriptionId, "Inactive", getTenantName());

		List<Long> packageIdList = new ArrayList<Long>();
		packageIdList.add(1L);
		DataServiceResponse resp = manager.deleteOfferInSubscription(subscriptionId, packageIdList, getTenantName());

		return resp;

	}

	@Test
	public void testOfferInSubscriptionChangeStatus() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = offerInSubscriptionChangeStatus();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse offerInSubscriptionChangeStatus() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		List<Long> packageIdList = new ArrayList<Long>();
		packageIdList.add(1L);
		DataServiceResponse resp = manager.offerInSubscriptionChangeStatus("Active", subscriptionId, packageIdList, getTenantName());

		return resp;

	}

	@Test
	public void testc_searchSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = c_searchSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse c_searchSubscription() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(subscriptionId, getTenantName());

		return resp;

	}

	@Test
	public void testc_searchSubscriptionDetail() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = c_searchSubscriptionDetail();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse c_searchSubscriptionDetail() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscriptionDetail(subscriptionId, 1L, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSubscription() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(subscriptionId, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSubscriptionsByParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSubscriptionsByParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSubscriptionsByParty() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(subscriptionId, getTenantName());

		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		SdpSubscriptionResponseDto subscriptionResponseDto = (SdpSubscriptionResponseDto) resp.getSearchResult().get(0);

		Long partyId = subscriptionResponseDto.getPartyId();

		resp = manager.searchSubscriptionsByParty(partyId, 0L, 0L, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSubscriptionsByCredential() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSubscriptionsByCredential();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSubscriptionsByCredential() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(subscriptionId, getTenantName());

		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		SdpSubscriptionResponseDto subscriptionResponseDto = (SdpSubscriptionResponseDto) resp.getSearchResult().get(0);

		String username = subscriptionResponseDto.getUserName();

		resp = manager.searchSubscriptionsByCredential(username, 0L, 0L, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSubscriptionsByAccount() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSubscriptionsByAccount();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSubscriptionsByAccount() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(subscriptionId, getTenantName());

		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		SdpSubscriptionResponseDto subscriptionResponseDto = (SdpSubscriptionResponseDto) resp.getSearchResult().get(0);

		String payeeAccountName = subscriptionResponseDto.getPayeeAccountName();

		resp = manager.searchSubscriptionsByAccount(null, payeeAccountName, 0L, 0L, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSubscriptionsBySite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSubscriptionsBySite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSubscriptionsBySite() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(subscriptionId, getTenantName());

		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		SdpSubscriptionResponseDto subscriptionResponseDto = (SdpSubscriptionResponseDto) resp.getSearchResult().get(0);

		String siteName = subscriptionResponseDto.getSiteName();

		resp = manager.searchSubscriptionsBySite(siteName, 0L, 0L, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSubscriptionsByParentSubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSubscriptionsByParentSubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSubscriptionsByParentSubscription() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createChildSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long childSubscriptionId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchSubscription(childSubscriptionId, getTenantName());

		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		SdpSubscriptionResponseDto subscriptionResponseDto = (SdpSubscriptionResponseDto) resp.getSearchResult().get(0);

		Long parentSubscriptionId = subscriptionResponseDto.getParentSubscriptionId();

		resp = manager.searchSubscriptionsByParentSubscription(parentSubscriptionId, 0L, 0L, getTenantName());

		return resp;

	}

}
