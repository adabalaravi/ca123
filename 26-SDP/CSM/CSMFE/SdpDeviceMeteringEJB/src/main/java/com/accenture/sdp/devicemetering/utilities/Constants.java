package com.accenture.sdp.devicemetering.utilities;

import java.io.File;

public interface Constants {

	// JBoss6
	String JB6_HOME_DIR = "jboss.server.home.dir";
	String CONFIGURATION_PATH_JB6 = System.getProperty(JB6_HOME_DIR) + File.separator + "conf";

	// JBoss7
	String CONFIGURATION_PATH_JB7 = System.getProperty("jboss.home.dir") + File.separator + "standalone" + File.separator + "configuration";

	String CONFIGURATION_PATH = CONFIGURATION_PATH_JB6;

	String CSM_CONFIGURATION_PATH = CONFIGURATION_PATH + File.separator + "csm" + File.separator;

	String DATE_FORMAT = "dd/MM/yyyy";

	String EVENT_DESCRIPTION_FILENAME = "event_description.properties";
	String COMPONENTIDS_FILENAME = "event_description.properties";
	String EXCEPTIONS_CATEGORY = "exception_categories.properties";
	String BLP_EVENT_DESCRIPTION_FILENAME = "blp_event_description.properties";

	// CODES
	// 000
	String CODE_OK = "ok";
	// 001
	String CODE_OK_OUT_OF_RANGE = "okoutofrange";
	// 002
	String CODE_OK_RELATED_SUBSCRIPTIONS = "okrelatedsub";
	// 010
	String CODE_MANDATORY_FIELD_MISSING = "mandatoryfieldmissing";
	// 020
	String CODE_VALUE_NOT_ALLOWED = "valuenotallowed";
	// 030
	String CODE_INSERT_FAILED = "insertfailed";
	// 040
	String CODE_DUPLICATE_ENTRY = "duplicateentry";
	// 050
	String CODE_STATUS_TRANSACTION_ERROR = "statustransactionerror";
	// 060
	String CODE_PARENT_NOT_FOUND = "parentnotfound";
	// 070
	String CODE_GENERIC_ERROR = "genericerror";
	// 090
	String CODE_UPDATE_FAILED = "updatefailed";
	// 100
	String CODE_ELEMENT_NOT_FOUND = "elementnotfound";
	// 110
	String CODE_ELEMENT_WITH_NOT_DELETED_CHILDS = "elementwithchilds";
	// 130
	String CODE_ZERO_RECORD_FOUND = "zerorecordfound";
	// 140
	String CODE_UPDATE_NOT_ALLOWED_FOR_STATUS = "updatenotallowedforstatus";
	// 150
	String CODE_ELEMENT_ALREADY_ASSOCIATED = "elementalreadyassociated";
	// 170
	String CODE_NOT_POSSIBLE_TO_CREATE_THE_CREDENTIAL_FOR_THE_PARTY = "notpossibletocreatethecredentialfortheparty";
	// 180
	String CODE_LIST_USERNAME_ALTERNATIVE_NOT_CREATED = "listusernamealternativenotcreated";
	// 190
	String CODE_ERROR_GENERATION_USERNAME = "errorgenerationusername";
	// 210
	String CODE_PARAMETER_NOT_RESPECT_CONSTRAINT_WITH_ANOTHER_PARAMETER = "parameternotrespectconstraintwithanotherparameter";
	// 220
	String CODE_NOT_POSSIBLE_TO_MODIFY_PASSWORD_FOR_CREDENTIAL = "notpossibletomodifypasswordforcredential";
	// 230
	String CODE_HIERARCHY_NOT_RESPECTED = "elementnotrespectherarchy";
	// 240
	String CODE_DELETE_FAILED = "deletefailed";
	// 250
	String CODE_TENANT_INVALID = "invalidtenant";
	// 260
	String CODE_TENANT_INACTIVE = "inactivetenant";
	// 420
	String CODE_INVALID_TOKEN = "invalidtoken";
	// 301 
	String CODE_DEVICE_MAX_EXCEEDED = "301";
	String CODE_DEVICE_SAFETY_PERIOD = "302";
	String CODE_DEVICE_USER_BL = "303";
	String CODE_DEVICE_BL = "304";
	String CODE_DEVICE_AUTH_REFUSED = "306";


	// Application Constants
	long SECRET_QUESTION_NUMBER = 3;
	int USERNAME_MAX_ALTERNATIVES = 10;
	String AT = "@";
	String REGEX_USERNAME = "(^(\\+|00)[1-9][0-9]{10,14}$|^[a-zA-Z0-9][a-zA-Z0-9._]+($|@[a-zA-Z0-9][a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$))";
	String REGEX_EMAIL = "^[a-zA-Z0-9][a-zA-Z0-9._]+@[a-zA-Z0-9][a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
	String REGEX_USERNAME_EMAIL_BAD_CHARS = "[^a-zA-Z0-9._]";
	// 8 characters, at least 2 digits, at least a special character, at least a
	// letter
	String PASSWORD_FORMAT_REGEX = "^(?=[0-9a-zA-Z.#@?!_]{8,})(?=.*[0-9]+.*[0-9]+)(?=.*[.#@?!_]+)(?=.*[a-zA-Z]+).*$";

	String USER_NAME_ADMIN_PREFIX = "Admin";

	// Internal PriceCatalogId with amount = 0.

	Long PRICE_CATALOGUE_ID_AMOUNT_ZERO = Long.valueOf(1L);

	// PERSISTENCE UNIT
	
	String TENANT_NAME = "tenantName";
	

	String PERSISTENCE_UNIT_NAME = "SDP_CSM_MPU";
	String PERSISTENCE_JDBC_DRIVER = "javax.persistence.jdbc.driver";
	String PERSISTENCE_JDBC_URL = "javax.persistence.jdbc.url";
	String PERSISTENCE_JDBC_USERNAME = "javax.persistence.jdbc.user";
	String PERSISTENCE_JDBC_PASSWORD = "javax.persistence.jdbc.password";
	String PERSISTENCE_JNDI_DATASOURCE = "";

	// Entity Type IDs, used for change status validation
	Long ENTITY_TYPE_OTHER = Long.valueOf(0L);
	Long ENTITY_TYPE_SUBSCRIPTION = Long.valueOf(1L);

	// STATUS NAME
	String ACTIVE = "ACTIVE";
	String INACTIVE = "INACTIVE";
	String DELETED = "DELETED";
	String ACTIVATING = "ACTIVATING";
	String DELETING = "DELETING";
	String UPDATING = "UPDATING";

	// PARTY TYPE
	String PARTY_TYPE_ORGANIZATION = "PARTY_ORGANIZATION";
	String PARTY_TYPE_USER = "PARTY_USER";

	String ROLE_NAME_USER = "ROLE_USER";
	String ROLE_NAME_ADMIN = "ROLE_ADMIN";

	String NULL_FREQUENCY = "NULL_FREQUENCY";
	String SDP_PLATFORM = "SDP";
	int MAX_ENCRYPTED_PASSWORD_SIZE = 256;
	
	// DEVICE
	String DEVICE_AUTHENTICATION_ENABLED = "IS_AUTHENTICATION_ENABLED";
	String DEVICE_PAIRING_ENABLED = "IS_PAIRING_ENABLED";
}
