package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpRoleDto;
import com.accenture.sdp.csmfe.webservices.response.role.RoleInfoResp;

public class RoleBeanConverter extends BaseBeanConverter {

	public static List<RoleInfoResp> convertRole(List<SdpRoleDto> rdtos) {
		if (rdtos == null) {
			return null;
		}
		List<RoleInfoResp> rinfos = new ArrayList<RoleInfoResp>();
		for (SdpRoleDto r : rdtos) {
			RoleInfoResp rinfo = new RoleInfoResp();
			convertBaseInfo(r, rinfo);
			rinfo.setRoleName(r.getRoleName());
			rinfo.setRoleDescription(r.getRoleDescription());
			rinfos.add(rinfo);

		}
		return rinfos;
	}
}
