package com.accenture.ams.accountmgmtservice;

public class AccountMgmtServiceConstant {

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
	
	public static final String INSERT = "1";
	public static final String UPDATE = "2";

	public static final String WARNING = "SchemaValidationWarning";
	public static final String ERROR = "SchemaValidationError";
	public static final String FATAL_ERROR = "SchemaValidationFatalError";
	
	/* VALORI DEGLI ATTRIBUTI UTENTE DA INSERIRE IN ATTRIBUTE ID */
	public static final int ATTR_PARENTAL_CONTROL_PIN = 1;
	public static final int ATTR_ISQA = 2;
	public static final int ATTR_USER_PC_LEVEL = 3;
	public static final int ATTR_REMEMBER_PIN = 4;
	public static final int ATTR_BANDWITH = 6;
	public static final int ATTR_PAY_TYPE = 15;
	public static final int ATTR_CRM_ACC_TYPE = 23;
	public static final int ATTR_CRM_ACC_COUNTRY = 24;
	public static final int ATTR_CRM_ACC_PURC_BLOCK_FLAG = 26;
	public static final int ATTR_CRM_ACC_PURC_BLOCK_THRES_CURR = 27;
	public static final int ATTR_CRM_ACC_PURC_BLOCK_THRES_PRD = 29;
	public static final int ATTR_CRM_ACC_PURC_BLOCK_ALERT_CH = 30;
	public static final int ATTR_CRM_ACC_PURC_BLOCK_ALERT_MODE = 31;
	public static final int ATTR_CRM_ACC_PURC_BLOCK_INT_THRES = 32;
	public static final int ATTR_CRM_ACC_CONS_BLOCK_FLAG = 33;
	public static final int ATTR_CRM_ACC_CONS_BLOCK_THRES_VAL = 34;
	public static final int ATTR_CRM_ACC_CONS_BLOCK_THRES_PRD = 35;
	public static final int ATTR_CRM_ACC_CONS_BLOCK_ALERT_FLAG = 36; 
	public static final int ATTR_CRM_ACC_CONS_BLOCK_ALERT_CH = 37;
	public static final int ATTR_CRM_ACC_CONS_BLOCK_ALERT_MODE = 38;
	public static final int ATTR_CRM_ACC_CONS_BLOCK_INT_THRES = 39;
	public static final int ATTR_USER_LANG = 40;
	public static final int ATTR_PIN_PURCHASE = 53;
	public static final int ATTR_PC_EXTEND_RATING = 48;
	public static final int ATTR_USER_PWD = 41;
	public static final int ATTR_RETAILER_ID = 54;
	
	/* POSSIBILI VALORI DEL CAMPO USER TYPE */
	public static final String USER_TYPE_DEFAULT = "1";
	public static final String USER_TYPE_QA = "2";
	public static final String USER_TYPE_VIP = "3";
	
	/* COSTANTI PER IL WS UPDATE_CRMACCOUNT_COMMERCIAL_PROFILE */
	public static final String ADD_COMMERCIAL_PROFILE = "ADD";
	public static final String DELETE_COMMERCIAL_PROFILE = "DELETE";
	
	/* COSTANTI PER IL WS UPDATE PASSWORD SERVICE */
	public static final String OP_RESET_PWD = "RESET";
	public static final String OP_CHANGE_PWD = "CHANGE";
	public static final String TXT_WRONG_PWD = "Wrong Password";
	
	/* COSTANTI PER GLI ERROR CODE */
	public static final String ERROR_BE_3022_DAO_QUERY = "Query.";
	public static final String INFO_BE_3026_DAO_EXECUPDATE = "Exec delete.";
	public static final String INFO_BE_3025_DAO_EXECDELETE = "Exec update.";
	
	/* COSTANTE PER IL NOME DEL FILE DI CONF DEI TENANT */
	public static final String TENANT_PROPERTIES = "/tenant.properties";
	public static final String AMS_PROPERTIES_PATH_ENV = "AMS_PROPERTIES";
	public static final String AMS_PROPERTIES_FILE = "ams.properties";
	public static final Long AMS_LOG4J_RELOAD_TIME_DEF = 3000L;
	
	/* COSTANTI PER IL WS CRM_CONTENT_PURCHASE_SERVICE */
	public static final String PURCHASE_OP_REFUND = "REFUND";
	public static final String PURCHASE_OP_PURCHASE = "PURCHASE";
	public static final String PURCHASE_DEF_OP = PURCHASE_OP_PURCHASE;
	public static final String DEF_PURCHASE_CHANNEL = "CONSOLE";
	
	public static final String PKG_VIEWS_NUMBER="PKG_VIEWS_NUMBER";
	public static final String PKG_START_DATE="PKG_START_DATE";
	public static final String PKG_END_DATE="PKG_END_DATE";
	public static final String PKG_VALIDITY_PERIOD="PKG_VALIDITY_PERIOD";
	
	public static final String[] PKG_ATTRIBUTE_SET = {PKG_VIEWS_NUMBER, PKG_START_DATE, PKG_END_DATE, PKG_VALIDITY_PERIOD};

	public static final String PKG_TYPE_VOD = "TVOD";
	public static final String PKG_TYPE_PPV = "PPV";
	
	public static final String DEF_PURCHASE_TYPE = "INVOICE";
	public static final String PURCHASE_STATUS_COMPLETED = "COMPLETED";
	public static final String PURCHASE_STATUS_REFUNDED = "REFUNDED"; 
}
