package com.accenture.avs.console.common.constants;

import java.io.File;

public interface ApplicationConstants {

	// tomcat
	String CONFIGURATION_PATH_TOMCAT = null;

	// jboss 6
	String CONFIGURATION_PATH_JBOSS6 = System.getProperty("jboss.server.home.dir") + File.separator + "conf" + File.separator;

	// JBOSS 7
	String CONFIGURATION_PATH_JBOSS7 = System.getProperty("jboss.home.dir") + File.separator + "standalone" + File.separator + "conf" + File.separator;

	String CONFIGURATION_PATH = CONFIGURATION_PATH_TOMCAT;

	String CONF_APP_DIR = "avsconsole";
	String LOG4J_FILE_NAME = "log4j.properties";
	String APPLICATION_FILE_NAME = "application.properties";
	String MESSAGE_BUNDLE = "avsconsole.resources.messages";
	String DATE_FORMAT = "dd/MM/yyyy";

	String ATTRIBUTE_OBJECT_NAME = "item";

	// WRAPPER BEANS
	String CONTROLLER_MENU_NAME = "menuController";
	String CONTROLLER_POPUP_NAME = "popupBean";
	String CONTROLLER_LAYOUT_NAME = "layoutController";
	String CONTROLLER_SESSION_NAME = "infoSessionBean";
	String MANAGER_OPERATOR = "operatorManager";

	// WEB SERVICE WSDL URLs */
	String ROOT_ENDPOINT_URL = "root.endpoint";
	String SERVICE_OPERATOR_BEAN = "operatorService";
	String WSDL_OPERATOR_URL = "sdpoperatorservice.endpoint";

	String CODE_OK = "000";
	String CODE_MANDATORY_FIELD_MISSING = "010";
	String CODE_START_POSITION_OUT_OF_RANGE = "001";
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
	String CODE_CHANGE_PASSWORD = "220";
	String CODE_DELETE_FAILED = "240";

	int ORGANIZZATION_NAME_SEARCH = 1;
	int FIRST_LAST_NAME_SEARCH = 2;
	int CREDENTIAL_SEARCH = 3;
	int BILLING_ACCOUNT_NAME_SEARCH = 4;
	int CHILD_PARTY_NAME_SEARCH = 5;

	long BUSINESS_PARTY_GROUP_ID = 1L;
	long RESIDENTIAL_PARTY_GROUP_ID = 2L;

	String SUBSCRIPTION_TYPE = "subscriptionType";

	/* Web Service Wsdl Urls */
	String CREDENTIALS_WSDL_URL = "sdpcredentialservice.endpoint";
	String BILLING_WSDL_URL = "sdpbillingservice.endpoint";
	String SITE_WSDL_URL = "sdpsiteservice.endpoint";
	String PARTY_WSDL_URL = "sdppartyservice.endpoint";
	String SUBSCRIPTION_WSDL_URL = "sdpsubscriptionservice.endpoint";
	String PARTY_GROUP_WSDL_URL = "sdppartygroupservice.endpoint";

	/* AVS Web Service Wsdl Urls */
	String AVS_ROOT_ENDPOINT_URL = "avs_root.endpoint";
	String AVS_ACCOUNT_MANAGEMENT_URL = "avs_account_management.endpoint";
	String AVS_PRICE_MANAGEMENT_URL = "avs_price_management.endpoint";

	String AVS_SERVICE_BEAN_NAME = "avsService";
	String AVS_SDP_URL = "avsservice.endpoint";

	String CATALOGUE_CONSOLE_URL = "catalogueconsole.url";
	String ASSURANCE_CONSOLE_URL = "assuranceconsole.url";

	/* Managed Bean Names */
	String PARTY_GROUP_SERVICE_BEAN = "partyGroupService";

	String PARTY_SERVICE_BEAN = "partyService";
	String PARTY_MANAGER = "partyManager";
	String PARTY_VALIDATION_NAME_FIELD = "mainForm:partyName";

	String BILLING_SERVICE_BEAN_NAME = "billingService";
	String BILLING_MANAGER = "billingManager";
	String BILLING_BEAN_NAME = "billingBean";

	String SITE_SERVICE_BEAN_NAME = "siteService";
	String SITE_MANAGER = "siteManager";
	String SITE_BEAN_NAME = "siteBean";

	String CREDENTIALS_BEAN_NAME = "credentialsBean";
	String CREDENTIALS_MANAGER = "credentialsManager";
	String SECRET_QUESTION_BEAN_NAME = "secretQuestionBean";

	String SUBSCRIPTION_MANAGER = "subscriptionManager";
	String SUBSCRIPTION_SERVICE_BEAN = "subscriptionService";

	String AVS_USER_SERVICE_BEAN = "avsUserService";

	/* Login */
	String LOGIN_BEAN_NAME = "loginBean";
	String CREDENTIALS_SERVICE_BEAN = "credentialsServiceBean";

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

	String OPERATOR_STATUS_ACTIVE = "operator.status.active";
	String OPERATOR_STATUS_INACTIVE = "operator.status.inactive";
	String OPERATOR_STATUS_ACT = "Active";
	String OPERATOR_STATUS_INACT = "Inactive";

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

	String OFFER_MANAGER = "offerManager";
	String SOLUTION_OFFER_SERVICE_BEAN = "solutionOfferService";
	String SOLUTION_OFFER_WSDL_URL = "solutionofferservice.endpoint";

	String SOLUTION_SERVICE_BEAN = "solutionService";
	String SOLUTION_WSDL_URL = "solutionservice.endpoint";

	String PACKAGE_SERVICE_BEAN = "packageService";
	String PACKAGE_WSDL_URL = "packageservice.endpoint";

	String OFFER_SERVICE_BEAN = "offerService";
	String OFFER_WSDL_URL = "offerservice.endpoint";

	String VOUCHER_SERVICE_BEAN = "voucherService";
	String VOUCHER_WSDL_URL = "voucherservice.endpoint";

	String COOKIE_ENABLED = "Sticky_Cookie";
	String COOKIE_NAME = "Cookie_Name";
	String COOKIE_VALUE = "Cookie_Value";
	
	String ERROR_BEAN_NAME = "errorBean";
}
