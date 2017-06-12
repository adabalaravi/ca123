package com.accenture.sdp.csmac.business;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.party.AvsPartyBean;
import com.accenture.sdp.csmac.beans.party.SdpPartyBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.AvsPartyConverter;
import com.accenture.sdp.csmac.services.AvsAmsService;
import com.accenture.sdp.csmac.services.CredentialsService;

public final class AvsPartyBusiness {

	private AvsPartyBusiness() {
	}

	public static List<AvsPartyBean> searchPartiesByName(String firstName, String lastName) throws ServiceErrorException {
		List<SdpPartyBean> partyInfos = SdpPartyBusiness.searchPartiesByName(firstName, lastName);
		return prepareAvsPartiesStep1(partyInfos);
	}

	public static List<AvsPartyBean> searchPartiesByCredential(String username) throws ServiceErrorException {
		List<SdpPartyBean> partyInfos = SdpPartyBusiness.searchPartiesByCredential(username);
		return prepareAvsPartiesStep1(partyInfos);
	}

	public static List<AvsPartyBean> searchPartiesByPartyName(String partyName) throws ServiceErrorException {
		// FIXME il parentPartyId=1L e' encodato... ma tanto su avs nemmeno esiste!
		List<SdpPartyBean> partyInfos = SdpPartyBusiness.searchChildPartiesByPartyName(partyName, 1L);
		return prepareAvsPartiesStep1(partyInfos);
	}

	@Deprecated
	public static List<AvsPartyBean> prepareAvsParties(List<SdpPartyBean> parties) throws ServiceErrorException {
		// converte i party caricando anche le info di avs
		// non piu' usato, preferita l'operazione a 2 step
		AvsAmsService amsService = Utilities.findBean(ApplicationConstants.AVS_AMS_SERVICE_BEAN, AvsAmsService.class);
		CredentialsService credentialService = Utilities.findBean(ApplicationConstants.CREDENTIALS_SERVICE_BEAN, CredentialsService.class);
		List<AvsPartyBean> result = new ArrayList<AvsPartyBean>();
		for (SdpPartyBean party : parties) {
			result.add(AvsPartyConverter.convertChildPartyInfoToAvsBean(party, amsService.getSdpPartyProfileDataService(party.getName(), party.getId()),
					amsService.getSdpAccountProfileDataService(party.getName(), party.getId()),
					credentialService.searchCredentialsByParty(party.getId(), 0L, 0L, Utilities.getTenantName()).getCredentials().getCredential().get(0)));
		}
		return result;
	}

	private static List<AvsPartyBean> prepareAvsPartiesStep1(List<SdpPartyBean> parties) throws ServiceErrorException {
		// per ragioni di performance il caricamento e' stato splittato e rimandato
		List<AvsPartyBean> result = new ArrayList<AvsPartyBean>();
		CredentialsService credentialService = Utilities.findBean(ApplicationConstants.CREDENTIALS_SERVICE_BEAN, CredentialsService.class);
		for (SdpPartyBean party : parties) {
			AvsPartyBean avsInfo = AvsPartyConverter.convertChildPartyInfoToAvsBean(party, null, null, null);
			// su avs c'è una sola credenziale per party
			AvsPartyConverter.setupAvsPartyInfo(avsInfo, credentialService.searchCredentialsByParty(party.getId(), 0L, 0L, Utilities.getTenantName())
					.getCredentials().getCredential().get(0));
			// verra' fatto dal manager quando si seleziona il party
			// prepareAvsPartyStep2(avsInfo)
			result.add(avsInfo);
		}
		return result;
	}

	public static void prepareAvsPartyStep2(AvsPartyBean party) throws ServiceErrorException {
		// commentare tutto per ignorare ws avs
		AvsAmsService s = Utilities.findBean(ApplicationConstants.AVS_AMS_SERVICE_BEAN, AvsAmsService.class);
		AvsPartyConverter.setupAvsPartyInfo(party, s.getSdpPartyProfileDataService(party.getCrmAccountId(), party.getId()));
		AvsPartyConverter.setupAvsPartyInfo(party, s.getSdpAccountProfileDataService(party.getCrmAccountId(), party.getId()));
	}
	
	public static void createAVSUser(AvsPartyBean info) throws ServiceErrorException {
		AvsAmsService s = Utilities.findBean(ApplicationConstants.AVS_AMS_SERVICE_BEAN, AvsAmsService.class);
		// in realta' la chiamata dovrebbe essere fatta passando tutti i parametri
		s.createAVSUser(info);
	}
	
	public static void updateAVSUser(AvsPartyBean info) throws ServiceErrorException {
		AvsAmsService s = Utilities.findBean(ApplicationConstants.AVS_AMS_SERVICE_BEAN, AvsAmsService.class);
		// in realta' la chiamata dovrebbe essere fatta passando tutti i parametri
		s.updateAVSUser(info);
	}

}
