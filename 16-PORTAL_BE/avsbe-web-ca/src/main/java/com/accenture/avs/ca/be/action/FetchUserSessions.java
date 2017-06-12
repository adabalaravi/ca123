package com.accenture.avs.ca.be.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.accenture.avs.be.db.bean.CrmAccount;
import com.accenture.avs.be.db.dao.AccountManagementDAO;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.response.ProductPurchaseResponse;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.ca.be.dao.UserConcurrentStreamingDAO;
import com.accenture.avs.ca.be.db.bean.FetchUserSessionsCA;
import com.accenture.avs.ca.be.db.bean.UserConcurrentStreamingCA;
import com.accenture.avs.ca.be.formatter.PurchaseTransactionFormatter;

/**
 * @author 
 * 
 * API - FetchUserSessions
 * This API will fetch active user's sessions for UserConcurrentStreaming API. * 
 * Validating that user has active session or not.
 * If user has active session then it will show all the active session with resultCode OK.
 * otherwise it will show User Not Exist in AVS DB with resultCode KO.
 *
 */
 public class FetchUserSessions extends GenericAction{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FetchUserSessions.class);
	private HttpServletRequest request = null;	
	private String channel=null;
	private String email;
	
	public FetchUserSessions(HttpServletRequest request,HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response; 		
	}
	
	public void validateRequest()   throws ActionException
	  {
		
		  try
		  {			 	
			  channel=request.getParameter("channel");	
			  email = request.getParameter(Constants.EMAIL);
			//validate email parameter
				if (email != null) {
					this.validateEMAILParameter(email);
				}else{
					throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER+ "|email");
				}    
		  }
		  catch (Exception e)
		  {
			  
			  LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR, e);
			  if ((e instanceof ActionException)) {
				  
				  throw ((ActionException)e);
			  }
			  throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR);
		  }
		  
	  }
	
	/*
	 * (non-Javadoc)
	 * @see com.accenture.avs.be.framework.GenericAction#executeRequest()
	 * First we are fetching the user from Crm_account.
	 * if user is exist in AVS DB then we are fetching all the respective sessions with resultCode OK.
	 * if user is exist in AVS DB then we are showing User Not Exist in AVS DB with resultCode KO.
	 * 
	 */
	
	public Object executeRequest() throws ActionException {
		  String methodName="executeRequest";
		  LogUtil.writeCommonDebugLog(logger, "internal", "entering "	+ methodName);	
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  java.sql.Date date=null;		 
		  try {	
			  //fetching the user from Crm_account
			    CrmAccount crmAcc = AccountManagementDAO.getCrmAccount(email, tenantConfigurator);				    
				if (crmAcc != null){ 
				    LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" - is going to get all sessions from AVS ." );
				    //fetching active user's sessions.
				    List<UserConcurrentStreamingCA> userConcurrentStreamingCA= UserConcurrentStreamingDAO.getUserAllSession(crmAcc.getCrmaccountid(), tenantConfigurator); 			   			 				
					if(userConcurrentStreamingCA !=null){	
						
						LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" - Have "+userConcurrentStreamingCA.size()+" sessions in AVS." );					
						//Creating ArrayList<FetchUserSessionsCA> Object to add all sessions.
						List<FetchUserSessionsCA> fetchedUserList=new ArrayList<FetchUserSessionsCA>();
						// Iterator userConcurrentStreamingCA 
						 for (Iterator<UserConcurrentStreamingCA> iterator = userConcurrentStreamingCA.iterator();iterator.hasNext();) {							
								
							    UserConcurrentStreamingCA fetchedUsers = iterator.next();
								FetchUserSessionsCA  fetchUserSessionsCA=new FetchUserSessionsCA();
								// Setting Data to FetchUserSessionsCA for JSON Output
								fetchUserSessionsCA.setSessionid(fetchedUsers.getSessionid());
								date = new java.sql.Date(fetchedUsers.getCreation_date().getTime());							 								
								fetchUserSessionsCA.setCreation_date(df.format(date));
								fetchUserSessionsCA.setDeviceType(fetchedUsers.getDeviceType());
								fetchedUserList.add(fetchUserSessionsCA);	
						 }
						 
						 LogUtil.commonInfoLog(logger, methodName,"Email ID---: "+  email+" Feched All Data sucessfully from AVS");					 
						 
						 PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
						 long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
						 Object resultObjToJson = getObjToJSon(Constants.OK, "", "",fetchedUserList, String.valueOf(systemTime));					
						 return formatter.bean2JSON(resultObjToJson).toString();   
					}
				}
				else{
					//user isn't present in the system(AVS BE)
					 LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" is NOT present in the AVS");					 
					 PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
					 long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					 Object resultObjToJson = getObjToJSon(Constants.KO, "", "", "User Not Exist in AVS", String.valueOf(systemTime));					
					 return formatter.bean2JSON(resultObjToJson).toString();
				}	
				
				
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		}
		return null;
		  
	  }	 		
	
	
	
   private ProductPurchaseResponse getObjToJSon(String resultCode, String errorDescription, String message, Object rO, String systemTime) throws Exception {
	  ProductPurchaseResponse p = new ProductPurchaseResponse();	
	  p.setResultCode(resultCode);
	  p.setErrorDescription(errorDescription);
	  p.setMessage(message);
	  p.setResultObj(rO);
	  p.setSystemTime(systemTime);			
	  return p;
  }
							
}
