package com.accenture.sdp.csm.test.packageprice;

import org.junit.Test;

import com.accenture.sdp.csm.commons.FlagValue;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpPackagePriceManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.currency.SdpCurrencyManagerTest;
import com.accenture.sdp.csm.test.frequency.SdpFrequencyManagerTest;
import com.accenture.sdp.csm.test.price.SdpPriceCatalogManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpPackagePriceManagerTest extends BaseTestCase {

	SdpPackagePriceManager manager = SdpPackagePriceManager.getInstance();

	@Test
	public void testCreatePackagePrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createPackagePrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createPackagePrice() throws PropertyNotFoundException {

		SdpPriceCatalogManagerTest priceCatalogManagerTest = new SdpPriceCatalogManagerTest();

		SdpCurrencyManagerTest currencyManagerTest = new SdpCurrencyManagerTest();

		SdpFrequencyManagerTest frequencyManagerTest = new SdpFrequencyManagerTest();

		Long rcPriceCatalogId = priceCatalogManagerTest.createPrice().getEntityId();

		Long nrcPriceCatalogId = priceCatalogManagerTest.createPrice().getEntityId();

		Long rcFrequencyTypeId = frequencyManagerTest.createFrequency().getEntityId();

		Long currencyTypeId = currencyManagerTest.createCurrency().getEntityId();

		CreateServiceResponse response = manager.createPackagePrice(rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId, rcFrequencyTypeId,
				FlagValue.Flag.NO.getValue(), FlagValue.Flag.NO.getValue(), getTenantName());
		return response;
	}

	@Test
	public void testModifyPackagePrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyPackagePrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyPackagePrice() throws PropertyNotFoundException {

		SdpPriceCatalogManagerTest priceCatalogManagerTest = new SdpPriceCatalogManagerTest();

		SdpCurrencyManagerTest currencyManagerTest = new SdpCurrencyManagerTest();

		SdpFrequencyManagerTest frequencyManagerTest = new SdpFrequencyManagerTest();

		Long rcPriceCatalogId = priceCatalogManagerTest.createPrice().getEntityId();

		Long nrcPriceCatalogId = priceCatalogManagerTest.createPrice().getEntityId();

		Long rcFrequencyTypeId = frequencyManagerTest.createFrequency().getEntityId();

		Long currencyTypeId = currencyManagerTest.createCurrency().getEntityId();

		CreateServiceResponse createServiceResponse = createPackagePrice();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifyPackagePrice(createServiceResponse.getEntityId(), rcPriceCatalogId, nrcPriceCatalogId, currencyTypeId,
				rcFrequencyTypeId, FlagValue.Flag.NO.getValue(), FlagValue.Flag.NO.getValue(), getTenantName());

		return response;
	}

	@Test
	public void testDeletePackagePrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deletePackagePrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deletePackagePrice() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createPackagePrice();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long packagePriceId = createServiceResponse.getEntityId();

		DataServiceResponse response2 = manager.deletePackagePrice(packagePriceId, getTenantName());

		return response2;
	}

	@Test
	public void testSearchPackagePrice() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchPackagePrice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchPackagePrice() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createPackagePrice();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long PackagePriceId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchPackagePrice(PackagePriceId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchPackagePrice(Long PackagePriceId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchPackagePrice(PackagePriceId, getTenantName());

		return searchServiceResponse;
	}

}
