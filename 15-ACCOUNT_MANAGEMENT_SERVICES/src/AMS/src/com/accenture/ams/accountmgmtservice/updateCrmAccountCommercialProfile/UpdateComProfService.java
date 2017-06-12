package com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;
import types.accountmgmt.avs.accenture.UpdateCrmAccountCommercialProfileRequest;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.db.bean.AccountProduct;
import com.accenture.ams.db.bean.AccountProductBean;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.AccountUser;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.db.bean.CrmAccountBean;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.accountmgmtservice.*;

import commontypes.accountmgmt.avs.accenture.UpdateProfileType;

public class UpdateComProfService {

	private String crmAccountId = null;
	private AccountUserBean au = null;
	private List<UpdateProfileType> pkgList = null;
	private String tenantName = null;

	public UpdateComProfService(UpdateCrmAccountCommercialProfileRequest _data,
			String _tenantName) {
		LogUtil.writeCommonLog("INFO", UpdateComProfService.class, "WS",
				"Instanziate new UpdateCommercialProfileService");
		pkgList = _data.getUpdateCommProfileList().getUpdateCommProfOp();
		crmAccountId = _data.getCrmAccountId();
		tenantName = _tenantName;
	}

	public AccountMgmtResponse execute() {
		Long userName = null;
		try {
			CrmAccountBean cBean = AccountManagementDAO.getCrmAccountBean(
					crmAccountId, tenantName);
			if (cBean == null)
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_USER_NOT_PRESENT, null);

			userName = cBean.getUsername();

			UpdateComProfileManager updManager = new UpdateComProfileManager(
					pkgList, crmAccountId, userName, tenantName);
			
			/* excecute remove OP first */
			LogUtil.writeCommonLog("INFO", UpdateComProfService.class, "WS",
					"Removing subscription/packages with opType=DELETE '");			
			try{
				updManager.doDelete();
			}
			catch(Exception e){
				String desc = "Error removing subscription/packages";
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, desc);			
			}

			LogUtil.writeCommonLog("INFO", UpdateComProfService.class, "WS",
					"Deleted OK!");

			/* execute insert */
			LogUtil.writeCommonLog("INFO", UpdateComProfService.class, "WS",
					"Adding subscription/packages with opType=ADD '");					
			try{
				updManager.doInsert();
			}
			catch(ProductBuyedException e){
				String desc = e.getMessage();
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, desc);					
			}
			catch(Exception e){
				String desc = "Error adding subscription/packages";
				return Utils.getResponse(
						AccountMgmtServiceConstant.RET_DB_ERROR, desc);			
			}
			
			
			LogUtil.writeCommonLog("INFO", UpdateComProfService.class, "WS",
					"Service finished!!");

		} catch (Exception e) {
			LogUtil.writeErrorLog(UpdateComProfService.class, "ERROR ", e);
			return Utils.getResponse(AccountMgmtServiceConstant.RET_DB_ERROR,
					null);
		}

		return Utils.getResponse(AccountMgmtServiceConstant.RET_OK, null);

	}

}
