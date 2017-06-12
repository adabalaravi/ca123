package com.accenture.avs.ca.be.action;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.accenture.avs.be.component.CheckRightsResult;
import com.accenture.avs.be.component.Offer;
import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.core.bean.PurchaseTransactionDetails;
import com.accenture.avs.be.core.bean.PurchaseType;
import com.accenture.avs.be.core.bean.StatusType;
import com.accenture.avs.be.core.bean.Subscription;
import com.accenture.avs.be.core.bean.UserInfo;
import com.accenture.avs.be.core.dao.PurchaseDAO;
import com.accenture.avs.be.core.dao.UserDAO;
import com.accenture.avs.be.core.exception.DAOException;
import com.accenture.avs.be.core.factory.AvsDaoFactory;
import com.accenture.avs.be.db.bean.AccountTechnicalPkg;
import com.accenture.avs.be.db.bean.AccountTechnicalPkgPK;
import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.bean.PurchaseTransaction;
import com.accenture.avs.be.db.dao.AccountTechPackageDAO;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.ReportUtil;
import com.accenture.avs.be.db.util.ValidatorUtils;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.formatter.GetProductPurchaseDetailsFormatter;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.response.ProductPurchaseResponse;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.be.util.ErrorMessageFormatterUtil;
import com.accenture.avs.ca.be.bean.BPointClientResult;
import com.accenture.avs.ca.be.dao.BPointPurchaseTransactionDAO;
import com.accenture.avs.ca.be.dao.SolutionOfferinclDurationDAO;
import com.accenture.avs.ca.be.db.bean.SolutionOfferinclDurationDetailsBean;
import com.accenture.avs.ca.be.gateway.BPointPurchaseGateway;
import com.accenture.avs.ca.be.json.bean.BPointProductPurchaseDetails;
import com.accenture.avs.ca.be.util.CAConstants;
import com.accenture.avs.ca.be.util.ReportUtilCA;
import com.accenture.avs.ca.be.util.WebConstants;

/**
 * @author h.kumar.satkuri
 *
 */
public class ProductPurchaseBPoint extends GenericAction {
	private static Logger logger = Logger.getLogger(ProductPurchaseBPoint.class);
	private HttpSession sessionHttp = null;
	protected String channel;
	protected String contentType;
	protected String contentId;
	protected String paymentType;
	private String contentTitle;
	private String jsonOutput = "";
	private Profile profile = null;
	private Object resultObjToJson = null;
	
	//receipt id is added as part of the new CR in the 2nd season. This is only applicable to APP packages and not a mandatory one.
	private String receiptID;
	private String purchaseDate;
	private Timestamp purchaseDateTS;
	// flag to know whether user is eligible for BT purchases
	private boolean isBTEligible = false;

	private GetProductPurchaseDetailsFormatter formatter;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115926267711178401L;

	public ProductPurchaseBPoint(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
	}

	public void validateRequest() throws ActionException {
		try {
						
			sessionHttp = request.getSession();
			
			channel = this.request.getParameter(Constants.CHANNEL); // mandatary
			ValidatorUtils.checkParameter(Constants.CHANNEL, channel, tenantConfigurator);

			profile = (Profile) sessionHttp.getAttribute(Constants.USERPROFILE);
			ValidatorUtils.checkParameter(Constants.USERPROFILE, profile, tenantConfigurator);
			if (profile.getCrmAccountId().equalsIgnoreCase(Constants.ROWID_DEFAULT)) {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER + Constants.USERPROFILE);
			}

			contentType = this.request.getParameter(Constants.CONTENT_TYPE); // mandatary
			ValidatorUtils.checkParameter(Constants.CONTENT_TYPE, contentType, tenantConfigurator);

			// SolutionOfferId of the SDP_SOLUTION_OFFER table
			contentId = this.request.getParameter(Constants.CONTENTID); // mandatary
			ValidatorUtils.checkParameter(Constants.CONTENTID, contentId, tenantConfigurator);
			this.validateNumberParameter(Constants.CONTENTID, contentId);

			paymentType = this.request.getParameter(Constants.PAYMENT_TYPE); // mandatary
			ValidatorUtils.checkParameter(Constants.PAYMENT_TYPE, paymentType, tenantConfigurator);

			contentTitle = this.request.getParameter(Constants.CONTENT_TITLE); // not mandatary
			//ValidatorUtils.checkParameter(Constants.CONTENT_TITLE, contentTitle, tenantConfigurator);
			
			//Verifying the received contentID is a part of BTPackageList or not- "returns True if eligible or false if not". 
			isBTEligible = checkForBTPackageIds();
			
			//receipt id is added as part of the new CR in the 2nd season. This is only applicable to APP packages and is mandatory one.
			receiptID = this.request.getParameter(CAConstants.RECEIPT_ID); 
			
			//2nd Season - New CR - Auto Renewal of App packages.
			//APP will send the purchaseDate && receiptID when-ever user Purchases the APP packages or when auto renewal.
			//  isBTEligible == true condition added as part of BT Integration
			if (channel.equalsIgnoreCase("IOS") || channel.equalsIgnoreCase("ANDROID") || isBTEligible == true){
				
				// it can be like receiptID = "MHNsc123567|s.attuluri@accenture.com";
				validateParameter(CAConstants.RECEIPT_ID, receiptID);
				if(isBTEligible){
					receiptID=constantsParameter.get((WebConstants.PREFIX_RECEIPTID).trim())+receiptID;
				}
				//purchaseDate is added as part of the new CR-AUTO RENEWAL in the 2nd season. This is only applicable to APP packages and is mandatory one.		
				purchaseDate = this.request.getParameter(CAConstants.PURCHASE_DATE);
				//AUTO RENEWAL issue in FE apps and BE is fixing the issue to support FE apps-
				//1. Added the below logs for capturing purchase date
				//2. Commented the below code as Purchase date is not a mandatory
				LogUtil.commonInfoLog(logger, "validateParameter","paramName=purchaseDate ParamValue="+purchaseDate);
				logPurchaseStatusInfoORErrorMsg("validateParamekter","paramName=purchaseDate ParamValue="+purchaseDate);
				 
				//validateParameter(CAConstants.PURCHASE_DATE, purchaseDate);
				//purchaseDateTS = epochToUTCConverterTimeStamp(purchaseDate);
				
			}
			
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR+"| MSG:", e);
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR);
			}
		}
	}

/*
	 * (non-Javadoc)
	 * 
	 * @see com.accenture.avs.be.framework.GenericAction#executeRequest()
	 */
	@SuppressWarnings("unchecked")
	public Object executeRequest() throws ActionException {
		String methodName = "executeRequest";
		formatter = new GetProductPurchaseDetailsFormatter(tenantConfigurator);
		//SolutionOffer solutionOffer = null;
		Subscription subscription = null;
		Long itemType = null;
		PurchaseTransactionDetails purchaseTransactionSolutionOffer = null;
		logPurchaseStatusInfoORErrorMsg(methodName,"STARTED Purchase Transactin Process.....");
		try {

			if(this.isPurchaseTransactioninPanic()) {
				logPurchaseStatusInfoORErrorMsg(methodName,"Purchase Transaction Is in Panic.");
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.OK, "","", "PURCHASE TRANSACTION IS IN PANIC", String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}
			// From AvsDaoFactory retrieve an instance of PurchaseDAO
			PurchaseDAO pDAO = AvsDaoFactory.getPurchaseDAO(tenantConfigurator);
			
			//Unique TransactionID check for auto renewal,transactionID is unique for each purchase transaction hence we need to validate it for its existence in DB.
			//if transactionID is found in DB,means that purchase transaction has  already been done.
			//hence user cannot buy this subscription
			
			PurchaseTransaction purchaseTransaction = null;
			
			
			//* Block that validates whether the user has got active BT package or not
			if(isBTEligible){
				
				//String btReceiptID = constantsParameter.get((WebConstants.PREFIX_RECEIPTID).trim())+receiptID;
				purchaseTransaction = BPointPurchaseTransactionDAO.getPurchaseTransactionByTransactionIDForBT(receiptID, tenantConfigurator);
				String btStatusCheck = "";
				//checking the BT commercial package is already bought or not for the user
				btStatusCheck = checkForBTPurchase(purchaseTransaction);
				
				if (btStatusCheck.contains(Constants.KO)) {
					
					//logPurchaseStatusInfoORErrorMsg(methodName,"BT - Content Already Bought with the transactionID and subscription is active");
					
					//loggerDebugEnabled(methodName, "Content Already Bought with the transactionID and subscription is active");
					//long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					//resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), null, String.valueOf(systemTime));
					return btStatusCheck.toString();
				}
			}
			else if (channel.equalsIgnoreCase("IOS") || channel.equalsIgnoreCase("ANDROID")){
					logPurchaseStatusInfoORErrorMsg(methodName,"inside Auto renewal logic - while testing the presence of the transactionID");
					
					purchaseTransaction = BPointPurchaseTransactionDAO.getPurchaseTransactionByTransactionID(receiptID, tenantConfigurator);
					
					if(purchaseTransaction != null && purchaseTransaction.getTransactionId() != null && !purchaseTransaction.getTransactionId().trim().equals(""))
					{
						if (profile.getUserId().equals(purchaseTransaction.getUser().getUserId())){ // content already bought by the same user
							
							logPurchaseStatusInfoORErrorMsg(methodName,"Content Already Bought with the TransactionID "+receiptID);
							loggerDebugEnabled(methodName, "Content Already Bought with the TransactionID "+receiptID);
							long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
							resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), null, String.valueOf(systemTime));
							return formatter.bean2JSON(resultObjToJson).toString();
						}else{
							
							logPurchaseStatusInfoORErrorMsg(methodName,"Content Already Bought by different user with the TransactionID "+receiptID);
							loggerDebugEnabled(methodName, "Content Already Bought by different user with the TransactionID "+receiptID);
							long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
							resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_4101_ERROR_CONTEN_ALREADY_BOUGHT_BY_DIFFERENT_USER)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_4101_ERROR_CONTEN_ALREADY_BOUGHT_BY_DIFFERENT_USER)), null, String.valueOf(systemTime));
							return formatter.bean2JSON(resultObjToJson).toString();
						}
				}
			}
				
			
			// START COMMON SECTION: CHECK IF PRODUCT WAS ALREADY PURCHASED
			if ((paymentType.equalsIgnoreCase(WebConstants.PAYMENT_PGW_BPOINT))) {			

				CheckRightsResult resul = pDAO.checkProductPurchased(contentId, profile, "N", tenantConfigurator);
				LogUtil.writeInfoExternalCallLog(ProductPurchaseBPoint.class, "TOP", "checkProductPurchased result " + resul.getResultCode() + " - " + resul.getErrorDescription(), System.currentTimeMillis());			 
				
				 /* If result==OK the product was already purchased with this contentId/profile If result=KO_BUY the product is NOT PURCHASED YET */	 
				if (resul.getResultCode().equals(Constants.OK) && resul.getErrorDescription().equals(Constants.OK)) {
					
					logPurchaseStatusInfoORErrorMsg(methodName,"Content Already Bought.");
					
					loggerDebugEnabled(methodName, "Content already bought");
					long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), null, String.valueOf(systemTime));
					return formatter.bean2JSON(resultObjToJson).toString();
				}

			}			
			loggerDebugEnabled(methodName, "CheckContentRightsGateway...passed"); // CheckContentRightsGateway is passed even when there is no content with the given contentId
			logPurchaseStatusInfoORErrorMsg(methodName,"CheckContentRightsGateway...Passed");
			
			List<Object[]> listOfferBySolution = null;

			List<PurchaseTransactionDetails> listPurchaseTransaction = new Vector<PurchaseTransactionDetails>();

			Long paymentTypeId = null;
			Long statusId = null;
			Long solutionOfferId = new Long(contentId);

			// retrieving the list of Offer associated to SolutionOfferID (contentId input parameter)

			listOfferBySolution = pDAO.getListOfferBySolutionOfferID(solutionOfferId, tenantConfigurator);

			// retrieving the itemType from PurchaseTransactionType table
			itemType = pDAO.getItemTypeByItemDescription(contentType, tenantConfigurator);
			loggerDebugEnabled(methodName, " itemType: " + itemType);

			// retrieving the paymentTypeId from PaymentType table
			paymentTypeId = pDAO.getPaymentTypeId(paymentType.toUpperCase(), tenantConfigurator);
			loggerDebugEnabled(methodName, " paymentTypeId: " + paymentTypeId);


			String checkForProductPurchase = commonCheckForProductPurchase(listOfferBySolution, itemType, paymentTypeId, statusId, formatter);

			if (commonCheckForProductPurchase(listOfferBySolution, itemType, paymentTypeId, statusId, formatter) != null) {
				loggerDebugEnabled(methodName, "Failed check for product purchase...");
				
				logPurchaseStatusInfoORErrorMsg(methodName,"Failed to check for product purchase...");
				//sendPurchaseStatusReport(profile, okAction, subscription, itemType, purchaseTransaction, transactionId, profileId, endDate, actionType, avsSequenceId)
				return checkForProductPurchase;
			}

			// END COMMON SECTION

			if (paymentType.equalsIgnoreCase(WebConstants.PAYMENT_PGW_BPOINT)) {
				// retrieving the SolutionOffer/Offer price from
				subscription = (Subscription) pDAO.getProductPrice(solutionOfferId, profile.getUserId(), tenantConfigurator);
				
				Timestamp endDateofSolOffer = null;
				endDateofSolOffer = subscription.getEndDate(); 

				if(subscription.getEndDate() == null) {
					LogUtil.commonInfoLog(logger, methodName,"END Date of the subscription IS NULL");
				}
				//AUTO RENEWAL issue in FE apps and BE is fixing the issue to support FE apps-Commented below code as Purchase date is not mandatory and setting Purchase date as current time stamp.
				// Updating start_date to purchaseDate when price is ZERO
				/*if (subscription.getPrice() == 0 && purchaseDate != null && !purchaseDate.trim().equals("")){
					subscription.setStartDate(purchaseDateTS);
				}*/
				// Set StartDate as current Day if null
				if(subscription.getStartDate() == null) {
					LogUtil.commonInfoLog(logger, methodName,"Start Date of the subscription IS NULL");
					subscription.setStartDate(new Timestamp(System.currentTimeMillis()));
					LogUtil.commonInfoLog(logger, methodName,"Start Date of the subscription : "+subscription.getId() +" ------ "+subscription.getStartDate());
				}
				
					// Check for the Duration & set endDate based on Duration
					SolutionOfferinclDurationDAO solutionOfferinclDurationDAO = new SolutionOfferinclDurationDAO();
					SolutionOfferinclDurationDetailsBean solutionOfferinclDurationDetailsBean = solutionOfferinclDurationDAO.getDurationofSolutionOffer(subscription.getId(), tenantConfigurator); 

					Timestamp endDatewithDuration = null;
					if(solutionOfferinclDurationDetailsBean.getDuration()!=null && solutionOfferinclDurationDetailsBean.getDuration() > 0 ) { 
						Long duration = solutionOfferinclDurationDetailsBean.getDuration();
						LogUtil.commonInfoLog(logger, methodName,"ContentId  --- StartDate and DURATION : "+subscription.getId() +" ------ "+subscription.getStartDate() +"---------"+duration.intValue() +";");
						Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
						c.setTimeInMillis(subscription.getStartDate().getTime());
						c.add(Calendar.DAY_OF_WEEK, duration.intValue());
						subscription.setEndDate(new java.sql.Timestamp(c.getTimeInMillis()));
						endDatewithDuration = new java.sql.Timestamp(c.getTimeInMillis());
						LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration : "+subscription.getId() +" ------ "+subscription.getEndDate() +";");
				}
					if(endDateofSolOffer!=null && !endDateofSolOffer.equals("") && endDatewithDuration!=null){
						if(endDatewithDuration.after(endDateofSolOffer)) {
							LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration is after EndDate of Solution Offer");
							LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration --- "+endDatewithDuration+" --- EndDate of Solution Offer --- "+endDateofSolOffer+";");
							subscription.setEndDate(endDateofSolOffer);
						}
					}
				
				loggerDebugEnabled(methodName, "Original Price : "+subscription.getPrice());
				loggerDebugEnabled(methodName, " Price AFTER CONVERSION : "+amountInCents(subscription.getPrice()));
				
				logPurchaseStatusInfoORErrorMsg(methodName,"SubScription StartDate="+subscription.getStartDate()+ " EndDate="+subscription.getEndDate()+" duration="+solutionOfferinclDurationDetailsBean.getDuration());		
				
				if(subscription.getDiscountedPrice() == null) {
					subscription.setDiscountedPrice(subscription.getPrice());
				}				
				loggerDebugEnabled(methodName, " Discounted Price AFTER CONVERSION : "+amountInCents(subscription.getDiscountedPrice()));				
				

				// Not required to check
				/*if(subscription.getFrequencyDays() > 0) { //is a one time purchase
					long systemTime = (long) (System.currentTimeMillis() / 1000);
					resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(WebConstants.ERR_PURCHASE_FREQUENCY_NOT_ZERO)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(WebConstants.ERR_PURCHASE_FREQUENCY_NOT_ZERO)), null, String.valueOf(systemTime));
					return jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
				} else  {*/

					String token = /*CheckSumUtil.checkSumSHA256(*/getUniqueStringForTransaction(contentId,
							profile.getUserId().toString(),
							DoubletoString(subscription.getDiscountedPrice()),
							subscription.getCurrency().toString(),
							WebConstants.MSG_PIPE);  
					
					// retrieve array offer
					Offer[] arrayOffer = subscription.getArrayOffer();

					// setting the solution offer data in PurchaseTransaction bean
					purchaseTransactionSolutionOffer = setDataPurchaseTransaction(solutionOfferId, null, itemType, paymentTypeId, 10L, subscription.getCurrency(), subscription.getPrice(), subscription.getDiscountedPrice(), subscription.getStartDate(), subscription.getEndDate(), token, subscription.getRecurrencyDiscountedPrice(), subscription.getRecurrencyPrice());
					listPurchaseTransaction.add(purchaseTransactionSolutionOffer);
											
					
					Iterator<Object[]> offerBySolutionIterator = listOfferBySolution.iterator();

					Object lstPkgPurcTran = null;
					if(subscription.getPrice() == 0) {
						lstPkgPurcTran = getListPkgTran(offerBySolutionIterator, arrayOffer, listPurchaseTransaction, solutionOfferId, itemType, paymentTypeId, 12L, subscription.getStartDate(), subscription.getEndDate(), token);
					}
					else {
						lstPkgPurcTran = getListPkgTran(offerBySolutionIterator, arrayOffer, listPurchaseTransaction, solutionOfferId, itemType, paymentTypeId, 10L, subscription.getStartDate(), subscription.getEndDate(), token);
					}
					if (lstPkgPurcTran!=null && lstPkgPurcTran instanceof String) {
						return lstPkgPurcTran.toString();
					} else {
						Object[] arrayLstPkgPurcTran = (Object[]) lstPkgPurcTran;
						listPurchaseTransaction = (List<PurchaseTransactionDetails>) arrayLstPkgPurcTran[1];
					}
					
					// Save purchase transaction
					String sequenceId = pDAO.saveListPurchaseTransaction(listPurchaseTransaction, tenantConfigurator);

					
					/* if price is zero(0) , no payment is done and
					 a successfull transaction is inserted into Purchase Transaction and Account Technical Package Table */
			
					if(subscription.getPrice() == 0) {
						logPurchaseStatusInfoORErrorMsg(methodName,"Subscription Price is ZERO...");
						Object jsonOutput = insertSuccessfulTransaction(sequenceId);
						
						return jsonOutput;
					}
					sendPurchaseStatusReport(methodName,profile, true, subscription,purchaseTransactionSolutionOffer, purchaseTransactionSolutionOffer.getTransactionId(), "", subscription.getEndDate(), "", sequenceId,"Payment Authentication is in Progress");
					//PurchaseTransactionDetails purchaseTran = pDAO.getSolutionOfferPurchaseTransactionByToken(token, tenantConfigurator);
					
						LogUtil.commonInfoLog(logger, methodName, "Sequence ID OBTAINED: "+sequenceId);
						LogUtil.commonInfoLog(logger, methodName, "Amount for Authorization : "+amountInCents(subscription.getDiscountedPrice()));
					
					//generate the CRN using the sequenceID
					String crn_1 = constantsParameter.get(WebConstants.CRN_1) + WebConstants.MSG_PIPE + sequenceId;
					logPurchaseStatusInfoORErrorMsg(methodName, "CRN1="+crn_1);
					
					// Initialize the payment by authorizing the merchant
					BPointPurchaseGateway bPointPurchaseGateway = new BPointPurchaseGateway();
					
					if(this.isBPointinPanic()) {
						logPurchaseStatusInfoORErrorMsg(methodName, "BPoint is in Panic");
						long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
						resultObjToJson = getObjToJSon(Constants.OK, "","", "BPOINT IS IN PANIC", String.valueOf(systemTime));
						return formatter.bean2JSON(resultObjToJson).toString();
					}
					
					
					BPointClientResult bPointClientResult = new BPointClientResult();
					
					bPointClientResult = (BPointClientResult) bPointPurchaseGateway.authorizeMerchant(crn_1,amountInCents(subscription.getDiscountedPrice()),tenantConfigurator);
					
					// if RESULT = OK Pass the details to FE else Update the records inserted into PurchaseTransaction 
					
					if (bPointClientResult.getOut_Result_Code().equals(Constants.KO)) {

						long systemTime = (long) (System.currentTimeMillis() / 1000);
						resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(bPointClientResult.getOut_ErrorText()),
													ErrorMessageFormatterUtil.setErrorMessage(bPointClientResult.getOut_ErrorText()), "", String.valueOf(systemTime));
						LogUtil.commonInfoLog(logger, methodName, "User id " + profile.getUserId() + "|" +"Result code :KO | Error while Authorizing the Merchant = " + bPointClientResult.getOut_Resp_code() +"|"+  bPointClientResult.getOut_ErrorText());
						logPurchaseStatusInfoORErrorMsg(methodName, "User id " + profile.getUserId() + "|" +"Result code :KO | Error while Authorizing the Merchant = " + bPointClientResult.getOut_Resp_code() +"|"+  bPointClientResult.getOut_ErrorText());
						// Update the record which was inserted with error details.. 
						try {
							updateTransactionRecord(pDAO,tenantConfigurator,sequenceId,bPointClientResult);
						 } catch (Exception e) {
							 LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
							 logPurchaseStatusInfoORErrorMsg(methodName,"ERROR while updating the error Text="+systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
							 logPurchaseStatusInfoORErrorMsg(methodName,"Actual ERROR while updating the error Text="+e.getMessage());
								if (e instanceof ActionException) {
									throw (ActionException) e;
								} else {
									throw new ActionException(systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
								}
						}
						return jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
					} else{

						long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
						resultObjToJson = getObjToJSon(Constants.OK, "", WebConstants.SUCCESS, getObjToJSon_BPointProductPurchaseDetails(contentId,contentTitle,contentType,subscription.getCurrency(),
										amountInCents(subscription.getPrice()), bPointClientResult.getOut_Resp_code(), bPointClientResult.getOut_Pay_Token(),bPointClientResult.getOut_ErrorText(),crn_1),String.valueOf(systemTime));
						LogUtil.commonInfoLog(logger, methodName, "User id " + profile.getUserId() + "|" + "Result code :OK | Response Code = " + bPointClientResult.getOut_Resp_code());
						jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
						
						sendPurchaseStatusReport(methodName,profile, true, subscription,purchaseTransactionSolutionOffer, purchaseTransactionSolutionOffer.getTransactionId(), "", subscription.getEndDate(), "", sequenceId,"Payment Authentication is COMPLETED SUCCESFULLY.");
					}
		//		}
			}

			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, "CHANNEL: " + channel + " CONTENT_ID: " + contentId + "CONTENT_TITLE: "+ contentTitle + " PROFILE: " + profile);
			}
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
			sendPurchaseStatusReport(methodName,profile, true, subscription,purchaseTransactionSolutionOffer, purchaseTransactionSolutionOffer.getTransactionId(), "", subscription.getEndDate(), "", "","Payment Authentication is FAILED");
			logPurchaseStatusInfoORErrorMsg(methodName, "ERROR=Payment Authentication is FAILED DUE TO "+e.getMessage());
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
			}
		} 
			return jsonOutput;
	}

	private String getUniqueStringForTransaction(String contentId, String userId, String disAmt, String currency, String separator) {
		StringBuilder uniqueStr = new StringBuilder();
		uniqueStr.append(System.currentTimeMillis()+separator);
		uniqueStr.append(contentId+separator);
		uniqueStr.append(userId+separator);
		uniqueStr.append(disAmt+separator);
		uniqueStr.append(currency);
		return uniqueStr.toString();
	}


	private String commonCheckForProductPurchase(List<Object[]> listOfferBySolution, Long itemType, Long paymentTypeId, Long statusId, GetProductPurchaseDetailsFormatter formatter) throws Exception {
		String methodName = "commonCheckForProductPurchase";
		if (listOfferBySolution == null || listOfferBySolution.size() == 0) {
			loggerDebugEnabled(methodName, "contentId (solutionOfferId) not found on DB (SdpSolutionOfferView)");
			logPurchaseStatusInfoORErrorMsg(methodName,"contentId (solutionOfferId) not found on DB (SdpSolutionOfferView)");
			
			long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
			resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_3089_CONTENT_NOT_FOUND_ON_DB), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_3089_CONTENT_NOT_FOUND_ON_DB), null, String.valueOf(systemTime));
			return formatter.bean2JSON(resultObjToJson).toString();
		}

		if (itemType == null) {
			loggerDebugEnabled(methodName, "contentType (itemDescription) not found on DB (PurchaseTransactionType table)");
			logPurchaseStatusInfoORErrorMsg(methodName,"contentType (itemDescription) not found on DB (PurchaseTransactionType table)");
			long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
			resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_CONFIGURATOR_3069_NOTFAOUNDONDB), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_CONFIGURATOR_3069_NOTFAOUNDONDB), null, String.valueOf(systemTime));
			return formatter.bean2JSON(resultObjToJson).toString();
		}

		if (paymentTypeId == null) {
			loggerDebugEnabled(methodName, "paymentType (paymentMethod) not found on DB (PaymentType table)");
			logPurchaseStatusInfoORErrorMsg(methodName,"paymentType (paymentMethod) not found on DB (PaymentType table)");
			long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
			resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_CONFIGURATOR_3069_NOTFAOUNDONDB), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_CONFIGURATOR_3069_NOTFAOUNDONDB), null, String.valueOf(systemTime));
			return formatter.bean2JSON(resultObjToJson).toString();
		}
		return null;
	}



	/**
	 * 
	 * @param id
	 * @param arrayOffer
	 * @return
	 * @throws Exception
	 */
	private Offer getOfferById(Long id, Offer[] arrayOffer) throws Exception {
		Offer offerFound = null;

		for (int i = 0; i < arrayOffer.length; i++) {
			Offer offer = (Offer) arrayOffer[i];

			if (id.equals(offer.getId())) {
				offerFound = offer;
				break;
			}
		}

		return offerFound;
	}

	/**
	 * 
	 * @param userId
	 * @param techPackageId
	 * @param crmAccountId
	 * @return
	 * @throws Exception
	 */
	private AccountTechnicalPkg setDataAccountTechnicalPkg(Long userId, Long techPackageId, String crmAccountId, Timestamp validityPeriod) throws Exception {
		AccountTechnicalPkg accountTechnicalPkg = new AccountTechnicalPkg();
		AccountTechnicalPkgPK compId = new AccountTechnicalPkgPK();
		compId.setUserId(userId);
		compId.setTechPackageId(String.valueOf(techPackageId));
		accountTechnicalPkg.setCompId(compId);
		accountTechnicalPkg.setViewsNumber(null);
		accountTechnicalPkg.setTechPackageValue("S");
		accountTechnicalPkg.setValidityPeriod(validityPeriod);
		accountTechnicalPkg.setCrmAccountId(crmAccountId);

		return accountTechnicalPkg;
	}

	/**
	 * 
	 * @param solutionOfferId
	 * @param externalID
	 * @param itemType
	 * @param paymentTypeId
	 * @param statusId
	 * @param currency
	 * @param price
	 * @param discountedPrice
	 * @return
	 * @throws Exception
	 */
	private PurchaseTransactionDetails setDataPurchaseTransaction(Long solutionOfferId, Long externalID, Long itemType, Long paymentTypeId, Long statusId, String currency, Double price, Double discountedPrice, Timestamp startDate, Timestamp endDate, String tokenS, Double recurringDiscPrice, Double recurringOrigPrice) throws Exception {
		PurchaseTransactionDetails purchaseTran = new PurchaseTransactionDetails();

		Long itemId = null;
		Long parentItenId = null;

		if (externalID == null) {
			itemId = solutionOfferId;
			parentItenId = null;
		} else {
			itemId = externalID;
			parentItenId = solutionOfferId;
		}

		purchaseTran.setItemId(String.valueOf(itemId));
		purchaseTran.setParentItemId(parentItenId);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(profile.getUserId());
		userInfo.setEmail(profile.getCrmAccountId());
		userInfo.setCrmAccountId(profile.getCrmAccountId());
		userInfo.setStatusId(profile.getStatusId());


		purchaseTran.setUserInfo(userInfo);

		String transactionId = null;
		purchaseTran.setTransactionId(transactionId);

		purchaseTran.setCurrency(currency);
		purchaseTran.setOriginalPrice(DoubletoString(price));
		purchaseTran.setTransactionPrice(DoubletoString(discountedPrice));
		
		PurchaseType purchaseType = new PurchaseType();
		purchaseType.setItemType(itemType);
		purchaseTran.setItemType(purchaseType);

		purchaseTran.setPaymentTypeId(paymentTypeId);

		StatusType state = new StatusType();
		state.setStatusId(statusId);
		purchaseTran.setState(state);

		purchaseTran.setStartDate(startDate);
		purchaseTran.setEndDate(endDate);

		purchaseTran.setRecurringTransactionPrice(DoubletoString(recurringDiscPrice));
		purchaseTran.setRecurringOriginalPrice(DoubletoString(recurringOrigPrice));
		purchaseTran.setToken(tokenS);
		
		if(price == 0) {
			purchaseTran.setPgwStatus(WebConstants.COMPLETED);
		}

		return purchaseTran;
	}

	/**
	 * 
	 * @param resultCode
	 * @param errorDescription
	 * @param message
	 * @param rO
	 * @param systemTime
	 * @return
	 * @throws Exception
	 */
	private ProductPurchaseResponse getObjToJSon(String resultCode, String errorDescription, String message, Object rO, String systemTime) throws Exception {
		ProductPurchaseResponse p = new ProductPurchaseResponse();
		p.setResultCode(resultCode);
		p.setErrorDescription(errorDescription);
		p.setMessage(message);
		p.setResultObj(rO);
		p.setSystemTime(systemTime);

		return p;
	}


	/**
	 * 
	 * @param contentId
	 * @param contentType
	 * @param price
	 * @param token
	 * @param currency
	 * @param contentTitle
	 * @return
	 * @throws Exception
	 */
	private BPointProductPurchaseDetails getObjToJSon_BPointProductPurchaseDetails(String contentId, String contentTitle, 
						String contentType, String currency, String price, String responseCode, String token , String errorText,String crn ) throws Exception {
		BPointProductPurchaseDetails t = new BPointProductPurchaseDetails();
		t.setContentId(contentId);
		t.setContentTitle(contentTitle);
		t.setContentType(contentType);
		t.setCrn(crn);
		t.setCurrency(currency);
		t.setErrorText(errorText);
		t.setPrice(price);
		t.setResponseCode(responseCode);
		t.setToken(token);
		return t;
	}	

	/**
	 * 
	 * @param externalID
	 * @return
	 * @throws Exception
	 */
	private Long getExternalIDNumber(String externalID) throws Exception {
		String numExternalID = null;
		Long numExtId = null;

		for (int i = 0; i < externalID.length(); i++) {
			char car = externalID.charAt(i);
			if (car == '_') {
				// getting the numerical string after the character '_'
				numExternalID = externalID.substring(i + 1);
				break;
			}
		}

		try {// check if numerical string is a valid number
			numExtId = new Long(numExternalID);
		} catch (NumberFormatException ne) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3135_NUMBER_FORMAT, ne);
			throw new Exception("externalID not valid: " + externalID);
		}

		return numExtId;
	}

	private Object getListPkgTran(Iterator<Object[]> offerBySolutionIterator, Offer[] arrayOffer, List<PurchaseTransactionDetails> listPurchaseTransaction, Long solutionOfferId, Long itemType, Long paymentTypeId, Long statusId, Timestamp startDate, Timestamp endDate, String tokenS) throws Exception {
		String methodName = "getListPkgTran";
		Object[] lstPkgPurTran = new Object[2];

		// List<PurchaseTransaction> listPurchaseTransaction = null;
		List<AccountTechnicalPkg> listAccountTechnicalPkg = null;

		// listPurchaseTransaction = new Vector<PurchaseTransaction>();
		listAccountTechnicalPkg = new Vector<AccountTechnicalPkg>();

		while (offerBySolutionIterator.hasNext()) {
			Object[] currRec = offerBySolutionIterator.next();

			// Long solutionOfferID = (Long)currRec[2];
			String externalID = (String) currRec[19]; // sdp_offer_view.externalID

			Long numExtId = null;
			try {
				numExtId = this.getExternalIDNumber(externalID);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					LogUtil.commonDebugLog(logger, methodName, "externalID (by sdp_offer_view) not valid");
				}
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), null, String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}

			// retrieve the offer bean from array offer
			Offer offer = this.getOfferById(numExtId, arrayOffer);

			if (offer == null) {
				if (logger.isDebugEnabled()) {
					LogUtil.commonDebugLog(logger, methodName, "externalID number (by sdp_offer_view) not matching with data retrieved by PaymentGatewayUtil component");
				}
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), null, String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}

			// setting the offer data in PurchaseTransaction bean
			PurchaseTransactionDetails purchaseTransactionOffer = setDataPurchaseTransaction(solutionOfferId, numExtId, itemType, paymentTypeId, statusId, offer.getCurrency(), offer.getPrice(), offer.getDiscountedPrice(), startDate, endDate, tokenS, offer.getRecurrencyDiscountedPrice(), offer.getRecurrencyPrice());
			listPurchaseTransaction.add(purchaseTransactionOffer);

		/*	// setting time of endDato to 23:59.59
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
			Timestamp endDateAtMidnight = null;
			if (endDate != null) {
				c.setTimeInMillis(endDate.getTime());
				String endTimeForPurchase = constantsParameter.PURCHASE_END_TIME;
				if (endTimeForPurchase == null || endTimeForPurchase.isEmpty())
					endTimeForPurchase = "23:59:59";

				String[] tmp = endTimeForPurchase.split(":");
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmp[0]));
				c.set(Calendar.MINUTE, Integer.parseInt(tmp[1]));
				c.set(Calendar.SECOND, Integer.parseInt(tmp[2]));
				endDateAtMidnight = new java.sql.Timestamp(c.getTimeInMillis());
			}
*/			
			// setting the data in AccountTechnicalPkg bean
			AccountTechnicalPkg accountTechnicalPkgOffer = setDataAccountTechnicalPkg(profile.getUserId(), numExtId, profile.getCrmAccountId(), endDate);
			listAccountTechnicalPkg.add(accountTechnicalPkgOffer);

		}

		lstPkgPurTran[0] = listAccountTechnicalPkg;
		lstPkgPurTran[1] = listPurchaseTransaction;

		return lstPkgPurTran;
	}
	
	private void loggerDebugEnabled(String methodName, String text)
	{
		logPurchaseStatusInfoORErrorMsg(methodName, text);
		if (logger.isDebugEnabled()) {
			LogUtil.commonDebugLog(logger, methodName, text);
		}
	}
	
	private void updateTransactionRecord(PurchaseDAO purchaseDAO, TenantConfigurator tenantConfigurator,String sequenceId,BPointClientResult bPointClientResult) throws Exception{
		String methodName ="updateTransactionRecord";
		LogUtil.commonInfoLog(logger, methodName, "Error while Authorization Of Merchant with BPoint ---- STARTS");
		logPurchaseStatusInfoORErrorMsg(methodName, "Error while Authorization Of Merchant with BPoint ---- STARTS");
		PurchaseTransactionDetails tran = null;
		try {
				if(sequenceId == null) {
					LogUtil.commonInfoLog(logger, methodName, "sequenceId is NULL");
					logPurchaseStatusInfoORErrorMsg(methodName, "sequenceId is NULL");
					throw new ActionException("sequenceId is NULL");
				}
				
				tran = purchaseDAO.getPurchaseTransactionBySeqId(Long.valueOf(sequenceId), tenantConfigurator);
				
				if(tran == null) {
					LogUtil.commonInfoLog(logger, methodName, "No Record in DB with sequence ID --  "+sequenceId+";");
					logPurchaseStatusInfoORErrorMsg(methodName, "No Record in DB with sequence ID --  "+sequenceId+";");
					throw new ActionException(systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID));
				}
				
				// Set State to 2
				StatusType status = new StatusType();
				status.setStatusId(2L);
				tran.setState(status);
				tran.setPgwStatus(WebConstants.ERROR);
				tran.setNotes("Error while Authorization Of Merchant with BPoint | "+bPointClientResult.getOut_Resp_code() +"|" + bPointClientResult.getOut_ErrorText());
				logPurchaseStatusInfoORErrorMsg(methodName, "Error while Authorization Of Merchant with BPoint | "+bPointClientResult.getOut_Resp_code() +"|" + bPointClientResult.getOut_ErrorText());
				
				// retrieve purchase transaction by token
				List<PurchaseTransactionDetails> lstPurchaseTrans = new ArrayList<PurchaseTransactionDetails>();
				lstPurchaseTrans = purchaseDAO.getOfferPurchaseTransactionByToken(tran.getToken(), tenantConfigurator);

				if (lstPurchaseTrans == null || lstPurchaseTrans.size() == 0) {
					StatusType stateError = new StatusType();
					stateError.setStatusId(2L);
					tran.setState(stateError);
					purchaseDAO.updatePurchaseTransaction(tran, tenantConfigurator);
					throw new Exception(systemMessages.ERROR_BE_ACTION_3126_ERRPURCHASE);
				}
				
				for (int i = 0; i < lstPurchaseTrans.size(); i++) {
					PurchaseTransactionDetails pt = new PurchaseTransactionDetails();
					pt = lstPurchaseTrans.get(i);
					pt.setState(tran.getState());
					pt.setPgwStatus(tran.getPgwStatus());
				}

				lstPurchaseTrans.add(tran);
				//Save Purchase Transactions
				purchaseDAO.saveListPurchaseTransaction(lstPurchaseTrans, tenantConfigurator);	
				LogUtil.commonInfoLog(logger, methodName, "Error while Authorization Of Merchant with BPoint ---- ENDS");
				logPurchaseStatusInfoORErrorMsg(methodName, "Error while Authorization Of Merchant with BPoint ---- ENDS");
		}
		catch (Exception e) {
			LogUtil.commonInfoLog(logger, methodName, WebConstants.ERR_ORDERID_NOT_VALID+" | sequenceId:"+sequenceId+";");
			logPurchaseStatusInfoORErrorMsg(methodName, "Actual ERROR="+e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}
	
	// The below method is called when the price of the product is ZERO(0)
	
	private Object insertSuccessfulTransaction(String sequenceId) throws Exception{
		String methodName = "insertSuccessfulTransaction";
		formatter = new GetProductPurchaseDetailsFormatter(tenantConfigurator);
		Subscription subscription = null;
		Long itemType = null;
		String transactionId = "";
		String contentId = "";
		Long userId =null;
		UserInfo userInfo = null;
		Timestamp tmstEndDate = null;
		Timestamp tranStartDate = null;
		
		PurchaseTransactionDetails tran = null;
		String customerID=null;
		UserDAO uDAO = null;
		
		try {
			
			LogUtil.commonInfoLog(logger, methodName,"");
			logPurchaseStatusInfoORErrorMsg(methodName,"STARTED...");
			// retrieve from factory an instance of PurchaseDAO
			PurchaseDAO pDAO = AvsDaoFactory.getPurchaseDAO(tenantConfigurator);
			uDAO = AvsDaoFactory.getUserDAO(tenantConfigurator);

			List<Object[]> listOfferBySolution = null;

			List<PurchaseTransactionDetails> listPurchaseTransaction = new Vector<PurchaseTransactionDetails>();
			List<AccountTechnicalPkg> listAccountTechnicalPkg = new Vector<AccountTechnicalPkg>();

			Long paymentTypeId = null;
			Long statusId = null;
			String totalAmount = null;
			customerID = sequenceId;
			
			LogUtil.commonInfoLog(logger, methodName,"customerID:"+customerID+"; Transaction Ref. No.:");
			logPurchaseStatusInfoORErrorMsg(methodName,"CustomerID="+customerID);
			
			try { // customerID is the SequenceId which is part of CRN
				tran = pDAO.getPurchaseTransactionBySeqId(Long.valueOf(customerID), tenantConfigurator);
			} catch(DAOException e) {
				LogUtil.commonInfoLog(logger, methodName, systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID)+" | customerID:"+customerID+";");
				
				logPurchaseStatusInfoORErrorMsg(methodName,"ERROR="+systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID)+" | customerID:"+customerID+";");
				logPurchaseStatusInfoORErrorMsg(methodName,"Actual ERROR="+e.getMessage() + " CustomerID="+customerID);
				
				return sendErrorResponse(systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID), systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID));
			}

			// Check the status before processing the transaction, if status is completed then return transaction is already processed
			String statusName = tran.getState().getStatusName();

			if (statusName.equalsIgnoreCase(WebConstants.COMPLETED)) {
				LogUtil.commonInfoLog(logger, methodName, systemMessages.get(WebConstants.INFO_TRAN_COMPLETED)+" | customerID:"+customerID+";");
				
				logPurchaseStatusInfoORErrorMsg(methodName,"CustomerID="+customerID + "; "+systemMessages.get(WebConstants.INFO_TRAN_COMPLETED));

				return sendErrorResponse(systemMessages.get(WebConstants.INFO_TRAN_COMPLETED), systemMessages.get(WebConstants.INFO_TRAN_COMPLETED));
			}
			
			Long solutionOfferId = new Long(tran.getItemId());
			String token = tran.getToken();
			listOfferBySolution = pDAO.getListOfferBySolutionOfferID(solutionOfferId, tenantConfigurator);
			itemType = tran.getItemType().getItemType();
			paymentTypeId = tran.getPaymentTypeId();
			totalAmount = tran.getTransactionPrice();
			userId = tran.getUserInfo().getUserId();
			contentId = String.valueOf(solutionOfferId);
			contentType = tran.getItemType().getItemDescription();
			tranStartDate = tran.getCreationDate();
			
			// WHAT SHOULD BE THE VALUE FOR THE TRANSACTION ID ?
			//transactionId = null;//getUniqueStringForTransaction(contentId, String.valueOf(userId), totalAmount, tran.getCurrency(),WebConstants.MSG_PIPE); 
			
			//receipt id is added as part of the new CR in the 2nd season. This is only applicable to APP packages and not a mandatory one.
			transactionId = receiptID;
			
			subscription = (Subscription) pDAO.getProductPrice(solutionOfferId, userId, tenantConfigurator);


			LogUtil.commonInfoLog(logger, methodName,"Inserting  successful Transaction record as Price is ZERO (0) | customerID: "+customerID+";");
			
			logPurchaseStatusInfoORErrorMsg(methodName,"Inserting  successful Transaction record as Price is ZERO (0) | customerID: "+customerID+";");
			
			try {
					String payerId = tran.getPayerId();

					tran.setTransactionId(transactionId);
					tran.setPgwStatus(WebConstants.COMPLETED);
					//tran.setEndDate(tmstEndDate);
					tran.setPayerId(payerId);
					tran.setCreationDate(tranStartDate);
					tran.setNotes("TRANSACTION | "+WebConstants.COMPLETED);

					StatusType statusType = new StatusType();
					statusType.setStatusId(12L);
					// statusType.setStatusName(WebConstants.COMPLETED); 
					tran.setState(statusType);	
						
					//load UserInfo Data
					userInfo = uDAO.loadUser(userId, true);
					
					// retrieve array offer
					Offer[] arrayOffer = subscription.getArrayOffer();
					Iterator<Object[]> offerBySolutionIterator = listOfferBySolution.iterator();

					//2nd Season - New CR - Auto Renewal of App packages.
					//
					//AUTO RENEWAL issue in FE apps and BE is fixing the issue to support FE apps-Commented below code as Purchase date is not mandatory 
					//subscription.setStartDate(purchaseDateTS);
					// Set StartDate as current Day if null
					if(subscription.getStartDate() == null) {
						LogUtil.commonInfoLog(logger, methodName,"Start Date of the subscription IS NULL");
						//Commented the below code as start date setting as current date  
						//subscription.setStartDate(new Timestamp(System.currentTimeMillis()));
						//Added the below code as start date taken as transaction start date 
						subscription.setStartDate(tran.getStartDate());
						LogUtil.commonInfoLog(logger, methodName,"Start Date of the subscription : "+subscription.getId() +" ------ "+subscription.getStartDate());
						
						logPurchaseStatusInfoORErrorMsg(methodName,"Start Date of the subscription : "+subscription.getId() +" ------ "+subscription.getStartDate());
						
						
					}
					
					Timestamp endDateofSolOffer = null;
					endDateofSolOffer = subscription.getEndDate();
					
					// calculate endDate
					Calendar today = Calendar.getInstance();
					if (subscription.getFrequencyDays() > 0) {
						today.add(Calendar.DAY_OF_MONTH, subscription.getFrequencyDays().intValue());
						tmstEndDate = new Timestamp(today.getTimeInMillis());
						tran.setEndDate(tmstEndDate);
					} 		
					else {
						if(subscription.getEndDate() != null) {
							tmstEndDate = subscription.getEndDate(); //Subscription Enddate fetched from sdp_solution_offer_view enddate
							tran.setEndDate(tmstEndDate);
							LogUtil.commonInfoLog(logger, methodName,"EndDate of the subscription : "+subscription.getId() +" ------ "+subscription.getEndDate());
							logPurchaseStatusInfoORErrorMsg(methodName,"EndDate of the subscription : "+subscription.getId() +" ------ "+subscription.getEndDate());
						}
						
						// Check for the Duration & set endDate based on Duration
						SolutionOfferinclDurationDAO solutionOfferinclDurationDAO = new SolutionOfferinclDurationDAO();
						SolutionOfferinclDurationDetailsBean solutionOfferinclDurationDetailsBean = solutionOfferinclDurationDAO.getDurationofSolutionOffer(subscription.getId(), tenantConfigurator); 

						Timestamp endDatewithDuration = null;
						if(solutionOfferinclDurationDetailsBean.getDuration()!=null && solutionOfferinclDurationDetailsBean.getDuration() > 0 ) { 
							Long duration = solutionOfferinclDurationDetailsBean.getDuration();
							LogUtil.commonInfoLog(logger, methodName,"ContentId  --- StartDate and DURATION : "+subscription.getId() +" ------ "+subscription.getStartDate() +"---------"+duration.intValue() +";");
							logPurchaseStatusInfoORErrorMsg(methodName,"ContentId  --- StartDate and DURATION : "+subscription.getId() +" ------ "+subscription.getStartDate() +"---------"+duration.intValue() +";");
							Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
							c.setTimeInMillis(subscription.getStartDate().getTime());
							c.add(Calendar.DAY_OF_WEEK, duration.intValue());
							tmstEndDate = new java.sql.Timestamp(c.getTimeInMillis());
							tran.setEndDate(tmstEndDate);
							endDatewithDuration = new java.sql.Timestamp(c.getTimeInMillis());
							LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration : "+subscription.getId() +" ------ "+endDatewithDuration +";");
						}
						
						if(endDateofSolOffer!=null && !endDateofSolOffer.equals("") && endDatewithDuration!=null){
							if(endDatewithDuration.after(endDateofSolOffer)) {
								LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration is after EndDate of Solution Offer");
								LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration --- "+endDatewithDuration+" --- EndDate of Solution Offer --- "+endDateofSolOffer+";");
								logPurchaseStatusInfoORErrorMsg(methodName,"EndDate of subscription after adding duration --- "+endDatewithDuration+" --- EndDate of Solution Offer --- "+endDateofSolOffer+";");
								tmstEndDate = endDateofSolOffer;
								tran.setEndDate(tmstEndDate);
							}
						}
					}
						
					Object lstPkgPurcTran = getListPkgTranforAppPurchase(offerBySolutionIterator, arrayOffer, listPurchaseTransaction, solutionOfferId, itemType, paymentTypeId, 12L, subscription.getStartDate(), tmstEndDate, token, userInfo);
					
					if (lstPkgPurcTran instanceof String) {
						return lstPkgPurcTran.toString();
					} else {
						Object[] arrayLstPkgPurcTran = (Object[]) lstPkgPurcTran;
						listAccountTechnicalPkg = (List<AccountTechnicalPkg>) arrayLstPkgPurcTran[0];
					}
						
					
					// SAVE account technical package
					AccountTechPackageDAO.saveListAccountTechnicalPkg(listAccountTechnicalPkg, tenantConfigurator);							
					
					// retrieve purchase transaction by token
					List<PurchaseTransactionDetails> lstPurchaseTrans = new ArrayList<PurchaseTransactionDetails>();
					lstPurchaseTrans = pDAO.getOfferPurchaseTransactionByToken(token, tenantConfigurator);

					if (lstPurchaseTrans == null) {
						StatusType stateError = new StatusType();
						stateError.setStatusId(2L);
						tran.setState(stateError);
						tran.setPayerId(tran.getPayerId());
						pDAO.updatePurchaseTransaction(tran, tenantConfigurator);
						throw new Exception(systemMessages.ERROR_BE_ACTION_3126_ERRPURCHASE);

					}
					for (int i = 0; i < lstPurchaseTrans.size(); i++) {
						PurchaseTransactionDetails pt = new PurchaseTransactionDetails();
						pt = lstPurchaseTrans.get(i);
						pt.setEndDate(tran.getEndDate());
						pt.setStartDate(tran.getStartDate());
						pt.setPayerId(tran.getPayerId());
						pt.setState(tran.getState());
						pt.setRecurringProfileId(tran.getRecurringProfileId());
						pt.setPgwStatus(tran.getPgwStatus());
						pt.setTransactionId(tran.getTransactionId());
						pt.setCreationDate(tran.getCreationDate());
					}

					lstPurchaseTrans.add(tran);
					//Save Purchase Transactions
					pDAO.saveListPurchaseTransaction(lstPurchaseTrans, tenantConfigurator);	
					
				} catch (Exception exp) {
					LogUtil.errorLog(logger, systemMessages.get(WebConstants.ERR_TRAN_PURCHASE_ERROR)+" | customerID:"+customerID+";", exp);
					
					logPurchaseStatusInfoORErrorMsg(methodName, "ERROR="+systemMessages.get(WebConstants.ERR_TRAN_PURCHASE_ERROR)+" | customerID:"+customerID);
					logPurchaseStatusInfoORErrorMsg(methodName,"Actual ERROR="+exp.getMessage());
					
					AccountTechPackageDAO.deleteListAccountTechnicalPkg(listAccountTechnicalPkg, tenantConfigurator);	
					StatusType stateError = new StatusType();
					stateError.setStatusId(2L);
					tran.setState(stateError);
					tran.setPayerId(tran.getPayerId());
					pDAO.updatePurchaseTransaction(tran, tenantConfigurator);
					throw new Exception(exp.getMessage());

				}

				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.OK, "", WebConstants.SUCCESS, getObjToJSon_BPointProductPurchaseDetails(contentId,contentTitle,contentType,subscription.getCurrency(),
						amountInCents(subscription.getPrice()), "", "","",""), String.valueOf(systemTime));
				jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
				LogUtil.commonInfoLog(logger, methodName, "customerID:"+customerID+"; CHANNEL: " + channel + " CONTENT_ID: " + solutionOfferId + " PROFILE: " + userInfo != null ? userInfo.getCrmAccountId() : "");

				sendPurchaseStatusReport(methodName,profile,true,subscription, tran, transactionId, "", tmstEndDate, "", String.valueOf(tran.getSequenceId()),"insertSuccesfulTransaction: SUCCESS.");
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR+" | customerID:"+customerID, e);
			
			logPurchaseStatusInfoORErrorMsg(methodName, "ERROR="+systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
			logPurchaseStatusInfoORErrorMsg(methodName, "Actual ERROR="+e.getMessage());
			sendPurchaseStatusReport(methodName,profile,true,subscription, tran, transactionId, "", tmstEndDate, "", String.valueOf(tran.getSequenceId()),"insertSuccesfulTransaction: FAILED.");
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
			}
		} finally {
				sendReport(profile, true, subscription, String.valueOf(itemType), tran, transactionId, "", tmstEndDate, "", String.valueOf(tran.getSequenceId()));				
			}
		return jsonOutput;
	}
	private String sendErrorResponse(String errorDesc, String errMsg) throws Exception {		
		long systemTime = (long) (System.currentTimeMillis() / 1000);
		resultObjToJson = getObjToJSon(Constants.KO, errorDesc, errMsg, null, String.valueOf(systemTime));
		return jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
	}
	
	protected void sendReport(Profile profile, boolean okAction,
			Subscription subscription, String itemType,
			PurchaseTransactionDetails purchaseTransaction,
			String transactionId, String profileId, Timestamp endDate,
			String actionType, String avsSequenceId) {
		try {
			if (profile != null && okAction) {
				ReportUtil report = new ReportUtil();
				LogUtil.commonInfoLog(logger, "sendReport ", "LOG VALUES contentId | "+ contentId +" | contentType:"+ contentType +" | paymentType :"+paymentType);
				report.sendPurchaseReport("purchase", tenantConfigurator.getTenantId(), profile.getCrmAccountId(), "", endDate, channel, contentId, "", paymentType, String.valueOf(purchaseTransaction.getSequenceId()),
								transactionId, String.valueOf(purchaseTransaction.getTransactionPrice()), String.valueOf(subscription.getPrice()), subscription.getCurrency(), "COMPLETED", contentType, subscription.getRecurrencyDiscountedPrice().toString(),
						subscription.getRecurrencyPrice().toString(), profileId);
			}
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3128_ERRSENDREPORTSYSLOG, e);

		}
	}
	protected void sendPurchaseStatusReport(String methodName,Profile profile, boolean okAction,
			Subscription subscription,
			PurchaseTransactionDetails purchaseTransaction,
			String transactionId, String profileId, Timestamp endDate,
			String actionType, String avsSequenceId,String statusMsg) {
		try {
			if (profile != null && okAction) {
				ReportUtilCA report = new ReportUtilCA();
				
				LogUtil.commonInfoLog(logger, "sendPurchaseStatusReport ", "LOG VALUES contentId | "+ contentId +" | contentType:"+ contentType +" | paymentType :"+paymentType);
				report.sendPurchaseStatusReport("purchaseStatus",methodName, 
												tenantConfigurator.getTenantId(), 
												profile.getCrmAccountId(), 
												"", 
												endDate, 
												channel, 
												contentId, 
												"", 
												paymentType, 
												String.valueOf(purchaseTransaction.getSequenceId()),
												transactionId, 
												String.valueOf(purchaseTransaction.getTransactionPrice()), 
												String.valueOf(subscription.getPrice()), 
												subscription.getCurrency(), 
												statusMsg, 
												contentType, 
												subscription.getRecurrencyDiscountedPrice().toString(),
												subscription.getRecurrencyPrice().toString(), 
												profileId);
			}
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3128_ERRSENDREPORTSYSLOG, e);

		}
	}
	
	protected void logPurchaseStatusInfoORErrorMsg(String methodName,String msg) {
		try 
		{
			if(profile != null)
			{	
				ReportUtilCA report = new ReportUtilCA();	
				
				LogUtil.commonInfoLog(logger, "sendPurchaseStatusReport", "LOG VALUES contentId | "+ contentId +" | contentType:"+ contentType +" | paymentType :"+paymentType);
				report.logPurchaseStatusInfoORErrorMsg(methodName,tenantConfigurator.getTenantId(),profile.getCrmAccountId(),channel,contentId,paymentType,contentType,msg);
			}	
		} catch (Exception e) {	
			
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3128_ERRSENDREPORTSYSLOG, e);
		}
	}

	
	// This method is used when price of the content is ZERO(0)
	private Object getListPkgTranforAppPurchase(Iterator<Object[]> offerBySolutionIterator, Offer[] arrayOffer, List<PurchaseTransactionDetails> listPurchaseTransaction, Long solutionOfferId, Long itemType, Long paymentTypeId, Long statusId, Timestamp startDate, Timestamp endDate, String tokenS, UserInfo userInfo) throws Exception {
		String methodName = "getListPkgTranforAppPurchase";
		Object[] lstPkgPurTran = new Object[2];

		// List<PurchaseTransaction> listPurchaseTransaction = null;
		List<AccountTechnicalPkg> listAccountTechnicalPkg = null;

		// listPurchaseTransaction = new Vector<PurchaseTransaction>();
		listAccountTechnicalPkg = new Vector<AccountTechnicalPkg>();

		while (offerBySolutionIterator.hasNext()) {
			Object[] currRec = offerBySolutionIterator.next();

			// Long solutionOfferID = (Long)currRec[2];
			String externalID = (String) currRec[19]; // sdp_offer_view.externalID

			Long numExtId = null;
			try {
				numExtId = this.getExternalIDNumber(externalID);
			} catch (Exception e) {
				LogUtil.commonInfoLog(logger, methodName, "externalID (by sdp_offer_view) not valid, -CRMACCOUNTID:"+userInfo.getCrmAccountId()+";");
				
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), null, String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}

			// retrieve the offer bean from array offer
			Offer offer = this.getOfferById(numExtId, arrayOffer);

			if (offer == null) {
				LogUtil.commonInfoLog(logger, methodName, "externalID number (by sdp_offer_view) not matching with data retrieved by PaymentGatewayUtil component, -CRMACCOUNTID:"+userInfo.getCrmAccountId()+";");
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR), null, String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}

			// setting the offer data in PurchaseTransaction bean
			PurchaseTransactionDetails purchaseTransactionOffer = setDataPurchaseTransactionforAppPurchase(solutionOfferId, numExtId, itemType, paymentTypeId, statusId, offer.getCurrency(), offer.getPrice(), offer.getDiscountedPrice(), endDate, tokenS, offer.getRecurrencyDiscountedPrice(), offer.getRecurrencyPrice(), userInfo);
			listPurchaseTransaction.add(purchaseTransactionOffer);

			// setting time of endDato to 23:59.59
			/*Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
			Timestamp endDateAtMidnight = null;
			if (endDate != null) {
				LogUtil.commonInfoLog(logger, methodName, "END DATE OF THE SUBSCRIPTION IN ACC TECH PKG BEFORE : "+endDate+" ;");
				c.setTimeInMillis(endDate.getTime());
				String endTimeForPurchase = constantsParameter.PURCHASE_END_TIME;
				if (endTimeForPurchase == null || endTimeForPurchase.isEmpty())
					endTimeForPurchase = "23:59:59";

				String[] tmp = endTimeForPurchase.split(":");
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmp[0]));
				c.set(Calendar.MINUTE, Integer.parseInt(tmp[1]));
				c.set(Calendar.SECOND, Integer.parseInt(tmp[2]));
				endDateAtMidnight = new java.sql.Timestamp(c.getTimeInMillis());
				LogUtil.commonInfoLog(logger, methodName, "END DATE OF THE SUBSCRIPTION IN ACC TECH PKG AFTER : "+endDateAtMidnight+" ;");
			}*/
			// setting the data in AccountTechnicalPkg bean
			AccountTechnicalPkg accountTechnicalPkgOffer = setDataAccountTechnicalPkgforAppPurchase(userInfo.getUserId(), numExtId, userInfo.getCrmAccountId(), startDate, endDate);
			listAccountTechnicalPkg.add(accountTechnicalPkgOffer);

		}

		lstPkgPurTran[0] = listAccountTechnicalPkg;
		lstPkgPurTran[1] = listPurchaseTransaction;

		return lstPkgPurTran;
	}
	
	 /**
	 * 
	 * @param solutionOfferId
	 * @param externalID
	 * @param itemType
	 * @param paymentTypeId
	 * @param statusId
	 * @param currency
	 * @param price
	 * @param discountedPrice
	 * @return
	 * @throws Exception
	 */
	private PurchaseTransactionDetails setDataPurchaseTransactionforAppPurchase(Long solutionOfferId, Long externalID, Long itemType, Long paymentTypeId, Long statusId, String currency, Double price, Double discountedPrice, Timestamp endDate, String tokenS, Double recurringDiscPrice, Double recurringOrigPrice, UserInfo userInfo) throws Exception {
		PurchaseTransactionDetails purchaseTran = new PurchaseTransactionDetails();
		Long itemId = null;
		Long parentItenId = null;

		if (externalID == null) {
			itemId = solutionOfferId;
			parentItenId = null;
		} else {
			itemId = externalID;
			parentItenId = solutionOfferId;
		}

		purchaseTran.setItemId(String.valueOf(itemId));
		purchaseTran.setParentItemId(parentItenId);

		purchaseTran.setUserInfo(userInfo);

		//String transactionId = null;  commented as we receive the receipt id for the app packages.
		
		//receipt id is added as part of the new CR in the 2nd season. This is only applicable to APP packages and not a mandatory one.
		String transactionId = receiptID; 
		purchaseTran.setTransactionId(transactionId);

		purchaseTran.setCurrency(currency);
		purchaseTran.setOriginalPrice(String.valueOf(price));
		purchaseTran.setTransactionPrice(String.valueOf(discountedPrice));

		PurchaseType purchaseType = new PurchaseType();
		purchaseType.setItemType(itemType);
		purchaseTran.setItemType(purchaseType);

		purchaseTran.setPaymentTypeId(paymentTypeId);

		StatusType state = new StatusType();
		state.setStatusId(statusId);
		//state.setStatusName(actionType);
		//state.setStatusDescription(actionType);
		purchaseTran.setState(state);

		Date now = new Date();
		Timestamp currentTimestamp = new Timestamp(now.getTime());

		purchaseTran.setStartDate(currentTimestamp);

		// Timestamp endDate = new Timestamp(now.getTime());
		// Timestamp endDate = null;
		purchaseTran.setEndDate(endDate);

		purchaseTran.setRecurringTransactionPrice(String.valueOf(recurringDiscPrice));
		purchaseTran.setRecurringOriginalPrice(String.valueOf(recurringOrigPrice));

		purchaseTran.setToken(tokenS);

		return purchaseTran;
	}

	/**
	 * 
	 * @param userId
	 * @param techPackageId
	 * @param crmAccountId
	 * @return
	 * @throws Exception
	 */
	private AccountTechnicalPkg setDataAccountTechnicalPkgforAppPurchase(Long userId, Long techPackageId, String crmAccountId, Timestamp startPeriod, Timestamp validityPeriod) throws Exception {
		AccountTechnicalPkg accountTechnicalPkg = new AccountTechnicalPkg();
		AccountTechnicalPkgPK compId = new AccountTechnicalPkgPK();
		compId.setUserId(userId);
		compId.setTechPackageId(String.valueOf(techPackageId));
		accountTechnicalPkg.setCompId(compId);
		accountTechnicalPkg.setViewsNumber(null);
		accountTechnicalPkg.setTechPackageValue("S");
		accountTechnicalPkg.setCreationDate(startPeriod);
		accountTechnicalPkg.setValidityPeriod(validityPeriod);
		accountTechnicalPkg.setCrmAccountId(crmAccountId);

		return accountTechnicalPkg;
	}	
	
	private String amountInCents(Double amount){
		Double amtInCents = amount * 100;
		 int i = amtInCents.intValue();
		return String.valueOf(i);
	}
	
	private String DoubletoString(Double d) {
		if(d!=null)
			return BigDecimal.valueOf(d).toString();
		else
			return String.valueOf(d);
	}
	
	private boolean isPurchaseTransactioninPanic(){
		String methodName="isPurchaseinPanic";
		LogUtil.commonInfoLog(logger, methodName,"PURCHASE_TRANSACTION_IN_PANIC VALUE : " + constantsParameter.get(WebConstants.PURCHASE_TRANSACTION_IN_PANIC)+";");
		
		if(constantsParameter.get(WebConstants.PURCHASE_TRANSACTION_IN_PANIC).equalsIgnoreCase("Y")) {
				LogUtil.commonInfoLog(logger, methodName,"PURCHASE TRANSACTION IS IN PANIC");
				return true;
		}
		return false;
	}
	
	private boolean isBPointinPanic(){
		String methodName="isBPointinPanic";
		LogUtil.commonInfoLog(logger, methodName,"BPOINT_IN_PANIC VALUE : " + constantsParameter.get(WebConstants.BPOINT_IN_PANIC)+";");
		
		if(constantsParameter.get(WebConstants.BPOINT_IN_PANIC).equalsIgnoreCase("Y")) {
				LogUtil.commonInfoLog(logger, methodName,"BPOINT IS IN PANIC");
				return true;
		}
		return false;
	}
	private void validateParameter(String name,String value) throws ActionException
	{
		LogUtil.commonInfoLog(logger, "validateParameter","paramName="+name+" ParamValue="+value);
		logPurchaseStatusInfoORErrorMsg("validateParameter","paramName="+name+" ParamValue="+value);
		if(value == null)
		{
			throw new ActionException(
					systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER
							+ "|" +name);
		}
		if (value.trim().equals(""))
		{
			throw new ActionException(
					systemMessages.ERROR_BE_ACTION_3019_INVALID_PARAMETER
							+ "|"+name+" & Value = "+value);
		}
	}
	
	/**
	 * 
	 * @param purchaseDate
	 * @return
	 * @throws Exception
	 * 
	 * EpochToUTCconverter to convert epoc date format into UTC timeformat.
	 * 
	 */
	private Timestamp epochToUTCConverterTimeStamp(String purchaseDate) throws Exception{
		
			try{
				Date date = new Date(Long.valueOf(purchaseDate));
		        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		        String formatted = format.format(date);
		        //System.out.println("UTC formatted = "+formatted);
		        
		        format.setTimeZone(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));//("Australia/Sydney"));
		        formatted = format.format(date);
		        
		        //System.out.println("Refernce timezone= "+format.getTimeZone().getDisplayName()+" - "+formatted);
		        Date newDate = format.parse(formatted);
		        
		        Timestamp ts = new java.sql.Timestamp(newDate.getTime());
		        
		        //System.out.println("Date="+ts.getDay() +" month=" +ts.getMonth() +" Year="+ts.getYear()+" Minutes="+ts.getMinutes()+" Hours="+ts.getHours() +" Seconds="+ts.getSeconds());
		        return ts;
	        
			}catch (Exception e) {
				LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR+"| MSG:", e);
				
					throw new ActionException(systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR + " - purchaseDate= "+purchaseDate);
			}
		}
    private String checkForBTPurchase(PurchaseTransaction purchaseTransaction) throws Exception {
    	String methodName="checkBtPurchase";
    	String status = Constants.OK;
    	java.util.Date date = new java.util.Date();
    	try {
    	if(purchaseTransaction != null && purchaseTransaction.getTransactionId() != null
    			&& !purchaseTransaction.getTransactionId().trim().equals("") && purchaseTransaction.getEndDate().after(date) ) {
			if (profile.getUserId().equals(purchaseTransaction.getUser().getUserId())){ // content already bought by the same user
				
				logPurchaseStatusInfoORErrorMsg(methodName,"BT--Content Already Bought with the TransactionID and Subscription is Active "+receiptID);
				loggerDebugEnabled(methodName, "BT--Content Already Bought with the TransactionID and Subscription is Active"+receiptID);
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_3150_ERROR_CONTEN_ALREADY_BOUGHT), null, String.valueOf(systemTime));
				status = formatter.bean2JSON(resultObjToJson).toString();
			}else{

				logPurchaseStatusInfoORErrorMsg(methodName,"BT-- Content Already Bought by different user with the TransactionID and Subscription is Active"+receiptID);
				loggerDebugEnabled(methodName, "BT-- Content Already Bought by different user with the TransactionID and Subscription is Active"+receiptID);
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_4101_ERROR_CONTEN_ALREADY_BOUGHT_BY_DIFFERENT_USER)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_4101_ERROR_CONTEN_ALREADY_BOUGHT_BY_DIFFERENT_USER)), null, String.valueOf(systemTime));
				status =  formatter.bean2JSON(resultObjToJson).toString();
			}
		
		}else{
			status = Constants.OK;
		}
    	}catch (Exception e){
    		LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR+"| MSG:", e);
			
			throw new ActionException(systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR + " - purchaseDate= "+purchaseDate);
    	}
		return status;
		
    }
    
    private boolean checkForBTPackageIds() throws Exception {
    	boolean statusFlag = false;
    	String packageIds = constantsParameter.get(WebConstants.BT_PACKAGE_IDS);
    	List<String> packageList = Arrays.asList(packageIds.replace(" ","").split("\\s*,\\s*"));
    	
    	try{
			if (packageList != null && packageList.size() > 0 && packageIds.trim().length() > 0) {

				if (packageList.contains(contentId)) {
					statusFlag = true;
				}

			}
		} catch (Exception e) {
			LogUtil.errorLog(logger,systemMessages.ERROR_BE_ACTION_3128_ERRSENDREPORTSYSLOG, e);
			statusFlag = false;
		}
    	
    	
		return statusFlag;
    	
    }


}
