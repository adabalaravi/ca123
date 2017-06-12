/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.accenture.sdp.csmcc.beans.SolutionBean;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.solution.SearchSolutionComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solution.SolutionComplexInfoResp;

public final class SolutionConverter {

	public static List<SolutionBean> buildSolutionTable(SearchSolutionComplexRespPaginated response){
		List<SolutionComplexInfoResp> infos=response.getSolutions().getSolution();
		List<SolutionBean> beanList=new ArrayList<SolutionBean>();
		for(SolutionComplexInfoResp info:infos){
			beanList.add(convertSolutionInfoToBean(info));
		}
		return beanList;
	}

	public static List<SolutionBean> buildSolutionTable(SearchSolutionComplexResp response){
		List<SolutionComplexInfoResp> infos=response.getSolutions().getSolution();
		List<SolutionBean> beanList=new ArrayList<SolutionBean>();
		for(SolutionComplexInfoResp info:infos){
			beanList.add(convertSolutionInfoToBean(info));
		}
		return beanList;
	}



	public static SolutionBean convertSolutionInfoToBean(SolutionComplexInfoResp info){
		SolutionBean bean=new SolutionBean();

		if(info.getCreatedDate() != null) {
			bean.setSolutionCreationDate(info.getCreatedDate().toGregorianCalendar().getTime());
		}
		XMLGregorianCalendar startCalendar=info.getStartDate();
		if (startCalendar!=null) {
			bean.setSolutionStartDate(startCalendar.toGregorianCalendar().getTime());
		}
		XMLGregorianCalendar endCalendar=info.getEndDate();
		if (endCalendar!=null) {
			bean.setSolutionEndDate(endCalendar.toGregorianCalendar().getTime());
		}
		bean.setSolutionDesc(info.getSolutionDescription());
		bean.setSolutionExtId(info.getExternalId());
		bean.setSolutionId(info.getSolutionId());
		bean.setSolutionName(info.getSolutionName());
		bean.setSolutionProfile(info.getProfile());
		bean.setSolutionStatus(info.getStatusName());
		bean.setSolutionTypeId(info.getSolutionTypeId());
		bean.setSolutionTypeName(info.getSolutionTypeName());
		return bean;
	}









	private SolutionConverter() {

	}



}
