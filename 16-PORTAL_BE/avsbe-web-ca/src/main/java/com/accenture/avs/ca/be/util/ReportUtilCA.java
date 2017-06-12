package com.accenture.avs.ca.be.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.ca.be.action.ProductPurchaseBPoint;

public class ReportUtilCA {
	protected static Logger logPurchaseStatus = Logger.getLogger("purchaseStatus");
	protected static Logger logPurchase = Logger.getLogger("purchase");
	private static Logger logger = Logger.getLogger(ReportUtilCA.class);
	
	protected static Logger logUserConcurrentStream = Logger.getLogger("UserConcurrentStream");
	public ReportUtilCA() {
		
	}
	public void sendPurchaseStatusReport(String appenderName,String methodName, String tenantId, String rowId, String smartCardId, Timestamp endDate, String channel, String contentId, String consumption, String paymentType, String workOrderId, String transactionId, String price, String originalPrice, String currency, String state, String contentType, String recurringDiscPrice, String recurringOriginalPrice,
			String profileId) {

		
		DateFormat dateFormatRep = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar nowRep = Calendar.getInstance();
		String nowDateStringRep = dateFormatRep.format(nowRep.getTime());
		String endDateStringRep = "";

	
		if (rowId == null) {
			rowId = "";
		}
		if (nowDateStringRep == null) {
			nowDateStringRep = "";
		}
		if (contentId == null) {
			contentId = "";
		}

		if (consumption == null) {
			consumption = "";
		}
		if (paymentType == null) {
			paymentType = "";
		}
		if (workOrderId == null) {
			workOrderId = "";
		}
		if (transactionId == null) {
			transactionId = "";
		}
		if (price == null) {
			price = "";
		}
		if (endDate != null) {
			endDateStringRep = (dateFormatRep.format(endDate)).toString();
		}
		if (originalPrice == null) {
			originalPrice = "";
		}
		if (currency == null) {
			currency = "";
		}
		if (state == null) {// INITIALIZED, CONFIRMED, CANCELED, ERROR,
							// COMPLETED, REFUNDED, ERROR_REFUND
			state = "";
		}
		if (contentType == null) {
			contentType = "";
		}
		logPurchaseStatus.info(methodName +":"+ tenantId + ";" + rowId + ";" + smartCardId + ";" + nowDateStringRep + ";" + endDateStringRep + ";" + channel + ";" + contentId + ";" + paymentType + ";" + consumption + ";" + workOrderId + ";" + transactionId + ";" + price + ";" + originalPrice + ";" + currency + ";" + state + ";" + contentType + ";" + recurringDiscPrice + ";" + recurringOriginalPrice + ";" + profileId);

	}

	public void logPurchaseStatusInfoORErrorMsg(String methodName,String tenantId, String rowId, String channel, String contentId, String paymentType, String contentType, String msg) {
	
		if (rowId == null) {
			rowId = "";
		}
		
		if (contentId == null) {
			contentId = "";
		}

		if (paymentType == null) {
			paymentType = "";
		}
		if (contentType == null) {
			contentType = "";
		}
				
		logPurchaseStatus.info(methodName+" : "+tenantId + " ; " + rowId + " ; " + channel + " ; " + contentId + " ; " + paymentType + " ; " + contentType + " ; " + " Info / Error Msg:"+ msg);				
	}
	public void sendUserConcurrentStreamingStatusReport(String crmAccounId,String sessionId,String deviceType,String channel,String status) {
		
		if (crmAccounId == null) {
			crmAccounId = "";
		}
		
		if (sessionId == null) {
			sessionId = "";
		}
		
		if (deviceType == null) {
			deviceType = "";
		}
		if (channel == null) {
			channel = "";
		}
		
		logUserConcurrentStream.info(crmAccounId + " #BT_CA# " + sessionId + " #BT_CA# " + deviceType + " #BT_CA# " + channel + " #BT_CA# " + status);				
	}

}
