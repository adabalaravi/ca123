package com.accenture.ams.db.bean;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CATEGORY database table.
 * 
 * @author BEA Workshop
 */
public class Category  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long categoryId;
	private String categoryType;
	private String channelCategory;
	private Long contentId;
	private String contentOrderType;
	private String asNew;
	private String isVisible;
	private String name;
	private Long orderId;
	private Long parentCategoryId;
	private String contentTitle;
	private String adult;
	private String pictureUrl;
	private String title;
	
	

	private java.util.Set<CategoryAggregation> categoryAggregations1;
	private java.util.Set<CategoryAggregation> categoryAggregations2;

    public Category() {
    }
    
    
    public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Long getCategoryId() {
		return categoryId;
	}




	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}




	public String getChannelCategory() {
		return channelCategory;
	}




	public void setChannelCategory(String channelCategory) {
		this.channelCategory = channelCategory;
	}




	public Long getContentId() {
		return contentId;
	}




	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}




	public String getContentOrderType() {
		return contentOrderType;
	}




	public void setContentOrderType(String contentOrderType) {
		this.contentOrderType = contentOrderType;
	}




	public String getAsNew() {
		return asNew;
	}




	public void setAsNew(String asNew) {
		this.asNew = asNew;
	}




	public String getIsVisible() {
		return isVisible;
	}




	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public Long getOrderId() {
		return orderId;
	}




	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}




	public Long getParentCategoryId() {
		return parentCategoryId;
	}




	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}




	public String getContentTitle() {
		return contentTitle;
	}




	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public void setAdult(String adult) {
		this.adult = adult;
	}




	public java.util.Set<CategoryAggregation> getCategoryAggregations1() {
		return categoryAggregations1;
	}




	public void setCategoryAggregations1(java.util.Set<CategoryAggregation> categoryAggregations1) {
		this.categoryAggregations1 = categoryAggregations1;
	}




	public java.util.Set<CategoryAggregation> getCategoryAggregations2() {
		return categoryAggregations2;
	}




	public void setCategoryAggregations2(java.util.Set<CategoryAggregation> categoryAggregations2) {
		this.categoryAggregations2 = categoryAggregations2;
	}





	
	
	
	public String getCategoryType() {
		return categoryType;
	}
	public String getAdult() {
		return adult;
	}




	public void setCategoryType(String categoryType) {
		if(categoryType.equalsIgnoreCase("LEAF")){
			this.categoryType="TYPE_VOD";
		}else if (categoryType.equalsIgnoreCase("NODE")){
			this.categoryType="TYPE_NODE";
		}
		this.categoryType = categoryType;
		
	}




	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Category)) {
			return false;
		}
		Category castOther = (Category)other;
		return new EqualsBuilder()
			.append(this.getCategoryId(), castOther.getCategoryId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCategoryId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("categoryId", getCategoryId())
			.toString();
	}

	
}