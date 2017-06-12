package com.accenture.sdp.csmcc.common.constants;

public interface MessageConstants {

	String POPUP_CONFIRM_MESSAGE = "pupup.confirm.message.";

	String BREADCRUMB_OPERATOR = "breadcrumb.operator";
	String BREADCRUMB_ADD_OPERATOR = "breadcrumb.addOperator";	
	String BREADCRUMB_UPDATE_OPERATOR = "breadcrumb.updateOperator";
	String BREADCRUMB_CHANGE_PWD = "breadcrumb.changePassword";
	String HEADER_OPERATOR ="header.operator";



	String PLATFORM = "platform";
	String SERVICE_TEMPLATE = "serviceTemplate";
	String SERVICE_VARIANT = "serviceVariant";


	String PARTY_GROUP = "partyGroup";
	String SOLUTION = "solution";
	String SOLUTION_OFFER = "solutionOffer";
	String OFFER_DETAIL = "offerDetail";
	String OFFER = "offer";

	String PRICE = "price";
	String FREQUENCY = "frequency";

	String TENANT_NAME = "tenantName";

	String ERROR_ENDDATE_NOT_SET = "description.enddatenotset";
	String POPUP_MESSAGE_COMMERCIAL_PACKAGE_ASSIGNED = "pupup.message.commercialPackage.assigned";
	String POPUP_MESSAGE_TECHNICAL_PACKAGE_ASSIGNED = "pupup.message.technicalPackage.assigned";




	String MENU_OFFER_MANAGEMENT = "menu.offerManagement";
	String MENU_OFFER_MANAGEMENT_VIEW_SUBSCRIPTIONS = "menu.offerManagement.viewSubscriptions";
	String MENU_OFFER_MANAGEMENT_VIEW_VODS = "menu.offerManagement.viewVoDs";
	String MENU_OFFER_MANAGEMENT_VIEW_PPVS = "menu.offerManagement.viewLivePPVs";
	String MENU_OFFER_MANAGEMENT_MANAGE_SUBSCRIPTION_OFFERS = "menu.offerManagement.manageSubscriptionOffers";
	String MENU_OFFER_MANAGEMENT_MANAGE_VOD_PPV_OFFERS = "menu.offerManagement.manageVoDPPVOffers";
	String MENU_OFFER_MANAGEMENT_MANAGE_VOD_PPV_BUNDLE_OFFERS = "menu.offerManagement.manageVoDPPVBundleOffers";

	String MENU_ADMINISTRATION = "menu.administration";
	String MENU_ADMINISTRATION_CLUSTER = "menu.administration.cluster";
	String MENU_ADMINISTRATION_PRICE = "menu.administration.price";
	String MENU_ADMINISTRATION_FREQUENCY = "menu.administration.frequency";


	String ERROR_COMPOSITE_CODE ="code.";
	String ERROR_COMPOSITE_DESC =".description";

	String OK_MESSAGE="code.000.description";
	String MANDATORY_MESSAGE="validator.mandatory.message";
	String NOT_EXIST_MESSAGE="validator.notexist.message";
	String NUMBER_FORMAT_VALIDATOR="javax.faces.converter.BigDecimalConverter.DECIMAL";

	//RESULTS

	String CHANGE_STATUS_000_MESSAGE = "code.000.description.method.changeStatus";



	// PLATFORM
	String ADD_PLATFORM = "platform.addPlatform";
	String UPDATE_PLATFORM = "platform.updatePlatform";
	String DELETE_PLATFORM = "platform.viewPlatform.operations.delete";

	String ADD_PLATFORM_MESSAGE="code.000.description.method.addPlatform";
	String UPDATE_PLATFORM_MESSAGE="code.000.description.method.modifyPlatform";
	String DELETE_PLATFORM_MESSAGE="code.000.description.method.deletePlatform";

	String PLATFORM_NAME_LBL="platform.addPlatform.platformNameLbl";
	String PLATFORM_DESC_LBL="platform.addPlatform.platformDescLbl";
	String PLATFORM_EXTID_LBL="platform.addPlatform.platformExtIdLbl";;
	String PLATFORM_PROFILE_LBL="platform.addPlatform.platformProfileLbl";


	//SERVICE TEMPLATE
	String ADD_SERVICETEMPLATE = "serviceTemplate.addServiceTemplate";
	String UPDATE_SERVICETEMPLATE = "serviceTemplate.updateServiceTemplate";
	String DELETE_SERVICETEMPLATE="serviceTemplate.viewServiceTemplate.operations.delete";
	String DETAILS_SERVICETEMPLATE = "serviceTemplate.viewServiceTemplate.operations.related";

	String ADD_SERVICETEMPLATE_MESSAGE="code.000.description.method.addServiceTemplate";
	String UPDATE_SERVICETEMPLATE_MESSAGE="code.000.description.method.modifyServiceTemplate";
	String DELETE_SERVICETEMPLATE_MESSAGE="code.000.description.method.deleteServiceTemplate";


	String SERVICETEMPLATE_NAME_LBL="serviceTemplate.addServiceTemplate.serviceTemplateNameLbl";
	String SERVICETEMPLATE_DESC_LBL="serviceTemplate.addServiceTemplate.serviceTemplateDescLbl";
	String SERVICETEMPLATE_EXTID_LBL="serviceTemplate.addServiceTemplate.serviceTemplateExtIdLbl";
	String SERVICETEMPLATE_PROFILE_LBL="serviceTemplate.addServiceTemplate.serviceTemplateProfileLbl";
	String SERVICETEMPLATE_TYPENAME_LBL="serviceTemplate.addServiceTemplate.serviceTemplateTypeNameLbl";
	String SERVICETEMPLATE_PLATFORMNAME_LBL="serviceTemplate.addServiceTemplate.platformNameLbl";

	//SERVICE VARIANT
	String ADD_SERVICEVARIANT = "serviceVariant.addServiceVariant";
	String UPDATE_SERVICEVARIANT = "serviceVariant.updateServiceVariant";
	String DELETE_SERVICEVARIANT = "serviceVariant.viewServiceVariant.operations.delete";
	String DETAILS_SERVICEVARIANT = "serviceVariant.viewServiceVariant.operations.related";
	String OPERATIONS_RELATED_SERVICEVARIANT = "breadcrumb.operation";
	String ADD_OPERATION_SERVICEVARIANT = "breadcrumb.operation.addOperation";


	String ADD_SERVICEVARIANT_MESSAGE = "code.000.description.method.addServiceVariant";
	String UPDATE_SERVICEVARIANT_MESSAGE = "code.000.description.method.modifyServiceVariant";
	String DELETE_SERVICEVARIANT_MESSAGE = "code.000.description.method.deleteServiceVariant";

	String ADD_OPERATION_MESSAGE = "code.000.description.method.addOperation";
	String UPDATE_OPERATION_MESSAGE = "code.000.description.method.updateOperation";
	String DELETE_OPERATION_MESSAGE = "code.000.description.method.deleteOperation";

	String SERVICEVARIANT_NAME_LBL = "serviceVariant.addServiceVariant.serviceVariantNameLbl";
	String SERVICEVARIANT_DESC_LBL = "serviceVariant.addServiceVariant.serviceVariantDescLbl";
	String SERVICEVARIANT_EXTID_LBL = "serviceVariant.addServiceVariant.serviceVariantExtIdLbl";
	String SERVICEVARIANT_PROFILE_LBL = "serviceVariant.addServiceVariant.serviceVariantProfileLbl";
	String SERVICEVARIANT_TYPENAME_LBL = "serviceVariant.addServiceVariant.serviceVariantTypeNameLbl";
	String SERVICEVARIANT_TEMPLATENAME_LBL = "serviceVariant.addServiceVariant.templateNameLbl";

	// PARTY GROUP

	String ADD_PARTY_GROUP = "partyGroup.add";
	String UPDATE_PARTY_GROUP = "partyGroup.update";
	String DELETE_PARTY_GROUP = "partyGroup.delete";

	String ADD_PARTY_GROUP_MESSAGE="code.000.description.method.addPartyGroup";
	String UPDATE_PARTY_GROUP_MESSAGE="code.000.description.method.modifyPartyGroup";
	String DELETE_PARTY_GROUP_MESSAGE="code.000.description.method.deletePartyGroup";

	String PARTY_GROUP_NAME = "partyGroup.name";
	String PARTY_GROUP_DESCRIPTION = "partyGroup.description";



	//SOLUTION
	String ADD_SOLUTION_MESSAGE = "code.000.description.method.addSolution";
	String UPDATE_SOLUTION_MESSAGE = "code.000.description.method.modifySolution";
	String UPDATE_PARTYGROUPS_MESSAGE = "code.000.description.method.modifyPartyGroups";
	String DELETE_SOLUTION_MESSAGE = "code.000.description.method.deleteSolution";

	String ADD_SOLUTION_STEP1 = "solution.addSolutionStep1";
	String ADD_SOLUTION_STEP2 = "solution.addSolutionStep2";
	String UPDATE_SOLUTION = "solution.updateSolution";
	String UPDATE_PARTYGROUPS = "solution.updatePartyGroups";
	String DELETE_SOLUTION = "solution.viewSolution.operations.delete";

	String SOLUTION_NAME_LBL="solution.addSolution.solutionNameLbl";
	String SOLUTION_DESC_LBL="solution.addSolution.solutionDescLbl";
	String SOLUTION_EXTID_LBL="solution.addSolution.solutionExtIdLbl";
	String SOLUTION_PROFILE_LBL="solution.addSolution.solutionProfileLbl";
	String SOLUTION_TYPENAME_LBL="solution.addSolution.solutionTypeNameLbl";
	String SOLUTION_STARTDATE_LBL="solution.addSolution.solutionStartDateLbl";
	String SOLUTION_ENDDATE_LBL="solution.addSolution.solutionEndDateLbl";
	String SOLUTION_SOLUTIONTYPE_LBL="solution.addSolution.solutionTypeLbl";
	String SOLUTION_PARTYGROUPS_LBL="solution.addSolution.partyGroupsSelected";

	//SOLUTION OFFER
	String ADD_SOLUTION_OFFER_MESSAGE = "code.000.description.method.addSolutionOffer";
	String UPDATE_SOLUTION_OFFER_MESSAGE = "code.000.description.method.modifySolutionOffer";
	String DELETE_SOLUTION_OFFER_MESSAGE="code.000.description.method.deleteSolutionOffer";


	String UPDATE_SOLUTION_OFFER = "solutionOffer.updateSolutionOffer";
	String DELETE_SOLUTION_OFFER = "solutionOffer.viewSolutionOffer.operations.delete";
	String ADD_SOLUTION_OFFER_TYPE_BASIC =  "solutionOffer.addSolutionOffer.type.basic";
	String ADD_SOLUTION_OFFER_TYPE_OPTIONAL = "solutionOffer.addSolutionOffer.type.optional";
	String ADD_OFFER_GROUP_NAME_EMPTY_ERROR = "solutionOffer.addSolutionOffer.addOfferGroupName.emptyError";
	String ADD_OFFER_GROUP_NAME_ALREADY_EXIST_ERROR = "solutionOffer.addSolutionOffer.offerGroupNameLabel.alreadyExist";
	String UPDATE_SOLUTION_OFFER_PARTY_GROUPS = "solutionOffer.operations.updatePartyGroups";


	String SOLUTION_OFFER_SOLUTION_NAME_LBL = "solutionOffer.updateSolutionOffer.solutionNameLbl";
	String SOLUTION_OFFER_NAME_LBL = "solutionOffer.updateSolutionOffer.solutionOfferNameLbl";
	String SOLUTION_OFFER_DESCRIPTION_LBL = "solutionOffer.updateSolutionOffer.solutionOfferDescLbl";
	String SOLUTION_OFFER_START_DATE_LBL = "solutionOffer.updateSolutionOffer.solutionOfferStartDateColumn";
	String SOLUTION_OFFER_END_DATE_LBL = "solutionOffer.updateSolutionOffer.solutionOfferEndDateColumn";
	String SOLUTION_OFFER_EXTID_LBL = "solutionOffer.updateSolutionOffer.solutionOfferExtIdLbl";
	String SOLUTION_OFFER_PROFILE_LBL = "solutionOffer.updateSolutionOffer.solutionOfferProfileLbl";

	String SOLUTION_OFFER_VALIDATION_OFFERS_SELECTED = "solutionOffer.validation.offersSelected";


	// DISCOUNT SOLUTION OFFER
	String DISCOUNT_SOLUTION_OFFER = "discountSolutionOffer";
	String ADD_DISCOUNT_SOLUTION_OFFER = "discountSolutionOffer.add";
	String UPDATE_DISCOUNT_SOLUTION_OFFER = "discountSolutionOffer.update";
	String UPDATE_DISCOUNT_PACKAGE = "discountSolutionOffer.updateDiscount";
	String DISCOUNT_PACKAGE = "discountSolutionOffer.relatedDiscount";

	//SOLUTION OFFER (AVS)

	String ADD_SOLUTION_OFFER_AVS = "solutionOfferAVS.add";



	// OFFER DETAIL
	String ADD_OFFER_DETAIL = "offerDetail.addOfferDetail";
	String UPDATE_OFFER_DETAIL = "offerDetail.updateOfferDetail";
	String DELETE_OFFER_DETAIL_MESSAGE = "code.000.description.method.deleteOfferDetail";
	String ADD_OFFER_DETAIL_MESSAGE="code.000.description.method.addOfferDetail";
	String UPDATE_OFFER_DETAIL_MESSAGE = "code.000.description.method.modifyOfferDetail";

	String ADD_OFFER_DETAIL_OFFER_NAME = "offerDetail.offerName";
	String ADD_OFFER_DETAIL_NRC = "offerDetail.nrc";
	String ADD_OFFER_DETAIL_RCPRICE = "offerDetail.rcPrice";
	String ADD_OFFER_DETAIL_RCFREQUENCY = "offerDetail.rcFrequency";
	String ADD_OFFER_DETAIL_TYPE = "offerDetail.type";
	String ADD_OFFER_DETAIL_PACKAGE_LINK = "offerDetail.packageLink";
	String ADD_OFFER_DETAIL_GROUP_NAME = "offerDetail.offerGroupNameLabel";

	// OFFER
	String ADD_OFFER = "offer.addOffer";
	String UPDATE_OFFER = "offer.updateOffer";
	String ADD_OFFER_MESSAGE="code.000.description.method.addOffer";
	String MODIFY_OFFER_MESSAGE="code.000.description.method.modifyOffer";
	String DELETE_OFFER="offer.viewOffer.operations.delete";
	String DELETE_OFFER_MESSAGE="code.000.description.method.deleteOffer";

	String OFFER_NAME="offer.name";
	String OFFER_DESC="offer.description";
	String OFFER_PROFILE="offer.profile";
	String OFFER_SERVICEVARIANT="offer.serviceVariantName";
	String OFFER_EXTERNALID="offer.externalId";

	//FREQUENCY
	String ADD_FREQUENCY = "frequency.addFrequency";
	String FREQUENCY_NAME_LBL="frequency.addFrequency.FrequencyNameLbl";
	String FREQUENCY_DESC_LBL="frequency.addFrequency.FrequencyDescLbl";
	String FREQUENCY_DAY_LBL="frequency.addFrequency.FrequencyDaysLbl";
	String ADD_FREQUENCY_MESSAGE="code.000.description.method.addFrequency";
	String DELETE_FREQUENCY="frequency.viewFrequency.operations.delete";
	String DELETE_FREQUENCY_MESSAGE="code.000.description.method.deleteFrequency";

	//PRICE
	String ADD_PRICE = "price.addPrice";
	String PRICE_NAME_LBL="price.addPrice.PriceLbl";
	String ADD_PRICE_MESSAGE="code.000.description.method.addPrice";
	String DELETE_PRICE="price.viewPrice.operations.delete";
	String DELETE_PRICE_MESSAGE="code.000.description.method.deletePrice";

	//LOGIN

	String LOGIN_ERROR="login.loginError";

	//OPERATOR
	String ADD_OPERATOR_MESSAGE="code.000.description.method.add";
	String DELETE_OPERATOR_MESSAGE="code.000.description.method.delete";
	String UPDATE_OPERATOR_MESSAGE="code.000.description.method.updateOperator";
	String CHANGE_OPERATOR_PASSWORD_MESSAGE="code.000.description.method.changePassword";
	String FIRST_NAME_LBL="operator.firstName";
	String LAST_NAME_LBL="operator.lastName";
	String ROLE_LBL="operator.role";
	String PWD_LBL="operator.pwd";
	String DELETE_OPERATOR="operations.delete";
	String STATUS_OPERATOR="operator.status";
	String USER_NAME_LBL="operator.userName";
	String OK_MESSAGE_RESET_PWD="code.000.description.resetpassword";
	String KO_MESSAGE_RESET_PWD="description.resetpassword.error";
	String CONFIRM_RESET_PWD="operator.confirm.reset.password";
	String OLD_PASSWORD_LBL="operator.oldPassword";
	String NEW_PASSWORD_LBL="operator.newPassword";
	String CONFIRM_PASSWORD_LBL="operator.confirmNewPassword";

	// PACKAGE INGESTOR
	String PACKAGE_INGESTOR_EXTERNAL_OFFER_ID = "packageIngestor.externalOfferId";
	String PACKAGE_INGESTOR_PACKAGE_NAME = "packageIngestor.packageName";
	String PACKAGE_INGESTOR_PACKAGE_DESCRIPTION = "packageIngestor.packageDescription";
	String PACKAGE_INGESTOR_PACKAGE_TYPE = "packageIngestor.packageType";
	String PACKAGE_INGESTOR_IS_ENABLE = "packageIngestor.isEnabled";
	String PACKAGE_INGESTOR_START_DATE = "packageIngestor.validityStartDate";
	String PACKAGE_INGESTOR_END_DATE = "packageIngestor.validityEndDate";
	String PACKAGE_INGESTOR_PERIOD = "packageIngestor.validityPeriod";
	String PACKAGE_INGESTOR_VIEW_NUMBER = "packageIngestor.viewNumber";

	String ADD_VOUCHER_CAMPAIGN_MESSAGE = "code.000.description.method.addVoucherCampaign";

	String POPUP_INACTIVATE_SOME_ITEM = "pupup.confirm.message.Inactivate.some";
	String NO_ITEM_SELECTED = "pupup.noitemselected";
	String INACTIVATE_SOME_ITEM_OK = "inactivate.items.selected";


}
