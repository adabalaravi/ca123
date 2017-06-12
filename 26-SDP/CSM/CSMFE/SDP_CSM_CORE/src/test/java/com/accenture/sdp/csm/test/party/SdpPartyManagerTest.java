package com.accenture.sdp.csm.test.party;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.CreateServicesResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpAccountRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartySiteRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartySiteDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpCredentialManager;
import com.accenture.sdp.csm.managers.SdpPartyManager;
import com.accenture.sdp.csm.managers.SdpSiteManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.account.SdpAccountManagerTest;
import com.accenture.sdp.csm.test.site.SdpSiteManagerTest;
import com.accenture.sdp.csm.test.subscription.SdpSubscriptionManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpPartyManagerTest extends BaseTestCase {

	SdpPartyManager manager = SdpPartyManager.getInstance();

	@Test
	public void testCreateParentParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createParentParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createParentParty() throws PropertyNotFoundException {

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		CreateServiceResponse resp = manager.createParentParty(partyName, partyDescription, externalId, partyProfile, partyGroupIds, getTenantName());
		printResponse(resp, Utilities.getCurrentClassAndMethod());
		assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));

		return resp;

	}

	@Test
	public void testCreateChildParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createChildParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createChildParty() throws PropertyNotFoundException {

		CreateServiceResponse parentResp = createParentParty();

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		// create site

		String name = Utils.getRandomName(10);
		String description = Utils.getRandomName(10);

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();

		CreateServiceResponse siteResponse = sdpSiteManager.createSite(name, description, parentResp.getEntityId(), externalId, "address", null, null, null,
				null, null, getTenantName());

		CreateServiceResponse resp = manager.createChildParty(partyName, partyDescription, parentResp.getEntityId(), partyGroupIds, externalId, partyProfile,
				"firstName", "lastName", siteResponse.getEntityId(), "streetAddress", "city", "12345", "RM", "IT", "M", new Date(), "NA", "Italy", "Naples",
				null, null, null, null, getTenantName());

		return resp;

	}

	@Test
	public void testCreateChildPartyAndCredential() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createChildPartyAndCredential();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createChildPartyAndCredential() throws PropertyNotFoundException {

		CreateServiceResponse parentResp = createParentParty();

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		String username = Utils.getRandomName(10);
		String password = "password1";

		// create site

		String name = Utils.getRandomName(10);
		String description = Utils.getRandomName(10);

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();

		CreateServiceResponse siteResponse = sdpSiteManager.createSite(name, description, parentResp.getEntityId(), externalId, "address", null, null, null,
				null, null, getTenantName());

		CreateServiceResponse resp = manager.createChildPartyAndCredential(partyName, partyDescription, parentResp.getEntityId(), partyGroupIds, externalId,
				partyProfile, "firstName", "lastName", siteResponse.getEntityId(), "streetAddress", "city", "12345", "RM", "IT", "M", new Date(), "NA",
				"Italy", "Naples", null, null, null, null, username, password, null, secretQuestions, getTenantName());

		return resp;

	}

	@Test
	public void testCreateParentPartyComplete() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createParentPartyComplete();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createParentPartyComplete() throws PropertyNotFoundException {

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		List<SdpPartySiteRequestDto> partySites = new ArrayList<SdpPartySiteRequestDto>();

		SdpPartySiteRequestDto siteRequestDto = new SdpPartySiteRequestDto();
		siteRequestDto.setSiteName(Utils.getRandomName(10));
		siteRequestDto.setSiteDescription(Utils.getRandomName(10));
		partySites.add(siteRequestDto);

		List<SdpAccountRequestDto> accounts = new ArrayList<SdpAccountRequestDto>();

		SdpAccountRequestDto accountRequestDto = new SdpAccountRequestDto();
		accountRequestDto.setAccountName(Utils.getRandomName(10));
		accountRequestDto.setAccountDescription(Utils.getRandomName(10));
		accounts.add(accountRequestDto);

		CreateServiceResponse resp = manager.createParentPartyComplete(partyName, partyDescription, partyGroupIds, externalId, partyProfile, username,
				password, null, secretQuestions, partySites, accounts, getTenantName());
		return resp;

	}

	@Test
	public void testCreateChildPartyAndParentDummy() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServicesResponse resp = createChildPartyAndParentDummy();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServicesResponse createChildPartyAndParentDummy() throws PropertyNotFoundException {
		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();
		
		// create site
		String siteName = Utils.getRandomName(10);
		String siteDescription = Utils.getRandomName(10);
		List<SdpPartySiteRequestDto> partySites = new ArrayList<SdpPartySiteRequestDto>();

		SdpPartySiteRequestDto siteDto = new SdpPartySiteRequestDto();
		siteDto.setSiteName(siteName);
		siteDto.setSiteDescription(siteDescription);
		partySites.add(siteDto);

		CreateServicesResponse resp = manager.createChildPartyAndParentDummy(partyName, partyDescription, partyGroupIds, externalId, partyProfile, "firstName",
				"lastName", siteName, "streetAddress", "city", "12345", "RM", "IT", "M", new Date(), "NA", "Italy", "Naples", null, null, null, null, username,
				password, null, secretQuestions, partySites, getTenantName());

		return resp;

	}

	@Test
	public void testModifyParentParty() {

		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyParentParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse modifyParentParty() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createParentParty();

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;

		DataServiceResponse dataServiceResponse = manager.modifyParentParty(createServiceResponse.getEntityId(), partyName, partyDescription, externalId,
				partyProfile, getTenantName());
		return dataServiceResponse;
	}

	@Test
	public void testModifyChildParty() {

		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyChildParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse modifyChildParty() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createChildParty();

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;

		DataServiceResponse dataServiceResponse = manager.modifyChildParty(createServiceResponse.getEntityId(), partyName, partyDescription, externalId,
				partyProfile, 1L, "firstName", "lastName", "streetAddress", "city", "81100", "RM", "IT", "M", new Date(), null, null, null, null, null, null,
				null, getTenantName());
		return dataServiceResponse;
	}

	@Test
	public void testDeleteParty() {

		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse deleteParty() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createChildParty();

		Long partyId = createServiceResponse.getEntityId();

		manager.partyChangeStatus(partyId, "Suspended", getTenantName());

		manager.partyChangeStatus(partyId, "Inactive", getTenantName());

		DataServiceResponse dataServiceResponse = manager.deleteParty(partyId, getTenantName());
		return dataServiceResponse;
	}

	@Test
	public void testSearchParentParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchParentParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchParentParty() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createParentParty();

		Long partyId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchParty(partyId, getTenantName());

		return resp;
	}

	@Test
	public void testSearchChildParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchChildParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchChildParty() throws PropertyNotFoundException {
		CreateServiceResponse parentResp = createParentParty();

		String partyName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		// create site

		String name = Utils.getRandomName(10);
		String description = Utils.getRandomName(10);

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();

		CreateServiceResponse siteResponse = sdpSiteManager.createSite(name, description, parentResp.getEntityId(), externalId, "address", null, null, null,
				null, null, getTenantName());

		manager.createChildParty(partyName, partyDescription, parentResp.getEntityId(), partyGroupIds, externalId, partyProfile, "firstName", "lastName",
				siteResponse.getEntityId(), "streetAddress", "city", "12345", "RM", "IT", "M", new Date(), "NA", "Italy", "Naples", null, null, null, null,
				getTenantName());

		SearchServiceResponse searchServiceResponse = manager.searchChildParty(parentResp.getEntityId(), partyName, 0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchChildPartyByName() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchChildPartyByName();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchChildPartyByName() throws PropertyNotFoundException {
		CreateServiceResponse parentResp = createParentParty();

		String partyName = Utils.getRandomName(10);
		String firstName = Utils.getRandomName(10);
		String lastName = Utils.getRandomName(10);
		String partyDescription = Utils.getRandomName(10);
		String externalId = Utils.getRandomName(10);
		String partyProfile = null;
		ArrayList<Long> partyGroupIds = new ArrayList<Long>();
		partyGroupIds.add(1L);

		// create site

		String name = Utils.getRandomName(10);
		String description = Utils.getRandomName(10);

		SdpSiteManager sdpSiteManager = SdpSiteManager.getInstance();

		CreateServiceResponse siteResponse = sdpSiteManager.createSite(name, description, parentResp.getEntityId(), externalId, "address", null, null, null,
				null, null, getTenantName());

		manager.createChildParty(partyName, partyDescription, parentResp.getEntityId(), partyGroupIds, externalId, partyProfile, firstName, lastName,
				siteResponse.getEntityId(), "streetAddress", "city", "12345", "RM", "IT", "M", new Date(), "NA", "Italy", "Naples", null, null, null, null,
				getTenantName());

		// vecchio searchChildPartyByName ha parentPartyName e non Id : va trovato
		SearchServiceResponse searchParentResp = searchParty(parentResp.getEntityId());
		String parentName = ((SdpPartyResponseDto) searchParentResp.getSearchResult().get(0)).getPartyName();

		SearchServiceResponse searchServiceResponse = manager.searchChildPartyByName(firstName, lastName, partyName, parentName, 0L, 0L, getTenantName());
		return searchServiceResponse;
	}

	@Test
	public void testSearchPartyByAccount() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPartyByAccount();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPartyByAccount() throws PropertyNotFoundException {

		SdpAccountManagerTest sdpAccountManagerTest = new SdpAccountManagerTest();
		CreateServiceResponse createServiceResponse = sdpAccountManagerTest.createAccount();
		Long accountId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = sdpAccountManagerTest.searchAccountById(accountId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		searchServiceResponse = manager.searchPartyByAccount(accountResponseDto.getAccountName(), 1L, getTenantName());
		return searchServiceResponse;
	}

	@Test
	public void testSearchPartiesBySite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPartiesBySite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPartiesBySite() throws PropertyNotFoundException {

		SdpSiteManagerTest siteManagerTest = new SdpSiteManagerTest();

		CreateServiceResponse createServiceResponse = siteManagerTest.createSite();

		Long siteId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = siteManagerTest.searchSite(siteId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartySiteDto partySiteDto = (SdpPartySiteDto) searchServiceResponse.getSearchResult().get(0);

		searchServiceResponse = manager.searchPartiesBySite(partySiteDto.getSiteId(), 1L, 0L, 0L, getTenantName());
		return searchServiceResponse;
	}

	@Test
	public void testSearchPartyByCredential() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPartyByCredential();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPartyByCredential() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		createServiceResponse = SdpCredentialManager.getInstance()
				.createCredential(username, password, partyId, null, "User", secretQuestions, getTenantName());

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SearchServiceResponse searchServiceResponse = manager.searchPartyByCredential(username, 1L, getTenantName());
		return searchServiceResponse;
	}

	@Test
	public void testSearchPartyBySubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPartyBySubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPartyBySubscription() throws PropertyNotFoundException {

		SdpSubscriptionManagerTest subscriptionManagerTest = new SdpSubscriptionManagerTest();
		CreateServiceResponse createServiceResponse = subscriptionManagerTest.c_createSubscription();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long subscriptionId = createServiceResponse.getEntityId();
		SearchServiceResponse searchServiceResponse = manager.searchPartyBySubscription(subscriptionId, 1L, getTenantName());
		return searchServiceResponse;
	}

	@Test
	public void testSearchParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchParty() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createChildParty();

		SearchServiceResponse searchServiceResponse = manager.searchParty(createServiceResponse.getEntityId(), getTenantName());
		return searchServiceResponse;
	}

	public SearchServiceResponse searchParty(Long partyId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchParty(partyId, getTenantName());
		return searchServiceResponse;
	}

	@Test
	public void testModifyPartyCluster() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyPartyCluster() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createChildParty();

		List<SdpPartyGroupRequestDto> partyGroupRequestDtos = new ArrayList<SdpPartyGroupRequestDto>();
		SdpPartyGroupRequestDto dto1 = new SdpPartyGroupRequestDto();
		dto1.setOperation("ADD");
		dto1.setPartyGroupId(2L);
		partyGroupRequestDtos.add(dto1);

		DataServiceResponse resp = manager.modifyPartyCluster(createServiceResponse.getEntityId(), partyGroupRequestDtos, getTenantName());
		return resp;
	}
}
