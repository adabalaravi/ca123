package com.accenture.sdp.csm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.ParameterDto;

/**
 * 
 * 
 */

public class SearchServiceResponse extends DataServiceResponse implements
		Serializable {
	private static final long serialVersionUID = 1L;

	private List<?> searchResult;
	private Long totalResult;

	/**
	 * @return the searchResult
	 */
	public List<?> getSearchResult() {
		return searchResult;
	}

	/**
	 * @param searchResult
	 *            the searchResult to set
	 */
	public void setSearchResult(List<?> searchResult) {
		this.searchResult = searchResult;
	}

	/**
	 * @param resultCode
	 * @param description
	 */
	public SearchServiceResponse(String resultCode, String description,ParameterDto... parameters) {
		super(resultCode, description,parameters);
		this.searchResult = new ArrayList<Object>();
	}

	public Long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(Long totalResult) {
		this.totalResult = totalResult;
	}

}