package com.accenture.sdp.csm.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.commons.DeviceEnums.Filter;
import com.accenture.sdp.csm.commons.IsDefaultAccount;
import com.accenture.sdp.csm.commons.IsMandatory;
import com.accenture.sdp.csm.commons.PartyTypeIdConverter;
import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.dto.requests.ExternalIdDto;
import com.accenture.sdp.csm.dto.responses.AvsCountryLangCodeDto;
import com.accenture.sdp.csm.dto.responses.AvsDeviceIdTypeDto;
import com.accenture.sdp.csm.dto.responses.AvsDigitalGoodDto;
import com.accenture.sdp.csm.dto.responses.AvsPaymentTypeDto;
import com.accenture.sdp.csm.dto.responses.AvsPcExtendedRatingDto;
import com.accenture.sdp.csm.dto.responses.AvsPcLevelDto;
import com.accenture.sdp.csm.dto.responses.AvsPlatformDto;
import com.accenture.sdp.csm.dto.responses.AvsRetailerDomainDto;
import com.accenture.sdp.csm.dto.responses.RefCurrencyTypeResponseDto;
import com.accenture.sdp.csm.dto.responses.RefFrequencyTypeResponseDto;
import com.accenture.sdp.csm.dto.responses.RefServiceTypeResponseDto;
import com.accenture.sdp.csm.dto.responses.RefSolutionTypeResponseDto;
import com.accenture.sdp.csm.dto.responses.RefStatusTypeResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpChildPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpCredentialDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceAccessResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceBrandResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceChannelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceCounterResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceModelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDevicePolicyConfigResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDevicePolicyResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpEndpointInfoDto;
import com.accenture.sdp.csm.dto.responses.SdpOfferDto;
import com.accenture.sdp.csm.dto.responses.SdpOfferGroupDto;
import com.accenture.sdp.csm.dto.responses.SdpOperatorResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpOperatorRightResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPackageDto;
import com.accenture.sdp.csm.dto.responses.SdpPackagePriceDto;
import com.accenture.sdp.csm.dto.responses.SdpParentPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyDeviceResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyGroupResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartySiteDto;
import com.accenture.sdp.csm.dto.responses.SdpPlatformDto;
import com.accenture.sdp.csm.dto.responses.SdpPriceCatalogDto;
import com.accenture.sdp.csm.dto.responses.SdpRoleDto;
import com.accenture.sdp.csm.dto.responses.SdpSecretQuestionDto;
import com.accenture.sdp.csm.dto.responses.SdpServiceTemplateDto;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantDto;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantOperationDto;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartItemResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDetailDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionDetailResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpTenantResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpVoucherDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.model.jpa.AvsCountryLangCode;
import com.accenture.sdp.csm.model.jpa.AvsDeviceIdType;
import com.accenture.sdp.csm.model.jpa.AvsDigitalGood;
import com.accenture.sdp.csm.model.jpa.AvsLnkOfferDigitalGood;
import com.accenture.sdp.csm.model.jpa.AvsPaymentType;
import com.accenture.sdp.csm.model.jpa.AvsPcExtendedRating;
import com.accenture.sdp.csm.model.jpa.AvsPcLevel;
import com.accenture.sdp.csm.model.jpa.AvsPlatform;
import com.accenture.sdp.csm.model.jpa.AvsRetailerDomain;
import com.accenture.sdp.csm.model.jpa.RefCurrencyType;
import com.accenture.sdp.csm.model.jpa.RefDeviceBrand;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.RefDeviceModel;
import com.accenture.sdp.csm.model.jpa.RefFrequencyType;
import com.accenture.sdp.csm.model.jpa.RefServiceType;
import com.accenture.sdp.csm.model.jpa.RefSolutionOfferType;
import com.accenture.sdp.csm.model.jpa.RefSolutionType;
import com.accenture.sdp.csm.model.jpa.RefStatusType;
import com.accenture.sdp.csm.model.jpa.RefTenant;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpDevice;
import com.accenture.sdp.csm.model.jpa.SdpDeviceCounterConfig;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicy;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicyConfig;
import com.accenture.sdp.csm.model.jpa.SdpDiscount;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpOfferGroup;
import com.accenture.sdp.csm.model.jpa.SdpOperator;
import com.accenture.sdp.csm.model.jpa.SdpOperatorRight;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpPackagePrice;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.model.jpa.SdpPartyGroup;
import com.accenture.sdp.csm.model.jpa.SdpPlatform;
import com.accenture.sdp.csm.model.jpa.SdpPriceCatalog;
import com.accenture.sdp.csm.model.jpa.SdpRole;
import com.accenture.sdp.csm.model.jpa.SdpSecretQuestion;
import com.accenture.sdp.csm.model.jpa.SdpServiceTemplate;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariantOperation;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCart;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCartItem;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.model.jpa.SdpSolution;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOfferExternalId;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.model.jpa.SdpSubscriptionDetail;
import com.accenture.sdp.csm.model.jpa.SdpVoucher;
import com.accenture.sdp.csm.model.jpa.SdpVoucherCampaign;

public abstract class BeanConverter {

	public static List<SdpParentPartyDto> convertParentParties(List<SdpParty> parties) throws PropertyNotFoundException {
		ArrayList<SdpParentPartyDto> resp = new ArrayList<SdpParentPartyDto>();
		if (parties != null) {
			for (SdpParty p : parties) {
				resp.add(convertParentParty(p));
			}
		}
		return resp;
	}

	public static SdpParentPartyDto convertParentParty(SdpParty party) throws PropertyNotFoundException {
		if (party == null) {
			return null;
		}
		SdpParentPartyDto resp = new SdpParentPartyDto();
		resp.setChangeStatusById(party.getChangeStatusById());
		resp.setChangeStatusDate(party.getChangeStatusDate());
		resp.setCreatedById(party.getCreatedById());
		resp.setCreatedDate(party.getCreatedDate());
		resp.setDeletedById(party.getDeletedById());
		resp.setDeletedDate(party.getDeletedDate());
		resp.setExternalId(party.getExternalId());
		resp.setPartyProfile(party.getPartyProfile());
		resp.setPartyDescription(party.getPartyDescription());
		resp.setPartyGroups(convertListOfPartyGroupResponseDto(party.getPartyGroups()));
		resp.setPartyId(party.getPartyId());
		resp.setPartyName(party.getPartyName());
		resp.setStatus(StatusIdConverter.getStatusDescription(party.getStatusId()));
		resp.setStatusId(party.getStatusId());
		resp.setUpdatedById(party.getUpdatedById());
		resp.setUpdatedDate(party.getUpdatedDate());

		if (party.getSdpPartyDeviceExt() != null) {
			resp.setBlacklisted(BooleanConverter.getBooleanValue(party.getSdpPartyDeviceExt().getIsBl()));
			resp.setWhitelisted(BooleanConverter.getBooleanValue(party.getSdpPartyDeviceExt().getIsWl()));
		}
		return resp;
	}

	public static List<SdpChildPartyDto> convertChildParties(List<SdpParty> parties) throws PropertyNotFoundException {
		ArrayList<SdpChildPartyDto> resp = new ArrayList<SdpChildPartyDto>();
		if (parties != null) {
			for (SdpParty p : parties) {
				resp.add(convertChildParty(p));
			}
		}
		return resp;
	}

	public static SdpChildPartyDto convertChildParty(SdpParty party) throws PropertyNotFoundException {
		if (party == null) {
			return null;
		}
		SdpChildPartyDto resp = new SdpChildPartyDto();
		resp.setPartyId(party.getPartyId());
		resp.setPartyName(party.getPartyName());
		// FIXME try messo per AVS
		try {
			if (party.getParentParty() != null) {
				resp.setParentPartyId(party.getParentParty().getPartyId());
				resp.setParentPartyName(party.getParentParty().getPartyName());
			}
		} catch (Exception e) {
		}
		resp.setPartyDescription(party.getPartyDescription());
		resp.setPartyTypeDescription(PartyTypeIdConverter.getPartyTypeDescription(party.getRefPartyType()));
		resp.setStatusId(party.getStatusId());
		resp.setStatus(StatusIdConverter.getStatusDescription(party.getStatusId()));
		resp.setPartyGroups(convertListOfPartyGroupResponseDto(party.getPartyGroups()));
		resp.setExternalId(party.getExternalId());
		resp.setPartyProfile(party.getPartyProfile());
		if (party.getSdpPartyData() != null) {
			if (party.getSdpPartyData().getSdpPartySite() != null) {
				resp.setDefaultSiteId(party.getSdpPartyData().getSdpPartySite().getSiteId());
				resp.setDefaultSiteName(party.getSdpPartyData().getSdpPartySite().getSiteName());
			}
			resp.setFirstName(party.getSdpPartyData().getFirstName());
			resp.setLastName(party.getSdpPartyData().getLastName());
			resp.setStreetAddress(party.getSdpPartyData().getAddress());
			resp.setCity(party.getSdpPartyData().getCity());
			resp.setZipCode(party.getSdpPartyData().getZipCode());
			resp.setProvince(party.getSdpPartyData().getProvince());
			resp.setCountry(party.getSdpPartyData().getCountry());
			resp.setGender(party.getSdpPartyData().getGender());
			resp.setBirthDate(party.getSdpPartyData().getBirthDate());
			resp.setBirthProvince(party.getSdpPartyData().getBirthProvince());
			resp.setBirthCountry(party.getSdpPartyData().getBirthCountry());
			resp.setBirthCity(party.getSdpPartyData().getBirthCity());
			resp.setContactTel(party.getSdpPartyData().getContactTel());
			resp.setContactFax(party.getSdpPartyData().getContactFax());
			resp.setEmail(party.getSdpPartyData().getEmail());
			resp.setNote(party.getSdpPartyData().getNote());
		}
		resp.setCreatedById(party.getCreatedById());
		resp.setCreatedDate(party.getCreatedDate());
		resp.setUpdatedById(party.getUpdatedById());
		resp.setUpdatedDate(party.getUpdatedDate());
		resp.setDeletedById(party.getDeletedById());
		resp.setDeletedDate(party.getDeletedDate());
		resp.setChangeStatusById(party.getChangeStatusById());
		resp.setChangeStatusDate(party.getChangeStatusDate());

		if (party.getSdpPartyDeviceExt() != null) {
			resp.setBlacklisted(BooleanConverter.getBooleanValue(party.getSdpPartyDeviceExt().getIsBl()));
			resp.setWhitelisted(BooleanConverter.getBooleanValue(party.getSdpPartyDeviceExt().getIsWl()));
		}
		return resp;
	}

	public static List<SdpPartySiteDto> convertSpdSites(List<SdpSite> sites) throws PropertyNotFoundException {
		ArrayList<SdpPartySiteDto> resp = new ArrayList<SdpPartySiteDto>();
		if (sites != null) {
			for (SdpSite s : sites) {
				resp.add(convertSpdSite(s));
			}
		}
		return resp;
	}

	public static SdpPartySiteDto convertSpdSite(SdpSite site) throws PropertyNotFoundException {
		if (site == null) {
			return null;
		}
		SdpPartySiteDto dto = new SdpPartySiteDto();
		dto.setSiteId(site.getSiteId());
		dto.setSiteName(site.getSiteName());
		dto.setSiteDescription(site.getSiteDescription());
		dto.setStatusId(site.getStatusId());
		dto.setStatus(StatusIdConverter.getStatusDescription(site.getStatusId()));
		if (site.getSdpParty() != null) {
			dto.setPartyId(site.getSdpParty().getPartyId());
		}
		dto.setExternalID(site.getExternalId());
		dto.setSiteProfile(site.getSiteProfile());
		dto.setStreetAddress(site.getAddress());
		dto.setCity(site.getCity());
		dto.setZipCode(site.getZipCode());
		dto.setProvince(site.getProvince());
		dto.setCountry(site.getCountry());
		dto.setCreatedById(site.getCreatedById());
		dto.setCreatedDate(site.getCreatedDate());
		dto.setUpdatedById(site.getUpdatedById());
		dto.setUpdatedDate(site.getUpdatedDate());
		dto.setDeletedById(site.getDeletedById());
		dto.setDeletedDate(site.getDeletedDate());
		dto.setChangeStatusById(site.getChangeStatusById());
		dto.setChangeStatusDate(site.getChangeStatusDate());
		return dto;
	}

	public static SdpAccountResponseDto convertSdpAccount(SdpAccount account) throws PropertyNotFoundException {
		if (account == null) {
			return null;
		}
		SdpAccountResponseDto dto = new SdpAccountResponseDto();
		dto.setAccountDescription(account.getAccountDescription());
		dto.setAccountId(account.getAccountId());
		dto.setAccountName(account.getAccountName());
		dto.setAccountProfile(account.getAccountProfile());
		dto.setDefaulAccount(IsDefaultAccount.DefaultAccount.getDefaultAccountBool(account.getIsDefaultPartyAccount()));
		dto.setExternalId(account.getExternalId());
		dto.setPartyId(account.getSdpParty().getPartyId());
		dto.setPartyName(account.getSdpParty().getPartyName());
		// FIXME try messo per AVS
		try {
			if (account.getSdpParty().getParentParty() != null) {
				dto.setParentPartyName(account.getSdpParty().getParentParty().getPartyName());
			}
		} catch (Exception e) {
		}
		if (account.getSdpPartySite() != null) {
			dto.setSiteId(account.getSdpPartySite().getSiteId());
			dto.setSiteName(account.getSdpPartySite().getSiteName());
		}
		dto.setStatus(StatusIdConverter.getStatusDescription(account.getStatusId()));
		dto.setCreatedById(account.getCreatedById());
		dto.setCreatedDate(account.getCreatedDate());
		dto.setUpdatedById(account.getUpdatedById());
		dto.setUpdatedDate(account.getUpdatedDate());
		dto.setDeletedById(account.getDeletedById());
		dto.setDeletedDate(account.getDeletedDate());
		dto.setChangeStatusById(account.getChangeStatusById());
		dto.setChangeStatusDate(account.getChangeStatusDate());
		return dto;
	}

	public static SdpAccountResponseDto convertSdpAccount(SdpAccount account, String accountType) throws PropertyNotFoundException {
		if (account == null) {
			return null;
		}
		SdpAccountResponseDto dto = convertSdpAccount(account);
		dto.setAccountType(accountType);
		return dto;
	}

	public static List<SdpSecretQuestionDto> convertSecretQuestion(List<SdpSecretQuestion> secretQuestionsModels) {
		List<SdpSecretQuestionDto> secretQuestions = new ArrayList<SdpSecretQuestionDto>();
		if (secretQuestionsModels != null) {
			for (SdpSecretQuestion secretQuestionModel : secretQuestionsModels) {
				SdpSecretQuestionDto sq = new SdpSecretQuestionDto();
				sq.setAnswer(secretQuestionModel.getSecretAnswer());
				sq.setSecretQuestionDescription(secretQuestionModel.getSecretQuestionDescription());
				sq.setSecretQuestionId(secretQuestionModel.getSecretQuestionId());
				sq.setCreatedById(secretQuestionModel.getCreatedById());
				sq.setCreatedDate(secretQuestionModel.getCreatedDate());
				sq.setUpdatedById(secretQuestionModel.getUpdatedById());
				sq.setUpdatedDate(secretQuestionModel.getUpdatedDate());
				secretQuestions.add(sq);
			}
		}
		return secretQuestions;
	}

	public static SdpCredentialDto convertCredentialDto(SdpCredential result) throws PropertyNotFoundException {
		if (result == null) {
			return null;
		}
		SdpCredentialDto resultDto = new SdpCredentialDto();
		resultDto.setChangeStatusById(result.getChangeStatusById());
		resultDto.setChangeStatusDate(result.getChangeStatusDate());
		resultDto.setCreatedById(result.getCreatedById());
		resultDto.setCreatedDate(result.getCreatedDate());
		resultDto.setDeletedById(result.getDeletedById());
		resultDto.setDeletedDate(result.getDeletedDate());
		resultDto.setUpdatedById(result.getUpdatedById());
		resultDto.setUpdatedDate(result.getUpdatedDate());
		resultDto.setExternalId(result.getExternalId());
		if (result.getSdpParty() != null) {
			resultDto.setPartyName(result.getSdpParty().getPartyName());
			resultDto.setPartyId(result.getSdpParty().getPartyId());
			// FIXME try messo per AVS
			try {
				if (result.getSdpParty().getParentParty() != null) {
					resultDto.setParentPartyId(result.getSdpParty().getParentParty().getPartyId());
					resultDto.setParentPartyName(result.getSdpParty().getParentParty().getPartyName());
				}
			} catch (Exception e) {
			}
		}
		resultDto.setRoleName(result.getSdpRole());
		resultDto.setSecretQuestions(convertSecretQuestion(result.getSdpSecretQuestions()));
		resultDto.setStatusID(result.getStatusId());
		resultDto.setStatusName(StatusIdConverter.getStatusDescription(result.getStatusId()));
		resultDto.setUsername(result.getUsername());
		return resultDto;
	}

	public static List<SdpAccountResponseDto> convertSdpAccounts(List<SdpAccount> accounts) throws PropertyNotFoundException {
		List<SdpAccountResponseDto> result = new ArrayList<SdpAccountResponseDto>();
		if (accounts != null) {
			for (SdpAccount acc : accounts) {
				result.add(convertSdpAccount(acc));
			}
		}
		return result;
	}

	public static List<SdpPlatformDto> convertPlatforms(List<SdpPlatform> results) throws PropertyNotFoundException {
		List<SdpPlatformDto> resultDto = new ArrayList<SdpPlatformDto>();
		if (results != null) {
			for (SdpPlatform model : results) {
				resultDto.add(convertPlatform(model));
			}
		}
		return resultDto;
	}

	public static SdpPlatformDto convertPlatform(SdpPlatform model) throws PropertyNotFoundException {
		if (model == null) {
			return null;
		}
		SdpPlatformDto dto = new SdpPlatformDto();
		dto.setChangeStatusById(model.getChangeStatusById());
		dto.setChangeStatusDate(model.getChangeStatusDate());
		dto.setCreatedById(model.getCreatedById());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setDeletedById(model.getDeletedById());
		dto.setDeletedDate(model.getDeletedDate());
		dto.setUpdatedById(model.getUpdatedById());
		dto.setUpdatedDate(model.getUpdatedDate());
		dto.setExternalId(model.getExternalId());
		dto.setPlatformDescription(model.getPlatformDescription());
		dto.setPlatformId(model.getPlatformId());
		dto.setPlatformName(model.getPlatformName());
		dto.setPlatformProfile(model.getPlatformProfile());
		dto.setPlatformStatusId(model.getStatusId());
		dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
		return dto;
	}

	public static SdpServiceVariantDto convertServiceVariant(SdpServiceVariant model) throws PropertyNotFoundException {
		if (model == null) {
			return null;
		}
		SdpServiceVariantDto dto = new SdpServiceVariantDto();
		dto.setChangeStatusById(model.getChangeStatusById());
		dto.setChangeStatusDate(model.getChangeStatusDate());
		dto.setCreatedById(model.getCreatedById());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setDeletedById(model.getDeletedById());
		dto.setDeletedDate(model.getDeletedDate());
		dto.setUpdatedById(model.getUpdatedById());
		dto.setUpdatedDate(model.getUpdatedDate());
		dto.setExternalId(model.getExternalId());
		if (model.getSdpServiceTemplate() != null) {
			dto.setServiceTemplateId(model.getSdpServiceTemplate().getServiceTemplateId());
			dto.setServiceTemplateName(model.getSdpServiceTemplate().getServiceTemplateName());
		}
		dto.setServiceVariantDescription(model.getServiceVariantDescription());
		dto.setServiceVariantId(model.getServiceVariantId());
		dto.setServiceVariantName(model.getServiceVariantName());
		dto.setServiceVariantProfile(model.getServiceVariantProfile());
		dto.setStatusId(model.getStatusId());
		dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
		return dto;
	}

	public static List<SdpServiceVariantDto> convertServiceVariant(List<SdpServiceVariant> models) throws PropertyNotFoundException {
		List<SdpServiceVariantDto> dtos = new ArrayList<SdpServiceVariantDto>();
		if (models != null) {
			for (SdpServiceVariant obj : models) {
				dtos.add(convertServiceVariant(obj));
			}
		}
		return dtos;
	}

	public static List<SdpServiceTemplateDto> convertServiceTemplate(List<SdpServiceTemplate> results) throws PropertyNotFoundException {
		List<SdpServiceTemplateDto> resultDto = new ArrayList<SdpServiceTemplateDto>();
		if (results != null) {
			for (SdpServiceTemplate model : results) {
				resultDto.add(convertServiceTemplate(model));
			}
		}
		return resultDto;
	}

	public static SdpServiceTemplateDto convertServiceTemplate(SdpServiceTemplate model) throws PropertyNotFoundException {
		if (model == null) {
			return null;
		}
		SdpServiceTemplateDto dto = new SdpServiceTemplateDto();
		dto.setChangeStatusById(model.getChangeStatusById());
		dto.setChangeStatusDate(model.getChangeStatusDate());
		dto.setCreatedById(model.getCreatedById());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setDeletedById(model.getDeletedById());
		dto.setDeletedDate(model.getDeletedDate());
		dto.setUpdatedById(model.getUpdatedById());
		dto.setUpdatedDate(model.getUpdatedDate());
		dto.setExternalId(model.getExternalId());
		if (model.getSdpPlatform() != null) {
			dto.setPlatformId(model.getSdpPlatform().getPlatformId());
			dto.setPlatformName(model.getSdpPlatform().getPlatformName());
		}
		dto.setServiceTemplateDescription(model.getServiceTemplateDescription());
		dto.setServiceTemplateId(model.getServiceTemplateId());
		dto.setServiceTemplateName(model.getServiceTemplateName());
		dto.setServiceTemplateProfile(model.getServiceTemplateProfile());
		if (model.getRefServiceType() != null) {
			dto.setServiceTypeId(model.getRefServiceType().getServiceTypeId());
			dto.setServiceTypeName(model.getRefServiceType().getServiceTypeName());
		}
		dto.setStatusId(model.getStatusId());
		dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
		return dto;
	}

	public static List<SdpCredentialDto> convertCredentialDto(List<SdpCredential> results) throws PropertyNotFoundException {
		List<SdpCredentialDto> resultsDto = new ArrayList<SdpCredentialDto>();
		if (results != null) {
			for (SdpCredential obj : results) {
				resultsDto.add(convertCredentialDto(obj));
			}
		}
		return resultsDto;

	}

	public static List<SdpRoleDto> convertRole(List<SdpRole> results) {
		List<SdpRoleDto> resultDto = new ArrayList<SdpRoleDto>();
		if (results != null) {
			for (SdpRole model : results) {
				SdpRoleDto dto = new SdpRoleDto();
				dto.setCreatedById(model.getCreatedById());
				dto.setCreatedDate(model.getCreatedDate());
				dto.setUpdatedById(model.getUpdatedById());
				dto.setUpdatedDate(model.getUpdatedDate());
				dto.setRoleDescription(model.getRoleDescription());
				dto.setRoleName(model.getRoleName());
				resultDto.add(dto);
			}
		}
		return resultDto;

	}

	public static SdpSolutionDto convertSdpSolution(SdpSolution solution) throws PropertyNotFoundException {
		if (solution == null) {
			return null;
		}
		SdpSolutionDto solutionDto = new SdpSolutionDto();
		solutionDto.setChangeStatusById(solution.getChangeStatusById());
		solutionDto.setChangeStatusDate(solution.getChangeStatusDate());
		solutionDto.setCreatedById(solution.getCreatedById());
		solutionDto.setCreatedDate(solution.getCreatedDate());
		solutionDto.setDeletedById(solution.getDeletedById());
		solutionDto.setDeletedDate(solution.getDeletedDate());
		solutionDto.setEndDate(solution.getEndDate());
		solutionDto.setExternalId(solution.getExternalId());
		solutionDto.setSolutionDescription(solution.getSolutionDescription());
		solutionDto.setSolutionName(solution.getSolutionName());
		solutionDto.setStartDate(solution.getStartDate());
		solutionDto.setStatusId(solution.getStatusId());
		solutionDto.setStatusName(StatusIdConverter.getStatusDescription(solution.getStatusId()));
		solutionDto.setUpdatedById(solution.getUpdatedById());
		solutionDto.setUpdatedDate(solution.getUpdatedDate());
		solutionDto.setSolutionId(solution.getSolutionId());
		solutionDto.setSolutionTypeId(solution.getRefSolutionType().getSolutionTypeId());
		solutionDto.setSolutionTypeName(solution.getRefSolutionType().getSolutionTypeName());
		solutionDto.setProfile(solution.getSolutionProfile());
		return solutionDto;
	}

	public static List<SdpSolutionDto> convertSolution(List<SdpSolution> results) throws PropertyNotFoundException {
		List<SdpSolutionDto> resultDto = new ArrayList<SdpSolutionDto>();
		if (results != null) {
			for (SdpSolution model : results) {
				SdpSolutionDto dto = new SdpSolutionDto();
				dto.setChangeStatusById(model.getChangeStatusById());
				dto.setChangeStatusDate(model.getChangeStatusDate());
				dto.setCreatedById(model.getCreatedById());
				dto.setCreatedDate(model.getCreatedDate());
				dto.setDeletedById(model.getDeletedById());
				dto.setDeletedDate(model.getDeletedDate());
				dto.setUpdatedById(model.getUpdatedById());
				dto.setUpdatedDate(model.getUpdatedDate());
				dto.setEndDate(model.getEndDate());
				dto.setExternalId(model.getExternalId());
				dto.setProfile(model.getSolutionProfile());
				dto.setSolutionDescription(model.getSolutionDescription());
				dto.setSolutionId(model.getSolutionId());
				dto.setSolutionName(model.getSolutionName());
				if (model.getRefSolutionType() != null) {
					dto.setSolutionTypeId(model.getRefSolutionType().getSolutionTypeId());
					dto.setSolutionTypeName(model.getRefSolutionType().getSolutionTypeName());
				}
				dto.setStartDate(model.getStartDate());
				dto.setStatusId(model.getStatusId());
				dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
				dto.setPartyGroups(convertListOfPartyGroupResponseDto(model.getSdpPartyGroups()));
				resultDto.add(dto);
			}
		}
		return resultDto;

	}

	public static List<SdpPriceCatalogDto> convertListOfPriceCatalogDto(List<SdpPriceCatalog> priceList) {
		List<SdpPriceCatalogDto> result = new ArrayList<SdpPriceCatalogDto>();
		if (priceList != null) {
			for (SdpPriceCatalog priceCatalog : priceList) {
				SdpPriceCatalogDto dto = new SdpPriceCatalogDto();
				dto.setPriceCatalogId(priceCatalog.getPriceCatalogId());
				if (priceCatalog.getPrice() != null) {
					dto.setPrice(priceCatalog.getPrice());
				}
				dto.setCreatedById(priceCatalog.getCreatedById());
				dto.setCreatedDate(priceCatalog.getCreatedDate());
				dto.setUpdatedById(priceCatalog.getUpdatedById());
				dto.setUpdatedDate(priceCatalog.getUpdatedDate());
				result.add(dto);

			}
		}
		return result;
	}

	public static List<SdpPartyGroupResponseDto> convertListOfPartyGroupResponseDto(List<SdpPartyGroup> partyGroups) {
		List<SdpPartyGroupResponseDto> result = new ArrayList<SdpPartyGroupResponseDto>();
		if (partyGroups != null) {
			for (SdpPartyGroup partyGroup : partyGroups) {
				SdpPartyGroupResponseDto dto = convertPartyGroupResponseDto(partyGroup);
				result.add(dto);
			}
		}
		return result;
	}

	public static SdpPartyGroupResponseDto convertPartyGroupResponseDto(SdpPartyGroup partyGroup) {
		if (partyGroup == null) {
			return null;
		}
		SdpPartyGroupResponseDto dto = new SdpPartyGroupResponseDto();
		dto.setPartyGroupId(partyGroup.getPartyGroupId());
		dto.setPartyGroupName(partyGroup.getPartyGroupName());
		dto.setPartyGroupDescription(partyGroup.getPartyGroupDescription());
		dto.setCreatedById(partyGroup.getCreatedById());
		dto.setCreatedDate(partyGroup.getCreatedDate());
		dto.setUpdatedById(partyGroup.getUpdatedById());
		dto.setUpdatedDate(partyGroup.getUpdatedDate());
		return dto;
	}

	public static RefFrequencyTypeResponseDto convertRefFrequencyTypeResponseDto(RefFrequencyType frequency) {
		if (frequency == null) {
			return null;
		}
		RefFrequencyTypeResponseDto dto = new RefFrequencyTypeResponseDto();
		dto.setFrequencyTypeId(frequency.getFrequencyTypeId());
		dto.setFrequencyTypeName(frequency.getFrequencyTypeName());
		dto.setFrequencyTypeDescription(frequency.getFrequencyTypeDescription());
		dto.setFrequencyDays(frequency.getFrequencyDays());
		dto.setCreatedById(frequency.getCreatedById());
		dto.setCreatedDate(frequency.getCreatedDate());
		dto.setUpdatedById(frequency.getUpdatedById());
		dto.setUpdatedDate(frequency.getUpdatedDate());
		return dto;
	}

	public static SdpPackagePriceDto convertSdpPackagePriceResponseDto(SdpPackagePrice packagePrice) {
		if (packagePrice == null) {
			return null;
		}
		SdpPackagePriceDto dto = new SdpPackagePriceDto();

		dto.setPackagePriceId(packagePrice.getPackagePriceId());
		if (packagePrice.getRefFrequencyType() != null) {
			dto.setFrequencyTypeId(packagePrice.getRefFrequencyType().getFrequencyTypeId());
		}
		if (packagePrice.getRefPriceCatalogRC() != null) {
			dto.setRcPriceCatalogId(packagePrice.getRefPriceCatalogRC().getPriceCatalogId());
		}
		if (packagePrice.getRefPriceCatalogNRC() != null) {
			dto.setNrcPriceCatalogId(packagePrice.getRefPriceCatalogNRC().getPriceCatalogId());
		}
		if (packagePrice.getRefCurrencyType() != null) {
			dto.setCurrencyTypeId(packagePrice.getRefCurrencyType().getCurrencyTypeId());
		}
		dto.setRcFlagProrate(packagePrice.getRcFlagProrate());
		dto.setRcInAdvance(packagePrice.getRcInAdvance());

		dto.setCreatedById(packagePrice.getCreatedById());
		dto.setCreatedDate(packagePrice.getCreatedDate());
		dto.setUpdatedById(packagePrice.getUpdatedById());
		dto.setUpdatedDate(packagePrice.getUpdatedDate());
		return dto;
	}

	public static SdpPriceCatalogDto convertSdpPriceCatalogResponseDto(SdpPriceCatalog priceCatalog) {
		if (priceCatalog == null) {
			return null;
		}
		SdpPriceCatalogDto dto = new SdpPriceCatalogDto();
		dto.setPrice(priceCatalog.getPrice());
		dto.setCreatedById(priceCatalog.getCreatedById());
		dto.setCreatedDate(priceCatalog.getCreatedDate());
		dto.setUpdatedById(priceCatalog.getUpdatedById());
		dto.setUpdatedDate(priceCatalog.getUpdatedDate());
		return dto;
	}

	public static RefCurrencyTypeResponseDto convertRefCurrencyTypeResponseDto(RefCurrencyType currency) {
		if (currency == null) {
			return null;
		}
		RefCurrencyTypeResponseDto dto = new RefCurrencyTypeResponseDto();
		dto.setCurrencyTypeId(currency.getCurrencyTypeId());
		dto.setCurrencyTypeName(currency.getCurrencyTypeName());
		dto.setCreatedById(currency.getCreatedById());
		dto.setCreatedDate(currency.getCreatedDate());
		dto.setUpdatedById(currency.getUpdatedById());
		dto.setUpdatedDate(currency.getUpdatedDate());
		return dto;
	}

	public static RefFrequencyTypeResponseDto convertRefFrequencTypeResponseDto(RefFrequencyType frequency) {
		if (frequency == null) {
			return null;
		}
		RefFrequencyTypeResponseDto dto = new RefFrequencyTypeResponseDto();
		dto.setFrequencyTypeName(frequency.getFrequencyTypeName());
		dto.setFrequencyTypeDescription(frequency.getFrequencyTypeDescription());
		dto.setFrequencyDays(frequency.getFrequencyDays());
		dto.setCreatedById(frequency.getCreatedById());
		dto.setCreatedDate(frequency.getCreatedDate());
		dto.setUpdatedById(frequency.getUpdatedById());
		dto.setUpdatedDate(frequency.getUpdatedDate());
		return dto;
	}

	public static RefSolutionTypeResponseDto convertRefSolutionTypeResponseDto(RefSolutionType type) {
		if (type == null) {
			return null;
		}
		RefSolutionTypeResponseDto dto = new RefSolutionTypeResponseDto();
		dto.setSolutionTypeId(type.getSolutionTypeId());
		dto.setSolutionTypeName(type.getSolutionTypeName());
		dto.setSolutionTypeDescription(type.getSolutionTypeDescription());
		dto.setCreatedById(type.getCreatedById());
		dto.setCreatedDate(type.getCreatedDate());
		dto.setUpdatedById(type.getUpdatedById());
		dto.setUpdatedDate(type.getUpdatedDate());
		return dto;
	}

	public static List<RefSolutionTypeResponseDto> convertRefSolutionTypeResponseDto(List<RefSolutionType> types) {
		List<RefSolutionTypeResponseDto> result = new ArrayList<RefSolutionTypeResponseDto>();
		if (types != null) {
			for (RefSolutionType type : types) {
				result.add(convertRefSolutionTypeResponseDto(type));
			}
		}
		return result;
	}

	public static RefServiceTypeResponseDto convertRefServiceTypeResponseDto(RefServiceType type) {
		if (type == null) {
			return null;
		}
		RefServiceTypeResponseDto dto = new RefServiceTypeResponseDto();
		dto.setServiceTypeId(type.getServiceTypeId());
		dto.setServiceTypeName(type.getServiceTypeName());
		dto.setServiceTypeDescription(type.getServiceTypeDescription());
		dto.setCreatedById(type.getCreatedById());
		dto.setCreatedDate(type.getCreatedDate());
		dto.setUpdatedById(type.getUpdatedById());
		dto.setUpdatedDate(type.getUpdatedDate());
		return dto;
	}

	public static List<RefServiceTypeResponseDto> convertRefServiceTypeResponseDto(List<RefServiceType> types) {
		List<RefServiceTypeResponseDto> result = new ArrayList<RefServiceTypeResponseDto>();
		if (types != null) {
			for (RefServiceType type : types) {
				result.add(convertRefServiceTypeResponseDto(type));
			}
		}
		return result;
	}

	public static List<RefFrequencyTypeResponseDto> convertListOfRefFrequencyTypeResponseDto(List<RefFrequencyType> frequencies) {
		List<RefFrequencyTypeResponseDto> result = new ArrayList<RefFrequencyTypeResponseDto>();
		if (frequencies != null) {
			for (RefFrequencyType frequency : frequencies) {
				result.add(convertRefFrequencyTypeResponseDto(frequency));
			}
		}
		return result;
	}

	public static List<RefCurrencyTypeResponseDto> convertListOfRefCurrencyTypeResponseDto(List<RefCurrencyType> currencies) {
		List<RefCurrencyTypeResponseDto> result = new ArrayList<RefCurrencyTypeResponseDto>();
		if (currencies != null) {
			for (RefCurrencyType currency : currencies) {
				result.add(convertRefCurrencyTypeResponseDto(currency));
			}
		}
		return result;
	}

	public static SdpSolutionOfferDto converSdpSolutionOffer(SdpSolutionOffer solutionOffer) throws PropertyNotFoundException {
		if (solutionOffer == null) {
			return null;
		}
		SdpSolutionOfferDto solutionOfferDto = new SdpSolutionOfferDto();
		solutionOfferDto.setChangeStatusById(solutionOffer.getChangeStatusById());
		solutionOfferDto.setChangeStatusDate(solutionOffer.getChangeStatusDate());
		solutionOfferDto.setCreatedById(solutionOffer.getCreatedById());
		solutionOfferDto.setCreatedDate(solutionOffer.getCreatedDate());
		solutionOfferDto.setDeletedById(solutionOffer.getDeletedById());
		solutionOfferDto.setDeletedDate(solutionOffer.getDeletedDate());
		solutionOfferDto.setEndDate(solutionOffer.getEndDate());
		// EXTERNAL ID PER STAR
		if (solutionOffer.getExternalIds() != null) {
			List<ExternalIdDto> externalIdList = new ArrayList<ExternalIdDto>();
			solutionOfferDto.setExternalIdList(externalIdList);
			for (SdpSolutionOfferExternalId i : solutionOffer.getExternalIds()) {
				ExternalIdDto extDto = new ExternalIdDto();
				extDto.setExternalPlatformName(i.getId().getExternalPlatformName());
				extDto.setExternalId(i.getExternalId());
				externalIdList.add(extDto);
			}
		}
		if (solutionOffer.getSdpSolution() != null) {
			solutionOfferDto.setSolutionId(solutionOffer.getSdpSolution().getSolutionId());
			solutionOfferDto.setSolutionName(solutionOffer.getSdpSolution().getSolutionName());
		}
		if (solutionOffer.getParentSolutionOffer() != null) {
			solutionOfferDto.setParentSolutionOfferId(solutionOffer.getParentSolutionOffer().getSolutionOfferId());
			// child solutionOffer returns same solution as its parent
			solutionOfferDto.setSolutionId(solutionOffer.getParentSolutionOffer().getSdpSolution().getSolutionId());
			solutionOfferDto.setSolutionName(solutionOffer.getParentSolutionOffer().getSdpSolution().getSolutionName());
		}
		solutionOfferDto.setSolutionOfferId(solutionOffer.getSolutionOfferId());
		solutionOfferDto.setSolutionOfferDescription(solutionOffer.getSolutionOfferDescription());
		solutionOfferDto.setSolutionOfferName(solutionOffer.getSolutionOfferName());
		solutionOfferDto.setStartDate(solutionOffer.getStartDate());
		solutionOfferDto.setStatusId(solutionOffer.getStatusId());
		solutionOfferDto.setStatusName(StatusIdConverter.getStatusDescription(solutionOffer.getStatusId()));
		solutionOfferDto.setUpdatedById(solutionOffer.getUpdatedById());
		solutionOfferDto.setUpdatedDate(solutionOffer.getUpdatedDate());
		solutionOfferDto.setProfile(solutionOffer.getSolutionOfferProfile());
		solutionOfferDto.setPartyGroups(convertListOfPartyGroupResponseDto(solutionOffer.getPartyGroups()));
		solutionOfferDto.setSolutionOfferType(solutionOffer.getSolutionOfferType().getSolutionOfferTypeName());
		solutionOfferDto.setDuration(solutionOffer.getDuration());
		return solutionOfferDto;
	}

	public static List<SdpSolutionOfferDto> convertSdpSolutionOffers(List<SdpSolutionOffer> solutionOffers) throws PropertyNotFoundException {
		List<SdpSolutionOfferDto> results = new ArrayList<SdpSolutionOfferDto>();
		if (solutionOffers != null) {
			for (SdpSolutionOffer solutionOffer : solutionOffers) {
				results.add(converSdpSolutionOffer(solutionOffer));
			}
		}
		return results;
	}

	public static SdpOfferDto converSdpOffer(SdpOffer offer) throws PropertyNotFoundException {
		if (offer == null) {
			return null;
		}
		SdpOfferDto offerDto = new SdpOfferDto();
		offerDto.setChangeStatusById(offer.getChangeStatusById());
		offerDto.setChangeStatusDate(offer.getChangeStatusDate());
		offerDto.setCreatedById(offer.getCreatedById());
		offerDto.setCreatedDate(offer.getCreatedDate());
		offerDto.setDeletedById(offer.getDeletedById());
		offerDto.setDeletedDate(offer.getDeletedDate());
		offerDto.setExternalId(offer.getExternalId());
		offerDto.setOfferDescription(offer.getOfferDescription());
		offerDto.setOfferId(offer.getOfferId());
		offerDto.setOfferName(offer.getOfferName());
		offerDto.setOfferProfile(offer.getOfferProfile());
		if (offer.getSdpServiceVariant() != null) {
			offerDto.setServiceVariantId(offer.getSdpServiceVariant().getServiceVariantId());
			offerDto.setServiceVariantName(offer.getSdpServiceVariant().getServiceVariantName());
		}
		offerDto.setStatusId(offer.getStatusId());
		offerDto.setStatusName(StatusIdConverter.getStatusDescription(offer.getStatusId()));
		offerDto.setUpdatedById(offer.getUpdatedById());
		offerDto.setUpdatedDate(offer.getUpdatedDate());
		return offerDto;
	}

	public static List<SdpOfferDto> converSdpOffers(List<SdpOffer> offers) throws PropertyNotFoundException {
		ArrayList<SdpOfferDto> results = new ArrayList<SdpOfferDto>();
		if (offers != null) {
			for (SdpOffer offer : offers) {
				results.add(converSdpOffer(offer));
			}
		}
		return results;
	}

	public static SdpOfferDto converSdpOfferUpToPlatform(SdpOffer offer) throws PropertyNotFoundException {
		if (offer == null) {
			return null;
		}
		SdpOfferDto offerDto = new SdpOfferDto();
		offerDto.setChangeStatusById(offer.getChangeStatusById());
		offerDto.setChangeStatusDate(offer.getChangeStatusDate());
		offerDto.setCreatedById(offer.getCreatedById());
		offerDto.setCreatedDate(offer.getCreatedDate());
		offerDto.setDeletedById(offer.getDeletedById());
		offerDto.setDeletedDate(offer.getDeletedDate());
		offerDto.setExternalId(offer.getExternalId());
		offerDto.setOfferDescription(offer.getOfferDescription());
		offerDto.setOfferId(offer.getOfferId());
		offerDto.setOfferName(offer.getOfferName());
		offerDto.setOfferProfile(offer.getOfferProfile());

		SdpServiceVariant variant = offer.getSdpServiceVariant();
		if (variant != null) {
			offerDto.setServiceVariantId(variant.getServiceVariantId());
			offerDto.setServiceVariantName(variant.getServiceVariantName());
			SdpServiceTemplate template = variant.getSdpServiceTemplate();
			if (template != null) {
				offerDto.setServiceTemplateId(template.getServiceTemplateId());
				offerDto.setServiceTemplateName(template.getServiceTemplateName());
				SdpPlatform platform = template.getSdpPlatform();
				if (platform != null) {
					offerDto.setPlatformId(platform.getPlatformId());
					offerDto.setPlatformName(platform.getPlatformName());
				}
			}
		}
		offerDto.setStatusId(offer.getStatusId());
		offerDto.setStatusName(StatusIdConverter.getStatusDescription(offer.getStatusId()));
		offerDto.setUpdatedById(offer.getUpdatedById());
		offerDto.setUpdatedDate(offer.getUpdatedDate());
		return offerDto;
	}

	public static List<SdpOfferDto> converSdpOffersUpToPlatform(List<SdpOffer> offers) throws PropertyNotFoundException {
		ArrayList<SdpOfferDto> results = new ArrayList<SdpOfferDto>();
		if (offers != null) {
			for (SdpOffer offer : offers) {
				results.add(converSdpOfferUpToPlatform(offer));
			}
		}
		return results;
	}

	public static SdpSubscriptionResponseDto convertSdpSubscriptionResponseDto(SdpSubscription subscription) throws PropertyNotFoundException {
		if (subscription == null) {
			return null;
		}
		SdpSubscriptionResponseDto dto = new SdpSubscriptionResponseDto();
		dto.setSubscriptionId(subscription.getSubscriptionId());
		dto.setStatusId(subscription.getStatusId());
		dto.setStatusName(StatusIdConverter.getStatusDescription(subscription.getStatusId()));
		// Set party
		if (subscription.getSdpParty() != null) {
			SdpParty party = subscription.getSdpParty();
			dto.setPartyId(subscription.getSdpParty().getPartyId());
			dto.setPartyName(subscription.getSdpParty().getPartyName());
			dto.setPartyTypeId(party.getRefPartyType());
			dto.setPartyTypeName(PartyTypeIdConverter.getPartyTypeDescription(party.getRefPartyType()));
		}
		// set solution offer
		if (subscription.getSdpSolutionOffer() != null) {
			dto.setSolutionOfferId(subscription.getSdpSolutionOffer().getSolutionOfferId());
			dto.setSolutionOfferName(subscription.getSdpSolutionOffer().getSolutionOfferName());
		}
		if (subscription.getParentSubscription() != null) {
			dto.setParentSubscriptionId(subscription.getParentSubscription().getSubscriptionId());
		}
		if (subscription.getSdpCredential() != null) {
			dto.setUserName(subscription.getSdpCredential().getUsername());
		}
		dto.setRoleName(subscription.getSdpRole());
		// set owner
		if (subscription.getSdpAccountOwner() != null) {
			dto.setOwnerId(subscription.getSdpAccountOwner().getAccountId());
			dto.setOwnerAccountName(subscription.getSdpAccountOwner().getAccountName());
		}
		// set payee
		if (subscription.getSdpAccountPayee() != null) {
			dto.setPayeeId(subscription.getSdpAccountPayee().getAccountId());
			dto.setPayeeAccountName(subscription.getSdpAccountPayee().getAccountName());
		}
		if (subscription.getSdpPartySite() != null) {
			dto.setSiteId(subscription.getSdpPartySite().getSiteId());
			dto.setSiteName(subscription.getSdpPartySite().getSiteName());
		}
		dto.setExternalId(subscription.getExternalId());
		dto.setStartDate(subscription.getStartDate());
		dto.setEndDate(subscription.getEndDate());

		dto.setCreatedById(subscription.getCreatedById());
		dto.setCreatedDate(subscription.getCreatedDate());
		dto.setDeletedById(subscription.getDeletedById());
		dto.setDeletedDate(subscription.getDeletedDate());
		dto.setUpdatedById(subscription.getUpdatedById());
		dto.setUpdatedDate(subscription.getUpdatedDate());
		dto.setChangeStatusById(subscription.getChangeStatusById());
		dto.setChangeStatusDate(subscription.getChangeStatusDate());
		return dto;
	}

	public static List<SdpSubscriptionResponseDto> convertListOfSdpSubscriptionResponseDto(List<SdpSubscription> subscriptions)
			throws PropertyNotFoundException {
		List<SdpSubscriptionResponseDto> result = new ArrayList<SdpSubscriptionResponseDto>();
		if (subscriptions != null) {
			for (SdpSubscription subscription : subscriptions) {
				result.add(convertSdpSubscriptionResponseDto(subscription));
			}
		}
		return result;
	}

	public static SdpSubscriptionDetailResponseDto convertSdpSubscriptionDetailResponseDto(SdpSubscriptionDetail subscriptionDetail)
			throws PropertyNotFoundException {
		if (subscriptionDetail == null) {
			return null;
		}
		SdpSubscriptionDetailResponseDto dto = new SdpSubscriptionDetailResponseDto();
		// non leggibili direttamente perche' fanno parte della PK
		dto.setSubscriptionId(subscriptionDetail.getSdpSubscription().getSubscriptionId());
		dto.setPackageId(subscriptionDetail.getSdpPackage().getPackageId());
		//
		dto.setStatusId(subscriptionDetail.getStatusId());
		dto.setStatusName(StatusIdConverter.getStatusDescription(subscriptionDetail.getStatusId()));
		dto.setSubscriptionOfferProfile(subscriptionDetail.getSubscriptionOfferProfile());
		dto.setDetailExternalId(subscriptionDetail.getExternalId());
		dto.setCreatedById(subscriptionDetail.getCreatedById());
		dto.setCreatedDate(subscriptionDetail.getCreatedDate());
		dto.setDeletedById(subscriptionDetail.getDeletedById());
		dto.setDeletedDate(subscriptionDetail.getDeletedDate());
		dto.setUpdatedById(subscriptionDetail.getUpdatedById());
		dto.setUpdatedDate(subscriptionDetail.getUpdatedDate());
		dto.setChangeStatusById(subscriptionDetail.getChangeStatusById());
		dto.setChangeStatusDate(subscriptionDetail.getChangeStatusDate());
		return dto;
	}

	public static List<SdpSubscriptionDetailResponseDto> convertListOfSdpSubscriptionDetailResponseDto(List<SdpSubscriptionDetail> subscriptionDetailList)
			throws PropertyNotFoundException {
		List<SdpSubscriptionDetailResponseDto> result = new ArrayList<SdpSubscriptionDetailResponseDto>();
		if (subscriptionDetailList != null) {
			for (SdpSubscriptionDetail subscriptionDetail : subscriptionDetailList) {
				result.add(convertSdpSubscriptionDetailResponseDto(subscriptionDetail));
			}
		}
		return result;
	}

	// this method adds offer informations to the dto. It's a separate method
	// for perfomance reasons
	public static List<SdpSubscriptionDetailResponseDto> convertListOfSdpSubscriptionDetailResponseWithOfferDto(
			List<SdpSubscriptionDetail> subscriptionDetailList) throws PropertyNotFoundException {
		List<SdpSubscriptionDetailResponseDto> result = new ArrayList<SdpSubscriptionDetailResponseDto>();
		if (subscriptionDetailList != null) {
			for (SdpSubscriptionDetail subscriptionDetail : subscriptionDetailList) {
				SdpSubscriptionDetailResponseDto dto = convertSdpSubscriptionDetailResponseDto(subscriptionDetail);
				if (dto != null && subscriptionDetail.getSdpPackage() != null && subscriptionDetail.getSdpPackage().getSdpOffer() != null) {
					dto.setOfferId(subscriptionDetail.getSdpPackage().getSdpOffer().getOfferId());
					dto.setOfferName(subscriptionDetail.getSdpPackage().getSdpOffer().getOfferName());
				}
				result.add(dto);
			}
		}
		return result;
	}

	public static SdpPackageDto convertSdpPackageResponseDto(SdpPackage sdpPackage) throws PropertyNotFoundException {
		if (sdpPackage == null) {
			return null;
		}
		SdpPackageDto dto = new SdpPackageDto();
		dto.setPackageId(sdpPackage.getPackageId());
		if (sdpPackage.getBasePackage() != null) {
			dto.setBasePackageId(sdpPackage.getBasePackage().getPackageId());
			if (sdpPackage.getBasePackage().getSdpOffer() != null) {
				dto.setBaseOfferName(sdpPackage.getBasePackage().getSdpOffer().getOfferName());
			}
		}
		if (sdpPackage.getSdpSolutionOffer() != null) {
			dto.setSolutionOfferId(sdpPackage.getSdpSolutionOffer().getSolutionOfferId());
		}
		if (sdpPackage.getSdpOffer() != null) {
			dto.setOfferId(sdpPackage.getSdpOffer().getOfferId());
			dto.setOfferName(sdpPackage.getSdpOffer().getOfferName());
		}
		if (sdpPackage.getSdpOfferGroup() != null) {
			dto.setOfferGroupId(sdpPackage.getSdpOfferGroup().getGroupId());
			dto.setOfferGroupName(sdpPackage.getSdpOfferGroup().getGroupName());
		}
		if (sdpPackage.getSdpPackagePrice() != null) {
			dto.setPackagePriceId(sdpPackage.getSdpPackagePrice().getPackagePriceId());
			SdpPackagePrice priceInfo = sdpPackage.getSdpPackagePrice();
			dto.setProrate(priceInfo.getRcFlagProrate());
			dto.setInAdvance(priceInfo.getRcInAdvance());
			dto.setNrcPrice(priceInfo.getRefPriceCatalogNRC().getPrice());
			dto.setRcPrice(priceInfo.getRefPriceCatalogRC().getPrice());
			dto.setCurrencyPriceName(priceInfo.getRefCurrencyType().getCurrencyTypeName());
			dto.setFrequencyPriceName(priceInfo.getRefFrequencyType().getFrequencyTypeName());

			dto.setNrcPriceId(priceInfo.getRefPriceCatalogNRC().getPriceCatalogId());
			dto.setRcPriceId(priceInfo.getRefPriceCatalogRC().getPriceCatalogId());
			dto.setCurrencyTypeId(priceInfo.getRefCurrencyType().getCurrencyTypeId());
			dto.setFrequencyTypeId(priceInfo.getRefFrequencyType().getFrequencyTypeId());
			dto.setFrequencyDays(priceInfo.getRefFrequencyType().getFrequencyDays());
		}
		dto.setStatusId(sdpPackage.getStatusId());
		dto.setStatusName(StatusIdConverter.getStatusDescription(sdpPackage.getStatusId()));
		dto.setExternalId(sdpPackage.getExternalId());
		dto.setIsMandatory(IsMandatory.Mandatory.getMandatoryString(sdpPackage.getIsMandatory()));
		dto.setProfile(sdpPackage.getPackageProfile());
		dto.setCreatedById(sdpPackage.getCreatedById());
		dto.setCreatedDate(sdpPackage.getCreatedDate());
		dto.setDeletedById(sdpPackage.getDeletedById());
		dto.setDeletedDate(sdpPackage.getDeletedDate());
		dto.setUpdatedById(sdpPackage.getUpdatedById());
		dto.setUpdatedDate(sdpPackage.getUpdatedDate());
		dto.setChangeStatusById(sdpPackage.getChangeStatusById());
		dto.setChangeStatusDate(sdpPackage.getChangeStatusDate());
		return dto;
	}

	public static List<SdpPackageDto> convertListOfSdpPackageResponseDto(List<SdpPackage> sdpPackagelList) throws PropertyNotFoundException {
		List<SdpPackageDto> result = new ArrayList<SdpPackageDto>();
		if (sdpPackagelList != null) {
			for (SdpPackage sdpPackage : sdpPackagelList) {
				SdpPackageDto dto = convertSdpPackageResponseDto(sdpPackage);
				result.add(dto);
			}
		}
		return result;
	}

	public static SdpPackageDto convertSdpPackageResponseDto(SdpPackage sdpPackage, SdpDiscount discount) throws PropertyNotFoundException {
		SdpPackageDto dto = convertSdpPackageResponseDto(sdpPackage);
		dto.setDiscountId(discount.getId());
		dto.setDiscountAbsNrc(discount.getDiscountAbsNRC());
		dto.setDiscountAbsRc(discount.getDiscountAbsRC());
		dto.setDiscountPercNrc(discount.getDiscountPercNRC());
		dto.setDiscountPercRc(discount.getDiscountPercRC());
		return dto;
	}

	public static List<?> convertListOfDiscountedSdpPackageResponseDto(List<SdpPackage> packages, List<SdpDiscount> discounts) throws PropertyNotFoundException {
		List<SdpPackageDto> result = new ArrayList<SdpPackageDto>();
		if (packages != null) {
			for (SdpPackage sdpPackage : packages) {
				SdpDiscount discount = null;
				for (SdpDiscount d : discounts) {
					if (d.getSdpPackage().getPackageId().equals(sdpPackage.getPackageId())) {
						discount = d;
						break;
					}
				}
				if (discount != null) {
					result.add(convertSdpPackageResponseDto(sdpPackage, discount));
				} else {
					result.add(convertSdpPackageResponseDto(sdpPackage));
				}
			}
		}
		return result;
	}

	public static SdpOfferGroupDto convertSdpOfferGroupResponseDto(SdpOfferGroup sdpPackageGroup) {
		if (sdpPackageGroup == null) {
			return null;
		}
		SdpOfferGroupDto dto = new SdpOfferGroupDto();
		dto.setGroupId(sdpPackageGroup.getGroupId());
		dto.setGroupName(sdpPackageGroup.getGroupName());
		if (sdpPackageGroup.getSdpSolutionOffer() != null) {
			dto.setSolutionOfferId(sdpPackageGroup.getSdpSolutionOffer().getSolutionOfferId());
		}
		dto.setCreatedById(sdpPackageGroup.getCreatedById());
		dto.setCreatedDate(sdpPackageGroup.getCreatedDate());
		dto.setUpdatedById(sdpPackageGroup.getUpdatedById());
		dto.setUpdatedDate(sdpPackageGroup.getUpdatedDate());
		return dto;
	}

	public static List<SdpOfferGroupDto> convertListOfSdpOfferGroupResponseDto(List<SdpOfferGroup> sdpOfferGroupList) {
		List<SdpOfferGroupDto> result = new ArrayList<SdpOfferGroupDto>();
		if (sdpOfferGroupList != null) {
			for (SdpOfferGroup group : sdpOfferGroupList) {
				SdpOfferGroupDto dto = convertSdpOfferGroupResponseDto(group);
				result.add(dto);
			}
		}
		return result;
	}

	public static SdpSolutionOfferDetailDto convertSolutionOfferDetail(SdpPackage pacage, SdpPackagePrice packagePrice) {
		if (pacage == null || packagePrice == null) {
			return null;
		}
		SdpSolutionOfferDetailDto result = new SdpSolutionOfferDetailDto();
		result.setPackageId(pacage.getPackageId());
		result.setPackagePriceId(packagePrice.getPackagePriceId());
		return result;
	}

	public static SdpServiceVariantOperationDto convertSdpServiceVariantOperation(SdpServiceVariantOperation sdpServiceVariantOperation) {
		if (sdpServiceVariantOperation == null) {
			return null;
		}
		SdpServiceVariantOperationDto dto = new SdpServiceVariantOperationDto();
		dto.setServiceVariantId(sdpServiceVariantOperation.getServiceVariantId());
		if (sdpServiceVariantOperation.getSdpServiceVariant() != null && sdpServiceVariantOperation.getSdpServiceVariant().getSdpServiceTemplate() != null) {
			SdpServiceTemplate template = sdpServiceVariantOperation.getSdpServiceVariant().getSdpServiceTemplate();
			dto.setServiceTemplateId(template.getServiceTemplateId());
			dto.setServiceTemplateName(template.getServiceTemplateName());
			dto.setServiceVariantName(sdpServiceVariantOperation.getSdpServiceVariant().getServiceVariantName());

		}
		dto.setMethodName(sdpServiceVariantOperation.getMethodName());
		dto.setInputParameters(sdpServiceVariantOperation.getInputParameters());
		dto.setInputXslt(sdpServiceVariantOperation.getInputXslt());
		dto.setOutputXslt(sdpServiceVariantOperation.getOutputXslt());
		dto.setUddiKey(sdpServiceVariantOperation.getUddiKey());
		dto.setOperationType(sdpServiceVariantOperation.getOperationType());
		dto.setCreatedById(sdpServiceVariantOperation.getCreatedById());
		dto.setCreatedDate(sdpServiceVariantOperation.getCreatedDate());
		dto.setUpdatedById(sdpServiceVariantOperation.getUpdatedById());
		dto.setUpdatedDate(sdpServiceVariantOperation.getUpdatedDate());
		return dto;
	}

	public static List<SdpServiceVariantOperationDto> convertListOfSdpServiceVariantOperationDto(List<SdpServiceVariantOperation> sdpServiceVariantOperationList) {
		List<SdpServiceVariantOperationDto> result = new ArrayList<SdpServiceVariantOperationDto>();
		if (sdpServiceVariantOperationList != null) {
			for (SdpServiceVariantOperation sdpServiceVariantOperation : sdpServiceVariantOperationList) {
				SdpServiceVariantOperationDto dto = convertSdpServiceVariantOperation(sdpServiceVariantOperation);
				result.add(dto);
			}
		}
		return result;
	}

	public static List<SdpOperatorResponseDto> convertOperators(List<SdpOperator> results) throws PropertyNotFoundException {
		List<SdpOperatorResponseDto> resultDto = new ArrayList<SdpOperatorResponseDto>();
		if (results != null) {
			for (SdpOperator model : results) {
				resultDto.add(convertOperator(model));
			}
		}
		return resultDto;
	}

	public static SdpOperatorResponseDto convertOperator(SdpOperator model) throws PropertyNotFoundException {
		if (model == null) {
			return null;
		}
		SdpOperatorResponseDto dto = new SdpOperatorResponseDto();
		dto.setChangeStatusById(model.getChangeStatusById());
		dto.setChangeStatusDate(model.getChangeStatusDate());
		dto.setCreatedById(model.getCreatedById());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setUpdatedById(model.getUpdatedById());
		dto.setUpdatedDate(model.getUpdatedDate());
		dto.setUsername(model.getUsername());
		dto.setFirstName(model.getFirstName());
		dto.setLastName(model.getLastName());
		dto.setEmail(model.getEmail());
		if (model.getStatusId() != null) {
			dto.setStatusId(model.getStatusId());
			dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
		}
		if (model.getTenants() != null) {
			dto.setTenants(convertTenants(model.getTenants()));
		}
		return dto;
	}

	public static List<SdpTenantResponseDto> convertTenants(List<RefTenant> results) throws PropertyNotFoundException {
		List<SdpTenantResponseDto> resultDto = new ArrayList<SdpTenantResponseDto>();
		if (results != null) {
			for (RefTenant model : results) {
				resultDto.add(convertTenant(model));
			}
		}
		return resultDto;
	}

	public static SdpTenantResponseDto convertTenant(RefTenant model) throws PropertyNotFoundException {
		if (model == null) {
			return null;
		}
		SdpTenantResponseDto dto = new SdpTenantResponseDto();
		dto.setChangeStatusById(model.getChangeStatusById());
		dto.setChangeStatusDate(model.getChangeStatusDate());
		dto.setCreatedById(model.getCreatedById());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setUpdatedById(model.getUpdatedById());
		dto.setUpdatedDate(model.getUpdatedDate());
		dto.setTenantDescription(model.getDescription());
		dto.setTenantName(model.getName());
		dto.setStatusId(model.getStatusId());
		if (model.getStatusId() != null) {
			dto.setStatusId(model.getStatusId());
			dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
		}
		return dto;
	}

	public static SdpPartyResponseDto convertParty(SdpParty model) throws PropertyNotFoundException {
		if (model == null) {
			return null;
		}
		SdpPartyResponseDto dto = new SdpPartyResponseDto();
		dto.setPartyId(model.getPartyId());
		if (model.getRefPartyType() != null) {
			dto.setPartyTypeId(model.getRefPartyType());
			dto.setPartyTypeName(PartyTypeIdConverter.getPartyTypeDescription(model.getRefPartyType()));
		}
		dto.setPartyName(model.getPartyName());
		dto.setPartyDescription(model.getPartyDescription());
		// FIXME try messo per AVS
		try {
			if (model.getParentParty() != null) {
				dto.setParentPartyId(model.getParentParty().getPartyId());
			}
		} catch (Exception e) {
		}
		if (model.getStatusId() != null) {
			dto.setStatusId(model.getStatusId());
			dto.setStatusName(StatusIdConverter.getStatusDescription(model.getStatusId()));
		}
		dto.setExternalId(model.getExternalId());
		dto.setPartyProfile(model.getPartyProfile());
		if (model.getSdpPartyData() != null) {
			dto.setFirstName(model.getSdpPartyData().getFirstName());
			dto.setLastName(model.getSdpPartyData().getLastName());
			if (model.getSdpPartyData().getSdpPartySite() != null) {
				dto.setUserSiteId(model.getSdpPartyData().getSdpPartySite().getSiteId());
			}
			dto.setStreetAddress(model.getSdpPartyData().getAddress());
			dto.setCity(model.getSdpPartyData().getCity());
			dto.setZipCode(model.getSdpPartyData().getZipCode());
			dto.setProvince(model.getSdpPartyData().getProvince());
			dto.setCountry(model.getSdpPartyData().getCountry());
			dto.setGender(model.getSdpPartyData().getGender());
			dto.setBirthDate(model.getSdpPartyData().getBirthDate());
			dto.setBirthProvince(model.getSdpPartyData().getBirthProvince());
			dto.setBirthCountry(model.getSdpPartyData().getBirthCountry());
			dto.setBirthCity(model.getSdpPartyData().getBirthCity());
			dto.setContactTel(model.getSdpPartyData().getContactTel());
			dto.setContactFax(model.getSdpPartyData().getContactFax());
			dto.setEmail(model.getSdpPartyData().getEmail());
			dto.setNote(model.getSdpPartyData().getNote());
		}
		dto.setCreatedById(model.getCreatedById());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setUpdatedById(model.getUpdatedById());
		dto.setUpdatedDate(model.getUpdatedDate());
		dto.setDeletedById(model.getDeletedById());
		dto.setDeletedDate(model.getDeletedDate());
		dto.setChangeStatusById(model.getChangeStatusById());
		dto.setChangeStatusDate(model.getChangeStatusDate());
		dto.setPartyGroups(convertListOfPartyGroupResponseDto(model.getPartyGroups()));

		if (model.getSdpPartyDeviceExt() != null) {
			dto.setBlacklisted(BooleanConverter.getBooleanValue(model.getSdpPartyDeviceExt().getIsBl()));
			dto.setWhitelisted(BooleanConverter.getBooleanValue(model.getSdpPartyDeviceExt().getIsWl()));
		}

		return dto;
	}

	public static List<SdpEndpointInfoDto> convertListOfSdpEndpointInfoDto(List<SdpServiceVariantOperation> results) {
		List<SdpEndpointInfoDto> resultDto = new ArrayList<SdpEndpointInfoDto>();
		if (results != null) {
			for (SdpServiceVariantOperation model : results) {
				resultDto.add(convertEndpointInfo(model));
			}
		}
		return resultDto;
	}

	public static SdpEndpointInfoDto convertEndpointInfo(SdpServiceVariantOperation serviceVariantOperation) {
		if (serviceVariantOperation == null) {
			return null;
		}
		SdpEndpointInfoDto dto = new SdpEndpointInfoDto();
		if (serviceVariantOperation.getSdpServiceVariant() != null && serviceVariantOperation.getSdpServiceVariant().getSdpServiceTemplate() != null) {
			dto.setPlatformName(serviceVariantOperation.getSdpServiceVariant().getSdpServiceTemplate().getSdpPlatform().getPlatformName());
			dto.setServiceName(serviceVariantOperation.getSdpServiceVariant().getServiceVariantName());
			dto.setOperationName(serviceVariantOperation.getMethodName());
		}
		return dto;
	}

	public static List<RefStatusTypeResponseDto> convertListOfStatus(List<RefStatusType> results) {
		List<RefStatusTypeResponseDto> resultDto = new ArrayList<RefStatusTypeResponseDto>();
		if (results != null) {
			for (RefStatusType model : results) {
				resultDto.add(convertStatus(model));
			}
		}
		return resultDto;
	}

	public static RefStatusTypeResponseDto convertStatus(RefStatusType bean) {
		RefStatusTypeResponseDto resp = new RefStatusTypeResponseDto();
		resp.setStatusId(bean.getStatusId());
		resp.setStatusName(bean.getStatusName());
		resp.setStatusDescription(bean.getStatusDescription());
		resp.setCreatedById(bean.getCreatedById());
		resp.setCreatedDate(bean.getCreatedDate());
		resp.setUpdatedById(bean.getUpdatedById());
		resp.setUpdatedDate(bean.getUpdatedDate());
		return resp;
	}

	public static SdpOperatorRightResponseDto convertOperatorRight(SdpOperatorRight bean) {
		SdpOperatorRightResponseDto resp = new SdpOperatorRightResponseDto();
		resp.setCreatedById(bean.getCreatedById());
		resp.setCreatedDate(bean.getCreatedDate());
		resp.setUpdatedById(bean.getUpdatedById());
		resp.setUpdatedDate(bean.getUpdatedDate());
		resp.setRightDescription(bean.getDescription());
		resp.setRightId(bean.getId());
		resp.setRightName(bean.getName());
		if (bean.getOperatorResource() != null) {
			resp.setOperatorResourceId(bean.getOperatorResource().getId());
			resp.setOperatorResourceName(bean.getOperatorResource().getName());
			resp.setOperatorResourceDescription(bean.getOperatorResource().getDescription());
		}
		return resp;
	}

	public static List<SdpOperatorRightResponseDto> convertOperatorRights(List<SdpOperatorRight> beans) {
		List<SdpOperatorRightResponseDto> dtos = new ArrayList<SdpOperatorRightResponseDto>();
		if (beans != null) {
			for (SdpOperatorRight bean : beans) {
				dtos.add(convertOperatorRight(bean));
			}
		}
		return dtos;
	}

	public static SdpVoucherDto convertVoucherToVoucherDto(SdpVoucher voucher) {
		if (voucher == null) {
			return null;
		}
		SdpVoucherDto dto = new SdpVoucherDto();
		dto.setValidityPeriod(voucher.getSdpVoucherCampaign().getValidityPeriod());
		dto.setVoucherCode(voucher.getVoucherCode());
		dto.setVoucherId(voucher.getVoucherId());
		dto.setVoucherType(voucher.getSdpVoucherCampaign().getVoucherType());
		if (voucher.getSdpParty() != null) {
			dto.setPartyId(voucher.getSdpParty().getPartyId());
		}
		if (voucher.getSdpVoucherCampaign().getSdpSolutionOffer() != null) {
			dto.setSolutionOfferId(voucher.getSdpVoucherCampaign().getSdpSolutionOffer().getSolutionOfferId());
		}
		dto.setCreatedById(voucher.getCreatedById());
		dto.setCreatedDate(voucher.getCreatedDate());
		dto.setUpdatedById(voucher.getUpdatedById());
		dto.setUpdatedDate(voucher.getUpdatedDate());
		dto.setStartDate(voucher.getSdpVoucherCampaign().getStartDate());
		dto.setEndDate(voucher.getSdpVoucherCampaign().getEndDate());
		return dto;
	}

	public static List<SdpVoucherDto> convertListOfVoucherToVoucherDto(List<SdpVoucher> vouchers) {
		List<SdpVoucherDto> result = new ArrayList<SdpVoucherDto>();
		if (vouchers != null) {
			for (SdpVoucher voucher : vouchers) {
				SdpVoucherDto dto = convertVoucherToVoucherDto(voucher);
				result.add(dto);
			}
		}
		return result;
	}

	public static SdpVoucherDto convertVoucherCampaignToVoucherDto(SdpVoucherCampaign voucherCampaign) {
		if (voucherCampaign == null) {
			return null;
		}
		SdpVoucherDto dto = new SdpVoucherDto();
		dto.setValidityPeriod(voucherCampaign.getValidityPeriod());
		dto.setVoucherType(voucherCampaign.getVoucherType());
		dto.setCreatedById(voucherCampaign.getCreatedById());
		dto.setCreatedDate(voucherCampaign.getCreatedDate());
		dto.setUpdatedById(voucherCampaign.getUpdatedById());
		dto.setUpdatedDate(voucherCampaign.getUpdatedDate());
		dto.setStartDate(voucherCampaign.getStartDate());
		dto.setEndDate(voucherCampaign.getEndDate());
		return dto;
	}

	public static List<SdpVoucherDto> convertListOfVoucherCampaignToVoucherDto(List<SdpVoucherCampaign> voucherCampaigns) {
		List<SdpVoucherDto> result = new ArrayList<SdpVoucherDto>();
		if (voucherCampaigns != null) {
			for (SdpVoucherCampaign voucher : voucherCampaigns) {
				SdpVoucherDto dto = convertVoucherCampaignToVoucherDto(voucher);
				result.add(dto);
			}
		}
		return result;
	}

	// ////////// AVS ///////////////////

	public static List<AvsCountryLangCodeDto> convertAvsCountryLangCodes(List<AvsCountryLangCode> infos) {
		ArrayList<AvsCountryLangCodeDto> results = new ArrayList<AvsCountryLangCodeDto>();
		if (infos != null) {
			for (AvsCountryLangCode info : infos) {
				AvsCountryLangCodeDto dto = new AvsCountryLangCodeDto();
				dto.setCountry(info.getCountry());
				dto.setCountryCode(info.getCountryCode());
				results.add(dto);
			}
		}
		return results;
	}

	public static List<AvsRetailerDomainDto> convertAvsRetailerDomains(List<AvsRetailerDomain> infos) {
		ArrayList<AvsRetailerDomainDto> results = new ArrayList<AvsRetailerDomainDto>();
		if (infos != null) {
			for (AvsRetailerDomain info : infos) {
				AvsRetailerDomainDto dto = new AvsRetailerDomainDto();
				dto.setCreationDate(info.getCreationDate());
				dto.setDescription(info.getDescription());
				dto.setHostDomain(info.getHostDomain());
				dto.setRetailerId(info.getRetailerId());
				dto.setUpdateDate(info.getUpdateDate());
				results.add(dto);
			}
		}
		return results;
	}

	public static List<AvsDeviceIdTypeDto> convertAvsDeviceIdTypes(List<AvsDeviceIdType> infos) {
		ArrayList<AvsDeviceIdTypeDto> results = new ArrayList<AvsDeviceIdTypeDto>();
		if (infos != null) {
			for (AvsDeviceIdType info : infos) {
				AvsDeviceIdTypeDto dto = new AvsDeviceIdTypeDto();
				dto.setCreationDate(info.getCreationDate());
				dto.setTypeDescription(info.getTypeDescription());
				dto.setTypeId(info.getTypeId());
				dto.setUpdateDate(info.getUpdateDate());
				dto.setValue(info.getValue());
				results.add(dto);
			}
		}
		return results;
	}

	public static List<AvsPaymentTypeDto> convertAvsPaymentTypes(List<AvsPaymentType> infos) {
		ArrayList<AvsPaymentTypeDto> results = new ArrayList<AvsPaymentTypeDto>();
		if (infos != null) {
			for (AvsPaymentType info : infos) {
				AvsPaymentTypeDto dto = new AvsPaymentTypeDto();
				dto.setCreationDate(info.getCreationDate());
				dto.setPaymentMethod(info.getPaymentMethod());
				dto.setPaymentTypeId(info.getPaymentTypeId());
				dto.setUpdateDate(info.getUpdateDate());
				results.add(dto);
			}
		}
		return results;
	}

	public static List<AvsPcExtendedRatingDto> convertAvsPcExtendedRatings(List<AvsPcExtendedRating> infos) {
		ArrayList<AvsPcExtendedRatingDto> results = new ArrayList<AvsPcExtendedRatingDto>();
		if (infos != null) {
			for (AvsPcExtendedRating info : infos) {
				AvsPcExtendedRatingDto dto = new AvsPcExtendedRatingDto();
				dto.setCreationDate(info.getCreationDate());
				dto.setPcDescription(info.getPcDescription());
				dto.setPcId(info.getPcId());
				dto.setPcValue(info.getPcValue());
				dto.setUpdateDate(info.getUpdateDate());
				results.add(dto);
			}
		}
		return results;
	}

	public static List<AvsPcLevelDto> convertAvsPcLevels(List<AvsPcLevel> infos) {
		ArrayList<AvsPcLevelDto> results = new ArrayList<AvsPcLevelDto>();
		if (infos != null) {
			for (AvsPcLevel info : infos) {
				AvsPcLevelDto dto = new AvsPcLevelDto();
				dto.setCreationDate(info.getCreationDate());
				dto.setDescription(info.getDescription());
				dto.setValue(info.getValue());
				dto.setUpdateDate(info.getUpdateDate());
				results.add(dto);
			}
		}
		return results;
	}

	public static List<AvsPlatformDto> convertAvsPlatforms(List<AvsPlatform> infos) {
		ArrayList<AvsPlatformDto> results = new ArrayList<AvsPlatformDto>();
		if (infos != null) {
			for (AvsPlatform info : infos) {
				AvsPlatformDto dto = new AvsPlatformDto();
				dto.setCreationDate(info.getCreationDate());
				dto.setPlatformId(info.getPlatformId());
				dto.setPlatformName(info.getPlatformName());
				dto.setUpdateDate(info.getUpdateDate());
				dto.setDeviceTypes(convertAvsDeviceIdTypes(info.getDeviceIdTypes()));
				results.add(dto);
			}
		}
		return results;
	}

	public static AvsDigitalGoodDto convertAvsDigitalGood(AvsDigitalGood bean) {
		if (bean == null) {
			return null;
		}
		AvsDigitalGoodDto dto = new AvsDigitalGoodDto();
		dto.setCreationDate(bean.getCreationDate());
		dto.setDescription(bean.getDescription());
		dto.setExternalId(bean.getExternalId());
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		dto.setType(bean.getType());
		dto.setUpdateDate(bean.getUpdateDate());
		return dto;
	}

	public static AvsDigitalGoodDto convertAvsDigitalGood(AvsLnkOfferDigitalGood bean) {
		if (bean == null) {
			return null;
		}
		AvsDigitalGoodDto dto = convertAvsDigitalGood(bean.getAvsDigitalGood());
		if (dto != null) {
			dto.setPrice(bean.getPrice());
		}
		return dto;
	}

	public static List<AvsDigitalGoodDto> convertAvsDigitalGoods(List<AvsLnkOfferDigitalGood> beans) {
		List<AvsDigitalGoodDto> dtos = new ArrayList<AvsDigitalGoodDto>();
		if (beans != null) {
			for (AvsLnkOfferDigitalGood bean : beans) {
				dtos.add(convertAvsDigitalGood(bean));
			}
		}
		return dtos;
	}

	public static List<SdpDeviceAccessResponseDto> convertPartyToDeviceAccess(List<SdpPartyDeviceExt> beans) {
		List<SdpDeviceAccessResponseDto> dtos = new ArrayList<SdpDeviceAccessResponseDto>();
		if (beans != null) {
			for (SdpPartyDeviceExt bean : beans) {
				SdpDeviceAccessResponseDto dto = new SdpDeviceAccessResponseDto();
				dto.setId(String.valueOf(bean.getPartyId()));
				dto.setItemType(Filter.USER.getValue());
				dto.setReason(bean.getBlReason());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static List<SdpDeviceAccessResponseDto> convertDeviceToDeviceAccess(List<SdpDevice> beans) {
		List<SdpDeviceAccessResponseDto> dtos = new ArrayList<SdpDeviceAccessResponseDto>();
		if (beans != null) {
			for (SdpDevice bean : beans) {
				SdpDeviceAccessResponseDto dto = new SdpDeviceAccessResponseDto();
				dto.setId(bean.getDeviceUuid());
				dto.setItemType(Filter.DEVICE.getValue());
				dto.setReason(bean.getBlReason());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static List<SdpDeviceAccessResponseDto> convertDeviceChannelToDeviceAccess(List<RefDeviceChannel> beans) {
		List<SdpDeviceAccessResponseDto> dtos = new ArrayList<SdpDeviceAccessResponseDto>();
		if (beans != null) {
			for (RefDeviceChannel bean : beans) {
				SdpDeviceAccessResponseDto dto = new SdpDeviceAccessResponseDto();
				dto.setId(String.valueOf(bean.getDeviceChannelId()));
				dto.setItemType(Filter.DEVICE_CHANNEL.getValue());
				dto.setReason(bean.getBlReason());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static List<SdpDeviceAccessResponseDto> convertDeviceBrandToDeviceAccess(List<RefDeviceBrand> beans) {
		List<SdpDeviceAccessResponseDto> dtos = new ArrayList<SdpDeviceAccessResponseDto>();
		if (beans != null) {
			for (RefDeviceBrand bean : beans) {
				SdpDeviceAccessResponseDto dto = new SdpDeviceAccessResponseDto();
				dto.setId(String.valueOf(bean.getDeviceBrandId()));
				dto.setItemType(Filter.DEVICE_BRAND.getValue());
				dto.setReason(bean.getBlReason());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static List<SdpDeviceAccessResponseDto> convertDeviceModelToDeviceAccess(List<RefDeviceModel> beans) {
		List<SdpDeviceAccessResponseDto> dtos = new ArrayList<SdpDeviceAccessResponseDto>();
		if (beans != null) {
			for (RefDeviceModel bean : beans) {
				SdpDeviceAccessResponseDto dto = new SdpDeviceAccessResponseDto();
				dto.setId(String.valueOf(bean.getDeviceModelId()));
				dto.setItemType(Filter.DEVICE_MODEL.getValue());
				dto.setReason(bean.getBlReason());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static SdpDeviceBrandResponseDto convertDeviceBrand(RefDeviceBrand bean) {
		if (bean == null) {
			return null;
		}
		SdpDeviceBrandResponseDto dto = new SdpDeviceBrandResponseDto();
		dto.setBrandId(bean.getDeviceBrandId());
		dto.setBrandName(bean.getDeviceBrandName());
		dto.setIsBlacklisted(BooleanConverter.getBooleanValue(bean.getIsBl()));
		dto.setIsWhitelisted(BooleanConverter.getBooleanValue(bean.getIsWl()));
		dto.setBlacklistReason(bean.getBlReason());
		return dto;
	}

	public static List<SdpDeviceBrandResponseDto> convertDeviceBrands(List<RefDeviceBrand> beans) {
		List<SdpDeviceBrandResponseDto> dtos = new ArrayList<SdpDeviceBrandResponseDto>();
		if (beans != null) {
			for (RefDeviceBrand bean : beans) {
				dtos.add(convertDeviceBrand(bean));
			}
		}
		return dtos;
	}

	private static SdpDeviceModelResponseDto convertDeviceModel(RefDeviceModel bean) {
		if (bean == null) {
			return null;
		}
		SdpDeviceModelResponseDto dto = new SdpDeviceModelResponseDto();
		dto.setModelId(bean.getDeviceModelId());
		dto.setModelName(bean.getDeviceModelName());
		dto.setIsBlacklisted(BooleanConverter.getBooleanValue(bean.getIsBl()));
		dto.setIsWhitelisted(BooleanConverter.getBooleanValue(bean.getIsWl()));
		dto.setBlacklistReason(bean.getBlReason());
		return dto;
	}

	public static List<SdpDeviceModelResponseDto> convertDeviceModels(List<RefDeviceModel> beans) {
		List<SdpDeviceModelResponseDto> dtos = new ArrayList<SdpDeviceModelResponseDto>();
		if (beans != null) {
			for (RefDeviceModel bean : beans) {
				dtos.add(convertDeviceModel(bean));
			}
		}
		return dtos;
	}

	private static SdpDeviceChannelResponseDto convertDeviceChannel(RefDeviceChannel bean) {
		if (bean == null) {
			return null;
		}
		SdpDeviceChannelResponseDto dto = new SdpDeviceChannelResponseDto();
		dto.setDeviceChannelId(bean.getDeviceChannelId());
		dto.setDeviceChannelName(bean.getDeviceChannelName());
		dto.setPortable(BooleanConverter.getBooleanValue(bean.getIsPortable()));
		dto.setIsBlacklisted(BooleanConverter.getBooleanValue(bean.getIsBl()));
		dto.setIsWhitelisted(BooleanConverter.getBooleanValue(bean.getIsWl()));
		dto.setBlacklistReason(bean.getBlReason());
		return dto;
	}

	public static List<SdpDeviceChannelResponseDto> convertDeviceChannels(List<RefDeviceChannel> beans) {
		List<SdpDeviceChannelResponseDto> dtos = new ArrayList<SdpDeviceChannelResponseDto>();
		if (beans != null) {
			for (RefDeviceChannel bean : beans) {
				dtos.add(convertDeviceChannel(bean));
			}
		}
		return dtos;
	}

	public static SdpDevicePolicyConfigResponseDto convertDevicePolicyConfig(SdpDevicePolicyConfig bean) {
		if (bean == null) {
			return null;
		}
		SdpDevicePolicyConfigResponseDto dto = new SdpDevicePolicyConfigResponseDto();
		dto.setDeviceChannel(bean.getRefDeviceChannel().getDeviceChannelName());
		dto.setMaximumNumber(bean.getMaximumAllowedDevices());
		return dto;
	}

	public static List<SdpDevicePolicyConfigResponseDto> convertDevicePolicyConfigs(List<SdpDevicePolicyConfig> beans) {
		List<SdpDevicePolicyConfigResponseDto> dtos = new ArrayList<SdpDevicePolicyConfigResponseDto>();
		if (beans != null) {
			for (SdpDevicePolicyConfig bean : beans) {
				dtos.add(convertDevicePolicyConfig(bean));
			}
		}
		return dtos;
	}

	public static SdpDevicePolicyResponseDto convertDevicePolicy(SdpDevicePolicy bean) {
		if (bean == null) {
			return null;
		}
		SdpDevicePolicyResponseDto dto = new SdpDevicePolicyResponseDto();
		dto.setPolicyId(bean.getPolicyId());
		dto.setPolicyName(bean.getPolicyName());
		dto.setIsDefaultPolicy(BooleanConverter.getBooleanValue(bean.getIsDefault()));
		dto.setMaxAssociationsNumber(bean.getNumberOfAssociations());
		dto.setSafetyPeriodDuration(bean.getSafetyPeriodDuration());
		dto.setConfigs(convertDevicePolicyConfigs(bean.getSdpDevicePolicyConfigs()));
		return dto;
	}

	public static List<SdpDevicePolicyResponseDto> convertDevicePolicies(List<SdpDevicePolicy> beans) {
		List<SdpDevicePolicyResponseDto> dtos = new ArrayList<SdpDevicePolicyResponseDto>();
		if (beans != null) {
			for (SdpDevicePolicy bean : beans) {
				dtos.add(convertDevicePolicy(bean));
			}
		}
		return dtos;
	}

	public static SdpDeviceResponseDto convertDevice(SdpDevice bean) throws PropertyNotFoundException {
		if (bean == null) {
			return null;
		}
		SdpDeviceResponseDto dto = new SdpDeviceResponseDto();
		dto.setBlacklisted(BooleanConverter.getBooleanValue(bean.getIsBl()));
		dto.setWhitelisted(BooleanConverter.getBooleanValue(bean.getIsWl()));
		dto.setPaired(BooleanConverter.getBooleanValue(bean.getIsPaired()));
		dto.setStatus(StatusIdConverter.getStatusDescription(bean.getStatusId()));
		dto.setLastFruitionDate(bean.getLastFruitionDate());
		if (bean.getSdpPartyDeviceExt() != null) {
			dto.setPartyId(bean.getSdpPartyDeviceExt().getPartyId());
		}
		dto.setDeviceUUID(bean.getDeviceUuid());
		dto.setDeviceUUIDType(bean.getRefDeviceUuidType().getDeviceUuidTypeName());
		if (bean.getRefDeviceChannel() != null) {
			dto.setDeviceChannel(bean.getRefDeviceChannel().getDeviceChannelName());
		}
		if (bean.getRefDeviceBrand() != null) {
			dto.setDeviceBrand(bean.getRefDeviceBrand().getDeviceBrandName());
		}
		if (bean.getRefDeviceModel() != null) {
			dto.setDeviceModel(bean.getRefDeviceModel().getDeviceModelName());
		}
		dto.setDeviceAlias(bean.getAlias());
		dto.setBlacklistReason(bean.getBlReason());

		dto.setCreatedById(bean.getCreatedById());
		dto.setCreatedDate(bean.getCreatedDate());
		dto.setDeletedById(bean.getDeletedById());
		dto.setDeletedDate(bean.getDeletedDate());
		dto.setUpdatedById(bean.getUpdatedById());
		dto.setUpdatedDate(bean.getUpdatedDate());
		dto.setChangeStatusById(bean.getChangeStatusById());
		dto.setChangeStatusDate(bean.getChangeStatusDate());
		return dto;
	}

	public static List<SdpDeviceResponseDto> convertDevices(List<SdpDevice> beans) throws PropertyNotFoundException {
		List<SdpDeviceResponseDto> dtos = new ArrayList<SdpDeviceResponseDto>();
		if (beans != null) {
			for (SdpDevice bean : beans) {
				dtos.add(convertDevice(bean));
			}
		}
		return dtos;
	}

	public static SdpDeviceCounterResponseDto convertDeviceCounter(SdpDeviceCounterConfig bean) {
		if (bean == null) {
			return null;
		}
		SdpDeviceCounterResponseDto dto = new SdpDeviceCounterResponseDto();
		dto.setDeviceChannelId(bean.getRefDeviceChannel().getDeviceChannelId());
		dto.setDeviceChannelName(bean.getRefDeviceChannel().getDeviceChannelName());
		dto.setRegisteredDevices(bean.getRegisteredDevices());
		return dto;
	}

	public static List<SdpDeviceCounterResponseDto> convertDeviceCounters(List<SdpDeviceCounterConfig> beans) {
		List<SdpDeviceCounterResponseDto> dtos = new ArrayList<SdpDeviceCounterResponseDto>();
		if (beans != null) {
			for (SdpDeviceCounterConfig bean : beans) {
				dtos.add(convertDeviceCounter(bean));
			}
		}
		return dtos;
	}

	public static SdpPartyDeviceResponseDto convertPartyDeviceExt(SdpPartyDeviceExt bean) {
		if (bean == null) {
			return null;
		}
		SdpPartyDeviceResponseDto dto = new SdpPartyDeviceResponseDto();
		dto.setCounters(convertDeviceCounters(bean.getSdpDeviceCounterConfigs()));
		dto.setRegistrationsDone(bean.getRegistrationsDone());
		dto.setSafetyPeriodExpirationDate(bean.getSafetyPeriodExpiration());
		return dto;
	}

	public static SdpShoppingCartResponseDto convertShoppingCart(SdpShoppingCart bean) throws PropertyNotFoundException {
		if (bean == null) {
			return null;
		}
		SdpShoppingCartResponseDto dto = new SdpShoppingCartResponseDto();
		dto.setShoppingCartId(bean.getShoppingCartId());
		dto.setStatus(StatusIdConverter.getStatusDescription(bean.getStatusId()));
		dto.setCreatedById(bean.getCreatedById());
		dto.setCreatedDate(bean.getCreatedDate());
		dto.setUpdatedById(bean.getUpdatedById());
		dto.setUpdatedDate(bean.getUpdatedDate());
		dto.setChangeStatusById(bean.getChgStatusById());
		dto.setChangeStatusDate(bean.getChgStatusDate());
		dto.setItems(convertShoppingCartItems(bean.getSdpShoppingCartItems()));
		// prezzo totale va calcolato a parte
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (SdpShoppingCartItem i : bean.getSdpShoppingCartItems()) {
			totalPrice = totalPrice.add(i.getItemSubtotal());
		}
		dto.setTotalPrice(totalPrice);
		return dto;
	}

	public static List<SdpShoppingCartResponseDto> convertShoppingCarts(List<SdpShoppingCart> beans) throws PropertyNotFoundException {
		List<SdpShoppingCartResponseDto> dtos = new ArrayList<SdpShoppingCartResponseDto>();
		if (beans != null) {
			for (SdpShoppingCart bean : beans) {
				dtos.add(convertShoppingCart(bean));
			}
		}
		return dtos;
	}

	public static SdpShoppingCartItemResponseDto convertShoppingCartItem(SdpShoppingCartItem bean) {
		if (bean == null) {
			return null;
		}
		SdpShoppingCartItemResponseDto dto = new SdpShoppingCartItemResponseDto();
		dto.setItemId(bean.getId().getItemId());
		dto.setItemType(bean.getRefItemType().getItemTypeName());
		dto.setItemDescription(bean.getItemDescription());
		dto.setQuantity(bean.getQuantity());
		dto.setItemPrice(bean.getItemPrice());
		dto.setSubTotalPrice(bean.getItemSubtotal());
		return dto;
	}

	public static List<SdpShoppingCartItemResponseDto> convertShoppingCartItems(List<SdpShoppingCartItem> beans) {
		List<SdpShoppingCartItemResponseDto> dtos = new ArrayList<SdpShoppingCartItemResponseDto>();
		if (beans != null) {
			for (SdpShoppingCartItem bean : beans) {
				dtos.add(convertShoppingCartItem(bean));
			}
		}
		return dtos;
	}

	public static List<String> convertSolutionOfferTypes(List<RefSolutionOfferType> beans) {
		List<String> results = new ArrayList<String>();
		if (beans != null) {
			for (RefSolutionOfferType bean : beans) {
				results.add(bean.getSolutionOfferTypeName());
			}
		}
		return results;
	}
}