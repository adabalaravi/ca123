package com.accenture.sdp.csmfe.webservices.services;

import javax.xml.ws.Holder;

import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.utils.BaseBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.Utilities;

public abstract class BaseWebService {

	protected LoggerWrapper log;

	protected BaseWebService() {
		log = new LoggerWrapper(this.getClass());
	}

	protected String trim(String input) {
		return Utilities.trim(input);
	}

	protected String trim(Holder<String> input) {
		return trim(input.value);
	}

	protected void convertBaseResponse(DataServiceResponse coreResp, BaseResp wsResp) {
		wsResp.setResultCode(coreResp.getResultCode());
		wsResp.setDescription(coreResp.getDescription());
		wsResp.setParameters(BaseBeanConverter.convertParameterInfo(coreResp.getParameters()));
	}
}
