package com.accenture.sdp.csmfe.webservices.request.operators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyOperatorRoleRightListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8134692075404639277L;

	private List<ModifyOperatorRoleRightInfoRequest> rightList;

	public ModifyOperatorRoleRightListRequest() {
		rightList = new ArrayList<ModifyOperatorRoleRightInfoRequest>();
	}

	@XmlElement(name = "right")
	public List<ModifyOperatorRoleRightInfoRequest> getRightList() {
		return rightList;
	}

	public void setRightList(List<ModifyOperatorRoleRightInfoRequest> rightList) {
		this.rightList = rightList;
	}

}
