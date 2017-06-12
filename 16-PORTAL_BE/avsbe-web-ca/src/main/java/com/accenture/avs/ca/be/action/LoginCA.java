package com.accenture.avs.ca.be.action;

import java.lang.reflect.Constructor;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.db.bean.AccountAttribute;
import com.accenture.avs.be.db.bean.AccountUser;
import com.accenture.avs.be.db.bean.AccountUserBean;
import com.accenture.avs.be.db.bean.CrmAccount;
import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.dao.AccountManagementDAO;
import com.accenture.avs.be.db.dao.ProfileDAO;
import com.accenture.avs.be.db.framework.ConstantsParameter;
import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.ReportUtil;
import com.accenture.avs.be.db.util.ValidatorUtils;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.formatter.TransactionOutputFormat;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.bean.Transaction;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.be.util.CookieUtil;
import com.accenture.avs.be.util.TransactionBuilder;
import com.accenture.avs.be.util.crypto.MD5Utils;
import com.accenture.avs.ca.be.bean.LoginCAResponseBean;
import com.accenture.avs.ca.be.util.CAConstants;
import com.accenture.avs.ca.be.util.SendMailGateway;
import com.accenture.avs.ca.be.util.WebConstants;


/**
 * @author s.attuluri
 * 
 * API - LoginCA
 * This is the entry point to the AVS application.
 * This API will return the user profile back to the FE by keeping it in a session. 
 *
 */
public class LoginCA extends GenericAction {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginCA.class);

	private HttpSession sessionHttp = null;
	
	private String email;
	private String signatureTimestamp;
	private String uid;
	private String uidSignature;
	private String channel;
	private boolean insertStatus = false;
	private boolean okLogin = false;
	private String deviceType = null;

	private String sessionToken;
	private String sessionSecret;
	
	private String defaultPassword;

	public LoginCA(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
	}
	
	public void validateRequest() throws ActionException {
		String methodName = "validateRequest";
		try {
			
			LogUtil.writeCommonDebugLog(logger, "internal", "entering "
					+ methodName);
			
			sessionHttp = request.getSession();
			
			email = request.getParameter(Constants.EMAIL);
			
			//validate channel
			channel = this.request.getParameter(Constants.CHANNEL);
			validateParameter(Constants.CHANNEL,channel);
			
			// validate signatureTimestamp
			signatureTimestamp = request.getParameter(CAConstants.SIGNATURETIMESTAMP);
			validateParameter(CAConstants.SIGNATURETIMESTAMP,signatureTimestamp);
			
			//validate UID
			uid= request.getParameter(CAConstants.UID);
			validateParameter(CAConstants.UID,uid);
					
			//validate email parameter
			if (email != null) {
				this.validateEMAILParameter(email);
			}else{
				throw new ActionException(
						systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER
								+ "|email");
			}
			
			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, "Input parameter - "
						+ email + " / " + channel + " / "
						+ signatureTimestamp + " / " + uid+ " / " + ""+" / " + "");
			}
			
			
			
		} catch (Exception e) {
			LogUtil.errorLog(logger,
					systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR,
					e);
			if (e instanceof ActionException) {
				throw (ActionException) e;
			} else {
				throw new ActionException(
						systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR);
			}// End if..else...
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.accenture.avs.be.framework.GenericAction#executeRequest()
	 * 
	 * Here in this method - as per the new CR received from the Client - Only the Social media users will login to web-site.
	 * 1. Removed all the logic related to Gigya invocations. As this is taken care by the FE team.
	 * 2. Removed Password related profile verification. As the password will not be sent to the AVS BE - we are saving the default password to all the users.
	 * 3. Removed the ForceRegistration logic - As this is not being sent by the FE.
	 * 4. Need to verify the existance of the user in the AVS BE - 
	 * 			if user profile exists return the profile to the FE
	 * 			if user profile exists - means - is a new user and need to create an account in the AVS BE and return the user profile to the FE.
	 * 
	 * 5. UIDSignature validation is required as part of the security check. 
	 *
	 *  
	 */
	public Object executeRequest() throws ActionException {
		
		String methodName = "executeRequest";
		Profile profile = null;// = new Profile();
		String ipAddress = ""; /* da verificare se arriva dall'header*/
		
		LogUtil.writeCommonDebugLog(logger, "internal", "entering "	+ methodName);

		String composizioneLog = "Login API PROCESS : [";
		
		try {
			String cookie="";	
				
			defaultPassword =  MD5Utils.getHashPassword("#!reset&#");
			ProfileDAO profileDao = new ProfileDAO(tenantConfigurator);
					
			//Checking the existence of the user with the provided email.
			LogUtil.writeCommonInfoLog(logger, methodName, "Checking the existence of the user with the provided email - "+email);
			CrmAccount crmAcc = AccountManagementDAO.getCrmAccount(email, tenantConfigurator);
			
			if (crmAcc == null) {
				//user isn't present in the system(AVS BE) then create a user with the provided email
				LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" - not existed in the AVS BE. And creating an account for this user." );
				boolean isSocialMedial = Boolean.TRUE; 
				composizioneLog +="New User - ";
				
				//creating an account with the given email id.
				crmAcc = createUser(crmAcc);
				
				composizioneLog +="Created new User - "+ crmAcc.getCrmaccountid();
				this.sendReport(crmAcc, ipAddress, insertStatus);

				// fetching the user profile to keep it in the session
				profile = profileDao.getProfile(crmAcc.getCrmaccountid(), tenantConfigurator);
				LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" - created and crm_account id and fetched the profile -"+profile.getCrmAccountId());
				
				//Send mail after successful registration	
				String mailPanicFlag = constantsParameter.get(WebConstants.MAIL_GATEWAY_PANIC_FLAG);
				LogUtil.writeCommonInfoLog(logger, methodName, "MAIL_GATEWAY_PANIC_FLAG - "+mailPanicFlag);
				if(WebConstants.NO.equalsIgnoreCase(mailPanicFlag))
					sendRegistrationMail(crmAcc, isSocialMedial); 
					
			}else{
				LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" is present in the AVS BE");
				// User is there in the system(AVS BE) - so profile will be returned to the FE.
				composizioneLog +="User Existing - "+crmAcc.getCrmaccountid();
				
				//fetching the user profile to cross verify and to keep it in the session and 
				profile = profileDao.getProfile(crmAcc.getCrmaccountid(), tenantConfigurator);
				LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" - fetched the user profile - ");
				
				if(profile == null ){
					
					if (logger.isDebugEnabled()) {
						LogUtil.commonDebugLog(logger, methodName, composizioneLog+" ]");
					}
					
					throw new ActionException(
							systemMessages.ERROR_BE_ACTION_3036_INCORRECT_USER_PASSWORD);
				}
				
				AccountUser au = AccountManagementDAO.getAccountUser(crmAcc.getCrmaccountid(), tenantConfigurator);
				manageStateUser(Integer.parseInt((au.getStatusType().getStatusId()+"")));
			}

			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, composizioneLog+"]");
			}
			
			if (logger.isDebugEnabled()) {
				LogUtil.commonDebugLog(logger, methodName, "Result LoginCA: OK - CRMACCOUNTID:" + profile.getCrmAccountId());
			}
			okLogin=true;
			return  outLoginCA_Action(profile, cookie);// TransactionOutputFormat.bean2JSON(this.getTransaction(), tenantConfigurator);
			
			
			} catch (Exception e) {
				okLogin = false;
				LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
	
				if (e instanceof ActionException) {
					throw (ActionException) e;
				} else {
					throw new ActionException(systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
				}
			} finally {
				this.sendReport(profile, okLogin);
			} 
		
	}
	
	
	/** 
	 * This method send, through log4j Appender, the Login report to syslog-ng
	 * server. 
	 * It will call when the operation is create a new User
	 * 
	 * @param CrmAccount
	 * @param okLogin
	 */
	private void sendReport(CrmAccount acc, String ipAddress, boolean okLogin) {
		try {
			if (acc != null && okLogin) {
				ReportUtil report = new ReportUtil();
				report.sendJoinReport("LoginCA", tenantId, acc.getCreationDate(), email, ipAddress, acc.getUsername());

			}
		} catch (Exception e) {
			LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3049_SEND_JOIN_REPORT_TO_SYSLOGNG, e);
		}
	}
	
	
	/**
	 * This method send, through log4j Appender, the Login report to syslog-ng
	 * server.
	 * It will call when the operation is Login
	 * 
	 * @param profile
	 * @param okLogin
	 */
	protected void sendReport(Profile profile, boolean okLogin) {
		// call ProfileDAO to checkLogin
		try {
			String methodName = "sendReport";
			String CLIENT = constantsParameter.CLIENT;
			
			if (profile != null && okLogin && sessionHttp != null) {
				String tmpChannel = "";
				if (this.deviceType != null && !this.deviceType.equals("")) {
					tmpChannel = this.deviceType;
				} else {
					tmpChannel = this.channel;
				}
				Class gatewayClass = null;
				Constructor costruttore = null;
				ReportUtil gateway;
				
				if (CLIENT != null) {
					
					String reportGatewayPath = "com.accenture.avs";
					String reportGatewayClassPath = "be.db.util.ReportUtil";
					
					String path = reportGatewayPath + "." + CLIENT + "."
							+ reportGatewayClassPath;
					LogUtil.commonDebugLog(logger, methodName,
							"gateway path : " + path);
					try {
						gatewayClass = Class.forName(path);
						costruttore = gatewayClass
								.getConstructor(new Class[] {});
						gateway = (ReportUtil) costruttore.newInstance();
						gateway.sendLoginReport(
								"login",
								tenantConfigurator.getTenantId(),
								profile.getCrmAccountId(),
								"",
								tmpChannel,
								(String) (sessionHttp
										.getAttribute(Constants.TRANSACTION_NUMBER)));
					} catch (Exception e) {
						LogUtil.errorLog(logger,
								"Error creating gateway for login reporting. ",
								e);
						com.accenture.avs.be.db.util.ReportUtil report = new com.accenture.avs.be.db.util.ReportUtil();
						report.sendLoginReport(
								"login",
								tenantConfigurator.getTenantId(),
								profile.getCrmAccountId(),
								"",
								tmpChannel,
								(String) (sessionHttp
										.getAttribute(Constants.TRANSACTION_NUMBER)));
					}
				} else {
					com.accenture.avs.be.db.util.ReportUtil report = new com.accenture.avs.be.db.util.ReportUtil();
					report.sendLoginReport(
							"login",
							tenantConfigurator.getTenantId(),
							profile.getCrmAccountId(),
							"",
							tmpChannel,
							(String) (sessionHttp
									.getAttribute(Constants.TRANSACTION_NUMBER)));
				}
			}
		} catch (Exception e) {
			LogUtil.errorLog(
					logger,
					systemMessages.ERROR_BE_ACTION_3048_SEND_LOGIN_REPORT_TO_SYSLOGNG,
					e);
		}
	}

	
	
	
	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	
	private AccountAttribute compositeAttribute(Integer attrId, String attrVal){
		AccountAttribute attr = new AccountAttribute();
		attr.setAttributeValue(attrVal);
		attr.setAttributeDetailId(attrId.longValue());
		return attr;
	}
	
	private ArrayList<AccountAttribute> getAccoutAttributes() {
		
		ArrayList<AccountAttribute> accAttrs = new ArrayList<AccountAttribute>();
		accAttrs.add(compositeAttribute(Constants.ATTR_ISQA, "N"));
		accAttrs.add(compositeAttribute(Constants.ATTR_CRM_ACC_TYPE, "1"));

		accAttrs.add(compositeAttribute(Constants.ATTR_PASSWORD, defaultPassword));
		accAttrs.add(compositeAttribute(Constants.REATAILER_DOMAIN, "0001"));

		accAttrs.add(compositeAttribute(Constants.ATTR_USER_PC_LEVEL_VOD, "3"));
		accAttrs.add(compositeAttribute(Constants.ATTR_USER_PC_LEVEL_EPG, "3"));
		accAttrs.add(compositeAttribute(Constants.ATTR_PC_FLAG, "1"));
		accAttrs.add(compositeAttribute(Constants.ATTR_USER_PC_EXTENDED_RATINGS, ""));
		accAttrs.add(compositeAttribute(Constants.ATTR_SECURITY_PIN, ""));
		accAttrs.add(compositeAttribute(Constants.ATTR_REMEMBER_PURCHASE_PIN_FLAG, "N"));
		//accAttrs.add(compositeAttribute(CAConstants.GIGYA_NOTIFY_RESPONSE, "N"));
		
		return accAttrs;

	}

//	private void addAccountAttribute(Integer attrId, String attrVal) {
//		AccountAttribute attr = new AccountAttribute();
//		attr.setAttributeValue(attrVal);
//		attr.setAttributeDetailId(attrId.longValue());
//		accAttrs.add(attr);
//	}
	

	private AccountUserBean createAccountUser() {
		AccountUserBean accountUser = new AccountUserBean();
		accountUser.setStatusId(1l);
		accountUser.setCrmaccountid(email);
		accountUser.setBloccoAcquisti("N");
		accountUser.setDataPrimoAccesso(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		return accountUser;
	}
	
	/*private AccountAttribute getNewGigyaAccoutAttribute(CrmAccount crmAcc){
		
		AccountAttribute aaItem = new AccountAttribute();
		aaItem.setAttributeValue("N");
		aaItem.setAttributeDetailId(Long.parseLong(CAConstants.GIGYA_NOTIFY_RESPONSE+""));
		aaItem.setUserId(crmAcc.getAccountUser().getUsername());
		aaItem.setUpdateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		return aaItem;
	}*/
	
	
	private CrmAccount createUser(CrmAccount crmAcc){
		try{
			
			
			if (crmAcc == null) {
				crmAcc = new CrmAccount();
			}
			crmAcc.setEmail(email);
			crmAcc.setCrmaccountid(email);
			
			// Assigning Default values to the user
			
			crmAcc.setVip("N");
			
			ArrayList<AccountAttribute> accAttrs = getAccoutAttributes();
			
			insertStatus = AccountManagementDAO.insertUpdateCrmAccount(crmAcc, createAccountUser(), accAttrs, null, null, null, tenantConfigurator);
			if(insertStatus){
				//get crmAccount			 
				crmAcc = AccountManagementDAO.getCrmAccount(email, tenantConfigurator);
			}
		
		}catch(Exception ex){
			//TODO
		}
		return crmAcc;
		
	}

//	public Transaction getTransaction() {
//		return transaction;
//	}
	
	private String getSysParameter(String paramName) {
		  String methodName = "getSysParameter";
		  try {
		   TenantConfigurator tc = getBaseTenantConfigurator();
		   ConstantsParameter cp = tc.getConstantsParameter();
		   String value = cp.get(paramName);
		   return value;
		  } catch (Exception e) {
		   LogUtil.commonInfoLog(logger, methodName, "Attribute " + paramName + "is not present");
		   return null;
		  }
		 }
	
	
	private String getSysMessages(String paramName) {
		  String methodName = "getSysMessages";
		  try {
		   TenantConfigurator tc = getBaseTenantConfigurator();
		   SystemMessages sm = tc.getSystemMessages();
		   String value = sm.get(paramName);
		   return value;
		  } catch (Exception e) {
		   LogUtil.commonInfoLog(logger, methodName, "Attribute " + paramName + "is not present");
		   return null;
		  }
		 }
	
	private Object outLoginCA_Action(Profile profile, String cookie) throws ActionException {
		Transaction transaction = null;
		TransactionBuilder builder = null;
		LoginCAResponseBean objectResponse = new LoginCAResponseBean();
		CookieUtil cookieUtil = new CookieUtil(tenantConfigurator);
		String output = "";
		// Execute standard steps for a Login Action
		sessionHttp.setAttribute(Constants.USERPROFILE, profile);
		try {
			cookieUtil.addCookies(profile, request, response);
			cookieUtil.addCookieUserInfo(profile, request, response);
			if(cookie != null && !cookie.trim().equals("")){
				cookieUtil.addCookieUserInfoForStub(profile, request, response);
			}
		} catch (UnknownHostException e) {
			LogUtil.errorLog(logger,
					systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR,
					e);
			throw new ActionException(
					systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		objectResponse.setCrmAccountId(profile.getCrmAccountId());
		objectResponse.setSessionSecret(this.sessionSecret);
		objectResponse.setSessionToken(this.sessionToken);
		builder = new TransactionBuilder(Constants.OK, "", "", "", "",
				objectResponse);
		transaction = builder.build();
		output = TransactionOutputFormat.bean2JSON(transaction,
				tenantConfigurator);
		LogUtil.commonInfoLog(logger, "LoginCA",
				"Result Login: OK - CRMACCOUNTID:" + profile.getCrmAccountId());
		return (output);
	}
	
	
	private void manageStateUser(int statusId) throws ActionException{
		
		// CHECK ACCOUNT STATUS
			switch (statusId) {
			case 0:
				LogUtil.commonInfoLog(logger, "manageStateUser", "Result Login: KO - "
						+ systemMessages.ERROR_BE_ACTION_3055_USER_NOT_FOUND);
				throw new ActionException(
						systemMessages.ERROR_BE_ACTION_3055_USER_NOT_FOUND);
			case 1:
			case 4:
				// only for KPN
				// profile active, reset login attempts
				break;
			case 3:		
				LogUtil.commonInfoLog(logger, "manageStateUser", "Result Login: KO - "
						+ systemMessages.ERROR_BE_ACTION_3035_BLOCKED_ACCOUNT);
				throw new ActionException(
						systemMessages.ERROR_BE_ACTION_3035_BLOCKED_ACCOUNT);
			case 5:
				LogUtil.commonInfoLog(logger, "manageStateUser", "Result Login: KO - "
						+ systemMessages.ERROR_BE_ACTION_3159_ERROR_STATE_ACCOUNT);
				throw new ActionException(
						systemMessages.ERROR_BE_ACTION_3159_ERROR_STATE_ACCOUNT);
			default:
				LogUtil.commonInfoLog(logger, "manageStateUser", "Result Login: KO - "
						+ systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR);
				throw new ActionException(
						systemMessages.ERROR_BE_ACTION_300_GENERIC_ERROR);
			}
		
	}
	
	/**
	 * Subscription Mail is sent to suer after successful subscription. 
	 * @param subscription
	 * @param sequenceId
	 */
	private void sendRegistrationMail(CrmAccount crmAccount, boolean isSocialMedial){
		String methodName = "sendRegistrationMail";
		try {
			new SendMailGateway(tenantConfigurator).sendMail(createInput(crmAccount));
			LogUtil.commonInfoLog(logger, methodName, CAConstants.REG_MAIL_SUCCESS + crmAccount.getCrmaccountid() + CAConstants.SOCIAL_MEDIA + isSocialMedial);
		} catch (AddressException addressException) {
			LogUtil.errorLog(logger, CAConstants.REG_MAIL_FAILURE + crmAccount.getCrmaccountid() + CAConstants.SOCIAL_MEDIA  + isSocialMedial, addressException);
		} catch (MessagingException messagingException) {
			LogUtil.errorLog(logger, CAConstants.REG_MAIL_FAILURE + crmAccount.getCrmaccountid() + CAConstants.SOCIAL_MEDIA  + isSocialMedial, messagingException);
		}
	}

	/**
	 * It creates input object which is used to send mail. The data it contains is subject, mail content, to mail id. and from mail id.
	 * @param inputHash
	 * @return input
	 */
	private String[] createInput(CrmAccount crmAccount) {
		String[] input = new String[4];
		input[0] = constantsParameter.get(WebConstants.REGISTRATION_MAIL_SUBJECT);
		input[1] = constantsParameter.get(WebConstants.REGISTRATION_MAIL_TEXT);
		input[2] = crmAccount.getCrmaccountid();
		input[3] = constantsParameter.get(WebConstants.MAIL_FROM_ADDRESS_NO_REPLY);
		return input;
	}
	
	private void validateParameter(String name,String value) throws ActionException
	{
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

}
