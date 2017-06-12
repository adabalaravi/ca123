package com.accenture.sdp.csm.test.device;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationBLRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceBrandResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceChannelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceModelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDevicePolicyResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.managers.SdpDeviceAccessManager;
import com.accenture.sdp.csm.managers.SdpDeviceManager;
import com.accenture.sdp.csm.managers.SdpDevicePolicyManager;
import com.accenture.sdp.csm.model.jpa.RefDeviceBrand;
import com.accenture.sdp.csm.model.jpa.RefDeviceModel;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;

public class DeviceConfigurator {

	private DeviceConfigurator() {
	}

	public static List<String> getChannels(String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse resp = SdpDeviceManager.getInstance().searchAllDeviceChannels(tenantName);
		List<SdpDeviceChannelResponseDto> results = (List<SdpDeviceChannelResponseDto>) resp.getSearchResult();
		List<String> channels = new ArrayList<String>();
		for (SdpDeviceChannelResponseDto result : results) {
			channels.add(result.getDeviceChannelName());
		}
		return channels;
	}

	public static SdpDevicePolicyResponseDto getPolicyRandom(String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse resp = SdpDevicePolicyManager.getInstance().searchAllDevicePolicies(tenantName);
		// prendo il primo che capita, che non sia il default
		if (resp.getSearchResult() != null) {
			for (SdpDevicePolicyResponseDto dto : (List<SdpDevicePolicyResponseDto>) resp.getSearchResult()) {
				if (!dto.getIsDefaultPolicy()) {
					return dto;
				}
			}
		}
		throw new RuntimeException("No policy available");
	}

	public static SdpDevicePolicyResponseDto getPolicyDefault(String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse resp = SdpDevicePolicyManager.getInstance().searchAllDevicePolicies(tenantName);
		// prendo il primo che capita, che non sia il default
		if (resp.getSearchResult() != null) {
			for (SdpDevicePolicyResponseDto dto : (List<SdpDevicePolicyResponseDto>) resp.getSearchResult()) {
				if (dto.getIsDefaultPolicy()) {
					return dto;
				}
			}
		}
		throw new RuntimeException("No default policy available");
	}

	public static SdpDeviceChannelResponseDto getChannelRandom(String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse resp = SdpDeviceManager.getInstance().searchAllDeviceChannels(tenantName);
		// prendo uno a caso
		if (resp.getSearchResult() != null && !resp.getSearchResult().isEmpty()) {
			List<SdpDeviceChannelResponseDto> results = (List<SdpDeviceChannelResponseDto>) resp.getSearchResult();
			return results.get(Utils.getRandomInt(results.size()));
		}
		throw new RuntimeException("No channel available");
	}

	public static SdpDeviceBrandResponseDto getBrandRandom(String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse resp = SdpDeviceManager.getInstance().searchAllDeviceBrands(tenantName);
		// prendo uno a caso
		if (resp.getSearchResult() != null && !resp.getSearchResult().isEmpty()) {
			List<SdpDeviceBrandResponseDto> results = (List<SdpDeviceBrandResponseDto>) resp.getSearchResult();
			return results.get(Utils.getRandomInt(results.size()));
		}
		throw new RuntimeException("No brand available");
	}

	public static SdpDeviceModelResponseDto getModelRandom(String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse brandResp = SdpDeviceManager.getInstance().searchAllDeviceBrands(tenantName);
		List<SdpDeviceBrandResponseDto> brands = (List<SdpDeviceBrandResponseDto>) brandResp.getSearchResult();
		// controlla tutti i brand finche' non trova un modello
		for (SdpDeviceBrandResponseDto brand : brands) {
			SearchServiceResponse resp = SdpDeviceManager.getInstance().searchDeviceModelsByBrand(brand.getBrandId(), tenantName);
			// prendo uno a caso
			if (resp.getSearchResult() != null && !resp.getSearchResult().isEmpty()) {
				List<SdpDeviceModelResponseDto> results = (List<SdpDeviceModelResponseDto>) resp.getSearchResult();
				return results.get(Utils.getRandomInt(results.size()));
			}
		}
		throw new RuntimeException("No model available");
	}

	public static Long createBrand(String brandName, String tenantName) {
		EntityManager em = null;
		EntityTransaction tx = null;
		Long brandId = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			tx = em.getTransaction();
			tx.begin();
			RefDeviceBrand brand = new RefDeviceBrand();
			brand.setIsBl((byte) 0);
			brand.setIsWl((byte) 0);
			brand.setCreatedById(TestConstants.TEST_BEANS_PREFIX);
			brand.setDeviceBrandName(brandName);
			em.persist(brand);
			tx.commit();
			brandId = brand.getDeviceBrandId();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return brandId;
	}

	public static Long createModel(String modelName, Long brandId, String tenantName) {
		EntityManager em = null;
		EntityTransaction tx = null;
		Long modelId = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			tx = em.getTransaction();
			tx.begin();
			RefDeviceModel model = new RefDeviceModel();
			model.setIsBl((byte) 0);
			model.setIsWl((byte) 0);
			model.setCreatedById(TestConstants.TEST_BEANS_PREFIX);
			model.setDeviceModelName(modelName);
			model.setRefDeviceBrand(em.find(RefDeviceBrand.class, brandId));
			em.persist(model);
			tx.commit();
			modelId = model.getDeviceModelId();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return modelId;
	}
	
	public static void resetPartyDevice(Long partyId, String tenantName) {
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			tx = em.getTransaction();
			tx.begin();
			SdpParty party = SdpPartyHelper.getInstance().searchPartyById(partyId, em);
			party.setSdpPartyDeviceExt(null);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("unable to reset party device");
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
	
	public static void blacklistParty(Long partyId, String tenantName) throws PropertyNotFoundException {
		List<SdpDeviceAccessOperationBLRequestDto> operations =  new ArrayList<SdpDeviceAccessOperationBLRequestDto>();
		SdpDeviceAccessOperationBLRequestDto operation = new SdpDeviceAccessOperationBLRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.USER.getValue());
		operation.setId(String.valueOf(partyId));
		operation.setOpType(DeviceEnums.Operation.ADD.getValue());
		operation.setReason(Utils.getRandomName(20));
		SdpDeviceAccessManager.getInstance().manageBlackList(operations, tenantName);
	}
	
	public static void blacklistDevice(String deviceUuid, String tenantName) throws PropertyNotFoundException {
		List<SdpDeviceAccessOperationBLRequestDto> operations =  new ArrayList<SdpDeviceAccessOperationBLRequestDto>();
		SdpDeviceAccessOperationBLRequestDto operation = new SdpDeviceAccessOperationBLRequestDto();
		operations.add(operation);
		operation.setItemType(DeviceEnums.Item.DEVICE.getValue());
		operation.setId(String.valueOf(deviceUuid));
		operation.setOpType(DeviceEnums.Operation.ADD.getValue());
		operation.setReason(Utils.getRandomName(20));
		SdpDeviceAccessManager.getInstance().manageBlackList(operations, tenantName);
	}
}
