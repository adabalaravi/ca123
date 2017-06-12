package com.accenture.sdp.csm.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.commons.DeviceEnums.AuthMode;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.CommittableException;
import com.accenture.sdp.csm.exceptions.EncryptionException;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpDeviceHelper;
import com.accenture.sdp.csm.helpers.SdpDevicePolicyHelper;
import com.accenture.sdp.csm.helpers.SdpPartyDeviceHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.metering.MeteringManager;
import com.accenture.sdp.csm.metering.MeteringManager.EventTypeEnum;
import com.accenture.sdp.csm.model.jpa.RefDeviceBrand;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.RefDeviceModel;
import com.accenture.sdp.csm.model.jpa.RefDeviceUuidType;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpDevice;
import com.accenture.sdp.csm.model.jpa.SdpDeviceCounterConfig;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicy;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicyConfig;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpDeviceManager extends SdpBaseManager {

	private static final String DEVICE_BRAND = "deviceBrand";
	private static final String DEVICE_MODEL = "deviceModel";

	private SdpDeviceManager() {
		super();
	}

	private static SdpDeviceManager instance;

	public static SdpDeviceManager getInstance() {
		if (instance == null) {
			instance = new SdpDeviceManager();
		}
		return instance;
	}

	public SearchServiceResponse searchAllDeviceBrands(String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			List<RefDeviceBrand> beans = SdpDeviceHelper.getInstance().searchAllDeviceBrands(em);

			if (beans == null || beans.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertDeviceBrands(beans));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildSearchResponse(e.getDescription(), e.getParameters());
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

	public SearchServiceResponse searchDeviceModelsByBrand(Long brandId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("brandId", brandId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
			helper.validateSearchDeviceBrandById(brandId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			RefDeviceBrand brand = helper.searchDeviceBrandById(brandId, em);
			if (brand == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "brandId", brandId);
			}

			List<RefDeviceModel> beans = helper.searchDeviceModelByBrandId(brandId, em);
			if (beans == null || beans.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertDeviceModels(beans));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildSearchResponse(e.getDescription(), e.getParameters());
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

	public SearchServiceResponse searchAllDeviceChannels(String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			List<RefDeviceChannel> beans = SdpDeviceHelper.getInstance().searchAllDeviceChannels(em);

			if (beans == null || beans.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertDeviceChannels(beans));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildSearchResponse(e.getDescription(), e.getParameters());
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

	public DataServiceResponse registerDevice(String deviceUUID, String deviceUUIDType, String deviceChannel, String deviceBrand, String deviceModel,
			String deviceAlias, String username, String password, boolean pairEnabled, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(DEVICE_ID, deviceUUID);
		logMap.put("deviceUUIDType", deviceUUIDType);
		logMap.put("deviceChannel", deviceChannel);
		logMap.put(DEVICE_BRAND, deviceBrand);
		logMap.put(DEVICE_MODEL, deviceModel);
		logMap.put("deviceAlias", deviceAlias);
		logMap.put(USERNAME, username);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : HIDDEN_VALUE);
		logMap.put("pairEnabled", pairEnabled);
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
			boolean isAuthEnabled = ConstantsHandler.getInstance(tenantName).retrieveBooleanConstant(Constants.DEVICE_AUTHENTICATION_ENABLED);
			boolean isPairEnabled = pairEnabled && ConstantsHandler.getInstance(tenantName).retrieveBooleanConstant(Constants.DEVICE_PAIRING_ENABLED);
			registerDevice(isAuthEnabled, deviceUUID, deviceUUIDType, deviceChannel, deviceBrand, deviceModel, deviceAlias, username, password, isPairEnabled,
					Utilities.getCurrentClassAndMethod(), em, mm);

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
		} catch (CommittableException validationException) {
			log.logDebug(validationException.getMessage());
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_COMMIT);
				tx.commit();
				log.logDebug(TRANSACTION_COMMITED);
			}
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
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

	SdpDevice registerDevice(boolean isAuthenticationEnabled, String deviceUUID, String deviceUUIDType, String deviceChannel, String deviceBrand,
			String deviceModel, String deviceAlias, String username, String password, boolean pairEnabled, String doneBy, EntityManager em, MeteringManager mm)
			throws ValidationException, PropertyNotFoundException, EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
		SdpCredentialHelper credHelper = SdpCredentialHelper.getInstance();
		SdpPartyDeviceHelper partyDeviceHelper = SdpPartyDeviceHelper.getInstance();
		// validazione
		helper.validateRegisterDevice(deviceUUID, deviceUUIDType, deviceChannel);
		credHelper.validateSearchCredentialByUsername(username);

		// START METERING EVENT dopo validazione, che e' esclusa
		mm.startEvent(EventTypeEnum.DEVICE_REGISTRATION);
		mm.getLastEvent().setDeviceUUID(deviceUUID);

		SdpCredential credential = credHelper.searchCredentialByUsername(username, em);
		if (credential != null) {
			// UPDATE METERING EVENT
			mm.getLastEvent().setPartyId(credential.getSdpParty().getPartyId());
		}
		if (isAuthenticationEnabled) {
			credHelper.validateCheckCredential(username, password);
			// checkCredential non e' quello standard ... per un qualche motivo!
			if (!credHelper.checkCredentialMd5(credential, password, em)) {
				throw new ValidationException(Constants.CODE_DEVICE_AUTH_REFUSED);
			}
		}
		// controllo solo ora, cosi' ha priorita' l'auth refused
		if (credential == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
		}

		// RELATED ENTITIES
		SdpParty party = credential.getSdpParty();
		// bonifica PartyDeviceExt
		if (party.getSdpPartyDeviceExt() == null) {
			SdpPartyDeviceManager.getInstance().createPartyDeviceExt(party.getPartyId(), doneBy, em);
		}
		SdpPartyDeviceExt partyExt = party.getSdpPartyDeviceExt();

		RefDeviceUuidType idType = helper.searchDeviceUuidTypeByName(deviceUUIDType, em);
		if (idType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "deviceUUIDType", deviceUUIDType);
		}
		// validazione formato UUID
		helper.validateDeviceUuid(deviceUUID, idType);

		RefDeviceChannel channel = helper.searchDeviceChannelByName(deviceChannel, em);
		if (channel == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "deviceChannel", deviceChannel);
		}

		RefDeviceBrand brand = helper.searchDeviceBrandByName(deviceBrand, em);
		if (!Utilities.isNull(deviceBrand) && brand == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, DEVICE_BRAND, deviceBrand);
		}

		RefDeviceModel model = helper.searchDeviceModelByName(deviceModel, em);
		if (!Utilities.isNull(deviceModel) && model == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, DEVICE_MODEL, deviceModel);
		}

		// verifica POLICY
		SdpDevicePolicy policy = partyExt.getSdpDevicePolicy();
		if (partyDeviceHelper.checkIsInSafetyPeriod(partyExt, doneBy)) {
			// deve fare commit nonostante l'eccezione
			throw new CommittableException(Constants.CODE_DEVICE_SAFETY_PERIOD);
		}
		SdpDeviceCounterConfig counter = partyDeviceHelper.searchDeviceCounterConfigById(party.getPartyId(), channel.getDeviceChannelId(), em);
		// se non c'e' lo creo
		if (counter == null) {
			counter = partyDeviceHelper.createDeviceCounterConfig(partyExt, channel, em);
		}
		// controllo max per channel
		SdpDevicePolicyConfig policyConfig = SdpDevicePolicyHelper.getInstance().searchDevicePolicyConfigById(policy.getPolicyId(),
				channel.getDeviceChannelId(), em);
		if (policyConfig != null && counter.getRegisteredDevices() >= policyConfig.getMaximumAllowedDevices()) {
			throw new ValidationException(Constants.CODE_DEVICE_MAX_EXCEEDED);
		}

		// verifica duplicati
		SdpDevice device = helper.searchDeviceById(deviceUUID, em);
		boolean reactivation = false;
		if (device != null) {
			if (isStatusActive(device.getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, DEVICE_ID, deviceUUID);
			}
			// se il party non cambia non e' nuova registrazione
			if (device.getSdpPartyDeviceExt().equals(partyExt)) {
				reactivation = true;
			}
			helper.modifyDevice(device, idType, partyExt, channel, brand, model, deviceAlias, isAuthenticationEnabled && pairEnabled, doneBy);
			// attiva il device (sullo stesso o su altro party)
			helper.changeDeviceStatus(device, ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE), doneBy);
		} else {
			// CREAZIONE
			device = helper.createDevice(deviceUUID, idType, partyExt, channel, brand, model, deviceAlias, isAuthenticationEnabled && pairEnabled, doneBy, em);
		}

		// incrementa contatori
		partyDeviceHelper.updateDeviceCounterConfig(counter, +1);
		// portable e riattivazione non prevedono l'incremento del contatore
		if (!BooleanConverter.getBooleanValue(channel.getIsPortable()) && !reactivation) {
			partyDeviceHelper.increaseRegistrationsDone(partyExt, doneBy);
		}

		// METERING EVENT COMPLETED
		mm.flush();
		return device;
	}

	public CreateServiceResponse checkDeviceAccess(String deviceUUID, String username, String password, String authMode, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(DEVICE_ID, deviceUUID);
		logMap.put(USERNAME, username);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : HIDDEN_VALUE);
		logMap.put("authMode", authMode);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		MeteringManager mm = new MeteringManager(tenantName);
		try {
			// validazione
			SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
			helper.validateCheckDeviceAccess(deviceUUID, authMode, username, password);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			// START METERING EVENT dopo validazione, che e' esclusa
			mm.startEvent(EventTypeEnum.AUTHENTICATE_DEVICE);
			mm.getLastEvent().setDeviceUUID(deviceUUID);

			SdpDevice device = helper.searchDeviceById(deviceUUID, em);
			if (device == null || !isStatusActive(device.getStatusId())) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, DEVICE_ID, deviceUUID);
			}

			// solo se e' abilitata l'autenticazione, altrimenti skip
			if (ConstantsHandler.getInstance(tenantName).retrieveBooleanConstant(Constants.DEVICE_AUTHENTICATION_ENABLED)) {
				AuthMode mode = DeviceEnums.getAuthMode(authMode);
				switch (mode) {
				case BASIC:
					SdpCredentialHelper credHelper = SdpCredentialHelper.getInstance();
					SdpCredential credential = credHelper.searchCredentialByUsername(username, em);
					if (credential != null) {
						// UPDATE METERING EVENT - anticipata per eventuali eccezioni
						mm.getLastEvent().setPartyId(credential.getSdpParty().getPartyId());
					}
					if (credential == null || !credHelper.checkCredentialMd5(credential, password, em)) {
						log.logDebug("Wrong username/password");
						throw new ValidationException(Constants.CODE_DEVICE_AUTH_REFUSED);
					}
					if (!credential.getSdpParty().getPartyId().equals(device.getSdpPartyDeviceExt().getPartyId())) {
						log.logDebug("Device owner mismatch with credential");
						throw new ValidationException(Constants.CODE_DEVICE_AUTH_REFUSED);
					}
					break;
				case PAIRING:
					// UPDATE METERING EVENT - anticipata per eventuali eccezioni
					mm.getLastEvent().setPartyId(device.getSdpPartyDeviceExt().getPartyId());
					if (ConstantsHandler.getInstance(tenantName).retrieveBooleanConstant(Constants.DEVICE_PAIRING_ENABLED)) {
						if (!BooleanConverter.getBooleanValue(device.getIsPaired())) {
							log.logDebug("Device is not paired");
							throw new ValidationException(Constants.CODE_DEVICE_AUTH_REFUSED);
						}
					} else {
						log.logDebug("Pairing is non enabled");
						throw new ValidationException(Constants.CODE_DEVICE_AUTH_REFUSED);
					}
				}
			}

			// controllo WL/BL
			SdpPartyDeviceExt partyExt = device.getSdpPartyDeviceExt();
			// UPDATE METERING EVENT
			mm.getLastEvent().setPartyId(partyExt.getPartyId());
			if (!BooleanConverter.getBooleanValue(partyExt.getIsWl()) && !BooleanConverter.getBooleanValue(device.getIsWl())) {
				if (BooleanConverter.getBooleanValue(partyExt.getIsBl())) {
					throw new ValidationException(Constants.CODE_DEVICE_USER_BL);
				}
				if (BooleanConverter.getBooleanValue(device.getIsBl())) {
					log.logDebug("Device in blacklist");
					throw new ValidationException(Constants.CODE_DEVICE_BL);
				}
				if (BooleanConverter.getBooleanValue(device.getRefDeviceChannel().getIsWl())) {
					log.logDebug("Channel in whitelist");
				} else if (device.getRefDeviceBrand() != null && BooleanConverter.getBooleanValue(device.getRefDeviceBrand().getIsWl())) {
					log.logDebug("Brand in whitelist");
				} else if (device.getRefDeviceModel() != null && BooleanConverter.getBooleanValue(device.getRefDeviceModel().getIsWl())) {
					log.logDebug("Model in whitelist");
				} else {
					if (BooleanConverter.getBooleanValue(device.getRefDeviceChannel().getIsBl())) {
						log.logDebug("Channel in blacklist");
						throw new ValidationException(Constants.CODE_DEVICE_BL);
					}
					if (device.getRefDeviceBrand() != null && BooleanConverter.getBooleanValue(device.getRefDeviceBrand().getIsBl())) {
						log.logDebug("Brand in blacklist");
						throw new ValidationException(Constants.CODE_DEVICE_BL);
					}
					if (device.getRefDeviceModel() != null && BooleanConverter.getBooleanValue(device.getRefDeviceModel().getIsBl())) {
						log.logDebug("Model in blacklist");
						throw new ValidationException(Constants.CODE_DEVICE_BL);
					}
				}
			}

			mm.flush();
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(device.getSdpPartyDeviceExt().getPartyId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			mm.commit(resp);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
			mm.rollBack(resp);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
			mm.rollBack(resp);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
			mm.rollBack(resp);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public DataServiceResponse updateDevice(String deviceUUID, String deviceBrand, String deviceModel, String deviceAlias, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(DEVICE_ID, deviceUUID);
		logMap.put(DEVICE_BRAND, deviceBrand);
		logMap.put(DEVICE_MODEL, deviceModel);
		logMap.put("deviceAlias", deviceAlias);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// let's do the magic
			updateDevice(deviceUUID, deviceBrand, deviceModel, deviceAlias, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
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

	void updateDevice(String deviceUUID, String deviceBrand, String deviceModel, String deviceAlias, String doneBy, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
		// validazione
		helper.validateSearchDeviceById(deviceUUID);

		SdpDevice device = helper.searchDeviceById(deviceUUID, em);
		if (device == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, DEVICE_ID, deviceUUID);
		}

		RefDeviceBrand brand = helper.searchDeviceBrandByName(deviceBrand, em);
		if (!Utilities.isNull(deviceBrand) && brand == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, DEVICE_BRAND, deviceBrand);
		}

		RefDeviceModel model = helper.searchDeviceModelByName(deviceModel, em);
		if (!Utilities.isNull(deviceModel) && model == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, DEVICE_MODEL, deviceModel);
		}

		helper.modifyDevice(device, brand, model, deviceAlias, doneBy);
	}

	public DataServiceResponse unregisterDevice(String deviceUUID, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(DEVICE_ID, deviceUUID);
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
			unregisterDevice(deviceUUID, Utilities.getCurrentClassAndMethod(), em, mm);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			mm.commit(resp);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
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

	void unregisterDevice(String deviceUUID, String doneBy, EntityManager em, MeteringManager mm) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
		// validazione
		helper.validateSearchDeviceById(deviceUUID);

		// START METERING EVENT dopo validazione, che e' esclusa
		mm.startEvent(EventTypeEnum.DEVICE_UNREGISTRATION);
		mm.getLastEvent().setDeviceUUID(deviceUUID);

		SdpDevice device = helper.searchDeviceById(deviceUUID, em);
		if (device == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, DEVICE_ID, deviceUUID);
		}
		// UPDATE METERING EVENT - anticipata per eventuali eccezioni
		mm.getLastEvent().setPartyId(device.getSdpPartyDeviceExt().getPartyId());

		if (!isStatusActive(device.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, DEVICE_ID, deviceUUID);
		}

		helper.changeDeviceStatus(device, ConstantsHandler.getInstance().retrieveLongConstant(Constants.INACTIVE), doneBy);
		// reset paired
		helper.modifyDevice(device, false, doneBy);

		// update counter
		SdpPartyDeviceHelper partyDeviceHelper = SdpPartyDeviceHelper.getInstance();
		SdpDeviceCounterConfig counter = partyDeviceHelper.searchDeviceCounterConfigById(device.getSdpPartyDeviceExt().getPartyId(), device
				.getRefDeviceChannel().getDeviceChannelId(), em);
		partyDeviceHelper.updateDeviceCounterConfig(counter, -1);
		mm.flush();
	}

	public DataServiceResponse updateDeviceLastFruitionDate(String deviceUUID, Date lastFruitionDate, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(DEVICE_ID, deviceUUID);
		logMap.put("lastFruitionDate", lastFruitionDate);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// let's do the magic
			updateDeviceLastFruitionDate(deviceUUID, lastFruitionDate, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
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

	void updateDeviceLastFruitionDate(String deviceUUID, Date lastFruitionDate, String doneBy, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
		// validazione
		helper.validateSearchDeviceById(deviceUUID);
		helper.validateLastFruitionDate(lastFruitionDate);

		SdpDevice device = helper.searchDeviceById(deviceUUID, em);
		if (device == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, DEVICE_ID, deviceUUID);
		}

		helper.modifyDevice(device, lastFruitionDate, doneBy);
	}

	public SearchServiceResponse searchDevicesByPartyId(Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpPartyHelper helper = SdpPartyHelper.getInstance();
			helper.validateSearchPartyById(partyId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpParty party = helper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}

			List<SdpDevice> beans = party.getSdpPartyDeviceExt() == null ? null : party.getSdpPartyDeviceExt().getSdpDevices();

			if (beans == null || beans.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertDevices(beans));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildSearchResponse(e.getDescription(), e.getParameters());
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

	public ComplexServiceResponse searchDeviceById(String deviceUUID, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(DEVICE_ID, deviceUUID);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpDeviceHelper helper = SdpDeviceHelper.getInstance();
			// validazione
			helper.validateSearchDeviceById(deviceUUID);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpDevice device = helper.searchDeviceById(deviceUUID, em);
			if (device == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, DEVICE_ID, deviceUUID);
			}

			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertDevice(device));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildComplexResponse(e.getDescription(), e.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildComplexResponse(Constants.CODE_GENERIC_ERROR);
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
