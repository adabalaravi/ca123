package com.accenture.avs.ca.be.action;

import java.util.ArrayList;
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
import com.accenture.avs.ca.be.db.bean.UserPurchasesCA;
import com.accenture.avs.ca.be.formatter.PurchaseTransactionFormatter;
import com.accenture.avs.ca.be.util.CAConstants;

/*
 * This class verifies that Retrieves all active and eligible channels to view Live Streaming  for the respective logged in user from AVS platform
 */
public class GetUserEligibleChannelsCA   extends GenericAction {
  
  private static Logger logger = Logger.getLogger(GetUserEligibleChannelsCA.class);
  private static final long serialVersionUID = 1L;
  private HttpServletRequest request = null;
  private HttpSession sessionHttp = null;
  private Integer userId = (Integer)null;
  
  /**
	 * A simple API Constructor.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
  public GetUserEligibleChannelsCA(HttpServletRequest request, HttpServletResponse response)
  {
    super(request, response);
    this.request = request;
    this.response = response;
    this.sessionHttp = request.getSession();
  }
  
  public void validateRequest()
    throws ActionException
  {
    try
    {
      this.sessionHttp = this.request.getSession();
      
      Profile profile = (Profile)this.sessionHttp.getAttribute(Constants.USERPROFILE);
      ValidatorUtils.checkParameter(Constants.USERPROFILE, profile, this.tenantConfigurator);
      if (profile == null || profile.getCrmAccountId().equalsIgnoreCase(Constants.ROWID_DEFAULT)) {
			throw new ActionException(systemMessages.ERROR_BE_ACTION_3000_MISSING_PARAMETER + Constants.USERPROFILE);
		}
      this.userId = Integer.valueOf(profile.getUserId().intValue());
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
    Object obj = null;
   
    try
     {
      //Fetching the Active technical packages for the user.
      List<UserPurchasesCA> userPurchasesCAList = PurchaseTransactionDAOCA.getPurchaseTransactionHistory(this.userId, this.tenantConfigurator);
      List<Long> technicalPKGList = new ArrayList<Long>();
      // if User does not have any active technical packages ,then throws 
      if ((userPurchasesCAList != null) && (userPurchasesCAList.size() > 0))
      {
        Iterator<?> itResList = userPurchasesCAList.iterator();
        while (itResList.hasNext())
        {
          UserPurchasesCA userPurchasesCA = (UserPurchasesCA)itResList.next();
          if (userPurchasesCA != null)
          {
        	//Setting technical package id to the List technicalPKGList
        	technicalPKGList.add(userPurchasesCA.getTech_pkg_id());
        	
          }
        }
      
        LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ userId+"|| Technical packages List:---"+technicalPKGList.toString());
        
        //Fetching channels list by passing users's corresponding technical package details
        List<Channel> channelList=PurchaseTransactionDAOCA.getChannelsByID(technicalPKGList, tenantConfigurator);
        // if User does not have any active channels packages ,then throws
        if(channelList==null || channelList.size()== 0){
      	  LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ userId+"|| Total Active Channels are --- :"+channelList.size());
      	  
      	  PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
  		  long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
  		  
  		  Object resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS)), null, String.valueOf(systemTime));
  		  
  		  return formatter.bean2JSON(resultObjToJson).toString(); 
        }
        obj = getPurchaseFormatter(channelList,userPurchasesCAList,this.userId);        
      }     
      else{
    	  LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ userId+"|| Techinical package ids count is null---size:"+userPurchasesCAList.size());
    	  PurchaseTransactionFormatter formatter = new PurchaseTransactionFormatter(this.tenantConfigurator);
		  long systemTime = (long) (System.currentTimeMillis() / 1000);// In seconds..
		  Object resultObjToJson = getObjToJSon(Constants.KO, ErrorMessageFormatterUtil.setErrorDescription(systemMessages.get(CAConstants.ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS)), ErrorMessageFormatterUtil.setErrorMessage(systemMessages.get(CAConstants.ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS)), null, String.valueOf(systemTime));
		  
		  return formatter.bean2JSON(resultObjToJson).toString();
      }
      
    }
    catch (Exception e)
    {
      LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
      
      throw new ActionException(this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR);
    }
    return obj;
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
}
