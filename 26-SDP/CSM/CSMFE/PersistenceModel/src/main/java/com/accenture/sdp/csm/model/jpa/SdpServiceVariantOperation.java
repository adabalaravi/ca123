package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_SERVICE_Variant_OPERATION database table.
 * 
 */
@Entity
@Table(name = "SDP_SERVICE_VARIANT_OPERATION")
@NamedQueries({
		@NamedQuery(name = "selectServiceVariantOperationByName", query = "select a from SdpServiceVariantOperation a where a.methodName=:methodName and a.sdpServiceVariant.serviceVariantName=:serviceVariantName"),
		@NamedQuery(name = "selectServiceVariantOperationById", query = "select a from SdpServiceVariantOperation a where a.methodName=:methodName and a.sdpServiceVariant.serviceVariantId=:serviceVariantId"),
		@NamedQuery(name = "selectServiceVariantOperationByServiceVariantId", query = "select a from SdpServiceVariantOperation a where a.sdpServiceVariant.serviceVariantId=:serviceVariantId"),
		@NamedQuery(name = "selectServiceVariantOperationByOperationType", query = "select a from SdpServiceVariantOperation a where a.sdpServiceVariant.serviceVariantId=:serviceVariantId and a.operationType=:operationType") })
@IdClass(SdpServiceVariantOperationPK.class)
public class SdpServiceVariantOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String PARAM_METHOD_NAME = "methodName";

	@Id
	@Column(name = "SERVICE_VARIANT_ID")
	private Long serviceVariantId;

	@Id
	@Column(name = "METHOD_NAME")
	private String methodName;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Lob()
	@Column(name = "INPUT_PARAMETERS")
	private String inputParameters;

	@Lob()
	@Column(name = "INPUT_XSLT")
	private String inputXslt;

	@Lob()
	@Column(name = "OUTPUT_XSLT")
	private String outputXslt;

	@Column(name = "UDDI_KEY")
	private String uddiKey;

	@Column(name = "OPERATION_TYPE")
	private String operationType;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpServiceVariant
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_VARIANT_ID", insertable = false, updatable = false)
	private SdpServiceVariant sdpServiceVariant;

	public SdpServiceVariantOperation() {
	}

	public Long getServiceVariantId() {
		return serviceVariantId;
	}

	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

	public String getInputParameters() {
		return this.inputParameters;
	}

	public void setInputParameters(String inputParameters) {
		this.inputParameters = inputParameters;
	}

	public String getInputXslt() {
		return this.inputXslt;
	}

	public void setInputXslt(String inputXslt) {
		this.inputXslt = inputXslt;
	}

	public String getOutputXslt() {
		return this.outputXslt;
	}

	public void setOutputXslt(String outputXslt) {
		this.outputXslt = outputXslt;
	}

	public String getUddiKey() {
		return this.uddiKey;
	}

	public void setUddiKey(String uddiKey) {
		this.uddiKey = uddiKey;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
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

	public SdpServiceVariant getSdpServiceVariant() {
		return sdpServiceVariant;
	}

	public void setSdpServiceVariant(SdpServiceVariant sdpServiceVariant) {
		this.sdpServiceVariant = sdpServiceVariant;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + ((serviceVariantId == null) ? 0 : serviceVariantId.hashCode());
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
		SdpServiceVariantOperation other = (SdpServiceVariantOperation) obj;
		if (getMethodName() == null) {
			if (other.getMethodName() != null) {
				return false;
			}
		} else if (!getMethodName().equals(other.getMethodName())) {
			return false;
		}
		if (getServiceVariantId() == null) {
			if (other.getServiceVariantId() != null) {
				return false;
			}
		} else if (!getServiceVariantId().equals(other.getServiceVariantId())) {
			return false;
		}
		return true;
	}
}