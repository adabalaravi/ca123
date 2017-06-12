package com.accenture.ams.accountmgmtservice.updatePwdService;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;

import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.db.util.crypto.MD5Utils;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile.UpdateComProfService;

public class UpdateUserPasswordService {

	private String tenantName;

	public UpdateUserPasswordService(String _tenantName) {
		tenantName = _tenantName;
	}

	public AccountMgmtResponse changePassword(String userName,
			String oldPassword, String newPassword, String opType) {
		try {
			LogUtil.writeCommonLog("INFO", UpdateUserPasswordService.class,
					"WS",
					"I'm in UpdateUserPasswordService.changePassword for username: "
							+ userName);

			/* check if username is present in platform and check for password */
			String currentPwd = AccountManagementDAO.getAccountUser(
					Long.parseLong(userName), tenantName);
			if (currentPwd == null)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_USER_NOT_PRESENT, null);

			/* check for password only if opType is CHANGE, not for RESTE */

			if (opType
					.equalsIgnoreCase(AccountMgmtServiceConstant.OP_CHANGE_PWD)
					&& !currentPwd.equalsIgnoreCase(MD5Utils
							.getHashPassword(oldPassword)))
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_REQ_NOT_ALLOWED,
						AccountMgmtServiceConstant.TXT_WRONG_PWD);

			AccountAttribute accAtt = AccountManagementDAO.getAccountAttribute(
					Long.valueOf(41), Long.parseLong(userName), tenantName);

			String hashPassword = MD5Utils.getHashPassword(newPassword);
			accAtt.setAttributeValue(hashPassword);
			boolean opResult = AccountManagementDAO
					.insertUpdateAccountAttribute(accAtt, tenantName);

			if (opResult)
				return Utils.getResponse(AccountMgmtServiceConstant.RET_OK,
						null);
			else
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, null);
		} catch (Exception e) {
			LogUtil.writeErrorLog(UpdateUserPasswordService.class,
					"RET_DB_ERROR", e);
			return Utils.getResponse(AccountMgmtServiceConstant.RET_DB_ERROR,
					null);

		}
	}

	/**
	 * This method reset the password by entering the new password
	 * 
	 * @param userName
	 *            : user_id that refers to the user
	 * @param newPassword
	 *            : value of new password to encrypt
	 * 
	 * @return response
	 */
	public AccountMgmtResponse resetPassword(String userName, String newPassword) {
		LogUtil.writeCommonLog("INFO", UpdateUserPasswordService.class, "WS",
				"I'm in UpdateUserPasswordService.resetPassword for username "
						+ userName);
		try {
			String currentPwd = AccountManagementDAO.getAccountUser(Long
					.parseLong(userName), tenantName);
			if (currentPwd == null)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_USER_NOT_PRESENT, null);

			AccountAttribute accAtt = AccountManagementDAO.getAccountAttribute(
					Long.valueOf(41), Long.parseLong(userName), tenantName);

			String hashPassword = MD5Utils.getHashPassword(newPassword);
			accAtt.setAttributeValue(hashPassword);
			boolean opResult = AccountManagementDAO
					.insertUpdateAccountAttribute(accAtt, tenantName);

			if (opResult)
				return Utils.getResponse(AccountMgmtServiceConstant.RET_OK,
						null);
			else
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, null);
		} catch (Exception e) {
			LogUtil.writeErrorLog(UpdateUserPasswordService.class,
					"RET_DB_ERROR", e);
			return Utils.getResponse(AccountMgmtServiceConstant.RET_DB_ERROR,
					null);

		}
	}
}
