package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsPcExtendedRatingListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsPcExtendedRatingInfoResp> pcExtendedRatingList;

	@XmlElement(name = "pcExtendedRating")
	public List<AvsPcExtendedRatingInfoResp> getPcExtendedRatingList() {
		return pcExtendedRatingList;
	}

	public void setPcExtendedRatingList(List<AvsPcExtendedRatingInfoResp> pcExtendedRatingList) {
		this.pcExtendedRatingList = pcExtendedRatingList;
	}
}
