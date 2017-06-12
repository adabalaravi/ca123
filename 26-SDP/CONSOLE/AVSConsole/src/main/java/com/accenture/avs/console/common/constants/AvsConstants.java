package com.accenture.avs.console.common.constants;

public interface AvsConstants {

	// OK
	String ACN_200 = "ACN_200";
	// 300-GENERIC ERROR
	String ACN_300 = "ACN_300";
	// DB Error
	String ACN_3061 = "ACN_3061";
	// CRM Account doesn't exist
	String ACN_3066 = "ACN_3066";
	// Request Not Allowed
	String ACN_3064 = "ACN_3064";
	// ACN_3065
	String ACN_3065 = "ACN_3065";

	String AVS_USER_ACTIVE = "avs_user_active";
	String AVS_USER_INACTIVE = "avs_user_inactive";
	String AVS_USER_CEASED = "avs_user_ceased";

	// OFFER and SOLUTION OFFER PROFILE
	String OFFER_CHANNEL_FIELD = "channel";
	String OFFER_CATEGORY_FIELD = "category";
	String OFFER_PRICE_FIELD = "price";
	String OFFER_TYPE_FIELD = "type";
	String OFFER_RENT_PRICE_FIELD = "rentprice";
	String OFFER_BROADCAST_DATE_FIELD = "broadcastdate";
	String SOLUTION_OFFER_TYPE_FIELD = "solutionOfferType";
	String SOLUTION_OFFER_TYPE_FRUITION_MAX_NUMBER_FIELD = "fruitionMaxNumber";
	String SOLUTION_OFFER_BUNDLE_DURATION_FIELD = "bundleDuration";
	String SOLUTION_OFFER_OFFER_TYPE_FIELD = "offerType";
	String SUBSCRIPTION_VOUCHER_PREFIX = "VID_";
	// values
	String SOLUTION_OFFER_TECHNICAL_TYPE = "technical";
	String SOLUTION_OFFER_BUNDLE_TYPE = "bundle";
	String AVS_OFFER_VOD = "VOD";
}
