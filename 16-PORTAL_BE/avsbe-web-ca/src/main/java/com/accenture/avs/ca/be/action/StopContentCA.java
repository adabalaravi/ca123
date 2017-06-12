/**
 * 
 */
package com.accenture.avs.ca.be.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.ValidatorUtils;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.response.ProductPurchaseResponse;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.ca.be.dao.UserConcurrentStreamingDAO;
import com.accenture.avs.ca.be.formatter.PurchaseTransactionFormatter;
import com.accenture.avs.ca.be.util.WebConstants;

/**
 * @author siril.babu.nalluri
This API is used to delete the user’s session details from DB whenever the respective streaming got stopped. 
Log out Use cases 
Case 1: if user logged out 
i)	The respective User session record will be deleted.
ii)	send “OK” response

On Closing the Browser
Case 2: if user logged out 
i)	The respective User session record will be deleted.
ii)	send “OK” response

Case 3: if the Streaming Over.
Note – All the devices has to send the request to AVS to notify the streaming over , then those respective device sessions will be deleted from DB. 
i)	The respective User’s session record will be deleted.
ii)	Send “OK” response.

Note:Implemented the new session Listener(StopContentCAListener) mechanism to the user's session validate.
Find the further comments in StopContentCAListener class.

NOTE: Existed Sessions to be deleted manually from DB,in case of if all JBoss restarted.

 */
public class StopContentCA extends GenericAction{

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(StopContentCA.class);
	private HttpServletRequest request = null;
	private HttpSession sessionHttp = null;
	private Integer userId = null;
	Profile profile =null;
	private String channel=null;
	private boolean clearFlag = false; 
	private String clear;
	 
	public StopContentCA(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response; 
		// TODO Auto-generated constructor stub
	}
	  @Override
	public void validateRequest()   throws ActionException
	  {
		  try
		  {
			  this.sessionHttp = this.request.getSession();

			  profile = (Profile)this.sessionHttp.getAttribute(Constants.USERPROFILE);
			  channel=request.getParameter("channel");
			  ValidatorUtils.checkParameter(Constants.USERPROFILE, profile, this.tenantConfigurator);
			  if (profile == null || profile.getCrmAccountId().equalsIgnoreCase(Constants.ROWID_DEFAULT)) {
				  throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER + Constants.USERPROFILE);
			  }

			 
			 
			  if(request.getParameter("clear") != null){
				  clear = request.getParameter("clear");
				  //If clear value is "All",will be delete all user's respective session details from DB 
			   if(clear.equalsIgnoreCase("All")){
				  clearFlag=true;
			   }
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
		  try {
			  
			  //Below Panic flag mechanism is used to by pass the stop content API logic in case of any of the issues.
				 //so that user can watch the streaming on any number of devices with out having any interruptions.
			  if(isConcurrentStreamingInPanic()){
					//logPurchaseStatusInfoORErrorMsg(methodName,"Concurrent Streaming Is in Panic.");
					PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
					long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					Object resultObjToJson = getObjToJSon(Constants.OK, "","", "StopContentCA API IS IN PANIC", String.valueOf(systemTime));
					return formatter.bean2JSON(resultObjToJson).toString();		
				}
			  
			  this.userId = Integer.valueOf(profile.getUserId().intValue());
			  //delete the user's session(s) based on clearFlag
			  //if clear value is "All" then clearFlag value is 'true' else clearFlag value is 'FALSE'
			  //if StopContentCA API being called for deleteUserSession,then pass "STOPPED" flag  this flag is used for ConcurrentStreamingStatusReport.
				boolean deleteUserSucessfulFlag= UserConcurrentStreamingDAO.deleteUserSession(profile.getCrmAccountId(), sessionHttp.getId(), clearFlag,"STOPPED", tenantConfigurator); 
				if(deleteUserSucessfulFlag){
					LogUtil.commonInfoLog(logger, methodName,"User ID---: "+  profile.getCrmAccountId()+"|| Session id deleted sucessfully---size:"+sessionHttp.getId());
					 PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
					 long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
					 Object resultObjToJson = getObjToJSon(Constants.OK, "", "", "", String.valueOf(systemTime));
					 return formatter.bean2JSON(resultObjToJson).toString();   
				}
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		}
		return null;
		  
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
	private boolean isConcurrentStreamingInPanic(){
		String methodName="isConcurrentStreamingInPanic";
		boolean returnFlage=false;
		LogUtil.commonInfoLog(logger, methodName,"CONCURRENT_STREAMING_IN_PANIC_ VALUE : " + constantsParameter.get(WebConstants.CONCURRENT_STREAMING_IN_PANIC)+";");

		if(constantsParameter.get(WebConstants.CONCURRENT_STREAMING_IN_PANIC).equalsIgnoreCase("YES")) {
			LogUtil.commonInfoLog(logger, methodName,"StopContentCA API IS IN PANIC");
			returnFlage=true;
		}
		return returnFlage;
	}
}
