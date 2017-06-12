/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.PartyGroupInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchPartyGroupResp;

public final class PartyGroupConverter {
	
	private PartyGroupConverter(){
		
	}
	
	
	public static List<PartyGroupBean> buildPartyGroupTable(SearchPartyGroupResp response){
		List<PartyGroupInfoResp> infos=response.getPartyGroups().getPartyGroup();
		List<PartyGroupBean> beanList=new ArrayList<PartyGroupBean>();
		for(PartyGroupInfoResp info:infos){
				beanList.add(convertPartyGroupInfoToBean(info));
		}
		return beanList;
	}
	
	
	
	public static PartyGroupBean convertPartyGroupInfoToBean(PartyGroupInfoResp info){
		PartyGroupBean bean=new PartyGroupBean();
		bean.setPartyGroupDescription(info.getPartyGroupDescription());
		bean.setPartyGroupName(info.getPartyGroupName());
		bean.setPartyGroupId(info.getPartyGroupId());
		bean.setCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		return bean;
	}

}
