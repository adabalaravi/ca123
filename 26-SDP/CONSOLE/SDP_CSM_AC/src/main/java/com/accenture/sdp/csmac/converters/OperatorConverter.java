package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.operator.OperatorBean;
import com.accenture.sdp.csmac.beans.operator.TenantBean;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.TenantInfoResp;

public abstract class OperatorConverter {

	public static OperatorBean convertOperator(OperatorComplexInfoResp info) throws ServiceErrorException {
		OperatorBean bean = new OperatorBean();
		bean.setUsername(info.getUsername());
		bean.setFirstName(info.getFirstName());
		bean.setLastName(info.getLastName());
		bean.setStatusName(info.getStatusName());
		bean.setStatusId(info.getStatusId());
		if (info.getTenants() != null) {
			bean.setTenants(convertTenants(info.getTenants().getTenant()));
		}
		bean.setEmail(info.getEmail());
		return bean;
	}

	public static List<TenantBean> convertTenants(List<TenantInfoResp> tenants) {
		List<TenantBean> resultList = new ArrayList<TenantBean>();
		if (tenants != null) {
			for (TenantInfoResp info : tenants) {
				resultList.add(convertTenant(info));
			}
		}
		return resultList;
	}

	public static TenantBean convertTenant(TenantInfoResp info) {
		TenantBean bean = new TenantBean();
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatusName());
		bean.setTenantName(info.getTenantName());
		bean.setTenantDescription(info.getTenantDescription());
		return bean;
	}

}
