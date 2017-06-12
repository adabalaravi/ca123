package com.accenture.sdp.csm.test.device;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.commons.DeviceEnums.Filter;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationBLRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpDeviceAccessManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpDeviceAccessTest extends BaseTestCase {

	SdpDeviceAccessManager manager = SdpDeviceAccessManager.getInstance();

	@Test
	public void testManageBlackList000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = manageBlackList();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testManageBlackList010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// cancello un id a caso
			List<SdpDeviceAccessOperationBLRequestDto> operations = prepareBlackListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setId(null);
			DataServiceResponse resp = manageBlackList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// cancello un type a caso
			operations = prepareBlackListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setItemType(null);
			resp = manageBlackList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// cancello un optype a caso
			operations = prepareBlackListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setOpType(null);
			resp = manageBlackList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testManageBlackList100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// sposto un id a caso
			List<SdpDeviceAccessOperationBLRequestDto> operations = prepareBlackListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setId(String.valueOf(Utils.getGreaterRandomLong(1000000000)));
			DataServiceResponse resp = manageBlackList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse manageBlackList() throws PropertyNotFoundException {
		return manageBlackList(prepareBlackListOperations());
	}

	public DataServiceResponse manageBlackList(List<SdpDeviceAccessOperationBLRequestDto> operations) throws PropertyNotFoundException {
		return manager.manageBlackList(operations, getTenantName());
	}

	private List<SdpDeviceAccessOperationBLRequestDto> prepareBlackListOperations() throws PropertyNotFoundException {
		List<SdpDeviceAccessOperationBLRequestDto> operations = new ArrayList<SdpDeviceAccessOperationBLRequestDto>();
		// ne metto uno di tutti
		SdpDeviceAccessOperationBLRequestDto operation = new SdpDeviceAccessOperationBLRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.USER.getValue());
		operation.setId(String.valueOf(getRandomPartyId()));
		prepareBlackListOperation(operation);
		// FIXME tranne device, perche' non si puo' creare
		// operation = new SdpDeviceAccessOperationBLRequestDto();
		// operations.add(operation);
		// operation.setItemType(DeviceEnums.Item.DEVICE.getValue());
		// operation.setId(???);
		// prepareBlackListOperation(operation);
		operation = new SdpDeviceAccessOperationBLRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE_CHANNEL.getValue());
		operation.setId(String.valueOf(DeviceConfigurator.getChannelRandom(tenantName).getDeviceChannelId()));
		prepareBlackListOperation(operation);
		operation = new SdpDeviceAccessOperationBLRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE_MODEL.getValue());
		operation.setId(String.valueOf(DeviceConfigurator.getModelRandom(tenantName).getModelId()));
		prepareBlackListOperation(operation);
		operation = new SdpDeviceAccessOperationBLRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE_BRAND.getValue());
		operation.setId(String.valueOf(DeviceConfigurator.getBrandRandom(tenantName).getBrandId()));
		prepareBlackListOperation(operation);
		return operations;
	}

	private void prepareBlackListOperation(SdpDeviceAccessOperationBLRequestDto operation) {
		// casuale aggiunge o rimuove
		if (Utils.coinFlip()) {
			operation.setOpType(DeviceEnums.Operation.ADD.getValue());
			operation.setReason(Utils.getRandomName(20));
		} else {
			operation.setOpType(DeviceEnums.Operation.REMOVE.getValue());
		}
	}

	@Test
	public void testManageWhiteList000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = manageWhiteList();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testManageWhiteList010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// cancello un id a caso
			List<SdpDeviceAccessOperationRequestDto> operations = prepareWhiteListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setId(null);
			DataServiceResponse resp = manageWhiteList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// cancello un type a caso
			operations = prepareWhiteListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setItemType(null);
			resp = manageWhiteList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// cancello un optype a caso
			operations = prepareWhiteListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setOpType(null);
			resp = manageWhiteList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testManageWhiteList100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// sposto un id a caso
			List<SdpDeviceAccessOperationRequestDto> operations = prepareWhiteListOperations();
			operations.get(Utils.getRandomInt(operations.size())).setId(String.valueOf(Utils.getGreaterRandomLong(1000000000)));
			DataServiceResponse resp = manageWhiteList(operations);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse manageWhiteList() throws PropertyNotFoundException {
		return manageWhiteList(prepareWhiteListOperations());
	}

	public DataServiceResponse manageWhiteList(List<SdpDeviceAccessOperationRequestDto> operations) throws PropertyNotFoundException {
		return manager.manageWhiteList(operations, getTenantName());
	}

	private List<SdpDeviceAccessOperationRequestDto> prepareWhiteListOperations() throws PropertyNotFoundException {
		List<SdpDeviceAccessOperationRequestDto> operations = new ArrayList<SdpDeviceAccessOperationRequestDto>();
		// ne metto uno di tutti
		SdpDeviceAccessOperationRequestDto operation = new SdpDeviceAccessOperationRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.USER.getValue());
		operation.setId(String.valueOf(getRandomPartyId()));
		prepareWhiteListOperation(operation);
		// FIXME tranne device, perche' non si puo' creare
		// operation = new SdpDeviceAccessOperationRequestDto();
		// operations.add(operation);
		// operation.setItemType(DeviceEnums.Item.DEVICE.getValue());
		// operation.setId(???);
		// prepareWhiteListOperation(operation);
		operation = new SdpDeviceAccessOperationRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE_CHANNEL.getValue());
		operation.setId(String.valueOf(DeviceConfigurator.getChannelRandom(tenantName).getDeviceChannelId()));
		prepareWhiteListOperation(operation);
		operation = new SdpDeviceAccessOperationRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE_MODEL.getValue());
		operation.setId(String.valueOf(DeviceConfigurator.getModelRandom(tenantName).getModelId()));
		prepareWhiteListOperation(operation);
		operation = new SdpDeviceAccessOperationRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE_BRAND.getValue());
		operation.setId(String.valueOf(DeviceConfigurator.getBrandRandom(tenantName).getBrandId()));
		prepareWhiteListOperation(operation);
		return operations;
	}

	private void prepareWhiteListOperation(SdpDeviceAccessOperationRequestDto operation) {
		// casuale aggiunge o rimuove
		if (Utils.coinFlip()) {
			operation.setOpType(DeviceEnums.Operation.ADD.getValue());
		} else {
			operation.setOpType(DeviceEnums.Operation.REMOVE.getValue());
		}
	}

	@Test
	public void testGetBlackList000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// metto solo filtro ALL altrimenti con altri rischio zero found
			DataServiceResponse resp = getBlackList(Filter.ALL.getValue());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetBlackList010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = getBlackList(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetBlackList020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = getBlackList(Utils.getRandomName(6));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse getBlackList(String filter) throws PropertyNotFoundException {
		return manager.getBlackList(filter, getTenantName());
	}
	
	@Test
	public void testGetWhiteList000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// metto solo filtro ALL altrimenti con altri rischio zero found
			DataServiceResponse resp = getWhiteList(Filter.ALL.getValue());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetWhiteList010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = getWhiteList(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetWhiteList020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = getWhiteList(Utils.getRandomName(6));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse getWhiteList(String filter) throws PropertyNotFoundException {
		return manager.getWhiteList(filter, getTenantName());
	}
}
