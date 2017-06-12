package com.accenture.avs.ca.be.json.bean;

public class UserPurchaseTransactionCA
{
  private Long itemId;
 // private long userId;
  private String itemName;
  private String itemType;
  private String itemDescription;
  private long startDate;
  private long endDate;
  private String statusDescription;
  private String originalPrice;

  
  public Long getItemId()
  {
    return this.itemId;
  }
  
  public void setItemId(Long itemId)
  {
    this.itemId = itemId;
  }
  
  /*public long getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(long userId)
  {
    this.userId = userId;
  }*/
  
  public String getItemName()
  {
    return this.itemName;
  }
  
  public void setItemName(String itemName)
  {
    this.itemName = itemName;
  }
  
  public String getItemType()
  {
    return this.itemType;
  }
  
  public void setItemType(String itemType)
  {
    this.itemType = itemType;
  }
  
  public String getItemDescription()
  {
    return this.itemDescription;
  }
  
  public void setItemDescription(String itemDescription)
  {
    this.itemDescription = itemDescription;
  }
  
  public long getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(long startDate)
  {
    this.startDate = startDate;
  }
  
  public long getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(long endDate)
  {
    this.endDate = endDate;
  }
  
  public String getStatusDescription()
  {
    return this.statusDescription;
  }
  
  public void setStatusDescription(String statusDescription)
  {
    this.statusDescription = statusDescription;
  }
  
 
  
  public String getOriginalPrice()
  {
    return this.originalPrice;
  }
  
  public void setOriginalPrice(String originalPrice)
  {
    this.originalPrice = originalPrice;
  }
  
 
}
