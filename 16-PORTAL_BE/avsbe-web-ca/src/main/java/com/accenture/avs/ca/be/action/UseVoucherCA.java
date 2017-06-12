package com.accenture.avs.ca.be.action;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.bean.SdpVoucherView;
import com.accenture.avs.be.db.dao.SolutionOfferDAO;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.ValidatorUtils;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.formatter.TransactionOutputFormat;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.bean.Transaction;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.be.util.ErrorMessageFormatterUtil;
import com.accenture.avs.ca.be.dao.VoucherCADAO;
import com.accenture.avs.ca.be.util.CAConstants;
import com.accenture.avs.ca.be.util.WebConstants;


public class UseVoucherCA extends GenericAction {

	/**
	 * 
	 * This class verifies that the voucher code and bypass the captcha code logic based on SYS_PARAMETER values “Y/N”.
	 * for the user logged in and it gets the technical packages for the user to
	 * associate the day of validity of the voucher.
	 */
	private static Logger logger = Logger.getLogger(UseVoucherCA.class);
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request = null;
	private HttpSession sessionHttp = null;

	String captchaCode = null;
	String channel = null;
	String voucherID = null;
	

	/**
	 * A simple API Constructor.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	public UseVoucherCA(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
	}

	/**
	 * This method validate the input parameter of the UseVoucherCA API.
	 * 
	 * @param ConstantsParameter
	 *            .CAPTCHA_CODE, UserID, constantsParameter.VOUCHER_ID and
	 *            constantsParameter.CHANNEL
	 */
	@Override
	public void validateRequest() throws ActionException {
		try {
			sessionHttp = request.getSession();
			Profile profile = (Profile) sessionHttp.getAttribute(Constants.USERPROFILE);
			ValidatorUtils.checkParameter(Constants.USERPROFILE, profile, tenantConfigurator);
			//check that the user is logged
			if (profile == null || profile.getCrmAccountId().equalsIgnoreCase(Constants.ROWID_DEFAULT)) {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER + Constants.USERPROFILE);
			}
			// check captcha code in the request
			captchaCode = this.request.getParameter(Constants.CAPTCHA_CODE_REQ);
			// check voucherID code in the request
			voucherID = this.request.getParameter(Constants.VOUCHER_ID);
			if (voucherID == null ) {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER +" "+ Constants.VOUCHER_ID);
				// check the length not more than 15
			} else if (voucherID.equalsIgnoreCase("") || voucherID.trim().length() > 15) {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3019_INVALID_PARAMETER +" "+ Constants.VOUCHER_ID);
			}
			channel = this.request.getParameter(Constants.CHANNEL);
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR, e);
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR);
			}
		}
	}

	/**
	 * This method gets the technical packages for the user to associate the day
	 * of validity of the voucher. The technical packages already associated
	 * with the user are not changed.
	 * 
	 * @throws ActionException
	 * @return OK or KO
	 */
	@Override
	public Object executeRequest() throws ActionException {
		String methodName = "executeRequest";
		java.sql.Timestamp currentTimeStamp=null;
		
		/*This Captcha code validation & respective logic can be bypassed based on SYS_PARAMETER values “Y/N”.
		 Now Currently for this Optus requirement , this sys_parameter value will be “N”.
		 if voucher code is null,then setting sys_parameter value will be “Y”
		 */
		
		 String isCapatchaValidationRequired = constantsParameter.get(WebConstants.IS_CAPTCHA_VALIDATION_REQUIRED);
		 LogUtil.commonInfoLog(logger, methodName,"isCapatchaValidationRequired Value is : "+isCapatchaValidationRequired);
		 if(isCapatchaValidationRequired==null || isCapatchaValidationRequired.trim().equals("") ){
			 
			 isCapatchaValidationRequired="Y";
			 LogUtil.commonInfoLog(logger, methodName,"Captcha Code is null so setting the value Y ||"+isCapatchaValidationRequired);
		 }
		 if(isCapatchaValidationRequired!=null && isCapatchaValidationRequired.equalsIgnoreCase("Y") ){
			 
			if (captchaCode == null || captchaCode.equalsIgnoreCase("")) {
			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, " CAPTCHA CODE: " + captchaCode);
			}
			String errorMsg = ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER);
			String errorDesc = ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER);
			return getResponse(Constants.KO, errorMsg, errorDesc, null);
		}
		
			// check the captcha code in the session
		String captchaCodeOK = (String) sessionHttp.getAttribute(Constants.CAPTCHA_CODE);
				
		if (captchaCodeOK == null) {
			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, " CAPTCHA CODE NOT IN SESSION");
			}
			// CAPTCHA CODE IS NOT IN SESSION...RESPONSE WITH AN ERROR
			String errorMsg = ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_3100_CAPTCHA_NOT_IN_SESSION);
			String errorDesc = ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_3100_CAPTCHA_NOT_IN_SESSION);
			return getResponse(Constants.KO, errorMsg, errorDesc, null);

		}
		
		if (!captchaCode.equals(captchaCodeOK)) {
			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, " CAPTCHA CODE OK: " + captchaCodeOK);
				LogUtil.commonDebugLog(logger, methodName, " CAPTCHA CODE INSERED: " + captchaCode);
			}
			// WRONG CAPTCHA CODE...RESPONSE WITH AN ERROR
			String errorMsg = ErrorMessageFormatterUtil.setErrorMessage(systemMessages.ERROR_BE_ACTION_3099_WRONG_CAPTCHA_CODE);
			String errorDesc = ErrorMessageFormatterUtil.setErrorDescription(systemMessages.ERROR_BE_ACTION_3099_WRONG_CAPTCHA_CODE);
			return getResponse(Constants.KO, errorMsg, errorDesc, null);
		 }

		 }

		Profile profile = (Profile) sessionHttp.getAttribute(Constants.USERPROFILE);
		
		SdpVoucherView voucher = null;
		Transaction transaction = new Transaction();
		String output = "";
		try {
			//Fetching the voucher code from the DB
			voucher = VoucherCADAO.getVoucherID(this.request.getParameter(Constants.VOUCHER_ID), tenantConfigurator);
			
			//verify that the voucher code that exists or not,if voucher code is null then throws the exception
			if (voucher == null) {
				if (logger.isDebugEnabled()) {
					LogUtil.commonDebugLog(logger, methodName, "VOUCHER INSERED: " + voucher);
				}
				LogUtil.commonInfoLog(logger, methodName,"VOUCHER ID not existed:"+voucherID);
				transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_3163_VOUCHER_ID_NOT_EXIST);
				output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				return output;
			}
			
			LogUtil.commonInfoLog(logger, methodName,"Voucher Code-- :"+voucher.getVoucherCode()+"isCapatchaValidationRequired value is--:"+isCapatchaValidationRequired);
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone(constantsParameter.REFERENCE_TIMEZONE));
			currentTimeStamp=new java.sql.Timestamp(c.getTimeInMillis());
			//Checking that voucher code is expired or not,if voucher code is already expired then throws exception 
			LogUtil.commonInfoLog(logger, methodName,"Voucher EndDate: "+voucher.getSdpVoucherCampaignView().getEndDate() +"|| Current TimeStamp:"+currentTimeStamp);
			
			if(voucher.getSdpVoucherCampaignView().getEndDate().before(currentTimeStamp)){
				LogUtil.commonInfoLog(logger, methodName,"VOUCHER EXPIRED:"+voucher.getVoucherCode());
				transaction = this.returnTransaction(false, systemMessages.get(CAConstants.ERROR_BE_ACTION_4102_ACTION_VOUCHER_EXPIRED));				
				output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				return output;
			
			}
			
			// Checking voucher code that has not already been used.
			if (voucher.getPartyID() != null) {
				LogUtil.commonInfoLog(logger, methodName,"VOUCHER ID Already Used:"+voucherID);
				transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_3164_VOUCHER_ID_ALREADY_USED);
				output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				return output;

			}
			// revenue solutionOffer by voucherId
			Long solutionOffer = voucher.getSdpVoucherCampaignView().getSolutionOfferID();//voucher.getSolutionOfferID();
			LogUtil.commonInfoLog(logger, methodName,"Before Inserting Party ID : "+voucher.getPartyID() +" ||Solution Offer ID :"+solutionOffer);
			// revenue technical packages by solutionOffer
			List<String> techPacks = SolutionOfferDAO.getTechnicalPackageBySolutionOffer(solutionOffer, tenantConfigurator);
			if (techPacks == null) {
				LogUtil.commonInfoLog(logger, methodName,"VOUCHER ID cannot be used"+voucherID);
				transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_3165_VOUCHER_ID_CAN_NOT_BE_USED);
				output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				return output;
			}
			
			// insert row in purchaseTransaction
			try{
				VoucherCADAO.preparePurchaseTransactionByVoucher(profile, voucher, techPacks, solutionOffer, channel,tenantConfigurator);
			}catch(Exception e)
			{
				String exc = e.getCause().getMessage();
				
				if(exc.contains("ipn_tnx_id") && exc.contains("Duplicate")){
					
				LogUtil.commonInfoLog(logger, methodName,"ERROR - DUPLICATE TRANSACTION ------- :"+voucher.getVoucherCode()+" --  "+ e.getMessage());
				transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_3164_VOUCHER_ID_ALREADY_USED);
				 output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				return output;
				}else{
					throw new ActionException(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR);
				}
				
			}
			// association in the table AccountTechnicalPkg if I find it return
			// false, otherwise it returns true, and insert a row
			 boolean insered = VoucherCADAO.setVoucherToAccountTechPkg(profile, techPacks, voucher, tenantConfigurator);
			 if (!insered) {
				 LogUtil.commonInfoLog(logger, methodName,"ERROR AT -- TECHNICAL PACKAGE INSERTION"+voucherID);
			 transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_3164_VOUCHER_ID_ALREADY_USED);
			 output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
			 return output;
			 }
			// edit row of the SdpVoucherView with PartyID
			voucher.setPartyID(profile.getUserId());
			
			//inserting the user id into sdp_voucher table
			boolean ins = VoucherCADAO.insertPartyID(voucher, tenantConfigurator);
			if (ins) {
				transaction = this.returnTransaction(true, "");
				LogUtil.commonInfoLog(logger, methodName,"Set Voucher code to user sucessfully-- : "+voucher.getPartyID() +" ||Solution Offer ID :"+solutionOffer);
			}
			
			output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);

		} catch (Exception e) {
			if(e.getMessage().contains(Constants.VOUCHER_NOT_VALID)){
				LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3163_VOUCHER_ID_NOT_EXIST, e);
				transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_3163_VOUCHER_ID_NOT_EXIST);
				output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3163_VOUCHER_ID_NOT_EXIST);
			}else{
				LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR, e);
				transaction = this.returnTransaction(false, systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR);
				output = TransactionOutputFormat.bean2JSON(transaction, tenantConfigurator);
				throw new ActionException(systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR);
			}

		}
		return output;

	}

	/**
	 * Utility method for json return
	 * 
	 * @param okLogin
	 * @param systemMessagesError
	 * @return
	 */
	private Transaction returnTransaction(boolean okLogin, String systemMessagesError) {
		Transaction transaction = new Transaction();
		if (okLogin) {
			transaction.setResultCode(Constants.OK);
			transaction.setErrorDescription("");
			transaction.setMessage("");
			transaction.setResultObj("");
		} else {
			transaction.setResultCode(Constants.KO);
			transaction.setErrorDescription(ErrorMessageFormatterUtil.setErrorDescription(systemMessagesError));
			transaction.setMessage(ErrorMessageFormatterUtil.setErrorMessage(systemMessagesError));
			transaction.setResultObj("");
		}
		transaction.setSystemTime(System.currentTimeMillis() / 1000);
		return transaction;

	}
	
	}