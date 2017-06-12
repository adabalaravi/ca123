package com.accenture.sdp.csm.managers;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.CreateServicesResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.ResetPwdServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.utilities.CodeManager;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public abstract class SdpBaseManager implements FieldConstants {

	protected LoggerWrapper log;

	protected static final String TRANSACTION_START = "Start Transaction";
	protected static final String TRANSACTION_END = "End Transaction";
	protected static final String TRANSACTION_COMMIT = "Transaction Commit";
	protected static final String TRANSACTION_COMMITED = "Transaction Committed";
	protected static final String TRANSACTION_ROLLBACK = "Rollbacking Transaction";
	protected static final String TRANSACTION_ROLLBACKED = "Transaction rollbacked";
	protected static final String ENTITY_MANAGER_OPENED = "Entity Manager Opened";
	protected static final String ENTITY_MANAGER_CLOSED = "Entity Manager Closed";

	protected static final String RESULT_STRING = "%s result %s";
	protected static final String EXCEPTION_WARNING_STRING = "*************** Exception ***************";
	protected static final String ROLLBACK_WARNING_STRING = "*************** Rollback ***************";

	protected SdpBaseManager() {
		log = new LoggerWrapper(this.getClass());
	}

	protected CreateServiceResponse buildCreateResponse(String code, ParameterDto... params) throws PropertyNotFoundException {
		String resultCode = CodeManager.loadCode(code);
		String resultDescription = CodeManager.loadCodeDescription(code);
		return new CreateServiceResponse(resultCode, resultDescription, params);
	}

	protected ComplexServiceResponse buildComplexResponse(String code, ParameterDto... params) throws PropertyNotFoundException {
		String resultCode = CodeManager.loadCode(code);
		String resultDescription = CodeManager.loadCodeDescription(code);
		return new ComplexServiceResponse(resultCode, resultDescription, params);
	}

	protected CreateServicesResponse buildCreateResponses(String code, ParameterDto... params) throws PropertyNotFoundException {
		String resultCode = CodeManager.loadCode(code);
		String resultDescription = CodeManager.loadCodeDescription(code);
		return new CreateServicesResponse(resultCode, resultDescription, params);
	}

	protected DataServiceResponse buildUpdateResponse(String code, ParameterDto... params) throws PropertyNotFoundException {
		String resultCode = CodeManager.loadCode(code);
		String resultDescription = CodeManager.loadCodeDescription(code);
		return new DataServiceResponse(resultCode, resultDescription, params);
	}

	protected SearchServiceResponse buildSearchResponse(String code, ParameterDto... params) throws PropertyNotFoundException {
		String resultCode = CodeManager.loadCode(code);
		String errorDescription = CodeManager.loadCodeDescription(code);
		return new SearchServiceResponse(resultCode, errorDescription, params);
	}

	protected ResetPwdServiceResponse buildResetPwdResponse(String code, ParameterDto... params) throws PropertyNotFoundException {
		String resultCode = CodeManager.loadCode(code);
		String errorDescription = CodeManager.loadCodeDescription(code);
		return new ResetPwdServiceResponse(resultCode, errorDescription, params);
	}

	protected boolean isStatusActive(Long status) throws PropertyNotFoundException {
		return status.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
	}

	protected boolean isStatusInactive(Long status) throws PropertyNotFoundException {
		return status.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.INACTIVE));
	}

	protected boolean isStatusDeleted(Long status) throws PropertyNotFoundException {
		return status.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
	}

	protected boolean isStatusUpdating(Long status) throws PropertyNotFoundException {
		return status.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.UPDATING));
	}

	protected boolean isStatusActivating(Long status) throws PropertyNotFoundException {
		return status.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVATING));
	}
}
