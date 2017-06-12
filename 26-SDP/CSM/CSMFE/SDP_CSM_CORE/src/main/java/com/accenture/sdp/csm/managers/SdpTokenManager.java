package com.accenture.sdp.csm.managers;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpTokenDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpTokenHelper;
import com.accenture.sdp.csm.model.jpa.RefTokenProvider;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpToken;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.CryptoUtils;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpTokenManager extends SdpBaseManager {
	
	private static final String PLATFORM = "platform";

	private SdpTokenManager() {
		super();
	}

	private static SdpTokenManager instance;

	public static SdpTokenManager getInstance() {
		if (instance == null) {
			instance = new SdpTokenManager();
		}
		return instance;
	}

	private static final String TOKEN_DELIMITER = "|";

	/**
	 * <p>
	 * This method allows to generate an authentication token
	 * 
	 * @param username
	 *            Username of the user that requires authentication
	 * @param password
	 *            password of the user that requires authentication
	 * @param platform
	 *            platform to access
	 * @return Returns a CreateComplexServiceResponse operation result and generated authentication token
	 * @exception PropertyNotFoundException
	 */
	public ComplexServiceResponse generateToken(String username, String password, String platform) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put(PLATFORM, platform);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = PersistenceManager.getEntityManager();
		EntityTransaction tx = null;
		try {
			SdpTokenHelper sdpTokenHelper = SdpTokenHelper.getInstance();
			sdpTokenHelper.validateGenerateToken(username, password, platform);

			StringBuffer buffer = new StringBuffer();
			long timestamp = System.currentTimeMillis();
			buffer.append(username);
			buffer.append(TOKEN_DELIMITER);
			buffer.append(password);
			buffer.append(TOKEN_DELIMITER);
			buffer.append(platform);
			buffer.append(TOKEN_DELIMITER);
			buffer.append(timestamp);

			String baseTokenString = buffer.toString();
			String encryptedToken = CryptoUtils.crypt(baseTokenString);
			if (encryptedToken == null || encryptedToken.length() == 0) {
				log.logError("Encrypted Token is empty");
				throw new Exception("Encrypted Token is empty");

			}

			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			RefTokenProvider tokenProvider = em.find(RefTokenProvider.class, platform);

			if (tokenProvider == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM, platform);
			}

			if (tokenProvider.getTokenProvider().equals(Constants.SDP_PLATFORM)) {
				SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
				SdpCredential sdpCredential = credentialHelper.searchCredentialByUsername(username, em);
				if (sdpCredential == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
				} else if (!(CryptoUtils.decrypt(sdpCredential.getPassword())).equals(password)) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
				}

			}

			SdpToken token = sdpTokenHelper.selectTokenByUsernameAndPlatform(username, platform, em);

			if (token != null) {
				log.logDebug("Token already existent for username: %s and platform: %s", username, platform);
				sdpTokenHelper.deleteToken(token, em);
				log.logDebug("Old token deleted");
			}

			token = sdpTokenHelper.insertToken(encryptedToken, tokenProvider, username, timestamp, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(token.getTokenId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildComplexResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildComplexResponse(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildComplexResponse(Constants.CODE_GENERIC_ERROR);
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

	/**
	 * <p>
	 * This method allows to validate an authentication token
	 * 
	 * @param token
	 *            Authentication token of the user that requires authentication
	 * @param platform
	 *            platform to access
	 * @return Returns a SearchServiceResponse operation result and all informations related to authenticated user credentials
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse validateToken(String token, String platform) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("token", token);
		logMap.put(PLATFORM, platform);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpTokenHelper helper = SdpTokenHelper.getInstance();
			helper.validateCheckToken(token, platform);
			em = PersistenceManager.getEntityManager();
			SdpToken dbToken = helper.searchTokenById(token, platform, em);
			if (dbToken == null) {
				log.logDebug("Token not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto("token", token), new ParameterDto(PLATFORM, platform));
			}

			String decryptedToken = CryptoUtils.decrypt(token);

			// Split token
			String escapedDelimiter = "\\" + TOKEN_DELIMITER;
			String[] tokenElements = decryptedToken.split(escapedDelimiter);

			if (tokenElements.length != 4) {
				throw new ValidationException(Constants.CODE_INVALID_TOKEN, "token", token);
			}

			if (!tokenElements[0].equals(dbToken.getUserIdentifier()) || !tokenElements[2].equals(dbToken.getRefTokenProvider().getTokenProvider())) {
				throw new ValidationException(Constants.CODE_INVALID_TOKEN);
			}

			// Check for validity

			long currentTimeMillis = System.currentTimeMillis();

			long tokenTimestamp = dbToken.getTokenTimestamp().longValue();

			log.logDebug("Token timestamp: %s", tokenTimestamp);

			long diff = (currentTimeMillis - tokenTimestamp) / 1000;

			if (diff > dbToken.getRefTokenProvider().getTokenValiditySecond().longValue()) {
				log.logDebug("Token %s expired", dbToken.getTokenId());
				throw new ValidationException(Constants.CODE_INVALID_TOKEN);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpTokenDto> tokenDtos = new ArrayList<SdpTokenDto>();
			SdpTokenDto tokenDto = new SdpTokenDto();
			tokenDto.setUsername(tokenElements[0]);
			tokenDto.setPassword(tokenElements[1]);
			tokenDto.setPlatform(platform);
			tokenDtos.add(tokenDto);
			resp.setSearchResult(tokenDtos);
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
