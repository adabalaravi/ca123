package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationBLRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefDeviceBrand;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.RefDeviceModel;
import com.accenture.sdp.csm.model.jpa.SdpDevice;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.model.jpa.common.BlackWhiteListableEntity;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpDeviceAccessHelper extends SdpBaseHelper {

	private static SdpDeviceAccessHelper instance;

	private SdpDeviceAccessHelper() {
		super();
	}

	public static SdpDeviceAccessHelper getInstance() {
		if (instance == null) {
			instance = new SdpDeviceAccessHelper();
		}
		return instance;
	}

	public void validateDeviceAccessMgmt(List<SdpDeviceAccessOperationRequestDto> operations) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (operations == null || operations.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "operations");
		}
		for (SdpDeviceAccessOperationRequestDto dto : operations) {
			validateDeviceAccessMgmt(dto.getId(), dto.getItemType(), dto.getOpType());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateDeviceAccessBLMgmt(List<SdpDeviceAccessOperationBLRequestDto> operations) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (operations == null || operations.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "operations");
		}
		for (SdpDeviceAccessOperationBLRequestDto dto : operations) {
			validateDeviceAccessMgmt(dto.getId(), dto.getItemType(), dto.getOpType());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateDeviceAccessMgmt(String id, String itemType, String opType) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(ID, id);
		validationMap.put("itemType", itemType);
		validationMap.put("opType", opType);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("Validating Operation");
		res = ValidationUtils.validateDeviceAccess(opType, itemType);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
	}

	public void validateFilter(String filter) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("filter", filter);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("Validating Filter");
		res = ValidationUtils.validateFilter(filter);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public Long validateLongId(String id) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		Long result = Utilities.parseLong(id);
		if (result == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ID, id);
		}
		return result;
	}

	public void manageBlackList(BlackWhiteListableEntity item, boolean blackList, String blReason, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// operazione solo se cambia lo stato
		if (blackList != BooleanConverter.getBooleanValue(item.getIsBl())) {
			item.setIsBl(BooleanConverter.getByteValue(blackList));
			item.setBlReason(blReason);
			// whitelist in ogni caso e' false
			item.setIsWl(BooleanConverter.getByteValue(false));
			item.setUpdatedById(updatedBy);
			item.setUpdatedDate(new Date());
		}
	}

	public void manageWhiteList(BlackWhiteListableEntity item, boolean whiteList, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// operazione solo se cambia lo stato
		if (whiteList != BooleanConverter.getBooleanValue(item.getIsWl())) {
			item.setIsWl(BooleanConverter.getByteValue(whiteList));
			// blackList in ogni caso e' false
			item.setIsBl(BooleanConverter.getByteValue(false));
			item.setBlReason(null);
			item.setUpdatedById(updatedBy);
			item.setUpdatedDate(new Date());
		}
	}

	public List<SdpPartyDeviceExt> searchPartyDeviceExtByBlacklist(boolean isBl, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpPartyDeviceExt.PARAM_IS_BL, BooleanConverter.getByteValue(isBl));
		return NamedQueryHelper.executeNamedQuery(SdpPartyDeviceExt.class, SdpPartyDeviceExt.QUERY_RETRIEVE_BY_BLACKLIST, parameHashMap, em);
	}

	public List<SdpPartyDeviceExt> searchPartyDeviceExtByWhitelist(boolean isWl, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpPartyDeviceExt.PARAM_IS_WL, BooleanConverter.getByteValue(isWl));
		return NamedQueryHelper.executeNamedQuery(SdpPartyDeviceExt.class, SdpPartyDeviceExt.QUERY_RETRIEVE_BY_WHITELIST, parameHashMap, em);
	}

	public List<SdpDevice> searchDeviceByBlacklist(boolean isBl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpDevice.PARAM_IS_BL, BooleanConverter.getByteValue(isBl));
		return NamedQueryHelper.executeNamedQuery(SdpDevice.class, SdpDevice.QUERY_RETRIEVE_BY_BLACKLIST, parameHashMap, em);
	}

	public List<SdpDevice> searchDeviceByWhitelist(boolean isWl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpDevice.PARAM_IS_WL, BooleanConverter.getByteValue(isWl));
		return NamedQueryHelper.executeNamedQuery(SdpDevice.class, SdpDevice.QUERY_RETRIEVE_BY_WHITELIST, parameHashMap, em);
	}

	public List<RefDeviceChannel> searchDeviceChannelByBlacklist(boolean isBl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceChannel.PARAM_IS_BL, BooleanConverter.getByteValue(isBl));
		return NamedQueryHelper.executeNamedQuery(RefDeviceChannel.class, RefDeviceChannel.QUERY_RETRIEVE_BY_BLACKLIST, parameHashMap, em);
	}

	public List<RefDeviceChannel> searchDeviceChannelByWhitelist(boolean isWl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceChannel.PARAM_IS_WL, BooleanConverter.getByteValue(isWl));
		return NamedQueryHelper.executeNamedQuery(RefDeviceChannel.class, RefDeviceChannel.QUERY_RETRIEVE_BY_WHITELIST, parameHashMap, em);
	}

	public List<RefDeviceBrand> searchDeviceBrandByBlacklist(boolean isBl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceBrand.PARAM_IS_BL, BooleanConverter.getByteValue(isBl));
		return NamedQueryHelper.executeNamedQuery(RefDeviceBrand.class, RefDeviceBrand.QUERY_RETRIEVE_BY_BLACKLIST, parameHashMap, em);
	}

	public List<RefDeviceBrand> searchDeviceBrandByWhitelist(boolean isWl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceBrand.PARAM_IS_WL, BooleanConverter.getByteValue(isWl));
		return NamedQueryHelper.executeNamedQuery(RefDeviceBrand.class, RefDeviceBrand.QUERY_RETRIEVE_BY_WHITELIST, parameHashMap, em);
	}

	public List<RefDeviceModel> searchDeviceModelByBlacklist(boolean isBl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceModel.PARAM_IS_BL, BooleanConverter.getByteValue(isBl));
		return NamedQueryHelper.executeNamedQuery(RefDeviceModel.class, RefDeviceModel.QUERY_RETRIEVE_BY_BLACKLIST, parameHashMap, em);
	}

	public List<RefDeviceModel> searchDeviceModelByWhitelist(boolean isWl, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceModel.PARAM_IS_WL, BooleanConverter.getByteValue(isWl));
		return NamedQueryHelper.executeNamedQuery(RefDeviceModel.class, RefDeviceModel.QUERY_RETRIEVE_BY_WHITELIST, parameHashMap, em);
	}

}