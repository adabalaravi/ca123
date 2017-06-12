package com.accenture.ams.pkgingestor.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.jws.WebService;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.AccountMgmtService_AccountMgmtServicePortImpl;
import com.accenture.ams.accountmgmtservice.packageIngestor.PackageIngestorWS;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.pkgingestor.PackageIngestorPort;
import com.accenture.ams.pkgingestor.PackageIngestorRequest;
import com.accenture.ams.pkgingestor.PackageIngestorResponse;
import com.sun.xml.ws.developer.SchemaValidation;

@SchemaValidation(handler = com.accenture.ams.accountmgmtservice.InputRequestError.class)
@WebService(serviceName = "PackageIngestorService", endpointInterface = "com.accenture.ams.pkgingestor.PackageIngestorPort", targetNamespace = "http://ws.be.avs.accenture.com/PackageIngestorService-v1")
public class PackageIngestorPortImpl implements PackageIngestorPort, ErrorHandler {
	
	private boolean isRequestValid = true;
	private String requestError = "";
	
	public PackageIngestorResponse packageIngestorService(
			PackageIngestorRequest packageIngestorRequest) {
		
		LogUtil.writeCommonLog("DEBUG", PackageIngestorPortImpl.class, "INTERNAL", "PackageIngestor WS");
		// check request validity
		if (!isRequestValid)
			return getResponse(AccountMgmtServiceConstant.RET_MISSING_PARAM, -1, "Invalid Request");
		
		Map<String, String> menv = System.getenv();
		String propertyPath = menv.get(AccountMgmtServiceConstant.AMS_PROPERTIES_PATH_ENV);
		
		if (!HibernateUtil.getInstance().isInitialized()){
			if (!initTenantConfiguration(propertyPath))
				return getResponse(AccountMgmtServiceConstant.RET_MISSING_PARAM, -1, "Hibernate Error");
		}
		PackageIngestorWS service = new PackageIngestorWS(packageIngestorRequest.getTenantName());
		return service.executeService(packageIngestorRequest);
	}
		
	private PackageIngestorResponse getResponse(int code, long pkgId, String desc){
		PackageIngestorResponse response = new PackageIngestorResponse();
		response.setResultCode( AccountMgmtServiceConstant.RET_CODE[ code ] );
		response.setResultDescription( AccountMgmtServiceConstant.RET_DESC[ code ] + "|" + desc);
		response.setPackageId(pkgId);
		return response;
	}

	private boolean initTenantConfiguration(String propertyPath) {
		FileInputStream isFile = null;
		/*
		 * check if tenant.properties exist under AMS properties file path first
		 * elsewhere use the defualt tenant.properties under classpath
		 */
		String fullPath = propertyPath + AccountMgmtServiceConstant.TENANT_PROPERTIES;;
		File tenantPropertyFile = new File(fullPath);
		Properties prop = new Properties();
		
		try {
			isFile = new FileInputStream(tenantPropertyFile);
			prop.load(isFile);
		} 
		catch (FileNotFoundException e1) {
			LogUtil.writeCommonLog("DEBUG",
					AccountMgmtService_AccountMgmtServicePortImpl.class,
					"INTERNAL",
					"tenant.properties not found und properties path...use default!");
		}
		catch (IOException e1) {
			LogUtil.writeCommonLog("ERROR",
					AccountMgmtService_AccountMgmtServicePortImpl.class,
					"INTERNAL", "ERROR: Cannot read properties file. Cause : " + e1);
			return false;
		}
		return HibernateUtil.getInstance().initSessionFactories(prop);
	}
	
	public void error(SAXParseException exception) throws SAXException {
		LogUtil.writeCommonLog("DEBUG", PackageIngestorPortImpl.class, "INTERNAL", "Validation error : " + exception.getMessage());
		isRequestValid=false;
		requestError=exception.getMessage();
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		LogUtil.writeCommonLog("DEBUG", PackageIngestorPortImpl.class, "INTERNAL", "Validation fatal error" + exception.getMessage());
		isRequestValid=false;
		requestError=exception.getMessage();
	}

	public void warning(SAXParseException exception) throws SAXException {
		LogUtil.writeCommonLog("DEBUG", PackageIngestorPortImpl.class, "INTERNAL", "Validation warning" + exception.getMessage());
		
	}
}