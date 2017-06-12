package com.accenture.sdp.csmac.business;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.party.SdpPartyBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.SdpPartyConverter;
import com.accenture.sdp.csmac.services.CredentialsService;
import com.accenture.sdp.csmac.services.PartyService;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredentialsResp;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildPartyRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartyResp;

public final class SdpPartyBusiness {

	private SdpPartyBusiness() {
	}

	public static List<SdpPartyBean> searchPartiesByName(String firstName, String lastName) throws ServiceErrorException {
		PartyService service = Utilities.findBean(ApplicationConstants.PARTY_SERVICE_BEAN, PartyService.class);
		SearchChildPartyRespPaginated resp = service.searchPartiesByFirstAndLastName(firstName, lastName, 0L, 0L, Utilities.getTenantName());
		return SdpPartyConverter.convertChildParties(resp.getParties().getParty());
	}

	public static List<SdpPartyBean> searchPartiesByCredential(String username) throws ServiceErrorException {
		PartyService service = Utilities.findBean(ApplicationConstants.PARTY_SERVICE_BEAN, PartyService.class);
		// metodo di partyService e' deprecato - recupero credenziale
		CredentialsService credentialService = Utilities.findBean(ApplicationConstants.CREDENTIALS_SERVICE_BEAN, CredentialsService.class);
		SearchCredentialsResp resp = credentialService.searchCredential(username, Utilities.getTenantName());
		// recupero party dal partyId della credenziale
		SearchPartyResp resp2 = service.searchPartyById(resp.getCredentials().getCredential().get(0).getPartyId(), Utilities.getTenantName());
		// preparo una lista, anche se e' sempre solo uno
		List<SdpPartyBean> partyInfos = new ArrayList<SdpPartyBean>();
		partyInfos.add(SdpPartyConverter.convertParty(resp2.getParty()));
		return partyInfos;
	}
	
	public static List<SdpPartyBean> searchChildPartiesByPartyName(String partyName, Long parentPartyId) throws ServiceErrorException {
		PartyService service = Utilities.findBean(ApplicationConstants.PARTY_SERVICE_BEAN, PartyService.class);
		SearchChildPartyRespPaginated resp = service.searchChildPartiesByPartyName(partyName, parentPartyId, 0L, 0L, Utilities.getTenantName());
		return SdpPartyConverter.convertChildParties(resp.getParties().getParty());
	}

}
