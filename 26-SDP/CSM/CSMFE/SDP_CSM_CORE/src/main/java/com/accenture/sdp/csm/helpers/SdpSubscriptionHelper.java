package com.accenture.sdp.csm.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.model.jpa.SdpSubscriptionDetail;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpSubscriptionHelper extends SdpBaseHelper {

	private static SdpSubscriptionHelper instance;

	private SdpSubscriptionHelper() {
		super();
	}

	public static SdpSubscriptionHelper getInstance() {
		if (instance == null) {
			instance = new SdpSubscriptionHelper();
		}
		return instance;
	}

	public void validateSearchSubscriptionById(Long subscriptionId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SUBSCRIPTION_ID, subscriptionId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSubscriptionDetailList(List<Long> packageIdList) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (packageIdList == null || packageIdList.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "packageIdList");
		}
		for (Long id : packageIdList) {
			if (id == null) {
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, PACKAGE_ID);
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpSubscription> searchSubscriptionsNotDeletedByPartyId(Long partyId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSubscription.PARAM_PARTY_ID, partyId);
		parameHashMap.put(SdpSubscription.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpSubscription.class, SdpSubscription.QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS, parameHashMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsNotDeletedByAccountId(Long accountId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("accountId", accountId);
		parameHashMap.put(SdpSubscription.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		Set<SdpSubscription> resultSet = new HashSet<SdpSubscription>();
		List<SdpSubscription> subs = NamedQueryHelper.executeNamedQuery(SdpSubscription.class, "selectSubscriptionsByPayeeAccountIdAndStatusIdNotEqual",
				parameHashMap, em);
		if (subs != null) {
			resultSet.addAll(subs);
		}
		subs = NamedQueryHelper.executeNamedQuery(SdpSubscription.class, "selectSubscriptionsByOwnerAccountIdAndStatusIdNotEqual", parameHashMap, em);
		if (subs != null) {
			resultSet.addAll(subs);
		}
		List<SdpSubscription> results = new ArrayList<SdpSubscription>();
		results.addAll(resultSet);
		return results;
	}

	public void validateParentSubscriptionById(Long parentSubscriptionId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARENT_SUBSCRIPTION_ID, parentSubscriptionId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public Long searchSubscriptionByRoleNameCount(String roleName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (roleName == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("roleName", roleName);
		return NamedQueryHelper.executeNamedQueryCount("selectSubscriptionsByRoleNameCount", parameHashMap, em);
	}

	public SdpSubscription createSubscription(SdpParty sdpParty, SdpSolutionOffer sdpSolutionOffer, SdpSubscription parentSubscription,
			SdpCredential credential, String role, SdpAccount ownerAccount, SdpAccount payeeAccount, SdpSite site, String externalId, Date activationDate,
			Date expireDate, String createdBy, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSubscription newSubscription = new SdpSubscription();
		newSubscription.setSdpParty(sdpParty);
		newSubscription.setSdpSolutionOffer(sdpSolutionOffer);
		newSubscription.setParentSubscription(parentSubscription);
		newSubscription.setSdpCredential(credential);
		newSubscription.setSdpRole(role);
		newSubscription.setSdpAccountOwner(ownerAccount);
		newSubscription.setSdpAccountPayee(payeeAccount);
		newSubscription.setSdpPartySite(site);
		newSubscription.setExternalId(externalId);
		newSubscription.setStartDate(activationDate);
		newSubscription.setEndDate(expireDate);
		newSubscription.setCreatedById(createdBy);
		newSubscription.setCreatedDate(new Date());
		newSubscription.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVATING));
		em.persist(newSubscription);
		return newSubscription;
	}

	public SdpSubscriptionDetail createSubscriptionDetail(SdpSubscription subscription, SdpPackage sdpPackage, String subscriptionOfferProfile,
			String externalId, String createdBy, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSubscriptionDetail newSubscriptionDetail = new SdpSubscriptionDetail();
		newSubscriptionDetail.setPackageId(sdpPackage.getPackageId());
		newSubscriptionDetail.setSubscriptionId(subscription.getSubscriptionId());
		newSubscriptionDetail.setSdpSubscription(subscription);
		newSubscriptionDetail.setSdpPackage(sdpPackage);
		newSubscriptionDetail.setSubscriptionOfferProfile(subscriptionOfferProfile);
		newSubscriptionDetail.setExternalId(externalId);
		newSubscriptionDetail.setCreatedById(createdBy);
		newSubscriptionDetail.setCreatedDate(new Date());
		newSubscriptionDetail.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVATING));
		em.persist(newSubscriptionDetail);
		return newSubscriptionDetail;
	}

	public void modifySubscription(SdpSubscription subscription, SdpSubscription parentSubscription, SdpCredential credential, String role,
			SdpAccount ownerAccount, SdpAccount payeeAccount, SdpSite site, String externalId, Date activationDate, Date expireDate, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// il party non e' modificabile
		subscription.setParentSubscription(parentSubscription);
		subscription.setSdpCredential(credential);
		subscription.setSdpRole(role);
		subscription.setSdpAccountOwner(ownerAccount);
		subscription.setSdpAccountPayee(payeeAccount);
		subscription.setSdpPartySite(site);
		subscription.setExternalId(externalId);
		subscription.setStartDate(activationDate);
		subscription.setEndDate(expireDate);
		subscription.setUpdatedById(updatedBy);
		subscription.setUpdatedDate(new Date());
	}

	public void modifySubscriptionDetail(SdpSubscriptionDetail newSubscriptionDetail, String subscriptionOfferProfile, String externalId, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		newSubscriptionDetail.setSubscriptionOfferProfile(subscriptionOfferProfile);
		newSubscriptionDetail.setExternalId(externalId);
		newSubscriptionDetail.setUpdatedById(updatedBy);
		newSubscriptionDetail.setUpdatedDate(new Date());
	}

	public void deleteSubscription(SdpSubscription sdpSubscription, String deletedBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpSubscription.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETING));
		sdpSubscription.setDeletedById(deletedBy);
		sdpSubscription.setDeletedDate(new Date());
	}

	public void deleteSubscriptionDetail(SdpSubscriptionDetail sdpSubscriptionDetail, String deletedBy, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpSubscriptionDetail.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETING));
		sdpSubscriptionDetail.setDeletedById(deletedBy);
		sdpSubscriptionDetail.setDeletedDate(new Date());
	}

	public void changeSubscriptionStatus(SdpSubscription sdpSubscription, Long nextstatus, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpSubscription.setStatusId(nextstatus);
		sdpSubscription.setChangeStatusById(updatedBy);
		sdpSubscription.setChangeStatusDate(new Date());
	}

	public void changeSubscriptionDetailStatus(SdpSubscriptionDetail sdpSubscriptionDetail, Long nextstatus, String updatedBy, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpSubscriptionDetail.setStatusId(nextstatus);
		sdpSubscriptionDetail.setChangeStatusById(updatedBy);
		sdpSubscriptionDetail.setChangeStatusDate(new Date());
	}

	public SdpSubscription searchSubscriptionById(Long subscriptionId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (subscriptionId == null) {
			return null;
		}
		return em.find(SdpSubscription.class, subscriptionId);
	}

	public SdpSubscriptionDetail searchSubscriptionDetailById(Long subscriptionId, Long packageId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (packageId == null || subscriptionId == null) {
			return null;
		} else {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(SdpSubscriptionDetail.PARAM_SUBSCRIPTION_ID, subscriptionId);
			params.put(SdpSubscriptionDetail.PARAM_PACKAGE_ID, packageId);
			List<SdpSubscriptionDetail> results = NamedQueryHelper.executeNamedQuery(SdpSubscriptionDetail.class, "selectSubscriptionDetailsById", params, em);
			if (results == null || results.isEmpty()) {
				return null;
			}
			return results.get(0);
		}
	}

	public void validateSubscription(Long partyId, Long solutionOfferId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_ID, partyId);
		validationMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSubscriptionDetail(Long subscriptionId, Long packageId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SUBSCRIPTION_ID, subscriptionId);
		validationMap.put(PACKAGE_ID, packageId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpSubscription> searchSubscriptionsNotDeletedByUsername(String username, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(username)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSubscription.PARAM_USERNAME, username);
		parameHashMap.put(SdpSubscription.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpSubscription.class, "selectSubscriptionsByUsernameAndStatusIdNotEqual", parameHashMap, em);
	}

	public Long countSubscriptionDetailsNotDeletedByPackageId(Long packageId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (packageId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSubscriptionDetail.PARAM_PACKAGE_ID, packageId);
		parameHashMap.put(SdpSubscriptionDetail.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpSubscriptionDetail.QUERY_COUNT_BY_PACKAGEID_AND_NOT_STATUS, parameHashMap, em);
	}

	public Long countSubscriptionsByParty(SdpParty party, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (party == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_PARTY_ID, party.getPartyId());
		return NamedQueryHelper.executeNamedQueryCount(SdpSubscription.QUERY_COUNT_BY_PARTYID, parametersMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsByPartyPaginated(SdpParty party, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (party == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_PARTY_ID, party.getPartyId());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, SdpSubscription.QUERY_RETRIEVE_BY_PARTYID, parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public List<SdpSubscription> searchSubscriptionsByParentSubscriptionPaginated(SdpSubscription parentSubscription, Long startPosition,
			Long maxRecordsNumber, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentSubscription == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_PARENT_SUBSCRIPTION_ID, parentSubscription.getSubscriptionId());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, "selectSubscriptionsByParentSubscription", parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countSubscriptionsByParentSubscription(SdpSubscription parentSubscription, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentSubscription == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_PARENT_SUBSCRIPTION_ID, parentSubscription.getSubscriptionId());
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsByParentSubscription", parametersMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsByCredentialPaginated(SdpCredential credential, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (credential == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_USERNAME, credential.getUsername());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, "selectSubscriptionsByCredential", parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countSubscriptionsByCredential(SdpCredential credential, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (credential == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_USERNAME, credential.getUsername());
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsByCredential", parametersMap, em);
	}

	public void validateSearchSubscriptionsByAccount(String ownerAccountName, String payeeAccountName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("ownerAccountName", ownerAccountName);
		validationMap.put("payeeAccountName", payeeAccountName);
		// at least one have to be present
		ValidationResult res = ValidationUtils.validateSoftMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpSubscription> searchSubscriptionsByAccountOwnerAndAccountPayeePaginated(SdpAccount accountOwner, SdpAccount accountPayee,
			Long startPosition, Long maxRecordsNumber, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountOwner == null || accountPayee == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_OWNER_ID, accountOwner.getAccountId());
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_PAYEE_ID, accountPayee.getAccountId());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, "selectSubscriptionsByAccountOwnerAndAccountPayee", parametersMap,
				startPosition, maxRecordsNumber, em);
	}

	public Long countSubscriptionsByAccountOwnerAndAccountPayee(SdpAccount accountOwner, SdpAccount accountPayee, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountOwner == null || accountPayee == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_OWNER_ID, accountOwner.getAccountId());
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_PAYEE_ID, accountPayee.getAccountId());
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsByAccountOwnerAndAccountPayee", parametersMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsByAccountOwnerPaginated(SdpAccount accountOwner, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountOwner == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_OWNER_ID, accountOwner.getAccountId());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, "selectSubscriptionsByAccountOwner", parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countSubscriptionsByAccountOwner(SdpAccount accountOwner, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountOwner == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_OWNER_ID, accountOwner.getAccountId());
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsByAccountOwner", parametersMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsByAccountPayeePaginated(SdpAccount accountPayee, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountPayee == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_PAYEE_ID, accountPayee.getAccountId());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, "selectSubscriptionsByAccountPayee", parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countSubscriptionsByAccountPayee(SdpAccount accountPayee, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountPayee == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_ACCOUNT_PAYEE_ID, accountPayee.getAccountId());
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsByAccountPayee", parametersMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsByPartySite(SdpSite partySite, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partySite == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("partySiteId", partySite.getSiteId());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSubscription.class, "selectSubscriptionsByPartySite", parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countSubscriptionsByPartySite(SdpSite partySite, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partySite == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("partySiteId", partySite.getSiteId());
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsByPartySite", parametersMap, em);
	}

	public List<SdpSubscriptionDetail> searchSubscriptionDetailsBySubscriptionId(Long subscriptionId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (subscriptionId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSubscriptionDetail.PARAM_SUBSCRIPTION_ID, subscriptionId);
		return NamedQueryHelper.executeNamedQuery(SdpSubscriptionDetail.class, "selectSubscriptionDetailsBySubscriptionId", parameHashMap, em);
	}

	public List<SdpSubscription> searchSubscriptionsNotDeletedByParentSubscription(SdpSubscription parent, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parent == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSubscription.PARAM_PARENT_SUBSCRIPTION_ID, parent.getSubscriptionId());
		parameHashMap.put(SdpSubscription.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpSubscription.class, "selectSubscriptionsByParentSubscriptionAndStatusIdNotEqual", parameHashMap, em);
	}

	@Override
	public void validateChangeStatus(String status) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("status", status);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		res = ValidationUtils.validateStatus(status);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		if (status.equals(StatusIdConverter.getStatusDescription(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETING)))) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "status", status);
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpSubscription searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpSubscription.class, "selectSubscriptionByExternalId", externalId, em);
	}

	public SdpSubscriptionDetail searchDetailByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpSubscriptionDetail.class, "selectSubscriptionDetailByExternalId", externalId, em);
	}

	public Long countSubscriptionsNotDeletedBySolutionOfferId(Long solutionOfferId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionOfferId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSubscription.PARAM_SOLUTION_OFFER_ID, solutionOfferId);
		parametersMap.put(SdpSubscription.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount("countSubscriptionsBySolutionOfferIdAndStatusNotEqual", parametersMap, em);
	}
	
	public void validateDates(Date startDate, Date endDate) throws ValidationException {
		ValidationUtils.validateDates(startDate, endDate);
	}
}