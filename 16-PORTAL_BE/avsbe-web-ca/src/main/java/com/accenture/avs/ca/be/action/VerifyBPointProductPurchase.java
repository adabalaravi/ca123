package com.accenture.avs.ca.be.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.accenture.avs.be.component.Offer;

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
import com.accenture.avs.be.db.dao.AccountTechPackageDAO;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.ReportUtil;
import com.accenture.avs.be.db.util.ValidatorUtils;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.formatter.GetProductPurchaseDetailsFormatter;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.response.ProductPurchaseResponse;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.be.util.DynamicTextUtil;
import com.accenture.avs.be.util.ErrorMessageFormatterUtil;
import com.accenture.avs.ca.be.bean.BPointClientResult;
import com.accenture.avs.ca.be.bean.BPointPaymentResponseBean;
import com.accenture.avs.ca.be.dao.SolutionOfferinclDurationDAO;
import com.accenture.avs.ca.be.db.bean.SolutionOfferinclDurationDetailsBean;
import com.accenture.avs.ca.be.gateway.BPointPurchaseGateway;
import com.accenture.avs.ca.be.util.CAConstants;
import com.accenture.avs.ca.be.util.ReportUtilCA;
import com.accenture.avs.ca.be.util.SendMailGateway;
import com.accenture.avs.ca.be.util.WebConstants;

/**
 * @author h.kumar.satkuri
 *
 */
public class VerifyBPointProductPurchase extends GenericAction {
	private static Logger logger = Logger.getLogger(VerifyBPointProductPurchase.class);
	private String channel;
	protected String contentType;
	protected String contentId;
	protected String paymentType;

	private String in_request_resp_code;
	private String in_verify_token;
	private String in_amount;
	private String in_billercode;
	private String in_merchant_reference;
	private String in_crn1;
	private String in_crn2;
	private String in_crn3;
	private String in_response_code;
	private String in_bank_response_code;
	private String in_auth_result;
	private String in_txn_number;
	private String in_receipt_number;
	private String in_settlement_date;
	private String in_expiry_date;
	private String in_account_number;
	private String in_payment_date;
	private String in_store_card;
	
	private HttpSession sessionHttp = null;
	private String jsonOutput = "";
	private Object resultObjToJson = null;
	private GetProductPurchaseDetailsFormatter formatter;
	private BPointPaymentResponseBean paymentResponseBean=null;
	
	private Profile profile = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5115926267711178401L;

	public VerifyBPointProductPurchase(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
	}

	public void validateRequest() throws ActionException {
		try {
			
			sessionHttp = request.getSession();
			
			profile = (Profile) sessionHttp.getAttribute(Constants.USERPROFILE);
			ValidatorUtils.checkParameter(Constants.USERPROFILE, profile, tenantConfigurator);
			if (profile.getCrmAccountId().equalsIgnoreCase(Constants.ROWID_DEFAULT)) {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER + Constants.USERPROFILE);
			}
			
			channel = this.request.getParameter(Constants.CHANNEL); // mandatary
			ValidatorUtils.checkParameter(Constants.CHANNEL, channel, tenantConfigurator);
			
			in_request_resp_code = this.request.getParameter(WebConstants.IN_REQUEST_RESP_CODE); // mandatary;
			//ValidatorUtils.checkParameter(WebConstants.IN_REQUEST_RESP_CODE, in_request_resp_code, tenantConfigurator);
			
			in_verify_token = this.request.getParameter(WebConstants.IN_VERIFY_TOKEN); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_VERIFY_TOKEN, in_verify_token, tenantConfigurator);
			
			in_amount = this.request.getParameter(WebConstants.IN_AMOUNT); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_AMOUNT, in_amount, tenantConfigurator);
			
			in_billercode = this.request.getParameter(WebConstants.IN_BILLERCODE); // mandatary;
			//ValidatorUtils.checkParameter(WebConstants.IN_BILLERCODE, in_billercode, tenantConfigurator);
			
			in_merchant_reference = this.request.getParameter(WebConstants.IN_MERCHANT_REFERENCE); // mandatary;
			//ValidatorUtils.checkParameter(WebConstants.IN_MERCHANT_REFERENCE, in_merchant_reference, tenantConfigurator);
			
			in_crn1 = this.request.getParameter(WebConstants.IN_CRN1); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_CRN1, in_crn1, tenantConfigurator);
			
			in_crn2 = this.request.getParameter(WebConstants.IN_CRN2); // mandatary;
			//ValidatorUtils.checkParameter(WebConstants.IN_CRN2, in_crn2, tenantConfigurator);
			
			in_crn3 = this.request.getParameter(WebConstants.IN_CRN3); // mandatary;
			//ValidatorUtils.checkParameter(WebConstants.IN_CRN3, in_crn3, tenantConfigurator);
			
			in_response_code  = this.request.getParameter(WebConstants.IN_RESPONSE_CODE); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_RESPONSE_CODE, in_response_code, tenantConfigurator);
			
			in_bank_response_code  =this.request.getParameter(WebConstants.IN_BANK_RESPONSE_CODE); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_BANK_RESPONSE_CODE, channel, tenantConfigurator);
			
			in_auth_result = this.request.getParameter(WebConstants.IN_AUTH_RESULT); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_AUTH_RESULT, in_auth_result, tenantConfigurator);
			
			in_txn_number = this.request.getParameter(WebConstants.IN_TXN_NUMBER); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_TXN_NUMBER, in_txn_number, tenantConfigurator);
			
			in_receipt_number = this.request.getParameter(WebConstants.IN_RECEIPT_NUMBER); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_RECEIPT_NUMBER, in_receipt_number, tenantConfigurator);
			
			in_settlement_date = this.request.getParameter(WebConstants.IN_SETTLEMENT_DATE); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_SETTLEMENT_DATE, in_settlement_date, tenantConfigurator);
			
			in_expiry_date = this.request.getParameter(WebConstants.IN_EXPIRY_DATE); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_EXPIRY_DATE, in_expiry_date, tenantConfigurator);
			
			in_account_number = this.request.getParameter(WebConstants.IN_ACCOUNT_NUMBER); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_ACCOUNT_NUMBER, in_account_number, tenantConfigurator);
			
			in_payment_date = this.request.getParameter(WebConstants.IN_PAYMENT_DATE); // mandatary;
			ValidatorUtils.checkParameter(WebConstants.IN_PAYMENT_DATE, in_payment_date, tenantConfigurator);
	
			in_store_card = this.request.getParameter(WebConstants.IN_STORE_CARD); // mandatary;
			//ValidatorUtils.checkParameter(WebConstants.IN_STORE_CARD, in_store_card, tenantConfigurator);
			
			//Post Nullchecks set the values into BPointPaymentResponseBean
			
			  paymentResponseBean = new BPointPaymentResponseBean();
			  paymentResponseBean.setRequest_resp_code(in_request_resp_code);
			  paymentResponseBean.setVerify_token(in_verify_token);
		      paymentResponseBean.setAmount(in_amount);
		      paymentResponseBean.setBillercode(in_billercode);
		      paymentResponseBean.setMerchant_reference(in_merchant_reference);
		      paymentResponseBean.setCrn1(in_crn1);
		      paymentResponseBean.setCrn2(in_crn2);
		      paymentResponseBean.setCrn3(in_crn3);
		      paymentResponseBean.setResponse_code(in_response_code);
		      paymentResponseBean.setBank_response_code(in_bank_response_code);
		      paymentResponseBean.setAuth_result(in_auth_result);
		      paymentResponseBean.setTxn_number(in_txn_number);
		      paymentResponseBean.setReceipt_number(in_receipt_number);
		      paymentResponseBean.setSettlement_date(in_settlement_date);
		      paymentResponseBean.setExpiry_date(in_expiry_date);
		      paymentResponseBean.setAccount_number(in_account_number);
		      paymentResponseBean.setPayment_date(in_payment_date);
		      paymentResponseBean.setStore_card(in_store_card);
			
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR, e);
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
	@SuppressWarnings({ "unchecked", "null" })
	public Object executeRequest() throws ActionException {
		String methodName = "executeRequest";
		formatter = new GetProductPurchaseDetailsFormatter(tenantConfigurator);
		Subscription subscription = null;
		Long itemType = null;
		String transactionId = "";
		Long userId =null;
		UserInfo userInfo = null;
		Timestamp tmstEndDate = null;
		Timestamp tranStartDate = null;
		
		PurchaseTransactionDetails tran = null;
		String customerID=null;
		UserDAO uDAO = null;
		
		logPurchaseStatusInfoORErrorMsg(methodName,"STARTED PaymentVerification Process.....");
		BPointClientResult bPointClientResult = new BPointClientResult();
		try {
			if(this.isPurchaseTransactioninPanic()) {
				logPurchaseStatusInfoORErrorMsg(methodName, "Purchase Transaction is in Panic");
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.OK, "","", "PURCHASE TRANSACTION IS IN PANIC", String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}
			
			LogUtil.commonInfoLog(logger, methodName,"");
			
			// retrieve from factory an instance of PurchaseDAO   
			PurchaseDAO pDAO = AvsDaoFactory.getPurchaseDAO(tenantConfigurator);
			uDAO = AvsDaoFactory.getUserDAO(tenantConfigurator);

			List<Object[]> listOfferBySolution = null;

			List<PurchaseTransactionDetails> listPurchaseTransaction = new Vector<PurchaseTransactionDetails>();
			List<AccountTechnicalPkg> listAccountTechnicalPkg = new Vector<AccountTechnicalPkg>();

			Long paymentTypeId = null;
			Long statusId = null;
			String totalAmount = null;

		
			// BPointPaymentResponseBean here
			String transactionRefNo = paymentResponseBean.getReceipt_number();
			customerID = getSequenceIdfromCRN(paymentResponseBean.getCrn1());  // CustomerID is the SequenceId/OrderId
			
			LogUtil.commonInfoLog(logger, methodName,"customerID:"+customerID+"; Transaction Ref. No.:"+paymentResponseBean.getReceipt_number());
			logPurchaseStatusInfoORErrorMsg(methodName, "customerID:"+customerID+"; Transaction Ref. No.:"+paymentResponseBean.getReceipt_number());
			
			try { // customerID is the SequenceId which is part of CRN
				tran = pDAO.getPurchaseTransactionBySeqId(Long.valueOf(customerID), tenantConfigurator);
				if(tran == null)
					return sendErrorResponse(systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID), systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID));
			} catch(DAOException e) {
				LogUtil.commonInfoLog(logger, methodName, WebConstants.ERR_ORDERID_NOT_VALID+" | customerID:"+customerID+";");
				
				logPurchaseStatusInfoORErrorMsg(methodName, "ERROR="+systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID));
				logPurchaseStatusInfoORErrorMsg(methodName, "Actual ERROR="+e.getMessage());
				
				return sendErrorResponse(systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID), systemMessages.get(WebConstants.ERR_ORDERID_NOT_VALID));
			}
			
			// Check the status before processing the transaction, if status is completed then return transaction is already processed
			String statusName = tran.getState().getStatusName();

			if (statusName.equalsIgnoreCase(WebConstants.COMPLETED)) {
				LogUtil.commonInfoLog(logger, methodName, WebConstants.INFO_TRAN_COMPLETED+" | customerID:"+customerID+";");				
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
			
			transactionId = transactionRefNo; // this can be the receipt number from BPointPaymentResponse Bean
			
			
			subscription = (Subscription) pDAO.getProductPrice(solutionOfferId, userId, tenantConfigurator);

			paymentType = WebConstants.PAYMENT_PGW_BPOINT;
			// Verify the Payment with BPoint
			
			if(this.isBPointinPanic()) {
				logPurchaseStatusInfoORErrorMsg(methodName, "BPoint is in Panic");
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.OK, "","", "BPOINT IS IN PANIC", String.valueOf(systemTime));
				return formatter.bean2JSON(resultObjToJson).toString();
			}
			
			BPointPurchaseGateway bPointPurchaseGateway = new BPointPurchaseGateway();
			bPointClientResult = (BPointClientResult) bPointPurchaseGateway.verifyPaymentBPoint(paymentResponseBean,tenantConfigurator);
			
			sendPurchaseStatusReport(methodName,profile, true, subscription, tran, transactionId, "", tmstEndDate, "", String.valueOf(tran.getSequenceId()),"PaymentVerification In Progress");
			
			if(bPointClientResult.getOut_Result_Code().equalsIgnoreCase(Constants.OK)) // if the resultCode is OK 
			{				
				LogUtil.commonInfoLog(logger, methodName,"Payment Verification is successful | customerID:"+customerID+";");
				logPurchaseStatusInfoORErrorMsg(methodName, "BPoint Verification is successful And AVS BE is in Progress | customerID:"+customerID+";");
				try {
					String payerId = tran.getPayerId();

					tran.setTransactionId(transactionRefNo);
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
					// calculate endDate
					/*Calendar today = Calendar.getInstance();
					if (subscription.getFrequencyDays() > 0) {
						today.add(Calendar.DAY_OF_MONTH, subscription.getFrequencyDays().intValue());
						tmstEndDate = new Timestamp(today.getTimeInMillis());
						tran.setEndDate(tmstEndDate);
					} 		
					else {*/
					
						Timestamp endDateofSolOffer = null;
						endDateofSolOffer = subscription.getEndDate();
										
						// Set StartDate to the StartDate of PT if null
						if(subscription.getStartDate() == null) {
							LogUtil.commonInfoLog(logger, methodName,"Start Date of the subscription IS NULL");
							// subscription.setStartDate(tran.getStartDate());
							subscription.setStartDate(new Timestamp(System.currentTimeMillis())); // Changes
							LogUtil.commonInfoLog(logger, methodName,"Start Date of the subscription : "+subscription.getId() +" ------ "+subscription.getStartDate());
							logPurchaseStatusInfoORErrorMsg(methodName, "Start Date of the subscription : "+subscription.getId() +" ------ "+subscription.getStartDate());
						}
						if(subscription.getEndDate() != null) {
							LogUtil.commonInfoLog(logger, methodName,"Subscription EndDate  : "+subscription.getId() +" ------ "+subscription.getEndDate());
							logPurchaseStatusInfoORErrorMsg(methodName, "Subscription EndDate  : "+subscription.getId() +" ------ "+subscription.getEndDate());
							tmstEndDate = subscription.getEndDate(); //Subscription Enddate fetched from sdp_solution_offer_view enddate
							tran.setEndDate(tmstEndDate);
						}
						// Check for the Duration & set endDate based on Duration
						SolutionOfferinclDurationDAO solutionOfferinclDurationDAO = new SolutionOfferinclDurationDAO();
						SolutionOfferinclDurationDetailsBean solutionOfferinclDurationDetailsBean = solutionOfferinclDurationDAO.getDurationofSolutionOffer(subscription.getId(), tenantConfigurator);

						Timestamp endDatewithDuration = null;
						if(solutionOfferinclDurationDetailsBean.getDuration()!=null && solutionOfferinclDurationDetailsBean.getDuration() > 0) { 
							Long duration = solutionOfferinclDurationDetailsBean.getDuration();
							LogUtil.commonInfoLog(logger, methodName,"Start Date and DURATION : "+subscription.getId() +" ------ "+subscription.getStartDate() +"---------"+duration.intValue() +";");
							logPurchaseStatusInfoORErrorMsg(methodName, "Start Date and DURATION : "+subscription.getId() +" ------ "+subscription.getStartDate() +"---------"+duration.intValue() +";");
							Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
							c.setTimeInMillis(subscription.getStartDate().getTime());
							// c.setTimeInMillis(new Timestamp(System.currentTimeMillis())); // Change Not needed here
							c.add(Calendar.DAY_OF_WEEK, duration.intValue());
							tmstEndDate = new java.sql.Timestamp(c.getTimeInMillis());
							tran.setEndDate(tmstEndDate);
							endDatewithDuration = new java.sql.Timestamp(c.getTimeInMillis());
							LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration : "+subscription.getId() +" ------ "+endDatewithDuration+";");
							logPurchaseStatusInfoORErrorMsg(methodName, "EndDate of subscription after adding duration : "+subscription.getId() +" ------ "+endDatewithDuration+";");
						}
						
						if(endDateofSolOffer!=null && !endDateofSolOffer.equals("") && endDatewithDuration!=null){
							if(endDatewithDuration.after(endDateofSolOffer)) {
								LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration is after EndDate of Solution Offer");
								LogUtil.commonInfoLog(logger, methodName,"EndDate of subscription after adding duration --- "+endDatewithDuration+" --- EndDate of Solution Offer --- "+endDateofSolOffer+";");
								logPurchaseStatusInfoORErrorMsg(methodName,"EndDate of subscription after adding duration is after EndDate of Solution Offer --- "+endDatewithDuration+" --- EndDate of Solution Offer --- "+endDateofSolOffer+";");
								tmstEndDate = endDateofSolOffer;
								tran.setEndDate(tmstEndDate);
							}
						}
						
					Object lstPkgPurcTran = getListPkgTran(offerBySolutionIterator, arrayOffer, listPurchaseTransaction, solutionOfferId, itemType, paymentTypeId, statusId, subscription.getStartDate(), tmstEndDate, token, userInfo);
					
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

					if (lstPurchaseTrans == null && lstPurchaseTrans.size() == 0) {
						StatusType stateError = new StatusType();
						stateError.setStatusId(2L);
						tran.setState(stateError);
						tran.setPayerId(tran.getPayerId());
						pDAO.updatePurchaseTransaction(tran, tenantConfigurator);
						logPurchaseStatusInfoORErrorMsg(methodName, "ERROR = BPointClientResultCode -- KO -- errored while retrieving the purchase transaction by token: "+systemMessages.ERROR_BE_ACTION_3126_ERRPURCHASE);
						
						throw new Exception(systemMessages.ERROR_BE_ACTION_3126_ERRPURCHASE);

					}
					
					// Setting PT's SD & ED to be same as AC's creationDate and Validity Period
					tran.setStartDate(subscription.getStartDate());
					tran.setEndDate(tmstEndDate);
					
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
					
					/*if(purchaseState.equalsIgnoreCase("INPROGRESS")) {
						// Should be in pending state,Do not update any status till the record will older than configured value(48 hrs)
						long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
						resultObjToJson = getObjToJSon(Constants.OK, "", "Pending Status,Bill Desk is waiting for response from bank", "", String.valueOf(systemTime));
						jsonOutput = formatter.bean2JSON(resultObjToJson).toString();	
						return formatter.bean2JSON(resultObjToJson).toString();	
					}*/
						
					logPurchaseStatusInfoORErrorMsg(methodName, "BPointClientResultCode -- OK --  AND Saved/Updated the transaction in AVS_BE");

				} catch (Exception exp) {
					LogUtil.errorLog(logger, systemMessages.get(WebConstants.ERR_TRAN_PURCHASE_ERROR)+" | customerID:"+customerID+";", exp);
					logPurchaseStatusInfoORErrorMsg(methodName, "ERROR="+systemMessages.get(WebConstants.ERR_TRAN_PURCHASE_ERROR)+" | customerID:"+customerID+";");
					logPurchaseStatusInfoORErrorMsg(methodName, "Actual ERROR="+exp.getMessage()+" | customerID:"+customerID+";");
					AccountTechPackageDAO.deleteListAccountTechnicalPkg(listAccountTechnicalPkg, tenantConfigurator);	
					StatusType stateError = new StatusType();
					stateError.setStatusId(2L);
					tran.setState(stateError);
					tran.setPayerId(tran.getPayerId());
					pDAO.updatePurchaseTransaction(tran, tenantConfigurator);
					throw new Exception(exp.getMessage());

				}

			} else if (bPointClientResult.getOut_Result_Code().equalsIgnoreCase(Constants.KO)) {    // If Verification RESULT =  KO
				LogUtil.commonInfoLog(logger, methodName,"Error while Verification of Payment;");
				logPurchaseStatusInfoORErrorMsg(methodName, "Error while Payment Verification;");
					String payerId = tran.getPayerId();
					tran.setTransactionId(transactionRefNo);
					tran.setPgwStatus(WebConstants.ERROR);
					//tran.setEndDate(tmstEndDate);
					tran.setPayerId(payerId);
					tran.setCreationDate(tranStartDate);
					tran.setNotes("Error while Verification of Payment | "+bPointClientResult.getOut_Resp_code() +"|" + bPointClientResult.getOut_ErrorText());
					logPurchaseStatusInfoORErrorMsg(methodName, "Error while Verification of Payment | "+bPointClientResult.getOut_Resp_code() +"|" + bPointClientResult.getOut_ErrorText());
					
					if( ! bPointClientResult.getOut_Resp_code().equalsIgnoreCase(WebConstants.EMPTY_STRING)) {
						tran.setNotes("Error while Verification of Payment | "+bPointClientResult.getOut_Resp_code() +"|" + bPointClientResult.getOut_ErrorText());// Inserting the error description in NOTES column
					}
					
					StatusType status = new StatusType();
					status.setStatusId(2L);
					tran.setState(status);
					
					// retrieve purchase transaction by token
					List<PurchaseTransactionDetails> lstPurchaseTrans = new ArrayList<PurchaseTransactionDetails>();
					lstPurchaseTrans = pDAO.getOfferPurchaseTransactionByToken(token, tenantConfigurator);

					if (lstPurchaseTrans == null && lstPurchaseTrans.size() == 0) {
						StatusType stateError = new StatusType();
						stateError.setStatusId(2L);
						tran.setState(stateError);
						tran.setPayerId(tran.getPayerId());
						pDAO.updatePurchaseTransaction(tran, tenantConfigurator);
						logPurchaseStatusInfoORErrorMsg(methodName, "ERROR = BPointClientResultCode -- KO -- errored while retrieving the purchase transaction by token: "+systemMessages.ERROR_BE_ACTION_3126_ERRPURCHASE);
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
					
					logPurchaseStatusInfoORErrorMsg(methodName, "BPointClientResultCode -- KO --  AND Saved/Updated the transaction in AVS_BE");
				} 

			if (bPointClientResult.getOut_Result_Code().equals(Constants.KO)) {
				long systemTyme = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.KO, bPointClientResult.getOut_ErrorText(), "", "", String.valueOf(systemTyme));
				jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
				return formatter.bean2JSON(resultObjToJson).toString();
			} else {
				sendSubscriptionMail(subscription, tran, userInfo); //Send mail after successful subscription
				
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				resultObjToJson = getObjToJSon(Constants.OK, "", WebConstants.SUCCESS, "", String.valueOf(systemTime));
				jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
			}
			LogUtil.commonInfoLog(logger, methodName, "customerID:"+customerID+"; CHANNEL: " + channel + " CONTENT_ID: " + solutionOfferId + " PROFILE: " + userInfo != null ? userInfo.getCrmAccountId() : "");
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR+" | customerID:"+customerID, e);
			logPurchaseStatusInfoORErrorMsg(methodName, "ERROR="+systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR+" | customerID:"+customerID);
			logPurchaseStatusInfoORErrorMsg(methodName, "Actual ERROR="+e.getMessage() + "  | customerID:"+customerID);
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
			}
		} finally {			
			LogUtil.commonInfoLog(logger, methodName, "Writing to Purchase Report in finally");// On Successful Transaction

			if(bPointClientResult!=null) {				
			if (bPointClientResult.getOut_Result_Code() != null && bPointClientResult.getOut_Result_Code().equals(Constants.OK)) {
				LogUtil.commonInfoLog(logger, methodName, "Writing to Purchase Report");// On Successful Transaction
				sendReport(profile, true, subscription, String.valueOf(itemType), tran, transactionId, "", tmstEndDate, "", String.valueOf(tran.getSequenceId()));
				
				sendPurchaseStatusReport(methodName,profile, true, subscription, tran, transactionId, "", tmstEndDate, "", String.valueOf(tran.getSequenceId()),"PaymentVerification Completed");
			}
			}
		}

		return jsonOutput;
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
	private AccountTechnicalPkg setDataAccountTechnicalPkg(Long userId, Long techPackageId, String crmAccountId, Timestamp startPeriod, Timestamp validityPeriod) throws Exception {
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
	private PurchaseTransactionDetails setDataPurchaseTransaction(Long solutionOfferId, Long externalID, Long itemType, Long paymentTypeId, Long statusId, String currency, Double price, Double discountedPrice, Timestamp endDate, String tokenS, Double recurringDiscPrice, Double recurringOrigPrice, UserInfo userInfo) throws Exception {
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

		String transactionId = null;
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

	private Object getListPkgTran(Iterator<Object[]> offerBySolutionIterator, Offer[] arrayOffer, List<PurchaseTransactionDetails> listPurchaseTransaction, Long solutionOfferId, Long itemType, Long paymentTypeId, Long statusId, Timestamp startDate, Timestamp endDate, String tokenS, UserInfo userInfo) throws Exception {
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
			PurchaseTransactionDetails purchaseTransactionOffer = setDataPurchaseTransaction(solutionOfferId, numExtId, itemType, paymentTypeId, statusId, offer.getCurrency(), offer.getPrice(), offer.getDiscountedPrice(), endDate, tokenS, offer.getRecurrencyDiscountedPrice(), offer.getRecurrencyPrice(), userInfo);
			listPurchaseTransaction.add(purchaseTransactionOffer);

			/*// setting time of endDato to 23:59.59
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
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
			AccountTechnicalPkg accountTechnicalPkgOffer = setDataAccountTechnicalPkg(userInfo.getUserId(), numExtId, userInfo.getCrmAccountId(), startDate, endDate);
			listAccountTechnicalPkg.add(accountTechnicalPkgOffer);

		}

		lstPkgPurTran[0] = listAccountTechnicalPkg;
		lstPkgPurTran[1] = listPurchaseTransaction;

		return lstPkgPurTran;
	}
	
	private String sendErrorResponse(String errorDesc, String errMsg) throws Exception {		
		long systemTime = (long) (System.currentTimeMillis() / 1000);
		resultObjToJson = getObjToJSon(Constants.KO, errorDesc, errMsg, "", String.valueOf(systemTime));
		return jsonOutput = formatter.bean2JSON(resultObjToJson).toString();
	}
	private String getSequenceIdfromCRN(String crn){
		String sequenceId = null;

		for (int i = 0; i < crn.length(); i++) {
			char c = crn.charAt(i);
			if (c == '|') {
				// getting the string after the character '|'
				sequenceId = crn.substring(i + 1);
				break;
			}
		}
		return sequenceId;
	}
	
	/**
	 * 
	 * @param profile
	 * @param okAction
	 */
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
	
	/**
	 * Subscription Mail is sent to suer after successful subscription. 
	 * @param bpointPaymentResponseBean
	 * @param subscription
	 * @return SendMailResult
	 */
	private void sendSubscriptionMail(Subscription subscription, PurchaseTransactionDetails transaction, UserInfo userInfo){
		String methodName = "sendSubscriptionMail";
		LogUtil.commonInfoLog(logger, methodName, "Start");
		try {
			String packageName = nullCheck(subscription.getPackageName());
			HashMap<Integer, String> inputHash = loadMapper(packageName, transaction);
			new SendMailGateway(tenantConfigurator).sendMail(createInput(packageName, inputHash, userInfo));
			LogUtil.commonInfoLog(logger, methodName, CAConstants.SUBS_MAIL_SUCCESS + profile.getCrmAccountId() + CAConstants.CRN_1 + in_crn1);
		} catch (AddressException addressException) {
			LogUtil.errorLog(logger, CAConstants.SUBS_MAIL_FAILURE + profile.getCrmAccountId() + CAConstants.CRN_1 + in_crn1, addressException);
		} catch (MessagingException messagingException) {
			LogUtil.errorLog(logger, CAConstants.SUBS_MAIL_FAILURE + profile.getCrmAccountId() + CAConstants.CRN_1 + in_crn1, messagingException);
		} catch (PatternSyntaxException patternSyntaxException) {
			LogUtil.errorLog(logger, CAConstants.SUBS_MAIL_FAILURE + profile.getCrmAccountId() + CAConstants.CRN_1 + in_crn1, patternSyntaxException);
		} catch (IllegalStateException illegalStateException) {
			LogUtil.errorLog(logger, CAConstants.SUBS_MAIL_FAILURE + profile.getCrmAccountId() + CAConstants.CRN_1 + in_crn1, illegalStateException);
		} catch (NullPointerException nullPointerException){
			LogUtil.errorLog(logger, CAConstants.SUBS_MAIL_FAILURE + profile.getCrmAccountId() + CAConstants.CRN_1 + in_crn1, nullPointerException);
		}
		LogUtil.commonInfoLog(logger, methodName, "End");
	}

	/**
	 * It creates input object which is used to send mail. The data it contains is subject, mail content, to mail id. and from mail id. 
	 * @param inputHash
	 * @return input
	 */
	private String[] createInput(String packageName, HashMap<Integer, String> inputHash, UserInfo userInfo) {
		String[] input = new String[4];
		String emailContent = null;
		
		//This is to compare package name, if bought package type is membership then email template with out ACF details used 
		Pattern pattern = Pattern.compile(CAConstants.PATTERN_MEMEBSHIP, Pattern.CASE_INSENSITIVE); 
		Matcher matcher = pattern.matcher(packageName);
		
		if(matcher.find()){		
			emailContent = constantsParameter.get(WebConstants.SUBSCRIPTION_MAIL_TEXT_MEMBERSHIP);
		} else {
			emailContent = constantsParameter.get(WebConstants.SUBSCRIPTION_MAIL_TEXT);
		}
		
		input[0] = constantsParameter.get(WebConstants.SUBSCRIPTION_MAIL_SUBJECT);
		input[1] = DynamicTextUtil.generateDynamicText(replaceDynamicText(emailContent, userInfo), inputHash);
		input[2] = profile.getCrmAccountId();
		input[3] = constantsParameter.get(WebConstants.MAIL_FROM_ADDRESS_NO_REPLY);
		return input;
	}
	
	/**
	 * This method is used to format the mail content. Dynamic data is inserted into mail template.
	 * @param bpointPaymentResponseBean
	 * @param subscription
	 * @return inputHash
	 */
	private HashMap<Integer, String> loadMapper(String packageName, PurchaseTransactionDetails transaction) {
		HashMap<Integer, String> inputHash = new HashMap<Integer, String>();
		String startDateFormatted = null;
		String endDateFormatted = null;
		
		startDateFormatted = dateToString(transaction.getStartDate());
		endDateFormatted = dateToString(transaction.getEndDate());

		inputHash.put(1, nullCheck(packageName));
		inputHash.put(2, startDateFormatted);
		inputHash.put(3, endDateFormatted);
		inputHash.put(4, nullCheck(transaction.getOriginalPrice()));
		inputHash.put(5, nullCheck(transaction.getTransactionId()));
	
		return inputHash;
	}

	
	/**
	 * Checks whether string is empty or not. If empty returns true else false
	 * @param inputValue
	 * @return isEmpty
	 */
	private boolean isEmptyString(String inputValue){
		boolean isEmpty = Boolean.TRUE;
		if(inputValue != null && !inputValue.trim().isEmpty()){
			isEmpty = Boolean.FALSE;
		}
		return isEmpty;
	}
	
	/**
	 * Converts date value to string in the format dd/MM/yy HH:mm. If date is null then return empty string
	 * @param inputValue
	 * @return
	 */
	private String dateToString(Date inputValue){
		String returnValue = CAConstants.EMPTY_STRING;
		SimpleDateFormat outFormat = new SimpleDateFormat(CAConstants.SUBSCRIPTION_DATE_FORMAT);

		if(inputValue != null){
			returnValue = outFormat.format(inputValue);
		}
		return returnValue;
	}
	
	/**
	 * Null check - If  null return empty string
	 * @param inputValue
	 * @return returnString
	 */
	private String nullCheck(String inputValue){
		String returnString = CAConstants.EMPTY_STRING;
		if(inputValue != null){
			returnString = inputValue;
		}
		return returnString;
	}
	

	/**
	 * @param dbValue
	 * @param mapper
	 * @return generatedText
	 */
	private String replaceDynamicText(String dbValue, UserInfo userInfo) {
		String firstName = CAConstants.SUBSCRIBER;

		String generatedText = null;
		Pattern pattern = Pattern.compile(CAConstants.PATTERN);
		Matcher matcher = pattern.matcher(dbValue);
		if (matcher.find()) {
			String matchedString = matcher.group();

			if (userInfo != null && !isEmptyString(userInfo.getName())) {
				firstName = userInfo.getName();
			} else if (!isEmptyString(matchedString)) {
				firstName = matchedString.replaceAll(
						CAConstants.MAIL_SUBSCRIBER_TAG,
						CAConstants.EMPTY_STRING);

				if (isEmptyString(firstName)) {
					firstName = CAConstants.SUBSCRIBER;
				}
			}

			generatedText = matcher.replaceFirst(firstName);
		}

		return generatedText != null ? generatedText : dbValue;
	}
}
