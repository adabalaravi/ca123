package com.accenture.ams.accountmgmtservice.crmContentPurchase;

import java.util.ArrayList;
import java.util.HashMap;

import com.accenture.ams.db.bean.LiveContent;
import com.accenture.ams.db.dao.CrmPurchaseDAO;

public class ConfigurationData {
	
	private String tenantName;
	private String crmAccountId;
	
	/**
	 * List of packageId and externalOfferId involved in call
	 */
	private ArrayList<String> listOfPackageId = null;
	private ArrayList<String> listOfExternaOfferId = null;
	
	/**
	 * HashMap of Technical Package involved in this call
	 * Map key is PackageId value
	 * Map value is a TechPackageBean object
	 */
	private HashMap<Long, TechPackageBean> mapOfTechPkg = null;
	private HashMap<Long, LiveContent> mapOfLiveContent = null;
	
	/**
	 * UserId corresponding to crmAccountId in the call
	 */
	private Long userId = null;
	
	/**
	 * HashMap of payment type method
	 * Key is payment type description
	 * Value is payment type id
	 */
	private HashMap<String, Long> paymentTypeMap = null;
	
	/**
	 * HashMap of status type
	 * Key is status type description
	 * Value is status type id
	 */
	private HashMap<String, Long> statusTypeMap = null;
	
	/**
	 * HashMap of Purchase Transaction Type
	 * key is transaction description
	 * value is transaction type id
	 */
	private HashMap<String, Long> purchaseTransactionTypeMap = null;
	
	public ConfigurationData(String _tenantName, ArrayList<String> _listOfPackageId,
			ArrayList<String> _listOfExternaOfferId, String _crmAccountId){
		tenantName = _tenantName;
		listOfPackageId = _listOfPackageId;
		listOfExternaOfferId = _listOfExternaOfferId;
		crmAccountId = _crmAccountId;
	}
	
	public ConfigurationData(){}
	
	public void load() throws Exception{
		Object[] values = CrmPurchaseDAO.loadData(tenantName, listOfPackageId,
				listOfExternaOfferId, crmAccountId);
		
		if ( values[0]!=null )
			mapOfTechPkg = (HashMap<Long, TechPackageBean>)values[0];
		if ( values[1]!=null )
			userId = (Long)values[1];
		if ( values[2]!=null )
			statusTypeMap = (HashMap<String, Long>)values[2];
		if ( values[3]!=null )
			paymentTypeMap = (HashMap<String, Long>)values[3];
		if ( values[4]!=null )
			purchaseTransactionTypeMap = (HashMap<String, Long>)values[4];	
	}
	
	public HashMap<Long, TechPackageBean> getMapOfTechPkg() {
		return mapOfTechPkg;
	}

	public HashMap<Long, LiveContent> getMapOfLiveContent() {
		return mapOfLiveContent;
	}

	public Long getUserId() {
		return userId;
	}

	public HashMap<String, Long> getPaymentTypeMap() {
		return paymentTypeMap;
	}

	public HashMap<String, Long> getStatusTypeMap() {
		return statusTypeMap;
	}

	public HashMap<String, Long> getPurchaseTransactionTypeMap() {
		return purchaseTransactionTypeMap;
	}

	public void setListOfPackageId(ArrayList<String> listOfPackageId) {
		this.listOfPackageId = listOfPackageId;
	}

	public void setListOfExternaOfferId(ArrayList<String> listOfExternaOfferId) {
		this.listOfExternaOfferId = listOfExternaOfferId;
	}

	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
}
