package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.commons.DeviceEnums.AuthMode;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefDeviceBrand;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.RefDeviceModel;
import com.accenture.sdp.csm.model.jpa.RefDeviceUuidType;
import com.accenture.sdp.csm.model.jpa.SdpDevice;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpDeviceHelper extends SdpBaseHelper {

	private static SdpDeviceHelper instance;

	private SdpDeviceHelper() {
		super();
	}

	public static SdpDeviceHelper getInstance() {
		if (instance == null) {
			instance = new SdpDeviceHelper();
		}
		return instance;
	}

	public void validateSearchDeviceById(String deviceUUID) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(DEVICE_ID, deviceUUID);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchDeviceBrandById(Long brandId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("brandId", brandId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpDevice searchDeviceById(String deviceUUID, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(deviceUUID)) {
			return null;
		}
		return em.find(SdpDevice.class, deviceUUID);
	}

	public RefDeviceChannel searchDeviceChannelById(Long deviceChannelId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (deviceChannelId == null) {
			return null;
		}
		return em.find(RefDeviceChannel.class, deviceChannelId);
	}

	public RefDeviceModel searchDeviceModelById(Long deviceModelId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (deviceModelId == null) {
			return null;
		}
		return em.find(RefDeviceModel.class, deviceModelId);
	}

	public RefDeviceBrand searchDeviceBrandById(Long deviceBrandId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (deviceBrandId == null) {
			return null;
		}
		return em.find(RefDeviceBrand.class, deviceBrandId);
	}

	public RefDeviceUuidType searchDeviceUuidTypeByName(String deviceUuidTypeName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(deviceUuidTypeName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceUuidType.PARAM_NAME, deviceUuidTypeName);
		return NamedQueryHelper.executeNamedQuerySingle(RefDeviceUuidType.class, RefDeviceUuidType.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
	}

	public RefDeviceChannel searchDeviceChannelByName(String deviceChannelName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(deviceChannelName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceChannel.PARAM_NAME, deviceChannelName);
		return NamedQueryHelper.executeNamedQuerySingle(RefDeviceChannel.class, RefDeviceChannel.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
	}

	public RefDeviceModel searchDeviceModelByName(String deviceModelName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(deviceModelName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceModel.PARAM_NAME, deviceModelName);
		return NamedQueryHelper.executeNamedQuerySingle(RefDeviceModel.class, RefDeviceModel.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
	}

	public RefDeviceBrand searchDeviceBrandByName(String deviceBrandName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(deviceBrandName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceBrand.PARAM_NAME, deviceBrandName);
		return NamedQueryHelper.executeNamedQuerySingle(RefDeviceBrand.class, RefDeviceBrand.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
	}

	public List<RefDeviceBrand> searchAllDeviceBrands(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefDeviceBrand.class, RefDeviceBrand.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<RefDeviceModel> searchDeviceModelByBrandId(Long brandId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (brandId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefDeviceModel.PARAM_BRAND_ID, brandId);
		return NamedQueryHelper.executeNamedQuery(RefDeviceModel.class, RefDeviceModel.QUERY_RETRIEVE_BY_BRANDID, parameHashMap, em);
	}

	public List<RefDeviceChannel> searchAllDeviceChannels(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefDeviceChannel.class, RefDeviceChannel.QUERY_RETRIEVE_ALL, null, em);
	}

	public void validateRegisterDevice(String deviceUUID, String deviceUUIDType, String deviceChannel) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(DEVICE_ID, deviceUUID);
		validationMap.put("deviceUUIDType", deviceUUIDType);
		validationMap.put("deviceChannel", deviceChannel);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpDevice createDevice(String deviceUUID, RefDeviceUuidType deviceUuidType, SdpPartyDeviceExt partyExt, RefDeviceChannel channel,
			RefDeviceBrand brand, RefDeviceModel model, String alias, boolean pairEnabled, String createdById, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDevice device = new SdpDevice();
		device.setDeviceUuid(deviceUUID);
		device.setRefDeviceUuidType(deviceUuidType);
		device.setSdpPartyDeviceExt(partyExt);
		device.setRefDeviceChannel(channel);
		device.setRefDeviceBrand(brand);
		device.setRefDeviceModel(model);
		device.setAlias(alias);
		device.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		device.setIsPaired(BooleanConverter.getByteValue(pairEnabled));
		device.setIsBl(BooleanConverter.getByteValue(false));
		device.setIsWl(BooleanConverter.getByteValue(false));
		device.setCreatedById(createdById);
		device.setCreatedDate(new Date());
		em.persist(device);
		return device;
	}

	public void modifyDevice(SdpDevice device, RefDeviceBrand brand, RefDeviceModel model, String alias, String doneBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		device.setAlias(alias);
		device.setRefDeviceBrand(brand);
		device.setRefDeviceModel(model);
		device.setUpdatedById(doneBy);
		device.setUpdatedDate(new Date());
	}

	public void modifyDevice(SdpDevice device, RefDeviceUuidType deviceUuidType, SdpPartyDeviceExt partyExt, RefDeviceChannel channel, RefDeviceBrand brand,
			RefDeviceModel model, String alias, boolean pairEnabled, String doneBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		modifyDevice(device, brand, model, alias, doneBy);
		device.setRefDeviceUuidType(deviceUuidType);
		device.setSdpPartyDeviceExt(partyExt);
		device.setRefDeviceChannel(channel);
		device.setIsPaired(BooleanConverter.getByteValue(pairEnabled));
	}

	public void modifyDevice(SdpDevice device, boolean pairEnabled, String doneBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		device.setIsPaired(BooleanConverter.getByteValue(pairEnabled));
		device.setUpdatedById(doneBy);
		device.setUpdatedDate(new Date());
	}

	public void modifyDevice(SdpDevice device, Date lastFruitionDate, String doneBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		device.setLastFruitionDate(lastFruitionDate);
		device.setUpdatedById(doneBy);
		device.setUpdatedDate(new Date());
	}

	public void validateCheckDeviceAccess(String deviceUUID, String authMode, String username, String password) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(DEVICE_ID, deviceUUID);
		validationMap.put("authMode", authMode);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("Validating authMode");
		res = ValidationUtils.validateAuthMode(authMode);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		// resetto mappa per validare username/pwd
		validationMap.clear();
		validationMap.put(USERNAME, username);
		validationMap.put(PASSWORD, password);
		AuthMode mode = DeviceEnums.getAuthMode(authMode);
		if (mode.equals(AuthMode.BASIC)) {
			// nella basic i parametri sono obbligatori
			res = ValidationUtils.validateMandatoryFields(validationMap);
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
			}
		} else {
			// nella pairing i parametri NON devono essere passati
			res = ValidationUtils.validateForbiddenFields(validationMap);
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateLastFruitionDate(Date lastFruitionDate) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("lastFruitionDate", lastFruitionDate);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		HashMap<String, Date> dateMap = new HashMap<String, Date>();
		validationMap.put("lastFruitionDate", lastFruitionDate);
		res = ValidationUtils.validateDates(dateMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void changeDeviceStatus(SdpDevice device, Long nextstatus, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		device.setStatusId(nextstatus);
		device.setChangeStatusById(updatedBy);
		device.setChangeStatusDate(new Date());
	}

	public void validateDeviceUuid(String deviceUUID, RefDeviceUuidType idType) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (idType.getDeviceUuidTypePattern() != null && !deviceUUID.matches(idType.getDeviceUuidTypePattern())) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, DEVICE_ID, deviceUUID);
		}
		log.logDebug(VALIDATION_END);
	}
}