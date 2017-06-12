package com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.accenture.ams.db.bean.AccountProduct;
import com.accenture.ams.db.bean.AccountProductBean;
import com.accenture.ams.db.bean.AccountProductPK;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.AccountTechnicalPkgPK;
import com.accenture.ams.db.bean.AccountUser;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.db.bean.PurchaseTransaction;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.MissingParameterException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.crmContentPurchase.ConfigurationData;
import com.accenture.ams.accountmgmtservice.crmContentPurchase.TechPackageBean;
import com.accenture.ams.accountmgmtservice.updateUserServices.UpdateUserService;

import commontypes.accountmgmt.avs.accenture.ExternalOfferListType;
import commontypes.accountmgmt.avs.accenture.ExternalOfferType;
import commontypes.accountmgmt.avs.accenture.UpdateProfileType;

public class InsertCrmAccCommProfileManager {

	private ArrayList<AccountProductBean> accProductList = new ArrayList<AccountProductBean>();
	private ArrayList<AccountTechnicalPkg> listOfAccTechPack = new ArrayList<AccountTechnicalPkg>();
	private ArrayList<PurchaseTransaction> listOfPurchaseTransaction = new ArrayList<PurchaseTransaction>();
	
	private AccountUserBean au = null;
	private String crmAccountId = null;
	private String tenantName = null;
	
	private ConfigurationData cd = null;

	/**
	 * List of packageId and externalOfferId involved in call
	 */
	private ArrayList<String> listOfPackageId = new ArrayList<String>();
	private ArrayList<String> listOfExternaOfferId = new ArrayList<String>();
	
	public InsertCrmAccCommProfileManager(String _tenantName){
		tenantName = _tenantName;
	}
	
	public void setAccountUser(AccountUserBean _au){
		au = _au;
	}
	
	public void setCrmAccountId(String _crmAccountId){
		crmAccountId = _crmAccountId;
	}
	
	public ArrayList<AccountProductBean> getAccountProductList(){
		return accProductList;
	}
	
	public ArrayList<AccountTechnicalPkg> getAccountTechnicalPkgList(){
		return listOfAccTechPack;
	}
	
	public ArrayList<PurchaseTransaction> getPurchaseTransactionList(){
		return listOfPurchaseTransaction;
	}
	
	public boolean createBeans(List<UpdateProfileType> pList, String opType) throws MissingParameterException{
		if (au==null)
			throw new MissingParameterException("AccountUser is not setted");
		if ( !Utils.isValorized(crmAccountId) )
			throw new MissingParameterException("crmAccountId is not setted");

		Iterator<UpdateProfileType>pIt = pList.iterator();
		LogUtil.writeCommonLog("INFO", InsertCrmAccCommProfileManager.class, "WS",
				"I'm in InsertCrmAccCommProfileManager.createBeans for crm account id: "+crmAccountId);
		Long currPackageId=null;
		String currPackageValue=null;
		
		cd = new ConfigurationData();
		cd.setTenantName(tenantName);
		cd.setCrmAccountId(crmAccountId);
		
		while ( pIt.hasNext() ){
			UpdateProfileType pItem = pIt.next();
			if (pItem.getOperationType().value().equalsIgnoreCase( opType ) ){
				
				Long crmProductId = pItem.getCrmProductId();
				addAccountProductBean( crmProductId.longValue() );
				
				List<ExternalOfferType> eolt = pItem.getExternalOfferList().getExternalOffer();
				Iterator<ExternalOfferType> eoltIt = eolt.iterator();
				
				while (eoltIt.hasNext()){
					ExternalOfferType eot = eoltIt.next();
					listOfPackageId.add(eot.getPackageId());
					listOfExternaOfferId.add(eot.getExternalOfferId());
				}
				
				cd.setListOfExternaOfferId(listOfExternaOfferId);
				cd.setListOfPackageId(listOfPackageId);
				
				try {
					cd.load();
				} 
				catch (Exception e1) {
					LogUtil.writeErrorLog(InsertCrmAccCommProfileManager.class, "ERROR LOADING CONFIGURATION DATA", e1);
					throw new MissingParameterException(e1.getMessage());
				}
				try {
					List<Object[]> techPacks = AccountManagementDAO.getCommercialCategory(crmProductId.intValue(), tenantName);
					Iterator<Object[]> techPackIterator = techPacks.iterator();
					
					while ( techPackIterator.hasNext() ){
						Object[] currRec = techPackIterator.next();
						currPackageId = (Long)currRec[1];
						currPackageValue = (String)currRec[2];
						addTechnicalPackages(currPackageId, currPackageValue);						
					}										
				} 
				catch (Exception e) {
					LogUtil.writeErrorLog(InsertCrmAccCommProfileManager.class, "ERROR", e);
					throw new MissingParameterException(e.getMessage());
				}
			}
		}
		
		
		return true;
	}
	
	public void clean(){
		accProductList.clear();
		listOfAccTechPack.clear();
	}
	
	private void addAccountProductBean(Long solutionOfferId){
		AccountProductBean ap = new AccountProductBean();
		
		//ap.setUserId(userId)
	
		AccountProductPK apPK = new AccountProductPK();
		apPK.setUsername(au.getUsername());
		apPK.setSolutionOfferId(solutionOfferId);
		
		ap.setCompId(apPK);
		accProductList.add(ap);
	}
	
	private void addTechnicalPackages(Long techPackageId, String techPackageValue){		
		AccountTechnicalPkg atp = new AccountTechnicalPkg();
		atp.setUserId(au.getUsername());
		atp.setTechPackageId(techPackageId);

		listOfAccTechPack.add(atp);
	}

	private void addPurchaseTransaction(String packageId, String externalOfferId){
		PurchaseTransaction ptb = new PurchaseTransaction();
		
		/* QUESTI VALORI ANDRANNO LETTI TRAMITE SPECIFICA INTERFACCIA */
		String currency="EUR";
		String price="12.12";
		String discountedPrice="4.32";
		
		ptb.setCurrency(currency);
		ptb.setPrice(price);
		ptb.setDiscountedPrice(discountedPrice);
//		ptb.setStartDate(Utils.getDate(null));
//		ptb.setEndDate(Utils.getDate(15));
		
		ptb.setUserId(au.getUsername());
		//TechPackageBean tpb = mapOfTechPkg.get(item.getPackageId());
		
		//String itemId = tpb.getExternalId();
		//String startDate = tpb.getStartDate();
		String endDate = null;
		
//		if ( tpb.getEndDate()!=null && tpb.getEndDate().length()>0 ){
//			endDate = tpb.getEndDate();
//		}
//		else if ( tpb.getValidityPeriod()!=null && tpb.getValidityPeriod().length()>0 ) {
//			Utils.getDateString(null);
//		}
		
		//ptb.setItemId( itemId );
		
//		Long itemType = purchaseTransactionTypeMap.get("TVOD");
//		ptb.setItemType(itemType);
		
		//Long stateId = statusTypeMap.get(AccountMgmtServiceConstant.PURCHASE_STATUS_COMPLETED);
		//ptb.setState(stateId);
		
		//Long paymentTypeId = paymentTypeMap.get(AccountMgmtServiceConstant.DEF_PURCHASE_TYPE);
		//ptb.setPaymentTypeId(paymentTypeId);

		return;		
	}
}
