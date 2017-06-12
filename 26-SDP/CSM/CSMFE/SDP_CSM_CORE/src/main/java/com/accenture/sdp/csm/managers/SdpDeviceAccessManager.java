package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.commons.DeviceEnums.Filter;
import com.accenture.sdp.csm.commons.DeviceEnums.Item;
import com.accenture.sdp.csm.commons.DeviceEnums.Operation;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationBLRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceAccessResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpDeviceAccessHelper;
import com.accenture.sdp.csm.helpers.SdpDeviceHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.metering.MeteringManager;
import com.accenture.sdp.csm.metering.MeteringManager.EventTypeEnum;
import com.accenture.sdp.csm.model.jpa.RefDeviceBrand;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.RefDeviceModel;
import com.accenture.sdp.csm.model.jpa.SdpDevice;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpDeviceAccessManager extends SdpBaseManager {

	private SdpDeviceAccessManager() {
		super();
	}

	private static SdpDeviceAccessManager instance;

	public static SdpDeviceAccessManager getInstance() {
		if (instance == null) {
			instance = new SdpDeviceAccessManager();
		}
		return instance;
	}

	public DataServiceResponse manageBlackList(List<SdpDeviceAccessOperationBLRequestDto> operations, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("operation", operations);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		MeteringManager mm = new MeteringManager(tenantName);
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// let's do the magic
			manageBlackList(operations, Utilities.getCurrentClassAndMethod(), em, mm);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			mm.commit(resp);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_INSERT_FAILED);
			mm.rollBack(resp);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
			mm.rollBack(resp);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
			mm.rollBack(resp);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	void manageBlackList(List<SdpDeviceAccessOperationBLRequestDto> operations, String doneBy, EntityManager em, MeteringManager mm)
			throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceAccessHelper.getInstance().validateDeviceAccessBLMgmt(operations);
		for (SdpDeviceAccessOperationBLRequestDto dto : operations) {
			manageBlackList(dto.getId(), dto.getItemType(), dto.getOpType(), dto.getReason(), doneBy, em, mm);
		}
	}

	void manageBlackList(String id, String itemType, String opType, String reason, String doneBy, EntityManager em, MeteringManager mm)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceAccessHelper helper = SdpDeviceAccessHelper.getInstance();
		Item item = DeviceEnums.getItem(itemType);
		Operation operation = DeviceEnums.getOperation(opType);
		BlackWhiteListableEntity entity = null;
		// START METERING EVENT solo per party e device
		if (item.equals(Item.USER) || item.equals(Item.DEVICE)) {
			switch (operation) {
			case ADD:
				mm.startEvent(EventTypeEnum.ADD_TO_BL);
				break;
			case REMOVE:
				mm.startEvent(EventTypeEnum.DEL_FROM_BL);
				break;
			}
		}
		// recupero entity in base ad item
		switch (item) {
		case USER:
			Long partyId = helper.validateLongId(id);
			SdpParty party = SdpPartyHelper.getInstance().searchPartyById(partyId, em);
			if (party != null) {
				if (party.getSdpPartyDeviceExt() == null) {
					// bonifica
					SdpPartyDeviceManager.getInstance().createPartyDeviceExt(partyId, doneBy, em);
				}
				entity = party.getSdpPartyDeviceExt();
			}
			// event id
			mm.getLastEvent().setPartyId(partyId);
			break;
		case DEVICE:
			entity = SdpDeviceHelper.getInstance().searchDeviceById(id, em);
			// event id
			mm.getLastEvent().setDeviceUUID(id);
			break;
		case DEVICE_CHANNEL:
			Long channelId = helper.validateLongId(id);
			entity = SdpDeviceHelper.getInstance().searchDeviceChannelById(channelId, em);
			break;
		case DEVICE_BRAND:
			Long brandId = helper.validateLongId(id);
			entity = SdpDeviceHelper.getInstance().searchDeviceBrandById(brandId, em);
			break;
		case DEVICE_MODEL:
			Long modelId = helper.validateLongId(id);
			entity = SdpDeviceHelper.getInstance().searchDeviceModelById(modelId, em);
			break;
		}
		if (entity == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ID, id);
		}
		switch (operation) {
		case ADD:
			helper.manageBlackList(entity, true, reason, doneBy);
			break;
		case REMOVE:
			helper.manageBlackList(entity, false, null, doneBy);
			break;
		}
		mm.flush();
	}

	public DataServiceResponse manageWhiteList(List<SdpDeviceAccessOperationRequestDto> operations, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("operation", operations);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		MeteringManager mm = new MeteringManager(tenantName);
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// let's do the magic
			manageWhiteList(operations, Utilities.getCurrentClassAndMethod(), em, mm);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			mm.commit(resp);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_INSERT_FAILED);
			mm.rollBack(resp);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
			mm.rollBack(resp);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
			mm.rollBack(resp);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	void manageWhiteList(List<SdpDeviceAccessOperationRequestDto> operations, String doneBy, EntityManager em, MeteringManager mm) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceAccessHelper.getInstance().validateDeviceAccessMgmt(operations);
		for (SdpDeviceAccessOperationRequestDto dto : operations) {
			manageWhiteList(dto.getId(), dto.getItemType(), dto.getOpType(), doneBy, em, mm);
		}
	}

	void manageWhiteList(String id, String itemType, String opType, String doneBy, EntityManager em, MeteringManager mm) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceAccessHelper helper = SdpDeviceAccessHelper.getInstance();
		Item item = DeviceEnums.getItem(itemType);
		Operation operation = DeviceEnums.getOperation(opType);
		BlackWhiteListableEntity entity = null;
		// START METERING EVENT solo per party e device
		if (item.equals(Item.USER) || item.equals(Item.DEVICE)) {
			switch (operation) {
			case ADD:
				mm.startEvent(EventTypeEnum.ADD_TO_WL);
				break;
			case REMOVE:
				mm.startEvent(EventTypeEnum.DEL_FROM_WL);
				break;
			}
		}
		switch (item) {
		case USER:
			Long partyId = helper.validateLongId(id);
			SdpParty party = SdpPartyHelper.getInstance().searchPartyById(partyId, em);
			if (party != null) {
				if (party.getSdpPartyDeviceExt() == null) {
					// bonifica
					SdpPartyDeviceManager.getInstance().createPartyDeviceExt(partyId, doneBy, em);
				}
				entity = party.getSdpPartyDeviceExt();
			}
			mm.getLastEvent().setPartyId(partyId);
			break;
		case DEVICE:
			entity = SdpDeviceHelper.getInstance().searchDeviceById(id, em);
			mm.getLastEvent().setDeviceUUID(id);
			break;
		case DEVICE_CHANNEL:
			Long channelId = helper.validateLongId(id);
			entity = SdpDeviceHelper.getInstance().searchDeviceChannelById(channelId, em);
			break;
		case DEVICE_BRAND:
			Long brandId = helper.validateLongId(id);
			entity = SdpDeviceHelper.getInstance().searchDeviceBrandById(brandId, em);
			break;
		case DEVICE_MODEL:
			Long modelId = helper.validateLongId(id);
			entity = SdpDeviceHelper.getInstance().searchDeviceModelById(modelId, em);
			break;
		}
		if (entity == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ID, id);
		}
		switch (operation) {
		case ADD:
			helper.manageWhiteList(entity, true, doneBy);
			break;
		case REMOVE:
			helper.manageWhiteList(entity, false, doneBy);
			break;
		}
		mm.flush();
	}

	public SearchServiceResponse getBlackList(String filter, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("filter", filter);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpDeviceAccessHelper helper = SdpDeviceAccessHelper.getInstance();
			helper.validateFilter(filter);
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			// let's do the magic
			Filter filterEn = DeviceEnums.getFilter(filter);
			List<SdpDeviceAccessResponseDto> results = new ArrayList<SdpDeviceAccessResponseDto>();
			if (filterEn.equals(Filter.USER) || filterEn.equals(Filter.ALL)) {
				List<SdpPartyDeviceExt> beans = helper.searchPartyDeviceExtByBlacklist(true, em);
				results.addAll(BeanConverter.convertPartyToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE) || filterEn.equals(Filter.ALL)) {
				List<SdpDevice> beans = helper.searchDeviceByBlacklist(true, em);
				results.addAll(BeanConverter.convertDeviceToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE_CHANNEL) || filterEn.equals(Filter.ALL)) {
				List<RefDeviceChannel> beans = helper.searchDeviceChannelByBlacklist(true, em);
				results.addAll(BeanConverter.convertDeviceChannelToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE_BRAND) || filterEn.equals(Filter.ALL)) {
				List<RefDeviceBrand> beans = helper.searchDeviceBrandByBlacklist(true, em);
				results.addAll(BeanConverter.convertDeviceBrandToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE_MODEL) || filterEn.equals(Filter.ALL)) {
				List<RefDeviceModel> beans = helper.searchDeviceModelByBlacklist(true, em);
				results.addAll(BeanConverter.convertDeviceModelToDeviceAccess(beans));
			}
			if (results.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(results);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public SearchServiceResponse getWhiteList(String filter, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("filter", filter);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpDeviceAccessHelper helper = SdpDeviceAccessHelper.getInstance();
			helper.validateFilter(filter);
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			// let's do the magic
			Filter filterEn = DeviceEnums.getFilter(filter);
			List<SdpDeviceAccessResponseDto> results = new ArrayList<SdpDeviceAccessResponseDto>();
			if (filterEn.equals(Filter.USER) || filterEn.equals(Filter.ALL)) {
				List<SdpPartyDeviceExt> beans = helper.searchPartyDeviceExtByWhitelist(true, em);
				results.addAll(BeanConverter.convertPartyToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE) || filterEn.equals(Filter.ALL)) {
				List<SdpDevice> beans = helper.searchDeviceByWhitelist(true, em);
				results.addAll(BeanConverter.convertDeviceToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE_CHANNEL) || filterEn.equals(Filter.ALL)) {
				List<RefDeviceChannel> beans = helper.searchDeviceChannelByWhitelist(true, em);
				results.addAll(BeanConverter.convertDeviceChannelToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE_BRAND) || filterEn.equals(Filter.ALL)) {
				List<RefDeviceBrand> beans = helper.searchDeviceBrandByWhitelist(true, em);
				results.addAll(BeanConverter.convertDeviceBrandToDeviceAccess(beans));
			}
			if (filterEn.equals(Filter.DEVICE_MODEL) || filterEn.equals(Filter.ALL)) {
				List<RefDeviceModel> beans = helper.searchDeviceModelByWhitelist(true, em);
				results.addAll(BeanConverter.convertDeviceModelToDeviceAccess(beans));
			}
			if (results.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(results);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

}
