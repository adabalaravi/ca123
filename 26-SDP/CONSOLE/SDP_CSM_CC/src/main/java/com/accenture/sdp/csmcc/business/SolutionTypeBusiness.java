/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.SolutionTypeBean;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.SolutionTypeConverter;
import com.accenture.sdp.csmcc.services.SolutionTypeService;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SearchSolutionTypesResp;

public final class SolutionTypeBusiness implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private SolutionTypeBusiness(){
		
	}
	
	public  static List<SolutionTypeBean> searchAllSolutionTypes() throws ServiceErrorException{
		SolutionTypeService service = Utilities.findBean(ApplicationConstants.SOLUTIONTYPE_SERVICE_BEAN_NAME, SolutionTypeService.class);
		SearchSolutionTypesResp resp=service.searchAllSolutionTypes(Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			return (List<SolutionTypeBean>) SolutionTypeConverter.buildSolutionTypeTable(resp);
		} else {
			return new ArrayList<SolutionTypeBean>();
		}
		
	}
	
}
