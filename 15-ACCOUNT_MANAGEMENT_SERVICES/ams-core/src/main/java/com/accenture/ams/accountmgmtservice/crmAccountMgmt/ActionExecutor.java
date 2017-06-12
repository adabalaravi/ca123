package com.accenture.ams.accountmgmtservice.crmAccountMgmt;

import wsclient.types.accountmgmt.avs.accenture.AccountMgmtResponse;

import com.accenture.ams.accountmgmtservice.DbErrorException;
import com.accenture.ams.accountmgmtservice.GenericException;
import com.accenture.ams.accountmgmtservice.MissingParameterException;

public interface ActionExecutor {

	public void setPayload(Object pt);
	
	public AccountMgmtResponse execute() throws MissingParameterException, DbErrorException, GenericException;
}
