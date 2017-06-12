package com.accenture.avs.console.common.constants;

public interface PathConstants {

	// WRAPPER ACTIONS
	String ACTION_CONSOLE_CATALOGUE = "goToCatalogueConsole";
	String ACTION_CONSOLE_ASSURANCE = "goToAssuranceConsole";
	String ACTION_CONSOLE_CATCHUP = "goToCatchupConsole";
	String ACTION_CONSOLE_MULTICAMERA = "goToMulticameraConsole";
	String ACTION_CONSOLE_OPERATORS = "goToOperatorsConsole";
	String ACTION_CONSOLE_MWALLET = "goToMwalletConsole";

	// WRAPPER URLS
	String URL_LOGIN = "login.jsp";
	String URL_LOGOUT = "logout.jsp";
	String URL_CONSOLE_CATALOGUE = "/SDP_CSM_CC/";
	String URL_CONSOLE_ASSURANCE = "/SDP_CSM_AC/";
	String URL_CONSOLE_CATCHUP = "/mm-web/console/catchup";
	String URL_CONSOLE_MULTICAMERA = "/mm-web/console/multicamera";
	String URL_CONSOLE_OPERATORS = "main/operators.xhtml";
	String URL_CONSOLE_MWALLET = "mwallet.url";

	// Actions
	String LOGIN = "login";
	String START_PAGE = "startPage";
	String PARTY_SEARCH = "partySearch";
	String PARTY_VIEW = "partyView";
	String PARTY_CREATE = "partyCreate";
	String PARTY_DETAILS_VIEW = "partyDetailsView";
	String PARTY_DETAILS_UPDATE = "partyDetailsUpdate";
	String PARTY_CLUSTER_UPDATE = "partyClusterUpdate";
	String OPERATOR_VIEW = "operatorManagement";

	// PARTY

	String PARTY_BILLING_VIEW = "partyBillingView";

	String PARTY_SITE_VIEW = "partySiteView";

	String PARTY_CREDENTIALS_VIEW = "partyCredentialsView";
	String RESET_PASSWORD = "resetPassword";

	String PARTY_SUBSCRIPTIONS_VIEW = "partySubscriptionsView";
	String PARTY_SUBSCRIPTIONS_DETAILS = "partySubscriptionsDetails";

	// Page Urls
	String VIEW_PLATFORM_URL = "sections/platform/viewPlatform.xhtml";
	String ADD_PLATFORM_URL = "sections/platform/addPlatform.xhtml";
	String UPDATE_PLATFORM_URL = "sections/platform/updatePlatform.xhtml";

	String LOGIN_URL = "login.xhtml";
	String START_PAGE_URL = "startBlank.xhtml";
	String PARTY_SEARCH_URL = "sections/party/partySearch.xhtml";
	String PARTY_VIEW_URL = "sections/party/partyView.xhtml";
	String PARTY_CREATE_URL = "sections/party/manageAvsUser.xhtml";

	String PARTY_DETAILS_VIEW_URL = "sections/party/partyPage.xhtml";
	String PARTY_DETAILS_URL = "partyDetails.xhtml";
	String PARTY_CLUSTER_UPDATE_URL = "sections/party/manageClusters.xhtml";

	String PARTY_CREDENTIALS_VIEW_URL = "partyCredentialsView.xhtml";
	String RESET_PASSWORD_URL = "resetPassword.xhtml";

	String SUBSCRIPTION_DETAILS_VIEW_URL = "sections/subscription/partySubscriptionDetails.xhtml";

	String OPERATOR_VIEW_URL = "sections/operator/operatorView.xhtml";
	String ADD_OPERATOR_STEP1_URL = "sections/operator/addOperatorStep1.xhtml";
	String ADD_OPERATOR_STEP2_URL = "sections/operator/addOperatorStep2.xhtml";
	String ADD_OPERATOR_STEP3_URL = "sections/operator/addOperatorStep3.xhtml";
	String UPDATE_OPERATOR_URL = "sections/operator/updateOperator.xhtml";
	String UPDATE_OPERATOR_RIGHTS_URL = "sections/operator/updateOperatorRights.xhtml";
	String UPDATE_OPERATOR_TENANT_URL = "sections/operator/modifyTenants.xhtml";
	String SEARCH_OPERATOR_URL = "sections/operator/operatorSearch.xhtml";
	String CHANGE_PASSWORD_URL = "main/sections/operator/changePasswordOperator.xhtml";

	String ADD_OPERATOR_STEP1 = "addOperatorStep1";
	String ADD_OPERATOR_STEP2 = "addOperatorStep2";
	String ADD_OPERATOR_STEP3 = "addOperatorStep3";
	String UPDATE_OPERATOR = "updateOperator";
	String UPDATE_OPERATOR_RIGHTS = "updateOperatorRights";
	String UPDATE_OPERATOR_TENANTS = "updateOperatorTenants";
	String SEARCH_OPERATOR = "operatorSearch";
	String CHANGE_PASSWORD = "changePassword";

	String VIEW_OFFER_AVS = "viewOfferAVS";
	String VIEW_OFFER_PPV_URL = "sections/offers/viewOfferPPV.xhtml";
	String VIEW_OFFER_VOD_URL = "sections/offers/viewOfferVOD.xhtml";
	String VIEW_SOLUTION_OFFER_URL = "sections/offers/viewSolutionOffer.xhtml";
	String VIEW_SOLUTION_OFFER_AVS_URL = "sections/offers/viewSolutionOfferAVS.xhtml";
	String ENTER_VOUCHER_URL = "sections/offers/enterVoucher.xhtml";

	String SUBSCRIBE_START_VOD = "goToBuyVod";
	String SUBSCRIBE_START_PPV = "goToBuyPpv";
	String SUBSCRIBE_START_COMM_OFFER = "buyCommOffer";
	String SUBSCRIBE_START_BUNDLE = "goToBuyBundle";
	String SUBSCRIBE_START_VOUCHER = "useVoucher";
}
