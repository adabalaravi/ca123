package com.accenture.avs.console.converters;

import java.util.List;
import java.util.Map;

import com.accenture.avs.console.beans.operator.OperatorRoleInfo;
import com.accenture.avs.console.common.constants.OperatorRightsConstants;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorRoleRightInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.ModifyOperatorRoleRightListRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorRightInfoResp;

public final class OperatorRightsConverter {

	private static final String NEW = "New";
	private static final String DELETE = "Delete";

	private OperatorRightsConverter() {
	}

	public static OperatorRoleInfo convertRights(List<OperatorRightInfoResp> list) {
		OperatorRoleInfo result = new OperatorRoleInfo();
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

	public static ModifyOperatorRoleRightListRequest convertRightsToRequest(OperatorRoleInfo info, OperatorRoleInfo stored, Map<String, Long> rightsMap) {
		ModifyOperatorRoleRightListRequest roleRequest = new ModifyOperatorRoleRightListRequest();

		// COMMERCIAL
		if (info.isCatalogueRead() != stored.isCatalogueRead()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_CATALOGUE + OperatorRightsConstants.OPERATOR_READ));
			right.setOperation((info.isCatalogueRead() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}
		if (info.isCatalogueWrite() != stored.isCatalogueWrite()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_CATALOGUE + OperatorRightsConstants.OPERATOR_WRITE));
			right.setOperation((info.isCatalogueWrite() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}

		// ASSURANCE
		if (info.isAssuranceRead() != stored.isAssuranceRead()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_ASSURANCE + OperatorRightsConstants.OPERATOR_READ));
			right.setOperation((info.isAssuranceRead() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}
		if (info.isAssuranceWrite() != stored.isAssuranceWrite()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_ASSURANCE + OperatorRightsConstants.OPERATOR_WRITE));
			right.setOperation((info.isAssuranceWrite() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}

		// CATCH-UP
		if (info.isCatchupRead() != stored.isCatchupRead()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_CATCHUP + OperatorRightsConstants.OPERATOR_READ));
			right.setOperation((info.isCatchupRead() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}
		if (info.isCatchupWrite() != stored.isCatchupWrite()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_CATCHUP + OperatorRightsConstants.OPERATOR_WRITE));
			right.setOperation((info.isCatchupWrite() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}

		// MULTICAMERA
		if (info.isMulticameraRead() != stored.isMulticameraRead()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_MULTICAMERA + OperatorRightsConstants.OPERATOR_READ));
			right.setOperation((info.isMulticameraRead() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}
		if (info.isMulticameraWrite() != stored.isMulticameraWrite()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_MULTICAMERA + OperatorRightsConstants.OPERATOR_WRITE));
			right.setOperation((info.isMulticameraWrite() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}

		// MULTICAMERA
		if (info.isOperatorsRead() != stored.isOperatorsRead()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_OPERATORS + OperatorRightsConstants.OPERATOR_READ));
			right.setOperation((info.isOperatorsRead() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}
		if (info.isOperatorsWrite() != stored.isOperatorsWrite()) {
			ModifyOperatorRoleRightInfoRequest right = new ModifyOperatorRoleRightInfoRequest();
			right.setRightId(rightsMap.get(OperatorRightsConstants.CONSOLE_OPERATORS + OperatorRightsConstants.OPERATOR_WRITE));
			right.setOperation((info.isOperatorsWrite() ? NEW : DELETE));
			roleRequest.getRight().add(right);
		}

		return roleRequest;
	}

}
