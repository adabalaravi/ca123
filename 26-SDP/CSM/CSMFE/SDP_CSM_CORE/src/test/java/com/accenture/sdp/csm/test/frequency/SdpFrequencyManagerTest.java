package com.accenture.sdp.csm.test.frequency;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.RefFrequencyTypeManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpFrequencyManagerTest extends BaseTestCase {

	RefFrequencyTypeManager manager = RefFrequencyTypeManager.getInstance();

	@Test
	public void testCreateFrequency() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createFrequency();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createFrequency() throws PropertyNotFoundException {

		String frequencyName = Utils.getRandomName(50);
		String frequencyDescription = Utils.getRandomName(100);
		Long days = 1L + Utils.getRandomInt(99);

		CreateServiceResponse response = manager.createFrequency(frequencyName, frequencyDescription, days, getTenantName());
		return response;

	}

	@Test
	public void testDeleteFrequency() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteFrequency();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteFrequency() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createFrequency();

		Long frequencyTypeId = createServiceResponse.getEntityId();

		DataServiceResponse response = manager.deleteFrequency(frequencyTypeId, getTenantName());
		return response;

	}

	@Test
	public void testSearchFrequencyById() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchFrequencyById();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchFrequencyById() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createFrequency();
		Long frequencyId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchFrequency(frequencyId, getTenantName());

		return resp;

	}

	public SearchServiceResponse searchFrequencyById(Long frequencyId) throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchFrequency(frequencyId, getTenantName());

		return resp;

	}

	@Test
	public void testSearchAllFrequencies() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllFrequencies();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAllFrequencies() throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchAllFrequencies(0L, 0L, getTenantName());

		return resp;

	}

}
