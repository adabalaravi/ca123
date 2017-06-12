package com.accenture.avs.ca.be.util;

import java.util.HashMap;
import java.util.Map;



public class WebConstants {
	
	public static final String PAYMENT_PGW_BPOINT = "PGW";//"BPOINTGW";
	public static final String MSG_PIPE = "|";
	public static final String MSG_PIPE_SPLIT = "\\|";
	public static final String APPENDER = "NA";
	public static final String EMPTY_STRING = "";
	
	
	public static final String INPROGRESS = "INPROGRESS";
	public static final String ERROR = "ERROR";
	public static final String COMPLETED = "COMPLETED";
	public static final String SUCCESS="SUCCESS";
  
	public static final String IN_REQUEST_RESP_CODE = "in_request_resp_code";
	public static final String IN_VERIFY_TOKEN = "in_verify_token";
	public static final String IN_AMOUNT = "in_amount";
	public static final String IN_BILLERCODE = "in_billercode";
	public static final String IN_MERCHANT_REFERENCE = "in_merchant_reference";
	public static final String IN_CRN1 = "in_crn1";
	public static final String IN_CRN2 = "in_crn2";
	public static final String IN_CRN3 = "in_crn3";
	public static final String IN_RESPONSE_CODE = "in_response_code";
	public static final String IN_BANK_RESPONSE_CODE = "in_bank_response_code";
	public static final String IN_AUTH_RESULT = "in_auth_result";
	public static final String IN_TXN_NUMBER = "in_txn_number";
	public static final String IN_RECEIPT_NUMBER = "in_receipt_number";
	public static final String IN_SETTLEMENT_DATE = "in_settlement_date";
	public static final String IN_EXPIRY_DATE = "in_expiry_date";
	public static final String IN_ACCOUNT_NUMBER = "in_account_number";
	public static final String IN_PAYMENT_DATE = "in_payment_date";
	public static final String IN_STORE_CARD = "in_store_card";	  

		// From SYS Messages/ .properties
		
		public static final String MERCHANT_NUMBER = "MERCHANT_NUMBER";
		public static final String MERCHANT_USERNAME = "MERCHANT_USERNAME";
		public static final String MERCHANT_PASSWORD = "MERCHANT_PASSWORD";
		public static final String CRN_1 = "CRN_1";
		
		public static final String BPOINT_URL_CONNECTION_RETRY_COUNT = "BPOINT_URL_CONNECTION_RETRY_COUNT";
		public static final String BPOINT_CONNECTION_TIME_OUT ="BPOINT_CONNECTION_TIME_OUT";
		
		public static final String BPOINT_AUTH_URL = "BPOINT_AUTH_URL";//"https://evolve-uat.premier.com.au/connectornew/auth.aspx";
		public static final String BPOINT_PAY_URL = "BPOINT_PAY_URL";//"https://evolve-uat.premier.com.au/connectornew/pay.aspx";
		public static final String BPOINT_VERIFY_URL = "BPOINT_VERIFY_URL";//https://evolve-uat.premier.com.au/connectornew/verify.aspx";
		
		public static final String ERR_ORDERID_NOT_VALID ="ERR_ORDERID_NOT_VALID"; // No Record in DB.
		public static final String INFO_TRAN_COMPLETED ="INFO_TRAN_COMPLETED"; // Transaction Already completed.
		public static final String ERR_TRAN_PURCHASE_ERROR ="ERR_TRAN_PURCHASE_ERROR";
		
		public static final String BPOINT_SUCCESSFUL_RESP_CODE = "0";
		public static final String BPOINT_SUCCESSFUL_RESULT_CODE = "OK";
		public static final String BPOINT_FAILURE_RESULT_CODE = "KO";
		
		public static final String PURCHASE_TRANSACTION_IN_PANIC="PURCHASE_TRANSACTION_IN_PANIC";
		public static final String BPOINT_IN_PANIC="BPOINT_IN_PANIC";
		
		//Added for registration and subscription mail change request
		public static final String REGISTRATION_MAIL_TEXT="REGISTRATION_MAIL_TEXT";
		public static final String REGISTRATION_MAIL_SUBJECT="REGISTRATION_MAIL_SUBJECT";
		public static final String SUBSCRIPTION_MAIL_TEXT="SUBSCRIPTION_MAIL_TEXT";
		public static final String SUBSCRIPTION_MAIL_SUBJECT="SUBSCRIPTION_MAIL_SUBJECT";
		public static final String SUBSCRIPTION_MAIL_TEXT_MEMBERSHIP="SUBSCRIPTION_MAIL_TEXT_MEMBERSHIP";
		public static final String MAIL_FROM_ADDRESS_NO_REPLY="MAIL_FROM_ADDRESS_NO_REPLY";
		
		//MAIL PANIC FLAG
		public static final String MAIL_GATEWAY_PANIC_FLAG = "MAIL_GATEWAY_PANIC_FLAG";
		public static final String YES = "Y";
		public static final String NO = "N";
		
		// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS 
		//CR CHANGES FOR GET PRODUCT LIST FOR CONDITIONAL PACKAGES AND PRODUCTS TO BE SHOWN CHANGES 
		public static final String CONDITIONAL_PACKAGE_IDS_GETPRODUCTLIST="CONDITIONAL_PACKAGE_IDS_GETPRODUCTLIST"; 
		public static final String PACKAGES_TOBE_SHOWN_GETPRODUCTLIST="PACKAGES_TOBE_SHOWN_GETPRODUCTLIST";
		
		//UseVoucherCA
		// This Captcha code validation & respective logic can be bypassed based on SYS_PARAMETER values “Y/N”. Now Currently for this Optus requirement , this sys_parameter value will be “N”.
		public static final String IS_CAPTCHA_VALIDATION_REQUIRED="IS_CAPTCHA_VALIDATION_REQUIRED";
		
		//Concurrent Streaming Threshold count

		public static final String CONCURRENT_STREAMING_THRESHOLD_COUNT="CONCURRENT_STREAMING_THRESHOLD_COUNT";
		
		public static final String CONCURRENT_STREAMING_IN_PANIC="CONCURRENT_STREAMING_IN_PANIC";
		
		public static final String CHANNELS_FOR_FREE_VIDEOS="CHANNELS_FOR_FREE_VIDEOS";
		
		public static final String IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED="IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED";

		//BT package ID's List
		public static final String BT_PACKAGE_IDS="BT_PACKAGE_IDS";
		//Prefix String for receiptID of BT package integration
		public static final String PREFIX_RECEIPTID="PREFIX_RECEIPTID";
		
		// This is used to get the respective systemMessage Key for given BPoint error code
		public static String getSysMessageParam(String param)
		{
			
			HashMap<String,String> sysMap = (HashMap<String,String>)getSysMessageMap();
			String sysParam = sysMap.get(param);
			
			return sysParam;
		}

		public static Map<String,String> getSysMessageMap()
		{
			HashMap<String, String> errorCodeMap = new HashMap<String, String>();
			errorCodeMap.put("BPOINT_ERROR_CODE_1",  "ERROR_4001_INVALID_MERCHANT_ID");
			errorCodeMap.put("BPOINT_ERROR_CODE_2",  "ERROR_4002_INVALID_MERCHANT_USERNAME");
			errorCodeMap.put("BPOINT_ERROR_CODE_3",  "ERROR_4003_INVALID_MERCHANT_PASSWORD");
			errorCodeMap.put("BPOINT_ERROR_CODE_4",  "ERROR_4004_INVALID_IP_ADDRESS");
			errorCodeMap.put("BPOINT_ERROR_CODE_5",  "ERROR_4005_INVALID_AMOUNT");
			errorCodeMap.put("BPOINT_ERROR_CODE_6",  "ERROR_4006_AMOUNT_CANNOT_BE_ZERO_OR_LESSTHAN_ZERO");
			errorCodeMap.put("BPOINT_ERROR_CODE_7",  "ERROR_4007_INVALID_MERCHANT_REFERENCE_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_8",  "ERROR_4008_INVALID_CRN1");
			errorCodeMap.put("BPOINT_ERROR_CODE_9",  "ERROR_4009_INVALID_CRN2");
			errorCodeMap.put("BPOINT_ERROR_CODE_10", "ERROR_4010_INVALID_CRN3");
			errorCodeMap.put("BPOINT_ERROR_CODE_11", "ERROR_4011_INVALID_CREDIT_CARD_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_12", "ERROR_4012_INVALID_EXPIRY_MONTH");
			errorCodeMap.put("BPOINT_ERROR_CODE_13", "ERROR_4013_INVALID_EXPIRY_YEAR");
			errorCodeMap.put("BPOINT_ERROR_CODE_14", "ERROR_4014_INVALID_CW");
			errorCodeMap.put("BPOINT_ERROR_CODE_15", "ERROR_4015_INVALID_RECEIPT_PAGE_URL");
			errorCodeMap.put("BPOINT_ERROR_CODE_16", "ERROR_4016_INVALID_RESPONSE_CODE");
			errorCodeMap.put("BPOINT_ERROR_CODE_17", "ERROR_4017_INVALID_BANK_RESP_CODE");
			errorCodeMap.put("BPOINT_ERROR_CODE_18", "ERROR_4018_INVALID_AUTH_RESULT");
			errorCodeMap.put("BPOINT_ERROR_CODE_19", "ERROR_4019_INVALID_TXN_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_20", "ERROR_4020_INVALID_RECEIPT_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_21", "ERROR_4021_INVALID_SETTLEMENT_DATE");
			errorCodeMap.put("BPOINT_ERROR_CODE_22", "ERROR_4022_INVALID_EXPIRY_DATE");
			errorCodeMap.put("BPOINT_ERROR_CODE_23", "ERROR_4023_INVALID_ACCOUNT_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_24", "ERROR_4024_INVALID_PAYMENT_DATE");
			errorCodeMap.put("BPOINT_ERROR_CODE_25", "ERROR_4025_INVALID_PAY_TOKEN");
			errorCodeMap.put("BPOINT_ERROR_CODE_26", "ERROR_4026_INVALID_VERIFY_TOKEN");
			errorCodeMap.put("BPOINT_ERROR_CODE_27", "ERROR_4027_MERCHANT_NUMBER_NOT_EXISTS");
			errorCodeMap.put("BPOINT_ERROR_CODE_28", "ERROR_4028_BILLER_CODE_NOT_EXISTS");
			errorCodeMap.put("BPOINT_ERROR_CODE_29", "ERROR_4029_INVALID_LOGIN_DETAILS");
			errorCodeMap.put("BPOINT_ERROR_CODE_30", "ERROR_4030_INVALID_SIGNATURE_DATA");
			errorCodeMap.put("BPOINT_ERROR_CODE_31", "ERROR_4031_INVALID_SESSION_REQUEST");
			errorCodeMap.put("BPOINT_ERROR_CODE_32", "ERROR_4032_INVALID_DV_TOKEN");
			errorCodeMap.put("BPOINT_ERROR_CODE_33", "ERROR_4033_DATA_VAULT_TOKEN_NOT_EXISTS");
			errorCodeMap.put("BPOINT_ERROR_CODE_34", "ERROR_4034_INVALID_ACCOUNT_NAME");
			errorCodeMap.put("BPOINT_ERROR_CODE_35", "ERROR_4035_INVALID_BSB_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_36", "ERROR_4036_ACCOUNT_NUMBER");
			errorCodeMap.put("BPOINT_ERROR_CODE_37", "ERROR_4037_USER_NOT_ACCEPTED_DIRECT_DEBIT_TERMS_AND_CONDITIONS");
			errorCodeMap.put("BPOINT_ERROR_CODE_100","ERROR_4100_SYSTEM_ERROR");
			
			return errorCodeMap;
		}
	  
		}
