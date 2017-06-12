package com.accenture.ams.getpricebyuserservice.stub.util;

import com.accenture.ams.getpricebyuserservice.stub.component.ItemPrice;

public class PaymentGatewayUtil {
	
	/**
	 * 
	 * @param itemId
	 * @param userId
	 * @return
	 */
	public static Object getContentPrice(Long itemId,Long userId){
		
		ItemPrice  result=new ItemPrice();
		result.setPriceDiscounted(15.0);
		result.setPrice(16.0);
		result.setCurrency("EUR");
		return result;
		
	}
	
	/**
	 * 
	 * @param itemId
	 * @param userId
	 * @return
	 */
	public static Object getPPVPrice(Long itemId,Long userId){
		ItemPrice  result=new ItemPrice();
		result.setPriceDiscounted(20.50);
		result.setPrice(22.0);
		result.setCurrency("EUR");
		return result;
		
	}
	
	/**
	 * 
	 * @param itemId
	 * @param userId
	 * @return
	 */
	public static Object getProductPrice(Long itemId,Long userId){
		ItemPrice  result=new ItemPrice();
		result.setPriceDiscounted(25.00);
		result.setPrice(30.0);
		result.setCurrency("EUR");
		return result;
		
	}
	
	/**
	 * 
	 * @param itemId
	 * @param userId
	 * @return
	 */
	public static Object getBundlePrice(Long itemId,Long userId){
		ItemPrice  result=new ItemPrice();
		result.setPriceDiscounted(30.00);
		result.setPrice(33.0);
		result.setCurrency("EUR");
		return result;
		
	}

}
