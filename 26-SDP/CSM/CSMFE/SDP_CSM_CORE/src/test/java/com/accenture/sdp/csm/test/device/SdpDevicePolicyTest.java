package com.accenture.sdp.csm.test.device;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDevicePolicyConfigRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartyDeviceExtRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpDevicePolicyManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpDevicePolicyTest extends BaseTestCase {

	private SdpDevicePolicyManager manager = SdpDevicePolicyManager.getInstance();

	@Test
	public void testCreateDevicePolicy000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createDevicePolicy();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateDevicePolicy010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// tutti mandatori
			boolean[] array = { true, true, true, true, true };
			// li testo tutti perche' sono generoso
			for (int i = 0; i < array.length; i++) {
				array[i] = false;
				CreateServiceResponse resp = createDevicePolicy(array);
				printResponse(resp, Utilities.getCurrentClassAndMethod());
				assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
				array[i] = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateDevicePolicy040() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createDevicePolicy(DeviceConfigurator.getPolicyRandom(tenantName).getPolicyName());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_040));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createDevicePolicy(boolean... valued) throws PropertyNotFoundException {
		String policyName = (valued.length > 0 && !valued[0]) ? null : Utils.getRandomNamePrefixed();
		return createDevicePolicy(policyName, Utils.removeHead(valued));
	}

	public CreateServiceResponse createDevicePolicy(String policyName, boolean... valued) throws PropertyNotFoundException {
		Long maxAssociationsNumber = (valued.length > 0 && !valued[0]) ? null : Utils.getRandomLong(100);
		Long safetyPeriodDuration = (valued.length > 1 && !valued[1]) ? null : Utils.getRandomLong(100);

		List<SdpDevicePolicyConfigRequestDto> maximumAllowedDevices = new ArrayList<SdpDevicePolicyConfigRequestDto>();
		List<String> channels = DeviceConfigurator.getChannels(tenantName);
		for (int i = 0; i <= Utils.getRandomInt(channels.size()); i++) {
			// non obbligatoriamente li metto tutti, ma almeno uno (il primo)
			if (Utils.coinFlip() || maximumAllowedDevices.size() == 0) {
				SdpDevicePolicyConfigRequestDto device = new SdpDevicePolicyConfigRequestDto();
				device.setDeviceChannel((valued.length > 2 && !valued[2]) ? null : channels.get(i));
				device.setMaximumNumber((valued.length > 3 && !valued[3]) ? null : Utils.getRandomLong(10));
				maximumAllowedDevices.add(device);
			}
		}
		return manager.createDevicePolicy(policyName, maxAssociationsNumber, safetyPeriodDuration, maximumAllowedDevices, getTenantName());
	}

	@Test
	public void testUpdateDevicePolicy000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = updateDevicePolicy();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDevicePolicy010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// tutti mandatori
			boolean[] array = { true, true, true, true, true, true };
			// li testo tutti
			for (int i = 0; i < array.length; i++) {
				array[i] = false;
				DataServiceResponse resp = updateDevicePolicy(array);
				printResponse(resp, Utilities.getCurrentClassAndMethod());
				assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
				array[i] = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDevicePolicy040() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());

			// prendo nome di una vecchia policy
			String policyName = DeviceConfigurator.getPolicyRandom(tenantName).getPolicyName();
			// ne creo una nuova
			CreateServiceResponse createServiceResponse = createDevicePolicy();
			Long policyId = createServiceResponse.getEntityId();
			// e la modifico col vecchio nome
			DataServiceResponse resp = updateDevicePolicy(policyId, policyName);

			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_040));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDevicePolicy100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());

			// metto un id grande causale
			DataServiceResponse resp = updateDevicePolicy(Utils.getGreaterRandomLong(1000000000));

			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse updateDevicePolicy(boolean... valued) throws PropertyNotFoundException {
		Long policyId = (valued.length > 0 && !valued[0]) ? null : DeviceConfigurator.getPolicyRandom(tenantName).getPolicyId();
		return updateDevicePolicy(policyId, Utils.removeHead(valued));
	}

	public DataServiceResponse updateDevicePolicy(Long policyId, boolean... valued) throws PropertyNotFoundException {
		String policyName = (valued.length > 0 && !valued[0]) ? null : Utils.getRandomNamePrefixed();
		return updateDevicePolicy(policyId, policyName, Utils.removeHead(valued));
	}

	public DataServiceResponse updateDevicePolicy(Long policyId, String policyName, boolean... valued) throws PropertyNotFoundException {
		Long maxAssociationsNumber = (valued.length > 0 && !valued[0]) ? null : Utils.getRandomLong(100);
		Long safetyPeriodDuration = (valued.length > 1 && !valued[1]) ? null : Utils.getRandomLong(100);

		List<SdpDevicePolicyConfigRequestDto> maximumAllowedDevices = new ArrayList<SdpDevicePolicyConfigRequestDto>();
		List<String> channels = DeviceConfigurator.getChannels(tenantName);
		for (int i = 0; i <= Utils.getRandomInt(channels.size()); i++) {
			SdpDevicePolicyConfigRequestDto device = new SdpDevicePolicyConfigRequestDto();
			device.setDeviceChannel((valued.length > 2 && !valued[2]) ? null : channels.get(i));
			device.setMaximumNumber((valued.length > 3 && !valued[3]) ? null : Utils.getRandomLong(10));
			maximumAllowedDevices.add(device);
		}
		return manager.updateDevicePolicy(policyId, policyName, maxAssociationsNumber, safetyPeriodDuration, maximumAllowedDevices, getTenantName());
	}

	@Test
	public void testDeleteDevicePolicy000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// la creo prima di cancellarla... anche se il prerequisito dice altro...
			CreateServiceResponse createServiceResponse = createDevicePolicy();
			Long policyId = createServiceResponse.getEntityId();
			DataServiceResponse resp = deleteDevicePolicy(policyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteDevicePolicy010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteDevicePolicy(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteDevicePolicy020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// la default policy e' vietata
			DataServiceResponse resp = deleteDevicePolicy(DeviceConfigurator.getPolicyDefault(tenantName).getPolicyId());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteDevicePolicy100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// metto un id grande causale
			DataServiceResponse resp = deleteDevicePolicy(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteDevicePolicy(Long policyId) throws PropertyNotFoundException {
		return manager.deleteDevicePolicy(policyId, getTenantName());
	}

	@Test
	public void testSearchDevicePolicyById000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// la creo prima di cancellarla... anche se il prerequisito dice altro...
			CreateServiceResponse createServiceResponse = createDevicePolicy();
			Long policyId = createServiceResponse.getEntityId();
			ComplexServiceResponse resp = searchDevicePolicyById(policyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDevicePolicyById010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			ComplexServiceResponse resp = searchDevicePolicyById(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDevicePolicyById100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			ComplexServiceResponse resp = searchDevicePolicyById(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ComplexServiceResponse searchDevicePolicyById(Long policyId) throws PropertyNotFoundException {
		return manager.searchDevicePolicyById(policyId, getTenantName());
	}

	@Test
	public void testSearchAllDevicePolicies000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllDevicePolicies();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllDevicePolicies() throws PropertyNotFoundException {
		return manager.searchAllDevicePolicies(getTenantName());
	}

	@Test
	public void testSetPartyDevicePolicy000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = setPartyDevicePolicy();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetPartyDevicePolicy010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// tutti mandatori
			boolean[] array = { true, true };
			// li testo tutti
			for (int i = 0; i < array.length; i++) {
				array[i] = false;
				DataServiceResponse resp = setPartyDevicePolicy(array);
				printResponse(resp, Utilities.getCurrentClassAndMethod());
				assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
				array[i] = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetPartyDevicePolicy100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// party inesistente
			DataServiceResponse resp = setPartyDevicePolicy(Utils.getGreaterRandomLong(1000000000), DeviceConfigurator.getPolicyRandom(tenantName)
					.getPolicyId());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
			// policy inesistente
			resp = setPartyDevicePolicy(getRandomPartyId(), Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse setPartyDevicePolicy(boolean... valued) throws PropertyNotFoundException {
		List<SdpPartyDeviceExtRequestDto> policies = new ArrayList<SdpPartyDeviceExtRequestDto>();
		// ne metto tra 1 e 5
		int j = 1 + Utils.getRandomInt(5);
		for (int i = 0; i < j; i++) {
			SdpPartyDeviceExtRequestDto policy = new SdpPartyDeviceExtRequestDto();
			policy.setPartyId((valued.length > 0 && !valued[0]) ? null : getRandomPartyId());
			policy.setPolicyId((valued.length > 1 && !valued[1]) ? null : DeviceConfigurator.getPolicyRandom(tenantName).getPolicyId());
			policies.add(policy);
		}
		return manager.setPartyDevicePolicy(policies, tenantName);
	}

	public DataServiceResponse setPartyDevicePolicy(Long partyId, Long policyId) throws PropertyNotFoundException {
		List<SdpPartyDeviceExtRequestDto> policies = new ArrayList<SdpPartyDeviceExtRequestDto>();
		SdpPartyDeviceExtRequestDto policy = new SdpPartyDeviceExtRequestDto();
		policy.setPartyId(partyId);
		policy.setPolicyId(policyId);
		policies.add(policy);
		return manager.setPartyDevicePolicy(policies, tenantName);
	}

	@Test
	public void testSearchDevicePolicyByPartyId000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// devo settarla prima, altrimenti mai funzionerebbe
			Long partyId = getRandomPartyId();
			Long policyId = DeviceConfigurator.getPolicyRandom(tenantName).getPolicyId();
			setPartyDevicePolicy(partyId, policyId);
			// poi la cerco
			DataServiceResponse resp = searchDevicePolicyByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDevicePolicyByPartyId010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDevicePolicyByPartyId(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDevicePolicyByPartyId100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDevicePolicyByPartyId(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchDevicePolicyByPartyId130() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prendo un party e lo resetto
			Long partyId = getRandomPartyId();
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			// quindi ricerco
			DataServiceResponse resp = searchDevicePolicyByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_130));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse searchDevicePolicyByPartyId(Long partyId) throws PropertyNotFoundException {
		return manager.searchDevicePolicyByPartyId(partyId, tenantName);
	}

}
