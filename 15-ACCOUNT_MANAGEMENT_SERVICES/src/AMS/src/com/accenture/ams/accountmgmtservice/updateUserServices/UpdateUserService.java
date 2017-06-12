package com.accenture.ams.accountmgmtservice.updateUserServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.db.bean.CrmAccountBean;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.updatePwdService.UpdateUserPasswordService;

import commontypes.accountmgmt.avs.accenture.ExtendedRatingType;
import commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import commontypes.accountmgmt.avs.accenture.UserPcExtendedRatingsListType;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;
import types.accountmgmt.avs.accenture.UpdateUserRequest;

public class UpdateUserService {

	UpdateUserRequest updateUserRequest = null;
	CrmAccountBean crmAccountBean = null;
	AccountUserBean accountUserBean = null;
	ArrayList<AccountAttribute> accountAttributes = new ArrayList<AccountAttribute>();
	private String tenantName = null;

	public UpdateUserService(UpdateUserRequest _updateUserRequest,
			String _tenantName) {
		updateUserRequest = _updateUserRequest;
		tenantName = _tenantName;
	}

	public AccountMgmtResponse updateUser() {

		/*
		 * check if crmAccountId is present into db and retrieve bean for
		 * CrmAccount, AccountUser, AccountAttribute
		 */
		String crmAccountId = updateUserRequest.getCrmAccountId();

		try {
			LogUtil.writeCommonLog("INFO", UpdateUserService.class, "WS",
					"I'm in UpdateUserService.updateUser for crm account id: "
							+ crmAccountId);
			crmAccountBean = AccountManagementDAO
					.getCrmAccountBean(crmAccountId, tenantName);
			if (crmAccountBean == null)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_USER_NOT_PRESENT, null);

			Long oldUserName = crmAccountBean.getUsername();
			accountUserBean = AccountManagementDAO
					.getAccountUserBean(oldUserName, tenantName);

			accountAttributes = (ArrayList<AccountAttribute>) AccountManagementDAO
					.getAccountAttributesBean(oldUserName, tenantName);

			updateUserData();

			boolean result = AccountManagementDAO.updateUserData(
					crmAccountBean, accountUserBean, accountAttributes, tenantName);

			if (result)
				return Utils.getResponse(AccountMgmtServiceConstant.RET_OK,
						null);
			else
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, null);

		} catch (Exception e) {
			LogUtil.writeErrorLog(UpdateUserService.class, "RET_DB_ERROR", e);
			return Utils.getResponse(AccountMgmtServiceConstant.RET_DB_ERROR,
					e.getMessage());
		}

	}

	private void updateUserData() {
		LogUtil.writeCommonLog("INFO", UpdateUserService.class, "WS",
				"I'm in UpdateUserService.updateUserData for crm account id: "
						+ updateUserRequest.getCrmAccountId());
		// userName
		String _newUserName = updateUserRequest.getNewUsername();
		if (Utils.isValorized(_newUserName)) {
			accountUserBean.setName(_newUserName);
			crmAccountBean.setName(_newUserName);
		}

		// userType
		String userType = updateUserRequest.getUserType();

		if (Utils.isValorized(userType)) {
			if (userType
					.equalsIgnoreCase(AccountMgmtServiceConstant.USER_TYPE_DEFAULT)) {
				updateAccountAttribute(AccountMgmtServiceConstant.ATTR_ISQA,
						"N");
				crmAccountBean.setVip("N");
			} else if (userType
					.equalsIgnoreCase(AccountMgmtServiceConstant.USER_TYPE_QA)) {
				updateAccountAttribute(AccountMgmtServiceConstant.ATTR_ISQA,
						"Y");
				crmAccountBean.setVip("N");
			} else if (userType
					.equalsIgnoreCase(AccountMgmtServiceConstant.USER_TYPE_VIP)) {
				updateAccountAttribute(AccountMgmtServiceConstant.ATTR_ISQA,
						"N");
				crmAccountBean.setVip("Y");
			}
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_TYPE, userType);
		}

		// userStatus
		String userStatus = updateUserRequest.getUserStatus();
		if (Utils.isValorized(userStatus))
			accountUserBean.setStatusId(Long.parseLong(userStatus));// (
																	// Long.parseLong(userType.toString())
																	// );

		// userCountry
		String userCountry = updateUserRequest.getUserCountry();
		if (Utils.isValorized(userCountry))
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_CRM_ACC_COUNTRY,
					userCountry);

		// userLanguage
		String userLanguage = updateUserRequest.getUserLanguage();
		if (Utils.isValorized(userLanguage))
			updateAccountAttribute(AccountMgmtServiceConstant.ATTR_USER_LANG,
					userLanguage);

		// UserRememberPinFlag
		FlagTypeYN userRemeberPinFlag = updateUserRequest
				.getUserRememberPinFlag();
		if (userRemeberPinFlag != null)
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_REMEMBER_PIN,
					userRemeberPinFlag.value());

		// UserPcLevel
		Integer userPcLevel = null;
		userPcLevel = updateUserRequest.getUserPcLevel();
		if (userPcLevel != null)
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_USER_PC_LEVEL,
					Integer.toString(userPcLevel));

		// NewPinPurchase
		String newPinPurhcase = updateUserRequest.getNewPinPurchase();
		if (Utils.isValorized(newPinPurhcase))
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_PIN_PURCHASE,
					newPinPurhcase);

		// NewPinParentalControl
		String newPinParentalControl = updateUserRequest
				.getNewPinParentalControl();
		if (Utils.isValorized(newPinParentalControl))
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_PARENTAL_CONTROL_PIN,
					newPinParentalControl);

		// NewPcExtendedRatings
		String userPcExtRat = "";
		UserPcExtendedRatingsListType listOfExtendRating = updateUserRequest.getUserPcExtendedRatings();
		if ( listOfExtendRating!=null ){
			List<ExtendedRatingType> ratList = listOfExtendRating.getUserPcExtendedRating();
			Iterator<ExtendedRatingType> itList = ratList.iterator();
			while ( itList.hasNext() ){
				String nextRating = itList.next().value();
				userPcExtRat = userPcExtRat + nextRating + ";";
			}
		}
		//remove last ";"
		if (userPcExtRat.length()> 0){
			userPcExtRat = userPcExtRat.substring(0, userPcExtRat.length()-1);
			updateAccountAttribute(
					AccountMgmtServiceConstant.ATTR_PC_EXTEND_RATING,
					userPcExtRat);
		}
	}

	/**
	 * Search in Array of attributes for the specified attrId and if find,
	 * update Attribute value if not found, insert Attribute value
	 * 
	 * @param attrId
	 * @param newValue
	 * @return true if it is present
	 */

	private boolean updateAccountAttribute(int attrId, String newValue) {

		boolean isPresent = false;
		Iterator<AccountAttribute> attrIt = accountAttributes.iterator();

		while (attrIt.hasNext() && !isPresent) {
			AccountAttribute item = attrIt.next();

			if (item.getAttributeDetailId() == attrId) {
				item.setAttributeValue(newValue);
				isPresent = true;
			} else {
				item.setAttributeValue(newValue);
				item.setAttributeDetailId(Utils.intToLong(attrId));
				isPresent = true;
			}
		}

		return isPresent;

	}

	/**
	 * This method checks to see if NewPcExtendedRatings contains valid values
	 * ​​
	 * 
	 * @param newPcExtendedRatings
	 *            - string to test
	 * @return true if it is a valid value
	 */
	private boolean checkNewPcExtendedRatings(Integer newPcExtendedRatings) {
		boolean isValid = false;
		String newPcExt = newPcExtendedRatings.toString();

		if (!newPcExt.contains("S") || !newPcExt.contains("T")
				|| !newPcExt.contains("H") || !newPcExt.contains("D")
				|| !newPcExt.contains("A") || !newPcExt.contains("G")) {
			return isValid;

		} else { // Se contiene almeno uno di questi valori
			isValid = true;
		}

		return isValid;

	}

}
