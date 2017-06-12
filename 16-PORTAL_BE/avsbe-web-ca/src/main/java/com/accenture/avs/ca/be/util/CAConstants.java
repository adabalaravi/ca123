package com.accenture.avs.ca.be.util;

public class CAConstants {
	
	
	public static String GIGYA_API_KEY= "GIGYA_API_KEY";
	public static String GIGYA_SECRET_KEY= "GIGYA_SECRET_KEY";
	public static String RETRY_CALL_GIGYA = "10";
	public static int GIGYA_NOTIFY_RESPONSE = 62;
	public static String URL_GIGYA_NOTIFY_REGISTRATION ="https://socialize.gigya.com/socialize.notifyRegistration";
	public static String URL_GIGYA_NOTIFY_LOGIN ="https://socialize.gigya.com/socialize.notifyLogin";
	public static String URL_GIGYA_INFOUSER = "https://socialize.gigya.com/socialize.getUserInfo";
	public static String GIGYA_IN_PANIC = "GIGYA_IN_PANIC";
	public static String PLATFORMS_MOBILE = "PLATFORMS_MOBILE";
	public static String ERROR_BE_ACTION_CA_3036_INCORRECT_USER_PASSWORD = "ERROR_BE_ACTION_CA_3036_INCORRECT_USER_PASSWORD";
	public static String UID_NOT_FOUND = "404003";
	public static String UNAUTHORIZED_USER = "403005";
	
	public static final String REG_MAIL_SUCCESS = "Successfully sent registration mail for user: ";
	public static final String REG_MAIL_FAILURE = "Exception thrown while sending registration mail for user: ";
	public static final String SUBS_MAIL_SUCCESS = "Successfully sent purchase mail for user: ";
	public static final String SUBS_MAIL_FAILURE = "Exception thrown while sending purchase mail for user: ";
	public static final String SOCIAL_MEDIA = "isSocialMedial: ";
	public static final String CRN_1 = "CRN1: ";
	public static final String SUBSCRIBER = "Subscriber";
	public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String SUBSCRIPTION_DATE_FORMAT = "dd/MM/yy HH:mm";
	public static final String EMPTY_STRING = "";
	public static final String CONTENT_TYPE = "text/html";
	public static final String PATTERN = "(?i)(<#>)(.*?)(<#>)";
	public static final String MAIL_SUBSCRIBER_TAG = "<#>";
	public static final String PATTERN_MEMEBSHIP = ".*membership*.";
	
	//Below is the newly added input parameter receiptId on the ProductPurchaseBpoint API - for the new CR - 2nd season
	public static final String RECEIPT_ID = "receiptID";
	
	//Below is the newly added input parameter transactionID on the ProductPurchaseBpoint API - for the new CR - 2nd season
		public static final String TRANSACTION_ID = "transactionID";
	// Below are the constants added for the input parameters on LoginCA
	public static final String UID = "UID";
	public static final String SIGNATURETIMESTAMP = "signatureTimestamp";
	////2nd Season - New CR - Auto Renewal of App packages.
	public static String ERROR_4101_ERROR_CONTEN_ALREADY_BOUGHT_BY_DIFFERENT_USER = "ERROR_4101_ERROR_CONTEN_ALREADY_BOUGHT_BY_DIFFERENT_USER";
	public static final String PURCHASE_DATE = "purchaseDate";
	//UseVoucherCA
	//Below is the newly added input parameter used to  Checking voucher code is expired or not,if voucher code is already expired then throws exception
	public static String ERROR_BE_ACTION_4102_ACTION_VOUCHER_EXPIRED = "ERROR_BE_ACTION_4102_ACTION_VOUCHER_EXPIRED";
	
	//Added as part GetUserEligibleChaanelCA
	//If user does not have any active channels.
	public static String ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS = "ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS";
	//user concurrent streaming
	public static String ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS = "ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS";

}
