package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferInfoResp;

public abstract class SolutionOfferConverter {

	public static List<SolutionOfferBean> convertSolutionOffers(List<SolutionOfferComplexInfoResp> inputs) {
		if (inputs == null) {
			return null;
		}
		List<SolutionOfferBean> result = new ArrayList<SolutionOfferBean>();
		for (SolutionOfferComplexInfoResp so : inputs) {
			result.add(convertSolutionOffer(so));
		}
		return result;
	}

	public static SolutionOfferBean convertSolutionOffer(SolutionOfferComplexInfoResp so) {
		SolutionOfferBean info = new SolutionOfferBean();
		info.setSolutionId(so.getSolutionId());
		info.setSolutionName(so.getSolutionName());
		info.setId(so.getSolutionOfferId());
		info.setParentSolutionOfferId(so.getSolutionId());
		info.setName(so.getSolutionOfferName());
		info.setDescription(so.getSolutionOfferDescription());
		info.setStatusId(so.getStatusId());
		info.setStatusName(so.getStatusName());
		info.setStartDate(Utilities.getDateFromGregorianCalendar(so.getStartDate()));
		info.setEndDate(Utilities.getDateFromGregorianCalendar(so.getEndDate()));
		info.setProfile(so.getProfile());
		info.setCreationDate(Utilities.getDateFromGregorianCalendar(so.getCreatedDate()));
		return info;
	}

	public static SolutionOfferBean convertSolutionOffer(SolutionOfferInfoResp so) {
		SolutionOfferBean info = new SolutionOfferBean();
		info.setSolutionId(so.getSolutionId());
		info.setSolutionName(so.getSolutionName());
		info.setId(so.getSolutionOfferId());
		info.setParentSolutionOfferId(so.getSolutionId());
		info.setName(so.getSolutionOfferName());
		info.setDescription(so.getSolutionOfferDescription());
		info.setStatusId(so.getStatusId());
		info.setStartDate(Utilities.getDateFromGregorianCalendar(so.getStartDate()));
		info.setEndDate(Utilities.getDateFromGregorianCalendar(so.getEndDate()));
		info.setProfile(so.getProfile());
		info.setCreationDate(Utilities.getDateFromGregorianCalendar(so.getCreatedDate()));
		return info;
	}
}
