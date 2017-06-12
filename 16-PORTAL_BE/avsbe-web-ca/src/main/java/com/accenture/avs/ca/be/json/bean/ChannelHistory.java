package com.accenture.avs.ca.be.json.bean;

public class ChannelHistory {
	 private String resultCode = "";
	  private String errorDescription = "";
	  private String message = "";
	  private ContentPurchaseHistoryCA resultObj = null;
	  
	  public String getResultCode()
	  {
	    return this.resultCode;
	  }
	  
	  public void setResultCode(String resultCode)
	  {
	    this.resultCode = resultCode;
	  }
	  
	  public String getErrorDescription()
	  {
	    return this.errorDescription;
	  }
	  
	  public void setErrorDescription(String errorDescription)
	  {
	    this.errorDescription = errorDescription;
	  }
	  
	  public String getMessage()
	  {
	    return this.message;
	  }
	  
	  public void setMessage(String message)
	  {
	    this.message = message;
	  }
	  
	  public ContentPurchaseHistoryCA getResultObj()
	  {
	    return this.resultObj;
	  }
	  
	  public void setResultObj(ContentPurchaseHistoryCA resultObj)
	  {
	    this.resultObj = resultObj;
	  }

}
