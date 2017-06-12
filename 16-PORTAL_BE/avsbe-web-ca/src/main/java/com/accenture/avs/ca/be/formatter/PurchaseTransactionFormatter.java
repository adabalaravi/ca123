package com.accenture.avs.ca.be.formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.db.bean.Channel;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.formatter.ProductListFormatter;
import com.accenture.avs.ca.be.db.bean.UserPurchasesCA;
import com.accenture.avs.ca.be.json.bean.ChannelHistory;
import com.accenture.avs.ca.be.json.bean.ContentPurchaseHistoryCA;
import com.accenture.avs.ca.be.json.bean.UserEligibleChannelsCA;
import com.accenture.avs.ca.be.json.bean.UserPurchaseTransactionCA;


public class PurchaseTransactionFormatter extends ProductListFormatter{

	private static Logger logger = Logger.getLogger(PurchaseTransactionFormatter.class);
	public PurchaseTransactionFormatter(TenantConfigurator tenantConfigurator) {
		super(tenantConfigurator);
		// TODO Auto-generated constructor stub
	}
	
	
	public Object bean2JSON(List<UserPurchasesCA> userPurchaseList,List<Channel> channelList,int userID)
	  {
		String methodName="bean2JSON";
	    Calendar date1 = Calendar.getInstance();
	    JSON returnJSON = null;
	    JsonConfig jsonConfig = new JsonConfig();
	    ChannelHistory ch = new ChannelHistory();
	    ContentPurchaseHistoryCA contentsres = null;
	    UserEligibleChannelsCA userEligibleChannelsCA =null;
	    if ((userPurchaseList != null) && (userPurchaseList.size() > 0))
	    {
	      contentsres = new ContentPurchaseHistoryCA();
	      UserPurchaseTransactionCA[] arraycontp = new UserPurchaseTransactionCA[userPurchaseList.size()];
	      
	      Iterator<UserPurchasesCA> iteratoruserPurchasesIn = userPurchaseList.iterator();
	      
	      int i = 0;
	      while (iteratoruserPurchasesIn.hasNext())
	      {
	        UserPurchasesCA userPurchases = (UserPurchasesCA)iteratoruserPurchasesIn.next();
	        
	        UserPurchaseTransactionCA userPurchaseTransaction = new UserPurchaseTransactionCA();
	        userPurchaseTransaction.setEndDate(userPurchases.getEnd_date().getTime() / 1000L);
	        userPurchaseTransaction.setItemDescription(userPurchases.getItem_description());
	        userPurchaseTransaction.setItemId(userPurchases.getItemId());
	        userPurchaseTransaction.setItemName(userPurchases.getItem_name());
	        userPurchaseTransaction.setItemType(userPurchases.getItem_type());
	        userPurchaseTransaction.setOriginalPrice(userPurchases.getOriginal_price());
	        userPurchaseTransaction.setStartDate(userPurchases.getStart_date().getTime() / 1000L);
	        userPurchaseTransaction.setStatusDescription(userPurchases.getStatus_description());
	       // userPurchaseTransaction.setUserId(userPurchases.getUser_id());
	        arraycontp[(i++)] = userPurchaseTransaction;
	      }
	      contentsres.setUserPurchasesTransactions(arraycontp);
	    }	    
	    else
	    {
	      contentsres = new ContentPurchaseHistoryCA();
	    }
	    
        if(channelList!=null && channelList.size()>0  ){
        	
		   List<String> activechannelList=new ArrayList<String>();
		   userEligibleChannelsCA = new UserEligibleChannelsCA();
		     
		   for (Iterator<Channel> iterator = channelList.iterator(); iterator.hasNext();) {
			Channel channel = (Channel) iterator.next();
			activechannelList.add(channel.getChannelId().toString());		
		   }
		   LogUtil.commonInfoLog(logger, methodName,"User ID---: "+ userID+"|| Total Active Channels are ----- :"+activechannelList.toString());
	     userEligibleChannelsCA.setChannelList(activechannelList);
	     contentsres.setUserEligibleChannels(userEligibleChannelsCA);
	  }
	   
	    
	    ch.setResultCode("OK");
	    ch.setResultObj(contentsres);
	    Calendar date2 = Calendar.getInstance();
	    LogUtil.profileAPIResult(PurchaseTransactionFormatter.class, ch.getResultCode(), date1, date2);
	    returnJSON = JSONSerializer.toJSON(ch, jsonConfig);
	    return returnJSON;
	  }
	
	public Object bean2JSON(Object resultContent)
		    throws Exception
		  {
		    JSON returnJSON = null;
		    JsonConfig jsonConfig = new JsonConfig();
		    returnJSON = JSONSerializer.toJSON(resultContent, jsonConfig);
		    
		    return returnJSON.toString();
		  }


}
