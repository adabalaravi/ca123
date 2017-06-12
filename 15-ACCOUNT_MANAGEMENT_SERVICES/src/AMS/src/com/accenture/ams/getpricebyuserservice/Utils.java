package com.accenture.ams.getpricebyuserservice;

import com.accenture.ams.getpricebyuserservice.bean.GetPriceByUserServiceBean;

import types.getpricebyuser.avs.accenture.GetPriceByUserResponse;
import types.getpricebyuser.avs.accenture.GetPriceByUserResponseData;
import types.getpricebyuser.avs.accenture.GetPriceByUserResultData;

public class Utils {

	/**
	 * 
	 * @param id
	 * @param optionalText
	 * @param getPriceByUserServiceBean
	 * @return
	 */
	public static GetPriceByUserResponse getResponse(int id, String optionalText, GetPriceByUserServiceBean getPriceByUserServiceBean){
		GetPriceByUserResponse response = new GetPriceByUserResponse();
		
		GetPriceByUserResultData getPriceByUserResultData = new GetPriceByUserResultData();
		getPriceByUserResultData.setResultCode(GetPriceByUserServiceConstant.RET_CODE[id]);
		
		if (optionalText!=null){
			StringBuffer text = new StringBuffer(GetPriceByUserServiceConstant.RET_DESC[id]);
			text.append("|").append(optionalText);
			getPriceByUserResultData.setResultDescription( text.toString() );
		}
		else
			getPriceByUserResultData.setResultDescription( GetPriceByUserServiceConstant.RET_DESC[id] );
		
		response.setResult(getPriceByUserResultData);
		
		if(getPriceByUserServiceBean!=null){
			GetPriceByUserResponseData data = new GetPriceByUserResponseData();
			data.setCurrency(getPriceByUserServiceBean.getCurrency());
			data.setPrice(getPriceByUserServiceBean.getPrice().toString());
			data.setPriceDiscount(getPriceByUserServiceBean.getPriceDiscount().toString());
			
			response.setResultData(data);
		}
		
		return response;
	}
	
	/**
	 * Return Long value: if string value is not null and it has a numeric format.
	 * 
	 * @param value
	 * @return
	 */
	public static Long stringToLong(String value){
		Long longValue = null;
		
		if(value!=null){
			try{
				longValue = new Long(value);
			}
			catch(NumberFormatException nfe){
				longValue = null;
			}
		}
		return longValue;
	}
	
	
		
	
}
