package com.accenture.avs.console.common.constants;

public interface MessageConstants {

	String ERROR_COMPOSITE_CODE = "code.";
	String ERROR_COMPOSITE_DESC = ".description";

	String MENU_PARTY = "menu.party";
	String MENU_NEW_SEARCH = "menu.newsearch";
	String HEADER_OPERATOR = "header.operator";
	String BREADCRUMB_SEARCH = "breadcrumb.search";
	String BREADCRUMB_RESULT_LIST = "breadcrumb.resultList";
	String BREADCRUMB_DETAILS = "breadcrumb.details";
	String BREADCRUMB_SUBSCRIPTIONS = "breadcrumb.subscriptions";
	String BREADCRUMB_SUBSCRIPTION_DETAILS = "breadcrumb.subscriptions.details";
	String BREADCRUMB_BILLING = "breadcrumb.billing";
	String BREADCRUMB_CREDENTIALS = "breadcrumb.credentials";
	String BREADCRUMB_OPERATOR = "breadcrumb.operator";
	String BREADCRUMB_SITE = "breadcrumb.site";
	String BREADCRUMB_USERS = "breadcrumb.users";
	String BREADCRUMB_CREATE_USER = "breadcrumb.createUser";
	String BREADCRUMB_CLUSTER_UPDATE_USER = "breadcrumb.clusterUpdateUser";
	String BREADCRUMB_RESET_PWD = "breadcrumb.resetPassword";
	String BREADCRUMB_PARTY_DETAILS_UPDATE = "breadcrumb.updateDetails";
	String BREADCRUMB_ADD_OPERATOR = "breadcrumb.addOperator";
	String BREADCRUMB_UPDATE_OPERATOR = "breadcrumb.updateOperator";
	String BREADCRUMB_UPDATE_OPERATOR_RIGHTS = "breadcrumb.updateOperatorRights";
	String BREADCRUMB_UPDATE_OPERATOR_TENANT = "breadcrumb.updateOperatorTenant";
	String BREADCRUMB_SEARCH_OPERATOR = "breadcrumb.searchOperator";
	String BREADCRUMB_CHANGE_PWD = "header.changePassword";
	String BREADCRUMB_BUY_VOD = "breadcrumb.buyVod";
	String BREADCRUMB_BUY_PPV = "breadcrumb.buyPpv";
	String BREADCRUMB_BUY_COMM_OFFER = "breadcrumb.buyCommOffer";
	String BREADCRUMB_BUY_BUNDLE = "breadcrumb.buyBundle";
	String BREADCRUMB_BUY_VOUCHER = "breadcrumb.buyVoucher";
	

	String ERROR_COMMON_CODE = "code.%s.description";

	String OK_MESSAGE = "code.000.description";
	String MANDATORY_MESSAGE = "validator.mandatory.message";
	String NUMBER_FORMAT_VALIDATOR = "javax.faces.converter.BigDecimalConverter.DECIMAL";

	String OK_MESSAGE_RESET_PWD = "code.000.description.resetpassword";
	String KO_MESSAGE_RESET_PWD = "description.resetpassword.error";

	// LOGIN

	String LOGIN_ERROR = "login.loginError";
	String UPDATE_DETAILS_MESSAGE = "code.000.description.method.updateDetails";
	String ADD_OPERATOR_MESSAGE = "code.000.description.method.add";
	String DELETE_OPERATOR_MESSAGE = "code.000.description.method.delete";
	String UPDATE_OPERATOR_MESSAGE = "code.000.description.method.updateOperator";
	String UPDATE_OPERATOR_RIGHTS_MESSAGE = "code.000.description.method.updateOperatorRights";
	String CHANGE_OPERATOR_PASSWORD_MESSAGE = "code.000.description.method.changePassword";
	// BILLING
	String BILLING_NAME_LBL = "billing.billingNameLbl";
	String BILLING_EXTID_LBL = "billing.billingExtIdLbl";

	// PARTY
	String PARTY_NAME_LBL = "party.organizationName";
	String PARTY_EXTID_LBL = "party.extId";
	String PARTY_GROUPID_LBL = "party.partyGroupId";
	String OFFICE_SITE_LBL = "user.officeSite";

	// SITE
	String SITE_NAME_LBL = "site.siteNameLbl";
	String SITE_EXTID_LBL = "site.siteExtIdLbl";

	String USER_NAME_LBL = "operator.username";

	String FIRST_NAME_LBL = "operator.firstName";
	String LAST_NAME_LBL = "operator.lastName";
	String ROLE_LBL = "operator.role";
	String DELETE_OPERATOR = "operations.delete";
	String STATUS_OPERATOR = "operator.status";

	String PWD_LBL = "operator.pwd";
	// String
	String OLD_PASSWORD_LBL = "operator.oldPassword";
	String NEW_PASSWORD_LBL = "operator.newPassword";
	String CONFIRM_PASSWORD_LBL = "operator.confirmNewPassword";
	
	String AVS_USER_ACTIVE = "avs.status.active";
	String AVS_USER_INACTIVE = "avs.status.inactive";
	String AVS_USER_CEASED = "avs.status.ceased";

	String OPERATION_COMPLETED = "popup.operation.completed";
	
	// VOUCHERS
	String VOUCHER_ERROR_ALREADY_USED_LBL = "voucher.error.used";
	String VOUCHER_ERROR_EXPIRED_LBL = "voucher.error.expired";
}
