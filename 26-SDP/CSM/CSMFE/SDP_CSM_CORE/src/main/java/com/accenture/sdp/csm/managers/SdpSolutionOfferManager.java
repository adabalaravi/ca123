package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.LinkUpdateOperation;
import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDiscountModifyRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpDiscountRequestDto;
import com.accenture.sdp.csm.dto.requests.ExternalIdDto;
import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSolutionOfferDetailRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferAndDiscountDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferPackageDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionSolutionOfferDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpOfferHelper;
import com.accenture.sdp.csm.helpers.SdpPackageHelper;
import com.accenture.sdp.csm.helpers.SdpPartyGroupHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.RefSolutionOfferType;
import com.accenture.sdp.csm.model.jpa.SdpDiscount;
import com.accenture.sdp.csm.model.jpa.RefExternalPlatform;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpOfferGroup;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpPackagePrice;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSolution;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOfferExternalId;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpSolutionOfferManager extends SdpBaseManager {

	private static final String PARTY_GROUP_LIST = "partyGroup";
	private static final String SOLUTION_OFFER_DESCRIPTION = "solutionOfferDescription";
	private static final String EXTERNAL_PLATFORM_NAME = "externalPlatformName";

	private SdpSolutionOfferManager() {
		super();
	}

	private static SdpSolutionOfferManager instance;

	public static SdpSolutionOfferManager getInstance() {
		if (instance == null) {
			instance = new SdpSolutionOfferManager();
		}
		return instance;
	}

	public CreateServiceResponse createSolutionOffer(Long solutionId, String solutionOfferName, String solutionOfferDescription, Date startDate, Date endDate,
			List<ExternalIdDto> externalId, String profile, List<String> partyGroups, String solutionOfferType, Long duration, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_ID, solutionId);
		logMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		logMap.put(SOLUTION_OFFER_DESCRIPTION, solutionOfferDescription);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("profile", profile);
		logMap.put(PARTY_GROUP_LIST, partyGroups);
		logMap.put(SOLUTION_OFFER_TYPE, solutionOfferType);
		logMap.put(DURATION, duration);
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

			SdpSolutionOffer solutionOffer = createSolutionOffer(solutionId, solutionOfferName, solutionOfferDescription, startDate, endDate, externalId,
					profile, partyGroups, Utilities.getCurrentClassAndMethod(), solutionOfferType, duration, em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(solutionOffer.getSolutionOfferId());
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

	SdpSolutionOffer createSolutionOffer(Long solutionId, String solutionOfferName, String solutionOfferDescription, Date startDate, Date endDate,
			List<ExternalIdDto> externalIdList, String profile, List<String> partyGroups, String createdBy, String solutionOfferType, Long duration, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpSolutionHelper solutionHelper = SdpSolutionHelper.getInstance();
		solutionHelper.validateSearchSolutionById(solutionId);
		solutionOfferHelper.validateSolutionOffer(solutionOfferName, startDate, endDate, solutionOfferType, duration);

		SdpSolution solution = solutionHelper.searchSolutionById(solutionId, em);
		if (solution == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
		}
		RefSolutionOfferType sot = solutionOfferHelper.searchSolutionOfferTypeByType(solutionOfferType, em);
		if (sot == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_TYPE, solutionOfferType);
		}
		// AGGIUNTO X AVS
		SdpPartyGroupHelper groupHelper = SdpPartyGroupHelper.getInstance();
		List<SdpPartyGroup> groups = new ArrayList<SdpPartyGroup>();
		if (partyGroups != null) {
			for (String groupName : partyGroups) {
				groupHelper.validatePartyGroup(groupName);
				SdpPartyGroup group = groupHelper.searchPartyGroupByName(groupName, em);
				if (group == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_NAME, groupName);
				}
				// verifica duplicati -> gestisco direttamente sulla lista
				if (groups.contains(group)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_NAME, solutionOfferName), new ParameterDto(
							PARTY_GROUP_NAME, groupName));
				}
				groups.add(group);
			}
		}
		// FINE AGGIUNTO X AVS

		// duplicates
		SdpSolutionOffer solOffer = solutionOfferHelper.searchSolutionOfferByName(solutionOfferName, em);
		if (solOffer != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SOLUTION_OFFER_NAME, solutionOfferName);
		}

		SdpSolutionOffer solutionOffer = solutionOfferHelper.createSolutionOffer(solution, null, solutionOfferName, solutionOfferDescription, startDate,
				endDate, profile, groups, createdBy, sot, duration, em);
		// NUOVA GESTIONE EXTERNAL ID PER STAR
		createSolutionOfferExternalIds(solutionOffer, externalIdList, em);

		return solutionOffer;
	}

	private List<SdpSolutionOfferExternalId> createSolutionOfferExternalIds(SdpSolutionOffer solutionOffer, List<ExternalIdDto> externalIdList, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		List<SdpSolutionOfferExternalId> result = new ArrayList<SdpSolutionOfferExternalId>();
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		// NUOVA GESTIONE EXTERNAL ID PER STAR
		if (externalIdList != null) {
			for (ExternalIdDto dto : externalIdList) {
				// validation
				solutionOfferHelper.validateSearchByExternalId(dto.getExternalId(), dto.getExternalPlatformName());
				RefExternalPlatform externalPlatform = solutionOfferHelper.searchExternalPlatformByName(dto.getExternalPlatformName(), em);
				if (externalPlatform == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, EXTERNAL_PLATFORM_NAME, dto.getExternalPlatformName());
				}
				// duplicates
//				SdpSolutionOffer checkExternalId = solutionOfferHelper.searchByExternalId(dto.getExternalId(), dto.getExternalPlatformName(), em);
//				if (checkExternalId != null) {
//					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(EXTERNAL_ID, dto.getExternalId()), new ParameterDto(
//							EXTERNAL_PLATFORM_NAME, dto.getExternalPlatformName()));
//				}
				if (solutionOfferHelper.searchExternalId(solutionOffer, externalPlatform) != null) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_PLATFORM_NAME, dto.getExternalPlatformName());
				}
				// creation
				result.add(solutionOfferHelper.createSolutionOfferExternalId(solutionOffer, dto.getExternalId(), externalPlatform, em));
			}
		}
		return result;
	}

	public ComplexServiceResponse createSolutionOfferAndPackage(String solutionName, String solutionOfferName, String solutionOfferDescription,
			Date solutionOfferstartDate, Date solutionOfferendDate, List<ExternalIdDto> externalId, String solutionOfferprofile,
			List<SdpSolutionOfferDetailRequestDto> solutionsDetails, List<String> partyGroups, String solutionOfferType, Long duration, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_NAME, solutionName);
		logMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		logMap.put(SOLUTION_OFFER_DESCRIPTION, solutionOfferDescription);
		logMap.put("solutionOfferStartDate", solutionOfferstartDate);
		logMap.put("solutionOfferEndDate", solutionOfferendDate);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("solutionOfferProfile", solutionOfferprofile);
		logMap.put("solutionsDetails", solutionsDetails);
		logMap.put(PARTY_GROUP_LIST, partyGroups);
		logMap.put(SOLUTION_OFFER_TYPE, solutionOfferType);
		logMap.put(DURATION, duration);
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
			SdpSolutionOfferPackageDto result = createSolutionOfferAndPackage(solutionName, solutionOfferName, solutionOfferDescription,
					solutionOfferstartDate, solutionOfferendDate, externalId, solutionOfferprofile, solutionsDetails, partyGroups,
					Utilities.getCurrentClassAndMethod(), solutionOfferType, duration, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(result);
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

	SdpSolutionOfferPackageDto createSolutionOfferAndPackage(String solutionName, String solutionOfferName, String solutionOfferDescription,
			Date solutionOfferstartDate, Date solutionOfferendDate, List<ExternalIdDto> externalId, String solutionOfferprofile,
			List<SdpSolutionOfferDetailRequestDto> solutionsDetails, List<String> partyGroups, String createdBy, String solutionOfferType, Long duration, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionOfferPackageDto response = new SdpSolutionOfferPackageDto();
		SdpSolutionHelper solutionHelper = SdpSolutionHelper.getInstance();
		solutionHelper.validateSearchSolutionByName(solutionName);
		SdpSolution solution = solutionHelper.searchSolutionByName(solutionName, em);
		if (solution == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_NAME, solutionName);
		}
		SdpSolutionOffer solutionOffer = createSolutionOffer(solution.getSolutionId(), solutionOfferName, solutionOfferDescription, solutionOfferstartDate,
				solutionOfferendDate, externalId, solutionOfferprofile, partyGroups, createdBy, solutionOfferType, duration, em);
		SdpOfferHelper offerHelper = SdpOfferHelper.getInstance();
		SdpOfferGroupManager offerGroupManager = SdpOfferGroupManager.getInstance();
		SdpPackageManager packageManager = SdpPackageManager.getInstance();
		SdpPackagePriceManager packagePriceManager = SdpPackagePriceManager.getInstance();
		ArrayList<com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDetailDto> solutionOffersDetails = new ArrayList<com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDetailDto>();
		// maps to avoid to access database
		HashMap<String, SdpOfferGroup> offerGroupMap = new HashMap<String, SdpOfferGroup>();
		HashMap<Long, SdpPackage> packageMap = new HashMap<Long, SdpPackage>();
		for (SdpSolutionOfferDetailRequestDto solutionsDetail : solutionsDetails) {
			Long groupId = null;
			if (solutionsDetail.getGroupName() != null) {
				SdpOfferGroup offerGroup = offerGroupMap.get(solutionsDetail.getGroupName());
				if (offerGroup == null) {
					offerGroup = offerGroupManager.createOfferGroup(solutionsDetail.getGroupName(), solutionOffer.getSolutionOfferId(), createdBy, em);
					offerGroupMap.put(solutionsDetail.getGroupName(), offerGroup);
				}
				groupId = offerGroup.getGroupId();
			}
			SdpPackagePrice packagePrice = packagePriceManager.createPackagePrice(solutionsDetail.getRcPriceCatalogId(),
					solutionsDetail.getNrcPriceCatalogId(), solutionsDetail.getCurrencyTypeID(), solutionsDetail.getRcFrequencyTypeID(),
					solutionsDetail.getRcFlagProrate(), solutionsDetail.getRcInAdvance(), createdBy, em);
			SdpOffer offer = offerHelper.searchOfferById(solutionsDetail.getOfferId(), em);
			if (offer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "offerId", String.valueOf(solutionsDetail.getOfferId()));
			}
			Long basePackageId = null;
			if (solutionsDetail.getParentOfferId() != null) {
				SdpOffer parentOffer = offerHelper.searchOfferById(solutionsDetail.getParentOfferId(), em);
				if (parentOffer == null) {
					throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "parentOfferId", String.valueOf(solutionsDetail.getParentOfferId()));
				}
				SdpPackage parentPackage = packageMap.get(solutionsDetail.getParentOfferId());
				if (parentPackage != null) {
					basePackageId = parentPackage.getPackageId();
				}
			}
			SdpPackage packge = packageManager.createPackage(solutionOffer.getSolutionOfferId(), solutionsDetail.getOfferId(), solutionsDetail.isMandatory(),
					packagePrice.getPackagePriceId(), basePackageId, groupId, solutionsDetail.getPackageExternalId(), solutionsDetail.getPackageProfile(),
					createdBy, em);
			packageMap.put(solutionsDetail.getOfferId(), packge);
			solutionOffersDetails.add(BeanConverter.convertSolutionOfferDetail(packge, packagePrice));
		}
		response.setSolutionOfferDto(BeanConverter.converSdpSolutionOffer(solutionOffer));
		response.setSolutionOfferDetailDto(solutionOffersDetails);
		return response;
	}

	public ComplexServiceResponse createSolutionOfferAndDiscount(Long parentSolutionOfferId, String solutionOfferName, String solutionOfferDescription,
			Date startDate, Date endDate, List<ExternalIdDto> externalId, String profile, List<SdpDiscountRequestDto> discounts, List<String> partyGroups,
			String solutionOfferType, Long duration, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put("parentSolutionOfferId", parentSolutionOfferId);
		logMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		logMap.put(SOLUTION_OFFER_DESCRIPTION, solutionOfferDescription);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("profile", profile);
		logMap.put("discount", discounts);
		logMap.put(PARTY_GROUP_LIST, partyGroups);
		logMap.put(SOLUTION_OFFER_TYPE, solutionOfferType);
		logMap.put(DURATION, duration);
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
			SdpSolutionOffer solOffer = createSolutionOfferAndDiscount(parentSolutionOfferId, solutionOfferName, solutionOfferDescription, startDate, endDate,
					externalId, profile, discounts, partyGroups, Utilities.getCurrentClassAndMethod(), solutionOfferType, duration, em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			// prepare result dto
			SdpSolutionOfferAndDiscountDto result = new SdpSolutionOfferAndDiscountDto();
			result.setSolutionOfferId(solOffer.getSolutionOfferId());
			if (solOffer.getDiscounts() != null) {
				result.setDiscountIdList(new ArrayList<Long>());
				for (SdpDiscount d : solOffer.getDiscounts()) {
					result.getDiscountIdList().add(d.getId());
				}
			}
			// prepare response
			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(result);
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

	SdpSolutionOffer createSolutionOfferAndDiscount(Long parentSolutionOfferId, String solutionOfferName, String solutionOfferDescription, Date startDate,
			Date endDate, List<ExternalIdDto> externalIdList, String profile, List<SdpDiscountRequestDto> discounts, List<String> partyGroups,
			String createdBy, String solutionOfferType, Long duration, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		solutionOfferHelper.validateSearchSolutionOfferByParentSolutionOfferId(parentSolutionOfferId);
		solutionOfferHelper.validateSolutionOffer(solutionOfferName, startDate, endDate, solutionOfferType, duration);

		SdpSolutionOffer parentSolutionOffer = solutionOfferHelper.searchSolutionOfferById(parentSolutionOfferId, em);
		if (parentSolutionOffer == null) {
			throw new ValidationException(Constants.CODE_PARENT_NOT_FOUND, "parentSolutionOfferId", String.valueOf(parentSolutionOfferId));
		}
		if (solutionOfferHelper.isDiscounted(parentSolutionOffer)) {
			throw new ValidationException(Constants.CODE_PARENT_NOT_FOUND, "parentSolutionOfferId", String.valueOf(parentSolutionOfferId));
		}
		RefSolutionOfferType sot = solutionOfferHelper.searchSolutionOfferTypeByType(solutionOfferType, em);
		if (sot == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_TYPE, solutionOfferType);
		}
		// AGGIUNTO X AVS
		SdpPartyGroupHelper groupHelper = SdpPartyGroupHelper.getInstance();
		List<SdpPartyGroup> groups = new ArrayList<SdpPartyGroup>();
		if (partyGroups != null) {
			for (String groupName : partyGroups) {
				groupHelper.validatePartyGroup(groupName);
				SdpPartyGroup group = groupHelper.searchPartyGroupByName(groupName, em);
				if (group == null) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_GROUP_NAME, groupName);
				}
				// verifica duplicati -> gestisco direttamente sulla lista
				if (groups.contains(group)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_NAME, solutionOfferName), new ParameterDto(
							PARTY_GROUP_NAME, groupName));
				}
				groups.add(group);
			}
		}
		// FINE AGGIUNTO X AVS

		// verifica duplicati
		SdpSolutionOffer duplicateSolOffer = solutionOfferHelper.searchSolutionOfferByName(solutionOfferName, em);
		if (duplicateSolOffer != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SOLUTION_OFFER_NAME, solutionOfferName);
		}

		SdpSolutionOffer solutionOffer = solutionOfferHelper.createSolutionOffer(null, parentSolutionOffer, solutionOfferName, solutionOfferDescription,
				startDate, endDate, profile, groups, createdBy, sot, duration, em);
		// NUOVA GESTIONE EXTERNAL ID PER STAR
		createSolutionOfferExternalIds(solutionOffer, externalIdList, em);

		// creazione sconti sui package
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		// every package should have its own discount :
		// null/null/null/null for missing ones
		// create a list to track missing ones, also useful for duplicates
		List<SdpPackage> packages = new ArrayList<SdpPackage>();
		if (parentSolutionOffer.getSdpPackages() != null) {
			packages.addAll(parentSolutionOffer.getSdpPackages());
		}
		for (SdpDiscountRequestDto discount : discounts) {
			packageHelper.validateSearchPackageById(discount.getPackageId());
			solutionOfferHelper.validateDiscount(discount.getDiscountAbsRc(), discount.getDiscountAbsNrc(), discount.getDiscountPercRc(),
					discount.getDiscountPercNrc());

			SdpPackage pack = packageHelper.searchPackageById(discount.getPackageId(), em);
			if (pack == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PACKAGE_ID, discount.getPackageId());
			}
			if (!pack.getSdpSolutionOffer().getSolutionOfferId().equals(parentSolutionOfferId)) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PACKAGE_ID, discount.getPackageId());
			}
			if (!packages.remove(pack)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, PACKAGE_ID, discount.getPackageId());
			}
			solutionOfferHelper.createDiscount(pack, solutionOffer, discount.getDiscountAbsRc(), discount.getDiscountAbsNrc(), discount.getDiscountPercRc(),
					discount.getDiscountPercNrc(), createdBy, em);
		}
		// create empty discounts for missing packages
		for (SdpPackage pack : packages) {
			solutionOfferHelper.createDiscount(pack, solutionOffer, null, null, null, null, createdBy, em);
		}
		return solutionOffer;
	}

	public DataServiceResponse modifySolutionOffer(Long solutionOfferId, String solutionOfferName, String solutionOfferDescription, Date startDate,
			Date endDate, List<ExternalIdDto> externalId, Long solutionId, String profile, String solutionOfferType, Long duration, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		logMap.put(SOLUTION_OFFER_DESCRIPTION, solutionOfferDescription);
		logMap.put(START_DATE, startDate);
		logMap.put(END_DATE, endDate);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(SOLUTION_ID, solutionId);
		logMap.put("profile", profile);
		logMap.put(SOLUTION_OFFER_TYPE, solutionOfferType);
		logMap.put(DURATION, duration);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifySolutionOffer(solutionOfferId, solutionOfferName, solutionOfferDescription, startDate, endDate, externalId, solutionId, profile,
					Utilities.getCurrentClassAndMethod(), solutionOfferType, duration, em);

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

	SdpSolutionOffer modifySolutionOffer(Long solutionOfferId, String solutionOfferName, String solutionOfferDescription, Date startDate, Date endDate,
			List<ExternalIdDto> externalIdList, Long solutionId, String profile, String modifiedBy, String solutionOfferType, Long duration, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		// Validazione Formale
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		SdpSolutionHelper solutionHelper = SdpSolutionHelper.getInstance();
		solutionOfferHelper.validateSearchSolutionOfferById(solutionOfferId);
		solutionOfferHelper.validateSolutionOffer(solutionOfferName, startDate, endDate, solutionOfferType, duration);

		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		RefSolutionOfferType sot = solutionOfferHelper.searchSolutionOfferTypeByType(solutionOfferType, em);
		if (sot == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_OFFER_TYPE, solutionOfferType);
		}
		SdpSolution solution = null;
		if (!solutionOfferHelper.isDiscounted(solutionOffer)) {
			solutionHelper.validateSearchSolutionById(solutionId);
			solution = solutionHelper.searchSolutionById(solutionId, em);
			if (solution == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_ID, solutionId);
			}
		} else {
			if (solutionId != null) {
				log.logDebug("Unallowed change of solutionId for a discounted SolutionOffer");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SOLUTION_ID, solutionId);
			}
		}

		// verifica duplicati
		SdpSolutionOffer checkName = solutionOfferHelper.searchSolutionOfferByName(solutionOfferName, em);
		if (checkName != null && !checkName.getSolutionOfferId().equals(solutionOfferId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SOLUTION_OFFER_NAME, solutionOfferName);
		}

		if (!isStatusActive(solutionOffer.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SOLUTION_OFFER_ID, solutionOfferId);
		}
		solutionOfferHelper.modifySolutionOffer(solutionOffer, solution, solutionOfferName, solutionOfferDescription, startDate, endDate, profile, modifiedBy, sot, duration);

		// NUOVA GESTIONE EXTERNAL ID PER STAR
		// butta via tutto
		solutionOfferHelper.deleteSolutionOfferExternalIds(solutionOffer, em);
		// puliti!
		createSolutionOfferExternalIds(solutionOffer, externalIdList, em);

		return solutionOffer;
	}

	public DataServiceResponse modifyDiscounts(List<SdpDiscountModifyRequestDto> discounts, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put("discount", discounts);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyDiscounts(discounts, Utilities.getCurrentClassAndMethod(), em);

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

	List<SdpDiscount> modifyDiscounts(List<SdpDiscountModifyRequestDto> discounts, String modifiedBy, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		// Validazione Formale
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		if (discounts == null || discounts.isEmpty()) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(DISCOUNT_ID, null), new ParameterDto("discountAbsRc", null),
					new ParameterDto("discountAbsNrc", null), new ParameterDto("discountPercRc", null), new ParameterDto("discountPercNrc", null));
		}
		List<SdpDiscount> beans = new ArrayList<SdpDiscount>();
		for (SdpDiscountModifyRequestDto dto : discounts) {
			solutionOfferHelper.validateSearchDiscountById(dto.getDiscountId());
			solutionOfferHelper.validateDiscount(dto.getDiscountAbsRc(), dto.getDiscountAbsNrc(), dto.getDiscountPercRc(), dto.getDiscountPercNrc());

			SdpDiscount discount = solutionOfferHelper.searchDiscountById(dto.getDiscountId(), em);
			if (discount == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, DISCOUNT_ID, String.valueOf(dto.getDiscountId()));
			}
			if (!isStatusActive(discount.getSolutionOffer().getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, new ParameterDto(DISCOUNT_ID, String.valueOf(dto.getDiscountId())),
						new ParameterDto(SOLUTION_OFFER_ID, String.valueOf(discount.getSolutionOffer().getSolutionOfferId())));
			}
			if (!isStatusActive(discount.getSdpPackage().getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, new ParameterDto(DISCOUNT_ID, String.valueOf(dto.getDiscountId())),
						new ParameterDto(PACKAGE_ID, String.valueOf(discount.getSdpPackage().getPackageId())));
			}
			solutionOfferHelper.modifyDiscount(discount, dto.getDiscountAbsRc(), dto.getDiscountAbsNrc(), dto.getDiscountPercRc(), dto.getDiscountPercNrc(),
					modifiedBy, em);
			beans.add(discount);
		}
		return beans;
	}

	public DataServiceResponse modifySolutionOfferPartyGroups(Long solutionOfferId, List<SdpPartyGroupRequestDto> partyGroups, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(PARTY_GROUP_LIST, partyGroups);
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

			modifySolutionOfferPartyGroups(solutionOfferId, partyGroups, Utilities.getCurrentClassAndMethod(), em);

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

	SdpSolutionOffer modifySolutionOfferPartyGroups(Long solutionOfferId, List<SdpPartyGroupRequestDto> partyGroups, String modifiedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());

		SdpSolutionOfferHelper handler = SdpSolutionOfferHelper.getInstance();
		SdpPartyGroupHelper groupHelper = SdpPartyGroupHelper.getInstance();
		// formal validation
		handler.validateSearchSolutionOfferById(solutionOfferId);
		groupHelper.validateLinkUpdateOperation(partyGroups);
		// workflow validation
		SdpSolutionOffer toModify = handler.searchSolutionOfferById(solutionOfferId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SOLUTION_OFFER_ID, solutionOfferId);
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
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SOLUTION_OFFER_ID, solutionOfferId), new ParameterDto(
							PARTY_GROUP_ID, String.valueOf(dto.getPartyGroupId())));
				}
				groupHelper.addPartyGroupLink(toModify, group, modifiedBy);
			}

		}
		return toModify;
	}

	public DataServiceResponse deleteSolutionOffer(Long solutionOfferId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			boolean warning = deleteSolutionOffer(solutionOfferId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			if (warning) {
				resp = buildUpdateResponse(Constants.CODE_OK_RELATED_SUBSCRIPTIONS);
			} else {
				resp = buildUpdateResponse(Constants.CODE_OK);
			}
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

	boolean deleteSolutionOffer(Long solutionOfferId, String deletedBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		// Validazione Formale
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		solutionOfferHelper.validateSearchSolutionOfferById(solutionOfferId);

		// retrieve refPartyGroup
		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		// SDP_02_16
		SdpPackageHelper packageHelper = SdpPackageHelper.getInstance();
		Long nPack = packageHelper.countPackagesNotDeletedBySolutionOfferId(solutionOfferId, em);
		if (nPack != null && nPack > 0L) {
			log.logDebug(nPack + " packages not deleted found for solution offer with id " + solutionOfferId);
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, SOLUTION_OFFER_ID, solutionOfferId);
		}

		// SDP_03_03
		if (!solutionOfferHelper.isDiscounted(solutionOffer)) {
			Long nDiscounted = solutionOfferHelper.countSolutionOffersNotDeletedByParent(solutionOfferId, em);
			if (nDiscounted != null && nDiscounted > 0L) {
				log.logDebug(nDiscounted + " discounted solutions not deleted found for solution offer with id " + solutionOfferId);
				throw new ValidationException(Constants.CODE_ELEMENT_WITH_NOT_DELETED_CHILDS, SOLUTION_OFFER_ID, solutionOfferId);
			}
		}

		solutionOfferHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, solutionOffer.getStatusId(), ConstantsHandler.getInstance()
				.retrieveLongConstant(Constants.DELETED), em);

		solutionOfferHelper.deleteSolutionOffer(solutionOffer, deletedBy);

		// SDP_02_01
		SdpSubscriptionHelper subHelper = SdpSubscriptionHelper.getInstance();
		Long nSub = subHelper.countSubscriptionsNotDeletedBySolutionOfferId(solutionOfferId, em);
		if (nSub != null && nSub > 0L) {
			log.logDebug(nSub + " subscriptions not deleted found for solution offer with id " + solutionOfferId);
			return true;
		}
		return false;
	}

	public SearchServiceResponse searchSolutionOffer(Long solutionOfferId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			// Validazione Formale
			solutionOfferHelper.validateSearchSolutionOfferById(solutionOfferId);

			// retrieve refPartyGroup
			SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
			if (solutionOffer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpSolutionOfferDto> searchResult = new ArrayList<SdpSolutionOfferDto>();
			searchResult.add(BeanConverter.converSdpSolutionOffer(solutionOffer));
			resp.setSearchResult(searchResult);
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

	public SearchServiceResponse searchSolutionOffersBySolution(String solutionName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpSolutionHelper solutionHelper = SdpSolutionHelper.getInstance();
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_NAME, solutionName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			solutionHelper.validateSearchSolutionByName(solutionName);
			solutionHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);
			// retrieve refPartyGroup
			SdpSolution solution = solutionHelper.searchSolutionByName(solutionName, em);
			if (solution == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_NAME, solutionName);
			}
			Long totalRes = solutionOfferHelper.countSolutionOffersBySolutionId(solution.getSolutionId(), em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SOLUTION_NAME, solutionName);
			}

			List<SdpSolutionOffer> soloffers = solutionOfferHelper.searchSolutionOffersBySolutionId(solution.getSolutionId(), startPosition, maxRecordsNumber,
					em);

			if (soloffers == null || soloffers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			SdpSolutionSolutionOfferDto solutionSolutionOfferDto = new SdpSolutionSolutionOfferDto();
			solutionSolutionOfferDto.setSolutionDto(BeanConverter.convertSdpSolution(solution));
			solutionSolutionOfferDto.setSolutionOfferDto(BeanConverter.convertSdpSolutionOffers(soloffers));
			ArrayList<SdpSolutionSolutionOfferDto> searchResult = new ArrayList<SdpSolutionSolutionOfferDto>();
			searchResult.add(solutionSolutionOfferDto);
			resp.setSearchResult(searchResult);
			resp.setTotalResult(totalRes);
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

	public DataServiceResponse solutionOfferChangeStatus(Long solutionOfferId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			solutionOfferChangeStatus(solutionOfferId, status, Utilities.getCurrentClassAndMethod(), em);

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

	SdpSolutionOffer solutionOfferChangeStatus(Long solutionOfferId, String status, String changedStatusBy, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		// Validazione Formale
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		solutionOfferHelper.validateSearchSolutionOfferById(solutionOfferId);
		solutionOfferHelper.validateChangeStatus(status);

		SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
		if (solutionOffer == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
		}
		Long statusId = StatusIdConverter.getStatusValue(status);
		solutionOfferHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, solutionOffer.getStatusId(), statusId, em);
		solutionOfferHelper.changeStatus(solutionOffer, statusId, changedStatusBy);
		return solutionOffer;
	}

	public SearchServiceResponse searchAllSolutionOffers(Long startPosition, Long maxRecordNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			solutionOfferHelper.validateSearchAll(startPosition, maxRecordNumber);
			// Validazione Formale
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = solutionOfferHelper.searchAllSolutionOffersCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpSolutionOffer> soloffers = solutionOfferHelper.searchAllSolutionOffers(startPosition, maxRecordNumber, em);

			if (soloffers == null || soloffers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSdpSolutionOffers(soloffers));
			resp.setTotalResult(totalRes);

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

	public SearchServiceResponse searchAllDiscountSolutionOffers(Long startPosition, Long maxRecordNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			solutionOfferHelper.validateSearchAll(startPosition, maxRecordNumber);
			// Validazione Formale
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = solutionOfferHelper.searchAllDiscountSolutionOffersCount(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpSolutionOffer> soloffers = solutionOfferHelper.searchAllDiscountSolutionOffers(startPosition, maxRecordNumber, em);

			if (soloffers == null || soloffers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSdpSolutionOffers(soloffers));
			resp.setTotalResult(totalRes);

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

	public SearchServiceResponse searchDiscountedSolutionOfferBySolutionOffer(String solutionOfferName, Long startPosition, Long maxRecordNumber,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_NAME, solutionOfferName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
			solutionOfferHelper.validateSearchSolutionOfferByName(solutionOfferName);
			solutionOfferHelper.validateSearchAll(startPosition, maxRecordNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpSolutionOffer parent = solutionOfferHelper.searchSolutionOfferByName(solutionOfferName, em);
			if (parent == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_NAME, solutionOfferName);
			}
			Long totalRes = solutionOfferHelper.countSolutionOffersByParent(parent.getSolutionOfferId(), em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpSolutionOffer> soloffers = solutionOfferHelper
					.searchSolutionOffersByParent(parent.getSolutionOfferId(), startPosition, maxRecordNumber, em);
			if (soloffers == null || soloffers.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSdpSolutionOffers(soloffers));
			resp.setTotalResult(totalRes);

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

	public SearchServiceResponse searchSolutionOffersByPartyGroup(Long partyGroupId, String tenantName) throws PropertyNotFoundException {
		// METODO PER AVS //
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PARTY_GROUP_ID, partyGroupId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpPartyGroupHelper handler = SdpPartyGroupHelper.getInstance();
			// formal validation
			handler.validateSearchPartyGroupById(partyGroupId);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpPartyGroup partyGroup = handler.searchPartyGroupById(partyGroupId, em);
			if (partyGroup == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_GROUP_ID, partyGroupId);
			}
			List<SdpSolutionOffer> soloffers = partyGroup.getSolutionOffers();
			if (soloffers == null || soloffers.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertSdpSolutionOffers(soloffers));
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

	public DataServiceResponse isSolutionOfferSubscribed(Long solutionOfferId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
			solutionOfferHelper.validateSearchSolutionOfferById(solutionOfferId);
			SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);
			if (solutionOffer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}
			Long result = SdpSubscriptionHelper.getInstance().countSubscriptionsNotDeletedBySolutionOfferId(solutionOfferId, em);
			if (result != null && result.longValue() > 0L) {
				resp = buildSearchResponse(Constants.CODE_OK);
			} else {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}
	
	public SearchServiceResponse searchAllSolutionOfferTypes(String tenantName) throws PropertyNotFoundException {
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

			List<RefSolutionOfferType> beans = SdpSolutionOfferHelper.getInstance().searchAllSolutionOfferTypes(em);

			if (beans == null || beans.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertSolutionOfferTypes(beans));
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

}
