package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.LinkUpdateOperation;
import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.CreateServicesResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpAccountRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartySiteRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpChildPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyResponseDto;
import com.accenture.sdp.csm.exceptions.EncryptionException;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpAccountHelper;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpPartyGroupHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpSiteHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

/**
 * @author patrizio.pontecorvi
 * 
 */
public final class SdpPartyManager extends SdpBaseManager {

	private static final String BIRTH_CITY = "birthCity";
	private static final String BIRTH_COUNTRY = "birthCountry";
	private static final String BIRTH_DATE = "birthDate";
	private static final String BIRTH_PROVINCE = "birthProvince";
	private static final String CITY = "city";
	private static final String COUNTRY = "country";
	private static final String EMAIL = "email";
	private static final String FAX_NUMBER = "faxNumber";
	private static final String GENDER = "gender";
	private static final String NOTE = "note";
	private static final String PARTY_DESCRIPTION = "partyDescription";
	private static final String PARTY_PROFILE = "partyProfile";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String PROVINCE = "province";
	private static final String STREET_ADDRESS = "streetAddress";
	private static final String USER_DEFAULT_SITE_ID = "userDefaultSiteId";
	private static final String ZIPCODE = "zipCode";

	private SdpPartyManager() {
		super();
	}

	private static SdpPartyManager instance;

	public static SdpPartyManager getInstance() {
		if (instance == null) {
			instance = new SdpPartyManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new Party as a Parent Party (used to create a new organization). The new party is created by default
	 * with logical status = ACTIVE and logical party type=Organization. It uses
	 * {@link SdpPartyManager#createParentParty(String, String, String, String, Long, String, EntityManager)} .
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party, it is an identifier of the commercial segment associated to the party (Typically Residential
	 *            or Business but it can assume also others valued in custom scenario).
	 * @return {@link com.accenture.sdp.csm.dto.CreateServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success also the Id of new party just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @see SdpPartyManager#createParentParty(String, String, String, String, Long, String, EntityManager)
	 */
	public CreateServiceResponse createParentParty(String partyName, String partyDescription, String externalId, String partyProfile, List<Long> partyGroups,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(PARTY_GROUP_ID, partyGroups);
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
			SdpParty party = createParentParty(partyName, partyDescription, externalId, partyProfile, partyGroups, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(party.getPartyId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildCreateResponse(e.getDescription(), e.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the validation and the business logic to create new Parent Party. It don't manage transaction. This method can be called
	 * only from this or other Manager classes. The new party is created by default with logical status = ACTIVE and logical party type=Organization.
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created. Required.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party, it is an identifier of the commercial segment associated to the party (Typically Residential
	 *            or Business but it can assume also others valued in custom scenario). Required.
	 * @param createdBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * 
	 */
	SdpParty createParentParty(String partyName, String partyDescription, String externalId, String partyProfile, List<Long> partyGroups, String createdBy,
			EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();
		partyHelper.validateParentParty(partyName);
		if (partyGroups == null || partyGroups.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, PARTY_GROUP_ID);
		}
		ArrayList<SdpPartyGroup> sdpPartyGroups = new ArrayList<SdpPartyGroup>();
		for (Long partyGroupId : partyGroups) {
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);
			// retrieve refPartyGroup
			SdpPartyGroup refPartyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			if (refPartyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			sdpPartyGroups.add(refPartyGroup);
		}
		// verifica duplicati
		if (partyHelper.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}
		// creazione party
		return partyHelper.createParentParty(partyName, partyDescription, externalId, partyProfile, sdpPartyGroups, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new Party as a Child Party (used to create a new user). The new party is created by default with logical
	 * status = ACTIVE and logical party type=User. It uses
	 * {@link SdpPartyManager#createChildParty(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, EntityManager)}
	 * .
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param parentPartyId
	 *            The id of parent party to associate at this child.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user.
	 * @param lastName
	 *            The last name of user.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user. The User Site ID must be a site associated to the parent party.
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The Postcode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @return {@link com.accenture.sdp.csm.dto.CreateServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success also the Id of new party just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @see SdpPartyManager#createChildParty(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String,
	 *      Date, String, String, String, String, String, String, String, String, EntityManager)
	 */
	public CreateServiceResponse createChildParty(String partyName, String partyDescription, Long parentPartyId, List<Long> partyGroups, String externalId,
			String partyProfile, String firstName, String lastName, Long userDefaultSiteId, String streetAddress, String city, String zipCode, String province,
			String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber,
			String email, String note, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(PARENT_PARTY_ID, parentPartyId);
		logMap.put(PARTY_GROUP_ID, partyGroups);
		logMap.put(USER_DEFAULT_SITE_ID, userDefaultSiteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put(STREET_ADDRESS, streetAddress);
		logMap.put(CITY, city);
		logMap.put(ZIPCODE, zipCode);
		logMap.put(PROVINCE, province);
		logMap.put(COUNTRY, country);
		logMap.put(GENDER, gender);
		logMap.put(BIRTH_DATE, Utilities.formatDate(birthDate));
		logMap.put(BIRTH_PROVINCE, birthProvince);
		logMap.put(BIRTH_COUNTRY, birthCountry);
		logMap.put(BIRTH_CITY, birthCity);
		logMap.put(PHONE_NUMBER, phoneNumber);
		logMap.put(FAX_NUMBER, faxNumber);
		logMap.put(EMAIL, email);
		logMap.put(NOTE, note);
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

			SdpParty party = createChildParty(partyName, partyDescription, parentPartyId, partyGroups, externalId, partyProfile, firstName, lastName,
					userDefaultSiteId, streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber,
					faxNumber, email, note, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(party.getPartyId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the validation and the business logic to create new Child Party. It don't manage transaction. This method can be called
	 * only from this or other Manager classes. The new party is created by default with logical status = ACTIVE and logical party type=User.
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created. Required.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param parentPartyId
	 *            The id of parent party to associate at this child. Required.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent. Required.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user. Required.
	 * @param lastName
	 *            The last name of user. Required.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user. The User Site ID must be a site associated to the parent party.
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The zipCode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @param createdBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 */
	SdpParty createChildParty(String partyName, String partyDescription, Long parentPartyId, List<Long> partyGroups, String externalId, String partyProfile,
			String firstName, String lastName, Long userDefaultSiteId, String streetAddress, String city, String zipCode, String province, String country,
			String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber, String email,
			String note, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
		SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();

		partyHelper.validateSearchParentPartyById(parentPartyId);
		partyHelper.validateChildParty(partyName, firstName, lastName, gender, birthDate);
		if (partyGroups == null || partyGroups.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, PARTY_GROUP_ID);
		}
		// search Parent
		SdpParty parentParty = partyHelper.searchPartyById(parentPartyId, em);
		if (parentParty == null || !partyHelper.isAParent(parentParty)) {
			throw new ValidationException(Constants.CODE_PARENT_NOT_FOUND, PARENT_PARTY_ID, String.valueOf(parentPartyId));
		}
		ArrayList<SdpPartyGroup> sdpPartyGroups = new ArrayList<SdpPartyGroup>();
		for (Long partyGroupId : partyGroups) {
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);
			SdpPartyGroup refPartyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			// Modificato in vincolo di scenario non piu' applicativo
			// || !refPartyGroup.equals(parentParty.getRefPartyGroup())
			if (refPartyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			sdpPartyGroups.add(refPartyGroup);
		}
		SdpSite partySite = partySiteHelper.retrieveDefaultPartySite(userDefaultSiteId, parentParty.getSdpPartySites());

		// verifica duplicati
		if (partyHelper.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}
		// creazione party
		return partyHelper.createChildParty(partyName, partyDescription, parentParty, sdpPartyGroups, partySite, externalId, partyProfile, firstName, lastName,
				streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber, faxNumber, email,
				note, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new Party as a Child Party and new credentials for this party. The new party is created by default with
	 * logical status = ACTIVE and logical party type=User. The new credential is created by default with logical status = ACTIVE It use
	 * {@link SdpPartyManager#createChildPartyAndCredential(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, String, String, List, String, EntityManager)}
	 * .
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param parentPartyId
	 *            The id of parent party to associate at this child.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user.
	 * @param lastName
	 *            The last name of user.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user. The User Site ID must be a site associated to the parent party.
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The Postcode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @param userName
	 *            Username of the credential.
	 * @param password
	 *            Password of the credential.
	 * @param usernameExternalId
	 *            External id of new credential to create used by external system.
	 * @param secretQuestions
	 *            Liist of three secret questions and answers used for retrieve the username or the password.
	 * @return {@link com.accenture.sdp.csm.dto.CreateServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success also the Id of new party just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @see SdpPartyManager#createChildPartyAndCredential(String, String, Long, Long, String, String, String, String, Long, String, String, String, String,
	 *      String, String, Date, String, String, String, String, String, String, String, String, String, String, List, String, EntityManager)
	 */
	public CreateServiceResponse createChildPartyAndCredential(String partyName, String partyDescription, Long parentPartyId, List<Long> partyGroups,
			String externalId, String partyProfile, String firstName, String lastName, Long userDefaultSiteId, String streetAddress, String city,
			String zipCode, String province, String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity,
			String phoneNumber, String faxNumber, String email, String note, String userName, String password, String usernameExternalId,
			List<SdpSecretQuestionRequestDto> secretQuestions, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(PARENT_PARTY_ID, parentPartyId);
		logMap.put(PARTY_GROUP_ID, partyGroups);
		logMap.put(USER_DEFAULT_SITE_ID, userDefaultSiteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put(STREET_ADDRESS, streetAddress);
		logMap.put(CITY, city);
		logMap.put(ZIPCODE, zipCode);
		logMap.put(PROVINCE, province);
		logMap.put(COUNTRY, country);
		logMap.put(GENDER, gender);
		logMap.put(BIRTH_DATE, Utilities.formatDate(birthDate));
		logMap.put(BIRTH_PROVINCE, birthProvince);
		logMap.put(BIRTH_COUNTRY, birthCountry);
		logMap.put(BIRTH_CITY, birthCity);
		logMap.put(PHONE_NUMBER, phoneNumber);
		logMap.put(FAX_NUMBER, faxNumber);
		logMap.put(EMAIL, email);
		logMap.put(NOTE, note);
		logMap.put(USERNAME, userName);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : "***");
		logMap.put("usernameExternalId", usernameExternalId);
		logMap.put("secretQuestions", secretQuestions);
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

			SdpParty party = createChildPartyAndCredential(partyName, partyDescription, parentPartyId, partyGroups, externalId, partyProfile, firstName,
					lastName, userDefaultSiteId, streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity,
					phoneNumber, faxNumber, email, note, userName, password, usernameExternalId, secretQuestions, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(party.getPartyId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the calls to internal methods
	 * {@link SdpPartyManager#createChildParty(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, EntityManager)}
	 * and {@link SdpCredentialManager#createCredential(String, String, Long, String, String, List, String, EntityManager)} It don't manage transaction. This
	 * method can be called only from this or other Manager classes. The new party is created by default with logical status = ACTIVE and logical party
	 * type=User. The new credential is created by default with logical status = ACTIVE
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param parentPartyId
	 *            The id of parent party to associate at this child.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user.
	 * @param lastName
	 *            The last name of user.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user. The User Site ID must be a site associated to the parent party.
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The Postcode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @param userName
	 *            Username of the credential.
	 * @param password
	 *            Password of the credential.
	 * @param usernameExternalId
	 *            External id of new credential to create used by external system.
	 * @param secretQuestions
	 *            Liist of three secret questions and answers used for retrieve the username or the password.
	 * @param createdBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * @exception EncryptionException
	 *                throws when the crypting of a password fail.
	 * @see SdpPartyManager#createChildParty(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String,
	 *      Date, String, String, String, String, String, String, String, String, EntityManager)
	 * @see SdpCredentialManager#createCredential(String, String, Long, String, String, List, String, EntityManager)
	 */
	SdpParty createChildPartyAndCredential(String partyName, String partyDescription, Long parentPartyId, List<Long> partyGroups, String externalId,
			String partyProfile, String firstName, String lastName, Long userDefaultSiteId, String streetAddress, String city, String zipCode, String province,
			String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber,
			String email, String note, String username, String password, String usernameExternalId, List<SdpSecretQuestionRequestDto> secretQuestions,
			String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException, EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// creazione party
		SdpParty party = createChildParty(partyName, partyDescription, parentPartyId, partyGroups, usernameExternalId, partyProfile, firstName, lastName,
				userDefaultSiteId, streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber,
				faxNumber, email, note, createdBy, em);

		SdpCredentialManager.getInstance().createCredential(username, password, party.getPartyId(), externalId,
				ConstantsHandler.getInstance().retrieveConstant(Constants.ROLE_NAME_USER), secretQuestions, createdBy, em);
		return party;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database new Parties, one as Parent and one as a Child, a list of site associated at parent Party, and a couple of
	 * credential (one as administrator and one as user) refered to Child The new parent party is created by default with logical status = ACTIVE and logical
	 * party type=User. The new credentials are created by default with logical status = ACTIVE It use
	 * {@link SdpPartyManager#createChildPartyAndParentDummy(String, String, Long, String, String, String, String, String, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, String, String, List, List, String, EntityManager)}
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user.
	 * @param lastName
	 *            The last name of user.
	 * @param userDefaultSiteName
	 *            Name of the site to associate to party
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The Postcode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @param userName
	 *            Username of the credential.
	 * @param password
	 *            Password of the credential.
	 * @param usernameExternalId
	 *            External id of new credential to create used by external system.
	 * @param secretQuestions
	 *            List of three secret questions and answers used for retrieve the username or the password.
	 * @param partySites
	 *            List of sites to create
	 * @return {@link com.accenture.sdp.csm.dto.CreateServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success also the Id of new party just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @see SdpPartyManager#createChildPartyAndParentDummy(String, String, Long, String, String, String, String, String, String, String, String, String, String,
	 *      String, Date, String, String, String, String, String, String, String, String, String, String, List, List, String, EntityManager)
	 */
	public CreateServicesResponse createChildPartyAndParentDummy(String partyName, String partyDescription, List<Long> partyGroups, String externalId,
			String partyProfile, String firstName, String lastName, String userDefaultSiteName, String streetAddress, String city, String zipCode,
			String province, String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber,
			String faxNumber, String email, String note, String userName, String password, String usernameExternalId,
			List<SdpSecretQuestionRequestDto> secretQuestions, List<SdpPartySiteRequestDto> partySites, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServicesResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put("userDefaultSiteName", userDefaultSiteName);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put("address", streetAddress);
		logMap.put(CITY, city);
		logMap.put(ZIPCODE, zipCode);
		logMap.put(PROVINCE, province);
		logMap.put(COUNTRY, country);
		logMap.put(GENDER, gender);
		logMap.put(BIRTH_DATE, Utilities.formatDate(birthDate));
		logMap.put(BIRTH_PROVINCE, birthProvince);
		logMap.put(BIRTH_COUNTRY, birthCountry);
		logMap.put(BIRTH_CITY, birthCity);
		logMap.put(PHONE_NUMBER, phoneNumber);
		logMap.put(FAX_NUMBER, faxNumber);
		logMap.put(EMAIL, email);
		logMap.put(NOTE, note);
		logMap.put(USERNAME, userName);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : "***");
		logMap.put("usernameExternalId", usernameExternalId);
		logMap.put("secretQuestions", secretQuestions);
		logMap.put("partySites", partySites);
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

			SdpParty party = createChildPartyAndParentDummy(partyName, partyDescription, partyGroups, externalId, partyProfile, firstName, lastName,
					userDefaultSiteName, streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity,
					phoneNumber, faxNumber, email, note, userName, password, usernameExternalId, secretQuestions, partySites,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponses(Constants.CODE_OK);
			resp.addEntityId("partyParentId", party.getParentParty().getPartyId());
			resp.addEntityId("partyChildId", party.getPartyId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponses(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponses(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponses(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the calls to other internal methods
	 * {@link SdpPartyManager#_createParentParty(String, String, String, String, Long, String, EntityManager)} ,
	 * {@link SdpSiteManager#_createSite(String, String, Long, String, String, String, String, String, String, String, String, EntityManager)},
	 * {@link SdpPartyManager#createChildParty(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, EntityManager)}
	 * and tow times {@link SdpCredentialManager#createCredential(String, String, Long, String, String, List, String, EntityManager)} create administration
	 * credential (with username prefix "admin" and input username) and user credential (with input username) It don't manage transaction. This method can be
	 * called only from this or other Manager classes. The new party are created by default with logical status = ACTIVE and logical party type=User. The new
	 * credential are created by default with logical status = ACTIVE
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param parentPartyId
	 *            The id of parent party to associate at this child.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user.
	 * @param lastName
	 *            The last name of user.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user. The User Site ID must be a site associated to the parent party.
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The Postcode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @param userName
	 *            Username of the credential.
	 * @param password
	 *            Password of the credential.
	 * @param usernameExternalId
	 *            External id of new credential to create used by external system.
	 * @param secretQuestions
	 *            Liist of three secret questions and answers used for retrieve the username or the password.
	 * @param createdBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object of child just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * @exception EncryptionException
	 *                throws when the crypting of a password fail.
	 * @see SdpPartyManager#createParentParty(String, String, String, String, Long, String, EntityManager)
	 * @see SdpSiteManager#createSite(String, String, Long, String, String, String, String, String, String, String, String, EntityManager)
	 * @see SdpPartyManager#createChildParty(String, String, Long, Long, String, String, String, String, Long, String, String, String, String, String, String,
	 *      Date, String, String, String, String, String, String, String, String, EntityManager)
	 * @see SdpCredentialManager#createCredential(String, String, Long, String, String, List, String, EntityManager)
	 * */
	SdpParty createChildPartyAndParentDummy(String partyName, String partyDescription, List<Long> partyGroups, String externalId, String partyProfile,
			String firstName, String lastName, String userDefaultSiteName, String streetAddress, String city, String zipCode, String province, String country,
			String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber, String email,
			String note, String username, String password, String usernameExternalId, List<SdpSecretQuestionRequestDto> secretQuestions,
			List<SdpPartySiteRequestDto> partySites, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException,
			EncryptionException {

		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpParty parent = createParentParty(partyName, partyDescription, null, partyProfile, partyGroups, createdBy, em);
		SdpSiteManager partySiteManager = SdpSiteManager.getInstance();
		ArrayList<SdpSite> sites = new ArrayList<SdpSite>();
		for (SdpPartySiteRequestDto dto : partySites) {
			sites.add(partySiteManager.createSite(dto.getSiteName(), dto.getSiteDescription(), parent.getPartyId(), dto.getExternalID(), dto.getAddress(),
					dto.getCity(), dto.getZipCode(), dto.getProvince(), dto.getCountry(), dto.getSiteProfile(), createdBy, em));
		}
		SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
		SdpSite partySite = partySiteHelper.retrieveDefaultPartySite(userDefaultSiteName, sites);

		SdpParty party = createChildParty(partyName, partyDescription, parent.getPartyId(), partyGroups, externalId, partyProfile, firstName, lastName,
				partySite == null ? null : partySite.getSiteId(), streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince,
				birthCountry, birthCity, phoneNumber, faxNumber, email, note, createdBy, em);

		SdpCredentialManager credentialManager = SdpCredentialManager.getInstance();
		credentialManager.createCredential(username, password, party.getPartyId(), usernameExternalId,
				ConstantsHandler.getInstance().retrieveConstant(Constants.ROLE_NAME_USER), secretQuestions, createdBy, em);
		credentialManager.createCredential(Constants.USER_NAME_ADMIN_PREFIX + username, password, parent.getPartyId(), null, ConstantsHandler.getInstance()
				.retrieveConstant(Constants.ROLE_NAME_ADMIN), secretQuestions, createdBy, em);

		return party;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database new Parent Party, a list of site associated with it, an administrator credential and a list of Account The
	 * new parent party is created by default with logical status = ACTIVE and logical party type=Organization. The new child party is created by default with
	 * logical status = ACTIVE and logical party type=User. The new credentials are created by default with logical status = ACTIVE. The Accounts are created by
	 * default with logical status = ACTIVE. It use
	 * {@link SdpPartyManager#createParentPartyComplete(String, String, Long, String, String, String, String, String, List, List, List, String, EntityManager)}
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param username
	 *            Username of the credential.
	 * @param password
	 *            Password of the credential.
	 * @param userNameExternaId
	 *            External id of new credential to create used by external system.
	 * @param secretQuestions
	 *            List of three secret questions and answers used for retrieve the username or the password.
	 * @param partySites
	 *            List of sites to create
	 * @param accounts
	 *            List of sites to create
	 * @return {@link com.accenture.sdp.csm.dto.CreateServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success also the Id of new party just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @see SdpPartyManager#createParentPartyComplete(String, String, Long, String, String, String, String, String, List, List, List, String, EntityManager)
	 */
	public CreateServiceResponse createParentPartyComplete(String partyName, String partyDescription, List<Long> partyGroups, String externalId,
			String partyProfile, String username, String password, String userNameExternaId, List<SdpSecretQuestionRequestDto> secretQuestions,
			List<SdpPartySiteRequestDto> partySites, List<SdpAccountRequestDto> accounts, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp = null;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(PARTY_GROUP_ID, partyGroups);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(USERNAME, username);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : "***");
		logMap.put("userNameExternaId", userNameExternaId);
		logMap.put("secretQuestions", secretQuestions);
		logMap.put("partySites", partySites);
		logMap.put("accounts", accounts);
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

			SdpParty party = createParentPartyComplete(partyName, partyDescription, partyGroups, externalId, partyProfile, username, password,
					userNameExternaId, secretQuestions, partySites, accounts, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(party.getPartyId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the calls to internal methods
	 * {@link SdpPartyManager#_createParentParty(String, String, String, String, Long, String, EntityManager)} ,
	 * {@link SdpSiteManager#_createSite(String, String, Long, String, String, String, String, String, String, String, String, EntityManager)},
	 * {@link SdpAccountManager#_createAccount(String, String, boolean, Long, Long, String, String, String, EntityManager) )} and tow times
	 * {@link SdpCredentialManager#createCredential(String, String, Long, String, String, List, String, EntityManager)} create administration credential It
	 * don't manage transaction. This method can be called only from this or other Manager classes. The new party is created by default with logical status =
	 * ACTIVE and logical party type=Organization. The new credential is created by default with logical status = ACTIVE The accounts are created by default
	 * with logical status = ACTIVE
	 * </p>
	 * 
	 * @param partyName
	 *            Name of the party to be created.
	 * @param partyDescription
	 *            A short description of the party.
	 * @param parentPartyId
	 *            The id of parent party to associate at this child.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario). It
	 *            should be the same of parent.
	 * @param externalId
	 *            External id of new party to create used by external system.
	 * @param partyProfile
	 *            Extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param firstName
	 *            The first name of user.
	 * @param lastName
	 *            The last name of user.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user. The User Site ID must be a site associated to the parent party.
	 * @param streetAddress
	 *            Street home address.
	 * @param city
	 *            The city of user.
	 * @param zipCode
	 *            The Postcode of user.
	 * @param province
	 *            The province of user.
	 * @param country
	 *            The country of user.
	 * @param gender
	 *            The gender of user.
	 * @param birthDate
	 *            The birth date of user.
	 * @param birthProvince
	 *            The birth province of user
	 * @param birthCountry
	 *            The birth country of user
	 * @param birthCity
	 *            The birth city of user.
	 * @param phoneNumber
	 *            The phone number of user.
	 * @param faxNumber
	 *            The fax number of user.
	 * @param email
	 *            The email number of user.
	 * @param note
	 *            Notes about user
	 * @param userName
	 *            Username of the credential.
	 * @param password
	 *            Password of the credential.
	 * @param usernameExternalId
	 *            External id of new credential to create used by external system.
	 * @param secretQuestions
	 *            Liist of three secret questions and answers used for retrieve the username or the password.
	 * @param createdBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object just created.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * @exception EncryptionException
	 *                throws when the crypting of a password fail.
	 * @see SdpPartyManager#createParentParty(String, String, String, String, Long, String, EntityManager)
	 * @see SdpSiteManager#createSite(String, String, Long, String, String, String, String, String, String, String, String, EntityManager)
	 * @see SdpCredentialManager#createCredential(String, String, Long, String, String, List, String, EntityManager)
	 * @see SdpAccountManager#createAccount(String, String, boolean, Long, Long, String, String, String, EntityManager)
	 * */
	SdpParty createParentPartyComplete(String partyName, String partyDescription, List<Long> partyGroups, String externalId, String partyProfile,
			String adminUsername, String password, String adminExternaId, List<SdpSecretQuestionRequestDto> secretQuestionsDto,
			List<SdpPartySiteRequestDto> partySitesDto, List<SdpAccountRequestDto> accountsDto, String createdBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException, EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpParty parent = createParentParty(partyName, partyDescription, externalId, partyProfile, partyGroups, createdBy, em);
		SdpCredentialManager.getInstance().createCredential(adminUsername, password, parent.getPartyId(), adminExternaId,
				ConstantsHandler.getInstance().retrieveConstant(Constants.ROLE_NAME_ADMIN), secretQuestionsDto, createdBy, em);

		ArrayList<SdpSite> partySites = new ArrayList<SdpSite>();
		SdpSiteManager partySiteManager = SdpSiteManager.getInstance();
		if (partySitesDto != null && !partySitesDto.isEmpty()) {
			for (SdpPartySiteRequestDto dto : partySitesDto) {
				SdpSite site = partySiteManager.createSite(dto.getSiteName(), dto.getSiteDescription(), parent.getPartyId(), dto.getExternalID(),
						dto.getAddress(), dto.getCity(), dto.getZipCode(), dto.getProvince(), dto.getCountry(), dto.getSiteProfile(), createdBy, em);
				partySites.add(site);
			}
			parent.setSdpPartySites(partySites);
		}
		SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
		SdpAccountManager accountManager = SdpAccountManager.getInstance();
		if (accountsDto != null && !accountsDto.isEmpty()) {
			for (SdpAccountRequestDto account : accountsDto) {
				SdpSite auxSite = null;
				if (!Utilities.isNull(account.getAccountSiteName())) {
					auxSite = partySiteHelper.retrievePartySite(account.getAccountSiteName(), partySites);
					if (auxSite == null) {
						throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "accountSiteName", account.getAccountSiteName());
					}
				}
				accountManager.createAccount(account.getAccountName(), account.getAccountDescription(), account.isDefaultPartyAccount(), parent.getPartyId(),
						auxSite == null ? null : auxSite.getSiteId(), account.getExternalID(), account.getAccountProfile(), createdBy, em);
			}
		}
		return parent;
	}

	/**
	 * <p>
	 * This method allows to modify a Parent Party into CSM database. The parent logically status must be ACTIVE It uses
	 * {@link SdpPartyManager#c_modifyParentParty(Long, String, String, String, String, Long, String, EntityManager)} .
	 * </p>
	 * 
	 * @param partyId
	 *            The id of party to modify
	 * @param partyName
	 *            The new name of party.
	 * @param partyDescription
	 *            The new short description of party.
	 * @param externalId
	 *            the new external id of party used by external system.
	 * @param partyProfile
	 *            the new extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param partyGroupId
	 *            The new party Group Id of refPartyGroup that relates the party, it is an identifier of the commercial segment associated to the party
	 *            (Typically Residential or Business but it can assume also others valued in custom scenario).
	 * @return a {@link com.accenture.sdp.csm.dto.DataServiceResponse} that contains the informations related operation result and operation description.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @see SdpPartyManager#c_modifyParentParty(Long, String, String, String, String, Long, String, EntityManager)
	 */
	public DataServiceResponse modifyParentParty(Long partyId, String partyName, String partyDescription, String externalId, String partyProfile,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
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

			modifyParentParty(partyId, partyName, partyDescription, externalId, partyProfile, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
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

	/**
	 * <p>
	 * Internal method that performs the validation and the business logic to modify a Parent Party. It don't manage transaction. This method can be called only
	 * from this or other Manager classes. The party must be a parent party with logical status Active.
	 * </p>
	 * 
	 * @param parentPartyId
	 *            The id of party to modify
	 * @param partyName
	 *            The new name of party.
	 * @param partyDescription
	 *            The new short description of party.
	 * @param externalId
	 *            the new external id of party used by external system.
	 * @param partyProfile
	 *            the new extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param partyGroupId
	 *            The new party Group Id of refPartyGroup that relates the party, it is an identifier of the commercial segment associated to the party
	 *            (Typically Residential or Business but it can assume also others valued in custom scenario).
	 * @param modifiedBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object modified.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * 
	 */
	SdpParty modifyParentParty(Long parentPartyId, String partyName, String partyDescription, String externalId, String partyProfile, String modifiedBy,
			EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		partyHelper.validateSearchPartyById(parentPartyId);
		partyHelper.validateParentParty(partyName);
		SdpParty party = partyHelper.searchPartyById(parentPartyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, String.valueOf(parentPartyId));
		}
		if (!partyHelper.isAParent(party)) {
			throw new ValidationException(Constants.CODE_HIERARCHY_NOT_RESPECTED, PARTY_ID, String.valueOf(parentPartyId));
		}
		if (!isStatusActive(party.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, PARTY_ID, String.valueOf(parentPartyId));
		}

		// verifica duplicati
		if (!Utilities.isNull(externalId)) {
			SdpParty temp = partyHelper.searchByExternalId(externalId, em);
			if (temp != null && !temp.getPartyId().equals(parentPartyId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		partyHelper.modifyParentParty(party, partyName, partyDescription, externalId, partyProfile, modifiedBy);
		return party;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new Party as a Child Party (used to create a new user). The new party is created by default with logical
	 * status = ACTIVE and logical party type=User. It uses
	 * {@link SdpPartyManager#modifyChildParty(Long, String, String, String, Long, String, Long, String, String, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, EntityManager)}
	 * </p>
	 * 
	 * @param partyId
	 *            The id of party to modify
	 * @param partyName
	 *            The new name of the party.
	 * @param partyDescription
	 *            The new short description of the party.
	 * @param externalId
	 *            The new external id of new party to create used by external system.
	 * @param partyGroupId
	 *            The new id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario)
	 *            to modify. It should be the same of parent.
	 * @param partyProfile
	 *            The new extended profile of the party that contains an XmlDocument provides additional\custom information about this party.
	 * @param userDefaultSiteId
	 *            The new Identifier of the Site associated to the user to modify. The User Site ID must be a site associated to the parent party.
	 * @param firstName
	 *            The new first name of user.
	 * @param lastName
	 *            The new last name of user.
	 * @param streetAddress
	 *            The new Street home address.
	 * @param city
	 *            The new city of user to modify.
	 * @param zipCode
	 *            The new Postcode of user to modify.
	 * @param province
	 *            The new province of user to modify.
	 * @param country
	 *            The new country of user to modify.
	 * @param gender
	 *            The new gender of user to modify.
	 * @param birthDate
	 *            The new birth date of user to modify.
	 * @param birthProvince
	 *            The new birth province of user to modify.
	 * @param birthCountry
	 *            The new birth country of user to modify.
	 * @param birthCity
	 *            The new birth city of user to modify.
	 * @param phoneNumber
	 *            The new phone number of user to modify.
	 * @param faxNumber
	 *            The new fax number of user to modify.
	 * @param email
	 *            The new email number of user to modify.
	 * @param note
	 *            New notes about user to modify.
	 * @return {@link com.accenture.sdp.csm.dto.DataServiceResponse} that contains the informations related operation result and operation description.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @see SdpPartyManager#modifyChildParty(Long, String, String, String, Long, String, Long, String, String, String, String, String, String, String, String,
	 *      Date, String, String, String, String, String, String, String, String, EntityManager)
	 */
	public DataServiceResponse modifyChildParty(Long partyId, String partyName, String partyDescription, String externalId, String partyProfile,
			Long userDefaultSiteId, String firstName, String lastName, String streetAddress, String city, String zipCode, String province, String country,
			String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber, String email,
			String note, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(USER_DEFAULT_SITE_ID, userDefaultSiteId);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put(STREET_ADDRESS, streetAddress);
		logMap.put(CITY, city);
		logMap.put(ZIPCODE, zipCode);
		logMap.put(PROVINCE, province);
		logMap.put(COUNTRY, country);
		logMap.put(GENDER, gender);
		logMap.put(BIRTH_DATE, Utilities.formatDate(birthDate));
		logMap.put(BIRTH_PROVINCE, birthProvince);
		logMap.put(BIRTH_COUNTRY, birthCountry);
		logMap.put(BIRTH_CITY, birthCity);
		logMap.put(PHONE_NUMBER, phoneNumber);
		logMap.put(FAX_NUMBER, faxNumber);
		logMap.put(EMAIL, email);
		logMap.put(NOTE, note);
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

			modifyChildParty(partyId, partyName, partyDescription, externalId, partyProfile, userDefaultSiteId, firstName, lastName, streetAddress, city,
					zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber, faxNumber, email, note,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the validation and the business logic to modify a Child Party. It don't manage transaction. This method can be called only
	 * from this or other Manager classes. The party must be a child party with logical status Active.
	 * </p>
	 * 
	 * @param partyId
	 *            The id of party to modify
	 * @param partyName
	 *            The new Name of the party to modify.
	 * @param partyDescription
	 *            The new short description of the party to modify.
	 * @param externalId
	 *            The new external id of new party to create used by external system to modify.
	 * @param partyGroupId
	 *            The new id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario)
	 *            to modify. It should be the same of parent.
	 * @param partyProfile
	 *            The new Extended profile of the party that contains an XmlDocument provides additional\custom information about this party to modify.
	 * @param userDefaultSiteId
	 *            The new Identifier of the Site associated to the user to modify. The User Site ID must be a site associated to the parent party.
	 * @param firstName
	 *            The new first name of user to modify.
	 * @param lastName
	 *            The new last name of user to modify.
	 * @param streetAddress
	 *            The new street home address to modify.
	 * @param city
	 *            The new city of user to modify.
	 * @param zipCode
	 *            The new Postcode of user to modify.
	 * @param province
	 *            The new province of user to modify.
	 * @param country
	 *            The new country of user to modify.
	 * @param gender
	 *            The new gender of user to modify.
	 * @param birthDate
	 *            The new birth date of user to modify.
	 * @param birthProvince
	 *            The new birth province of user to modify.
	 * @param birthCountry
	 *            The new birth country of user to modify.
	 * @param birthCity
	 *            The new birth city of user to modify.
	 * @param phoneNumber
	 *            The new phone number of user to modify.
	 * @param faxNumber
	 *            The new fax number of user to modify.
	 * @param email
	 *            The new email number of user to modify.
	 * @param note
	 *            new notes about user to modify.
	 * @param modifyBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object just modified.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * 
	 */
	SdpParty modifyChildParty(Long partyId, String partyName, String partyDescription, String externalId, String partyProfile, Long userDefaultSiteId,
			String firstName, String lastName, String streetAddress, String city, String zipCode, String province, String country, String gender,
			Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber, String email, String note,
			String modifyBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
		partyHelper.validateSearchPartyById(partyId);
		partyHelper.validateChildParty(partyName, firstName, lastName, gender, birthDate);

		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		if (partyHelper.isAParent(party)) {
			throw new ValidationException(Constants.CODE_HIERARCHY_NOT_RESPECTED, PARTY_ID, partyId);
		}
		if (!isStatusActive(party.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, PARTY_ID, partyId);
		}
		SdpSite site = partySiteHelper.retrieveDefaultPartySite(userDefaultSiteId, party.getParentParty().getSdpPartySites());

		// verifica duplicati
		if (!Utilities.isNull(externalId)) {
			SdpParty temp = partyHelper.searchByExternalId(externalId, em);
			if (temp != null && !temp.getPartyId().equals(partyId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		partyHelper.modifyChildParty(party, partyName, partyDescription, partyProfile, site, externalId, firstName, lastName, streetAddress, city, zipCode,
				province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber, faxNumber, email, note, modifyBy);
		return party;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new Party as a Child Party (used to create a new user). The new party is created by default with logical
	 * status = ACTIVE and logical party type=User. It uses
	 * {@link SdpPartyManager#modifyChildParty(Long, String, String, String, Long, String, Long, String, String, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, EntityManager)}
	 * </p>
	 * 
	 * @param parentPartyId
	 *            The id of parent party to modify
	 * @param partyId
	 *            The id of child party to modify
	 * @param partyName
	 *            The new name of the party.
	 * @param partyDescription
	 *            A new short description of the party to modify.
	 * @param externalId
	 *            new external id of new party to create used by external system to modify.
	 * @param partyGroupId
	 *            The new id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario)
	 *            to modify. It should be the same of parent.
	 * @param partyProfile
	 *            New extended profile of the party that contains an XmlDocument provides additional\custom information about this party to modify.
	 * @param userDefaultSiteId
	 *            New Identifier of the Site associated to the user to modify. The User Site ID must be a site associated to the parent party.
	 * @param firstName
	 *            The new first name of user to modify.
	 * @param lastName
	 *            The new last name of user to modify.
	 * @param streetAddress
	 *            New street home address to modify.
	 * @param city
	 *            The new city of user to modify.
	 * @param zipCode
	 *            The new Postcode of user to modify.
	 * @param province
	 *            The new province of user to modify.
	 * @param country
	 *            The new country of user to modify.
	 * @param gender
	 *            The new gender of user to modify.
	 * @param birthDate
	 *            The new birth date of user to modify.
	 * @param birthProvince
	 *            The new birth province of user to modify.
	 * @param birthCountry
	 *            The new birth country of user to modify.
	 * @param birthCity
	 *            The new birth city of user to modify.
	 * @param phoneNumber
	 *            The new phone number of user to modify.
	 * @param faxNumber
	 *            The new fax number of user to modify.
	 * @param email
	 *            The new email number of user to modify.
	 * @param note
	 *            New notes about user to modify.
	 * @return {@link com.accenture.sdp.csm.dto.DataServiceResponse} that contains the informations related operation result and operation description.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @see SdpPartyManager#modifyChildParty(Long, String, String, String, Long, String, Long, String, String, String, String, String, String, String, String,
	 *      Date, String, String, String, String, String, String, String, String, EntityManager)
	 */
	public DataServiceResponse modifyChildPartyAndParentDummy(Long parentPartyId, Long partyId, String partyName, String partyDescription, String externalId,
			String partyProfile, Long userDefaultSiteId, String firstName, String lastName, String streetAddress, String city, String zipCode, String province,
			String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber,
			String email, String note, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_DESCRIPTION, partyDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARTY_PROFILE, partyProfile);
		logMap.put(USER_DEFAULT_SITE_ID, userDefaultSiteId);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put(STREET_ADDRESS, streetAddress);
		logMap.put(CITY, city);
		logMap.put(ZIPCODE, zipCode);
		logMap.put(PROVINCE, province);
		logMap.put(COUNTRY, country);
		logMap.put(GENDER, gender);
		logMap.put(BIRTH_DATE, Utilities.formatDate(birthDate));
		logMap.put(BIRTH_PROVINCE, birthProvince);
		logMap.put(BIRTH_COUNTRY, birthCountry);
		logMap.put(BIRTH_CITY, birthCity);
		logMap.put(PHONE_NUMBER, phoneNumber);
		logMap.put(FAX_NUMBER, faxNumber);
		logMap.put(EMAIL, email);
		logMap.put(NOTE, note);
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

			modifyChildPartyAndParentDummy(parentPartyId, partyId, partyName, partyDescription, externalId, partyProfile, userDefaultSiteId, firstName,
					lastName, streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber,
					faxNumber, email, note, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the calls to internal methods
	 * {@link SdpPartyManager#_modifyParentParty(Long, String, String, String, String, Long, String, EntityManager)} and
	 * {@link SdpPartyManager#modifyChildParty(Long, String, String, String, Long, String, Long, String, String, String, String, String, String, String, String, Date, String, String, String, String, String, String, String, String, EntityManager)}
	 * to modify a Parent and a related Child Party. It don't manage transaction. This method can be called only from this or other Manager classes. The party
	 * must be a child party with logical status Active.
	 * </p>
	 * 
	 * @param parentPartyId
	 *            The id of parent party to modify Required.
	 * @param partyId
	 *            The id of child party to modify Required.
	 * @param partyName
	 *            The new name of the party.
	 * @param partyDescription
	 *            new short description of the party to modify.
	 * @param externalId
	 *            new external id of new party to create used by external system to modify.
	 * @param partyGroupId
	 *            The new id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario)
	 *            to modify. It should be the same of parent.
	 * @param partyProfile
	 *            new extended profile of the party that contains an XmlDocument provides additional\custom information about this party to modify.
	 * @param userDefaultSiteId
	 *            Identifier of the Site associated to the user to modify. The new User Site ID must be a site associated to the parent party.
	 * @param firstName
	 *            The new first name of user to modify. Required.
	 * @param lastName
	 *            The new last name of user to modify. Required.
	 * @param streetAddress
	 *            new street home address to modify.
	 * @param city
	 *            The new city of user to modify.
	 * @param zipCode
	 *            The new Postcode of user to modify.
	 * @param province
	 *            The new province of user to modify.
	 * @param country
	 *            The new country of user to modify.
	 * @param gender
	 *            The new gender of user to modify.
	 * @param birthDate
	 *            The new birth date of user to modify.
	 * @param birthProvince
	 *            The new birth province of user to modify.
	 * @param birthCountry
	 *            The new birth country of user to modify.
	 * @param birthCity
	 *            The new birth city of user to modify.
	 * @param phoneNumber
	 *            The new phone number of user to modify.
	 * @param faxNumber
	 *            The new fax number of user to modify.
	 * @param email
	 *            The email number of user to modify.
	 * @param note
	 *            new notes about user to modify.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object of child just modified.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * @see SdpPartyManager#c_modifyParentParty(Long, String, String, String, String, Long, String, EntityManager)
	 * @see SdpPartyManager#modifyChildParty(Long, String, String, String, Long, String, Long, String, String, String, String, String, String, String, String,
	 *      Date, String, String, String, String, String, String, String, String, EntityManager)
	 */
	SdpParty modifyChildPartyAndParentDummy(Long parentPartyId, Long partyId, String partyName, String partyDescription, String externalId,
			String partyProfile, Long userDefaultSiteId, String firstName, String lastName, String streetAddress, String city, String zipCode, String province,
			String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber,
			String email, String note, String modifyBy, EntityManager em) throws PropertyNotFoundException, ValidationException {

		modifyParentParty(parentPartyId, partyName, partyDescription, null, partyProfile, modifyBy, em);
		SdpParty party = modifyChildParty(partyId, partyName, partyDescription, externalId, partyProfile, userDefaultSiteId, firstName, lastName,
				streetAddress, city, zipCode, province, country, gender, birthDate, birthProvince, birthCountry, birthCity, phoneNumber, faxNumber, email,
				note, modifyBy, em);
		return party;
	}

	/**
	 * <p>
	 * This method allows to update the links between a party and the related party groups with the following constraints:
	 * 
	 * <li>The party status is ACTIVE</li>
	 * </p>
	 * 
	 * @param partyId
	 *            Id of the solution to modify
	 * @param partyGroups
	 *            party groups to relate to the party to create
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyPartyCluster(Long partyId, List<SdpPartyGroupRequestDto> partyGroups, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put("partyGroup", partyGroups);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyPartyCluster(partyId, partyGroups, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
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

	SdpParty modifyPartyCluster(Long partyId, List<SdpPartyGroupRequestDto> partyGroups, String modifiedBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpPartyHelper handler = SdpPartyHelper.getInstance();
		SdpPartyGroupHelper groupHelper = SdpPartyGroupHelper.getInstance();
		// formal validation
		handler.validateSearchPartyById(partyId);
		groupHelper.validateLinkUpdateOperation(partyGroups);
		// workflow validation
		SdpParty toModify = handler.searchPartyById(partyId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, PARTY_ID, partyId);
		}
		for (SdpPartyGroupRequestDto dto : partyGroups) {
			SdpPartyGroup group = groupHelper.searchPartyGroupById(dto.getPartyGroupId(), em);
			if (group == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, String.valueOf(dto.getPartyGroupId()));
			}
			if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.DELETE.getValue())) {
				groupHelper.removePartyGroupLink(toModify, group, modifiedBy);
			} else if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.NEW.getValue())) {
				// verifica duplicati -> gestisco direttamente sulla lista
				if (toModify.getPartyGroups().contains(group)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(PARTY_ID, partyId), new ParameterDto(PARTY_GROUP_ID,
							String.valueOf(dto.getPartyGroupId())));
				}
				groupHelper.addPartyGroupLink(toModify, group, modifiedBy);
			}
		}
		return toModify;
	}

	/**
	 * <p>
	 * This method allows to set the logical status of a party to DELETE. This method modify the status of child or parent party. It uses
	 * {@link SdpPartyManager#deleteParty(Long, String, EntityManager)}.
	 * </p>
	 * 
	 * @param partyId
	 *            Id of the party to be update to delete status.
	 * @return {@link com.accenture.sdp.csm.dto.DataServiceResponse} that contains the informations related operation result and operation description.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @see SdpPartyManager#deleteParty(Long, String, EntityManager)
	 */
	public DataServiceResponse deleteParty(Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
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

			deleteParty(partyId, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the validation and the business logic to create new Parent Party. It don't manage transaction. This method can be called
	 * only from this or other Manager classes. The new party is created by default with logical status = ACTIVE and logical party type=Organization.
	 * </p>
	 * 
	 * @param partyId
	 *            Id of the party to be update to delete status. Required.
	 * @param deletedBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return void.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * 
	 */
	void deleteParty(Long partyId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		partyHelper.validateSearchPartyById(partyId);
		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		partyHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, party.getStatusId(),
				ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		if (partyHelper.isAParent(party)) {
			checkDeleteParent(partyId, em);
		} else {
			checkDeleteChild(partyId, em);
		}
		partyHelper.deleteParty(party, deletedBy);

	}

	private void checkDeleteChild(Long partyId, EntityManager em) throws ValidationException, PropertyNotFoundException {
		SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();
		List<SdpAccount> accountsNotDeleted = accountHelper.searchAccountsNotDeletedByPartyId(partyId, em);
		if (accountsNotDeleted != null && !accountsNotDeleted.isEmpty()) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		List<SdpCredential> credentialsNotDeleted = credentialHelper.searchCredentialsNotDeletedByPartyId(partyId, em);
		if (credentialsNotDeleted != null && !credentialsNotDeleted.isEmpty()) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		List<SdpSubscription> subscribtionsNotDeleted = subscriptionHelper.searchSubscriptionsNotDeletedByPartyId(partyId, em);
		if (subscribtionsNotDeleted != null && !subscribtionsNotDeleted.isEmpty()) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
	}

	private void checkDeleteParent(Long partyId, EntityManager em) throws ValidationException, PropertyNotFoundException {
		// check site not deleted
		SdpSiteHelper siteHelper = SdpSiteHelper.getInstance();
		Long nSites = siteHelper.countSitesNotDeletedByPartyId(partyId, em);
		if (nSites != null && nSites > 0L) {
			log.logDebug("party has site not deleted");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
		// check account not deleted
		SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();
		List<SdpAccount> accountsNotDeleted = accountHelper.searchAccountsNotDeletedByPartyId(partyId, em);
		if (accountsNotDeleted != null && !accountsNotDeleted.isEmpty()) {
			log.logDebug("party has account not deleted");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
		// check credential not deleted
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		List<SdpCredential> credentialsNotDeleted = credentialHelper.searchCredentialsNotDeletedByPartyId(partyId, em);
		if (credentialsNotDeleted != null && !credentialsNotDeleted.isEmpty()) {
			log.logDebug("party has credential not deleted");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
		// check child not deleted
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		List<SdpParty> partiesNotDeleted = partyHelper.searchChildsNotDeletedByParentPartyId(partyId, em);
		if (partiesNotDeleted != null && !partiesNotDeleted.isEmpty()) {
			log.logDebug("party has child not deleted");
			throw new ValidationException(Constants.CODE_ELEMENT_WITH_NOT_DELETED_CHILDS, PARTY_ID, partyId);
		}
		// check subscription not deleted
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		List<SdpSubscription> subscribtionsNotDeleted = subscriptionHelper.searchSubscriptionsNotDeletedByPartyId(partyId, em);
		if (subscribtionsNotDeleted != null && !subscribtionsNotDeleted.isEmpty()) {
			log.logDebug("party has subscription not deleted");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PARTY_ID, partyId);
		}
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a parent party with party_type = Organization.. This search is a rigth like search
	 * </p>
	 * 
	 * @param partyName
	 *            The new name of the party. Required.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario) to
	 *            modify. Required.
	 * @param startPosition
	 *            The index of result. it's the first record returned form result set. Required.
	 * @param maxRecordsNumber
	 *            The max number of record returned. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpParentPartyDto} and the total count of the search.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchParentParty(String partyName, Long partyGroupId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(PARTY_NAME, partyName);
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			partyHelper.validateSearchPartyByName(partyName);
			partyHelper.validateSearchAll(startPosition, maxRecordsNumber);
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpPartyGroup partyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			// executeSelect count of results
			Long totalResult = partyHelper.countParentPartyByNameLikeAndPartyGroupId(partyName, partyGroupId, em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpParty> parties = partyHelper.searchParentPartyByNameLikeAndPartyGroupId(partyName, partyGroupId, startPosition, maxRecordsNumber, em);

			if (parties == null || parties.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertParentParties(parties));
			resp.setTotalResult(totalResult);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to the child parties associated to a party parent (with party type = Organization) if in input is
	 * valorized only the parameter Parent Party ID and a party child (or a list of party child in case of right search) of the party parent present in input.
	 * This search is a right like search only for the field party name if valorized.
	 * </p>
	 * 
	 * @param parentPartyId
	 *            The id of parent party that Required
	 * @param partyName
	 *            The name of the party.
	 * @param startPosition
	 *            The index of result. it's the first record returned form result set. Required.
	 * @param maxRecordsNumber
	 *            The max number of record returned. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpChildPartyDto} and the total count of the search.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchChildParty(Long parentPartyId, String partyName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(PARENT_PARTY_ID, parentPartyId);
		logMap.put(PARTY_NAME, partyName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			partyHelper.validateSearchParentPartyById(parentPartyId);
			partyHelper.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			// executeSelect count of results
			Long totalResult = partyHelper.countChildPartyByParentPartyIdAndPartyNameLike(partyName, parentPartyId, em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpParty> parties = partyHelper.searchChildPartyByParentPartyIdAndPartyNameLike(partyName, parentPartyId, startPosition, maxRecordsNumber, em);

			if (parties == null || parties.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertChildParties(parties));
			resp.setTotalResult(totalResult);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a child party using as principal search key lastName. The key firstName, partyName and
	 * parentPartyName are optional.
	 * </p>
	 * 
	 * @param firstName
	 *            The First name of party to find
	 * @param lastName
	 *            The Last name of party to find Required.
	 * @param partyName
	 *            The name of the party.
	 * @param parentPartyName
	 *            The name of parent party.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario) to
	 *            modify. Required.
	 * @param startPosition
	 *            The index of result. it's the first record returned form result set. Required.
	 * @param maxRecordsNumber
	 *            The max number of record returned. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpChildPartyDto} and the total count of the search.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchChildPartyByName(String firstName, String lastName, String partyName, String parentPartyName, Long startPosition,
			Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put(PARTY_NAME, partyName);
		logMap.put("parentPartyName", parentPartyName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			partyHelper.validateSearchChildPartyByName(lastName);
			partyHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long nRes = partyHelper.countChildPartyByName(firstName, lastName, partyName, parentPartyName, em);

			if (nRes == null || nRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpParty> parties = partyHelper.searchChildPartyByName(firstName, lastName, partyName, parentPartyName, startPosition, maxRecordsNumber, em);

			if (parties == null || parties.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertChildParties(parties));
			resp.setTotalResult(nRes);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a party using as search key an account name related to Party.
	 * </p>
	 * 
	 * @param accountName
	 *            The username of account associated to the party to find.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario) to
	 *            modify. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related, operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpChildPartyDto} with only one record.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchPartyByAccount(String accountName, Long partyGroupId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put("accountName", accountName);
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			accountHelper.validateSearchAccountByName(accountName);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);
			SdpPartyGroup partyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			SdpAccount account = accountHelper.searchAccountByName(accountName, em);
			if (account == null || !account.getSdpParty().getPartyGroups().contains(partyGroup)) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "accountName", accountName);
			}
			SdpChildPartyDto partyDto = BeanConverter.convertChildParty(account.getSdpParty());
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpChildPartyDto> partiesResponse = new ArrayList<SdpChildPartyDto>();
			partiesResponse.add(partyDto);
			resp.setSearchResult(partiesResponse);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a child party using as search key a siteId .
	 * </p>
	 * 
	 * @param siteId
	 *            The Id of a site used as search key. Required.
	 * @param partyGroupId
	 *            The new id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario)
	 *            to modify. Required.
	 * @param startPosition
	 *            The index of result. it's the first record returned form result set. Required.
	 * @param maxRecordsNumber
	 *            The max number of record returned. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpChildPartyDto} with only one record.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchPartiesBySite(Long siteId, Long partyGroupId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("siteId", siteId);
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
			SdpPartyHelper helper = SdpPartyHelper.getInstance();
			// Validazione Formale
			partySiteHelper.validateSearchSiteById(siteId);
			SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);
			helper.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpSite site = partySiteHelper.searchSiteById(siteId, em);
			if (site == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "siteId", siteId);
			}
			SdpPartyGroup partyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			if (maxRecordsNumber < 0L || startPosition < 0L) {
				throw new ValidationException(Constants.CODE_GENERIC_ERROR);
			}
			List<SdpParty> parties = new ArrayList<SdpParty>();
			Long nRes = helper.countChildPartyBySiteIdAndPartyGroupId(siteId, partyGroupId, em);
			// modified maxRecordsNumber and startPosition
			// to consider the presence of parentParty
			Long actualMaxRecordsNumber = maxRecordsNumber;
			Long actualStartPosition = startPosition;
			if (actualMaxRecordsNumber.longValue() == 0L) {
				actualMaxRecordsNumber = nRes;
			}
			// parentParty will be added if partyGroup is ok
			if (site.getSdpParty().getPartyGroups().contains(partyGroup)) {
				nRes++;
				// parentParty is considered as first position
				if (actualStartPosition.longValue() == 0L) {
					parties.add(site.getSdpParty());
					actualMaxRecordsNumber--;
				} else {
					actualStartPosition--;
				}
			}
			// LO ZERO SI RENDE NECESSARIO PERCHE' NON E' PIU' SICURO CHE IL
			// PARENT VENGA AGGIUNTO
			if (nRes == null || nRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "siteId", siteId);
			}

			if (actualMaxRecordsNumber > 0L) {
				List<SdpParty> childParties = helper.searchChildPartyBySiteIdAndPartyGroupIdPaginated(siteId, partyGroupId, actualStartPosition,
						actualMaxRecordsNumber, em);
				if (childParties != null) {
					parties.addAll(childParties);
				}
			}

			if (parties == null || parties.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertChildParties(parties));
			resp.setTotalResult(nRes);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a child party using as search key a subscriptionId .
	 * </p>
	 * 
	 * @param subscriptionId
	 *            The Id of a subscription used as search key. Required.
	 * @param partyGroupId
	 *            The new id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario)
	 *            to modify. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpChildPartyDto} with only one record.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchPartyBySubscription(Long subscriptionId, Long partyGroupId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("subscriptionId", subscriptionId);
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
			SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);

			em = PersistenceManager.getEntityManager(tenantName);
			SdpPartyGroup partyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			SdpSubscription subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
			if (subscription == null || !subscription.getSdpParty().getPartyGroups().contains(partyGroup)) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "subscriptionId", subscriptionId);
			}
			ArrayList<SdpChildPartyDto> partiesResponse = new ArrayList<SdpChildPartyDto>();
			partiesResponse.add(BeanConverter.convertChildParty(subscription.getSdpParty()));
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(partiesResponse);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a child party using as search key the username of a credential.
	 * </p>
	 * 
	 * @param userName
	 *            The username of credential associated at party to find. Required.
	 * @param partyGroupId
	 *            The id of refPartyGroup that relates the party (Typically Residential or Business but it can assume also others valued in custom scenario) to
	 *            modify. Required.
	 * @return {@link com.accenture.sdp.csm.dto.SearchServiceResponse} that contains the informations related operation result, operation description and in
	 *         case of success return also a list of {@link com.accenture.sdp.csm.dto.responses.SdpChildPartyDto} with only one record.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 */
	public SearchServiceResponse searchPartyByCredential(String userName, Long partyGroupId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpCredentialHelper credHelper = SdpCredentialHelper.getInstance();
			credHelper.validateSearchCredentialByUsername(userName);
			SdpPartyGroupHelper partyGroupHelper = SdpPartyGroupHelper.getInstance();
			partyGroupHelper.validateSearchPartyGroupById(partyGroupId);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpPartyGroup partyGroup = partyGroupHelper.searchPartyGroupById(partyGroupId, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_ID, partyGroupId);
			}
			SdpCredential credential = credHelper.searchCredentialByUsername(userName, em);
			if (credential == null || !credential.getSdpParty().getPartyGroups().contains(partyGroup)) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
			}
			ArrayList<SdpChildPartyDto> partiesResponse = new ArrayList<SdpChildPartyDto>();
			partiesResponse.add(BeanConverter.convertChildParty(credential.getSdpParty()));
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(partiesResponse);
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

	/**
	 * <p>
	 * This method allows to change the status of entity in accord to possible states stored on db It uses
	 * {@link SdpPartyManager#partyChangeStatus(Long, String, String, EntityManager)} .
	 * </p>
	 * 
	 * @param partyId
	 *            Id of the party to be update the status.
	 * @param status
	 *            the logical name of new status.
	 * @return {@link com.accenture.sdp.csm.dto.DataServiceResponse} that contains the informations related operation result and operation description.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @see SdpPartyManager#partyChangeStatus(Long, String, String, EntityManager)
	 */
	public DataServiceResponse partyChangeStatus(Long partyId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(STATUS, status);
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

			partyChangeStatus(partyId, status, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
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
	 * Internal method that performs the validation and the business logic to change the status of a Party. It don't manage transaction. This method can be
	 * called only from this or other Manager classes. The allowed status that entity can be moved are store into SdpStatusVariation.
	 * </p>
	 * 
	 * @param partyId
	 *            Id of the party to be update to delete status. Required.
	 * @param status
	 *            The name of new status Required
	 * @param changedBy
	 *            The business logic class that requires this action.
	 * @param em
	 *            EntityManager to use to perform the action. Required.
	 * @return {@link com.accenture.sdp.csm.model.jpa.SdpParty} Object just modified.
	 * @exception PropertyNotFoundException
	 *                throws when an configuration error don't allow to processing the request.
	 * @exception ValidationException
	 *                throws when there is an error generated by validation action on SpdParty entity o performing the store.
	 * 
	 */
	SdpParty partyChangeStatus(Long partyId, String status, String changedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		// Validazione Formale
		partyHelper.validateSearchPartyById(partyId);
		partyHelper.validateChangeStatus(status);

		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		Long statusId = StatusIdConverter.getStatusValue(status);
		partyHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, party.getStatusId(), statusId, em);
		partyHelper.changeStatus(party, statusId, changedBy);

		return party;
	}

	public SearchServiceResponse searchParty(Long partyId, String tenantName) throws PropertyNotFoundException {
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
			// Validazione Formale
			SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
			partyHelper.validateSearchPartyById(partyId);
			em = PersistenceManager.getEntityManager(tenantName);

			SdpParty party = partyHelper.searchPartyById(partyId, em);

			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			List<SdpPartyResponseDto> result = new ArrayList<SdpPartyResponseDto>();
			result.add(BeanConverter.convertParty(party));
			resp.setSearchResult(result);
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
