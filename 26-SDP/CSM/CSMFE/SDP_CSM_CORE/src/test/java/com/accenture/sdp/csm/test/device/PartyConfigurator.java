package com.accenture.sdp.csm.test.device;

import java.util.List;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpCredentialDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpCredentialManager;
import com.accenture.sdp.csm.test.utilities.Utils;

public class PartyConfigurator {

	private PartyConfigurator() {
	}

	public static String getUsernameRandom(Long partyId, String tenantName) throws PropertyNotFoundException {
		SearchServiceResponse resp = SdpCredentialManager.getInstance().searchCredentialsByParty(partyId, 0L, 0L, tenantName);
		// prendo uno a caso
		if (resp.getSearchResult() != null && !resp.getSearchResult().isEmpty()) {
			List<SdpCredentialDto> results = (List<SdpCredentialDto>) resp.getSearchResult();
			return results.get(Utils.getRandomInt(results.size())).getUsername();
		}
		throw new RuntimeException("No credential available");
	}
}
