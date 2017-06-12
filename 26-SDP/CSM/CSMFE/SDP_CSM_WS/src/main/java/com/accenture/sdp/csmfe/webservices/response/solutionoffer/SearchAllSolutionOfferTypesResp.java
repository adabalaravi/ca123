package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllSolutionOfferTypesResp extends BaseResp {

	private List<String> solutionOfferTypeList;

	public SearchAllSolutionOfferTypesResp() {
		solutionOfferTypeList = new ArrayList<String>();
	}

	@XmlElement(name = "solutionOfferType")
	public List<String> getSolutionOfferTypeList() {
		return solutionOfferTypeList;
	}

	public void setSolutionOfferTypeList(List<String> solutionOfferTypeList) {
		this.solutionOfferTypeList = solutionOfferTypeList;
	}

}
