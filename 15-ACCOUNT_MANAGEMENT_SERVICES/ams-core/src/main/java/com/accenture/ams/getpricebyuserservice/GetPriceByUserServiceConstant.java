package com.accenture.ams.getpricebyuserservice;

public class GetPriceByUserServiceConstant {
	public static final int RET_OK = 0;
	public static final int RET_GENERIC_ERROR = 1;
	public static final int RET_DB_ERROR = 2;
	public static final int RET_USER_PRESENT = 3;
	public static final int RET_USER_NOT_PRESENT = 4;
	public static final int RET_REQ_NOT_ALLOWED = 5;
	public static final int RET_MISSING_PARAM = 6;
	public static final int RET_CRM_ACC_NOT_EXIST = 7;
	
	public static final String[] RET_CODE = {"ACN_200", "ACN_300", "ACN_3061", "ACN_3062", "ACN_3063", "ACN_3064", "ACN_3065", "ACN_3066"};
	
	public static final String[] RET_DESC = {
		"OK", 
		"300-GENERIC ERROR", 
		"DB ERROR", 
		"CRM Account already present",
		"User doesn’t exist",
		"Request Not Allowed", 
		"Missing Required Parameter <PARAMETER>",
		"CRM Account doesn’t exist"		
	};

	
	/* COSTANTI PER GLI ERROR CODE */
	public static final String ERROR_BE_3022_DAO_QUERY = "Query.";
	
	
	/* COSTANTE PER IL NOME DEL FILE DI CONF DEI TENANT */
	public static final String TENANT_PROPERTIES = "/tenant.properties";
	public static String AMS_PROPERTIES_PATH_ENV = "AMS_PROPERTIES";
	
}
