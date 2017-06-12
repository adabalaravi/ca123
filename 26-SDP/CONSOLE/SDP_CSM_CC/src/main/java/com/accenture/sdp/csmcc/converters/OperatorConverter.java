package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.OperatorInfo;
import com.accenture.sdp.csmcc.beans.TenantBean;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.TenantInfoResp;

public abstract class OperatorConverter {

	public static OperatorInfo convertOperatorInfoToBean(OperatorComplexInfoResp info) throws ServiceErrorException{
		OperatorInfo bean=new OperatorInfo();
		bean.setUsername(info.getUsername());
		bean.setFirstName(info.getFirstName());
		bean.setLastName(info.getLastName());
		bean.setStatusName(info.getStatusName());
		bean.setStatusId(info.getStatusId());
		if (info.getTenants()!=null){
			bean.setTenants(convertTenantInfoToBean(info.getTenants().getTenant()));
		}
		bean.setEmail(info.getEmail());
		return bean;
	}

	public static List<TenantBean> convertTenantInfoToBean(List<TenantInfoResp> tenants) {
		List<TenantBean> resultList = new ArrayList<TenantBean>();
		if (tenants!=null) {
			for (TenantInfoResp info : tenants){
				resultList.add(convertTenantInfoToBean(info));
			}
		}
		return resultList;
	}

	public static TenantBean convertTenantInfoToBean(TenantInfoResp info) {
		TenantBean bean=new TenantBean();
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatusName());
		bean.setTenantName(info.getTenantName());
		bean.setTenantDescription(info.getTenantDescription());
		return bean;
	}

}
