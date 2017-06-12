package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SDP_TOKEN database table.
 * 
 */

public class SdpTokenPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "TOKEN_ID")
	private String tokenId;

	@Column(name = "TOKEN_PROVIDER")
	private String tokenProvider;

	public SdpTokenPK() {
	}

	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTokenProvider() {
		return this.tokenProvider;
	}

	public void setTokenProvider(String tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
		result = prime * result + ((tokenProvider == null) ? 0 : tokenProvider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SdpTokenPK other = (SdpTokenPK) obj;
		if (tokenId == null) {
			if (other.tokenId != null) {
				return false;
			}
		} else if (!tokenId.equals(other.tokenId)) {
			return false;
		}
		if (tokenProvider == null) {
			if (other.tokenProvider != null) {
				return false;
			}
		} else if (!tokenProvider.equals(other.tokenProvider)) {
			return false;
		}
		return true;
	}

}