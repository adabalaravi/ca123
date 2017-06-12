/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.ServiceTypeBean;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.ServiceTypeConverter;
import com.accenture.sdp.csmcc.services.ServiceTypeService;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SearchServiceTypesResp;

public final class ServiceTypeBusiness {
	
	private ServiceTypeBusiness(){
		
	}
	
	
	public static List<ServiceTypeBean> searchAllServiceTypes() throws ServiceErrorException{
		ServiceTypeService service = Utilities.findBean("serviceTypeService", ServiceTypeService.class);
		SearchServiceTypesResp resp=service.searchAllServiceTypes(Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			return (List<ServiceTypeBean>) ServiceTypeConverter.buildServiceTypeTable(resp);
		} else {
			return new ArrayList<ServiceTypeBean>();
		}
		
	}
}
