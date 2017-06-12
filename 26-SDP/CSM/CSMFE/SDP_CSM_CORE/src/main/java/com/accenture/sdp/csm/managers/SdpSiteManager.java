package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpAccountHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpSiteHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpSiteManager extends SdpBaseManager {

	private SdpSiteManager() {
		super();
	}

	private static SdpSiteManager instance;

	public static SdpSiteManager getInstance() {
		if (instance == null) {
			instance = new SdpSiteManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new site and to relate it to the party. The new site is created by default with ACTIVE Status
	 * </p>
	 * 
	 * @param siteName
	 *            Name of the new Site to create
	 * @param siteDescription
	 *            Descripion of the new Site to create
	 * @param parentPartyId
	 *            Id of organization party to associate to new site
	 * @param externalId
	 *            External id of new Site to create
	 * @param address
	 *            Street address of the new Site to create
	 * @param city
	 *            City of the new Site to create
	 * @param zipCode
	 *            Zip Code of the new Site to create
	 * @param province
	 *            Province of the new Site to create
	 * @param country
	 *            Country of the new Site to create
	 * @param siteProfile
	 *            Profile of the new Site to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createSite(String siteName, String siteDescription, Long parentPartyId, String externalId, String address, String city,
			String zipCode, String province, String country, String siteProfile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_NAME, siteName);
		logMap.put("siteDescription", siteDescription);
		logMap.put(PARENT_PARTY_ID, parentPartyId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("address", address);
		logMap.put("city", city);
		logMap.put("zipCode", zipCode);
		logMap.put("province", province);
		logMap.put("country", country);
		logMap.put("siteProfile", siteProfile);
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
			// creazione party
			SdpSite newSite = createSite(siteName, siteDescription, parentPartyId, externalId, address, city, zipCode, province, country, siteProfile,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(newSite.getSiteId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
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

	SdpSite createSite(String siteName, String siteDescription, Long parentPartyId, String externalId, String address, String city, String zipCode,
			String province, String country, String siteProfile, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpSiteHelper handler = SdpSiteHelper.getInstance();
		SdpPartyHelper partyHelp = SdpPartyHelper.getInstance();
		// Validazione Formale
		handler.validateSite(siteName);
		partyHelp.validateSearchParentPartyById(parentPartyId);
		SdpParty organization = partyHelp.searchPartyById(parentPartyId, em);
		if (organization == null || !partyHelp.isAParent(organization)) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARENT_PARTY_ID, String.valueOf(parentPartyId));
		}
		// Check if site is already present and associated to parentPartyId
		List<SdpSite> partySites = organization.getSdpPartySites();
		for (SdpSite tempSite : partySites) {
			if (tempSite.getSiteName() == siteName) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SITE_NAME, siteName);
			}
		}

		// verifica duplicati
		List<SdpSite> tempList = handler.searchSitesByPartyId(parentPartyId, em);
		if (tempList != null && !tempList.isEmpty()) {
			for (SdpSite tempSite : tempList) {
				if (tempSite.getSiteName().equals(siteName)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SITE_NAME, siteName);
				}
			}
		}
		if (handler.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}
		// creazione party
		return handler.createSite(siteName, siteDescription, organization, externalId, address, city, zipCode, province, country, siteProfile, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a site (but not the Status) already present into CSM database with the following constraints:
	 * 
	 * <li>The site status is ACTIVE</li>
	 * </p>
	 * 
	 * @param siteId
	 *            Id of the site to modify
	 * @param siteName
	 *            Name of the site to modify
	 * @param siteDescription
	 *            Description of the site to modify
	 * @param address
	 *            Street address of the site to modify
	 * @param city
	 *            City of the site to modify
	 * @param zipCode
	 *            Zip Code of the site to modify
	 * @param province
	 *            Province of the site to modify
	 * @param country
	 *            Country of the site to modify
	 * @param externalId
	 *            External id of the site to modify
	 * @param siteProfile
	 *            Profile of the site to modify
	 * @return Returns a DataServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifySite(Long siteId, String siteName, String siteDescription, String address, String city, String zipCode, String province,
			String country, String externalId, String siteProfile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_ID, siteId);
		logMap.put(SITE_NAME, siteName);
		logMap.put("siteDescription", siteDescription);
		logMap.put("address", address);
		logMap.put("city", city);
		logMap.put("zipCode", zipCode);
		logMap.put("province", province);
		logMap.put("zipCode", zipCode);
		logMap.put("country", country);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("siteProfile", siteProfile);
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

			modifySite(siteId, siteName, siteDescription, address, city, zipCode, province, country, externalId, siteProfile,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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

	SdpSite modifySite(Long siteId, String siteName, String siteDescription, String address, String city, String zipCode, String province, String country,
			String externalId, String siteProfile, String modifiedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpSiteHelper handler = SdpSiteHelper.getInstance();
		handler.validateSearchSiteById(siteId);
		handler.validateSite(siteName);
		SdpSite site = handler.searchSiteById(siteId, em);
		if (site == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
		}
		if (!isStatusActive(site.getStatusId())) {
			log.logDebug("Site not active");
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SITE_ID, siteId);
		}

		// verifica duplicati
		List<SdpSite> tempList = handler.searchSitesByPartyId(site.getSdpParty().getPartyId(), em);
		if (tempList != null && !tempList.isEmpty()) {
			for (SdpSite tempSite : tempList) {
				if (tempSite.getSiteName().equals(siteName) && !tempSite.getSiteId().equals(siteId)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SITE_NAME, siteName);
				}
			}
		}
		if (!Utilities.isNull(externalId)) {
			SdpSite temp = handler.searchByExternalId(externalId, em);
			if (temp != null && !temp.getSiteId().equals(siteId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		handler.modifySite(site, siteName, siteDescription, address, city, zipCode, province, country, externalId, siteProfile, modifiedBy);
		return site;
	}

	/**
	 * <p>
	 * This method allows to delete a site, only in logically mode, setting the status of the site to DELETED.
	 * 
	 * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * <li>The site to be deleted is in status Inactive</li>
	 * <li>There aren't child party associated to the site with status different from Deleted</li>
	 * <li>There aren't account associated to the site with status different from Deleted</li>
	 * </p>
	 * 
	 * @param siteId
	 *            Id of the Site to delete
	 * @return Returns a DataServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteSite(Long siteId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_ID, siteId);
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

			deleteSite(siteId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
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

	void deleteSite(Long siteId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpSiteHelper handler = SdpSiteHelper.getInstance();

		handler.validateSearchSiteById(siteId);
		// verifica site sia cancellabile
		SdpSite site = handler.searchSiteById(siteId, em);
		if (site == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
		}

		handler.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, site.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		Long nParties = SdpPartyHelper.getInstance().countChildPartiesNotDeletedBySiteId(siteId, em);
		if (nParties != null && nParties > 0L) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, SITE_ID, siteId);
		}
		Long nAccounts = SdpAccountHelper.getInstance().countAccountsNotDeletedBySiteId(siteId, em);
		if (nAccounts != null && nAccounts > 0L) {
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, SITE_ID, siteId);
		}

		handler.deleteSite(site, deletedBy);
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a site. NOTE: The input parameter are not mandatory but at least a parameter must be valorized
	 * 
	 * @param siteId
	 *            Id of the Site to search
	 * @param siteName
	 *            Name of the Site to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSites(Long siteId, String siteName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_ID, siteId);
		logMap.put(SITE_NAME, siteName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpSiteHelper handler = SdpSiteHelper.getInstance();
			handler.validateSearchSite(siteId, siteName);
			if (siteId == null) {
				handler.validateSearchAll(startPosition, maxRecordsNumber);
			}

			em = PersistenceManager.getEntityManager(tenantName);

			List<SdpSite> sites = new ArrayList<SdpSite>();
			Long nRes;
			if (siteId != null) {
				log.logDebug("Searching SdpPartySite with id: %s", siteId);
				SdpSite site = handler.searchSiteById(siteId, em);
				if (site == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
				}
				sites.add(site);
				resp = buildSearchResponse(Constants.CODE_OK);
				nRes = Long.valueOf(1L);
			} else {
				log.logDebug("Searching SdpPartySite with name: %s", siteName);
				nRes = handler.countSitesByNameLike(siteName, em);
				if (nRes == null || nRes == 0L) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SITE_NAME, siteName);
				}

				sites = handler.searchSitesByNameLikePaginated(siteName, startPosition, maxRecordsNumber, em);
				if (sites == null || sites.isEmpty()) {
					resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
				} else {
					resp = buildSearchResponse(Constants.CODE_OK);
				}
			}
			resp.setSearchResult(BeanConverter.convertSpdSites(sites));
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
	 * This method allows to retrieve the information related to a site given an Organization Id
	 * 
	 * @param parentPartyId
	 *            parentPartyId of the Site to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSitesByParty(Long parentPartyId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARENT_PARTY_ID, parentPartyId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpPartyHelper partyHelp = SdpPartyHelper.getInstance();
			SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
			partyHelp.validateSearchParentPartyById(parentPartyId);
			partySiteHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			SdpParty organization = partyHelp.searchPartyById(parentPartyId, em);
			if (organization == null || !partyHelp.isAParent(organization)) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARENT_PARTY_ID, String.valueOf(parentPartyId));
			}
			Long nRes = partySiteHelper.countSitesByPartyId(parentPartyId, em);
			if (nRes == null || nRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARENT_PARTY_ID, String.valueOf(parentPartyId));
			}

			List<SdpSite> sites = partySiteHelper.searchSitesByPartyIdPaginated(parentPartyId, startPosition, maxRecordsNumber, em);
			if (sites == null || sites.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSpdSites(sites));
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
	 * This method allows to retrieve the information related to a site given an account name
	 * 
	 * @param accountName
	 *            Account name related to the Site to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSiteByAccount(String accountName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("accountName", accountName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpAccountHelper accHelp = SdpAccountHelper.getInstance();
			accHelp.validateSearchAccountByName(accountName);
			em = PersistenceManager.getEntityManager(tenantName);

			SdpAccount account = accHelp.searchAccountByName(accountName, em);
			if (account == null) {
				log.logDebug("Account not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "accountName", accountName);
			}

			List<SdpSite> sites = new ArrayList<SdpSite>();
			if (account.getSdpPartySite() == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "accountName", accountName);
			}
			sites.add(account.getSdpPartySite());
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertSpdSites(sites));
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
	 * This method allows to retrieve the information related to a site given a subscriptionId
	 * 
	 * @param subscriptionId
	 *            Subscription Id related to the Site to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchSiteBySubscription(Long subscriptionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("subscriptionId", subscriptionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpSubscriptionHelper subHelp = SdpSubscriptionHelper.getInstance();
			subHelp.validateSearchSubscriptionById(subscriptionId);
			em = PersistenceManager.getEntityManager(tenantName);

			SdpSubscription subscription = subHelp.searchSubscriptionById(subscriptionId, em);
			if (subscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "subscriptionId", subscriptionId);
			}
			List<SdpSite> sites = new ArrayList<SdpSite>();
			if (subscription.getSdpPartySite() == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "subscriptionId", subscriptionId);
			}
			sites.add(subscription.getSdpPartySite());

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertSpdSites(sites));
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
	 * This method allows to change the status of a site.
	 * 
	 * @param siteId
	 *            Id of the the Site to update
	 * @param status
	 *            value of the status to set on the site
	 * @return Returns a DataServiceResponse containing all the informations related to SdpPartySite and operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse siteChangeStatus(Long siteId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SITE_ID, siteId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		DataServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			siteChangeStatus(siteId, status, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
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

	SdpSite siteChangeStatus(Long siteId, String status, String changedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpSiteHelper handler = SdpSiteHelper.getInstance();
		handler.validateSearchSiteById(siteId);
		// Validazione Formale
		handler.validateChangeStatus(status);
		SdpSite site = handler.searchSiteById(siteId, em);
		if (site == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
		}
		handler.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, site.getStatusId(), StatusIdConverter.getStatusValue(status), em);

		handler.changeStatus(site, StatusIdConverter.getStatusValue(status), changedBy);
		return site;
	}

}
