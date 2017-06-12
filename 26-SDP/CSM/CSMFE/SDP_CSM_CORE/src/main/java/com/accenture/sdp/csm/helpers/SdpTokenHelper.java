package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefTokenProvider;
import com.accenture.sdp.csm.model.jpa.SdpToken;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpTokenHelper extends SdpBaseHelper {

	private static SdpTokenHelper instance;

	private SdpTokenHelper() {
		super();
	}

	public static SdpTokenHelper getInstance() {
		if (instance == null) {
			instance = new SdpTokenHelper();
		}
		return instance;
	}

	public SdpToken insertToken(String token, RefTokenProvider tokenProvider, String username, long timestamp, String createdById, EntityManager em) {

		SdpToken sdpToken = new SdpToken();
		sdpToken.setTokenId(token);
		sdpToken.setTokenProvider(tokenProvider.getTokenProvider());
		sdpToken.setRefTokenProvider(tokenProvider);
		sdpToken.setUserIdentifier(username);
		sdpToken.setCreatedById(createdById);
		sdpToken.setCreatedDate(new Date());
		sdpToken.setTokenTimestamp(timestamp);
		em.persist(sdpToken);
		return sdpToken;
	}

	public void updateToken(SdpToken sdpToken, String updatedById, EntityManager em) {
		sdpToken.setUpdatedById(updatedById);
		sdpToken.setUpdatedDate(new Date());
	}

	public void deleteToken(SdpToken sdpToken, EntityManager em) {
		em.remove(sdpToken);
	}

	public SdpToken selectTokenByUsernameAndPlatform(String username, String platformName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("username", username);
		parameHashMap.put("platformName", platformName);
		List<SdpToken> result = NamedQueryHelper.executeNamedQuery(SdpToken.class, "selectTokenByUsernameAndPlatform", parameHashMap, em);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);

	}

	public void validateGenerateToken(String username, String password, String platformName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(USERNAME, username);
		validationMap.put(PASSWORD, password);
		validationMap.put("platform", platformName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateCheckToken(String token, String platform) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("token", token);
		validationMap.put("platform", platform);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpToken searchTokenById(String tokenId, String tokenProvider, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		if (tokenId == null || tokenProvider == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("tokenId", tokenId);
		parameHashMap.put("tokenProvider", tokenProvider);
		List<SdpToken> result = NamedQueryHelper.executeNamedQuery(SdpToken.class, "selectTokenById", parameHashMap, em);

		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

}
