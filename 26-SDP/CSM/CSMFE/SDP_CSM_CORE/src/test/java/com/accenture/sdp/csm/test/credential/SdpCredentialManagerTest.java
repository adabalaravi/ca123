package com.accenture.sdp.csm.test.credential;

import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpCredentialManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.party.SdpPartyManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpCredentialManagerTest extends BaseTestCase {

	SdpCredentialManager manager = SdpCredentialManager.getInstance();
	String roleName = "User";

	@Test
	public void testcreateCredentials() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createCredentials();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createCredentials() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";
		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		CreateServiceResponse response = manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());
		return response;
	}

	@Test
	public void testUpdateCredential() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = updateCredential();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse updateCredential() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		String oldPassword = password;
		String newPassword = "password2";
		String confirmNewPassword = newPassword;
		String externalId = null;
		secretQuestions = Utils.prepareSecretQuestions();

		DataServiceResponse resp = manager.modifyCredential(username, oldPassword, newPassword, confirmNewPassword, externalId, secretQuestions,
				getTenantName());
		return resp;
	}

	@Test
	public void testDeleteCredential() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteCredential();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteCredential() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		manager.credentialChangeStatus(username, "Suspended", getTenantName());
		manager.credentialChangeStatus(username, "Inactive", getTenantName());

		DataServiceResponse resp = manager.deleteCredential(username, getTenantName());
		return resp;
	}

	@Test
	public void testSearchCredentialByUsername() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchCredentialByUsername();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchCredentialByUsername() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		SearchServiceResponse resp = manager.searchCredential(username, getTenantName());
		return resp;
	}

	@Test
	public void testSearchCredentialBySubscription() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchCredentialBySubscription();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchCredentialBySubscription() throws PropertyNotFoundException {

		Long subscriptionId = 1L;

		SearchServiceResponse resp = manager.searchCredentialBySubscription(subscriptionId, getTenantName());
		return resp;
	}

	@Test
	public void testSearchCredentialByParty() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchCredentialByParty();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchCredentialByParty() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		SearchServiceResponse resp = manager.searchCredentialsByParty(partyId, 0L, 0L, getTenantName());
		return resp;
	}

	@Test
	public void testCredentialChangeStatus() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = credentialChangeStatus();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse credentialChangeStatus() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		DataServiceResponse resp = manager.credentialChangeStatus(username, "Inactive", getTenantName());
		return resp;
	}

	@Test
	public void testCheckCredential() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = checkCredential();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse checkCredential() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		DataServiceResponse resp = manager.checkCredential(username, password, getTenantName());
		return resp;
	}

	@Test
	public void testResetPassword() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = resetPassword();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse resetPassword() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);
		String password = "password1";

		List<SdpSecretQuestionRequestDto> secretQuestions = Utils.prepareSecretQuestions();

		manager.createCredential(username, password, partyId, null, roleName, secretQuestions, getTenantName());

		DataServiceResponse resp = manager.resetPassword(username, getTenantName());
		return resp;
	}

	@Test
	public void testReserveUsername() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = reserveUsername();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse reserveUsername() throws PropertyNotFoundException {

		SdpPartyManagerTest partyManagerTest = new SdpPartyManagerTest();
		CreateServiceResponse createServiceResponse = partyManagerTest.createChildParty();
		Long partyId = createServiceResponse.getEntityId();

		String username = Utils.getRandomName(10);

		DataServiceResponse resp = manager.reserveUsername(username, partyId, getTenantName());
		return resp;
	}
}
