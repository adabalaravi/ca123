package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the SDP_SECRET_QUESTION database table.
 * 
 */
@Entity
@Table(name = "SDP_SECRET_QUESTION")
public class SdpSecretQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SECRET_QUESTION_ID")
	private Long secretQuestionId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "SECRET_ANSWER")
	private String secretAnswer;

	@Column(name = "SECRET_QUESTION_DESCRIPTION")
	private String secretQuestionDescription;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpCredential
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
	private SdpCredential sdpCredential;

	public SdpSecretQuestion() {
	}

	public Long getSecretQuestionId() {
		return this.secretQuestionId;
	}

	public void setSecretQuestionId(Long secretQuestionId) {
		this.secretQuestionId = secretQuestionId;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getSecretAnswer() {
		return this.secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getSecretQuestionDescription() {
		return this.secretQuestionDescription;
	}

	public void setSecretQuestionDescription(String secretQuestionDescription) {
		this.secretQuestionDescription = secretQuestionDescription;
	}

	public String getUpdatedById() {
		return this.updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public SdpCredential getSdpCredential() {
		return this.sdpCredential;
	}

	public void setSdpCredential(SdpCredential sdpCredential) {
		this.sdpCredential = sdpCredential;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((secretQuestionId == null) ? 0 : secretQuestionId.hashCode());
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
		SdpSecretQuestion other = (SdpSecretQuestion) obj;
		if (getSecretQuestionId() == null) {
			if (other.getSecretQuestionId() != null) {
				return false;
			}
		} else if (!getSecretQuestionId().equals(other.getSecretQuestionId())) {
			return false;
		}
		return true;
	}

}