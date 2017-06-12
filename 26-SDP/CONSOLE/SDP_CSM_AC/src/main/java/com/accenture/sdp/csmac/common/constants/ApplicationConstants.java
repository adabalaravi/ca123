package com.accenture.sdp.csmac.common.constants;

import java.io.File;

public interface ApplicationConstants {

	// tomcat
	String CONFIGURATION_PATH_TOMCAT = null;

	// jboss 6
	String CONFIGURATION_PATH_JBOSS6 = System.getProperty("jboss.server.home.dir") + File.separator + "conf" + File.separator;

	// JBOSS 7
	String CONFIGURATION_PATH_JBOSS7 = System.getProperty("jboss.home.dir") + File.separator + "standalone" + File.separator + "conf" + File.separator;

	String CONFIGURATION_PATH = CONFIGURATION_PATH_TOMCAT;

	String CONF_APP_DIR = "assuconsole";
	String LOG4J_FILE_NAME = "log4j.properties";
	String APPLICATION_FILE_NAME = "application.properties";
	String MESSAGE_BUNDLE = "assuconsole.resources.messages";
	String DATE_FORMAT = "dd/MM/yyyy";
	
	String ATTRIBUTE_OBJECT_NAME = "item";

	// SCOPED BEANS
	String CONTROLLER_POPUP_NAME = "popupBean";
	String CONTROLLER_SESSION_NAME = "infoSessionBean";
	String CONTROLLER_MENU_NAME = "menuBean";
	String CONTROLLER_LAYOUT_NAME = "layoutController";
	

	// WEB SERVICE WSDL URLs
	String ROOT_ENDPOINT_URL = "root.endpoint";
	String CREDENTIALS_WSDL_URL = "sdpcredentialservice.endpoint";
	String ACCOUNT_WSDL_URL = "sdpaccountservice.endpoint";
	String SITE_WSDL_URL = "sdpsiteservice.endpoint";
	String PARTY_WSDL_URL = "sdppartyservice.endpoint";
	String SUBSCRIPTION_WSDL_URL = "sdpsubscriptionservice.endpoint";
	String OPERATOR_WSDL_URL = "sdpoperatorservice.endpoint";
	String PARTY_GROUP_WSDL_URL = "sdppartygroupservice.endpoint";
	String DEVICE_ACCES_WSDL_URL = "sdpdeviceaccessservice.endpoint";
	String DEVICE_BRAND_WSDL_URL = "sdpdevicebrandservice.endpoint";
	String DEVICE_WSDL_URL = "sdpdeviceservice.endpoint";
	String DEVICE_POLICY_WSDL_URL = "sdpdevicepolicyservice.endpoint";
	String SDP_AVS_WSDL_URL = "avsservice.endpoint";
	// AVS WEB SERVICES
	String AVS_AMS_ROOT_ENDPOINT_URL = "avs_ams_root.endpoint";
	String AVS_AMS_WSDL_URL = "avs_account_management.endpoint";
	
	// WEB SERVICE RESPONSE CODE
	String CODE_OK = "000";
	String CODE_START_POSITION_OUT_OF_RANGE = "001";
	String CODE_MANDATORY_FIELD_MISSING = "010";
	String CODE_VALUE_NOT_ALLOWED = "020";
	String CODE_INSERT_FAILED = "030";
	String CODE_DUPLICATE_ENTRY = "040";
	String CODE_STATUS_TRANSACTION_ERROR = "050";
	String CODE_PARENT_NOT_FOUND = "060";
	String CODE_GENERIC_ERROR = "070";
	String CODE_UPDATE_FAILED = "090";
	String CODE_ELEMENT_NOT_FOUND = "100";
	String CODE_ELEMENT_WITH_NOT_DELETED_CHILDS = "110";
	String CODE_ZERO_RECORD_FOUND = "130";
	String CODE_UPDATE_NOT_ALLOWED_FOR_STATUS = "140";
	String CODE_ELEMENT_ALREADY_ASSOCIATED = "150";
	String CODE_CONSTRAINT_VIOLATED = "210";
	String CODE_DELETE_FAILED = "240";

	int CHILD_PARTY_NAME_SEARCH = 1;
	int FIRST_LAST_NAME_SEARCH = 2;
	int CREDENTIAL_SEARCH = 3;
	int BILLING_ACCOUNT_NAME_SEARCH = 4;
	int ORGANIZZATION_NAME_SEARCH = 5;

	String SUBSCRIPTION_TYPE = "subscriptionType";
	String USER_SUBSCRIPTION_TYPE = "userSubscriptionType";

	String AVS_SERVICE_BEAN_NAME = "avsService";


	/* Managed Bean Names */
	String MESSAGE_BEAN_NAME = "popupBean";

	String PARTY_GROUP_SERVICE_BEAN = "partyGroupService";

	String PARTY_SERVICE_BEAN = "partyService";
	String PARTY_MANAGER = "partyManager";
	String PARTY_VALIDATION_NAME_FIELD = "mainForm:partyName";

	String ACCOUNT_SERVICE_BEAN_NAME = "accountService";

	String SITE_SERVICE_BEAN_NAME = "siteService";

	String SUBSCRIPTION_MANAGER = "subscriptionManager";
	String SUBSCRIPTION_SERVICE_BEAN = "subscriptionService";

	String OPERATOR_RIGHTS_MANAGER_BEAN = "operatorRightsManagerBean";

	String AVS_AMS_SERVICE_BEAN = "avsUserService";

	/* Login */
	String CREDENTIALS_SERVICE_BEAN = "credentialsServiceBean";

	String OPERATOR_SERVICE_BEAN = "operatorService";
	String OPERATOR_BEAN_NAME = "operatorBean";
	String OPERATOR_MANAGER = "operatorManager";
	

	String EXTERNALID = "externalId";
	String USER_DEFAULT_SITE_ID = "userDefaultSiteId";

	String SERVICEVARIANTNAME = "serviceVariantName";
	String STATUS = "status";
	String PARTY_GROUP_ID = "partyGroupId";
	String SERVICETEMPLATENAME = "serviceTemplateName";
	String PLATFORMNAME = "externalId";
	String SOLUTIONNAME = "serviceVariantName";
	String ITEM = "item";
	String STATUS_DELETED = "Deleted";
	String STATUS_ACTIVE = "Active";
	String LOGOUT_OUTCOME = "logout";

	String OPERATOR_ROLE_SUPER_ADMIN = "SuperAdmin";
	String OPERATOR_ROLE_ADMININISTRATOR = "Administrator";
	String OPERATOR_ROLE_OPERATOR = "Operator";
	String CONSOLE_ASSURANCE = "Assurance";
	String CONSOLE_CATALOGUE = "Catalogue";

	int OPERATOR_SEARCH_BY_USERNAME = 1;
	int OPERATOR_SEARCH_BY_FIRSTNAME_AND_LASTNAME = 2;
	int OPERATOR_SEARCH_BY_TENANT = 3;
	int OPERATOR_SEARCH_ALL = 4;

	String USER_NAME = "username";
	String FIRST_NAME = "firstName";
	String LAST_NAME = "username";
	String PASSWORD = "password";
	String ROLE = "role";

	String OPERATOR_ROLE_SUPER_ADMIN_LIST = "operator.role.SuperAdmin";
	String OPERATOR_ROLE_ADMINISTRATOR_LIST = "operator.role.Administrator";
	String OPERATOR_ROLE_OPERATOR_LIST = "operator.role.operator";

	String OLD_PASSWORD = "oldpassword";
	String NEW_PASSWORD = "newpassword";
	String CONFIRM_PASSWORD = "confirmNewPassword";
	String OLD_PASSWORD_LBL = "operator.oldPassword";
	String NEW_PASSWORD_LBL = "operator.newPassword";
	String CONFIRM_PASSWORD_LBL = "operator.confirmNewPassword";

	String SOLUTION_OFFER_MANAGER = "solutionOfferManager";
	String SOLUTION_OFFER_SERVICE_BEAN = "solutionOfferService";
	String SOLUTION_OFFER_WSDL_URL = "solutionofferservice.endpoint";

	String PACKAGE_SERVICE_BEAN = "packageService";
	String PACKAGE_WSDL_URL = "packageservice.endpoint";

	String OFFER_SERVICE_BEAN = "offerService";
	String OFFER_WSDL_URL = "offerservice.endpoint";

	String VOUCHER_SERVICE_BEAN = "voucherService";
	String VOUCHER_WSDL_URL = "voucherservice.endpoint";
	
	String DEVICE_BEAN_NAME = "deviceBean";
	String DEVICE_RULES_BEAN_NAME = "deviceRulesBean";
	String DEVICE_MANAGER = "deviceManager";
	
	String DEVICE_ACCESS_MANAGER = "deviceAccessManager";
	String DEVICE_BRAND_MANAGER ="deviceBrandManager";
	String DEVICE_MODEL_MANAGER ="deviceModelManager";
	
	String DEVICE_ACCESS_SERVICE_BEAN = "deviceAccessService";
	String DEVICE_SERVICE_BEAN = "deviceService";
	String DEVICE_BRAND_SERVICE = "deviceBrandService";
	String DEVICE_MODEL_SERVICE = "deviceModelService";
	
	String DEVICE_POLICY_SERVICE_BEAN = "devicePolicyService";
	String DEVICE_POLICY_MANAGER = "devicePolicyManager";

	String TIMEZONE = "timezone";
}
