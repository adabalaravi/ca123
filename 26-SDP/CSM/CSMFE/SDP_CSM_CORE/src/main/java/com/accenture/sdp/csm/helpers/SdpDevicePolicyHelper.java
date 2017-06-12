package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpDevicePolicyConfigRequestDto;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicy;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicyConfig;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicyConfigPK;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpDevicePolicyHelper extends SdpBaseHelper {

	private static SdpDevicePolicyHelper instance;

	private SdpDevicePolicyHelper() {
		super();
	}

	public static SdpDevicePolicyHelper getInstance() {
		if (instance == null) {
			instance = new SdpDevicePolicyHelper();
		}
		return instance;
	}

	public void validateDevicePolicy(String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(POLICY_NAME, policyName);
		validationMap.put("maxAssociationsNumber", maxAssociationsNumber);
		validationMap.put("safetyPeriodDuration", safetyPeriodDuration);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		// verifica positivo
		HashMap<String, Long> positives = new HashMap<String, Long>();
		positives.put("maxAssociationsNumber", maxAssociationsNumber);
		positives.put("safetyPeriodDuration", safetyPeriodDuration);
		res = ValidationUtils.validatePositiveLongFields(positives);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchDevicePolicyById(Long policyId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(POLICY_ID, policyId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateDevicePolicyMaxAllowed(String deviceChannel, Long maximumNumber) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("deviceChannel", deviceChannel);
		validationMap.put("maximumNumber", maximumNumber);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		// verifica positivo
		HashMap<String, Long> positives = new HashMap<String, Long>();
		positives.put("maximumNumber", maximumNumber);
		res = ValidationUtils.validatePositiveLongFields(positives);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateDevicePolicyConfigs(List<SdpDevicePolicyConfigRequestDto> devices) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (devices == null || devices.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "maximumAllowedDevices");
		}
		for (SdpDevicePolicyConfigRequestDto dto : devices) {
			validateDevicePolicyMaxAllowed(dto.getDeviceChannel(), dto.getMaximumNumber());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpDevicePolicy searchDevicePolicyById(Long policyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (policyId == null) {
			return null;
		}
		return em.find(SdpDevicePolicy.class, policyId);
	}

	public SdpDevicePolicy searchDevicePolicyByName(String policyName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(policyName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpDevicePolicy.PARAM_POLICY_NAME, policyName);
		return NamedQueryHelper.executeNamedQuerySingle(SdpDevicePolicy.class, SdpDevicePolicy.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
	}

	public SdpDevicePolicy createDevicePolicy(String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration, String createdById, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDevicePolicy policy = new SdpDevicePolicy();
		// Model valorization
		policy.setPolicyName(policyName);
		policy.setNumberOfAssociations(maxAssociationsNumber);
		policy.setSafetyPeriodDuration(safetyPeriodDuration);
		policy.setCreatedById(createdById);
		policy.setCreatedDate(new Date());
		em.persist(policy);
		return policy;
	}

	public void modifyDevicePolicy(SdpDevicePolicy policy, String policyName, Long maxAssociationsNumber, Long safetyPeriodDuration, String updatedById,
			EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		policy.setPolicyName(policyName);
		policy.setNumberOfAssociations(maxAssociationsNumber);
		policy.setSafetyPeriodDuration(safetyPeriodDuration);
		policy.setUpdatedById(updatedById);
		policy.setUpdatedDate(new Date());
	}

	public SdpDevicePolicyConfig createDevicePolicyConfig(SdpDevicePolicy policy, RefDeviceChannel channel, Long maximumAllowedDevices, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDevicePolicyConfig config = new SdpDevicePolicyConfig();
		SdpDevicePolicyConfigPK id = new SdpDevicePolicyConfigPK();
		id.setDeviceChannelId(channel.getDeviceChannelId());
		id.setPolicyId(policy.getPolicyId());
		config.setId(id);
		// Model valorization
		config.setSdpDevicePolicy(policy);
		config.setRefDeviceChannel(channel);
		config.setMaximumAllowedDevices(maximumAllowedDevices);
		em.persist(config);
		policy.getSdpDevicePolicyConfigs().add(config);
		return config;
	}

	public void updateDevicePolicyConfig(SdpDevicePolicyConfig config, Long maximumAllowedDevices) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		config.setMaximumAllowedDevices(maximumAllowedDevices);
		// no log parameters to update
	}

	public boolean updateDevicePolicyConfig(SdpDevicePolicy policy, RefDeviceChannel channel, Long maximumAllowedDevices) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		for (SdpDevicePolicyConfig config : policy.getSdpDevicePolicyConfigs()) {
			if (channel.equals(config.getRefDeviceChannel())) {
				updateDevicePolicyConfig(config, maximumAllowedDevices);
				return true;
			}
		}
		return false;
	}

	public void deleteDevicePolicy(SdpDevicePolicy policy, SdpDevicePolicy replacement, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// setto altra policy sugli utenti che l'avevano
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpPartyDeviceExt.PARAM_POLICY_ID, policy.getPolicyId());
		parameHashMap.put(SdpPartyDeviceExt.PARAM_POLICY_ID_UPDATE, replacement.getPolicyId());
		NamedQueryHelper.executeNamedQueryUpdate(SdpPartyDeviceExt.QUERY_UPDATE_POLICY, parameHashMap, em);
		// now can remove the policy - configs will be deleted on cascade
		em.remove(policy);
	}

	public List<SdpDevicePolicy> searchAllDevicePolicies(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(SdpDevicePolicy.class, SdpDevicePolicy.QUERY_RETRIEVE_ALL, null, em);
	}

	public SdpDevicePolicy searchDefaultPolicy(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpDevicePolicy.PARAM_IS_DEFAULT, BooleanConverter.getByteValue(true));
		return NamedQueryHelper.executeNamedQuerySingle(SdpDevicePolicy.class, SdpDevicePolicy.QUERY_RETRIEVE_BY_ISDEFAULT, parameHashMap, em);
	}

	public void setPartyDevicePolicy(SdpParty party, SdpDevicePolicy policy, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyDeviceExt ext = party.getSdpPartyDeviceExt();
		ext.setSdpDevicePolicy(policy);
		ext.setUpdatedById(updatedBy);
		ext.setUpdatedDate(new Date());
	}

	public SdpDevicePolicyConfig searchDevicePolicyConfigById(Long policyId, Long channelId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (policyId == null || channelId == null) {
			return null;
		}
		SdpDevicePolicyConfigPK id = new SdpDevicePolicyConfigPK();
		id.setPolicyId(policyId);
		id.setDeviceChannelId(channelId);
		return em.find(SdpDevicePolicyConfig.class, id);
	}
}