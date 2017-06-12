package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the COMMERCIAL_CATEGORY database table.
 * 
 * @author BEA Workshop
 */
public class CommercialCategory  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long offerTypeId;
//	private java.sql.Timestamp creationDate;
	private java.sql.Timestamp endDate;
	private String offerTypeDescription;
	private String offerTypeName;
	private java.sql.Timestamp startDate;
	private String statusId;
//	private java.sql.Timestamp updateDate;
	private java.util.Set<Product> products;
	private java.util.Set<RelPackageCommCat> relPackageCommCats;

    public CommercialCategory() {
    }

	public Long getOfferTypeId() {
		return this.offerTypeId;
	}
	public void setOfferTypeId(Long offerTypeId) {
		this.offerTypeId = offerTypeId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public java.sql.Timestamp getEndDate() {
		return this.endDate;
	}
	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getOfferTypeDescription() {
		return this.offerTypeDescription;
	}
	public void setOfferTypeDescription(String offerTypeDescription) {
		this.offerTypeDescription = offerTypeDescription;
	}

	public String getOfferTypeName() {
		return this.offerTypeName;
	}
	public void setOfferTypeName(String offerTypeName) {
		this.offerTypeName = offerTypeName;
	}

	public java.sql.Timestamp getStartDate() {
		return this.startDate;
	}
	public void setStartDate(java.sql.Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getStatusId() {
		return this.statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to Product
	public java.util.Set<Product> getProducts() {
		return this.products;
	}
	public void setProducts(java.util.Set<Product> products) {
		this.products = products;
	}

	//bi-directional many-to-one association to RelPackageCommCat
	public java.util.Set<RelPackageCommCat> getRelPackageCommCats() {
		return this.relPackageCommCats;
	}
	public void setRelPackageCommCats(java.util.Set<RelPackageCommCat> relPackageCommCats) {
		this.relPackageCommCats = relPackageCommCats;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CommercialCategory)) {
			return false;
		}
		CommercialCategory castOther = (CommercialCategory)other;
		return new EqualsBuilder()
			.append(this.getOfferTypeId(), castOther.getOfferTypeId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOfferTypeId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("offerTypeId", getOfferTypeId())
			.toString();
	}
}