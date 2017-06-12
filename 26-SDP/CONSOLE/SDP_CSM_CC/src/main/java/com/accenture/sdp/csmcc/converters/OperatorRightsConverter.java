package com.accenture.sdp.csmcc.converters;

import com.accenture.sdp.csmcc.beans.OperatorRoleInfo;
import com.accenture.sdp.csmcc.common.constants.OperatorRightsConstants;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorRightInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorRightResp;

public final class OperatorRightsConverter {
	
	private OperatorRightsConverter(){
		
	}

	public static OperatorRoleInfo convertRights(SearchOperatorRightResp resp) {
		OperatorRoleInfo result = new OperatorRoleInfo();
		if (resp != null && resp.getRights().getRight() != null) {
			for (OperatorRightInfoResp i : resp.getRights().getRight()) {
				if (OperatorRightsConstants.CONSOLE_ASSURANCE.equals(i.getResourceName())) {
					if (OperatorRightsConstants.OPERATOR_READ.equals(i.getRightName())){
						result.setAssuranceRead(true);
					} else if (OperatorRightsConstants.OPERATOR_WRITE.equals(i.getRightName())) {
						result.setAssuranceWrite(true);
					}
				} else if (OperatorRightsConstants.CONSOLE_CATALOGUE.equals(i.getResourceName())) {
					if (OperatorRightsConstants.OPERATOR_READ.equals(i.getRightName())) {
						result.setCatalogueRead(true);
					} else if (OperatorRightsConstants.OPERATOR_WRITE.equals(i.getRightName())) {
						result.setCatalogueWrite(true);
					}
				} else if (OperatorRightsConstants.CONSOLE_CATCHUP.equals(i.getResourceName())) {
					if (OperatorRightsConstants.OPERATOR_READ.equals(i.getRightName())) {
						result.setCatchupRead(true);
					} else if (OperatorRightsConstants.OPERATOR_WRITE.equals(i.getRightName())) {
						result.setCatchupWrite(true);
					}
				} else if (OperatorRightsConstants.CONSOLE_MULTICAMERA.equals(i.getResourceName())) {
					if (OperatorRightsConstants.OPERATOR_READ.equals(i.getRightName())) {
						result.setMulticameraRead(true);
					}else if (OperatorRightsConstants.OPERATOR_WRITE.equals(i.getRightName())){
						result.setMulticameraWrite(true);
					}
				} else if (OperatorRightsConstants.CONSOLE_OPERATORS.equals(i.getResourceName())) {
					if (OperatorRightsConstants.OPERATOR_READ.equals(i.getRightName())) {
						result.setOperatorsRead(true);
					} else if (OperatorRightsConstants.OPERATOR_WRITE.equals(i.getRightName())) {
						result.setOperatorsWrite(true);
					}
				}
			}
		}
		return result;
	}

}
