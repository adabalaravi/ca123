package com.accenture.sdp.csm.test.price;

import java.math.BigDecimal;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpPriceCatalogManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpPriceCatalogManagerTest extends BaseTestCase {

	SdpPriceCatalogManager manager = SdpPriceCatalogManager.getInstance();

	@Test
	public void testCreatePrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createPrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createPrice() throws PropertyNotFoundException {

		BigDecimal price = Utils.getRandomDecimal(100000000);

		CreateServiceResponse response = manager.createPrice(price, getTenantName());
		return response;
	}

	@Test
	public void testDeletePrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deletePrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deletePrice() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createPrice();

		System.out.println(createServiceResponse.getEntityId());

		DataServiceResponse response = manager.deletePrice(createServiceResponse.getEntityId(), getTenantName());
		return response;
	}

	@Test
	public void testSearchPrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPrice() throws PropertyNotFoundException {
		CreateServiceResponse createServiceResponse = createPrice();

		SearchServiceResponse response = manager.searchPrice(createServiceResponse.getEntityId(), getTenantName());
		return response;
	}

	public SearchServiceResponse searchPrice(Long PriceId) throws PropertyNotFoundException {
		SearchServiceResponse response = manager.searchPrice(PriceId, getTenantName());
		return response;
	}

	@Test
	public void testSearchAllPrices() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllPrices();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllPrices() throws PropertyNotFoundException {

		SearchServiceResponse response = manager.searchAllPrices(0L, 0L, getTenantName());
		return response;
	}
}
