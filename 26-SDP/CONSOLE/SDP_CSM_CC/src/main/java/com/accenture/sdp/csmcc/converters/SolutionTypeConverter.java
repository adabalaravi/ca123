/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.SolutionTypeBean;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SearchSolutionTypesResp;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SolutionTypeInfoResp;

public final class SolutionTypeConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	private SolutionTypeConverter(){
		
	}
	
	
	public static List<SolutionTypeBean> buildSolutionTypeTable(SearchSolutionTypesResp response){
		List<SolutionTypeInfoResp> infos=response.getSolutionTypeList().getSolutionType();
		List<SolutionTypeBean> beanList=new ArrayList<SolutionTypeBean>();
		for(SolutionTypeInfoResp info:infos){
				beanList.add(convertSolutionTypeInfoToBean(info));
		}
		return beanList;
	}
	
	
	
	public static SolutionTypeBean convertSolutionTypeInfoToBean(SolutionTypeInfoResp info){
		SolutionTypeBean bean=new SolutionTypeBean();
		bean.setSolutionTypeId(info.getSolutionTypeId());
		bean.setSolutionTypeName(info.getSolutionTypeName());
		return bean;
	}

	

}
