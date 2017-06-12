package com.accenture.avs.ca.be.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.bean.SysParameterGroup;
import com.accenture.avs.be.db.bean.SysParameters;
import com.accenture.avs.be.db.bean.SysParametersPK;
import com.accenture.avs.be.db.framework.ConstantsParameter;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.ca.be.bean.BPointClientResult;
import com.accenture.avs.ca.be.bean.BPointPaymentResponseBean;
import com.accenture.avs.ca.be.util.ReportUtilCA;
import com.accenture.avs.ca.be.util.WebConstants;
import com.accenture.avs.be.framework.GenericAction;

/**
 * 
 * @author s.attuluri
 * 
 * This class acts as a client to connect to the BPoint gateway.
 *
 */

public class BPointPurchaseGateway {
	private static Logger logger = Logger.getLogger(BPointPurchaseGateway.class);
	private TenantConfigurator tc;
	private String formattedLogMsg = "";
	/**
	 * 
	 * @param crn
	 * @param productPrice
	 * @param tenantConfigurator
	 * @return
	 * @throws Exception
	 * 
	 * This method will authorize the merchant when user chooses to buy a product.
	 * It will return the BPointClientResult with the respective response code and errorText/auth token
	 */		     
		public Object authorizeMerchant(String crn,String productPrice,TenantConfigurator tenantConfigurator) throws Exception
		{
			String methodName =  "AuthorizeMerchant";
			loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- START -- : Authorizing the merchant with the BPOINT");
			logPurchaseStatusInfoORErrorMsg(methodName, "BPOINTPurchaseGateway -- START -- : Authorizing the merchant with the BPOINT");
			
			tc = tenantConfigurator;
			
			BPointClientResult responseObj = new BPointClientResult();
			String bPointURL = getSysParameter(tc,WebConstants.BPOINT_AUTH_URL);
			String in_merchant_number = getSysParameter(tc,WebConstants.MERCHANT_NUMBER);
			String in_merchant_username = getSysParameter(tc,WebConstants.MERCHANT_USERNAME);
			String in_merchant_password = getSysParameter(tc,WebConstants.MERCHANT_PASSWORD);

			String in_amount = productPrice;
			String in_crn1= crn;
			HttpClient client = null;
			HttpPost post = null;
			try
			{					
				loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- BPOINT AUTH URL:"+bPointURL);
				
				post = new HttpPost(bPointURL);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

		      	//This below method is taking care of logging the parameters, null check and create the required post parameters 
				createPostParams(methodName,nameValuePairs,"in_merchant_number", in_merchant_number);
				createPostParams(methodName,nameValuePairs,"in_merchant_username", in_merchant_username);
				createPostParams(methodName,nameValuePairs,"in_merchant_password", in_merchant_password);
				createPostParams(methodName,nameValuePairs,"in_amount", in_amount);
				createPostParams(methodName,nameValuePairs,"in_crn1",in_crn1);		      	
		      	
				formattedLogMsg = "CRN_1="+in_crn1+" Amount="+in_amount;
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpParams httpParams = new BasicHttpParams();
				
				int timeOut = Integer.parseInt(getSysParameter(tc,WebConstants.BPOINT_CONNECTION_TIME_OUT));
				HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
				loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Configured Connection Time-out="+HttpConnectionParams.getConnectionTimeout(httpParams));
				
				// HttpConnectionParams.setSoTimeout(httpParams, 500);         
				client = new DefaultHttpClient(httpParams);
				
				int retryCount =  Integer.parseInt(getSysParameter(tc,WebConstants.BPOINT_URL_CONNECTION_RETRY_COUNT));
				DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount,true);
				((AbstractHttpClient) client).setHttpRequestRetryHandler(retryHandler);
				
			    loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Configured Retry Count:"+retryHandler.getRetryCount());
			    
			    HttpResponse response = client.execute(post);
			    loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- HttpResponse="+response.toString(),true);
			    
			//Get the Response from the BPoint
			    responseObj = getResponseFromBPoint(response,true);
			}catch (IOException e) {
				responseObj.setOut_Result_Code(WebConstants.BPOINT_FAILURE_RESULT_CODE);
				responseObj.setOut_ErrorText("Error due to Network / Connection Failure OR Timed-Out.");
				logPurchaseStatusInfoORErrorMsg(methodName, "Error PaymentAuth due to Network / Connection Failure OR Timed-Out. BPoint Result Code");
				
				loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- IOException ="+ e.getMessage(),true);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- Exception ="+ e.getMessage(),true);
				throw e;
			}
		return responseObj;
	    }
	
		/**
		 * 
		 * @param paymentResponseBean
		 * @param tenantConfigurator
		 * @return
		 * @throws Exception
		 * This method will be used by the AVS_BE to verify the payment with the BPoint.
		 * It will return the BPointClientResult with the respective response code and errorText.
		 */
		public Object verifyPaymentBPoint(BPointPaymentResponseBean paymentResponseBean,TenantConfigurator tenantConfigurator) throws Exception
		{

				String methodName =  "VerifyPaymentBPoint";
				loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- START -- : Verifying the Payment with the BPOINT");
				logPurchaseStatusInfoORErrorMsg(methodName, "BPOINTPurchaseGateway -- START -- : Verifying the Payment with the BPOINT");
			
				tc = tenantConfigurator;
			
				//Get the URL from the Properties file
				String bPointVerificationURL = getSysParameter(tc,WebConstants.BPOINT_VERIFY_URL);
								
				BPointClientResult responseObj = new BPointClientResult();

				String in_merchant_number   = getSysParameter(tc,WebConstants.MERCHANT_NUMBER);
				String in_merchant_username = getSysParameter(tc,WebConstants.MERCHANT_USERNAME);
				String in_merchant_password = getSysParameter(tc,WebConstants.MERCHANT_PASSWORD);
				
				HttpClient client = null;
				try{									
					HttpPost post = new HttpPost(bPointVerificationURL);
					
					loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- BPOINT Payment Verification URL:"+bPointVerificationURL);

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					
					//This below method is taking care of logging the parameters, null check and create the required post parameters 
					createPostParams(methodName,nameValuePairs,"in_merchant_number", in_merchant_number);
					createPostParams(methodName,nameValuePairs,"in_merchant_username", in_merchant_username);
					createPostParams(methodName,nameValuePairs,"in_merchant_password", in_merchant_password);
					createPostParams(methodName,nameValuePairs,"in_amount", paymentResponseBean.getAmount());
					createPostParams(methodName,nameValuePairs,"in_crn1",paymentResponseBean.getCrn1());
					createPostParams(methodName,nameValuePairs,"in_response_code", paymentResponseBean.getResponse_code());
					createPostParams(methodName,nameValuePairs,"in_bank_response_code",paymentResponseBean.getBank_response_code());
					createPostParams(methodName,nameValuePairs,"in_auth_result",paymentResponseBean.getAuth_result());
					createPostParams(methodName,nameValuePairs,"in_txn_number", paymentResponseBean.getTxn_number());
					createPostParams(methodName,nameValuePairs,"in_receipt_number",paymentResponseBean.getReceipt_number());
					createPostParams(methodName,nameValuePairs,"in_settlement_date", paymentResponseBean.getSettlement_date());
					createPostParams(methodName,nameValuePairs,"in_expiry_date", paymentResponseBean.getExpiry_date());
					createPostParams(methodName,nameValuePairs,"in_account_number", paymentResponseBean.getAccount_number());
					createPostParams(methodName,nameValuePairs,"in_payment_date", paymentResponseBean.getPayment_date());
					createPostParams(methodName,nameValuePairs,"in_verify_token",paymentResponseBean.getVerify_token());										

					post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					formattedLogMsg = "CRN_1="+paymentResponseBean.getCrn1()+"Amount="+paymentResponseBean.getAmount(); 
					
					HttpParams httpParams = new BasicHttpParams();

					int timeOut = Integer.parseInt(getSysParameter(tc,WebConstants.BPOINT_CONNECTION_TIME_OUT));
					HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
					loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Configured Connection Time-out="+HttpConnectionParams.getConnectionTimeout(httpParams));
					
					// HttpConnectionParams.setSoTimeout(httpParams, 500);         
					client = new DefaultHttpClient(httpParams);
					
					int retryCount =  Integer.parseInt(getSysParameter(tc,WebConstants.BPOINT_URL_CONNECTION_RETRY_COUNT));
					DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount,true);
					((AbstractHttpClient) client).setHttpRequestRetryHandler(retryHandler);
					
					loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Configured Retry Count:"+retryHandler.getRetryCount());
				    
				    HttpResponse response = client.execute(post);
				    loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- HttpResponse="+response.toString(),true);
				    
				//Get the Response from the BPoint
				    responseObj = getResponseFromBPoint(response,false);
				}catch (IOException e) {
					responseObj.setOut_Result_Code(WebConstants.BPOINT_FAILURE_RESULT_CODE);
					responseObj.setOut_ErrorText("Error due to Network / Connection Failure OR Timed-Out.");
					
					logPurchaseStatusInfoORErrorMsg(methodName, "Error PaymentVerification due to Network / Connection Failure OR Timed-Out. BPoint Result Code");
					
					loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- RunTime Exception ="+ e.getMessage(),true);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- IOException ="+ e.getMessage(),true);
					throw e;
				}
			return responseObj;
		}
		
		private BPointClientResult getResponseFromBPoint(HttpResponse resp,boolean isAuthCodeRequest) throws Exception
		{						
			
			String methodName = "getResponseFromBPoint";
			loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Processing the BPOINT response with respective to Auth / Verify request.");
			
			BufferedReader respBufferReader = null;
			BPointClientResult responseObj = null;
			try
			{
				respBufferReader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
				loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- respBufferReader="+respBufferReader.toString(),true);
				
				String inputLine;								
				List<String> responseListObj = new ArrayList<String>();
				
				while ((inputLine = respBufferReader.readLine()) != null) {
					if (logger.isDebugEnabled()) {					
						LogUtil.commonDebugLog(logger, methodName, "BPoint URL Connection Response Details: "+inputLine);
					}
					responseListObj.add(inputLine);
				}								
				if(responseListObj.size() > 0)
					responseObj = createBPointResponseObject(responseListObj,isAuthCodeRequest);
				
			}finally{
				
				respBufferReader.close();
				loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- closing the BufferedReader",true);
			}
		
		return responseObj;		
		}	
		
		private BPointClientResult createBPointResponseObject(List<String> responseListObj,boolean isAuthCodeRequest)
		{
			String methodName = "createBPointResponseObject";
			
			loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Creating the BPOINT response object.");
			
			String responseCodeString = responseListObj.get(0);
			String[] respCodeArray = responseCodeString.split("=");
			String respCode = respCodeArray[1];
			
			
			BPointClientResult respObj = new BPointClientResult();
			respObj.setOut_Resp_code(respCode);
			
			loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- isAuthCodeRequest = "+isAuthCodeRequest,true);
			
			loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- Actual Response from the BPoint="+responseListObj.toString(),true);
			
			loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- RespCode from BPoint = "+respCode,true);
			
			
			if(WebConstants.BPOINT_SUCCESSFUL_RESP_CODE.equals(respCode))
			{
				loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Response OK");
				respObj.setOut_Result_Code(WebConstants.BPOINT_SUCCESSFUL_RESULT_CODE);
				
				if(isAuthCodeRequest)
				{
					String respPayTokenString = responseListObj.get(1);
					loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- respPayTokenString="+respPayTokenString,true);
					
					String[] respPayTokenArray = respPayTokenString.split("=");
					
					String payToken = respPayTokenArray[1];
					respObj.setOut_Pay_Token(payToken);
					
					loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- payToken="+payToken,true);
				}
			}else
			{
				loggerCommonInfo(methodName, "BPOINTPurchaseGateway -- Response KO");
				
				respObj.setOut_Result_Code(WebConstants.BPOINT_FAILURE_RESULT_CODE);
				String errorString = responseListObj.get(1);
				loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- errorString="+errorString,true);
				
				String[] errorStringArray = errorString.split("=");
				
				String formattedErrorCodeString = "BPOINT_ERROR_CODE_"+respCode.trim();
				String sysMsgparam = WebConstants.getSysMessageParam(formattedErrorCodeString);
				String errorText = tc.getSystemMessages().get(sysMsgparam);
				
				respObj.setOut_ErrorText(errorText);
				
				loggerDebugEnabled(methodName, "BPOINTPurchaseGateway -- errorText="+errorText,true);
			}
		
		return respObj;
		}
		
		private void createPostParams(String methodName,List<NameValuePair> nameValuePairs,String paramName,String paramValue)
		{
			boolean writeLog = true;
			if(paramName.equalsIgnoreCase("in_merchant_number") || paramName.equalsIgnoreCase("in_merchant_username") || paramName.equalsIgnoreCase("in_merchant_password"))
			{
				writeLog = false;
			}
			loggerDebugEnabled(methodName, "BPOINTPurchaseGateway --"+ paramName+"="+paramValue,writeLog);
			if(paramValue != null && !"".endsWith(paramValue.trim()));
			{
				nameValuePairs.add(new BasicNameValuePair(paramName, paramValue));
			}
		}
		private void loggerDebugEnabled(String methodName, String debugText,boolean writeLog)
		{
			if(writeLog)
			{	
				logPurchaseStatusInfoORErrorMsg(methodName, debugText);
				if (logger.isDebugEnabled()) {
					LogUtil.commonDebugLog(logger, methodName, debugText);
				}
			}
		}
		private void loggerCommonInfo(String methodName, String infoText)
		{			
			LogUtil.commonDebugLog(logger, methodName, infoText);
		}
		
		private String getSysParameter(TenantConfigurator tc,String paramName) {
			  String methodName = "getSysParameter";
			  try {
			   
			   ConstantsParameter cp = tc.getConstantsParameter();
			   String value = cp.get(paramName);
			   return value;
			  } catch (Exception e) {
			   LogUtil.commonInfoLog(logger, methodName, "Attribute " + paramName + "is not present");
			   logPurchaseStatusInfoORErrorMsg(methodName, "Attribute " + paramName + "is not present");
			   return null;
			  }
		}
		protected void logPurchaseStatusInfoORErrorMsg(String methodName,String msg) {
			try 
			{
					ReportUtilCA report = new ReportUtilCA();						
					String logMsg = formattedLogMsg +" || "+ msg;
					report.logPurchaseStatusInfoORErrorMsg(methodName,tc.getTenantId(),"","","","","",logMsg);					
			} catch (Exception e) {	
				
				
			}
		}
	
}
