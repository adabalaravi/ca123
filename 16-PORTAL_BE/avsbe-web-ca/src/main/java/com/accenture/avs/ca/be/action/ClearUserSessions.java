package com.accenture.avs.ca.be.action;



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
import com.accenture.avs.ca.be.formatter.PurchaseTransactionFormatter;

/**
 * @author 
 * 
 * API - ClearUserSessions
 * This API will clear all active sessions for respective user. * 
 * Validating that user has active session or not.
 * If user has active session then it will clear all the active session with resultCode OK.
 * otherwise it will show User Not Exist in AVS  with resultCode KO.
 *
 */
public class ClearUserSessions extends GenericAction{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ClearUserSessions.class);
	private HttpServletRequest request = null;	
	private String channel=null;
	private String email;
	
	public ClearUserSessions(HttpServletRequest request,HttpServletResponse response) {
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
	
	public Object executeRequest() throws ActionException {
		  String methodName="executeRequest";		 
		  LogUtil.writeCommonDebugLog(logger, "internal", "entering "	+ methodName);
		  try {					
				LogUtil.writeCommonInfoLog(logger, methodName, "Checking the existence of the user with the provided email - "+email);
				//Checking the existence of the user with the provided email.
				CrmAccount crmAcc = AccountManagementDAO.getCrmAccount(email, tenantConfigurator);				
				if (crmAcc != null){ 								
					//user is present in the system(AVS BE)				
					LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" is present in the AVS ");																							    
					LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" - is going to clear session from AVS ." );
					//Deleting User All Sessions
					UserConcurrentStreamingDAO.deleteUserSession(email, null, true,"CLEARSESSION",tenantConfigurator);
					
					LogUtil.commonInfoLog(logger, methodName,"Email ID---: "+  email+" deleted sucessfully from AVS ");

					PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
					long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					Object resultObjToJson = getObjToJSon(Constants.OK, "", "", "", String.valueOf(systemTime));					
					return formatter.bean2JSON(resultObjToJson).toString();
				}
				else{
					 //user isn't present in the system(AVS BE)
					 LogUtil.writeCommonInfoLog(logger, methodName, "User - "+email +" is NOT present in the AVS");					 
					 PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
					 long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					 Object resultObjToJson = getObjToJSon(Constants.KO, "", "", "User Does Not Exist in AVS", String.valueOf(systemTime));					
					 return formatter.bean2JSON(resultObjToJson).toString();
				}								
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		}		 		
	  }	 
	//For JSON Response.
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
