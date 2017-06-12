package com.accenture.ams.accountmgmtservice.crmAccountMgmt;

import java.math.BigDecimal;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.datatype.XMLGregorianCalendar;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;

import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.AccountProduct;

import com.accenture.ams.db.bean.AccountDeviceAMS;
import com.accenture.ams.db.bean.AccountProductBean;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.AccountUser;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.db.bean.CrmAccount;
import com.accenture.ams.db.bean.DeviceIdType;
import com.accenture.ams.db.bean.StatusType;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.db.util.crypto.MD5Utils;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.DbErrorException;
import com.accenture.ams.accountmgmtservice.GenericException;
import com.accenture.ams.accountmgmtservice.MissingParameterException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile.InsertCrmAccCommProfileManager;
import com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile.UpdateComProfileManager;
import com.accenture.ams.accountmgmtservice.updatePwdService.UpdateUserPasswordService;

import commontypes.accountmgmt.avs.accenture.CrmAccountDeviceType;
import commontypes.accountmgmt.avs.accenture.CrmAccountDevicesListType;
import commontypes.accountmgmt.avs.accenture.ExtendedRatingType;
import commontypes.accountmgmt.avs.accenture.FlagTypeMF;
import commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import commontypes.accountmgmt.avs.accenture.PayloadType;
import commontypes.accountmgmt.avs.accenture.UserPcExtendedRatingsListType;

public class InsertCrmAccount implements ActionExecutor {

	private PayloadType data = null;
	private ArrayList<AccountAttribute> accAttrs = new ArrayList<AccountAttribute>();
	private ArrayList<AccountProductBean> accProductList = new ArrayList<AccountProductBean>();
	private ArrayList<AccountTechnicalPkg> listOfAccTechPack = new ArrayList<AccountTechnicalPkg>();

	AccountUserBean au = new AccountUserBean();

	// Values used by more than 1 private methods. Store them at class level
	Double intermediate = null;
	String userName = null;
	String name = null;
	String crmAccountId = null;
	boolean blockingFlag = false;
	boolean blockingAlertFlag = false;
	boolean blockingConsumptionFlag = false;
	boolean blockingEnabledFlag = false;
	private String tenantName = null;

	public InsertCrmAccount(String _tenantName) {
		tenantName = _tenantName;
	}

	public AccountMgmtResponse execute() throws MissingParameterException,
			DbErrorException, GenericException {

		name = data.getName();
		crmAccountId = data.getCrmAccountId();
		userName = data.getUsername();
		LogUtil.writeCommonLog("INFO", InsertCrmAccount.class, "WS",
				"I'm in InsertCrmAccount.execute for crmAccountId: "
						+ crmAccountId);
		try {
			/* Check if crmAccountId is alredy present */
			boolean isPresent = AccountManagementDAO.isCrmAccountIdPresent(
					crmAccountId, tenantName);
			if (isPresent)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_USER_PRESENT, null);
		} catch (Exception e) {
			LogUtil.writeErrorLog(InsertCrmAccount.class, "DB ERROR: ", e);
			throw new DbErrorException(e.getMessage());
		}

		CrmAccount acc = null;
		AccountUserBean au = null;
		ArrayList<AccountDeviceAMS> ad = null;
		try {
			/* Create CrmAccount bean */
			acc = getCrmAccountBean();
			/* Create AccountUserBean */
			au = getAccountUserBean();
			/* Create AccountDevice bean */
			ad = getAccountDeviceBean();
			/* Create AccountAttribute bean */
			createAccountAttributes();
		} catch (MissingParameterException e) {
			LogUtil.writeErrorLog(InsertCrmAccount.class,
					"Missing Parameter: ", e);
			throw new MissingParameterException(e.getMessage());
		} catch (Exception e) {
			LogUtil.writeErrorLog(InsertCrmAccount.class, "Error: ", e);
			throw new DbErrorException(e.getMessage());
		}

		try {
			/*
			 * Populate tables CRM_ACCOUNT, ACCOUNT_USER, ACCOUNT_ATTRIBUTE,
			 * ACCOUNT_DEVICE
			 */
			boolean insertResult = AccountManagementDAO.insertUpdateCrmAccount(
					acc, au, accAttrs, ad, null, null, tenantName);

			if (!insertResult)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, null);

			/* Create AccountProduct bean AND listOfAccTechPack bean */
			UpdateComProfileManager helper = new UpdateComProfileManager(data
					.getUpdateCommProfileList().getUpdateCommProfOp(),
					crmAccountId, au.getUsername(), tenantName);

			try {
				helper.doInsert();
			} catch (Exception e) {
				LogUtil.writeErrorLog(InsertCrmAccount.class,
						"Error during techpackage insert : ", e);
				throw new DbErrorException(e.getMessage());
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(InsertCrmAccount.class, "DB ERROR: ", e);
			throw new DbErrorException(e.getMessage());
		}

		return Utils.getResponse(AccountMgmtServiceConstant.RET_OK, null);
	}

	private CrmAccount getCrmAccountBean() throws MissingParameterException {
		CrmAccount acc = new CrmAccount();

		// crmAccountId
		acc.setCrmaccountid(crmAccountId);
		LogUtil.writeCommonLog("INFO", InsertCrmAccount.class, "WS",
				"I'm in InsertCrmAccount.getCrmAccountBean for crmAccountId: "
						+ crmAccountId);
		// userType
		String userType = data.getUserType();

		if (userType
				.equalsIgnoreCase(AccountMgmtServiceConstant.USER_TYPE_DEFAULT)) {
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_ISQA, "N");
			acc.setVip("N");
		} else if (userType
				.equalsIgnoreCase(AccountMgmtServiceConstant.USER_TYPE_QA)) {
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_ISQA, "Y");
			acc.setVip("N");
		} else if (userType
				.equalsIgnoreCase(AccountMgmtServiceConstant.USER_TYPE_VIP)) {
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_ISQA, "N");
			acc.setVip("Y");
		}
		addAccountAttribute(AccountMgmtServiceConstant.ATTR_CRM_ACC_TYPE,
				userType);
		// username
		// acc.setUsername( Long.parseLong(userName));
		// name
		acc.setName(name);
		// surname
		acc.setSurname(data.getSurname());
		// birthDate
		XMLGregorianCalendar gc = data.getBirthDate();
		if (gc != null) {
			java.sql.Timestamp ts = new java.sql.Timestamp(data.getBirthDate()
					.toGregorianCalendar().getTimeInMillis());
			acc.setBirthDate(ts);
		} else
			acc.setBirthDate(null);
		// gender ( sesso )
		FlagTypeMF sesso = data.getGender();
		if (sesso != null)
			acc.setSesso(data.getGender().value());
		else
			acc.setSesso(null);
		// zipCode
		acc.setCap(data.getCrmAccountZipCode());
		// email
		String eMail = data.getEmail();
		if (eMail != null && eMail.length() > 0) {
			if (!Utils.isValidEmailAddress(eMail))
				throw new MissingParameterException(
						"Email address is not valid!!");
		}
		acc.setEmail(eMail);
		// mobileNumber
		String mobilePhone = data.getCrmAccountMobileNumber();

		if (mobilePhone != null && mobilePhone.length() < 10
				&& mobilePhone.length() > 16)
			throw new MissingParameterException(
					"Mobile Phone Number is incorrect!!");

		acc.setMobilePhone(mobilePhone);

		return acc;
	}

	private AccountUserBean getAccountUserBean()
			throws MissingParameterException, DbErrorException,
			GenericException {

		// userStatus
		Integer userType = data.getUserStatus();
		LogUtil.writeCommonLog("INFO", InsertCrmAccount.class, "WS",
				"I'm in InsertCrmAccount.getAccountUserBean for crmAccountId: "
						+ crmAccountId);
		if (userType != null) {
			try {
				if (!AccountManagementDAO.isValidStatusId(userType.longValue(),
						tenantName))
					throw new MissingParameterException(
							"UserStatus value is not valid");
			} catch (NumberFormatException e) {
				LogUtil.writeErrorLog(InsertCrmAccount.class,
						"UserStatus value is not valid: ", e);
				throw new GenericException("UserStatus value is not valid");
			} catch (Exception e) {
				LogUtil.writeErrorLog(InsertCrmAccount.class,
						"UserStatus value is not valid: ", e);
				throw new DbErrorException("UserStatus value is not valid");

			}
			au.setStatusId(userType.longValue());
		} else
			au.setStatusId(null);

		// name
		if (userName != null || !userName.isEmpty())
			au.setName(userName);

		// userName
		// au.setUsername(Long.parseLong(userName));
		// sequence da generare
		// blocco acquisti
		FlagTypeYN bf = data.getCrmAccountPurchaseBlockingFlag();
		if (bf != null) {
			String val = bf.value();
			au.setBloccoAcquisti(bf.value());
			if (val.equalsIgnoreCase("Y"))
				blockingFlag = true;
		} else
			au.setBloccoAcquisti("N");

		// threshold blocking value
		if (blockingFlag) {
			BigDecimal pBlockT = data
					.getCrmAccountPurchaseBlockingThresholdValue();
			if (pBlockT != null)
				au.setPurchaseThresholdBlocking(pBlockT.doubleValue());
			else
				throw new MissingParameterException(
						"CrmAccountPurchaseBlockingThresholdValue cannot be null if AccountPurchaseBlockingFlag values is Y");
		}
		// purchaseAlerting
		BigDecimal pBlockIntermediate = data
				.getCrmAccountPurchaseBlockingIntermediateThreshold();

		if (pBlockIntermediate == null || pBlockIntermediate == BigDecimal.ZERO) {
			Double pBlocking = au.getPurchaseThresholdBlocking();
			intermediate = pBlocking * 0.8;
		} else {
			intermediate = pBlockIntermediate.doubleValue();
		}
		au.setPurchaseAlerting(intermediate);
		// CrmAccountConsumptionBlockingThresholdValue
		Integer val = data.getCrmAccountConsumptionBlockingThresholdValue();
		if (val != null)
			au.setConsumptionThreshold(val);
		// CrmAccountPurchaseValue
		BigDecimal pVal = data.getCrmAccountPurchaseValue();
		if (pVal != null)
			au.setPurchaseValue(pVal.doubleValue());
		// CrmAccountConsumption
		Integer accConsVal = data.getCrmAccountConsumption();
		if (accConsVal != null)
			au.setConsumption(accConsVal);
		// first login date
		au.setDataPrimoAccesso(new Timestamp(System.currentTimeMillis()));
		au.setCrmaccountid(crmAccountId);

		return au;
	}

	private ArrayList<AccountDeviceAMS> getAccountDeviceBean()
			throws MissingParameterException, DbErrorException {
		CrmAccountDevicesListType accDevListType = data.getCrmAccountDevices();

		if (accDevListType == null)
			return null;

		List<CrmAccountDeviceType> accDevList = accDevListType
				.getCrmAccountDevice();
		Iterator<CrmAccountDeviceType> itDevList = accDevList.iterator();
		ArrayList<AccountDeviceAMS> listOfBean = new ArrayList<AccountDeviceAMS>();

		while (itDevList.hasNext()) {
			CrmAccountDeviceType item = itDevList.next();

			String crmAccDeviceId = item.getCrmAccountDeviceId();
			int crmAccDeviceType = -1;
			crmAccDeviceType = item.getCrmAccountDeviceIdType();
			// if crmAccountDeviceId is valorized, crmAccDeviceType must be
			// valorized too
			if (crmAccDeviceType == -1)
				throw new MissingParameterException(
						"crmAccDeviceType must be valorized");

			String str = Integer.toString(crmAccDeviceType);
			try {
				if (!AccountManagementDAO.isDeviceTypeIdValid(str, tenantName))
					throw new MissingParameterException("'" + str
							+ "' is not valid deviceId type");
				if (AccountManagementDAO.isDeviceIdPresent(crmAccDeviceId,
						null, tenantName))
					throw new MissingParameterException("'" + crmAccDeviceId
							+ "' is used by another device");

			} catch (Exception e) {
				LogUtil.writeErrorLog(InsertCrmAccount.class, "DB ERROR: ", e);
				throw new DbErrorException(e.getMessage());
			}

			String channel = item.getChannel();

			try {
				if (!Utils.isValorized(channel)
						|| !AccountManagementDAO.isChannelPresent(channel,
								tenantName))
					throw new MissingParameterException(
							"Channel must be valorized");
			} catch (Exception e) {
				LogUtil.writeErrorLog(InsertCrmAccount.class, "DB ERROR: ", e);
				throw new DbErrorException(e.getMessage());
			}

			AccountDeviceAMS accDevBean = new AccountDeviceAMS();
			accDevBean.setDeviceId(crmAccDeviceId);
			accDevBean.setPlatformId(channel);
			accDevBean.setType(Utils.intToLong(crmAccDeviceType));

			listOfBean.add(accDevBean);
		}
		return listOfBean;
	}

	private void createAccountAttributes() throws MissingParameterException,
			DbErrorException, Exception {
		// password
		String pwd = data.getPassword();
		if (Utils.isValorized(userName) && !Utils.isValorized(pwd))
			throw new MissingParameterException(
					"Password must be valorized if userName is present");

		String hashPassword;
		try {
			hashPassword = MD5Utils.getHashPassword(pwd);
		} catch (NoSuchAlgorithmException e1) {
			LogUtil.writeErrorLog(InsertCrmAccount.class, "ERROR: ", e1);
			throw new Exception(e1.getMessage());
		}
		addAccountAttribute(AccountMgmtServiceConstant.ATTR_USER_PWD,
				hashPassword);

		// userCountry
		String userCountry = data.getUserCountry();
		if (Utils.isValorized(userCountry)) {
			if (!AccountManagementDAO.isCountryLangCodeValide(userCountry,
					tenantName))
				throw new MissingParameterException("Country code : '"
						+ userCountry + "' is incorrect!!");
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_COUNTRY,
					userCountry);
		}

		// userLanguage
		String userLanguage = data.getUserLanguage();
		if (Utils.isValorized(userLanguage)) {
			if (!AccountManagementDAO.isCountryLangCodeValide(userLanguage,
					tenantName))
				throw new MissingParameterException("Language code : '"
						+ userLanguage + "' is incorrect!!");
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_USER_LANG,
					userLanguage);
		}

		// QualitySetting
		String quality = data.getQualitySetting();
		if (Utils.isValorized(quality))
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_BANDWITH,
					quality);
		else
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_BANDWITH, "1");

		// UserPaymentMethod
		/*
		 * TODO : Check if PaymentMethod’s values match with PAYMENT_TYPE_ID
		 * column’s values of PAYMENT_TYPE
		 */
		Integer userPayMethod = null;
		userPayMethod = data.getUserPaymentMethod();

		if (userPayMethod != null) {
			if (!AccountManagementDAO.isPaymentMethodPresent(
					userPayMethod.longValue(), tenantName))
				throw new MissingParameterException("Payment Method : '"
						+ userPayMethod + "' is not present in platform");
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_PAY_TYPE,
					Integer.toString(userPayMethod));
		}

		// UserRememberPinFlag
		FlagTypeYN rPin = data.getUserRememberPinFlag();
		if (rPin != null && rPin.value().equalsIgnoreCase("Y"))
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_REMEMBER_PIN,
					rPin.value());
		else
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_REMEMBER_PIN,
					"N");

		// UserPcLevel
		Integer userPcLev = null;
		userPcLev = data.getUserPcLevel();
		if (userPcLev != null) {
			if (!AccountManagementDAO.isPcLevelCodePresent(
					userPcLev.longValue(), tenantName))
				throw new MissingParameterException("PC Level : '" + userPcLev
						+ "' is not present in platform");
			addAccountAttribute(AccountMgmtServiceConstant.ATTR_USER_PC_LEVEL,
					Integer.toString(userPcLev));
		}
		// UserPinPurchase
		String userPinPurch = data.getUserPinPurchase();
		if (Utils.isValorized(userPinPurch)) {
			String hashUserPinPurch;
			try {
				hashUserPinPurch = MD5Utils.getHashPassword(userPinPurch);
				addAccountAttribute(
						AccountMgmtServiceConstant.ATTR_PIN_PURCHASE,
						hashUserPinPurch);
			} catch (NoSuchAlgorithmException e1) {
				LogUtil.writeErrorLog(InsertCrmAccount.class, "ERROR: ", e1);
				throw new Exception(e1.getMessage());
			}
		}
		// UserPinParentalControl
		String userPinPC = data.getUserPinParentalControl();
		if (Utils.isValorized(userPinPC)) {
			String hashUserPinPC;
			try {
				hashUserPinPC = MD5Utils.getHashPassword(userPinPC);
				addAccountAttribute(
						AccountMgmtServiceConstant.ATTR_PARENTAL_CONTROL_PIN,
						hashUserPinPC);
			} catch (NoSuchAlgorithmException e1) {
				LogUtil.writeErrorLog(InsertCrmAccount.class, "ERROR: ", e1);
				throw new Exception(e1.getMessage());
			}
		}

		// UserPcExtendedRatings
		/*
		 * TODO : Check if UserPcExtendedRatings’s values match with VALUE
		 * column’s values of PC_EXTENDED_RATING table
		 */
		String userPcExtRat = "";
		UserPcExtendedRatingsListType listOfExtendRating = data
				.getUserPcExtendedRatings();
		if (listOfExtendRating != null) {
			List<ExtendedRatingType> ratList = listOfExtendRating
					.getUserPcExtendedRating();
			Iterator<ExtendedRatingType> itList = ratList.iterator();
			while (itList.hasNext()) {
				String nextRating = itList.next().value();
				userPcExtRat = userPcExtRat + nextRating + ";";
			}
		}
		// remove last ";"
		if (userPcExtRat.length() > 0) {
			userPcExtRat = userPcExtRat.substring(0, userPcExtRat.length() - 1);
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_PC_EXTEND_RATING,
					userPcExtRat);
		}
		if (blockingFlag) {
			// CrmAccountPurchaseBlockingThresholdCurrency
			String curr = data.getCrmAccountPurchaseBlockingThresholdCurrency();
			if (!AccountManagementDAO.isCurrencyCodeValid(curr, tenantName))
				throw new MissingParameterException(curr
						+ " is not a valid code");
			// CrmAccountPurchaseBlockingThresholdPeriod
			String cbp = data.getCrmAccountPurchaseBlockingThresholdPeriod();

			if (!Utils.isValorized(curr))
				throw new MissingParameterException(
						"CrmAccountPurchaseBlockingThresholdCurrency is required if the CrmAccountPurchaseBlockingFlag value is Y");
			if (!Utils.isValorized(cbp))
				throw new MissingParameterException(
						"CrmAccountPurchaseBlockingThresholdPeriod is required if the CrmAccountPurchaseBlockingFlag value is Y");

			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_THRES_CURR,
					curr);
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_THRES_PRD,
					cbp);
		}

		// CrmAccountPurchaseBlockingAlertEnabledFlag
		FlagTypeYN pbAlertFlag = data
				.getCrmAccountPurchaseBlockingAlertEnabledFlag();
		if (pbAlertFlag != null && pbAlertFlag.value().equalsIgnoreCase("Y")) {
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_FLAG,
					pbAlertFlag.value());
			blockingAlertFlag = true;
		} else
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_FLAG,
					"N");

		if (blockingAlertFlag) {
			// CrmAccountPurchaseBlockingAlertChannel
			String alertCh = data.getCrmAccountPurchaseBlockingAlertChannel();
			// CrmAccountPurchaseBlockingAlertMode
			String alMode = data.getCrmAccountPurchaseBlockingAlertMode();

			if (!Utils.isValorized(alertCh))
				throw new MissingParameterException(
						"CrmAccountPurchaseBlockingAlertChannel is required if the CrmAccountPurchaseBlockingAlertEnabledFlag value is Y");
			if (!Utils.isValorized(alMode))
				throw new MissingParameterException(
						"CrmAccountPurchaseBlockingAlertMode is required if the CrmAccountPurchaseBlockingAlertEnabledFlag value is Y");

			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_ALERT_CH,
					alertCh);
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_ALERT_MODE,
					alMode);
		}

		// CrmAccountPurchaseBlockingIntermediateThreshold
		addAccountAttribute(
				AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_INT_THRES,
				Double.toString(intermediate));

		// CrmAccountConsumptionBlockingFlag
		FlagTypeYN bFlag = data.getCrmAccountConsumptionBlockingFlag();
		if (bFlag != null && bFlag.value().equalsIgnoreCase("Y")) {
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_FLAG,
					bFlag.value());
			blockingConsumptionFlag = true;
		} else
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_FLAG,
					"N");

		if (blockingConsumptionFlag) {
			// CrmAccountConsumptionBlockingThresholdValue
			Integer bThreVal = null;
			bThreVal = data.getCrmAccountConsumptionBlockingThresholdValue();
			// CrmAccountConsumptionBlockingThresholdPeriod
			String cPeriod = data
					.getCrmAccountConsumptionBlockingThresholdPeriod();

			if (bThreVal == null)
				throw new MissingParameterException(
						"CrmAccountConsumptionBlockingThresholdValue is required if the CrmAccountConsumptionBlockingFlag value is Y");
			if (!Utils.isValorized(cPeriod))
				throw new MissingParameterException(
						"CrmAccountConsumptionBlockingThresholdPeriod is required if the CrmAccountConsumptionBlockingFlag value is Y");

			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_THRES_VAL,
					Integer.toString(bThreVal));
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_THRES_PRD,
					cPeriod);
		}

		// CrmAccountConsumptionBlockingEnabledFlag
		FlagTypeYN blFlag = data.getCrmAccountConsumptionBlockingEnabledFlag();
		if (blFlag != null && blFlag.value().equalsIgnoreCase("Y")) {
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_FLAG,
					blFlag.value());
			blockingEnabledFlag = true;
		} else
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_FLAG,
					"N");

		if (blockingEnabledFlag) {
			// CrmAccountConsumptionBlockingAlertChannel
			String alChan = data.getCrmAccountConsumptionBlockingAlertChannel();
			// CrmAccountConsumptionBlockingAlertMode
			String cbam = data.getCrmAccountConsumptionBlockingAlertMode();

			if (!Utils.isValorized(alChan))
				throw new MissingParameterException(
						"CrmAccountConsumptionBlockingAlertChannel is required if the ConsumptionBlockingAlertEnableFlag value is Y");

			if (!Utils.isValorized(cbam))
				throw new MissingParameterException(
						"CrmAccountConsumptionBlockingAlertMode is required if the ConsumptionBlockingAlertEnableFlag value is Y");

			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_CH,
					alChan);
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_MODE,
					cbam);
		}

		// CrmAccountConsumptionBlockingIntermediateThreshold
		Integer bitFlag = null;
		bitFlag = data.getCrmAccountConsumptionBlockingIntermediateThreshold();
		if (bitFlag != null)
			addAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_INT_THRES,
					Integer.toString(bitFlag));

		// CrmAccountRetailerDomain
		String retailerId = data.getCrmAccountRetailerDomain();
		if (!Utils.isValorized(retailerId))
			throw new MissingParameterException("RetailerId cannot be null");

		try {
			boolean isRetailerIdOk = AccountManagementDAO
					.isReatilerDomainPresent(retailerId, tenantName);
			if (!isRetailerIdOk)
				throw new MissingParameterException("RetailerId : '"
						+ retailerId + "' is not present in platform");
		} catch (Exception e) {
			LogUtil.writeErrorLog(InsertCrmAccount.class, "DB ERROR: ", e);
			throw new DbErrorException(e.getMessage());
		}
		addAccountAttribute(AccountMgmtServiceConstant.ATTR_RETAILER_ID,
				retailerId);
	}

	private void addAccountAttribute(int attrName, String attrVal) {
		AccountAttribute attr = new AccountAttribute();
		// AttributeDetail ad = new AttributeDetail();
		// ad.setAttributeDetailId(Utils.intToLong(attrName));
		// attr.setUserId( Long.parseLong(userName) );
		attr.setAttributeValue(attrVal);
		attr.setAttributeDetailId(Utils.intToLong(attrName));
		accAttrs.add(attr);
	}

	// @Override
	public void setPayload(Object pt) {
		data = (PayloadType) pt;
	}
}
