package com.accenture.avs.ca.be.json.bean;

import java.io.Serializable;

public class ContentPurchaseHistoryCA implements Serializable{

	private static final long serialVersionUID = 1L;
		private UserPurchaseTransactionCA[] userPurchasesTransactions = null;
		
		public UserPurchaseTransactionCA[] getUserPurchasesTransactions() {
			return userPurchasesTransactions;
		}

		public void setUserPurchasesTransactions(
				UserPurchaseTransactionCA[] userPurchasesTransactions) {
			this.userPurchasesTransactions = userPurchasesTransactions;
		}

		private UserEligibleChannelsCA userEligibleChannels = null;
		
		  	

		public UserEligibleChannelsCA getUserEligibleChannels() {
			return userEligibleChannels;
		}

		public void setUserEligibleChannels(UserEligibleChannelsCA userEligibleChannels) {
			this.userEligibleChannels = userEligibleChannels;
		}

		
		
		
		  
		  
		}
