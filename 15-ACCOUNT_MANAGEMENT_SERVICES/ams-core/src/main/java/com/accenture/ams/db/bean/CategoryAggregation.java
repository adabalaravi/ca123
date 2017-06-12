package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CATEGORY_AGGREGATION database table.
 * 
 * @author BEA Workshop
 */
public class CategoryAggregation  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private CategoryAggregationPK compId;

	//private Category category1;
	//private Category category2;

    public CategoryAggregation() {
    }

	public CategoryAggregationPK getCompId() {
		return this.compId;
	}
	public void setCompId(CategoryAggregationPK compId) {
		this.compId = compId;
	}

	
	/*
	public Category getCategory1() {
		return this.category1;
	}
	public void setCategory1(Category category1) {
		this.category1 = category1;
	}

	
	public Category getCategory2() {
		return this.category2;
	}
	public void setCategory2(Category category2) {
		this.category2 = category2;
	}
*/
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CategoryAggregation)) {
			return false;
		}
		CategoryAggregation castOther = (CategoryAggregation)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}