
package com.accenture.avs.ca.be.json.bean;

/**
 * @author aditya.madhav.k
 *
 */
public class ProductListResponseCA {

	private String resultCode="";
	private String errorDescription="";
	private String message="";
	private ProductListCA resultObj=null;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ProductListCA getResultObj() {
		return resultObj;
	}
	public void setResultObj(ProductListCA resultObj) {
		this.resultObj = resultObj;
	}
}
