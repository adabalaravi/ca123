package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpPartySiteDto;
import com.accenture.sdp.csmfe.webservices.response.site.SiteInfoResp;

public class PartySiteBeanConverter extends BaseBeanConverter {

	public static List<SiteInfoResp> convertSites(List<SdpPartySiteDto> sdtos) {
		if (sdtos == null) {
			return null;
		}
		List<SiteInfoResp> sinfos = new ArrayList<SiteInfoResp>();
		for (SdpPartySiteDto s : sdtos) {
			SiteInfoResp sinfo = new SiteInfoResp();
			convertBaseInfo(s, sinfo);
			sinfo.setSiteId(s.getSiteId());
			sinfo.setSiteName(s.getSiteName());
			sinfo.setSiteDescription(s.getSiteDescription());
			sinfo.setStatusId(s.getStatusId());
			sinfo.setStatus(s.getStatus());
			sinfo.setPartyId(s.getPartyId());
			sinfo.setPartyName(s.getPartyName());
			sinfo.setStreetAddress(s.getStreetAddress());
			sinfo.setCity(s.getCity());
			sinfo.setZipCode(s.getZipCode());
			sinfo.setProvince(s.getProvince());
			sinfo.setCountry(s.getCountry());
			sinfo.setExternalId(s.getExternalID());
			sinfo.setSiteProfile(s.getSiteProfile());
			sinfos.add(sinfo);
		}
		return sinfos;
	}
}
