package com.accenture.sdp.csm.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.model.jpa.AvsCountryLangCode;
import com.accenture.sdp.csm.model.jpa.AvsDeviceIdType;
import com.accenture.sdp.csm.model.jpa.AvsLnkOfferDigitalGood;
import com.accenture.sdp.csm.model.jpa.AvsPaymentType;
import com.accenture.sdp.csm.model.jpa.AvsPcExtendedRating;
import com.accenture.sdp.csm.model.jpa.AvsPcLevel;
import com.accenture.sdp.csm.model.jpa.AvsPlatform;
import com.accenture.sdp.csm.model.jpa.AvsRetailerDomain;
import com.accenture.sdp.csm.utilities.Utilities;

public final class AvsHelper extends SdpBaseHelper {

	private static AvsHelper instance;

	private AvsHelper() {
		super();
	}

	public static AvsHelper getInstance() {
		if (instance == null) {
			instance = new AvsHelper();
		}
		return instance;
	}

	public List<AvsCountryLangCode> searchAllAvsCountryLangCodes(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsCountryLangCode.class, AvsCountryLangCode.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsRetailerDomain> searchAllAvsRetailerDomains(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsRetailerDomain.class, AvsRetailerDomain.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsDeviceIdType> searchAllAvsDeviceIdTypes(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsDeviceIdType.class, AvsDeviceIdType.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsPaymentType> searchAllAvsPaymentTypes(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsPaymentType.class, AvsPaymentType.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsPcExtendedRating> searchAllAvsPcExtendedRatings(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsPcExtendedRating.class, AvsPcExtendedRating.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsPcLevel> searchAllAvsPcLevels(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsPcLevel.class, AvsPcLevel.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsPlatform> searchAllAvsPlatforms(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(AvsPlatform.class, AvsPlatform.QUERY_RETRIEVE_ALL, null, em);
	}

	public List<AvsLnkOfferDigitalGood> searchAvsLnkOfferDigitalGoodByOfferId(Long offerId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (offerId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(AvsLnkOfferDigitalGood.PARAM_OFFER_ID, offerId);
		// retrieve links with digital goods already loaded
		List<Object[]> temp = NamedQueryHelper.executeNamedQuery(Object[].class, AvsLnkOfferDigitalGood.QUERY_LNK_AND_DIGITALGOODS_RETRIEVE_BY_OFFERID, parameHashMap, em);
		if (temp == null) {
			return null;
		}
		List<AvsLnkOfferDigitalGood> result = new ArrayList<AvsLnkOfferDigitalGood>();
		// only take the links, digital goods will be in the em
		for (Object[] o : temp) {
			result.add((AvsLnkOfferDigitalGood) o[0]);
		}
		return result;
	}
}