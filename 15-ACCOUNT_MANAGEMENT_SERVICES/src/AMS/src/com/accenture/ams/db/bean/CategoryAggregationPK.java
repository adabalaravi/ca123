package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CATEGORY_AGGREGATION database table.
 * 
 * @author BEA Workshop
 */
public class CategoryAggregationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long categoryId;
	private Long leafCategoryId;

    public CategoryAggregationPK() {
    }

	public Long getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getLeafCategoryId() {
		return this.leafCategoryId;
	}
	public void setLeafCategoryId(Long leafCategoryId) {
		this.leafCategoryId = leafCategoryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CategoryAggregationPK)) {
			return false;
		}
		CategoryAggregationPK castOther = (CategoryAggregationPK)other;
		return new EqualsBuilder()
			.append(this.getCategoryId(), castOther.getCategoryId())
			.append(this.getLeafCategoryId(), castOther.getLeafCategoryId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCategoryId())
			.append(getLeafCategoryId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("categoryId", getCategoryId())
			.append("leafCategoryId", getLeafCategoryId())
			.toString();
	}
}