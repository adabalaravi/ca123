package com.accenture.avs.ca.be.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Transient;

public class UserPurchasesCA
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long itemId;
  private long user_id;
  private String item_name;
  private String item_type;
  private String item_description;
  private Timestamp start_date;
  private Timestamp end_date;
  private String status_description;
  private String broadcast_channel;
  private String original_price;
  private String price;
  private String currency;
  private String max_views;
  private String views_number;
  private String transaction_price;
  private String refund_date;
  private String refund_price;
  private Long sequenceId;
  private Long tech_pkg_id;
  
  
  
  @Transient
  private String contentSubTitle;
  
  public Long getItemId()
  {
    return this.itemId;
  }
  
  public void setItemId(Long itemId)
  {
    this.itemId = itemId;
  }
  
  public long getUser_id()
  {
    return this.user_id;
  }
  
  public void setUser_id(long user_id)
  {
    this.user_id = user_id;
  }
  
  public String getItem_name()
  {
    return this.item_name;
  }
  
  public void setItem_name(String item_name)
  {
    this.item_name = item_name;
  }
  
  public String getItem_type()
  {
    return this.item_type;
  }
  
  public void setItem_type(String item_type)
  {
    this.item_type = item_type;
  }
  
  public String getItem_description()
  {
    return this.item_description;
  }
  
  public void setItem_description(String item_description)
  {
    this.item_description = item_description;
  }
  
  public Timestamp getStart_date()
  {
    return this.start_date;
  }
  
  public void setStart_date(Timestamp start_date)
  {
    this.start_date = start_date;
  }
  
  public Timestamp getEnd_date()
  {
    return this.end_date;
  }
  
  public void setEnd_date(Timestamp end_date)
  {
    this.end_date = end_date;
  }
  
  public String getStatus_description()
  {
    return this.status_description;
  }
  
  public void setStatus_description(String status_description)
  {
    this.status_description = status_description;
  }
  
  public String getBroadcast_channel()
  {
    return this.broadcast_channel;
  }
  
  public void setBroadcast_channel(String broadcast_channel)
  {
    this.broadcast_channel = broadcast_channel;
  }
  
  public String getOriginal_price()
  {
    return this.original_price;
  }
  
  public void setOriginal_price(String original_price)
  {
    this.original_price = original_price;
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setPrice(String price)
  {
    this.price = price;
  }
  
  public String getMax_views()
  {
    return this.max_views;
  }
  
  public void setMax_views(String max_views)
  {
    this.max_views = max_views;
  }
  
  public String getViews_number()
  {
    return this.views_number;
  }
  
  public void setViews_number(String views_number)
  {
    this.views_number = views_number;
  }
  
  public String getTransaction_price()
  {
    return this.transaction_price;
  }
  
  public void setTransaction_price(String transaction_price)
  {
    this.transaction_price = transaction_price;
  }
  
  public String getRefund_date()
  {
    return this.refund_date;
  }
  
  public void setRefund_date(String refund_date)
  {
    this.refund_date = refund_date;
  }
  
  public String getRefund_price()
  {
    return this.refund_price;
  }
  
  public void setRefund_price(String refund_price)
  {
    this.refund_price = refund_price;
  }
  
  public String getCurrency()
  {
    return this.currency;
  }
  
  public void setCurrency(String currency)
  {
    this.currency = currency;
  }
  
  public String getContentSubTitle()
  {
    return this.contentSubTitle;
  }
  
  public void setContentSubTitle(String contentSubTitle)
  {
    this.contentSubTitle = contentSubTitle;
  }
  
  public Long getSequenceId()
  {
    return this.sequenceId;
  }
  
  public void setSequenceId(Long sequenceId)
  {
    this.sequenceId = sequenceId;
  }

public Long getTech_pkg_id() {
	return tech_pkg_id;
}

public void setTech_pkg_id(Long tech_pkg_id) {
	this.tech_pkg_id = tech_pkg_id;
}
}
