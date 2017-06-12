package com.accenture.sdp.csmac.common.constants;

public interface PathConstants {

	// Actions
	String PARTY_SEARCH = "partySearch";
	String PARTY_VIEW = "partyView";
	String PARTY_CREATE = "partyCreate";
	String PARTY_DETAILS_VIEW = "partyDetailsView";
	String PARTY_DETAILS_UPDATE = "partyDetailsUpdate";
	String PARTY_CLUSTER_UPDATE = "partyClusterUpdate";
	String OPERATOR_VIEW = "operatorManagement";

	// PARTY
	String PARTY_SUBSCRIPTIONS_VIEW = "partySubscriptionsView";
	String PARTY_SUBSCRIPTIONS_DETAILS = "partySubscriptionsDetails";
	String PARTY_DEVICE = "partyDevice";
	String PARTY_DEVICE_DETAILS_VIEW = "partyDeviceDetailsView";
	
	// Page Urls
	String VIEW_PLATFORM_URL = "sections/platform/viewPlatform.xhtml";
	String ADD_PLATFORM_URL = "sections/platform/addPlatform.xhtml";
	String UPDATE_PLATFORM_URL = "sections/platform/updatePlatform.xhtml";

	String PARTY_SEARCH_URL = "sections/party/partySearch.xhtml";
	String PARTY_VIEW_URL = "sections/party/partyView.xhtml";
	String PARTY_CREATE_URL = "sections/party/createAvsUser.xhtml";

	String RESET_PASSWORD_URL = "resetPassword.xhtml";

	String SUBSCRIPTION_DETAILS_VIEW_URL = "sections/subscription/partySubscriptionDetails.xhtml";

	String OPERATOR_VIEW_URL = "sections/operator/operatorView.xhtml";
	String ADD_OPERATOR_URL = "sections/operator/addOperator.xhtml";
	String ADD_OPERATOR2_URL = "sections/operator/addOperator2.xhtml";
	String UPDATE_OPERATOR_URL = "sections/operator/modifyOperator.xhtml";
	String UPDATE_OPERATOR_TENANT_URL = "sections/operator/modifyTenants.xhtml";
	String SEARCH_OPERATOR_URL = "sections/operator/operatorSearch.xhtml";
	String CHANGE_PASSWORD_URL = "sections/operator/changePasswordOperator.xhtml";

	String PARTY_DEVICE_POLICY_URL = "sections/party/manageDevicePolicy.xhtml";
	String PARTY_DETAILS_VIEW_URL = "sections/party/partyPage.xhtml";
	String PARTY_DETAILS_URL = "partyDetails.xhtml";
	String PARTY_DEVICE_DETAILS_URL = "sections/party/partyDeviceDetails.xhtml";
	String PARTY_CLUSTER_UPDATE_URL = "sections/party/manageClusters.xhtml";
	
	String ADD_OPERATOR = "addOperator";
	String ADD_OPERATOR2 = "addOperator2";
	String UPDATE_OPERATOR = "updateOperator";
	String UPDATE_OPERATOR_TENANTS = "updateOperatorTenants";
	String SEARCH_OPERATOR = "operatorSearch";

	String VIEW_SOLUTION_OFFER_URL = "sections/offers/viewSolutionOffer.xhtml";
	String ENTER_VOUCHER_URL = "sections/offers/enterVoucher.xhtml";

	String SUBSCRIBE_START_COMM_OFFER = "buyCommOffer";
	String SUBSCRIBE_START_VOUCHER = "useVoucher";
	
	String DEVICE_ACCESS_CHANNEL = "deviceAccessChannel";
	String DEVICE_ACCESS_BRAND = "deviceAccessBrand";
	String DEVICE_ACCESS_MODEL = "deviceAccessModel";
	String DEVICE_ACCESS_CHANNEL_URL = "sections/party/manageBWLChannel.xhtml";
	String DEVICE_ACCESS_BRAND_URL = "sections/party/manageBWLBrand.xhtml";
	String DEVICE_ACCESS_MODEL_URL = "sections/party/manageBWLModel.xhtml";
	
	String DEVICE_POLICY_DETAILS_VIEW = "devicePolicyDetail";
	String DEVICE_POLICY_DETAILS_VIEW_URL = "sections/party/devicePolicyDetail.xhtml";
	String DEVICE_POLICY_ADD = "addPolicy";
	String DEVICE_POLICY_ADD_URL = "sections/party/addPolicy.xhtml";
}
