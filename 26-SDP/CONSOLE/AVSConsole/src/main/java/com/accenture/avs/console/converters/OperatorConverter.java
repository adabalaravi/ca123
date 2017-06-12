package com.accenture.avs.console.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.avs.console.beans.operator.OperatorInfo;
import com.accenture.avs.console.beans.operator.TenantInfo;
import com.accenture.sdp.csmfe.webservices.clients.operators.OperatorComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.TenantInfoResp;

public final class OperatorConverter {

	private OperatorConverter() {
	}

	public static List<OperatorInfo> convertOperatorInfoToBean(List<OperatorComplexInfoResp> infos) {
		List<OperatorInfo> beanList = new ArrayList<OperatorInfo>();
		for (OperatorComplexInfoResp info : infos) {
			beanList.add(convertOperatorInfoToBean(info));
		}
		return beanList;
	}

	public static OperatorInfo convertOperatorInfoToBean(OperatorComplexInfoResp info) {
		OperatorInfo bean = new OperatorInfo();
		bean.setUsername(info.getUsername());
		bean.setFirstName(info.getFirstName());
		bean.setLastName(info.getLastName());
		bean.setStatusName(info.getStatusName());
		bean.setStatusId(info.getStatusId());
		if (info.getTenants() != null) {
			bean.setTenants(convertTenantInfoToBean(info.getTenants().getTenant()));
		}
		bean.setEmail(info.getEmail());
		return bean;
	}

	public static List<TenantInfo> convertTenantInfoToBean(List<TenantInfoResp> tenants) {
		List<TenantInfo> resultList = new ArrayList<TenantInfo>();
		if (tenants != null) {
			for (TenantInfoResp info : tenants) {
				resultList.add(convertTenantInfoToBean(info));
			}
		}
		return resultList;
	}

	public static TenantInfo convertTenantInfoToBean(TenantInfoResp info) {
		TenantInfo bean = new TenantInfo();
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatusName());
		bean.setTenantName(info.getTenantName());
		bean.setTenantDescription(info.getTenantDescription());
		return bean;
	}

}
