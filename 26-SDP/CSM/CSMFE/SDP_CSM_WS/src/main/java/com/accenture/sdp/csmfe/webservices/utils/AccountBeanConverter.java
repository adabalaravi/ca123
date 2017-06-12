package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csmfe.webservices.response.account.AccountInfoResp;
import com.accenture.sdp.csmfe.webservices.response.account.AccountInfoWithSubscriptionResp;

public class AccountBeanConverter extends BaseBeanConverter {

	public static List<AccountInfoResp> convertAccounts(List<SdpAccountResponseDto> adtos) {
		if (adtos == null) {
			return null;
		}
		List<AccountInfoResp> ainfos = new ArrayList<AccountInfoResp>();
		for (SdpAccountResponseDto a : adtos) {
			AccountInfoResp ainfo = new AccountInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setAccountName(a.getAccountName());
			ainfo.setAccountDescription(a.getAccountDescription());
			ainfo.setStatus(a.getStatus());
			ainfo.setDefaultAccount(a.isDefaulAccount());
			ainfo.setPartyId(a.getPartyId());
			ainfo.setPartyName(a.getPartyName());
			ainfo.setPartyParentName(a.getParentPartyName());
			ainfo.setSiteId(a.getSiteId());
			ainfo.setSiteName(a.getSiteName());
			ainfo.setExternalId(a.getExternalId());
			ainfo.setAccountProfile(a.getAccountProfile());
			ainfos.add(ainfo);
		}
		return ainfos;
	}

	// questo metodo aggiunge l'account id, che erroneamente nelle specifiche non e' stato messo
	public static List<AccountInfoResp> convertAccountsProper(List<SdpAccountResponseDto> adtos) {
		if (adtos == null) {
			return null;
		}
		List<AccountInfoResp> ainfos = new ArrayList<AccountInfoResp>();
		for (SdpAccountResponseDto a : adtos) {
			AccountInfoResp ainfo = new AccountInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setAccountId(a.getAccountId());
			ainfo.setAccountName(a.getAccountName());
			ainfo.setAccountDescription(a.getAccountDescription());
			ainfo.setStatus(a.getStatus());
			ainfo.setDefaultAccount(a.isDefaulAccount());
			ainfo.setPartyId(a.getPartyId());
			ainfo.setPartyName(a.getPartyName());
			ainfo.setPartyParentName(a.getParentPartyName());
			ainfo.setSiteId(a.getSiteId());
			ainfo.setSiteName(a.getSiteName());
			ainfo.setExternalId(a.getExternalId());
			ainfo.setAccountProfile(a.getAccountProfile());
			ainfos.add(ainfo);
		}
		return ainfos;
	}

	public static List<AccountInfoWithSubscriptionResp> convertAccountsWhithSupbscription(List<SdpAccountResponseDto> adtos) {
		if (adtos == null) {
			return null;
		}
		List<AccountInfoWithSubscriptionResp> ainfos = new ArrayList<AccountInfoWithSubscriptionResp>();
		for (SdpAccountResponseDto a : adtos) {
			AccountInfoWithSubscriptionResp ainfo = new AccountInfoWithSubscriptionResp();
			convertBaseInfo(a, ainfo);
			ainfo.setAccountName(a.getAccountName());
			ainfo.setAccountDescription(a.getAccountDescription());
			ainfo.setStatus(a.getStatus());
			ainfo.setDefaultAccount(a.isDefaulAccount());
			ainfo.setPartyId(a.getPartyId());
			ainfo.setPartyName(a.getPartyName());
			ainfo.setPartyParentName(a.getParentPartyName());
			ainfo.setSiteId(a.getSiteId());
			ainfo.setSiteName(a.getSiteName());
			ainfo.setExternalId(a.getExternalId());
			ainfo.setAccountProfile(a.getAccountProfile());
			ainfo.setAccountType(a.getAccountType());
			ainfos.add(ainfo);

		}
		return ainfos;
	}
}
