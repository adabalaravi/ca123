package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the AVS_Payment_Type database table.
 * 
 */
@Entity
@Table(name = "AVS_Payment_Type")
@NamedQueries({
	@NamedQuery(name = AvsPaymentType.QUERY_RETRIEVE_ALL, query = "select o from AvsPaymentType o ORDER BY o.paymentTypeId")
})
public class AvsPaymentType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsPaymentTypes";

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="payment_method")
	private String paymentMethod;

	@Id
	@Column(name="payment_type_id")
	private int paymentTypeId;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

    public AvsPaymentType() {
    }

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}