package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.avs.CrmAccountDeviceBean;
import com.accenture.sdp.csmac.beans.party.AvsPartyBean;
import com.accenture.sdp.csmac.beans.party.SdpPartyBean;
import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.CrmAccountDeviceType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.ExtendedRatingType;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.GetSdpAccountProfileDataResponse;
import com.accenture.sdp.csmfe.webservices.avs.clients.accountmgmt.GetSdpPartyProfileDataResponse;
import com.accenture.sdp.csmfe.webservices.clients.credential.CredentialInfoResp;

public abstract class AvsPartyConverter {

	private static LoggerWrapper log = new LoggerWrapper(AvsPartyConverter.class);

	public static AvsPartyBean convertChildPartyInfoToAvsBean(SdpPartyBean info, GetSdpPartyProfileDataResponse partyProfile,
			GetSdpAccountProfileDataResponse accountProfile, CredentialInfoResp credentialInfo) {
		AvsPartyBean bean = new AvsPartyBean();

		// GET ATTIBUTES FROM SDP_PARTY
		bean.setId(info.getId());
		bean.setCrmAccountId(info.getName());
		// FIXME adattamento degli statusId
		switch (info.getStatusId().intValue()) {
		case 2:
			bean.setUserStatus(1);
			break;
		case 4:
			bean.setUserStatus(3);
			break;
		default:
			bean.setUserStatus(info.getStatusId().intValue());
		}
		bean.setStatusName(info.getStatusName());

		bean.setFirstName(info.getFirstName());
		bean.setLastName(info.getLastName());
		bean.setBirthDate(info.getBirthDate());
		bean.setGender(info.getGender());
		bean.setCrmAccountZipCode(info.getZipCode());
		bean.setEmail(info.getEmail());
		bean.setCrmAccountMobileNumber(info.getPhoneNumber());
		bean.setUserCountry(info.getCountry());

		// GET ATTRIBUTES FROM PARTY PROFILE
		if (partyProfile != null) {
			setupAvsPartyInfo(bean, partyProfile);
		}

		// GET ATTRIBUTES FROM ACCOUNT PROFILE

		if (accountProfile != null) {
			setupAvsPartyInfo(bean, accountProfile);
		}

		// GET ATTRIBUTES FROM CREDENTIAL

		if (credentialInfo != null) {
			setupAvsPartyInfo(bean, credentialInfo);
		}

		return bean;
	}

	public static void setupAvsPartyInfo(AvsPartyBean bean, GetSdpPartyProfileDataResponse partyProfile) {
		if (partyProfile.getUserType() != null) {
			try {
				bean.setUserType(Integer.parseInt(partyProfile.getUserType()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		bean.setUserLanguage(partyProfile.getUserLanguage());
		if (partyProfile.getQualitySetting() != null) {
			try {
				bean.setQualitySetting(Integer.parseInt(partyProfile.getQualitySetting()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		if (partyProfile.getUserPaymentMethod() != null) {
			bean.setUserPaymentMethod(partyProfile.getUserPaymentMethod());
		}
		if (partyProfile.getCrmAccountPurchaseBlockingFlag() != null) {
			bean.setCrmAccountPurchaseBlockingFlag(partyProfile.getCrmAccountPurchaseBlockingFlag().value());
		}
		bean.setCrmAccountPurchaseBlockingThresholdCurrency(partyProfile.getCrmAccountPurchaseBlockingThresholdCurrency());
		bean.setCrmAccountPurchaseBlockingThresholdValue(partyProfile.getCrmAccountPurchaseBlockingThresholdValue());
		if (partyProfile.getCrmAccountPurchaseBlockingThresholdPeriod() != null) {
			try {
				bean.setCrmAccountPurchaseBlockingThresholdPeriod(Integer.parseInt(partyProfile.getCrmAccountPurchaseBlockingThresholdPeriod()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		bean.setCrmAccountPurchaseBlockingAlertEnabledFlag(partyProfile.getCrmAccountPurchaseBlockingAlertEnabledFlag().value());
		if (partyProfile.getCrmAccountPurchaseBlockingAlertChannel() != null) {
			try {
				bean.setCrmAccountPurchaseBlockingAlertChannel(Integer.parseInt(partyProfile.getCrmAccountPurchaseBlockingAlertChannel()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		if (partyProfile.getCrmAccountPurchaseBlockingAlertMode() != null) {
			try {
				bean.setCrmAccountPurchaseBlockingAlertMode(Integer.parseInt(partyProfile.getCrmAccountPurchaseBlockingAlertMode()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		bean.setCrmAccountPurchaseBlockingIntermediateThreshold(partyProfile.getCrmAccountPurchaseBlockingIntermediateThreshold());
		if (partyProfile.getCrmAccountConsumptionBlockingFlag() != null) {
			bean.setCrmAccountConsumptionBlockingFlag(partyProfile.getCrmAccountConsumptionBlockingFlag().value());
		}
		if (partyProfile.getCrmAccountConsumptionBlockingThresholdValue() != null) {
			bean.setCrmAccountConsumptionBlockingThresholdValue(partyProfile.getCrmAccountConsumptionBlockingThresholdValue());
		}
		if (partyProfile.getCrmAccountConsumptionBlockingThresholdPeriod() != null) {
			try {
				bean.setCrmAccountConsumptionBlockingThresholdPeriod(Integer.parseInt(partyProfile.getCrmAccountConsumptionBlockingThresholdPeriod()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		if (partyProfile.getCrmAccountConsumptionBlockingEnabledFlag() != null) {
			bean.setCrmAccountConsumptionBlockingEnabledFlag(partyProfile.getCrmAccountConsumptionBlockingEnabledFlag().value());
		}
		if (partyProfile.getCrmAccountConsumptionBlockingAlertChannel() != null) {
			try {
				bean.setCrmAccountConsumptionBlockingAlertChannel(Integer.parseInt(partyProfile.getCrmAccountConsumptionBlockingAlertChannel()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		if (partyProfile.getCrmAccountConsumptionBlockingAlertMode() != null) {
			try {
				bean.setCrmAccountConsumptionBlockingAlertMode(Integer.parseInt(partyProfile.getCrmAccountConsumptionBlockingAlertMode()));
			} catch (Exception e) {
				// dovrebbe essere un int, ma ce lo danno string...
				log.logError(e);
			}
		}
		if (partyProfile.getCrmAccountConsumptionBlockingIntermediateThreshold() != null) {
			bean.setCrmAccountConsumptionBlockingIntermediateThreshold(partyProfile.getCrmAccountConsumptionBlockingIntermediateThreshold());
		}
		bean.setCrmAccountPurchaseValue(partyProfile.getCrmAccountPurchaseValue());
		bean.setCrmAccountConsumption(partyProfile.getCrmAccountConsumption());

	}

	public static void setupAvsPartyInfo(AvsPartyBean bean, GetSdpAccountProfileDataResponse accountProfile) {
		List<CrmAccountDeviceBean> crmAccountDevicelist = new ArrayList<CrmAccountDeviceBean>();

		if (accountProfile.getCrmAccountDevices() != null && accountProfile.getCrmAccountDevices().getCrmAccountDevice() != null) {
			for (CrmAccountDeviceType i : accountProfile.getCrmAccountDevices().getCrmAccountDevice()) {
				CrmAccountDeviceBean device = new CrmAccountDeviceBean();
				device.setChannel(i.getChannel());
				device.setCrmAccountDeviceId(i.getCrmAccountDeviceId());
				device.setCrmAccountDeviceIdType(i.getCrmAccountDeviceIdType());
				crmAccountDevicelist.add(device);
			}
		}
		bean.setCrmAccountDeviceList(crmAccountDevicelist);

		if (accountProfile.getUserRememberPinFlag() != null) {
			bean.setUserRememberPinFlag(accountProfile.getUserRememberPinFlag().value());
		}
		if (accountProfile.getUserPcLevel() != null) {
			bean.setUserPcLevel(accountProfile.getUserPcLevel());
		}
		bean.setCrmAccountRetailerDomain(accountProfile.getRetailerDomain());
		bean.setUserPinPurchase(accountProfile.getUserPinPurchase());
		bean.setUserPinParentalControl(accountProfile.getUserPinParentalControl());
		List<String> userPcExtendedRatings = new ArrayList<String>();
		if (accountProfile.getUserPcExtendedRatings() != null && accountProfile.getUserPcExtendedRatings().getUserPcExtendedRating() != null) {
			for (ExtendedRatingType i : accountProfile.getUserPcExtendedRatings().getUserPcExtendedRating()) {
				userPcExtendedRatings.add(i.value());
			}
		}
		bean.setUserPcExtendedRatings(userPcExtendedRatings);
	}

	public static void setupAvsPartyInfo(AvsPartyBean bean, CredentialInfoResp credentialInfoResp) {
		bean.setUsername(credentialInfoResp.getUsername());
	}
}
