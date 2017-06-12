package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsPcExtendedRatingResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsPcExtendedRatingListResp pcExtendedRatings;
	
	public SearchAvsPcExtendedRatingResp() {
		super();
		pcExtendedRatings = new AvsPcExtendedRatingListResp();
	}

	public AvsPcExtendedRatingListResp getPcExtendedRatings() {
		return pcExtendedRatings;
	}

	public void setPcExtendedRatings(AvsPcExtendedRatingListResp pcExtendedRatings) {
		this.pcExtendedRatings = pcExtendedRatings;
	}
}
