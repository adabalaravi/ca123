package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.CreateServicesResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpAccountRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartySiteRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSubscriptionDetailRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSubscriptionRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionDetailResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpAccountHelper;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpPackageHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpRoleHelper;
import com.accenture.sdp.csm.helpers.SdpSiteHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpRole;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.model.jpa.SdpSubscriptionDetail;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpSubscriptionManager extends SdpBaseManager {

	private static final String OWNER_ACCOUNT_NAME = "ownerAccountName";
	private static final String PAYEE_ACCOUNT_NAME = "payeeAccountName";
	private static final String OWNER_ACCOUNT_ID = "ownerId";
	private static final String PAYEE_ACCOUNT_ID = "payeeId";

	private SdpSubscriptionManager() {
		super();
	}

	private static SdpSubscriptionManager instance;

	public static SdpSubscriptionManager getInstance() {
		if (instance == null) {
			instance = new SdpSubscriptionManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This is a crude method that allows to insert data into SDP_Subscription Table. If the operation ends with success, the status is automatically set at the
	 * value: "Activating".
	 * </p>
	 * 
	 * @param partyId
	 * @param parentSubscriptionId
	 * @param userName
	 * @param roleName
	 * @param ownerId
	 * @param payeeId
	 * @param siteId
	 * @param externalId
	 * @param startDate
	 * @param endDate
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createSubscription(Long partyId, Long solutionOfferId, Long parentSubscriptionId, String userName, String roleName,
			Long ownerId, Long payeeId, Long siteId, String externalId, Date startDate, Date endDate, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(USERNAME, userName);
		logMap.put(ROLE_NAME, roleName);
		logMap.put(OWNER_ACCOUNT_ID, ownerId);
		logMap.put(PAYEE_ACCOUNT_ID, payeeId);
		logMap.put(SITE_ID, siteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		CreateServiceResponse resp = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			SdpSubscription newSdpSubscription = createSubscription(partyId, solutionOfferId, parentSubscriptionId, userName, roleName, ownerId, payeeId,
					siteId, externalId, startDate, endDate, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newSdpSubscription.getSubscriptionId());
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

	SdpSubscription createSubscription(Long partyId, Long solutionOfferId, Long parentSubscriptionId, String userName, String roleName, Long ownerId,
			Long payeeId, Long siteId, String externalId, Date startDate, Date endDate, String createdBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {

		// Validazione Formale
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		subscriptionHelper.validateSubscription(partyId, solutionOfferId);
		SdpSubscription checkExternalId = subscriptionHelper.searchByExternalId(externalId, em);
		if (checkExternalId != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		SdpSubscription subscription = this.setupSubscription(partyId, solutionOfferId, parentSubscriptionId, userName, roleName, ownerId, payeeId, siteId,
				startDate, endDate, em);

		SdpParty party = subscription.getSdpParty();
		SdpSolutionOffer solutionOffer = subscription.getSdpSolutionOffer();
		SdpSubscription parentSubscription = subscription.getParentSubscription();
		SdpCredential credential = subscription.getSdpCredential();
		String sdpRoleName = subscription.getSdpRole();
		SdpAccount ownerAccount = subscription.getSdpAccountOwner();
		SdpAccount payeeAccount = subscription.getSdpAccountPayee();
		SdpSite site = subscription.getSdpPartySite();

		return subscriptionHelper.createSubscription(party, solutionOffer, parentSubscription, credential, sdpRoleName, ownerAccount, payeeAccount, site,
				externalId, startDate, endDate, createdBy, em);
	}

	/**
	 * <p>
	 * This is a crud method that allows to insert data into SDP_Subscription_Detail Table. If the operation ends with success, the status is automatically set
	 * at the value: "Activating"
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param packageId
	 * @param subscriptionOfferProfile
	 * @param externalId
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscriptionDetail and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createSubscriptionDetail(Long subscriptionId, Long packageId, String subscriptionOfferProfile, String externalId,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(PACKAGE_ID, packageId);
		logMap.put("subscriptionOfferProfile", subscriptionOfferProfile);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		CreateServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			SdpSubscriptionDetail subscriptionDetail = createSubscriptionDetail(subscriptionId, packageId, subscriptionOfferProfile, externalId,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(subscriptionDetail.getSubscriptionId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			log.logDebug("subscriptionDetail with id:%s inserted successfully", subscriptionDetail.getSubscriptionId());
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

	SdpSubscriptionDetail createSubscriptionDetail(Long subscriptionId, Long packageId, String subscriptionOfferProfile, String externalId, String createdBy,
			EntityManager em) throws PropertyNotFoundException, ValidationException {
		// Validazione Formale
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		subscriptionHelper.validateSubscriptionDetail(subscriptionId, packageId);

		SdpSubscriptionHelper subscritpionHelper = SdpSubscriptionHelper.getInstance();
		SdpSubscription subscription = null;
		if (subscriptionId != null) {
			subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
			if (subscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
			}
		}

		// verifica duplicati
		if (subscriptionHelper.searchSubscriptionDetailById(subscriptionId, packageId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SUBSCRIPTION_ID, subscriptionId), new ParameterDto(PACKAGE_ID,
					packageId));
		}
		if (subscriptionHelper.searchDetailByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		SdpPackage sdpPackage = packageHelper.searchPackageById(packageId, em);
		if (sdpPackage == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
		}
		// SDP_02_28
		if (!isStatusActive(sdpPackage.getStatusId())) {
			log.logDebug("package is not active");
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, PACKAGE_ID, packageId);
		}

		SdpSolutionOfferHelper solOffHelper = SdpSolutionOfferHelper.getInstance();
		SdpSolutionOffer solOffer = solOffHelper.searchSolutionOfferById(subscription.getSdpSolutionOffer().getSolutionOfferId(), em);
		// se scontata, la sol offer di riferimento e' quella padre
		if (solOffHelper.isDiscounted(solOffer)) {
			solOffer = solOffer.getParentSolutionOffer();
		}
		if (!sdpPackage.getSdpSolutionOffer().getSolutionOfferId().equals(solOffer.getSolutionOfferId())) {
			log.logDebug("package solutionOffer mismatches with subscription's one");
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PACKAGE_ID, packageId);
		}

		return subscritpionHelper.createSubscriptionDetail(subscription, sdpPackage, subscriptionOfferProfile, externalId, createdBy, em);
	}

	/**
	 * <p>
	 * This is an high level method that allows to insert into CSM database all informations concerning the subscriptions performed by a Party.
	 * 
	 * This method calls the following crude methods:
	 * 
	 * 1. _CreateSubscription; 2. _CreateSubscriptionDetail;
	 * 
	 * The _CreateSubscription method if if the process ends successfully returns as output the subscriptionId which is then passed as input for the
	 * _CreateSubscriptionDetail.
	 * 
	 * The _CreateSubscriptionDetail method will be invoked a number of times equal to the number of offers contained in the subscribed solution offer
	 * 
	 * When you create a subscription, if the operation ends with success, the status is automatically set at the value: "Activating". Otherwise rollback.
	 * </p>
	 * 
	 * @param partyId
	 * @param parentSubscriptionId
	 * @param userName
	 * @param roleName
	 * @param ownerId
	 * @param payeeId
	 * @param siteId
	 * @param externalId
	 * @param startDate
	 * @param endDate
	 * @param subscriptionDetailList
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createSubscription(Long partyId, Long solutionOfferId, Long parentSubscriptionId, String userName, String roleName,
			Long ownerId, Long payeeId, Long siteId, String externalId, Date startDate, Date endDate,
			List<SdpSubscriptionDetailRequestDto> subscriptionDetailList, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(USERNAME, userName);
		logMap.put(ROLE_NAME, roleName);
		logMap.put(OWNER_ACCOUNT_ID, ownerId);
		logMap.put(PAYEE_ACCOUNT_ID, payeeId);
		logMap.put(SITE_ID, siteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put("subscriptionDetail", subscriptionDetailList);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		CreateServiceResponse resp = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			SdpSubscription newSdpSubscription = this.createSubscription(partyId, solutionOfferId, parentSubscriptionId, userName, roleName, ownerId, payeeId,
					siteId, externalId, startDate, endDate, subscriptionDetailList, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newSdpSubscription.getSubscriptionId());
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

	SdpSubscription createSubscription(Long partyId, Long solutionOfferId, Long parentSubscriptionId, String userName, String roleName, Long ownerId,
			Long payeeId, Long siteId, String externalId, Date startDate, Date endDate, List<SdpSubscriptionDetailRequestDto> subscriptionDetailList,
			String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException {

		SdpSubscription newSdpSubscription = createSubscription(partyId, solutionOfferId, parentSubscriptionId, userName, roleName, ownerId, payeeId, siteId,
				externalId, startDate, endDate, createdBy, em);
		log.logDebug("SdpSubscription with id:%s inserted successfully", newSdpSubscription.getSubscriptionId());

		if (newSdpSubscription != null && subscriptionDetailList != null) {
			Long subscriptionId = newSdpSubscription.getSubscriptionId();
			for (SdpSubscriptionDetailRequestDto detail : subscriptionDetailList) {
				SdpSubscriptionDetail subscriptionDetail = createSubscriptionDetail(subscriptionId, detail.getPackageId(),
						detail.getSubscriptionOfferProfile(), detail.getExternalId(), createdBy, em);
				log.logDebug("SdpSubscriptionDetail with subscriptionId:%s / packageId inserted successfully", subscriptionId, detail.getPackageId());
				newSdpSubscription.getSdpSubscriptionDetails().add(subscriptionDetail);
			}
		}
		return newSdpSubscription;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database at once all informations related to a subscription and the party, the account, the credentials and the
	 * site associated with it. This method will call in the correct sequence the interfaces that are involved in the complete creation.
	 * 
	 * When you create a subscription, if the operation ends with success, the status is automatically set at the value: "Activating". Otherwise If an error is
	 * found on each of the entities involved in the CreateSubscriptionComplete method you will have the full rollback.
	 * 
	 * This method calls the following methods in cascade:
	 * 
	 * 1. CreateParentPartyComplete 2. CreateSubscription
	 * </p>
	 * 
	 * @param partyName
	 * @param partyDescription
	 * @param partyGroupId
	 * @param externalId
	 * @param partyProfile
	 * @param userName
	 * @param password
	 * @param userNameExternalId
	 * @param secretQuestions
	 * @param sites
	 * @param accounts
	 * @param subscription
	 * @param subscriptionDetailList
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServicesResponse createPartyAndSubscription(String partyName, String partyDescription, List<Long> partyGroups, String externalId,
			String partyProfile, String userName, String password, String userNameExternalId, List<SdpSecretQuestionRequestDto> secretQuestions,
			List<SdpPartySiteRequestDto> sites, List<SdpAccountRequestDto> accounts, SdpSubscriptionRequestDto subscription,
			List<SdpSubscriptionDetailRequestDto> subscriptionDetailList, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("partyName", partyName);
		logMap.put("partyDescription", partyDescription);
		logMap.put(PARTY_GROUP_ID, partyGroups);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("partyProfile", partyProfile);
		logMap.put(USERNAME, userName);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : "***");
		logMap.put("userNameExternalId", userNameExternalId);
		logMap.put("secretQuestion", secretQuestions);
		logMap.put("site", sites);
		logMap.put("account", accounts);
		logMap.put("subscription", subscription);
		logMap.put("subscriptionDetail", subscriptionDetailList);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		CreateServicesResponse resp = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpParty party = SdpPartyManager.getInstance().createParentPartyComplete(partyName, partyDescription, partyGroups, externalId, partyProfile,
					userName, password, userNameExternalId, secretQuestions, sites, accounts, Utilities.getCurrentClassAndMethod(), em);

			Long partyId = party.getPartyId();

			log.logDebug("SdpParty with id:%s inserted successfully", partyId);

			SdpAccount ownerAccount = null;
			if (!Utilities.isNull(subscription.getOwnerAccountName())) {
				ownerAccount = (SdpAccountHelper.getInstance()).retrieveAccount(subscription.getOwnerAccountName(), party.getSdpAccounts());
				if (ownerAccount == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "subscription.ownerAccountName", subscription.getOwnerAccountName());
				}
			}
			SdpAccount payeeAccount = null;
			if (!Utilities.isNull(subscription.getPayeeAccountName())) {
				payeeAccount = (SdpAccountHelper.getInstance()).retrieveAccount(subscription.getPayeeAccountName(), party.getSdpAccounts());
				if (payeeAccount == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "subscription.payeeAccountName", subscription.getPayeeAccountName());
				}
			}
			SdpSite site = null;
			if (!Utilities.isNull(subscription.getSiteName())) {
				site = (SdpSiteHelper.getInstance()).retrievePartySite(subscription.getSiteName(), party.getSdpPartySites());
				if (site == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "subscription.siteName", subscription.getSiteName());
				}
			}

			if (subscription.getUserName() != null && !subscription.getUserName().equals(userName)) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "subscription.userName", subscription.getUserName());
			}

			SdpSubscription newSdpSubscription = this.createSubscription(partyId, subscription.getSolutionOfferId(), subscription.getParentSubscriptionId(),
					subscription.getUserName(), subscription.getRoleName(), ownerAccount == null ? null : ownerAccount.getAccountId(),
					payeeAccount == null ? null : payeeAccount.getAccountId(), site == null ? null : site.getSiteId(), subscription.getExternalId(),
					subscription.getStartDate(), subscription.getEndDate(), subscriptionDetailList, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			log.logDebug("SdpSubscription with id:%s inserted successfully", newSdpSubscription.getSubscriptionId());

			resp = buildCreateResponses(Constants.CODE_OK);
			resp.addEntityId(PARTY_ID, partyId);
			resp.addEntityId(SUBSCRIPTION_ID, newSdpSubscription.getSubscriptionId());
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
	 * This is a crude method that allows to update the parameters on SDP_Subscription Table.
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param parentSubscriptionId
	 * @param userName
	 * @param roleName
	 * @param ownerId
	 * @param payeeId
	 * @param siteId
	 * @param externalId
	 * @param startDate
	 * @param endDate
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse modifySubscription(Long subscriptionId, Long parentSubscriptionId, String userName, String roleName, Long ownerId, Long payeeId,
			Long siteId, String externalId, Date startDate, Date endDate, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		String updatedBy = Utilities.getCurrentClassAndMethod();
		log.logDebug(updatedBy);
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(USERNAME, userName);
		logMap.put(ROLE_NAME, roleName);
		logMap.put(OWNER_ACCOUNT_ID, ownerId);
		logMap.put(PAYEE_ACCOUNT_ID, payeeId);
		logMap.put(SITE_ID, siteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		DataServiceResponse response = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifySubscription(subscriptionId, parentSubscriptionId, userName, roleName, ownerId, payeeId, siteId, externalId, startDate, endDate, updatedBy,
					em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			response = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			response = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
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
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	SdpSubscription modifySubscription(Long subscriptionId, Long parentSubscriptionId, String userName, String roleName, Long ownerId, Long payeeId,
			Long siteId, String externalId, Date startDate, Date endDate, String updatedBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		// validazione
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		subscriptionHelper.validateSearchSubscriptionById(subscriptionId);

		SdpSubscription subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
		if (subscription == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
		}
		if (!isStatusUpdating(subscription.getStatusId()) && !isStatusActivating(subscription.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SUBSCRIPTION_ID, subscriptionId);
		}
		SdpSubscription checkExternalId = subscriptionHelper.searchByExternalId(externalId, em);
		if (checkExternalId != null && !checkExternalId.getSubscriptionId().equals(subscriptionId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		SdpSubscription subscriptionCopy = this.setupSubscription(subscription.getSdpParty().getPartyId(), subscription.getSdpSolutionOffer()
				.getSolutionOfferId(), parentSubscriptionId, userName, roleName, ownerId, payeeId, siteId, startDate, endDate, em);

		SdpSubscription parentSubscription = subscriptionCopy.getParentSubscription();
		SdpCredential credential = subscriptionCopy.getSdpCredential();
		String sdpRoleName = subscriptionCopy.getSdpRole();
		SdpAccount ownerAccount = subscriptionCopy.getSdpAccountOwner();
		SdpAccount payeeAccount = subscriptionCopy.getSdpAccountPayee();
		SdpSite site = subscriptionCopy.getSdpPartySite();

		subscriptionHelper.modifySubscription(subscription, parentSubscription, credential, sdpRoleName, ownerAccount, payeeAccount, site, externalId,
				startDate, endDate, updatedBy);
		return subscription;
	}

	/**
	 * <p>
	 * This is a crude method that allows to update the parameters on SDP_Subscription_DetailsTable.
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param packageId
	 * @param subscriptionOfferProfile
	 * @param detailExternalId
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscriptionDetail and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse modifySubscriptionDetail(Long subscriptionId, Long packageId, String subscriptionOfferProfile, String detailExternalId,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		String updatedBy = Utilities.getCurrentClassAndMethod();
		log.logDebug(updatedBy);
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(PACKAGE_ID, packageId);
		logMap.put("subscriptionOfferProfile", subscriptionOfferProfile);
		logMap.put(EXTERNAL_ID, detailExternalId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		DataServiceResponse response = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifySubscriptionDetail(subscriptionId, packageId, subscriptionOfferProfile, detailExternalId, updatedBy, em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			response = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			response = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
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
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	SdpSubscriptionDetail modifySubscriptionDetail(Long subscriptionId, Long packageId, String subscriptionOfferProfile, String detailExternalId,
			String updatedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		subscriptionHelper.validateSubscriptionDetail(subscriptionId, packageId);
		SdpSubscription subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
		if (subscription == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
		}

		SdpPackage sdpPackage = null;
		if (packageId != null) {
			sdpPackage = packageHelper.searchPackageById(packageId, em);
			if (sdpPackage == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
			}
		}

		SdpSubscriptionDetail subscriptionDetailToUpdate = subscriptionHelper.searchSubscriptionDetailById(subscriptionId, packageId, em);
		if (subscriptionDetailToUpdate == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SUBSCRIPTION_ID, subscriptionId), new ParameterDto(PACKAGE_ID,
					packageId));
		}
		if (!isStatusUpdating(subscriptionDetailToUpdate.getStatusId()) && !isStatusActivating(subscriptionDetailToUpdate.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, new ParameterDto(SUBSCRIPTION_ID, subscriptionId), new ParameterDto(
					PACKAGE_ID, packageId));
		}

		SdpSubscriptionDetail checkExternalId = subscriptionHelper.searchDetailByExternalId(detailExternalId, em);
		if (checkExternalId != null && !checkExternalId.getSubscriptionId().equals(subscriptionId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, detailExternalId);
		}

		subscriptionHelper.modifySubscriptionDetail(subscriptionDetailToUpdate, subscriptionOfferProfile, detailExternalId, updatedBy);
		return subscriptionDetailToUpdate;
	}

	/**
	 * <p>
	 * This is an high level method that allows to update the information associated to a Subscription already present into CSM database. The method calls the
	 * following crude methods in cscade:
	 * 
	 * - _ModifySubscription - _ModifySubscriptionDetail
	 * 
	 * The method updates only the field valorized in input. The ModifySubscription method updates all the other attributes but not the Status. The Update is
	 * performed only if the entity is in Active Status. The change is committed on DB only in case of success, otherwise rollback.
	 * 
	 * If the update concerns the subscriptionOfferProfile, before making the change will be necessary to carry out a check on the status of the subscribed
	 * offer . If the status is 'Active' it will be possible to make the change otherwise it will return an error message (see codes table).
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param parentSubscriptionId
	 * @param userName
	 * @param roleName
	 * @param ownerId
	 * @param payeeId
	 * @param siteId
	 * @param externalId
	 * @param startDate
	 * @param endDate
	 * @param subscriptionDetailList
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse modifySubscription(Long subscriptionId, Long parentSubscriptionId, String userName, String roleName, Long ownerId, Long payeeId,
			Long siteId, String externalId, Date startDate, Date endDate, List<SdpSubscriptionDetailRequestDto> subscriptionDetailList, String tenantName)
			throws PropertyNotFoundException {

		long startTime = System.currentTimeMillis();
		String updatedBy = Utilities.getCurrentClassAndMethod();
		log.logDebug(updatedBy);
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(USERNAME, userName);
		logMap.put(ROLE_NAME, roleName);
		logMap.put(OWNER_ACCOUNT_ID, ownerId);
		logMap.put(PAYEE_ACCOUNT_ID, payeeId);
		logMap.put(SITE_ID, siteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put("subscriptionDetail", subscriptionDetailList);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		DataServiceResponse response = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			this.modifySubscription(subscriptionId, parentSubscriptionId, userName, roleName, ownerId, payeeId, siteId, externalId, startDate, endDate,
					subscriptionDetailList, updatedBy, em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			response = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			response = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
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
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;

	}

	SdpSubscription modifySubscription(Long subscriptionId, Long parentSubscriptionId, String userName, String roleName, Long ownerId, Long payeeId,
			Long siteId, String externalId, Date startDate, Date endDate, List<SdpSubscriptionDetailRequestDto> subscriptionDetailList, String updatedBy,
			EntityManager em) throws PropertyNotFoundException, ValidationException {

		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
		SdpSubscription subscription = null;
		if (subscriptionId != null) {
			subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
			if (subscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
			}
		}

		SdpSubscription subscriptionUpdated = this.modifySubscription(subscriptionId, parentSubscriptionId, userName, roleName, ownerId, payeeId, siteId,
				externalId, startDate, endDate, updatedBy, em);

		log.logDebug("SdpSubscription with id:%s modified successfully", subscriptionUpdated.getSubscriptionId());

		if (subscriptionUpdated != null && subscriptionDetailList != null) {
			for (SdpSubscriptionDetailRequestDto detail : subscriptionDetailList) {
				modifySubscriptionDetail(subscriptionId, detail.getPackageId(), detail.getSubscriptionOfferProfile(), detail.getExternalId(), updatedBy, em);

				log.logDebug("SdpSubscriptionDetail with subscriptionId:%s / packageId:%s modified successfully", subscriptionId, detail.getPackageId());
			}
		}
		return subscriptionUpdated;
	}

	/**
	 * <p>
	 * This method allows the logical deletion of an existing subscription and.for all the offer related to the subscription The deletion is related only to a
	 * particular user (Parent or child Party) linked to the subscription_id passed as input. By this method is possible to logically delete the entire
	 * subscription, passing the subscriptionId as input. In this case will be update the status on the SDP_Subscription Table and also on the
	 * SDP_Subscription_Detail table for all the record related to the subscriptionId passed as input.
	 * 
	 * If the deletion concerns a subscription of a Parent Party (Organization) it will be necessary to perform a check to verify that all the subscriptions of
	 * the child parties are already deleted. if it is not, it will be returned an error message (see codes table) the DeleteSubscription method sets the
	 * subscription in "Deleting" Status only if the operation ends with success, otherwise rollback. So that the operation ends successfully,the initial state
	 * of the subscription must be "Inactive"
	 * </p>
	 * 
	 * @param subscriptionId
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse deleteSubscription(Long subscriptionId, String tenantName) throws PropertyNotFoundException {

		long startTime = System.currentTimeMillis();
		String currentClassAndMethod = Utilities.getCurrentClassAndMethod();
		log.logDebug(currentClassAndMethod);
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			deleteSubscription(subscriptionId, currentClassAndMethod, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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

	void deleteSubscription(Long subscriptionId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {

		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();

		subscriptionHelper.validateSearchSubscriptionById(subscriptionId);

		// Check if subscription exist.
		SdpSubscription sdpSubscriptionToDelete = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
		if (sdpSubscriptionToDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
		}

		// Check if status is Inactive
		subscriptionHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_SUBSCRIPTION, sdpSubscriptionToDelete.getStatusId(),
				ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETING), em);

		// check if all childs are deleted.
		List<SdpSubscription> childsNotDeleted = subscriptionHelper.searchSubscriptionsNotDeletedByParentSubscription(sdpSubscriptionToDelete, em);
		if (childsNotDeleted != null && childsNotDeleted.size() > 0) {
			throw new ValidationException(Constants.CODE_ELEMENT_WITH_NOT_DELETED_CHILDS, SUBSCRIPTION_ID, subscriptionId);
		}

		// search subscription details
		List<SdpSubscriptionDetail> detailsToDelete = subscriptionHelper.searchSubscriptionDetailsBySubscriptionId(subscriptionId, em);

		// delete subscription
		subscriptionHelper.deleteSubscription(sdpSubscriptionToDelete, deletedBy);

		// delete details
		if (detailsToDelete != null) {
			for (SdpSubscriptionDetail subscriptionDetail : detailsToDelete) {
				subscriptionHelper.deleteSubscriptionDetail(subscriptionDetail, deletedBy, em);
			}

		}

	}

	/**
	 * <p>
	 * This method allows the logical deletion of a single or a group of offer subscription into an existing solution offer subscription The deletion is related
	 * only to a particular user (Parent or child Party) linked to the subscription_id passed as input. By this method is possible to logically delete only one
	 * or more offer subscription for a specific subscribed Solution Offer by passing subscriptionId and a list of packageId. as input. In this case will be
	 * update only the status on the SDP_Subscription_Details Table for the records related to the subscriptionId and the packageId list passed as input.
	 * 
	 * If the deletion concerns a subscription of a Parent Party (Organization) it will be necessary to perform a check to verify that all the subscriptions of
	 * the child parties are already deleted. if it is not, it will be returned an error message (see codes table) the DeleteSubscriptionOffer method sets the
	 * offer related to the subscription in "Deleting" Status only if the operation ends with success, otherwise rollback. So that the operation ends
	 * successfully,the initial state of the offer related to the subscription must be "Inactive"
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param packageIdList
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse deleteOfferInSubscription(Long subscriptionId, List<Long> packageIdList, String tenantName) throws PropertyNotFoundException {

		long startTime = System.currentTimeMillis();
		String currentClassAndMethod = Utilities.getCurrentClassAndMethod();
		log.logDebug(currentClassAndMethod);
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(PACKAGE_ID, packageIdList);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			this.deleteOfferInSubscription(subscriptionId, packageIdList, currentClassAndMethod, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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

	void deleteOfferInSubscription(Long subscriptionId, List<Long> packageIdList, String deletedBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {

		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();

		subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
		subscriptionHelper.validateSubscriptionDetailList(packageIdList);

		// Check if subscription exist.
		SdpSubscription sdpSubscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
		if (sdpSubscription == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
		}

		// check if all childs are deleted.

		List<SdpSubscription> childsNotDeleted = subscriptionHelper.searchSubscriptionsNotDeletedByParentSubscription(sdpSubscription, em);
		if (childsNotDeleted != null && childsNotDeleted.size() > 0) {
			throw new ValidationException(Constants.CODE_ELEMENT_WITH_NOT_DELETED_CHILDS, SUBSCRIPTION_ID, subscriptionId);
		}

		if (packageIdList != null) {
			for (Long packageId : packageIdList) {
				// Check if status is Inactiv
				SdpSubscriptionDetail subscriptionDetailToDelete = subscriptionHelper.searchSubscriptionDetailById(subscriptionId, packageId, em);
				if (subscriptionDetailToDelete == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, packageId);
				}
				subscriptionHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_SUBSCRIPTION, subscriptionDetailToDelete.getStatusId(),
						ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETING), em);
				subscriptionHelper.deleteSubscriptionDetail(subscriptionDetailToDelete, deletedBy, em);
			}
		}

	}

	/**
	 * <p>
	 * This method allows to change the status of an existing Subscription and.for all the offer related to the subscription. In this case will be update the
	 * status on the SDP_Subscription Table and also on the SDP_Subscription_Detail table for all the record related to the subscriptionId passed as input. This
	 * method does not set the status "Deleting".
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param statusName
	 * 
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse subscriptionChangeStatus(Long subscriptionId, String statusName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		String currentClassAndMethod = Utilities.getCurrentClassAndMethod();
		log.logDebug(currentClassAndMethod);
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put("statusName", statusName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			this.subscriptionChangeStatus(subscriptionId, statusName, currentClassAndMethod, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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

	SdpSubscription subscriptionChangeStatus(Long subscriptionId, String statusName, String updatedBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
		subscriptionHelper.validateChangeStatus(statusName);

		// Check if subscription exist.
		SdpSubscription sdpSubscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
		if (sdpSubscription == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
		}

		// Set new status to subscription
		Long nextStatus = StatusIdConverter.getStatusValue(statusName);
		subscriptionHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_SUBSCRIPTION, sdpSubscription.getStatusId(), nextStatus, em);

		List<SdpSubscriptionDetail> detailsToChangeStatus = subscriptionHelper.searchSubscriptionDetailsBySubscriptionId(subscriptionId, em);

		subscriptionHelper.changeSubscriptionStatus(sdpSubscription, nextStatus, updatedBy);

		// Set new status to ALL subscriptionDetails
		if (detailsToChangeStatus != null) {
			for (SdpSubscriptionDetail subscriptionDetail : detailsToChangeStatus) {
				subscriptionHelper.changeSubscriptionDetailStatus(subscriptionDetail, nextStatus, updatedBy, em);
			}
		}
		return sdpSubscription;
	}

	/**
	 * <p>
	 * This method allows to change the status of a single or a group of offer subscription into an existing solution offer subscription. In this case will be
	 * update only the status on the SDP_Subscription_Details Table for the records related to the subscriptionId and the packageId list passed as input. This
	 * method does not set the status "Deleting"
	 * </p>
	 * 
	 * @param statusName
	 * @param subscriptionId
	 * @param packageIdList
	 * @return Returns a DataServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse offerInSubscriptionChangeStatus(String statusName, Long subscriptionId, List<Long> packageIdList, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		String currentClassAndMethod = Utilities.getCurrentClassAndMethod();
		log.logDebug(currentClassAndMethod);
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put("statusName", statusName);
		logMap.put(PACKAGE_ID, packageIdList);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			this.offerInSubscriptionChangeStatus(statusName, subscriptionId, packageIdList, currentClassAndMethod, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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

	SdpSubscription offerInSubscriptionChangeStatus(String statusName, Long subscriptionId, List<Long> packageIdList, String updatedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
		subscriptionHelper.validateSubscriptionDetailList(packageIdList);
		subscriptionHelper.validateChangeStatus(statusName);
		Long nextStatus = StatusIdConverter.getStatusValue(statusName);

		// Check if subscription exist.
		SdpSubscription sdpSubscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
		if (sdpSubscription == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
		}

		// Set new status to ALL subscriptionDetails
		if (packageIdList != null) {
			for (Long packageId : packageIdList) {
				SdpSubscriptionDetail subscriptionDetailToChangeStatus = subscriptionHelper.searchSubscriptionDetailById(subscriptionId, packageId, em);
				if (subscriptionDetailToChangeStatus == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SUBSCRIPTION_ID, subscriptionId), new ParameterDto(
							PACKAGE_ID, packageId));
				}
				subscriptionHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_SUBSCRIPTION, subscriptionDetailToChangeStatus.getStatusId(), nextStatus, em);
				subscriptionHelper.changeSubscriptionDetailStatus(subscriptionDetailToChangeStatus, nextStatus, updatedBy, em);
			}
		}
		return sdpSubscription;

	}

	/**
	 * <p>
	 * This method allows the research on SDP_Subscription Table for information relating to the subscription that fits in the input field.
	 * </p>
	 * 
	 * @param subscriptionId
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscription(Long subscriptionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpSubscription subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
			if (subscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
			}
			List<SdpSubscription> subscriptions = new ArrayList<SdpSubscription>();
			subscriptions.add(subscription);
			List<SdpSubscriptionResponseDto> subscriptionDtoList = BeanConverter.convertListOfSdpSubscriptionResponseDto(subscriptions);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(subscriptionDtoList);
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
	 * This method allows the research SDP_Subscription_Detail for information relating to the subscribed offers *
	 * </p>
	 * 
	 * @param subscriptionId
	 * @param packageId
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscriptionDetail and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscriptionDetail(Long subscriptionId, Long packageId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(PACKAGE_ID, packageId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			subscriptionHelper.validateSubscriptionDetail(subscriptionId, packageId);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpSubscriptionDetail subscriptionDetail = subscriptionHelper.searchSubscriptionDetailById(subscriptionId, packageId, em);
			if (subscriptionDetail == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SUBSCRIPTION_ID, subscriptionId), new ParameterDto(PACKAGE_ID,
						packageId));
			}

			List<SdpSubscriptionDetail> subscriptionDetailList = new ArrayList<SdpSubscriptionDetail>();
			subscriptionDetailList.add(subscriptionDetail);
			List<SdpSubscriptionDetailResponseDto> subscriptionDetailDtoList = BeanConverter
					.convertListOfSdpSubscriptionDetailResponseDto(subscriptionDetailList);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(subscriptionDetailDtoList);
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
	 * This method allows the research for information relating to the subscription that fits in the input field.
	 * </p>
	 * 
	 * @param subscriptionId
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscriptionAndDetails(Long subscriptionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		LogMap logMap = new LogMap();
		logMap.put(SUBSCRIPTION_ID, subscriptionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
			// Search subscription
			SdpSubscription subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
			if (subscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SUBSCRIPTION_ID, subscriptionId);
			}
			SdpSubscriptionResponseDto subscriptionDto = BeanConverter.convertSdpSubscriptionResponseDto(subscription);

			List<SdpSubscriptionDetail> details = subscriptionHelper.searchSubscriptionDetailsBySubscriptionId(subscriptionId, em);

			subscriptionDto.setOffers(BeanConverter.convertListOfSdpSubscriptionDetailResponseWithOfferDto(details));

			List<SdpSubscriptionResponseDto> subscriptions = new ArrayList<SdpSubscriptionResponseDto>();
			subscriptions.add(subscriptionDto);
			response = buildSearchResponse(Constants.CODE_OK);
			response.setSearchResult(subscriptions);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;

	}

	/**
	 * <p>
	 * This method allows the research for information relating to all the subscriptions linked to the specific partyId passed in input.
	 * </p>
	 * 
	 * @param partyId
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscriptionsByParty(Long partyId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
			partyHelper.validateSearchPartyById(partyId);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			// Search Party
			SdpParty party = partyHelper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByParty(party, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARTY_ID, partyId);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByPartyPaginated(party, startPosition, maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			response.setSearchResult(getListOfSdpSubscriptionResponseWithOffersDto(subscriptions, subscriptionHelper, em));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);

		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	public SearchServiceResponse searchSubscriptionsByPartyLight(Long partyId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
			partyHelper.validateSearchPartyById(partyId);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			// Search Party
			SdpParty party = partyHelper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByParty(party, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARTY_ID, partyId);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByPartyPaginated(party, startPosition, maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			// cambia il metodo chiamato per la conversione
			response.setSearchResult(BeanConverter.convertListOfSdpSubscriptionResponseDto(subscriptions));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	/**
	 * <p>
	 * This method allows the research for information relating to all the child subscriptions linked to an organization and so to the specific
	 * subscriptionParentId passed in input.
	 * </p>
	 * 
	 * @param parentSubscriptionId
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscriptionsByParentSubscription(Long parentSubscriptionId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();

			subscriptionHelper.validateParentSubscriptionById(parentSubscriptionId);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			// Search parentSubscription
			SdpSubscription parentSubscription = subscriptionHelper.searchSubscriptionById(parentSubscriptionId, em);
			if (parentSubscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByParentSubscription(parentSubscription, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByParentSubscriptionPaginated(parentSubscription, startPosition,
					maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			response.setSearchResult(getListOfSdpSubscriptionResponseWithOffersDto(subscriptions, subscriptionHelper, em));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	public SearchServiceResponse searchSubscriptionsByParentSubscriptionLight(Long parentSubscriptionId, Long startPosition, Long maxRecordsNumber,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();

			subscriptionHelper.validateParentSubscriptionById(parentSubscriptionId);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			// Search parentSubscription
			SdpSubscription parentSubscription = subscriptionHelper.searchSubscriptionById(parentSubscriptionId, em);
			if (parentSubscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByParentSubscription(parentSubscription, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByParentSubscriptionPaginated(parentSubscription, startPosition,
					maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			// cambia il metodo chiamato per la conversione
			response.setSearchResult(BeanConverter.convertListOfSdpSubscriptionResponseDto(subscriptions));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	/**
	 * <p>
	 * This method allows the research for information relating to all the subscriptions linked to the Credential passed in input.
	 * </p>
	 * 
	 * @param userName
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSubscriptionsByCredential(String userName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
			credentialHelper.validateSearchCredentialByUsername(userName);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);
			// Search parentSubscription
			SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
			if (credential == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByCredential(credential, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, USERNAME, userName);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByCredentialPaginated(credential, startPosition, maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			response.setSearchResult(getListOfSdpSubscriptionResponseWithOffersDto(subscriptions, subscriptionHelper, em));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	public SearchServiceResponse searchSubscriptionsByCredentialLight(String userName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
			credentialHelper.validateSearchCredentialByUsername(userName);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);
			// Search parentSubscription
			SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
			if (credential == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByCredential(credential, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, USERNAME, userName);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByCredentialPaginated(credential, startPosition, maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			// cambia il metodo chiamato per la conversione
			response.setSearchResult(BeanConverter.convertListOfSdpSubscriptionResponseDto(subscriptions));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	/**
	 * <p>
	 * This method allows the research for information relating to all the subscriptions linked to the Account passed in input. As input parameter can be used
	 * the ownerAccountName, the payeeAccountName or both. At least one of the two input fields must be enhanced
	 * </p>
	 * 
	 * @param ownerAccountName
	 * @param payeeAccountName
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscriptionsByAccount(String ownerAccountName, String payeeAccountName, Long startPosition, Long maxRecordsNumber,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(OWNER_ACCOUNT_NAME, ownerAccountName);
		logMap.put(PAYEE_ACCOUNT_NAME, payeeAccountName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();

			subscriptionHelper.validateSearchSubscriptionsByAccount(ownerAccountName, payeeAccountName);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			// Search parentSubscription
			SdpAccount accountOwner = null;
			SdpAccount accountPayee = null;
			List<SdpSubscription> subscriptions = null;

			// Retrieve accountOwner
			if (!Utilities.isNull(ownerAccountName)) {
				accountOwner = accountHelper.searchAccountByName(ownerAccountName, em);
				if (accountOwner == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OWNER_ACCOUNT_NAME, ownerAccountName);
				}
			}

			// Retrieve payeeAccount
			if (!Utilities.isNull(payeeAccountName)) {
				accountPayee = accountHelper.searchAccountByName(payeeAccountName, em);
				if (accountPayee == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PAYEE_ACCOUNT_NAME, payeeAccountName);
				}
			}

			Long totalRes = null;
			// Search subscriptions
			if (accountOwner != null && accountPayee != null) {
				totalRes = subscriptionHelper.countSubscriptionsByAccountOwnerAndAccountPayee(accountOwner, accountPayee, em);
				if (totalRes == null || totalRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, new ParameterDto(OWNER_ACCOUNT_NAME, ownerAccountName), new ParameterDto(
							PAYEE_ACCOUNT_NAME, payeeAccountName));
				}

				subscriptions = subscriptionHelper.searchSubscriptionsByAccountOwnerAndAccountPayeePaginated(accountOwner, accountPayee, startPosition,
						maxRecordsNumber, em);
			} else if (accountOwner != null) {
				totalRes = subscriptionHelper.countSubscriptionsByAccountOwner(accountOwner, em);
				if (totalRes == null || totalRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, OWNER_ACCOUNT_NAME, ownerAccountName);
				}

				subscriptions = subscriptionHelper.searchSubscriptionsByAccountOwnerPaginated(accountOwner, startPosition, maxRecordsNumber, em);
			} else if (accountPayee != null) {
				totalRes = subscriptionHelper.countSubscriptionsByAccountPayee(accountPayee, em);
				if (totalRes == null || totalRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PAYEE_ACCOUNT_NAME, payeeAccountName);
				}

				subscriptions = subscriptionHelper.searchSubscriptionsByAccountPayeePaginated(accountPayee, startPosition, maxRecordsNumber, em);
			}

			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			response.setSearchResult(getListOfSdpSubscriptionResponseWithOffersDto(subscriptions, subscriptionHelper, em));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	public SearchServiceResponse searchSubscriptionsByAccountLight(String ownerAccountName, String payeeAccountName, Long startPosition, Long maxRecordsNumber,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(OWNER_ACCOUNT_NAME, ownerAccountName);
		logMap.put(PAYEE_ACCOUNT_NAME, payeeAccountName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();

			subscriptionHelper.validateSearchSubscriptionsByAccount(ownerAccountName, payeeAccountName);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			// Search parentSubscription
			SdpAccount accountOwner = null;
			SdpAccount accountPayee = null;
			List<SdpSubscription> subscriptions = null;

			// Retrieve accountOwner
			if (!Utilities.isNull(ownerAccountName)) {
				accountOwner = accountHelper.searchAccountByName(ownerAccountName, em);
				if (accountOwner == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OWNER_ACCOUNT_NAME, ownerAccountName);
				}
			}

			// Retrieve payeeAccount
			if (!Utilities.isNull(payeeAccountName)) {
				accountPayee = accountHelper.searchAccountByName(payeeAccountName, em);
				if (accountPayee == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PAYEE_ACCOUNT_NAME, payeeAccountName);
				}
			}

			Long totalRes = null;
			// Search subscriptions
			if (accountOwner != null && accountPayee != null) {
				totalRes = subscriptionHelper.countSubscriptionsByAccountOwnerAndAccountPayee(accountOwner, accountPayee, em);
				if (totalRes == null || totalRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, new ParameterDto(OWNER_ACCOUNT_NAME, ownerAccountName), new ParameterDto(
							PAYEE_ACCOUNT_NAME, payeeAccountName));
				}

				subscriptions = subscriptionHelper.searchSubscriptionsByAccountOwnerAndAccountPayeePaginated(accountOwner, accountPayee, startPosition,
						maxRecordsNumber, em);
			} else if (accountOwner != null) {
				totalRes = subscriptionHelper.countSubscriptionsByAccountOwner(accountOwner, em);
				if (totalRes == null || totalRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, OWNER_ACCOUNT_NAME, ownerAccountName);
				}

				subscriptions = subscriptionHelper.searchSubscriptionsByAccountOwnerPaginated(accountOwner, startPosition, maxRecordsNumber, em);
			} else if (accountPayee != null) {
				totalRes = subscriptionHelper.countSubscriptionsByAccountPayee(accountPayee, em);
				if (totalRes == null || totalRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PAYEE_ACCOUNT_NAME, payeeAccountName);
				}

				subscriptions = subscriptionHelper.searchSubscriptionsByAccountPayeePaginated(accountPayee, startPosition, maxRecordsNumber, em);
			}

			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}

			response.setTotalResult(totalRes);
			// cambia il metodo chiamato per la conversione
			response.setSearchResult(BeanConverter.convertListOfSdpSubscriptionResponseDto(subscriptions));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	/**
	 * <p>
	 * This method allows the research for information relating to all the subscriptions linked to the Site passed in input.
	 * </p>
	 * 
	 * @param siteName
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpSubscription and operation result
	 * @exception PropertyNotFoundException
	 */

	public SearchServiceResponse searchSubscriptionsBySite(String siteName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_NAME, siteName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpSiteHelper siteHelper = SdpSiteHelper.getInstance();

			siteHelper.validateSite(siteName);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			// Search Site
			List<SdpSite> sites = siteHelper.searchSitesByName(siteName, em);
			SdpSite site = null;
			if (sites == null || sites.size() == 0) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_NAME, siteName);
			} else {
				site = sites.get(0);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByPartySite(site, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SITE_NAME, siteName);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByPartySite(site, startPosition, maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			response.setSearchResult(getListOfSdpSubscriptionResponseWithOffersDto(subscriptions, subscriptionHelper, em));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	public SearchServiceResponse searchSubscriptionsBySiteLight(String siteName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse response;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_NAME, siteName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
			SdpSiteHelper siteHelper = SdpSiteHelper.getInstance();

			siteHelper.validateSite(siteName);
			subscriptionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			// Search Site
			List<SdpSite> sites = siteHelper.searchSitesByName(siteName, em);
			SdpSite site = null;
			if (sites == null || sites.size() == 0) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_NAME, siteName);
			} else {
				site = sites.get(0);
			}

			Long totalRes = subscriptionHelper.countSubscriptionsByPartySite(site, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SITE_NAME, siteName);
			}

			// Search subscriptions
			List<SdpSubscription> subscriptions = subscriptionHelper.searchSubscriptionsByPartySite(site, startPosition, maxRecordsNumber, em);
			if (subscriptions == null || subscriptions.isEmpty()) {
				response = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				response = buildSearchResponse(Constants.CODE_OK);
			}
			response.setTotalResult(totalRes);
			// cambia il metodo chiamato per la conversione
			response.setSearchResult(BeanConverter.convertListOfSdpSubscriptionResponseDto(subscriptions));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			response = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			response = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), response);
		return response;
	}

	// -----------------------------------------------------------------------------
	// Utility Methods
	// -----------------------------------------------------------------------------

	private SdpSubscription setupSubscription(Long partyId, Long solutionOfferId, Long parentSubscriptionId, String userName, String roleName, Long ownerId,
			Long payeeId, Long siteId, Date startDate, Date endDate, EntityManager em) throws ValidationException, PropertyNotFoundException {

		// Create all necessary helpers
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		SdpRoleHelper roleHelper = SdpRoleHelper.getInstance();
		SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();
		SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();

		SdpSubscription result = new SdpSubscription();

		SdpParty party = null;
		SdpSolutionOffer solutionOffer = null;
		SdpSubscription parentSubscription = null;
		SdpCredential credential = null;
		SdpRole role = null;
		SdpAccount accountOwner = null;
		SdpAccount accountPayee = null;
		SdpSite partySite = null;

		// Retrieve SdpParty
		log.logDebug("Searching for SdpParty with id: %s ", partyId);
		party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			log.logDebug("SdpParty with id: %s not found", partyId);
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			log.logDebug("SdpSolutionOffer with id: %s not found", solutionOfferId);
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}

		// verifica solutionOffer=>solution e party abbiano partyGroup in comune
		boolean isPartyGroupValid = false;
		List<SdpPartyGroup> soPartyGroups = null;
		// se solutionOffer è scontata, si deve leggere dalla padre
		if (solutionOfferHelper.isDiscounted(solutionOffer)) {
			soPartyGroups = solutionOffer.getParentSolutionOffer().getSdpSolution().getSdpPartyGroups();
		} else {
			soPartyGroups = solutionOffer.getSdpSolution().getSdpPartyGroups();
		}
		if (soPartyGroups != null && party.getPartyGroups() != null) {
			for (SdpPartyGroup pg1 : soPartyGroups) {
				for (SdpPartyGroup pg2 : party.getPartyGroups()) {
					if (pg1.getPartyGroupId().equals(pg2.getPartyGroupId())) {
						isPartyGroupValid = true;
						break;
					}
				}
				if (isPartyGroupValid) {
					break;
				}
			}
		}
		if (!isPartyGroupValid) {
			log.logDebug("Party groups not valid", solutionOfferId);
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_ID, solutionOfferId);
		}

		// Retrieve parentSubscription
		if (parentSubscriptionId != null) {
			if (partyHelper.isAParent(party)) {
				log.logDebug("parentSubscriptionId unallowed for an organization");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}
			log.logDebug("Searching for parentSubscription with id: %s ", parentSubscriptionId);
			parentSubscription = subscriptionHelper.searchSubscriptionById(parentSubscriptionId, em);
			if (parentSubscription == null) {
				log.logDebug("parentSubscription not found");
				throw new ValidationException(Constants.CODE_PARENT_NOT_FOUND, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}
			if (!partyHelper.isAParent(parentSubscription.getSdpParty())) {
				log.logDebug("parentSubscriptionId unallowed because owned by a child");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}
			if (!parentSubscription.getSdpParty().equals(party.getParentParty())) {
				log.logDebug("parentSubscriptionId unallowed because owned by another organization");
				throw new ValidationException(Constants.CODE_PARENT_NOT_FOUND, PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
			}
			if (!parentSubscription.getSdpSolutionOffer().equals(solutionOffer)) {
				log.logDebug("parentSubscription has another solutionOffer");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_ID, solutionOfferId);
			}
		}
		// SDP_02_12
		else {
			if (!partyHelper.isAParent(party)) {
				log.logDebug("parentSubscriptionId mandatory for a child party");
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, PARENT_SUBSCRIPTION_ID, null);
			}
		}

		// Retrieve credential
		SdpRole credentialRole = null;
		if (!Utilities.isNull(userName)) {
			credential = credentialHelper.searchCredentialByUsername(userName, em);
			if (credential == null) {
				log.logDebug("userName not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
			}
			if (!credential.getSdpParty().equals(party)) {
				log.logDebug("userName of another party");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, USERNAME, userName);
			}
			credentialRole = roleHelper.searchRoleByName(credential.getSdpRole(), em);
			if (Utilities.isNull(roleName)) {
				log.logDebug("role missing but userName entered");
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, ROLE_NAME, roleName);
			}
		}

		// Retrieve role
		if (!Utilities.isNull(roleName)) {
			role = roleHelper.searchRoleByName(roleName, em);
			if (role == null) {
				log.logDebug("role not found");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, ROLE_NAME, roleName);
			}
		}

		// Check if credentialRole = "Admin" then roleName must be "Admin"
		if (credential != null && roleHelper.isAdmin(credentialRole) && !roleHelper.isAdmin(role)) {
			log.logDebug("credential is Admin but role is not Admin");
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, ROLE_NAME, roleName);
		}

		// Retrieve ownerAccount
		if (ownerId != null) {
			accountOwner = accountHelper.searchAccountById(ownerId, em);
			if (accountOwner == null) {
				log.logDebug("owner not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, OWNER_ACCOUNT_ID, String.valueOf(ownerId));
			}
			if (!isEqualToPartyOrParent(accountOwner.getSdpParty(), party, partyHelper)) {
				log.logDebug("owner is not the party or its organization");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, OWNER_ACCOUNT_ID, String.valueOf(ownerId));
			}
		}

		// Retrieve payeeAccount
		if (payeeId != null) {
			accountPayee = accountHelper.searchAccountById(payeeId, em);
			if (accountPayee == null) {
				log.logDebug("payee not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PAYEE_ACCOUNT_ID, String.valueOf(payeeId));
			}
			if (!isEqualToPartyOrParent(accountPayee.getSdpParty(), party, partyHelper)) {
				log.logDebug("payee is not the party or its organization");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PAYEE_ACCOUNT_ID, String.valueOf(payeeId));
			}
		}

		// Retrieve site
		if (siteId != null) {
			partySite = partySiteHelper.searchSiteById(siteId, em);
			if (partySite == null) {
				log.logDebug("site not found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
			}
			if (!isEqualToPartyOrParent(partySite.getSdpParty(), party, partyHelper)) {
				log.logDebug("site is not owned by the party or its organization");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SITE_ID, siteId);
			}
		}

		subscriptionHelper.validateDates(startDate, endDate);

		result.setSdpParty(party);
		result.setParentSubscription(parentSubscription);
		result.setSdpSolutionOffer(solutionOffer);
		result.setSdpCredential(credential);
		if (role != null) {
			result.setSdpRole(role.getRoleName());
		}
		result.setSdpAccountOwner(accountOwner);
		result.setSdpAccountPayee(accountPayee);
		result.setSdpPartySite(partySite);

		return result;

	}

	private boolean isEqualToPartyOrParent(SdpParty owner, SdpParty party, SdpPartyHelper helper) throws PropertyNotFoundException {
		return (owner.equals(party) || (!helper.isAParent(party)) && owner.equals(party.getParentParty()));
	}

	private List<SdpSubscriptionResponseDto> getListOfSdpSubscriptionResponseWithOffersDto(List<SdpSubscription> subscriptions, SdpSubscriptionHelper helper,
			EntityManager em) throws ValidationException, PropertyNotFoundException {
		List<SdpSubscriptionResponseDto> subscriptionDtos = new ArrayList<SdpSubscriptionResponseDto>();
		if (subscriptions != null) {
			for (SdpSubscription subscription : subscriptions) {
				SdpSubscriptionResponseDto subscriptionDto = BeanConverter.convertSdpSubscriptionResponseDto(subscription);

				List<SdpSubscriptionDetail> details = helper.searchSubscriptionDetailsBySubscriptionId(subscription.getSubscriptionId(), em);

				subscriptionDto.setOffers(BeanConverter.convertListOfSdpSubscriptionDetailResponseWithOfferDto(details));

				subscriptionDtos.add(subscriptionDto);
			}
		}
		return subscriptionDtos;
	}

}
