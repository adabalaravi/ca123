package com.accenture.sdp.csm.test.utilities;

import java.util.List;

import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpChildPartyDto;
import com.accenture.sdp.csm.managers.SdpPartyManager;

public class AvsAdapter {

	public static Long retrieveRandomAvsPartyId() {
		try {
			// fatta a 2 step, altrimenti ci metteva 3 secondi a chiamata
			// prima recupero il totale
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchChildParty(1L, "%", 0L, 1L, TestConfigurator.getInstance().getTenantName());
			Long theWinnerIs = Utils.getRandomLong(resp.getTotalResult().intValue());
			// quindi ne scelgo uno a caso, e recupero solo quello
			resp = SdpPartyManager.getInstance().searchChildParty(1L, "%", theWinnerIs - 1, 1L, TestConfigurator.getInstance().getTenantName());
			@SuppressWarnings("unchecked")
			List<SdpChildPartyDto> dtos = (List<SdpChildPartyDto>) resp.getSearchResult();
			return dtos.get(0).getPartyId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
