package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpPartySiteRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpSiteHelper extends SdpBaseHelper {

	private static SdpSiteHelper instance;

	private SdpSiteHelper() {
		super();
	}

	public static SdpSiteHelper getInstance() {
		if (instance == null) {
			instance = new SdpSiteHelper();
		}
		return instance;
	}

	// method Create-Modify-Search Site
	public SdpSite createSite(String siteName, String siteDescription, SdpParty parentParty, String externalId, String address, String city, String zipCode,
			String province, String country, String siteProfile, String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSite newPartySite = new SdpSite();
		// Model valorization
		newPartySite.setSiteName(siteName);
		newPartySite.setSiteDescription(siteDescription);
		newPartySite.setSdpParty(parentParty);
		newPartySite.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newPartySite.setExternalId(externalId);
		newPartySite.setAddress(address);
		newPartySite.setCity(city);
		newPartySite.setZipCode(zipCode);
		newPartySite.setProvince(province);
		newPartySite.setCountry(country);
		newPartySite.setSiteProfile(siteProfile);
		newPartySite.setCreatedById(createdBy);
		newPartySite.setCreatedDate(new Date());
		em.persist(newPartySite);
		// aggiunto dopo modifica alla _createSite per anomalia SDP00000053
		parentParty.getSdpPartySites().add(newPartySite);
		return newPartySite;

	}

	public void modifySite(SdpSite siteToUpdate, String siteName, String siteDescription, String address, String city, String zipCode, String province,
			String country, String externalId, String siteProfile, String modifiedBy) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		siteToUpdate.setSiteName(siteName);
		siteToUpdate.setSiteDescription(siteDescription);
		siteToUpdate.setAddress(address);
		siteToUpdate.setCity(city);
		siteToUpdate.setZipCode(zipCode);
		siteToUpdate.setProvince(province);
		siteToUpdate.setCountry(country);
		siteToUpdate.setExternalId(externalId);
		siteToUpdate.setSiteProfile(siteProfile);
		siteToUpdate.setUpdatedById(modifiedBy);
		siteToUpdate.setUpdatedDate(new Date());
	}

	public void deleteSite(SdpSite siteToUpdate, String deletedBy) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		siteToUpdate.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		siteToUpdate.setDeletedById(deletedBy);
		siteToUpdate.setDeletedDate(new Date());
	}

	public List<SdpSite> searchSitesByName(String siteName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(siteName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSite.PARAM_SITE_NAME, siteName);
		return NamedQueryHelper.executeNamedQuery(SdpSite.class, SdpSite.QUERY_RETRIVE_BY_NAME, parameHashMap, em);
	}

	public List<SdpSite> searchSitesByNameLikePaginated(String siteName, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " paginated start : " + startPosition + " record : " + maxRecordsNumber);
		if (siteName == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSite.PARAM_SITE_NAME, siteName + "%");
		return NamedQueryHelper.executePaginatedNamedQuery(SdpSite.class, SdpSite.QUERY_RETRIVE_BY_NAME_LIKE, parameHashMap, startPosition, maxRecordsNumber,
				em);
	}

	public Long countSitesByNameLike(String siteName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " paginated count start");
		if (siteName == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSite.PARAM_SITE_NAME, siteName + "%");
		return NamedQueryHelper.executeNamedQueryCount(SdpSite.QUERY_COUNT_BY_NAME_LIKE, parameHashMap, em);
	}

	public List<SdpSite> searchSitesByPartyId(Long parentPartyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentPartyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSite.PARAM_PARTY_ID, parentPartyId);
		return NamedQueryHelper.executeNamedQuery(SdpSite.class, SdpSite.QUERY_RETRIEVE_BY_PARTYID, parameHashMap, em);
	}

	public List<SdpSite> searchSitesByPartyIdPaginated(Long parentPartyId, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentPartyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSite.PARAM_PARTY_ID, parentPartyId);
		return NamedQueryHelper
				.executePaginatedNamedQuery(SdpSite.class, SdpSite.QUERY_RETRIEVE_BY_PARTYID, parameHashMap, startPosition, maxRecordsNumber, em);
	}

	public Long countSitesByPartyId(Long parentPartyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentPartyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpSite.PARAM_PARTY_ID, parentPartyId);
		return NamedQueryHelper.executeNamedQueryCount(SdpSite.QUERY_COUNT_BY_PARTYID, parameHashMap, em);
	}

	public void changeStatus(SdpSite siteToUpdate, Long nextStatusId, String modifiedBy) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		siteToUpdate.setStatusId(nextStatusId);
		siteToUpdate.setChangeStatusById(modifiedBy);
		siteToUpdate.setChangeStatusDate(new Date());
	}

	public void validateSite(String siteName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SITE_NAME, siteName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchSite(Long siteId, String siteName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SITE_ID, siteId);
		validationMap.put(SITE_NAME, siteName);
		// at least one have to be present
		ValidationResult res = ValidationUtils.validateSoftMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSites(List<SdpPartySiteRequestDto> partySites) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partySites != null) {
			for (SdpPartySiteRequestDto partySite : partySites) {
				validateSite(partySite.getSiteName());
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpSite searchSiteById(Long siteId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null) {
			return null;
		}
		return em.find(SdpSite.class, siteId);
	}

	public SdpSite retrieveDefaultPartySite(String userDefaultSiteName, List<SdpSite> sites) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(userDefaultSiteName)) {
			return null;
		}
		if (sites != null) {
			for (SdpSite site : sites) {
				if (userDefaultSiteName.equalsIgnoreCase(site.getSiteName())) {
					return site;
				}
			}
		}
		throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "userDefaultSiteName", userDefaultSiteName);
	}

	public SdpSite retrieveDefaultPartySite(Long userDefaultSiteId, List<SdpSite> sites) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (userDefaultSiteId == null) {
			return null;
		}
		if (sites != null) {
			for (SdpSite site : sites) {
				if (userDefaultSiteId.equals(site.getSiteId())) {
					return site;
				}
			}
		}
		throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "userDefaultSiteId", String.valueOf(userDefaultSiteId));
	}

	public SdpSite retrievePartySite(String siteName, List<SdpSite> sites) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(siteName) || sites == null) {
			return null;
		}
		for (SdpSite site : sites) {
			if (siteName.equalsIgnoreCase(site.getSiteName())) {
				return site;
			}
		}
		return null;
	}

	public Long countSitesNotDeletedByPartyId(Long partyId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put(SdpSite.PARAM_PARTY_ID, partyId);
		parametersMap.put(SdpSite.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpSite.QUERY_COUNT_BY_PARTYID_AND_NOT_STATUS, parametersMap, em);
	}

	public void validateSearchSiteById(Long siteId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SITE_ID, siteId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpSite searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpSite.class, SdpSite.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}
}
