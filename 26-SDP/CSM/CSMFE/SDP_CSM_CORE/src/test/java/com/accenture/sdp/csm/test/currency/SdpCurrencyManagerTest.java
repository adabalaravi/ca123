package com.accenture.sdp.csm.test.currency;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.RefCurrencyTypeManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpCurrencyManagerTest extends BaseTestCase {

	RefCurrencyTypeManager manager = RefCurrencyTypeManager.getInstance();

	@Test
	public void testCreateCurrency() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createCurrency();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createCurrency() throws PropertyNotFoundException {

		String currencyName = Utils.getRandomName(3);

		CreateServiceResponse response = manager.createCurrency(currencyName, getTenantName());
		return response;

	}

	@Test
	public void testDeleteCurrency() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteCurrency();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteCurrency() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createCurrency();

		Long currencyId = createServiceResponse.getEntityId();

		DataServiceResponse response = manager.deleteCurrency(currencyId, getTenantName());

		return response;

	}

	@Test
	public void testSearchCurrencyById() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchCurrencyById();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchCurrencyById() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createCurrency();
		Long currencyId = createServiceResponse.getEntityId();

		SearchServiceResponse resp = manager.searchCurrency(currencyId, getTenantName());

		return resp;

	}

	public SearchServiceResponse searchCurrencyById(Long currencyId) throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchCurrency(currencyId, getTenantName());

		return resp;

	}

	@Test
	public void testSearchAllCurrencies() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllCurrencies();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchServiceResponse searchAllCurrencies() throws PropertyNotFoundException {

		SearchServiceResponse resp = manager.searchAllCurrencies(0L, 0L, getTenantName());

		return resp;

	}

}
