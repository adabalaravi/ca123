package com.accenture.ams.accountmgmtservice;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.accenture.ams.accountmgmtservice.sdpAccountProfileDataService.SdpAccountProfileDataBean;
import com.accenture.ams.accountmgmtservice.sdpPartyProfileDataService.bean.SdpPartyProfileDataServiceBean;
import com.accenture.ams.db.bean.AccountDeviceAMS;

import wsclient.commontypes.accountmgmt.avs.accenture.CrmAccountDeviceType;
import wsclient.commontypes.accountmgmt.avs.accenture.CrmAccountDevicesListType;
import wsclient.commontypes.accountmgmt.avs.accenture.ExtendedRatingType;
import wsclient.commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import wsclient.commontypes.accountmgmt.avs.accenture.UserPcExtendedRatingsListType;

import wsclient.types.accountmgmt.avs.accenture.AccountMgmtResponse;
import wsclient.types.accountmgmt.avs.accenture.GetSdpAccountProfileDataResponse;
import wsclient.types.accountmgmt.avs.accenture.GetSdpPartyProfileDataResponse;
import wsclient.types.accountmgmt.avs.accenture.SdpAccountProfileDataResponse;
import wsclient.types.accountmgmt.avs.accenture.SdpPartyProfileDataResponse;
import wsclient.types.accountmgmt.avs.accenture.SdpResult;

public class Utils {

	public static Long intToLong(int val){
		return Long.parseLong( Integer.toString(val) );
	}
	
	public static boolean isValorized(String data){
		if (data!=null && !data.isEmpty())
			return true;
		return false;
	}
	
	public static AccountMgmtResponse getResponse(int id, String optionalText){
		AccountMgmtResponse resp = new AccountMgmtResponse();
				
		resp.setResultCode( AccountMgmtServiceConstant.RET_CODE[id] );
		
		if (optionalText!=null){
			StringBuffer text = new StringBuffer(AccountMgmtServiceConstant.RET_DESC[id]);
			text.append("|").append(optionalText);
			resp.setResultDescription( text.toString() );
		}
		else
			resp.setResultDescription( AccountMgmtServiceConstant.RET_DESC[id] );
		
		return resp;
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} 
		catch (AddressException ex) {
			result = false;
		}
		return result;
	}
	
	public static Timestamp getDate(Calendar cal){
		if (cal==null)
			return new Timestamp( System.currentTimeMillis() );
		
		return new Timestamp( cal.getTimeInMillis() );
		
	}
	
	public static Timestamp getDate(int offset){
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_MONTH, offset);
		
		return getDate(cal);		
	}
	
	public static String getDateString(Calendar cal){
		if (cal==null)
			cal = Calendar.getInstance();
		
		String year = Integer.toString( cal.get(Calendar.YEAR) );
		String month = Integer.toString( cal.get(Calendar.MONTH) );
		String day = Integer.toString( cal.get(Calendar.DAY_OF_MONTH) );
		String h = Integer.toString( cal.get(Calendar.HOUR_OF_DAY) );
		String min = Integer.toString( cal.get(Calendar.MINUTE) );
		String sec = Integer.toString( cal.get(Calendar.SECOND) );
		
		if (month.length()<2)
			month = "0"+month;
		
		if (day.length()<2)
			day = "0"+day;
		
		if (min.length()<2)
			min = "0"+min;
		
		if (sec.length()<2)
			sec = "0"+sec;
		
		return year+month+day+h+min+sec;
	}
	
	/*
	 * return sysdate+day_offset in the fomat :
	 * YYYYMMDDHHMISS
	 */
	public static String getDateString(int offset){
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_MONTH, offset);
		
		return getDateString(cal);
	}
	
	/**
	 * 
	 * @param id
	 * @param optionalText
	 * @param sdpPartyProfileDataServiceBean
	 * @return
	 */
	public static SdpPartyProfileDataResponse getSdpPartyProfileDataResponse(int id, String optionalText, SdpPartyProfileDataServiceBean sdpPartyProfileDataServiceBean){
		SdpPartyProfileDataResponse resp = new SdpPartyProfileDataResponse();
		
		SdpResult sdpresult = new SdpResult();
		sdpresult.setResultCode(AccountMgmtServiceConstant.RET_CODE[id]);
		
		if (optionalText!=null){
			StringBuffer text = new StringBuffer(AccountMgmtServiceConstant.RET_DESC[id]);
			text.append("|").append(optionalText);
			sdpresult.setResultDescription( text.toString() );
		}
		else
			sdpresult.setResultDescription( AccountMgmtServiceConstant.RET_DESC[id] );
		
		
		resp.setResult(sdpresult);		
		
		if(sdpPartyProfileDataServiceBean!=null){
			GetSdpPartyProfileDataResponse data = new GetSdpPartyProfileDataResponse();
			data.setUserType(sdpPartyProfileDataServiceBean.getUserType());
			data.setUserLanguage(sdpPartyProfileDataServiceBean.getUserLanguage());
			data.setQualitySetting(sdpPartyProfileDataServiceBean.getQualitySetting());
			data.setUserPaymentMethod(sdpPartyProfileDataServiceBean.getUserPaymentMethod());
			data.setCrmAccountPurchaseBlockingFlag(Utils.booleanToFlagTypeYN(sdpPartyProfileDataServiceBean.isCrmAccountPurchaseBlockingFlag()));
			data.setCrmAccountPurchaseBlockingThresholdCurrency(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseBlockingThresholdCurrency());
			data.setCrmAccountPurchaseBlockingThresholdValue(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseBlockingThresholdValue());
			data.setCrmAccountPurchaseBlockingThresholdPeriod(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseBlockingThresholdPeriod());
			data.setCrmAccountPurchaseBlockingAlertEnabledFlag(Utils.booleanToFlagTypeYN(sdpPartyProfileDataServiceBean.isCrmAccountPurchaseBlockingAlertEnabledFlag()));
			data.setCrmAccountPurchaseBlockingAlertChannel(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseBlockingAlertChannel());
			data.setCrmAccountPurchaseBlockingAlertMode(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseBlockingAlertMode());
			data.setCrmAccountPurchaseBlockingIntermediateThreshold(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseBlockingIntermediateThreshold());
			data.setCrmAccountConsumptionBlockingFlag(Utils.booleanToFlagTypeYN(sdpPartyProfileDataServiceBean.isCrmAccountConsumptionBlockingFlag()));
			data.setCrmAccountConsumptionBlockingThresholdValue(sdpPartyProfileDataServiceBean.getCrmAccountConsumptionBlockingThresholdValue());
			data.setCrmAccountConsumptionBlockingThresholdPeriod(sdpPartyProfileDataServiceBean.getCrmAccountConsumptionBlockingThresholdPeriod());
			data.setCrmAccountConsumptionBlockingEnabledFlag(Utils.booleanToFlagTypeYN(sdpPartyProfileDataServiceBean.isCrmAccountConsumptionBlockingEnabledFlag()));
			data.setCrmAccountConsumptionBlockingAlertChannel(sdpPartyProfileDataServiceBean.getCrmAccountConsumptionBlockingAlertChannel());
			data.setCrmAccountConsumptionBlockingAlertMode(sdpPartyProfileDataServiceBean.getCrmAccountConsumptionBlockingAlertMode());
			data.setCrmAccountConsumptionBlockingIntermediateThreshold(sdpPartyProfileDataServiceBean.getCrmAccountConsumptionBlockingIntermediateThreshold());
			data.setCrmAccountPurchaseValue(sdpPartyProfileDataServiceBean.getCrmAccountPurchaseValue());
			data.setCrmAccountConsumption(sdpPartyProfileDataServiceBean.getCrmAccountConsumption().intValue());
						
			resp.setResultData(data);			
		}
		
		return resp;
	}
	
	/**
	 * 
	 * @param id
	 * @param optionalText
	 * @param sdpAccountProfileDataBean
	 * @return SdpAccountProfileDataResponse
	 */
	public static SdpAccountProfileDataResponse getSdpAccountProfileDataResponse(int id, String optionalText, SdpAccountProfileDataBean sdpAccountProfileDataBean){
		SdpAccountProfileDataResponse resp = new SdpAccountProfileDataResponse();
		
		SdpResult sdpresult = new SdpResult();
		sdpresult.setResultCode(AccountMgmtServiceConstant.RET_CODE[id]);
		
		if (optionalText!=null){
			StringBuffer text = new StringBuffer(AccountMgmtServiceConstant.RET_DESC[id]);
			text.append("|").append(optionalText);
			sdpresult.setResultDescription( text.toString() );
		}
		else
			sdpresult.setResultDescription( AccountMgmtServiceConstant.RET_DESC[id] );
		
		
		resp.setResult(sdpresult);		
		CrmAccountDeviceType crmAccountDeviceType=null;
		
		if(sdpAccountProfileDataBean!=null){
			GetSdpAccountProfileDataResponse data = new GetSdpAccountProfileDataResponse();
			
			//Set crmAccountDevicesListType to data object
			CrmAccountDevicesListType crmAccountDevicesListType=null;
			if(!sdpAccountProfileDataBean.getListOfCrmAccountDevice().isEmpty())
			{
				Iterator<AccountDeviceAMS> listofCrmAccountDevices=sdpAccountProfileDataBean.getListOfCrmAccountDevice().iterator();
				
				crmAccountDevicesListType=new CrmAccountDevicesListType();
				List<CrmAccountDeviceType> listOfCrmAccountDeviceType=crmAccountDevicesListType.getCrmAccountDevice();
			   
				while(listofCrmAccountDevices.hasNext()){
					AccountDeviceAMS accountDeviceAMS=listofCrmAccountDevices.next();
					crmAccountDeviceType=new CrmAccountDeviceType();
					
					crmAccountDeviceType.setChannel(accountDeviceAMS.getPlatformId());
					crmAccountDeviceType.setCrmAccountDeviceId(accountDeviceAMS.getDeviceId());
					crmAccountDeviceType.setCrmAccountDeviceIdType(Integer.parseInt(accountDeviceAMS.getType().toString()));
					
					listOfCrmAccountDeviceType.add(crmAccountDeviceType);
				}
			}
			data.setCrmAccountDevices(crmAccountDevicesListType);
			
			//Set userPcExtendedRatingsListType to data object
			UserPcExtendedRatingsListType userPcExtendedRatingsListType=new UserPcExtendedRatingsListType();
			if(sdpAccountProfileDataBean.getPcExtendedRating() != null){
			StringTokenizer tokens=new StringTokenizer(sdpAccountProfileDataBean.getPcExtendedRating(),";");
			List<ExtendedRatingType> userPcExtendedRating=userPcExtendedRatingsListType.getUserPcExtendedRating();
			while(tokens.hasMoreTokens()){
					String theToken = tokens.nextToken();
					if("S".equalsIgnoreCase(theToken)){
					userPcExtendedRating.add(ExtendedRatingType.S);
					}else if("T".equalsIgnoreCase(theToken)){
					userPcExtendedRating.add(ExtendedRatingType.T);
					}else if("H".equalsIgnoreCase(theToken)){
					userPcExtendedRating.add(ExtendedRatingType.H);
					}else if("D".equalsIgnoreCase(theToken)){
					userPcExtendedRating.add(ExtendedRatingType.D);
					}else if("A".equalsIgnoreCase(theToken)){
					userPcExtendedRating.add(ExtendedRatingType.A);
					}else if("G".equalsIgnoreCase(theToken)){
					userPcExtendedRating.add(ExtendedRatingType.G);
				}
				
			}
			}
			data.setUserPcExtendedRatings(userPcExtendedRatingsListType);
			
			//Set UserPCLevel, ParentalPin, UserPin to data object
			data.setUserPcLevel(Integer.parseInt(sdpAccountProfileDataBean.getUserPCLevel()));
			data.setUserPinParentalControl(sdpAccountProfileDataBean.getParentalPin());
			data.setUserPinPurchase(sdpAccountProfileDataBean.getUserPin());
			data.setRetailerDomain( sdpAccountProfileDataBean.getRetailerDomain() );
			//Set UserRememberPinFlag to data object
			if("Y".equalsIgnoreCase(sdpAccountProfileDataBean.getUserRememberPinFlag())){
				data.setUserRememberPinFlag(FlagTypeYN.Y);
			}else if("N".equalsIgnoreCase(sdpAccountProfileDataBean.getUserRememberPinFlag())){
				data.setUserRememberPinFlag(FlagTypeYN.N);
			}
			
			resp.setResultData(data);			
		}
		
		return resp;
	}
	/**
	 * Return boolean value: true if flag-string is 'Y', otherwise false
	 * 
	 * @param value
	 * @return
	 */
	public static boolean stringToBoolean(String value){
		boolean retValue = false;
		if(value!=null && value.equalsIgnoreCase("Y")){
			retValue = true;
		}
		
		return retValue;
	}
	
	/**
	 * Return BigDecimal value: if string value is not null and it has a numeric format.
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String value){
		BigDecimal retValue = null;
		
		if(value!=null){
			try{
				retValue = new BigDecimal(value);
			}
			catch(NumberFormatException nfe){
				retValue = null;
			}
		}
		
		return retValue;
	}

	/**
	 * Return BigDecimal value: if double value has a valid format.
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal doubleToBigDecimal(Double value){
		BigDecimal retValue = null;
		
		if(value!=null){
			try{
				retValue = new BigDecimal(value.doubleValue());
			}
			catch(NumberFormatException nfe){
				retValue = null;
			}
		}
		return retValue;
	}
	
	
	/**
	 *  Return Integer value: if string value is not null and it has a numeric format.
	 * 
	 * @param value
	 * @return
	 */
	public static Integer stringToInteger(String value){
		Integer retValue = null;
		
		if(value!=null){
			try{
				retValue = new Integer(value);
			}
			catch(NumberFormatException nfe){
				retValue = null;
			}
		}
		
		return retValue;
	}
	
	/**
	 * Return type FlagTypeYN
	 * @param value
	 * @return
	 */
	public static FlagTypeYN booleanToFlagTypeYN(boolean value){
		if(value==true)
			return FlagTypeYN.fromValue(FlagTypeYN.Y.value());
		else 
			return FlagTypeYN.fromValue(FlagTypeYN.N.value());
	}
	
	
}
