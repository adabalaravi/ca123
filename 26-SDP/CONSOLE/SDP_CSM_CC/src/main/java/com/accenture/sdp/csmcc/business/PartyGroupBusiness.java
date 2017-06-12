/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.PartyGroupConverter;
import com.accenture.sdp.csmcc.services.PartyGroupService;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchPartyGroupResp;

public final class PartyGroupBusiness {

	private PartyGroupBusiness(){
		
	}
	
	public static List<PartyGroupBean> searchAllPartyGroups() throws ServiceErrorException{
		PartyGroupService service = Utilities.findBean(ApplicationConstants.PARTYGROUP_SERVICE_BEAN_NAME, PartyGroupService.class);
		SearchPartyGroupResp resp=service.searchAllPartyGroups(Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			return (List<PartyGroupBean>) PartyGroupConverter.buildPartyGroupTable(resp);
		} else {
			return new ArrayList<PartyGroupBean>();
		}
		
	}
	

}
