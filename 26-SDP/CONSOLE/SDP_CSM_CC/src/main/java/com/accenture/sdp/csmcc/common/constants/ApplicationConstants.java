package com.accenture.sdp.csmcc.common.constants;

public interface ApplicationConstants {

	String CONFIGURATION_PATH = ConfigurationPathConstant.CONFIGURATION_PATH;

	String STAR_EXTERNAL_PLATFORM_PREFIX = "star.external.platform.";
	String STAR_EXTERNAL_PLATFORMS_SEPARATOR = ";";

	String TENANT_NAME = "tenant.name";

	String CONF_APP_DIR = "catconsole";
	String FILE_SEPARATOR_PROP = "file.separator";
	String CONFIG_DIR_ENV_VAR = "CSM_CC_CONFIG";
	String CSM_MES_CONFIG = "CSM_MES_CONFIG";
	String LOG4J_FILE_NAME = "log4j.properties";
	String APPLICATION_FILE_NAME = "application.properties";
	String DATE_FORMAT = "dd/MM/yyyy";
	String PAGINATOR_DEFAULT_ROWS = "10";

	String YES_VALUE = "Y";
	String NO_VALUE = "N";

	String SERVICE_VARIANT_ID_AVS = "serviceVariantIdAVS";
	String SOLUTION_NAME_AVS = "solutionNameAVS";
	String OFFER_TYPES_AVS = "offer.types";
	String OFFER_TYPES_AVS_LABEL = "offer.typesLabel";

	String CODE_OK = "000";
	String CODE_MANDATORY_FIELD_MISSING = "010";
	String CODE_START_POSITION_OUT_OF_RANGE = "001";
	String CODE_VALUE_NOT_ALLOWED = "020";
	String CODE_INSERT_FAILED = "030";
	String CODE_DUPLICATE_ENTRY = "040";
	String CODE_STATUS_TRANSACTION_ERROR = "050";
	String CODE_STATUS_TRANSACTION_ERROR_DELETED = "050_Deleted";
	String CODE_PARENT_NOT_FOUND = "060";
	String CODE_GENERIC_ERROR = "070";
	String CODE_UPDATE_FAILED = "090";
	String CODE_ELEMENT_NOT_FOUND = "100";
	String CODE_ELEMENT_WITH_NOT_DELETED_CHILDS = "110";
	String CODE_ZERO_RECORD_FOUND = "130";
	String CODE_UPDATE_NOT_ALLOWED_FOR_STATUS = "140";
	String CODE_ELEMENT_ALREADY_ASSOCIATED = "150";
	String CONSTRAINT_VIOLATED = "210";
	String CODE_DELETE_FAILED = "240";

	/* Web Service Wsdl Urls */

	String ROOT_ENDPOINT_URL = "root.endpoint";
	String AVS_ROOT_ENDPOINT_URL = "avs.root.endpoint";

	String PLATFORM_WSDL_URL = "sdpplatform.endpoint";
	String SERVICETEMPLATE_WSDL_URL = "sdpservicetemplate.endpoint";
	String SERVICEVARIANT_WSDL_URL = "sdpservicevariant.endpoint";
	String SOLUTION_OFFER_WSDL_URL = "sdpsolutionoffer.endpoint";
	String SOLUTION_WSDL_URL = "sdpsolution.endpoint";
	String SOLUTIONTYPE_WSDL_URL = "sdpsolutiontype.endpoint";
	String SERVICETYPE_WSDL_URL = "sdpservicetype.endpoint";
	String OFFER_WSDL_URL = "sdpoffer.endpoint";
	String FREQUENCY_WSDL_URL = "sdpfrequency.endpoint";
	String PARTYGROUP_WSDL_URL = "sdppartygroup.endpoint";
	String PRICE_WSDL_URL = "sdpPriceCatalog.endpoint";
	String PACKAGE_WSDL_URL = "sdppackageservice.endpoint";
	String PACKAGE_PRICE_WSDL_URL = "sdppackagepriceservice.endpoint";
	String OPERATOR_WSDL_URL = "sdpoperatorservice.endpoint";
	String SERVICE_VARIANT_OPERATIONS_WSDL_URL = "sdpServiceVariantOperations.endpoint";
	String CURRENCY_WSDL_URL = "sdpcurrency.endpoint";
	String AVS_SERVICE_WSDL_URL = "avsservice.endpoint";
	String VOUCHER_WSDL_URL = "voucherservice.endpoint";
	String PACKAGE_INGESTOR_AVS_WSDL_URL = "packageingestor.endpoint";

	/* Managed Bean Names */

	String MENU_BEAN_NAME = "menuBean";
	String LAYOUT_CONTROLLER_BEAN_NAME = "layoutController";
	String METHOD_POPUP = "methodPopup";
	String OPERATOR_RIGHTS_MANAGER_BEAN = "operatorRightsManagerBean";

	/* AVS Managed Bean Names */

	String OFFER_MANAGER = "offerManager";
	String SOLUTION_OFFER_MANAGER = "solutionOfferMananger";
	String VOUCHER_MANAGER = "voucherManager";
	String VOUCHER_SERVICE_BEAN = "voucherService";

	String SOLUTION_OFFER_DISCOUNTED_MANAGER = "solutionOfferDiscountedManager";

	String PPV_BUNDLE_TABLE_BEAN_NAME = "ppvBoundleManager";

	/* Technical Catalogue */
	String PLATFORM_VALIDATION_NAME_FIELD = "mainForm:platformName";
	String PLATFORM_SERVICE_BEAN_NAME = "platformService";
	String PLATFORM_TABLE_BEAN_NAME = "platformTableBean";
	String PLATFORM_BEAN_NAME = "platformBean";

	String SERVICETEMPLATE_VALIDATION_NAME_FIELD = "mainForm:serviceTemplateName";
	String SERVICETEMPLATE_VALIDATION_PLATFORM_FIELD = "mainForm:serviceTemplatePlatformName";
	String SERVICETEMPLATE_VALIDATION_TYPE_FIELD = "mainForm:serviceTemplateTypeName";

	String SERVICETEMPLATE_TABLE_BEAN_NAME = "serviceTemplateTableBean";
	String SERVICETEMPLATE_BEAN_NAME = "serviceTemplateBean";
	String SERVICETEMPLATE_SERVICE_BEAN_NAME = "serviceTemplateService";
	Long SERVICETYPE_DEFAULT = 1L;
	String SERVICETYPE_SERVICE_BEAN_NAME = "serviceTypeService";

	String SERVICEVARIANT_VALIDATION_NAME_FIELD = "mainForm:serviceVariantName";
	String SERVICEVARIANT_VALIDATION_TEMPLATE_FIELD = "mainForm:serviceVariantTemplateName";
	String SERVICEVARIANT_TABLE_BEAN_NAME = "serviceVariantTableBean";
	String SERVICEVARIANT_BEAN_NAME = "serviceVariantBean";
	String SERVICEVARIANT_SERVICE_BEAN_NAME = "serviceVariantService";

	String OPERATION_SERVICE_BEAN_NAME = "operationService";
	String OPERATION_MANAGER = "operationManager";

	/* Commercial Catalogue */

	String PARTY_GROUP_MANAGER = "partyGroupManager";
	String PARTY_GROUP_VALIDATION_NAME_FIELD = "mainForm:partyGroupName";

	String SOLUTION_SERVICE_BEAN_NAME = "solutionService";
	String SOLUTION_TABLE_BEAN_NAME = "solutionTableBean";
	String SOLUTION_BEAN_NAME = "solutionBean";
	String SOLUTION_VALIDATION_NAME_FIELD = "mainForm:solutionName";
	String SOLUTION_VALIDATION_PARTYGROUPS_FIELD = "mainForm:solutionPartyGroups";
	String SOLUTION_VALIDATION_TYPE_FIELD = "mainForm:solutionTypeName";

	String SOLUTIONTYPE_SERVICE_BEAN_NAME = "solutionTypeService";

	String PARTYGROUP_SERVICE_BEAN_NAME = "partyGroupService";

	String SOLUTION_OFFER_TABLE_BEAN_NAME = "solutionOfferTableBean";
	String SOLUTION_OFFER_SERVICE_BEAN_NAME = "solutionOfferService";
	String SOLUTION_OFFER_BEAN_NAME = "solutionOfferBean";
	String SOLUTION_OFFER_VALIDATION_NAME_FIELD = "mainForm:solutionOfferName";
	String SOLUTION_OFFER_VALIDATION_SOLUTION_NAME_FIELD = "mainForm:solutionName";
	String ADD_OFFER_GROUP_POPUP_BEAN_NAME = "addOfferGroupPopupBean";

	String PACKAGE_SERVICE_BEAN_NAME = "packageService";
	String PACKAGE_MANAGER = "packageManager";
	String PACKAGE_PRICE_SERVICE_BEAN_NAME = "PackagePriceService";

	String OFFER_VALIDATION_NAME_FIELD = "mainForm:offerName";
	String OFFER_VALIDATION_VARIANT_FIELD = "mainForm:serviceVariantName";
	String OFFER_SERVICE_BEAN_NAME = "offerService";

	String AVS_GENERAL_SERVICE_BEAN_NAME = "avsGeneralService";
	String PACKAGE_INGESTOR_SERVICE_BEAN_NAME = "packageIngestorService";

	/* Price Catalogue */
	String PRICE_VALIDATION_NAME_FIELD = "mainForm:price";
	String PRICE_VALIDATION_PRICE_FIELD = "mainForm:price";

	String FREQUENCY_MANAGER = "frequencyManager";
	String FREQUENCY_SERVICE_BEAN_NAME = "frequencyService";
	String FREQUENCY_VALIDATION_NAME_FIELD = "mainForm:frequencyName";
	String FREQUENCY_VALIDATION_DAYS_FIELD = "mainForm:frequencyDays";

	String PRICE_MANAGER = "priceManager";
	String PRICE_SERVICE_BEAN_NAME = "priceService";

	String CURRENCY_SERVICE_BEAN_NAME = "currencyService";

	/* Login */

	String LOGIN_BEAN_NAME = "loginBean";
	String AUTHENTICATION_ORGANIZATION = "authenticationOrganization";

	/* PROFILE */

	String PROFILE_MANAGER = "profileManager";

	String INFO_SESSION_BEAN_NAME = "infoSessionBean";
	String MSGS_BEAN_NAME = "msgs";
	String POPUP_BEAN = "popupBean";
	String CONFIRM_POPUP_BEAN = "confirmPopupBean";

	String MESSAGE_BUNDLE = "catconsole.resources.messages";
	String PAGE_URL_BUNDLE = "catconsole.resources.pageurl";
	String EXTERNALID = "externalId";
	String SERVICEVARIANTNAME = "serviceVariantName";
	String STATUS = "status";
	String SERVICETEMPLATENAME = "serviceTemplateName";
	String PLATFORMNAME = "externalId";
	String SOLUTIONNAME = "serviceVariantName";
	String ITEM = "item";
	String FROM = "_from";
	String TO = "_to";
	String DELETED = ".deleted";
	String LOGOUT_OUTCOME = "logout";
	String STATUS_PARAMETER = "status";

	String OPERATOR_SERVICE_BEAN = "operatorService";
	String OPERATOR_BEAN_NAME = "operatorBean";
	String OPERATOR_MANAGER = "operatorManager";
	String PAGE_BEAN_NAME = "pageBean";

	String OPERATOR_GROUP_ALL = "operator.group.OPERATOR_GROUP_ALL";
	String OPERATOR_GROUP_AC = "operator.group.OPERATOR_GROUP_AC";
	String OPERATOR_GROUP_CC = "operator.group.OPERATOR_GROUP_CC";
	String OPERATOR_ROLE_ADMIN = "Administrator";
	String OPERATOR_ROLE_USER = "User";
	String OPERATOR_STATUS_ACT = "Active";
	String OPERATOR_STATUS_INACT = "Inactive";
	String OPERATOR_VALIDATION_NAME_FIELD = "mainForm:userName";
	String OPERATOR_STATUS_ACTIVE = "operator.status.active";
	String OPERATOR_STATUS_INACTIVE = "operator.status.inactive";
	String OPERATOR_STATUS_ACTIVE_VALUE = "operator.status.active.value";
	String OPERATOR_STATUS_INACTIVE_VALUE = "operator.status.inactive.value";
	String OPERATOR_GROUP_ALL_ST = "operator.group.OPERATOR_GROUP_ALL_ST";
	String OPERATOR_GROUP_AC_ST = "operator.group.OPERATOR_GROUP_AC_ST";
	String OPERATOR_GROUP_CC_ST = "operator.group.OPERATOR_GROUP_CC_ST";

	String OPERATOR_ROLE_ADMIN_LIST = "operator.role.Administrator";
	String OPERATOR_ROLE_USER_LIST = "operator.role.User";

	String USER_NAME = "userName";
	String FIRST_NAME = "firstName";
	String LAST_NAME = "userName";
	String PASSWORD = "password";
	String ROLE = "role";
	String OPERATOR_GROUP = "operatorGroup";
	String OLD_PASSWORD = "oldpassword";
	String NEW_PASSWORD = "newpassword";
	String CONFIRM_PASSWORD = "confirmNewPassword";
	String OLD_PASSWORD_LBL = "operator.oldPassword";
	String NEW_PASSWORD_LBL = "operator.newPassword";
	String CONFIRM_PASSWORD_LBL = "operator.confirmNewPassword";

	// SOLUTION OFFER PROFILE
	String SOLUTION_OFFER_TYPE_FIELD = "solutionOfferType";
	String SOLUTION_OFFER_TYPE_FRUITION_MAX_NUMBER_FIELD = "fruitionMaxNumber";
	String SOLUTION_OFFER_BUNDLE_DURATION_FIELD = "bundleDuration";
	String SOLUTION_OFFER_OFFER_TYPE_FIELD = "offerType";

	String SOLUTION_OFFER_TECHNICAL_TYPE = "technical";
	String SOLUTION_OFFER_BUNDLE_TYPE = "bundle";

	String BUNDLE_VALUE = "BUNDLE";

	// SOLUTION OFFER PROFILE
	String OFFER_CHANNEL_FIELD = "channel";
	String OFFER_CATEGORY_FIELD = "category";
	String OFFER_PRICE_FIELD = "price";
	String OFFER_TYPE_FIELD = "type";
	String OFFER_RENT_PRICE_FIELD = "rentprice";
	String OFFER_BROADCAST_DATE_FIELD = "broadcastdate";

	String CATALOGUE_CONSOLE_URL = "catalogueconsole.url";
	String ASSURANCE_CONSOLE_URL = "assuranceconsole.url";

	String STATUS_INACTIVE = "Inactive";
	String STATUS_DELETED = "Deleted";
	String STATUS_ACTIVE = "Active";

	String TECHNICAL_PACKAGE_PREVIX = "PKG_";
	String TIMEZONE = "timezone";

}
