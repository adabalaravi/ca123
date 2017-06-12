package com.accenture.sdp.csmfe.webservices.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class BaseRespPaginated extends BaseResp{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1544571646096914722L;

	private Long totalResult;

	public Long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(Long totalResult) {
		this.totalResult = totalResult;
	}
}
