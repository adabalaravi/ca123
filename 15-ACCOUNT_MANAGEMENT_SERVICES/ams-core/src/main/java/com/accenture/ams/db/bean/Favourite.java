package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the FAVOURITE database table.
 * 
 * @author BEA Workshop
 */
public class Favourite  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private FavouritePK compId;
	private long NOrder;

    public Favourite() {
    }

	public FavouritePK getCompId() {
		return this.compId;
	}
	public void setCompId(FavouritePK compId) {
		this.compId = compId;
	}

	public long getNOrder() {
		return this.NOrder;
	}
	public void setNOrder(long NOrder) {
		this.NOrder = NOrder;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Favourite)) {
			return false;
		}
		Favourite castOther = (Favourite)other;
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