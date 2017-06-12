package com.accenture.sdp.csmfe.webservices.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.utilities.CodeManager;
import com.accenture.sdp.csm.utilities.Constants;

/**
 * @author elia.furiozzi
 * 
 */
@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class BaseResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 625214020704196218L;

	private String resultCode;
	private String description;
	private ParameterListResp parameters;

	public BaseResp() {
		try {
			resultCode = CodeManager.loadCode(null, Constants.CODE_GENERIC_ERROR);
			description = CodeManager.loadCodeDescription(null, Constants.CODE_GENERIC_ERROR);
		} catch (PropertyNotFoundException e) {
			resultCode = "070";
			description = "ERROR Generic Error";
		}
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ParameterListResp getParameters() {
		return parameters;
	}

	public void setParameters(ParameterListResp parameters) {
		this.parameters = parameters;
	}

}
