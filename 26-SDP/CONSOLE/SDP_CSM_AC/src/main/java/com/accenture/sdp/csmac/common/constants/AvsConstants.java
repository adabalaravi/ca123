package com.accenture.sdp.csmac.common.constants;

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
	
	// PROFILE PARAMETERS
	String PROFILE_SUBSCRIPTION_VOUCHER_PREFIX = "VID_";
}
