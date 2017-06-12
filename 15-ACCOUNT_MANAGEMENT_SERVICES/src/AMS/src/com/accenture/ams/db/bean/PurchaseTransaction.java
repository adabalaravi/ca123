package com.accenture.ams.db.bean;

import java.sql.Timestamp;

public class PurchaseTransaction {

	private Long sequenceId;
	private String currency;
	private String originalPrice;
	private String discountedPrice;
	private Timestamp startDate;
	private Timestamp endDate;
	private Long userId;
	private String itemId;
	private Long itemType;
	private Long state;
	private String token;
	private String transactionId;
	private String refundTransactionId;
	private String pgwStatus;
	private Long paymentTypeId;
	private String payerId;
	private Timestamp refundDate;
	private String refundDescription;
	private String refundPrice;
	private Long parentItemId;
	
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPrice() {
		return originalPrice;
	}
	public void setPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Long getItemType() {
		return itemType;
	}
	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRefundTransactionId() {
		return refundTransactionId;
	}
	public void setRefundTransactionId(String refundTransactionId) {
		this.refundTransactionId = refundTransactionId;
	}
	public String getPgwStatus() {
		return pgwStatus;
	}
	public void setPgwStatus(String pgwStatus) {
		this.pgwStatus = pgwStatus;
	}
	public Long getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public Timestamp getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Timestamp refundDate) {
		this.refundDate = refundDate;
	}
	public String getRefundDescription() {
		return refundDescription;
	}
	public void setRefundDescription(String refundDescription) {
		this.refundDescription = refundDescription;
	}
	public String getRefundPrice() {
		return refundPrice;
	}
	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}
	public Long getParentItemId() {
		return parentItemId;
	}
	public void setParentItemId(Long parentItemId) {
		this.parentItemId = parentItemId;
	}
	
}
