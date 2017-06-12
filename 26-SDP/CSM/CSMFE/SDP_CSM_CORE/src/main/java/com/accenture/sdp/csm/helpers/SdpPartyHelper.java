package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.GenderType;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyData;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpPartyHelper extends SdpBaseHelper {

	private static SdpPartyHelper instance;

	private SdpPartyHelper() {
		super();
	}

	public static SdpPartyHelper getInstance() {
		if (instance == null) {
			instance = new SdpPartyHelper();
		}
		return instance;
	}

	public SdpParty createParentParty(String partyName, String partyDescription, String externalId, String partyProfile, List<SdpPartyGroup> partyGroups,
			String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpParty newParty = new SdpParty();
		newParty.setPartyName(partyName);
		newParty.setPartyDescription(partyDescription);
		newParty.setRefPartyType(ConstantsHandler.getInstance().retrieveLongConstant(Constants.PARTY_TYPE_ORGANIZATION));
		newParty.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newParty.setExternalId(externalId);
		newParty.setPartyProfile(partyProfile);
		newParty.setCreatedById(createdBy);
		newParty.setCreatedDate(new Date());
		newParty.setPartyGroups(partyGroups);
		em.persist(newParty);
		return newParty;
	}

	public SdpParty createChildParty(String partyName, String partyDescription, SdpParty parentParty, List<SdpPartyGroup> partyGroups, SdpSite userDefaultSite,
			String externalId, String partyProfile, String firstName, String lastName, String address, String city, String zipCode, String province,
			String country, String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber,
			String email, String note, String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpParty newParty = new SdpParty();
		newParty.setPartyName(partyName);
		newParty.setPartyDescription(partyDescription);
		newParty.setRefPartyType(ConstantsHandler.getInstance().retrieveLongConstant(Constants.PARTY_TYPE_USER));
		newParty.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newParty.setParentParty(parentParty);
		newParty.setPartyGroups(partyGroups);
		newParty.setExternalId(externalId);
		newParty.setPartyProfile(partyProfile);
		newParty.setCreatedById(createdBy);
		newParty.setCreatedDate(new Date());
		SdpPartyData partyData = new SdpPartyData();
		partyData.setSdpPartySite(userDefaultSite);
		partyData.setFirstName(firstName);
		partyData.setLastName(lastName);
		partyData.setAddress(address);
		partyData.setCity(city);
		partyData.setZipCode(zipCode);
		partyData.setProvince(province);
		partyData.setCountry(country);
		if (!Utilities.isNull(gender)) {
			partyData.setGender(GenderType.Gender.getGenderValue(gender));
		}
		partyData.setBirthDate(birthDate);
		partyData.setBirthProvince(birthProvince);
		partyData.setBirthCountry(birthCountry);
		partyData.setBirthCity(birthCity);
		partyData.setContactTel(phoneNumber);
		partyData.setContactFax(faxNumber);
		partyData.setEmail(email);
		partyData.setNote(note);
		partyData.setCreatedById(createdBy);
		partyData.setCreatedDate(new Date());
		log.logDebug("Persisting new Party");
		em.persist(newParty);
		partyData.setPartyId(newParty.getPartyId());
		log.logDebug("Persisting Party Data");
		em.persist(partyData);
		newParty.setSdpPartyData(partyData);
		return newParty;
	}

	public void modifyParentParty(SdpParty party, String partyName, String partyDescription, String externalId, String partyProfile, String modifyBy)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		party.setPartyName(partyName);
		party.setPartyDescription(partyDescription);
		party.setPartyProfile(partyProfile);
		party.setExternalId(externalId);
		party.setUpdatedById(modifyBy);
		party.setUpdatedDate(new Date());
	}

	public void modifyChildParty(SdpParty party, String partyName, String partyDescription, String partyProfile, SdpSite site, String externalId,
			String firstName, String lastName, String streetAddress, String city, String zipCode, String province, String country, String gender,
			Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber, String email, String note,
			String modifyBy) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		party.setPartyName(partyName);
		party.setPartyDescription(partyDescription);
		party.setPartyProfile(partyProfile);
		party.setExternalId(externalId);
		Date updatedDate = new Date();
		SdpPartyData sdpPartyData = party.getSdpPartyData();
		if (sdpPartyData != null) {
			sdpPartyData.setSdpPartySite(site);
			sdpPartyData.setFirstName(firstName);
			sdpPartyData.setLastName(lastName);
			sdpPartyData.setAddress(streetAddress);
			sdpPartyData.setCity(city);
			sdpPartyData.setZipCode(zipCode);
			sdpPartyData.setProvince(province);
			sdpPartyData.setCountry(country);
			sdpPartyData.setGender(GenderType.Gender.getGenderValue(gender));
			sdpPartyData.setBirthDate(birthDate);
			sdpPartyData.setBirthProvince(birthProvince);
			sdpPartyData.setBirthCountry(birthCountry);
			sdpPartyData.setBirthCity(birthCity);
			sdpPartyData.setContactTel(phoneNumber);
			sdpPartyData.setContactFax(faxNumber);
			sdpPartyData.setEmail(email);
			sdpPartyData.setNote(note);
			sdpPartyData.setUpdatedById(modifyBy);
			sdpPartyData.setUpdatedDate(updatedDate);
		}
		// Set UpdatedById && UpdatedDate
		party.setUpdatedById(modifyBy);
		party.setUpdatedDate(updatedDate);
	}

	public List<SdpParty> searchParentPartyByNameLikeAndPartyGroupId(String partyName, Long partyGroupId, Long startPosition, Long maxRecordsNumber,
			EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpParty.PARAM_PARENT_PARTY_NAME, partyName + "%");
		parametersMap.put(SdpParty.PARAM_PARTY_GROUP_ID, partyGroupId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpParty.class, "selectOrganizationPartyByNameLikeAndPartyGroupId", parametersMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countParentPartyByNameLikeAndPartyGroupId(String partyName, Long partyGroupId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpParty.PARAM_PARENT_PARTY_NAME, partyName + "%");
		parametersMap.put(SdpParty.PARAM_PARTY_GROUP_ID, partyGroupId);
		return NamedQueryHelper.executeNamedQueryCount("selectOrganizationPartyByNameLikeAndPartyGroupIdCount", parametersMap, em);
	}

	public List<SdpParty> searchParentPartyByNameLike(String partyName, Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpParty.PARAM_PARENT_PARTY_NAME, partyName + "%");
		return NamedQueryHelper.executePaginatedNamedQuery(SdpParty.class, "selectOrganizationPartyByNameLike", parametersMap, startPosition, maxRecordsNumber,
				em);
	}

	public Long searchParentPartyByNameLikeCount(String partyName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpParty.PARAM_PARENT_PARTY_NAME, partyName + "%");
		return NamedQueryHelper.executeNamedQueryCount("selectOrganizationPartyByNameLikeCount", parametersMap, em);
	}

	public List<SdpParty> searchChildPartyByParentPartyIdAndPartyNameLike(String partyName, Long parentPartyId, Long startPosition, Long maxRecordsNumber,
			EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentPartyId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		// FIXME levato per avs che lo setta errato
		// parametersMap.put("organizationId", parentPartyId)
		List<SdpParty> parties;
		if (!Utilities.isNull(partyName)) {
			parametersMap.put(SdpParty.PARAM_PARTY_NAME, partyName + "%");
			parties = NamedQueryHelper.executePaginatedNamedQuery(SdpParty.class, "selectChildPartyByOrganizationIdAndPartyNameLike", parametersMap,
					startPosition, maxRecordsNumber, em);
		} else {
			parties = NamedQueryHelper.executeNamedQuery(SdpParty.class, "selectChildPartyByOrganizationId", parametersMap, em);
		}
		return parties;
	}

	public Long countChildPartyByParentPartyIdAndPartyNameLike(String partyName, Long parentPartyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentPartyId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		// FIXME levato per avs che lo setta errato
		// parametersMap.put("organizationId", parentPartyId)
		Long totalCount;
		if (!Utilities.isNull(partyName)) {
			parametersMap.put(SdpParty.PARAM_PARTY_NAME, partyName + "%");
			totalCount = NamedQueryHelper.executeNamedQueryCount("selectChildPartyByOrganizationIdAndPartyNameLikeCount", parametersMap, em);
		} else {
			totalCount = NamedQueryHelper.executeNamedQueryCount("selectChildPartyByOrganizationIdCount", parametersMap, em);
		}
		return totalCount;
	}

	public SdpParty searchPartyById(Long partyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		return em.find(SdpParty.class, partyId);
	}

	public List<SdpParty> searchChildsNotDeletedByParentPartyId(Long partyId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("organizationId", partyId);
		parametersMap.put(SdpParty.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpParty.class, "selectChildPartyByOrganizatioIdAndStatusIdNotEqual", parametersMap, em);
	}

	public List<SdpParty> searchChildPartyByName(String firstName, String lastName, String partyName, String parentPartyName, Long startPosition,
			Long maxResultsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(lastName)) {
			return null;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("lastName", lastName);
		String queryString = "select p from SdpParty p where p.refPartyType=2 and p.sdpPartyData.lastName=:lastName";
		if (!Utilities.isNull(firstName)) {
			params.put("firstName", firstName);
			queryString += " and p.sdpPartyData.firstName=:firstName";
		}
		if (!Utilities.isNull(partyName)) {
			params.put("partyName", partyName);
			queryString += " and p.partyName=:partyName";
		}
		if (!Utilities.isNull(parentPartyName)) {
			params.put("parentPartyName", parentPartyName);
			queryString += " and p.parentParty.partyName=:parentPartyName and p.parentParty.refPartyType=1";
		}
		return NamedQueryHelper.executePaginatedQueryString(SdpParty.class, queryString, params, startPosition, maxResultsNumber, em);
	}

	public Long countChildPartyByName(String firstName, String lastName, String partyName, String parentPartyName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(lastName)) {
			return null;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("lastName", lastName);
		String queryString = "select COUNT(p.partyId) from SdpParty p where p.refPartyType=2 and p.sdpPartyData.lastName=:lastName";
		if (!Utilities.isNull(firstName)) {
			params.put("firstName", firstName);
			queryString += " and p.sdpPartyData.firstName=:firstName";
		}
		if (!Utilities.isNull(partyName)) {
			params.put("partyName", partyName);
			queryString += " and p.partyName=:partyName";
		}
		if (!Utilities.isNull(parentPartyName)) {
			params.put("parentPartyName", parentPartyName);
			queryString += " and p.parentParty.partyName=:parentPartyName and p.parentParty.refPartyType=1";
		}
		return NamedQueryHelper.executeQueryStringCount(queryString, params, em);
	}

	public void changeStatus(SdpParty party, Long statusId, String changedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		party.setChangeStatusDate(new Date());
		party.setChangeStatusById(changedBy);
		party.setStatusId(statusId);
	}

	public void validateParentParty(String partyName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("partyName", partyName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateChildParty(String partyName, String firstName, String lastName, String gender, Date birthDate) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_NAME, partyName);
		validationMap.put(FIRST_NAME, firstName);
		validationMap.put(LAST_NAME, lastName);
		log.logDebug("Validating mandatory fields");
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("Validating Gender");
		res = ValidationUtils.validateGender(gender);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		// check date is not in the future
		if (birthDate != null) {
			log.logDebug("Validating BirthDate");
			res = ValidationUtils.isAfterNow("birthDate", birthDate);
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchParentPartyByName(String partyName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_NAME, partyName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
	}

	public void validateSearchPartyById(Long partyId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_ID, partyId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public boolean isAParent(SdpParty parentParty) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentParty == null) {
			return false;
		}
		return ConstantsHandler.getInstance().retrieveLongConstant(Constants.PARTY_TYPE_ORGANIZATION).equals(parentParty.getRefPartyType());
	}

	public void validateSearchPartyByName(String partyName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PARTY_NAME, partyName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchChildPartyByName(String lastName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("Last Name", lastName);
		log.logDebug("Validating mandatory fields");
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void deleteParty(SdpParty party, String madeBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		party.setDeletedDate(new Date());
		party.setDeletedById(madeBy);
		party.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
	}

	public void validateSearchParentPartyById(Long parentPartyId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("parentPartyId", parentPartyId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpParty> searchChildPartyBySiteIdAndPartyGroupIdPaginated(Long siteId, Long partyGroupId, Long startPosition, Long maxResultsNumber,
			EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null || partyGroupId == null) {
			return null;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(SdpParty.PARAM_SITE_ID, siteId);
		params.put(SdpParty.PARAM_PARTY_GROUP_ID, partyGroupId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpParty.class, "selectChildPartyBySiteIdAndPartyGroupId", params, startPosition, maxResultsNumber,
				em);
	}

	public Long countChildPartyBySiteIdAndPartyGroupId(Long siteId, Long partyGroupId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null || partyGroupId == null) {
			return null;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(SdpParty.PARAM_SITE_ID, siteId);
		params.put(SdpParty.PARAM_PARTY_GROUP_ID, partyGroupId);
		return NamedQueryHelper.executeNamedQueryCount("selectChildPartyBySiteIdAndPartyGroupIdCount", params, em);
	}

	public Long countChildPartiesNotDeletedBySiteId(Long siteId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpParty.PARAM_SITE_ID, siteId);
		parameHashMap.put(SdpParty.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpParty.QUERY_CHILD_COUNT_BY_SITEID_AND_NOT_STATUS, parameHashMap, em);
	}

	public SdpParty searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpParty.class, SdpParty.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}

}
