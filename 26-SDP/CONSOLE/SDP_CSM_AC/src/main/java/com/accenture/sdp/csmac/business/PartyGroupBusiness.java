package com.accenture.sdp.csmac.business;

import java.util.List;

import com.accenture.sdp.csmac.beans.party.PartyGroupBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.PartyGroupConverter;
import com.accenture.sdp.csmac.services.PartyGroupService;
import com.accenture.sdp.csmac.services.PartyService;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartyResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchPartyGroupResp;

public final class PartyGroupBusiness {

	private PartyGroupBusiness() {
	}

	public static List<PartyGroupBean> searchAllPartyGroups() throws ServiceErrorException {
		PartyGroupService s = Utilities.findBean(ApplicationConstants.PARTY_GROUP_SERVICE_BEAN, PartyGroupService.class);
		SearchPartyGroupResp resp = s.searchAllPartyGroups(Utilities.getTenantName());
		return PartyGroupConverter.convertPartyGroups(resp.getPartyGroups().getPartyGroup());
	}
	
	public static List<PartyGroupBean> searchPartyGroupsByPartyId(Long partyId) throws ServiceErrorException {
		PartyService service = Utilities.findBean(ApplicationConstants.PARTY_SERVICE_BEAN, PartyService.class);
		SearchPartyResp info = service.searchPartyById(partyId, Utilities.getTenantName());
		return PartyGroupConverter.convertPartyGroupsOfParty(info.getParty().getPartyGroups().getPartyGroup());
	}
}
