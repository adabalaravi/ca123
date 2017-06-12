package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyDiscountInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5146583801989129162L;
	
	private Long discountId;
	private BigDecimal discountAbsRc;
	private BigDecimal discountAbsNrc;
	private BigDecimal discountPercRc;
	private BigDecimal discountPercNrc;
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public BigDecimal getDiscountAbsRc() {
		return discountAbsRc;
	}
	public void setDiscountAbsRc(BigDecimal discountAbsRc) {
		this.discountAbsRc = discountAbsRc;
	}
	public BigDecimal getDiscountAbsNrc() {
		return discountAbsNrc;
	}
	public void setDiscountAbsNrc(BigDecimal discountAbsNrc) {
		this.discountAbsNrc = discountAbsNrc;
	}
	public BigDecimal getDiscountPercRc() {
		return discountPercRc;
	}
	public void setDiscountPercRc(BigDecimal discountPercRc) {
		this.discountPercRc = discountPercRc;
	}
	public BigDecimal getDiscountPercNrc() {
		return discountPercNrc;
	}
	public void setDiscountPercNrc(BigDecimal discountPercNrc) {
		this.discountPercNrc = discountPercNrc;
	}
	
	
}
