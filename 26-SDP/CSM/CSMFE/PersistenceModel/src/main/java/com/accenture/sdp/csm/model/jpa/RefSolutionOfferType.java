package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "REF_SOLUTION_OFFER_TYPE")
@NamedQueries({ @NamedQuery(name = RefSolutionOfferType.SOLUTION_OFFER_TYPE_RETRIEVE_ALL, query = "select st from RefSolutionOfferType st ORDER BY st.solutionOfferTypeName") })
public class RefSolutionOfferType implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SOLUTION_OFFER_TYPE_RETRIEVE_ALL = "searchAllSolutionOfferTypes";

	@Id
	@Column(name = "SOLUTION_OFFER_TYPE_NAME")
	private String solutionOfferTypeName;

	@Column(name = "SOLUTION_OFFER_TYPE_DESCRIPTION")
	private String solutionOfferTypeDescription;

	public String getSolutionOfferTypeName() {
		return solutionOfferTypeName;
	}

	public void setSolutionOfferTypeName(String solutionOfferTypeName) {
		this.solutionOfferTypeName = solutionOfferTypeName;
	}

	public String getSolutionOfferTypeDescription() {
		return solutionOfferTypeDescription;
	}

	public void setSolutionOfferTypeDescription(
			String solutionOfferTypeDescription) {
		this.solutionOfferTypeDescription = solutionOfferTypeDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((solutionOfferTypeName == null) ? 0 : solutionOfferTypeName
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefSolutionOfferType other = (RefSolutionOfferType) obj;
		if (solutionOfferTypeName == null) {
			if (other.solutionOfferTypeName != null)
				return false;
		} else if (!solutionOfferTypeName.equals(other.solutionOfferTypeName))
			return false;
		return true;
	}
}