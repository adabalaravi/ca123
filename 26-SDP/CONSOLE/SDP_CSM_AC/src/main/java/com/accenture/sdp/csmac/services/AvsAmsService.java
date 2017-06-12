package com.accenture.sdp.csmac.services;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.ws.BindingProvider;

import com.accenture.sdp.csmac.beans.avs.CrmAccountDeviceBean;
import com.accenture.sdp.csmac.beans.avs.dto.ExternalOfferBean;
import com.accenture.sdp.csmac.beans.avs.dto.UpdateCommProfOpBean;
import com.accenture.sdp.csmac.beans.party.AvsPartyBean;
import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.PropertyManager;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.AvsConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.AccountMgmtPort;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.AccountMgmtResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.AccountMgmtService;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.CrmAccountDeviceType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.CrmAccountDevicesListType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.CrmAccountMgmtRequest;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.CrmContentPurchaseRequest;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.ExtendedRatingType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.ExternalOfferListType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.ExternalOfferType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.FlagTypeMF;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.FlagTypeYN;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.GetSdpAccountProfileDataResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.GetSdpPartyProfileDataResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.OperationProfileType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.OperationPwdType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.OperationType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.PayloadType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.SdpAccountProfileDataResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.SdpDataRequest;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.SdpPartyProfileDataResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.UpdateCrmAccountCommercialProfileRequest;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.UpdateProfileListType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.UpdateProfileType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.UpdateUserPwdRequest;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.UserPcExtendedRatingsListType;

@ManagedBean(name = ApplicationConstants.AVS_AMS_SERVICE_BEAN)
@ApplicationScoped
public class AvsAmsService {

	private static LoggerWrapper log = new LoggerWrapper(AvsAmsService.class);

	private AccountMgmtPort accountMgmtPort;

	private static final int OPERATION_TYPE_CREATE = 1;
	private static final int OPERATION_TYPE_UPDATE = 2;
	private static final String CONTENT_PURCHASE_CHANNEL = "CONSOLE";
	private static final String PURCHASE_OPERATION_DESCRIPTION = "purchased on assurance console";

	private static final String REQUEST_DEBUG_STRING = "%s request composed";
	private static final String RESPONSE_DEBUG_STRING = "%s response received \tcode: %s\tdescription: %s";

	public AvsAmsService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.AVS_AMS_ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.AVS_AMS_WSDL_URL);
		try {
			URL url = new URL(urlString);
			log.logDebug("creating AVS AmsService at Url: %s", url);
			AccountMgmtService accountMgmtService = new AccountMgmtService(url);

			accountMgmtPort = accountMgmtService.getAccountMgmtServicePort();
			log.logDebug("AVS AmsService instantiated.Endpoint Url: %s", url);

			Utilities.addSoapHandler((BindingProvider) accountMgmtPort, urlString);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public AccountMgmtResponse createAVSUser(AvsPartyBean info) throws ServiceErrorException {
		return manageAVSUser(OPERATION_TYPE_CREATE, info.getCrmAccountId(), info.getUserType(), info.getUserStatus(), info.getCrmAccountDeviceList(),
				info.getUsername(), info.getPassword(), info.getFirstName(), info.getLastName(), info.getBirthDate(), info.getGender(),
				info.getCrmAccountZipCode(), info.getEmail(), info.getCrmAccountMobileNumber(), info.getUserCountry(), info.getUserLanguage(),
				info.getQualitySetting(), info.getUserPaymentMethod(), info.getUserRememberPinFlag(), info.getUserPcLevel(), info.getUserPinPurchase(),
				info.getUserPinParentalControl(), info.getUserPcExtendedRatings(), info.getCrmAccountPurchaseBlockingFlag(),
				info.getCrmAccountPurchaseBlockingThresholdCurrency(), info.getCrmAccountPurchaseBlockingThresholdValue(),
				info.getCrmAccountPurchaseBlockingThresholdPeriod(), info.getCrmAccountPurchaseBlockingAlertEnabledFlag(),
				info.getCrmAccountPurchaseBlockingAlertChannel(), info.getCrmAccountPurchaseBlockingAlertMode(),
				info.getCrmAccountPurchaseBlockingIntermediateThreshold(), info.getCrmAccountConsumptionBlockingFlag(),
				info.getCrmAccountConsumptionBlockingThresholdValue(), info.getCrmAccountConsumptionBlockingThresholdPeriod(),
				info.getCrmAccountConsumptionBlockingEnabledFlag(), info.getCrmAccountConsumptionBlockingAlertChannel(),
				info.getCrmAccountConsumptionBlockingAlertMode(), info.getCrmAccountConsumptionBlockingIntermediateThreshold(),
				info.getCrmAccountPurchaseValue(), info.getCrmAccountConsumption(), info.getCrmAccountRetailerDomain(), info.getUpdateCommProfOpBeans());
	}

	public AccountMgmtResponse updateAVSUser(AvsPartyBean info) throws ServiceErrorException {
		return manageAVSUser(OPERATION_TYPE_UPDATE, info.getCrmAccountId(), info.getUserType(), info.getUserStatus(), info.getCrmAccountDeviceList(),
				info.getUsername(), info.getPassword(), info.getFirstName(), info.getLastName(), info.getBirthDate(), info.getGender(),
				info.getCrmAccountZipCode(), info.getEmail(), info.getCrmAccountMobileNumber(), info.getUserCountry(), info.getUserLanguage(),
				info.getQualitySetting(), info.getUserPaymentMethod(), info.getUserRememberPinFlag(), info.getUserPcLevel(), info.getUserPinPurchase(),
				info.getUserPinParentalControl(), info.getUserPcExtendedRatings(), info.getCrmAccountPurchaseBlockingFlag(),
				info.getCrmAccountPurchaseBlockingThresholdCurrency(), info.getCrmAccountPurchaseBlockingThresholdValue(),
				info.getCrmAccountPurchaseBlockingThresholdPeriod(), info.getCrmAccountPurchaseBlockingAlertEnabledFlag(),
				info.getCrmAccountPurchaseBlockingAlertChannel(), info.getCrmAccountPurchaseBlockingAlertMode(),
				info.getCrmAccountPurchaseBlockingIntermediateThreshold(), info.getCrmAccountConsumptionBlockingFlag(),
				info.getCrmAccountConsumptionBlockingThresholdValue(), info.getCrmAccountConsumptionBlockingThresholdPeriod(),
				info.getCrmAccountConsumptionBlockingEnabledFlag(), info.getCrmAccountConsumptionBlockingAlertChannel(),
				info.getCrmAccountConsumptionBlockingAlertMode(), info.getCrmAccountConsumptionBlockingIntermediateThreshold(),
				info.getCrmAccountPurchaseValue(), info.getCrmAccountConsumption(), info.getCrmAccountRetailerDomain(), info.getUpdateCommProfOpBeans());
	}

	private AccountMgmtResponse manageAVSUser(int opType, String crmAccountId, Integer userType, Integer userStatus,
			List<CrmAccountDeviceBean> crmAccountDeviceList, String username, String password, String firstName, String lastName, Date birthDate,
			String gender, String crmAccountZipCode, String email, String crmAccountMobileNumber, String userCountry, String userLanguage,
			Integer qualitySetting, Integer userPaymentMethod, String userRememberPinFlag, Integer userPcLevel, String userPinPurchase,
			String userPinParentalControl, List<String> userPcExtendedRatings, String crmAccountPurchaseBlockingFlag,
			String crmAccountPurchaseBlockingThresholdCurrency, BigDecimal crmAccountPurchaseBlockingThresholdValue,
			Integer crmAccountPurchaseBlockingThresholdPeriod, String crmAccountPurchaseBlockingAlertEnabledFlag,
			Integer crmAccountPurchaseBlockingAlertChannel, Integer crmAccountPurchaseBlockingAlertMode,
			BigDecimal crmAccountPurchaseBlockingIntermediateThreshold, String crmAccountConsumptionBlockingFlag,
			Integer crmAccountConsumptionBlockingThresholdValue, Integer crmAccountConsumptionBlockingThresholdPeriod,
			String crmAccountConsumptionBlockingEnabledFlag, Integer crmAccountConsumptionBlockingAlertChannel, Integer crmAccountConsumptionBlockingAlertMode,
			Integer crmAccountConsumptionBlockingIntermediateThreshold, BigDecimal crmAccountPurchaseValue, Integer crmAccountConsumption,
			String crmAccountRetailerDomain, List<UpdateCommProfOpBean> updateCommProfOpBeans) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		AccountMgmtResponse response = null;
		try {
			CrmAccountMgmtRequest crmAccountMgmtRequest = new CrmAccountMgmtRequest();

			crmAccountMgmtRequest.setOperationType(String.valueOf(opType));

			PayloadType payload = new PayloadType();
			crmAccountMgmtRequest.setPayload(payload);
			payload.setTenantName(Utilities.getTenantName().value);

			payload.setCrmAccountId(crmAccountId);
			if (userType != null) {
				payload.setUserType(String.valueOf(userType));
			}
			payload.setUserStatus(userStatus);
			CrmAccountDevicesListType devicesListType = new CrmAccountDevicesListType();
			payload.setCrmAccountDevices(devicesListType);
			for (CrmAccountDeviceBean bean : crmAccountDeviceList) {
				CrmAccountDeviceType deviceType = new CrmAccountDeviceType();
				if (!Utilities.isEmptyString(bean.getChannel())) {
					deviceType.setChannel(bean.getChannel());
				}
				if (!Utilities.isEmptyString(bean.getCrmAccountDeviceId())) {
					deviceType.setCrmAccountDeviceId(bean.getCrmAccountDeviceId());
				}
				deviceType.setCrmAccountDeviceIdType(bean.getCrmAccountDeviceIdType());
				devicesListType.getCrmAccountDevice().add(deviceType);
			}

			if (!Utilities.isEmptyString(username)) {
				payload.setUsername(username);
			}
			if (!Utilities.isEmptyString(password)) {
				payload.setPassword(password);
			}
			if (!Utilities.isEmptyString(firstName)) {
				payload.setName(firstName);
			}
			if (!Utilities.isEmptyString(lastName)) {
				payload.setSurname(lastName);
			}
			payload.setBirthDate(Utilities.getXMLGregorianCalendarWithoutTime(birthDate));
			if (!Utilities.isEmptyString(gender)) {
				payload.setGender(FlagTypeMF.valueOf(gender));
			}
			if (!Utilities.isEmptyString(crmAccountZipCode)) {
				payload.setCrmAccountZipCode(crmAccountZipCode);
			}
			if (!Utilities.isEmptyString(email)) {
				payload.setEmail(email);
			}
			if (!Utilities.isEmptyString(crmAccountMobileNumber)) {
				payload.setCrmAccountMobileNumber(crmAccountMobileNumber);
			}
			if (!Utilities.isEmptyString(userCountry)) {
				payload.setUserCountry(userCountry);
			}
			if (!Utilities.isEmptyString(userLanguage)) {
				payload.setUserLanguage(userLanguage);
			}
			if (qualitySetting != null) {
				payload.setQualitySetting(String.valueOf(qualitySetting));
			}
			payload.setUserPaymentMethod(userPaymentMethod);
			if (!Utilities.isEmptyString(userRememberPinFlag)) {
				payload.setUserRememberPinFlag(FlagTypeYN.valueOf(userRememberPinFlag));
			}

			payload.setUserPcLevel(userPcLevel);
			if (!Utilities.isEmptyString(userPinPurchase)) {
				payload.setUserPinPurchase(userPinPurchase);
			}
			if (!Utilities.isEmptyString(userPinParentalControl)) {
				payload.setUserPinParentalControl(userPinParentalControl);
			}

			UserPcExtendedRatingsListType extendedRatingsListType = new UserPcExtendedRatingsListType();
			payload.setUserPcExtendedRatings(extendedRatingsListType);
			for (String rating : userPcExtendedRatings) {
				ExtendedRatingType extendedRatingType = ExtendedRatingType.valueOf(rating);
				extendedRatingsListType.getUserPcExtendedRating().add(extendedRatingType);
			}

			if (!Utilities.isEmptyString(crmAccountPurchaseBlockingFlag)) {
				payload.setCrmAccountPurchaseBlockingFlag(FlagTypeYN.valueOf(crmAccountPurchaseBlockingFlag));
				if (FlagTypeYN.Y.value().equals(crmAccountPurchaseBlockingFlag)) {
					payload.setCrmAccountPurchaseBlockingThresholdCurrency(crmAccountPurchaseBlockingThresholdCurrency);
					// FIXME PurchaseBlockingThresholdCurrency fisso EUR
					payload.setCrmAccountPurchaseBlockingThresholdCurrency("EUR");
					payload.setCrmAccountPurchaseBlockingThresholdValue(crmAccountPurchaseBlockingThresholdValue);
					if (crmAccountPurchaseBlockingThresholdPeriod != null) {
						payload.setCrmAccountPurchaseBlockingThresholdPeriod(String.valueOf(crmAccountPurchaseBlockingThresholdPeriod));
					}
				}
			}
			if (!Utilities.isEmptyString(crmAccountPurchaseBlockingAlertEnabledFlag)) {
				payload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.valueOf(crmAccountPurchaseBlockingAlertEnabledFlag));
				if (crmAccountPurchaseBlockingAlertChannel != null) {
					payload.setCrmAccountPurchaseBlockingAlertChannel(String.valueOf(crmAccountPurchaseBlockingAlertChannel));
				}
				if (crmAccountPurchaseBlockingAlertMode != null) {
					payload.setCrmAccountPurchaseBlockingAlertMode(String.valueOf(crmAccountPurchaseBlockingAlertMode));
				}
			} else {
				payload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.N);
			}
			if (crmAccountPurchaseBlockingIntermediateThreshold != null) {
				payload.setCrmAccountPurchaseBlockingIntermediateThreshold(crmAccountPurchaseBlockingIntermediateThreshold);
			} else {
				payload.setCrmAccountPurchaseBlockingIntermediateThreshold(BigDecimal.ZERO);
			}
			if (!Utilities.isEmptyString(crmAccountConsumptionBlockingFlag)) {
				payload.setCrmAccountConsumptionBlockingFlag(FlagTypeYN.valueOf(crmAccountConsumptionBlockingFlag));
				payload.setCrmAccountConsumptionBlockingThresholdValue(crmAccountConsumptionBlockingThresholdValue);
				if (crmAccountConsumptionBlockingThresholdPeriod != null) {
					payload.setCrmAccountConsumptionBlockingThresholdPeriod(String.valueOf(crmAccountConsumptionBlockingThresholdPeriod));
				}
			} else {
				payload.setCrmAccountConsumptionBlockingFlag(FlagTypeYN.N);
			}
			if (!Utilities.isEmptyString(crmAccountConsumptionBlockingEnabledFlag)) {
				payload.setCrmAccountConsumptionBlockingEnabledFlag(FlagTypeYN.valueOf(crmAccountConsumptionBlockingEnabledFlag));
				if (crmAccountConsumptionBlockingAlertChannel != null) {
					payload.setCrmAccountConsumptionBlockingAlertChannel(String.valueOf(crmAccountConsumptionBlockingAlertChannel));
				}
				if (crmAccountConsumptionBlockingAlertMode != null) {
					payload.setCrmAccountConsumptionBlockingAlertMode(String.valueOf(crmAccountConsumptionBlockingAlertMode));
				}
			}
			payload.setCrmAccountConsumptionBlockingIntermediateThreshold(crmAccountConsumptionBlockingIntermediateThreshold);
			if (crmAccountPurchaseValue != null) {
				payload.setCrmAccountPurchaseValue(crmAccountPurchaseValue);
			} else {
				payload.setCrmAccountPurchaseValue(BigDecimal.ZERO);
			}
			if (crmAccountConsumption != null) {
				payload.setCrmAccountConsumption(crmAccountConsumption);
			}
			if (!Utilities.isEmptyString(crmAccountRetailerDomain)) {
				payload.setCrmAccountRetailerDomain(crmAccountRetailerDomain);
			}

			UpdateProfileListType updateProfileListType = new UpdateProfileListType();
			payload.setUpdateCommProfileList(updateProfileListType);
			if (updateCommProfOpBeans != null) {
				for (UpdateCommProfOpBean updateCommProfOpBean : updateCommProfOpBeans) {
					UpdateProfileType updateProfileType = new UpdateProfileType();
					updateProfileListType.getUpdateCommProfOp().add(updateProfileType);
					updateProfileType.setCrmProductId(updateCommProfOpBean.getCrmProductId());
					updateProfileType.setOperationType(OperationProfileType.valueOf(updateCommProfOpBean.getOperationType()));

					ExternalOfferListType offerListType = new ExternalOfferListType();
					updateProfileType.setExternalOfferList(offerListType);
					for (ExternalOfferBean externalOfferBean : updateCommProfOpBean.getExternalOfferList()) {
						ExternalOfferType offerType = new ExternalOfferType();
						if (!Utilities.isEmptyString(externalOfferBean.getExternalOfferId())) {
							offerType.setExternalOfferId(String.valueOf(externalOfferBean.getExternalOfferId()));
						}
						if (!Utilities.isEmptyString(externalOfferBean.getPackageId())) {
							offerType.setPackageId(String.valueOf(externalOfferBean.getPackageId()));
						}
						offerType.setPrice(externalOfferBean.getPrice());
						offerListType.getExternalOffer().add(offerType);
					}
				}
			}

			log.logDebug(REQUEST_DEBUG_STRING, "AVS.crmAccountMgmtService");
			response = accountMgmtPort.crmAccountMgmtService(crmAccountMgmtRequest);
			log.logDebug(RESPONSE_DEBUG_STRING, "AVS.crmAccountMgmtService", response.getResultCode(), response.getResultDescription());
		} catch (Exception e) {
			log.logError(e);
			throw new ServiceErrorException(e);
		}
		parseResponse(response);
		return response;
	}

	public GetSdpAccountProfileDataResponse getSdpAccountProfileDataService(String crmAccountId, Long partyId) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpAccountProfileDataResponse response = null;
		try {
			SdpDataRequest request = new SdpDataRequest();
			request.setCrmAccountId(crmAccountId);
			request.setUserId(String.valueOf(partyId));
			request.setTenantName(Utilities.getTenantName().value);

			log.logDebug(REQUEST_DEBUG_STRING, "AVS.getSdpAccountProfileDataService");
			response = accountMgmtPort.getSdpAccountProfileDataService(request);
			log.logDebug(RESPONSE_DEBUG_STRING, "AVS.getSdpAccountProfileDataService", response.getResult().getResultCode(), response.getResult()
					.getResultDescription());
		} catch (Exception e) {
			log.logError(e);
			throw new ServiceErrorException(e);
		}
		parseResponse(response);
		return response.getResultData();
	}

	public GetSdpPartyProfileDataResponse getSdpPartyProfileDataService(String crmAccountId, Long partyId) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyProfileDataResponse response = null;
		try {
			SdpDataRequest request = new SdpDataRequest();
			request.setCrmAccountId(crmAccountId);
			request.setUserId(String.valueOf(partyId));
			request.setTenantName(Utilities.getTenantName().value);

			log.logDebug(REQUEST_DEBUG_STRING, "AVS.getSdpPartyProfileDataService");
			response = accountMgmtPort.getSdpPartyProfileDataService(request);
			log.logDebug(RESPONSE_DEBUG_STRING, "AVS.getSdpPartyProfileDataService", response.getResult().getResultCode(), response.getResult()
					.getResultDescription());
		} catch (Exception e) {
			log.logError(e);
			throw new ServiceErrorException(e);
		}
		parseResponse(response);
		return response.getResultData();
	}

	public AccountMgmtResponse addCrmAccountCommercialProfile(String crmAccountId, List<UpdateCommProfOpBean> updateCommProfOpBeans)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return updateCrmAccountCommercialProfileService(crmAccountId, updateCommProfOpBeans, OperationProfileType.ADD);
	}

	public AccountMgmtResponse deleteCrmAccountCommercialProfile(String crmAccountId, List<UpdateCommProfOpBean> updateCommProfOpBeans)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return updateCrmAccountCommercialProfileService(crmAccountId, updateCommProfOpBeans, OperationProfileType.DELETE);
	}
	
	private AccountMgmtResponse updateCrmAccountCommercialProfileService(String crmAccountId, List<UpdateCommProfOpBean> updateCommProfOpBeans, OperationProfileType operation)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		AccountMgmtResponse response = null;
		try {
			UpdateCrmAccountCommercialProfileRequest request = new UpdateCrmAccountCommercialProfileRequest();
			request.setTenantName(Utilities.getTenantName().value);
			request.setCrmAccountId(crmAccountId);
			request.setPurchaseChannel(CONTENT_PURCHASE_CHANNEL);
			UpdateProfileListType updateProfileListType = new UpdateProfileListType();
			request.setUpdateCommProfileList(updateProfileListType);
			for (UpdateCommProfOpBean updateCommProfOpBean : updateCommProfOpBeans) {
				UpdateProfileType updateProfileType = new UpdateProfileType();
				updateProfileType.setCrmProductId(updateCommProfOpBean.getCrmProductId());
				updateProfileType.setOperationType(operation);
				updateProfileType.setVoucherCode(updateCommProfOpBean.getVoucherCode());
				ExternalOfferListType offerListType = new ExternalOfferListType();
				for (ExternalOfferBean externalOfferBean : updateCommProfOpBean.getExternalOfferList()) {
					ExternalOfferType offerType = new ExternalOfferType();
					offerType.setExternalOfferId(externalOfferBean.getExternalOfferId());
					offerType.setPackageId(externalOfferBean.getPackageId());
					offerType.setPrice(externalOfferBean.getPrice());
					offerListType.getExternalOffer().add(offerType);
				}
				updateProfileType.setExternalOfferList(offerListType);
				updateProfileListType.getUpdateCommProfOp().add(updateProfileType);
			}

			log.logDebug(REQUEST_DEBUG_STRING, "AVS.updateCrmAccountCommercialProfileService");
			response = accountMgmtPort.updateCrmAccountCommercialProfileService(request);
			log.logDebug(RESPONSE_DEBUG_STRING, "AVS.updateCrmAccountCommercialProfileService", response.getResultCode(), response.getResultDescription());
		} catch (Exception e) {
			log.logError(e);
			throw new ServiceErrorException(e);
		}
		parseResponse(response);
		return response;
	}

	/**
	 * never used
	 * 
	 * @deprecated never tested. Use with caution
	 */
	public AccountMgmtResponse resetAvsUserPassword(String crmAccountId, String username, String newPassword) throws ServiceErrorException {
		return manageAvsUserPassword(OperationPwdType.RESET, crmAccountId, username, null, newPassword);
	}

	private AccountMgmtResponse manageAvsUserPassword(OperationPwdType opType, String crmAccountId, String username, String oldPassword, String newPassword)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		AccountMgmtResponse response = null;
		try {
			UpdateUserPwdRequest updateUserPwdRequest = new UpdateUserPwdRequest();
			updateUserPwdRequest.setTenantName(Utilities.getTenantName().value);
			updateUserPwdRequest.setType(opType);
			updateUserPwdRequest.setCrmAccountId(crmAccountId);
			updateUserPwdRequest.setUserName(username);
			if (oldPassword != null && oldPassword.length() > 0) {
				updateUserPwdRequest.setOldPassword(oldPassword);
			}
			updateUserPwdRequest.setNewPassword(newPassword);

			log.logDebug(REQUEST_DEBUG_STRING, "AVS.updateUserPwdService");
			response = accountMgmtPort.updateUserPwdService(updateUserPwdRequest);
			log.logDebug(RESPONSE_DEBUG_STRING, "AVS.updateUserPwdService", response.getResultCode(), response.getResultDescription());
		} catch (Exception e) {
			log.logError(e);
			throw new ServiceErrorException(e);
		}
		parseResponse(response);
		return response;
	}

	/**
	 * Was used for bundle purchase
	 * 
	 * @deprecated never tested. Use with caution
	 */
	@Deprecated
	public AccountMgmtResponse crmContentPurchase(String crmAccountId, long solutionOfferId, List<ExternalOfferBean> externalOfferBeans)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		AccountMgmtResponse response = null;
		try {
			CrmContentPurchaseRequest request = new CrmContentPurchaseRequest();
			request.setTenantName(Utilities.getTenantName().value);
			request.setOperationType(OperationType.PURCHASE);
			request.setOperationDescription(PURCHASE_OPERATION_DESCRIPTION);
			request.setCrmAccountId(crmAccountId);
			request.setPurchaseChannel(CONTENT_PURCHASE_CHANNEL);
			request.getExternalSolutionOffer().setExternalSolutionOfferId(solutionOfferId);

			for (ExternalOfferBean externalOfferBean : externalOfferBeans) {
				ExternalOfferType externalOfferType = new ExternalOfferType();
				externalOfferType.setExternalOfferId(String.valueOf(externalOfferBean.getExternalOfferId()));
				externalOfferType.setPackageId(String.valueOf(externalOfferBean.getPackageId()));
				externalOfferType.setPrice(externalOfferBean.getPrice());
				request.getExternalSolutionOffer().getExternalOfferList().getExternalOffer().add(externalOfferType);
			}

			log.logDebug(REQUEST_DEBUG_STRING, "AVS.crmContentPurchaseService");
			response = accountMgmtPort.crmContentPurchaseService(request);
			log.logDebug(RESPONSE_DEBUG_STRING, "AVS.crmContentPurchaseService", response.getResultCode(), response.getResultDescription());
		} catch (Exception e) {
			log.logError(e);
			throw new ServiceErrorException(e);
		}
		parseResponse(response);
		return response;
	}

	private void parseResponse(AccountMgmtResponse resp) throws ServiceErrorException {
		if (!AvsConstants.ACN_200.equals(resp.getResultCode())) {
			throw new ServiceErrorException(resp.getResultCode(), resp.getResultDescription());
		}
	}

	private void parseResponse(SdpAccountProfileDataResponse resp) throws ServiceErrorException {
		if (!AvsConstants.ACN_200.equals(resp.getResult().getResultCode())) {
			throw new ServiceErrorException(resp.getResult().getResultCode(), resp.getResult().getResultDescription());
		}
	}

	private void parseResponse(SdpPartyProfileDataResponse resp) throws ServiceErrorException {
		if (!AvsConstants.ACN_200.equals(resp.getResult().getResultCode())) {
			throw new ServiceErrorException(resp.getResult().getResultCode(), resp.getResult().getResultDescription());
		}
	}

}
