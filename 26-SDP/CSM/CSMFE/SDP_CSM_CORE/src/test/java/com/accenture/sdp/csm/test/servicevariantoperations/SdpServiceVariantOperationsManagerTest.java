package com.accenture.sdp.csm.test.servicevariantoperations;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpServiceVariantOperationsManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.servicevariant.SdpServiceVariantManagerTest;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpServiceVariantOperationsManagerTest extends BaseTestCase {

	SdpServiceVariantOperationsManager manager = SdpServiceVariantOperationsManager.getInstance();

	@Test
	public void testCreateServiceVariantOperation() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createServiceVariantOperation();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createServiceVariantOperation() throws PropertyNotFoundException {

		Long serviceVariantId = 1L;

		SdpServiceVariantManagerTest serviceVariantManagerTest = new SdpServiceVariantManagerTest();
		SearchServiceResponse searchServiceResponse = serviceVariantManagerTest.searchServiceVariant(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		String methodName = Utils.getRandomName(10);
		String inputParameters = "";
		String inputXslt = null;
		String outputXslt = null;
		String uddiKey = null;
		CreateServiceResponse response = manager.createSdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt, outputXslt,
				uddiKey, null, getTenantName());
		return response;

	}

	@Test
	public void testModifyServiceVariantOperation() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifyServiceVariantOperation();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifyServiceVariantOperation() throws PropertyNotFoundException {

		Long serviceVariantId = 1L;

		SdpServiceVariantManagerTest serviceVariantManagerTest = new SdpServiceVariantManagerTest();
		SearchServiceResponse searchServiceResponse = serviceVariantManagerTest.searchServiceVariant(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		String methodName = Utils.getRandomName(10);
		String inputParameters = "";
		String inputXslt = null;
		String outputXslt = null;
		String uddiKey = null;
		CreateServiceResponse createServiceResponse = manager.createSdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt,
				outputXslt, uddiKey, null, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response = manager.modifySdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt, outputXslt, uddiKey,
				null, getTenantName());
		return response;
	}

	@Test
	public void testc_deleteSdpServiceVariantOperation() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = c_deleteSdpServiceVariantOperation();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse c_deleteSdpServiceVariantOperation() throws PropertyNotFoundException {

		Long serviceVariantId = 1L;

		SdpServiceVariantManagerTest serviceVariantManagerTest = new SdpServiceVariantManagerTest();
		SearchServiceResponse searchServiceResponse = serviceVariantManagerTest.searchServiceVariant(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		String methodName = Utils.getRandomName(10);
		String inputParameters = "";
		String inputXslt = null;
		String outputXslt = null;
		String uddiKey = null;
		CreateServiceResponse createServiceResponse = manager.createSdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt,
				outputXslt, uddiKey, null, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		DataServiceResponse response2 = manager.deleteSdpServiceVariantOperation(serviceVariantId, methodName, getTenantName());

		return response2;
	}

	@Test
	public void testSearchServiceVariantOperation() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchServiceVariantOperation();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchServiceVariantOperation() throws PropertyNotFoundException {

		Long serviceVariantId = 1L;

		SdpServiceVariantManagerTest serviceVariantManagerTest = new SdpServiceVariantManagerTest();
		SearchServiceResponse searchServiceResponse = serviceVariantManagerTest.searchServiceVariant(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		String methodName = Utils.getRandomName(10);
		String inputParameters = "";
		String inputXslt = null;
		String outputXslt = null;
		String uddiKey = null;
		CreateServiceResponse createServiceResponse = manager.createSdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt,
				outputXslt, uddiKey, null, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		searchServiceResponse = manager.searchSdpServiceVariantOperationById(serviceVariantId, methodName, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchSdpServiceVariantOperationByName() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSdpServiceVariantOperationByName();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchSdpServiceVariantOperationByName() throws PropertyNotFoundException {

		Long serviceVariantId = 1L;

		SdpServiceVariantManagerTest serviceVariantManagerTest = new SdpServiceVariantManagerTest();
		SearchServiceResponse searchServiceResponse = serviceVariantManagerTest.searchServiceVariant(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		SdpServiceVariantDto serviceVariantDto = (SdpServiceVariantDto) searchServiceResponse.getSearchResult().get(0);

		String serviceVariantName = serviceVariantDto.getServiceVariantName();

		String methodName = Utils.getRandomName(10);
		String inputParameters = "";
		String inputXslt = null;
		String outputXslt = null;
		String uddiKey = null;
		CreateServiceResponse createServiceResponse = manager.createSdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt,
				outputXslt, uddiKey, null, getTenantName());
		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		searchServiceResponse = manager.searchSdpServiceVariantOperationByName(serviceVariantName, methodName, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchSdpServiceVariantOperationByServiceVariantId() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSdpServiceVariantOperationByServiceVariantId();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchSdpServiceVariantOperationByServiceVariantId() throws PropertyNotFoundException {

		Long serviceVariantId = 1L;

		SdpServiceVariantManagerTest serviceVariantManagerTest = new SdpServiceVariantManagerTest();
		SearchServiceResponse searchServiceResponse = serviceVariantManagerTest.searchServiceVariant(1L);
		assertTrue(searchServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		searchServiceResponse = manager.searchSdpServiceVariantOperationByServiceVariantId(serviceVariantId, getTenantName());

		return searchServiceResponse;
	}

}
