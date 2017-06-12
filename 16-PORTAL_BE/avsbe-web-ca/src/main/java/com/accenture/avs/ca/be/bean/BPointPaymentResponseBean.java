package com.accenture.avs.ca.be.bean;

//This bean is used as the input for Verification of Payment 

public class BPointPaymentResponseBean {
			private String request_resp_code;
			private String verify_token;
			private String amount;
			private String billercode;
			private String merchant_reference;
			private String crn1;
			private String crn2;
			private String crn3;
			private String response_code;
			private String bank_response_code;
			private String auth_result;
			private String txn_number;
			private String receipt_number;
			private String settlement_date;
			private String expiry_date;
			private String account_number;
			private String payment_date;
			private String store_card;
			
			public String getRequest_resp_code() {
				return request_resp_code;
			}
			public void setRequest_resp_code(String request_resp_code) {
				this.request_resp_code = request_resp_code;
			}
			public String getVerify_token() {
				return verify_token;
			}
			public void setVerify_token(String verify_token) {
				this.verify_token = verify_token;
			}
			public String getAmount() {
				return amount;
			}
			public void setAmount(String amount) {
				this.amount = amount;
			}
			public String getBillercode() {
				return billercode;
			}
			public void setBillercode(String billercode) {
				this.billercode = billercode;
			}
			public String getMerchant_reference() {
				return merchant_reference;
			}
			public void setMerchant_reference(String merchant_reference) {
				this.merchant_reference = merchant_reference;
			}
			public String getCrn1() {
				return crn1;
			}
			public void setCrn1(String crn1) {
				this.crn1 = crn1;
			}
			public String getCrn2() {
				return crn2;
			}
			public void setCrn2(String crn2) {
				this.crn2 = crn2;
			}
			public String getCrn3() {
				return crn3;
			}
			public void setCrn3(String crn3) {
				this.crn3 = crn3;
			}
			public String getResponse_code() {
				return response_code;
			}
			public void setResponse_code(String response_code) {
				this.response_code = response_code;
			}
			public String getBank_response_code() {
				return bank_response_code;
			}
			public void setBank_response_code(String bank_response_code) {
				this.bank_response_code = bank_response_code;
			}
			public String getAuth_result() {
				return auth_result;
			}
			public void setAuth_result(String auth_result) {
				this.auth_result = auth_result;
			}
			public String getTxn_number() {
				return txn_number;
			}
			public void setTxn_number(String txn_number) {
				this.txn_number = txn_number;
			}
			public String getReceipt_number() {
				return receipt_number;
			}
			public void setReceipt_number(String receipt_number) {
				this.receipt_number = receipt_number;
			}
			public String getSettlement_date() {
				return settlement_date;
			}
			public void setSettlement_date(String settlement_date) {
				this.settlement_date = settlement_date;
			}
			public String getExpiry_date() {
				return expiry_date;
			}
			public void setExpiry_date(String expiry_date) {
				this.expiry_date = expiry_date;
			}
			public String getAccount_number() {
				return account_number;
			}
			public void setAccount_number(String account_number) {
				this.account_number = account_number;
			}
			public String getPayment_date() {
				return payment_date;
			}
			public void setPayment_date(String payment_date) {
				this.payment_date = payment_date;
			}
			public String getStore_card() {
				return store_card;
			}
			public void setStore_card(String store_card) {
				this.store_card = store_card;
			}
			
			
}
