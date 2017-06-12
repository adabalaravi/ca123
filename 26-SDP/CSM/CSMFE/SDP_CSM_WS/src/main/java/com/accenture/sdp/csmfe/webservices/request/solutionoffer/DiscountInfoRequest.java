package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DiscountInfoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5807306333999245139L;

	private Long packageId;
	private BigDecimal discountAbsRc;
	private BigDecimal discountAbsNrc;
	private BigDecimal discountPercRc;
	private BigDecimal discountPercNrc;
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
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
