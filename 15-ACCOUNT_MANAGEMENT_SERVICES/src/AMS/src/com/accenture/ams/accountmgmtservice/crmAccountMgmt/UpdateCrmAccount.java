package com.accenture.ams.accountmgmtservice.crmAccountMgmt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;

import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.AccountDeviceAMS;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.db.bean.CrmAccount;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.DbErrorException;
import com.accenture.ams.accountmgmtservice.GenericException;
import com.accenture.ams.accountmgmtservice.MissingParameterException;
import com.accenture.ams.accountmgmtservice.Utils;

import commontypes.accountmgmt.avs.accenture.CrmAccountDeviceType;
import commontypes.accountmgmt.avs.accenture.CrmAccountDevicesListType;
import commontypes.accountmgmt.avs.accenture.ExtendedRatingType;
import commontypes.accountmgmt.avs.accenture.FlagTypeMF;
import commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import commontypes.accountmgmt.avs.accenture.PayloadType;
import commontypes.accountmgmt.avs.accenture.UserPcExtendedRatingsListType;

public class UpdateCrmAccount implements ActionExecutor {

	private PayloadType data = null;
	private ArrayList<AccountAttribute> accAttrs = new ArrayList<AccountAttribute>();
	AccountUserBean au = null;
	private ArrayList<AccountDeviceAMS> ad = null;
	private String tenantName = null;
	Double intermediate = null;

	public UpdateCrmAccount(String _tenantName) {
		tenantName = _tenantName;
	}

	public AccountMgmtResponse execute() throws MissingParameterException,
			DbErrorException, GenericException {
		/* 1 - Check if crmAccountId is alredy present */
		// String crmAccountId = data.getCrmAccountId();
		String userName = data.getUsername();
		String crmAccountId = data.getCrmAccountId();
		try {
			CrmAccount currAccount = null;
			currAccount = AccountManagementDAO.getCrmAccount(crmAccountId,
					tenantName);
			LogUtil.writeCommonLog("INFO", UpdateCrmAccount.class, "WS",
					"I'm in UpdateCrmAccount.execute for crmAccountId: "
							+ crmAccountId);

			if (currAccount == null)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_REQ_NOT_ALLOWED, null);

			au = AccountManagementDAO.getAccountUserBean(
					currAccount.getUsername(), tenantName);

			ad = getAccountDeviceBean(au.getUsername());

			accAttrs = (ArrayList<AccountAttribute>) createAccountAttributes(au
					.getUsername());

			CrmAccount newAccount = updateFieldsCrmAccount(currAccount
					.getCrmaccountid());

			au = updateFieldsAccAttribure(au);
			
			boolean insertResult = AccountManagementDAO.insertUpdateCrmAccount(
					newAccount, au, accAttrs, ad, null, null, tenantName);

			if (!insertResult)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, null);
		} catch (MissingParameterException e) {
			LogUtil.writeErrorLog(UpdateCrmAccount.class,
					"Missing Parameter: ", e);
			return Utils.getResponse(
					AccountMgmtServiceConstant.RET_MISSING_PARAM,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.writeErrorLog(UpdateCrmAccount.class, "ERROR: ", e);
			return Utils.getResponse(
					AccountMgmtServiceConstant.RET_GENERIC_ERROR, null);
		}
		return Utils.getResponse(AccountMgmtServiceConstant.RET_OK, null);

	}

	private ArrayList<AccountDeviceAMS> getAccountDeviceBean(Long userId)
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
						userId, tenantName))
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

	/**
	 * This method checks if there is a new field to edit
	 * 
	 * @param crmAccountId
	 * @return CrmAccount Modified
	 * @throws DbErrorException
	 */
	private CrmAccount updateFieldsCrmAccount(String crmAccountId)
			throws DbErrorException {

		CrmAccount crmAcc = null;
		try {
			crmAcc = AccountManagementDAO.getCrmAccount(crmAccountId,
					tenantName);

			if (crmAcc == null)
				throw new DbErrorException(
						"Error retriving crm account for crm account id "
								+ crmAccountId);

			// Email
			String email = data.getEmail();
			if (email != null && !email.isEmpty())
				crmAcc.setEmail(email);

			// Name
			String name = null;
			name = data.getName();
			if (name != null && !name.isEmpty())
				crmAcc.setName(name);

			// Surname
			String surname = data.getSurname();
			if (surname != null && !surname.isEmpty())
				crmAcc.setSurname(surname);

			// BirthDate
			XMLGregorianCalendar birthDate = data.getBirthDate();
			if (birthDate != null)
				crmAcc.setBirthDate(new java.sql.Timestamp(data.getBirthDate()
						.toGregorianCalendar().getTimeInMillis()));

			// Gender
			FlagTypeMF gender = data.getGender();
			if (gender != null && !gender.value().isEmpty())
				crmAcc.setSesso(gender.value());

			// CrmAccountMobileNumber
			String mobileNumber = data.getCrmAccountMobileNumber();
			if (mobileNumber != null && !mobileNumber.isEmpty())
				crmAcc.setMobilePhone(mobileNumber);

			// CrmAccountZipCode
			String zipCode = data.getCrmAccountZipCode();
			if (zipCode != null && !zipCode.isEmpty())
				crmAcc.setCap(zipCode);

		} catch (Exception e) {
			LogUtil.writeErrorLog(UpdateCrmAccount.class, "ERROR: ", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return crmAcc;

	}

	
	private AccountUserBean updateFieldsAccAttribure(AccountUserBean aUser){
		
		//CrmAccountPurchaseBlockingFlag
		if ( data.getCrmAccountPurchaseBlockingFlag()!=null )
			aUser.setBloccoAcquisti( data.getCrmAccountPurchaseBlockingFlag().value() );
		
		//CrmAccountPurchaseBlockingThresholdValue
		if ( data.getCrmAccountPurchaseBlockingThresholdValue()!=null)
			aUser.setPurchaseThresholdBlocking(data.getCrmAccountPurchaseBlockingThresholdValue().doubleValue());
		
		//CrmAccountPurchaseValue
		if ( data.getCrmAccountPurchaseValue()!=null)
			aUser.setPurchaseValue(data.getCrmAccountPurchaseValue().doubleValue());
		
		//CrmAccountConsumption
		if ( data.getCrmAccountConsumption() > 0)
			aUser.setConsumption(data.getCrmAccountConsumption());
		
		return aUser;
	}
	
	private List<AccountAttribute> createAccountAttributes(Long userId)
			throws DbErrorException {

		List<AccountAttribute> accAttrList;
		try {
			accAttrList = AccountManagementDAO.getAccountAttributesBean(userId,
					tenantName);
		} catch (Exception e1) {
			LogUtil.writeErrorLog(UpdateCrmAccount.class, "DB ERROR: ", e1);
			throw new DbErrorException(e1.getMessage());
		}

		if (accAttrList == null)
			throw new DbErrorException(
					"Error retriving acccount attribute for user " + userId);

		// QualitySetting
		String quality = data.getQualitySetting();
		if (quality != null && !quality.isEmpty())
			updateAccountAttribute(AccountMgmtServiceConstant.ATTR_BANDWITH,
					quality, accAttrList);
		else
			updateAccountAttribute(AccountMgmtServiceConstant.ATTR_BANDWITH,
					"1", accAttrList);

		// UserPaymentMethod
		Integer userPayMethod = null;
		userPayMethod = data.getUserPaymentMethod();
		if (userPayMethod != null)
			updateAccountAttribute(AccountMgmtServiceConstant.ATTR_PAY_TYPE,
					Integer.toString(userPayMethod), accAttrList);

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
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_PC_EXTEND_RATING,
					userPcExtRat, accAttrList);
		}
		
		// CrmAccountPurchaseBlockingThresholdCurrency
		String curr = data.getCrmAccountPurchaseBlockingThresholdCurrency();
		if (curr != null && !curr.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_THRES_CURR,
					curr, accAttrList);
		
		// CrmAccountPurchaseBlockingPeriod
		String blockPeriod = data.getCrmAccountPurchaseBlockingThresholdPeriod();
		if (blockPeriod != null && !blockPeriod.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_THRES_PRD,
					blockPeriod, accAttrList);		
		// CrmAccountPurchaseBlockingAlertEnabledFlag
		FlagTypeYN pbAlertFlag = data.getCrmAccountPurchaseBlockingFlag();
		if (pbAlertFlag != null && pbAlertFlag.value().equalsIgnoreCase("Y"))
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_FLAG,
					pbAlertFlag.value(), accAttrList);
		else
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_FLAG,
					"N", accAttrList);

		// CrmAccountPurchaseBlockingAlertChannel
		String alertCh = data.getCrmAccountPurchaseBlockingAlertChannel();
		if (alertCh != null && !alertCh.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_ALERT_CH,
					alertCh, accAttrList);

		// CrmAccountPurchaseBlockingAlertMode
		String alMode = data.getCrmAccountPurchaseBlockingAlertMode();
		if (alMode != null && !alMode.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_ALERT_MODE,
					alMode, accAttrList);

		// CrmAccountPurchaseBlockingIntermediateThreshold
		// addAccountAttribute(AccountMgmtServiceConstant.ATTR_CRM_ACC_PURC_BLOCK_INT_THRES,
		// Double.toString(intermediate));

		// CrmAccountConsumptionBlockingFlag
		FlagTypeYN bFlag = data.getCrmAccountConsumptionBlockingFlag();
		if (bFlag != null && bFlag.value().equalsIgnoreCase("Y"))
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_FLAG,
					bFlag.value(), accAttrList);
		else
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_FLAG,
					"N", accAttrList);

		// CrmAccountConsumptionBlockingThresholdValue
		Integer bThreVal = null;
		bThreVal = data.getCrmAccountConsumptionBlockingThresholdValue();
		if (bThreVal != null)
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_THRES_VAL,
					Integer.toString(bThreVal), accAttrList);

		// CrmAccountConsumptionBlockingThresholdPeriod
		String cPeriod = data.getCrmAccountConsumptionBlockingThresholdPeriod();
		if (cPeriod != null && !cPeriod.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_THRES_PRD,
					cPeriod, accAttrList);

		// CrmAccountConsumptionBlockingEnabledFlag
		FlagTypeYN blFlag = data.getCrmAccountConsumptionBlockingEnabledFlag();
		if (blFlag != null && blFlag.value().equalsIgnoreCase("Y"))
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_FLAG,
					blFlag.value(), accAttrList);
		else
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_FLAG,
					"N", accAttrList);

		// CrmAccountConsumptionBlockingAlertChannel
		String alChan = data.getCrmAccountConsumptionBlockingAlertChannel();
		if (alChan != null && !alChan.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_CH,
					alChan, accAttrList);

		// CrmAccountConsumptionBlockingAlertMode
		String cbam = data.getCrmAccountConsumptionBlockingAlertMode();
		if (cbam != null && !cbam.isEmpty())
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_ALERT_MODE,
					cbam, accAttrList);

		// CrmAccountConsumptionBlockingIntermediateThreshold
		Integer bitFlag = null;
		bitFlag = data.getCrmAccountConsumptionBlockingIntermediateThreshold();
		if (bitFlag != null)
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_CONS_BLOCK_INT_THRES,
					Integer.toString(bitFlag), accAttrList);

		// CrmAccountRetailerDomain
		String retailerId = data.getCrmAccountRetailerDomain();
		try {
			boolean isRetailerIdOk = AccountManagementDAO
					.isReatilerDomainPresent(retailerId, tenantName);
			if (!isRetailerIdOk)
				throw new MissingParameterException("RetailerId : '"
						+ retailerId + "' is not present in platform");
			return accAttrList;
		} catch (Exception e) {
			LogUtil.writeErrorLog(UpdateCrmAccount.class, "DB ERROR: ", e);
			throw new DbErrorException(e.getMessage());
		}

	}

	private void updateAccountAttribute(int attrName, String attrVal,
			List<AccountAttribute> aa) {
		Iterator<AccountAttribute> aIt = aa.iterator();
		boolean trovato = false;
		Long userId = aa.get(0).getUserId();
		while (aIt.hasNext() && !trovato) {
			AccountAttribute a = aIt.next();
			Long attrDetId = Utils.intToLong(attrName);
			if (a.getAttributeDetailId() == attrDetId) {
				a.setAttributeValue(attrVal);
				trovato = true;
			}
		}
		
		if (!trovato){
			AccountAttribute newAccAttr = new AccountAttribute();
			newAccAttr.setAttributeDetailId( Utils.intToLong(attrName) );
			newAccAttr.setAttributeValue(attrVal);
			newAccAttr.setUserId(userId);
			aa.add(newAccAttr);
		}
	}

	private Long intToLong(int val) {
		return Long.parseLong(Integer.toString(val));
	}

	// @Override
	public void setPayload(Object pt) {
		data = (PayloadType) pt;
	}
}
