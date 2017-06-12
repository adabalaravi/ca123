package com.accenture.avs.ca.be.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.accenture.avs.be.db.bean.DiscountBean;
import com.accenture.avs.be.db.bean.PackagePriceView;
import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.bean.PurchaseTransaction;
import com.accenture.avs.be.db.bean.SdpSolutionOfferExternalId;
import com.accenture.avs.be.db.bean.SolutionOfferDetailsBean;
import com.accenture.avs.be.db.dao.SolutionOfferDAO;
import com.accenture.avs.be.db.framework.ConstantsParameter;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.ca.be.dao.SolutionOfferinclDurationDAO;
import com.accenture.avs.ca.be.db.bean.ProductBeanCA;
import com.accenture.avs.ca.be.formatter.ProductListFormatterCA;
import com.accenture.avs.ca.be.util.ProductListUtilCA;
import com.accenture.avs.ca.be.util.WebConstants;


public class GetProductListCA extends GenericAction {

	private static Logger logger = Logger.getLogger(GetProductListCA.class);
	private static final long serialVersionUID = 1L;

	private HttpSession sessionHttp = null;
	private String channel = "";
	private String itemType = "";

	private Long userId;
	
	ConstantsParameter constantsParamete=null;
	
	// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
	List<String> packageToBeShownList=null; //This list is to store all particular packages list to be shown.
	List<String> conditionalPackageDetailsList=null; //This list is to store all conditional(like 365 package ids) packages list.
	boolean productEligibleToDisplay = true;
	

	public GetProductListCA(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
	}

	public void validateRequest() throws ActionException {
		try {
			sessionHttp = request.getSession();
			channel = this.request.getParameter(Constants.CHANNEL);
			Profile profile = (Profile) sessionHttp.getAttribute(Constants.USERPROFILE);
			if (profile != null) {
				userId = profile.getUserId();
			}
			itemType = request.getParameter(Constants.ITEM_TYPE);

			this.validateStringParameter(Constants.ITEM_TYPE, itemType, 15);

		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR, e);
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR);
			}
		}
	}

	public Object executeRequest() throws ActionException {
		
		String methodName="executeRequest()";

		try {
			constantsParameter=tenantConfigurator.getConstantsParameter();
						
						// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
						// If user have the purchased 365 Pass , this CR will be restricting all the other packages
						// This CR customizes the need to have additional blocking of specific packages by setting the "eligible' field to 'false'
						//when needed by using Sys_parameters.
						
						if(userId != null){
							
							String conditionalPackageIds = constantsParameter.get(WebConstants.CONDITIONAL_PACKAGE_IDS_GETPRODUCTLIST);				
							
							LogUtil.commonInfoLog(logger, methodName,"conditionalPackageIds :"  + conditionalPackageIds);
							//Setting productEligibleToDisplay = false if any of the conditional package is available ,so setting 'eligible=false' field for each product in the response.
							if(conditionalPackageIds != null && !"".equals(conditionalPackageIds.trim())){
								
								String PackageToBeShown = constantsParameter.get(WebConstants.PACKAGES_TOBE_SHOWN_GETPRODUCTLIST);
								LogUtil.commonInfoLog(logger, methodName, "| PackageToBeShown Ids :" + PackageToBeShown);
								String[] packagesids=conditionalPackageIds.split(",");
								conditionalPackageDetailsList=Arrays.asList(ProductListUtilCA.trimListOfStrings(packagesids));
								
								 //The below invocation returns list of conditional packages if these packages are subscribed to the user.
								 List<PurchaseTransaction> listOfconditionalPackageDetails = SolutionOfferinclDurationDAO.getConditionalPackageIdsDetail(userId, conditionalPackageDetailsList , tenantConfigurator);
								 
								 LogUtil.commonInfoLog(logger, methodName,"listOfconditionalPackageDetails size :" + listOfconditionalPackageDetails.size() );
								 //if conditional packages available,then setting the productEligibleToDisplay = false;
								 if(listOfconditionalPackageDetails != null && (listOfconditionalPackageDetails.size() > 0)){
									productEligibleToDisplay = false;
									
							     }
								 if(PackageToBeShown != null && !"".equals(PackageToBeShown)){
										
										String[] packageids=PackageToBeShown.trim().split(",");
										packageToBeShownList=Arrays.asList(ProductListUtilCA.trimListOfStrings(packageids));
										
									}
							 }
							//Creating a list with all the available package ids to be shown which are driven by the sys parameter  PACKAGES_TOBE_SHOWN_GETPRODUCTLIST
							
						}
			
			// get all the Solution Offer Id's and create a ProductMap using the
			// Solution Offer Ids
			
			List<SolutionOfferDetailsBean> listOfSolutionOfferDetails = SolutionOfferDAO.getSolutionIdsForUser(userId, channel, itemType, tenantConfigurator);
			
			ProductListUtilCA prodListUtil = new ProductListUtilCA();
			if (listOfSolutionOfferDetails.size() > 0) {
				
				// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
				//As part of CR ,Added productEligibleToDisplay,packageToBeShownList parameter to the method
				//Passing the productEligibleToDisplay flag and packages to be shown list -  so that eligible field for each product will be processed either true or false 
				prodListUtil.segregateSolutionIds(listOfSolutionOfferDetails,productEligibleToDisplay,packageToBeShownList);

				// get the price list for all the base solution offer Id's and
				// update the respective ProductMap entry
				// List<PriceCatalogBean> listOfPriceCatalogBeans =
				// PriceDiscountUtil.getPrice(ProductListUtil.getAllBaseSolutionIds(),
				// tenantConfigurator);
				String baseIds = prodListUtil.getAllBaseSolutionIds();
				if (!"".equals(baseIds)) {
					List<PackagePriceView> listOfPackagePriceView = SolutionOfferDAO.getPriceForSetOfSolutionOfferIds(baseIds, tenantConfigurator);
					prodListUtil.addSolutionOfferPriceToProductMap(listOfPackagePriceView);
				}

				// get the price list for all the sub solution offer Id's and
				// update the respective ProductMap entry
				// List<PriceCatalogBean> listOfPriceCatalogBeansForsubSolutions
				// =
				// PriceDiscountUtil.getPrice(ProductListUtil.getAllSubSolutionIds(),
				// tenantConfigurator);

				
				
				List<SdpSolutionOfferExternalId> sdpSolutionOfferExternalIdList = SolutionOfferDAO.getDeviceInfoForSetOfSolutionOfferIds(baseIds, tenantConfigurator);
				prodListUtil.addDeviceInfoToProductMap(sdpSolutionOfferExternalIdList);
				
				
				
				String subSolutionIds = prodListUtil.getAllSubSolutionIds();
				if (!"".equals(subSolutionIds)) {

					List<PackagePriceView> listOfPackagePriceViewForsubSolutions = SolutionOfferDAO.getPriceForSetOfSolutionOfferIds(prodListUtil.getAllSubSolutionIds(), tenantConfigurator);
					prodListUtil.addSolutionOfferPriceToProductMap(listOfPackagePriceViewForsubSolutions);

					// get the Discounted for all the sub solution offer Id's,
					// calculate the Discount Price and update the respective
					// ProductMap entry
					List<DiscountBean> listOfDiscountBeans = SolutionOfferDAO.getDiscountForSetOfSolutionOfferIds(prodListUtil.getAllSubSolutionIds(), tenantConfigurator);

					prodListUtil.calculateDiscountedPrice(listOfPackagePriceViewForsubSolutions, listOfDiscountBeans);

					// Add up all the offerId price that are related to a
					// solutionofferId
					prodListUtil.addRelatedPriceForSolutionOfferId(listOfPackagePriceViewForsubSolutions);

					prodListUtil.setDiscountedPriceToProductMap(listOfPackagePriceViewForsubSolutions);

				}
			}
			
			Map<Long,ProductBeanCA> productMap = prodListUtil.getProductMap();
			Object objReturn = ProductListFormatterCA.bean2JSON(new ArrayList<ProductBeanCA>(productMap.values()), tenantConfigurator);
			String result = objReturn.toString();
			return result;

		} catch (Exception e) {
		
			LogUtil.errorLog(logger,
					systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR,
					e);
			throw new ActionException(systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		}
	}
}