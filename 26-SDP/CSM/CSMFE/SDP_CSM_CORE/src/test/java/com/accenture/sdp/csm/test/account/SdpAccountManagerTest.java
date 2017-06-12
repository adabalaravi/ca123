package com.accenture.sdp.csm.test.account;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpAccountManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.party.SdpPartyManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpAccountManagerTest extends BaseTestCase {

	SdpAccountManager manager = SdpAccountManager.getInstance();

	@Test
	public void testCreateAccount() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createAccount();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createAccount() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();

		Long partyId = createServiceResponse.getEntityId();

		String accountName = Utils.getRandomName(10);
		String accountDescription = Utils.getRandomName(10);
		String externalId = null;
		String accountProfile = null;

		SearchServiceResponse searchServiceResponse = partyManagerTest.searchParty(partyId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpPartyResponseDto sdpPartyResponseDto = (SdpPartyResponseDto) searchServiceResponse.getSearchResult().get(0);

		Long siteId = sdpPartyResponseDto.getUserSiteId();

		CreateServiceResponse response = manager.createAccount(accountName, accountDescription, true, partyId, siteId, externalId, accountProfile,
				getTenantName());
		return response;

	}

	@Test
	public void testModifyAccount() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyAccount();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyAccount() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createAccount();

		Long accountId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = searchAccountById(accountId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		String accountName = Utils.getRandomName(10);
		String accountDescription = Utils.getRandomName(10);
		String externalId = null;
		String accountProfile = null;

		DataServiceResponse response = manager.modifyAccount(createServiceResponse.getEntityId(), accountName, accountDescription, true,
				accountResponseDto.getPartyId(), accountResponseDto.getSiteId(), externalId, accountProfile, getTenantName());
		return response;

	}

	@Test
	public void testDeleteAccount() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteAccount();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteAccount() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createAccount();

		Long accountId = createServiceResponse.getEntityId();

		manager.accountChangeStatus(accountId, "Suspended", getTenantName());

		manager.accountChangeStatus(accountId, "Inactive", getTenantName());

		DataServiceResponse response = manager.deleteAccount(accountId, getTenantName());

		return response;

	}

	@Test
	public void testSearchAccountById() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAccountById();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAccountById() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createAccount();
		Long accountId = createServiceResponse.getEntityId();

		return searchAccountById(accountId);
	}

	public SearchServiceResponse searchAccountById(Long accountId) throws PropertyNotFoundException {
		// metodo non previsto prima della versione mongo
		// qui necessario praticamente sempre
		SearchServiceResponse resp = null; //manager.searchAccount(accountId, getTenantName());

		return resp;
	}

	@Test
	public void testSearchAccountByName() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAccountByName();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAccountByName() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createAccount();
		Long accountId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = searchAccountById(accountId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		SearchServiceResponse resp = manager.searchAccount(accountResponseDto.getAccountName(), getTenantName());

		return resp;
	}

	public SearchServiceResponse searchAccountByName(String accountName) throws PropertyNotFoundException {
		SearchServiceResponse resp = manager.searchAccount(accountName, getTenantName());

		return resp;
	}

	@Test
	public void testSearchAccountByParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAccountByParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAccountByParty() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createAccount();
		Long accountId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = searchAccountById(accountId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		SearchServiceResponse resp = manager.searchAccountsByParty(accountResponseDto.getPartyId(), 0L, 0L, getTenantName());

		return resp;

	}

	@Test
	public void testSearchAccountBySite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAccountBySite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAccountBySite() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createAccount();
		Long accountId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = searchAccountById(accountId);

		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpAccountResponseDto accountResponseDto = (SdpAccountResponseDto) searchServiceResponse.getSearchResult().get(0);

		SearchServiceResponse resp = manager.searchAccountsBySite(accountResponseDto.getSiteId(), 0L, 0L, getTenantName());

		return resp;
	}

	@Test
	public void testSearchAccountsBySubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAccountsBySubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAccountsBySubscription() throws PropertyNotFoundException {

		Long subscriptionId = 1L;

		SearchServiceResponse resp = manager.searchAccountsBySubscription(subscriptionId, getTenantName());

		return resp;

	}

	@Test
	public void testAccountChangeStatus() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = accountChangeStatus();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse accountChangeStatus() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createAccount();
		Long accountId = createServiceResponse.getEntityId();

		DataServiceResponse resp = manager.accountChangeStatus(accountId, "Inactive", getTenantName());
		return resp;
	}
}
