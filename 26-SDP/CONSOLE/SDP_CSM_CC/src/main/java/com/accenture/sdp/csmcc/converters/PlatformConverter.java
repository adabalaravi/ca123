/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.platform.PlatformComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformComplexRespPaginated;



public final class PlatformConverter implements Serializable {

	private static final long serialVersionUID = 1L;


	public static List<PlatformBean> buildPlatformTable(SearchPlatformComplexRespPaginated response){
		List<PlatformComplexInfoResp> infos=response.getPlatforms().getPlatform();
		List<PlatformBean> beanList=new ArrayList<PlatformBean>();
		for(PlatformComplexInfoResp info:infos){
			beanList.add(convertPlatformInfoToBean(info));
		}

		return beanList;
	}

	public static PlatformBean convertPlatformInfoToBean(PlatformComplexInfoResp info){
		PlatformBean bean=new PlatformBean();
		if (info.getCreatedDate()!=null) {
			bean.setPlatformCreationDate(Utilities.formatDate((((XMLGregorianCalendar)info.getCreatedDate()).toGregorianCalendar().getTime())));
		}
		bean.setPlatformDesc(info.getPlatformDescription());
		bean.setPlatformExtId(info.getExternalId());
		bean.setPlatformId(info.getPlatformId());
		bean.setPlatformName(info.getPlatformName());
		bean.setPlatformProfile(info.getPlatformProfile());
		bean.setPlatformStatus(info.getStatusName());
		return bean;
	}



	private PlatformConverter() {

	}



}
