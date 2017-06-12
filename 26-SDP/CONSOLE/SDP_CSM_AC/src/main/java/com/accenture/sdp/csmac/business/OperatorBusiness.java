package com.accenture.sdp.csmac.business;

import com.accenture.sdp.csmac.beans.operator.OperatorBean;
import com.accenture.sdp.csmac.beans.operator.OperatorRoleBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.OperatorConverter;
import com.accenture.sdp.csmac.converters.OperatorRightsConverter;
import com.accenture.sdp.csmac.services.OperatorService;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorRightResp;

public final class OperatorBusiness {

	private OperatorBusiness() {
	}

	public static OperatorBean searchOperatorByUsername(String username) throws ServiceErrorException {
		OperatorService s = Utilities.findBean(ApplicationConstants.OPERATOR_SERVICE_BEAN, OperatorService.class);
		SearchOperatorComplexResp resp = s.searchOperatorByUsername(username);
		return OperatorConverter.convertOperator(resp.getOperators().getOperator().get(0));
	}

	public static OperatorRoleBean searchOperatorRights(String username) throws ServiceErrorException {
		OperatorService s = Utilities.findBean(ApplicationConstants.OPERATOR_SERVICE_BEAN, OperatorService.class);
		SearchOperatorRightResp resp = s.searchOperatorRights(username);
		return OperatorRightsConverter.convertRights(resp.getRights().getRight());
	}
}
