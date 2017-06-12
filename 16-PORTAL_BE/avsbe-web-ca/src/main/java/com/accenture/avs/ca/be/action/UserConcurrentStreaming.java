package com.accenture.avs.ca.be.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.accenture.avs.be.db.bean.Channel;
import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.ValidatorUtils;
import com.accenture.avs.be.exception.ActionException;
import com.accenture.avs.be.framework.GenericAction;
import com.accenture.avs.be.json.response.ProductPurchaseResponse;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.be.util.ErrorMessageFormatterUtil;
import com.accenture.avs.ca.be.dao.PurchaseTransactionDAOCA;
import com.accenture.avs.ca.be.dao.UserConcurrentStreamingDAO;
import com.accenture.avs.ca.be.db.bean.UserConcurrentStreamingCA;
import com.accenture.avs.ca.be.db.bean.UserPurchasesCA;
import com.accenture.avs.ca.be.formatter.PurchaseTransactionFormatter;
import com.accenture.avs.ca.be.util.CAConstants;
import com.accenture.avs.ca.be.util.ProductListUtilCA;
import com.accenture.avs.ca.be.util.WebConstants;

/**
 * @author siril.babu.nalluri
 * Pre-conditions
 * User should be logged-in.
 * Validate whether the user is having active pass or not. If user does not have active subscription, send “KO” response with message like “user does not have active Packages”.
 * If respective users streaming details are already with AVS and exceeds threshold value which is configurable in DB,then  send “KO” response with message like “User Exceeded the Max Device Limit”
 * If user session is already available,Sends "OK" response.

*/
public class UserConcurrentStreaming extends GenericAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserConcurrentStreaming.class);
	private HttpServletRequest request = null;
	private HttpSession sessionHttp = null;
	private Integer userId = (Integer)null;
	Profile profile =null;
	private String channel;
	private String id;

	public UserConcurrentStreaming(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
		// TODO Auto-generated constructor stub
	}

	public void validateRequest() 
			throws ActionException
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

			//Requested  live Channel Id where the live event will be streamed 
			id=request.getParameter("id");
			if(id==null ||id.equals("")){
				throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER + Constants.CHANNEL_ID);
			}

		}
		catch (Exception e)
		{
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR, e);
			if (e instanceof ActionException) {
				throw ((ActionException) e);
			}
			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3020_VALIDATE_REQUEST_ERROR);
		}
			}

	public Object executeRequest() throws ActionException {
		String methodName="executeRequest";
		Object obj = null;
		//List<String> allUserSessionsList = new ArrayList<String>();

		try
		{
         //Below Panic flag mechanism is used to by pass the concurrent streaming API logic in case of any of the issues.
		 //so that user can watch the streaming on any number of devices with out having any interruptions .
         //Panic flag is configurable in DB, and the default value flag is “NO”.
		 //If Panic flag is “YES”,  then by pass the concurrent streaming API .

			if(isConcurrentStreamingInPanic()){
				//logPurchaseStatusInfoORErrorMsg(methodName,"Concurrent Streaming Is in Panic.");
				PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				Object resultObjToJson = getObjToJSon(Constants.OK, "","", "UserConcurrentStreaming API IS IN PANIC", String.valueOf(systemTime));
				obj= formatter.bean2JSON(resultObjToJson).toString();		
			}
			
			this.userId = Integer.valueOf(profile.getUserId().intValue());
			boolean activeTechinicalPkgFlag=true;
			List<String> freeChannelList=new ArrayList<String>();
			String isActiveChannelValidationRequired = WebConstants.YES;
			//CONCURRENT_STREAMING_COUNT is configurable in DB,Based on the number user can watch the video concurrently in irrespective of devices.
			String concurrentStreamingThresholdCount = constantsParameter.get(WebConstants.CONCURRENT_STREAMING_THRESHOLD_COUNT);
			
			//"IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED" flag is configurable in DB,which is used to validate the active channels or not
			//Default value of "IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED" flag is "YES".
			
			isActiveChannelValidationRequired = constantsParameter.get(WebConstants.IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED);
			List<Long> technicalPKGList= new ArrayList<Long>();
			
			//if "IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED" flag is "null" or "" then setting the value is "NO"
			if(isActiveChannelValidationRequired == null || "".equals(isActiveChannelValidationRequired.trim())){
				isActiveChannelValidationRequired = WebConstants.NO;
			}
			
			// "CHANNELS_FOR_FREE_VIDEOS" is configurable in DB, which are the free video relative channels.
			
			String concurrentStreamingChannelforFreeVideos = constantsParameter.get(WebConstants.CHANNELS_FOR_FREE_VIDEOS);
			
			 if(concurrentStreamingChannelforFreeVideos != null && !"".equals(concurrentStreamingChannelforFreeVideos.trim())){
					
					String[] packageids=concurrentStreamingChannelforFreeVideos.trim().split(",");
				    freeChannelList =Arrays.asList(ProductListUtilCA.trimListOfStrings(packageids));
					
				}
		 
			// if channel id is belongs to free video channel,then by-pass the getActiveTechnicalpackages logic
			if(isActiveChannelValidationRequired.equalsIgnoreCase(WebConstants.YES) && (freeChannelList.size() > 0 && !freeChannelList.contains(id)) ){
			
				//Fetching the Active technical packages for the user.
				 technicalPKGList = getActiveTechnicalpackages();
				 
				//Fetching the Active technical packages for the respective channel.				
				activeTechinicalPkgFlag=checkTechnicalPgsAvailable(technicalPKGList);
			}
			
            
			//If there are no valid technical packages then throws KO response 
			 	
		    if(activeTechinicalPkgFlag)
			{
		    			    	
		    	//Fetching the user's active concurrent streaming count from DB 
				List<UserConcurrentStreamingCA> userConcurrentStreamingCAList=UserConcurrentStreamingDAO.getUserConcurrentStreamList(profile.getCrmAccountId(), tenantConfigurator);
				
				LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| Existed Active Sessions count available:---"+userConcurrentStreamingCAList.size());
				
				List<String> allUserSessionsList = new ArrayList<String>();
				
		    	if(userConcurrentStreamingCAList != null ){
		    		
		    		for (Iterator<UserConcurrentStreamingCA> iterator = userConcurrentStreamingCAList.iterator(); iterator.hasNext();) {
						UserConcurrentStreamingCA userConcurrentStreamingCA = (UserConcurrentStreamingCA) iterator.next();
						allUserSessionsList.add(userConcurrentStreamingCA.getSessionid());
						
					}
		    		//Checking if users session details are available or not
		    		//
		    		if(allUserSessionsList!=null && allUserSessionsList.contains(sessionHttp.getId()) ){
		    			LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"--Session id is already existed---- :"+sessionHttp.getId());
						PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
						long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
						Object resultObjToJson = getObjToJSon(Constants.OK, "", "", "", String.valueOf(systemTime));
						obj= formatter.bean2JSON(resultObjToJson).toString();  
		    		}else{//New session comes in
						//Checks users streaming details are already with AVS,if user session details less than threshold value.
			    		if(userConcurrentStreamingCAList.size() < Integer.parseInt(concurrentStreamingThresholdCount)){
				    		  LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"--New Session id is---- :"+sessionHttp.getId());
				    		  UserConcurrentStreamingCA userConcurrentStreamingCA = getStreamingObject("FALSE");
					    	  boolean insertSucessfulFlag=UserConcurrentStreamingDAO.insertUserStreaming(userConcurrentStreamingCA,tenantConfigurator);
					    			
			                 //if new session id inserted successfully
							if(insertSucessfulFlag){
								UserConcurrentStreamingDAO.sendUserConcurrentStreamingStatusReport(profile.getCrmAccountId(),userConcurrentStreamingCA.getSessionid(),userConcurrentStreamingCA.getDeviceType(),userConcurrentStreamingCA.getChannel(),"INITIATED");
								LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"--New Session id is inserted sucessfully---  :"+sessionHttp.getId());
								PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
								long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
								Object resultObjToJson = getObjToJSon(Constants.OK, "", "", "", String.valueOf(systemTime));
								obj= formatter.bean2JSON(resultObjToJson).toString();  
								}
						}else{//User have already having active streaming
                            try{
                                
                                LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| User has exceeded concurrent streaming and existed count is:-- "+userConcurrentStreamingCAList.size() +"--concurrentStreamingThresholdCount is :"+Integer.parseInt(concurrentStreamingThresholdCount));
                                PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
                                LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| User has exceeded concurrent streaming :-- showing the message");
                                long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
                                LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| User has exceeded concurrent streaming :-- showing the message systemTime="+systemTime);
                                //Object resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS)), null, String.valueOf(systemTime));
                                Object resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS)), null, String.valueOf(systemTime));
                                obj= formatter.bean2JSON(resultObjToJson).toString();
                                LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| User has exceeded concurrent streaming :-- showing the resultObject="+obj);

                         }catch(Exception ex)
                         {
                                LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| User has exceeded concurrent streaming :-- showing the error message = "+ex.getMessage());
                                ex.printStackTrace();
                                throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
                         }
                  }
     
 }
		    	}
		    		
				
			}else{//User doesn’t have active channels
				LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ profile.getCrmAccountId()+"|| User does not have active channels---size:"+technicalPKGList.size());
				PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
				long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
				Object resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS)), null, String.valueOf(systemTime));
				obj= formatter.bean2JSON(resultObjToJson).toString();
			}

		}
		catch (Exception e)
		{
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);

			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		}
		return obj;
	}

	/**
	 * @return
	 */
	private UserConcurrentStreamingCA getStreamingObject(String isDisabled) {
		UserConcurrentStreamingCA userConcurrentStreamingCA =new  UserConcurrentStreamingCA();
		userConcurrentStreamingCA.setCrmAccountId(profile.getCrmAccountId());
		userConcurrentStreamingCA.setSessionid(sessionHttp.getId());
		userConcurrentStreamingCA.setDeviceType(channel);
		userConcurrentStreamingCA.setIs_disabled(isDisabled);
		userConcurrentStreamingCA.setChannel(id);
		return userConcurrentStreamingCA;
	}
	protected Object getPurchaseFormatter(List<Channel> channelList,List<UserPurchasesCA> userPurchasesCAList,int userId)
	{

		PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
		Object obj = formatter.bean2JSON(userPurchasesCAList,channelList,userId);
		obj = obj.toString();
		return obj;
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

	//Fetching the Active technical packages for the user.

	private List<Long> getActiveTechnicalpackages() throws ActionException {

		List<Long> technicalPKGList = null;
		try {

			List<UserPurchasesCA> userPurchasesCAList = PurchaseTransactionDAOCA.getPurchaseTransactionHistory(this.userId,	this.tenantConfigurator);

			technicalPKGList = new ArrayList<Long>();
			// if User does not have any active technical packages ,then throws
			if ((userPurchasesCAList != null)
					&& (userPurchasesCAList.size() > 0)) {
				Iterator<UserPurchasesCA> itResList = userPurchasesCAList.iterator();
				while (itResList.hasNext()) {
					UserPurchasesCA userPurchasesCA = (UserPurchasesCA) itResList.next();
					if (userPurchasesCA != null) {
						// Setting technical package id to the List
						// technicalPKGList
						technicalPKGList.add(userPurchasesCA.getTech_pkg_id());

					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
			//e.printStackTrace();
			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
		}
		return technicalPKGList;
	}
//Panic flag mechanism is used to by pass the concurrent streaming API logic in case of any of the issues so that user can watch the streaming on any number of devices with out having any interruptions .
	private boolean isConcurrentStreamingInPanic(){
		String methodName="isConcurrentStreamingInPanic";		
		LogUtil.commonInfoLog(logger, methodName,"CONCURRENT_STREAMING_IN_PANIC_ VALUE : " + constantsParameter.get(WebConstants.CONCURRENT_STREAMING_IN_PANIC)+";");

		if(constantsParameter.get(WebConstants.CONCURRENT_STREAMING_IN_PANIC).equalsIgnoreCase("YES")) {
			LogUtil.commonInfoLog(logger, methodName,"CONCURRENT STREAMING IS IN PANIC");
			return true;
		}
		return false;
	}
//Validating technical packages with current channel's technical packages.
	private boolean checkTechnicalPgsAvailable(List<Long> technicalPKGList) throws ActionException {
		boolean pkgFlag = false;
		try {
			
			List<Long> listOfTechnicalPks = UserConcurrentStreamingDAO.getTechnicalPkgIdsByChannel(id, tenantConfigurator);
			
			if (listOfTechnicalPks != null && technicalPKGList != null) {
				for (Iterator<Long> iterator = listOfTechnicalPks.iterator(); iterator.hasNext();) {
					Long channelTechPkg = iterator.next();
					if (technicalPKGList.contains(channelTechPkg)) {
						pkgFlag = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			pkgFlag = false;
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
			throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
			
		}

		return pkgFlag;

	}
	
}