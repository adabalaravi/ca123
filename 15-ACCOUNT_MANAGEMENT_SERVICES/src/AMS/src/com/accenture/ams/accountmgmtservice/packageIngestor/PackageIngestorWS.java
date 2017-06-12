package com.accenture.ams.accountmgmtservice.packageIngestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.TechnicalPackage;
import com.accenture.ams.db.bean.TechnicalPackageAttribute;
import com.accenture.ams.db.dao.PkgIngestorDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.pkgingestor.PackageIngestorRequest;
import com.accenture.ams.pkgingestor.PackageIngestorResponse;
import com.accenture.ams.pkgingestor.impl.PackageIngestorPortImpl;

public class PackageIngestorWS {
	

	
	private String tenantName=null;
	private String packageDescription=null;
	private String isEnabled=null;
	private Long externalOfferId=null;
	private String packageName=null;
	private String packageType=null;
	private Long validityPeriod=null;
	private Long numberOfView=null;
	private Long validityEndDate=null;
	private Long validityStartDate=null;
	private Long packageId=null;
	private HashMap<String, Long> attributeMapping=null;
	
	public PackageIngestorWS(String _tenantName){
		try{
			tenantName = _tenantName;
			attributeMapping = PkgIngestorDAO.getTechPackageAttributeSet(AccountMgmtServiceConstant.PKG_ATTRIBUTE_SET, tenantName);
		}
		catch(Exception e){
			LogUtil.writeErrorLog(PackageIngestorWS.class,
					"Error retrieving TechnicalPackage attribute set ",
					e);
			attributeMapping=null;
		}
	}
	
	public PackageIngestorResponse executeService(PackageIngestorRequest req){
		//if attributeMapping is null there was an error during initilization
		if (attributeMapping==null)
			return getResponse(AccountMgmtServiceConstant.RET_DB_ERROR, -1);
		//read request data
		readRequestData(req);
		// check if package is ingested on db....
		TechnicalPackage tpBean = null;
		List<TechnicalPackageAttribute> tpaBeanList = new ArrayList<TechnicalPackageAttribute>();
		try{	
			tpBean = PkgIngestorDAO.getTechnicalPackage(Long.toString(externalOfferId), tenantName);
		}
		catch(Exception e){
			LogUtil.writeErrorLog(PackageIngestorPortImpl.class,
					"Error retrieving TechnicalPackage bean ",
					e);
			return getResponse(AccountMgmtServiceConstant.RET_DB_ERROR, -1);
		}
		//if package is alredy created this is an UPDATE operation, store packageId
		//and retrieve TechnicalPackage Attribute for this package
		if (tpBean!=null){
			packageId=tpBean.getPackageId();
		}
		
		// set data into bean (update if tbBean!=null, insert otherwise)
		tpBean = setTechPackageHibernateBean(tpBean);
		//try to set technical package attribute now if packageId is not null
		setTechPacakgeAttributeHibernateBean(tpaBeanList, packageId);
		//save/update bean
		try{
			PkgIngestorDAO.saveUpdate(tpBean, tenantName);
		}
		catch(Exception e){
			LogUtil.writeErrorLog(PackageIngestorPortImpl.class,
					"Error saving TechnicalPackage bean ",
					e);
			return getResponse(AccountMgmtServiceConstant.RET_DB_ERROR, -1);			
		}
		
		//read bean again to retrieve packageId ONLY IF this is a new package
		if (packageId==null){
			try{	
				tpBean = PkgIngestorDAO.getTechnicalPackage(Long.toString(externalOfferId), tenantName);
				packageId = tpBean.getPackageId();
				//set technical package attribute new package
				setTechPacakgeAttributeHibernateBean(tpaBeanList, packageId);
			}
			catch(Exception e){
				LogUtil.writeErrorLog(PackageIngestorPortImpl.class,
						"Error retrieving TechnicalPackage bean ",
						e);
				return getResponse(AccountMgmtServiceConstant.RET_DB_ERROR, -1);			
			}
		}
		try{
			PkgIngestorDAO.cleanTechPkgAttribute(packageId, tenantName);
			PkgIngestorDAO.saveUpdate(tpaBeanList, tenantName);
		}
		catch(Exception e){
			LogUtil.writeErrorLog(PackageIngestorPortImpl.class,
					"Error saving TechnicalPackage bean ",
					e);
			return getResponse(AccountMgmtServiceConstant.RET_DB_ERROR, -1);			
		}			
		return getResponse(AccountMgmtServiceConstant.RET_OK, packageId);			
	}
	
	private void readRequestData(PackageIngestorRequest request){
		tenantName = request.getTenantName();
		packageDescription = request.getPackageDescription();
		isEnabled = request.getIsEnabled().value();
		externalOfferId = request.getExternalOfferId();
		packageName = request.getPackageName();
		packageType = request.getPackageType();
		validityPeriod = request.getValidityPeriod();
		numberOfView = request.getViewNumber();
		
		if ( request.getValidityEndDate()!=null )
			validityEndDate = request.getValidityEndDate().toGregorianCalendar().getTimeInMillis();
		if ( request.getValidityStartDate()!=null ) 
			validityStartDate = request.getValidityStartDate().toGregorianCalendar().getTimeInMillis();		
	}
	
	private TechnicalPackage setTechPackageHibernateBean(TechnicalPackage bean){
		if (bean==null)
			bean = new TechnicalPackage();
		
		bean.setPackageName(packageName);
		bean.setPackageType(packageType);
		bean.setPackageDescription(packageDescription);
		bean.setIsEnabled(isEnabled);
		bean.setExternalId( Long.toString(externalOfferId) );
		return bean;
	}
	
	private void setTechPacakgeAttributeHibernateBean(List<TechnicalPackageAttribute> attrList, Long packageId){
		if (packageId==null)
			return;

		TechnicalPackageAttribute tpaViewNum = new TechnicalPackageAttribute();
		TechnicalPackageAttribute tpaValidityPeriod = new TechnicalPackageAttribute();
		TechnicalPackageAttribute tpaValidityEndDate = new TechnicalPackageAttribute();
		TechnicalPackageAttribute tpaValidityStartDate = new TechnicalPackageAttribute();		
		
		if (numberOfView!=null){
			Long attrId = attributeMapping.get(AccountMgmtServiceConstant.PKG_VIEWS_NUMBER);
			tpaViewNum.setAttributeDetailId(attrId);
			tpaViewNum.setPackageId(packageId);
			tpaViewNum.setAttributeValue( Long.toString(numberOfView) );
			attrList.add(tpaViewNum);
		}
		if (validityPeriod!=null){
			Long attrId = attributeMapping.get(AccountMgmtServiceConstant.PKG_VALIDITY_PERIOD);
			tpaValidityPeriod.setAttributeDetailId(attrId);
			tpaValidityPeriod.setPackageId(packageId);
			tpaValidityPeriod.setAttributeValue( Long.toString(validityPeriod) );
			attrList.add(tpaValidityPeriod);
		}
		if (validityStartDate!=null){
			Long attrId = attributeMapping.get(AccountMgmtServiceConstant.PKG_START_DATE);
			tpaValidityStartDate.setAttributeDetailId(attrId);
			tpaValidityStartDate.setPackageId(packageId);
			tpaValidityStartDate.setAttributeValue( validityStartDate.toString() );
			attrList.add(tpaValidityStartDate);
		}
		if (validityEndDate!=null){
			Long attrId = attributeMapping.get(AccountMgmtServiceConstant.PKG_END_DATE);
			tpaValidityEndDate.setAttributeDetailId(attrId);
			tpaValidityEndDate.setPackageId(packageId);
			tpaValidityEndDate.setAttributeValue( validityEndDate.toString() );
			attrList.add(tpaValidityEndDate);
		}		
	}
	
	private void updateTechPackageAttribute(List<TechnicalPackageAttribute> attrList){
		
		Iterator<TechnicalPackageAttribute> it = attrList.iterator();
		while (it.hasNext()){
			
		}
	}
	
	private PackageIngestorResponse getResponse(int code, long pkgId){
		PackageIngestorResponse response = new PackageIngestorResponse();
		response.setResultCode( AccountMgmtServiceConstant.RET_CODE[ code ] );
		response.setResultDescription( AccountMgmtServiceConstant.RET_DESC[ code ]);
		response.setPackageId(pkgId);
		return response;
	}
}
