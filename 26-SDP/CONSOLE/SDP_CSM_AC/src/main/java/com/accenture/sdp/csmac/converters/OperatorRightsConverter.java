package com.accenture.sdp.csmac.converters;

import java.util.List;

import com.accenture.sdp.csmac.beans.operator.OperatorRoleBean;
import com.accenture.sdp.csmac.common.constants.OperatorRightsConstants;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorRightInfoResp;

public abstract class OperatorRightsConverter {

	public static OperatorRoleBean convertRights(List<OperatorRightInfoResp> list) {
		OperatorRoleBean result = new OperatorRoleBean();
		if (list != null) {
			for (OperatorRightInfoResp i : list) {
				if (OperatorRightsConstants.CONSOLE_ASSURANCE.equals(i.getResourceName())) {
					if (OperatorRightsConstants.OPERATOR_READ.equals(i.getRightName())) {
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
					} else if (OperatorRightsConstants.OPERATOR_WRITE.equals(i.getRightName())) {
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
