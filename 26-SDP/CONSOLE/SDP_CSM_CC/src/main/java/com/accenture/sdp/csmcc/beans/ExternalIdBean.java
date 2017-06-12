package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.RegexValidator;
import javax.faces.validator.ValidatorException;

public class ExternalIdBean implements Serializable, Comparable<ExternalIdBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4831444036575292052L;

	private String externalId;
	private String externalPlatform;
	private String validationPattern;
	
	private RegexValidator regexValidator;

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    if (validationPattern != null) {
	    	regexValidator = new RegexValidator();
	        regexValidator.setPattern(validationPattern);
	        regexValidator.validate(context, component, value);
	    }
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalPlatform() {
		return externalPlatform;
	}

	public void setExternalPlatform(String externalPlatform) {
		this.externalPlatform = externalPlatform;
	}

	public String getValidationPattern() {
		return validationPattern;
	}

	public void setValidationPattern(String validationPattern) {
		this.validationPattern = validationPattern;
	}

	@Override
	public String toString() {
		return "[externalId = " + externalId + ", externalPlatform=" + externalPlatform + "]";
	}

	public int compareTo(ExternalIdBean o) {
		return externalPlatform.compareTo(o.externalPlatform);
	}

}
