package com.accenture.sdp.csm.test.site;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpSiteManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.party.SdpPartyManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpSiteManagerTest extends BaseTestCase {

	SdpSiteManager manager = SdpSiteManager.getInstance();

	@Test
	public void testCreateSite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createSite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateServiceResponse createSite() {
		try {

			SdpPartyManagerTest sdpPartyManagerTest = new SdpPartyManagerTest();

			String name = Utils.getRandomName(10);
			String description = Utils.getRandomName(10);
			String externalId = Utils.getRandomName(10);

			Long parentPartyId = sdpPartyManagerTest.createParentParty().getEntityId();

			CreateServiceResponse resp = manager.createSite(name, description, parentPartyId, externalId, "address", null, null, null, null, null,
					getTenantName());

			return resp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Test
	public void testModifySite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifySite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse modifySite() {
		try {

			CreateServiceResponse createServiceResponse = createSite();
			Long siteId = createServiceResponse.getEntityId();

			String name = Utils.getRandomName(10);
			String description = Utils.getRandomName(10);

			DataServiceResponse resp = manager.modifySite(siteId, name, description, "address", null, null, null, null, null, null, getTenantName());

			return resp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Test
	public void testDeleteSite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteSite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataServiceResponse deleteSite() {
		try {

			CreateServiceResponse createServiceResponse = createSite();
			Long siteId = createServiceResponse.getEntityId();

			manager.siteChangeStatus(siteId, "Suspended", getTenantName());

			manager.siteChangeStatus(siteId, "Inactive", getTenantName());

			DataServiceResponse resp = manager.deleteSite(siteId, getTenantName());

			return resp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Test
	public void testSearchSite() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchSite();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSite() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createSite();
		Long siteId = createServiceResponse.getEntityId();
		return searchSite(siteId);
	}

	public SearchServiceResponse searchSite(Long siteId) throws PropertyNotFoundException {
		// metodo con firma corretta introdotto con mongo
		// SearchServiceResponse resp = manager.searchSite(siteId, getTenantName());
		// uso vecchio metodo
		SearchServiceResponse resp = manager.searchSites(siteId, null, null, null, getTenantName());

		return resp;

	}

	@Test
	public void testSearchSites() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchSites();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSites() {
		try {

			SdpPartyManagerTest sdpPartyManagerTest = new SdpPartyManagerTest();

			String name = Utils.getRandomName(10);
			String description = Utils.getRandomName(10);
			String externalId = Utils.getRandomName(10);

			Long parentPartyId = sdpPartyManagerTest.createParentParty().getEntityId();

			manager.createSite(name, description, parentPartyId, externalId, "address", null, null, null, null, null, getTenantName());

			SearchServiceResponse response = manager.searchSites(null, name, 0L, 0L, getTenantName());

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Test
	public void testSearchSiteByParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchSiteByParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSiteByParty() {
		try {
			SdpPartyManagerTest sdpPartyManagerTest = new SdpPartyManagerTest();

			String name = Utils.getRandomName(10);
			String description = Utils.getRandomName(10);
			String externalId = Utils.getRandomName(10);

			Long parentPartyId = sdpPartyManagerTest.createParentParty().getEntityId();

			manager.createSite(name, description, parentPartyId, externalId, "address", null, null, null, null, null, getTenantName());

			SearchServiceResponse response = manager.searchSitesByParty(parentPartyId, 0L, 0L, getTenantName());

			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Test
	public void testSearchSiteByAccount() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchSiteByAccount();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSiteByAccount() {
		try {

			String accountName = "";

			SearchServiceResponse response = manager.searchSiteByAccount(accountName, getTenantName());

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Test
	public void testSearchSiteBySubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchSiteBySubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchSiteBySubscription() {
		try {

			Long subscriptionId = 1L;

			SearchServiceResponse response = manager.searchSiteBySubscription(subscriptionId, getTenantName());

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
